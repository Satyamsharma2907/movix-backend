package org.niit.UserMovieService.repository;

import org.niit.UserMovieService.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends MongoRepository<User, String> {

}
