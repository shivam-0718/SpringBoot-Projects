package in.shivamvyas.vaccinemanager.controller;

import in.shivamvyas.vaccinemanager.dto.VaccineRequest;
import in.shivamvyas.vaccinemanager.dto.VaccineResponse;
import in.shivamvyas.vaccinemanager.service.VaccineManagerService;
import in.shivamvyas.vaccinemanager.view.ResultView1;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vaccines")
public class VaccineManagerController {
    private VaccineManagerService service;

    @Autowired
    public void setService(VaccineManagerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VaccineResponse> registerVaccine(@Valid @RequestBody VaccineRequest request) {
        VaccineResponse response = service.registerVaccine(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VaccineResponse>> fetchVaccines() {
        List<VaccineResponse> vaccinesList = service.fetchVaccines();
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccineResponse> updateVaccineDetails(@PathVariable Long id, @Valid @RequestBody VaccineRequest request) {
        VaccineResponse response = service.updateVaccineDetails(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVaccineById(@PathVariable Long id) {
        String response = service.deleteVaccine(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineResponse> getVaccineById(@PathVariable Long id) {
        VaccineResponse response = service.getVaccineById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<VaccineResponse>> registerVaccines(@RequestBody List<@Valid VaccineRequest> vaccineRequests) {
        List<VaccineResponse> responseList = service.registerVaccines(vaccineRequests);
        return new ResponseEntity<>(responseList, HttpStatus.CREATED);
    }

    @GetMapping(params = "vaccineName")
    public ResponseEntity<List<VaccineResponse>> vaccinesByVaccineName(@RequestParam String vaccineName) {
        List<VaccineResponse> vaccinesList = service.searchByVaccineName(vaccineName);
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @GetMapping(params = "vaccineCompany")
    public ResponseEntity<List<VaccineResponse>> vaccinesByVaccineCompany(@RequestParam String vaccineCompany) {
        List<VaccineResponse> vaccinesList = service.searchByVaccineCompany(vaccineCompany);
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @GetMapping(params = {"minDoses", "maxDoses"})
    public ResponseEntity<List<VaccineResponse>> vaccinesByAvailableDoses(@RequestParam int minDoses, @RequestParam int maxDoses) {
        List<VaccineResponse> vaccinesList = service.searchByAvailableDosesBetween(minDoses, maxDoses);
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @GetMapping(params = {"vaccineName", "view=summary"})
    public ResponseEntity<List<ResultView1>> vaccinesByVaccinesName(@RequestParam String vaccineName) {
        List<ResultView1> vaccinesList = service.searchByVaccineNameContainingIgnoreCase(vaccineName, ResultView1.class);
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @GetMapping(params = {"vaccineCompany", "view=summary"})
    public ResponseEntity<List<ResultView1>> vaccinesByVaccineCompanyName(@RequestParam String vaccineCompany) {
        List<ResultView1> vaccinesList = service.searchByVaccineCompanyContainingIgnoreCase(vaccineCompany, ResultView1.class);
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @GetMapping(params = "threshold")
    public ResponseEntity<List<VaccineResponse>> vaccinesWithLowStock(@RequestParam int threshold) {
        List<VaccineResponse> lowStockVaccines = service.findVaccinesWithLowStock(threshold);
        return new ResponseEntity<>(lowStockVaccines, HttpStatus.OK);
    }

    @GetMapping(params = "disease")
    public ResponseEntity<List<VaccineResponse>> vaccinesEffectiveAgainstDisease(@RequestParam String disease) {
        List<VaccineResponse> lowStockVaccines = service.searchByDisease(disease);
        return new ResponseEntity<>(lowStockVaccines, HttpStatus.OK);
    }

    @GetMapping("/stats/average-cost")
    public ResponseEntity<Map<String, BigDecimal>> averageCost() {
        Map<String, BigDecimal> averageCost = new HashMap<>();
        averageCost.put("averageCost", service.findTheAverageCost());
        return new ResponseEntity<>(averageCost, HttpStatus.OK);
    }

    @PatchMapping(params = {"vaccineName"})
    public ResponseEntity<Map<String, Integer>> updateCostByVaccineName(@RequestBody BigDecimal newCost, @RequestParam String vaccineName) {
        Map<String, Integer> updatedRecords = new HashMap<>();
        int updatedRows = service.updateVaccineCostByVaccineName(newCost, vaccineName);
        updatedRecords.put("updatedCount", updatedRows);
        return new ResponseEntity<>(updatedRecords, HttpStatus.OK);
    }

}
