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
public class Carrera {
    
    String CodigoCarrera,Descripcion;

    public Carrera(String CodigoCarrera, String Descripcion) {
        this.CodigoCarrera = CodigoCarrera;
        this.Descripcion = Descripcion;
    }

    public String getCodigoCarrera() {
        return CodigoCarrera;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setCodigoCarrera(String CodigoCarrera) {
        this.CodigoCarrera = CodigoCarrera;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    
    
}
