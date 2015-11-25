/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author albe211
 */
public class Usuarios {
    int Matricula;
     String NombreUsuario,ApellidoUsuario,Contraseña,TipoDeUsuario,FechaNac,Sexo,Carrera;

    public Usuarios(int Matricula, String NombreUsuario, String ApellidoUsuario, String Contraseña, String TipoDeUsuario, String FechaNac, String Sexo, String Carrera) {
        this.Matricula = Matricula;
        this.NombreUsuario = NombreUsuario;
        this.ApellidoUsuario = ApellidoUsuario;
        this.Contraseña = Contraseña;
        this.TipoDeUsuario = TipoDeUsuario;
        this.FechaNac = FechaNac;
        this.Sexo = Sexo;
        this.Carrera = Carrera;
    }

    public int getMatricula() {
        return Matricula;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public String getApellidoUsuario() {
        return ApellidoUsuario;
    }

    public void setMatricula(int Matricula) {
        this.Matricula = Matricula;
    }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public void setApellidoUsuario(String ApellidoUsuario) {
        this.ApellidoUsuario = ApellidoUsuario;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = Contraseña;
    }

    public void setTipoDeUsuario(String TipoDeUsuario) {
        this.TipoDeUsuario = TipoDeUsuario;
    }

    public void setFechaNac(String FechaNac) {
        this.FechaNac = FechaNac;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public void setCarrera(String Carrera) {
        this.Carrera = Carrera;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public String getTipoDeUsuario() {
        return TipoDeUsuario;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public String getSexo() {
        return Sexo;
    }

    public String getCarrera() {
        return Carrera;
    }
    
    


    
}
