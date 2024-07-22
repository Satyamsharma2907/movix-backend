package org.niit.UserMovieService.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.niit.UserMovieService.domain.Movie;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO
{
   // private JSONObject jsonObject;
    private List<Movie> movies;
    private String email;
}
