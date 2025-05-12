package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clientes implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes {
    private static final String FICHERO_CLIENTES = String.format("%s%s%s", "datos" , File.separator, "clientes.xml");
    private static final String RAIZ = "clientes";
    private static final String CLIENTE = "cliente";
    private static final String NOMBRE = "nombre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";

    Cliente coleccionClientes;
    private static Clientes instancia;

    static Clientes getInstancia(){
        if (instancia == null){
            instancia = new Clientes();
        }
        return instancia;
    }

    private void procesarDocumentoXml(Document documentoXml) {

        NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
        for (int i = 0; i < clientes.getLength(); i++) {
            Node cliente = clientes.item(i);
            if (cliente.getNodeType() == Node.ELEMENT_NODE) {
                try {
                    insertar(getCliente((Element) cliente));
                } catch (TallerMecanicoExcepcion | IllegalArgumentException | NullPointerException e) {
                    System.out.printf("Error al procesar el cliente %s : %s", i, e.getMessage());
                }
            }
        }
    }

    private Document crearDocumentoXml() {
        DocumentBuilder contructorCliente = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (contructorCliente != null){
            documentoXml = contructorCliente.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Cliente cliente : clientes){
                Element elementoCliente = getElemento(documentoXml, cliente);
                documentoXml.getDocumentElement().appendChild(elementoCliente);
            }
        }
        return documentoXml;

    }


    private Element getElemento(Document documentoXml , Cliente cliente){
        Element elementoCliente = documentoXml.createElement(CLIENTE);
        elementoCliente.setAttribute(DNI,cliente.getDni());
        elementoCliente.setAttribute(NOMBRE,cliente.getNombre());
        elementoCliente.setAttribute(TELEFONO,cliente.getTelefono());
        return elementoCliente;
    }

    private Cliente getCliente(Element elemento){
        String nombre = (elemento).getAttribute(NOMBRE);
        String dni = (elemento).getAttribute(DNI);
        String telefono = (elemento).getAttribute(TELEFONO);
        return new Cliente(nombre,dni,telefono);
    }


    List<Cliente> clientes;
    private Clientes(){
        clientes = new ArrayList<>();
    }

    @Override
    public List<Cliente> get(){
        List <Cliente> nuevaCliente;
        nuevaCliente = clientes;
        return nuevaCliente;
    }

    @Override
    public void insertar(Cliente cliente)throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"No se puede insertar un cliente nulo.");
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
        } else {
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
    }

    @Override
    public boolean modificar(Cliente cliente, String nombre, String telefono)throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");

        if (clientes.contains(cliente)){



            if (nombre != null && !nombre.isBlank()){
                buscar(cliente).setNombre(nombre);
            }

            if (telefono != null && !telefono.isBlank()){
                buscar(cliente).setTelefono(telefono);
            }

            return true;

        } else {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
    }

    @Override
    public Cliente buscar(Cliente cliente){
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        if (clientes.contains(cliente)){
            int indexCliente = clientes.indexOf(cliente);
            return clientes.get(indexCliente);
        } else {
            return null;
        }
    }

    @Override
    public void borrar(Cliente cliente)throws TallerMecanicoExcepcion{
        Objects.requireNonNull(cliente,"No se puede borrar un cliente nulo.");
        if (clientes.contains(cliente)){
            clientes.remove(cliente);
        } else {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
    }

    public void comenzar() {
        Document documentoXML = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        if (documentoXML != null){
            procesarDocumentoXml(documentoXML);
            System.out.println("Fichero leído correctamente.");
        }
    }

    public void terminar() {
        Document documentXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentXml,FICHERO_CLIENTES);
        System.out.println("Fichero clientes escrito correctamente.");

    }


}
