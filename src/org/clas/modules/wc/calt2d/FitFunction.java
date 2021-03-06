/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.clas.modules.wc.calt2d;

import org.clas.detector.clas12calibration.dc.analysis.Coordinate;
import org.freehep.math.minuit.FCNBase;
import org.jlab.groot.data.GraphErrors;
import org.jlab.rec.dc.Constants;

import java.util.Map;

/**
 *

 */
public class FitFunction implements FCNBase{

    public double _beta = 1.0;
    
    private Map<Coordinate, GraphErrors> _tvstrkdocasProf;
    private int i;
    
    public FitFunction() {
        
    }
    public FitFunction(int i, Map<Coordinate, GraphErrors> tvstrkdocasProf) {
        this.i = i;
        _tvstrkdocasProf = tvstrkdocasProf;
    }

    public double eval(double x, double ralpha, double B, double[] par) {
        
        double v_0 = par[0];
        double vm = par[1];
        double tmax = par[3];
        double distbeta = par[4]; 
        double delBf = par[5]; 
        double Bb1 = par[6]; 
        double Bb2 = par[7]; 
        double Bb3 = par[8]; 
        double Bb4 = par[9]; 
        double R = par[2];
        double dmax = par[10];
        double deltatime_beta = (Math.sqrt(x * x + (distbeta * _beta * _beta) 
                * (distbeta* _beta * _beta)) - x) / Constants.V0AVERAGED;

        double calcTime = this.polyFcnMac(x,  ralpha,  B,  v_0,  vm,  R, 
            tmax,  dmax,  delBf,  Bb1,  Bb2,  Bb3,  Bb4, i+1) + deltatime_beta ;
        
        return calcTime;
    }
    public double polyFcnMac(double x, double alpha, double bfield, double v_0, double vm, double R, 
            double tmax, double dmax, double delBf, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        
        if(x>dmax)
            x=dmax;
        double time = 0;
        // alpha correction 
        double cos30minusalpha=Math.cos(Math.toRadians(30.-alpha));
        double dmaxalpha = dmax*cos30minusalpha;
        double xhatalpha = x/dmaxalpha;
        //   rcapital is an intermediate parameter
        double rcapital = R*dmax;
        //   delt is another intermediate parameter
        double delt=tmax-dmax/v_0;
        double delv=1./vm-1./v_0;
        //   now calculate the primary parameters a, b, c, d
        
        double c = ((3.*delv)/(R*dmax)+(12*R*R*delt)/(2.*(1-2*R)*
            (dmax*dmax)));
        c = c /(4.-(1.-6.*R*R)/(1.-2.*R));
        double b = delv/(rcapital*rcapital) - 4.*c/(3.*rcapital);
        double d = 1/v_0;
        double a = (tmax -  b*dmaxalpha*dmaxalpha*dmaxalpha - 
                c*dmaxalpha*dmaxalpha - d*dmaxalpha)/(dmaxalpha*dmaxalpha*dmaxalpha*dmaxalpha) ;       
        time = a*x*x*x*x + b*x*x*x + c*x*x + d*x ;
        

        return time;
    }
    @Override
    public double valueOf(double[] par) {
        double chisq = 0;
        double delta = 0;
        for (int j = 0; j < T2DCalib.alphaBins; j++) {
            if(this.i>1 && this.i<4) {
                for(int k = 0; k < T2DCalib.BBins; k++) {
                    if(_tvstrkdocasProf.get(new Coordinate(this.i, j, k)).getVectorX().size()>0){ 
                        //local angle correction
                        double theta0 = Math.toDegrees(Math.acos(1-0.02*T2DCalib.BfieldValuesUpd[i-2][j][k]));
                        double alpha = T2DCalib.AlphaValues[j];
                        // correct alpha with theta0, the angle corresponding to the isochrone lines twist due to the electric field
                        alpha-=(double)T2DCalib.polarity*theta0;
                        //reduce the corrected angle
                        double ralpha = (double) this.getReducedAngle(alpha);
                        GraphErrors gr = _tvstrkdocasProf.get(new Coordinate(this.i, j, k));
                            
                        for (int ix =0; ix< gr.getDataSize(0); ix++) {
                            double x = gr.getDataX(ix);
                            double time = gr.getDataY(ix);
                            double err = gr.getDataEY(ix);
                            if(err>0) {
                                double calcTime = this.eval(x, ralpha, T2DCalib.BfieldValuesUpd[i-2][j][k], par);
                                delta = (time - calcTime) / err; 
                                chisq += delta * delta;
                            }
                        }
                    }
                }
            } else {
                if(_tvstrkdocasProf.get(new Coordinate(this.i, j, T2DCalib.BBins)).getVectorX().size()>0){ 
                    //local angle correction
                    double alpha = T2DCalib.AlphaValues[j];
                    //reduce the corrected angle
                    double ralpha = (double) this.getReducedAngle(alpha);
                    GraphErrors gr = _tvstrkdocasProf.get(new Coordinate(this.i, j, T2DCalib.BBins));

                    for (int ix =0; ix< gr.getDataSize(0); ix++) {
                        double x = gr.getDataX(ix);
                        double time = gr.getDataY(ix);
                        double err = gr.getDataEY(ix);
                        if(err>0) {
                            double calcTime = this.eval(x, ralpha, 0.0, par);
                            delta = (time - calcTime) / err; 
                            chisq += delta * delta;
                        }
                    }
                }
            }
        }
        return chisq;
        
    }
    
    public double getReducedAngle(double alpha) {
        double ralpha = 0;

        ralpha = Math.abs(Math.toRadians(alpha));

        while (ralpha > Math.PI / 3.) {
            ralpha -= Math.PI / 3.;
        }
        if (ralpha > Math.PI / 6.) {
            ralpha = Math.PI / 3. - ralpha;
        }

        return Math.toDegrees(ralpha);
    }  
    
    
}
