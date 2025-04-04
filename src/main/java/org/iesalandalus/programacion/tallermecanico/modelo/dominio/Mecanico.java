package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.util.Objects;

public class Mecanico extends Trabajo {
    private static final float FACTOR_HORA = 30;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5F;
    private float precioMaterial = 0 ;


    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        super(cliente,vehiculo,fechaInicio);
    }

    @Override
    public float getPrecioEspecifico() {
        return (FACTOR_HORA * getHoras()) + (precioMaterial * FACTOR_PRECIO_MATERIAL);
    }

    public void anadirPrecioMaterial(float precioMaterial)throws TallerMecanicoExcepcion{
        if (precioMaterial <=0){
            throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        }
        if (estaCerrado()){
            throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        }
        this.precioMaterial = precioMaterial + getPrecioMaterial();
    }

    public float getPrecioMaterial(){
        return precioMaterial;
    }


    public Mecanico(Mecanico mecanico){
        super(mecanico);
        Objects.requireNonNull(mecanico,"La revisión no puede ser nula.");
        precioMaterial = mecanico.precioMaterial;
    }

    @Override
    public String toString() {
        String fechaInicioStr = getFechaInicio().format(FORMATO_FECHA);
        String fechaFinStr = (getFechaFin() == null) ? " " : getFechaFin().format(FORMATO_FECHA);
        String stringBueno = null;
        if (getFechaFin() == null){
            stringBueno = String.format("Mecánico -> %s - %s (%s -%s): %s horas, %.2f € en material", getCliente(),getVehiculo() , fechaInicioStr, fechaFinStr, getHoras(), getPrecioMaterial());
        }else {
            stringBueno = String.format("Mecánico -> %s - %s (%s - %s): %s horas, %.2f € en material, %.2f € total", getCliente(), getVehiculo(), fechaInicioStr, fechaFinStr, getHoras(), getPrecioMaterial(),getPrecio());
        }
        return stringBueno;
    }
}
