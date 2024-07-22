package org.niit.UserMovieService;

import org.niit.UserMovieService.controller.MovieController;
import org.niit.UserMovieService.domain.User;
import org.niit.UserMovieService.filter.JwtFilter;
import org.niit.UserMovieService.proxy.UserProxy;
import org.niit.UserMovieService.repository.IMovieRepository;
import org.niit.UserMovieService.service.MovieServiceImpl;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class UserMovieServiceApplication {

	public static void main(String[] args) {
	  ApplicationContext context=	SpringApplication.run(UserMovieServiceApplication.class, args);
	MovieServiceImpl m = (MovieServiceImpl) context.getBean("movieServiceImpl");
		MovieController movieController = (MovieController) context.getBean("movieController");
       String m1   = movieController.toString();
		System.out.println(m1);
		UserProxy userProxy = new UserProxy() {
			@Override
			public ResponseEntity<?> saveUser(User user) {
				return null;
			}

			@Override
			public ResponseEntity<?> updateUser(User u, String email) {
				return null;
			}
		};
         MovieServiceImpl m2 = new MovieServiceImpl(userProxy) ;
         IMovieRepository iMovieRepository = new IMovieRepository() {
			 @Override
			 public <S extends User> List<S> saveAll(Iterable<S> entities) {
				 return null;
			 }

			 @Override
			 public List<User> findAll() {
				 return null;
			 }

			 @Override
			 public List<User> findAll(Sort sort) {
				 return null;
			 }

			 @Override
			 public <S extends User> S insert(S entity) {
				 return null;
			 }

			 @Override
			 public <S extends User> List<S> insert(Iterable<S> entities) {
				 return null;
			 }

			 @Override
			 public <S extends User> List<S> findAll(Example<S> example) {
				 return null;
			 }

			 @Override
			 public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
				 return null;
			 }

			 @Override
			 public Page<User> findAll(Pageable pageable) {
				 return null;
			 }

			 @Override
			 public <S extends User> S save(S entity) {
				 return null;
			 }

			 @Override
			 public Optional<User> findById(String s) {
				 return Optional.empty();
			 }

			 @Override
			 public boolean existsById(String s) {
				 return false;
			 }

			 @Override
			 public Iterable<User> findAllById(Iterable<String> strings) {
				 return null;
			 }

			 @Override
			 public long count() {
				 return 0;
			 }

			 @Override
			 public void deleteById(String s) {

			 }

			 @Override
			 public void delete(User entity) {

			 }

			 @Override
			 public void deleteAllById(Iterable<? extends String> strings) {

			 }

			 @Override
			 public void deleteAll(Iterable<? extends User> entities) {

			 }

			 @Override
			 public void deleteAll() {

			 }

			 @Override
			 public <S extends User> Optional<S> findOne(Example<S> example) {
				 return Optional.empty();
			 }

			 @Override
			 public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
				 return null;
			 }

			 @Override
			 public <S extends User> long count(Example<S> example) {
				 return 0;
			 }

			 @Override
			 public <S extends User> boolean exists(Example<S> example) {
				 return false;
			 }

			 @Override
			 public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
				 return null;
			 }
		 };
		 ConnectionFactory connectionFactory = new ConnectionFactory() {
			 @Override
			 public Connection createConnection() throws AmqpException {
				 return null;
			 }

			 @Override
			 public String getHost() {
				 return null;
			 }

			 @Override
			 public int getPort() {
				 return 0;
			 }

			 @Override
			 public String getVirtualHost() {
				 return null;
			 }

			 @Override
			 public String getUsername() {
				 return null;
			 }

			 @Override
			 public void addConnectionListener(ConnectionListener connectionListener) {

			 }

			 @Override
			 public boolean removeConnectionListener(ConnectionListener connectionListener) {
				 return false;
			 }

			 @Override
			 public void clearConnectionListeners() {

			 }
		 };
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	//	rabbitTemplate.convertAndSend();
		//System.out.println(m.num());
		//ClassPathXmlApplicationContext
		//AnnotationConfigApplicationContext
		//FileSystemXmlApplicationContext

	}

	@Bean
	public FilterRegistrationBean jwtFilter() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new JwtFilter());
		//frb.addUrlPatterns("/api/v1/user", "/api/v1/users", "/api/v1/add");  // variable arguments
		frb.addUrlPatterns("/api/movieservice/user/*"); // all urls
		return frb;
	}
}
