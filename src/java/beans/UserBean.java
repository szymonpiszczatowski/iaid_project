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
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToNominal;

/**
 *
 * @author piszc
 */
@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean implements Serializable {

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

            ArrayList<Attribute> attributeList = new ArrayList<Attribute>();

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
            attributeList.add(new Attribute("@@class@@", classVal));

            Instances data = new Instances("TestInstances", attributeList, 0);
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
            
            Instances dataUnlabeled = new Instances("TestInstances", attributeList, 0);
            dataUnlabeled.add(new DenseInstance(1.0, instanceValue));
            dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);   

           /* inst_co.setValue(lcore, ReplaceLCore(newData.getLCore()));
            inst_co.setValue(lsure, ReplaceLSurf(newData.getLSurf()));
            inst_co.setValue(l02, ReplaceLO2(newData.getLO2()));
            inst_co.setValue(lbp, "a");
            inst_co.setValue(ss, newData.getSurfStbl());
            inst_co.setValue(cs, newData.getCoreStbl());
            inst_co.setValue(bs, newData.getBpStbl());
            inst_co.setValue(comfort, newData.getComfort());
            data.add(inst_co);*/


            Classifier cls = (Classifier) weka.core.SerializationHelper.read("j48.model");
            double result = cls.classifyInstance(dataUnlabeled.firstInstance());
            //System.err.print(cls.toString());
            System.err.print(result);

        } catch (Exception ex) {
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
