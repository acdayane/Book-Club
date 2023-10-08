package ada.mod3.bookclub.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ada.mod3.bookclub.controller.dto.BookRequest;
import ada.mod3.bookclub.controller.dto.BookResponse;
import ada.mod3.bookclub.model.Book;
import ada.mod3.bookclub.model.BookGenre;
import ada.mod3.bookclub.repository.BookGenreRepository;
import ada.mod3.bookclub.repository.BookRepository;
import ada.mod3.bookclub.utils.BookConvert;

@Service
public class BookService {
    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookGenreRepository bookGenreRepository;

    public BookResponse saveBook(BookRequest bookDTO) {
        Optional<BookGenre> genreResponse =  bookGenreRepository.findById(bookDTO.getGenre_Id());
        if(genreResponse.isPresent()){
            System.out.println(genreResponse.get().getId());
            Book book = BookConvert.toEntity(bookDTO, genreResponse.get());
            return BookConvert.toResponse(bookRepository.save(book));
        } else {
            throw new RuntimeException("Cannot save: book genre not found");
        }

    }

    public void deleteBookById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
    }

    public BookResponse getBookById(Integer id) {     
        Optional<Book> bookResponse =  bookRepository.findById(id);
        if(bookResponse.isPresent()){
            return BookConvert.toResponse(bookResponse.get());
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<BookResponse> getByTitleOrAuthor(String titleOrAuthor){
        List<Book> bookResponse =  bookRepository.findByTitleOrAuthorIgnoreCase(titleOrAuthor);
        if(bookResponse.isEmpty()){
          throw new RuntimeException("No title or author found");
        }
        return BookConvert.toResponseList(bookRepository.findByTitleOrAuthorIgnoreCase(titleOrAuthor));
    }

}
