package ada.mod3.bookclub.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ada.mod3.bookclub.controller.dto.UserRequest;
import ada.mod3.bookclub.controller.dto.UserResponse;
import ada.mod3.bookclub.model.User;
import ada.mod3.bookclub.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {
    
    @Autowired
    UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest userDTO) {
        UserResponse user =  userService.saveUser(userDTO);
        return ResponseEntity.created(URI.create("/user/" + user.getId())).body(user);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

}
