package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {
    List<Vehiculo> coleccionVehiculos = new ArrayList<>();

    public Vehiculos(){
        coleccionVehiculos = new ArrayList<>();
    }

    @Override
    public List<Vehiculo> get(){
        return new ArrayList<>(coleccionVehiculos);
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(vehiculo,"No se puede insertar un vehículo nulo.");
        if (!coleccionVehiculos.contains(vehiculo)){
            coleccionVehiculos.add(vehiculo);
        }else {
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo,"No se puede buscar un vehículo nulo.");
        if(coleccionVehiculos.contains(vehiculo)){
            int vehiculosIndex = coleccionVehiculos.indexOf(vehiculo);
            return coleccionVehiculos.get(vehiculosIndex);
        }else{
            return null;
        }

    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(vehiculo,"No se puede borrar un vehículo nulo.");
        if (coleccionVehiculos.contains(vehiculo)){
            coleccionVehiculos.remove(vehiculo);
        }else {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
    }

}
