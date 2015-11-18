
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class BDConexion {
    
    Connection conn = null;
    
    public static Connection ConnecrDb(){
    
        try{
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdb:sqlite:C:\\Users\\HellenFranchesca\\Documents\\NetBeansProjects\\ProyectoSistemaBiblioteca\\ProyectoSistemaBibliotecaBD.sqlite");
        JOptionPane.showMessageDialog(null, "Connection Established");
        return conn;
        
        }catch (Exception e){
        
            JOptionPane.showMessageDialog(null, e);
            return null;
            
        }
    
    }
    
}
