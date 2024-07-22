package org.niit.FavouriteMovieService.service;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.niit.FavouriteMovieService.domain.Movie;

import java.util.List;

public interface IFavouriteService {
    public List<Movie> getAllFavourites(String email);
//    public String deleteFavMovie1(int mid , String email);
}
