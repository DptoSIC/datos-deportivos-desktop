package es.lanyu.desktop;

import java.awt.BorderLayout;
import java.awt.Label;

import javax.swing.JFrame;

public class App {

  public static void main(String[] args) {
    int ancho = 480, alto = 270;
    JFrame frame = new JFrame("Mi Frame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setLayout(null); // No se recomienda
    System.out.println(frame.getLayout());
    frame.getContentPane().add(new Label("Mi Etiqueta"), BorderLayout.SOUTH);
    
//    frame.show();
    frame.setSize(ancho, alto);
    frame.setVisible(true);
  }
}
