package ada.mod3.bookclub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponse saveUser(UserRequest userDTO) {
        User user = UserConvert.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        User userEntity = userRepository.save(user);
        return UserConvert.toResponse(userEntity);
    }

    public UserResponse updateUser(Integer id, UserRequest userRequest) {
        Optional<User> user =  userRepository.findById(id);

        if (user.isPresent()) {
            User existingUser = user.get();
    
            existingUser.setName(userRequest.getName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            existingUser.setActive(true);
    
            User updatedUser = userRepository.save(existingUser);
    
            return UserConvert.toResponse(updatedUser);
        } else {
            throw new RuntimeException("Can not update user");
        }
    }

    public void deleteUser (Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(false);
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUserByEmail(String email) {     
        return UserConvert.toResponse(userRepository.findByEmail(email));
    }

    public UserResponse getUserById(Integer id) {     
        Optional<User> userResponse =  userRepository.findById(id);
        if(userResponse.isPresent()){
            return UserConvert.toResponse(userResponse.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public List<UserResponse> getAllByName(String name){
        List<User> userResponse =  userRepository.findAllByNameIgnoreCase(name);
        if(userResponse.isEmpty()){
          throw new RuntimeException("Name not found");
        }
        return UserConvert.toResponseList(userRepository.findAllByNameIgnoreCase(name));
    }
}
