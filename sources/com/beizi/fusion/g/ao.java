package com.beizi.fusion.g;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.update.ShakeArcView;
import com.beizi.fusion.widget.ShakeView;
import java.math.BigDecimal;

/* loaded from: classes2.dex */
public class ao {

    /* renamed from: b, reason: collision with root package name */
    private static SensorManager f5156b;

    /* renamed from: a, reason: collision with root package name */
    ShakeView f5157a;

    /* renamed from: c, reason: collision with root package name */
    private Context f5158c;

    /* renamed from: d, reason: collision with root package name */
    private double f5159d;

    /* renamed from: e, reason: collision with root package name */
    private double f5160e;

    /* renamed from: f, reason: collision with root package name */
    private double f5161f;

    /* renamed from: g, reason: collision with root package name */
    private int f5162g;

    /* renamed from: h, reason: collision with root package name */
    private int f5163h;

    /* renamed from: i, reason: collision with root package name */
    private int f5164i;

    /* renamed from: j, reason: collision with root package name */
    private int f5165j;

    /* renamed from: k, reason: collision with root package name */
    private int f5166k = 0;

    /* renamed from: l, reason: collision with root package name */
    private int f5167l = 0;

    /* renamed from: m, reason: collision with root package name */
    private float f5168m = -100.0f;

    /* renamed from: n, reason: collision with root package name */
    private float f5169n = -100.0f;

    /* renamed from: o, reason: collision with root package name */
    private float f5170o = -100.0f;

    /* renamed from: p, reason: collision with root package name */
    private int f5171p = 0;

    /* renamed from: q, reason: collision with root package name */
    private a f5172q = null;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5173r = false;

    /* renamed from: s, reason: collision with root package name */
    private int f5174s = 200;

    /* renamed from: t, reason: collision with root package name */
    private long f5175t = 0;

    /* renamed from: u, reason: collision with root package name */
    private ShakeArcView f5176u = null;

    /* renamed from: v, reason: collision with root package name */
    private int f5177v = 0;

