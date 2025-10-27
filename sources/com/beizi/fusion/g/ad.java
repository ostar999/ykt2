package com.beizi.fusion.g;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.ShakeView;

/* loaded from: classes2.dex */
public class ad {

    /* renamed from: b, reason: collision with root package name */
    private static SensorManager f5075b;

    /* renamed from: a, reason: collision with root package name */
    ShakeView f5076a;

    /* renamed from: c, reason: collision with root package name */
    private Context f5077c;

    /* renamed from: d, reason: collision with root package name */
    private double f5078d;

    /* renamed from: e, reason: collision with root package name */
    private double f5079e;

    /* renamed from: f, reason: collision with root package name */
    private double f5080f;

    /* renamed from: g, reason: collision with root package name */
    private int f5081g;

    /* renamed from: h, reason: collision with root package name */
    private int f5082h;

    /* renamed from: t, reason: collision with root package name */
    private AdSpacesBean.BuyerBean.CoolShakeViewBean f5094t;

    /* renamed from: u, reason: collision with root package name */
    private String f5095u;

    /* renamed from: i, reason: collision with root package name */
    private int f5083i = 0;

    /* renamed from: j, reason: collision with root package name */
    private int f5084j = 0;

    /* renamed from: k, reason: collision with root package name */
    private float f5085k = -100.0f;

    /* renamed from: l, reason: collision with root package name */
    private float f5086l = -100.0f;

    /* renamed from: m, reason: collision with root package name */
    private float f5087m = -100.0f;

    /* renamed from: n, reason: collision with root package name */
    private int f5088n = 0;

    /* renamed from: o, reason: collision with root package name */
    private a f5089o = null;

    /* renamed from: p, reason: collision with root package name */
    private boolean f5090p = false;

    /* renamed from: q, reason: collision with root package name */
    private int f5091q = 200;

    /* renamed from: r, reason: collision with root package name */
    private View f5092r = null;

    /* renamed from: s, reason: collision with root package name */
    private long f5093s = 0;

    /* renamed from: v, reason: collision with root package name */
    private boolean f5096v = false;

