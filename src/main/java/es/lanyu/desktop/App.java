package es.lanyu.desktop;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.esotericsoftware.tablelayout.swing.Table;

import es.lanyu.commons.config.Propiedades;
import es.lanyu.commons.math.MathUtils;
import es.lanyu.commons.servicios.entidad.ServicioEntidad;
import es.lanyu.commons.servicios.entidad.ServicioEntidadImpl;
import es.lanyu.comun.evento.Partido;
import es.lanyu.comun.suceso.GolImpl;
import es.lanyu.comun.suceso.Suceso;
import es.lanyu.comun.suceso.TarjetaImpl;
import es.lanyu.comun.suceso.TarjetaImpl.TipoTarjeta;
import es.lanyu.participante.Participante;
import es.lanyu.ui.swing.SimpleJTable;
import es.lanyu.ui.swing.render.CondicionalCustomRenderer;

public class App {
  public final static Propiedades PROPIEDADES;
  
  static {
    PROPIEDADES = new Propiedades("app.properties");
  }

  private static void guardarConfiguracion(JFrame frame) {
    PROPIEDADES.setProperty("ancho", frame.getWidth() + "");
    PROPIEDADES.setProperty("alto", frame.getHeight() + "");
    PROPIEDADES.guardarPropiedades();
  }
  
  public static void main(String[] args) {
    
    // Usar propiedades
    int ancho = PROPIEDADES.leerPropiedadInt("ancho");
    int alto = PROPIEDADES.leerPropiedadInt("alto");
    JFrame frame = new JFrame("Mi Frame");
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        guardarConfiguracion(frame);
        super.windowClosing(e);
      }
    });
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Table tabla = new Table();
    frame.getContentPane().add(tabla);
    
    // Datos de la API
    List<Participante> participantes = new ParticipanteDAO().getParticipantes();
    // y cacheo de Participantes
    ServicioEntidad servicioEntidad = new ServicioEntidadImpl();
    participantes.forEach(p -> servicioEntidad.getGestorNombrables().addNombrable(Participante.class, p));
    
    // Datos de partidos
    List<Partido> partidos = new PartidoDAO().getPartidos();
    partidos.forEach(p -> {
      // generar sucesos para que sea mas vistoso
      p.setServicioEntidad(servicioEntidad);
      generarSucesos(p.getLocal(), (int)MathUtils.generarFloatRandom(3, 7)).forEach(p::addSuceso);
      generarSucesos(p.getVisitante(), (int)MathUtils.generarFloatRandom(3, 7)).forEach(p::addSuceso);
    });
    // Ver partidos cargados
    partidos.forEach(System.out::println);
    
    // Panel para formularios
    JPanel panelFormulario = new JPanel();
    panelFormulario.add(new JLabel("Seleccione un partido con doble click"));
    tabla.addCell(panelFormulario).expandX();
    tabla.row();
    
    // Tabla de Partidos
    SimpleJTable<Partido> tablaPartidos = new SimpleJTable<Partido>(partidos,
        new String[] { "Fecha", "Detalles", "Empate" },
        p -> getStringFechaPartido(p),
        p -> p,
        p -> p.getGanador() == null);
    
    tablaPartidos.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1) {
          Partido partido = tablaPartidos.getSeleccionado();
          System.out.println(partido);
          panelFormulario.removeAll();
          panelFormulario.add(new PartidoForm(partido));
          panelFormulario.revalidate();
        }
      };
    });
    int anchoFecha = 140;
    tablaPartidos.setAnchosPreferidos(anchoFecha, 600);
    TableColumn columnaFecha = tablaPartidos.getColumn("Fecha");
    columnaFecha.setMaxWidth(anchoFecha);
    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
    dtcr.setHorizontalAlignment(JLabel.CENTER);
    columnaFecha.setCellRenderer(dtcr);
    
    tablaPartidos.getColumnModel().getColumn(1).setCellRenderer(new CondicionalCustomRenderer<Partido>(
        p -> p.detallesDelPartido(), p -> p.getGanador() == null, Color.decode("#b9ea96")));
    
    // La JTable debe ir dentro de JScrollPane
    JScrollPane scrollPane = new JScrollPane(tablaPartidos);
    tabla.addCell(scrollPane).fillX();
    
    tabla.debug();
    frame.setSize(ancho, alto);
    frame.setLocationRelativeTo(null);
//    frame.setLocation(2000, 200);
    frame.setVisible(true);
  }
  
  private static Collection<Suceso> generarSucesos(Participante participante, int numero) {
    Collection<Suceso> sucesos = new ArrayList<Suceso>();
    for (int i = 0; i < numero; i++) {
      Suceso suceso;
      if (MathUtils.nextFloat() > .2) {
        suceso = new TarjetaImpl(null, participante,   MathUtils.nextFloat() > .9 ? TipoTarjeta.ROJA : TipoTarjeta.AMARILLA); 
      } else {
        suceso = new GolImpl(null, "N/D", participante);
      }
      sucesos.add(suceso);
    }
    
    return sucesos;
  }
  
  // Metodos de utilidad para mostrar partidos
  private static SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy HH:mm");
  public static String getStringFechaPartido(Partido partido) {
    Date fecha = partido != null ? partido.getFecha() : null;
    String resultadoFecha = "No disponible";
    if (fecha != null) {
      resultadoFecha = sdf.format(fecha).replace(".", "").toUpperCase();
    }
    
    return resultadoFecha;
  }
  
}
