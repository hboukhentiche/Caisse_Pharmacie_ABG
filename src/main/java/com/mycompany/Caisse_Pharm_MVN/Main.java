package com.mycompany.Caisse_Pharm_MVN;// the compiler must be upgrade
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class Main  {
 public static void main(String[] args) throws SQLException {
    Qaisse App_Qaisse = new Qaisse();
    App_Qaisse.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
System.out.println("hello begin");
   App_Qaisse.init();
System.out.println("hello end");
   App_Qaisse.buildGUI();
 }
  }