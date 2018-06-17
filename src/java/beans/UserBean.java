/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Dataset;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

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
        
        
        try {
      
            ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2);

            Attribute lcore = new Attribute("L-CORE");
            Attribute lsure = new Attribute("L-SURF");
            Attribute l02 = new Attribute("L-O2");
            Attribute lbp = new Attribute("L-BP");
            Attribute ss = new Attribute("SURF-STBL");
            Attribute cs = new Attribute("CORE-STBL");
            Attribute bs = new Attribute("BP-STBL");
            Attribute comfort = new Attribute("COMFORT");

            attributeList.add(lcore);
            attributeList.add(lsure);
            attributeList.add(l02);
            attributeList.add(lbp);
            attributeList.add(ss);
            attributeList.add(cs);
            attributeList.add(bs);
            attributeList.add(comfort);        
                            
            ArrayList<String> classVal = new ArrayList<String>();
            classVal.add("I");
            classVal.add("S");
            classVal.add("A");
            attributeList.add(new Attribute("@@class@@",classVal));

            Instances data = new Instances("TestInstances",attributeList,0);

            Instance inst_co;
            inst_co = new DenseInstance(data.numAttributes());
            data.add(inst_co);

            inst_co.setValue(lcore, parseInt(newData.getLCore()));
            inst_co.setValue(lsure, parseInt(newData.getLSurf()));
            inst_co.setValue(l02, parseInt(newData.getLO2()));
            inst_co.setValue(lbp, parseInt(newData.getLBp()));                      
            inst_co.setValue(ss, newData.getSurfStbl());
            inst_co.setValue(cs, newData.getCoreStbl());
            inst_co.setValue(bs, newData.getBpStbl());
            inst_co.setValue(comfort, parseInt(newData.getComfort()));

            Classifier cls = (Classifier) weka.core.SerializationHelper.read("j48.model");
            double result=cls.classifyInstance(inst_co);
            System.err.print(cls.toString());  
            System.err.print(result);  
            
        }catch(Exception ex){
         System.err.print(ex);
        }


        
        decision = "Jesteś psem!";
    }
    
    /**
     * Creates a new instance of UserBean
     */
        private int GetIntValueFromStringDecision(String decision) {
        if (decision.equals("I")) {
            return 0;
        }
        if (decision.equals("S")) {
            return 1;
        }
        if (decision.equals("A")) {
            return 2;
        }
        return 0;
    }
    
    
    
    
    public UserBean() {
        newData = new Dataset();
    }
    
}
