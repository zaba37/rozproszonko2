/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import entity.Admindictionary;
import entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author zaba3
 */
@Stateless
public class AdmindictionaryFacade extends AbstractFacade<Admindictionary> {
    @PersistenceContext(unitName = "projektPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdmindictionaryFacade() {
        super(Admindictionary.class);
    }
    
     public Admindictionary findById (int id){
        TypedQuery<Admindictionary> q = em.createNamedQuery("Admindictionary.findById", Admindictionary.class);
        q.setParameter("id", id);
        List<Admindictionary> result = q.getResultList();
        
        if(result.size() != 0){
            return result.get(0);
        }else{
            return null;
        }
     }
    
}
