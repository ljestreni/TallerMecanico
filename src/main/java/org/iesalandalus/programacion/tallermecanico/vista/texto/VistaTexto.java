package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.iesalandalus.programacion.tallermecanico.vista.texto.Consola.*;

public class VistaTexto implements Vista {
public GestorEventos gestorEventos;

    private Controlador controlador;
    @Override
    public GestorEventos getGestorEventos(){
        return gestorEventos;
    }

    public VistaTexto(){
        gestorEventos = new GestorEventos(Evento.values());
    }

    @Override
    public void comenzar() throws TallerMecanicoExcepcion {
        Evento evento;
        do {
            Consola.mostrarMenu();
            evento = Consola.elegirOpcion();
            ejecutar(evento);
        } while (evento != Evento.SALIR);
        controlador.terminar();
    }

    @Override
    public void terminar(){
        System.out.println("Me doy el piro, vampiro!");
    }

    private void ejecutar(Evento evento) throws TallerMecanicoExcepcion {

        switch(evento){
            case INSERTAR_CLIENTE -> gestorEventos.notificar(Evento.INSERTAR_CLIENTE);
            case INSERTAR_VEHICULO -> gestorEventos.notificar(Evento.INSERTAR_VEHICULO);
            case INSERTAR_TRABAJO -> gestorEventos.notificar(Evento.INSERTAR_TRABAJO);
            case BUSCAR_CLIENTE -> gestorEventos.notificar(Evento.BUSCAR_CLIENTE);
            case BUSCAR_VEHICULO -> gestorEventos.notificar(Evento.BUSCAR_VEHICULO);
            case BUSCAR_TRABAJO -> gestorEventos.notificar(Evento.BUSCAR_TRABAJO);
            case MODIFICAR_CLIENTE ->gestorEventos.notificar(Evento.MODIFICAR_CLIENTE);
            case ANADIR_HORAS_TRABAJO -> gestorEventos.notificar(Evento.ANADIR_HORAS_TRABAJO);
            case ANADIR_PRECIO_MATERIAL_TRABAJO -> gestorEventos.notificar(Evento.ANADIR_PRECIO_MATERIAL_TRABAJO);
            case BORRAR_CLIENTE -> gestorEventos.notificar(Evento.BORRAR_CLIENTE);
            case BORRAR_TRABAJO -> gestorEventos.notificar(Evento.BORRAR_TRABAJO);
            case CERRAR_TRABAJO -> gestorEventos.notificar(Evento.CERRAR_TRABAJO);
            case BORRAR_VEHICULO -> gestorEventos.notificar(Evento.BORRAR_VEHICULO);
            case LISTAR_CLIENTES -> gestorEventos.notificar(Evento.LISTAR_CLIENTES);
            case LISTAR_TRABAJOS -> gestorEventos.notificar(Evento.LISTAR_TRABAJOS);
            case LISTAR_TRABAJOS_CLIENTE -> gestorEventos.notificar(Evento.LISTAR_TRABAJOS_CLIENTE);
            case LISTAR_TRABAJOS_VEHICULO -> gestorEventos.notificar(Evento.LISTAR_TRABAJOS_VEHICULO);
            case LISTAR_VEHICULOS -> gestorEventos.notificar(Evento.LISTAR_VEHICULOS);
            case SALIR -> gestorEventos.notificar(Evento.SALIR);
        }
    }

    @Override
    public Cliente leerCliente(){
        String nombre = leerCadena("Introduce el nombre del cliente.");
        String dni = leerCadena("Introduce el dni del cliente.");
        String telefono = leerCadena("Introduce el telefono del cliente.");
        return new Cliente(nombre,dni,telefono);
    }

    @Override
    public Cliente leerClienteDni(){
        return Cliente.get(leerCadena("Introduce el dni del cliente."));
    }

    @Override
    public String leerNuevoNombre(){
        return leerCadena("Introduce un nuevo nombre.");
    }

    @Override
    public String leerNuevoTelefono(){
        return leerCadena("Introduce un nuevo telefono.");
    }

    @Override
    public Vehiculo leerVehiculo(){
        String marca = leerCadena("Introduce la marca del vehiculo.");
        String modelo = leerCadena("Introduce el modelo del vehiculo.");
        String matricula = leerCadena("Introduce la matricula del vehiculo.");
        return new Vehiculo(marca,modelo,matricula);
    }

    @Override
    public Vehiculo leerMatriculaVehiculo(){
        return Vehiculo.get(leerCadena("Introduce la matricula del vehiculo."));
    }

    @Override
    public Revision leerRevision() {
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        LocalDate fechaInicio = leerFecha("Introduce la fecha.");

        return new Revision(cliente,vehiculo,fechaInicio);
    }

    @Override
    public Mecanico leerMecanico() {
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        LocalDate fechaInicio = leerFecha("Introduce la fecha.");

        return new Mecanico(cliente,vehiculo,fechaInicio);
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        Vehiculo vehiculo = leerVehiculo();
        return Trabajo.copiar(Trabajo.get(vehiculo));
    }

    @Override
    public int leerHoras(){
        return Consola.leerEntero("Introduce las horas.");
    }

    @Override
    public float leerPrecioMaterial(){
        return Consola.leerReal("Introduce el precio del material.");
    }

    @Override
    public LocalDate leerFechaCierre(){
        return Consola.leerFecha("Introduzca la fecha de cierre.");
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito){
        if (exito){
            System.out.println(texto);
        }else {
            System.out.printf("ERROR: %s%n",texto);
        }
    }

    @Override
    public void mostarClientes(List<Cliente> clientes){
        Objects.requireNonNull(clientes,"Los clientes no pueden se nulos.");
        Consola.mostrarCabecera("Listado de clientes");
        if (!clientes.isEmpty()){
            for (Cliente cliente : clientes){
                System.out.println(cliente);
            }
        }else {
            System.out.println("La lista esta vacía.");
        }
    }

    @Override
    public void mostarVehiculos(List<Vehiculo> vehiculos){
        Objects.requireNonNull(vehiculos,"Los vehiculos no pueden se nulos.");
        Consola.mostrarCabecera("Listado de vehículos");
        if (!vehiculos.isEmpty()){
            for (Vehiculo vehiculo : vehiculos){
                System.out.println(vehiculo);
            }

        }else {
            System.out.println("La lista esta vacía.");
        }
    }

    @Override
    public void mostarTrabajos(List<Trabajo> trabajos){
        Objects.requireNonNull(trabajos,"Los trabajos no pueden se nulos.");
        Consola.mostrarCabecera("Listado de trabajos");
        if (!trabajos.isEmpty()){
            for (Trabajo trabajo : trabajos){
                System.out.println(trabajo);
            }

        }else {
            System.out.println("La lista esta vacía.");
        }
    }

    @Override
    public void mostarTrabajo(Trabajo trabajo){
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");
        System.out.println(trabajo);
    }

    @Override
    public void mostarVehiculo(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo,"El vehiculo no puede ser nulo.");
        System.out.println(vehiculo);
    }

    @Override
    public void mostarCliente(Cliente cliente){
        Objects.requireNonNull(cliente,"El cliente no puede ser nulo.");
        System.out.println(cliente);
    }

    @Override
    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador,"El controlador no puede ser nulo.");
        this.controlador = controlador;
    }


}
