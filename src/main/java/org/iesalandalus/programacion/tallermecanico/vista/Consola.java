package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Consola {

    private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
    private static final Scanner scanner = new Scanner(System.in);

    private Consola() {
    }

    public static void mostrarCabecera(String mensaje) {
        System.out.println(mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }

    public static void mostrarMenu() {
        mostrarCabecera("Gestión del Taller Mecánico");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {
        int numeroOpcion;
        do {
            numeroOpcion = leerEntero("Elige una opción: ");
        } while (!Opcion.esValida(numeroOpcion));
        return Opcion.get(numeroOpcion);
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Entrada inválida. " + mensaje);
        }
        return scanner.nextInt();
    }

    private static float leerReal(String mensaje) {
        System.out.print(mensaje);
        while (!scanner.hasNextFloat()) {
            scanner.next();
            System.out.print("Entrada inválida. " + mensaje);
        }
        return scanner.nextFloat();
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return scanner.next();
    }

    private static LocalDate leerFecha(String mensaje) {
        System.out.print(mensaje);
        while (true) {
            try {
                return LocalDate.parse(scanner.next(), FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.print("Formato incorrecto. " + mensaje);
            }
        }
    }

    public static Cliente leerCliente() {
        String nombre = leerCadena("Introduce el nombre del cliente: ");
        String dni = leerCadena("Introduce el DNI del cliente: ");
        String telefono = leerCadena("Introduce el teléfono del cliente: ");
        return new Cliente(nombre, dni, telefono);
    }

    public static Cliente leerClienteDni() {
        String dni = leerCadena("Introduce el DNI del cliente: ");
        return new Cliente("", dni, "");
    }

    public static String leerNuevoNombre() {
        return leerCadena("Introduce el nuevo nombre del cliente: ");
    }

    public static String leerNuevoTelefono() {
        return leerCadena("Introduce el nuevo teléfono del cliente: ");
    }

    public static Vehiculo leerVehiculo() {
        String marca = leerCadena("Introduce la marca del vehículo: ");
        String modelo = leerCadena("Introduce el modelo del vehículo: ");
        String matricula = leerCadena("Introduce la matrícula del vehículo: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    public static Vehiculo leerVehiculoMatricula() {
        String matricula = leerCadena("Introduce la matrícula del vehículo: ");
        return new Vehiculo("", "", matricula);
    }

    public static Revision leerRevision() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio: ");
        return new Revision(cliente, vehiculo, fechaInicio);
    }

    public static int leerHoras() {
        return leerEntero("Introduce las horas a añadir: ");
    }

    public static float leerPrecioMaterial() {
        return leerReal("Introduce el precio del material a añadir: ");
    }

    public static LocalDate leerFechaCierre() {
        return leerFecha("Introduce la fecha de cierre: ");
    }
}
