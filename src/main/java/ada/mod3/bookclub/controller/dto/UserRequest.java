package ada.mod3.bookclub.controller.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequest {
    
    @Length(min = 3, max = 50)
    private String name;

    @Email
    private String email;

    @Length(min = 4, max = 8)
    private String password;

}
