package org.niit.FavouriteMovieService.service;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.niit.FavouriteMovieService.config.MovieDTO;
import org.niit.FavouriteMovieService.domain.Favourite;
import org.niit.FavouriteMovieService.domain.Movie;
import org.niit.FavouriteMovieService.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class FavouriteServiceImpl implements IFavouriteService {

    private FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteServiceImpl(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    @Override
    public List<Movie> getAllFavourites(String email) {
        List<Movie>favMovie =  favouriteRepository.findById(email).get().getMovies();
        return favMovie;
    }

//    @Override
//    public String deleteFavMovie1(int mid, String email) {
//    Favourite fav = favouriteRepository.findById(email).get();
//        MovieDTO movieDTO = new MovieDTO();
//
//    List<Movie>movie1 = fav.getMovies();
//    Favourite favourite = new Favourite();
//        System.out.println(movie1);
//
//        Movie temp=null;
//        for(Movie m : movie1){
//            if(m.getId()==mid){
//                temp=m;
//                break;
//            }
//        }
//        System.out.println(movie1);
//         movie1.remove(temp);
//
//
//
//        favouriteRepository.save(fav);
//        return "deleted successfully";
//    }
}
