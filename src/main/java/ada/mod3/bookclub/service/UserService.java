package ada.mod3.bookclub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ada.mod3.bookclub.controller.dto.UserRequest;
import ada.mod3.bookclub.controller.dto.UserResponse;
import ada.mod3.bookclub.model.User;
import ada.mod3.bookclub.repository.UserRepository;
import ada.mod3.bookclub.utils.UserConvert;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public UserResponse saveUser (UserRequest userDTO) {
        User user = UserConvert.toEntity(userDTO);
        user.setActive(true);
        User userEntity = userRepository.save(user);
        return UserConvert.toResponse(userEntity);
    }

    public List<User> getUsers () {
        return userRepository.findAll();
    }
}
