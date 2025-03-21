package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Mecanico extends Trabajo{

    private static final float FACTOR_HORA = 0;
    private static final float FACTOR_PRECIO_MATERIAL = 0;
    private static float precioMaterial = 0;

    public Mecanico (Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {

    }

    public Mecanico (Mecanico mecanico) {

    }

    public float getPrecioMaterial() {

    }

    public void anadirPrecioMaterial(float precioMaterial) {

    }

    public float getPrecioEspecifico() {

    }

    @Override
    public String toString() {
        return String.format("[cliente=%s, vehiculo=%s, fechaInicio=%s, fechaFin=%s, horas=%s, precioMaterial=%s]", cliente, vehiculo, fechaInicio, fechaFin, horas, precioMaterial);
    }
}
