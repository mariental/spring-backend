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
public class AuthenticationRequest {
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password cannot be empty")
    private String password;
    
}

