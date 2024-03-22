package Individuos;

import Escuela.Asignaturas;
import Escuela.Expediente;
import Escuela.Curso;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *Clase Profesor: se trata de una extensión de Persona e implementa Gestion Académica
 * @author vazqu
 * @version 1.0 22/02/2024
 */
public class Profesor extends Persona implements GestionAcademica, Serializable{

    /**Atributos**/
    private static final long serialVersionUID = 1L;
    private String fechaAlta; //Fecha en la que se da de alta un profesor en un curso
    private Asignaturas asignaturaImparte; //Asignatura que imparte, solo puede impartir una asignatura

    /**Constructores**/
    /**
     * Constructor sin parametros
     */
    public Profesor() {
    }
    /**
     * Constructor con parametros de la propia clase hija
     * @param fechaAlta Fecha en la que se da de alta un profesor en un curso
     * @param asignaturaImparte Asignatura que imparte, solo puede impartir una asignatura
     */
    public Profesor(String fechaAlta, Asignaturas asignaturaImparte) {
        this.fechaAlta = fechaAlta;
        this.asignaturaImparte = asignaturaImparte;
    }
    /**
     * Constructor con atributos heredados y sus propios atributos
     * @param fechaAlta Fecha en la que se da de alta un profesor en un curso
     * @param asignaturaImparte Asignatura que imparte, solo puede impartir una asignatura
     * @param dni Documento de identidad DNI o NIE
     * @param identificador Código único identificador del profesor
     * @param nombreCompleto Nombre y apellidos completo
     * @param email Correo electrónico del profesor
     */
    public Profesor(String fechaAlta, Asignaturas asignaturaImparte, String dni, String identificador, String nombreCompleto, String email) {
        super(dni, identificador, nombreCompleto, email);
        this.fechaAlta = fechaAlta;
        this.asignaturaImparte = asignaturaImparte;
    }

    /**Getters**/
    /**
     * Obtiene la fecha de alta del profesor
     * @return String Fecha de alta
     */
    public String getFechaAlta() {
        return fechaAlta;
    }
    /**
     * Obtiene la asignatura que imparte 
     * @return Asignatura la asignatura que imparte
     */
    public Asignaturas getAsignaturaImparte() {
        return asignaturaImparte;
    }

    /**Setteres**/
    /**
     * Establece la fecha de alta del profesor
     * @param fechaAlta fecha en la que se dió de alta, no podrá ser posterior a la finalización del curso
     */
    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    /**
     * Establece la asignatura que imparte
     * @param asignaturaImparte asignatura que imparte, debe ser una del enumerado de Asignaturas
     */
    public void setAsignaturaImparte(Asignaturas asignaturaImparte) {
        this.asignaturaImparte = asignaturaImparte;
    }

    /**Metodos**/
    /**
     * Metodo para validar que el código identificador de un profesor sigua el patrón establecido
     * @param id Codigo identificador a validar
     * @return true si coincide con el patrón; false si no coincide.
     */
    public static boolean validarIdentificador(String id) {
        Pattern patron = Pattern.compile("^(590|591|592)([0-9]{5})(INF|ING|FOL|SIA)$");
        Matcher match = patron.matcher(id);
        return match.matches();
    }

    /**
     * 
     * Metodo para obtener un informe de resultados de un alumno.     
     * @param persona Objeto persona para el que realizar el informe
     * @param curso Curso del que se quiere realizar el informe
     * @return Stringbuilder con un informe sobre los resultados en las diferentes asignaturas
     */
    @Override
    public StringBuilder informeResultados(Persona persona, Curso curso) {

        StringBuilder informe = new StringBuilder();
        Profesor profesor = (Profesor) persona;

        //Informe de datos identificativos del profesor:
        informe.append("\n---------------------------\n");
        informe.append("INFORME DE LA ASIGNATURA\n");
        informe.append("---------------------------\n");
        informe.append("ID del profesor: ").append(profesor.getIdentificador()).append("\n");
        informe.append("Nombre del profesor: ").append(profesor.getNombreCompleto()).append("\n");

        //Asignatura que imparte
        Asignaturas asignaturaImparte = profesor.getAsignaturaImparte();
        informe.append("ID de la asignatura: ").append(asignaturaImparte.getCodigo()).append("\n");
        informe.append("Nombre de la asignatura: ").append(asignaturaImparte.getDescripcion()).append("\n");

        //Datos de la asignatura
        //Se recorre el array de notas para guardar en una variable los alumnos aprobados, los suspensos y el total de las notas
        int numTotalAlumnos = curso.getAlumnosConNotas().size();
        if(numTotalAlumnos == 0){
            informe.append("No hay alumnos matriculados en esta asignatura. No se pueden mostrar estadísticas.");
        } else {
            int aprobados = 0;
            int suspensos = 0;
            int sumaNotas = 0;
            int notaMinima = 0;
            int notaMaxima = 0;

            for (Expediente i : curso.getAlumnosConNotas()) {
                // Obtener la nota del alumno en la asignatura que imparte el profesor
                int nota = i.getNotas().get(asignaturaImparte);
                sumaNotas += nota;

                // Actualizar estadísticas de aprobados, suspensos, nota mínima y nota máxima
                if (nota >= 5) {
                    aprobados++;
                } else {
                    suspensos++;
                }
                notaMinima = Math.min(notaMinima, nota);
                notaMaxima = Math.max(notaMaxima, nota);
            }

            // Calculo de porcentajes de aprobados y suspensos y nota media
            double porcentajeAprobados = (double) aprobados / numTotalAlumnos * 100;
            double porcentajeSuspensos = (double) suspensos / numTotalAlumnos * 100;
            double notaMedia = sumaNotas / numTotalAlumnos;

            // Mostrar estadísticas
            informe.append("\nNúmero de alumnos aprobados: ").append(aprobados).append(" (").append(String.format("%.2f", porcentajeAprobados)).append("%)");
            informe.append("\nNúmero de alumnos suspensos: ").append(suspensos).append(" (").append(String.format("%.2f", porcentajeSuspensos)).append("%)");
            informe.append("\nNota media de la asignatura: ").append(String.format("%.2f", notaMedia));
            informe.append("\nNota mínima: ").append(notaMinima);
            informe.append("\nNota máxima: ").append(notaMaxima);

        }
        return informe;
    }
}
