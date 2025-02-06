package com.vishal.recipeBackend.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class HttpConfigFile {

    @Autowired
    ChefDetailService chefDetailService;

//    @Autowired
//    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(customizer->customizer.disable());
        http.authorizeHttpRequests(auth->auth.requestMatchers("/backend/login","/backend/signup").permitAll().anyRequest().authenticated());
        http.formLogin(Customizer->Customizer.disable());
        http.httpBasic(customizer->customizer.disable());
//        http.httpBasic(Customizer.withDefaults());
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authprovider() {
    DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
    authprovider.setPasswordEncoder(new BCryptPasswordEncoder(12));
    authprovider.setUserDetailsService(chefDetailService);
    return authprovider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(chefDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return new ProviderManager(List.of(authProvider));
    }
}
