package com.mycompany.Caisse_Pharm_MVN;// the compiler must be upgrade
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class Qaisse_Recap extends JFrame {

  private JButton Afficher,Valider;
  private JLabel Transaction, Heure, Montant_Globale, Espece, Recupere, Reste ;
  private JTextField Trans,Hour, Mont_Globale, Esp, Recp, Rest;
  
  public JTable Table=null;
  public DefaultTableModel model;
  private JScrollPane JSPVH=null;
    
  private Connection connection;
  private Statement statement;
  private ResultSet rs;
  
  private int j=0,k=0,t=0;
  
  
  
  
  public Qaisse_Recap()
  {
      super();
      try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/caisse","root","");
      statement = connection.createStatement();
      
    } catch (Exception e) {
      System.err.println("Unable to find and load driver");
      System.exit(1);
    }
  }
  
  public void build() throws SQLException {
      
   final Container c = getContentPane();
   c.setLayout(new FlowLayout());
    
   JButton Afficher = new JButton("Afficher");
   Afficher.addActionListener(new ActionListener() { 
       private String[] Col1, Col2, Col3, Col4, Col5, Col6;
       private Object[][] data;
       private int N=999;
   public void actionPerformed(ActionEvent e) {
    // Les boutons:::
        
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit .getScreenSize();
        Col1 = new String[100];  
        Col2 = new String[100];
        Col3 = new String[100];
        Col4 = new String[100];
        Col5 = new String[100];
        Col6 = new String[100];
        data = new Object[N][6];
        if (JSPVH!=null && Table!=null) {getContentPane().remove(JSPVH); getContentPane().remove(Table);}

        String title[] = {"Transaction_key", "Heure", "Montant_Globale","Espece","Recupere","Reste"};
        
        ResultSet rs = null; 
       try {
           rs = statement.executeQuery("SELECT * FROM Table_Globale");
       } catch (SQLException ex) {
           Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
       }
                    k=0;t=0;
       try {
           
           while (rs.next()) { 
               
                       Col1[t] = rs.getString("Transaction_key"); 
                       Col2[t] = rs.getString("Heure"); 
                       Col3[t] = rs.getString("Montant_Globale"); 
                       Col4[t] = rs.getString("Espece"); 
                       Col5[t] = rs.getString("Recupere"); 
                       Col6[t] = rs.getString("Reste"); 
                       
                       data[t][k]  = Col1[t];
                       data[t][1+k]= Col2[t];
                       data[t][2+k]= Col3[t];
                       data[t][3+k]= Col4[t];
                       data[t][4+k]= Col5[t];
                       data[t][5+k]= Col6[t];
                       
                       t++; 
                        
                   }
       
       } catch (SQLException ex) {
           Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
       }
        JTable Table= new JTable(data, title);        
        Table.setAutoscrolls(true);
        Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Table.setAutoCreateRowSorter(true);

        JPanel tableau = new JPanel (new BorderLayout ()); 
        tableau.add (Table.getTableHeader (), BorderLayout.NORTH); 
        tableau.add (Table, BorderLayout.CENTER); 
        JSPVH=new JScrollPane(Table);
        getContentPane().add( JSPVH, BorderLayout.CENTER);
        pack();
        setSize(screenSize);
        TableColumn column = null; 
       for(int i=0;i<4 ; i++){ 
        column= Table.getColumnModel().getColumn(i); 
        column.setPreferredWidth(150); 
        }
   }});              
   JButton Valider = new JButton("Valider");
   Valider.addActionListener(new ActionListener() { 
   public void actionPerformed(ActionEvent e) { 
    try {
                 
          Statement statement = connection.createStatement();
          int i= statement.executeUpdate("INSERT INTO Table_Globale VALUES("
                  +Trans.getText()+ ", " + "'"
                  +Hour.getText() + "', " + "'"
                  +Mont_Globale.getText() + "', " + "'"
                  +Esp.getText() + "', " + "'"
                  +Recp.getText() + "', " + "'"
                  +Rest.getText() + "');");
          } catch (SQLException selectException) {
          displaySQLErrors(selectException);
          
        }
}

       private void displaySQLErrors(SQLException selectException) {
           throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       }
   });
    //private JLabel Transaction, Heure, Montant_Globale, Espece, Recuper�, Reste ;
  //private JTextField Trans,Hour, Mont_Globale, Esp, Recp, Rest;
   
   Table = new JTable(16, 8);//pour eviter la duplication
   
    Trans = new JTextField(15);
    Hour= new JTextField(15);
    Mont_Globale= new JTextField(15);
    Esp= new JTextField(15);
    Recp= new JTextField(15);
    Rest= new JTextField(15);
    
    Transaction = new JLabel("Transaction");
    Heure = new JLabel("Heure");
    Montant_Globale = new JLabel("Montant_Globale");
    Espece = new JLabel("Espace");
    Recupere = new JLabel("Recupere");
    Reste = new JLabel("Reste");

    JPanel first = new JPanel();
    first.setLayout(new GridLayout(6, 2));
    
    first.add(Transaction);first.add(Trans);
    first.add(Heure);first.add(Hour);
    first.add(Montant_Globale);first.add(Mont_Globale);
    first.add(Espece);first.add(Esp);
    first.add(Recupere);first.add(Recp);
    first.add(Reste);first.add(Rest);
    
    JPanel second = new JPanel();
    second.setLayout(new GridLayout(1, 2));
    second.add(Valider);
    second.add(Afficher);
    
    c.add(first);
    c.add(second);
    setSize(600, 300);
    setTitle("Application Quaisse");
    show();
    
   }
}