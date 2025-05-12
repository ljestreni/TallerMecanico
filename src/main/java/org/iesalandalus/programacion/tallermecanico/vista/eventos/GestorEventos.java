package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {
    private  Map<Evento, List<ReceptorEventos>> receptores = new EnumMap<>(Evento.class);

    public GestorEventos(Evento... eventos){
        Objects.requireNonNull(eventos,"Los eventos no pueden ser nulo.");
        for (Evento evento : eventos){
            receptores.put(evento,new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor, Evento... eventos){
        Objects.requireNonNull(eventos,"Los eventos no pueden ser nulo.");
        for (Evento evento : eventos){
            receptores.get(evento).add(receptor);
        }
    }

    public void desuscribir(ReceptorEventos receptor, Evento... eventos){
        Objects.requireNonNull(eventos,"Los eventos no pueden ser nulo.");
        for (Evento evento : eventos){
            receptores.get(evento).remove(receptor);
        }
    }

    public void notificarEvento(Evento evento){
        Objects.requireNonNull(evento,"Lo evento no pueden ser nulo.");
        for (ReceptorEventos receptor : receptores.get(evento)){
            receptor.actualizar(evento);
        }
    }
}