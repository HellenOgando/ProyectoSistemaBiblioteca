package BaseDeDatos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;

public class BDConexion {
    Connection miconexion;
    Statement stSentencias;
ResultSet rsDatos ;
PreparedStatement psPrepararSentencias ;
Connection conect = null;
    public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:SistemaBibliotecaBD.db");
      
      stmt = c.createStatement();
    String sql = "CREATE TABLE Estudiantes " +
                   "(Matricula INT PRIMARY KEY     NOT NULL," +
                   " Nombre           TEXT    NOT NULL, " + 
                   " Apellido         TEXT    NOT NULL, " +
                   " Contrasena       TEXT    NOT NULL, " +
                   " TipoUsuario      TEXT    NOT NULL, " + 
                   " FechaNacimiento  DATE    NOT NULL, " +
                   " Genero           TEXT    NOT NULL, " +
                   " Carrera          TEXT    NOT NULL     )"; 
      stmt.executeUpdate(sql);
       sql = "CREATE TABLE Administradores " +
                   "(Codigo INT PRIMARY KEY     NOT NULL," +
                   " Nombre           TEXT    NOT NULL, " + 
                   " Apellido         TEXT    NOT NULL, " +
                   " Contrasena       TEXT    NOT NULL, " + 
                   " FechaNacimiento  DATE    NOT NULL, " +
                   " Genero           TEXT    NOT NULL,  "+ 
                   " TipoUsuario      TEXT    NOT NULL)"; 
      stmt.executeUpdate(sql);
      
       sql = "CREATE TABLE Carreras " +
                   "(IDCarrera   INT PRIMARY KEY     NOT NULL," +
                   " Descripcion TEXT NOT NULL )"; 
      stmt.executeUpdate(sql);
      
      sql = "CREATE TABLE Libros " +
                   "(IDLibro INT PRIMARY KEY     NOT NULL," +
                   " Titulo           TEXT    NOT NULL, " + 
                   " Autor            TEXT    NOT NULL, " + 
                   " LugarPublicacion TEXT    NOT NULL, " +
                   " FechaPublicacion DATE    NOT NULL, " + 
                   " Edicion          TEXT     NOT NULL, " +
                   " Editorial        TEXT     NOT NULL, " +
                   " NumeroPaginas    TEXT     NOT NULL, " +
                   " CantidadLibros   TEXT     NOT NULL, " +
                   " CodigoUbicacion  TEXT    NOT NULL )"; 
      stmt.executeUpdate(sql);
      sql = "CREATE TABLE Historial " +
                   "(IDTransaccion        INT  PRIMARY KEY     NOT NULL," +
                   " IDUsuario            INT  NOT NULL, " + 
                   " IDLibro              INT  NOT NULL, " +
                   " FechaEntrega         DATE, " + 
                   " FechaDevolucion      DATE, " +
                   " Estado               TEXT, " +
                   " UltimaActualizacion  DATE, " +
                   " FOREIGN KEY(IDUsuario) REFERENCES Estudiantes(Matricula),"+ 
                   " FOREIGN KEY(IDLibro) REFERENCES Libros(IDLibros))"; 
      stmt.executeUpdate(sql);
     /*sql = "INSERT INTO Historial (IDTransaccion, IDUsuario, IDLibro, Estado) VALUES (3, 131066, 2, 'Entregado')";
      stmt.executeUpdate(sql);*/
              stmt.close();
      
      
      
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Base de datos abierta exitosamente");
           
        }
    public Connection Conexion1() {
        try {
       
      Class.forName("org.sqlite.JDBC");
      
      conect = DriverManager.getConnection("jdbc:sqlite:SistemaBibliotecaBD.db");
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
        return conect;
    
    
    }
    public ResultSet consulta(String sql) throws SQLException {
        try {
            conect = DriverManager.getConnection("jdbc:sqlite:SistemaBibliotecaBD.db");
            psPrepararSentencias = conect.prepareStatement(sql);
            rsDatos = psPrepararSentencias.executeQuery();
           
        } catch(SQLException ex){throw ex;}
        
   return rsDatos;
}
    }

