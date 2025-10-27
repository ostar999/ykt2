package com.beizi.fusion.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import cn.hutool.core.text.StrPool;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.s;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/* loaded from: classes2.dex */
public class DevInfo {
    private static final String KEY_SDK_UID_NEW = "SDK_UID_KEY_NEW";
    private static final String KEY_SDK_UID_ORIG = "SDK_UID_KEY";
    private static final int algorithmVersion = 1;
    private String cnOaid;
    private Context context;
    private String density;
    private String densityDpi;
    private String devType;
    private String gaid;
    private String language;
    private String msaOaid;
    private String oaid;
    private String resolution;
    private String root;
    private String screenSize;
    private String sdkUID;
    private String sdkUIDOrig;
    private String smOaid;
    private String os = s.c();
    private String platform = "2";
    private String brand = s.a();
    private String model = s.b();

    public DevInfo(Context context) {
        this.context = context;
        this.sdkUID = aq.a(context, KEY_SDK_UID_NEW);
        this.sdkUIDOrig = aq.a(context, KEY_SDK_UID_ORIG);
        this.devType = s.d(context);
        getScreenInformation(context);
        this.language = Locale.getDefault().getLanguage();
        this.root = as.a();
        if ((BeiZis.getCustomController() == null || BeiZis.getCustomController().isCanUseGaid()) && !((Boolean) aq.b(context, "isLimitTrackGaid", Boolean.FALSE)).booleanValue()) {
            this.gaid = (String) aq.b(context, "__GAID__", "");
        }
        if (TextUtils.isEmpty(this.sdkUID)) {
            generateSdkUID(context, this.brand, this.model);
        }
    }

    private String generateCurrentTimeMillis() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.clear();
        calendar.set(2020, 0, 1, 0, 0, 0);
        return String.valueOf(System.currentTimeMillis() - calendar.getTimeInMillis());
    }

    private String generateFourDigitRandom() {
        Random random = new Random();
        return "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
    }

    private void generateSdkUID(Context context, String str, String str2) {
        String strGenerateFourDigitRandom = generateFourDigitRandom();
        String strGenerateCurrentTimeMillis = generateCurrentTimeMillis();
        String str3 = ar.a(str + StrPool.UNDERLINE + str2 + StrPool.UNDERLINE + as.c(context) + StrPool.UNDERLINE + 1 + StrPool.UNDERLINE + strGenerateFourDigitRandom + StrPool.UNDERLINE + strGenerateCurrentTimeMillis) + StrPool.UNDERLINE + 1 + StrPool.UNDERLINE + strGenerateFourDigitRandom + StrPool.UNDERLINE + strGenerateCurrentTimeMillis;
        this.sdkUID = str3;
        aq.a(context, KEY_SDK_UID_NEW, str3);
    }

    private void getScreenInformation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.resolution = displayMetrics.widthPixels + StrPool.UNDERLINE + displayMetrics.heightPixels;
        float f2 = ((float) displayMetrics.heightPixels) / displayMetrics.ydpi;
        float f3 = ((float) displayMetrics.widthPixels) / displayMetrics.xdpi;
        double dSqrt = Math.sqrt((double) ((f3 * f3) + (f2 * f2)));
        this.screenSize = String.format("%.2f", Double.valueOf(Math.round(dSqrt * r2) / Math.pow(10.0d, 2.0d)));
        this.density = context.getResources().getDisplayMetrics().density + "";
        this.densityDpi = context.getResources().getDisplayMetrics().densityDpi + "";
    }

    public String getBrand() {
        return this.brand;
    }

    public String getCnOaid() {
        return this.cnOaid;
    }

    public String getDensity() {
        return this.density;
    }

    public String getDensityDpi() {
        return this.densityDpi;
    }

    public String getDevType() {
        return this.devType;
    }

    public String getGaid() {
        return this.gaid;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getModel() {
        return this.model;
    }

    public String getMsaOaid() {
        return this.msaOaid;
    }

    public String getOaid() {
        return this.oaid;
    }

    public String getOs() {
        return this.os;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getResolution() {
        return this.resolution;
    }

    public String getRoot() {
        return this.root;
    }

    public String getScreenSize() {
        return this.screenSize;
    }

    public String getSdkUID() {
        return this.sdkUID;
    }

    public String getSdkUIDOrig() {
        return this.sdkUIDOrig;
    }

    public String getSmOaid() {
        return this.smOaid;
    }

    public void setBrand(String str) {
        this.brand = str;
    }

    public void setCnOaid(String str) {
        this.cnOaid = str;
    }

    public void setDensity(String str) {
        this.density = str;
    }

    public void setDensityDpi(String str) {
        this.densityDpi = str;
    }

    public void setDevType(String str) {
        this.devType = str;
    }

    public void setGaid(String str) {
        this.gaid = str;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public void setMsaOaid(String str) {
        this.msaOaid = str;
    }

    public void setOaid(String str) {
        this.oaid = str;
    }

    public void setOs(String str) {
        this.os = str;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public void setResolution(String str) {
        this.resolution = str;
    }

    public void setRoot(String str) {
        this.root = str;
    }

    public void setScreenSize(String str) {
        this.screenSize = str;
    }

    public void setSdkUID(String str) {
        this.sdkUID = str;
    }

    public void setSdkUIDOrig(String str) {
        this.sdkUIDOrig = str;
    }

    public void setSmOaid(String str) {
        this.smOaid = str;
    }
}
