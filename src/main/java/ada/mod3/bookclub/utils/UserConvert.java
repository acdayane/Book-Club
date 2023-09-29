package ada.mod3.bookclub.utils;


import ada.mod3.bookclub.controller.dto.UserRequest;
import ada.mod3.bookclub.controller.dto.UserResponse;
import ada.mod3.bookclub.model.User;

public class UserConvert {
    
    public static User toEntity(UserRequest userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public static UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
