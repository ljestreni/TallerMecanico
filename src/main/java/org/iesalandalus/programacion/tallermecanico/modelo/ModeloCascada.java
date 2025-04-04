package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements Modelo {

    private IClientes clientes;
    private IVehiculos vehiculos;
    private ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos){
        Objects.requireNonNull(fabricaFuenteDatos,"No se puede trabajar con unos datos nulos.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        clientes = fuenteDatos.crearClientes();
        vehiculos = fuenteDatos.crearVehiculos();
        trabajos = fuenteDatos.crearTrabajos();
        comenzar();
    }

    @Override
    public void comenzar(){
        System.out.println("Modelo comenzado");
    }

    @Override
    public void terminar(){
        System.out.println("El modelo ha terminado.");
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"El cliente no puede ser nulo.");
        Cliente cliente1 = new Cliente(cliente);
        clientes.insertar(cliente1);
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(vehiculo,"El vehiculo no puede ser nulo.");
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"La revisión no puede ser nula.");
        Trabajo trabajoInsertar = null;
        if (trabajo instanceof Revision revision){
            trabajoInsertar = new Revision(clientes.buscar(trabajo.getCliente()),vehiculos.buscar(trabajo.getVehiculo()),trabajo.getFechaInicio());
        } else if (trabajo instanceof Mecanico mecanico){
            trabajoInsertar = new Mecanico(clientes.buscar(trabajo.getCliente()),vehiculos.buscar(trabajo.getVehiculo()),trabajo.getFechaInicio());
        }
        trabajos.insertar(trabajoInsertar);
    }

    @Override
    public Cliente buscar(Cliente cliente){

        return new Cliente(clientes.buscar(cliente));
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo){
        return Trabajo.copiar(trabajos.buscar(trabajo));
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono)throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"El cliente no puede ser nulo.");
        Objects.requireNonNull(nombre,"El nombre no puede ser nulo.");
        Objects.requireNonNull(telefono,"El teléfono no puede ser nulo.");
        clientes.modificar(cliente,nombre,telefono);
        return new Cliente(cliente);
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"La revisión no puede ser nula.");
        trabajos.anadirHoras(trabajo, horas);
        return Trabajo.copiar(trabajo);
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"La revisión no puede ser nula.");
        return trabajos.anadirPrecioMaterial(trabajo,precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"La revisión no puede ser nula.");
        Objects.requireNonNull(fechaFin,"La fecha no puede ser nula.");

        trabajos.cerrar(trabajo,fechaFin);
        return Trabajo.copiar(trabajo);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"El cliente no pude ser nulo.");
        for (Trabajo trabajosCliente : trabajos.get(cliente)){
            vehiculos.borrar(trabajosCliente.getVehiculo());
            trabajos.borrar(trabajosCliente);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(vehiculo,"El vehiculo no puede ser nulo.");
        for (Trabajo trabajo : trabajos.get(vehiculo)){
            trabajos.borrar(trabajo);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(trabajo,"La revisión no puede ser nula.");
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> nuevosClientes = new ArrayList<>();
        for (Cliente cliente: clientes.get()){

            nuevosClientes.add(new Cliente(cliente));
        }
        return nuevosClientes;
    }

    @Override
    public List<Vehiculo> getVehiculos() {

        return new ArrayList<>(vehiculos.get());
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> nuevasTrabajos = new ArrayList<>();

        for (Trabajo trabajo : trabajos.get()){
            nuevasTrabajos.add(Trabajo.copiar(trabajo));
        }
        return nuevasTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente){

        List<Trabajo> nuevasTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(cliente)){
            nuevasTrabajos.add(Trabajo.copiar(trabajo));
        }
        return nuevasTrabajos;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo){
        List<Trabajo> nuevasTrabajos = new ArrayList<>();
        for (Trabajo trabajo : trabajos.get(vehiculo)){
            nuevasTrabajos.add(Trabajo.copiar(trabajo));
        }
        return nuevasTrabajos;
    }
}