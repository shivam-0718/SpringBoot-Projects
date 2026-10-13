package in.shivamvyas.vaccinemanager.service;

import in.shivamvyas.vaccinemanager.dto.VaccineRequest;
import in.shivamvyas.vaccinemanager.dto.VaccineResponse;

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
}
