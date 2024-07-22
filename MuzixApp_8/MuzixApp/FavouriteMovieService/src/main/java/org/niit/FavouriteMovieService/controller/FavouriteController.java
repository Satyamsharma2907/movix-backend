package org.niit.FavouriteMovieService.controller;

import org.niit.FavouriteMovieService.domain.Movie;
import org.niit.FavouriteMovieService.service.IFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/favouriteservice")
public class FavouriteController {

    IFavouriteService favouriteService;

    @Autowired
    public FavouriteController(IFavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @GetMapping("/favourite/{email}")
    public ResponseEntity<?> getFavourites(@PathVariable String email){
        return new ResponseEntity<>(favouriteService.getAllFavourites(email), HttpStatus.OK);

    }

//    @PutMapping("/favourites/{email}/{mid}")
//    public ResponseEntity<?> getNotification1(@PathVariable String email,@PathVariable int mid){
//        return new ResponseEntity<>(favouriteService.deleteFavMovie1(mid,email), HttpStatus.OK);
//    }
}
