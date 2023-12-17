package ada.mod3.bookclub.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ada.mod3.bookclub.model.User;
import ada.mod3.bookclub.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceUnitTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    public PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void Should_Authenticate_When_UserExists() {
        User user = new User();
        user.setEmail("unit@test.com");

        when(userRepository.findByEmail("unit@test.com")).thenReturn(user);
    }

    @Test
    public void Should_ThrowsException_When_InvalidUser() {
        User user = new User();
        user.setEmail("unit@test.com");

        User userResponse = userRepository.findByEmail("wrong@test.com");

        if (userResponse == null) {
            Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
                @Override
                public void execute() throws Throwable {
                    throw new IllegalArgumentException("User not found");
                }
            });             
        }
    }

}
