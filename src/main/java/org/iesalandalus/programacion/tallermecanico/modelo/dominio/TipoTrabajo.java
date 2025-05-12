package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

public enum TipoTrabajo {
    Mecánico("Mecánico"),
    Revisión("Revisión");

    private String nombre;

    private TipoTrabajo(String nombre){
        this.nombre = nombre;
    }

    public static TipoTrabajo getTipoTrabajo(Trabajo trabajo){
        TipoTrabajo tipoTrabajo = null;
        if (trabajo instanceof Mecanico){
            tipoTrabajo = Mecánico;
        }else if (trabajo instanceof Revision){
            tipoTrabajo = Revisión;
        }
        return tipoTrabajo;
    }

    @Override
    public String toString() {
        return String.format("%s", nombre);
    }
}