package mx.com.spring.controlclientes.servicio;

import lombok.extern.slf4j.Slf4j;
import mx.com.spring.controlclientes.dao.UsuarioDAO;
import mx.com.spring.controlclientes.domain.Rol;
import mx.com.spring.controlclientes.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * TODO @Service - Marca una clase como un servicio proporciona metadatos adicionales para Spring, permitiéndole identificar automáticamente los servicios durante el escaneo de componentes.
 * Las clases anotadas con @Service suelen contener la lógica de negocio principal de la aplicación. Esto puede incluir operaciones de procesamiento, acceso a bases de datos, llamadas a
 * servicios externos, y cualquier otra tarea relacionada con la funcionalidad principal de la aplicación.
 * También funciona para Inyectar dependencias y escaneo automático ( Spring identifica las clases marcadas y las registra como beans ).
 *
 * TODO @Service("userDetailsService") - marca una clase como un servicio llamado "userDetailsService" en el contexto de Spring, y este servicio puede estar relacionado con la gestión de detalles del usuario,
 * especialmente en el ámbito de la autenticación.
 */
@Service("userDetailsService")
@Slf4j
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioDAO usuarioDao;
    @Override
    /**
     * TODO @Transactional -  indica que el método debe ejecutarse dentro de una transacción.
     * Una transacción agrupa un conjunto de operaciones de base de datos, asegurando que estas operaciones se realicen de manera atómica,
     * consistente, aislada y duradera (propiedades ACID).
     * Las transacciones son esenciales para mantener la integridad de la base de datos y garantizar que las operaciones se realicen correctamente
     * Spring maneja automáticamente la gestión de la transacción, asegurándose de que se inicie y se realice (commit) correctamente, o se revierta (rollback) en caso de errores.
     *
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        if(usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        var roles = new ArrayList<GrantedAuthority>();
        for (Rol rol : usuario.getRoles()) {
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        //TODO User - Es un objeto de spring el cual se usa en spring security de forma automática.
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
