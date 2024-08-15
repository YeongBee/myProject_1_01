package com.yeongbee.store.mydelight.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] ALL_PATTERNS = new String[]{
            "/h2-console/**",
            "/blog/create",
            /*"/login",*/
            "/weather/api",
            "/weather/list",
            "/blog/test",
            "/blog/test2",
            "/blog/upload",
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/blog/tests").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/blog/create").authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .csrf( csrf -> csrf.ignoringRequestMatchers( "/blog/upload"))

//                .csrf(auth -> auth.disable())
                  /*  .csrf(csrf -> Arrays.stream(ALL_PATTERNS)
                            .map(pattern -> new AntPathRequestMatcher(pattern, "POST"))
                            .forEach(csrf::ignoringRequestMatchers)
                    )*/
                /*.csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )*/
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .sessionManagement((auth) -> auth
                        .sessionFixation()
                        .changeSessionId()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false))

                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(new CustomAuthSuccessHandler()))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/blog")
                        .invalidateHttpSession(true)
                        .deleteCookies()
                );

        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
