package org.niit.UserAuthenticationService.service;

import org.niit.UserAuthenticationService.domain.User;
import org.niit.UserAuthenticationService.exception.UserAlreadyExistsException;
import org.niit.UserAuthenticationService.exception.UserNotFoundException;
//import org.niit.UserAuthenticationService.repository.ImageRepositiry;
import org.niit.UserAuthenticationService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IUserService
{
    private UserRepository userRespository;

     @Autowired
    public UserServiceImpl(UserRepository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public User saveUser(User u)throws UserAlreadyExistsException {
         if(userRespository.findById(u.getEmail()).isPresent())
         {
             throw new UserAlreadyExistsException();
         }
        // String s = "satyam";
        return userRespository.save(u);
    }

    @Override
    public List<User> getAllUsers() {
        return userRespository.findAll();
    }

    @Override
    public Optional<User> getUserByEmail(String email){
        if(userRespository.findById(email).isPresent()) {
            return Optional.of(userRespository.findById(email).get());
        }
        return null;
    }
    @Override
    public User updateUser(User u,String email)throws UserNotFoundException {//client
        System.out.println("service method called");
        Optional<User> optional=userRespository.findById(email);

        if(optional.isEmpty()){
            throw new UserNotFoundException();
        }
        User result=optional.get();//object from data
        System.out.println("**************************user object*************************"+u);
        System.out.println("**************************result object*************************"+result);
        if(u.getName()!=null ) {
            result.setName(u.getName());
        }
        if(u.getPhoneNo()!=null){
            result.setPhoneNo(u.getPhoneNo());
        }
        if(u.getPassword()!=null){
            result.setPassword(u.getPassword());
        }
        return userRespository.save(result);
    }
    @Override
    public String deleteUser(String email) throws UserNotFoundException {
        if(userRespository.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        else{
            userRespository.deleteById(email);
            return "user deleted succefully";
        }

    }
    @Override
    public User loginCheck(String emailId, String password) {
        if(userRespository.findById(emailId).isPresent()){
            // get userobjet by using emailId
            User user=userRespository.findById(emailId).get();
            //check password
            if(user.getPassword().equals(password)){
                user.setPassword("");
                return user;
            }else{
                //invalid user
                return null;
            }

        }else{
            //user not exits
            return null;
        }
    }

}
