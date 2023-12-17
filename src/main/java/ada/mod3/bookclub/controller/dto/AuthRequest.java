package ada.mod3.bookclub.controller.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class AuthRequest {
    
    @Email
    private String email;

    @Length(min = 4, max = 8)
    private String password;

}
