package es.lanyu.desktop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.lanyu.participante.Participante;

public class ParticipanteDAO extends DatosDeportivosAbstractDAO<Participante> {

  public List<Participante> getParticipantes() {
    Map<String, String> parametros = new HashMap<>();
    parametros.put("size", "300");
    return getEntidades(Participante.class, "participantes", parametros);
  }
}
