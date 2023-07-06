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
public class PostRequest {
    @NotNull(message = "Title is mandatory")
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotNull(message = "Content is mandatory")
    @NotBlank(message = "Content cannot be empty")
    private String content;
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email cannot be empty")
    private String email;
}
