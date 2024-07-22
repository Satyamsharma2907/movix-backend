package org.niit.UserMovieService.service;

import org.niit.UserMovieService.config.MovieDTO;
import org.niit.UserMovieService.domain.MessageObject;
import org.niit.UserMovieService.domain.Movie;
import org.niit.UserMovieService.domain.User;
import org.niit.UserMovieService.exception.UserAlreadyExistsException;
import org.niit.UserMovieService.exception.UserNotFoundException;
import org.niit.UserMovieService.proxy.UserProxy;
import org.niit.UserMovieService.repository.IMovieRepository;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Component
@Service
public class MovieServiceImpl implements IMovieService{
    private IMovieRepository movieRepository;
    private UserProxy userProxy;
    private RabbitTemplate rabbitTemplate;
    private TopicExchange topicExchange;
    boolean flag = true;

    int i = 0;

    @Autowired
    
    public MovieServiceImpl(IMovieRepository movieRepository, UserProxy userProxy, RabbitTemplate rabbitTemplate, TopicExchange topicExchange) {
        this.movieRepository = movieRepository;
        this.userProxy = userProxy;
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
        System.out.println("satyam ");
    }
      public MovieServiceImpl()
      {

      }
   public MovieServiceImpl(UserProxy userProxy)
   {
         this.userProxy = userProxy;
   }
    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        if (movieRepository.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
     ResponseEntity<?> u =   userProxy.saveUser(user);

//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("movie_list",favouriteList);
//        jsonObject.put("email",email);
        EmailDTO email=new EmailDTO(user.getEmail(),"Welcome To our Application","Registration is Successful");
        rabbitTemplate.convertAndSend(topicExchange.getName(),"email_binding",email);
        System.out.println("SEND SUCCEFULL"+topicExchange.getName()+email);
        return movieRepository.save(user);
    }

    @Override
    public User updateUser(User user, String email) throws UserNotFoundException{
        System.out.println("service method called");
        Optional<User> optional=movieRepository.findById(email);

        if(optional.isEmpty()){
            throw new UserNotFoundException();
        }
        User result=optional.get();//object from data

        System.out.println("**************************user object*************************"+user);
        System.out.println("**************************result object*************************"+result);
        if(user.getName()!=null ) {
            result.setName(user.getName());
        }
        if(user.getPhoneNo()!=null){
            result.setPhoneNo(user.getPhoneNo());
        }
        if(user.getPassword()!=null){
            result.setPassword(user.getPassword());
        }

        userProxy.updateUser(user, email);
        return movieRepository.save(user);
    }

