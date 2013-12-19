/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author John624
 */
@Entity
@Table(name = "Offre")
@XmlRootElement
@NamedQueries({
    
    @NamedQuery(name = "Offre.maxId", query = "SELECT max(o.idO) FROM Offre o"),
    @NamedQuery(name = "Offre.findAll", query = "SELECT o FROM Offre o"),
    @NamedQuery(name = "Offre.findByType", query = "SELECT o.idO FROM Offre o join Categorie c on o.categorie=c.idCateg WHERE c.nomCateg = 'Resto'"),
    @NamedQuery(name = "Offre.findByIdO", query = "SELECT o FROM Offre o WHERE o.idO = :idO"),
    @NamedQuery(name = "Offre.findByTitle", query = "SELECT o FROM Offre o WHERE o.title = :title"),
    @NamedQuery(name = "Offre.findByIdC", query = "SELECT o FROM Offre o WHERE o.categorie = :categorie"),
    @NamedQuery(name = "Offre.findByPrixOrigin", query = "SELECT o FROM Offre o WHERE o.prixOrigin = :prixOrigin"),
    @NamedQuery(name = "Offre.findByPrixActuel", query = "SELECT o FROM Offre o WHERE o.prixActuel = :prixActuel"),
    @NamedQuery(name = "Offre.findByDateDebut", query = "SELECT o FROM Offre o WHERE o.dateDebut = :dateDebut"),
    @NamedQuery(name = "Offre.findByDateFin", query = "SELECT o FROM Offre o WHERE o.dateFin = :dateFin"),
    @NamedQuery(name = "Offre.findByValidation", query = "SELECT o FROM Offre o WHERE o.validation = :validation"),
    @NamedQuery(name = "Offre.findByDateModif", query = "SELECT o FROM Offre o WHERE o.dateModif = :dateModif"),
    @NamedQuery(name = "Offre.findByNbCoupons", query = "SELECT o FROM Offre o WHERE o.nbCoupons = :nbCoupons"),
    @NamedQuery(name = "Offre.findByLimite", query = "SELECT o FROM Offre o WHERE o.limite = :limite"),
    @NamedQuery(name = "Offre.findByCategorieGlobale", query = "SELECT o FROM Offre o WHERE o.categorieGlobale = :categorieGlobale")})
public class Offre implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idO")
    private Long idO;
    @Size(max = 150)
    @Column(name = "title")
    private String title;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prixOrigin")
    private BigDecimal prixOrigin;
    @Column(name = "prixActuel")
    private BigDecimal prixActuel;
    @Column(name = "dateDebut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "dateFin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Lob
    @Column(name = "dscription")
    private String dscription;
    @Lob
    @Column(name = "image")
    private String image;
    @Lob
    @Column(name = "conseille")
    private String conseille;
    @Column(name = "validation")
    private Integer validation;
    @Column(name = "dateModif")
    @Temporal(TemporalType.DATE)
    private Date dateModif;
    @Column(name = "nbCoupons")
    private Short nbCoupons;
    @Column(name = "limite")
    private Short limite;
    @Size(max = 50)
    @Column(name = "categorieGlobale")
    private String categorieGlobale;
    @OneToMany(mappedBy = "idOffre")
    private Collection<Coupon> couponCollection;
    @JoinColumn(name = "idE", referencedColumnName = "idE")
    @ManyToOne
    private Entreprise idE;
    @JoinColumn(name = "categorie", referencedColumnName = "idCateg")
    @ManyToOne
    private Categorie categorie;

    public Offre() {
    }

    public Offre(Long idO) {
        this.idO = idO;
    }

    public Long getIdO() {
        return idO;
    }

    public void setIdO(Long idO) {
        this.idO = idO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrixOrigin() {
        return prixOrigin;
    }

    public void setPrixOrigin(BigDecimal prixOrigin) {
        this.prixOrigin = prixOrigin;
    }

    public BigDecimal getPrixActuel() {
        return prixActuel;
    }

    public void setPrixActuel(BigDecimal prixActuel) {
        this.prixActuel = prixActuel;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDscription() {
        return dscription;
    }

    public void setDscription(String dscription) {
        this.dscription = dscription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getConseille() {
        return conseille;
    }

    public void setConseille(String conseille) {
        this.conseille = conseille;
    }

    public Integer getValidation() {
        return validation;
    }

    public void setValidation(Integer validation) {
        this.validation = validation;
    }

    public Date getDateModif() {
        return dateModif;
    }

    public void setDateModif(Date dateModif) {
        this.dateModif = dateModif;
    }

    public Short getNbCoupons() {
        return nbCoupons;
    }

    public void setNbCoupons(Short nbCoupons) {
        this.nbCoupons = nbCoupons;
    }

    public Short getLimite() {
        return limite;
    }

    public void setLimite(Short limite) {
        this.limite = limite;
    }

    public String getCategorieGlobale() {
        return categorieGlobale;
    }

    public void setCategorieGlobale(String categorieGlobale) {
        this.categorieGlobale = categorieGlobale;
    }

    
    @XmlTransient
    public Collection<Coupon> getCouponCollection() {
        return couponCollection;
    }

    public void setCouponCollection(Collection<Coupon> couponCollection) {
        this.couponCollection = couponCollection;
    }

    public Entreprise getIdE() {
        return idE;
    }

    public void setIdE(Entreprise idE) {
        this.idE = idE;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idO != null ? idO.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Offre)) {
            return false;
        }
        Offre other = (Offre) object;
        if ((this.idO == null && other.idO != null) || (this.idO != null && !this.idO.equals(other.idO))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Offre[ idO=" + idO + " ]";
    }
    
}
