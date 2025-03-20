package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {
    private List<Revision> coleccionRevisiones;

    public Revisiones() {
        coleccionRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return new ArrayList<>(coleccionRevisiones);
    }

    public List<Revision> get(Cliente cliente) {
        List<Revision> revisionesCliente = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getCliente().equals(cliente)) {
                revisionesCliente.add(revision);
            }
        }
        return revisionesCliente;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        List<Revision> revisionesVehiculo = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getVehiculo().equals(vehiculo)) {
                revisionesVehiculo.add(revision);
            }
        }
        return revisionesVehiculo;
    }

    public void insertar(Revision revision) {
        if (revision == null) {
            throw new NullPointerException("No se puede insertar una revisión nula.");
        }
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        if (coleccionRevisiones.contains(revision)) {
            throw new TallerMecanicoExcepcion("Ya existe esa revisión en el sistema.");
        }
        coleccionRevisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) {
        for (Revision revision : coleccionRevisiones) {
            if ((revision.getCliente().equals(cliente) || revision.getVehiculo().equals(vehiculo))) {
                if (!revision.estaCerrada() || (revision.getFechaFin() != null && revision.getFechaFin().isAfter(fechaRevision))) {
                    throw new TallerMecanicoExcepcion("No se puede iniciar una revisión mientras haya otra sin cerrar o con fecha posterior.");
                }
            }
        }
    }

    private Revision getRevision(Revision revision) {
        if (revision == null) {
            throw new NullPointerException("No se puede buscar una revisión nula.");
        }
        int index = coleccionRevisiones.indexOf(revision);
        if (index == -1) {
            throw new TallerMecanicoExcepcion("No existe esa revisión en el sistema.");
        }
        return coleccionRevisiones.get(index);
    }

    public Revision añadirHoras(Revision revision, int horas) {
        Revision existente = getRevision(revision);
        existente.añadirHoras(horas);
        return existente;
    }

    public Revision añadirPrecioMaterial(Revision revision, float precioMaterial) {
        Revision existente = getRevision(revision);
        existente.añadirPrecioMaterial(precioMaterial);
        return existente;
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) {
        Revision existente = getRevision(revision);
        existente.cerrar(fechaFin);
        return existente;
    }

    public Revision buscar(Revision revision) {
        if (revision == null) {
            throw new NullPointerException("No se puede buscar una revisión nula.");
        }
        int index = coleccionRevisiones.indexOf(revision);
        return (index != -1) ? coleccionRevisiones.get(index) : null;
    }

    public void borrar(Revision revision) {
        if (revision == null) {
            throw new NullPointerException("No se puede borrar una revisión nula.");
        }
        if (!coleccionRevisiones.remove(revision)) {
            throw new TallerMecanicoExcepcion("No existe esa revisión en el sistema.");
        }
    }
}