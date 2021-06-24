package es.lanyu.desktop;

import java.util.Collection;

import javax.swing.JLabel;

import com.esotericsoftware.tablelayout.swing.Table;

import es.lanyu.comun.evento.Partido;
import es.lanyu.comun.suceso.Tarjeta;
import es.lanyu.comun.suceso.TarjetaImpl.TipoTarjeta;
import es.lanyu.participante.Participante;

public class PartidoForm extends Table {

  private Partido partido;
  
  private Partido getPartido() {
    return partido;
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
    
    addCell(lblFecha).colspan(3);
    row();
    addCell(local.getNombre()).uniformX().left();
    addCell(lblResultado).pad(0, 5, 0, 5);
    addCell(visitante.getNombre()).uniformX().right();
    row();
    addCell(tablaTarjetas).colspan(3).fillX();
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
  
}
