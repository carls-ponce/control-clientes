package mx.com.spring.controlclientes.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
@Data
@Entity
@Table(name = "persona")
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Persona;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    /**
     * TODO Email - se utiliza para validar que un campo de un bean es una dirección de correo electrónico válida.
     * Esta anotación ayuda a asegurar que el valor de un campo cumpla con el formato básico de una dirección de correo electrónico.
     */
    @Email
    private String email;
    private String telefono;

}
