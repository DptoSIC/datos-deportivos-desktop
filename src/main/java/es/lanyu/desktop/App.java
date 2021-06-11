package es.lanyu.desktop;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.esotericsoftware.tablelayout.swing.Table;

public class App {

  public static void main(String[] args) {
    int ancho = 480, alto = 270;
    JFrame frame = new JFrame("Mi Frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Table tabla = new Table();
//    frame.add(tabla);
    frame.getContentPane().add(tabla);
    
    tabla.addCell(new JLabel("Mi etiqueta")).width(ancho/2);
    tabla.addCell(new JLabel("otra"));
    tabla.row();
    tabla.addCell(new JLabel("Otra fila"));
    
    tabla.debug();
//    frame.show();
    frame.setSize(ancho, alto);
    frame.setVisible(true);
  }
}
