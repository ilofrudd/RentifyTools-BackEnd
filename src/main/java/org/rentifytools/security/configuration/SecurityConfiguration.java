package org.rentifytools.security.configuration;

import lombok.RequiredArgsConstructor;
import org.rentifytools.security.sec_filters.TokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenFilter filter;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/v3/api-docs/**", "/configuration/ui", "/swagger-resources/**",
                                        "/configuration/security", "/swagger-ui/**", "/webjars/**",
                                        "/swagger-resources/configuration/ui", "/swagger-ui/index.html").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/files/upload").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/users/check-email").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/me").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/users/search").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/tools").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/tools/{toolId}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/tools/me").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/tools/search").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/tools").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/tools/{toolId}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/tools/{toolId}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/tools/{toolId}").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/tools/category/{categoryId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/categories").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/refresh").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/address").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/address/city-zip").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/address").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/address/{id}").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
