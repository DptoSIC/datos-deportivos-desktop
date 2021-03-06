package es.lanyu.desktop;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.esotericsoftware.tablelayout.swing.Table;

import es.lanyu.participante.Participante;

public class ParticipanteForm extends JPanel {
  private Participante participante;
  private JTextField txtNombre;
  
  private Participante getParticipante() {
    return participante;
  }
  
  private void setParticipante(Participante participante) {
    this.participante = participante;
  }

  private JTextField getTxtNombre() {
    return txtNombre;
  }
  
  public ParticipanteForm(Participante participante) {
    this(participante, false);
  }
  
  public ParticipanteForm(Participante participante, boolean editable) {
    // Componentes
    Table tabla = new Table();// No hace falta acceder desde fuera
    tabla.debug();
    txtNombre = new JTextField();// Si hace falta acceder desde fuera
    getTxtNombre().setEnabled(editable);
    
    // Cargar Datos
    cargarParticipante(participante);
    
    // Layout
    add(tabla);
    tabla.addCell(new JLabel("ID: " + getParticipante().getIdentificador()));
    tabla.addCell(getTxtNombre()).width(250);
    
    // Listeners
    getTxtNombre().addActionListener(e -> cambiaParticipante());
    getTxtNombre().addCaretListener(e -> cambiaParticipante());
    
    Font fuente = new Font(Font.SERIF, Font.PLAIN, 30);
    setFont(fuente);
  }
  
  @Override
  public void setFont(Font font) {
    super.setFont(font);
    for (Component componente : getComponents()) {
      componente.setFont(font);
      if (componente instanceof Container) {
        Container container = (Container)componente;
        Arrays.asList(container.getComponents()).forEach(c -> c.setFont(font));
      }
    }
  }
  
  private void cambiaParticipante() {
    String nombrePropiedad = "participante";
    getParticipante().setNombre(getTxtNombre().getText());
    PropertyChangeEvent evento = new PropertyChangeEvent(this, nombrePropiedad, null, getParticipante());
    Arrays.asList(getPropertyChangeListeners(nombrePropiedad)).forEach(l -> l.propertyChange(evento));
  }
  
  private void cargarParticipante(Participante participante) {
    setParticipante(participante);
    if (participante != null) {
      getTxtNombre().setText(getParticipante().getNombre());
    }
  }
  
  @Override
  public void repaint() {
    super.repaint();
    if (getParticipante() != null) {
      cargarParticipante(getParticipante());
    }
  }
}
