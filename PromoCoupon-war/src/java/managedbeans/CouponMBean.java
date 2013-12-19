/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbeans;

import entities.Coupon;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import session.CouponManager;

/**
 *
 * @author liuju
 */
@Named(value = "couponMBean")
@SessionScoped
public class CouponMBean implements Serializable{

    private List<Coupon> coupons;
    private Coupon coupon;
    @EJB
    private CouponManager couponManager;
    
    public CouponMBean() {
        coupon=new Coupon();
        couponManager = new CouponManager();
    }
     /** 
     * returns customer list for display in a datatable DataTable 
     * @return 
     */  
    public List<Coupon> getCoupons() {  
        if((coupons == null) || (coupons.isEmpty()))
            refresh();
        return couponManager.getAllCoupons();  
    }  
  
    
    private void refresh() {
        if((coupons == null) || (coupons.isEmpty()))
            coupons = couponManager.getAllCoupons();
        }
    
//    public void refresh() {  
//        tousLesComptes = compteBancaireFacade.findAll();  
//    }  
  
    /** 
     * returns details of a customer. Useful for displaying in a form a customer's details 
     * @return 
     */  
    public Coupon getDetails() {  
        return coupon;  
    }  
  
    /** 
     * Action handler - Called when a line in the table is clicked 
     * @param coupon 
     * @return 
     */  
    public String showDetails(Coupon coupon) {  
        this.coupon = coupon;  
        return "Coupondetails"; // will display CustomerDetails.xml JSF page  
    }  
  
    /** 
     * Action handler - update the customer model in the database. 
     * called when one press the update button in the form 
     * @return 
     */  
    public String  update() {
        System.out.println("###UPDATE###");  
        if( couponManager.exiteCoupon(coupon.getReference()) )
            return "ERROR.xhtml?msg=CouponExisted";
        coupon = couponManager.update(coupon);  
        return "CouponList"; // will display the customer list in a table  
    }  
  
    /** 
     * Action handler - returns to the list of customers in the table 
     */  
    public String list() {  
        System.out.println("###LIST###");  
        return "CouponList";  
    }  

    public void update(Coupon coupon) {
        System.out.println("###UPDATE###");  
        couponManager.update(coupon);  
    }
    
     public int getNbCouponsDisponibles(long idOffre){
        int res = 0;
        List<Coupon> coupons = couponManager.getAllCoupons();
        for(Coupon coupon : coupons)
            if(coupon.getIdOffre().getIdO().longValue()==idOffre && coupon.getStatus().shortValue()==1)
                res++;
        return res;
    }
}
