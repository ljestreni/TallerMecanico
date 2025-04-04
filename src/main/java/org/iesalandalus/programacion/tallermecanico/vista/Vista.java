package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public interface Vista {
    GestorEventos getGestorEventos();

    void comenzar() throws TallerMecanicoExcepcion;

    void terminar();

    Cliente leerCliente();

    Cliente leerClienteDni();

    String leerNuevoNombre();

    String leerNuevoTelefono();

    Vehiculo leerVehiculo();

    Vehiculo leerMatriculaVehiculo();

    Revision leerRevision();

    Mecanico leerMecanico();

    Trabajo leerTrabajoVehiculo();

    int leerHoras();

    float leerPrecioMaterial();

    LocalDate leerFechaCierre();

    void notificarResultado(Evento evento, String texto, boolean exito);

    void mostarClientes(List<Cliente> clientes);

    void mostarVehiculos(List<Vehiculo> vehiculos);

    void mostarTrabajos(List<Trabajo> trabajos);

    void mostarTrabajo(Trabajo trabajo);

    void mostarVehiculo(Vehiculo vehiculo);

    void mostarCliente(Cliente cliente);

    void setControlador(Controlador controlador);
}
