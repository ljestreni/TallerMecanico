package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.negocio.cascada.ModeloCascada;

public enum FabricaModelo {
        CASCADA {
            @Override
            public Modelo crear(FabricaFuenteDatos fabricaFuenteDatos) {
                return new ModeloCascada(fabricaFuenteDatos);
            }
        };

        public abstract Modelo crear(FabricaFuenteDatos fabricaFuenteDatos);
    }