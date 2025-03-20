package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Revision {

    private static final float PRECIO_HORA = 30;
    private static final float PRECIO_DIA = 10;
    private static final float PRECIO_MATERIAL = 1.5F;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;
    private float precioMaterial;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
        this.horas = 0;
        this.precioMaterial = 0;
    }

    public Revision(Revision revision) {
        if (revision == null) {
            throw new NullPointerException("No es posible copiar una revisión nula.");
        }
        this.cliente = new Cliente(revision.cliente);
        this.vehiculo = revision.vehiculo;
        this.fechaInicio = revision.fechaInicio;
        this.fechaFin = revision.fechaFin;
        this.horas = revision.horas;
        this.precioMaterial = revision.precioMaterial;
    }

    public Cliente getCliente() {
        return new Cliente(cliente);
    }

    private void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("El cliente no puede ser nulo.");
        }
        this.cliente = new Cliente(cliente);
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new NullPointerException("El vehículo no puede ser nulo.");
        }
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        if (fechaInicio == null) {
            throw new NullPointerException("La fecha de inicio no puede ser nula.");
        }
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a hoy.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        if (fechaFin == null) {
            throw new NullPointerException("La fecha de fin no puede ser nula.");
        }
        if (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio) || fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio y no puede ser posterior a hoy.");
        }
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }

    public void añadirHoras(int horas) {
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas añadidas deben ser mayores que cero.");
        }
        this.horas += horas;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void añadirPrecioMaterial(float precioMaterial) {
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material debe ser mayor que cero.");
        }
        this.precioMaterial += precioMaterial;
    }

    public boolean estaCerrada() {
        return fechaFin != null;
    }

    public void cerrar(LocalDate fechaFin) {
        setFechaFin(fechaFin);
    }

    public float getPrecio() {
        if (!estaCerrada()) {
            throw new IllegalStateException("No se puede calcular el precio de una revisión no cerrada.");
        }
        return (horas * PRECIO_HORA) + (getDias() * PRECIO_DIA) + (precioMaterial * PRECIO_MATERIAL);
    }

    private long getDias() {
        return fechaInicio.until(fechaFin).getDays();
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
        return String.format("Revisión de %s con %s - Inicio: %s, Fin: %s, Horas: %d, Material: %.2f",
                cliente, vehiculo,
                fechaInicio.format(FORMATO_FECHA),
                (fechaFin != null ? fechaFin.format(FORMATO_FECHA) : "No cerrada"),
                horas, precioMaterial);
    }
}