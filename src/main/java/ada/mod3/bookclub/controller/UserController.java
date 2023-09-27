package ada.mod3.bookclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ada.mod3.bookclub.model.User;
import ada.mod3.bookclub.service.UserService;

@RestController
public class UserController {
    
    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // @GetMapping("/users")
    // public List<User> getUsers() {
    //     return userService.getUsers();
    // }

}
