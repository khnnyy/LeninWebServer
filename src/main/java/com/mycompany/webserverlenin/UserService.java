package com.mycompany.webserverlenin;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {

    
    private static final String FILE_NAME = "admin.jit";
    private  JOVar config = new JOVar(); 

    // Method to update user credentials
    public boolean updateUserCredentials(String username, String password) {
        try (FileWriter myWriter = new FileWriter(FILE_NAME)) {
            myWriter.write(username + "\n");
            myWriter.write(password);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to read configuration from file
    @Autowired
    public JOVar getConfig() {
        return config;
    }

    
    // Read configuration from the file and update the JOVar instance
    @Autowired
    public void readConfigFromFile() {
        try (Scanner myReader = new Scanner(new File(FILE_NAME))) {
            if (myReader.hasNextLine()) {
                String user = myReader.nextLine();
                config.setUser(user);
            }
            if (myReader.hasNextLine()) {
                String password = myReader.nextLine();
                config.setPassword(password);
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
