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
public class Qaisse extends JFrame {

  private JButton Recap,Valider;
  private JLabel Montant_Regle;
  private JTextField Mont_Regle=null;
  
  public JTable Table=null;
  public DefaultTableModel model;
  private JScrollPane JSPVH=null;
    
  private Connection connection;
  private Statement statement;
  private ResultSet rs;
  
  private int j=0,k=0,t=0;
  private Vector<String> rowData2 = new Vector<String>();
  
  
  
  public Qaisse()
  {
      super();
      try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      
    } catch (Exception e) {
      System.err.println("Unable to find and load driver");
      System.exit(1);
    }
  }
  public void buildGUI() throws SQLException {
      
   final Container c = getContentPane();
   c.setLayout(new FlowLayout());
   
   JButton Recap = new JButton("Recapetulation");
   Recap.addActionListener(new ActionListener() { 
      
   public void actionPerformed(ActionEvent e) {
       Qaisse_Recap qaisse_generale = new Qaisse_Recap();
       try {
           qaisse_generale.build();
       } catch (SQLException ex) {
           Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
       }
   }});
   JButton Valider = new JButton("Valider/Afficher");
   Valider.addActionListener(new ActionListener() { 
       private String[] Col1;
       private Object[][] data;
       private int N=999;
   public void actionPerformed(ActionEvent e) {
    // Les boutons:::
   
       
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit .getScreenSize();
        Col1 = new String[100];  
        data = new Object[N][1];
        if (JSPVH!=null && Table!=null) {getContentPane().remove(JSPVH); getContentPane().remove(Table);}

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Transaction_key");
        model.addColumn("Heure");
        model.addColumn("Montant");
        Scanner sc = null;
           try {
               sc = new Scanner(new FileInputStream("C:/data.txt")); //on ouvre le fichier pour le lire
           } catch (FileNotFoundException ex) {
               Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
           }
        while(sc.hasNext()){//tant qu'il y a des choses a lire 
            StringTokenizer token = new StringTokenizer(sc.nextLine(), " ");//on split une ligne en fonction de l'epace simple pour ne garder que le texte 
            Vector<String> rowData = new Vector<String>(); 
            while(token.hasMoreTokens())//lecture des token et on les mets dans le vecteur 
            {
                rowData.add(token.nextToken()); 
            }
            model.addRow(rowData);//on ajoute la ligne
            //System.out.println(rowData);
        } 
        sc.close();//
        model.addColumn("Montant_Regle");
        ResultSet rs = null; 
       try {
           rs = statement.executeQuery("SELECT * FROM Table_Qaisse");
       } catch (SQLException ex) {
           Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
       }
                    k=0;t=0;
       try {
           
           while (rs.next()) { 
                       Col1[t] = rs.getString("Montant_Regle"); 
                       data[t][k]= Col1[t];
                       if(t<model.getRowCount())            
                       model.setValueAt(data[t][k],t,k+3);
                       t++; 
                        
                   }
       
       } catch (SQLException ex) {
           Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
       }
        JTable Table= new JTable(model);        
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
       
       
     if(! Mont_Regle.getText().equals(""))
     {
         
       Statement statement = null;
            try {
                statement = connection.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                for(int i=0;i<Mont_Regle.getText().length();i++)
                {
                    if(! Character.isDigit(Mont_Regle.getText().charAt(i)) ){
                      Mont_Regle.setText("it is not a number");
                    } else {
                    int stat= statement.executeUpdate("INSERT INTO Table_Qaisse VALUES('"
                                              +Mont_Regle.getText() + "' " + ");");
                    rowData2.addElement(Mont_Regle.getText());
                       }
          
                }
            } catch (SQLException ex) {
                Logger.getLogger(Qaisse.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       Table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
       String numeros = null;
       for(int i=j; i<Table.getRowCount(); i++)
         {
           numeros = (String) Table.getModel().getValueAt(i,Table.getColumnCount()-1);
           if (numeros==null) 
               {
                 Table.setValueAt(rowData2.lastElement(),i,Table.getColumnCount()-1);
                 j++;
                 break;
               }
           else continue;
         }
     } 
}});              
   
    
    

    
    Mont_Regle = new JTextField(15);
    //JFormattedTextField Mont_Regle = new JFormattedTextField(NumberFormat.getNumberInstance()); 
    Table = new JTable(16, 8);//pour eviter la duplication
    Montant_Regle = new JLabel("Montant a Regler");
    
    
    
    JPanel second = new JPanel();
    second.setLayout(new GridLayout(1, 3));
    
    second.add(Montant_Regle);
    second.add(Mont_Regle);
    second.add(Valider);
    
    JPanel first= new JPanel();
    first.add(Recap);
    
    c.add(second);
    c.add(first);
    setSize(600, 300);
    setTitle("Application Caisse");
    show();
    
   }
  public void connectToDB() {
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/caisse","root","");
      statement = connection.createStatement();
      //int i= statement.executeUpdate("DROP TABLE IF EXISTS Table_Globale;");
       int j= statement.executeUpdate("CREATE TABLE IF NOT EXISTS Table_Globale(Transaction_key INT, Heure TIME, "
                                     + "Montant_Globale DOUBLE, Espece DOUBLE,"
                                     + "Recupere DOUBLE, Reste DOUBLE, PRIMARY KEY(Transaction_key));");
       int k= statement.executeUpdate("CREATE TABLE IF NOT EXISTS Table_Qaisse("
                                     +"Montant_Regle DOUBLE);");
       
       //recuperation depuis file
    } catch (SQLException connectException) {
      System.out.println(connectException.getMessage());
      System.out.println(connectException.getSQLState());
      System.out.println(connectException.getErrorCode());
      System.exit(1);
    }
  }
  public void init() {
    connectToDB();
  }
  
  private void displaySQLErrors(SQLException e) {
    //m�thode vide
  }
  
}