package in.shivamvyas.vaccinemanager.controller;

import in.shivamvyas.vaccinemanager.dto.VaccineRequest;
import in.shivamvyas.vaccinemanager.dto.VaccineResponse;
import in.shivamvyas.vaccinemanager.service.VaccineManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VaccineManagerController {

    private VaccineManagerService service;

    @Autowired
    public void setService(VaccineManagerService service) {
        this.service = service;
    }

    @PostMapping("/vaccine")
    public ResponseEntity<VaccineResponse> registerVaccine(@Valid @RequestBody VaccineRequest request) {
        VaccineResponse response = service.registerVaccine(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/vaccines")
    public ResponseEntity<List<VaccineResponse>> fetchVaccines() {
        List<VaccineResponse> vaccinesList = service.fetchVaccines();
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @PutMapping("/vaccines/{id}")
    public ResponseEntity<VaccineResponse> updateVaccineDetails(@PathVariable Long id, @Valid @RequestBody VaccineRequest request) {
        VaccineResponse response = service.updateVaccineDetails(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/vaccines/{id}")
    public ResponseEntity<String> deleteVaccineById(@PathVariable Long id) {
        String response = service.deleteVaccine(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

}
