package org.niit.UserAuthenticationService.service;

import org.niit.UserAuthenticationService.domain.User;
import org.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import org.niit.UserAuthenticationService.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUserService
{
    public User saveUser(User u) throws UserAlreadyExistsException;
    public List<User> getAllUsers();

    public Optional<User> getUserByEmail(String email);
    public User updateUser(User u,String email) throws UserNotFoundException;
    public String deleteUser(String email)throws UserNotFoundException;
    public User loginCheck(String emailId,String password);
}
