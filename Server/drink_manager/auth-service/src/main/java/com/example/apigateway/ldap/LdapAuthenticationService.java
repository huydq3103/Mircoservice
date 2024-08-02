package com.example.apigateway.ldap;

import com.example.apigateway.entity.PermissionEntity;
import com.example.apigateway.entity.RoleEntity;
import com.example.apigateway.services.implement.PermissionServiceImpl;
import com.example.apigateway.services.implement.RoleServiceImpl;
import com.example.apigateway.until.constants.LdapConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@Slf4j
public class LdapAuthenticationService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private PermissionServiceImpl permissionService;

    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

    @Autowired
    private RoleServiceImpl roleService;

    /**
     * Xác thực người dùng LDAP với tên người dùng và mật khẩu.
     *
     * @param username Tên người dùng.
     * @param password Mật khẩu.
     * @return Thông tin chi tiết người dùng.
     * @throws BadCredentialsException Nếu xác thực thất bại.
     * @throws RuntimeException Nếu có lỗi bất ngờ xảy ra.
     */
    public UserDetails authenticateLdapUser(String username, String password) {
        try {
            ldapTemplate.authenticate(query().where(LdapConstants.UID_ATTRIBUTE).is(username), password);
            return loadLdapUserDetails(username);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("LDAP authentication failed", e);
        } catch (Exception e) {
            log.error("Unexpected error during LDAP authentication for username: {}", username, e);
            throw new RuntimeException("Unexpected error during LDAP authentication", e);
        }
    }

    /**
     * Tải thông tin chi tiết người dùng LDAP bằng tên người dùng.
     *
     * @param username Tên người dùng.
     * @return Thông tin chi tiết người dùng.
     * @throws UsernameNotFoundException Nếu người dùng không được tìm thấy trong LDAP.
     * @throws RuntimeException Nếu có lỗi bất ngờ xảy ra.
     */
    public UserDetails loadLdapUserDetails(String username) {
        try {
            DirContextOperations context = ldapTemplate.searchForContext(
                    query().where(LdapConstants.UID_ATTRIBUTE).is(username));

            List<GrantedAuthority> authorities = new ArrayList<>(mapLdapRolesToAppRoles(context));

            log.info("Loaded user details for username: {}", username);
            return new User(username, "", authorities);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User not found in LDAP", e);
        } catch (Exception e) {
            log.error("Failed to load LDAP user details for username: {}", username, e);
            throw new RuntimeException("Failed to load LDAP user details", e);
        }
    }

    /**
     * Ánh xạ vai trò LDAP thành vai trò ứng dụng.
     *
     * @param context Ngữ cảnh DirContextOperations.
     * @return Tập hợp các GrantedAuthority.
     */
    public Set<GrantedAuthority> mapLdapRolesToAppRoles(DirContextOperations context) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        String uid = context.getStringAttribute("uid");
        log.debug("User UID: {}", uid);

        Set<RoleEntity> roles = mapLdapGroupsToAppRoles(context);

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            for (PermissionEntity permission : role.getPermissionss()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
        }

        log.debug("Mapped authorities: {}", authorities);
        return authorities;
    }

    /**
     * Ánh xạ nhóm LDAP thành vai trò ứng dụng.
     *
     * @param context Ngữ cảnh DirContextOperations.
     * @return Tập hợp các RoleEntity.
     */
    private Set<RoleEntity> mapLdapGroupsToAppRoles(DirContextOperations context) {
        Set<RoleEntity> roles = new HashSet<>();

        String uid = context.getStringAttribute("uid");
        log.debug("Mapping roles for user: {}", uid);

        /**
         * Thực hiện truy vấn LDAP để tìm nhóm của người dùng
         * objectclass: Xác định loại của entry trong LDAP. "groupOfUniqueNames" là một loại nhóm phổ biến trong LDAP.
         * uniqueMember: Chứa DNs của các thành viên trong nhóm.
         * cn (Common Name): Thường được sử dụng để lưu trữ tên của nhóm.
         */
        AndFilter filter = new AndFilter();

        /**
         * Tạo một điều kiện tìm kiếm các entry có thuộc tính objectClass là "groupOfUniqueNames".
         * Điều này đảm bảo rằng chúng ta chỉ tìm kiếm các nhóm, không phải các loại entry khác
         */
        filter.and(new EqualsFilter(LdapConstants.OBJECT_CLASS_ATTRIBUTE, LdapConstants.GROUP_OF_UNIQUE_NAMES));

        /**
         * Tạo một điều kiện tìm kiếm các nhóm mà có thuộc tính uniqueMember chứa DN của người dùng hiện tại.
         * Điều này giúp chúng ta tìm tất cả các nhóm mà người dùng là thành viên
         */
        filter.and(new EqualsFilter(LdapConstants.UNIQUE_MEMBER_ATTRIBUTE, context.getDn().toString()));

        List<String> groupNames = ldapTemplate.search(
                LdapUtils.emptyLdapName(),
                filter.encode(),
                (AttributesMapper<String>) attrs -> (String) attrs.get(LdapConstants.CN_ATTRIBUTE).get()
        );

        for (String groupName : groupNames) {
            log.debug("User belongs to group: {}", groupName);
            if (LdapConstants.GROUP_MATHEMATICIANS.equalsIgnoreCase(groupName)) {
                roleService.findByName(LdapConstants.ROLE_ADMIN).ifPresent(roles::add);
            } else if (LdapConstants.GROUP_SCIENTISTS.equalsIgnoreCase(groupName)) {
                roleService.findByName(LdapConstants.ROLE_MANAGER).ifPresent(roles::add);
            }
        }

        // Nếu không tìm thấy vai trò nào, gán vai trò mặc định
        if (roles.isEmpty()) {
            roleService.findByName(LdapConstants.ROLE_USER).ifPresent(roles::add);
        }

        log.debug("Mapped roles: {}", roles);
        return roles;
    }
}
