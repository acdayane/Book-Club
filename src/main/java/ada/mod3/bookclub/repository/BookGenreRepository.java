package ada.mod3.bookclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ada.mod3.bookclub.model.BookGenre;

public interface BookGenreRepository extends JpaRepository<BookGenre, Integer> {
    
    @Query("SELECT g FROM BookGenre g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<BookGenre> findBookGenreByNameIgnoreCase(String name);

}
