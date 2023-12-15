package ada.mod3.bookclub.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ada.mod3.bookclub.model.BookGenre;
import ada.mod3.bookclub.repository.BookGenreRepository;
import ada.mod3.bookclub.service.BookGenreService;

@ExtendWith(SpringExtension.class)
public class BookGenreUnitTest {
    
    @InjectMocks
    private BookGenreService service;

    @Mock
    private BookGenreRepository repository;

    @Test
    public void Should_ThrowException_When_InvalidBookGenre() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setName("a");
        repository.save(bookGenre);
    }

    @Test
    public void Should_SaveBookGenre_When_ValidBookGenre() {
        BookGenre bookGenre = new BookGenre();
        repository.save(bookGenre);
    }

}
