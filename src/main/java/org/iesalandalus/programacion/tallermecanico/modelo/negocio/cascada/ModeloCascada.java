package org.iesalandalus.programacion.tallermecanico.modelo.negocio.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.Modelo {

    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos, "La factoría de la fuente de datos no puede ser nula.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
    }

    @Override
    public void comenzar() {
        System.out.println("Modelo comenzado.");
    }

    @Override
    public void terminar() {
        System.out.println("Modelo terminado.");
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Cliente cliente = clientes.buscar(trabajo.getCliente());
        Vehiculo vehiculo = vehiculos.buscar(trabajo.getVehiculo());
        if (trabajo instanceof Revision) {
            trabajo = new Revision(cliente, vehiculo, trabajo.getFechaInicio());
        } else {
            trabajo = new Mecanico(cliente, vehiculo, trabajo.getFechaInicio());
        }
        trabajos.insertar(trabajo);
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        cliente = Objects.requireNonNull(clientes.buscar(cliente), "No existe un cliente igual.");
        return new Cliente(cliente);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        vehiculo = Objects.requireNonNull(vehiculos.buscar(vehiculo), "No existe un vehículo igual.");
        return vehiculo;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Trabajo encontrado = trabajos.buscar(trabajo);
        System.out.println("Trabajo encontrado.");
        return encontrado;
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) {
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        clientes.modificar(cliente);
        System.out.println("Cliente modificado.");
        return cliente;
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) {
        trabajos.anadirHoras(trabajo, horas);
        System.out.println("Horas añadidas.");
        return trabajo;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) {
        trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
        System.out.println("Precio de material añadido.");
        return trabajo;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) {
        trabajos.cerrar(trabajo, fechaFin);
        System.out.println("Trabajo cerrado.");
        return trabajo;
    }

    @Override
    public void borrar(Cliente cliente) {
        clientes.borrar(cliente);
        System.out.println("Cliente borrado.");
    }

    @Override
    public void borrar(Vehiculo vehiculo) {
        vehiculos.borrar(vehiculo);
        System.out.println("Vehículo borrado.");
    }

    @Override
    public void borrar(Trabajo trabajo) {
        trabajos.borrar(trabajo);
        System.out.println("Trabajo borrado.");
    }

    @Override
    public List<Cliente> getClientes() {
        return clientes.get();
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculos.get();
    }

    @Override
    public List<Trabajo> getTrabajos() {
        return trabajos.get();
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        return trabajos.get(cliente);
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        return trabajos.get(vehiculo);
    }
}
