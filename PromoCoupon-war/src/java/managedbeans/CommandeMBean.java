/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package managedbeans;

import entities.Commande;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import session.CommandeManager;

/**
 *
 * @author liuju
 */
@Named(value = "commandeMBean")
@SessionScoped
public class CommandeMBean implements Serializable{
    
    @EJB
    private CommandeManager cmdManager;
    private List<Commande> commandes;
    private Commande cmd;
    
     public CommandeMBean() {
         cmd = new Commande();
    }
    
    public  List<Commande> getCommandes(){
        if((commandes == null)|| (commandes.isEmpty())){
           refresh();
        }
        return commandes;
    }

    private void refresh() {
         if((commandes == null) || (commandes.isEmpty()))
            commandes = cmdManager.getAllCommande();
    }

    /** 
     * returns details of a customer. Useful for displaying in a form a customer's details 
     * @return 
     */  
    public Commande getDetails() {  
        return cmd;  
    }  
  
    /** 
     * Action handler - Called when a line in the table is clicked 
     * @param utilisateur 
     * @return 
     */  
    public String showDetails(Commande cmd) {  
        this.cmd = cmd;  
        return "CommandeDetails"; // will display CustomerDetails.xml JSF page  
    }  
  
    /** 
     * Action handler - update the customer model in the database. 
     * called when one press the update button in the form 
     * @return 
     */  
    public String update() {  
        System.out.println("###UPDATE###");  
        cmd = cmdManager.update(cmd);  
        return "CommandeList"; // will display the customer list in a table  
    }  
  
    /** 
     * Action handler - returns to the list of customers in the table 
     */  
    public String list() {  
        System.out.println("###LIST###");  
        return "CommandeList";  
    } 
    
    public String delectAction(Commande commande){
        commandes.remove(commande);
        cmdManager.removeCmd(commande);
        return "CommandeList"; 
    }
    
}
