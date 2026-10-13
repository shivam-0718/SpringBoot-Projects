package in.shivamvyas.vaccinemanager.dao;

import in.shivamvyas.vaccinemanager.dto.VaccineResponse;
import in.shivamvyas.vaccinemanager.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VaccineManagerRepo extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByVaccineNameContainingIgnoreCase(String vaccineName);
    List<Vaccine> findByVaccineCompanyContainingIgnoreCase(String vaccineCompany);
    List<Vaccine> findByCostBetween(BigDecimal minCost, BigDecimal maxCost);
    List<Vaccine> findByAvailableDosesBetween(int minDoses, int maxDoses);
}
