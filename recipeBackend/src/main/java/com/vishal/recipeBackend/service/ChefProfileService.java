package com.vishal.recipeBackend.service;

import com.vishal.recipeBackend.model.Chefs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class ChefProfileService {

    @Autowired
    private ChefService chefService;

    public HashMap<String, Object> chefInfo(Chefs chef) {
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("id",chef.getId());
        hm.put("firstName",chef.getFirstName());
        hm.put("lastName",chef.getLastName());
        hm.put("profilepic",chefService.preSignedUrl(chef.getProfilepic(),chefService.profilePicBucket));

        return hm;
    }
}
