/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.util;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import modelo.EjemplarTb;

/**
 *
 * @author Endy
 */
@Named(value = "controladorSesion")
@SessionScoped

public class ControladorSesion implements Serializable {

    private EjemplarTb ejemplar;

    public EjemplarTb getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(EjemplarTb ejemplar) {
        this.ejemplar = ejemplar;
    }
    
    //private TaxonomiaTb taxonomia;

    /*
     public FamiliaTb getFamilia() {
     return familia;
     }

     public void setFamilia(FamiliaTb familia) {
     this.familia = familia;
     }
     */

    /**
     * Creates a new instance of ControladorSesion
     */
    public ControladorSesion() {
    }

}
