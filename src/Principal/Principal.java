package Principal;

import Escuela.Asignaturas;
import static Escuela.Asignaturas.mostrarAsignaturas;
import static Escuela.Asignaturas.validarAsignatura;
import Escuela.Expediente;
import Escuela.Curso;
import Individuos.Alumno;
import Individuos.Profesor;
import Validar.Validaciones;
import static Validar.Validaciones.validarDNI;
import static Validar.Validaciones.validarEMAIL;
import static Validar.Validaciones.validarFormatoFecha;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 * Clase principal que muestra un menú para la creacion de un curso acádemico. 
 * Permite gestionarlo con sus diferentes opciones:
 * 1. Crear un curso
 * 2. Añadir un profesor
 * 3. Añadir un estudiante
 * 4. Actualizar nota
 * 5. Informe de asignatura
 * 6. Informe de alumno
 * 7. Informe general del curso
 * 8. Eliminar un alumno
 * 9. Salir
 *
 * @author vazqu
 * @version 2.0 13/03/2024
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in); //escaner para recoger datos por teclado
        int opcion; //opción del menu
        boolean validacion = false; //variable para controlar validaciones
        boolean excepcion = false; //variable para controlar escepciones
        Curso nuevoCurso = null; //inicialización a null de curso

        do {
            //Muestra el menú
            System.out.println("""
                               Elige una opcion: 
                               1. Crear curso 
                               2. Nuevo Profesor
                               3. Nuevo Estudiante 
                               4. Actualizar nota 
                               5. Obtener el informe de la asignatura 
                               6. Obtener informe de estudiante 
                               7. Obtener informe general 
                               8. Eliminar alumno
                               9. Salir""");
            opcion = teclado.nextInt();

            switch (opcion) {
                //Opcion 1. Crear un curso
                case 1:
                    teclado.nextLine();
                    //Se inicializa el curso
                    nuevoCurso = new Curso();
                    //Pide la fecha de inicio
                    do {
                        try {
                            System.out.println("Introduce la fecha de inicio del curso (formato aaaa-mm-dd)");
                            nuevoCurso.setFechaInicial(LocalDate.parse(teclado.nextLine()));
                            excepcion = true;
                        } catch (DateTimeParseException | NumberFormatException e) {
                            System.out.println("El formato no es correcto");
                        } catch (Exception e) {
                            System.out.println("Se produjo un error");
                        }
                    } while (!excepcion);
                    //Pide la fecha final de curso
                    excepcion = false;
                    LocalDate fechaFinal = null;
                    do {
                        try {
                            System.out.println("Introduce la fecha final del curso (formato aaaa-mm-dd)");
                            String fechaFinalStr = teclado.nextLine();
                            fechaFinal = LocalDate.parse(fechaFinalStr);
                            if (nuevoCurso.validarFechaFinal(fechaFinal)) {
                                nuevoCurso.setFechaFinal(fechaFinal);
                                excepcion = true;
                                break;
                            } else {
                                System.out.println("La fecha no puede ser anterior a la fecha de inicio del curso. Por favor, vuelva a introducirla.");
                            }
                        } catch (DateTimeParseException | NumberFormatException e) {
                            System.out.println("El formato de fecha no es correcto");
                        }
                    } while (!excepcion);
                    break;

                //Opcion 2. Añadir un profesor
                case 2:
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else {
                        teclado.nextLine();
                        //Petición del identificador
                        String idProfesor;
                        do {
                            System.out.println("Identificador del profesor:");
                            idProfesor = teclado.nextLine();
                            //Si se pulsa X o x se sale de nuevo al menú
                            if ("X".equalsIgnoreCase(idProfesor)) {
                                System.out.println("Operación cancelada.");
                                break; //
                            }
                            //validacion del identificador
                            if (!Profesor.validarIdentificador(idProfesor)) {
                                System.out.println("Identificador inválido. Inténtelo de nuevo.");
                            }
                        } while (!Profesor.validarIdentificador(idProfesor));

                        //Petición del nombre completo
                        String nombreProfesor;
                        int validarNombre;
                        do {
                            System.out.println("Nombre completo del profesor: ");
                            nombreProfesor = teclado.nextLine();
                            validarNombre = Validaciones.validarNombre(nombreProfesor);
                            if (validarNombre == -1){
                                nombreProfesor = nombreProfesor.substring(0, 40);
                            }else if (validarNombre == -2){
                                System.out.println("Ha dejado el campo el blanco.");
                        }
                        } while (validarNombre == -2);
                       
                        //Petición fecha de alta
                        String fechaAlta;
                        boolean validarFecha = false;
                        do {
                            System.out.println("Fecha de alta del profesor(formato dd-mm-aaaa): ");
                            fechaAlta = teclado.nextLine();
                            //Comprueba que este en el formato correcto
                            if (validarFormatoFecha(fechaAlta)) {
                                //Cambio a objeto de tipo LocalDate
                                LocalDate fechaAltaFormatoLocalDate = Validaciones.formatearFecha(fechaAlta);
                                //Comprobacion de que la fecha es anterior a la fecha de finalización del curso
                                if (fechaAltaFormatoLocalDate.isBefore(nuevoCurso.getFechaFinal())) {
                                    validarFecha = true;
                                } else {
                                    System.out.println("La fecha debe ser anterior a la finalización del curso");
                                }
                            } else {
                                System.out.println("El formato de fecha introducido es incorrecto");
                            }
                        } while (!validarFecha);

                        //Petición dni
                        String dniProfesor;
                        do {
                            System.out.println("DNI o NIE del profesor: ");
                            dniProfesor = teclado.nextLine();

                            if ("X".equalsIgnoreCase(dniProfesor)) {
                                System.out.println("Operación cancelada.");
                                break; //
                            }
                            validacion = validarDNI(dniProfesor);
                            if (!validacion) {
                                System.out.println("Identificador inválido. Inténtelo de nuevo.");
                            }
                        } while (!validacion);

                        //Peticion correo electrónico
                        String emailProfesor;
                        do {
                            System.out.println("Correo electrónico del profesor: ");
                            emailProfesor = teclado.nextLine();
                        } while (!validarEMAIL(emailProfesor));

                        //Peticion de la asignatura que imparte
                        String codigoAsignatura;
                        do {
                            System.out.println("Introduce la clave de la asignatura que imparte el profesor: ");
                            mostrarAsignaturas();
                            codigoAsignatura = teclado.nextLine();
                        } while (!validarAsignatura(codigoAsignatura));
                        Asignaturas asignaturaImpartida = Asignaturas.valueOf(codigoAsignatura);

                        //Añade el nuevo profesor al curso
                        if (nuevoCurso.insertarProfesor(fechaAlta, asignaturaImpartida, dniProfesor, idProfesor, nombreProfesor, emailProfesor) == 1) {
                            System.out.println("Se ha reemplazado el profesor de la asignatura" + asignaturaImpartida);
                        } else {
                            System.out.println("Se ha añadido el profesor");
                        }
                    }
                    break;

                //Opcion 3. Crear nuevo estudiante
                case 3:
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else {
                        teclado.nextLine();

                        //Peticion de identificador
                        String idAlumno;
                        do {
                            System.out.println("Identificador del alumno:");
                            idAlumno = teclado.nextLine();
                            if ("X".equalsIgnoreCase(idAlumno)) {
                                System.out.println("Operación cancelada.");
                                break; //Si se pulsa X o x se sale de nuevo al menú
                            } else if (!Alumno.validarIdentificador(idAlumno)) {
                                System.out.println("Identificador inválido. Inténtelo de nuevo.");
                            } else if (nuevoCurso.alumnoExiste(idAlumno)) {
                                System.out.println("El ID ya existe"); //si el alumno ya existe
                            }
                        } while (!Alumno.validarIdentificador(idAlumno) || nuevoCurso.alumnoExiste(idAlumno));
                    
                        //Petición de nombre completo
                        String nombreAlumno;
                        do {
                            System.out.println("Nombre completo del alumno: ");
                            nombreAlumno = teclado.nextLine();
                            if (Validaciones.validarNombre(nombreAlumno) == -1) {
                                nombreAlumno = nombreAlumno.substring(0, 40);
                            } else if (Validaciones.validarNombre(nombreAlumno) == -2) {
                                System.out.println("Ha dejado el campo el blanco.");
                            }
                        } while (Validaciones.validarNombre(nombreAlumno) == -2);

                        //Petición de fecha de Matriculacion
                        String fechaMatriculacion;
                        boolean validarFechaMatriculacion = false;
                        do {
                            System.out.println("Fecha de matriculacion (formato dd-mm-aaaa): ");
                            fechaMatriculacion = teclado.nextLine();
                            if ("X".equalsIgnoreCase(fechaMatriculacion)) {
                                System.out.println("Operación cancelada.");
                                break; //
                            }
                            //Comprobación de que se encuentra en el formato correcto
                            if (validarFormatoFecha(fechaMatriculacion)) {
                                //Cambio a objeto de tipo LocalDate
                                LocalDate fechaMatriculacionFormatoLocalDate = Validaciones.formatearFecha(fechaMatriculacion);
                                //Comprobación de que se encuentra entre la fecha de inicio y final de curso
                                if (fechaMatriculacionFormatoLocalDate.isEqual(nuevoCurso.getFechaInicial()) || fechaMatriculacionFormatoLocalDate.isEqual(nuevoCurso.getFechaFinal())
                                        || (fechaMatriculacionFormatoLocalDate.isAfter(nuevoCurso.getFechaInicial()) && fechaMatriculacionFormatoLocalDate.isBefore(nuevoCurso.getFechaFinal()))) {
                                    validarFechaMatriculacion = true;
                                } else {
                                    System.out.println("La fecha debe estar comprendida entre el inicio y final del curso");
                                }
                            } else {
                                System.out.println("El formato de fecha introducido es incorrecto");
                            }
                        } while (!validarFechaMatriculacion);

                        //Petición del DNI o NIE del alumno
                        String dniAlumno;
                        do {
                            System.out.println("DNI o NIE del alumno: ");
                            dniAlumno = teclado.nextLine();
                            if ("X".equalsIgnoreCase(dniAlumno)) {
                                System.out.println("Operación cancelada.");
                                return; //
                            }
                            validacion = validarDNI(dniAlumno);
                            if (!validacion) {
                                System.out.println("Identificador inválido. Inténtelo de nuevo.");
                            }
                        } while (!validacion);

                        //Peticion del correo electrónico del alumno
                        String emailAlumno;
                        do {
                            System.out.println("Correo electrónico del alumno: ");
                            emailAlumno = teclado.nextLine();
                        } while (!validarEMAIL(emailAlumno));

                        //Creación del objeto alumno
                        if (nuevoCurso.insertarAlumno(nombreAlumno, idAlumno, dniAlumno, emailAlumno, fechaMatriculacion)) {
                            System.out.println("El alumno fue añadido correctamente\n");
                            System.out.println("Introduzca las notas del alumno:");

                            //Solicita las notas de cada asignatura
                            excepcion = false;
                            for (Asignaturas i : Asignaturas.values()) {
                                int nota = 0;
                                do {
                                    excepcion = false;
                                    try {
                                        System.out.println(i.getDescripcion() + ":");
                                        nota = Integer.parseInt(teclado.nextLine());
                                        if (!nuevoCurso.setNota(idAlumno, i.getCodigo(), nota)) {
                                            System.out.println("La nota debe estar comprendida entre 0 y 10");
                                        } else {
                                            System.out.println("Nota introducida correctamente\n");
                                            excepcion = true;
                                        }
                                    } catch (InputMismatchException | NumberFormatException e) {
                                        System.out.println("Error: Debes introducir un número entre el 0 y el 10.\n");
                                    }
                                } while (!excepcion);
                            }
                        }
                    }
                    break;

                //Opcion 4. Actualizar nota
                case 4:
                    teclado.nextLine();
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else {
                        System.out.println("Introduce el ID del alumno que quiera modificar la nota: ");
                        System.out.println(nuevoCurso.mostrarAlumnos());
                        String id = teclado.nextLine();
                        System.out.println("Introduce el código de la asignatura: ");
                        Asignaturas.mostrarAsignaturas();
                        String codAsignatura = teclado.nextLine();
                        int nota = nuevoCurso.getNota(id, codAsignatura);

                        switch (nota) {
                            case -1:
                                System.out.println("El ID del alumno introducido es incorrecto o no existe");
                                break;
                            case -2:
                                System.out.println("El código de la asignatura es incorrecto");
                                break;
                            default:
                                System.out.println("La nota del alumno " + id + " en la asignatura "
                                        + Asignaturas.obtenerAsignaturaPorCodigo(codAsignatura).getDescripcion()
                                        + " es: " + nota);
                                do {
                                    excepcion = false;
                                    try {
                                        System.out.println("Introduce la nueva nota: ");
                                        int nuevaNota = Integer.parseInt(teclado.nextLine());
                                        if (!nuevoCurso.setNota(id, codAsignatura, nuevaNota)) {
                                            System.out.println("La nota debe estar comprendida entre 0 y 10");
                                        } else {
                                            System.out.println("Nota introducida correctamente\n");
                                            excepcion = true;
                                        }
                                    } catch (InputMismatchException | NumberFormatException e) {
                                        System.out.println("Error: Debes introducir un número entre el 0 y el 10.\n");
                                    }
                                } while (!excepcion);
                        }
                    }
                    break;
                        
                //Opcion 5. Informe de asignatura
                case 5:
                    teclado.nextLine();
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else if (nuevoCurso.getProfesores() == null) {
                        System.out.println("No existen profesores. Por favor, añada uno");
                    } else {
                        String id;
                        //Pide el Identificador del profesor que se quiere hacer informe
                        System.out.println("Introduce el ID del profesor: ");
                        System.out.println(nuevoCurso.mostrarProfesores());
                        id = teclado.nextLine();
                        //Busca el ID del profesor
                        Profesor profesorInforme = nuevoCurso.buscaProfesor(id);
                        
                        if (profesorInforme == null) {
                            System.out.println("ID introducido incorrecto, el profesor no existe");
                        } else {
                            //Muestra el informe
                            System.out.println(profesorInforme.informeResultados(profesorInforme, nuevoCurso));
                        }
                    }
                    break;
                    
                //Opcion 6. Informe de alumno
                case 6:
                    teclado.nextLine();
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else if (nuevoCurso.getAlumnosConNotas() == null) {
                        System.out.println("No existen alumnos. Por favor, añada uno");
                    } else {
                        String id;
                        System.out.println("Introduce el ID del alumno: ");
                        System.out.println(nuevoCurso.mostrarAlumnos());
                        id = teclado.nextLine();
                        Expediente alumnoInforme = nuevoCurso.buscarAlumno(id);
                        if (alumnoInforme == null) {
                            System.out.println("ID introducido incorrecto, el alumno no existe");
                        } else {
                            System.out.println(alumnoInforme.getAlumno().informeResultados(alumnoInforme.getAlumno(), nuevoCurso));
                        }
                    }
                    break;
                    
                //Opcion 7. Informe general
                case 7:
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else {
                        System.out.println(nuevoCurso.InformeGeneral(nuevoCurso));
                    }
                    break;
                    
                //Opción 8. Eliminar alumno
                case 8:
                    teclado.nextLine();
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else if (nuevoCurso.getAlumnosConNotas() == null) {
                        System.out.println("No existen alumnos que eliminar");
                    } else {
                        System.out.println("Introduce el ID del alumno que desea eliminar: ");
                        System.out.println(nuevoCurso.mostrarAlumnos());
                        String id = teclado.nextLine();
                        if(nuevoCurso.eliminarAlumno(id)){
                            System.out.println("El alumno se eliminó correctamente");
                        }else{
                            System.out.println("No se encontró el alumno.");
                        }
                    }
                    break;
                
                //Opción 9. Salir del programa
                case 9:
                    System.out.println("El programa se cerrará");
                    break;
                //Cualquier opcion que no sea 1-9
                default:
                    System.out.println("Elige una opción válida");
                    break;
            }
        } while (opcion != 9);
    }
}