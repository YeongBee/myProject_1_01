package com.yeongbee.store.mydelight.ipconfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public GeoIPService geoIPService(){
        return new GeoIPService();
    }

    @Bean
    public FilterRegistrationBean<GeoIPFilter> geoIPFilterRegistrationBean(GeoIPService geoIPService) {
        FilterRegistrationBean<GeoIPFilter> registrationBean = new FilterRegistrationBean<>();
        GeoIPFilter geoIPFilter = new GeoIPFilter(geoIPService);

        registrationBean.setFilter(geoIPFilter);
        registrationBean.addUrlPatterns("/*"); // 모든 요청에 대해 필터 적용

        return registrationBean;
    }
}
