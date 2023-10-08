package ada.mod3.bookclub.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ada.mod3.bookclub.controller.dto.BookGenreRequest;
import ada.mod3.bookclub.controller.dto.BookGenreResponse;
import ada.mod3.bookclub.model.BookGenre;
import ada.mod3.bookclub.repository.BookGenreRepository;
import ada.mod3.bookclub.utils.BookGenreConvert;

@Service
public class BookGenreService {
    
    @Autowired
    BookGenreRepository bookGenreRepository;

    public BookGenreResponse saveGenre(BookGenreRequest genreDTO) {
        BookGenre genre = BookGenreConvert.toEntity(genreDTO);
        return BookGenreConvert.toResponse(bookGenreRepository.save(genre));
    }

    public void deleteGenreById(Integer id) {
        BookGenre genre = bookGenreRepository.findById(id).orElseThrow();
        bookGenreRepository.delete(genre);
    }

    public BookGenreResponse getGenreById(Integer id) {     
        Optional<BookGenre> genreResponse =  bookGenreRepository.findById(id);
        if(genreResponse.isPresent()){
            return BookGenreConvert.toResponse(genreResponse.get());
        } else {
            throw new RuntimeException("Book genre not found");
        }
    }

    public List<BookGenre> getAllGenres() {
        return bookGenreRepository.findAll();
    }

    public List<BookGenreResponse> getByName(String name){
        List<BookGenre> genreResponse =  bookGenreRepository.findBookGenreByNameIgnoreCase(name);
        if(genreResponse.isEmpty()){
          throw new RuntimeException("Name not found");
        }
        return BookGenreConvert.toResponseList(bookGenreRepository.findBookGenreByNameIgnoreCase(name));
    }

}
