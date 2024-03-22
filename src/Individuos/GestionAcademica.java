package Individuos;

import Escuela.Curso;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
/**
 * Interfaz Gestion acádemica: declara los métodos que serán necesarios en las
 * clases Alumno y Profesor.
 *
 * @author vazqu
 * @version 1.0, 22/02/2024
 */
public interface GestionAcademica {
    /**
     * Devuelve un informe de los resultados de una Persona en un Curso
     *
     * @param persona persona sobre la que irá el informe
     * @param curso curso del que se quiere conseguir los resultados
     * @return informe con resultados
     */
    public abstract StringBuilder informeResultados(Persona persona, Curso curso);

}
