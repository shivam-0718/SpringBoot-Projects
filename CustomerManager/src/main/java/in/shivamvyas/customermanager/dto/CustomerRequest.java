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

    @Size(min = 2, max = 20)
    @NotBlank(message = "First name must be between 2 and 20 characters")
    private String firstName;

    @Size(min = 2, max = 20)
    @NotBlank(message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @Size(min = 2, max = 20)
    @NotBlank(message = "First name must be between 2 and 20 characters")
    private String city;
}
