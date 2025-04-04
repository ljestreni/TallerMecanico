package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.ModeloCascada;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.texto.VistaTexto;

public enum FabricaVista {
    TEXTO {
        @Override
        public Vista crear() {
            return new VistaTexto();
        }
        public Vista crear(FabricaFuenteDatos fabricaFuenteDatos) {return new VistaTexto();}};

    public abstract Vista crear();
}
