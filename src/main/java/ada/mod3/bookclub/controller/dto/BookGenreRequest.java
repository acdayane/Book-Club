package ada.mod3.bookclub.controller.dto;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookGenreRequest {
    
    @Length(min = 3, max = 50)
    private String name;

}
