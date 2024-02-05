package mx.com.spring.controlclientes.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
@Configuration
/**
 * Clase utilizada para el manejo de idiomas en Spring Boot y registro de redireccionamiento.
 * TODO - WebMvcConfiguration
 */
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver(){
        /**
         * Clases del api de spring para el manejo de idiomas.
         * TODO SessionLocaleResolver - Su función principal es resolver y establecer la configuración regional
         * Esta implementación almacena la configuración regional en la sesión del usuario, lo que permite a la aplicación recordar y aplicar la configuración regional
         * preferida del usuario en sesiones futuras.
         * Prefijo - Elementos de Internaciolización (es, en)
          */
    var slr = new SessionLocaleResolver();
    slr.setDefaultLocale(Locale.forLanguageTag("es-ES"));
    return slr;
    }

    /**
     * Interceptor para cambiar el idioma de forma dinámica.
     * TODO - LocaleChangeInterceptor para permitir que los usuarios cambien su configuración regional mediante un parámetro en la URL ("lang").
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
    var lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
    }

    /**
     * TODO addInterceptors - método definido en la interfaz WebMvcConfigurer permite agregar interceptores personalizados a la aplicación.
     * Al configurar interceptores, puedes realizar tareas como la manipulación de la configuración regional (Locale), la autorización, el registro de tiempos de ejecución, entre otras cosas.
     * Regristro del interceptor.
     * TODO InterceptorRegistry - proporciona métodos para registrar y configurar interceptores en Spring MVC.
     * utilizada por addInterceptors para agregar interceptores a la configuración global de la aplicación.
     * @param registro
     */
    @Override
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor());
    }

    /**
     * TODO addViewControllers - Método de la interfaz WebMvcConfigurer, agregar asignaciones directas de URL a vistas sin la necesidad de un controlador.
     * simplificar la configuración cuando solo se necesita asignar una URL a una vista sin procesamiento adicional.
     * TODO ViewControllerRegistry - objeto que se proporciona para facilitar la configuración de asignaciones directas de URL a vistas.
     * TODO addViewController - configura una asignación directa de la URL "/" a la vista llamada "index".
     * Esto significa que cuando se accede a "/", Spring mostrará automáticamente la vista "index" sin necesidad de un controlador adicional.
     * TODO setViewName - establece el nombre de la vista que se asociará con una ruta URL específica.
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login");
        registry.addViewController("/errors/403").setViewName("/errors/403");
    }
}
