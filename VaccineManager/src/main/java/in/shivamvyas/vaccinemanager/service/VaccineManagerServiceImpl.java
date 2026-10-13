package in.shivamvyas.vaccinemanager.service;

import in.shivamvyas.vaccinemanager.dao.VaccineManagerRepo;
import in.shivamvyas.vaccinemanager.dto.VaccineRequest;
import in.shivamvyas.vaccinemanager.dto.VaccineResponse;
import in.shivamvyas.vaccinemanager.entity.Vaccine;
import in.shivamvyas.vaccinemanager.exception.VaccineNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VaccineManagerServiceImpl implements VaccineManagerService{
    private VaccineManagerRepo vaccineRepo;

    @Autowired
    public void setVaccineRepo(VaccineManagerRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    @Override
    @Transactional
    public VaccineResponse registerVaccine(VaccineRequest request) {
        Vaccine entity = requestToEntity(request);
        Vaccine savedVaccine = vaccineRepo.save(entity);
        return entityToResponse(savedVaccine);
    }

    @Override
    public List<VaccineResponse> fetchVaccines() {
        List<Vaccine> vaccinesList = vaccineRepo.findAll();

        List<VaccineResponse> vaccineResponseList = new ArrayList<>();
        for(Vaccine v : vaccinesList) {
            vaccineResponseList.add(entityToResponse(v));
        }

        return vaccineResponseList;
    }

    @Override
    @Transactional
    public VaccineResponse updateVaccineDetails(Long id, VaccineRequest request) {
        Optional<Vaccine> optional = vaccineRepo.findById(id);
        if(optional.isPresent()) {
            Vaccine vaccine = optional.get();

            vaccine.setVaccineName(request.getVaccineName());
            vaccine.setVaccineCompany(request.getVaccineCompany());
            vaccine.setCost(request.getCost());
            vaccine.setAvailableDoses(request.getAvailableDoses());
            vaccine.setEffectiveAgainst(request.getEffectiveAgainst());

            vaccineRepo.save(vaccine);

            return entityToResponse(vaccine);
        }
        throw new VaccineNotFoundException("Vaccine with given id is not available. Try again with correct vaccine id.");
    }

    @Override
    @Transactional
    public String deleteVaccine(Long id) {
        Optional<Vaccine> optional = vaccineRepo.findById(id);
        if(optional.isPresent()) {
            vaccineRepo.delete(optional.get());
            return "The vaccine with id " + id + " has been deleted from the records. Thank you!";
        }

        throw new VaccineNotFoundException("Vaccine with given id is not available. Try again with correct vaccine id.");
    }

    @Override
    public VaccineResponse getVaccineById(Long id) {
        Optional<Vaccine> optional = vaccineRepo.findById(id);
        if(optional.isPresent()) {
            Vaccine fetchedVaccine = optional.get();
            return entityToResponse(fetchedVaccine);
        }
        throw new VaccineNotFoundException("Vaccine with id " + id + " is not available. Try again with correct id.");
    }

    @Override
    @Transactional
    public List<VaccineResponse> registerVaccines(List<VaccineRequest> vaccineRequestList) {
        List<VaccineResponse> registeredVaccines = new ArrayList<>();

        for(VaccineRequest request : vaccineRequestList) {
            // convert request to entity
            Vaccine entity = requestToEntity(request);

            // save into the database
            Vaccine registeredVaccine = vaccineRepo.save(entity);

            // extract the response DTO from the registered vaccine
            registeredVaccines.add(entityToResponse(registeredVaccine));
        }

        return registeredVaccines;
    }

    @Override
    public List<VaccineResponse> searchByVaccineName(String vaccineName) {
        List<Vaccine> list = vaccineRepo.findByVaccineNameContainingIgnoreCase(vaccineName);
        List<VaccineResponse> fetchedVaccines = new ArrayList<>();

        for (Vaccine v : list) {
            fetchedVaccines.add(entityToResponse(v));
        }

        return fetchedVaccines;
    }

    @Override
    public List<VaccineResponse> searchByVaccineCompany(String vaccineCompany) {
        List<Vaccine> list = vaccineRepo.findByVaccineCompanyContainingIgnoreCase(vaccineCompany);
        List<VaccineResponse> fetchedVaccines = new ArrayList<>();

        for (Vaccine v : list) {
            fetchedVaccines.add(entityToResponse(v));
        }

        return fetchedVaccines;
    }

    @Override
    public List<VaccineResponse> searchByCostBetween(BigDecimal minCost, BigDecimal maxCost) {
        List<Vaccine> list = vaccineRepo.findByCostBetween(minCost, maxCost);
        List<VaccineResponse> fetchedVaccines = new ArrayList<>();

        for (Vaccine v : list) {
            fetchedVaccines.add(entityToResponse(v));
        }

        return fetchedVaccines;
    }

    @Override
    public List<VaccineResponse> searchByAvailableDosesBetween(int minDoses, int maxDoses) {
        List<Vaccine> list = vaccineRepo.findByAvailableDosesBetween(minDoses, maxDoses);
        List<VaccineResponse> fetchedVaccines = new ArrayList<>();

        for (Vaccine v : list) {
            fetchedVaccines.add(entityToResponse(v));
        }

        return fetchedVaccines;
    }

    /**
     * Private helper method to convert VaccineRequest DTO to Vaccine Entity for persistence in the DB
     * @return Vaccine
     */
    private Vaccine requestToEntity(VaccineRequest request) {
        return Vaccine.builder().vaccineName(request.getVaccineName())
                .vaccineCompany(request.getVaccineCompany())
                .cost(request.getCost())
                .availableDoses(request.getAvailableDoses())
                .effectiveAgainst(request.getEffectiveAgainst())
                .build();
    }

    /**
     * Private helper method to convert Vaccine Entity to VaccineResponse DTO for giving response to the client
     * @return VaccineResponse
     */
    private VaccineResponse entityToResponse(Vaccine vaccine) {
        return VaccineResponse.builder()
                .id(vaccine.getId())
                .vaccineName(vaccine.getVaccineName())
                .vaccineCompany(vaccine.getVaccineCompany())
                .cost(vaccine.getCost())
                .availableDoses(vaccine.getAvailableDoses())
                .build();
    }
}
