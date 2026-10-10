package in.shivamvyas.customermanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Size(min = 2, max = 20, message = "City must be between 2 and 20 characters")
    @NotBlank(message = "City is required")
    private String city;
}
