package in.shivamvyas.vaccinemanager.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineRequest {
    @Size(min = 5, max = 50)
    @NotBlank(message = "Vaccine Name should be between 5 and 50 characters.")
    private String vaccineName;

    @Size(min = 5, max = 50)
    @NotBlank(message = "Vaccine Company Name should be between 5 and 50 characters.")
    private String vaccineCompany;

    @NotNull
    @DecimalMin(value = "0.0", message = "Cost should be greater than or equal to zero.")
    @Digits(integer = 10, fraction = 2, message = "Cost is allowed up to 10 digits and 2 decimal places.")
    private BigDecimal cost;

    @PositiveOrZero
    @NotBlank(message = "Available doses should be greater than or equal to zero.")
    private int availableDoses;

    @Size(min = 2, max = 20)
    @NotBlank(message = "Description should be between 2 to 20 characters")
    private String effectiveAgainst;
}
