package com.example.apigateway.until.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Enum đại diện cho các vai trò và các quyền tương ứng trong ứng dụng.
 */
public enum RolePermissionEnum {

    /**
     * Vai trò người dùng không có quyền mặc định.
     */
    ROLE_USER(),

    /**
     * Vai trò quản trị viên với quyền đọc, viết, cập nhật và xóa thông tin đồ uống.
     */
    ROLE_ADMIN(
            PermissionEnum.READ_DRINK,
            PermissionEnum.WRITE_DRINK,
            PermissionEnum.UPDATE_DRINK,
            PermissionEnum.DELETE_DRINK,
            PermissionEnum.VIEW_PERMISSIONS,
            PermissionEnum.ADD_PERMISSIONS_FROM_ROLE,
            PermissionEnum.DELETE_PERMISSIONS_FROM_ROLE,
            PermissionEnum.VIEW_ROLES

    ),

    /**
     * Vai trò quản lý với quyền đọc, viết và cập nhật thông tin đồ uống.
     */
    ROLE_MANAGER(
            PermissionEnum.READ_DRINK,
            PermissionEnum.WRITE_DRINK,
            PermissionEnum.UPDATE_DRINK
    ),
    ROLE_MOD();

    private final Set<PermissionEnum> permissions;

    /**
     * Khởi tạo một vai trò với tập hợp các quyền.
     *
     * @param permissions Các quyền thuộc vai trò.
     */
    RolePermissionEnum(PermissionEnum... permissions) {
        this.permissions = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(permissions)));
    }

    /**
     * Lấy tên của vai trò.
     *
     * @return Tên của vai trò.
     */
    public String getName() {
        return this.name();
    }

    /**
     * Lấy tập hợp các quyền thuộc vai trò.
     *
     * @return Tập hợp các quyền.
     */
    public Set<PermissionEnum> getPermissions() {
        return permissions;
    }

    /**
     * Lấy tập hợp các quyền cho vai trò cụ thể.
     *
     * @param roleName Tên của vai trò cần lấy quyền.
     * @return Tập hợp các quyền thuộc vai trò, hoặc tập hợp rỗng nếu vai trò không tồn tại.
     */
    public static Set<PermissionEnum> getPermissionEnumsForRole(String roleName) {
        try {
            return valueOf(roleName).permissions;
        } catch (IllegalArgumentException e) {
            return Collections.emptySet();
        }
    }

    /**
     * Lấy tất cả các vai trò và quyền tương ứng.
     *
     * @return Tập hợp các RolePermissionEnum.
     */
    public static Set<RolePermissionEnum> getAllRolePermissions() {
        return new HashSet<>(Arrays.asList(RolePermissionEnum.values()));
    }
}