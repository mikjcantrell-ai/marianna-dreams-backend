package com.mariannadreams.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration for the Marianna Dreams API.
 *
 * <p>Strategy:
 * <ul>
 *   <li>All GET requests to any path — public.</li>
 *   <li>POST /api/newsletter/** and POST /api/contact — public (fan-facing).</li>
 *   <li>All other POST/PUT/DELETE — protected with HTTP Basic (admin).</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.user.name:admin}")
    private String adminUsername;

    @Value("${spring.security.user.password:marianna2024}")
    private String adminPassword;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username(adminUsername)
            .password("{noop}" + adminPassword) // {noop} disables password encoding since it's raw text in properties
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // delegated to CorsConfig
            .authorizeHttpRequests(auth -> auth
                // All GET requests are public (covers /api/songs/1/lyrics etc.)
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/**")).permitAll()
                // Fan-facing POST endpoints are public
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/newsletter/**")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/contact")).permitAll()
                // Everything else (admin writes) requires Basic auth
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});
        return http.build();
    }
}
