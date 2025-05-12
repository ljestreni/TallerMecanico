package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Vista {
    int leerHoras();

    float leerPrecioMaterial();

    LocalDate leerFechaCierre();

    GestorEventos getGestorEventos();

    void comenzar() throws TallerMecanicoExcepcion;

    void terminar();


    Cliente leerCliente();

    Cliente leerClienteDNI();

    String leerNuevoNombre();

    String leerNuevoTelefono();

    Vehiculo leerVehiculo();

    Vehiculo leerVehiculoMatricula();

    Revision leerRevision();

    Mecanico leerMecanico();

    Trabajo leerTrabajoVehiculo();

    void notificarResultado(Evento evento, String texto, boolean exito);

    void mostrarClientes(List<Cliente> clientes);

    void mostrarVehiculos(List<Vehiculo> vehiculos);

    void mostrarTrabajos(List<Trabajo> trabajos);

    void mostrarCliente(Cliente cliente);

    void mostrarTrabajo(Trabajo trabajo);

    void mostrarVehiculo(Vehiculo vehiculo);

    LocalDate leerMes();

    void mostrarEstadisticas(Map<TipoTrabajo,Integer> estadistica);
}