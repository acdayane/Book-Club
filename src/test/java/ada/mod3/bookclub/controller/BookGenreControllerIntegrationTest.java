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

import ada.mod3.bookclub.model.BookGenre;
import ada.mod3.bookclub.repository.BookGenreRepository;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookGenreControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Test
    public void Should_Answer4xx_When_TrySaveInvalidBookGenre() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/book-genre")
                .content("""                            
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
    public void Should_SaveBookGenre_When_ValidBookGenre() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/book-genre")
                .content("""
                    {
                        "name": "Suspense"
                    }
                """)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void Should_DeleteBookGenre_When_IdExists() throws Exception {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);
        Integer genreIdToDelete = bookGenre.getId();

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/book-genre/{id}", genreIdToDelete)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void Should_ThrowException_When_TryDeleteBookGenreWithInvalidId() throws Exception {
        Assertions.assertThrows(ServletException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                    MockMvcRequestBuilders.delete("/book/{id}", 000)
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        }); 
    }

    @Test
    public void Should_ThrowException_When_TryGetBookGenreWithInvalidId() throws Exception {     
        Assertions.assertThrows(ServletException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                    MockMvcRequestBuilders.get("/book/{id}", 000)
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        }); 
    }

    @Test
    public void Should_GetBookGenre_When_IdExists() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book-genre/{id}", bookGenre.getId())
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
    public void Should_ThrowsException_When_TryGetInvalidId() throws Exception {
        Assertions.assertThrows(ServletException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                    MockMvcRequestBuilders.get("/book/{id}", 000)
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        }); 
    }

    @Test
    public void Should_GetAllGenres() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book-genre/all")
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
    public void Should_GetGenresList_When_StringExists() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book-genre/name/int")
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
    public void Should_ThrowException_When_TryGetString() throws Exception {        
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                    MockMvcRequestBuilders.get("book-genre/name/111111111")
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        }); 
    }

}
