package academy.digitallab.store.serviceproduct.repository;

import academy.digitallab.store.serviceproduct.entity.Category;
import academy.digitallab.store.serviceproduct.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void whenFindByCategory_thenReturnListProduct(){
    Product product01 = Product.builder()
            .name("computer")
            .category(Category.builder().id(1L).build())
            .description("")
            .stock(Double.parseDouble("10"))
            .price(Double.parseDouble("1240.99"))
            .status("Created")
            .createAt(new Date()).build();
    productRepository.save(product01);

    List<Product> founds= productRepository.findByCategory(product01.getCategory());

    Assertions.assertEquals(3, founds.size());
  }
}