package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;

public enum FabricaModelo {
    CASCADA {@Override public ModeloCascada crear(FabricaFuenteDatos fabricaFuenteDatos) {return new ModeloCascada(fabricaFuenteDatos);}};

    public abstract ModeloCascada crear(FabricaFuenteDatos fabricaFuenteDatos);
}
