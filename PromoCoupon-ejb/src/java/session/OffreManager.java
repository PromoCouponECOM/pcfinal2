package session;

import entities.Categorie;
import entities.Offre;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class OffreManager {
    @PersistenceContext(unitName = "PromoCoupon-ejbPU")
    private EntityManager em;

    public List<Offre> getAllOffre() {
        Query query= em.createNamedQuery("Offre.findAll");   
        return query.getResultList();
    }
    
    
    public String getTitreOffre(int idOffre){
        Query query= em.createNamedQuery("Offre.findByIdO");
        query.setParameter("IdO", idOffre);
        
        List<Offre> tmp = query.getResultList();
        if(tmp!=null && !tmp.isEmpty())
            return tmp.get(0).getTitle();
        return null;
    }
    
     public Offre getOffreById(long idOffre) {
        Query query= em.createNamedQuery("Offre.findByIdO");
        query.setParameter("idO", idOffre);
        
        List<Offre> tmp = query.getResultList();
        if(tmp!=null && !tmp.isEmpty())
            return tmp.get(0);
        return null;
    }
    
     public String getTitreEtPrixById(int idOffre){
        Query query= em.createNamedQuery("Offre.findByIdO");
        query.setParameter("IdO", idOffre);
        
        List<Offre> tmp = query.getResultList();
        if(tmp!=null && !tmp.isEmpty())
            return tmp.get(0).getTitle()+"|"+tmp.get(0).getPrixActuel();
        return null;
    }

    public Offre update(Offre offre) {
        return em.merge(offre);
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    public Long nextId(){
        Query query = em.createNamedQuery("Offre.maxId");
        Long res = (Long)query.getResultList().get(0);
        if(res==null)
            return new Long(0);
        return res+1;
    }
    
    public List<Offre> getSpecialOffres (String typeOffre){
        Query queryCategorie = em.createNamedQuery("Categorie.findByNomCateg");
        queryCategorie.setParameter("nomCateg", typeOffre);

        Categorie idc = (Categorie)queryCategorie.getResultList().get(0);
        
        Query queryOffre = em.createNamedQuery("Offre.findByIdC");
        queryOffre.setParameter("categorie",idc);
        
        System.out.println("###hello"+queryOffre.getResultList()+"###");
        return queryOffre.getResultList();
    }
    
    public List<Offre> getSpecialGlobalOffres(String typeGlobal) {
        Query query = em.createNamedQuery("Offre.findByCategorieGlobale");
        query.setParameter("categorieGlobale", typeGlobal);
        return query.getResultList();
    }

    public Offre getOffreGlobalByNameselectedGlobal(String selectedGlobal) {
        Query query = em.createNamedQuery("Offre.findByCategorieGlobale");
        query.setParameter("selectedGlobal", selectedGlobal);
        Offre res = (Offre)query.getSingleResult();
        return res;
    }
}
