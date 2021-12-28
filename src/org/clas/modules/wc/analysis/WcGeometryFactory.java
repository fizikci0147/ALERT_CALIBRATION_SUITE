package org.clas.modules.wc.analysis;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import org.jlab.detector.base.DetectorType;
import org.jlab.detector.calib.utils.DatabaseConstantProvider;
import org.clas.modules.utils.ALERTDatabaseConstantProvider;
import org.jlab.geom.base.ConstantProvider;
import org.jlab.geom.base.Detector;
import org.jlab.geom.detector.cnd.CNDFactory;
import org.jlab.geom.detector.dc.DCFactory;
import org.jlab.geom.detector.ec.ECFactory;
import org.jlab.geom.detector.ftof.FTOFFactory;

public class WcGeometryFactory {
    public static int SYSTEM_LOCAL = 1;
    public static int SYSTEM_TILTED = 2;
    public static int SYSTEM_CLAS = 3;

    public WcGeometryFactory() {
    }

    public static ConstantProvider getConstants(DetectorType type, int run, String variation) {
        DatabaseConstantProvider provider = new DatabaseConstantProvider(run, variation);
        if (type == DetectorType.DC) {
            provider.loadTable("/test/test_vars/test_dc_aler_stat0");
         /*   provider.loadTable("/geometry/dc/region");
            provider.loadTable("/geometry/dc/superlayer");
            provider.loadTable("/geometry/dc/layer");
            provider.loadTable("/geometry/dc/alignment");
            provider.loadTable("/geometry/dc/ministagger");
            provider.loadTable("/geometry/dc/endplatesbow");*/
        }


        provider.disconnect();
        return provider;
    }

    public static ConstantProvider getConstants(DetectorType type) { return getConstants(type, 10, "default"); }

    public static Detector getDetector(DetectorType type) {
        return getDetector(type, 10, "default");
    }

    public static Detector getDetector(DetectorType type, int run, String variation) {
        ConstantProvider provider = getConstants(type, run, variation);
        if (type == DetectorType.DC) {
            DCFactory factory = new DCFactory();
            Detector dc = factory.createDetectorCLAS(provider);
            return dc;
        } else {
            System.out.println("[GeometryFactory] --->  detector construction for " + type.getName() + "  is not implemented");
            return null;
        }
    }
}

