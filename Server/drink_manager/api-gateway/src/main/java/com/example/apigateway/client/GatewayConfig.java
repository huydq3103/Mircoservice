package com.example.apigateway.client;

import com.example.apigateway.constant.UrlMappingConstants;
import com.example.apigateway.filter.AuthorizationFilter;
import com.example.apigateway.mapper.UrlMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

//@Configuration
//public class GatewayConfig {
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        RouteLocatorBuilder.Builder routes = builder.routes();
//
//        // Duyệt qua các ánh xạ URL từ enum và cấu hình các route
//        for (UrlMapping mapping : UrlMapping.values()) {
//            routes.route(mapping.getOriginalUrl(), r -> r
//                    .path(mapping.getMappedUrl()) // Lắng nghe yêu cầu đến drink-service với ánh xạ URL
//                    .filters(f -> f.rewritePath(mapping.getMappedUrl(), mapping.getOriginalUrl())) // Ánh xạ lại yêu cầu đến URL gốc
//                    .uri(UrlMappingConstants.LOAD_BALANCER + mapping.getServiceName())); // Chỉ định dịch vụ đích
//        }
//
//        return routes.build();
//    }
//}

@Configuration
public class GatewayConfig {

    @Autowired
    @Lazy
    private AuthorizationFilter authorizationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routes = builder.routes();

        for (UrlMapping mapping : UrlMapping.values()) {
            routes.route(mapping.getOriginalUrl(), r -> r
                    .path(mapping.getMappedUrl())
                    .filters(f -> f
                            .rewritePath(mapping.getMappedUrl(), mapping.getOriginalUrl())
                            .filter(authorizationFilter)
                    )
                    .uri(UrlMappingConstants.LOAD_BALANCER + mapping.getServiceName()));
        }

        return routes.build();
    }
}
