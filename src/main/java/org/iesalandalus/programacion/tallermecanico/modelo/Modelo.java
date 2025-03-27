package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Modelo {
    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
        System.out.println("El modelo ha comenzado.");
    }

    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    public void insertar(Cliente cliente) {
        clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) {
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) {
        Cliente clienteEncontrado = clientes.buscar(revision.getCliente());
        Vehiculo vehiculoEncontrado = vehiculos.buscar(revision.getVehiculo());
        if (clienteEncontrado == null || vehiculoEncontrado == null) {
            throw new TallerMecanicoExcepcion("Cliente o vehículo no encontrado para la revisión.");
        }
        revisiones.insertar(new Revision(clienteEncontrado, vehiculoEncontrado, revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) {
        Cliente encontrado = clientes.buscar(cliente);
        return (encontrado != null) ? new Cliente(encontrado) : null;
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) {
        Revision encontrada = revisiones.buscar(revision);
        return (encontrada != null) ? new Revision(encontrada) : null;
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) {
        return clientes.modificar(cliente, nombre, telefono);
    }

    public Revision añadirHoras(Revision revision, int horas) {
        return revisiones.añadirHoras(revision, horas);
    }

    public Revision añadirPrecioMaterial(Revision revision, float precioMaterial) {
        return revisiones.añadirPrecioMaterial(revision, precioMaterial);
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) {
        return revisiones.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) {
        List<Revision> revisionesCliente = getRevisiones(cliente);
        for (Revision revision : revisionesCliente) {
            revisiones.borrar(revision);
        }
        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) {
        List<Revision> revisionesVehiculo = getRevisiones(vehiculo);
        for (Revision revision : revisionesVehiculo) {
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) {
        revisiones.borrar(revision);
    }

    public List<Cliente> getClientes() {
        List<Cliente> copiaClientes = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copiaClientes.add(new Cliente(cliente));
        }
        return copiaClientes;
    }

    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos.get());
    }

    public List<Revision> getRevisiones() {
        List<Revision> copiaRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            copiaRevisiones.add(new Revision(revision));
        }
        return copiaRevisiones;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> copiaRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            copiaRevisiones.add(new Revision(revision));
        }
        return copiaRevisiones;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> copiaRevisiones = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            copiaRevisiones.add(new Revision(revision));
        }
        return copiaRevisiones;
    }
}