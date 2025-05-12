package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;



import java.util.Objects;

public class Controlador implements IControlador {
    private Modelo modelo;
    private Vista vista;
    public Controlador(FabricaModelo fabricaModelo, FabricaFuenteDatos fabricaFuenteDatos, FabricaVista fabricaVista){
        Objects.requireNonNull(fabricaModelo,"El modelo no puede ser nulo.");
        Objects.requireNonNull(fabricaVista,"La vista no puede ser nula.");
        Objects.requireNonNull(fabricaFuenteDatos,"La fuente de datos no puede ser nula.");
        modelo = fabricaModelo.crear(fabricaFuenteDatos);
        vista = fabricaVista.crear();
        this.vista.getGestorEventos().suscribir(this, Evento.values());
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
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.leerClienteDNI()));
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculoMatricula()));
                case BUSCAR_TRABAJO -> vista.mostrarTrabajo(modelo.buscar(vista.leerRevision()));
                case MODIFICAR_CLIENTE -> {modelo.modificar(vista.leerClienteDNI(), vista.leerNuevoNombre(), vista.leerNuevoTelefono()); resultado = "El cliente ha sido modificado correctamente.";}
                case ANADIR_HORAS_TRABAJO -> {modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras()); resultado = "Horas añadidas correctamente.";}
                case ANADIR_PRECIO_MATERIAL_MECANICO -> {modelo.anadirPrecioMaterial(vista.leerMecanico(), vista.leerPrecioMaterial()); resultado = "Precio de material añadido correctamente.";}
                case CERRAR_TRABAJO -> {modelo.cerrar(vista.leerRevision(), vista.leerFechaCierre());resultado = "El trabajo se ha cerrado correctamente";}
                case BORRAR_CLIENTE -> {modelo.borrar(vista.leerClienteDNI()); resultado = "Cliente borrado correctamente.";}
                case BORRAR_TRABAJO -> {modelo.borrar(vista.leerRevision()); resultado = "Trabajo borrado correctamente.";}
                case BORRAR_VEHICULO -> {modelo.borrar(vista.leerVehiculoMatricula());resultado = "Vehiculo borrado correctamente.";}
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDNI()));
                case LISTAR_TRABAJOS_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));
                case MOSTRAR_ESTADISTICAS_MENSUALES -> vista.mostrarEstadisticas(modelo.getEstadisticasMensuales(vista.leerMes()));
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
    public void terminar() throws TallerMecanicoExcepcion {
        vista.terminar();
        modelo.terminar();
    }


}