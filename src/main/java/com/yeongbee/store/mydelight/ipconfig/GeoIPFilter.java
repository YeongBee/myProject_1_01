package com.yeongbee.store.mydelight.ipconfig;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GeoIPFilter implements Filter{

    private final GeoIPService geoIPService;

    public GeoIPFilter(GeoIPService geoIPService) {
        this.geoIPService = geoIPService;
    }

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws ServletException {
        //필터 초기화
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

/*        String path = request.getRequestURI().substring(request.getContextPath().length());
//        log.info("path={}", path);

        //TODO static 폴더만 줄일 수 있을까?
        if (path.startsWith("/my")|| path.startsWith("/blogs")||path.startsWith("/errors")||
                path.startsWith("favicon") || path.startsWith("/discords")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }*/

        try {
            if (geoIPService.isBlockedCountry(request)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                log.info("Access Denied");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            log.info("Internal Server Error");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        //필터 제거
    }
}




