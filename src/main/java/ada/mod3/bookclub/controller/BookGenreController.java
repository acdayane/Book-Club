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

import ada.mod3.bookclub.controller.dto.BookGenreRequest;
import ada.mod3.bookclub.controller.dto.BookGenreResponse;
import ada.mod3.bookclub.model.BookGenre;
import ada.mod3.bookclub.service.BookGenreService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/book-genre")
public class BookGenreController {

    @Autowired
    BookGenreService bookGenreService;

    @PostMapping
    public ResponseEntity<BookGenreResponse> saveGenre(@Valid @RequestBody BookGenreRequest genreDTO) {
        BookGenreResponse genre = bookGenreService.saveGenre(genreDTO);
        return ResponseEntity.created(URI.create("/book-genre/" + genre.getId())).body(genre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable Integer id) {
        bookGenreService.deleteGenreById(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookGenreResponse> getGenre(@PathVariable Integer id) {
        return ResponseEntity.ok(bookGenreService.getGenreById(id));
    }

    @GetMapping("/all")
    public List<BookGenre> getGenres() {
        return bookGenreService.getAllGenres();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<BookGenreResponse>> getGenderByName(@PathVariable String name){
        return ResponseEntity.ok(bookGenreService.getByName(name));
    }
    
}
