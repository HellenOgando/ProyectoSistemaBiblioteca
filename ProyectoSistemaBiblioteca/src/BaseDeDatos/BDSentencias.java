
package BaseDeDatos;

import java.sql.*;
import objetos.Usuarios;
import Interfaz_Grafica.GestionUsuarios;



public class BDSentencias {
    
    BDConexion miconexion1 = new BDConexion();
    PreparedStatement pstm = null;
    Connection cnn = miconexion1.Conexion1();
    


public BDSentencias() throws ClassNotFoundException, SQLException {
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

}


