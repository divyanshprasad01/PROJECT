package com.EduHub.Authenticator.Services;

import com.EduHub.Authenticator.Models.Users;
import com.EduHub.Authenticator.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//Just a simple service class to save users in the database nothing related to spring security here...
@Service
public class SignUpService {

    @Autowired
    private UsersRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users signUpAndSave(Users users){
//      it changes plane password and encodes it with BcryptPasswordEncoder with the strength of 12...
        users.setPassword(encoder.encode(users.getPassword()));
//      Simply saves the user and return it to show success...
        repo.save(users);

        return users;
    }
}
