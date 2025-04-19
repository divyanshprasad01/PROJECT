package com.EduHub.Authenticator.SecurityFilters;

import com.EduHub.Authenticator.Models.Users;
import com.EduHub.Authenticator.Services.JwtService;
import com.EduHub.Authenticator.Services.UserDetailsServiceConf;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


// This is Json Web Token filter in spring security filter chain which is set before username password filter in configuration file.
// we needed to configure it so that we can add jwtToken functionality in our api..
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

//  context is used to get a bean which was required because it was giving error as circular reference..
    @Autowired
    ApplicationContext context;

//  Implementation of this method is required for jwt verification...
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//      It gets the authorization part from the request header.
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

//       checks weather jwt token is there or not, if it is there jwTokens always start with "Bearer "..
        if(authHeader != null && authHeader.startsWith("Bearer ")){
//          Removes the Bearer part from the autheader and stores the token in token variable..
            token = authHeader.substring(7);
//          Extracts the username from the token and stores it in username variable.
            username = jwtService.extractUsernameFromToken(token);
        }else{
//            If token is not present just continue with the other filters...
            filterChain.doFilter(request, response);
        }

//      Only authenticate if a user is not already authenticated...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
//          Loads the user from the database using username and set it in user object.
            Users user = (Users) context.getBean(UserDetailsServiceConf.class).loadUserByUsername(username);

//          Validates the token that it belongs to the user or not and if it is not expired..
//          and then it authenticates the user with all the details and stores it in an authentication object and sets it in the context.
            if(jwtService.validateToken(token, user)){
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);

    }
}
