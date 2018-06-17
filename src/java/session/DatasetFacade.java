/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Dataset;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author piszc
 */
@Stateless
public class DatasetFacade extends AbstractFacade<Dataset> {
    @PersistenceContext(unitName = "patient_projectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatasetFacade() {
        super(Dataset.class);
    }
    
    /*public Dataset findDataSet(){
        
    }
    public Dataset findByCos(Dataset data) {
        try{
            return em.createNamedQuery("Forum.findByCos").setParameter("status", status).getResultList();
        }catch(NoResultException ex)
        {
            return null;
        }
    }*/
}
