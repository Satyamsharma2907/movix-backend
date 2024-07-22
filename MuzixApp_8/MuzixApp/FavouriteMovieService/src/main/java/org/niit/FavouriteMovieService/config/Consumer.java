package org.niit.FavouriteMovieService.config;

import org.niit.FavouriteMovieService.domain.Favourite;
import org.niit.FavouriteMovieService.repository.FavouriteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @Autowired
    private FavouriteRepository favouriteRepository;

    @RabbitListener(queues = "userMovieQueue")
    public void saveFavourites(MovieDTO movieDTO) {

        //object of notification
        Favourite favourite = new Favourite();
        //fetch email from DTO object
      //  String email = movieDTO.getJsonObject().get("email").toString();
        System.out.println(movieDTO);
        String email = movieDTO.getEmail();
        if (favouriteRepository.findById(email).isEmpty()) {
            favourite.setEmail(email);
            favourite.setNotificationMessage("List of no watched movies");
           // favourite.setJsonObject(movieDTO.getJsonObject());
            favourite.setMovies(movieDTO.getMovies());
            favouriteRepository.save(favourite);
        } else if (favouriteRepository.findById(email).isPresent()) {
            Favourite n = favouriteRepository.findById(email).get();
          //  n.setJsonObject(movieDTO.getJsonObject());
            n.setMovies(movieDTO.getMovies());
            favouriteRepository.save(n);
        }
    }
}
