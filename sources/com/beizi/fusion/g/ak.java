package com.beizi.fusion.g;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.widget.TwistView;
import java.math.BigDecimal;

/* loaded from: classes2.dex */
public class ak {

    /* renamed from: a, reason: collision with root package name */
    private static final String f5112a = "ak";

    /* renamed from: c, reason: collision with root package name */
    private static SensorManager f5113c;

    /* renamed from: n, reason: collision with root package name */
    private static String f5114n;

    /* renamed from: o, reason: collision with root package name */
    private static String f5115o;

    /* renamed from: p, reason: collision with root package name */
    private static String f5116p;

    /* renamed from: q, reason: collision with root package name */
    private static String f5117q;

    /* renamed from: b, reason: collision with root package name */
    private Context f5118b;

    /* renamed from: j, reason: collision with root package name */
    private long f5125j;

    /* renamed from: k, reason: collision with root package name */
    private double f5126k;

    /* renamed from: l, reason: collision with root package name */
    private double f5127l;

    /* renamed from: m, reason: collision with root package name */
    private TwistView f5128m;

    /* renamed from: d, reason: collision with root package name */
    private long f5119d = 0;

    /* renamed from: e, reason: collision with root package name */
    private double f5120e = -999.0d;

    /* renamed from: f, reason: collision with root package name */
    private double f5121f = -999.0d;

    /* renamed from: g, reason: collision with root package name */
    private boolean f5122g = false;

    /* renamed from: h, reason: collision with root package name */
    private boolean f5123h = false;

    /* renamed from: i, reason: collision with root package name */
    private int f5124i = 0;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5129r = false;

    /* renamed from: s, reason: collision with root package name */
    private boolean f5130s = false;

    /* renamed from: t, reason: collision with root package name */
    private a f5131t = null;

