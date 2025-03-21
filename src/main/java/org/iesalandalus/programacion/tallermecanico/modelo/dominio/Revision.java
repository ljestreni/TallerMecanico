package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Revision extends Trabajo {

    private static final float FACTOR_HORA = 30;

    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio); // Calling the constructor of the parent class
        this.horas = 0;
        this.precioMaterial = 0;
    }

    public Revision(Revision revision) {
        super(revision);
        this.cliente = new Cliente(revision.cliente);
        this.vehiculo = revision.vehiculo;
        this.fechaInicio = revision.fechaInicio;
        this.fechaFin = revision.fechaFin;
        this.horas = revision.horas;
        this.precioMaterial = revision.precioMaterial;
    }

    @Override
    public float getPrecioEspecifico() {
        return (horas * FACTOR_HORA) + precioMaterial;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Revision revision = (Revision) obj;
        return Objects.equals(cliente, revision.cliente) && Objects.equals(vehiculo, revision.vehiculo) && Objects.equals(fechaInicio, revision.fechaInicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, vehiculo, fechaInicio);
    }

    @Override
    public String toString() {
        return String.format("Revisión de %s con %s - Inicio: %s, Fin: %s, Horas: %d, Material: %.2f, Precio Total: %.2f",
                cliente, vehiculo,
                fechaInicio.format(FORMATO_FECHA),
                (fechaFin != null ? fechaFin.format(FORMATO_FECHA) : "No cerrada"),
                horas);
    }
}
