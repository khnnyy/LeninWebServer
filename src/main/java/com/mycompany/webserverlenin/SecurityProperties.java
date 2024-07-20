package com.mycompany.webserverlenin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private UserCredentials user = new UserCredentials();
    private UserCredentials admin = new UserCredentials();

    public static class UserCredentials {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public UserCredentials getUser() {
        return user;
    }

    public void setUser(UserCredentials user) {
        this.user = user;
    }

    public UserCredentials getAdmin() {
        return admin;
    }

    public void setAdmin(UserCredentials admin) {
        this.admin = admin;
    }
}
