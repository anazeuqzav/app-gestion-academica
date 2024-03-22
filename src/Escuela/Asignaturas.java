package Escuela;

import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vazqu
 */
public enum Asignaturas implements Serializable {
    BD("Bases de datos", "BD", 4),
    ED("Entornos de desarrollo", "ED", 3),
    FOL("Formación y orientación laboral", "FOL", 2),
    ING("Inglés técnico", "ING", 3),
    LMSGI("Lenguajes de marcas", "LMSGI", 3),
    PROG("Programación", "PROG", 5),
    SI("Sistemas informáticos", "SI", 4);

    private static final long serialVersionUID = 1L;
    private final String descripcion;
    private final String codigo;
    private final int creditos;

    private Asignaturas(String descripcion, String codigo, int creditos) {
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.creditos = creditos;
    }
 
    public String getDescripcion()
{
        return descripcion;
    }
 
    public String getCodigo() {
        return codigo;
    }
 
    public int getCreditos() {
        return creditos;
    }
 
    public static String obtenerDescripcionAsignatura(String codigo) {
        for (Asignaturas asignatura : values()) {
            if (asignatura.getCodigo().equals(codigo)) {
                return asignatura.getDescripcion();
            }
        }
        return "Código no encontrado";
    }

    // Método para obtener el código a partir de la posición en la clase
    public static String obtenerCodigoPorPosicion(int posicion) {
        if (posicion >= 0 && posicion < values().length) {
            return values()[posicion].getCodigo();
        } else {
            return null;
        }
    }
    
    public static boolean validarAsignatura(String codigo) {
        for (Asignaturas asignatura : Asignaturas.values()) {
            if (asignatura.getCodigo().equals(codigo)) {
                return true;
            }
        }
        System.out.println("Clave de asignatura no válida. Introduce una clave válida.");
        return false;
    }
    //Método para obtener la asignatura segun el código
    public static Asignaturas obtenerAsignaturaPorCodigo(String codigo) {
        for (Asignaturas asignatura : Asignaturas.values()) {
            if (asignatura.getCodigo().equals(codigo)) {
                return asignatura;
            }
        }
        return null; // Si no se encuentra la asignatura con el código especificado
    }

    public static void mostrarAsignaturas() {
        for (Asignaturas asignatura : Asignaturas.values()) {
            System.out.println("- " + asignatura.getCodigo());

        }

    }
}
