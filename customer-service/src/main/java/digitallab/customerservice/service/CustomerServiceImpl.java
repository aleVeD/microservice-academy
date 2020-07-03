package digitallab.customerservice.service;

import digitallab.customerservice.entity.Customer;
import digitallab.customerservice.entity.Region;
import digitallab.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService{

  @Autowired
  CustomerRepository customerRepository;
  @Override
  public List<Customer> findAllCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public List<Customer> findCustomerByRegion(Region region) {
    return customerRepository.findCustomerByRegion(region);
  }
  @Override
  public Customer createCustomer(Customer customer) {

    Customer customerDB = customerRepository.findCustomerByNumberID ( customer.getNumberID () );
    if (customerDB != null){
      return  customerDB;
    }

    customer.setState("CREATED");
    customerDB = customerRepository.save ( customer );
    return customerDB;
  }


  @Override
  public Customer getCustomer(Long id) {
    return customerRepository.findById(id).orElse(null);
  }


  @Override
  public Customer updateCustomer(Customer customer) {
    Customer customerDB = getCustomer(customer.getId());
    if (customerDB == null){
      return  null;
    }
    customerDB.setFirstName(customer.getFirstName());
    customerDB.setLastName(customer.getLastName());
    customerDB.setEmail(customer.getEmail());
    customerDB.setPhotoUrl(customer.getPhotoUrl());

    return  customerRepository.save(customerDB);
  }

  @Override
  public Customer deleteCustomer(Customer customer) {
    Customer customerDb = getCustomer(customer.getId());
    if(customerDb == null){
      return null;
    }
    customerDb.setState("DELETED");
    return customerRepository.save(customerDb);
  }
}
