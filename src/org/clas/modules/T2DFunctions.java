package org.clas.modules;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


public class T2DFunctions {
    public T2DFunctions() {
    }

    public static synchronized double ExpoFcn(double x, double alpha, double bfield, double v_0, double delta_nm, double R, double tmax, double dmax, double delBf, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        if (x > dmax) {
            x = dmax;
        }

        double cos30minusalpha = Math.cos(Math.toRadians(30.0D - alpha));
        double xhat = x / dmax;
        double dmaxalpha = dmax * cos30minusalpha;
        double xhatalpha = x / dmaxalpha;
        double n = (1.0D + (delta_nm - 1.0D) * Math.pow(R, delta_nm)) / (1.0D - Math.pow(R, delta_nm));
        double m = n + delta_nm;
        double b = (tmax - dmax / v_0) / (1.0D - m / n);
        double a = -b * m / n;
        double balpha = (tmax - dmaxalpha / v_0 - a * Math.pow(cos30minusalpha, n)) / Math.pow(cos30minusalpha, m);
        double time = x / v_0 + a * Math.pow(xhat, n) + balpha * Math.pow(xhat, m);
        time += CorrectForB(delBf, bfield, tmax, xhatalpha, Bb1, Bb2, Bb3, Bb4, superlayer);
        return time;
    }

    public static synchronized double polyFcnP5(double x, double alpha, double bfield, double v_0, double vm, double R, double tmax, double dmax, double delBf, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        if (x > dmax) {
            x = dmax;
        }

        double time = 0.0D;
        double cos30minusalpha = Math.cos(Math.toRadians(30.0D - alpha));
        double dmaxalpha = dmax * cos30minusalpha;
        double xhatalpha = x / dmaxalpha;
        double denom = (cos30minusalpha - R) * (3.0D * cos30minusalpha * cos30minusalpha * cos30minusalpha + 19.0D * cos30minusalpha * cos30minusalpha * R - 50.0D * cos30minusalpha * R * R + 10.0D * R * R * R) * v_0 * vm;
        double denom_a = cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * dmax * dmax * dmax * dmax * R * R * denom;
        double denom_b = cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * dmax * dmax * dmax * R * R * denom;
        double denom_c = cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * dmax * dmax * R * R * denom;
        double denom_d = cos30minusalpha * cos30minusalpha * dmax * dmax * denom;
        double a = (-18.0D * Math.pow(cos30minusalpha, 4.0D) * Math.pow(dmax, 3.0D) * Math.pow(R, 3.0D) * v_0 * vm + 36.0D * Math.pow(R, 4.0D) * tmax * v_0 * vm + 12.0D * Math.pow(cos30minusalpha, 2.0D) * Math.pow(R, 2.0D) * (2.0D * dmax * R - tmax * v_0) * vm - 12.0D * cos30minusalpha * Math.pow(R, 3.0D) * (3.0D * dmax * R + 2.0D * tmax * v_0) * vm + 6.0D * Math.pow(cos30minusalpha, 3.0D) * dmax * Math.pow(R, 2.0D) * (v_0 + vm + 2.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * v_0 * vm) + Math.pow(cos30minusalpha, 5.0D) * dmax * (v_0 - vm + 6.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * v_0 * vm)) / denom_a;
        double b = -(15.0D * Math.pow(cos30minusalpha, 4.0D) * dmax * Math.pow(R, 2.0D) * vm + 45.0D * Math.pow(cos30minusalpha, 2.0D) * dmax * Math.pow(R, 4.0D) * vm - 27.0D * Math.pow(cos30minusalpha, 5.0D) * Math.pow(dmax, 3.0D) * Math.pow(R, 3.0D) * v_0 * vm + 60.0D * Math.pow(R, 5.0D) * tmax * v_0 * vm - 15.0D * cos30minusalpha * Math.pow(R, 4.0D) * (4.0D * dmax * R + 3.0D * tmax * v_0) * vm + 2.0D * Math.pow(cos30minusalpha, 6.0D) * dmax * (v_0 - vm + 6.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * v_0 * vm) + 5.0D * Math.pow(cos30minusalpha, 3.0D) * Math.pow(R, 2.0D) * (2.0D * dmax * R * (v_0 - vm) + 3.0D * Math.pow(dmax, 3.0D) * Math.pow(R, 3.0D) * v_0 * vm - 3.0D * tmax * v_0 * vm)) / denom_b;
        double c = (-20.0D * cos30minusalpha * dmax * Math.pow(R, 6.0D) * vm - 60.0D * Math.pow(cos30minusalpha, 2.0D) * Math.pow(R, 4.0D) * tmax * v_0 * vm + 20.0D * Math.pow(R, 6.0D) * tmax * v_0 * vm + 20.0D * Math.pow(cos30minusalpha, 3.0D) * Math.pow(R, 3.0D) * (3.0D * dmax * R + 2.0D * tmax * v_0) * vm - 18.0D * Math.pow(cos30minusalpha, 5.0D) * dmax * Math.pow(R, 2.0D) * (v_0 - vm + 2.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * v_0 * vm) + Math.pow(cos30minusalpha, 7.0D) * dmax * (v_0 - vm + 6.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * v_0 * vm) + 10.0D * Math.pow(cos30minusalpha, 4.0D) * dmax * Math.pow(R, 3.0D) * (-6.0D * vm + v_0 * (2.0D + 3.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * vm))) / denom_c;
        double d = (90.0D * Math.pow(cos30minusalpha, 3.0D) * dmax * Math.pow(R, 2.0D) * vm - 9.0D * Math.pow(cos30minusalpha, 6.0D) * Math.pow(dmax, 3.0D) * R * v_0 * vm - 30.0D * Math.pow(R, 4.0D) * tmax * v_0 * vm - 30.0D * Math.pow(cos30minusalpha, 2.0D) * Math.pow(R, 2.0D) * (4.0D * dmax * R + 3.0D * tmax * v_0) * vm + 30.0D * cos30minusalpha * Math.pow(R, 3.0D) * (dmax * R + 4.0D * tmax * v_0) * vm + 12.0D * Math.pow(cos30minusalpha, 5.0D) * dmax * (v_0 - vm + 2.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * v_0 * vm) - 5.0D * Math.pow(cos30minusalpha, 4.0D) * dmax * R * (-2.0D * vm + v_0 * (2.0D + 3.0D * Math.pow(dmax, 2.0D) * Math.pow(R, 2.0D) * vm))) / denom_d;
        double e = 1.0D / v_0;
        time = a * x * x * x * x * x + b * x * x * x * x + c * x * x * x + d * x * x + e * x;
        time += CorrectForB(delBf, bfield, tmax, xhatalpha, Bb1, Bb2, Bb3, Bb4, superlayer);
        return time;
    }

