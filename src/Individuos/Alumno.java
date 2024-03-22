package Individuos;

import Escuela.Asignaturas;
import Escuela.Expediente;
import Escuela.Curso;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Clase Alumno: se trata de una extensión de Persona e implementa Gestion Académica
 * y la interfaz comparable
 *
 * @author vazqu
 * @version 2.0, 13/03/2024
 */
public class Alumno extends Persona implements GestionAcademica, Comparable<Alumno> {

    /**Atributos**/
    private String fechaMatriculacion; //String de la fecha de matriculación del alumno

    /**Constructores**/
    /**
     * Constructor sin parametros
     */
    public Alumno() {
    }
    public Alumno(Alumno alumno){
        
    }
    /**
     * Constructor solo con parametros de la propia clase
     * @param fechaMatriculacion 
     */
    public Alumno(String fechaMatriculacion) {
        this.fechaMatriculacion = fechaMatriculacion;
    }
    /**
     * Constructor con parámetros heredados de la superclase
     * @param fechaMatriculacion String Fecha en la que se matricula el alumno
     * @param dni Superclase DNI o NIE del alumno
     * @param identificador Superclase Codigo identificador del alumno
     * @param nombreCompleto Superclase Nombre completo del alumno
     * @param email Superlcase Correo electrónico del alumno
     */
    public Alumno(String fechaMatriculacion, String dni, String identificador, String nombreCompleto, String email) {
        super(dni, identificador, nombreCompleto, email);
        this.fechaMatriculacion = fechaMatriculacion;
    }

    /**Getters**/
    /**
     * Obtiene la fecha de matriculación de un alumno
     * @return String de la fecha de matriculacion
     */
    public String getFechaMatriculacion() {
        return fechaMatriculacion;
    }

    /**Setters**/
    /**
     * Establece la fecha de matriculación de un alumno
     * @param fechaMatriculacion la fecha de matriculacion a establecer
     */
    public void setFechaMatriculacion(String fechaMatriculacion) {
        this.fechaMatriculacion = fechaMatriculacion;
    }
    
    /**
     * Metodo para validar que el código identificador de un alumno sigua el patrón establecido
     * @param id Codigo identificador a validar
     * @return true si coincide con el patrón; false si no coincide.
     */
    public static boolean validarIdentificador(String id) {
        Pattern patron = Pattern.compile("^NIES\\d{6}(AB|CR|CU|GU|TO|ES|XX)$");
        Matcher match = patron.matcher(id);
        return match.matches();
    }

    /**
     * Metodo para obtener un informe de resultados de un alumno.
     * @param curso Curso del que se quiere realizar el informe
     * @return Stringbuilder con un informe sobre los resultados en las
     * diferentes asignaturas
     */
    @Override
    public StringBuilder informeResultados(Persona persona, Curso curso) {
        StringBuilder informe = new StringBuilder();
        Alumno alumno = (Alumno) persona; //casting de objetos

        //Informe de datos identificativos del alumno
        informe.append("\n-----------------------\n");
        informe.append("INFORME DEL ALUMNO\n");
        informe.append("-----------------------\n");
        informe.append("ID: ").append(alumno.getIdentificador()).append("\n");
        informe.append("DNI/NIE: ").append(alumno.getDni()).append("\n");
        informe.append("Nombre y apellidos: ").append(alumno.getNombreCompleto()).append("\n");
        informe.append("Correo electrónico: ").append(alumno.getEmail()).append("\n");

        //Informe de notas del alumno
        informe.append("Notas: \n");

        //En primer lugar, encontramos la posición del alumno en el índice del array del curso
        Expediente notasAlumno = null;
        for (Expediente i : curso.getAlumnosConNotas()) {
            if (i.getAlumno().compareTo(alumno) == 0) {
                notasAlumno = i;
            }
        }
        //Por cada asignatura se añade al informe la descripción de la asignatura y la nota del alumno
        for (Asignaturas i : Asignaturas.values()) {
            int nota = notasAlumno.getNotas().get(i);
            informe.append(i.getDescripcion()).append(": ").append(nota).append("\n");
        }
        //Resumen de las notas
        //Se recorre el array de notas sumando las aprobadas a una variable y las suspensas a otra
        int aprobadas = 0;
        int suspensos = 0;
        int sumaNotas = 0;
        for (int i : notasAlumno.getNotas().values()) {
            sumaNotas += i;
            if (i >= 5) {
                aprobadas++;
            } else {
                suspensos++;
            }
        }
        //calculo de la nota media y del porcentaje de asignaturas aprobadas
        double notaMedia = sumaNotas / Curso.getNUM_ASIGNATURAS();
        double porcentajeAprobadas = (double) aprobadas / Curso.getNUM_ASIGNATURAS() * 100;

        //Formato para el resumen
        informe.append("RESUMEN:\n");
        informe.append("Numero de asignaturas aprobadas: ").append(aprobadas).append("\n");
        informe.append("Numero de asignaturas suspensas: ").append(suspensos).append("\n");
        informe.append("Nota media: ").append(String.format("%.2f", notaMedia)).append("\n");
        informe.append("% de asignaturas aprobadas: ").append(String.format("%.2f", porcentajeAprobadas)).append("\n");

        return informe;
    }
    /**
     * Método que compara los alumnos por su identificador
     * @param o objeto alumno
     * @return un entero negativo, cero o un entero positivo según este objeto 
     * sea menor, igual o mayor que el objeto especificado.
     */
    @Override
    public int compareTo(Alumno o) {
        return identificador.compareTo(o.identificador);
    }
}
