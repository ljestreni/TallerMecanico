package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;
import java.util.regex.Pattern;

import java.util.Objects;
import java.util.regex.Pattern;

public class Cliente {

    private static final String ER_NOMBRE = "^[A-ZÁÉÍÓÚ][a-záéíóú]+( [A-ZÁÉÍÓÚ][a-záéíóú]+)*$";
    private static final String ER_DNI = "^[0-9]{8}[A-HJ-NP-TV-Z]$";
    private static final String ER_TELEFONO = "^[6-9]\\d{8}$";

    private String nombre;
    private String dni;
    private String telefono;

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No es posible copiar un cliente nulo.");
        }
        this.nombre = cliente.nombre;
        this.dni = cliente.dni;
        this.telefono = cliente.telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || !Pattern.matches(ER_NOMBRE, nombre)) {
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        if (dni == null || !Pattern.matches(ER_DNI, dni) || !comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        this.dni = dni;
    }

    private boolean comprobarLetraDni(String dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCalculada = letras.charAt(numero % 23);
        return Character.toUpperCase(dni.charAt(8)) == letraCalculada;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !Pattern.matches(ER_TELEFONO, telefono)) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }

    public static Cliente get(String dni) {
        if (dni == null) {
            throw new NullPointerException("El DNI no puede ser nulo.");
        }
        if (!Pattern.matches(ER_DNI, dni) || !new Cliente("Test", dni, "600000000").comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        return new Cliente("Nombre Genérico", dni, "600000000");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", nombre, dni, telefono);
    }
}