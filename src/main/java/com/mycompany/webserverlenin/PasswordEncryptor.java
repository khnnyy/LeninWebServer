package com.mycompany.webserverlenin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncryptor {
    private final BCryptPasswordEncoder encoder;

    public PasswordEncryptor() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public String encryptPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
}
