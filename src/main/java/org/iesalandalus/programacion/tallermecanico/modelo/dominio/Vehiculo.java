package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;
import java.util.regex.Pattern;

public record Vehiculo(String marca, String modelo, String matricula) {

    private static final String ER_MARCA = "^[A-Z][a-zA-Z]*(?: [A-Z][a-zA-Z]*)*$";
    private static final String ER_MATRICULA = "^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$";

    public Vehiculo {
        if (marca == null) {
            throw new NullPointerException("La marca no puede ser nula.");
        }
        if (!Pattern.matches(ER_MARCA, marca)) {
            throw new IllegalArgumentException("La marca no tiene un formato válido.");
        }

        if (modelo == null) {
            throw new NullPointerException("El modelo no puede ser nulo.");
        }
        if (modelo.isBlank()) {
            throw new IllegalArgumentException("El modelo no puede estar en blanco.");
        }

        if (matricula == null) {
            throw new NullPointerException("La matrícula no puede ser nula.");
        }
        if (!Pattern.matches(ER_MATRICULA, matricula)) {
            throw new IllegalArgumentException("La matrícula no tiene un formato válido.");
        }
    }

    public static Vehiculo get(String matricula) {
        if (matricula == null) {
            throw new NullPointerException("La matrícula no puede ser nula.");
        }
        if (!Pattern.matches(ER_MATRICULA, matricula)) {
            throw new IllegalArgumentException("La matrícula no tiene un formato válido.");
        }
        return new Vehiculo("Desconocido", "Desconocido", matricula);
    }

    @Override
    public String toString() {
        return String.format("[marca=%s, modelo=%s, matricula=%s]", marca, modelo, matricula);
    }
}