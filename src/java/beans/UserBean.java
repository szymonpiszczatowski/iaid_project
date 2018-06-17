/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Dataset;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author piszc
 */
@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean {
    
    public String decision;

    public Dataset newData;

    public String getDecision() {
        return decision;
    }

    public void setDecision(String Decision) {
        this.decision = Decision;
    }
    
    public Dataset getNewData() {
        return newData;
    }

    public void setNewData(Dataset newData) {
        this.newData = newData;
    }

    public void Classify(){
        decision = "Jesteś psem!";
    }
    
    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
        newData = new Dataset();
    }
    
}
