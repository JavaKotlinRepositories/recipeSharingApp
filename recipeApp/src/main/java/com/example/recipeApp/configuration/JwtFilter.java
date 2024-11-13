package com.example.recipeApp.configuration;

import com.example.recipeApp.model.Chef;
import com.example.recipeApp.repository.ChefRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    ChefRepository chefRepo;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String userId=null;
        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token=authHeader.substring(7);
            userId=jwtService.getTokenSubject(token);
        }
        if(userId!=null && jwtService.isTokenValid(token)){
            Long longUserId=Long.parseLong(userId);
            Optional<Chef> chef=chefRepo.findById(longUserId);
            if(chef.isPresent()){
                ChefDetails chefDet=new ChefDetails(chef.get());
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(chefDet,null,chefDet.getAuthorities()));
                request.setAttribute("userId",longUserId);
            }
        }
        filterChain.doFilter(request,response);
    }
}