    public static synchronized double polyFcnDmaxV0Constraint(double x, double alpha, double bfield, double v_0, double vm, double R, double tmax, double dmax, double delBf, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        if (x > dmax) {
            x = dmax;
        }

        double time = 0.0D;
        double cos30minusalpha = Math.cos(Math.toRadians(30.0D - alpha));
        double dmaxalpha = dmax * cos30minusalpha;
        double xhatalpha = x / dmaxalpha;
        double denom = (cos30minusalpha - R) * (cos30minusalpha + 3.0D * R) * v_0 * vm;
        double denom_a = 4.0D * dmax * dmax * dmax * R * R * denom;
        double denom_b = 3.0D * dmax * dmax * R * R * denom;
        double denom_c = 2.0D * dmax * denom;
        double denom_e = -12.0D * R * R * denom;
        double e = (-18.0D * cos30minusalpha * cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * dmax * dmax * R * R * R * v_0 * vm + 36.0D * R * R * R * R * tmax * v_0 * vm + 12.0D * cos30minusalpha * cos30minusalpha * R * R * (2.0D * dmax * R - tmax * v_0) * vm - 12.0D * cos30minusalpha * R * R * R * (3.0D * dmax * R + 2.0D * tmax * v_0) * vm + 6.0D * cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * R * R * (v_0 + vm + 2.0D * dmax * dmax * R * R * v_0 * vm) + cos30minusalpha * cos30minusalpha * cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * (v_0 - vm + 6.0D * dmax * dmax * R * R * v_0 * vm)) / denom_e;
        double a = (6.0D * dmax * dmax * R * R * R * v_0 * vm + cos30minusalpha * (vm - v_0 * (1.0D + 6.0D * dmax * dmax * R * R * vm))) / denom_a;
        double b = (-3.0D * R * R * (v_0 - vm + 2.0D * dmax * dmax * R * R * v_0 * vm) + cos30minusalpha * cos30minusalpha * (v_0 - vm + 6.0D * dmax * dmax * R * R * v_0 * vm)) / denom_b;
        double c = -(3.0D * cos30minusalpha * (vm + v_0 * (-1.0D + 2.0D * cos30minusalpha * dmax * dmax * R * vm - 2.0D * dmax * dmax * R * R * vm))) / denom_c;
        double d = 1.0D / v_0;
        time = a * x * x * x * x + b * x * x * x + c * x * x + d * x + e;
        time += CorrectForB(delBf, bfield, tmax, xhatalpha, Bb1, Bb2, Bb3, Bb4, superlayer);
        return time;
    }

