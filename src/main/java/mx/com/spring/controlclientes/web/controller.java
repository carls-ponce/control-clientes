package mx.com.spring.controlclientes.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mx.com.spring.controlclientes.domain.Persona;
import mx.com.spring.controlclientes.servicio.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class controller {
    /**
     * TODO @Autowired - Inyección de dependencia, realiza instancia de forma automática gracias al IoC
     * @Autowired para conectar automáticamente los componentes de una aplicación y proporcionar las dependencias necesarias a través del contenedor de inversión de control (IoC).
     */
    @Autowired
    private PersonaService personaService;

    /**
     * TODO @GetMapping Significa que ese método responderá a las solicitudes HTTP GET dirigidas a la ruta raíz (/)
     * Anotación de mapeo en Spring MVC que facilita la creación de controladores web y la asignación de rutas URL a métodos específicos para manejar solicitudes HTTP GET.
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user){
        var personas = personaService.listarPersonas();
        log.info("Ejecutando el controlador Spring MVC");
        log.info("Usuario que inició sesión: " + user);
        model.addAttribute("personas", personas);
        return "index";
    }

    /**
     * TODO Métodos HTTP - @PostMapping, @PutMapping, y @DeleteMapping
     *
     * @PostMapping: Utilizado para mapear solicitudes HTTP POST agregar/crear.
     * @PutMapping: Utilizado para mapear solicitudes HTTP PUT actualizar/editar/modificar
     * @DeleteMapping: Utilizado para mapear solicitudes HTTP DELETE eliminar.
     * Summary:  proporcionan una forma más semántica y específica de definir los métodos que manejan solicitudes HTTP en comparación con la anotación más general @RequestMapping.
     * Al utilizar estas anotaciones, se mejora la legibilidad del código y se hace más evidente el propósito de cada método en el controlador.
     * @param persona
     * @return
     */
    @GetMapping("/agregar")
    public String agregar(Persona persona){
        return "modificar";
    }
    
    @PostMapping("/guardar")
    public String guardar(@Valid Persona persona, Errors errors){
        if(errors.hasErrors()){
            return "modificar";
        }
        personaService.guardar(persona);
        return "redirect:/";
    }

    @GetMapping("/editar/{id_Persona}")
    public String editar(Persona persona, Model model){
        persona = personaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";
    }

    @GetMapping("/eliminar")
    public String eliminar(Persona persona){
    personaService.eliminar(persona);
    return "redirect:/";
    }
}
