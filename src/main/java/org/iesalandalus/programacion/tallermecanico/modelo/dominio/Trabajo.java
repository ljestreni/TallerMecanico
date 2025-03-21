package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Trabajo {
    protected static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float FACTOR_DIA = 1.5f;

    protected Cliente cliente;
    protected Vehiculo vehiculo;
    private static LocalDate fechaInicio;
    private static LocalDate fechaFin;
    private static int horas;

    protected Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }

    protected Trabajo(Trabajo trabajo) {
        this(trabajo.cliente, trabajo.vehiculo, trabajo.fechaInicio);
        this.fechaFin = trabajo.fechaFin;
        this.horas = trabajo.horas;
        this.precioMaterial = trabajo.precioMaterial;
    }

    public Trabajo copiar(Trabajo trabajo) {
        return new Trabajo(trabajo);
    }

    public Trabajo get(Vehiculo vehiculo) {
        return this.vehiculo.equals(vehiculo) ? this : null;
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

    public float getPrecio() {
        if (!estaCerrada()) {
            throw new IllegalStateException("No se puede calcular el precio de una revisión no cerrada.");
        }
        return (horas * Revision.PRECIO_HORA) + (getDias() * Revision.PRECIO_DIA) + (precioMaterial * Revision.PRECIO_MATERIAL);
    }

    private float getPrecioFijo() {

        return 0;
    }

    private float getDias() {
        return fechaInicio != null && fechaFin != null ? fechaFin.toEpochDay() - fechaInicio.toEpochDay() : 0;
    }

    public abstract float getPrecioEspecifico();

    public boolean estaCerrada() {
        return fechaFin != null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Trabajo trabajo = (Trabajo) o;
        return Float.compare(trabajo.getPrecioFijo(), getPrecioFijo()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrecioFijo());
    }
}
