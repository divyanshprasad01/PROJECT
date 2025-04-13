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

@Configuration
@EnableWebSecurity
public class SecurityConfigrations {

    @Autowired
    private UserDetailsServiceConf userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {

        return httpSecurity.csrf(customizer -> customizer.disable())
           .authorizeHttpRequests(request -> request.requestMatchers("/signup", "/login").permitAll()
                                                                            .anyRequest().authenticated())
           .httpBasic(Customizer.withDefaults())
           .formLogin(Customizer.withDefaults())
           .sessionManagement(
                   session ->
                           session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
           .build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
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
