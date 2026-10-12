package in.shivamvyas.vaccinemanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "Vaccines")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Vaccine_Id")
    private Long id;

    @Column(name = "Vaccine_Name")
    private String vaccineName;

    @Column(name = "Vaccine_Company")
    private String vaccineCompany;

    @Column(name = "Cost_INR")
    private BigDecimal cost;

    @Column(name = "Available_Doses")
    private int availableDoses;

    @Column(name = "Effective_Against")
    private String effectiveAgainst;

}
