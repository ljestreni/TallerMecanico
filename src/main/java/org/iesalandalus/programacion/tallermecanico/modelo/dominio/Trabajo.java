package org.iesalandalus.programacion.tallermecanico.modelo.dominio;


import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public abstract class Trabajo {
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float FACTOR_DIA = 10;
    private float precioMaterial = 0;
    private int horas = 0;
    private LocalDate fechaInicio = null;
    private LocalDate fechaFin = null;
    private Cliente cliente;
    private Vehiculo vehiculo;


    protected Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){

        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
        horas = 0;
        fechaFin = null;
    }

    protected Trabajo(Trabajo trabajo){
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");
        cliente = new Cliente(trabajo.cliente);
        vehiculo = trabajo.vehiculo;
        horas = trabajo.horas;
        fechaInicio = trabajo.fechaInicio;
        fechaFin = trabajo.fechaFin;


    }



    public static Trabajo copiar(Trabajo trabajo){
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");

        Trabajo trabajo1 = null;
        if (trabajo instanceof Revision revision){
            trabajo1 = new Revision(revision);

        } else if (trabajo instanceof Mecanico mecanico){
            trabajo1 = new Mecanico(mecanico);
        }


        return trabajo1;
    }


    public static Trabajo get(Vehiculo vehiculo){
        Cliente cliente1 = new Cliente("Bob Esponja","64386693X","635981425");
        LocalDate fecha = LocalDate.now();
        return new Revision(cliente1,vehiculo,fecha);
    }


    public abstract float getPrecioEspecifico();


    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (fechaFin != null) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que el trabajo está cerrado.");
        }


        Objects.requireNonNull(horas, "Las horas no pueden ser nulas.");
        if (horas <= 0) {
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }


        this.horas += horas;
    }


    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(precioMaterial, "El precio material no puede ser nulo");
        if (fechaFin != null) {
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que la revisión está cerrada.");
        }
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        this.precioMaterial += precioMaterial;
    }


    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("El trabajo ya está cerrado.");
        }
        setFechaFin(fechaFin);
    }


    public float getPrecio() {

        return getPrecioFijo() + getPrecioEspecifico();
    }

    public float getPrecioFijo(){
        return FACTOR_DIA * getDias();
    }

    private float getDias() {
        if (fechaFin == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(getFechaInicio(), getFechaFin());
    }

    public int getHoras() {
        return horas;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }

        this.fechaInicio = fechaInicio;


    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }


    public void setFechaFin(LocalDate fechaFin) {


        if (fechaFin.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }


        this.fechaFin = fechaFin;
    }


    public float getPrecioMaterial() {
        return precioMaterial;
    }


    public void setPrecioMaterial(float precioMaterial) {


        this.precioMaterial = precioMaterial;
    }


    public Cliente getCliente() {
        return cliente;
    }


    public void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        this.cliente = cliente;
    }


    public Vehiculo getVehiculo() {
        return vehiculo;
    }


    public boolean estaCerrado() {
        return (fechaFin != null);


    }


    public void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trabajo trabajo)) return false;
        return Float.compare(precioMaterial, trabajo.precioMaterial) == 0 && Objects.equals(fechaInicio, trabajo.fechaInicio) && Objects.equals(cliente, trabajo.cliente) && Objects.equals(vehiculo, trabajo.vehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(precioMaterial, fechaInicio, cliente, vehiculo);
    }
}