package ada.mod3.bookclub.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ada.mod3.bookclub.controller.dto.AuthRequest;
import ada.mod3.bookclub.controller.dto.UserRequest;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private AuthController authController;

    @Test
    public void Should_ReturnToken_When_LoginSuccess() throws Exception {
        UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
        userController.saveUser(userDTO);

        AuthRequest authRequest = new AuthRequest("int@test.com", "int-test");
        ResponseEntity token = authController.login(authRequest);
        
        mockMvc.perform(
            MockMvcRequestBuilders.post("/sign-in")
                .content("""
                    {
                        "email": "int@test.com",
                        "password": "int-test"
                    }
                """)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.token").isString()
        );
    }

    @Test
    public void Should_ReturnUnauthorized_When_LoginFail() throws Exception {
        UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
        userController.saveUser(userDTO);

        AuthRequest authRequest = new AuthRequest("int@test.com", "int-test");
        ResponseEntity token = authController.login(authRequest);
        
        mockMvc.perform(
            MockMvcRequestBuilders.post("/sign-in")
                .content("""
                    {
                        "email": "int@test.com",
                        "password": "wrong"
                    }
                """)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isUnauthorized()
        );
    }

}
