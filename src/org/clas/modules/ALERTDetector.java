package org.clas.modules;


// this

import org.jlab.detector.calib.tasks.CalibrationEngine;
import org.jlab.detector.calib.utils.CalibrationConstants;
import org.jlab.detector.calib.utils.ConstantsManager;
import org.jlab.groot.group.DataGroup;
import org.jlab.utils.groups.IndexedList;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ALERTDetector {
    public double Test_ADC = 0.0;
    public double ADC_Front = 0.0;
    public double ADC_Back = 0.0;
    public double ADC_Top = 0.0;
    public double Test_Time = 0.0;
    public double Test_Energy = 0.0;
    public double XPOS = 0.0;
    public double YPOS = 0.0;
    public double ZPOS = 0.0;
    public double timeFront = 0.0;
    public double timeBack =0.0;
    public double timeTop=0.0;
    public double YAmp = 0.0;
    public int sector = 0;
    public int super_layer = 0;
    public int layer = 0;
    public double RefTime=0.0;


    public void setGeometry(int sector,int super_lay,int layer){
        this.sector = sector;
        this.super_layer = super_lay;
        this.layer = layer;
    }

    public void set_ADC(double ADC_F,double ADC_B) {
        ADC_Front = ADC_F;
        ADC_Back = ADC_B;
    }

    public void set_TDC(double TDC_F,double TDC_B) {
        timeFront = TDC_F;
        timeBack = TDC_B;
    }

    public void setPOS(double XPOS,double YPOS,double ZPOS) {
        this.XPOS = XPOS;
        this.YPOS = YPOS;
        this.ZPOS = ZPOS;
    }

    public double Y_Value(){
        double y = 0.0;

        y = YPOS;

        return y;
    }


    public double ReferenceTime(){
        // ATOF uses RF_Time along with starttime--- Need to update
        return RefTime;
    }


    public double TimeWalkCorL(){
        double tdcTimeF = timeFront  - 0 ;
        return tdcTimeF;
    }
    public double TimeWalkCorR(){
        double tdcTimeB = timeBack - 0  ; //Reference Time?
        return tdcTimeB;
    }

    public double HalfTime(){
        double timeL = TimeWalkCorL();
        double timeR = TimeWalkCorR();
        return ((timeL - timeR)/1);
    }

    public double VeffTestMethod(double xpos, double ypos,double test_ADC){
        YAmp = (ypos - xpos)/2;
        return YAmp;
    }


    public double veffTime(){
        double timeL = TimeWalkCorL();
        double timeR = TimeWalkCorR();
        return ((timeL - timeR )/2.0);
    }

}
