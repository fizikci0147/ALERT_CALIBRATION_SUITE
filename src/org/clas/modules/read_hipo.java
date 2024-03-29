package org.clas.modules;
import org.jlab.coda.jevio.*;

import javax.swing.JFrame;

//import org.jlab.clas12.detector.*;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.math.F1D;
import org.jlab.io.hipo.HipoDataBank;
import org.jlab.io.hipo.HipoDataEvent;
import org.jlab.io.hipo.HipoDataSource;

import java.io.IOException;

import org.jlab.groot.graphics.EmbeddedCanvasTabbed;

import org.jlab.groot.group.DataGroup;

public class read_hipo {

    public static double getThreshold2(H1F h) {
        // find the bin at which the integral corresponds to 1% of the full integral to max
        // this is the max range to obtain a threshold from a flat line fit
        double integral = 0;
        double partintegral = 0;

        org.jlab.groot.data.GraphErrors gr = new GraphErrors();
        for (int ix =0; ix< h.getMaximumBin(); ix++) {
            integral+= h.getBinContent(ix);
        }
        double x = 0;
        for (int ix =0; ix< h.getMaximumBin(); ix++) {
            x = h.getDataX(ix);
            double y = h.getBinContent(ix);
            double err = h.getBinError(ix);
            if(err<1) {
                err = 1.4142;
            }
            // fill graph
            gr.addPoint(x, y, 0, err);
            partintegral += h.getBinContent(ix);

            if(partintegral>0.5*integral)
                break;
        }
        // fit the graph
        F1D f0 = new F1D("f0","[p0]", h.getDataX(0), x);
        f0.setParameter(0, 0);
        DataFitter.fit(f0, gr, "Q");
        return f0.getParameter(0);
    }
    public static void main(String args[]) throws EvioException, IOException {

        EmbeddedCanvasTabbed canvasTabbed = null;
        canvasTabbed =new EmbeddedCanvasTabbed("tm");
        DataGroup dg = new DataGroup(1, 1);
        String input = "/Users/tuc72417/Work/clas_work/alert/gemc/mywork/out.hipo";
        H1F hadc = new H1F("hadc", "hadc", 100, 0, 200.0);
        hadc.setTitleX("Time (ns)");
        hadc.setTitleY("Counts");
        dg.addDataSet(hadc,0);
        HipoDataSource reader = new HipoDataSource();
        reader.open(input);
        int nevents = reader.getSize();
        int counter = 0;
        for(int i = 0; i < nevents; i++){
            HipoDataEvent event = (HipoDataEvent) reader.gotoEvent(i);
            //event.show();
            if(event.hasBank("ALRTDC::adc")==true){
                HipoDataBank  bank = (HipoDataBank) event.getBank("ALRTDC::adc");
                bank.show();
                int rows = bank.rows();
                for (int loop = 0; loop < rows; loop++) {
                    float time =bank.getFloat("time",loop);
                    int sec = bank.getInt("sector",loop);
                    int layer = bank.getInt("sector",loop);
                    int component =bank.getInt("component",loop);

                    //System.out.println("comp is "+ component);
                    if(component==15) {
                        hadc.fill(time);
                    }
                }
                }
        }

        F1D FitFunc = new F1D("FitFunc", "(1./(1.+exp([p0]-[p1]*x))*exp([p2]-[p3]*x))+[p4]",
                10,140);

        FitFunc.setParameter(0, 0.2);
        FitFunc.setParameter(1, -0.0);
        FitFunc.setParameter(2, 0.01);
        FitFunc.setParameter(3, 0.0);
        FitFunc.setParameter(4, 0);
        DataFitter.fit(FitFunc,hadc,"Q");

        canvasTabbed.getCanvas("tm").divide(1,1);
        canvasTabbed.getCanvas("tm").cd(0);
        canvasTabbed.getCanvas("tm").draw(dg.getH1F("hadc"));
        JFrame frame = new JFrame("ALERT");
        frame.setSize(1200, 800);
        frame.add(canvasTabbed);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        double ant =getThreshold2(hadc);

        double T0n =FitFunc.getParameter(0)/FitFunc.getParameter(1);

        System.out.println("Ton: "+ T0n);
    }
    }

