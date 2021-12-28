package org.clas.modules.scint;

import org.clas.gui.ALERTCalibGUI;
import org.clas.modules.ALERTCalConstants;
import org.clas.modules.ALERTCalibrationEngine;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H2F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.fitter.ParallelSliceFitter;
import org.jlab.groot.group.DataGroup;
import org.jlab.groot.math.F1D;
import org.jlab.utils.groups.IndexedList;

public class ALERTVeffCalibration {

    private int counter=0;



    private String fitMode = "RN"; // is fitMode a String -- ParallelSliceFitter line 48?
    private int fitMinEvents = 100;
    private int backgroundSF = -1; // ParallelSliceFitter lines [61-66]
    private float fitSliceMaxError = -1;
    private GraphErrors fixGraph(GraphErrors graphIn, String graphName) {
        System.out.println("seems to make it here!");
        int n = graphIn.getDataSize(0);
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (graphIn.getDataEY(i) < fitSliceMaxError) {
                m++;
            }
        }
        double[] x = new double[m];
        double[] xerr = new double[m];
        double[] y = new double[m];
        double[] yerr = new double[m];
        int j = 0;

        for (int i = 0; i < n; i++) {
            if (graphIn.getDataEY(i) < fitSliceMaxError) {
                x[j] = graphIn.getDataX(i);
                xerr[j] = graphIn.getDataEX(i);
                y[j] = graphIn.getDataY(i);
                yerr[j] = graphIn.getDataEY(i);
                j++;
            }
        }

        GraphErrors fixGraph = new GraphErrors(graphName, x, y, xerr, yerr);
        fixGraph.setName(graphName);

        return fixGraph;
    }

    public void calcVEFF(IndexedList<DataGroup> ds) {
        System.out.println("calcVEFF");
        ALERTCalibrationEngine Calib_1 = new ALERTCalibrationEngine();
        //F1D f2 = new F1D("f2", "[a]+[b]*x", -20.0, 50.0); // F1D(String name,String expression,double min, double max)
        F1D f2 = new F1D("f2", "[a]+[b]*x", -40.0, 50.0);
        H2F VeffHisto[][][] = new H2F[16][3][5];

        for (int sector = 0; sector < 16; sector++) {
            for (int slayer = 0; slayer < 2; slayer++) {
                for (int comp = 0; comp <= 4 - 1; comp++) {
                    System.out.println("sector"+sector);
                    ALERTCalibrationEngine cons = new ALERTCalibrationEngine();
                    //System.out.println("Sector" + sector);
                    VeffHisto[sector][slayer][comp] = ds.getItem(sector, slayer, comp).getH2F("Veff");
                    VeffHisto[sector][slayer][comp].setTitleX("Hit Position");
                    VeffHisto[sector][slayer][comp].setTitleY("time");
                    ParallelSliceFitter psf = new ParallelSliceFitter(VeffHisto[sector][slayer][comp]);


                    GraphErrors gr = new GraphErrors("gr");
                    psf.setFitMode(fitMode);
                    psf.setMinEvents(fitMinEvents);
                    psf.setBackgroundOrder(backgroundSF); //setBackgroundOrder(int mode) {return this.mode = mode +1} ----  so this would be -1+1 = 0 ---->  P0_BG (Flat background)
                    psf.setNthreads(1);
                    psf.fitSlicesX();

                    gr.copy(fixGraph(psf.getMeanSlices(), "gr")); //fixGraph(GraphErrors graphIN, String graphName)
                    f2.setParameter(0, 0.0);
                    f2.setParameter(1, 1.0 / 16.0);

                    try {
                        //Added Option "V"
                        //DataFitter.fit(f2, gr, "V");
                        DataFitter.fit(f2,gr,"RQ");
                    } catch (Exception e) {
                        System.out.println("Fit error ");
                        e.printStackTrace();
                    }
                    double gradient = f2.getParameter(1);
                    double gradient_error = f2.parameter(1).error();
                    //System.out.println("Error:"+gradient_error);
                    ALERTCalConstants CalibConstant = new ALERTCalConstants();
                    String CalibName = String.format("Veff_%d", sector);
                    //ALERTCalibrationEngine Calib_1 = new ALERTCalibrationEngine();
                    Calib_1.calib.setDoubleValue(gradient,"Veff",sector,slayer,comp);
                    Calib_1.calib.setDoubleValue(gradient_error,"Veff_err",sector,slayer,comp);
                    //CalibConstant.NewCalConstants(CalibName, gradient);
                }
            }
        }


        Calib_1.calib.fireTableDataChanged();
        ALERTCalConstants Cal_Passer = new ALERTCalConstants();
        //Cal_Passer.Calib_Passer();
        ALERTCalibGUI DrawHisto = new ALERTCalibGUI();
        DrawHisto.Draw(VeffHisto[0][1][1], f2);
    }


}
