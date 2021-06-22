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

public abstract class DatosDeportivosAbstractDAO<T> {

  private ObjectMapper mapper;
  
  private String getApiUrl() {
    return "https://datos-deportivos-api.herokuapp.com/api/";
  }
  
  private ObjectMapper getMapper() {
    return mapper;
  }
  
  public DatosDeportivosAbstractDAO() {
    super();
    mapper = new ObjectMapper();
    getMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    getMapper().addMixIn(Participante.class, MixIns.Participantes.class);
  }

  public List<T> getEntidades(Class<T> tipo, String path) {
    List<T> elementos = new ArrayList<T>();
    HttpURLConnection con = null;
    try {
      URL url = new URL(getApiUrl() + path);
      con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setConnectTimeout(5000);
      con.setReadTimeout(5000);
      
      BufferedReader buffer = new BufferedReader(new InputStreamReader(con.getInputStream()));

      JsonNode nodoElementos = getMapper().readTree(buffer).findValue(path);
      for (JsonNode nodo : nodoElementos) {
        T entidad = getMapper().readValue(nodo.traverse(), tipo);
        elementos.add(entidad);
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