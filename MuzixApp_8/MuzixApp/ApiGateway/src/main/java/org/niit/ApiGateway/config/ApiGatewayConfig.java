package org.niit.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator getRoutes(RouteLocatorBuilder builder){

//        return builder.routes()
//                .route(p->p.path("/api/authservice/**").
//                        uri("http://localhost:8081/"))
//                .route(p->p.path("/api/movieservice/**").
//                        uri("http://localhost:8082/"))
//                .build();

        return builder.routes()
                .route(p->p.path("/api/authservice/**").
                        uri("lb://auth-services"))
                .route(p->p.path("/api/movieservice/**").
                        uri("lb://movie-service"))
                .route(p->p.path("/api/favouriteservice/**").
                        uri("lb://favourite-service"))
                .route(p->p.path("/api/paymentservice/**").
                        uri("lb://payment-service"))
                .route(p->p.path("/api/emailservice/**").
                        uri("lb://email-service"))
                .build();

    }

//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedOriginPattern("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
//        source.registerCorsConfiguration("/**",config);
//        return new CorsFilter(source);
//    }
}
