package com.chukwuemeka.xpresswalletapi.setup.filters;

import com.chukwuemeka.xpresswalletapi.setup.exceptions.XpressException;
import com.chukwuemeka.xpresswalletapi.services.implementations.JwtImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtImplementation jwtImplementation;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String jwtHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmailAddress;
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = jwtHeader.substring(7);

        if(jwtImplementation.isExpired(jwtToken)) {
            throw new XpressException("Your session has expired. Please login");
        }
        userEmailAddress = jwtImplementation.extractEmailAddressFromToken(jwtToken);
        if(userEmailAddress != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                   this.userDetailsService.loadUserByUsername(userEmailAddress),
                   null,
                   Collections.emptyList()
            );

           token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           SecurityContextHolder.getContext().setAuthentication(token);
        }
        filterChain.doFilter(request,response);
    }
}
