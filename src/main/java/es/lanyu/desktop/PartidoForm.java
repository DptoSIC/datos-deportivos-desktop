package es.lanyu.desktop;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.esotericsoftware.tablelayout.swing.Table;
import com.github.lgooddatepicker.components.DateTimePicker;

import es.lanyu.commons.math.MathUtils;
import es.lanyu.commons.tiempo.DatableLocalDateTime;
import es.lanyu.comun.evento.Partido;
import es.lanyu.comun.suceso.Tarjeta;
import es.lanyu.comun.suceso.TarjetaImpl.TipoTarjeta;
import es.lanyu.participante.Participante;

public class PartidoForm extends Table {

  private Partido partido;
  private JComboBox<Participante> cbLocal, cbVisitante;
  private DateTimePicker dtpFechaPartido;
  
  private Partido getPartido() {
    return partido;
  }
  
  public PartidoForm(Collection<Participante> participantes) {
    JLabel lblResultado = new JLabel();
    lblResultado.setSize(20, 0);
    dtpFechaPartido = new DateTimePicker();
    dtpFechaPartido.setDateTimePermissive(LocalDateTime.now());
    cbLocal = cargarParticipante(participantes, false);
    cbLocal.setSelectedIndex((int)MathUtils.generarFloatRandom(0, participantes.size()));
    cbVisitante = cargarParticipante(participantes, false);
    cbVisitante.setSelectedIndex((int)MathUtils.generarFloatRandom(0, participantes.size()));
    montarComponentes(dtpFechaPartido, cbLocal, lblResultado, cbVisitante, new JLabel());
  }
  
  public PartidoForm(Partido partido) {
    cargarPartido(partido);
    Participante local = getPartido().getLocal();
    Participante visitante = getPartido().getVisitante();
    JLabel lblResultado = new JLabel(getPartido().getResultado());
    lblResultado.setFont(lblResultado.getFont().deriveFont(lblResultado.getFont().getSize() * 2f));
    JLabel lblFecha = new JLabel(getPartido().getFecha().toString());
    Table tablaTarjetas = new Table();
    tablaTarjetas.debug();
    tablaTarjetas.addCell(crearLabelTarjetas(getPartido().getTarjetasEquipo(local)));
    tablaTarjetas.addCell(new JLabel()).expandX();
    tablaTarjetas.addCell(crearLabelTarjetas(getPartido().getTarjetasEquipo(visitante)));
    
    montarComponentes(lblFecha, new JLabel(local.getNombre()), lblResultado, new JLabel(visitante.getNombre()), tablaTarjetas);
  }
  
  private void montarComponentes(JComponent cFecha, JComponent cLocal, JComponent cResultado, JComponent cVisitante, JComponent cTarjetas) {
    addCell(cFecha).colspan(3);
    row();
    addCell(cLocal).uniformX().left();
    addCell(cResultado).pad(0, 5, 0, 5);
    addCell(cVisitante).uniformX().right();
    row();
    addCell(cTarjetas).colspan(3).fillX();
    debug();
  }

  private void cargarPartido(Partido partido) {
    this.partido = partido;
  }
  
  private JLabel crearLabelTarjetas(Collection<Tarjeta> tarjetas) {
    StringBuilder sbTarjetas = new StringBuilder("<html>");
    for (Tarjeta tarjeta : tarjetas) {
      sbTarjetas.append("<font color="
          + (tarjeta.getTipoTarjeta() == TipoTarjeta.ROJA ? "red" : "yellow")
          + " size=+2 >&#9646;</font>");
    }
    sbTarjetas.append("</html>");
    
    return new JLabel(sbTarjetas.toString());
  }
  
  private static JComboBox<Participante> cargarParticipante(Collection<Participante> participantes, boolean alinearDerecha) {
    JComboBox<Participante> cbParticipante = new JComboBox<>(participantes.toArray(new Participante[0]));
    if (alinearDerecha) {
      ((JLabel)cbParticipante.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
    }
    
    return cbParticipante;
  }
  
  public Partido leerPartido() {
    Partido partido = new Partido(cbLocal.getItemAt(cbLocal.getSelectedIndex()), (Participante)cbVisitante.getSelectedItem());
    partido.setTimestamp(new DatableLocalDateTime(dtpFechaPartido.getDateTimeStrict()).getTimestamp());
    
    return partido;
  }
  
}
