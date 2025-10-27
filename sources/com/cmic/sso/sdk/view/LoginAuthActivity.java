package com.cmic.sso.sdk.view;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cmic.sso.sdk.view.f;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.l.n;
import com.mobile.auth.l.q;
import com.yikaobang.yixue.R2;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class LoginAuthActivity extends Activity implements View.OnClickListener {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f6424a = "LoginAuthActivity";
    private com.cmic.sso.sdk.view.a A;
    private int B;
    private int C;
    private boolean D;
    private Dialog E;

    /* renamed from: b, reason: collision with root package name */
    private Handler f6425b;

    /* renamed from: c, reason: collision with root package name */
    private Context f6426c;

    /* renamed from: d, reason: collision with root package name */
    private RelativeLayout f6427d;

    /* renamed from: e, reason: collision with root package name */
    private h f6428e;

    /* renamed from: f, reason: collision with root package name */
    private h f6429f;

    /* renamed from: g, reason: collision with root package name */
    private h f6430g;

    /* renamed from: h, reason: collision with root package name */
    private h f6431h;

    /* renamed from: i, reason: collision with root package name */
    private h f6432i;

    /* renamed from: j, reason: collision with root package name */
    private ArrayList<h> f6433j;

    /* renamed from: k, reason: collision with root package name */
    private ArrayList<String> f6434k;

    /* renamed from: l, reason: collision with root package name */
    private String[] f6435l;

    /* renamed from: m, reason: collision with root package name */
    private com.cmic.sso.sdk.a f6436m;

    /* renamed from: n, reason: collision with root package name */
    private com.mobile.auth.e.c f6437n;

    /* renamed from: p, reason: collision with root package name */
    private CheckBox f6439p;

    /* renamed from: q, reason: collision with root package name */
    private RelativeLayout f6440q;

    /* renamed from: r, reason: collision with root package name */
    private RelativeLayout f6441r;

    /* renamed from: v, reason: collision with root package name */
    private com.mobile.auth.e.b f6445v;

    /* renamed from: x, reason: collision with root package name */
    private RelativeLayout f6447x;

    /* renamed from: y, reason: collision with root package name */
    private String f6448y;

    /* renamed from: z, reason: collision with root package name */
    private String f6449z;

    /* renamed from: o, reason: collision with root package name */
    private String f6438o = "";

    /* renamed from: s, reason: collision with root package name */
    private long f6442s = 0;

    /* renamed from: t, reason: collision with root package name */
    private int f6443t = 0;

    /* renamed from: u, reason: collision with root package name */
    private a f6444u = null;

    /* renamed from: w, reason: collision with root package name */
    private boolean f6446w = true;

    public static class a extends Handler {

        /* renamed from: a, reason: collision with root package name */
        WeakReference<LoginAuthActivity> f6458a;

        public a(LoginAuthActivity loginAuthActivity) {
            this.f6458a = new WeakReference<>(loginAuthActivity);
        }

        private void a(Message message) {
            LoginAuthActivity loginAuthActivity = this.f6458a.get();
            if (loginAuthActivity == null || message.what != 1) {
                return;
            }
            loginAuthActivity.c();
            loginAuthActivity.k();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                a(message);
            } catch (Exception e2) {
                com.cmic.sso.sdk.d.c.f6417b.add(e2);
                e2.printStackTrace();
            }
        }
    }

    public static class b extends n.a {

        /* renamed from: a, reason: collision with root package name */
        WeakReference<LoginAuthActivity> f6459a;

        /* renamed from: b, reason: collision with root package name */
        WeakReference<c> f6460b;

        public b(LoginAuthActivity loginAuthActivity, c cVar) {
            this.f6459a = new WeakReference<>(loginAuthActivity);
            this.f6460b = new WeakReference<>(cVar);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean b() {
            c cVar = this.f6460b.get();
            if (this.f6459a.get() == null || cVar == null) {
                return false;
            }
            return cVar.a(false);
        }

        @Override // com.mobile.auth.l.n.a
        public void a() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
            final LoginAuthActivity loginAuthActivity = this.f6459a.get();
            loginAuthActivity.f6436m.a("logintype", 1);
            com.mobile.auth.l.h.a(true, false);
            loginAuthActivity.f6437n.b(loginAuthActivity.f6436m, new com.mobile.auth.e.d() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.b.1
                @Override // com.mobile.auth.e.d
                public void a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) throws InterruptedException {
                    if (b.this.b()) {
                        long jB = aVar.b("loginTime", 0L);
                        String strB = aVar.b("phonescrip");
                        if (jB != 0) {
                            aVar.a("loginTime", System.currentTimeMillis() - jB);
                        }
                        if (!"103000".equals(str) || TextUtils.isEmpty(strB)) {
                            loginAuthActivity.f6446w = false;
                            com.cmic.sso.sdk.d.a.a("authClickFailed");
                        } else {
                            com.cmic.sso.sdk.d.a.a("authClickSuccess");
                            loginAuthActivity.f6446w = true;
                        }
                        loginAuthActivity.a(str, str2, aVar, jSONObject);
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        loginAuthActivity.f6444u.sendEmptyMessage(1);
                    }
                }
            });
        }
    }

    public class c implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private com.cmic.sso.sdk.a f6464b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f6465c;

        public c(com.cmic.sso.sdk.a aVar) {
            this.f6464b = aVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized boolean a(boolean z2) {
            boolean z3;
            z3 = this.f6465c;
            this.f6465c = z2;
            return !z3;
        }

        @Override // java.lang.Runnable
        public void run() throws JSONException {
            if (a(true)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("resultCode", "102507");
                    jSONObject.put("resultString", "请求超时");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                LoginAuthActivity.this.f6446w = false;
                com.cmic.sso.sdk.d.a.a("authClickFailed");
                LoginAuthActivity.this.f6444u.sendEmptyMessage(1);
                long jB = this.f6464b.b("loginTime", 0L);
                if (jB != 0) {
                    this.f6464b.a("loginTime", System.currentTimeMillis() - jB);
                }
                LoginAuthActivity.this.a("102507", "请求超时", this.f6464b, jSONObject);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, com.cmic.sso.sdk.a aVar, JSONObject jSONObject) {
        com.mobile.auth.e.a aVarA;
        try {
            this.f6425b.removeCallbacksAndMessages(null);
            if ("103000".equals(str)) {
                if (com.mobile.auth.e.a.a(this) == null || com.mobile.auth.l.e.c(aVar.b("traceId")) == null) {
                    return;
                }
                aVar.a("keepListener", true);
                aVarA = com.mobile.auth.e.a.a(this);
            } else {
                if ("200020".equals(str)) {
                    if (com.mobile.auth.e.a.a(this) != null) {
                        if (com.mobile.auth.l.e.c(aVar.b("traceId")) != null) {
                            com.mobile.auth.e.a.a(this).a(str, str2, aVar, jSONObject);
                        }
                        a();
                        return;
                    }
                    return;
                }
                aVar.a("keepListener", true);
                aVarA = com.mobile.auth.e.a.a(this);
            }
            aVarA.a(str, str2, aVar, jSONObject);
        } catch (Exception e2) {
            com.mobile.auth.l.c.a(f6424a, "CallbackResult:未知错误");
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) {
        try {
            com.cmic.sso.sdk.d.a.a("authPageOut");
            a("200020", "登录页面关闭", this.f6436m, null);
        } catch (Exception e2) {
            com.cmic.sso.sdk.d.c.f6417b.add(e2);
            e2.printStackTrace();
        }
    }

    private void d() {
        String str;
        com.cmic.sso.sdk.a aVarD = com.mobile.auth.l.e.d(getIntent().getStringExtra("traceId"));
        this.f6436m = aVarD;
        if (aVarD == null) {
            this.f6436m = new com.cmic.sso.sdk.a(0);
        }
        this.f6445v = com.mobile.auth.l.e.c(this.f6436m.b("traceId", ""));
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        this.f6425b = new Handler(getMainLooper());
        this.f6444u = new a(this);
        this.f6438o = this.f6436m.b("securityphone");
        String str2 = f6424a;
        com.mobile.auth.l.c.b(str2, "mSecurityPhone value is " + this.f6438o);
        String strB = this.f6436m.b("operatortype", "");
        com.mobile.auth.l.c.b(str2, "operator value is " + strB);
        if (this.A.ap() == 1) {
            this.f6435l = com.cmic.sso.sdk.c.f6387b;
        } else if (this.A.ap() == 2) {
            this.f6435l = com.cmic.sso.sdk.c.f6388c;
        } else {
            this.f6435l = com.cmic.sso.sdk.c.f6386a;
        }
        if (strB.equals("1")) {
            this.f6448y = this.f6435l[0];
            str = "http://wap.cmpassport.com/resources/html/contract.html";
        } else if (strB.equals("3")) {
            this.f6448y = this.f6435l[1];
            str = Constant.CTCC_PROTOCOL_URL;
        } else {
            this.f6448y = this.f6435l[2];
            str = Constant.CUCC_PROTOCOL_URL;
        }
        h hVar = new h(this.f6426c, R.style.Theme.Translucent.NoTitleBar, this.f6448y, str);
        this.f6428e = hVar;
        hVar.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.1
            @Override // android.content.DialogInterface.OnKeyListener
            public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                if (i2 == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                    LoginAuthActivity.this.f6428e.b();
                }
                return true;
            }
        });
        this.f6433j = new ArrayList<>();
        this.f6434k = new ArrayList<>();
        if (!TextUtils.isEmpty(this.A.N())) {
            h hVar2 = new h(this.f6426c, R.style.Theme.Translucent.NoTitleBar, this.A.M(), this.A.N());
            this.f6429f = hVar2;
            hVar2.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.2
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                    if (i2 == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.f6429f.b();
                    }
                    return true;
                }
            });
            this.f6433j.add(this.f6429f);
            this.f6434k.add(this.A.M());
        }
        if (!TextUtils.isEmpty(this.A.P())) {
            h hVar3 = new h(this.f6426c, R.style.Theme.Translucent.NoTitleBar, this.A.O(), this.A.P());
            this.f6430g = hVar3;
            hVar3.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.3
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                    if (i2 == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.f6430g.b();
                    }
                    return true;
                }
            });
            this.f6433j.add(this.f6430g);
            this.f6434k.add(this.A.O());
        }
        if (!TextUtils.isEmpty(this.A.R())) {
            h hVar4 = new h(this.f6426c, R.style.Theme.Translucent.NoTitleBar, this.A.Q(), this.A.R());
            this.f6431h = hVar4;
            hVar4.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.4
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                    if (i2 == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.f6431h.b();
                    }
                    return true;
                }
            });
            this.f6433j.add(this.f6431h);
            this.f6434k.add(this.A.Q());
        }
        if (!TextUtils.isEmpty(this.A.T())) {
            h hVar5 = new h(this.f6426c, R.style.Theme.Translucent.NoTitleBar, this.A.S(), this.A.T());
            this.f6432i = hVar5;
            hVar5.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.5
                @Override // android.content.DialogInterface.OnKeyListener
                public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                    if (i2 == 4 && keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        LoginAuthActivity.this.f6432i.b();
                    }
                    return true;
                }
            });
            this.f6433j.add(this.f6432i);
            this.f6434k.add(this.A.S());
        }
        j();
        if (this.A.ad()) {
            for (int i2 = 0; i2 < this.f6434k.size(); i2++) {
                String str3 = String.format("《%s》", this.f6434k.get(i2));
                this.f6449z = this.f6449z.replaceFirst(this.f6434k.get(i2), str3);
                this.f6434k.set(i2, str3);
            }
        }
        f.a().a(new f.a() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.6
            @Override // com.cmic.sso.sdk.view.f.a
            public void a() {
                LoginAuthActivity.this.f6425b.removeCallbacksAndMessages(null);
                if (LoginAuthActivity.this.f6428e != null && LoginAuthActivity.this.f6428e.isShowing()) {
                    LoginAuthActivity.this.f6428e.dismiss();
                }
                if (LoginAuthActivity.this.f6429f != null && LoginAuthActivity.this.f6429f.isShowing()) {
                    LoginAuthActivity.this.f6429f.dismiss();
                }
                LoginAuthActivity.this.a(true);
            }
        });
    }

    private void e() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.f6441r.getLayoutParams();
        if (this.A.p() > 0 || this.A.q() < 0) {
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            this.f6441r.measure(iMakeMeasureSpec, iMakeMeasureSpec);
            String str = f6424a;
            com.mobile.auth.l.c.b(str, "mPhoneLayout.getMeasuredHeight()=" + this.f6441r.getMeasuredHeight());
            if (this.A.p() <= 0 || (this.B - this.f6441r.getMeasuredHeight()) - i.a(this.f6426c, this.A.p()) <= 0) {
                layoutParams.addRule(12, -1);
            } else {
                com.mobile.auth.l.c.b(str, "numberField_top");
                layoutParams.addRule(10, -1);
                layoutParams.setMargins(0, i.a(this.f6426c, this.A.p()), 0, 0);
            }
        } else if (this.A.q() <= 0 || (this.B - this.f6441r.getMeasuredHeight()) - i.a(this.f6426c, this.A.q()) <= 0) {
            layoutParams.addRule(10, -1);
        } else {
            com.mobile.auth.l.c.b(f6424a, "numberField_bottom");
            layoutParams.addRule(12, -1);
            layoutParams.setMargins(0, 0, 0, i.a(this.f6426c, this.A.q()));
        }
        this.f6441r.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.f6427d.getLayoutParams();
        int iMax = Math.max(this.A.y(), 0);
        int iMax2 = Math.max(this.A.z(), 0);
        if (this.A.A() > 0 || this.A.B() < 0) {
            if (this.A.A() <= 0 || this.B - i.a(this.f6426c, this.A.x() + this.A.A()) <= 0) {
                layoutParams2.addRule(12, -1);
                layoutParams2.setMargins(i.a(this.f6426c, iMax), 0, i.a(this.f6426c, iMax2), 0);
            } else {
                com.mobile.auth.l.c.b(f6424a, "logBtn_top");
                layoutParams2.addRule(10, -1);
                layoutParams2.setMargins(i.a(this.f6426c, iMax), i.a(this.f6426c, this.A.A()), i.a(this.f6426c, iMax2), 0);
            }
        } else if (this.A.B() <= 0 || this.B - i.a(this.f6426c, this.A.x() + this.A.B()) <= 0) {
            layoutParams2.addRule(10, -1);
            layoutParams2.setMargins(i.a(this.f6426c, iMax), 0, i.a(this.f6426c, iMax2), 0);
        } else {
            com.mobile.auth.l.c.b(f6424a, "logBtn_bottom");
            layoutParams2.addRule(12, -1);
            layoutParams2.setMargins(i.a(this.f6426c, iMax), 0, i.a(this.f6426c, iMax2), i.a(this.f6426c, this.A.B()));
        }
        this.f6427d.setLayoutParams(layoutParams2);
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.f6440q.getLayoutParams();
        int iZ = this.A.Z() >= 0 ? this.A.I() > 30 ? this.A.Z() : this.A.Z() - (30 - this.A.I()) : this.A.I() > 30 ? 0 : -(30 - this.A.I());
        int iMax3 = Math.max(this.A.aa(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.f6440q.measure(iMakeMeasureSpec2, iMakeMeasureSpec2);
        if (this.A.ab() > 0 || this.A.ac() < 0) {
            if (this.A.ab() <= 0 || (this.B - this.f6440q.getMeasuredHeight()) - i.a(this.f6426c, this.A.ab()) <= 0) {
                com.mobile.auth.l.c.b(f6424a, "privacy_bottom=" + iZ);
                layoutParams3.addRule(12, -1);
                layoutParams3.setMargins(i.a(this.f6426c, (float) iZ), 0, i.a(this.f6426c, (float) iMax3), 0);
            } else {
                com.mobile.auth.l.c.b(f6424a, "privacy_top = " + this.f6440q.getMeasuredHeight());
                layoutParams3.addRule(10, -1);
                layoutParams3.setMargins(i.a(this.f6426c, (float) iZ), i.a(this.f6426c, (float) this.A.ab()), i.a(this.f6426c, (float) iMax3), 0);
            }
        } else if (this.A.ac() <= 0 || (this.B - this.f6440q.getMeasuredHeight()) - i.a(this.f6426c, this.A.ac()) <= 0) {
            layoutParams3.addRule(10, -1);
            layoutParams3.setMargins(i.a(this.f6426c, iZ), 0, i.a(this.f6426c, iMax3), 0);
            com.mobile.auth.l.c.b(f6424a, "privacy_top");
        } else {
            com.mobile.auth.l.c.b(f6424a, "privacy_bottom=" + this.f6440q.getMeasuredHeight());
            layoutParams3.addRule(12, -1);
            layoutParams3.setMargins(i.a(this.f6426c, (float) iZ), 0, i.a(this.f6426c, (float) iMax3), i.a(this.f6426c, (float) this.A.ac()));
        }
        this.f6440q.setLayoutParams(layoutParams3);
    }

    private void f() {
        getWindow().addFlags(67108864);
        getWindow().addFlags(134217728);
        if (this.A.a() != 0) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().clearFlags(67108864);
            getWindow().setStatusBarColor(this.A.a());
            getWindow().setNavigationBarColor(this.A.a());
        }
        if (this.A.b()) {
            getWindow().getDecorView().setSystemUiVisibility(8192);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(0);
        }
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        View viewC = this.A.c();
        if (viewC != null) {
            ViewParent parent = viewC.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(viewC);
            }
            relativeLayout.addView(viewC);
        } else if (this.A.d() != -1) {
            getLayoutInflater().inflate(this.A.d(), relativeLayout);
        }
        setContentView(relativeLayout);
        int requestedOrientation = getRequestedOrientation();
        this.B = i.b(this.f6426c);
        int iA = i.a(this.f6426c);
        this.C = iA;
        boolean z2 = true;
        if ((requestedOrientation == 1 && iA > this.B) || (requestedOrientation == 0 && iA < this.B)) {
            this.C = this.B;
            this.B = iA;
        }
        com.mobile.auth.l.c.b(f6424a, "orientation = " + requestedOrientation + "--screenWidth = " + this.C + "--screenHeight = " + this.B);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (this.A.aj() != 0) {
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            getWindowManager().getDefaultDisplay().getSize(new Point());
            attributes.width = i.a(this.f6426c, this.A.aj());
            int iA2 = i.a(this.f6426c, this.A.ak());
            attributes.height = iA2;
            this.C = attributes.width;
            this.B = iA2;
            attributes.x = i.a(this.f6426c, this.A.al());
            if (this.A.an() == 1) {
                getWindow().setGravity(80);
            } else {
                attributes.y = i.a(this.f6426c, this.A.am());
            }
            getWindow().setAttributes(attributes);
        }
        relativeLayout.setFitsSystemWindows(this.A.aq());
        relativeLayout.setClipToPadding(true);
        try {
            g();
            relativeLayout.addView(this.f6441r);
            relativeLayout.addView(h());
            relativeLayout.addView(i());
            e();
            this.f6427d.setOnClickListener(this);
            this.f6447x.setOnClickListener(this);
            this.f6439p.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.7
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                    CheckBox checkBox;
                    LoginAuthActivity loginAuthActivity;
                    String str;
                    boolean z4 = true;
                    if (z3) {
                        LoginAuthActivity.this.f6427d.setEnabled(true);
                        try {
                            CheckBox checkBox2 = LoginAuthActivity.this.f6439p;
                            LoginAuthActivity loginAuthActivity2 = LoginAuthActivity.this;
                            checkBox2.setBackgroundResource(g.b(loginAuthActivity2, loginAuthActivity2.A.G()));
                            return;
                        } catch (Exception unused) {
                            checkBox = LoginAuthActivity.this.f6439p;
                            loginAuthActivity = LoginAuthActivity.this;
                            str = "umcsdk_check_image";
                        }
                    } else {
                        RelativeLayout relativeLayout2 = LoginAuthActivity.this.f6427d;
                        if (LoginAuthActivity.this.A.F() == null && TextUtils.isEmpty(LoginAuthActivity.this.A.C())) {
                            z4 = false;
                        }
                        relativeLayout2.setEnabled(z4);
                        try {
                            CheckBox checkBox3 = LoginAuthActivity.this.f6439p;
                            LoginAuthActivity loginAuthActivity3 = LoginAuthActivity.this;
                            checkBox3.setBackgroundResource(g.b(loginAuthActivity3, loginAuthActivity3.A.H()));
                            return;
                        } catch (Exception unused2) {
                            checkBox = LoginAuthActivity.this.f6439p;
                            loginAuthActivity = LoginAuthActivity.this;
                            str = "umcsdk_uncheck_image";
                        }
                    }
                    checkBox.setBackgroundResource(g.b(loginAuthActivity, str));
                }
            });
            k();
            try {
                if (this.A.K()) {
                    this.f6439p.setChecked(true);
                    this.f6439p.setBackgroundResource(g.b(this, this.A.G()));
                    this.f6427d.setEnabled(true);
                    return;
                }
                this.f6439p.setChecked(false);
                RelativeLayout relativeLayout2 = this.f6427d;
                if (this.A.F() == null && TextUtils.isEmpty(this.A.C())) {
                    z2 = false;
                }
                relativeLayout2.setEnabled(z2);
                this.f6439p.setBackgroundResource(g.b(this, this.A.H()));
            } catch (Exception unused) {
                this.f6439p.setChecked(false);
            }
        } catch (Exception e2) {
            com.cmic.sso.sdk.d.c.f6417b.add(e2);
            e2.printStackTrace();
            com.mobile.auth.l.c.a(f6424a, e2.toString());
            a("200040", "UI资源加载异常", this.f6436m, null);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|2|(1:4)(11:(2:7|(1:9)(1:10))|11|24|12|15|(1:17)|18|26|19|22|23)|5|11|24|12|15|(0)|18|26|19|22|23) */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0069, code lost:
    
        r0.setTextSize(2, 18.0f);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0094, code lost:
    
        r0.setTextColor(-13421773);
     */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void g() {
        /*
            r6 = this;
            android.widget.RelativeLayout r0 = new android.widget.RelativeLayout
            r0.<init>(r6)
            r6.f6441r = r0
            r1 = 13107(0x3333, float:1.8367E-41)
            r0.setId(r1)
            android.widget.RelativeLayout$LayoutParams r0 = new android.widget.RelativeLayout$LayoutParams
            r1 = -1
            r2 = -2
            r0.<init>(r1, r2)
            android.widget.RelativeLayout r1 = r6.f6441r
            r1.setLayoutParams(r0)
            android.widget.TextView r0 = new android.widget.TextView
            r0.<init>(r6)
            android.widget.RelativeLayout$LayoutParams r1 = new android.widget.RelativeLayout$LayoutParams
            r1.<init>(r2, r2)
            r2 = 15
            r0.setGravity(r2)
            com.cmic.sso.sdk.view.a r2 = r6.A
            int r2 = r2.o()
            r3 = 0
            if (r2 != 0) goto L36
            r2 = 13
        L32:
            r1.addRule(r2)
            goto L5d
        L36:
            if (r2 <= 0) goto L5d
            int r4 = r6.C
            int r5 = r0.getWidth()
            int r4 = r4 - r5
            android.content.Context r5 = r6.f6426c
            float r2 = (float) r2
            int r5 = com.cmic.sso.sdk.view.i.a(r5, r2)
            int r4 = r4 - r5
            if (r4 <= 0) goto L53
            android.content.Context r4 = r6.f6426c
            int r2 = com.cmic.sso.sdk.view.i.a(r4, r2)
            r1.setMargins(r2, r3, r3, r3)
            goto L5d
        L53:
            java.lang.String r2 = com.cmic.sso.sdk.view.LoginAuthActivity.f6424a
            java.lang.String r4 = "RelativeLayout.ALIGN_PARENT_RIGHT"
            com.mobile.auth.l.c.b(r2, r4)
            r2 = 11
            goto L32
        L5d:
            r2 = 2
            com.cmic.sso.sdk.view.a r4 = r6.A     // Catch: java.lang.Exception -> L69
            int r4 = r4.l()     // Catch: java.lang.Exception -> L69
            float r4 = (float) r4     // Catch: java.lang.Exception -> L69
            r0.setTextSize(r2, r4)     // Catch: java.lang.Exception -> L69
            goto L6e
        L69:
            r4 = 1099956224(0x41900000, float:18.0)
            r0.setTextSize(r2, r4)
        L6e:
            java.lang.String r2 = r6.f6438o
            r0.setText(r2)
            com.cmic.sso.sdk.view.a r2 = r6.A
            boolean r2 = r2.m()
            if (r2 == 0) goto L80
            android.graphics.Typeface r2 = android.graphics.Typeface.DEFAULT_BOLD
            r0.setTypeface(r2)
        L80:
            r2 = 30583(0x7777, float:4.2856E-41)
            r0.setId(r2)
            android.widget.RelativeLayout r2 = r6.f6441r
            r2.addView(r0, r1)
            com.cmic.sso.sdk.view.a r1 = r6.A     // Catch: java.lang.Exception -> L94
            int r1 = r1.n()     // Catch: java.lang.Exception -> L94
            r0.setTextColor(r1)     // Catch: java.lang.Exception -> L94
            goto L9a
        L94:
            r1 = -13421773(0xffffffffff333333, float:-2.3819765E38)
            r0.setTextColor(r1)
        L9a:
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r3)
            android.widget.RelativeLayout r1 = r6.f6441r
            r1.measure(r0, r0)
            java.lang.String r0 = com.cmic.sso.sdk.view.LoginAuthActivity.f6424a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "mPhoneLayout.getMeasuredHeight()="
            r1.append(r2)
            android.widget.RelativeLayout r2 = r6.f6441r
            int r2 = r2.getMeasuredHeight()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.mobile.auth.l.c.b(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cmic.sso.sdk.view.LoginAuthActivity.g():void");
    }

    private RelativeLayout h() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        this.f6427d = relativeLayout;
        relativeLayout.setId(R2.id.plvlc_live_control_more_latency_tv);
        this.f6427d.setLayoutParams(new RelativeLayout.LayoutParams(i.a(this.f6426c, this.A.w()), i.a(this.f6426c, this.A.x())));
        TextView textView = new TextView(this);
        textView.setTextSize(2, this.A.s());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        textView.setLayoutParams(layoutParams);
        if (this.A.t()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        this.f6427d.addView(textView);
        textView.setText(this.A.r());
        try {
            textView.setTextColor(this.A.u());
        } catch (Exception unused) {
            textView.setTextColor(-1);
        }
        try {
            this.f6427d.setBackgroundResource(g.b(this.f6426c, this.A.v()));
        } catch (Exception e2) {
            e2.printStackTrace();
            this.f6427d.setBackgroundResource(g.b(this.f6426c, "umcsdk_login_btn_bg"));
        }
        return this.f6427d;
    }

    private RelativeLayout i() {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        this.f6440q = relativeLayout;
        relativeLayout.setHorizontalGravity(1);
        this.f6440q.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        int I = this.A.I();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i.a(this.f6426c, Math.max(I, 30)), i.a(this.f6426c, Math.max(this.A.J(), 30)));
        if (this.A.ae() == 1) {
            layoutParams.addRule(15, -1);
        }
        RelativeLayout relativeLayout2 = new RelativeLayout(this);
        this.f6447x = relativeLayout2;
        relativeLayout2.setId(34952);
        this.f6447x.setLayoutParams(layoutParams);
        CheckBox checkBox = new CheckBox(this);
        this.f6439p = checkBox;
        checkBox.setChecked(false);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i.a(this.f6426c, this.A.I()), i.a(this.f6426c, this.A.J()));
        layoutParams2.setMargins(i.a(this.f6426c, I > 30 ? 0.0f : 30 - I), 0, 0, 0);
        layoutParams2.addRule(11, -1);
        if (this.A.ae() == 1) {
            layoutParams2.addRule(15, -1);
        }
        this.f6439p.setLayoutParams(layoutParams2);
        this.f6447x.addView(this.f6439p);
        this.f6440q.addView(this.f6447x);
        TextView textView = new TextView(this);
        textView.setTextSize(2, this.A.U());
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.setMargins(i.a(this.f6426c, 5.0f), 0, 0, i.a(this.f6426c, 5.0f));
        layoutParams3.addRule(1, 34952);
        textView.setLayoutParams(layoutParams3);
        this.f6440q.addView(textView);
        textView.setTextColor(this.A.W());
        textView.setText(i.a(this, this.f6449z, this.f6448y, this.f6428e, this.f6433j, this.f6434k));
        textView.setLineSpacing(8.0f, 1.0f);
        textView.setIncludeFontPadding(false);
        if (this.A.V()) {
            textView.setTypeface(Typeface.DEFAULT_BOLD);
        }
        if (this.A.Y()) {
            textView.setGravity(17);
        }
        textView.setHighlightColor(getResources().getColor(R.color.transparent));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        this.f6439p.setButtonDrawable(new ColorDrawable());
        try {
            this.f6439p.setBackgroundResource(g.b(this, this.A.H()));
        } catch (Exception unused) {
            this.f6439p.setBackgroundResource(g.b(this, "umcsdk_uncheck_image"));
        }
        return this.f6440q;
    }

    private String j() {
        this.f6449z = this.A.L();
        if (this.A.ad()) {
            this.f6448y = String.format("《%s》", this.f6448y);
        }
        if (this.f6449z.contains("$$运营商条款$$")) {
            this.f6449z = this.f6449z.replace("$$运营商条款$$", this.f6448y);
        }
        return this.f6449z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        this.f6427d.setClickable(true);
        this.f6439p.setClickable(true);
    }

    private void l() {
        this.f6427d.setClickable(false);
        this.f6439p.setClickable(false);
    }

    private void m() {
        try {
            if (this.f6443t >= 5) {
                Toast.makeText(this.f6426c, "网络不稳定,请返回重试其他登录方式", 1).show();
                this.f6427d.setClickable(true);
                return;
            }
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : stackTrace) {
                com.mobile.auth.l.c.a("stack", stackTraceElement.getClassName());
                String className = stackTraceElement.getClassName();
                if (!TextUtils.isEmpty(className) && className.contains("com.cmic.sso.sdk.activity") && !sb.toString().contains(className)) {
                    sb.append(className);
                    sb.append(com.alipay.sdk.util.h.f3376b);
                }
            }
            this.f6436m.a("loginTime", System.currentTimeMillis());
            String strB = this.f6436m.b("traceId", "");
            if (!TextUtils.isEmpty(strB) && com.mobile.auth.l.e.a(strB)) {
                String strC = q.c();
                this.f6436m.a("traceId", strC);
                com.mobile.auth.l.e.a(strC, this.f6445v);
            }
            b();
            l();
            c cVar = new c(this.f6436m);
            this.f6425b.postDelayed(cVar, com.mobile.auth.e.a.a(this).c());
            n.a(new b(this, cVar));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a() {
        this.f6425b.removeCallbacksAndMessages(null);
        h hVar = this.f6428e;
        if (hVar != null && hVar.isShowing()) {
            this.f6428e.dismiss();
        }
        h hVar2 = this.f6429f;
        if (hVar2 != null && hVar2.isShowing()) {
            this.f6429f.dismiss();
        }
        c();
        this.E = null;
        this.f6440q.clearAnimation();
        finish();
        if (this.A.ah() == null || this.A.ai() == null) {
            return;
        }
        overridePendingTransition(g.c(this, this.A.ai()), g.c(this, this.A.ah()));
    }

    public void b() {
        com.mobile.auth.l.c.a(f6424a, "loginClickStart");
        try {
            this.D = true;
            if (this.A.E() != null) {
                this.A.E().a(this.f6426c, null);
            } else {
                Dialog dialog = this.E;
                if (dialog != null) {
                    dialog.show();
                    return;
                }
                AlertDialog alertDialogCreate = new AlertDialog.Builder(this).create();
                this.E = alertDialogCreate;
                alertDialogCreate.setCancelable(false);
                this.E.setCanceledOnTouchOutside(false);
                this.E.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.cmic.sso.sdk.view.LoginAuthActivity.8
                    @Override // android.content.DialogInterface.OnKeyListener
                    public boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
                        return i2 == 4;
                    }
                });
                RelativeLayout relativeLayout = new RelativeLayout(this.E.getContext());
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
                ImageView imageView = new ImageView(this.E.getContext());
                imageView.setImageResource(g.b(this.f6426c, "dialog_loading"));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(80, 80);
                layoutParams.addRule(13, -1);
                relativeLayout.addView(imageView, layoutParams);
                if (this.E.getWindow() != null) {
                    this.E.getWindow().setDimAmount(0.0f);
                }
                this.E.show();
                this.E.setContentView(relativeLayout);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        com.mobile.auth.l.c.a(f6424a, "loginClickStart");
    }

    public void c() {
        try {
            com.mobile.auth.l.c.a(f6424a, "loginClickComplete");
            if (this.A.E() == null || !this.D) {
                Dialog dialog = this.E;
                if (dialog != null && dialog.isShowing()) {
                    this.E.dismiss();
                }
            } else {
                this.D = false;
                this.A.E().b(this.f6426c, null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws Resources.NotFoundException {
        try {
            int id = view.getId();
            if (id != 17476) {
                if (id == 26214) {
                    a(false);
                    return;
                } else {
                    if (id != 34952) {
                        return;
                    }
                    if (this.f6439p.isChecked()) {
                        this.f6439p.setChecked(false);
                        return;
                    } else {
                        this.f6439p.setChecked(true);
                        return;
                    }
                }
            }
            if (!this.f6439p.isChecked()) {
                if (this.A.as() != null) {
                    Context context = this.f6426c;
                    this.f6440q.startAnimation(AnimationUtils.loadAnimation(context, g.c(context, this.A.as())));
                }
                if (this.A.F() != null) {
                    this.A.F().a(this.f6426c, null);
                    return;
                } else if (!TextUtils.isEmpty(this.A.C())) {
                    Toast.makeText(this.f6426c, this.A.C(), 1).show();
                    return;
                }
            }
            this.f6443t++;
            m();
        } catch (Exception e2) {
            com.cmic.sso.sdk.d.c.f6417b.add(e2);
            e2.printStackTrace();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            if (bundle != null) {
                finish();
            }
            this.f6426c = this;
            com.cmic.sso.sdk.view.a aVarA = com.mobile.auth.e.a.a(this).a();
            this.A = aVarA;
            if (aVarA != null) {
                if (aVarA.ao() != -1) {
                    setTheme(this.A.ao());
                }
                if (this.A.af() != null && this.A.ag() != null) {
                    overridePendingTransition(g.c(this, this.A.af()), g.c(this, this.A.ag()));
                }
            }
            com.cmic.sso.sdk.d.a.a("authPageIn");
            this.f6442s = System.currentTimeMillis();
            this.f6437n = com.mobile.auth.e.c.a(this);
            d();
            f();
        } catch (Exception e2) {
            this.f6436m.a().f6391a.add(e2);
            com.mobile.auth.l.c.a(f6424a, e2.toString());
            e2.printStackTrace();
            a("200025", "发生未知错误", this.f6436m, null);
        }
    }

    @Override // android.app.Activity
    public void onDestroy() {
        try {
            this.f6425b.removeCallbacksAndMessages(null);
            com.cmic.sso.sdk.d.a.a("timeOnAuthPage", (System.currentTimeMillis() - this.f6442s) + "");
            com.cmic.sso.sdk.d.a.a("authPrivacyState", this.f6439p.isChecked() ? "1" : "0");
            this.E = null;
            f.a().c();
            this.f6444u.removeCallbacksAndMessages(null);
        } catch (Exception e2) {
            com.mobile.auth.l.c.a(f6424a, "LoginAuthActivity clear failed");
            com.cmic.sso.sdk.d.c.f6417b.add(e2);
            e2.printStackTrace();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        if (i2 != 4 || keyEvent.isCanceled() || keyEvent.getRepeatCount() != 0) {
            return true;
        }
        if (this.A.D() != null) {
            this.A.D().a();
        }
        if (this.A.aj() != 0 && !this.A.ar()) {
            return true;
        }
        a(false);
        return true;
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        try {
            com.cmic.sso.sdk.a aVar = this.f6436m;
            if (aVar != null) {
                aVar.a("loginMethod", "loginAuth");
            }
            com.mobile.auth.e.a.a(this).a("200087", (JSONObject) null);
        } catch (Exception e2) {
            this.f6436m.a().f6391a.add(e2);
            a("200025", "发生未知错误", this.f6436m, null);
        }
    }
}
