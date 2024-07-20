package com.mycompany.webserverlenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private Map<String, UserDetails> userDetailsMap = new HashMap<>();

    @Autowired
    public CustomUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        reloadUserDetails(); // Initial load of user details
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        reloadUserDetails(); // Reload user details on each login attempt
        UserDetails userDetails = userDetailsMap.get(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return userDetails;
    }

    public void reloadUserDetails() {
        userDetailsMap.clear();
        userService.readConfigFromFile(); // Refresh configuration
        JOVar config = userService.getConfig();

        UserDetails user = User.builder()
                .username("solutions")
                .password(passwordEncoder.encode("LENIN_solutions"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username(config.getUser())
                .password(passwordEncoder.encode(config.getPassword()))
                .roles("ADMIN")
                .build();

        userDetailsMap.put("solutions", user);
        userDetailsMap.put(config.getUser(), admin);
    }
}
