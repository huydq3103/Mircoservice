package com.example.apigateway.filter;

import com.example.apigateway.client.AuthServiceClient;
import com.example.apigateway.constant.UrlMappingConstants;
import com.example.apigateway.mapper.UrlMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Component
public class AuthorizationFilter implements GatewayFilter, Ordered {

    @Autowired
    private AuthServiceClient authServiceClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String token = extractToken(request);
        if (token == null) {
            log.error("No authorization token found in request");
            return onError(exchange, "No authorization token", HttpStatus.UNAUTHORIZED);
        }

        log.debug("Validating token: {}", token);

        return authServiceClient.validateTokenReactive(token)
                .doOnNext(authResponse -> log.debug("Auth response received: {}", authResponse))
                .flatMap(authResponse -> {
                    String path = request.getURI().getPath();

                    String originalUrl = UrlMapping.getOriginalUrlByMappedUrl(path);
                    if (originalUrl != null) {
                        path = originalUrl; // Use original path for permission checks
                    }

                    System.out.println(path+" -> path ne");

                    if (!hasPermission(authResponse.getRoles(), path)) {
                        log.warn("Access denied for user {} to path {}", authResponse.getUsername(), path);
                        return onError(exchange, "Access denied", HttpStatus.FORBIDDEN);
                    }

                    log.debug("Access granted for user {} to path {}", authResponse.getUsername(), path);

                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("X-User-Name", authResponse.getUsername())
                            .header("X-User-Roles", String.join(",", authResponse.getRoles()))
                            .header("X-Is-Ldap", String.valueOf(authResponse.isLdap()))
                            .build();

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                })
                .onErrorResume(e -> {
                    log.error("Error during authorization", e);
                    return onError(exchange, "Error during authorization", HttpStatus.UNAUTHORIZED);
                });
    }

    private boolean hasPermission(List<String> roles, String path) {
        // Tạo ánh xạ giữa originalUrl và permission
        Map<String, String> urlToPermissionMap = Arrays.stream(UrlMapping.values())
                .collect(Collectors.toMap(UrlMapping::getOriginalUrl, UrlMapping::getPermission));

        // Xác định quyền yêu cầu cho đường dẫn hiện tại
        String requiredPermission = urlToPermissionMap.get(path);

        // Nếu không có quyền yêu cầu cho đường dẫn, chặn truy cập (hoặc cho phép nếu bạn muốn)
        if (requiredPermission == null || requiredPermission.isEmpty()) {
            return false; // Có thể điều chỉnh tùy theo yêu cầu
        }

        // Kiểm tra xem vai trò có quyền yêu cầu hay không
        for (String role : roles) {
            if (requiredPermission.equals(role)) {
                return true; // Vai trò có quyền này
            }
        }

        return false; // Không có quyền phù hợp
    }

    private String extractToken(ServerHttpRequest request) {
        List<String> authHeaders = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeaders != null && !authHeaders.isEmpty()) {
            String authHeader = authHeaders.get(0);
            if (authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7);
            }
        }
        return null;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        // Tạo message body
        byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        // Trả về một số âm để đảm bảo filter này chạy sớm trong chuỗi
        return -1;
    }
}