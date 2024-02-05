package mx.com.spring.controlclientes.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * TODO  @Entity - Marca una clase como entidad persistente.
 * Representan tablas en la bd y se utiilizan para realizar operaciones CRUD en bd mediante JPA.
 */
@Entity
/**
 * TODO @Data - Genera automaticamente los métodos toString, equals, hashcode, getter y setter.
 * Es una anotación de lombok y facilita la creación de POJO's - Plain Old Java Object.
 */
@Data

/**
 * TODO @Table - Se utiliza para especificar el nombre de la tabla de BD, es opcional pero recomendable ya que puede haber conflictos entre el nombre de la clase java y BD.
 */
@Table(name="usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * TODO Id - Indica que se trata de una llave primaria.
     */
    @Id
    /**
     * TODO @GeneratedValue - Especifica el tipo de estrategia para la generación de clave primaria a usar.
     * El tipo IDENTITY se utiliza cuando la BD es responsable de generar automaticamente los valores de clave primaria.
     * Con ello le dice a hibernate/jpa que deje a la BD manejar el autoincremente del ID.
     * Si se omite, Spring usará el tipo AUTO pero puede que no sea una opción eficiente, se recomienda poner explicitamente el tipo.
     * https://stackoverflow.com/questions/20603638/what-is-the-use-of-annotations-id-and-generatedvaluestrategy-generationtype
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    //Un usuario puede tener múltiples roles -> Uno a muchos
    //Indica una relación de uno a muchos entre entidades.
    @OneToMany
    //Indica la columna que va a hacer la relación entre las tablas
    /**
     * TODO @JoinColumn - Utilizada para especificar la columna que se utilizará como clave foránea para la asociación entre entidades.
     */
    @JoinColumn(name = "id_usuario")
    //Lista que recupera los roles asociados a un usuario.
    private List<Rol> roles;
}
