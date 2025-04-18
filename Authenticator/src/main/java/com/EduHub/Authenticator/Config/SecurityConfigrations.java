package com.EduHub.Authenticator.Config;

import com.EduHub.Authenticator.Services.UserDetailsServiceConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
//                this method authorizes http requests if user is accessing signup or login page then it will not ask for authentication
//                for any other request user must be authenticated...
                .authorizeHttpRequests(request -> request
                                                                            .requestMatchers("signup","login")
                                                                            .permitAll()
                                                                            .anyRequest().authenticated())
//               It sets the authentication method to default basic username password authentication which is provided by spring security...
                .httpBasic(Customizer.withDefaults())

//                This method sets the session to a Always so that it will remember the user and ask everytime for authentication
//                if user closes the application or breaks the session...
                .sessionManagement(session -> session
                                                                            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
