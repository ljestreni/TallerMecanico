package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.List;

public interface Modelo {
    void comenzar();

    void terminar();

    void insertar(Cliente cliente);

    void insertar(Vehiculo vehiculo);

    void insertar(Trabajo trabajo);

    Cliente buscar(Cliente cliente);

    Vehiculo buscar(Vehiculo vehiculo);

    Trabajo buscar(Trabajo trabajo);

    Cliente modificar(Cliente cliente, String nombre, String telefono);

    Trabajo anadirHoras(Trabajo trabajo, int horas);

    Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial);

    Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin);

    void borrar(Cliente cliente);

    void borrar(Vehiculo vehiculo);

    void borrar(Trabajo trabajo);

    List<Cliente> getClientes();

    List<Vehiculo> getVehiculos();

    List<Trabajo> getTrabajos();

    List<Trabajo> getTrabajos(Cliente cliente);

    List<Trabajo> getTrabajos(Vehiculo vehiculo);
}
