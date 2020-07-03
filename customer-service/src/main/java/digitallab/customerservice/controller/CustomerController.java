package digitallab.customerservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import digitallab.customerservice.entity.Customer;
import digitallab.customerservice.entity.Region;
import digitallab.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers(@RequestParam(name = "regionId", required = false) Long regionId){
      List<Customer> customers = new ArrayList<>();
      if(regionId == null){
        customers = customerService.findAllCustomers();
        if(customers.isEmpty()){
          return ResponseEntity.noContent().build();
        }
      }else{
        Region region = new Region();
        region.setId(regionId);
        customers = customerService.findCustomerByRegion(region);
        if(customers == null){
          log.error("No se encontraron consumidores con region ", regionId);
          return ResponseEntity.notFound().build();
        }
      }
      return ResponseEntity.ok(customers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable(name = "id") Long id){
      log.info("Fetching Customer id", id);
      Customer customer = customerService.getCustomer(id);
      if(customer == null){
        log.error(" Customer don't found");
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result){
      log.info("creating a customer");
      if(result.hasErrors()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
      }
      Customer customerDb = customerService.createCustomer(customer);
      return ResponseEntity.status(HttpStatus.CREATED).body(customerDb);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable("id") Long id){
      log.info("updating customer");
      Customer customerDb = customerService.getCustomer ( id);
      if(customerDb == null){
        return ResponseEntity.notFound().build();
      }
      customer.setId(id);
      customerDb = customerService.updateCustomer(customer);
      return ResponseEntity.ok(customerDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") Long id){
      log.info("begin to deleting");
      Customer customer = customerService.getCustomer(id);
      if(customer==null){
        return ResponseEntity.notFound().build();
      }
      customer = customerService.deleteCustomer(customer);
      return ResponseEntity.ok(customer);
    }

    private String formatMessage(BindingResult result){
      List<Map<String,String>> errors = result.getFieldErrors().stream()
              .map(err ->{
                Map<String,String>  error =  new HashMap<>();
                error.put(err.getField(), err.getDefaultMessage());
                return error;

              }).collect(Collectors.toList());
      ErrorMessage errorMessage = ErrorMessage.builder()
              .code("01")
              .messages(errors).build();
      ObjectMapper mapper = new ObjectMapper();
      String jsonString="";
      try {
        jsonString = mapper.writeValueAsString(errorMessage);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return jsonString;
    }
}

































