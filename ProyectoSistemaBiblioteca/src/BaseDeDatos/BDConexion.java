package BaseDeDatos;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class BDConexion {
    
Connection miconexion;
Statement stSentencias;
ResultSet rsDatos ;
PreparedStatement psPrepararSentencias ;
Connection conect = null;

/*

    public static void main( String args[] )
  {
    Connection c = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:SistemaBibliotecaBD.db");
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Base de datos abierta exitosamente");
            
        }
    */



public BDConexion() throws ClassNotFoundException, SQLException {
    try {
        //String ruta = ":SistemaBibliotecaBD.db";
       // String ruta = ":C:\\Users\\albe211\\Documents\\NetBeansProjects\\ProyectoSistemaBiblioteca\\bd\\SistemaBibliotecaBD.sqlite";
       // String ruta = ":C:\\Users\\albert\\Dropbox\\7mo Semestre\\Analisis y Diseno Sistema\\Sistema Final\\bd\\SistemaBibliotecaBD.sqlite";
        String ruta = ":C:\\Users\\albe211\\Dropbox\\7mo Semestre\\Analisis y Diseno Sistema\\Sistema Final\\bd\\SistemaBibliotecaBD.sqlite";
      Class.forName("org.sqlite.JDBC");
      conect = DriverManager.getConnection("jdbc:sqlite"+ruta);
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Base de datos abierta exitosamente");
  

    }


    public ResultSet consulta(String sql) throws SQLException {
        try {

            psPrepararSentencias = conect.prepareStatement(sql);
            rsDatos = psPrepararSentencias.executeQuery();
           
        } catch(SQLException ex){throw ex;}
    return rsDatos;
}
    
    public Connection Conexion1() {
        try {
        //String ruta = ":SistemaBibliotecaBD.db";
       // String ruta = ":C:\\Users\\albe211\\Documents\\NetBeansProjects\\ProyectoSistemaBiblioteca\\bd\\SistemaBibliotecaBD.sqlite";
     //   String ruta = ":C:\\Users\\albert\\Dropbox\\7mo Semestre\\Analisis y Diseno Sistema\\Sistema Final\\bd\\SistemaBibliotecaBD.sqlite";
        String ruta = ":C:\\Users\\albe211\\Dropbox\\7mo Semestre\\Analisis y Diseno Sistema\\Sistema Final\\bd\\SistemaBibliotecaBD.sqlite";
      Class.forName("org.sqlite.JDBC");
      conect = DriverManager.getConnection("jdbc:sqlite"+ruta);
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
        return conect;
    
    
    }}

