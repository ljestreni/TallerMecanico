package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Trabajos implements ITrabajos {

    private List<Trabajo> trabajos;

    public Trabajos() {
        trabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(trabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : trabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }

    @Override
    public void insertar(Trabajo trabajo) {
        if (trabajo == null) {
            throw new NullPointerException("No se puede insertar un trabajo nulo.");
        }

        for (Trabajo t : trabajos) {
            if (t.getCliente().equals(trabajo.getCliente()) && !t.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
            }
            if (t.getVehiculo().equals(trabajo.getVehiculo()) && !t.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
            }
        }

        trabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        for (Trabajo t : trabajos) {
            if (t.getCliente().equals(cliente) && t.getVehiculo().equals(vehiculo) && !t.estaCerrada()) {
                throw new TallerMecanicoExcepcion("El cliente ya tiene un trabajo abierto para este vehículo.");
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo añadir horas a un trabajo nulo.");
        }

        if (!trabajos.contains(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        if (trabajo.estaCerrada()) {
            throw new TallerMecanicoExcepcion("El trabajo ya está cerrado, no se pueden añadir horas.");
        }

        trabajo.Horas(trabajo.getHoras() + horas);
        return trabajo;
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) {
        for (Trabajo t : trabajos) {
            if (t.getVehiculo().equals(vehiculo) && !t.estaCerrada()) {
                return t;
            }
        }
        return null;
    }


    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo añadir precio del material a un trabajo nulo.");
        }

        if (!trabajos.contains(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        if (trabajo instanceof Mecanico) {
            ((Mecanico) trabajo).setPrecioMaterial(precioMaterial);
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) {
        if (trabajo == null) {
            throw new NullPointerException("No puedo cerrar un trabajo nulo.");
        }

        if (!trabajos.contains(trabajo)) {
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

        for (Trabajo t : trabajos) {
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

        if (!trabajos.remove(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
    }
}
