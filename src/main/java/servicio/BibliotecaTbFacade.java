/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import modelo.BibliotecaTb;

/**
 *
 * @author Endy
 */
@Stateless
public class BibliotecaTbFacade extends AbstractFacade<BibliotecaTb> {
    @PersistenceContext(unitName = "mhes_biblioteca_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BibliotecaTbFacade() {
        super(BibliotecaTb.class);
    }
    
    public BibliotecaTb Buscar() {
        TypedQuery<BibliotecaTb> query = em.createQuery("SELECT p FROM BibliotecaTb p WHERE p.eIdbiblioteca=1", BibliotecaTb.class);
        return query.getSingleResult();
    }
    
}
