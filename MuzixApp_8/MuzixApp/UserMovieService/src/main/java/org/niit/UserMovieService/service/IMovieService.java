package org.niit.UserMovieService.service;

import org.niit.UserMovieService.domain.MessageObject;
import org.niit.UserMovieService.domain.Movie;
import org.niit.UserMovieService.domain.User;
import org.niit.UserMovieService.exception.UserAlreadyExistsException;
import org.niit.UserMovieService.exception.UserNotFoundException;

import java.util.List;

public interface IMovieService {
    User registerUser(User user) throws UserAlreadyExistsException;
    public User updateUser(User user, String email) throws UserNotFoundException;
    User saveUserMovieToList(Movie movies, String email) throws UserNotFoundException;
    public boolean getMovieStatus(Movie movies, String email) throws UserNotFoundException;
    public User updateFavouriteMovies(Movie movies, String email) throws UserNotFoundException;
    public User addFavouriteMovies(Movie movies, String email) throws UserNotFoundException;
    public MessageObject deleteFavouriteMovie(String email, int movieId) throws UserNotFoundException;
    //List<Movie> getAllUserMovies(String email) throws UserNotFoundException;
}
