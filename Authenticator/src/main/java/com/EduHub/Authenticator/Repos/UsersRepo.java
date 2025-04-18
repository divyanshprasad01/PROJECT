package com.EduHub.Authenticator.Repos;

import com.EduHub.Authenticator.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//User repo interface to access database using spring data jpa....
@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);
    Users save(Users users);

}
