package mx.com.spring.controlclientes.dao;

import mx.com.spring.controlclientes.domain.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaDAO extends CrudRepository<Persona, Long> {
}
