package Escuela;

import Individuos.Profesor;
import Individuos.Alumno;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Clase curso: implementa un curso académico con asignaturas, alumnos, y notas.
 * Contiene los métodos para gestionar un curso como: insertar alumnos, insertar
 * profesores, actualizar notas, etc.
 *
 * @author vazqu
 * @version 3.0, 23/03/2024
 */
public class Curso implements Serializable{

    /**
     * Atributos*
     */
    private static final long serialVersionUID = 1L;
    private String nombreCurso;
    private static int contadorCurso = 1;
    private static final int NUM_ASIGNATURAS = 7; //Numero de asignaturas del curso
    private static final String NOMBRE_CURSO = "DAM 1 E-Learning"; //Nombre del curso
    private LocalDate fechaInicial; //Fecha de inicio del curso
    private LocalDate fechaFinal; //Fecha final del curso
    private LinkedList<Expediente> alumnosConNotas = new LinkedList<Expediente>(); //Lista para almacenar las notas y los alumnos
    private Profesor[] profesores = new Profesor[NUM_ASIGNATURAS]; //Array para almacenar los profesores del curso
    private static int numeroProfesores = 0; //contador del numero de profesores

    /**
     * Constructor*
     */
    public Curso() {
        this.nombreCurso = "dam" + contadorCurso++;
    }
    
    /**Getters**/
    /**
     * 
     * @return 
     */
    public String getNombreCurso() {
        return nombreCurso;
    }
    /**
     * Obtiene el numero de asignaturas
     * @return Numero de asignaturas
     */
    public static int getNUM_ASIGNATURAS() {
        return NUM_ASIGNATURAS;
    }
    /**
     * Obtiene el nombre del curso
     * @return Nombre del curso
     */
    public static String getNOMBRE_CURSO() {
        return NOMBRE_CURSO;
    }
    /**
     * Obtiene la fecha de inicio del curso
     * @return Fecha de inicio del curso
     */
    public LocalDate getFechaInicial() {
        return fechaInicial;
    }
     /**
     * Obtiene la fecha de fin de curso
     * @return Fecha final de curso
     */
    public LocalDate getFechaFinal() {
        return fechaFinal;
    }
    /**
     * Obtiene la lista de alumnos y sus notas
     * @return lista de alumnos y notas
     */
    public LinkedList<Expediente> getAlumnosConNotas() {
        return alumnosConNotas;
    }
    /**
     * Obtiene el array de profesores
     * @return Array de profesores
     */
    public Profesor[] getProfesores() {
        return profesores;
    }
    /**
     * Obtiene el número de profesores en el curso
     * @return numero de profesores
     */
    public static int getNumeroProfesores() {
        return numeroProfesores;
    }

