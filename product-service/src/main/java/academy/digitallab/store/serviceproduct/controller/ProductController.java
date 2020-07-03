package academy.digitallab.store.serviceproduct.controller;

import academy.digitallab.store.serviceproduct.entity.Category;
import academy.digitallab.store.serviceproduct.entity.Product;
import academy.digitallab.store.serviceproduct.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@RestController
@RequestMapping(value ="/products")
public class ProductController {
  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<List<Product>> listProducts(){
    List<Product> list = productService.listAllProduct();

    if(list.isEmpty()){
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(list);
  }

  @GetMapping("/categories")
  public ResponseEntity<List<Product>> ListProductFiltered(@RequestParam(name = "categoryId", required = false) Long categoryId){
    List<Product> productsFilter = new ArrayList<>();
    if(categoryId == null){
      productsFilter = productService.listAllProduct();
      if(productsFilter.isEmpty()){
        return ResponseEntity.noContent().build();
      }
    }
    productsFilter = productService.findByCategory(Category.builder().id(categoryId).build());
    if(productsFilter.isEmpty()){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productsFilter);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Long id){
    Product product = productService.getProduct(id);
    if(product == null){
      return ResponseEntity.notFound().build();
    }else{
      return ResponseEntity.ok(product);
    }
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
    if (result.hasErrors()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
    }
    Product productCreate =  productService.createProduct(product);
    return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
  }


  private String formatMessage( BindingResult result){
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

  @PutMapping(value = "/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
    product.setId(id);
    Product productDb = productService.updateProduct(product);
    if(productDb == null){
      ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDb);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
    Product productDb = productService.deleteProduct(id);
    if(productDb == null){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDb);
  }
  @GetMapping(value = "/{id}/stock")
  public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id, @RequestParam(name = "quantity", required = true) Double quantity){
    Product product = productService.updateStock(id, quantity);
    if(product == null){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(product);
  }
}
