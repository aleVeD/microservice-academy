package academy.digitallab.store.serviceproduct.service;

import academy.digitallab.store.serviceproduct.entity.Category;
import academy.digitallab.store.serviceproduct.entity.Product;
import academy.digitallab.store.serviceproduct.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    productService = new ProductServiceImpl(productRepository);
    Product prod = Product.builder()
            .name("computer")
            .category(Category.builder().id(1L).build())
            .description("")
            .stock(Double.parseDouble("10"))
            .price(Double.parseDouble("1240.99"))
            .status("Created")
            .createAt(new Date()).build();
    when(productRepository.findById(1L)).thenReturn(Optional.of(prod));
    when(productRepository.save(prod)).thenReturn(prod);
  }

  @Test
  void listAllProduct() {
  }

  @Test
  void getProduct() {
    Product found = productService.getProduct(1L);
    assertEquals(found.getName(), "computer");
  }

  @Test
  void createProduct() {
  }

  @Test
  void updateProduct() {
  }

  @Test
  void deleteProduct() {
  }

  @Test
  void findByCategory() {
  }

  @Test
  void updateStock() {
    Product newStock = productService.updateStock(1L, 8D);
    assertEquals(newStock.getStock(), 18D);
  }
}