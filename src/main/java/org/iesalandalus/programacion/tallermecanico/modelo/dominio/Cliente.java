package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public class Cliente {
    private static final String ER_NOMBRE = "[A-Z][a-záéíóú]+( [A-Z][a-záéíóú]+)*";
    private static final String ER_DNI = "\\d{8}[A-Z]" ;
    private static final String ER_TELEFONO = "\\d{9}";
    private String nombre;
    private String dni;
    private String telefono;


    public Cliente(String nombre, String dni, String telefono){
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente){
        Objects.requireNonNull(cliente,"No es posible copiar un cliente nulo.");
        this.dni = cliente.dni;
        this.nombre = cliente.nombre;
        this.telefono = cliente.telefono;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre,"El nombre no puede ser nulo.");
        if (!nombre.matches(ER_NOMBRE)){
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    public  String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        Objects.requireNonNull(dni,"El DNI no puede ser nulo.");
        if (!dni.matches(ER_DNI)){
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        if(!comprobarLetraDni(dni)){
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        }

        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        Objects.requireNonNull(telefono,"El teléfono no puede ser nulo.");
        if (!telefono.matches(ER_TELEFONO)){
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }

        this.telefono = telefono;
    }

    private boolean comprobarLetraDni(String dni){
        String letrasDni = "TRWAGMYFPDXBNJZSQVHLCK";
        String numerosDni = dni.substring(0,8);
        int posicionLetra = Integer.parseInt(numerosDni) % 23;
        char letraDni = dni.charAt(8);
        return letraDni == letrasDni.charAt(posicionLetra);
    }

    public  static Cliente get(String dni){
     return new Cliente("Pepe", dni, "823848938");
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
        return Objects.hashCode(dni);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", nombre, dni, telefono);
    }
}

