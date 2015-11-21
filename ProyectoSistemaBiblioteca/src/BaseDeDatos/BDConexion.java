package BaseDeDatos;


import java.sql.Connection;
import java.sql.DriverManager;

public class BDConexion {
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
    
    }

