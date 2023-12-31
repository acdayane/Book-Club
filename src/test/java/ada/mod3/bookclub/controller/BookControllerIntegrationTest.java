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

import ada.mod3.bookclub.controller.dto.BookRequest;
import ada.mod3.bookclub.model.Book;
import ada.mod3.bookclub.model.BookGenre;
import ada.mod3.bookclub.repository.BookGenreRepository;
import ada.mod3.bookclub.repository.BookRepository;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void Should_Answer4xx_When_TrySaveInvalidBook() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/book")
                .content("""
                    {
                        "title": "A Ultima Festa",
                        "author": "Lucy Foley",  
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
    public void Should_SaveBook_When_ValidBook() throws Exception {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);
        Integer genreId = bookGenre.getId();

        BookRequest bookDTO = new BookRequest("int-test", "int-test", genreId);
        String requestBody = new ObjectMapper().writeValueAsString(bookDTO);
  
        mockMvc.perform(
            MockMvcRequestBuilders.post("/book")
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
    public void Should_ThrowServletException_When_TrySaveBookWithInvalidGenreId() throws Exception {
        BookRequest bookDTO = new BookRequest("int-test", "int-test", 000);
        String requestBody = new ObjectMapper().writeValueAsString(bookDTO);
 
        Assertions.assertThrows(ServletException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                mockMvc.perform(
                    MockMvcRequestBuilders.post("/book")
                        .content(requestBody.getBytes())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                );
            }
        }); 
    }

    @Test
    public void Should_DeleteBook_When_IdExists() throws Exception {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);
        Integer genreId = bookGenre.getId();

        Book bookToDelete = new Book();
        bookToDelete.setTitle("int-test");
        bookToDelete.setAuthor("int-test");
        bookToDelete.setGenre(bookGenre);
        bookRepository.save(bookToDelete);
        Integer bookId = bookToDelete.getId();
      
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/book/{id}", bookId)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void Should_ThrowServletException_When_TryDeleteBookWithInvalidId() throws Exception {
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
    public void Should_ThrowServletException_When_TryGetBookWithInvalidId() throws Exception {     
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
    public void Should_GetBook_When_IdExists() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);

        Book bookToGet = new Book();
        bookToGet.setTitle("int-test");
        bookToGet.setAuthor("int-test");
        bookToGet.setGenre(bookGenre);
        bookRepository.save(bookToGet);
        Integer bookId = bookToGet.getId();

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book/{id}", bookId)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.title").value("int-test")
        );
    }

    @Test
    public void Should_GetAllBooks() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);

        Book book = new Book();
        book.setTitle("int-test");
        book.setAuthor("int-test");
        book.setGenre(bookGenre);
        bookRepository.save(book);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book/all")
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
    public void Should_GetBooksList_When_TitleOrAuthorExist() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("int-test");
        bookGenreRepository.save(bookGenre);

        Book book = new Book();
        book.setTitle("int-test");
        book.setAuthor("int-test");
        book.setGenre(bookGenre);
        bookRepository.save(book);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book/search/int")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$").isArray()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].title").value("int-test")
        );
    }

    @Test
    public void Should_ThrowException_When_TitleOrAuthorDontExist() throws Exception {        
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

}
