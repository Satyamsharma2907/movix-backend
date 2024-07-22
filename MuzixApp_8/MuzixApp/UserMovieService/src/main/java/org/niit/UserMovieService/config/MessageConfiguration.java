package org.niit.UserMovieService.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {
    //queue
    //exchange
    //binding
    //RabbitTemplate
    //Jackson2JsonMessageConvertor

    private String exchangeName="userMovieExchange1";
    private String queueName="userMovieQueue";
    // private String exchangeName1 = "mailExchange";
    private String mailQueue  = "userMailQueue";
    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(exchangeName);
    }
    @Bean
    public Queue getQueue(){
        return new Queue(queueName);
    }
    @Bean
    public Queue getMailQueue(){
        return new Queue(mailQueue);
    }

    @Bean
    public Jackson2JsonMessageConverter getJackson2JsonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(getJackson2JsonMessageConvertor());
        return rabbitTemplate;
    }

    @Bean
    public Binding getBinding(){
        return BindingBuilder.bind(getQueue()).to(getTopicExchange()).with("movie_routing");
    }

    @Bean
    public Binding getBinding1()
    {
        return BindingBuilder.bind(getMailQueue()).to(getTopicExchange()).with("email_binding");
    }
}
