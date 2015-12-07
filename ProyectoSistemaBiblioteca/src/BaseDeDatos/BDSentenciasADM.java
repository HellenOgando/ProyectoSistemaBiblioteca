/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import Interfaz_Grafica.Login;
import Interfaz_Grafica.VentanaPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Administrador;
import objetos.Estudiante;
import objetos.Libros;

/**
 *
 * @author HellenFranchesca
 */
public class BDSentenciasADM {
    PreparedStatement pstm = null;
    
    Connection cnn = DriverManager.getConnection("jdbc:sqlite:SistemaBibliotecaBD.db");
    public static String Codigo =""; // variable que guardara la matricula del login para ponerlo en los otros frame
    public static String TipoUsuario="";
    public static String Nombre ="";
    public static String Apellido="";
    public static int Aprobacion = 0;// variable que se usa para saber si se logue el usuario y cerrar la ventana del login
    


public  BDSentenciasADM() throws ClassNotFoundException, SQLException {
    }

   PreparedStatement pstm(String insert_into__Invitados_Nom_EmpApe_EmpUsua) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


public void insert(Object datos) throws SQLException {

if (datos instanceof Administrador) {
            Administrador adm = (Administrador) datos;
            //pstm = miconexion1.miconexion.prepareStatement("INSERT into  Usuarios (Nom_Inv,Ape_Inv,Tel_Inv,Dir_Inv,Sex_Inv,Email_Inv) VALUES (?,?,?,?,?,?)");
            //pstm = miconexion1.miconexion.prepareStatement("INSERT into  Usuarios  VALUES (?,?,?,?,?,?,?)");
            // pstm = miconexion1.miconexion.prepareStatement("INSERT into  Usuarios (Matricula,Nombre,Apellido,Tipo De Usuario,Fecha de Nacimiento, Sexo, Carrera) VALUES (?,?,?,?,?,?,?)");
            String sql = "INSERT into  Administradores  VALUES (?,?,?,?,?,?,?)";
            pstm = cnn.prepareStatement(sql);
            pstm.setInt(1, adm.getCodigo());
            pstm.setString(2, adm.getNombre());
            pstm.setString(3, adm.getApellido());
            pstm.setString(4, adm.getContrasena());
            pstm.setString(5, adm.getFechaNacimiento());
            pstm.setString(6, adm.getGenero());
            pstm.setString(7, adm.getTipoUsuario());
            pstm.executeUpdate();
        }
}




 public void update(Object datos) throws SQLException {
  
     if (datos instanceof Administrador) {
            Administrador adm = (Administrador) datos;
          String sql = "UPDATE Administradores SET Nombre=?, Apellido=?,FechaNacimiento=?, Genero=? WHERE Matricula=?";
          
            pstm = cnn.prepareStatement(sql);

            pstm.setString(1, adm.getNombre());
            pstm.setString(2, adm.getApellido());
            pstm.setString(3, adm.getFechaNacimiento());
            pstm.setString(4, adm.getGenero());
            pstm.setInt(5, adm.getCodigo());
            
            pstm.execute();
        }
 }
 
  public void delete(Object datos) throws SQLException {
      
       if (datos instanceof Administrador) {
            Administrador adm = (Administrador) datos;
            String sql = "DELETE FROM Administradores WHERE Codigo=?";
            pstm = cnn.prepareStatement(sql);
            pstm.setInt(1, adm.getCodigo());
            pstm.execute();
        }
  }
  
  
  public void acceder(String codigo, String contrasena) {// metodo para el login 
        String TipoU = "";
        String Cod = "";
        String Pass = "";
        String nombre = "" , apellido= ""; 
        String sql = "SELECT * FROM Administradores WHERE Codigo='" + codigo + "' AND Contrasena='" + contrasena + "'";
        try {
            Statement st = cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                
                Cod = rs.getString("Codigo");
                Pass = rs.getString("Contrasena");
                nombre = rs.getString("Nombre");
                apellido = rs.getString("Apellido");

            }
            if (!Cod.equals(codigo) && !Cod.equals(contrasena)){
                JOptionPane.showMessageDialog(null, " Matricula o Contraseña Incorrecta");
                return;
                
            }
          
                
                 Cod = codigo;
                TipoUsuario = TipoU;
                Nombre = nombre;
                Apellido = apellido;
                Aprobacion = 1;
                
                
                VentanaPrincipal q = new VentanaPrincipal();
                q.setVisible(true);
                q.pack();
               

            
           
           

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
}
  
  public  boolean CambiarContraseña(int codigo, String ContraseñaActual , String ContraNew1, String ContraNew2){
      String Pass ="";
      String sql = "SELECT * FROM Admnistradores WHERE Codigo='" + codigo + "' AND Contraseña='" + ContraseñaActual + "'";
      //String sql2 ="UPDATE Usuarios SET Contraseña= '"+ContraNew1+"' WHERE Matricula='"+matricula+"'";
       String sql2 ="UPDATE Administradores SET Contraseña= ? WHERE Codigo = ?";
      try {
          Statement st = cnn.createStatement();
          ResultSet rs = st.executeQuery(sql);
           while (rs.next()) {
               Pass=rs.getString("Contraseña");
               
           }
           if ( !Pass.equals(ContraseñaActual)){
                JOptionPane.showMessageDialog(null, " La Contraseña Actual es Incorrecta");
                return false;
                
                
            }
           if (!ContraNew1.equals(ContraNew2)){
               
               JOptionPane.showMessageDialog(null, " Las Contraseñas Nuevas No Coinciden");
               return false;
           }else {
           //JOptionPane.showMessageDialog(null, matricula +" "+ContraNew1  );
           // st.executeQuery(sql2);
           
           pstm = cnn.prepareStatement(sql2);
           pstm.setInt(2, codigo);
           pstm.setString(1, ContraNew1);
           pstm.executeUpdate();
                   
           JOptionPane.showMessageDialog(null, " Se ha Cambiado la Contraseña Correctamente");
           }
      }catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
  }
 

}