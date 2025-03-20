package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import java.time.LocalDate;
import java.util.List;

public class Controlador {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        if (modelo == null) {
            throw new NullPointerException("El modelo no puede ser nulo.");
        }
        if (vista == null) {
            throw new NullPointerException("La vista no puede ser nula.");
        }
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    public void insertar(Cliente cliente) {
        modelo.insertar(cliente);
    }

    public void insertar(Vehiculo vehiculo) {
        modelo.insertar(vehiculo);
    }

    public void insertar(Revision revision) {
        modelo.insertar(revision);
    }

    public Cliente buscar(Cliente cliente) {
        return modelo.buscar(cliente);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return modelo.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) {
        return modelo.buscar(revision);
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) {
        return modelo.modificar(cliente, nombre, telefono);
    }

    public Revision añadirHoras(Revision revision, int horas) {
        return modelo.añadirHoras(revision, horas);
    }

    public Revision añadirPrecioMaterial(Revision revision, float precioMaterial) {
        return modelo.añadirPrecioMaterial(revision, precioMaterial);
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) {
        return modelo.cerrar(revision, fechaFin);
    }

    public void borrar(Cliente cliente) {
        modelo.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) {
        modelo.borrar(vehiculo);
    }

    public void borrar(Revision revision) {
        modelo.borrar(revision);
    }

    public List<Cliente> getClientes() {
        return modelo.getClientes();
    }

    public List<Vehiculo> getVehiculos() {
        return modelo.getVehiculos();
    }

    public List<Revision> getRevisiones() {
        return modelo.getRevisiones();
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        return modelo.getRevisiones(cliente);
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        return modelo.getRevisiones(vehiculo);
    }
}
