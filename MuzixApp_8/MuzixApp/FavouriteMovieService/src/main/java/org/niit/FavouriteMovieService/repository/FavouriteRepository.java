package org.niit.FavouriteMovieService.repository;

import org.niit.FavouriteMovieService.domain.Favourite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends MongoRepository<Favourite,String>{
}
