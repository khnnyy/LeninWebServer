package com.mycompany.webserverlenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the login view
    }

    @PostMapping("/login")
    public String login(@RequestParam("user_name") String username,
                    @RequestParam("password") String password,
                    Model model) {
    boolean success = userService.authenticate(username, password);
    if (success) {
        return "redirect:/home"; // Redirect to home or any protected resource
    } else {
        // Add error parameter to the URL
        return ""; // URL encode the error message
    }
}

}
