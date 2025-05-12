
package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {

    List<Trabajo> coleccionTrabajos;

    private static final String FICHERO_TRABAJO = String.format("%s%s%s","datos", File.separator,"trabajos.xml");
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ ="trabajos";
    private static final String TRABAJO = "trabajo";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehiculo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String TIPO = "tipo";
    private static final String REVISiON = "revision";
    private static final String MECANICO = "mecanico";
    private static Trabajos instancia;

    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {

        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajosCliente = new ArrayList<>();

        for (Trabajo trabajo1 : coleccionTrabajos) {
            if (trabajo1.getCliente().getDni().equals(cliente.getDni())) {
                trabajosCliente.add(trabajo1);
            }
        }
        return trabajosCliente;

    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().matricula().equals(vehiculo.matricula())) {
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");


        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());

        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
        Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
        Objects.requireNonNull(fechaRevision, "La fecha de revisión no puede ser nula.");

        for (Trabajo trabajoExistente : coleccionTrabajos) {

            if (!trabajoExistente.estaCerrado()) {
                if (trabajoExistente.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                }
                if (trabajoExistente.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            }

            if (trabajoExistente.estaCerrado()) {
                if (trabajoExistente.getCliente().equals(cliente) && !fechaRevision.isAfter(trabajoExistente.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                }
                if (trabajoExistente.getVehiculo().equals(vehiculo) && !fechaRevision.isAfter(trabajoExistente.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                }
            }
        }

    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList vehiculos = documentoXml.getElementsByTagName(TRABAJO);
        for (int i = 0;i < vehiculos.getLength(); i++){
            Node vehiculo = vehiculos.item(i);
            if (vehiculo.getNodeType() == Node.ELEMENT_NODE){
                try {
                    insertar(getTrabajo((Element) vehiculo));
                } catch (TallerMecanicoExcepcion|IllegalArgumentException|NullPointerException e) {
                    System.out.printf("Error al procesar al procesar el trabajo%s:  %s",i,e.getMessage());
                }
            }
        }
    }

    private Trabajo getTrabajo(Element elemento) throws TallerMecanicoExcepcion {
        Cliente cliente = Cliente.get(elemento.getAttribute(CLIENTE));
        cliente = Clientes.getInstancia().buscar(cliente);
        Vehiculo vehiculo = Vehiculo.get(elemento.getAttribute(VEHICULO));
        vehiculo = Vehiculos.getInstancia().buscar(vehiculo);
        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO),FORMATO_FECHA);
        String tipo = elemento.getAttribute(TIPO);
        Trabajo trabajo = null;
        if (tipo.equals(REVISiON)){
            trabajo = new Revision(cliente,vehiculo,fechaInicio);
        }else if (tipo.equals(MECANICO)){
            trabajo = new Mecanico(cliente,vehiculo,fechaInicio);
            if (elemento.hasAttribute(PRECIO_MATERIAL)){
                ((Mecanico)trabajo).anadirPrecioMaterial(Float.parseFloat(elemento.getAttribute(PRECIO_MATERIAL)));
            }
        }
        if (elemento.hasAttribute(HORAS) && trabajo != null){
            int horas = Integer.parseInt(elemento.getAttribute(HORAS));
            trabajo.anadirHoras(horas);
        }

        if (elemento.hasAttribute(FECHA_FIN) && trabajo != null){
            LocalDate fechaFin = LocalDate.parse(elemento.getAttribute(FECHA_FIN),FORMATO_FECHA);
            trabajo.cerrar(fechaFin);
        }
        return trabajo;
    }

    private Document crearDocumentoXml() throws TallerMecanicoExcepcion {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = null;
        if (constructor != null){
            documentoXml = constructor.newDocument();
            documentoXml.appendChild(documentoXml.createElement(RAIZ));
            for (Trabajo trabajo : coleccionTrabajos){
                Element trabajos = getElemento(documentoXml, trabajo);
                documentoXml.getDocumentElement().appendChild(trabajos);
            }
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml,Trabajo trabajo){
        Element elementoTrabajo = documentoXml.createElement(TRABAJO);
        elementoTrabajo.setAttribute(CLIENTE,trabajo.getCliente().getDni());
        elementoTrabajo.setAttribute(VEHICULO,trabajo.getVehiculo().matricula());
        elementoTrabajo.setAttribute(FECHA_INICIO,trabajo.getFechaInicio().format(FORMATO_FECHA));
        if (trabajo instanceof Revision ){
            elementoTrabajo.setAttribute(TIPO,REVISiON);
        }else if (trabajo instanceof Mecanico mecanico){
            elementoTrabajo.setAttribute(TIPO,MECANICO);
            if (mecanico.getPrecioMaterial() != 0){
                elementoTrabajo.setAttribute(PRECIO_MATERIAL, String.format(Locale.US,"%f",mecanico.getPrecioMaterial()));
            }
        }
        if (trabajo.getFechaFin() != null){
            elementoTrabajo.setAttribute(FECHA_FIN,trabajo.getFechaFin().format(FORMATO_FECHA));
        }
        if (trabajo.getHoras() != 0){
            elementoTrabajo.setAttribute(HORAS, String.format("%d",trabajo.getHoras()));
        }
        return elementoTrabajo;
    }

    @Override
    public void comenzar() {
        Document documentoXml = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJO);
        if (documentoXml != null){
            procesarDocumentoXml(documentoXml);
            System.out.println("Ficheros trabajos leído correctamente.");
        }
    }

    @Override
    public void terminar() throws TallerMecanicoExcepcion {
        Document documentoXml = crearDocumentoXml();
        UtilidadesXml.escribirDocumentoXml(documentoXml, FICHERO_TRABAJO);
        System.out.println("Fichero trabajos escrito correctamente.");
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo trabajo1;
        trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajo1 instanceof Revision revision){
            revision.anadirHoras(horas);

        } else if (trabajo1 instanceof Mecanico mecanico){
            mecanico.anadirHoras(horas);
        }


        return trabajo1;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Trabajo trabajo1;
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        trabajo1 = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajo1 instanceof Revision){
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");

        } else if (trabajo1 instanceof Mecanico mecanico){
            mecanico.anadirPrecioMaterial(precioMaterial);
        }

        return  trabajo1;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        if (!coleccionTrabajos.contains(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        getTrabajoAbierto(trabajo.getVehiculo()).cerrar(fechaFin);
        return trabajo;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        if (coleccionTrabajos.contains(trabajo)) {
            return trabajo;
        }

        return null;
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (coleccionTrabajos.contains(trabajo)) {
            coleccionTrabajos.remove(trabajo);
        } else {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
    }


    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) throws TallerMecanicoExcepcion{
        Trabajo trabajoAux = null;
        Iterator<Trabajo> iterator = coleccionTrabajos.iterator();
        boolean encontrado = false;
        while(iterator.hasNext() && !encontrado){
            trabajoAux = iterator.next();
            if (trabajoAux.getVehiculo().equals(vehiculo) && !trabajoAux.estaCerrado()) {
                encontrado = true;
            }
        }
        if (trabajoAux == null){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        return trabajoAux;
    }

    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes){
        Map<TipoTrabajo,Integer> mapaEstadistica;
        mapaEstadistica = inicializarEstadisticas();
        for (Trabajo trabajo : coleccionTrabajos){
            if (trabajo.getFechaInicio().getMonth() == mes.getMonth() && trabajo.getFechaInicio().getYear() == mes.getYear()){
                mapaEstadistica.put(TipoTrabajo.getTipoTrabajo(trabajo),mapaEstadistica.get(TipoTrabajo.getTipoTrabajo(trabajo))+1);
            }
        }
        return mapaEstadistica;
    }


    private Map<TipoTrabajo,Integer> inicializarEstadisticas(){
        Map<TipoTrabajo,Integer> mapaTipoTrabajo = new HashMap<>();
        mapaTipoTrabajo.put(TipoTrabajo.Mecánico,0);
        mapaTipoTrabajo.put(TipoTrabajo.Revisión,0);
        return mapaTipoTrabajo;
    }


}
