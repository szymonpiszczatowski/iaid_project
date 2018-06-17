/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author macie
 */
@Entity
@Table(name = "dataset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dataset.findAll", query = "SELECT d FROM Dataset d"),
    @NamedQuery(name = "Dataset.findById", query = "SELECT d FROM Dataset d WHERE d.id = :id"),
    @NamedQuery(name = "Dataset.findByLCore", query = "SELECT d FROM Dataset d WHERE d.lCore = :lCore"),
    @NamedQuery(name = "Dataset.findByLSurf", query = "SELECT d FROM Dataset d WHERE d.lSurf = :lSurf"),
    @NamedQuery(name = "Dataset.findByLO2", query = "SELECT d FROM Dataset d WHERE d.lO2 = :lO2"),
    @NamedQuery(name = "Dataset.findByLBp", query = "SELECT d FROM Dataset d WHERE d.lBp = :lBp"),
    @NamedQuery(name = "Dataset.findBySurfStbl", query = "SELECT d FROM Dataset d WHERE d.surfStbl = :surfStbl"),
    @NamedQuery(name = "Dataset.findByCoreStbl", query = "SELECT d FROM Dataset d WHERE d.coreStbl = :coreStbl"),
    @NamedQuery(name = "Dataset.findByBpStbl", query = "SELECT d FROM Dataset d WHERE d.bpStbl = :bpStbl"),
    @NamedQuery(name = "Dataset.findByComfort", query = "SELECT d FROM Dataset d WHERE d.comfort = :comfort"),
    @NamedQuery(name = "Dataset.findByDecision", query = "SELECT d FROM Dataset d WHERE d.decision = :decision"),
    @NamedQuery(name = "Dataset.findByDatasetType", query = "SELECT d FROM Dataset d WHERE d.datasetType = :datasetType")})
public class Dataset implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "l_core")
    private String lCore;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "l_surf")
    private String lSurf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "l_o2")
    private String lO2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "l_bp")
    private String lBp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "surf_stbl")
    private String surfStbl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "core_stbl")
    private String coreStbl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "bp_stbl")
    private String bpStbl;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "comfort")
    private String comfort;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "decision")
    private String decision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataset_type")
    private int datasetType;

    public Dataset() {
    }

    public Dataset(Integer id) {
        this.id = id;
    }

    public Dataset(Integer id, String lCore, String lSurf, String lO2, String lBp, String surfStbl, String coreStbl, String bpStbl, String comfort, String decision, int datasetType) {
        this.id = id;
        this.lCore = lCore;
        this.lSurf = lSurf;
        this.lO2 = lO2;
        this.lBp = lBp;
        this.surfStbl = surfStbl;
        this.coreStbl = coreStbl;
        this.bpStbl = bpStbl;
        this.comfort = comfort;
        this.decision = decision;
        this.datasetType = datasetType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLCore() {
        return lCore;
    }

    public void setLCore(String lCore) {
        this.lCore = lCore;
    }

    public String getLSurf() {
        return lSurf;
    }

    public void setLSurf(String lSurf) {
        this.lSurf = lSurf;
    }

    public String getLO2() {
        return lO2;
    }

    public void setLO2(String lO2) {
        this.lO2 = lO2;
    }

    public String getLBp() {
        return lBp;
    }

    public void setLBp(String lBp) {
        this.lBp = lBp;
    }

    public String getSurfStbl() {
        return surfStbl;
    }

    public void setSurfStbl(String surfStbl) {
        this.surfStbl = surfStbl;
    }

    public String getCoreStbl() {
        return coreStbl;
    }

    public void setCoreStbl(String coreStbl) {
        this.coreStbl = coreStbl;
    }

    public String getBpStbl() {
        return bpStbl;
    }

    public void setBpStbl(String bpStbl) {
        this.bpStbl = bpStbl;
    }

    public String getComfort() {
        return comfort;
    }

    public void setComfort(String comfort) {
        this.comfort = comfort;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(int datasetType) {
        this.datasetType = datasetType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dataset)) {
            return false;
        }
        Dataset other = (Dataset) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Dataset[ id=" + id + " ]";
    }
    
}
