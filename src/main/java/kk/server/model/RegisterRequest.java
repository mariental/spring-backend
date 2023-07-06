package kk.server.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull(message = "First name is mandatory")
    @NotBlank(message = "First name cannot be empty")
    private String firstname;
        
    @NotNull(message = "Last name is mandatory")
    @NotBlank(message = "Last name cannot be empty")
    private String lastname;
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password be empty")
    private String password;  
}
