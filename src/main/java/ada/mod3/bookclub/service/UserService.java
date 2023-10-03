package ada.mod3.bookclub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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

    public List<User> getUsers () {
        return userRepository.findAll();
    }

    public UserResponse saveUser (UserRequest userDTO) {
        User user = UserConvert.toEntity(userDTO);
        user.setActive(true);
        User userEntity = userRepository.save(user);
        return UserConvert.toResponse(userEntity);
    }

    public UserResponse updateUser (Integer id, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
    
            existingUser.setName(userRequest.getName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPassword(userRequest.getPassword());
            existingUser.setActive(true);
    
            User updatedUser = userRepository.save(existingUser);
    
            return UserConvert.toResponse(updatedUser);
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }

    public void deleteUser (Integer id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(false);
        userRepository.save(user);
    }
}
