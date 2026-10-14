package in.shivamvyas.vaccinemanager.dao;

import in.shivamvyas.vaccinemanager.entity.Vaccine;
import in.shivamvyas.vaccinemanager.view.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface VaccineManagerRepo extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByVaccineNameContainingIgnoreCase(String vaccineName);
    List<Vaccine> findByVaccineCompanyContainingIgnoreCase(String vaccineCompany);
    List<Vaccine> findByCostBetween(BigDecimal minCost, BigDecimal maxCost);
    List<Vaccine> findByAvailableDosesBetween(int minDoses, int maxDoses);

    <T extends View> List<T> findByVaccineNameContainingIgnoreCase(String vaccineName, Class<T> type);
    <T extends View> List<T> findByVaccineCompanyContainingIgnoreCase(String vaccineCompany, Class<T> type);

    @Transactional
    @Modifying
    @Query("UPDATE Vaccine SET cost=:newCost WHERE vaccineName=:vaccineName")
    int updateCostByVaccineName(@Param("newCost") BigDecimal newCost, @Param("vaccineName") String vaccineName);

    @Query("SELECT v FROM Vaccine v WHERE v.availableDoses < :threshold ORDER BY v.availableDoses ASC")
    List<Vaccine> findLowStock(@Param("threshold") int threshold);

    @Query("SELECT v FROM Vaccine v WHERE LOWER(v.effectiveAgainst) LIKE LOWER(CONCAT('%', :disease, '%'))")
    List<Vaccine> searchByDisease(@Param("disease") String disease);

    @Query("SELECT AVG(v.cost) FROM Vaccine v")
    BigDecimal averageCost();
}
