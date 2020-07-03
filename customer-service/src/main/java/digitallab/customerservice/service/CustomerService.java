package digitallab.customerservice.service;

import digitallab.customerservice.entity.Customer;
import digitallab.customerservice.entity.Region;

import java.util.List;

public interface CustomerService {
  List<Customer> findAllCustomers();
  List<Customer> findCustomerByRegion(Region region);
  Customer createCustomer(Customer customer);
  Customer updateCustomer(Customer customer);
  Customer deleteCustomer(Customer customer);
  Customer getCustomer(Long id);
}
