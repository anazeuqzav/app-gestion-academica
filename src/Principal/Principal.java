package Principal;

import Escuela.*;
import static Escuela.Asignaturas.mostrarAsignaturas;
import static Escuela.Asignaturas.validarAsignatura;
import Individuos.*;
import Validar.*;
import static Validar.Validaciones.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Pattern;

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
 * @version 3.0 22/03/2024
 */
public class Principal {
    static Scanner teclado = new Scanner(System.in); //escaner para peticiones de datos por teclado 
    private static final String PATHNAME = "./archivos/"; //ruta donde se guardarán los diferentes archivos
    /**
     * Metodo que permite guardar los datos de un curso
     * @param curso Objeto curso a guardar
     * @param archivo nombre del archivo
     */
    public static void guardarDatos(Curso curso, String archivo){
        archivo = PATHNAME + archivo;
        try {
            curso.guardarEstado(archivo);
            System.out.println("Curso guardado en: " +  archivo);
        } catch (IOException ex) {
            System.out.println("Error al guardar el archivo");
        }
    }
    /**
     * Método que permite cargar los datos de un curso
     * @param archivo nombre dela rchivo que se quiere cargar
     * @return el Objeto Curso cargado
     */
    public static Curso cargarDatos(String archivo) {
        archivo = PATHNAME + archivo;
        try {
            return Curso.cargarEstado(archivo);
        } catch (ClassNotFoundException | IOException ex) {
            System.out.println("Error al cargar el archivo");
            return null;
        }
    }
    /**
     * Valida el nombre de un archivo para que cumpla con las caracteristicas de los archivos en Windows.
     * @param nombreArchivo Cadena con el nombre del archivo
     * @return True si es válido y False si no es válido
     */
    public static boolean validarNombreArchivo(String nombreArchivo) {
        String patron = "^[a-zA-Z0-9_-]+$";
        return Pattern.matches(patron, nombreArchivo);
    }

    /**
     * Método que permite generar un archivo txt de un StringBuidler. Usado para generar el Informe General.
     * @param contenido Strinbuilder del que se hará el archivo .txt
     * @param nombreArchivo nombre que tendrá el archivo
     */
    public static void generarArchivo(StringBuilder contenido, String nombreArchivo) {
        if (!validarNombreArchivo(nombreArchivo)) {
            System.out.println("Error: El nombre del archivo es inválido. Debe contener solo letras, números, guiones bajos y guiones medios.");
        } else {
            // Guardar el informe en el archivo de texto
            nombreArchivo = PATHNAME + nombreArchivo + ".txt";
            try (FileWriter salida = new FileWriter(nombreArchivo)) {
                salida.write(contenido.toString());
                System.out.println("El informe se ha guardado en: " + nombreArchivo + " correctamente.");
                salida.close();
            } catch (IOException e) {
                System.out.println("Error al guardar el informe en el archivo.");
            }
        }
    }
  
    //Muestra el menu en pantalla.
    public static int mostrarMenu(){
            System.out.println("""
                                           GESTIÓN ACADÉMICA
                               ---------------------------------------
                               Elige una opcion: 
                               1. Crear curso 
                               2. Nuevo Profesor
                               3. Nuevo Estudiante 
                               4. Actualizar nota 
                               5. Obtener el informe de la asignatura 
                               6. Obtener informe de estudiante 
                               7. Obtener informe general 
                               8. Eliminar alumno
                               9. Guardar datos en disco
                               10. Cargar datos de disco
                               11. Salir""");
            int opcion = teclado.nextInt();
            return opcion;
}
    //Método que pide las fechas del curso
    public static void peticionFechas(Curso c) {
        Boolean excepcion = false;
        //Pide la fecha de inicio de curso
        do {
            try {
                System.out.println("Introduce la fecha de inicio del curso (formato aaaa-mm-dd)");
                c.setFechaInicial(LocalDate.parse(teclado.nextLine()));
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
                if (c.validarFechaFinal(fechaFinal)) {
                    c.setFechaFinal(fechaFinal);
                    excepcion = true;
                    break;
                } else {
                    System.out.println("La fecha no puede ser anterior a la fecha de inicio del curso. Por favor, vuelva a introducirla.");
                }
            } catch (DateTimeParseException | NumberFormatException e) {
                System.out.println("El formato de fecha no es correcto");
            }
        } while (!excepcion);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in); //escaner para recoger datos por teclado
        int opcion; //opción del menu
        boolean validacion = false; //variable para controlar validaciones
        boolean excepcion = false; //variable para controlar escepciones
        Curso nuevoCurso = null; //inicialización a null de curso

