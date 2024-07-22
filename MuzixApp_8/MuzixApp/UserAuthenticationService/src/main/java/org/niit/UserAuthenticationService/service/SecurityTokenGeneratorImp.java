package org.niit.UserAuthenticationService.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.niit.UserAuthenticationService.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class SecurityTokenGeneratorImp implements ISecurityTokenGenerator
{
    @Override
    public Map<String,String>tokenGenerator(User user)
    {
        String jwtToken=null;
        jwtToken= Jwts.builder()// jetbuilder instance
                .setSubject(user.getEmail())// set sub as user email
                .setIssuedAt(new Date())// date
                .signWith(SignatureAlgorithm.HS256,"securityKey")// algo and key(at time of filter)
                .compact();
        Map<String,String> map=new HashMap<>();
        map.put("token",jwtToken);
        map.put("message","User successfully loggedin");
        return map;
    }
}
