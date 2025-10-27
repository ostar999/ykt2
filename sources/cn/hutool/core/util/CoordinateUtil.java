package cn.hutool.core.util;

import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes.dex */
public class CoordinateUtil {
    public static final double CORRECTION_PARAM = 0.006693421622965943d;
    public static final double PI = 3.141592653589793d;
    public static final double RADIUS = 6378245.0d;
    public static final double X_PI = 52.35987755982988d;

    public static class Coordinate implements Serializable {
        private static final long serialVersionUID = 1;
        private double lat;
        private double lng;

        public Coordinate(double d3, double d4) {
            this.lng = d3;
            this.lat = d4;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Coordinate coordinate = (Coordinate) obj;
            return Double.compare(coordinate.lng, this.lng) == 0 && Double.compare(coordinate.lat, this.lat) == 0;
        }

        public double getLat() {
            return this.lat;
        }

        public double getLng() {
            return this.lng;
        }

        public int hashCode() {
            return Objects.hash(Double.valueOf(this.lng), Double.valueOf(this.lat));
        }

        public Coordinate offset(Coordinate coordinate) {
            this.lng += coordinate.lng;
            this.lat += coordinate.lat;
            return this;
        }

        public Coordinate setLat(double d3) {
            this.lat = d3;
            return this;
        }

        public Coordinate setLng(double d3) {
            this.lng = d3;
            return this;
        }

        public String toString() {
            return "Coordinate{lng=" + this.lng + ", lat=" + this.lat + '}';
        }
    }

    public static Coordinate bd09ToGcj02(double d3, double d4) {
        double d5 = d3 - 0.0065d;
        double d6 = d4 - 0.006d;
        double dSqrt = Math.sqrt((d5 * d5) + (d6 * d6)) - (Math.sin(d6 * 52.35987755982988d) * 2.0E-5d);
        double dAtan2 = Math.atan2(d6, d5) - (Math.cos(d5 * 52.35987755982988d) * 3.0E-6d);
        return new Coordinate(Math.cos(dAtan2) * dSqrt, dSqrt * Math.sin(dAtan2));
    }

    public static Coordinate bd09toWgs84(double d3, double d4) {
        Coordinate coordinateBd09ToGcj02 = bd09ToGcj02(d3, d4);
        return gcj02ToWgs84(coordinateBd09ToGcj02.lng, coordinateBd09ToGcj02.lat);
    }

    public static Coordinate gcj02ToBd09(double d3, double d4) {
        double dSqrt = Math.sqrt((d3 * d3) + (d4 * d4)) + (Math.sin(d4 * 52.35987755982988d) * 2.0E-5d);
        double dAtan2 = Math.atan2(d4, d3) + (Math.cos(d3 * 52.35987755982988d) * 3.0E-6d);
        return new Coordinate((Math.cos(dAtan2) * dSqrt) + 0.0065d, (dSqrt * Math.sin(dAtan2)) + 0.006d);
    }

    public static Coordinate gcj02ToWgs84(double d3, double d4) {
        return new Coordinate(d3, d4).offset(offset(d3, d4, false));
    }

    public static Coordinate mercatorToWgs84(double d3, double d4) {
        return new Coordinate((d3 / 2.0037508342789244E7d) * 180.0d, ((Math.atan(Math.exp((((d4 / 2.0037508342789244E7d) * 180.0d) * 3.141592653589793d) / 180.0d)) * 2.0d) - 1.5707963267948966d) * 57.29577951308232d);
    }

    private static Coordinate offset(double d3, double d4, boolean z2) {
        double d5 = d3 - 105.0d;
        double d6 = d4 - 35.0d;
        double dTransLng = transLng(d5, d6);
        double dTransLat = transLat(d5, d6);
        double d7 = (d4 / 180.0d) * 3.141592653589793d;
        double dSin = Math.sin(d7);
        double d8 = 1.0d - ((0.006693421622965943d * dSin) * dSin);
        double dSqrt = Math.sqrt(d8);
        double dCos = (dTransLng * 180.0d) / (((6378245.0d / dSqrt) * Math.cos(d7)) * 3.141592653589793d);
        double d9 = (dTransLat * 180.0d) / ((6335552.717000426d / (d8 * dSqrt)) * 3.141592653589793d);
        if (!z2) {
            dCos = -dCos;
            d9 = -d9;
        }
        return new Coordinate(dCos, d9);
    }

    public static boolean outOfChina(double d3, double d4) {
        return d3 < 72.004d || d3 > 137.8347d || d4 < 0.8293d || d4 > 55.8271d;
    }

    private static double transLat(double d3, double d4) {
        double d5 = d3 * 2.0d;
        double dSqrt = (-100.0d) + d5 + (d4 * 3.0d) + (d4 * 0.2d * d4) + (0.1d * d3 * d4) + (Math.sqrt(Math.abs(d3)) * 0.2d) + ((((Math.sin((6.0d * d3) * 3.141592653589793d) * 20.0d) + (Math.sin(d5 * 3.141592653589793d) * 20.0d)) * 2.0d) / 3.0d);
        double d6 = d4 * 3.141592653589793d;
        return dSqrt + ((((Math.sin(d6) * 20.0d) + (Math.sin((d4 / 3.0d) * 3.141592653589793d) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d4 / 12.0d) * 3.141592653589793d) * 160.0d) + (Math.sin(d6 / 30.0d) * 320.0d)) * 2.0d) / 3.0d);
    }

    private static double transLng(double d3, double d4) {
        double d5 = d3 * 0.1d;
        return d3 + 300.0d + (d4 * 2.0d) + (d5 * d3) + (d5 * d4) + (Math.sqrt(Math.abs(d3)) * 0.1d) + ((((Math.sin((6.0d * d3) * 3.141592653589793d) * 20.0d) + (Math.sin((d3 * 2.0d) * 3.141592653589793d) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(d3 * 3.141592653589793d) * 20.0d) + (Math.sin((d3 / 3.0d) * 3.141592653589793d) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d3 / 12.0d) * 3.141592653589793d) * 150.0d) + (Math.sin((d3 / 30.0d) * 3.141592653589793d) * 300.0d)) * 2.0d) / 3.0d);
    }

    public static Coordinate wgs84ToBd09(double d3, double d4) {
        Coordinate coordinateWgs84ToGcj02 = wgs84ToGcj02(d3, d4);
        return gcj02ToBd09(coordinateWgs84ToGcj02.lng, coordinateWgs84ToGcj02.lat);
    }

    public static Coordinate wgs84ToGcj02(double d3, double d4) {
        return new Coordinate(d3, d4).offset(offset(d3, d4, true));
    }

    public static Coordinate wgs84ToMercator(double d3, double d4) {
        return new Coordinate((d3 * 2.0037508342789244E7d) / 180.0d, ((Math.log(Math.tan(((d4 + 90.0d) * 3.141592653589793d) / 360.0d)) / 0.017453292519943295d) * 2.0037508342789244E7d) / 180.0d);
    }
}
