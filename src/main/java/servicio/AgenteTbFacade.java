/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import modelo.AgenteTb;

/**
 *
 * @author Endy
 */
@Stateless
public class AgenteTbFacade extends AbstractFacade<AgenteTb> {
    @PersistenceContext(unitName = "mhes_biblioteca_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgenteTbFacade() {
        super(AgenteTb.class);
    }
    
}
