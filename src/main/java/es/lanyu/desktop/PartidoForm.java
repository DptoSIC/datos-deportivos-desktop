package es.lanyu.desktop;

import javax.swing.JLabel;

import com.esotericsoftware.tablelayout.swing.Table;

import es.lanyu.comun.evento.Partido;

public class PartidoForm extends Table {

  private Partido partido;
  
  private Partido getPartido() {
    return partido;
  }
  
  public PartidoForm(Partido partido) {
    cargarPartido(partido);
    JLabel lblResultado = new JLabel(getPartido().getResultado());
    lblResultado.setFont(lblResultado.getFont().deriveFont(lblResultado.getFont().getSize() * 2f));
    JLabel lblFecha = new JLabel(getPartido().getFecha().toString());
    
    addCell(lblFecha).colspan(3);
    row();
    addCell(getPartido().getLocal().getNombre()).uniformX().left();
    addCell(lblResultado).pad(0, 5, 0, 5);
    addCell(getPartido().getVisitante().getNombre()).uniformX().right();
    debug();
  }

  private void cargarPartido(Partido partido) {
    this.partido = partido;
  }
}
