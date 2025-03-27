package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Trabajos implements ITrabajos {

    private List<Trabajo> coleccionTrabajos;

    public Trabajos() {coleccionTrabajos = new ArrayList<>();}

    @Override
    public List<Trabajo> get() {return new ArrayList<>(coleccionTrabajos);}

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }

    @Override
    public void insertar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        for (Trabajo t : coleccionTrabajos) {
            if (t.getCliente().equals(cliente) && t.getVehiculo().equals(vehiculo) && !t.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El cliente ya tiene un trabajo abierto para este vehículo.");
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) {
       Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
       Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
       trabajoEncontrado.anadirHoras(horas);
       return trabajoEncontrado;
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No puedo operar sobre un vehículo nulo.");
        Trabajo trabajoEncontrado = null;
        Iterator<Trabajo> iteradorTrabajos = coleccionTrabajos.iterator();
        while (iteradorTrabajos.hasNext() && trabajoEncontrado == null) {
            Trabajo trabajo = iteradorTrabajos.next();
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrada()) {
                trabajoEncontrado = trabajo;
            }
        }
        if (trabajoEncontrado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return trabajoEncontrado;
    }


    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoEncontrado = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoEncontrado instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de coleccionTrabajos.");
        }
        return trabajoEncontrado;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo cerrar un trabajo nulo.");
        }

        if (!coleccionTrabajos.contains(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        trabajo.setFechaFin(fechaFin);
        return trabajo;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        if (trabajo == null) {
            throw new NullPointerException("No se puede buscar un trabajo nulo.");
        }

        for (Trabajo t : coleccionTrabajos) {
            if (t.equals(trabajo)) {
                return t;
            }
        }

        return null;
    }

    @Override
    public void borrar(Trabajo trabajo) {
        if (trabajo == null) {
            throw new NullPointerException("No se puede borrar un trabajo nulo.");
        }

        if (!coleccionTrabajos.remove(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
    }
}
