package ada.mod3.bookclub.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ada.mod3.bookclub.controller.dto.BookRequest;
import ada.mod3.bookclub.controller.dto.BookResponse;
import ada.mod3.bookclub.model.Book;
import ada.mod3.bookclub.service.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(@Valid @RequestBody BookRequest bookDTO) {
        BookResponse book = bookService.saveBook(bookDTO);
        return ResponseEntity.created(URI.create("/book/" + book.getId())).body(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/all")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/search/{titleOrAuthor}")
    public ResponseEntity<List<BookResponse>> getSearch(@PathVariable String titleOrAuthor){
        return ResponseEntity.ok(bookService.getByTitleOrAuthor(titleOrAuthor));
    }
    
}
