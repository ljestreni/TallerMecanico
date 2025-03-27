package org.iesalandalus.programacion.tallermecanico.modelo.negocio.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;

import java.time.LocalDate;
import java.util.List;

public class ModeloCascada implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.Modelo {

    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        this.clientes = fabricaFuenteDatos.crear().crearClientes();
        this.vehiculos = fabricaFuenteDatos.crear().crearVehiculos();
        this.trabajos = fabricaFuenteDatos.crear().crearTrabajos();
    }

    @Override
    public void comenzar() {
        System.out.println("El modelo ha comenzado.");
    }

    @Override
    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    @Override
    public void insertar(Cliente cliente) {
        clientes.insertar(cliente);
        System.out.println("Cliente insertado.");
    }

    @Override
    public void insertar(Vehiculo vehiculo) {
        vehiculos.insertar(vehiculo);
        System.out.println("Vehículo insertado.");
    }

    @Override
    public void insertar(Trabajo trabajo) {
        trabajos.insertar(trabajo);
        System.out.println("Trabajo insertado.");
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Cliente encontrado = clientes.buscar(cliente);
        System.out.println("Cliente encontrado.");
        return encontrado;
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Vehiculo encontrado = vehiculos.buscar(vehiculo);
        System.out.println("Vehículo encontrado.");
        return encontrado;
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
