package com.EduHub.Authenticator.Services;

import com.EduHub.Authenticator.Models.Users;
import com.EduHub.Authenticator.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceConf implements UserDetailsService {

    @Autowired
    private UsersRepo repo;


//We needed to implement this loadbyUsername method of UserDetailsService interface so that it can fetch the user with the help of
//    spring data jpa and return a user object to it.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = repo.findByUsername(username);
        if(users == null){
            System.out.println("users not found!!");
        }
        return  users;
    }
}
