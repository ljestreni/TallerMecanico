package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola(){}

    public static void mostrarCabecera(String mensaje){
        System.out.printf("%n%s%n", mensaje);
        System.out.printf("-".repeat(mensaje.length()).concat("%n%n"));
    }

    public static void mostrarMenu(){
        mostrarCabecera("Gestión de un taller mecánico");
        for (Evento evento : Evento.values()){
            System.out.println(evento);
        }
    }

    public static Evento elegirOpcion(){
        Evento evento = null;
        do {
            try {
                evento = evento.get(leerEntero("\n Elige una opción: "));

            }catch (IllegalArgumentException e){
                System.out.printf("ERROR: %s%n", e.getMessage());
            }
        }while (evento == null);
        return evento;
    }

    public static int leerEntero(String mensaje){
        System.out.printf(mensaje);
        return Entrada.entero();
    }

    public static float leerReal(String mensaje){
        System.out.printf(mensaje);
        return Entrada.real();
    }

    public static String leerCadena(String mensaje){
        System.out.printf(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje){
        LocalDate fecha;
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        mensaje = String.format("%s%n: ", mensaje, CADENA_FORMATO_FECHA);
        try {
            fecha = LocalDate.parse(leerCadena(mensaje), formatoFecha);
        }catch (DateTimeException e){
            fecha = null;
        }
        return fecha;
    }




}
