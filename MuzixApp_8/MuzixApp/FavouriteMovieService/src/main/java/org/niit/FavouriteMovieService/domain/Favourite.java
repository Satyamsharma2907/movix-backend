package org.niit.FavouriteMovieService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Favourite
{
    @Id
    private String email;
    private String notificationMessage;
    //private JSONObject jsonObject;
   private  List<Movie> movies;
}
