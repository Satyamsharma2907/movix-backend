package org.niit.UserMovieService.controller;

import org.niit.UserMovieService.domain.Movie;
import org.niit.UserMovieService.domain.User;
import org.niit.UserMovieService.exception.UserAlreadyExistsException;
import org.niit.UserMovieService.exception.UserNotFoundException;
import org.niit.UserMovieService.service.IMovieService;
import org.niit.UserMovieService.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
//@Component
@RestController
@RequestMapping("/api/movieservice")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MovieController {
    private IMovieService movieService;
    private ResponseEntity<?> responseEntity;

           IMovieService movieService1 = new MovieServiceImpl();

    @Autowired
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserAlreadyExistsException {
        System.out.println(user);
        return new ResponseEntity<>(movieService.registerUser(user), HttpStatus.CREATED);
    }

    @PutMapping("update/{email}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String email) throws UserNotFoundException{
        try
        {
            return new ResponseEntity<>(movieService.updateUser(user,email),HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
    }

    @PostMapping("/add-movie-to-user1")
    public ResponseEntity<?> addMovieToUser(@RequestBody User user){
        System.out.println("controller" + user);
        return null;
    }
// http://localhost:8082/api/movieservice/user/joseph@gmail.com
    @PostMapping("/user/{email}")
    public ResponseEntity<?> saveUserMovieToList(@RequestBody Movie movies, @PathVariable String email) throws UserNotFoundException {
        try {
            responseEntity = new ResponseEntity<>(movieService.saveUserMovieToList(movies, email), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @PostMapping("status/{email}")
    public ResponseEntity<?>getMovieStatus(@RequestBody Movie movies, @PathVariable String email) throws UserNotFoundException
    {
        try {
            responseEntity = new ResponseEntity<>(movieService.getMovieStatus(movies, email), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @PutMapping("updates/{email}")
    public ResponseEntity<?>getMovieUpdate(@RequestBody Movie movies, @PathVariable String email) throws UserNotFoundException
    {
        try {
            responseEntity = new ResponseEntity<>(movieService.updateFavouriteMovies(movies, email), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @PostMapping("/add/{email}")
    public ResponseEntity<?>addFav(@RequestBody Movie movies, @PathVariable String email) throws UserNotFoundException
    {
        try {
            responseEntity = new ResponseEntity<>(movieService.addFavouriteMovies(movies, email), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

    @PutMapping("/favourites/{email}/{mid}")
    public ResponseEntity<?> deleteFavMovie(@PathVariable String email,@PathVariable int mid) throws UserNotFoundException {
        try {
            responseEntity = new ResponseEntity<>(movieService.deleteFavouriteMovie(email, mid), HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            throw new UserNotFoundException();
        }
        return responseEntity;
    }

//    @GetMapping("/users/{mail}")
//    public ResponseEntity<?> getAllUserMovies(@PathVariable String mail) throws UserNotFoundException {
//        try {
//            responseEntity = new ResponseEntity<>(movieService.getAllUserMovies(mail),HttpStatus.OK);
//        }
//        catch (UserNotFoundException e)
//        {
//            throw new UserNotFoundException();
//        }
//        return responseEntity;
//    }
}
