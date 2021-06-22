package es.lanyu.desktop;

import java.lang.reflect.Field;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import es.lanyu.commons.reflect.ReflectUtils;
import es.lanyu.comun.evento.Partido;

public class PartidoDAO extends DatosDeportivosAbstractDAO<Partido> {
  Field fieldIdLocal = ReflectUtils.getCampo(Partido.class, "idLocal", true);
  Field fieldIdVisitante = ReflectUtils.getCampo(Partido.class, "idVisitante", true);
  
  public List<Partido> getPartidos() {
    return getEntidades(Partido.class, "partidos", null);
  }
  
  @Override
  protected void completarMapeo(Partido entidad, JsonNode nodo) {
    super.completarMapeo(entidad, nodo);
    try {
      fieldIdLocal.set(entidad, nodo.findValue("idLocal").asText());
      fieldIdVisitante.set(entidad, nodo.findValue("idVisitante").asText());
    } catch (IllegalArgumentException | IllegalAccessException e) {
      e.printStackTrace();
    }
    
  }
}
