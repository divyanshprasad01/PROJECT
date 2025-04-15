package com.EduHub.Authenticator.Config;

import com.EduHub.Authenticator.Services.UserDetailsServiceConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//A class to implement security configurations instead of using the default one provided by spring security...
@Configuration
@EnableWebSecurity
public class SecurityConfigrations {

    @Autowired
    private UserDetailsServiceConf userDetailsService;

//   Here defining filter chain of the spring security so that it can use those filters to authenticate and reject users...
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        return httpSecurity
//                This disables cross site request forgery filter...it must be implemented it is a security issue
//                just disabled it for now will implement later...
                .csrf(customizer -> customizer.disable())
//                this method authorizez http rquests if user is accessing signup or login page then it will not ask for authentication
//                for any other request user must be authenticated...
                .authorizeHttpRequests(request -> request
                                                                            .requestMatchers("/signup", "/login")
                                                                            .permitAll()
                                                                            .anyRequest().authenticated())
//               It sets the authentication method to default basic username password authentication which is provided by spring security...
                .httpBasic(Customizer.withDefaults())
//                It provides a basic form UI provided by spring security to type in username and password...
                .formLogin(Customizer.withDefaults())
//                This method sets the session to a stateless so that it will not remember the user and ask everytime for authentication
//                if user closes the application or breaks the session...
                .sessionManagement(session -> session
                                                                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//              It will build the default security filter chain with provided additional configurations...
                .build();
    }


//  To connect our database with our spring security we need to implement this method provided in spring security
//    and return it and object of DAO type of provider so that it will go to our service to search user in our database.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//       It sets password encoder to Bcrypt and sets its strength to 12 so password will be stored in database in encrypted form..
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
//      It sets our created class which implements userDetailsService interface to fetch the user from database using username..
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    //For testing a starter code without database....
//    @Bean
//    public UserDetailsServiceConf userDetailsService(){
//        UserDetails user1 = Users
//                .withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin@123")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = Users
//                .withDefaultPasswordEncoder()
//                .username("admin2")
//                .password("admin2@123")
//                .roles("ADMIN")
//                .build();
//
//
//        return userDetailsService;
//    }

}
