package mx.com.spring.controlclientes.dao;

import mx.com.spring.controlclientes.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);

}
