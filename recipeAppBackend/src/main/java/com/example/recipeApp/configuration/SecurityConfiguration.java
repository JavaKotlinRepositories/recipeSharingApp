package com.example.recipeApp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    ChefDetailService chefDetailService;
    @Autowired
    JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain configureHttpSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer->customizer.disable());
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(request->request.requestMatchers("login","signup").permitAll().anyRequest().authenticated());
        http.addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider configureAuthProvider(){
        DaoAuthenticationProvider daoAuth=new DaoAuthenticationProvider();
        daoAuth.setPasswordEncoder(new BCryptPasswordEncoder(12));
        daoAuth.setUserDetailsService(chefDetailService);
        return daoAuth;
    }

    @Bean
    public AuthenticationManager getAuthManagerIntoSpringContext(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
