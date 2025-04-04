package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public record Vehiculo(String marca, String modelo, String matricula) {
    private static final String ER_MARCA = "[A-Z][A-z]+(\\W[ A-Z][a-z]+)*";
    private static final String ER_MATRICULA = "\\d{4}[^AEIOU]{3}";


    public Vehiculo {
        validarMarca(marca);
        validarMatricula(matricula);
        validarModelo(modelo);
    }

    private void validarMarca(String marca){
        Objects.requireNonNull(marca,"La marca no puede ser nula.");
        if (!marca.matches(ER_MARCA)){
            throw new IllegalArgumentException("La marca no tiene un formato válido.");
        }
    }

    private void validarMatricula(String matricula){
        Objects.requireNonNull(matricula,"La matrícula no puede ser nula.");
        if (!matricula.matches(ER_MATRICULA)){
            throw new IllegalArgumentException("La matrícula no tiene un formato válido.");
        }
    }

    private void validarModelo(String modelo){
        Objects.requireNonNull(modelo,"El modelo no puede ser nulo.");
        if (modelo.isBlank()){
            throw new IllegalArgumentException("El modelo no puede estar en blanco.");
        }
    }

    public static Vehiculo get(String matricula){
        Objects.requireNonNull(matricula,"La matrícula no puede ser nula.");

        return new Vehiculo("KIA","BBBBBB",matricula);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehiculo vehiculo = (Vehiculo) o;
        return Objects.equals(matricula, vehiculo.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matricula);
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", marca, modelo, matricula);
    }
}