    public static synchronized double polyFcnNoDmaxV0Constraint(double x, double alpha, double bfield, double v_0, double vm, double R, double tmax, double dmax, double delBf, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        if (x > dmax) {
            x = dmax;
        }

        double time = 0.0D;
        double cos30minusalpha = Math.cos(Math.toRadians(30.0D - alpha));
        double dmaxalpha = dmax * cos30minusalpha;
        double xhatalpha = x / dmaxalpha;
        double denom = cos30minusalpha * cos30minusalpha * dmax * dmax * (3.0D * cos30minusalpha * cos30minusalpha - 8.0D * cos30minusalpha * R + 6.0D * R * R) * v_0 * vm;
        double a = (cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * (v_0 - vm) - 3.0D * cos30minusalpha * dmax * R * R * vm + 3.0D * R * R * tmax * v_0 * vm + 3.0D * cos30minusalpha * cos30minusalpha * dmax * R * (-v_0 + vm)) / (dmax * dmax * R * R * denom);
        double b = (6.0D * cos30minusalpha * cos30minusalpha * dmax * R * R * (v_0 - vm) + 8.0D * cos30minusalpha * dmax * R * R * R * vm - 8.0D * R * R * R * tmax * v_0 * vm + cos30minusalpha * cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * (-v_0 + vm)) / (dmax * R * R * denom);
        double c = (3.0D * cos30minusalpha * cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * (v_0 - vm) - 6.0D * cos30minusalpha * dmax * R * R * R * vm + 6.0D * R * R * R * tmax * v_0 * vm + 6.0D * cos30minusalpha * cos30minusalpha * cos30minusalpha * dmax * R * (-v_0 + vm)) / (R * denom);
        double d = 1.0D / v_0;
        time = a * x * x * x * x + b * x * x * x + c * x * x + d * x;
        time += CorrectForB(delBf, bfield, tmax, xhatalpha, Bb1, Bb2, Bb3, Bb4, superlayer);
        return time;
    }

    public static synchronized double polyFcnMac(double x, double alpha, double bfield, double v_0, double vm, double R, double tmax, double dmax, double delBf, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        if (x > dmax) {
            x = dmax;
        }

        double time = 0.0D;
        double cos30minusalpha = Math.cos(Math.toRadians(30.0D - alpha));
        double dmaxalpha = dmax * cos30minusalpha;
        double xhatalpha = x / dmaxalpha;
        double rcapital = R * dmax;
        double delt = tmax - dmax / v_0;
        double delv = 1.0D / vm - 1.0D / v_0;
        double c = 3.0D * delv / (R * dmax) + 12.0D * R * R * delt / (2.0D * (1.0D - 2.0D * R) * dmax * dmax);
        c /= 4.0D - (1.0D - 6.0D * R * R) / (1.0D - 2.0D * R);
        double b = delv / (rcapital * rcapital) - 4.0D * c / (3.0D * rcapital);
        double d = 1.0D / v_0;
        double a = (tmax - b * dmaxalpha * dmaxalpha * dmaxalpha - c * dmaxalpha * dmaxalpha - d * dmaxalpha) / (dmaxalpha * dmaxalpha * dmaxalpha * dmaxalpha);
        time = a * x * x * x * x + b * x * x * x + c * x * x + d * x;
        time += CorrectForB(delBf, bfield, tmax, xhatalpha, Bb1, Bb2, Bb3, Bb4, superlayer);
        return time;
    }

