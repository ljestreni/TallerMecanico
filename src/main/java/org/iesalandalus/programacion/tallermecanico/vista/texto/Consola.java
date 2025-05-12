package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";
    private Consola(){}

    public static void mostrarCabecera(String mensaje){
        System.out.println(mensaje);
        System.out.println("-".repeat(mensaje.length()).concat(""));
        //Esto lo que hace es poner una cadena y despues el comanodo repetir por lo larga que sea la cadena del mensaje.

    }

    public static void mostrarMenu(){
        mostrarCabecera("Gestor de Taller de Reparación de Vehículos.");
        mostrarCabecera("Menú de opciones: ");
        for (Evento evento : Evento.values()){
            System.out.println(evento);
        }
    }

    public static Evento elegirOpcion(){
        Evento evento = null;
        do {
            try {
                evento = Evento.get(leerEntero("Introduzca el número de opción:"));
            } catch (IllegalArgumentException e) {
                System.out.printf("ERROR: %s%n", e.getMessage());
            }

        } while (evento == null);


        return evento;
    }

    public static float leerReal(String mensaje){
        System.out.println(mensaje);
        return Float.parseFloat(Entrada.cadena());

    }

    public static int leerEntero(String mensaje){
        System.out.println(mensaje);
        return Integer.parseInt(Entrada.cadena());

    }

    public static String leerCadena(String mensaje){
        System.out.println(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje){
        System.out.printf("Introduzca la fecha en este formato: %s", CADENA_FORMATO_FECHA);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        return LocalDate.parse(leerCadena(mensaje),formatter);
    }





}