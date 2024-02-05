package mx.com.spring.controlclientes.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryption {
    public static void main(String[] args) {

        String password = "123";
        System.out.println("Password -->" + password + "\n" + "Contraseña encriptada -->" + passwordEncryption(password));

    }
    public static String passwordEncryption(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
