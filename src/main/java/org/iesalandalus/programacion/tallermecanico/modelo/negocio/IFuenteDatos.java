package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

public interface IFuenteDatos {
    IClientes crearClientes();

    ITrabajos crearTrabajos();

    IVehiculos crearVehiculos();
}
