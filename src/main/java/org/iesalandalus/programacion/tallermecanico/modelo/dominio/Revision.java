package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

public class Revision extends Trabajo {
   private static final float FACTOR_HORA = 35;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        super(cliente,vehiculo,fechaInicio);
    }

    @Override
    public float getPrecioEspecifico() {
        return FACTOR_HORA * getHoras();
    }

    public Revision(Revision revision){
        super(revision);
    }

    @Override
    public String toString() {
        String fechaInicioStr = getFechaInicio().format(FORMATO_FECHA);
        String fechaFinStr = (getFechaFin() == null) ? " " : getFechaFin().format(FORMATO_FECHA);
        String stringBueno =  null;
        if (getFechaFin() == null) {
            stringBueno = String.format("Revisión -> %s - %s (%s - ): %s horas", getCliente(), getVehiculo(), fechaInicioStr, getHoras());
        } else {
             stringBueno = String.format("Revisión -> %s - %s (%s - %s): %s horas, %.2f € total", getCliente(), getVehiculo(), fechaInicioStr, fechaFinStr, getHoras(), getPrecio());
        }
        return stringBueno;
    }
}
