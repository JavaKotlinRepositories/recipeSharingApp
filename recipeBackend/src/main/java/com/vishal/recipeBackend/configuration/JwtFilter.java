package com.vishal.recipeBackend.configuration;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vishal.recipeBackend.service.ChefService;
import com.vishal.recipeBackend.service.JwtTokenGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

//@Service
public class JwtFilter extends OncePerRequestFilter {
//    @Autowired
    JwtTokenGenerator jwtTokenGenerator;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");

        if(authHeader!=null && authHeader.startsWith("Bearer ")) {
            String token=authHeader.substring(7);
            try {
                DecodedJWT decodedJWT = jwtTokenGenerator.validateToken(token);
                String username = decodedJWT.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Create authentication object
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(new ChefService());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication in security context
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


}
