/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author HellenFranchesca
 */
public class Libros {
    int IDLibro;
    String Titulo, Autor, FechaPublicacion, LugarPublicacion, Edicion, Editorial, Numeropaginas, CantidadLibros, Codigoubicacion; 

    public Libros(int IDLibro, String Titulo, String Autor, String FechaPublicacion, String LugarPublicacion, String Edicion, String Editorial, String Numeropaginas, String CantidadLibros, String Codigoubicacion) {
        this.IDLibro = IDLibro;
        this.Titulo = Titulo;
        this.Autor = Autor;
        this.FechaPublicacion = FechaPublicacion;
        this.LugarPublicacion = LugarPublicacion;
        this.Edicion = Edicion;
        this.Editorial = Editorial;
        this.Numeropaginas = Numeropaginas;
        this.CantidadLibros = CantidadLibros;
        this.Codigoubicacion = Codigoubicacion;
    }

    public int getIDLibro() {
        return IDLibro;
    }

    public void setIDLibro(int IDLibro) {
        this.IDLibro = IDLibro;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    public String getFechaPublicacion() {
        return FechaPublicacion;
    }

    public void setFechaPublicacion(String FechaPublicacion) {
        this.FechaPublicacion = FechaPublicacion;
    }

    public String getLugarPublicacion() {
        return LugarPublicacion;
    }

    public void setLugarPublicacion(String LugarPublicacion) {
        this.LugarPublicacion = LugarPublicacion;
    }

    public String getEdicion() {
        return Edicion;
    }

    public void setEdicion(String Edicion) {
        this.Edicion = Edicion;
    }

    public String getEditorial() {
        return Editorial;
    }

    public void setEditorial(String Editorial) {
        this.Editorial = Editorial;
    }

    public String getNumeropaginas() {
        return Numeropaginas;
    }

    public void setNumeropaginas(String Numeropaginas) {
        this.Numeropaginas = Numeropaginas;
    }

    public String getCantidadLibros() {
        return CantidadLibros;
    }

    public void setCantidadLibros(String CantidadLibros) {
        this.CantidadLibros = CantidadLibros;
    }

    public String getCodigoubicacion() {
        return Codigoubicacion;
    }

    public void setCodigoubicacion(String Codigoubicacion) {
        this.Codigoubicacion = Codigoubicacion;
    }
    
    
    
}
