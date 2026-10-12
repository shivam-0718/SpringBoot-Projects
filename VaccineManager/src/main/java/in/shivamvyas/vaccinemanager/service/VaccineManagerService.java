package in.shivamvyas.vaccinemanager.service;

import in.shivamvyas.vaccinemanager.dto.VaccineRequest;
import in.shivamvyas.vaccinemanager.dto.VaccineResponse;

import java.util.List;

public interface VaccineManagerService {
    VaccineResponse registerVaccine(VaccineRequest request);
    List<VaccineResponse> fetchVaccines();
    VaccineResponse updateVaccineDetails(Long id, VaccineRequest request);
    String deleteVaccine(Long id);
}
