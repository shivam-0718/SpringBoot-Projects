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
    /**
     * Derived (custom finder) query: Spring Data builds the SQL from the method name.
     * Containing = LIKE %value%, IgnoreCase = compares in upper case (case-insensitive).
     */
    List<Vaccine> findByVaccineNameContainingIgnoreCase(String vaccineName);

    /** Derived query on the company field, same Containing + IgnoreCase rules as the name finder. */
    List<Vaccine> findByVaccineCompanyContainingIgnoreCase(String vaccineCompany);

    /** Derived query: Between = SQL BETWEEN, both min and max are inclusive. */
    List<Vaccine> findByCostBetween(BigDecimal minCost, BigDecimal maxCost);

    /** Derived query: doses in the inclusive range minDoses..maxDoses. */
    List<Vaccine> findByAvailableDosesBetween(int minDoses, int maxDoses);

    /**
     * Dynamic projection: the trailing Class<T> decides the return type at call time, so the same finder can return
     * only the fields of the given View interface (closed projection -> SELECT lists only those columns).
     * T extends View (marker interface) restricts callers to our view interfaces.
     * Overloads the entity-returning finder of the same name.
     */
    <T extends View> List<T> findByVaccineNameContainingIgnoreCase(String vaccineName, Class<T> type);

    /** Dynamic projection on the company field, same rules as the name version above. */
    <T extends View> List<T> findByVaccineCompanyContainingIgnoreCase(String vaccineCompany, Class<T> type);

    /**
     * Custom JPQL update query (not available in JpaRepository). @Modifying is required for UPDATE/DELETE queries;
     * the return value is the number of rows updated. It needs a transaction, and it bypasses the persistence
     * context, so entities already loaded can be stale (clearAutomatically = true clears them).
     */
    @Transactional
    @Modifying
    @Query("UPDATE Vaccine SET cost=:newCost WHERE vaccineName=:vaccineName")
    int updateCostByVaccineName(@Param("newCost") BigDecimal newCost, @Param("vaccineName") String vaccineName);

    /**
     * Custom JPQL query: uses entity/field names (Vaccine, availableDoses), not table/column names.
     * :threshold is a named parameter bound through @Param.
     */
    @Query("SELECT v FROM Vaccine v WHERE v.availableDoses < :threshold ORDER BY v.availableDoses ASC")
    List<Vaccine> findLowStock(@Param("threshold") int threshold);

    /** Case-insensitive partial match: LOWER on both the column and the value, CONCAT adds the % wildcards. */
    @Query("SELECT v FROM Vaccine v WHERE LOWER(v.effectiveAgainst) LIKE LOWER(CONCAT('%', :disease, '%'))")
    List<Vaccine> searchByDisease(@Param("disease") String disease);

    /** Aggregate query returning a single value. Hibernate returns AVG as Double (converted here); null if the table is empty. */
    @Query("SELECT AVG(v.cost) FROM Vaccine v")
    BigDecimal averageCost();
}
