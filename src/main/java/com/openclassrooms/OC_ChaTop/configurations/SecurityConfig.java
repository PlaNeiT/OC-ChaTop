package com.openclassrooms.OC_ChaTop.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SecurityConfig class configures the security settings for the application.
 * It manages CORS, session management, JWT authentication, and route permissions.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor for SecurityConfig to inject dependencies.
     *
     * @param jwtAuthenticationFilter    the JWT authentication filter.
     * @param authenticationProvider     the authentication provider.
     */
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity object used to configure security.
     * @return a configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS with default settings
                .cors(withDefaults())
                // Disable CSRF as it's not needed with JWT
                .csrf(AbstractHttpConfigurer::disable)
                // Configure URL permissions
                .authorizeHttpRequests(auth -> auth
                        // Allow access to specific endpoints without authentication
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                // Set session management to stateless because JWT is used
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Set the custom authentication provider
                .authenticationProvider(authenticationProvider)
                // Add JWT authentication filter before the UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures CORS settings for the application.
     *
     * @return a CorsConfigurationSource containing the CORS configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Define allowed origins for CORS
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:3001"));
        // Define allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // Define allowed headers
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // Register the CORS configuration for all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
