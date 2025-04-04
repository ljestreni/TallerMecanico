package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    List<Cliente> coleccionClientes = new ArrayList<>();

    public Clientes(){
        coleccionClientes = new ArrayList<>();
    }

    @Override
    public List<Cliente> get() {
        List<Cliente> nuevoCliente;
        nuevoCliente = coleccionClientes;
        return coleccionClientes;
    }

    @Override
    public void insertar(Cliente cliente)throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (!coleccionClientes.contains(cliente)){
                coleccionClientes.add(cliente);
        }else {
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");

        if (coleccionClientes.contains(cliente)){

            if ((nombre != null) && !nombre.isBlank()){
                buscar(cliente).setNombre(nombre);
            }

            if ((telefono != null) && !telefono.isBlank()){
                buscar(cliente).setTelefono(telefono);
            }

            return buscar(cliente);
        }

        if (!coleccionClientes.contains(cliente)){
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        return buscar(cliente);
    }

    @Override
    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente,"No se puede buscar un cliente nulo.");
        if (coleccionClientes.contains(cliente)){
            int clientesIndex = coleccionClientes.indexOf(cliente);
            return coleccionClientes.get(clientesIndex);
        }else{
            return null;
        }
    }

    @Override
    public void borrar(Cliente cliente)throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"No se puede borrar un cliente nulo.");
        if (coleccionClientes.contains(cliente)){
            coleccionClientes.remove(cliente);
        }else {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
    }





}
