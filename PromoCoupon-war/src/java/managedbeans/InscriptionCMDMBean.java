/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbeans;

import entities.Adresse;
import entities.Commande;
import entities.Coupon;
import entities.Livraison;
import entities.Utilisateur;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import session.AdresseManager;
import session.CommandeManager;
import session.CouponManager;
import session.LivraisonManager;
import session.UtilisateurManager;

/**
 *
 * @author liuju
 */
@Named(value = "inscriptionCMDMBean")
@RequestScoped
public class InscriptionCMDMBean implements Serializable{

    private Map<String, String> settings;
    @EJB
    private CouponManager couponM;
    @EJB
    private LivraisonManager livM;
    @EJB
    private UtilisateurManager userM;
    @EJB
    private CommandeManager cmdM;
    @EJB
    private AdresseManager adrM;
    
    private Utilisateur user;
    private Coupon coupon;
    private Livraison liv;
    private Commande cmd;
    private Adresse adr;
    
    /**
     * Creates a new instance of InscriptionCMDMBean
     */
    public InscriptionCMDMBean() {
        settings = new HashMap<String, String>();
        user = new Utilisateur();
        coupon = new Coupon();
        liv = new Livraison();
        cmd = new Commande();
        adr = new Adresse();
    }

    public String save() {
        user = userM.getUserById(new Integer(1));
        if(user == null){
            System.out.println("ERROR.xhtml?msg=userInconnu");
            return "ERROR.xhtml?msg=userInconnu";
        }
        coupon = couponM.getCouponById(new Long(1));
        if(coupon == null){
            System.out.println("ERROR.xhtml?msg=couponInconnu");
            return "ERROR.xhtml?msg=userInconnu";
        }
        
        adr.setIdAdresse(adrM.nextId());
        adr.setNumEtRue(settings.get("rue"));
        adr.setComple(settings.get("compl"));
        adr.setVille(settings.get("ville"));
        adr.setPays(settings.get("pays"));
        adr.setCodePostale(settings.get("code"));
        adr.setDateModif(new Date());
        adrM.update(adr);
        
        liv.setIdL(livM.nextId());
        liv.setNomRecep("nomRecep");
        liv.setPrenomRecep("prenomRecep");
        liv.setTelRecep("telRecep");
        liv.setAdrRecep(adr);
        livM.update(liv);
        
        cmd.setIdC(cmdM.nextId());
        cmd.setIdCoupon(coupon);
        cmd.setIdLivraison(liv);
        cmd.setIdU(user);
        cmd.setMomentC(new Date());
        cmd.setMsgClient(settings.get("msgClient"));
        cmd.setMsgEntreprise(settings.get("msgEntreprise"));
        cmd.setStatus("payed");
        cmdM.update(cmd);
        
        //3--coupon vendu
        Short s = 3;
        coupon.setStatus(s);
        couponM.update(coupon);
        return "CommandeList";
    }
    
    public Map<String, String> getSettings() {
        return settings;
    }
}
