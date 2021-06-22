package es.lanyu.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.lanyu.participante.Participante;

public class ParticipanteDAO {

  public List<Participante> getParticipantes() {
    List<Participante> elementos = new ArrayList<Participante>();
    HttpURLConnection con = null;
    try {
      URL url = new URL("https://datos-deportivos-api.herokuapp.com/api/participantes");
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      
      BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));

      ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      mapper.addMixIn(Participante.class, MixIns.Participantes.class);
      
      JsonNode nodoParticipantes = mapper.readTree(buffer).findValue("participantes");
      for (JsonNode nodo : nodoParticipantes) {
        Participante p = mapper.readValue(nodo.traverse(), Participante.class);
        elementos.add(p);
      }
      
      buffer.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    } finally {
      con.disconnect();
    }
    return elementos;
  }
  
}
