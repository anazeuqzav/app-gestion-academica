/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Escuela;

import Individuos.Alumno;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Clase que implementa un expediente de un alumno con sus notas
 *
 * @author vazqu
 * @version 1.0 13/03/2024
 */
public class Expediente implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<Asignaturas, Integer> notas; //Mapa donde se almacenan las notas de cada asignatura
    private Alumno alumno; //Objeto alumno

    /**
     * Constructor
     */
    public Expediente() {
        this.notas = new HashMap<>();
        this.alumno= new Alumno();
    }
    /**
     * Obtiene las notas de un alumno
     * @return hashmap de notas
     */

    public HashMap<Asignaturas, Integer> getNotas() {
        return notas;
    }
    /**
     * obtiene un alumno
     * @return 
     */
    public Alumno getAlumno() {
        return alumno;
    }
    /**
     * Establece las notas de un alumno en cada asignatura
     * @param notas de cada asignatura
     */
    public void setNotas(HashMap<Asignaturas, Integer> notas) {
        this.notas = notas;
    }
    /**
     * Establece un nuevo alumno
     * @param alumno alumno
     */
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    
    
   
    
    

    
    
    
    


    
    
}
