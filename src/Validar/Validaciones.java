package Validar;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *Clase validaciones, implementa métodos para validar la entrade de algunos datos
 * @author vazqu
 */
public class Validaciones {
    /**
     * Valida el formato del DNI o NIE
     * @param dni String del dni o nie a validar
     * @return true si sigue el patrón necesario, false si no lo sigue.
     */
    public static boolean validarDNI(String dni) {
        String dniPattern = "^[XYxy]?[0-9]{1,9}[A-Za-z]$";
        Pattern pattern = Pattern.compile(dniPattern);
        Matcher matcher = pattern.matcher(dni);
        return matcher.matches();
    }
    /**
     * Valida que el Email tenga un formato correcto
     * @param email String de correo electrónico a validar
     * @return true si se corresponde con el patrón, o false si no se corresponde.
     */
    public static boolean validarEMAIL(String email) {
        String emailPattern = "^[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,5}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    /**
     * Valida que el formato de fecha introducido sea dd-mm-aaaa. Rango de años 2000- 2099.
     * @param fecha String de fecha a evaluar
     * @return true si se corresponde con el patrón, false si no se corresponde.
     */
    public static boolean validarFormatoFecha(String fecha) {
        Pattern patron = Pattern.compile("(0[1-9]|[1-2][0-9]|3[01])-(0[1-9]|1[0-2])-20([0-9][0-9])");
        Matcher match = patron.matcher(fecha);
        return match.matches();
    }
    /**
     * Valida que el nombre no se haya dejado el blanco o que no supere los 40 caracteres.
     * Si los supera solo guarda los 40 primeros.
     * @param nombreCompleto String de nombre completo
     * @return false si está en blanco, true si no está en blanco aunque supere los 40 caracteres
     */
    public static int validarNombre(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            System.out.println("Ha dejado el campo en blanco");
            return -2; //nombre en blanco
        } else if (nombreCompleto.length() >= 40) {
            System.out.println("Ha introducido m\u00e1s de 40 caracteres. Se almacenar\u00e1n los 40 primeros caracteres");
            return -1; //más de 40 caracteres
        } else {
            return 0; //validado con exito
        }
    }
    /**
     * Permite formatear una fecha en formato String (dd-mm-aaaa) a formato LocalDate (aaaa-mm-dd)
     * @param fecha fecha en formato dd-mm-aaaa
     * @return LocalDate fecha en formato: aaaa-mm-dd
     */
    public static LocalDate formatearFecha(String fecha) {
        String dia = fecha.substring(0, 2);
        String mes = fecha.substring(3, 5);
        String anio = fecha.substring(6, 10);
        // Construir la nueva fecha con el formato deseado
        String fechaFormateada = anio + "-" + mes + "-" + dia;
        LocalDate fechaFormatoLocalDate = LocalDate.parse(fechaFormateada);

        return fechaFormatoLocalDate;
    }
}