    /* renamed from: u, reason: collision with root package name */
    private final SensorEventListener f5132u = new SensorEventListener() { // from class: com.beizi.fusion.g.ak.1
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i2) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (System.currentTimeMillis() - ak.this.f5119d < 80) {
                return;
            }
            ak.this.f5119d = System.currentTimeMillis();
            float[] fArr = sensorEvent.values;
            double d3 = fArr[0] / 9.8d;
            double d4 = fArr[1] / 9.8d;
            double d5 = fArr[2] / 9.8d;
            double degrees = Math.toDegrees(Math.atan2(d3, d4));
            double d6 = degrees <= 0.0d ? (-degrees) - 180.0d : 180.0d - degrees;
            double degrees2 = Math.toDegrees(Math.atan2(d3, d5));
            double d7 = degrees2 <= 0.0d ? (-degrees2) - 180.0d : 180.0d - degrees2;
            double degrees3 = Math.toDegrees(Math.atan2(d5, Math.sqrt((d3 * d3) + (d4 * d4)))) + 90.0d;
            if (degrees3 <= 45.0d || degrees3 >= 135.0d || (Math.abs((Math.sin(d4) / 3.141592653589793d) * 180.0d) < 45.0d && ak.this.f5122g)) {
                if (ak.this.f5120e == -999.0d || !ak.this.f5122g) {
                    ak.this.f5120e = d7;
                    ak.this.f5122g = true;
                    ak.this.f5123h = false;
                    return;
                }
                ac.c(ak.f5112a, "水平角度 initialXZTheta:" + new BigDecimal(ak.this.f5120e).setScale(2, 4) + ";xyTheta:" + new BigDecimal(d6).setScale(2, 4) + ";xzTheta:" + new BigDecimal(d7).setScale(2, 4));
                if ((d7 >= ak.this.f5120e && d7 - ak.this.f5120e > ak.this.f5126k && d7 - ak.this.f5120e <= 180.0d) || (d7 < ak.this.f5120e && ak.this.f5120e > 0.0d && (360.0d - ak.this.f5120e) + d7 > ak.this.f5126k && (360.0d - ak.this.f5120e) + d7 <= 180.0d)) {
                    ac.c(ak.f5112a, "11111发生水平状态滚动 rollStatus:" + ak.this.f5124i);
                    ak.this.f5124i = 1;
                    ak.this.i();
                    return;
                }
                if (d7 < ak.this.f5120e || ((d7 - ak.this.f5120e >= ak.this.f5127l || Math.abs(d7 - ak.this.f5120e) > 180.0d) && (Math.abs(d7 - ak.this.f5120e) < 180.0d || (-(360.0d - Math.abs(d7 - ak.this.f5120e))) >= ak.this.f5127l))) {
                    if (d7 >= ak.this.f5120e) {
                        return;
                    }
                    if ((d7 - ak.this.f5120e >= ak.this.f5127l || Math.abs(d7 - ak.this.f5120e) > 180.0d) && (Math.abs(d7 - ak.this.f5120e) < 180.0d || 360.0d - Math.abs(d7 - ak.this.f5120e) >= ak.this.f5127l)) {
                        return;
                    }
                }
                ac.a(ak.f5112a, "2222发生水平状态回滚 rollStatus:" + ak.this.f5124i);
                if (ak.this.f5124i == 1) {
                    ak.this.f5124i = 2;
                    ac.a(ak.f5112a, "发生水平状态回滚");
                    ak.this.i();
                    return;
                }
                return;
            }
            if (ak.this.f5121f == -999.0d || !ak.this.f5123h) {
                ak.this.f5121f = d6;
                ak.this.f5123h = true;
                ak.this.f5122g = false;
                return;
            }
            ac.c(ak.f5112a, "垂直角度 initialXYTheta:" + new BigDecimal(ak.this.f5121f).setScale(2, 4) + ";xyTheta" + new BigDecimal(d6).setScale(2, 4) + ";xzTheta:" + new BigDecimal(d7).setScale(2, 4));
            if ((d6 >= ak.this.f5121f && d6 - ak.this.f5121f > ak.this.f5126k && d6 - ak.this.f5121f <= 180.0d) || (d6 < ak.this.f5121f && ak.this.f5121f > 0.0d && (360.0d - ak.this.f5121f) + d6 > ak.this.f5126k && (360.0d - ak.this.f5121f) + d6 <= 180.0d)) {
                ac.c(ak.f5112a, "11111发生垂直状态滚动 rollStatus:" + ak.this.f5124i);
                ak.this.f5124i = 1;
                ak.this.i();
                return;
            }
            if (d6 < ak.this.f5121f || ((d6 - ak.this.f5121f >= ak.this.f5127l || Math.abs(d6 - ak.this.f5121f) > 180.0d) && (Math.abs(d6 - ak.this.f5121f) < 180.0d || (-(360.0d - Math.abs(d6 - ak.this.f5121f))) >= ak.this.f5127l))) {
                if (d6 >= ak.this.f5121f) {
                    return;
                }
                if ((d6 - ak.this.f5121f >= ak.this.f5127l || Math.abs(d6 - ak.this.f5121f) > 180.0d) && (Math.abs(d6 - ak.this.f5121f) < 180.0d || 360.0d - Math.abs(d6 - ak.this.f5121f) >= ak.this.f5127l)) {
                    return;
                }
            }
            ac.a(ak.f5112a, "2222发生垂直状态回滚 rollStatus:" + ak.this.f5124i);
            if (ak.this.f5124i == 1) {
                ak.this.f5124i = 2;
                ac.a(ak.f5112a, "发生垂直状态回滚");
                ak.this.i();
            }
        }
    };

    public interface a {
        void a();

        void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8);
    }

    public ak(Context context) {
        this.f5118b = context;
        f5113c = (SensorManager) context.getApplicationContext().getSystemService(com.umeng.analytics.pro.am.ac);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        TwistView twistView = this.f5128m;
        if (twistView != null) {
            twistView.updateRollStatus(this.f5124i);
        }
    }

    public void c() {
        this.f5130s = false;
    }

    public void b() {
        SensorManager sensorManager = f5113c;
        if (sensorManager != null) {
            sensorManager.unregisterListener(this.f5132u);
        }
        TwistView twistView = this.f5128m;
        if (twistView != null) {
            twistView.destroyView();
            this.f5128m = null;
        }
        this.f5130s = false;
        this.f5129r = false;
    }

    public void a(ViewGroup viewGroup, int i2, int i3, AdSpacesBean.BuyerBean.RollViewBean rollViewBean) throws NumberFormatException {
        AdSpacesBean.BuyerBean.PercentPositionBean position;
        int i4;
        int i5;
        int i6;
        int i7;
        ac.a("BeiZis", "enter addRollView");
        if (this.f5118b == null || viewGroup == null || rollViewBean == null || (position = rollViewBean.getPosition()) == null) {
            return;
        }
        this.f5128m = new TwistView(this.f5118b);
        String centerX = position.getCenterX();
        String centerY = position.getCenterY();
        String width = position.getWidth();
        String height = position.getHeight();
        if (TextUtils.isEmpty(centerX) || "0".equals(centerX)) {
            centerX = "85%";
        }
        if (TextUtils.isEmpty(centerY) || "0".equals(centerY)) {
            centerY = "50%";
        }
        if (TextUtils.isEmpty(width) || "0".equals(width)) {
            width = "340";
        }
        if (TextUtils.isEmpty(height) || "0".equals(height)) {
            height = "70";
        }
        float fK = as.k(this.f5118b);
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
        int iA = as.a(this.f5118b, i8);
        int iA2 = as.a(this.f5118b, i7 + 95);
        int iA3 = as.a(this.f5118b, i4);
        int iA4 = as.a(this.f5118b, i5);
        ac.a("BeiZis", "widthInt = " + iA + ",heightInt = " + iA2);
        int iA5 = as.a(this.f5118b, 340.0f);
        int iA6 = as.a(this.f5118b, 165.0f);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iA5, iA6);
        ac.a("BeiZis", "centerYInt = " + iA4 + ",centerXInt = " + iA3 + ",adWidthDp = " + i2 + ",adHeightDp = " + i3);
        if (iA4 == 0) {
            iA4 = as.a(this.f5118b, i3) / 2;
        }
        if (iA3 == 0) {
            iA3 = as.a(this.f5118b, i2) / 2;
        }
        layoutParams.topMargin = iA4 - (iA6 / 2);
        layoutParams.leftMargin = iA3 - (iA5 / 2);
        this.f5128m.setTwistTotalLayoutWidthAndHeight(iA5, iA6);
        this.f5128m.setLayoutParams(layoutParams);
        this.f5128m.setTwistTotalLayoutBg(rollViewBean.getBgColor());
        this.f5128m.setMainTitleText(rollViewBean.getTitle());
        this.f5128m.setDescribeText(rollViewBean.getSubTitle());
        this.f5128m.setJumpClickListener(new View.OnClickListener() { // from class: com.beizi.fusion.g.ak.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ak.this.f5130s) {
                    return;
                }
                ak.this.f5130s = true;
                if (ak.this.f5131t != null) {
                    ak.this.f5131t.a(ak.f5114n, ak.f5115o, ak.f5116p, ak.f5117q, ak.f5114n, ak.f5115o, ak.f5116p, ak.f5117q);
                }
            }
        });
        this.f5128m.setJumpOnTouchListener(new View.OnTouchListener() { // from class: com.beizi.fusion.g.ak.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    if (motionEvent.getAction() != 0) {
                        return false;
                    }
                    String unused = ak.f5114n = motionEvent.getX() + "";
                    String unused2 = ak.f5115o = motionEvent.getY() + "";
                    String unused3 = ak.f5116p = motionEvent.getRawX() + "";
                    String unused4 = ak.f5117q = motionEvent.getRawY() + "";
                    return false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return false;
                }
            }
        });
        this.f5128m.setRotationEndCallback(new TwistView.a() { // from class: com.beizi.fusion.g.ak.4
            @Override // com.beizi.fusion.widget.TwistView.a
            public void a() {
                if (ak.this.f5129r) {
                    return;
                }
                ak.this.f5129r = true;
                if (ak.this.f5131t != null) {
                    ak.this.f5131t.a();
                }
            }
        });
        viewGroup.addView(this.f5128m, layoutParams);
    }

    public void b(double d3) {
        this.f5127l = d3;
    }

    public void a() {
        SensorManager sensorManager = f5113c;
        if (sensorManager != null) {
            sensorManager.registerListener(this.f5132u, sensorManager.getDefaultSensor(1), 100000);
        }
    }

    public void a(AdSpacesBean.BuyerBean.RollViewBean rollViewBean) {
        if (rollViewBean == null) {
            return;
        }
        try {
            ac.a(f5112a, "setRollParams getRollTime:" + rollViewBean.getRollTime() + ";getRollPlusAmplitude:" + rollViewBean.getRollPlusAmplitude() + ";getRollMinusAmplitude:" + rollViewBean.getRollMinusAmplitude());
            a(rollViewBean.getRollTime());
            a(rollViewBean.getRollPlusAmplitude());
            b(rollViewBean.getRollMinusAmplitude());
            TwistView twistView = this.f5128m;
            if (twistView != null) {
                twistView.setDurationAnimation(rollViewBean.getRollTime());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(AdSpacesBean.BuyerBean.CoolRollViewBean coolRollViewBean) {
        if (coolRollViewBean == null) {
            return;
        }
        try {
            ac.a(f5112a, "setRollCoolParams getRollTime:" + coolRollViewBean.getRollTime() + ";getRollPlusAmplitude:" + coolRollViewBean.getRollPlusAmplitude() + ";getRollMinusAmplitude:" + coolRollViewBean.getRollMinusAmplitude());
            a(coolRollViewBean.getRollTime());
            a(coolRollViewBean.getRollPlusAmplitude());
            b(coolRollViewBean.getRollMinusAmplitude());
            TwistView twistView = this.f5128m;
            if (twistView != null) {
                twistView.setDurationAnimation(coolRollViewBean.getRollTime());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(long j2) {
        this.f5125j = j2;
    }

    public void a(double d3) {
        this.f5126k = d3;
    }

    public void a(a aVar) {
        this.f5131t = aVar;
    }
}
