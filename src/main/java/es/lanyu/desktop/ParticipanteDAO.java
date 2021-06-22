package es.lanyu.desktop;

import java.util.List;

import es.lanyu.participante.Participante;

public class ParticipanteDAO extends DatosDeportivosAbstractDAO<Participante> {

  public List<Participante> getParticipantes() {
    return getEntidades(Participante.class, "participantes");
  }
}
