package ada.mod3.bookclub.controller.dto;

import ada.mod3.bookclub.model.BookGenre;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookResponse {
    
    private Integer id;
    private String title;
    private String author;
    private BookGenre genre;
    
}
