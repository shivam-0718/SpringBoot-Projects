package in.shivamvyas.vaccinemanager.dao;

import in.shivamvyas.vaccinemanager.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineManagerRepo extends JpaRepository<Vaccine, Long> {
}
