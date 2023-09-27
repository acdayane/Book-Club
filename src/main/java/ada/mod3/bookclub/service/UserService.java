package ada.mod3.bookclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ada.mod3.bookclub.model.User;
import ada.mod3.bookclub.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public User saveUser (User user) {
        return userRepository.save(user);
    }

    // public List<User> getUsers () {
    //     userRepository.findAll();
    //     return List<User>;
    // }
}
