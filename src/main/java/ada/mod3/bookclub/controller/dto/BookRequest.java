package ada.mod3.bookclub.controller.dto;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookRequest {
    
    @Length(min = 3, max = 50)
    private String title;

    @Length(min = 3, max = 50)
    private String author;

    private Integer genre_Id;
}
