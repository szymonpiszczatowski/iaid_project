/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Dataset;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import session.DatasetFacade;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.StringToNominal;

/**
 *
 * @author piszc
 */
@ManagedBean(name = "AdminBean")
@SessionScoped
public class AdminBean implements Serializable {

    @EJB
    private DatasetFacade dataSetFacade;

    public AdminBean() {
        //List<?> data = dataSetFacade.findAll();
    }

    public void buildKlas() {
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

        System.out.println(atts.toString());

        Instances dataRaw = new Instances("TestInstances", atts, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
        
        List<Dataset> dataBase = dataSetFacade.findAll();
        for (int i = 0; i < dataBase.size(); i++) {
            Dataset rowData = dataBase.get(i);

            double[] instanceValue = new double[dataRaw.numAttributes()];
            instanceValue[0] = dataRaw.attribute(0).addStringValue(rowData.getLCore());
            instanceValue[1] = dataRaw.attribute(1).addStringValue(rowData.getLSurf());
            instanceValue[2] = dataRaw.attribute(2).addStringValue(rowData.getLO2());
            instanceValue[3] = dataRaw.attribute(3).addStringValue(rowData.getLBp());
            instanceValue[4] = dataRaw.attribute(4).addStringValue(rowData.getSurfStbl());
            instanceValue[5] = dataRaw.attribute(5).addStringValue(rowData.getCoreStbl());
            instanceValue[6] = dataRaw.attribute(6).addStringValue(rowData.getBpStbl());
            instanceValue[7] = dataRaw.attribute(7).addStringValue(rowData.getComfort());
            instanceValue[8] = GetIntValueFromStringDecision(rowData.getDecision());
            dataRaw.add(new DenseInstance(1.0, instanceValue));
        }

        String[] options = new String[2];
        options[0] = "-R";
        options[1] = "first-last";
System.err.println(dataRaw);
        try {
            StringToNominal stringNominal = new StringToNominal();
            stringNominal.setOptions(options);
            stringNominal.setInputFormat(dataRaw);
            dataRaw = Filter.useFilter(dataRaw, stringNominal);

            System.err.println(dataRaw);
            J48 J48tree = new J48();
            J48tree.buildClassifier(dataRaw);
            
            
                                   Evaluation evaluation = new Evaluation(dataRaw);
                        evaluation.evaluateModel(J48tree, dataRaw);

                        System.out.println(evaluation.toSummaryString());
            
            
            
            
            
            
            
            SerializationHelper.write("j48.model", J48tree);

            System.err.print(J48tree.toString());
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

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
}
