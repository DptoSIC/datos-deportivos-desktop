package es.lanyu.desktop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import es.lanyu.participante.Participante;

public class MixIns {
    
    // Usado ya en practica sobre carga de entidades desde archivo
    @JsonIgnoreProperties(value = { "hashCode" })
    public static interface Participantes {
        @JsonProperty("id")
        abstract String getIdentificador();
    }
    
    @JsonIgnoreType // Ignora un tipo por completo
    public static interface IgnorarTipo {}
    
    @JsonIgnoreProperties(value = { "resultado", "ganador", // Evento
            "visitante", "participantes", "equipos", // LocalContraVisitante
            "resultadoYEquipos", "goles", // EventoConGoles
            "rojasTotal", "amarillasTotal", "tarjetas", // EventoConTarjetas
            "cornersTotal", "corners", // EventoConCorners
//          "competicion", // Partido
            })
    @JsonPropertyOrder({ "idLocal", "idVisitante", "timestamp", "sucesos" })
    // Se puede heredar asi que mejor hacerlo sobre interfaces
    // a menos que haya que anotar un campo
    public abstract class Partidos implements Datables, ContadorDeMinutos {
        // aunque no es necesario aqui, solo es un ejemplo
        @JsonIgnore Object local; // faltaba de LocalContraVisitante
    }
    
//  @JsonIgnoreProperties(value = { "temporal" })
    public static interface Datables {
        // Si lo ignoro sobre el miembro se puede heredar
        // No tiene por que coincidir el tipo
        @JsonIgnore Object getTemporal();
        @JsonIgnore Object getFecha();
    }
    
    //Si utilizo sobre clase @JsonIgnoreProperties sobrescribe sin reutilizar
    @JsonIgnoreProperties(value = { "participante" })// ya no hace falta: , "temporal" })
    public static interface Sucesos extends Datables, ContadorDeMinutos {}
    
    public static interface ContadorDeMinutos {
        @JsonIgnore Object getMinuto();
        @JsonIgnore Object getMinutoActual();
    }
    
    public static interface Goles extends Sucesos {
        @JsonIgnore abstract Participante getEquipoAnotador();
    }
}


