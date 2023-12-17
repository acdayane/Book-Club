package ada.mod3.bookclub.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ada.mod3.bookclub.controller.dto.UserRequest;
import ada.mod3.bookclub.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceUnitTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    public PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // @Test
    // public void Should_CheckUser() {
    //     UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
    //     //userController.saveUser(userDTO);
    //     passwordEncoder = new BCryptPasswordEncoder();

    //     userService = new UserService(userRepository, passwordEncoder);

    // }
}
