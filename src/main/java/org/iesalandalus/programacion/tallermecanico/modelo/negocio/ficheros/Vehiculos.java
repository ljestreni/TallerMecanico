package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos {
    Vehiculos coleccionVehiculos;
    private static Vehiculos instancia;
    List <Vehiculo> listaVehiculos;

    private static final String FICHERO_VEHICULOS = String.format("%s%s%s", "datos" , File.separator, "vehiculos.xml");
    private static final String RAIZ = "vehiculos";
    private static final String VEHICULO = "vehiculo";
    private static final String MARCA = "marca";
    private static final String MATRICULA = "matricula";
    private static final String MODELO = "modelo";

    private Vehiculos(){
        listaVehiculos = new ArrayList<>();
    }

    static Vehiculos getInstancia(){
        if (instancia == null){
            instancia = new Vehiculos();
        }
        return instancia;
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList vehiculos = documentoXml.getElementsByTagName(VEHICULO);
        for (int i = 0; i < vehiculos.getLength(); i++) {
            Node vehiculo = vehiculos.item(i);
            if (vehiculo.getNodeType() == Node.ELEMENT_NODE) {
                try {
                    insertar(getVehiculo((Element) vehiculo) );
                } catch (TallerMecanicoExcepcion | IllegalArgumentException | NullPointerException e) {
                    System.out.printf("Error al procesar el vehiculo %s : %s", i, e.getMessage());
                }
            }
        }
    }
    private Vehiculo getVehiculo(Element elemento){
        String matricula = ((Element)elemento).getAttribute(MATRICULA);
        String marca = ((Element)elemento).getAttribute(MARCA);
        String modelo = ((Element)elemento).getAttribute(MODELO);
        return new Vehiculo(marca,modelo,matricula);
    }

    @Override
    public List <Vehiculo> get(){
        List <Vehiculo> nuevaListaVehiculos;
        nuevaListaVehiculos = listaVehiculos;
        return nuevaListaVehiculos;
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        if (!listaVehiculos.contains(vehiculo)){
            listaVehiculos.add(vehiculo);
        } else {
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo){

        Objects.requireNonNull(vehiculo,"No se puede buscar un vehículo nulo.");
        int indexVehiculo;
        if (listaVehiculos.contains(vehiculo)){
            indexVehiculo = listaVehiculos.indexOf(vehiculo);
            return listaVehiculos.get(indexVehiculo);
        }
        return null;
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Objects.requireNonNull(vehiculo,"No se puede borrar un vehículo nulo.");
        if (listaVehiculos.contains(vehiculo)){
            listaVehiculos.remove(vehiculo);
        } else {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }

    }

    @Override
    public void comenzar() {
        Document documentoXML = UtilidadesXml.leerDocumentoXml(FICHERO_VEHICULOS);
        if (documentoXML != null){
            procesarDocumentoXml(documentoXML);
            System.out.println("Fichero leído correctamente.");
        }
    }

    @Override
    public void terminar() {
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_VEHICULOS);
        System.out.println("Fichero vehículos escrito correctamente.");
    }

    private Document crearDocumentoXml() {
        DocumentBuilder contructorVehiculo = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (contructorVehiculo != null){
            documentoXml = contructorVehiculo.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Vehiculo vehiculo : listaVehiculos){
                Element elementoVehiculos = getElemento(documentoXml, vehiculo);
                documentoXml.getDocumentElement().appendChild(elementoVehiculos);
            }
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml , Vehiculo vehiculo){
        Element elementoVehiculo = documentoXml.createElement(VEHICULO);
        elementoVehiculo.setAttribute(MARCA,vehiculo.marca());
        elementoVehiculo.setAttribute(MODELO,vehiculo.modelo());
        elementoVehiculo.setAttribute(MATRICULA, vehiculo.matricula());
        return elementoVehiculo;
    }

}