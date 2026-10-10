package in.shivamvyas.customermanager.repository;

import in.shivamvyas.customermanager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerManagerRepo extends JpaRepository<Customer, Long> {

}
