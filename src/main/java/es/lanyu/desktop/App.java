package es.lanyu.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.esotericsoftware.tablelayout.swing.Table;

import es.lanyu.participante.Participante;

public class App {

  public static void main(String[] args) {
    int ancho = 480, alto = 270;
    JFrame frame = new JFrame("Mi Frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Table tabla = new Table();
    frame.getContentPane().add(tabla);
    
    Participante participante = new Participante("Real Madrid");
    participante.setIdentificador("1");
    
    ParticipanteForm participanteEditor = new ParticipanteForm(participante, true);
    tabla.addCell(participanteEditor);
    tabla.row();
    
    ParticipanteForm participanteView = new ParticipanteForm(participante);
    tabla.addCell(participanteView);
    tabla.row();
    
    participanteEditor.addPropertyChangeListener("participante", e -> participanteView.repaint());
    
    JButton btnRefrescar = new JButton("Refrescar");
    btnRefrescar.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent evento) {
        System.out.println("El nombre actual es: " + participante.getNombre());
        participanteView.repaint();//.cargarParticipante(participante);
      }
      
    });
    tabla.addCell(btnRefrescar);
    
    tabla.debug();
    frame.setSize(ancho, alto);
    frame.pack();
    frame.setLocationRelativeTo(null);
//    frame.setLocation(2000, 200);
    frame.setVisible(true);
  }
}
