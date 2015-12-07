/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import objetos.Libros;

/**
 *
 * @author HellenFranchesca
 */
public class BDSentenciasLIB {
    
    PreparedStatement pstm = null;
    Connection cnn = DriverManager.getConnection("jdbc:sqlite:SistemaBibliotecaBD.db");
    
    public  BDSentenciasLIB() throws ClassNotFoundException, SQLException {
    }
    public void insertLibros(Object datos) throws SQLException {

if (datos instanceof Libros) {
            Libros lib = (Libros) datos;
            
            String sql = "INSERT into  Libros  VALUES (?,?,?,?,?,?,?,?,?,?)";
            pstm = cnn.prepareStatement(sql);
            pstm.setInt(1, lib.getIDLibro());
            pstm.setString(2, lib.getTitulo());
            pstm.setString(3, lib.getAutor());
            pstm.setString(4, lib.getFechaPublicacion());
            pstm.setString(5, lib.getLugarPublicacion());
            pstm.setString(6, lib.getEdicion());
            pstm.setString(7, lib.getEditorial());
            pstm.setString(8, lib.getNumeropaginas());
            pstm.setString(9, lib.getCantidadLibros());
            pstm.setString(10, lib.getCodigoubicacion());
            pstm.executeUpdate();
        }
}
     public void updateLibros(Object datos) throws SQLException {
  
     if (datos instanceof Libros) {
            Libros lib = (Libros) datos;
          String sql = "UPDATE Libros SET IDLibro=?,Titulo=?,Autor=?,FechaPublicacion=?,LugarPublicacion=?, Edicion=?, Editorial=?,NumeroPaginas=?, CantidadLibros=?, CodigoUbicacion=? WHERE IDLibro=?";
            pstm = cnn.prepareStatement(sql);
            //pstm = miconexion1.miconexion.prepareStatement("UPDATE Empleados  SET Nom_Emp=?,Ape_Emp=?,Usuario=?,Contrasena=?,Perfil_Emp=? WHERE idEmpleado=?");
            pstm = cnn.prepareStatement(sql);
            pstm.setInt(1, lib.getIDLibro());
            pstm.setString(2, lib.getTitulo());
            pstm.setString(3, lib.getAutor());
            pstm.setString(4, lib.getFechaPublicacion());
            pstm.setString(5, lib.getLugarPublicacion());
            pstm.setString(6, lib.getEdicion());
            pstm.setString(7, lib.getEditorial());
            pstm.setString(8, lib.getNumeropaginas());
            pstm.setString(9, lib.getCantidadLibros());
            pstm.setString(10, lib.getCodigoubicacion());
            pstm.setInt(11, lib.getIDLibro());
            pstm.execute();
        }
 }
     public void deleteLibro(Object datos) throws SQLException {
      
       if (datos instanceof Libros) {
            Libros lib = (Libros) datos;
            String sql = "DELETE FROM Libros WHERE IDLibro=?";
            pstm = cnn.prepareStatement(sql);
            pstm.setInt(1, lib.getIDLibro());
            pstm.execute();
        }
  }
     
     
    
}
