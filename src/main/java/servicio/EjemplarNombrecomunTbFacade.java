/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.EjemplarNombrecomunTb;

/**
 *
 * @author Endy
 */
@Stateless
public class EjemplarNombrecomunTbFacade extends AbstractFacade<EjemplarNombrecomunTb> {
    @PersistenceContext(unitName = "mhes_biblioteca_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EjemplarNombrecomunTbFacade() {
        super(EjemplarNombrecomunTb.class);
    }
    
}