    /* renamed from: w, reason: collision with root package name */
    private final SensorEventListener f5178w = new SensorEventListener() { // from class: com.beizi.fusion.g.ao.1
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i2) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (System.currentTimeMillis() - ao.this.f5175t < 200) {
                return;
            }
            float[] fArr = sensorEvent.values;
            float f2 = fArr[0];
            float f3 = fArr[1];
            float f4 = fArr[2];
            if (ao.this.f5168m == -100.0f) {
                ao.this.f5168m = f2;
            }
            if (ao.this.f5169n == -100.0f) {
                ao.this.f5169n = f3;
            }
            if (ao.this.f5170o == -100.0f) {
                ao.this.f5170o = f4;
            }
            ac.b("ShakeUtil", "x = " + f2 + ",initialX = " + ao.this.f5168m + ",y = " + f3 + ",initialY = " + ao.this.f5169n + ",z = " + f4 + ",initialZ = " + ao.this.f5170o);
            double dAbs = ((double) Math.abs(f2 - ao.this.f5168m)) / 9.8d;
            double dAbs2 = ((double) Math.abs(f3 - ao.this.f5169n)) / 9.8d;
            double dAbs3 = ((double) Math.abs(f4 - ao.this.f5170o)) / 9.8d;
            StringBuilder sb = new StringBuilder();
            sb.append("rotateX = ");
            sb.append(dAbs);
            sb.append(",rotateY = ");
            sb.append(dAbs2);
            sb.append(",rotateZ = ");
            sb.append(dAbs3);
            sb.append(",rotateAmplitude = ");
            sb.append(ao.this.f5161f);
            ac.b("ShakeUtil", sb.toString());
            if (dAbs > ao.this.f5161f) {
                ao.f(ao.this);
                ao.this.f5168m = f2;
            }
            if (dAbs2 > ao.this.f5161f) {
                ao.f(ao.this);
                ao.this.f5169n = f3;
            }
            if (dAbs3 > ao.this.f5161f) {
                ao.f(ao.this);
                ao.this.f5170o = f4;
            }
            ao aoVar = ao.this;
            double dA = aoVar.a(f2, f3, f4, aoVar.f5159d);
            if (dA > ao.this.f5159d) {
                ao.this.f5171p = 1;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("startSatisfy:");
            sb2.append(dA);
            sb2.append(";mShakeState = ");
            sb2.append(ao.this.f5171p);
            sb2.append(",isShakeStart = ");
            ao aoVar2 = ao.this;
            sb2.append(aoVar2.a(f2, f3, f4, aoVar2.f5159d));
            sb2.append(",isShakeEnd = ");
            ao aoVar3 = ao.this;
            sb2.append(aoVar3.b(f2, f3, f4, aoVar3.f5160e));
            ac.b("ShakeUtil", sb2.toString());
            if (ao.this.f5171p == 1) {
                ao aoVar4 = ao.this;
                if (aoVar4.b(f2, f3, f4, aoVar4.f5160e)) {
                    ao.this.f5171p = 2;
                    ao.j(ao.this);
                }
            }
            ao.this.a(dAbs, dAbs2, dAbs3, dA);
            ac.b("ShakeUtil", "mShakeCount = " + ao.this.f5166k + ",dstShakeCount = " + ao.this.f5162g + ",mRotateCount = " + ao.this.f5167l + ",dstRotateCount = " + ao.this.f5163h);
            if ((ao.this.f5162g <= 0 || ao.this.f5166k < ao.this.f5162g) && (ao.this.f5163h <= 0 || ao.this.f5167l < ao.this.f5163h)) {
                return;
            }
            ao.this.a();
        }
    };

    public interface a {
        void b();
    }

    public ao(Context context) {
        this.f5158c = context;
        f5156b = (SensorManager) context.getApplicationContext().getSystemService(com.umeng.analytics.pro.am.ac);
    }

    public static /* synthetic */ int f(ao aoVar) {
        int i2 = aoVar.f5167l;
        aoVar.f5167l = i2 + 1;
        return i2;
    }

    public static /* synthetic */ int j(ao aoVar) {
        int i2 = aoVar.f5166k;
        aoVar.f5166k = i2 + 1;
        return i2;
    }

    private void e() {
        int i2;
        ShakeArcView shakeArcView = this.f5176u;
        if (shakeArcView == null || (i2 = this.f5177v) == 0) {
            return;
        }
        if (i2 != 1) {
            if (i2 == 2) {
                shakeArcView.setMaxProgress(this.f5162g);
            }
        } else {
            if (this.f5163h > 0) {
                double d3 = this.f5161f;
                if (d3 > 0.0d) {
                    shakeArcView.setMaxProgress(d3);
                    return;
                }
            }
            shakeArcView.setMaxProgress(this.f5159d);
        }
    }

    private void f() {
        if (((Boolean) f(this.f5165j).second).booleanValue()) {
            y.a(new Runnable() { // from class: com.beizi.fusion.g.ao.2
                @Override // java.lang.Runnable
                public void run() {
                    ao.this.a();
                }
            }, this.f5164i + (((Integer) r0.first).intValue() * 10));
        }
    }

    public void d(int i2) {
        this.f5165j = i2;
        if (i2 > 0) {
            f();
        }
    }

    public void g(int i2) {
        this.f5174s = i2;
    }

    public void c(double d3) {
        this.f5161f = d3;
    }

    public void b(double d3) {
        this.f5160e = d3;
    }

    public void c(int i2) {
        this.f5164i = i2;
    }

    public void d() {
        this.f5173r = false;
        this.f5166k = 0;
        this.f5167l = 0;
        this.f5168m = -100.0f;
        this.f5169n = -100.0f;
        this.f5170o = -100.0f;
        this.f5171p = 0;
        this.f5172q = null;
        this.f5158c = null;
        this.f5157a = null;
        this.f5174s = 200;
        this.f5176u = null;
    }

    public void b(int i2) {
        this.f5162g = i2;
    }

    public void c() {
        ac.a("BeiZis", "enter unRegisterShakeListenerAndSetDefault");
        SensorManager sensorManager = f5156b;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this.f5178w);
        }
        d();
        ShakeView shakeView = this.f5157a;
        if (shakeView != null) {
            shakeView.stopShake();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(float f2, float f3, float f4, double d3) {
        return Math.sqrt((Math.pow(((double) f2) / 9.8d, 2.0d) + Math.pow(((double) f3) / 9.8d, 2.0d)) + Math.pow(((double) f4) / 9.8d, 2.0d)) < d3;
    }

    public void a(AdSpacesBean.BuyerBean.ShakeViewBean shakeViewBean) {
        if (shakeViewBean == null) {
            return;
        }
        ac.c("ShakeUtil", "setShakeParams mShakeCount:" + shakeViewBean.getShakeCount() + ";mRotateCount:" + shakeViewBean.getRotatCount());
        try {
            this.f5168m = -100.0f;
            this.f5169n = -100.0f;
            this.f5170o = -100.0f;
            this.f5166k = 0;
            this.f5167l = 0;
            this.f5171p = 0;
            b(shakeViewBean.getShakeCount());
            a(shakeViewBean.getShakeStartAmplitude());
            b(shakeViewBean.getShakeEndAmplitude());
            c(shakeViewBean.getRotatAmplitude());
            e(shakeViewBean.getRotatCount());
            c(shakeViewBean.getRandomClickTime());
            d(shakeViewBean.getRandomClickNum());
            g(shakeViewBean.getAnimationInterval());
            e();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Pair<Integer, Boolean> f(int i2) {
        int iRandom = (int) ((Math.random() * 100.0d) + 1.0d);
        if (iRandom <= i2) {
            return new Pair<>(Integer.valueOf(iRandom), Boolean.TRUE);
        }
        return new Pair<>(Integer.valueOf(iRandom), Boolean.FALSE);
    }

    public void b() {
        SensorManager sensorManager = f5156b;
        if (sensorManager != null) {
            sensorManager.registerListener(this.f5178w, sensorManager.getDefaultSensor(1), 100000);
        }
    }

    public void e(int i2) {
        this.f5163h = i2;
    }

    public void a(AdSpacesBean.BuyerBean.CoolShakeViewBean coolShakeViewBean) {
        if (coolShakeViewBean == null) {
            return;
        }
        ac.c("ShakeUtil", "setShakeCoolParams mShakeCount:" + coolShakeViewBean.getShakeCount() + ";mRotateCount:" + coolShakeViewBean.getRotatCount());
        try {
            b(coolShakeViewBean.getShakeCount());
            a(coolShakeViewBean.getShakeStartAmplitude());
            b(coolShakeViewBean.getShakeEndAmplitude());
            c(coolShakeViewBean.getRotatAmplitude());
            e(coolShakeViewBean.getRotatCount());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(AdSpacesBean.BuyerBean.AliaseShakeViewBean aliaseShakeViewBean) {
        if (aliaseShakeViewBean == null) {
            return;
        }
        ac.c("ShakeUtil", "setShakeAliaseParams mShakeCount:" + aliaseShakeViewBean.getShakeCount() + ";mRotateCount:" + aliaseShakeViewBean.getRotatCount());
        try {
            b(aliaseShakeViewBean.getShakeCount());
            a(aliaseShakeViewBean.getShakeStartAmplitude());
            b(aliaseShakeViewBean.getShakeEndAmplitude());
            c(aliaseShakeViewBean.getRotatAmplitude());
            e(aliaseShakeViewBean.getRotatCount());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(ShakeArcView shakeArcView, int i2) {
        ac.c("ShakeUtil", "setShakeFeedback feedback:" + i2);
        a(shakeArcView);
        a(i2);
        e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(double d3, double d4, double d5, double d6) {
        ShakeArcView shakeArcView = this.f5176u;
        if (shakeArcView == null) {
            return;
        }
        if (this.f5177v == 2) {
            shakeArcView.setCurrentProgress(this.f5166k);
            return;
        }
        if (this.f5163h > 0 && this.f5161f > 0.0d) {
            double dDoubleValue = new BigDecimal((d3 < d4 || d3 < d5) ? (d4 < d3 || d4 < d5) ? (d5 < d3 || d5 < d4) ? 0.0d : d5 : d4 : d3).setScale(2, 4).doubleValue();
            if (dDoubleValue >= 0.1d) {
                this.f5176u.setCurrentProgress(dDoubleValue);
                return;
            } else {
                if (dDoubleValue < 0.1d) {
                    this.f5176u.setCurrentProgress(0.0d);
                    return;
                }
                return;
            }
        }
        int i2 = this.f5162g;
        if (i2 > 0 && this.f5166k >= i2) {
            shakeArcView.setCurrentProgress(this.f5159d);
            return;
        }
        double dDoubleValue2 = new BigDecimal(d6).setScale(2, 4).doubleValue();
        if (dDoubleValue2 >= 1.1d) {
            this.f5176u.setCurrentProgress(dDoubleValue2);
        } else if (dDoubleValue2 < 1.1d) {
            this.f5176u.setCurrentProgress(0.0d);
        }
    }

    public void a(int i2) {
        this.f5177v = i2;
    }

    public void a(double d3) {
        this.f5159d = d3;
    }

    public void a(a aVar) {
        this.f5172q = aVar;
    }

    public void a(ShakeArcView shakeArcView) {
        this.f5176u = shakeArcView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double a(float f2, float f3, float f4, double d3) {
        return Math.sqrt(Math.pow(f2 / 9.8d, 2.0d) + Math.pow(f3 / 9.8d, 2.0d) + Math.pow(f4 / 9.8d, 2.0d));
    }

    public void a() {
        StringBuilder sb = new StringBuilder();
        sb.append("enter callBackShakeHappened and mShakeStateListener != null ? ");
        sb.append(this.f5172q != null);
        sb.append(",!isCallBack = ");
        sb.append(!this.f5173r);
        ac.a("BeiZis", sb.toString());
        if (this.f5172q == null || this.f5173r) {
            return;
        }
        ac.a("BeiZis", "callback onShakeHappened()");
        ShakeArcView shakeArcView = this.f5176u;
        if (shakeArcView != null && !au.b(shakeArcView)) {
            ac.b("ShakeUtil", "mShakeCount onShakeHappened mShakeArcView is not show");
            this.f5168m = -100.0f;
            this.f5169n = -100.0f;
            this.f5170o = -100.0f;
            this.f5166k = 0;
            this.f5167l = 0;
            this.f5171p = 0;
            return;
        }
        this.f5172q.b();
        this.f5173r = true;
        ShakeView shakeView = this.f5157a;
        if (shakeView != null) {
            shakeView.stopShake();
            c();
        }
    }

    public View a(int i2, int i3, AdSpacesBean.BuyerBean.PercentPositionBean percentPositionBean) throws NumberFormatException {
        int i4;
        int i5;
        int i6;
        int i7;
        ac.a("BeiZis", "enter getShakeView");
        if (this.f5158c == null || percentPositionBean == null) {
            return null;
        }
        this.f5157a = new ShakeView(this.f5158c);
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
        float fK = as.k(this.f5158c);
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
        int iA = as.a(this.f5158c, i8);
        int iA2 = as.a(this.f5158c, i7);
        int iA3 = as.a(this.f5158c, i4);
        int iA4 = as.a(this.f5158c, i5);
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
            iA4 = as.a(this.f5158c, i3) / 2;
        }
        if (iA3 == 0) {
            iA3 = as.a(this.f5158c, i2) / 2;
        }
        marginLayoutParams.topMargin = iA4 - (iA2 / 2);
        marginLayoutParams.leftMargin = iA3 - (iA / 2);
        this.f5157a.setLayoutParams(marginLayoutParams);
        ac.a("BeiZis", "topMargin = " + marginLayoutParams.topMargin + ",leftMargin = " + marginLayoutParams.leftMargin + ",widthInt = " + iA + ",heightInt = " + iA2);
        this.f5157a.startShake();
        b();
        return this.f5157a;
    }
}
