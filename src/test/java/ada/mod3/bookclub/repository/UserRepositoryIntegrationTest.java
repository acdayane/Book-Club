package ada.mod3.bookclub.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ada.mod3.bookclub.model.User;
import jakarta.transaction.Transactional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryIntegrationTest {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void Should_SaveUser() {
        User user = new User();
        user.setName("int-test");
        user.setEmail("int6@test.com");
        user.setPassword("int-test");
        userRepository.save(user);
    }

    @Test
    @Order(2)
    public void Should_GetUsersList() {
        List<User> users =  userRepository.findAll();
        Assertions.assertFalse(users.isEmpty());
    }

}
