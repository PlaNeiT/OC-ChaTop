package com.openclassrooms.OC_ChaTop.configurations;

import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Configuration class for application security settings and beans.
 */
@Configuration
public class AppConfig {

    private final UserRepository userRepository;

    /**
     * Constructor injection of UserRepository.
     *
     * @param userRepository the repository used to fetch user data
     */
    public AppConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Bean for UserDetailsService that loads user-specific data.
     *
     * @return UserDetailsService that fetches user details from the repository
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


    /**
     * Bean for password encoding using BCrypt.
     *
     * @return BCryptPasswordEncoder for encoding passwords
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for AuthenticationManager to handle authentication.
     *
     * @param config AuthenticationConfiguration to configure the manager
     * @return AuthenticationManager used for handling authentication
     * @throws Exception if there is an issue with the configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Bean for AuthenticationProvider to authenticate users.
     *
     * @return DaoAuthenticationProvider that uses the user details service and password encoder
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
