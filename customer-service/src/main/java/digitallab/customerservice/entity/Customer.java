package digitallab.customerservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "tbl_customers")
@Data
public class Customer implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "no puede quedar vacío")
  @Size(min = 8, max = 8, message = "el tamaño del documento debe de ser 8")
  @Column(name = "number_id", unique = true, length = 8, nullable = false)
  private String numberID;

  @NotEmpty(message = "El nombre no puede estar vacío")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotEmpty(message = "El apellido no puede estar vacio")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotEmpty(message = "El correo no puede estar vacío")
  @Email( message = "no es un correo válido")
  @Column(unique = true, nullable = false)
  private String email;

  @Column(name = "photo_url")
  private String photoUrl;

  @NotNull(message = "No puede estar vacia la region")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "region_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Region region;

  private String state;

}
