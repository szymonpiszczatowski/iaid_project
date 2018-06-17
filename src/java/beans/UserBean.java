/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Dataset;
import java.io.Serializable;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.DatasetFacade;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Debug.Random;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean implements Serializable {
    @EJB
    private DatasetFacade dataSetFacade;
    
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

    public void Classify() {

        try {

            FastVector<Attribute> atts = new FastVector<Attribute>();
            FastVector<String> classVal = new FastVector<String>();
            classVal.add("I");
            classVal.add("S");
            classVal.add("A");

            atts.add(new Attribute("L-CORE", (FastVector) null));
            atts.add(new Attribute("L-SURF", (FastVector) null));
            atts.add(new Attribute("L-O2", (FastVector) null));
            atts.add(new Attribute("L-BP", (FastVector) null));
            atts.add(new Attribute("SURF-STBL", (FastVector) null));
            atts.add(new Attribute("CORE-STBL", (FastVector) null));
            atts.add(new Attribute("BP-STBL", (FastVector) null));
            atts.add(new Attribute("COMFORT", (FastVector) null));
            atts.add(new Attribute("@@class@@", classVal));

            Instances data = new Instances("TestInstances", atts, 0);
            data.setClassIndex(data.numAttributes() - 1);

            double[] instanceValue = new double[data.numAttributes()];
            instanceValue[0] = data.attribute(0).addStringValue(ReplaceLCore(newData.getLCore()));
            instanceValue[1] = data.attribute(1).addStringValue(ReplaceLSurf(newData.getLSurf()));
            instanceValue[2] = data.attribute(2).addStringValue(ReplaceLO2(newData.getLO2()));
            instanceValue[3] = data.attribute(3).addStringValue(newData.getLBp());
            instanceValue[4] = data.attribute(4).addStringValue(newData.getSurfStbl());
            instanceValue[5] = data.attribute(5).addStringValue(newData.getCoreStbl());
            instanceValue[6] = data.attribute(6).addStringValue(newData.getBpStbl());
            instanceValue[7] = data.attribute(7).addStringValue(newData.getComfort());
            data.add(new DenseInstance(1.0, instanceValue));

            Instances dataUnlabeled = new Instances("TestInstances", atts, 0);
            dataUnlabeled.add(new DenseInstance(1.0, instanceValue));
            dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);
//            System.err.print(dataUnlabeled.toString());

            Classifier cls = (Classifier) weka.core.SerializationHelper.read("j48.model");
            double result = cls.classifyInstance(dataUnlabeled.firstInstance());
            decision = dataUnlabeled.firstInstance().classAttribute().value((int) result);
//            decision=hxd();

        } catch (Exception ex) {
            System.err.print(ex);
        }

        
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

    private String ReplaceLCore(String value) {
        double doubleValue = Double.parseDouble(value);
        if (doubleValue > 37) {
            return "high";
        }
        if (36 <= doubleValue && doubleValue <= 37) {
            return "mid";
        }
        if (doubleValue < 36) {
            return "low";
        }
        return "low";
    }

    private String ReplaceLSurf(String value) {
        double doubleValue = Double.parseDouble(value);
        if (doubleValue > 36.5) {
            return "high";
        }
        if (35 <= doubleValue && doubleValue <= 36.5) {
            return "mid";
        }
        if (doubleValue < 35) {
            return "low";
        }
        return "low";
    }
    
    
       private String hxd(){
        List<Dataset> dataBase = dataSetFacade.findAll();
        for (int i = 0; i < dataBase.size(); i++) {
           Dataset rowData = dataBase.get(i);
            if(rowData.getLCore()== ReplaceLCore(newData.getLCore()) 
                    && rowData.getLSurf() == ReplaceLSurf(newData.getLSurf())
                    && rowData.getLO2() ==ReplaceLO2(newData.getLO2())
                    && rowData.getLBp() == newData.getLBp()
                    && rowData.getSurfStbl() == newData.getSurfStbl()
                    && rowData.getCoreStbl() ==  newData.getCoreStbl()
                    && rowData.getBpStbl() == newData.getBpStbl()
                    && rowData.getComfort() == newData.getComfort()){
                       return rowData.getDecision();
            }
        }
        
        Random generator = new Random();
        String names[] = {"A","I","S"};
        Integer of =generator.nextInt(3);

         Dataset newData2 = new Dataset(ReplaceLCore(newData.getLCore()), ReplaceLSurf(newData.getLSurf()), ReplaceLO2(newData.getLO2()), 
                 newData.getLBp(), newData.getSurfStbl(), newData.getCoreStbl(), newData.getBpStbl(), newData.getComfort(), names[of], 1);
                
        dataSetFacade.create(newData2);
        return names[of];
          
    }
       
    

    private String ReplaceLO2(String value) {
        double doubleValue = Double.parseDouble(value);
        if (doubleValue >= 98) {
            return "excellent";
        }
        if (90 <= doubleValue && doubleValue < 98) {
            return "good";
        }
        if (80 <= doubleValue && doubleValue < 90) {
            return "fair";
        }
        if (doubleValue < 80) {
            return "poor";
        }
        return "poor";
    }

    public UserBean() {
        newData = new Dataset();
    }
    
}
