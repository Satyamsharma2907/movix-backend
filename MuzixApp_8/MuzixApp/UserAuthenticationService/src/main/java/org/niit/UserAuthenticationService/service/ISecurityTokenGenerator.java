package org.niit.UserAuthenticationService.service;

import org.niit.UserAuthenticationService.domain.User;

import java.util.Map;

public interface ISecurityTokenGenerator {
    public Map<String,String> tokenGenerator(User user);
}
