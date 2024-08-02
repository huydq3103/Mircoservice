package com.example.apigateway.jwt;

import com.example.apigateway.ldap.LdapAuthenticationService;
import com.example.apigateway.services.implement.CustomUserDetailsService;

import com.example.apigateway.until.constants.CommonConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private LdapAuthenticationService ldapAuthenticationService;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(CommonConstant.HEADER_AUTHORIZATION);

        String username = null;
        String jwt = null;

        boolean isLdap = false;


        if (authorizationHeader != null && authorizationHeader.startsWith(CommonConstant.BEARER)) {
            jwt = authorizationHeader.substring(CommonConstant.SEVEN);
            username = jwtUtil.extractUsername(jwt);
            isLdap = jwtUtil.extractLdap(jwt);

            log.info("JWT Token: {}", jwt);
            log.info("Extracted Username: {}", username);
            log.info("Is LDAP User: {}", isLdap);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;

            if (isLdap) {
                userDetails = ldapAuthenticationService.loadLdapUserDetails(username);
            } else {
                userDetails = customUserDetailsService.loadUserByUsername(username);
            }

            log.info("UserDetails: {}", userDetails);
            log.info("Authorities: {}", userDetails.getAuthorities());

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                log.info("Authentication successful for user: {}", username);
            } else {
                log.error("JWT validation failed for user: {}", username);
            }
           
        }
        chain.doFilter(request, response);
    }



}
