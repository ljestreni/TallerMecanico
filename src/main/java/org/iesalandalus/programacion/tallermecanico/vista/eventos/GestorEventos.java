package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {
    private static final Map<Evento, List<ReceptorEventos>> receptores = new EnumMap<>(Evento.class);

    public GestorEventos(Evento... eventos){
        Objects.requireNonNull(eventos,"No se puede operar con un trabajo nulo.");
        for (Evento evento: eventos){
            receptores.put(evento,new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptorEventos,Evento... eventos){
        Objects.requireNonNull(eventos,"No se puede operar con un trabajo nulo.");
        for (Evento evento: eventos){
            receptores.get(evento).add(receptorEventos);
        }
    }

    public void desuscribir(ReceptorEventos receptorEventos,Evento... eventos){
        Objects.requireNonNull(eventos,"No se puede operar con un trabajo nulo.");
        for (Evento evento: eventos){
            receptores.get(evento).remove(receptorEventos);
        }
    }

    public void notificar(Evento evento){
        Objects.requireNonNull(evento,"No se puede operar con un trabajo nulo.");
        for (ReceptorEventos receptorEventos : receptores.get(evento)){
            receptorEventos.actualizar(evento);
        }
    }
}
