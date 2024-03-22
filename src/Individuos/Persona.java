package Individuos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *Superclase persona: implementa los atributos comunes y métodos a las personas que intervienen en un curso académico
 * @author vazqu
 * @version 1.0, 22/02/2024
 */
public class Persona {

    /**Atributos**/
    protected String dni; //documento único identificador puede ser tambien un NIE
    protected String identificador; //código identificador
    protected String nombreCompleto; //nombre y apellidos completo (no podrá almacenar más de 40 caracteres)
    protected String email; //correo electrónico

    /*Constructores*/
    /**
     * Metodo constructor sin parámetros
     */
    public Persona() {
    }

    /**
     * Método constructor con parámetros
     *
     * @param dni atributo dni
     * @param identificador atributo identificador
     * @param nombreCompleto atributo nombreCompleto
     * @param email atributo email
     */
    public Persona(String dni, String identificador, String nombreCompleto, String email) {
        this.dni = dni;
        this.identificador = identificador;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
    }

    /**Getters**/
    /**
     * Obtiene el DNI o NIE de la persona
     * @return String con el DNI o NIE
     */
    public String getDni() {
        return dni;
    }

    /**
     * Obtiene el código identificador
     * @return String con el código identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * Obtiene el nombre completo
     * @return String con el nombre almacenado
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Obtiene el correo electrónico
     * @return String con el email
     */
    public String getEmail() {
        return email;
    }

    /**Setters**/
    /**
     * Establece el DNI o NIE
     * @param dni String dni a establecer
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Establece el código identificador
     * @param id String código id
     */
    public void setIdentificador(String id) {
        this.identificador = id;
    }

    /**
     * Establece el nombre completo de la persona
     * @param nombreCompleto String con el nombre completo
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Establece el correo electrónico
     * @param email String con el correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
