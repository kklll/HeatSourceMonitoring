package com.nuc.heatsourcemonitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @author:
 * @create:
 **/
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 1允许任何域名使用
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
        corsConfiguration.addAllowedHeader("*");
        // 2允许任何头
        corsConfiguration.addAllowedMethod("*");
        // 3允许任何方法（post、get等）
        corsConfiguration.setAllowCredentials(true);
        //支持安全证书。跨域携带cookie需要配置这个
        corsConfiguration.setMaxAge(3600L);
        //预检请求的有效期，单位为秒。设置maxage，可以避免每次都发出预检请求
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
