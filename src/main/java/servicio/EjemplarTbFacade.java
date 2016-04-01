/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import modelo.AgenteIdentificaEjemplarTb;
import modelo.EjemplarTb;

/**
 *
 * @author Endy
 */
@Stateless
public class EjemplarTbFacade extends AbstractFacade<EjemplarTb> {

    @PersistenceContext(unitName = "mhes_biblioteca_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EjemplarTbFacade() {
        super(EjemplarTb.class);
    }

    public List<EjemplarTb> EjemplaresPorNombreCientifico(String nombre) {
        String Sentencia = "select * from ejemplar_tb e inner join taxonomia_tb t on e.e_idtaxonomia=t.e_idtaxonomia where t.c_nombre like '%"+nombre+"%'";
        Query query = em.createNativeQuery(Sentencia, EjemplarTb.class);
        return query.getResultList();
    }
    
    public List<EjemplarTb> EjemplaresPorNombreComun(String nombre) {
        String Sentencia = "select * from ejemplar_tb e inner join taxonomia_tb t on e.e_idtaxonomia=t.e_idtaxonomia inner join nombrecomun_tb n on n.e_idtaxonomia=t.e_idtaxonomia where n.c_nombre like '%"+nombre+"%'";
        Query query = em.createNativeQuery(Sentencia, EjemplarTb.class);
        return query.getResultList();
    }
    
    public List<AgenteIdentificaEjemplarTb> ejemplarRecolector(Integer ejemplar, String recolector) {

        TypedQuery<AgenteIdentificaEjemplarTb> query = em.createQuery("SELECT p FROM AgenteIdentificaEjemplarTb p WHERE p.ejemplarTb.eIdejemplar=:h AND p.agenteIdentificaEjemplarTbPK.cTipo=:recol ORDER BY P.eSecuencia", AgenteIdentificaEjemplarTb.class);
        query.setParameter("h", ejemplar);
        query.setParameter("recol", recolector);
        return query.getResultList();
    }
    
    public List<AgenteIdentificaEjemplarTb> ejemplarIdentificador(Integer ejemplar, String identificador) {

        TypedQuery<AgenteIdentificaEjemplarTb> query = em.createQuery("SELECT p FROM AgenteIdentificaEjemplarTb p WHERE p.ejemplarTb.eIdejemplar=:h AND p.agenteIdentificaEjemplarTbPK.cTipo=:ident ORDER BY P.eSecuencia", AgenteIdentificaEjemplarTb.class);
        query.setParameter("h", ejemplar);
        query.setParameter("ident", identificador);
        return query.getResultList();
    }
}