    /**Setters**/
    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    /**
     * Establece la fecha inicial del curso
     * @param fechaInicial Fecha de inicio del curso
     */
    public void setFechaInicial(LocalDate fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    /**
     * Establece la fecha final del curso
     * @param fechaFinal Fecha final del curso
     */

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    /**
     * Establece la lista de alumnos y sus notas
     * @param alumnosConNotas Lista de alumnos y notas
     */
    public void setAlumnosConNotas(LinkedList<Expediente> alumnosConNotas) {
        this.alumnosConNotas = alumnosConNotas;
    }
    /**
     * Establece el array de profesores
     * @param profesores Array de profesores
     */
    public void setProfesores(Profesor[] profesores) {
        this.profesores = profesores;
    }
    
    /**Métodos**/
    
   /**
    * Valida que la fecha en la que finaliza el curso sea despues que la fecha
    * Inicial
    * @param fechaFinal fecha de finalización de curso
    * @return true si la fecha es posterior a la fecha inicial o false si es
    * anterior
    */
    public boolean validarFechaFinal(LocalDate fechaFinal) {
        return !fechaFinal.isBefore(fechaInicial);
    }

    /**
     * Busca un alumno en la lista de alumnosConNotas
     * @param id identificador para buscar el alumno
     * @return los datos del alumno si lo ha encontrado, o null si el
     * identificador no coincide con ninguno
     */
    public Expediente buscarAlumno(String id) {
        for (Expediente i : alumnosConNotas) {
            if (i.getAlumno().getIdentificador().equals(id)) {
                return i;
            }
        }
        return null; // No se encontró el alumno
    }

    /**
     * Comprueba que el id introducido se encuentre ya en la lista de
     * alumnosConNotas
     * @param id identificador para buscar el alumno
     * @return true SI YA EXISTE un alumno con el id introducido, false si NO
     * EXISTE un alumno con el id introducido.
     */
    public boolean alumnoExiste(String id) {
        boolean alumnoExiste;
        alumnoExiste = buscarAlumno(id) != null;
        return alumnoExiste;
    }

    /**
     * Inserta un nuevoAlumno en la lista de alumnosConNotas de manera ordenada
     * por su código identificativo.
     * @param nomAlum nombre del alumno
     * @param idAlum codigo identificativo
     * @param dniAlum dni o nie del alumno
     * @param emailAlum email del alumno
     * @param fechaMatr fecha de matriculación del alumno
     * @return true si se ha podido insertar el alumno con existo
     * false si no se ha introducio..
     */
    public boolean insertarAlumno(String nomAlum, String idAlum, String dniAlum, String emailAlum, String fechaMatr) {
        Alumno nuevoAlumno = new Alumno(fechaMatr, dniAlum, idAlum, nomAlum, emailAlum);

        Expediente nuevaNota = new Expediente();
        nuevaNota.setAlumno(nuevoAlumno);
        // Busca la posición donde se debe insertar de forma ordenada por identificador
        int index = 0;
        for (Expediente i : alumnosConNotas) {
            if (nuevoAlumno.compareTo(i.getAlumno()) < 0) {
                break;
            }
            index++;
        }
        // Insertar en la posición encontrada
        alumnosConNotas.add(index, nuevaNota);
        return true;
    }

    /**
     * Metodo que muestra la lista de los alumnos almacenados
     * @return StringBuilder de la lista de ID de alumnos matriculados
     */
    public StringBuilder mostrarAlumnos() {
        StringBuilder listaAlumnos = new StringBuilder();
        listaAlumnos.append("ID de alumnos matriculados:\n");
        for (Expediente i : alumnosConNotas) {
            listaAlumnos.append("- ").append(i.getAlumno().getIdentificador()).append("\n");
        }
        return listaAlumnos;
    }

    /**
     * Busca un profesor en el array de profesores del atributo de la clase
     * curso.
     * @param id identificador para buscar el profesor
     * @return los datos del alumno si lo ha encontrado, o null si el
     * identificador no coincide con ninguno
     */
    public Profesor buscaProfesor(String id) {
        for (int i = 0; i < NUM_ASIGNATURAS; i++) {
            if(profesores[i] == null){
                return null;
            }else if(profesores[i].getIdentificador().equals(id)) {
                return profesores[i];
            }
        }
        return null;
    }

    /**
     * Inserta un profesor en el array de profesores
     * @param fechaAlta
     * @param asignaturaImparte
     * @param dni
     * @param identificador
     * @param nombreCompleto
     * @param email
     * @return 1 si ya existía un profesor para una asignatura y por lo tanto se
     * ha reemplazado con el nuevo; y 0 si se ha introducido correctamente el
     * nuevo profesor.
     */
    public int insertarProfesor(String fechaAlta, Asignaturas asignaturaImparte, String dni, String identificador, String nombreCompleto, String email) {
        Profesor nuevoProfesor = new Profesor(fechaAlta, asignaturaImparte, dni, identificador, nombreCompleto, email);
        Asignaturas asignaturaProfesor = nuevoProfesor.getAsignaturaImparte();
        for (int i = 0; i < numeroProfesores; i++) {
            if (profesores[i].getAsignaturaImparte().equals(asignaturaProfesor)) {
                profesores[i] = nuevoProfesor; //reemplaza el profesor si ya existía uno
                return 1;
            }
        }
        //Establece el nuevo profesor en la última posición el array
        profesores[numeroProfesores] = nuevoProfesor;
        numeroProfesores++;
        return 0;
    }
    /**
     * Muestra los profesores que dan clase en el curso con sus asignaturas
     * @return StringBuilder de los profesores y las asignaturas que imparten
     */
    public StringBuilder mostrarProfesores(){
        StringBuilder listaProfesores = new StringBuilder();
        listaProfesores.append("ID de profesores:\n");
        for (int i = 0; i <numeroProfesores; i++){
            if (profesores[i] == null){
                listaProfesores.append("- Profesor no asignado");
            }
            listaProfesores.append("- ").append(profesores[i].getIdentificador()).append(" - ").append(profesores[i].getAsignaturaImparte());
        }
        return listaProfesores;
    }

    /**
     * Establece una nota de una asignatura
     * @param idAlumno codigo identificativo del alumno
     * @param codigoAsignatura codigo de la asignatura
     * @param nota nota que se desea establecer
     * @return false si está fuera del rango 0-10. true si se establece correctamente
     */
    public boolean setNota(String idAlumno, String codigoAsignatura, int nota) {
        if (nota < 0 || nota > 10) {
            return false; //la nota está fuera del rango permitido
        } else {
            Expediente expedienteAlumno = buscarAlumno(idAlumno);//busca el alumno en la lista
            if (expedienteAlumno != null) {
                Asignaturas asignatura = Asignaturas.obtenerAsignaturaPorCodigo(codigoAsignatura);
                if (asignatura != null) {
                    expedienteAlumno.getNotas().put(asignatura, nota); //se establece la nota en el mapa
                    return true; //
                } 
            }
        }
        return false; //No se pudo establecer la nota
    }

    /**
     * Obtiene la nota de un alumno en una asignatura
     * @param idAlumno codigo identificativo del alumno
     * @param codigoAsignatura codigo de la asignatura
     * @return -1 si no encuentra el alumno, -2 si el código de la asignatura es
     * incorrecto y devuelve la nota si todo es correcto
     */
    public int getNota(String idAlumno, String codigoAsignatura) {
        Expediente expedienteAlumno = buscarAlumno(idAlumno);
        if (expedienteAlumno == null) {
            return -1; //el alumno no se encuentra en la lista
        }
        Asignaturas asignatura = Asignaturas.obtenerAsignaturaPorCodigo(codigoAsignatura);
        if (asignatura == null) {
            return -2; //el codigo de la asignatura no existe
        }
        int nota = expedienteAlumno.getNotas().get(asignatura);
        return nota; //devuelve la nota encontrada en el mapa
    }

    /**
     * Devuelve un informe general con los datos del curso
     * @param curso curso del que se quiere realizar el informe general
     * @return Informe con datos como las notas de los alumnos, las medias, las
     * medias del curso y de las asignaturas.
     */
    public StringBuilder InformeGeneral(Curso curso) {
        StringBuilder informeGeneral = new StringBuilder();

        //Obtiene el año de inicio y de final del curso 
        int anioInicio = curso.getFechaInicial().getYear();
        int anioFin = curso.getFechaFinal().getYear();
        informeGeneral.append("\n\n");
        String titulo = "INFORME GENERAL DEL CURSO " + anioInicio + "/" + anioFin + "\n";
        informeGeneral.append(titulo);

        //Obtiene el nombre del curso
        informeGeneral.append(Curso.getNOMBRE_CURSO()).append("\n\n");

        //Columnas con los nombres de las asignaturas
        informeGeneral.append("ID alumno    | Nombre                                   | ");
        for (Asignaturas asignatura : Asignaturas.values()) {
            informeGeneral.append(asignatura.getCodigo()).append("   ");
        }
        informeGeneral.append("| Media\n");
        informeGeneral.append("-----------------------------------------------------------------------------------------------------------------------\n");

        //Información de cada alumno
        Iterator<Expediente> iteradorAlumnos = curso.getAlumnosConNotas().iterator();
        while (iteradorAlumnos.hasNext()) {
            Expediente notasAlumno = iteradorAlumnos.next();
            Alumno alumno = notasAlumno.getAlumno();
            informeGeneral.append(alumno.getIdentificador()).append(" | ");
            informeGeneral.append(String.format("%-40s", alumno.getNombreCompleto())).append(" | ");

            //Notas de cada asignatura
            double sumaNotasAlumno = 0;
            for (Asignaturas asignatura : Asignaturas.values()) {
                Integer notaAsignatura = notasAlumno.getNotas().get(asignatura);
                if (notaAsignatura != null) {
                    informeGeneral.append(String.format("%2d ", notaAsignatura)).append("    ");;
                    sumaNotasAlumno += notaAsignatura;
                } else {
                    informeGeneral.append("    "); // Espacio en blanco si no hay nota
                }
            }

            //Nota media del alumno
            double notaMediaAlumno = sumaNotasAlumno / Curso.getNUM_ASIGNATURAS();
            informeGeneral.append("| ").append(String.format("%.2f", notaMediaAlumno)).append("\n");
        }
        //Nota media de cada asignatura
        informeGeneral.append("\nTotales:\n");
        informeGeneral.append("Nota media de cada asignatura:                          ");
        for (Asignaturas i : Asignaturas.values()) {
            double sumaNotasAsignatura = 0;
            for (Expediente j : curso.getAlumnosConNotas()) {
                int nota = j.getNotas().get(i);
                sumaNotasAsignatura += nota;
            }
            double notaMediaAsignatura = sumaNotasAsignatura / curso.getAlumnosConNotas().size();
            informeGeneral.append(String.format("%7.2f", notaMediaAsignatura)).append(" ");
        }
        //Nota media del curso
        informeGeneral.append("\nNota media del curso:                                     ");
        double sumaNotasCurso = 0;
        for (Expediente i : curso.getAlumnosConNotas()) {
            double sumaNotasAlumno = 0;
            for (Asignaturas asignatura : Asignaturas.values()) {
                Integer nota = i.getNotas().get(asignatura);
                sumaNotasAlumno += nota;
            }
            sumaNotasCurso += sumaNotasAlumno / Curso.getNUM_ASIGNATURAS();
        }
        double notaMediaCurso = sumaNotasCurso / curso.getAlumnosConNotas().size();
        informeGeneral.append(String.format("%.2f", notaMediaCurso));
        informeGeneral.append("\n\n");

        return informeGeneral;
    }
    
    @Override
    public String toString(){
      return this.InformeGeneral(this).toString();
    }
    /**
     * Elimina un alumno por su codigo de indentificacion
     * @param id codigo de identificacion
     * @return true si se ha eliminado correctamente, false si no se ha encontrado.
     */
    public boolean eliminarAlumno(String id) {
        Iterator<Expediente> iterator = alumnosConNotas.iterator();
        while (iterator.hasNext()) {
            Expediente i = iterator.next();
            if (i.getAlumno().getIdentificador().equals(id)) {
                iterator.remove(); //Elimina el estudiante de la lista
                return true;
            }
        }
        return false; //si el estudiante no se encontró en la lista
    }
    /**
     * Guarda un archivo .dat del objeto curso creado
     * @param nombreArchivo nombre que tendrá el archivo, generalmente "damX.dat"
     * @throws IOException 
     */
    public void guardarEstado(String nombreArchivo) throws IOException {
        nombreArchivo = nombreArchivo + ".dat";
        ObjectOutputStream salidaDatos = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
        salidaDatos.writeObject(this);
        salidaDatos.close();
    }
    /**
     * @param nombreArchivo
     * @return
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Curso cargarEstado(String nombreArchivo) throws IOException, ClassNotFoundException{
        nombreArchivo = nombreArchivo + ".dat";
        ObjectInputStream entradaDatos = new ObjectInputStream(new FileInputStream(nombreArchivo));
        Curso cursoLeido = (Curso) (entradaDatos.readObject());
        return cursoLeido;
    }
    
}
