/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.Adresse;
import entities.Coupon;
import entities.Offre;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.PersistenceContext;
import session.AdresseManager;
import session.EntrepriseManager;
import session.UtilisateurManager;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import panier.Panier;
import session.CouponManager;
import session.OffreManager;

/**
 *
 * @author John624
 */

@Named(value = "sessionMBean")
@SessionScoped
public class SessionMBean implements Serializable {

    public enum State {
        NotConnected, ConnectedAsCustomer, ConnectedAsOrganization, ConnectedAsAdmin
    }

    private State state;
    private Integer idUser;
    private String loginName;
    private String loginNameTRY;
    private String passwordTRY;
    private Panier panier;
    
    public String getLoginNameTRY() {
        return loginNameTRY;
    }

    public void setLoginNameTRY(String loginNameTRY) {
        this.loginNameTRY = loginNameTRY;
    }

    public String getPasswordTRY() {
        return passwordTRY;
    }

    public void setPasswordTRY(String passwordTRY) {
        this.passwordTRY = passwordTRY;
    }

    @EJB
    private UtilisateurManager UM;
    @EJB
    private EntrepriseManager EM;
    @EJB
    private CouponManager CM;
    @EJB
    private OffreManager OM;
    

    public SessionMBean() {
        this.state = State.NotConnected;
        this.loginName = null;
        this.idUser = null;
        UM = new UtilisateurManager();
        EM = new EntrepriseManager();
        CM = new CouponManager();
        OM = new OffreManager();
        panier = new Panier();
    }

    /**
     * returns customer list for display in a datatable DataTable
     *
     * @return
     */
    public String userConnect(String login, String password) {
        if (UM.authUtilisateur(login, password)) {
            this.loginName = login;
            this.state = State.ConnectedAsCustomer;
            return "index";
        } else {
            if (EM.authEntreprise(login, password)) {
                this.loginName = login;
                this.state = State.ConnectedAsOrganization;
                panier = null;
                return "offresEntreprise";
            }
        }
        return "#";
    }
    
    public String tryConnection(){
        return userConnect(loginNameTRY, passwordTRY);
    }

    public String disconnect() {
        this.state = State.NotConnected;
        panier = null;
        return "index";
    }

    public String adminConnect(String login, String password) {
        return null;
    }

    public String getState() {
        return this.state.toString();
    }

    public Boolean isConnectedAsCustomer() {
        return this.state == State.ConnectedAsCustomer;
    }

    public Boolean isConnectedAsOrganization() {
        return this.state == State.ConnectedAsOrganization;
    }

    public String getLogin() {
        return this.loginName;
    }

    public String ajouterOffreAuPanier(long idOffre) {
        List<Coupon> coupons = CM.getAllCoupons();
        for(Coupon coupon : coupons){
            if((coupon.getIdOffre().getIdO().longValue()==idOffre && coupon.getStatus().shortValue()==1)){
                System.out.println("Offre n°"+idOffre+", Coupon n°"+coupon.getIdCoupon().longValue()+" (id de l'offre du coupon: "+coupon.getIdOffre().getIdO().longValue());
                System.out.println("Status du coupon: "+coupon.getStatus().shortValue());
                CM.modifyCouponStatus(coupon, (short)2);
                panier.addCoupon((int)idOffre, coupon.getIdCoupon().longValue(), coupon.getIdOffre().getPrixActuel().doubleValue());
                System.out.println("Coupon n°"+coupon.getIdCoupon().longValue()+" (offre n°"+idOffre
                +") ajouté au panier.");
                //retirerOffreDuPanier(idOffre);
                return "";
            }
        }
        System.out.println("pas ajouté au panier");
        //String[] res = CM.getFirstAvailableForOffre(idOffre).split("\\|");
        /*long idCoupon = Long.parseLong(res[0]);
        double prix = Double.parseDouble(res[1]);
        if (idCoupon == -1) {
            return "#";
        }
        panier.addCoupon(idOffre, idCoupon, prix);*/
        return "#";
    }

    public String retirerOffreDuPanier(long idOffre) {
        Offre offre = OM.getOffreById(idOffre);
        if(offre!=null){
            long idCoupon = panier.removeOne((int)idOffre, offre.getPrixActuel().doubleValue());
            if(idCoupon!=-1){
                Coupon coupon = CM.getCouponById(idCoupon);
                CM.modifyCouponStatus(coupon, (short)1);
            }
        }
        return "#";
    }
    
    public List<Offre> getOffresPanier(){
        ArrayList<Offre> res = new ArrayList<Offre>();
        for(Integer i : panier.getOffres()){
            res.add(OM.getOffreById(i));
        }
        return res;
    }
    /*
        public List<String> refreshPanierMiniature(){
        ArrayList<String> res = new ArrayList<String>();
        for(Integer i : panier.getOffres()){
            
            res.add(OM.getOffreById(i).getTitle()+" - (x"+panier.getQuantity(i)+")");
            System.out.println("MINIATURE PANIER: "+res.get(res.size()-1)); 
        }
        
        return res;
    }*/
        
    
    public double getPrixTotalPanier(){
        return panier.getPrixTotal();
    }
}
