package in.shivamvyas.vaccinemanager.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class VaccineResponse {
    private Long id;
    private String vaccineName;
    private String vaccineCompany;
    private BigDecimal cost;
    private int availableDoses;
}
