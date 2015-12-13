package com.mycompany.Caisse_Pharm_MVN;// the compiler must be upgrade
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
public class Application_pharmacie extends JFrame {

  private JButton Afficher_Table, Get_Account, Insert_Account, Update_Account,Delete_Account, 
                  Next, Previous, Last, First, Aller; 
  private JList Account_Liste;
  private JLabel Transaction, Date,Montant, Montant_Regle, Montant_Globale,
      Espece, Recupere, Reste;
  private JTextField Trans, Datum,Mont,Mont_Regle,Mont_Globale, Esp, Recup,Res, 
                     Acced_Trans;
  
  private JTable Table=null;
  private DefaultTableModel model;
  private JScrollPane JSPVH=null;
    
  private Connection connection;
  private Statement statement;
  private ResultSet rs;
  
  
  
  public Application_pharmacie()
  {
      try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      
    } catch (Exception e) {
      System.err.println("Unable to find and load driver");
      System.exit(1);
    }
  }
  private void loadAccounts(String Table_courrante) {
    Vector v = new Vector();
    try {
      rs = statement.executeQuery("SELECT * FROM "+Table_courrante);

      while (rs.next()) {
        v.addElement(rs.getString("Transaction_key"));
      }
    } catch (SQLException e) {
      displaySQLErrors(e);
    }
    Account_Liste.setListData(v);
  }
  public void buildGUI() throws SQLException {
      
    Account_Liste = new JList();
    loadAccounts("Table_Globale");
    Account_Liste.setVisibleRowCount(1);
    JScrollPane Account_Liste_ScrollPane = new JScrollPane(Account_Liste);
    
   final Container c = getContentPane();
   c.setLayout(new FlowLayout());
    
    
    // Les boutons:::
    Get_Account = new JButton("Afficher une transaction");
    Get_Account.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          rs.first();
          while (!rs.getString("Transaction_key").equals(
                Account_Liste.getSelectedValue()))
                rs.next();
          if (!rs.isAfterLast()) {
              
            Trans.setText(rs.getString("Transaction_key"));
            Datum.setText(rs.getString("Datum"));
            Mont.setText(rs.getString("Montant"));
            Mont_Regle.setText(rs.getString("Montant_Regle"));
            Mont_Globale.setText(rs.getString("Montant_Globale"));
            Esp.setText(rs.getString("Espece"));
            Recup.setText(rs.getString("Recupere"));
            Res.setText(rs.getString("Reste"));
          }
        } catch (SQLException selectException) {
          displaySQLErrors(selectException);
        }
      }
    });    
    Insert_Account = new JButton("Ajouter une transaction");
    Insert_Account.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
                 
          Statement statement = connection.createStatement();
          int i= statement.executeUpdate("INSERT INTO Table_Globale VALUES("
                  +Trans.getText()+ ", " + "'"
                  +Datum.getText() + "', " + "'"
                  +Mont.getText() + "', " + "'"
                  +Mont_Regle.getText() + "', " + "'"
                  +Mont_Globale.getText() + "', " + "'"
                  +Esp.getText() + "', " + "'"
                  +Recup.getText() + "', " + "'"
                  +Res.getText() + "');");
          Account_Liste.removeAll();
          loadAccounts("Table_Globale");
        } catch (SQLException selectException) {
          displaySQLErrors(selectException);
          
        }
      }
    });    
    Delete_Account = new JButton("Supprimer une transaction");
    Delete_Account.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          Statement statement = connection.createStatement();
          int i = statement
              .executeUpdate("DELETE FROM Table_Globale WHERE Transaction_key = "
                  + Trans.getText());
          Account_Liste.removeAll();
          loadAccounts("Table_Globale");
        } catch (SQLException insertException) {
          displaySQLErrors(insertException);
        }
      }
    });    
    Update_Account = new JButton("Mettre a jour");
    Update_Account.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          Statement statement = connection.createStatement();
          //Trans, Datum,Mont,Mont_Regle,Mont_Globale, Esp, Recup,Res;
          int i = statement.executeUpdate("UPDATE Table_Globale "
              + "SET Datum = '" + Datum.getText() + "', "
              + "Montant = '" + Mont.getText() + "', "
              + "Montant_Regle = '" + Mont_Regle.getText() + "', "
              +"Montant_Globale = '" + Mont_Globale.getText() + "', "
              + "Espece = '" + Esp.getText() + "', "
              + "Recupere = '" + Recup.getText() + "', "
              + "Reste = '" + Res.getText() + "' "
              + "WHERE Transaction_key = '"
              + Account_Liste.getSelectedValue()+"';");
          Account_Liste.removeAll();
          loadAccounts("Table_Globale");
        } catch (SQLException insertException) {
          displaySQLErrors(insertException);
          System.out.println("hhh");
        }
      }
    });
    
    Next = new JButton(">");
    Next.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (!rs.isLast()) {
            rs.next();
            Trans.setText(rs.getString("Transaction_key"));
            Datum.setText(rs.getString("Datum"));
            Mont.setText(rs.getString("Montant"));
            Mont_Regle.setText(rs.getString("Montant_Regle"));
            Mont_Globale.setText(rs.getString("Montant_Globale"));
            Esp.setText(rs.getString("Espece"));
            Recup.setText(rs.getString("Recupere"));
            Res.setText(rs.getString("Reste"));
          }
        } catch (SQLException insertException) {
          displaySQLErrors(insertException);
        }
      }
    });		
   Previous = new JButton("<");
   Previous.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (!rs.isFirst()) {
            rs.previous();
            Trans.setText(rs.getString("Transaction_key"));
            Datum.setText(rs.getString("Datum"));
            Mont.setText(rs.getString("Montant"));
            Mont_Regle.setText(rs.getString("Montant_Regle"));
            Mont_Globale.setText(rs.getString("Montant_Globale"));
            Esp.setText(rs.getString("Espece"));
            Recup.setText(rs.getString("Recupere"));
            Res.setText(rs.getString("Reste"));
          }
        } catch (SQLException insertException) {
          displaySQLErrors(insertException);
        }
      }
    }); 
   Last = new JButton(">|");
   Last.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          rs.last();
            Trans.setText(rs.getString("Transaction_key"));
            Datum.setText(rs.getString("Datum"));
            Mont.setText(rs.getString("Montant"));
            Mont_Regle.setText(rs.getString("Montant_Regle"));
            Mont_Globale.setText(rs.getString("Montant_Globale"));
            Esp.setText(rs.getString("Espece"));
            Recup.setText(rs.getString("Recupere"));
            Res.setText(rs.getString("Reste"));
        } catch (SQLException insertException) {
          displaySQLErrors(insertException);
        }
      }
    });
   First = new JButton("|<");
   First.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          rs.first();
            Trans.setText(rs.getString("Transaction_key"));
            Datum.setText(rs.getString("Datum"));
            Mont.setText(rs.getString("Montant"));
            Mont_Regle.setText(rs.getString("Montant_Regle"));
            Mont_Globale.setText(rs.getString("Montant_Globale"));
            Esp.setText(rs.getString("Espece"));
            Recup.setText(rs.getString("Recupere"));
            Res.setText(rs.getString("Reste"));
        } catch (SQLException insertException) {
          displaySQLErrors(insertException);
        }
      }
    });
    Aller = new JButton("Aller a");
    Aller.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          rs.absolute(Integer.parseInt(Acced_Trans.getText()));
            Trans.setText(rs.getString("Transaction_key"));
            Datum.setText(rs.getString("Datum"));
            Mont.setText(rs.getString("Montant"));
            Mont_Regle.setText(rs.getString("Montant_Regle"));
            Mont_Globale.setText(rs.getString("Montant_Globale"));
            Esp.setText(rs.getString("Espece"));
            Recup.setText(rs.getString("Recupere"));
            Res.setText(rs.getString("Reste"));
        } catch (SQLException insertException) {
          displaySQLErrors(insertException);
        }
      }
    });
    
    Afficher_Table = new JButton("Afficher la Table");
    Afficher_Table.addActionListener(new ActionListener() {
          private String[] Col1, Col2, Col3, Col4, Col5, Col6, Col7, Col8,
                           title;
          
          private Object[][] data;
          private int i=0,j=0, N=999;
       
          @SuppressWarnings("empty-statement")
      public void actionPerformed(ActionEvent e) {
        try {
            Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = defaultToolkit .getScreenSize();
            
            Col1 = new String[100]; 
            Col2 = new String[100]; 
            Col3 = new String[100]; 
            Col4 = new String[100]; 
            Col5 = new String[100]; 
            Col6 = new String[100]; 
            Col7 = new String[100]; 
            Col8 = new String[100]; 
            data = new Object[N][8];
            
            ResultSet rs=statement.executeQuery("SELECT * FROM Table_Globale"); 
                    j=0;i=0;
                    while (rs.next()) { 
               
                                Col1[j] = rs.getString("Transaction_key"); 
                                Col2[j] = rs.getString("Datum"); 
                                Col3[j] = rs.getString("Montant"); 
                                Col4[j] = rs.getString("Montant_Regle"); 
                                Col5[j] = rs.getString("Montant_Globale"); 
                                Col6[j] = rs.getString("Espece"); 
                                Col7[j] = rs.getString("Recupere"); 
                                Col8[j] = rs.getString("Reste"); 
                                // pour autant de colonnes que tu veux retourner 
                                data[j][i]=   Col1[j]; 
                                data[j][i+1]= Col2[j]; 
                                data[j][i+2]= Col3[j];
                                data[j][i+3]= Col4[j];
                                data[j][i+4]= Col5[j];
                                data[j][i+5]= Col6[j];
                                data[j][i+6]= Col7[j];
                                data[j][i+7]= Col8[j];
                                j++; 
                            }        
//String title[] = {"Transaction_key", "Datum", "Montant"};
if (JSPVH!=null && Table!=null) {getContentPane().remove(JSPVH); getContentPane().remove(Table);}
//JTable Table = new JTable(data, title);
DefaultTableModel model = new DefaultTableModel();
//for(int i = 1; i < 4; i++)//on place les titres 
model.addColumn("Transaction_key");
model.addColumn("Heure");
model.addColumn("Montant");
Scanner sc = new Scanner(new FileInputStream("C:/data.txt"));//on ouvre le fichier pour le lire 
        while(sc.hasNext()){//tant qu'il y a des choses � lire 
            StringTokenizer token = new StringTokenizer(sc.nextLine(), " ");//on split une ligne en fonction de l'epace simple pour ne garder que le texte 
            Vector<String> rowData = new Vector<String>(); 
            while(token.hasMoreTokens())//lecture des token et on les mets dans le vecteur 
                rowData.add(token.nextToken()); 
            model.addRow(rowData);//on ajoute la ligne 
        } 
        sc.close();//fini :) 

JTable Table= new JTable(model);        
Table.setAutoscrolls(true);
Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
Table.setAutoCreateRowSorter(true);
Table.setBackground(Color.red);
JPanel tableau = new JPanel (new BorderLayout ()); 
tableau.add (Table.getTableHeader (), BorderLayout.NORTH); 
tableau.add (Table, BorderLayout.CENTER); 
JSPVH=new JScrollPane(Table);
getContentPane().add( JSPVH, BorderLayout.CENTER);
pack();
setSize(screenSize);
TableColumn column = null; 
for(int i=0;i<3 ; i++){ 
column= Table.getColumnModel().getColumn(i); 
column.setPreferredWidth(150); 
} 
loadAccounts("Table_Globale");
         } catch (SQLException  insertException) {
          displaySQLErrors(insertException);
        }     catch (FileNotFoundException ex) {
                  Logger.getLogger(Application_pharmacie.class.getName()).log(Level.SEVERE, null, ex);
              }
      }
    });
    
    JPanel first = new JPanel(new GridLayout(5, 1));
    first.add(Account_Liste_ScrollPane);
    first.add(Get_Account);
    first.add(Insert_Account);
    first.add(Delete_Account);
    first.add(Update_Account);

    Trans = new JTextField(15);
    Datum = new JTextField(15);
    Mont = new JTextField(15);
    Mont_Regle = new JTextField(15);
    Mont_Globale = new JTextField(15);
    Esp = new JTextField(15);
    Recup = new JTextField(15);
    Res = new JTextField(15);
    Acced_Trans= new JTextField(15);
    
    Table = new JTable(16, 8);
    Transaction= new JLabel("Transaction");
    Date= new JLabel("Date");
    Montant= new JLabel("Montant");
    Montant_Regle = new JLabel("Montant_Regle");
    Montant_Globale= new JLabel("Montant_Globale");
    Espece= new JLabel("Espece");
    Recupere= new JLabel("Recupere");
    Reste= new JLabel("Reste");
    
    
    JPanel second = new JPanel();
    second.setLayout(new GridLayout(8, 2));
    second.add(Transaction);
    second.add(Trans);
    second.add(Date);
    second.add(Datum);
    second.add(Montant);
    second.add(Mont);
    second.add(Montant_Regle);
    second.add(Mont_Regle);
    second.add(Montant_Globale);
    second.add(Mont_Globale);
    second.add(Espece);
    second.add(Esp);
    second.add(Recupere);
    second.add(Recup);
    second.add(Reste);
    second.add(Res);
    

    JPanel third = new JPanel();    
    third.setLayout(new GridLayout(6, 1));
    third.add(First);
    third.add(Previous);
    third.add(Next);
    third.add(Last);
    third.add(Acced_Trans);
    third.add(Aller);
    
    

    JPanel fourth = new JPanel();
    fourth.add(Afficher_Table);
    
    c.add(first);
    c.add(second);
    c.add(third);
    c.add(fourth);
    setSize(800, 500);
    setTitle("Application Quaisse");
    show();
    
   }
  public void connectToDB() {
    try {
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/caisse","root","");
      statement = connection.createStatement();
      //int i= statement.executeUpdate("DROP TABLE IF EXISTS Table_Globale;");
       int j= statement.executeUpdate("CREATE TABLE IF NOT EXISTS Table_Globale(Transaction_key INT, Datum DATE, "
                                     + "Montant DOUBLE, Montant_Regle DOUBLE, Montant_Globale DOUBLE, Espece DOUBLE,"
                                     + "Recupere DOUBLE, Reste DOUBLE, PRIMARY KEY(Transaction_key, Datum));");
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