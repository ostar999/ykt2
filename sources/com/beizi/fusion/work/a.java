package com.beizi.fusion.work;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import com.beizi.fusion.NativeUnifiedAdResponse;
import com.beizi.fusion.b.b;
import com.beizi.fusion.b.d;
import com.beizi.fusion.d.c;
import com.beizi.fusion.d.e;
import com.beizi.fusion.d.h;
import com.beizi.fusion.d.v;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.EventItem;
import com.yikaobang.yixue.R2;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public abstract class a extends Observable implements c {

    /* renamed from: a, reason: collision with root package name */
    protected d f5537a;

    /* renamed from: b, reason: collision with root package name */
    protected b f5538b;

    /* renamed from: c, reason: collision with root package name */
    protected int f5539c;

    /* renamed from: d, reason: collision with root package name */
    protected e f5540d;

    /* renamed from: e, reason: collision with root package name */
    protected AdSpacesBean.BuyerBean f5541e;

    /* renamed from: f, reason: collision with root package name */
    protected AdSpacesBean.ForwardBean f5542f;

    /* renamed from: h, reason: collision with root package name */
    protected String f5544h;

    /* renamed from: i, reason: collision with root package name */
    protected String f5545i;

    /* renamed from: g, reason: collision with root package name */
    protected h f5543g = null;

    /* renamed from: j, reason: collision with root package name */
    protected com.beizi.fusion.f.a f5546j = com.beizi.fusion.f.a.ADDEFAULT;

    /* renamed from: n, reason: collision with root package name */
    private int f5550n = 0;

    /* renamed from: o, reason: collision with root package name */
    private int f5551o = 0;

    /* renamed from: p, reason: collision with root package name */
    private int f5552p = 0;

    /* renamed from: q, reason: collision with root package name */
    private int f5553q = 0;

    /* renamed from: r, reason: collision with root package name */
    private boolean f5554r = false;

    /* renamed from: s, reason: collision with root package name */
    private long f5555s = 0;

    /* renamed from: t, reason: collision with root package name */
    private boolean f5556t = false;

    /* renamed from: u, reason: collision with root package name */
    private boolean f5557u = false;

    /* renamed from: v, reason: collision with root package name */
    private boolean f5558v = false;

    /* renamed from: w, reason: collision with root package name */
    private boolean f5559w = false;

    /* renamed from: x, reason: collision with root package name */
    private TimerTask f5560x = null;

    /* renamed from: y, reason: collision with root package name */
    private Timer f5561y = null;

    /* renamed from: z, reason: collision with root package name */
    private long f5562z = 0;
    private boolean A = false;
    private String B = "WATERFALL";
    private int C = 0;
    private boolean D = false;
    private int E = 0;
    private boolean F = false;
    private String G = null;

    /* renamed from: k, reason: collision with root package name */
    protected int f5547k = 0;

    /* renamed from: l, reason: collision with root package name */
    protected long f5548l = 0;

    /* renamed from: m, reason: collision with root package name */
    @SuppressLint({"HandlerLeak"})
    protected Handler f5549m = new Handler(Looper.getMainLooper()) { // from class: com.beizi.fusion.work.a.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i2 = message.what;
            if (i2 == 1) {
                e eVar = a.this.f5540d;
                if (eVar == null || eVar.r() >= 1 || a.this.f5540d.q() == 2) {
                    return;
                }
                a.this.p();
                return;
            }
            if (i2 == 2) {
                ac.b("BeiZis", "before handleAdClose");
                a.this.M();
                a.this.ah();
            } else if (i2 == 3 && message.obj != null) {
                a.this.a(message);
                a.this.ao();
                if (a.this.c()) {
                    return;
                }
                a.this.e(message.arg1);
            }
        }
    };
    private boolean H = false;
    private boolean I = false;

    private boolean aL() {
        d dVar = this.f5537a;
        return (dVar == null || dVar.c()) ? false : true;
    }

    private void aM() {
        e eVar;
        if (this.f5558v || (eVar = this.f5540d) == null || eVar.q() == 2 || this.f5546j == com.beizi.fusion.f.a.ADFAIL) {
            return;
        }
        if (aR()) {
            aa();
        } else {
            this.f5540d.b((c) this);
            this.f5540d.b(g());
            Y();
        }
        this.f5558v = true;
    }

    private void aN() {
        Timer timer;
        StringBuilder sb = new StringBuilder();
        sb.append("mAdLifeManager != null ? ");
        sb.append(this.f5540d != null);
        ac.c("BeiZis", sb.toString());
        if (this.f5540d != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("enter cancelExposureTaskIfNeed (System.currentTimeMillis() - mStartLoadTime) = ");
            sb2.append(System.currentTimeMillis() - this.f5562z);
            sb2.append(",mAdLifeManager.getValidExposureTime() = ");
            sb2.append(this.f5540d.u());
            sb2.append(",mExposureTimerTask != null ? ");
            sb2.append(this.f5560x != null);
            sb2.append(",mExposureTimer != null ? ");
            sb2.append(this.f5561y != null);
            ac.c("BeiZis", sb2.toString());
        }
        if (this.f5540d == null || System.currentTimeMillis() - this.f5562z >= this.f5540d.u() || this.f5560x == null || (timer = this.f5561y) == null) {
            return;
        }
        timer.cancel();
        Z();
    }

    private void aO() {
        this.f5560x = new TimerTask() { // from class: com.beizi.fusion.work.a.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                a.this.aj();
            }
        };
        Timer timer = new Timer();
        this.f5561y = timer;
        if (this.f5540d != null) {
            timer.schedule(this.f5560x, r1.u());
            this.A = true;
        }
    }

    private boolean aP() {
        int iU;
        e eVar = this.f5540d;
        return eVar != null && (iU = eVar.u()) >= 0 && iU <= 3000;
    }

    private boolean aQ() {
        e eVar = this.f5540d;
        if (eVar == null) {
            return false;
        }
        Integer[] numArrV = eVar.v();
        return numArrV.length == 2 && numArrV[0].intValue() >= 0 && numArrV[1].intValue() > numArrV[0].intValue() && numArrV[1].intValue() - numArrV[0].intValue() <= 30;
    }

    private boolean aR() {
        e eVar;
        ac.c("BeiZis", "isRandomNoExposureRangeValid = " + aQ());
        if (!aQ() || (eVar = this.f5540d) == null) {
            return false;
        }
        Integer[] numArrV = eVar.v();
        int iRandom = (int) ((Math.random() * 100.0d) + 1.0d);
        ac.c("BeiZis", "random = " + iRandom + ",randomNoExposureRange[0] = " + numArrV[0] + ",randomNoExposureRange[1] = " + numArrV[1]);
        return iRandom >= numArrV[0].intValue() && iRandom <= numArrV[1].intValue();
    }

    private void aS() {
        boolean zW;
        e eVar = this.f5540d;
        if (eVar != null) {
            zW = eVar.w();
            ac.a("BeiZisBid", "mAdLifeControl = " + this.f5540d + ",isAllBidFinish = " + zW);
        } else {
            zW = false;
        }
        e eVar2 = this.f5540d;
        if (eVar2 == null || !zW) {
            return;
        }
        eVar2.a(eVar2.x());
    }

    private boolean aT() {
        StringBuilder sb = new StringBuilder();
        sb.append(g());
        sb.append(" isNormalWorkerWithCache isBidTypeWaterfall() ? ");
        sb.append(az());
        sb.append(",getCache() == 1 ");
        sb.append(i() == 1);
        ac.a("BeiZis", sb.toString());
        return az() && i() == 1;
    }

    public void A() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportInitBegin mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 1);
        }
    }

    public void B() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportInitEnd mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 2);
        }
    }

    public void C() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdRequest mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 3);
        }
    }

    public boolean D() {
        return this.H;
    }

    public void E() {
        if (aL()) {
            if (au() && !"MTG".equalsIgnoreCase(g())) {
                aI();
            }
            if (F()) {
                c(2);
                P();
            }
            G();
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdLoaded mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            if (!b()) {
                this.f5537a.f4809g.a(this.f5539c, 4);
            }
            as();
        }
    }

    public boolean F() {
        return at() && !D();
    }

    public void G() {
    }

    public void H() {
        if (aL()) {
            ap();
            if (F()) {
                Q();
                c(3);
            }
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdLoadFail mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            if (!b()) {
                this.f5537a.f4809g.a(this.f5539c, 11);
            }
            ar();
        }
    }

    public void I() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdShow mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 5);
        }
    }

    public void J() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdExposure mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 6);
        }
    }

    public void K() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdClick mManagerObserver.mChannelResultStatus.getStatus(channel)  = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 7);
        }
    }

    public void L() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdClickCallBack mManagerObserver.mChannelResultStatus.getStatus(channel)  = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 17);
        }
    }

    public void M() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdClose mManagerObserver.mChannelResultStatus.getStatus(channel)  = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 9);
        }
    }

    public void N() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdClickClose mManagerObserver.mChannelResultStatus.getStatus(channel)  = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 8);
        }
    }

    public void O() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportAdRewarded mManagerObserver.mChannelResultStatus.getStatus(channel)  = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 12);
        }
    }

    public void P() {
        if (this.f5537a == null || this.F) {
            return;
        }
        ac.c("BeiZis", "channel " + this.f5539c + " reportParticipateBid mManagerObserver.mBidChannelStatus.getStatus(channel)  = " + this.f5537a.f4813k.b(this.f5539c));
        this.F = true;
    }

    public void Q() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportParticipateBid mManagerObserver.mBidChannelStatus.getStatus(channel)  = " + this.f5537a.f4813k.b(this.f5539c));
        }
    }

    public void R() {
        d dVar = this.f5537a;
        if (dVar != null) {
            dVar.f4810h.a(this.f5539c, 3);
            ac.a("BeiZis", "channel == ---reportComparisonSuccess---" + g());
        }
    }

    public void S() {
    }

    public void T() {
        d dVar = this.f5537a;
        if (dVar != null) {
            dVar.f4810h.a(this.f5539c, 4);
        }
    }

    public void U() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportChannelClickEnhance GrayPass mManagerObserver.mChannelClickEnhanceStatus.getStatus(channel)  = " + this.f5537a.f4811i.b(this.f5539c));
            this.f5537a.f4811i.a(this.f5539c, 1);
        }
    }

    public void V() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportChannelClickEnhance RandomPass mManagerObserver.mChannelClickEnhanceStatus.getStatus(channel)  = " + this.f5537a.f4811i.b(this.f5539c));
            this.f5537a.f4811i.a(this.f5539c, 2);
        }
    }

    public void W() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportChannelClickEnhance LayerPass mManagerObserver.mChannelClickEnhanceStatus.getStatus(channel)  = " + this.f5537a.f4811i.b(this.f5539c));
            this.f5537a.f4811i.a(this.f5539c, 3);
        }
    }

    public void X() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportChannelClickEnhance ReduceArea mManagerObserver.mChannelClickEnhanceStatus.getStatus(channel)  = " + this.f5537a.f4811i.b(this.f5539c));
            this.f5537a.f4811i.a(this.f5539c, 4);
        }
    }

    public void Y() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportValidTimeExposure mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 13);
        }
    }

    public void Z() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportNotEnoughExposureTime mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 14);
        }
    }

    public void a(Activity activity) {
    }

    public void a(String str, int i2) {
        if (this.C != i2) {
            this.C = i2;
            setChanged();
            notifyObservers(str);
        }
    }

    public void aA() {
        double avgPrice = n() != null ? n().getAvgPrice() : 0.0d;
        if (au() || ax()) {
            ac.a("BeiZis", "bid worker " + g() + " show ad,price = " + avgPrice);
            return;
        }
        if (az()) {
            ac.a("BeiZis", "waterfall worker " + g() + " show ad,price = " + avgPrice);
        }
    }

    public void aB() {
        d dVar = this.f5537a;
        if (dVar != null) {
            dVar.a().a(this.f5539c, this.f5538b);
        }
    }

    public boolean aC() {
        ac.c("BeiZis", "enter checkCsjInitStatusInValid");
        boolean z2 = TextUtils.isEmpty(this.f5544h) || TextUtils.isEmpty(this.f5545i) || v.a() == null;
        if (z2) {
            aE();
        }
        return z2;
    }

    public void aD() {
        H();
        e eVar = this.f5540d;
        if (eVar != null) {
            eVar.b(R2.drawable.ic_coupon_middle);
        }
    }

    public void aE() {
        ac.c("BeiZis", "enter handleInitError");
        b("sdk custom error ".concat(g()).concat(" ").concat("init error"), R2.drawable.ic_coupon_middle);
    }

    public void aF() {
    }

    public void aG() {
    }

    public int aH() {
        return this.f5547k;
    }

    public void aI() {
        if (this.f5537a == null || !"C2S".equals(h())) {
            return;
        }
        ac.c("BeiZis", "channel " + this.f5539c + " reportC2SPrice mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
        this.f5537a.f4809g.a(this.f5539c, 20);
    }

    public String aJ() {
        return this.G;
    }

    public NativeUnifiedAdResponse aK() {
        return null;
    }

    public void aa() {
        if (this.f5537a != null) {
            ac.c("BeiZis", "channel " + this.f5539c + " reportRandomNoExposure mManagerObserver.mChannelResultStatus.getStatus(channel) = " + this.f5537a.f4809g.b(this.f5539c));
            this.f5537a.f4809g.a(this.f5539c, 15);
        }
    }

    public boolean ab() {
        if (this.f5540d != null) {
            ac.c("BeiZis", "adStatus = " + this.f5540d.r());
        }
        e eVar = this.f5540d;
        return eVar != null && eVar.r() < 1;
    }

    public boolean ac() {
        e eVar = this.f5540d;
        return eVar != null && eVar.g() && ((ay() && i() == 0) || ae() || aw());
    }

    public void ad() {
        if (this.f5543g == null && this.f5540d != null && aL()) {
            this.f5543g = this.f5540d.b(this);
        }
    }

    public boolean ae() {
        return false;
    }

    public void af() {
        this.H = true;
    }

    public void ag() {
        aA();
        this.f5559w = true;
        ac.c("BeiZis", "enter handleAdShow !isStartExposureTask ? " + (true ^ this.A) + ",isReportValidExposureTimeEvent = " + this.I);
        if (!this.A || this.I) {
            aM();
        }
    }

    public void ah() {
        if ((this.f5556t || this.f5540d == null) && !c(g())) {
            return;
        }
        this.f5540d.c(g());
        this.f5556t = true;
        if (this.A) {
            aN();
        }
    }

    public void ai() {
        e eVar;
        StringBuilder sb = new StringBuilder();
        sb.append("enter handleAdLoaded and !isHandleAdLoad ? ");
        sb.append(!this.f5557u);
        sb.append(",mAdLifeManager != null ? ");
        sb.append(this.f5540d != null);
        ac.c("BeiZis", sb.toString());
        if (this.f5557u || (eVar = this.f5540d) == null) {
            return;
        }
        eVar.a(g(), (View) null);
        this.f5548l = System.currentTimeMillis();
        this.f5557u = true;
        ac.c("BeiZis", "isExposureTimeValid = " + aP());
        if (aP()) {
            aO();
            this.f5562z = System.currentTimeMillis();
        }
    }

    public void aj() {
        this.f5549m.post(new Runnable() { // from class: com.beizi.fusion.work.a.3
            @Override // java.lang.Runnable
            public void run() {
                a.this.ak();
            }
        });
    }

    public void ak() {
        if (this.f5559w) {
            aM();
        } else {
            this.I = true;
        }
    }

    public void al() {
        if (this.f5540d == null || !az()) {
            return;
        }
        e eVar = this.f5540d;
        eVar.a("255.200", eVar.h(), new EventItem("255.200", String.valueOf(System.currentTimeMillis()), g(), this.f5545i));
    }

    public void am() {
        if (this.f5540d == null || !az()) {
            return;
        }
        e eVar = this.f5540d;
        eVar.a("280.300", eVar.h(), new EventItem("280.300", String.valueOf(System.currentTimeMillis()), g(), this.f5545i));
    }

    public void an() {
        if (this.f5540d == null || !az()) {
            return;
        }
        e eVar = this.f5540d;
        eVar.a("290.300", eVar.h(), new EventItem("290.300", String.valueOf(System.currentTimeMillis()), g(), this.f5545i));
    }

    public void ao() {
        if (this.f5540d == null || !az()) {
            return;
        }
        e eVar = this.f5540d;
        eVar.a("280.500", eVar.h(), new EventItem("280.500", String.valueOf(System.currentTimeMillis()), g(), this.f5545i));
    }

    public void ap() {
        if (at()) {
            d(3);
            aS();
        }
    }

    public void aq() {
        if (v() != 3) {
            ac.a("BeiZisBid", "mWorker = " + this + ",set ad suc");
            d(2);
        }
    }

    public void ar() {
        if (aT()) {
            ac.a("BeiZis", "buyer " + g() + " cache ad fail");
            b(3);
            a(g(), 3);
        }
    }

    public void as() {
        if (!aT() || v() == 3) {
            return;
        }
        ac.a("BeiZis", "worker " + this + " cache ad success,price = " + n().getAvgPrice());
        b(2);
        a(g(), 2);
    }

    public boolean at() {
        return au();
    }

    public boolean au() {
        return "C2S".equalsIgnoreCase(h());
    }

    public boolean av() {
        return "S2S".equalsIgnoreCase(h());
    }

    public boolean aw() {
        return av() || au();
    }

    public boolean ax() {
        return "BPDI".equalsIgnoreCase(h());
    }

    public boolean ay() {
        return az() || ax();
    }

    public boolean az() {
        return "WATERFALL".equalsIgnoreCase(h());
    }

    public void b(int i2) {
        this.f5553q = i2;
    }

    public boolean c() {
        return this.D;
    }

    public abstract void d();

    public void d(int i2) {
        this.f5552p = i2;
    }

    public void e() {
    }

    public void e(int i2) {
        if (this.f5540d == null || this.f5542f == null) {
            return;
        }
        if (ab()) {
            this.f5540d.a(this.f5542f.getComponent(), g(), true, i2, i());
        } else {
            ac.b("BeiZis", "fail distribute direct fail");
            this.f5540d.b(i2);
        }
    }

    public abstract void f();

    public void f(int i2) {
    }

    public abstract String g();

    public String h() {
        return this.B;
    }

    public int i() {
        return this.E;
    }

    public int j() {
        return this.f5553q;
    }

    public abstract com.beizi.fusion.f.a k();

    public String l() {
        return null;
    }

    public AdSpacesBean.ForwardBean m() {
        return this.f5542f;
    }

    public AdSpacesBean.BuyerBean n() {
        return this.f5541e;
    }

    public boolean o() {
        return this.f5554r;
    }

    public abstract void p();

    public void q() {
        Handler handler = this.f5549m;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public void r() {
    }

    public View s() {
        return null;
    }

    public int t() {
        return this.f5550n;
    }

    public int u() {
        return this.f5551o;
    }

    public int v() {
        return this.f5552p;
    }

    public void w() {
        R();
    }

    public void x() {
        e eVar = this.f5540d;
        if (eVar != null) {
            this.f5537a = eVar.c();
        }
        AdSpacesBean.BuyerBean buyerBean = this.f5541e;
        if (buyerBean != null) {
            this.f5539c = com.beizi.fusion.f.b.a(buyerBean.getId());
        }
    }

    public void y() {
        d dVar = this.f5537a;
        if (dVar != null) {
            dVar.a().a(com.beizi.fusion.f.b.a(g()), this.f5541e, this.f5542f);
        }
    }

    public void z() {
        d dVar = this.f5537a;
        if (dVar != null) {
            dVar.f4809g.a(this.f5539c, 16);
        }
    }

    public void b(boolean z2) {
        this.f5554r = z2;
    }

    public void c(int i2) {
        this.f5550n = i2;
        if (i2 == 2 || i2 == 3) {
            aS();
        }
    }

    private boolean b() {
        return at() && D();
    }

    private boolean c(String str) {
        return "FinalLink".equalsIgnoreCase(str);
    }

    public void b(String str, int i2) {
        this.f5546j = com.beizi.fusion.f.a.ADFAIL;
        if (aL()) {
            Message messageObtainMessage = this.f5549m.obtainMessage(3, str);
            messageObtainMessage.arg1 = i2;
            this.f5549m.sendMessage(messageObtainMessage);
        }
    }

    public void a(boolean z2) {
        this.D = z2;
    }

    public void a(String str) {
        this.B = str;
    }

    public void a(int i2) {
        ac.a("BeiZis", g() + " setCache  = " + i2);
        this.E = i2;
    }

    public void a(long j2) {
        this.f5555s = j2;
    }

    public void b(String str) {
        this.G = str;
    }

    @Override // com.beizi.fusion.d.c
    public void a() {
        if (k() != com.beizi.fusion.f.a.ADSHOW) {
            T();
        }
    }

    public void a(Message message) {
        b bVar;
        if (this.f5537a == null || (bVar = this.f5538b) == null) {
            return;
        }
        bVar.i(String.valueOf(message.obj));
        this.f5538b.m(String.valueOf(message.arg1));
        aB();
        H();
        this.f5538b.i(null);
        this.f5538b.m(null);
        aB();
    }

    public void a(double d3) {
        if (d3 > 0.0d) {
            if ("BEIZI".equalsIgnoreCase(g()) || "MTG".equalsIgnoreCase(g())) {
                this.f5541e.setAvgPrice(d3);
                b bVar = this.f5538b;
                if (bVar != null) {
                    bVar.M(String.valueOf(d3));
                }
            }
            if (aw()) {
                this.f5541e.setBidPrice(d3);
                b bVar2 = this.f5538b;
                if (bVar2 != null) {
                    bVar2.N(String.valueOf(d3));
                }
            }
            aB();
        }
    }
}
