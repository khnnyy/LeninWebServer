/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webserverlenin;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author khanny
 */
@Service
public class ReleasingService {

    private final MangoDBConnection mangoDBConnection;

    @Autowired
    public ReleasingService(MangoDBConnection mangoDBConnection) {
        this.mangoDBConnection = mangoDBConnection;
    }

    public boolean releaseJobCode(String jobCode) {
        try {
            MongoCollection<Document> collection = mangoDBConnection.getCollection();

            Document query = new Document("job_code", jobCode);
            Document update = new Document("$set", new Document("status", "released"));
            var result = collection.updateOne(query, update);

            return result.getMatchedCount() > 0; // Return true if at least one document was updated
        } catch (MongoException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
    }
    
}
