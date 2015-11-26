
package BaseDeDatos;

import java.sql.*;
import objetos.Usuarios;
import Interfaz_Grafica.GestionUsuarios;
import Interfaz_Grafica.Login;
import Interfaz_Grafica.VentanaPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



public class BDSentencias {
    
    BDConexion miconexion1 = new BDConexion();
    PreparedStatement pstm = null;
    Connection cnn = miconexion1.Conexion1();
    public static String Matricula =""; // variable que guardara la matricula del login para ponerlo en los otros frame
    public static String TipoUsuario="";
    public static String Nombre ="";
    public static String Apellido="";
    public static int Aprobacion = 0;// vatiable que se usa para saber si se logue el usuarioy cerrar la ventana del login
    


public  BDSentencias() throws ClassNotFoundException, SQLException {
    }

   PreparedStatement pstm(String insert_into__Invitados_Nom_EmpApe_EmpUsua) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


public void insert(Object datos) throws SQLException {

if (datos instanceof Usuarios) {
            Usuarios inv = (Usuarios) datos;
            //pstm = miconexion1.miconexion.prepareStatement("INSERT into  Usuarios (Nom_Inv,Ape_Inv,Tel_Inv,Dir_Inv,Sex_Inv,Email_Inv) VALUES (?,?,?,?,?,?)");
            //pstm = miconexion1.miconexion.prepareStatement("INSERT into  Usuarios  VALUES (?,?,?,?,?,?,?)");
            // pstm = miconexion1.miconexion.prepareStatement("INSERT into  Usuarios (Matricula,Nombre,Apellido,Tipo De Usuario,Fecha de Nacimiento, Sexo, Carrera) VALUES (?,?,?,?,?,?,?)");
            String sql = "INSERT into  Usuarios  VALUES (?,?,?,?,?,?,?,?)";
            pstm = cnn.prepareStatement(sql);
            pstm.setInt(1, inv.getMatricula());
            pstm.setString(2, inv.getNombreUsuario());
            pstm.setString(3, inv.getApellidoUsuario());
            pstm.setString(4, inv.getContraseña());
            pstm.setString(5, inv.getTipoDeUsuario());
            pstm.setString(6, inv.getFechaNac());
            pstm.setString(7, inv.getSexo());
            pstm.setString(8, inv.getCarrera());
            pstm.executeUpdate();
        }
}

 public void update(Object datos) throws SQLException {
  
     if (datos instanceof Usuarios) {
            Usuarios inv = (Usuarios) datos;
          String sql = "UPDATE Usuarios SET Nombre=?,Apellido=?,Contraseña=?,TipodeUsuario=?,FechadeNacimiento=?, Sexo=?, Carrera=? WHERE Matricula=?";
            pstm = cnn.prepareStatement(sql);
            //pstm = miconexion1.miconexion.prepareStatement("UPDATE Empleados  SET Nom_Emp=?,Ape_Emp=?,Usuario=?,Contrasena=?,Perfil_Emp=? WHERE idEmpleado=?");
            pstm.setString(1, inv.getNombreUsuario());
            pstm.setString(2, inv.getApellidoUsuario());
            pstm.setString(3, inv.getContraseña());
            pstm.setString(4, inv.getTipoDeUsuario());
            pstm.setString(5, inv.getFechaNac());
            pstm.setString(6, inv.getSexo());
            pstm.setString(7, inv.getCarrera());
            pstm.setInt(8, inv.getMatricula());
            pstm.execute();
        }
 }
 
  public void delete(Object datos) throws SQLException {
      
       if (datos instanceof Usuarios) {
            Usuarios users = (Usuarios) datos;
            String sql = "DELETE FROM Usuarios WHERE Matricula=?";
            pstm = cnn.prepareStatement(sql);
            pstm.setInt(1, users.getMatricula());
            pstm.execute();
        }
  }
  
  public void acceder(String matricula, String password) {// metodo para el login 
        String TipoU = "";
        String Matric = "";
        String Pass = "";
        String nombre = "" , apellido= ""; 
        String sql = "SELECT * FROM Usuarios WHERE Matricula='" + matricula + "' AND Contraseña='" + password + "'";
        try {
            Statement st = cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                TipoU = rs.getString("TipodeUsuario");
                Matric = rs.getString("Matricula");
                Pass = rs.getString("Contraseña");
                nombre = rs.getString("Nombre");
                apellido = rs.getString("Apellido");

            }
            if (!Matric.equals(matricula) && !Pass.equals(password)){
                JOptionPane.showMessageDialog(null, " Matricula o Contraseña Incorrecta");
                return;
                
            }
            if (TipoU.equals("Administrador") || TipoU.equals("Estudiante")  ) {// cap.equals("Usuario")
                
                 Matricula = matricula;
                TipoUsuario = TipoU;
                Nombre = nombre;
                Apellido = apellido;
                Aprobacion = 1;
                
                
                VentanaPrincipal q = new VentanaPrincipal();
                q.setVisible(true);
                q.pack();
                
                
                
                /* este metodo funciona tambien, solo que hay que poner public los textfields a los que seran enviados
                
                VentanaPrincipal q = new VentanaPrincipal();
                q.txtMatriculaVP.setText(matricula);
                q.txtUsuarioVP.setText(nombre + " " + apellido);
                q.txtTipoUsuarioVP.setText(TipoU);
                q.setVisible(true);
                q.pack();
                //JOptionPane.showMessageDialog(a, matricula);
                
                        */

            }
           
           

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
}
  
  public  boolean CambiarContraseña(int matricula, String ContraseñaActual , String ContraNew1, String ContraNew2){
      String Pass ="";
      String sql = "SELECT * FROM Usuarios WHERE Matricula='" + matricula + "' AND Contraseña='" + ContraseñaActual + "'";
      //String sql2 ="UPDATE Usuarios SET Contraseña= '"+ContraNew1+"' WHERE Matricula='"+matricula+"'";
       String sql2 ="UPDATE Usuarios SET Contraseña= ? WHERE Matricula = ?";
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
           pstm.setInt(2, matricula);
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


