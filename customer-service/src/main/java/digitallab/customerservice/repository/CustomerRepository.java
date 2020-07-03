package digitallab.customerservice.repository;

import digitallab.customerservice.entity.Customer;
import digitallab.customerservice.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Customer findCustomerByNumberID(String numberId);
  List<Customer> findCustomerByLastName(String lastName);
  List<Customer> findCustomerByRegion(Region region);
}
