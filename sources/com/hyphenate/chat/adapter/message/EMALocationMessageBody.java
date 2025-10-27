package com.hyphenate.chat.adapter.message;

/* loaded from: classes4.dex */
public class EMALocationMessageBody extends EMAMessageBody {
    public String address;
    public String buildingName;
    public double latitude;
    public double longitude;

    private EMALocationMessageBody() {
        nativeInit(0.0d, 0.0d, "", "");
    }

    public EMALocationMessageBody(double d3, double d4, String str, String str2) {
        nativeInit(d3, d4, str, str2);
    }

    public EMALocationMessageBody(EMALocationMessageBody eMALocationMessageBody) {
        nativeInit(eMALocationMessageBody);
    }

    public String address() {
        return nativeaddress();
    }

    public String buildingName() {
        return nativebuildingName();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public double latitude() {
        return nativelatitude();
    }

    public double longitude() {
        return nativelongitude();
    }

    public native void nativeFinalize();

    public native void nativeInit(double d3, double d4, String str, String str2);

    public native void nativeInit(EMALocationMessageBody eMALocationMessageBody);

    public native String nativeaddress();

    public native String nativebuildingName();

    public native double nativelatitude();

    public native double nativelongitude();

    public native void nativesetAddress(String str);

    public native void nativesetBuildingName(String str);

    public native void nativesetLatitude(double d3);

    public native void nativesetLongitude(double d3);

    public void setAddress(String str) {
        nativesetAddress(str);
    }

    public void setBuildingName(String str) {
        nativesetBuildingName(str);
    }

    public void setLatitude(double d3) {
        nativesetLatitude(d3);
    }

    public void setLongitude(double d3) {
        nativesetLongitude(d3);
    }
}
