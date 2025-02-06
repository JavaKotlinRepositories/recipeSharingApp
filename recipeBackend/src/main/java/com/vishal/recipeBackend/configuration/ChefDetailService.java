package com.vishal.recipeBackend.configuration;

import com.vishal.recipeBackend.model.Chefs;
import com.vishal.recipeBackend.repository.ChefsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ChefDetailService implements UserDetailsService {

    @Autowired
    ChefsRepository chefRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Chefs chef=chefRepo.findByEmail(email);
        
        if(chef==null){
            throw new UsernameNotFoundException(email);
        }
        return new ChefDetails(chef.getEmail(), chef.getPassword());
    }
}
