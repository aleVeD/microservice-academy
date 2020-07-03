package academy.digitallab.store.serviceproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tbl_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty(message = "Debe colocar un nombre")
  private String name;
  private String description;
  @Positive(message = "El valor del stock debe de ser mayor de 0")
  private Double stock;
  private Double price;
  private String status;
  @Column(name = "create_at")
  @Temporal(TemporalType.DATE)
  private Date createAt;
  @NotNull(message = "la categoría no puede ser vacía")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Category category;
}