    public static synchronized double polyFcnSpline(double x, double alpha, double bfield, double v_0, double vm, double R, double tmax, double dmax, double delBf, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        if (x > dmax) {
            x = dmax;
        }

        double time = 0.0D;
        double cosA = Math.cos(Math.toRadians(30.0D - alpha));
        double dmaxalpha = dmax * cosA;
        double xhatalpha = x / dmaxalpha;
        double d = 0.0D;
        double c = 0.0D;
        double a = 0.0D;
        double b = 0.0D;
        double k1 = 0.2D;
        double k2 = 0.6D;
        double a1 = (cosA * cosA * dmax * dmax * (k2 - dmax * R) * (v_0 - vm) + 3.0D * dmax * dmax * R * R * tmax * v_0 * vm + k1 * k1 * k1 * (-v_0 + vm) + k1 * k1 * (k2 * v_0 - k2 * vm - 3.0D * tmax * v_0 * vm) + cosA * dmax * (k1 * k1 * (2.0D * v_0 + vm) + dmax * R * (-2.0D * k2 * v_0 + 2.0D * k2 * vm - 3.0D * dmax * R * vm))) / (3.0D * dmax * dmax * k1 * k1 * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm);
        double b1 = (cosA * cosA * dmax * (-k2 + dmax * R) * (v_0 - vm) + R * (k1 * k1 * (v_0 - vm) + 3.0D * k1 * tmax * v_0 * vm - 3.0D * dmax * R * tmax * v_0 * vm + k1 * k2 * (-v_0 + vm)) + cosA * dmax * R * (2.0D * k2 * (v_0 - vm) + 3.0D * dmax * R * vm - k1 * (2.0D * v_0 + vm))) / (dmax * k1 * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm);
        double c1 = 1.0D / v_0;
        double a2 = (k2 * v_0 - k2 * vm - 3.0D * tmax * v_0 * vm + k1 * (-v_0 + vm) + cosA * dmax * (2.0D * v_0 + vm)) / (3.0D * dmax * dmax * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm);
        double b2 = -(R * (k2 * v_0 - k2 * vm - 3.0D * tmax * v_0 * vm + k1 * (-v_0 + vm) + cosA * dmax * (2.0D * v_0 + vm)) / (dmax * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm));
        double c2 = (cosA * cosA * (k2 - dmax * R) * v_0 + R * R * (-k1 + k2 + 3.0D * tmax * v_0) * vm - cosA * R * (2.0D * k2 * v_0 + dmax * R * vm)) / (((-k1 + k2) * R * R + cosA * cosA * (k2 - dmax * R) + 2.0D * cosA * R * (-k2 + dmax * R)) * v_0 * vm);
        double d2 = -(k1 * (cosA * cosA * (k2 - dmax * R) * (v_0 - vm) + 3.0D * R * R * tmax * v_0 * vm + cosA * R * (-2.0D * k2 * v_0 + 2.0D * k2 * vm - 3.0D * dmax * R * vm)) / (3.0D * ((-k1 + k2) * R * R + cosA * cosA * (k2 - dmax * R) + 2.0D * cosA * R * (-k2 + dmax * R)) * v_0 * vm));
        double a3 = (k2 * k2 * k2 * v_0 - k2 * k2 * k2 * vm - 3.0D * k2 * k2 * tmax * v_0 * vm + 3.0D * dmax * dmax * R * R * tmax * v_0 * vm + k1 * k2 * k2 * (-v_0 + vm) + 3.0D * cosA * cosA * dmax * dmax * (-k2 + dmax * R) * (v_0 + vm) + cosA * dmax * (k2 - dmax * R) * (2.0D * k1 * (v_0 - vm) + 3.0D * (k2 + dmax * R + 2.0D * tmax * v_0) * vm)) / (3.0D * dmax * dmax * Math.pow(-cosA * dmax + k2, 2.0D) * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm);
        double b3 = (3.0D * cosA * dmax * k2 * R * (-k2 + dmax * R) * vm + cosA * cosA * cosA * dmax * dmax * (k2 - dmax * R) * (2.0D * v_0 + vm) + cosA * cosA * dmax * (-k2 + dmax * R) * (k1 * (v_0 - vm) + 3.0D * tmax * v_0 * vm) + k2 * R * (k1 * k2 * (v_0 - vm) + 3.0D * k2 * tmax * v_0 * vm - 3.0D * dmax * R * tmax * v_0 * vm + k2 * k2 * (-v_0 + vm))) / (dmax * Math.pow(-cosA * dmax + k2, 2.0D) * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm);
        double c3 = (Math.pow(cosA, 4.0D) * dmax * dmax * (-k2 + dmax * R) * v_0 + (k1 - k2) * k2 * k2 * R * R * vm + cosA * cosA * cosA * dmax * (-k2 * k2 + dmax * dmax * R * R) * vm + cosA * cosA * (-k2 * k2 * k2 * v_0 + k1 * k2 * k2 * (v_0 - vm) + dmax * dmax * k1 * R * R * vm - 3.0D * dmax * dmax * k2 * R * R * vm - 3.0D * dmax * dmax * R * R * tmax * v_0 * vm + 3.0D * k2 * k2 * (dmax * R + tmax * v_0) * vm) + 2.0D * cosA * k2 * R * (-k1 * (k2 * (v_0 - vm) + dmax * R * vm) + v_0 * (k2 * k2 - 3.0D * k2 * tmax * vm + 3.0D * dmax * R * tmax * vm))) / (Math.pow(-cosA * dmax + k2, 2.0D) * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm);
        double d3 = (Math.pow(cosA, 4.0D) * dmax * dmax * k1 * (k2 - dmax * R) * (v_0 - vm) + 3.0D * (k1 - k2) * k2 * k2 * R * R * tmax * v_0 * vm + 3.0D * cosA * k2 * R * (2.0D * k2 * k2 * tmax * v_0 + dmax * R * (k2 * k2 - k1 * (k2 + 2.0D * tmax * v_0))) * vm + cosA * cosA * cosA * dmax * (-2.0D * k1 * k2 * k2 * (v_0 - vm) - 3.0D * dmax * dmax * k1 * R * R * vm + k2 * k2 * k2 * (2.0D * v_0 + vm)) + 3.0D * cosA * cosA * (-k2 * k2 * k2 * tmax * v_0 * vm + dmax * dmax * k1 * R * R * (2.0D * k2 + tmax * v_0) * vm - dmax * k2 * k2 * R * (k1 * (-v_0 + vm) + k2 * (v_0 + vm)))) / (3.0D * (-cosA * dmax + k2) * (-cosA * dmax + k2) * ((k1 - k2) * R * R + 2.0D * cosA * R * (k2 - dmax * R) + cosA * cosA * (-k2 + dmax * R)) * v_0 * vm);
        if (x >= 0.0D && x < k1) {
            d = 0.0D;
            a = a1;
            b = b1;
            c = c1;
        }

        if (x >= k1 && x < k2) {
            a = a2;
            b = b2;
            c = c2;
            d = d2;
        }

        if (x >= k2) {
            a = a3;
            b = b3;
            c = c3;
            d = d3;
        }

        time = a * x * x * x + b * x * x + c * x + d;
        time += CorrectForB(delBf, bfield, tmax, xhatalpha, Bb1, Bb2, Bb3, Bb4, superlayer);
        return time;
    }

    private static double CorrectForB(double delBf, double bfield, double tmax, double xhatalpha, double Bb1, double Bb2, double Bb3, double Bb4, int superlayer) {
        double time = 0.0D;
        if (superlayer == 3 || superlayer == 4) {
            double deltatime_bfield = delBf * Math.pow(bfield, 2.0D) * tmax * (Bb1 * xhatalpha + Bb2 * Math.pow(xhatalpha, 2.0D) + Bb3 * Math.pow(xhatalpha, 3.0D) + Bb4 * Math.pow(xhatalpha, 4.0D));
            time += deltatime_bfield;
        }

        return time;
    }
}

