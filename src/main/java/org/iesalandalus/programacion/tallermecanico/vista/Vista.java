package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.util.List;

public class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        System.out.println("Saliendo de la aplicación...");
    }

    private void ejecutar(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_CLIENTE -> insertarCliente();
            case INSERTAR_VEHICULO -> insertarVehiculo();
            case INSERTAR_REVISION -> insertarRevision();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BUSCAR_VEHICULO -> buscarVehiculo();
            case BUSCAR_REVISION -> buscarRevision();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case AÑADIR_HORAS_REVISION -> añadirHoras();
            case AÑADIR_PRECIO_MATERIAL_REVISION -> añadirPrecioMaterial();
            case CERRAR_REVISION -> cerrarRevision();
            case BORRAR_CLIENTE -> borrarCliente();
            case BORRAR_VEHICULO -> borrarVehiculo();
            case BORRAR_REVISION -> borrarRevision();
            case LISTAR_CLIENTES -> listarClientes();
            case LISTAR_VEHICULOS -> listarVehiculos();
            case LISTAR_REVISIONES -> listarRevisiones();
            case LISTAR_REVISIONES_CLIENTE -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULO -> listarRevisionesVehiculo();
            case SALIR -> salir();
        }
    }

    private void insertarCliente() {
        Consola.mostrarCabecera("Insertar Cliente");
        Cliente cliente = Consola.leerCliente();
        controlador.insertar(cliente);
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("Insertar Vehículo");
        Vehiculo vehiculo = Consola.leerVehiculo();
        controlador.insertar(vehiculo);
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("Insertar Revisión");
        Revision revision = Consola.leerRevision();
        controlador.insertar(revision);
    }

    private void buscarCliente() {
        Consola.mostrarCabecera("Buscar Cliente");
        Cliente cliente = Consola.leerClienteDni();
        System.out.println(controlador.buscar(cliente));
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("Buscar Vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        System.out.println(controlador.buscar(vehiculo));
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("Buscar Revisión");
        Revision revision = Consola.leerRevision();
        System.out.println(controlador.buscar(revision));
    }

    private void modificarCliente() {
        Consola.mostrarCabecera("Modificar Cliente");
        Cliente cliente = Consola.leerClienteDni();
        String nuevoNombre = Consola.leerNuevoNombre();
        String nuevoTelefono = Consola.leerNuevoTelefono();
        controlador.modificar(cliente, nuevoNombre, nuevoTelefono);
    }

    private void añadirHoras() {
        Consola.mostrarCabecera("Añadir Horas a Revisión");
        Revision revision = Consola.leerRevision();
        int horas = Consola.leerHoras();
        controlador.añadirHoras(revision, horas);
    }

    private void añadirPrecioMaterial() {
        Consola.mostrarCabecera("Añadir Precio de Material a Revisión");
        Revision revision = Consola.leerRevision();
        float precio = Consola.leerPrecioMaterial();
        controlador.añadirPrecioMaterial(revision, precio);
    }

    private void cerrarRevision() {
        Consola.mostrarCabecera("Cerrar Revisión");
        Revision revision = Consola.leerRevision();
        controlador.cerrar(revision, Consola.leerFechaCierre());
    }

    private void borrarCliente() {
        Consola.mostrarCabecera("Borrar Cliente");
        Cliente cliente = Consola.leerClienteDni();
        controlador.borrar(cliente);
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("Borrar Vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        controlador.borrar(vehiculo);
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("Borrar Revisión");
        Revision revision = Consola.leerRevision();
        controlador.borrar(revision);
    }

    private void listarClientes() {
        Consola.mostrarCabecera("Listado de Clientes");
        List<Cliente> clientes = controlador.getClientes();
        clientes.forEach(System.out::println);
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("Listado de Vehículos");
        List<Vehiculo> vehiculos = controlador.getVehiculos();
        vehiculos.forEach(System.out::println);
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("Listado de Revisiones");
        List<Revision> revisiones = controlador.getRevisiones();
        revisiones.forEach(System.out::println);
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("Listado de Revisiones por Cliente");
        Cliente cliente = Consola.leerClienteDni();
        List<Revision> revisiones = controlador.getRevisiones(cliente);
        revisiones.forEach(System.out::println);
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("Listado de Revisiones por Vehículo");
        Vehiculo vehiculo = Consola.leerVehiculoMatricula();
        List<Revision> revisiones = controlador.getRevisiones(vehiculo);
        revisiones.forEach(System.out::println);
    }

    private void salir() {
        terminar();
    }
}