package com.EduHub.Authenticator.Services;

import com.EduHub.Authenticator.Models.Users;
import com.EduHub.Authenticator.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    @Autowired
    private UsersRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users signUpAndSave(Users users){
        users.setPassword(encoder.encode(users.getPassword()));
        repo.save(users);

        return users;
    }
}