    /* renamed from: w, reason: collision with root package name */
    private final SensorEventListener f5097w = new SensorEventListener() { // from class: com.beizi.fusion.g.ad.1
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i2) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (System.currentTimeMillis() - ad.this.f5093s < 200) {
                return;
            }
            if (!au.a(ad.this.f5092r)) {
                ac.b("ShakeUtil", "onShakeHappened mContainerView is not show");
                ad.this.d();
                return;
            }
            if (ad.this.f5096v && ad.this.f5094t != null && !TextUtils.isEmpty(ad.this.f5095u) && an.a().b(ad.this.f5095u) > 0) {
                ac.c("ShakeUtil", "mShakeCount isUserSensitiveScheme:" + ad.this.f5096v + ";coolShakeViewBean:" + ad.this.f5094t + ";coolConfigKey:" + ad.this.f5095u + ";getCoolTime:" + an.a().b(ad.this.f5095u));
                ad adVar = ad.this;
                adVar.a(adVar.f5094t);
            }
            float[] fArr = sensorEvent.values;
            float f2 = fArr[0];
            float f3 = fArr[1];
            float f4 = fArr[2];
            if (ad.this.f5085k == -100.0f) {
                ad.this.f5085k = f2;
            }
            if (ad.this.f5086l == -100.0f) {
                ad.this.f5086l = f3;
            }
            if (ad.this.f5087m == -100.0f) {
                ad.this.f5087m = f4;
            }
            ac.b("ShakeUtil", "x = " + f2 + ",initialX = " + ad.this.f5085k + ",y = " + f3 + ",initialY = " + ad.this.f5086l + ",z = " + f4 + ",initialZ = " + ad.this.f5087m);
            double dAbs = ((double) Math.abs(f2 - ad.this.f5085k)) / 9.8d;
            double dAbs2 = ((double) Math.abs(f3 - ad.this.f5086l)) / 9.8d;
            double dAbs3 = ((double) Math.abs(f4 - ad.this.f5087m)) / 9.8d;
            StringBuilder sb = new StringBuilder();
            sb.append("rotateX = ");
            sb.append(dAbs);
            sb.append(",rotateY = ");
            sb.append(dAbs2);
            sb.append(",rotateZ = ");
            sb.append(dAbs3);
            sb.append(",rotateAmplitude = ");
            sb.append(ad.this.f5080f);
            ac.b("ShakeUtil", sb.toString());
            if (dAbs > ad.this.f5080f) {
                ad.j(ad.this);
                ad.this.f5085k = f2;
            }
            if (dAbs2 > ad.this.f5080f) {
                ad.j(ad.this);
                ad.this.f5086l = f3;
            }
            if (dAbs3 > ad.this.f5080f) {
                ad.j(ad.this);
                ad.this.f5087m = f4;
            }
            ad adVar2 = ad.this;
            if (adVar2.a(f2, f3, f4, adVar2.f5078d)) {
                ad.this.f5088n = 1;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("mRotateCount:");
            sb2.append(ad.this.f5084j);
            sb2.append(";mShakeCount:");
            sb2.append(ad.this.f5083i);
            sb2.append(",mShakeState = ");
            sb2.append(ad.this.f5088n);
            sb2.append(",isShakeStart = ");
            ad adVar3 = ad.this;
            sb2.append(adVar3.a(f2, f3, f4, adVar3.f5078d));
            sb2.append(",isShakeEnd = ");
            ad adVar4 = ad.this;
            sb2.append(adVar4.b(f2, f3, f4, adVar4.f5079e));
            ac.b("ShakeUtil", sb2.toString());
            if (ad.this.f5088n == 1) {
                ad adVar5 = ad.this;
                if (adVar5.b(f2, f3, f4, adVar5.f5079e)) {
                    ad.this.f5088n = 2;
                    ad.p(ad.this);
                }
            }
            ac.b("ShakeUtil", "mShakeCount = " + ad.this.f5083i + ",dstShakeCount = " + ad.this.f5081g + ",mRotateCount = " + ad.this.f5084j + ",dstRotateCount = " + ad.this.f5082h);
            if ((ad.this.f5081g > 0 && ad.this.f5083i >= ad.this.f5081g) || (ad.this.f5082h > 0 && ad.this.f5084j >= ad.this.f5082h)) {
                ad.this.a();
            }
            ad.this.f5093s = System.currentTimeMillis();
        }
    };

    public interface a {
        void a();
    }

    public ad(Context context) {
        this.f5077c = context;
        f5075b = (SensorManager) context.getApplicationContext().getSystemService(com.umeng.analytics.pro.am.ac);
    }

    public static /* synthetic */ int j(ad adVar) {
        int i2 = adVar.f5084j;
        adVar.f5084j = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int p(ad adVar) {
        int i2 = adVar.f5083i;
        adVar.f5083i = i2 + 1;
        return i2;
    }

    public void d() {
        this.f5085k = -100.0f;
        this.f5086l = -100.0f;
        this.f5087m = -100.0f;
        this.f5088n = 0;
        this.f5091q = 200;
        this.f5083i = 0;
        this.f5084j = 0;
        this.f5090p = false;
    }

    public void c(double d3) {
        this.f5080f = d3;
    }

    public void b(double d3) {
        this.f5079e = d3;
    }

    public void c() {
        ac.a("BeiZis", "enter unRegisterShakeListenerAndSetDefault");
        SensorManager sensorManager = f5075b;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this.f5097w);
        }
        d();
        ShakeView shakeView = this.f5076a;
        if (shakeView != null) {
            shakeView.stopShake();
        }
        this.f5089o = null;
        this.f5077c = null;
        this.f5076a = null;
    }

    public void b(int i2) {
        this.f5082h = i2;
    }

    public void a(AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean) {
        if (shakeViewBean == null) {
            return;
        }
        ac.a("BeiZis", "setShakeParams shakeCount:" + shakeViewBean.getShakeCount() + ";rotatCount:" + shakeViewBean.getRotatCount());
        try {
            this.f5096v = true;
            a(shakeViewBean.getShakeCount());
            a(shakeViewBean.getShakeStartAmplitude());
            b(shakeViewBean.getShakeEndAmplitude());
            c(shakeViewBean.getRotatAmplitude());
            b(shakeViewBean.getRotatCount());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void b(AdSpacesBean.BuyerBean.CoolShakeViewBean coolShakeViewBean) {
        this.f5094t = coolShakeViewBean;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(float f2, float f3, float f4, double d3) {
        return Math.sqrt((Math.pow(((double) f2) / 9.8d, 2.0d) + Math.pow(((double) f3) / 9.8d, 2.0d)) + Math.pow(((double) f4) / 9.8d, 2.0d)) < d3;
    }

    public void b() {
        SensorManager sensorManager = f5075b;
        if (sensorManager != null) {
            sensorManager.registerListener(this.f5097w, sensorManager.getDefaultSensor(1), 100000);
        }
    }

    public void a(AdSpacesBean.BuyerBean.CoolShakeViewBean coolShakeViewBean) {
        if (coolShakeViewBean == null) {
            return;
        }
        ac.c("ShakeUtil", "setShakeCoolParams mShakeCount:" + coolShakeViewBean.getShakeCount() + ";mRotateCount:" + coolShakeViewBean.getRotatCount());
        try {
            this.f5096v = false;
            a(coolShakeViewBean.getShakeCount());
            a(coolShakeViewBean.getShakeStartAmplitude());
            b(coolShakeViewBean.getShakeEndAmplitude());
            c(coolShakeViewBean.getRotatAmplitude());
            b(coolShakeViewBean.getRotatCount());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(AdSpacesBean.BuyerBean.CoolShakeViewBean coolShakeViewBean, String str) {
        b(coolShakeViewBean);
        a(str);
    }

    public void a(double d3) {
        this.f5078d = d3;
    }

    public void a(int i2) {
        this.f5081g = i2;
    }

    public void a(a aVar) {
        this.f5089o = aVar;
    }

    public void a(String str) {
        this.f5095u = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(float f2, float f3, float f4, double d3) {
        return Math.sqrt((Math.pow(((double) f2) / 9.8d, 2.0d) + Math.pow(((double) f3) / 9.8d, 2.0d)) + Math.pow(((double) f4) / 9.8d, 2.0d)) > d3;
    }

    public void a() {
        StringBuilder sb = new StringBuilder();
        sb.append("enter callBackShakeHappened and mShakeStateListener != null ? ");
        sb.append(this.f5089o != null);
        sb.append(",!isCallBack = ");
        sb.append(!this.f5090p);
        ac.a("BeiZis", sb.toString());
        if (this.f5089o == null || this.f5090p) {
            return;
        }
        ac.a("BeiZis", "callback onShakeHappened()");
        this.f5090p = true;
        this.f5089o.a();
    }

    public void a(View view) {
        this.f5092r = view;
    }

    public View a(int i2, int i3, AdSpacesBean.BuyerBean.PercentPositionBean percentPositionBean) throws NumberFormatException {
        int i4;
        int i5;
        int i6;
        int i7;
        ac.a("BeiZis", "enter getShakeView");
        if (this.f5077c == null || percentPositionBean == null) {
            return null;
        }
        this.f5076a = new ShakeView(this.f5077c);
        String centerX = percentPositionBean.getCenterX();
        String centerY = percentPositionBean.getCenterY();
        String width = percentPositionBean.getWidth();
        String height = percentPositionBean.getHeight();
        if (TextUtils.isEmpty(centerX) || "0".equals(centerX)) {
            centerX = "50%";
        }
        if (TextUtils.isEmpty(centerY) || "0".equals(centerY)) {
            centerY = "50%";
        }
        if (TextUtils.isEmpty(width) || "0".equals(width)) {
            width = "180";
        }
        if (TextUtils.isEmpty(height) || "0".equals(height)) {
            height = "180";
        }
        float fK = as.k(this.f5077c);
        if (centerX.endsWith("%")) {
            i4 = (Integer.parseInt(centerX.substring(0, centerX.indexOf("%"))) * i2) / 100;
        } else {
            i4 = Integer.parseInt(centerX);
        }
        if (centerY.endsWith("%")) {
            i5 = (Integer.parseInt(centerY.substring(0, centerY.indexOf("%"))) * i3) / 100;
        } else {
            i5 = Integer.parseInt(centerY);
        }
        int i8 = 400;
        if (width.endsWith("%")) {
            int i9 = Integer.parseInt(width.substring(0, width.indexOf("%")));
            if (fK >= 400.0f) {
                i6 = (i9 * 400) / 100;
                i8 = i6;
            } else {
                i8 = (((int) fK) * i9) / 100;
            }
        } else {
            i6 = Integer.parseInt(width);
            if (i6 < 400) {
                i8 = i6;
            }
        }
        if (height.endsWith("%")) {
            i7 = (Integer.parseInt(height.substring(0, height.indexOf("%"))) * i8) / 100;
        } else {
            i7 = Integer.parseInt(height);
        }
        int iA = as.a(this.f5077c, i8);
        int iA2 = as.a(this.f5077c, i7);
        int iA3 = as.a(this.f5077c, i4);
        int iA4 = as.a(this.f5077c, i5);
        ac.a("BeiZis", "widthInt = " + iA + ",heightInt = " + iA2);
        if (iA == 0) {
            iA = 180;
        }
        if (iA2 == 0) {
            iA2 = iA;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(iA, iA2);
        ac.a("BeiZis", "centerYInt = " + iA4 + ",centerXInt = " + iA3 + ",adWidthDp = " + i2 + ",adHeightDp = " + i3);
        if (iA4 == 0) {
            iA4 = as.a(this.f5077c, i3) / 2;
        }
        if (iA3 == 0) {
            iA3 = as.a(this.f5077c, i2) / 2;
        }
        marginLayoutParams.topMargin = iA4 - (iA2 / 2);
        marginLayoutParams.leftMargin = iA3 - (iA / 2);
        this.f5076a.setLayoutParams(marginLayoutParams);
        ac.a("BeiZis", "topMargin = " + marginLayoutParams.topMargin + ",leftMargin = " + marginLayoutParams.leftMargin + ",widthInt = " + iA + ",heightInt = " + iA2);
        this.f5076a.startShake();
        b();
        return this.f5076a;
    }
}
