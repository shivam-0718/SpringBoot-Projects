package in.shivamvyas.vaccinemanager.service;

import in.shivamvyas.vaccinemanager.dto.VaccineRequest;
import in.shivamvyas.vaccinemanager.dto.VaccineResponse;
import in.shivamvyas.vaccinemanager.view.View;
import java.math.BigDecimal;
import java.util.List;

public interface VaccineManagerService {
    VaccineResponse registerVaccine(VaccineRequest request);
    List<VaccineResponse> fetchVaccines();
    VaccineResponse updateVaccineDetails(Long id, VaccineRequest request);
    String deleteVaccine(Long id);

    VaccineResponse getVaccineById(Long id);
    List<VaccineResponse> registerVaccines(List<VaccineRequest> vaccineRequestList);

    /*
     * Service method names differ from the repository ones so the two layers are easy to tell apart.
     * Finder/query methods return VaccineResponse DTOs (entities are never exposed to the controller).
     */

    /** Wraps the repository name finder (partial, case-insensitive match) and maps the entities to DTOs. */
    List<VaccineResponse> searchByVaccineName(String vaccineName);

    /** Wraps the repository company finder and maps the entities to DTOs. */
    List<VaccineResponse> searchByVaccineCompany(String vaccineCompany);

    /** Wraps findByCostBetween (inclusive range) and maps the entities to DTOs. */
    List<VaccineResponse> searchByCostBetween(BigDecimal minCost, BigDecimal maxCost);

    /** Wraps findByAvailableDosesBetween (inclusive range) and maps the entities to DTOs. */
    List<VaccineResponse> searchByAvailableDosesBetween(int minDoses, int maxDoses);

    /**
     * Dynamic projection: returns only the fields of the given view (e.g. ResultView1) instead of VaccineResponse.
     * The caller (controller) chooses the view class; the service just passes it to the repository.
     */
    <T extends View> List<T> searchByVaccineNameContainingIgnoreCase(String vaccineName, Class<T> type);

    /** Dynamic projection on the company field, same behaviour as the name version. */
    <T extends View> List<T> searchByVaccineCompanyContainingIgnoreCase(String vaccineCompany, Class<T> type);

    /** Bulk update of the cost by vaccine name (@Modifying query); returns the number of rows updated. */
    int updateVaccineCostByVaccineName(BigDecimal newCost, String vaccineName);

    /** Vaccines whose available doses are below the threshold, lowest stock first (custom JPQL query). */
    List<VaccineResponse> findVaccinesWithLowStock(int threshold);

    /** Case-insensitive partial search on the effectiveAgainst field (custom JPQL query). */
    List<VaccineResponse> searchByDisease(String disease);

    /** Average cost of all vaccines (aggregate query); null when there are no vaccines. */
    BigDecimal findTheAverageCost();
}
