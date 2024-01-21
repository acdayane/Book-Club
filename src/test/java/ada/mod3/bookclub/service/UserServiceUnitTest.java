package ada.mod3.bookclub.service;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    public void given_user_exists_when_search_by_email_then_user_should_returns() {
        //Criação dos dados utilizados.
        User user = new User();
        user.setId(1);
        user.setEmail("unit@test.com");

        when(userRepository.findByEmail("unit@test.com")).thenReturn(user);

        //Chamada da lógica para execução do código a ser testado
        var found = userService.getUserByEmail("unit@test.com");

        //Validações do resultado
        Assertions.assertEquals(user.getId(), found.getId());
        Assertions.assertEquals(user.getEmail(), found.getEmail());
    }

    @Test
    public void given_user_was_not_registered_when_search_by_email_then_exception_should_thrown() {
        //Criação dos dados utilizados.
        User user = new User();
        user.setEmail("unit@test.com");

        //Validações da execução da parte lógica. O código dentro do assertThrows será executado primeiro.
        Assertions.assertThrows(
                RuntimeException.class,
                //Chamada da lógica para exeucução do código a ser testado
                () -> userService.getUserByEmail("wrong@test.com")
        );
    }

}
