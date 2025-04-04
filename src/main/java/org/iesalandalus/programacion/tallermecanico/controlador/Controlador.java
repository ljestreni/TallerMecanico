package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;

import java.util.Objects;

public class Controlador implements IControlador {
    private Modelo modelo;
    private Vista vista;
    public Controlador(Modelo modelo, Vista vista){
        Objects.requireNonNull(modelo,"El modelo no puede ser nulo.");
        Objects.requireNonNull(vista,"La vista no puede ser nula.");
        this.vista = vista;
        this.modelo = modelo;
        this.vista.setControlador(this);
        vista.getGestorEventos().suscribir(this,Evento.values());
    }

    @Override
    public void comenzar() throws TallerMecanicoExcepcion {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void actualizar(Evento evento){
        String resultado = "";
        try {
            switch (evento){
                case INSERTAR_CLIENTE -> {modelo.insertar(vista.leerCliente()); resultado = "Cliente insertado correctamente.";}
                case INSERTAR_VEHICULO -> {modelo.insertar(vista.leerVehiculo());resultado= "Vehiculo insertado correctamente.";}
                case INSERTAR_REVISION -> {modelo.insertar(vista.leerRevision());resultado = "Revisión añadida correctamente.";}
                case INSERTAR_MECANICO -> {modelo.insertar(vista.leerMecanico());resultado= "Trabajo mecánico añadido correctamente.";}
                case BUSCAR_CLIENTE -> vista.mostarCliente(modelo.buscar(vista.leerClienteDni()));
                case BUSCAR_VEHICULO -> vista.mostarVehiculo(modelo.buscar(vista.leerMatriculaVehiculo()));
                case BUSCAR_TRABAJO -> vista.mostarTrabajo(modelo.buscar(vista.leerRevision()));
                case MODIFICAR_CLIENTE -> {modelo.modificar(vista.leerClienteDni(), vista.leerNuevoNombre(), vista.leerNuevoTelefono()); resultado = "El cliente ha sido modificado correctamente.";}
                case ANADIR_HORAS_TRABAJO -> {modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras()); resultado = "Horas añadidas correctamente.";}
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {modelo.anadirPrecioMaterial(vista.leerMecanico(), vista.leerPrecioMaterial()); resultado = "Precio de material añadido correctamente.";}
                case CERRAR_TRABAJO -> {modelo.cerrar(vista.leerRevision(), vista.leerFechaCierre());resultado = "El trabajo se ha cerrado correctamente";}
                case BORRAR_CLIENTE -> {modelo.borrar(vista.leerClienteDni()); resultado = "Cliente borrado correctamente.";}
                case BORRAR_TRABAJO -> {modelo.borrar(vista.leerRevision()); resultado = "Trabajo borrado correctamente.";}
                case BORRAR_VEHICULO -> {modelo.borrar(vista.leerMatriculaVehiculo());resultado = "Vehiculo borrado correctamente.";}
                case LISTAR_CLIENTES -> vista.mostarClientes(modelo.getClientes());
                case LISTAR_VEHICULOS -> vista.mostarVehiculos(modelo.getVehiculos());
                case LISTAR_TRABAJOS -> vista.mostarTrabajos(modelo.getTrabajos());
                case LISTAR_TRABAJOS_CLIENTE -> vista.mostarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));
                case LISTAR_TRABAJOS_VEHICULO -> vista.mostarTrabajos(modelo.getTrabajos(vista.leerMatriculaVehiculo()));
                case SALIR -> terminar();
            }
            if (!resultado.isBlank()){
                vista.notificarResultado(evento,resultado,true);
            }
        } catch (Exception e) {
            vista.notificarResultado(evento,e.getMessage(),false);
        }
    }

    @Override
    public void terminar(){
        vista.terminar();
        modelo.terminar();
    }

}
