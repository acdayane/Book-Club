package ada.mod3.bookclub.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ada.mod3.bookclub.controller.dto.BookGenreResponse;
import ada.mod3.bookclub.model.BookGenre;
import ada.mod3.bookclub.utils.BookGenreConvert;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookGenreControllerIntegrationTest {

    @MockBean
    private BookGenreController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void Should_AnswerBadRequest_When_TrySaveInvalidBookGenre() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/book-genre")
                .content("""                            
                        """)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest()
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
            MockMvcResultMatchers.status().is2xxSuccessful()
        );
    }

    @Test
    public void Should_DeleteBookGenre_When_IdExists() throws Exception {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(123);

        Mockito.doAnswer(invocationOnMock -> {
            Integer bookGenreId = (Integer) invocationOnMock.getArgument(0);
            return null;
        }).when(controller).deleteGenre(Mockito.anyInt());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/book-genre/{id}", 123)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        );
    }

    @Test
    public void Should_GetBookGenre_When_IdExists() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(123);
        bookGenre.setName("int-test");

        BookGenreResponse bookGenreResponse = BookGenreConvert.toResponse(bookGenre); 
        when(controller.getGenre(bookGenre.getId())).thenReturn(ResponseEntity.ok(bookGenreResponse));

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book-genre/123")
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
        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(123);
        bookGenre.setName("int-test");

        when(controller.getGenre(bookGenre.getId())).thenThrow(new RuntimeException("Book genre not found"));

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book-genre/0")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        );
    }

    @Test
    public void Should_GetGenresList() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(123);
        bookGenre.setName("int-test");

        List<BookGenre> genresList = Collections.singletonList(bookGenre);
        when(controller.getGenres()).thenReturn(genresList);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/book-genre/all")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(
            MockMvcResultHandlers.print()
        ).andExpect(
            MockMvcResultMatchers.status().is2xxSuccessful()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$[0].name").value("int-test")
        );
    }

    @Test
    public void Should_GetGenresList_When_StringExists() throws Exception {        
        BookGenre bookGenre = new BookGenre();
        bookGenre.setId(123);
        bookGenre.setName("int-test");

        List<BookGenre> genresList = Collections.singletonList(bookGenre);
        when(controller.getGenreByName("int")).thenReturn(ResponseEntity.ok(BookGenreConvert.toResponseList(genresList)));

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
            MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1))
        );
    }

}
