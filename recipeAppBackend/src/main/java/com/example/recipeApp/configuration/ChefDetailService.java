package com.example.recipeApp.configuration;

import com.example.recipeApp.model.Chef;
import com.example.recipeApp.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ChefDetailService implements UserDetailsService {
    @Autowired
    ChefRepository chefrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Chef chef=chefrepo.findByChefEmail(username);
        if(chef==null){
            throw new UsernameNotFoundException("Chef not found in database");
        }
        return new ChefDetails(chef);
    }
}
