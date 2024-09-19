package com.openclassrooms.OC_ChaTop.configurations;

import com.openclassrooms.OC_ChaTop.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * JwtAuthenticationFilter is responsible for filtering each request to validate JWT tokens
 * and set up the security context if the token is valid.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService; // Service for handling JWT operations

    @Autowired
    private UserDetailsService userDetailsService; // Service to load user-specific data

    /**
     * Filters incoming requests to validate JWT tokens and set the authentication context.
     *
     * @param request     the incoming HTTP request
     * @param response    the outgoing HTTP response
     * @param filterChain the filter chain to pass the request and response along
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Retrieve the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Extract the JWT token
            username = jwtService.extractUsername(jwt); // Extract the username from the token
        }

        // Validate the extracted token and set the authentication context if valid
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Check if the token is valid for the extracted user details
            if (jwtService.validateToken(jwt, userDetails)) {
                // Create an authentication token and set the details
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
