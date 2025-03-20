package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Clientes {

    private List<Cliente> coleccionClientes;

    public Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    public List<Cliente> get() {
        List<Cliente> copiaClientes = new ArrayList<>();
        for (Cliente cliente : coleccionClientes) {
            copiaClientes.add(new Cliente(cliente));
        }
        return Collections.unmodifiableList(copiaClientes);
    }

    public void insertar(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede insertar un cliente nulo.");
        }
        if (coleccionClientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
        coleccionClientes.add(new Cliente(cliente));
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) {
        if (cliente == null) {
            throw new NullPointerException("No se puede modificar un cliente nulo.");
        }
        Cliente clienteExistente = buscar(cliente);
        if (clienteExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        if (nombre != null && !nombre.isBlank()) {
            clienteExistente.setNombre(nombre);
        }
        if (telefono != null && !telefono.isBlank()) {
            clienteExistente.setTelefono(telefono);
        }


        return clienteExistente;
    }

    public Cliente buscar(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede buscar un cliente nulo.");
        }
        int index = coleccionClientes.indexOf(cliente);
        return (index != -1) ? new Cliente(coleccionClientes.get(index)) : null;
    }

    public void borrar(Cliente cliente) {
        if (cliente == null) {
            throw new NullPointerException("No se puede borrar un cliente nulo.");
        }
        if (!coleccionClientes.remove(cliente)) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
    }
}