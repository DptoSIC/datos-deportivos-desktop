package es.lanyu.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  public List<T> getEntidades(Class<T> tipo, String path, Map<String, String> parametros) {
    List<T> elementos = new ArrayList<T>();
    HttpURLConnection con = null;
    try {
      String queryString = "";
      if (parametros != null) {
        queryString = getParamsString(parametros);
      }
      URL url = new URL(getApiUrl() + path + "?" + queryString );
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

  public static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();

    for (Map.Entry<String, String> entry : params.entrySet()) {
      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      result.append("&");
    }

    String resultString = result.toString();
    return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
  }
}