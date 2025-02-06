package com.vishal.recipeBackend.dto;

import org.springframework.web.multipart.MultipartFile;

public class chefDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private MultipartFile profilepic;

    public MultipartFile getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(MultipartFile profilepic) {
        this.profilepic = profilepic;
    }


    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }



}
