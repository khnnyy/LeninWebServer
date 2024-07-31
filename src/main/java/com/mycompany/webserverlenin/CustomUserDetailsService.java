package com.mycompany.webserverlenin;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MangoDBConnection mangoDBConnection;

    @Autowired
    public CustomUserDetailsService(MangoDBConnection mangoDBConnection) {
        this.mangoDBConnection = mangoDBConnection;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoCollection<Document> collection = mangoDBConnection.getConfiguration();
        Document user = collection.find(new Document("user_name", username)).first();

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String password = user.getString("password");
        String role = user.getString("role");

        return User.builder()
                .username(username)
                .password(password)
                .roles(role)
                .build();
    }
}