    @Override
    public User saveUserMovieToList(Movie movies, String email) throws UserNotFoundException {
        //  check user is present or not
        if (movieRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        // user present
        User result = movieRepository.findById(email).get();
        if (result.getMovies() != null) {
            List<Movie> allMovies = result.getMovies();
            for (int i = 0; i < allMovies.size(); i++) {
                System.out.println(allMovies.get(i));
                System.out.println(i);
                System.out.println();
                System.out.println();
                System.out.println();

            }
            for (int i = 0; i < allMovies.size(); i++) {
                if (allMovies.get(i).getId() == movies.getId()) {
                    System.out.println("Movie Already Exists");
                    System.out.println(allMovies.get(i).getId());
                    flag = false;
                    break;
                } else {
                    flag = true;
                }
            }
            System.out.println(flag);
            if (flag) {
                System.out.println("Movie doesn't exist");
                result.getMovies().add(movies);
                movieRepository.save(result);

            }
            flag = true;
        } else {
            result.setMovies(new ArrayList<>());

            movieRepository.save(result);
        }
        return result;
    }

    @Override
    public boolean getMovieStatus(Movie movies, String email) throws UserNotFoundException {
        if (movieRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        User result = movieRepository.findById(email).get();
        List<Movie> allMovies = result.getMovies();
        for (int i = 0; i < allMovies.size(); i++) {
            if (allMovies.get(i).getId() == movies.getId()) {
                System.out.println("Movie Already Exists");
                System.out.println(allMovies.get(i).getId());
                flag = false;
                break;
            } else {
                flag = true;
            }
        }
        System.out.println(flag);
        return flag;
    }

    @Override
    public User updateFavouriteMovies(Movie movies, String email) throws UserNotFoundException {
        if (movieRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        User result = movieRepository.findById(email).get();
        List<Movie> allMovies = result.getMovies();
        for (int i = 0; i < allMovies.size(); i++) {
            if (result.getMovies().get(i).getId() == movies.getId()) {
                Movie m = result.getMovies().get(i);
                System.out.println("Welcome to favourites");
                m.setFavourite(movies.getIsFavourite());
                    flag = false;
                break;
            }

        }
        movieRepository.save(result);
        rabbitMq(email);

        return result;
    }

    @Override
    public User addFavouriteMovies(Movie movies, String email) throws UserNotFoundException {
        if (movieRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        User result = movieRepository.findById(email).get();
        List<Movie> allMovies = result.getMovies();
        for (int i = 0; i < allMovies.size(); i++) {
            if (result.getMovies().get(i).getId() == movies.getId()) {
                Movie m = result.getMovies().get(i);
                System.out.println("Welcome to favourites");

                flag = false;
                break;
            } else {
                flag = true;
            }

        }
        if (flag) {
            //  User result = movieRepository.findById(email).get();
            result.getMovies().add(movies);
            movieRepository.save(result);
            rabbitMq(email);


        }
        return result;
    }

    @Override
    public MessageObject deleteFavouriteMovie(String email, int movieId) throws UserNotFoundException {
        if (movieRepository.findById(email).isEmpty()) {
            throw new UserNotFoundException();
        }
        String message = "Favourite not deleted";
        MessageObject msgObj = new MessageObject(message);
        User result = movieRepository.findById(email).get();
        List<Movie> allMovies = result.getMovies();
        for (int i = 0; i < allMovies.size(); i++) {
            if (result.getMovies().get(i).getId() == movieId) {
                Movie m = result.getMovies().get(i);
                m.setFavourite(false);
                movieRepository.save(result);
                message = "Favourite deleted";
                msgObj.setMessage(message);
                break;
            }
        }
        rabbitMq(email);
        return msgObj;
    }

    public void rabbitMq(String email)
    {
        List<Movie> allMovies=movieRepository.findById(email).get().getMovies();
        List<Movie> favouriteList=new ArrayList<>();
        for(Movie c: allMovies){
            if(c.getIsFavourite()){
                favouriteList.add(c);
            }
        }

        //create jsonobject
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("movie_list",favouriteList);
//        jsonObject.put("email",email);
        //create MovieDTO object

     //   MovieDTO movieDTO=new MovieDTO(jsonObject);
        MovieDTO movieDTO=new MovieDTO(favouriteList,email);
        //exchange name ,routingkey,,DtoObject
        rabbitTemplate.convertAndSend(topicExchange.getName(),"movie_routing",movieDTO);
        System.out.println("SEND SUCCEFULL"+topicExchange.getName()+movieDTO);
    }
    public int num()
    {
        return 5;
    }
//    @Override
//    public List<Movie> getAllUserMovies(String email) throws UserNotFoundException {
//        //check user is exist or not
//
//        if(movieRepository.findById(email).isEmpty()){
//            throw new UserNotFoundException();
//        }
//        //if not exist
//        List<Movie> allMovies=movieRepository.findById(email).get().getMovies();
//        List<Movie> favouriteList=new ArrayList<>();
//        for(Movie c: allMovies){
//            if(c.getIsFavourite()){
//                favouriteList.add(c);
//            }
//        }
//
//        //create jsonobject
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("movie_list",favouriteList);
//        jsonObject.put("email",email);
//        //create MovieDTO object
//
//        MovieDTO movieDTO=new MovieDTO(jsonObject);
//
//        //exchange name ,routingkey,,DtoObject
//        rabbitTemplate.convertAndSend(directExchange.getName(),"movie_routing",movieDTO);
//        System.out.println("SEND SUCCEFULL"+directExchange.getName()+movieDTO);
//        return allMovies;
//    }

}
