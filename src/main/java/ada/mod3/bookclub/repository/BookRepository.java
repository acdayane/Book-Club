package ada.mod3.bookclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ada.mod3.bookclub.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :titleOrAuthor, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :titleOrAuthor, '%'))")
    List<Book> findByTitleOrAuthorIgnoreCase(String titleOrAuthor);

}
