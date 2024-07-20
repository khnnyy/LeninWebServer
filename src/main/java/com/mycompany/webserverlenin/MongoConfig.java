/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webserverlenin;

/**
 *
 * @author khanny
 */
import com.mongodb.MongoClientException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MongoConfig {

  @Value("${spring.data.mongodb.uri}")
  private String mongoUri;

  @Bean
  public MongoDatabase mongoDatabase() throws MongoClientException {
    MongoClient mongoClient = MongoClients.create(mongoUri);
    return mongoClient.getDatabase("LeninJobOrder"); // Replace with your database name
  }
  
  
}


