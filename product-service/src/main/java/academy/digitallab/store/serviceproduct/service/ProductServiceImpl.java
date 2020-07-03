package academy.digitallab.store.serviceproduct.service;
import academy.digitallab.store.serviceproduct.entity.Category;
import academy.digitallab.store.serviceproduct.entity.Product;
import academy.digitallab.store.serviceproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

  private final ProductRepository productRepository;

  @Override
  public List<Product> listAllProduct() {
    return productRepository.findAll();
  }

  @Override
  public Product getProduct(Long id) {
    return productRepository.findById(id).orElse(null);
  }

  @Override
  public Product createProduct(Product product) {
    product.setStatus("Created");
    product.setCreateAt(new Date());
    return productRepository.save(product);
  }

  @Override
  public Product updateProduct(Product product) {
    Product productdb= getProduct(product.getId());
    if(productdb == null){
      return null;
    }else{
      productdb.setName(product.getName());
      productdb.setDescription(product.getDescription());
      productdb.setCategory(product.getCategory());
      productdb.setPrice(product.getPrice());
      productdb.setStatus(product.getStatus());
      productdb.setCreateAt(product.getCreateAt());
    }
    return productRepository.save(productdb);
  }

  @Override
  public Product deleteProduct(Long id) {
    Product productdb= getProduct(id);
    if(productdb == null){
      return null;
    }else{
      productdb.setStatus("DELETED");

    }
    return productRepository.save(productdb);
  }

  @Override
  public List<Product> findByCategory(Category category) {
    return productRepository.findByCategory(category);
  }

  @Override
  public Product updateStock(Long id, Double quantity) {
    Product productdb= getProduct(id);
    if(productdb == null){
      return null;
    }else {
      Double stock = productdb.getStock() + quantity;
      productdb.setStock(stock);
    }
    return productRepository.save(productdb);
  }
}
