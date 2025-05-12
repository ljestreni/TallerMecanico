package org.iesalandalus.programacion.tallermecanico.modelo.dominio;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Revision extends Trabajo {

    private static final float FACTOR_HORA = 35;
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        super(cliente,vehiculo,fechaInicio);
    }


    public Revision(Revision revision){
        super(revision);
    }

    @Override
    public String toString() {
        String fechaIF = getFechaFin().format(FORMATO_FECHA);

        if (getFechaFin() == null){

            return String.format("%s - %s (%s) - %s %s - %s: (%s - ), %s horas, %.2f € en material", getCliente().getNombre(), getCliente().getDni(), getCliente().getTelefono(), getVehiculo().marca(), getVehiculo().modelo(), getVehiculo().matricula(), fechaIF, getHoras(), getPrecioMaterial());
        }
        String fechaFF = getFechaFin().format(FORMATO_FECHA);
        return String.format("%s - %s (%s) - %s %s - %s: (%s - %s), %s horas, %.2f € en material, %.2f € total", getCliente().getNombre(), getCliente().getDni(), getCliente().getTelefono(), getVehiculo().marca(), getVehiculo().modelo(), getVehiculo().matricula(), fechaIF, fechaFF, getHoras(), getPrecioMaterial(), getPrecio());
    }


    @Override
    public float getPrecioEspecifico() {
        return FACTOR_HORA * getHoras();
    }
}