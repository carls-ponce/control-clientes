package mx.com.spring.controlclientes.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


/**
 * The type Security config.
 * TODO @Configuration - Marca la clase como fuente de configuración, Spring escaneará las clases anotadas con configuración para realizar el ajuste correspondiente.
 */
@Configuration
//Usado para habilitar la seguridad web.
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

/*  TODO: Este método da error del tipo BeanCurrentlyInCreationException ya que tiene dependencias mutuas (dependencias circulares).
    Se encontraron 2 soluciones a este error:

    1. Removiendo de la funcion que creamos passwordEnconder() la anotación @Bean.
    ---> @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    2. Creando directamente la codificación, es decir; en el método configurerGlobal()
    llamamos directamente el new BCryptPasswordEncoder():

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    */
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    /**
     * Users user details service.
     *
     * @return the user details service
     */
    @Bean
    public UserDetailsService users(){
        /**
         * TODO {noop} - "No Operation" indica que no se debe aplicar ninguna codificación (hashing) a la contraseña almacenada en texto claro.
         */
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN","USER")
                .build();
        UserDetails user = User.withUsername("user")
                .password("{noop}pass")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    /**
     * Create a new InMemoryUserDetailsManager instance using the specified username and password and roles.
     *
     * @param http the http
     * @return security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/editar/**","/agregar/**", "/eliminar", "/guardar").hasRole("ADMIN")
                        .requestMatchers("/").hasAnyRole("USER","ADMIN")
                        /**
                         * TODO anyRequest().authenticated() - Indica que cualquier solicitud entrante debe estar autenticada.
                         */
                        .anyRequest().authenticated()
                )
                .formLogin((formLogin) -> formLogin.loginPage("/login")
                        /*TODO defaultSuccessUrl - redirige al usuario después de que ha iniciado sesión correctamente.
                        El booleano (true) indica que siempre se debe redirigir al usuario a esa URL, incluso si intentó acceder a una página protegida y fue redirigido al formulario de inicio de sesión antes de la autenticación.*/
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )
                /**
                 * TODO ExceptionHandling - Manejo de excepxiones, con la propiedad accessDeniedPage permite configurar una página personalizada a la que se redirigirá al usuario.
                 */
                .exceptionHandling((exception) -> exception.accessDeniedPage("/errors/403"));
        /**
         * TODO http.build() - Se encarga de construir y devolver la instancia final de HTTPSecurity
         */
        return http.build();
    }

}
