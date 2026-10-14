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
    List<VaccineResponse> searchByVaccineName(String vaccineName);
    List<VaccineResponse> searchByVaccineCompany(String vaccineCompany);
    List<VaccineResponse> searchByCostBetween(BigDecimal minCost, BigDecimal maxCost);
    List<VaccineResponse> searchByAvailableDosesBetween(int minDoses, int maxDoses);

    <T extends View> List<T> searchByVaccineNameContainingIgnoreCase(String vaccineName, Class<T> type);
    <T extends View> List<T> searchByVaccineCompanyContainingIgnoreCase(String vaccineCompany, Class<T> type);
    int updateVaccineCostByVaccineName(BigDecimal newCost, String vaccineName);
    List<VaccineResponse> findVaccinesWithLowStock(int threshold);
    List<VaccineResponse> searchByDisease(String disease);
    BigDecimal averageCost();
}
