package ada.mod3.bookclub.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ada.mod3.bookclub.controller.dto.UserRequest;
import ada.mod3.bookclub.model.User;
import ada.mod3.bookclub.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserController userController;

    @Test
    public void Should_Answer4xx_When_TrySaveInvalidUser() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user/sign-up")
                .content("""
                    {
                        "name": "int-test",
                        "email": "int@test.com",
                        "password: "123"
                    }
                """)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is4xxClientError()
        );
    }

    @Test
    public void Should_SaveUser_When_ValidUser() throws Exception {
        UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
        String requestBody = new ObjectMapper().writeValueAsString(userDTO);
  
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user/sign-up")
                .content(requestBody.getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void Should_UpdateUser_When_IdExists() throws Exception {
        User user = new User();
        userRepository.save(user);

        UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
        String requestBody = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/user/{id}", user.getId())
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void Should_ThrowsException_When_TryUpdateUserWithInvalidId() throws Exception {
        UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
        String requestBody = new ObjectMapper().writeValueAsString(userDTO);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/user/{id}", 000)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is4xxClientError()
        );
    }

    @Test
    public void Should_DeleteUser_When_IdExists() throws Exception {
        User userToDelete = new User();
        userToDelete.setName("int-test");
        userToDelete.setEmail("int@test.com");
        userToDelete.setPassword("int-test");
        userRepository.save(userToDelete);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/user/{id}", userToDelete.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void Should_ThrowsException_When_TryDeleteUserWithInvalidId() throws Exception {
        Assertions.assertThrows(ServletException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                   MockMvcRequestBuilders.delete("/user/{id}", 0000)
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        });
    }

    @Test
    public void Should_GetAllUsers() throws Exception {  
        User user = new User();
        user.setName("int-test");
        user.setEmail("int@test.com");
        user.setPassword("int-test");
        userRepository.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/user/all")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$").isArray()
        );
    }

    @Test
    public void Should_GetUserById_When_IdExists() throws Exception {  
        User user = new User();
        user.setName("int-test");
        user.setEmail("int@test.com");
        user.setPassword("int-test");
        userRepository.save(user);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/user/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("int-test")
        );
    }

    @Test
    public void Should_ThrowsException_When_TryGetUserWithInvalidId() throws Exception {  
        Assertions.assertThrows(ServletException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                    MockMvcRequestBuilders.get("/user/{id}", 000)
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        }); 
    }

    @Test
    public void Should_GetUserByEmail_When_EmailExists() throws Exception {  
        UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
        userController.saveUser(userDTO);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/user/email/int@test.com")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.email").value("int@test.com")
        );
    }

    @Test
    public void Should_GetAllUsersByName_WhenStringExists() throws Exception {  
        UserRequest userDTO = new UserRequest("int-test", "int@test.com", "int-test");
        userController.saveUser(userDTO);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/user/name/int-test")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$").isArray()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].name").value("int-test")
        );
    }  

    @Test
    public void Should_GetAllUsersByName_WhenStringDoesntExist() throws Exception {  
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                    MockMvcRequestBuilders.get("user/name/{name}", 0000000)
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        }); 
    } 

}
