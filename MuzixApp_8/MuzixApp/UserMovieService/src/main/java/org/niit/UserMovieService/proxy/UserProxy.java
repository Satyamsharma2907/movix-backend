package org.niit.UserMovieService.proxy;

import org.niit.UserMovieService.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="auth-service" ,url ="localhost:9056" )

public interface UserProxy {

    @PostMapping("api/authservice/register")
    public ResponseEntity<?> saveUser(@RequestBody User user);

    @PutMapping("api/authservice/user/{email}")
    public ResponseEntity<?> updateUser(@RequestBody User u,@PathVariable String email);



}
