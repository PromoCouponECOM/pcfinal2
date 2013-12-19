    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panier;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zalgo
 */
public class Item {

    private int idOffre;
    private List<Long> idCoupons;

    public Item(int idOffre, long idCoupon) {
        this.idOffre = idOffre;
        this.idCoupons = new ArrayList<Long>();
        this.idCoupons.add(new Long(idCoupon));
    }

    public void addCoupon(long idCoupon) {
        this.idCoupons.add(idCoupon);
    }

    public long removeOne() {
        if (this.idCoupons.size() > 0) {
            return this.idCoupons.remove(0);
        } else {
            return -1;
        }
    }

    public int getQuantity() {
        return this.idCoupons.size();
    }

    public int getIdOffre(){
        return this.idOffre;
    }
    
    public List<Long> getIdCoupons(){
        return this.idCoupons;
    }
    
    public Boolean equals(Item item) {
        return this.idOffre == item.idOffre;
    }
}
