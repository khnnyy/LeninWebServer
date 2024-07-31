package com.mycompany.webserverlenin;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeploymentService {

    private final MangoDBConnection mangoDBConnection;

    @Autowired
    public DeploymentService(MangoDBConnection mangoDBConnection) {
        this.mangoDBConnection = mangoDBConnection;
    }

    public boolean deployJobCode(String jobCode) {
        try {
            MongoCollection<Document> collection = mangoDBConnection.getCollection();

            Document query = new Document("job_code", jobCode);
            Document update = new Document("$set", new Document("status", "deployed"));
            var result = collection.updateOne(query, update);

            return result.getMatchedCount() > 0; // Return true if at least one document was updated
        } catch (MongoException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
    }
    
    public boolean cancelJobCode(String jobCode) {
        try {
            MongoCollection<Document> collection = mangoDBConnection.getCollection();

            Document query = new Document("job_code", jobCode);
            Document update = new Document("$set", new Document("status", "canceled"));
            var result = collection.updateOne(query, update);

            return result.getMatchedCount() > 0; // Return true if at least one document was updated
        } catch (MongoException e) {
            e.printStackTrace();
            return false; // Return false in case of an exception
        }
    }
}
