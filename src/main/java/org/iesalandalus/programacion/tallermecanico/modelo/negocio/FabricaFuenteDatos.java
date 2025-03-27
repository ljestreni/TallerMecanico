package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria.FuenteDatosMemoria;

public enum FabricaFuenteDatos {
    MEMORIA;

    public IFuenteDatos crear() {
        switch (this) {
            case MEMORIA:
                return new FuenteDatosMemoria();
            default:
                throw new UnsupportedOperationException("Fuente de datos no soportada.");
        }
    }
}
