/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Coupon;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author John624
 */
@Stateless
@LocalBean
public class CouponManager {

    @PersistenceContext(unitName = "PromoCoupon-ejbPU")
    private EntityManager em;

//    public Coupon getCoupon(int i){
//        Query query = em.createNamedQuery("Coupon.findByIdCoupon").setParameter("idCoupon", i);
//        return query;
//    }singleton
    
    
    
    public void modifyCouponStatus(Coupon coupon, short status){
            coupon.setStatus(status);
            coupon = this.update(coupon);
    }
    
    public List<Long> getAllAvailableForOffre(long idOffre) {
        ArrayList<Long> res = new ArrayList<Long>();
        
        Query query = em.createNamedQuery("Coupon.findAllAvailableByOffre");
        query.setParameter("idOffre", idOffre);
        List<Coupon> coupons = query.getResultList();
        for (Coupon coupon : coupons) {
            res.add(new Long(coupon.getIdCoupon().longValue()));
        }
        return res;
    }
    
    //returns idCoupon|prixActuel
    public String getFirstAvailableForOffre(int idOffre) {
        Query query = em.createNamedQuery("Coupon.findAllAvailableByOffre");
        query.setParameter("idOffre", idOffre);
        List<Coupon> coupons = query.getResultList();
        if(coupons.isEmpty())
            return null;
        else
        {
            Coupon coupon = coupons.get(0);
            coupon.setStatus((short)2);
            coupon = this.update(coupon);
            return coupon.getIdCoupon().longValue()+"|"+coupon.getIdOffre().getPrixActuel();
        }
    }

    public List<Coupon> getAllCoupons() {
        Query query = em.createNamedQuery("Coupon.findAll");
        return query.getResultList();
    }

    public Coupon update(Coupon coupon) {
        return em.merge(coupon);
    }

    public void persist(Object object) {
        em.persist(object);
    }

    public Integer nextId() {
        Query query = em.createNamedQuery("Coupon.maxId");
        Integer res = (Integer) query.getResultList().get(0);
        if (res == null) {
            return new Integer(0);
        }
        return res + 1;
    }

    public boolean exiteCoupon(String reference) {
        Query query = em.createNamedQuery("Coupon.findByReference");
        query.setParameter("reference", reference);
        return !query.getResultList().isEmpty();
    }
    
        
    public Coupon getCouponById(Long idC){
         List<Coupon> coupons = getAllCoupons();
         for (Coupon c : coupons) {
            if(c.getIdCoupon().longValue()== idC)
                return c;
        }
        return null;
    }
}
