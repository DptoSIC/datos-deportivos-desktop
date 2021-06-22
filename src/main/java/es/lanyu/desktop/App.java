package es.lanyu.desktop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.esotericsoftware.tablelayout.swing.Table;

import es.lanyu.participante.Participante;
import es.lanyu.ui.swing.SimpleJTable;

public class App {
  
  public static void main(String[] args) {
    int ancho = 800, alto = 400;
    JFrame frame = new JFrame("Mi Frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Table tabla = new Table();
    frame.getContentPane().add(tabla);
    
    List<Participante> participantes = new ParticipanteDAO().getParticipantes();
    
    JPanel panelFormulario = new JPanel();
    tabla.addCell(panelFormulario).expandX();
    tabla.row();
    
    SimpleJTable<Participante> tablaParticipantes = new SimpleJTable<Participante>(participantes,
        new String[] { "ID", "Nombre" },
        p -> p.getIdentificador(),
        Participante::getNombre);
    tablaParticipantes.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1) {
          Participante participante = tablaParticipantes.getSeleccionado();
          System.out.println(participante);
          panelFormulario.removeAll();
          panelFormulario.add(new ParticipanteForm(participante, true));
          panelFormulario.revalidate();
//          panelFormulario.repaint();
        }
      };
    });
    
    JScrollPane scrollPane = new JScrollPane(tablaParticipantes);
    tabla.addCell(scrollPane).fillX();
    
    tabla.debug();
    frame.setSize(ancho, alto);
//    frame.pack();
    frame.setLocationRelativeTo(null);
//    frame.setLocation(2000, 200);
    frame.setVisible(true);
  }
}