        do {
            opcion = mostrarMenu();
            switch (opcion) {
                //Opcion 1. Crear un curso
                case 1:                    
                    //Si ya existe un curso creado
                    if (nuevoCurso != null) {
                        System.out.println("Ya existe un curso creado. Si continúa se borrará ¿Desea continuar? (S/N)");
                        String continuar;
                        do {
                            continuar = teclado.nextLine();
                            if ("S".equals(continuar)) {
                                nuevoCurso = new Curso(); //inicializar a un curso vacío
                                teclado.nextLine();
                                peticionFechas(nuevoCurso); //petición de fechas
                            } else if (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N")) {
                                System.out.println("Error: Ingrese 'S' para continuar o 'N' para salir.");
                            }
                        } while (!continuar.equals("S") && !continuar.equals("N"));
                    } else { //si no existe ningun curso creado, crea uno
                        nuevoCurso = new Curso(); //inicializa un curso
                        peticionFechas(nuevoCurso); //pide las fechas
                    }
                    break;

                //Opcion 2. Añadir un profesor
                case 2:
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else {
                        //Petición del identificador
                        String idProfesor;
                        do {
                            System.out.println("Identificador del profesor:");
                            idProfesor = teclado.nextLine();
                            if ("X".equalsIgnoreCase(idProfesor)) {
                                System.out.println("Operación cancelada.");
                                return;
                            }else if (!Profesor.validarIdentificador(idProfesor)) {
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
                            if (validarNombre == -1) { //si el nombre es mayor de 40 caracteres, solo guardará los primeros 40
                                nombreProfesor = nombreProfesor.substring(0, 40);
                            } else if (validarNombre == -2) {//si se deja en blanco salta un aviso
                                System.out.println("Ha dejado el campo el blanco.");
                            }
                        } while (validarNombre == -2);
                       
                        //Petición fecha de alta
                        String fechaAlta;
                        validacion = false;
                        do {
                            System.out.println("Fecha de alta del profesor(formato: dd-mm-aaaa): ");
                            fechaAlta = teclado.nextLine();
                            //Comprueba que este en el formato correcto
                            if (validarFormatoFecha(fechaAlta)) {
                                //Cambio a objeto de tipo LocalDate
                                LocalDate fechaAltaFormatoLocalDate = Validaciones.formatearFecha(fechaAlta);
                                //Comprobacion de que la fecha es anterior a la fecha de finalización del curso
                                if (fechaAltaFormatoLocalDate.isBefore(nuevoCurso.getFechaFinal())) {
                                    validacion = true;
                                } else {
                                    System.out.println("La fecha debe ser anterior a la finalización del curso");
                                }
                            } else {
                                System.out.println("El formato de fecha introducido es incorrecto");
                            }
                        } while (!validacion);

                        //Petición dni
                        String dniProfesor;
                        do {
                            System.out.println("DNI o NIE del profesor: ");
                            dniProfesor = teclado.nextLine();

                            if ("X".equalsIgnoreCase(dniProfesor)) {
                                System.out.println("Operación cancelada.");
                                return; //
                            }
                            validacion = validarDNI(dniProfesor);
                            if (!validacion) {
                                System.out.println("Identificador inválido. Inténtelo de nuevo.");
                            }
                        } while (!validacion || "X".equalsIgnoreCase(dniProfesor));

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
                        //Peticion de identificador
                        String idAlumno;
                        do {
                            System.out.println("Identificador del alumno:");
                            idAlumno = teclado.nextLine();
                            if ("X".equalsIgnoreCase(idAlumno)) {
                                System.out.println("Operación cancelada.");
                                return;
                            } else if (!Alumno.validarIdentificador(idAlumno)) {
                                System.out.println("Identificador inválido. Inténtelo de nuevo.");
                            } else if (nuevoCurso.alumnoExiste(idAlumno)) {
                                System.out.println("El ID ya existe"); //si el alumno ya existe
                            }
                        } while (!Alumno.validarIdentificador(idAlumno) || nuevoCurso.alumnoExiste(idAlumno) || "X".equalsIgnoreCase(idAlumno));
                    
                        //Petición de nombre completo
                        String nombreAlumno;
                        do {
                            System.out.println("Nombre completo del alumno: ");
                            nombreAlumno = teclado.nextLine();
                            if (Validaciones.validarNombre(nombreAlumno) == -1) {
                                nombreAlumno = nombreAlumno.substring(0, 40); //si introduce un nombre con más de 40 caracteres, se guardan los 40 primeros
                            } else if (Validaciones.validarNombre(nombreAlumno) == -2) {
                                System.out.println("Ha dejado el campo el blanco."); //si deja el campo en blanco
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
                                opcion = mostrarMenu();
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
                        } while (!validarFechaMatriculacion || "X".equalsIgnoreCase(fechaMatriculacion));

                        //Petición del DNI o NIE del alumno
                        String dniAlumno;
                        do {
                            System.out.println("DNI o NIE del alumno: ");
                            dniAlumno = teclado.nextLine();
                            if ("X".equalsIgnoreCase(dniAlumno)) {
                                System.out.println("Operación cancelada.");
                                return;
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

                        //Creación del objeto alumno en el curso
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
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else if (nuevoCurso.getProfesores() == null) {
                        System.out.println("No existen profesores. Por favor, añada uno");
                    } else {
                        //Pide el Identificador del profesor que se quiere hacer informe
                        System.out.println("Introduce el ID del profesor: ");
                        System.out.println(nuevoCurso.mostrarProfesores());
                        String id = teclado.nextLine();
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
                        System.out.println("\n¿Quiere guardar el informe en un archivo? (S/N)");
                        String continuar;
                        do {
                            continuar = teclado.nextLine();
                            if ("S".equalsIgnoreCase(continuar)) {
                                System.out.println("Escriba un nombre para el archivo: ");
                                String nombreArchivo = teclado.nextLine();
                                generarArchivo(nuevoCurso.InformeGeneral(nuevoCurso), nombreArchivo);
                            } else if (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N")) {
                                System.out.println("Error: Ingrese 'S' para continuar o 'N' para salir.");
                            }
                        } while (!continuar.equalsIgnoreCase("S") && !continuar.equalsIgnoreCase("N"));
                    }
                    break;
                    
                //Opción 8. Eliminar alumno
                case 8:
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
            
                //Opción 9. Guardar datos en disco    
                case 9:
                    if (nuevoCurso == null) {
                        System.out.println("Primero debe crear un curso");
                    } else {
                        guardarDatos(nuevoCurso, nuevoCurso.getNombreCurso());
                    }
                    break;
                    
                //Opción 10. Cargar datos de disco
                case 10:
                    if (nuevoCurso != null) {
                        String continuar;
                        System.out.println("Hay datos creados del curso " + nuevoCurso.getNOMBRE_CURSO() + ". Si continúa se perderán. ¿Desea continuar? (S/N)");
                        do {
                            continuar = teclado.nextLine();
                            if ("S".equals(continuar)) {
                                System.out.println("¿Qué curso desea cargar? (formato: damx)");
                                nuevoCurso = cargarDatos(teclado.nextLine());
                            } else if (!continuar.equals("S") && !continuar.equals("N")) {
                                System.out.println("Error: Ingrese 'S' para continuar o 'N' para salir.");
                            }
                        } while (!continuar.equals("S") && !continuar.equals("N"));
                    } else {
                        System.out.println("¿Qué curso desea cargar? (formato: damx)");
                        nuevoCurso = cargarDatos(teclado.nextLine());
                    }
                    break;
                //Opción 9. Salir del programa
                case 11:
                    System.out.println("El programa se cerrará");
                    break;
                //Cualquier opcion que no sea 1-9
                default:
                    System.out.println("Elige una opción válida");
                    break;
            }
        } while (opcion != 11);
    }
}