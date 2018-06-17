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
import java.util.Random;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.DatasetFacade;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.StringToNominal;

/**
 *
 * @author piszc
 */
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
            /*instanceValue[0] = data.attribute(0).addStringValue(ReplaceLCore(newData.getLCore()));
             instanceValue[1] = data.attribute(1).addStringValue(ReplaceLSurf(newData.getLSurf()));
             instanceValue[2] = data.attribute(2).addStringValue(ReplaceLO2(newData.getLO2()));
             instanceValue[3] = data.attribute(3).addStringValue(newData.getLBp());
             instanceValue[4] = data.attribute(4).addStringValue(newData.getSurfStbl());
             instanceValue[5] = data.attribute(5).addStringValue(newData.getCoreStbl());
             instanceValue[6] = data.attribute(6).addStringValue(newData.getBpStbl());
             instanceValue[7] = data.attribute(7).addStringValue(newData.getComfort());*/
            instanceValue[0] = data.attribute(0).addStringValue("mid");
            instanceValue[1] = data.attribute(1).addStringValue("high");
            instanceValue[2] = data.attribute(2).addStringValue("excellent");
            instanceValue[3] = data.attribute(3).addStringValue("high");
            instanceValue[4] = data.attribute(4).addStringValue("stable");
            instanceValue[5] = data.attribute(5).addStringValue("stable");
            instanceValue[6] = data.attribute(6).addStringValue("stable");
            instanceValue[7] = data.attribute(7).addStringValue("10");
            
            Instance i1 = new DenseInstance(1.0, instanceValue);
            data.add(i1);
            data.setClassIndex(data.numAttributes()-1);
           // data.add(new DenseInstance(1.0, instanceValue));
            
            //String[] options = new String[2];
           // options[0] = "-R";
           // options[1] = "first-last";
            
            //StringToNominal stringNominal = new StringToNominal();
            //stringNominal.setOptions(options);
           // stringNominal.setInputFormat(data);
            //data = Filter.useFilter(data, stringNominal);
            
            System.err.print(data.toString());

            //Instances dataUnlabeled = new Instances("TestInstances", atts, 0);
           // dataUnlabeled.add(new DenseInstance(1.0, instanceValue));
           //dataUnlabeled.setClassIndex(dataUnlabeled.numAttributes() - 1);

            J48 cls = (J48) weka.core.SerializationHelper.read("j48.model");
            System.err.print(cls.toString());
            double result = cls.classifyInstance(data.instance(0));

            System.err.print(result);
            
            Dataset dataP = new Dataset("mid","high","excellent","high","stable","stable","stable","10");

            Dataset dataS = dataSetFacade.findByCos(dataP);
            
            System.out.print("aaa " + dataS);
            
            if (dataS != null) {
                System.err.print(dataS.getDecision());
            }else{
                Random gen = new Random(3);
                System.err.print(gen.nextInt()+1);
            }

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
