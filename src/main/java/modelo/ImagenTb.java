/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Endy
 */
@Entity
@Table(name = "imagen_tb")
@NamedQueries({
    @NamedQuery(name = "ImagenTb.findAll", query = "SELECT i FROM ImagenTb i"),
    @NamedQuery(name = "ImagenTb.findByEIdimagen", query = "SELECT i FROM ImagenTb i WHERE i.eIdimagen = :eIdimagen"),
    @NamedQuery(name = "ImagenTb.findByCNombre", query = "SELECT i FROM ImagenTb i WHERE i.cNombre = :cNombre"),
    @NamedQuery(name = "ImagenTb.findByCRuta", query = "SELECT i FROM ImagenTb i WHERE i.cRuta = :cRuta")})
public class ImagenTb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "e_idimagen")
    private Integer eIdimagen;
    @Size(max = 80)
    @Column(name = "c_nombre")
    private String cNombre;
    @Size(max = 200)
    @Column(name = "c_ruta")
    private String cRuta;
    @JoinColumn(name = "e_idtaxonomia", referencedColumnName = "e_idtaxonomia")
    @ManyToOne
    private TaxonomiaTb eIdtaxonomia;

    public ImagenTb() {
    }

    public ImagenTb(Integer eIdimagen) {
        this.eIdimagen = eIdimagen;
    }

    public Integer getEIdimagen() {
        return eIdimagen;
    }

    public void setEIdimagen(Integer eIdimagen) {
        this.eIdimagen = eIdimagen;
    }

    public String getCNombre() {
        return cNombre;
    }

    public void setCNombre(String cNombre) {
        this.cNombre = cNombre;
    }

    public String getCRuta() {
        return cRuta;
    }

    public void setCRuta(String cRuta) {
        this.cRuta = cRuta;
    }

    public TaxonomiaTb getEIdtaxonomia() {
        return eIdtaxonomia;
    }

    public void setEIdtaxonomia(TaxonomiaTb eIdtaxonomia) {
        this.eIdtaxonomia = eIdtaxonomia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eIdimagen != null ? eIdimagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImagenTb)) {
            return false;
        }
        ImagenTb other = (ImagenTb) object;
        if ((this.eIdimagen == null && other.eIdimagen != null) || (this.eIdimagen != null && !this.eIdimagen.equals(other.eIdimagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ImagenTb[ eIdimagen=" + eIdimagen + " ]";
    }
    
}
