package com.beizi.fusion.d;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.beizi.fusion.AdListener;
import com.beizi.fusion.BannerAdListener;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.DrawAdListener;
import com.beizi.fusion.FullScreenVideoAdListener;
import com.beizi.fusion.InterstitialAdListener;
import com.beizi.fusion.NativeAdListener;
import com.beizi.fusion.NativeFloatAdListener;
import com.beizi.fusion.NativeNotificationAdListener;
import com.beizi.fusion.NativeUnifiedAdListener;
import com.beizi.fusion.RewardedVideoAdListener;
import com.beizi.fusion.g.ab;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.as;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.EventItem;
import com.beizi.fusion.model.Manager;
import com.beizi.fusion.model.ResponseInfo;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public abstract class e implements com.beizi.fusion.d.a, com.beizi.fusion.d.a.a, com.beizi.fusion.d.a.c, Observer {

    /* renamed from: a, reason: collision with root package name */
    protected static Context f4925a = null;

    /* renamed from: v, reason: collision with root package name */
    private static boolean f4926v = false;
    private com.beizi.fusion.b.d L;
    private long M;
    private List<AdSpacesBean.BuyerBean> N;
    private ab S;
    private a T;
    private boolean U;
    private com.beizi.fusion.d.a.b al;

    /* renamed from: b, reason: collision with root package name */
    protected Context f4927b;

    /* renamed from: c, reason: collision with root package name */
    protected com.beizi.fusion.b.b f4928c;

    /* renamed from: d, reason: collision with root package name */
    protected ViewGroup f4929d;

    /* renamed from: e, reason: collision with root package name */
    protected String f4930e;

    /* renamed from: f, reason: collision with root package name */
    protected long f4931f;

    /* renamed from: g, reason: collision with root package name */
    protected View f4932g;

    /* renamed from: h, reason: collision with root package name */
    protected com.beizi.fusion.a f4933h;

    /* renamed from: i, reason: collision with root package name */
    protected com.beizi.fusion.work.a f4934i;

    /* renamed from: n, reason: collision with root package name */
    protected String f4939n;

    /* renamed from: p, reason: collision with root package name */
    boolean f4941p;

    /* renamed from: x, reason: collision with root package name */
    private e f4948x;

    /* renamed from: y, reason: collision with root package name */
    private AdSpacesBean f4949y;

    /* renamed from: j, reason: collision with root package name */
    protected Map<String, com.beizi.fusion.work.a> f4935j = new Hashtable();

    /* renamed from: k, reason: collision with root package name */
    protected HashSet<String> f4936k = new HashSet<>();

    /* renamed from: l, reason: collision with root package name */
    protected ArrayList<c> f4937l = new ArrayList<>();

    /* renamed from: m, reason: collision with root package name */
    protected boolean f4938m = false;

    /* renamed from: o, reason: collision with root package name */
    protected Map<String, com.beizi.fusion.work.a> f4940o = new Hashtable();

    /* renamed from: q, reason: collision with root package name */
    boolean f4942q = false;

    /* renamed from: r, reason: collision with root package name */
    boolean f4943r = false;

    /* renamed from: w, reason: collision with root package name */
    private boolean f4947w = false;

    /* renamed from: z, reason: collision with root package name */
    private long f4950z = 500;
    private long A = 100;
    private volatile int B = 0;
    private int C = 0;
    private Timer D = new Timer();
    private Timer E = new Timer();
    private Timer F = new Timer();
    private boolean G = false;
    private boolean H = false;
    private boolean I = false;
    private boolean J = true;
    private boolean K = true;
    private List<AdSpacesBean.BuyerBean> O = new ArrayList();
    private List<AdSpacesBean.BuyerBean> P = new ArrayList();
    private List<AdSpacesBean.ForwardBean> Q = new ArrayList();
    private boolean R = false;
    private boolean V = false;
    private List<com.beizi.fusion.work.a> W = null;
    private AdSpacesBean X = null;
    private com.beizi.fusion.d.a.a Y = null;
    private List<AdSpacesBean.ForwardBean> Z = null;
    private com.beizi.fusion.b.a aa = null;
    private List<AdSpacesBean.ForwardBean> ab = null;
    private List<AdSpacesBean.ForwardBean> ac = null;

    /* renamed from: s, reason: collision with root package name */
    protected boolean f4944s = false;
    private String ad = null;
    private int ae = 0;
    private int af = 1;
    private int ag = 2;

    /* renamed from: t, reason: collision with root package name */
    protected boolean f4945t = false;
    private boolean ah = false;
    private TimerTask ai = new TimerTask() { // from class: com.beizi.fusion.d.e.1
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            e.this.C = 1;
            if (e.this.f4934i != null) {
                Log.d("BeiZis", "other worker has ready");
                e.this.f4946u.sendEmptyMessage(1);
            }
        }
    };
    private TimerTask aj = new TimerTask() { // from class: com.beizi.fusion.d.e.2
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Log.d("BeiZis", "========update inner outTime==========:" + System.currentTimeMillis());
            e.this.C = 2;
            e.this.f4946u.sendEmptyMessage(2);
            if (e.this.L != null) {
                e.this.L.a(true);
            }
        }
    };

    /* renamed from: u, reason: collision with root package name */
    @SuppressLint({"HandlerLeak"})
    protected Handler f4946u = new Handler(Looper.getMainLooper()) { // from class: com.beizi.fusion.d.e.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Map<String, com.beizi.fusion.work.a> mapP;
            int i2 = message.what;
            if (i2 == 1) {
                e eVar = e.this;
                com.beizi.fusion.work.a aVar = eVar.f4934i;
                if (aVar != null) {
                    eVar.b(aVar.g(), 2);
                    e.this.f4934i.w();
                    e eVar2 = e.this;
                    eVar2.a(eVar2.f4934i.g(), e.this.f4934i.s());
                    if ("4".equals(e.this.e())) {
                        e eVar3 = e.this;
                        if (eVar3.f4944s) {
                            return;
                        }
                        eVar3.f4934i.f();
                        e.this.f4944s = true;
                        return;
                    }
                    return;
                }
                return;
            }
            if (i2 == 2) {
                e.this.b(R2.drawable.homepage_shangcheng_press);
                if (e.this.L == null || (mapP = e.this.p()) == null) {
                    return;
                }
                for (com.beizi.fusion.work.a aVar2 : mapP.values()) {
                    if (aVar2.g().equals("GDT") || aVar2.g().equals("BAIDU")) {
                        aVar2.f(2);
                    }
                    int iA = com.beizi.fusion.f.b.a(aVar2.g());
                    ac.a("BeiZis", "AdRequest timeout channel = " + iA + ",mManagerObserver.mChannelResultStatus.getStatus(channel) = " + e.this.L.f4809g.b(iA));
                    if (e.this.L.f4809g.b(iA) < 4) {
                        e.this.L.f4809g.a(iA, -1);
                    }
                }
                return;
            }
            if (i2 != 3) {
                return;
            }
            com.beizi.fusion.b.d dVarG = b.a().g();
            e.this.L = new com.beizi.fusion.b.d(new com.beizi.fusion.b.b(b.f4908b, "", "", "", b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
            e eVar4 = e.this;
            eVar4.aa = eVar4.L.a();
            e eVar5 = e.this;
            eVar5.f4928c = eVar5.L.b();
            e.this.E();
            e.this.aa.a(e.this.f4928c);
            e.this.H();
            if (dVarG == null || dVarG.f4803a.a() != 2 || e.this.L.f4804b.a() != 0) {
                e.this.b(10000);
                return;
            }
            e.this.L.f4804b.a(1);
            if (e.this.L.f4804b.a() != 1) {
                e.this.L.f4804b.a(-2);
                e.this.a("kGetLocalConfigStatusInternalError");
                return;
            }
            e.this.L.f4804b.a(2);
            e eVar6 = e.this;
            eVar6.f4949y = com.beizi.fusion.c.a.a(eVar6.f4927b, eVar6.f4939n, eVar6.e());
            if (e.this.f4949y != null) {
                e.this.b(R2.drawable.homepage_shangcheng_press);
                return;
            }
            Log.d("BeiZis", "update spaceBean is null and return fail");
            if (e.this.L.f4804b.a() != 2) {
                e.this.L.f4804b.a(-2);
                e.this.a("kGetLocalConfigStatusInternalError");
                return;
            }
            int iA2 = com.beizi.fusion.c.a.a();
            if (iA2 == 1) {
                e.this.L.f4804b.a(4);
                e.this.b(10001);
            } else if (iA2 == 2) {
                e.this.L.f4804b.a(5);
                e.this.b(10100);
            } else if (iA2 != 3) {
                e.this.b(R2.drawable.homepage_shangcheng_press);
            } else {
                e.this.L.f4804b.a(6);
                e.this.b(10110);
            }
        }
    };
    private TimerTask ak = new TimerTask() { // from class: com.beizi.fusion.d.e.4
        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Log.d("BeiZis", "========update outTime==========:" + System.currentTimeMillis());
            e.this.f4946u.sendEmptyMessage(3);
        }
    };

    public class a extends BroadcastReceiver {
        public a() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) throws InterruptedException {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            int intExtra = intent.getIntExtra("updateResult", 0);
            e.this.U = true;
            if (TextUtils.equals(action, "com.ad.action.UPDATE_CONFIG_SUCCESS")) {
                if (intExtra == 1) {
                    if (e.this.F != null) {
                        e.this.F.cancel();
                        e.this.F = null;
                    }
                    e eVar = e.this;
                    eVar.a(eVar.f4929d);
                    return;
                }
                if (intExtra == 0 && e.this.V) {
                    if (e.this.F != null) {
                        e.this.F.cancel();
                        e.this.F = null;
                    }
                    e eVar2 = e.this;
                    eVar2.a(eVar2.f4929d);
                }
            }
        }
    }

    public e(Context context, String str, com.beizi.fusion.a aVar, long j2) {
        if (context == null) {
            as.b("Illegal Argument: context is null");
        } else {
            this.f4927b = context;
            f4925a = context.getApplicationContext();
            if (!(this.f4927b instanceof Activity)) {
                as.b("Illegal Argument: context is not Activity context");
            }
        }
        this.f4939n = str;
        this.f4933h = aVar;
        this.f4931f = j2;
        this.f4948x = this;
        C();
    }

    private void B() {
        TimerTask timerTask = this.aj;
        if (timerTask != null) {
            timerTask.cancel();
            this.aj = null;
        }
        Timer timer = this.E;
        if (timer != null) {
            timer.cancel();
            this.E = null;
        }
        Timer timer2 = this.F;
        if (timer2 != null) {
            timer2.cancel();
            this.F = null;
        }
    }

    private void C() {
        Context context = f4925a;
        if (context == null) {
            if (this.f4933h != null) {
                b(R2.drawable.ic_combineq_question_bg_empty_day);
            }
        } else {
            this.S = ab.a(context);
            IntentFilter intentFilter = new IntentFilter("com.ad.action.UPDATE_CONFIG_SUCCESS");
            a aVar = new a();
            this.T = aVar;
            this.S.registerReceiver(aVar, intentFilter);
        }
    }

    private void D() {
        TimerTask timerTask;
        if (this.U) {
            return;
        }
        com.beizi.fusion.update.b.a(f4925a).b(5);
        Timer timer = this.F;
        if (timer == null || (timerTask = this.ak) == null) {
            return;
        }
        timer.schedule(timerTask, this.f4931f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void E() {
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.b(this.f4930e);
            a();
            this.f4928c.e(b.a().b());
            this.f4928c.f(this.f4939n);
            this.f4928c.g(String.valueOf(this.f4931f));
        }
    }

    private boolean F() {
        TimerTask timerTask;
        TimerTask timerTask2;
        AdSpacesBean adSpacesBean = this.f4949y;
        if (adSpacesBean == null) {
            return false;
        }
        if (adSpacesBean.getBid() != null) {
            this.f4950z = r0.getReserveFRWTime();
            this.A = r0.getReserveTime();
        }
        Log.d("BeiZis", this.f4931f + ":mUsableTime=====" + this.f4950z + "=====mLastTime:" + this.A);
        if ((this instanceof r) || (this instanceof q)) {
            return false;
        }
        long j2 = this.f4931f;
        if (j2 <= this.A) {
            b(R2.drawable.ic_combine_question_loading2);
            this.L.f4804b.a(7);
            return true;
        }
        long j3 = this.f4950z;
        if (j2 > j3) {
            Timer timer = this.D;
            if (timer != null && (timerTask2 = this.ai) != null) {
                timer.schedule(timerTask2, j2 - j3);
            }
        } else {
            this.C = 1;
        }
        Timer timer2 = this.E;
        if (timer2 != null && (timerTask = this.aj) != null) {
            timer2.schedule(timerTask, this.f4931f - this.A);
        }
        return false;
    }

    private void G() throws InterruptedException {
        com.beizi.fusion.g.w.b(f4925a, this.f4939n);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void H() {
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            dVar.f4804b.addObserver(dVar);
            com.beizi.fusion.b.d dVar2 = this.L;
            dVar2.f4805c.addObserver(dVar2);
            com.beizi.fusion.b.d dVar3 = this.L;
            dVar3.f4806d.addObserver(dVar3);
            com.beizi.fusion.b.d dVar4 = this.L;
            dVar4.f4807e.addObserver(dVar4);
            com.beizi.fusion.b.d dVar5 = this.L;
            dVar5.f4808f.addObserver(dVar5);
            com.beizi.fusion.b.d dVar6 = this.L;
            dVar6.f4809g.addObserver(dVar6);
            com.beizi.fusion.b.d dVar7 = this.L;
            dVar7.f4810h.addObserver(dVar7);
            com.beizi.fusion.b.d dVar8 = this.L;
            dVar8.f4811i.addObserver(dVar8);
            com.beizi.fusion.b.d dVar9 = this.L;
            dVar9.f4812j.addObserver(dVar9);
            com.beizi.fusion.b.d dVar10 = this.L;
            dVar10.f4813k.addObserver(dVar10);
        }
    }

    private void I() {
        String str;
        AdSpacesBean adSpacesBean = this.f4949y;
        if (adSpacesBean != null) {
            String adType = adSpacesBean.getAdType();
            adType.hashCode();
            switch (adType) {
                case "NATIVE":
                    str = "5";
                    break;
                case "SPLASH":
                    str = "2";
                    break;
                case "INTERSTITIAL":
                    str = "3";
                    break;
                case "REWARDEDVIDEO":
                    str = "1";
                    break;
                case "DRAWFLOW":
                    str = "7";
                    break;
                case "INTERACTIVECARD":
                    str = Constants.VIA_REPORT_TYPE_JOININ_GROUP;
                    break;
                case "FULLSCREENVIDEO":
                    str = Constants.VIA_SHARE_TYPE_INFO;
                    break;
                case "REGIONALNATIVE":
                    str = Constants.VIA_REPORT_TYPE_SET_AVATAR;
                    break;
                case "BANNER":
                    str = "4";
                    break;
                default:
                    str = null;
                    break;
            }
            com.beizi.fusion.b.b bVar = this.f4928c;
            if (bVar != null) {
                bVar.d(str);
            }
        }
    }

    private void J() {
        List<com.beizi.fusion.work.a> list = this.W;
        if (list == null || list.size() <= 0) {
            return;
        }
        for (com.beizi.fusion.work.a aVar : this.W) {
            if (aVar != null && aVar.n() != null) {
                b(aVar.n().getId(), aVar);
            }
        }
    }

    private boolean K() {
        return this.f4935j.size() == 0 && !this.f4943r;
    }

    private void L() {
        ac.a("BeiZis", "enter doPriceOutInAdvance");
        if (this.f4934i != null) {
            this.C = 1;
            this.f4946u.sendEmptyMessage(1);
            b();
            B();
        }
    }

    private boolean M() {
        if (this.W != null) {
            for (int i2 = 0; i2 < this.W.size(); i2++) {
                com.beizi.fusion.work.a aVar = this.W.get(i2);
                int iT = aVar.t();
                int iV = aVar.v();
                ac.a("BeiZis", "checkIfShowValidWorkerInBid bidStatus = " + iT + ",bidAdStatus = " + iV);
                if (iV == 2 && iT != 3) {
                    aVar.c(1);
                    if (aVar.n() != null) {
                        b(aVar.n().getId(), aVar);
                    }
                    return !k(aVar);
                }
            }
        }
        return true;
    }

    private synchronized void N() {
        this.f4937l.clear();
    }

    private void O() {
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar == null || dVar.f4812j.a() != 0) {
            return;
        }
        this.L.f4812j.a(1);
    }

    private void P() {
        if (this.L != null) {
            ac.b("BeiZis", "mManagerObserver.mManagerResultStatus.getStatus() = " + this.L.f4812j.a());
        }
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            if (dVar.f4812j.a() == 1 || this.L.f4812j.a() == 2 || this.L.f4812j.a() == 3) {
                this.L.f4812j.a(3);
            }
        }
    }

    private void Q() {
        if (this.L != null) {
            ac.b("BeiZis", "mManagerObserver.mManagerResultStatus.getStatus() = " + this.L.f4812j.a());
        }
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            if (dVar.f4812j.a() == 3 || this.L.f4812j.a() == 1 || this.L.f4812j.a() == 2) {
                this.L.f4812j.a(4);
            }
        }
    }

    private boolean h(com.beizi.fusion.work.a aVar) {
        return this.f4940o.containsValue(aVar) && aVar.j() == 3;
    }

    private String i(String str) {
        ac.a("BeiZis", "enter convertSelfChannel buyerId = " + str);
        return ar.b().equals(str) ? "BEIZI" : str;
    }

    private void j(com.beizi.fusion.work.a aVar) {
        if (aVar == null) {
            return;
        }
        ac.c("BeiZisBid", "mAdWorker = " + aVar + ",worker bidType = " + aVar.h());
        if (k(aVar)) {
            return;
        }
        int iA = com.beizi.fusion.f.b.a(aVar.g());
        ac.c("BeiZis", "realRequestAd channel = " + iA + ",mManagerObserver.mChannelRequestStatus.getStatus(channel) = " + this.L.f4808f.b(iA));
        if ((this.L.f4808f.b(iA) == 2 || this.L.f4808f.b(iA) == 5) && !aVar.o()) {
            aVar.d();
            aVar.b(true);
            this.O.add(aVar.n());
        } else if (aVar.o()) {
            this.L.f4808f.a(iA, 5);
        } else {
            this.L.f4808f.a(iA, -2);
        }
    }

    private boolean k(com.beizi.fusion.work.a aVar) {
        if (aVar != null && aVar.at()) {
            ac.a("BeiZisBid", "mAdWorker = " + aVar + ",mAdWorker.getWorkerBidStatus()  = " + aVar.t() + ",mAdWorker.getWorkerAdStatus() = " + aVar.v());
            if (aVar.t() == 1 && aVar.v() == 2) {
                l(aVar);
                return true;
            }
        }
        return false;
    }

    private void l(com.beizi.fusion.work.a aVar) {
        ac.a("BeiZis", "enter compareToDecideIfShow");
        if (b(aVar) == h.SUCCESS) {
            aVar.ai();
            if ("4".equals(e())) {
                aVar.f();
            }
        }
    }

    private void unregisterReceiver() {
        a aVar;
        ab abVar = this.S;
        if (abVar == null || (aVar = this.T) == null) {
            return;
        }
        abVar.unregisterReceiver(aVar);
    }

    public com.beizi.fusion.b.b A() {
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            return bVar;
        }
        return null;
    }

    public abstract com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar);

    public abstract void a();

    public void m() {
        com.beizi.fusion.work.a aVar = this.f4934i;
        if (aVar != null) {
            aVar.r();
        }
    }

    public void n() {
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            if (dVar.f4812j.a() == 1 || this.L.f4812j.a() == 3 || this.L.f4812j.a() == 4) {
                this.L.f4812j.a(2);
            }
        }
    }

    public void o() {
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            if (dVar.f4812j.a() == 0 || this.L.f4812j.a() == 1) {
                this.L.f4812j.a(5);
            }
        }
    }

    public Map<String, com.beizi.fusion.work.a> p() {
        return this.f4935j;
    }

    public int q() {
        return this.C;
    }

    public int r() {
        return this.B;
    }

    public void s() {
        ac.c("BeiZis", "enter clearAdStatus");
        this.B = 0;
        this.I = false;
    }

    public boolean t() {
        return f4926v;
    }

    public int u() {
        AdSpacesBean adSpacesBean = this.f4949y;
        if (adSpacesBean == null || adSpacesBean.getEventStrategy() == null) {
            return Integer.MAX_VALUE;
        }
        AdSpacesBean.EventStrategyBean eventStrategy = this.f4949y.getEventStrategy();
        if (eventStrategy.getValidTimeShow() >= 0) {
            return eventStrategy.getValidTimeShow();
        }
        return Integer.MAX_VALUE;
    }

    @Override // java.util.Observer
    public void update(Observable observable, Object obj) {
        ac.a("BeiZis", "enter cache result update");
        if (observable instanceof com.beizi.fusion.work.a) {
            com.beizi.fusion.work.a aVar = (com.beizi.fusion.work.a) observable;
            if (aVar.j() == 2) {
                l(aVar);
                this.f4942q = false;
                return;
            }
            if (aVar.j() == 3) {
                ac.a("BeiZis", aVar.g() + " enter CACHE_STATUS_FAIL , try candidate , isDeepestLevelWorker = " + this.f4941p);
                if (this.f4941p || aVar.m() == null) {
                    return;
                }
                aVar.a(true);
                this.f4942q = true;
                a(aVar.m().getComponent(), aVar.g(), true, R2.drawable.ic_coupon_middle, aVar.i());
            }
        }
    }

    public Integer[] v() {
        AdSpacesBean.EventStrategyBean eventStrategy;
        List<AdSpacesBean.RandomStrategyBean> randomStrategy;
        AdSpacesBean adSpacesBean = this.f4949y;
        if (adSpacesBean != null && adSpacesBean.getEventStrategy() != null && (eventStrategy = this.f4949y.getEventStrategy()) != null && (randomStrategy = eventStrategy.getRandomStrategy()) != null && randomStrategy.size() > 0) {
            for (int i2 = 0; i2 < randomStrategy.size(); i2++) {
                AdSpacesBean.RandomStrategyBean randomStrategyBean = randomStrategy.get(i2);
                if ("show".equalsIgnoreCase(randomStrategyBean.getEventType())) {
                    return new Integer[]{Integer.valueOf(randomStrategyBean.getMin()), Integer.valueOf(randomStrategyBean.getMax())};
                }
            }
        }
        return new Integer[]{0, 0};
    }

    public boolean w() {
        StringBuilder sb = new StringBuilder();
        sb.append("mBidWorkerList != null ? ");
        sb.append(this.W != null);
        ac.a("BeiZisBid", sb.toString());
        List<com.beizi.fusion.work.a> list = this.W;
        if (list == null) {
            return true;
        }
        boolean z2 = true;
        for (com.beizi.fusion.work.a aVar : list) {
            if (!c(aVar)) {
                ac.b("BeiZis", "worker.getBidType() = " + aVar.h() + ",worker.getWorkerBidStatus() = " + aVar.t());
                z2 &= aVar.t() == 2 || aVar.t() == 3;
            }
        }
        ac.a("BeiZisBid", "isAllNotDefaultOrReq = " + z2);
        return z2;
    }

    public com.beizi.fusion.d.a.a x() {
        return this.Y;
    }

    public boolean y() {
        return this.f4947w;
    }

    public String z() {
        AdSpacesBean adSpacesBean = this.X;
        if (adSpacesBean != null) {
            return adSpacesBean.getSpaceId();
        }
        return null;
    }

    private void b() {
        TimerTask timerTask = this.ai;
        if (timerTask != null) {
            timerTask.cancel();
            this.ai = null;
        }
        Timer timer = this.D;
        if (timer != null) {
            timer.cancel();
            this.D = null;
        }
        Timer timer2 = this.F;
        if (timer2 != null) {
            timer2.cancel();
            this.F = null;
        }
    }

    private boolean f(com.beizi.fusion.work.a aVar) {
        return this.f4940o.containsValue(aVar) && aVar.j() == 2;
    }

    private boolean g(com.beizi.fusion.work.a aVar) {
        return this.f4940o.containsValue(aVar) && aVar.j() == 1;
    }

    private com.beizi.fusion.work.a h(String str) {
        return this.f4940o.get(i(str));
    }

    public com.beizi.fusion.b.d c() {
        return this.L;
    }

    public void d() {
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            dVar.f4804b.deleteObservers();
            this.L.f4805c.deleteObservers();
            this.L.f4806d.deleteObservers();
            this.L.f4807e.deleteObservers();
            this.L.f4808f.deleteObservers();
            this.L.f4809g.deleteObservers();
            this.L.f4810h.deleteObservers();
            this.L.f4811i.deleteObservers();
            this.L.f4812j.deleteObservers();
            this.L.f4813k.deleteObservers();
        }
    }

    public String e() {
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            return bVar.e();
        }
        return null;
    }

    private boolean c(AdSpacesBean.BuyerBean buyerBean) {
        List<com.beizi.fusion.work.a> listB;
        com.beizi.fusion.d.a.b bVar = this.al;
        if (bVar == null || buyerBean == null || (listB = bVar.b()) == null) {
            return false;
        }
        for (com.beizi.fusion.work.a aVar : listB) {
            AdSpacesBean.BuyerBean buyerBeanN = aVar.n();
            ac.a("BeiZisBid", "isSameBuyerInBid buyerBean = " + buyerBean + ",tempBuyerBean = " + buyerBeanN);
            if (buyerBeanN != null && buyerBeanN.getId() != null && buyerBeanN.getId().equalsIgnoreCase(buyerBean.getId())) {
                ac.a("BeiZisBid", "isSameBuyerInBid mBidWorker.getWorkerAdStatus() = " + aVar.v());
                return aVar.v() == 1;
            }
        }
        return false;
    }

    private boolean f(String str) {
        return this.f4940o.containsKey(i(str));
    }

    private com.beizi.fusion.work.a g(String str) {
        return this.f4935j.get(i(str));
    }

    private void i(com.beizi.fusion.work.a aVar) {
        List<com.beizi.fusion.work.a> list = this.W;
        if (list == null) {
            return;
        }
        for (com.beizi.fusion.work.a aVar2 : list) {
            ac.b("BeiZis", "adWorker = " + aVar2 + ",mWinWorker = " + aVar + ",adWorker.getWorkerBidNoticeStatus() = " + aVar2.u());
            if (!aVar2.equals(aVar) && aVar2.u() == 1) {
                aVar2.f(1);
            }
        }
    }

    private double m(com.beizi.fusion.work.a aVar) {
        if (aVar == null || aVar.n() == null) {
            return 0.0d;
        }
        AdSpacesBean.BuyerBean buyerBeanN = aVar.n();
        if (!e(aVar) && !d(aVar)) {
            return buyerBeanN.getAvgPrice();
        }
        if (buyerBeanN.getBidPrice() > 0.0d) {
            return buyerBeanN.getBidPrice();
        }
        return buyerBeanN.getAvgPrice();
    }

    public String h() {
        return this.f4939n;
    }

    private boolean e(String str) {
        return this.f4935j.containsKey(i(str));
    }

    private void o(com.beizi.fusion.work.a aVar) {
        List<AdSpacesBean.ForwardBean> list;
        List<AdSpacesBean.ForwardBean> list2;
        if (aVar == null) {
            return;
        }
        if (d(aVar) && (list2 = this.ab) != null && list2.size() > 0) {
            Iterator<AdSpacesBean.ForwardBean> it = this.ab.iterator();
            while (it.hasNext()) {
                if (it.next().getBuyerId().equalsIgnoreCase(aVar.m().getBuyerId())) {
                    it.remove();
                }
            }
            return;
        }
        if (!e(aVar) || (list = this.ac) == null || list.size() <= 0) {
            return;
        }
        Iterator<AdSpacesBean.ForwardBean> it2 = this.ac.iterator();
        while (it2.hasNext()) {
            if (it2.next().getBuyerId().equalsIgnoreCase(aVar.m().getBuyerId())) {
                it2.remove();
            }
        }
    }

    public void f() {
        if (M()) {
            this.L.f4806d.a(3);
            b(R2.drawable.ic_coupon_middle);
        }
    }

    public boolean g() {
        return (r() == 2 || r() == 4) ? false : true;
    }

    private boolean e(AdSpacesBean.BuyerBean buyerBean) {
        if (buyerBean == null) {
            return false;
        }
        return this.P.contains(buyerBean);
    }

    private void n(com.beizi.fusion.work.a aVar) {
        N();
        a((c) aVar);
    }

    @NonNull
    private h e(int i2) {
        d(i2);
        return h.SUCCESS;
    }

    private boolean f(AdSpacesBean.BuyerBean buyerBean) {
        if (buyerBean == null) {
            return false;
        }
        ac.b("BeiZis", "isSameBuyerInNormalRequest buyerBean = " + buyerBean + ",mRequestedBuyerBeanList = " + this.O);
        return this.O.contains(buyerBean);
    }

    public void k() {
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar == null || !(aVar instanceof RewardedVideoAdListener)) {
            return;
        }
        ((RewardedVideoAdListener) aVar).onRewardedVideoComplete();
    }

    public void l() {
        this.f4945t = true;
        b();
        B();
        com.beizi.fusion.work.a aVar = this.f4934i;
        if (aVar != null) {
            aVar.q();
        }
        if (this.ae != this.af) {
            this.f4933h = null;
        }
        Map<String, com.beizi.fusion.work.a> map = this.f4935j;
        if (map != null) {
            map.clear();
        }
        List<AdSpacesBean.BuyerBean> list = this.O;
        if (list != null) {
            list.clear();
        }
        List<AdSpacesBean.ForwardBean> list2 = this.Q;
        if (list2 != null) {
            list2.clear();
        }
        ArrayList<c> arrayList = this.f4937l;
        if (arrayList != null) {
            arrayList.clear();
        }
        if (this.f4948x != null) {
            this.f4948x = null;
        }
        unregisterReceiver();
        d();
    }

    public boolean e(com.beizi.fusion.work.a aVar) {
        return "C2S".equalsIgnoreCase(aVar.h());
    }

    @NonNull
    private h f(int i2) {
        c(i2);
        return h.FAIL;
    }

    public void a(com.beizi.fusion.b.d dVar) {
        this.L = dVar;
    }

    private boolean k(String str) {
        return "FinalLink".equalsIgnoreCase(str);
    }

    public void a(ViewGroup viewGroup) throws InterruptedException {
        AdSpacesBean.ComponentBean component;
        List<AdSpacesBean.BuyerBean> buyer;
        AdSpacesBean.BidComponent bidComponent;
        List<AdSpacesBean> adSpaces;
        AdSpacesBean adSpacesBean;
        if (this.R || this.f4927b == null || f4925a == null) {
            return;
        }
        this.f4929d = viewGroup;
        this.M = System.currentTimeMillis();
        this.f4930e = ar.a();
        com.beizi.fusion.b.d dVarG = b.a().g();
        com.beizi.fusion.b.d dVar = new com.beizi.fusion.b.d(new com.beizi.fusion.b.b(b.f4908b, "", "", "", b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), ""));
        this.L = dVar;
        this.aa = dVar.a();
        this.f4928c = this.L.b();
        E();
        this.aa.a(this.f4928c);
        H();
        if (!BeiZis.isIsSyncInit() && ((dVarG == null || dVarG.f4803a.a() != 2) && !this.ah)) {
            ResponseInfo.getInstance(f4925a).init();
            this.ah = true;
        }
        if (dVarG != null) {
            ac.b("BeiZis", "mInitObserver.mInitStatus.getStatus() = " + dVarG.f4803a.a() + ",mManagerObserver.mGetLocalConfigStatus.getStatus() = " + this.L.f4804b.a());
        }
        if ((dVarG != null && dVarG.f4803a.a() == 2 && this.L.f4804b.a() == 0) || this.ah) {
            this.L.f4804b.a(1);
            if (this.L.f4804b.a() == 1) {
                this.L.f4804b.a(2);
                AdSpacesBean adSpacesBeanA = com.beizi.fusion.c.a.a(this.f4927b, this.f4939n, e());
                this.f4949y = adSpacesBeanA;
                if (adSpacesBeanA != null) {
                    com.beizi.fusion.b.b bVar = this.f4928c;
                    if (bVar != null) {
                        bVar.A(adSpacesBeanA.getFilterSsid());
                        this.f4928c.B(this.f4949y.getComponentSsid());
                        this.f4928c.C(this.f4949y.getBzComponentSsid());
                        I();
                    }
                    ResponseInfo responseInfo = ResponseInfo.getInstance(f4925a);
                    Manager manager = responseInfo.getManager();
                    if (manager == null || (adSpaces = manager.getAdSpaces()) == null || adSpaces.size() <= 0 || (adSpacesBean = adSpaces.get(0)) == null) {
                        component = null;
                        buyer = null;
                        bidComponent = null;
                    } else {
                        component = adSpacesBean.getComponent();
                        bidComponent = adSpacesBean.getBidComponent();
                        buyer = adSpacesBean.getBuyer();
                    }
                    if ((component != null || bidComponent != null) && buyer != null && buyer.size() > 0) {
                        long maxValidTime = responseInfo.getMaxValidTime();
                        if (maxValidTime == 0) {
                            maxValidTime = 2592000000L;
                        }
                        if (System.currentTimeMillis() - ((Long) aq.b(f4925a, "lastUpdateTime", Long.valueOf(new Date(0L).getTime()))).longValue() <= maxValidTime) {
                            this.V = false;
                            this.R = true;
                            if (this.L.f4804b.a() == 2) {
                                this.L.f4804b.a(3);
                                if (F()) {
                                    return;
                                }
                                G();
                                if (this.L.c()) {
                                    this.L.f4804b.a(-1);
                                    return;
                                } else {
                                    a(this.f4949y);
                                    return;
                                }
                            }
                            return;
                        }
                        this.R = false;
                        this.V = true;
                        D();
                        return;
                    }
                    this.R = false;
                    D();
                    return;
                }
                Log.d("BeiZis", "spaceBean is null and return fail mUpdateConfigSuccess:" + this.U);
                if (this.U) {
                    if (this.L.f4804b.a() == 2) {
                        int iA = com.beizi.fusion.c.a.a();
                        if (iA == 1) {
                            this.L.f4804b.a(4);
                            b(10001);
                            return;
                        } else if (iA == 2) {
                            this.L.f4804b.a(5);
                            b(10100);
                            return;
                        } else {
                            if (iA == 3) {
                                this.L.f4804b.a(6);
                                b(10110);
                                return;
                            }
                            return;
                        }
                    }
                    this.L.f4804b.a(-2);
                    a("kGetLocalConfigStatusInternalError");
                    return;
                }
                Log.e("BeiZis", "startUpdateConfig");
                this.R = false;
                D();
                return;
            }
            this.L.f4804b.a(-2);
            a("kGetLocalConfigStatusInternalError");
            return;
        }
        if (this.U) {
            this.R = false;
            D();
        } else {
            b(10000);
        }
    }

    public synchronized void i() {
        Iterator<c> it = this.f4937l.iterator();
        while (it.hasNext()) {
            it.next().a();
        }
        N();
    }

    private void b(AdSpacesBean.ComponentBean componentBean) {
        if (r() >= 1) {
            return;
        }
        List<AdSpacesBean.ForwardBean> listA = a(componentBean, this.N, false);
        if (listA != null) {
            ac.a("BeiZis", "after auction forwardBeanList = " + listA.toString());
            Iterator<AdSpacesBean.ForwardBean> it = listA.iterator();
            while (it.hasNext()) {
                if (c(it.next())) {
                    it.remove();
                }
            }
        }
        a(listA);
        Log.d("BeiZis", "mAdWorker:" + this.f4935j.toString());
        if (K()) {
            f();
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (com.beizi.fusion.work.a aVar : this.f4935j.values()) {
            if (!e(aVar) && !d(aVar)) {
                if (f(aVar)) {
                    ac.a("BeiZis", aVar.g() + " isWorkerCacheSuc , auctionAndRealRequest");
                    arrayList2.add(aVar);
                } else if (g(aVar)) {
                    ac.a("BeiZis", aVar.g() + " is caching , wait result , auctionAndRealRequest");
                    aVar.addObserver(this);
                } else if (!h(aVar)) {
                    ac.a("BeiZis", aVar.g() + " !isWorkerCacheFail , auctionAndRealRequest");
                    j(aVar);
                } else {
                    ac.a("BeiZis", aVar.g() + " cache fail , request candidate , auctionAndRealRequest");
                    arrayList.add(aVar);
                }
            }
        }
        if (!this.f4943r) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                l((com.beizi.fusion.work.a) it2.next());
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            com.beizi.fusion.work.a aVar2 = (com.beizi.fusion.work.a) it3.next();
            if (aVar2.m() != null) {
                a(aVar2.m().getComponent(), aVar2.g(), true, R2.drawable.ic_coupon_middle, 0);
            }
        }
        arrayList2.clear();
        arrayList.clear();
    }

    private boolean c(AdSpacesBean.ForwardBean forwardBean) {
        if (forwardBean == null) {
            return false;
        }
        List<AdSpacesBean.ForwardBean> list = this.ab;
        if (list != null && list.size() > 0) {
            Iterator<AdSpacesBean.ForwardBean> it = this.ab.iterator();
            while (it.hasNext()) {
                if (it.next().getBuyerId().equalsIgnoreCase(forwardBean.getBuyerId())) {
                    return true;
                }
            }
        }
        List<AdSpacesBean.ForwardBean> list2 = this.ac;
        if (list2 != null && list2.size() > 0) {
            Iterator<AdSpacesBean.ForwardBean> it2 = this.ac.iterator();
            while (it2.hasNext()) {
                if (it2.next().getBuyerId().equalsIgnoreCase(forwardBean.getBuyerId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d(AdSpacesBean.BuyerBean buyerBean) {
        return f(buyerBean) || e(buyerBean);
    }

    public void d(String str) {
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar != null) {
            if (aVar instanceof AdListener) {
                ((AdListener) aVar).onAdClicked();
            } else if (aVar instanceof RewardedVideoAdListener) {
                ((RewardedVideoAdListener) aVar).onRewardedVideoClick();
            } else if (aVar instanceof FullScreenVideoAdListener) {
                ((FullScreenVideoAdListener) aVar).onAdClick();
            } else if (aVar instanceof NativeAdListener) {
                ((NativeAdListener) aVar).onAdClick();
            } else if (aVar instanceof BannerAdListener) {
                ((BannerAdListener) aVar).onAdClick();
            } else if (aVar instanceof DrawAdListener) {
                ((DrawAdListener) aVar).onAdClick();
            } else if (aVar instanceof InterstitialAdListener) {
                ((InterstitialAdListener) aVar).onAdClick();
            } else if (aVar instanceof NativeNotificationAdListener) {
                ((NativeNotificationAdListener) aVar).onAdClick();
            } else if (aVar instanceof NativeFloatAdListener) {
                ((NativeFloatAdListener) aVar).onAdClick();
            } else if (aVar instanceof NativeUnifiedAdListener) {
                ((NativeUnifiedAdListener) aVar).onAdClick();
            }
        }
        P();
    }

    public void j() {
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar == null || !(aVar instanceof RewardedVideoAdListener)) {
            return;
        }
        ((RewardedVideoAdListener) aVar).onRewarded();
    }

    public void c(String str) {
        if (this.ae == this.af) {
            this.ae = this.ag;
        }
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar != null) {
            if (aVar instanceof AdListener) {
                ((AdListener) aVar).onAdClosed();
            } else if (aVar instanceof RewardedVideoAdListener) {
                ((RewardedVideoAdListener) aVar).onRewardedVideoAdClosed();
            } else if (aVar instanceof FullScreenVideoAdListener) {
                ((FullScreenVideoAdListener) aVar).onAdClosed();
            } else if (aVar instanceof NativeAdListener) {
                ((NativeAdListener) aVar).onAdClosed();
            } else if (aVar instanceof BannerAdListener) {
                ((BannerAdListener) aVar).onAdClosed();
            } else if (aVar instanceof InterstitialAdListener) {
                ((InterstitialAdListener) aVar).onAdClosed();
            } else if (aVar instanceof NativeNotificationAdListener) {
                ((NativeNotificationAdListener) aVar).onAdClosed();
            } else if (aVar instanceof NativeFloatAdListener) {
                ((NativeFloatAdListener) aVar).onAdClosed();
            }
        }
        Q();
    }

    private String j(String str) {
        return com.beizi.fusion.f.b.b(str);
    }

    private void l(String str) {
        Map<String, com.beizi.fusion.work.a> mapP = p();
        ac.b("BeiZis", "enter removeChannelByIterator buyer = " + str + ",and workerList = " + mapP);
        Iterator<Map.Entry<String, com.beizi.fusion.work.a>> it = mapP.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, com.beizi.fusion.work.a> next = it.next();
            if (next.getKey().equals(str)) {
                ac.a("BeiZis", "channel == ---removeChannelByIterator---" + next.getKey() + "---" + m(next.getValue()));
                if (next.getKey().equals("GDT") || next.getKey().equals("BAIDU")) {
                    next.getValue().f(1);
                }
                o(next.getValue());
                it.remove();
            }
        }
        ac.b("BeiZis", "enter removeChannelByIterator after buyer = " + str + ",and workerList = " + mapP);
    }

    private void d(int i2) {
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            dVar.f4810h.a(i2, 3);
        }
    }

    public void c(int i2) {
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null) {
            dVar.f4810h.a(i2, 4);
        }
    }

    public boolean d(com.beizi.fusion.work.a aVar) {
        return "S2S".equalsIgnoreCase(aVar.h());
    }

    public boolean c(com.beizi.fusion.work.a aVar) {
        return "BPDI".equalsIgnoreCase(aVar.h());
    }

    private void b(String str, com.beizi.fusion.work.a aVar) {
        this.f4935j.put(i(str), aVar);
    }

    private List<AdSpacesBean.ForwardBean> b(List<AdSpacesBean.ForwardBean> list, List<AdSpacesBean.BuyerBean> list2) {
        ArrayList arrayList = new ArrayList();
        for (AdSpacesBean.ForwardBean forwardBean : list) {
            AdSpacesBean.BuyerBean buyerBeanA = com.beizi.fusion.f.b.a(forwardBean.getBuyerId(), list2, forwardBean.getBuyerSpaceUuId());
            if (buyerBeanA != null && "C2S".equals(buyerBeanA.getBidType()) && !a(forwardBean)) {
                arrayList.add(forwardBean);
            }
        }
        return arrayList;
    }

    private boolean b(AdSpacesBean.BuyerBean buyerBean) {
        if (buyerBean != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("isReadyToCache buyer = ");
            sb.append(buyerBean.getId());
            sb.append(" buyerBean.getCache() == 1 ? ");
            sb.append(buyerBean.getCache() == 1);
            sb.append(",!isSameBuyerInBid(buyerBean.getId()) ? ");
            sb.append(!c(buyerBean));
            sb.append(",!isBuyerInWorkerList(buyerBean.getId()) ? ");
            sb.append(!e(buyerBean.getId()));
            ac.a("BeiZis", sb.toString());
        }
        return (buyerBean == null || buyerBean.getCache() != 1 || c(buyerBean) || d(buyerBean)) ? false : true;
    }

    @Override // com.beizi.fusion.d.a.a
    public void b(List<com.beizi.fusion.work.a> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("enter onBidSuccess this = ");
        sb.append(getClass().getName());
        sb.append(",mSpaceBean == null ? ");
        sb.append(this.X == null);
        ac.b("BeiZisBid", sb.toString());
        this.f4943r = false;
    }

    private void b(boolean z2) {
        if (z2 && K()) {
            f();
        }
    }

    private void b(AdSpacesBean.ForwardBean forwardBean) {
        if (this.f4949y == null) {
            return;
        }
        int iA = com.beizi.fusion.f.b.a(forwardBean.getBuyerId());
        AdSpacesBean.BuyerBean buyerBeanA = a(forwardBean.getBuyerId(), this.N, forwardBean.getBuyerSpaceUuId());
        if (iA == 1013) {
            ac.b("BeiZis", "1013 enter updateChannelEventBeanFirst buyerBean = " + buyerBeanA);
        }
        this.aa.a(iA, buyerBeanA, forwardBean);
    }

    public synchronized void b(c cVar) {
        if (cVar != null) {
            this.f4937l.remove(cVar);
        }
    }

    public void b(String str) {
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar != null && (aVar instanceof BannerAdListener)) {
            ((BannerAdListener) aVar).onAdShown();
        } else if (this.B >= 2 && !k(str)) {
            return;
        }
        if (this.ae == 0) {
            this.ae = this.af;
        }
        Log.d("BeiZis", "AdShow:" + str);
        this.B = 2;
        com.beizi.fusion.a aVar2 = this.f4933h;
        if (aVar2 != null) {
            if (aVar2 instanceof AdListener) {
                ((AdListener) aVar2).onAdShown();
            } else if (aVar2 instanceof RewardedVideoAdListener) {
                ((RewardedVideoAdListener) aVar2).onRewardedVideoAdShown();
            } else if (aVar2 instanceof FullScreenVideoAdListener) {
                ((FullScreenVideoAdListener) aVar2).onAdShown();
            } else if (aVar2 instanceof DrawAdListener) {
                ((DrawAdListener) aVar2).onAdShown();
            } else if (aVar2 instanceof NativeAdListener) {
                ((NativeAdListener) aVar2).onAdShown();
            } else if (aVar2 instanceof InterstitialAdListener) {
                ((InterstitialAdListener) aVar2).onAdShown();
            } else if (aVar2 instanceof NativeNotificationAdListener) {
                ((NativeNotificationAdListener) aVar2).onAdShown();
            } else if (aVar2 instanceof NativeFloatAdListener) {
                ((NativeFloatAdListener) aVar2).onAdShown();
            } else if (aVar2 instanceof NativeUnifiedAdListener) {
                ((NativeUnifiedAdListener) aVar2).onAdShown();
            }
        }
        n();
        i();
    }

    @Override // com.beizi.fusion.d.a
    public void a(String str) {
        ac.c("BeiZis", "enter handleAdRequestStatusError error is " + str);
        b(R2.drawable.ic_combine_record_night);
    }

    public void a(String str, final String str2, final EventItem eventItem) {
        if (Arrays.asList(com.beizi.fusion.g.p.f5234a).contains(str)) {
            com.beizi.fusion.g.w.a(str2, eventItem);
            com.beizi.fusion.g.h.b().c().execute(new Runnable() { // from class: com.beizi.fusion.d.e.5
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        com.beizi.fusion.a.b.a(e.this.f4927b).insert(str2, eventItem);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
    }

    public void a(AdSpacesBean adSpacesBean) {
        if (this.L.f4804b.a() == 3 && this.L.f4805c.a() == 0) {
            this.L.f4805c.a(1);
            this.f4935j.clear();
            AdSpacesBean.FilterBean filter = adSpacesBean.getFilter();
            this.N = adSpacesBean.getBuyer();
            com.beizi.fusion.f.b.a(this.f4927b, this.f4931f, filter, this.L, h(), null, null, this);
            if (this.L.f4805c.a() == 2) {
                if (this.L.c()) {
                    this.L.f4805c.a(-1);
                    return;
                }
                a("200.000", this.f4939n, new EventItem("200.000", String.valueOf(System.currentTimeMillis()), null, null));
                this.X = adSpacesBean;
                if (adSpacesBean.getComponent() != null) {
                    a(this.X.getComponent(), this.N);
                }
                ac.c("BeiZis", "normal request");
                a(this.X.getComponent());
                return;
            }
            a(this.L.f4805c.a(), 6, "platform error = ");
            Log.d("BeiZis", "AdDispenses AdFilter fail:" + com.beizi.fusion.b.a.a(this.L.f4805c));
            b(R2.drawable.ic_combine_record_new_day);
            return;
        }
        this.L.f4805c.a(-2);
        a("kPlatformFilterStatusInternalError");
    }

    public void b(int i2) {
        if (this.B >= 2) {
            return;
        }
        i(this.f4934i);
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar != null) {
            if (aVar instanceof AdListener) {
                ((AdListener) aVar).onAdFailedToLoad(i2);
            } else if (aVar instanceof RewardedVideoAdListener) {
                ((RewardedVideoAdListener) aVar).onRewardedVideoAdFailedToLoad(i2);
            } else if (aVar instanceof FullScreenVideoAdListener) {
                ((FullScreenVideoAdListener) aVar).onAdFailed(i2);
            } else if (aVar instanceof NativeAdListener) {
                ((NativeAdListener) aVar).onAdFailed(i2);
            } else if (aVar instanceof BannerAdListener) {
                ((BannerAdListener) aVar).onAdFailed(i2);
            } else if (aVar instanceof DrawAdListener) {
                ((DrawAdListener) aVar).onAdFailed(i2);
            } else if (aVar instanceof InterstitialAdListener) {
                ((InterstitialAdListener) aVar).onAdFailed(i2);
            } else if (aVar instanceof NativeNotificationAdListener) {
                ((NativeNotificationAdListener) aVar).onAdFailed(i2);
            } else if (aVar instanceof NativeFloatAdListener) {
                ((NativeFloatAdListener) aVar).onAdFailed(i2);
            } else if (aVar instanceof NativeUnifiedAdListener) {
                ((NativeUnifiedAdListener) aVar).onAdFailed(i2);
            }
        }
        B();
        this.B = 4;
        o();
        f4926v = true;
    }

    public void a(AdSpacesBean.ComponentBean componentBean) {
        ac.c("BeiZis", "enter auctionAndRequestAd");
        if (this.N == null) {
            ac.c("BeiZis", "mBuyerBeanList == null ");
        } else {
            b(componentBean);
        }
    }

    public void a(List<AdSpacesBean.ForwardBean> list) {
        ac.c("BeiZis", "enter generateWorkers");
        if (list == null) {
            return;
        }
        ac.a("BeiZis", "generateWorkers forwardBeans.size() = " + list.size());
        for (int i2 = 0; i2 < list.size(); i2++) {
            AdSpacesBean.ForwardBean forwardBean = list.get(i2);
            String buyerId = forwardBean.getBuyerId();
            AdSpacesBean.BuyerBean buyerBeanA = a(buyerId, this.N, forwardBean.getBuyerSpaceUuId());
            if (buyerBeanA != null) {
                ac.c("BeiZis", "generateWorkers tmpBuyerId = " + buyerId + ",isBuyerInCacheWorkerList(tmpBuyerId) = " + f(buyerId));
            }
            if (buyerBeanA != null) {
                if (!f(buyerId)) {
                    if (e(buyerId)) {
                        ac.a("BeiZis", "generateWorkers " + buyerId + " already in workerList");
                    } else {
                        com.beizi.fusion.work.a aVarA = a(forwardBean, buyerId, buyerBeanA, buyerBeanA.getRenderView(), (com.beizi.fusion.work.a) null);
                        if (aVarA != null) {
                            aVarA.a(forwardBean.getSleepTime());
                            b(buyerId, aVarA);
                            ac.a("BeiZis", "generateWorkers put new " + buyerId + " worker into workerList");
                        }
                    }
                } else {
                    com.beizi.fusion.work.a aVarH = h(buyerId);
                    if (aVarH != null) {
                        b(buyerId, aVarH);
                        ac.a("BeiZis", "generateWorkers put cached " + buyerId + " worker into workerList");
                    }
                }
            }
        }
        ac.c("BeiZis", "after generateWorkers mWorkerList = " + this.f4935j);
    }

    public void b(String str, View view) {
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar != null && view != null && (aVar instanceof NativeAdListener)) {
            ((NativeAdListener) aVar).onAdClosed(view);
        }
        Q();
    }

    public h b(com.beizi.fusion.work.a aVar) {
        String str;
        String str2;
        String str3;
        double d3;
        AdSpacesBean.BuyerBean buyerBeanN;
        com.beizi.fusion.work.a aVar2 = aVar;
        if (k(aVar.g())) {
            a(aVar2, aVar.g());
            e(com.beizi.fusion.f.b.a(aVar.g()));
            return null;
        }
        ac.a("BeiZis", "enter comparePrices");
        AdSpacesBean.BuyerBean buyerBeanN2 = aVar.n();
        if (buyerBeanN2 == null) {
            return null;
        }
        Map<String, com.beizi.fusion.work.a> mapP = p();
        String id = buyerBeanN2.getId();
        int iA = com.beizi.fusion.f.b.a(id);
        ac.a("BeiZis", "comparePrices channel:" + iA);
        com.beizi.fusion.b.d dVar = this.L;
        if (dVar != null && dVar.f4812j.a() == 2) {
            this.L.f4812j.a(iA, 7);
            a(id, R2.drawable.ic_combineq_question_bg_empty_day);
            return f(iA);
        }
        if (this.C == 1 && this.f4934i == null) {
            return a(iA, aVar2, id);
        }
        ac.a("BeiZis", "workerList = " + mapP + ",isBuyerInWorkerList(buyerId) ? " + e(id));
        if (e(id)) {
            String zone = buyerBeanN2.getZone();
            ac.a("BeiZis", "channel " + iA + " enter comparePrices containsKey zone = " + zone);
            com.beizi.fusion.b.d dVar2 = this.L;
            if (dVar2 != null && (dVar2.f4809g.b(iA) == 4 || this.L.f4809g.b(iA) == 18)) {
                this.L.f4810h.a(iA, 1);
                if (zone.equals("HPFRW")) {
                    double dM = m(aVar);
                    StringBuilder sb = new StringBuilder();
                    String str4 = "channel == ";
                    sb.append("channel == ");
                    sb.append(iA);
                    sb.append(",compareWorkerTag = ");
                    sb.append(aVar.g());
                    sb.append(", avgPrice == ");
                    sb.append(aVar.n().getAvgPrice());
                    sb.append(", bidPrice == ");
                    sb.append(aVar.n().getBidPrice());
                    sb.append(", bidType == ");
                    sb.append(aVar.h());
                    sb.append(" Size:");
                    sb.append(mapP.values().size());
                    ac.a("BeiZis", sb.toString());
                    ArrayList<String> arrayList = new ArrayList<>();
                    double d4 = 0.0d;
                    for (com.beizi.fusion.work.a aVar3 : mapP.values()) {
                        if (aVar.g().equals(aVar3.g()) || (buyerBeanN = aVar3.n()) == null) {
                            str2 = id;
                            str3 = str4;
                            d3 = dM;
                        } else {
                            d3 = dM;
                            double dM2 = m(aVar3);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str4);
                            sb2.append(iA);
                            sb2.append(",workerTag == ");
                            sb2.append(aVar3.g());
                            sb2.append(",avgPrice == ");
                            str2 = id;
                            str3 = str4;
                            sb2.append(buyerBeanN.getAvgPrice());
                            sb2.append(",bidPrice == ");
                            sb2.append(buyerBeanN.getBidPrice());
                            sb2.append(",bidType == ");
                            sb2.append(aVar3.h());
                            sb2.append(",aAdStatus == ");
                            sb2.append(aVar3.k());
                            ac.a("BeiZis", sb2.toString());
                            double dMax = Math.max(d4, dM2);
                            com.beizi.fusion.f.a aVarK = aVar3.k();
                            if (aVarK == com.beizi.fusion.f.a.ADSHOW) {
                                this.H = true;
                            }
                            if (m(aVar) >= m(aVar3)) {
                                if (!a(buyerBeanN) || aVarK != com.beizi.fusion.f.a.ADDEFAULT) {
                                    arrayList.add(aVar3.g());
                                }
                            } else if (aVarK == com.beizi.fusion.f.a.ADLOAD) {
                                arrayList.add(aVar.g());
                            }
                            d4 = dMax;
                        }
                        dM = d3;
                        id = str2;
                        str4 = str3;
                    }
                    String str5 = id;
                    a(arrayList);
                    ac.b("BeiZis", "enter comparePrices price = " + dM + ",highestPrice = " + d4);
                    if (p().size() == 1 && this.J) {
                        if (!this.H) {
                            String str6 = str5;
                            for (com.beizi.fusion.work.a aVar4 : p().values()) {
                                if (aVar4 != aVar2 && aVar2.n() != null) {
                                    String id2 = aVar4.n().getId();
                                    int iA2 = com.beizi.fusion.f.b.a(id2);
                                    str6 = id2;
                                    aVar2 = aVar4;
                                    iA = iA2;
                                }
                            }
                            if (aVar2.k() == com.beizi.fusion.f.a.ADDEFAULT) {
                                this.L.f4810h.a(iA, 2);
                                return a(iA, aVar2);
                            }
                            return a(iA, aVar2, i(str6));
                        }
                        str = str5;
                    } else {
                        this.L.f4810h.a(iA, 2);
                        return a(iA, aVar2);
                    }
                } else {
                    if (zone.equals("FRW")) {
                        return a(iA, aVar2, i(id));
                    }
                    com.beizi.fusion.b.d dVar3 = this.L;
                    if (dVar3 != null) {
                        dVar3.f4810h.a(iA, 6);
                    }
                    if (aVar.m() != null) {
                        a(aVar.m().getComponent(), aVar.g(), true, -991, 0);
                    }
                    return f(iA);
                }
            } else {
                com.beizi.fusion.b.d dVar4 = this.L;
                if (dVar4 != null) {
                    dVar4.f4810h.a(iA, -2);
                }
                a(i(id), R2.drawable.ic_combine_record_night);
                return f(iA);
            }
        } else {
            str = id;
        }
        a(i(str), R2.drawable.ic_combineq_question_bg_empty_day);
        return f(iA);
    }

    private void a(String str, com.beizi.fusion.work.a aVar) {
        this.f4940o.put(i(str), aVar);
    }

    private void a(AdSpacesBean.BidComponent bidComponent, List<AdSpacesBean.BuyerBean> list, int i2) {
        ac.a("BeiZisBid", "startBid bidTimeOut = " + i2);
        if (bidComponent == null || list == null || list.size() == 0 || i2 <= 0) {
            return;
        }
        ac.a("BeiZisBid", "startBid bidComponent = " + bidComponent);
        List<AdSpacesBean.ForwardBean> listA = com.beizi.fusion.f.c.a().a(bidComponent, list);
        ac.a("BeiZisBid", "startBid filteredForwardList size = " + listA.size());
        if (listA.size() == 0) {
            return;
        }
        com.beizi.fusion.d.a.b bVar = new com.beizi.fusion.d.a.b(this.f4927b, this.f4939n, this.f4929d, this.f4932g, this.f4933h, this.f4931f);
        this.al = bVar;
        bVar.a(this.L);
        this.al.a(this.ab, list, i2, this, (com.beizi.fusion.d.a.c) this);
        this.W = this.al.a(this.ac, list, i2, this, (com.beizi.fusion.d.a.a) this);
        J();
        this.f4943r = true;
        ac.a("BeiZisBid", "after bidForward filteredForwardList.size() = " + listA.size());
    }

    private List<AdSpacesBean.ForwardBean> a(List<AdSpacesBean.ForwardBean> list, List<AdSpacesBean.BuyerBean> list2) {
        ArrayList arrayList = new ArrayList();
        for (AdSpacesBean.ForwardBean forwardBean : list) {
            AdSpacesBean.BuyerBean buyerBeanA = com.beizi.fusion.f.b.a(forwardBean.getBuyerId(), list2, forwardBean.getBuyerSpaceUuId());
            if (buyerBeanA != null && "S2S".equals(buyerBeanA.getBidType())) {
                arrayList.add(forwardBean);
            }
        }
        return arrayList;
    }

    private boolean a(AdSpacesBean.ForwardBean forwardBean) {
        List<AdSpacesBean.ForwardBean> list = this.ab;
        boolean z2 = false;
        if (list != null && list.size() > 0) {
            Iterator<AdSpacesBean.ForwardBean> it = this.ab.iterator();
            while (it.hasNext()) {
                if (it.next().getBuyerId().equalsIgnoreCase(forwardBean.getBuyerId())) {
                    z2 = true;
                }
            }
        }
        return z2;
    }

    @Override // com.beizi.fusion.d.a.c
    public void a(com.beizi.fusion.work.a aVar) {
        if (aVar != null && aVar.n() != null) {
            b(aVar.n().getId(), aVar);
        } else {
            List<AdSpacesBean.ForwardBean> list = this.ab;
            if (list != null && list.size() > 0) {
                this.ab.clear();
            }
        }
        this.J = true;
    }

    private boolean a(AdSpacesBean.ComponentBean componentBean, List<AdSpacesBean.BuyerBean> list) {
        if (componentBean == null || list == null || list.size() == 0) {
            return false;
        }
        this.f4941p = false;
        List<AdSpacesBean.ForwardBean> listA = com.beizi.fusion.f.b.a(componentBean, list, h());
        ac.a("BeiZis", "forward cache list size = " + listA.size());
        boolean z2 = false;
        for (int i2 = 0; i2 < listA.size(); i2++) {
            AdSpacesBean.ForwardBean forwardBean = listA.get(i2);
            String buyerId = forwardBean.getBuyerId();
            AdSpacesBean.BuyerBean buyerBeanA = a(buyerId, list, forwardBean.getBuyerSpaceUuId());
            if (buyerBeanA != null) {
                ac.a("BeiZis", "buyerBean = " + buyerBeanA + ",buyerBean.getCache() = " + buyerBeanA.getCache());
            }
            if (b(buyerBeanA)) {
                if (forwardBean.getComponent() == null || forwardBean.getComponent().getForward() == null || forwardBean.getComponent().getForward().size() == 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("enter deepest level tempForward.getComponent() == null ? ");
                    sb.append(forwardBean.getComponent() == null);
                    ac.a("BeiZis", sb.toString());
                    this.f4941p = true;
                }
                ac.a("BeiZis", "buyerId = " + buyerBeanA.getId() + ",spaceId = " + buyerBeanA.getSpaceId() + ",start cache");
                com.beizi.fusion.work.a aVarA = a(forwardBean, buyerId, buyerBeanA, buyerBeanA.getRenderView(), (com.beizi.fusion.work.a) null);
                aVarA.a(1);
                aVarA.b(1);
                aVarA.d();
                aVarA.b(true);
                this.P.add(aVarA.n());
                ac.a("BeiZis", "startCache requestAd worker = " + aVarA + ",isDeepestLevelWorker ? " + this.f4941p + ",isStillNeedObserver = " + this.f4942q);
                if (!this.f4943r && (this.f4941p || this.f4942q)) {
                    ac.a("BeiZis", "startCache put " + buyerBeanA.getId() + " worker into workerList");
                    b(buyerBeanA.getId(), aVarA);
                    aVarA.addObserver(this);
                }
                a(buyerBeanA.getId(), aVarA);
                z2 = true;
            } else {
                if (buyerBeanA != null) {
                    ac.a("BeiZis", "buyer " + buyerBeanA.getId() + " can not cache , try cache candidate");
                }
                if (forwardBean.getComponent() != null) {
                    a(forwardBean.getComponent(), list);
                }
            }
        }
        return z2;
    }

    private List<AdSpacesBean.ForwardBean> a(AdSpacesBean.ComponentBean componentBean, List<AdSpacesBean.BuyerBean> list, boolean z2) {
        AdSpacesBean adSpacesBean;
        AdSpacesBean adSpacesBean2;
        ac.c("BeiZis", "enter startAuction");
        if (this.K && (adSpacesBean2 = this.f4949y) != null && adSpacesBean2.getBidComponent() != null && list != null && list.size() > 0) {
            List<AdSpacesBean.ForwardBean> listA = com.beizi.fusion.f.c.a().a(this.f4949y.getBidComponent(), list);
            if (listA.size() > 0) {
                this.ab = a(listA, list);
                this.ac = b(listA, list);
                if (this.ab.size() > 0) {
                    this.J = false;
                }
            }
        }
        if (componentBean == null) {
            ac.c("BeiZis", "enter startAuction componentBean == null");
            if (this.K && (adSpacesBean = this.f4949y) != null && adSpacesBean.getBid() != null && this.f4949y.getBidComponent() != null) {
                ac.c("BeiZisBid", "startBid");
                a(this.f4949y.getBidComponent(), this.N, this.f4949y.getBid().getBidTime());
            } else {
                b(z2);
            }
            return null;
        }
        ac.a("BeiZis", "mManagerObserver.mPlatformFilterStatus.getStatus() = " + this.L.f4805c.a());
        if (this.L.f4805c.a() == 2) {
            this.L.f4806d.a(1);
            List<AdSpacesBean.ForwardBean> listA2 = com.beizi.fusion.f.b.a(componentBean, list, h());
            ac.c("BeiZis", "after AdForward list.size() = " + listA2.size());
            Iterator<AdSpacesBean.ForwardBean> it = listA2.iterator();
            while (it.hasNext()) {
                AdSpacesBean.ForwardBean next = it.next();
                int iA = com.beizi.fusion.f.b.a(next.getBuyerId());
                String strI = i(next.getBuyerId());
                ac.c("BeiZis", "forward success channel = " + iA);
                if (iA != -1) {
                    b(next);
                    ac.c("BeiZis", "mForwardSuccessBuyerSet.contains(forwardBean.getBuyerId()) = " + this.f4936k.contains(strI));
                    if (this.f4936k.contains(strI)) {
                        this.L.f4807e.a(iA, 9);
                        it.remove();
                    } else {
                        this.L.f4807e.a(iA, 1);
                        this.f4936k.add(strI);
                        ac.b("BeiZis", "test222 mForwardSuccessBuyerSet add " + strI);
                    }
                }
            }
            ac.a("BeiZis", "after arrange list.size() = " + listA2.size());
            if (listA2.size() > 0) {
                this.L.f4806d.a(2);
                if (this.L.f4812j.a() == 2) {
                    this.L.f4806d.a(4);
                    return null;
                }
                if (this.L.c()) {
                    this.L.f4806d.a(-1);
                    return null;
                }
                if (this.K && this.f4949y.getBid() != null && this.f4949y.getBidComponent() != null) {
                    ac.c("BeiZisBid", "startBid");
                    a(this.f4949y.getBidComponent(), this.N, this.f4949y.getBid().getBidTime());
                }
                Iterator<AdSpacesBean.ForwardBean> it2 = listA2.iterator();
                while (it2.hasNext()) {
                    if (!a(it2.next(), z2, false)) {
                        it2.remove();
                    }
                }
                return listA2;
            }
            if (this.K && this.f4949y.getBid() != null && this.f4949y.getBidComponent() != null) {
                ac.c("BeiZisBid", "startBid");
                a(this.f4949y.getBidComponent(), this.N, this.f4949y.getBid().getBidTime());
            } else {
                b(z2);
            }
        } else {
            this.L.f4806d.a(-2);
            a("kForwardChannelStatusInternalError");
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str, int i2) {
        Iterator<Map.Entry<String, com.beizi.fusion.work.a>> it = p().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, com.beizi.fusion.work.a> next = it.next();
            if (!next.getKey().equals(str)) {
                ac.a("BeiZis", "channel == ---removeOtherChannels---" + next.getKey() + "---" + m(next.getValue()));
                if (next.getKey().equals("GDT") || next.getKey().equals("BAIDU")) {
                    next.getValue().f(i2);
                }
                o(next.getValue());
                it.remove();
            }
        }
    }

    public void b(com.beizi.fusion.d.a.a aVar) {
        this.Y = aVar;
    }

    public boolean a(AdSpacesBean.ForwardBean forwardBean, boolean z2, boolean z3) {
        if (forwardBean == null) {
            return false;
        }
        if (!z3 && c(forwardBean)) {
            return false;
        }
        b(forwardBean);
        String buyerId = forwardBean.getBuyerId();
        String strJ = j(buyerId);
        int i2 = strJ != null ? Integer.parseInt(strJ) : -1;
        AdSpacesBean.BuyerBean buyerBeanA = a(buyerId, this.N, forwardBean.getBuyerSpaceUuId());
        ac.a("BeiZis", "AdDispense: buyerBean = " + buyerBeanA + ",mManagerObserver.mChannelFilterStatus.getStatus(channel) = " + this.L.f4807e.b(i2));
        if (i2 != -1 && (this.L.f4807e.b(i2) == 1 || this.L.f4807e.b(i2) == 3 || this.L.f4807e.b(i2) == 9 || a(buyerBeanA))) {
            Log.d("BeiZis", "AdDispense:" + buyerId);
            AdSpacesBean.ComponentBean component = forwardBean.getComponent();
            if (buyerBeanA != null) {
                this.L.f4807e.a(i2, 2);
                com.beizi.fusion.f.b.a(this.f4927b, this.f4931f, buyerBeanA.getFilter(), this.L, h(), buyerBeanA.getId(), buyerBeanA.getSpaceId(), this);
                if (this.L.f4807e.b(i2) == 3) {
                    if (this.L.c()) {
                        this.L.f4807e.a(i2, -1);
                        return false;
                    }
                    return a(forwardBean, buyerId, buyerBeanA);
                }
                a(this.L.f4807e.b(i2), 7, "channel error = ");
                Log.d("BeiZis", "AdDispense buyerBean AdFilter fail:" + com.beizi.fusion.b.a.a(i2, this.L.f4807e));
                a(component, i(buyerId), z2, -991, 0);
                return false;
            }
            a(component, i(buyerId), z2, -991, 0);
            a(strJ, i2, a(buyerId, this.N));
            return false;
        }
        this.L.f4807e.a(i2, -2);
        return false;
    }

    @Nullable
    public AdSpacesBean.BuyerBean a(String str, List<AdSpacesBean.BuyerBean> list, String str2) {
        return com.beizi.fusion.f.b.a(str, list, str2);
    }

    public boolean a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean) {
        int iA = a(forwardBean, buyerBean);
        ac.c("BeiZis", "enter requestAdOrFail buyerIdString = " + str + ",and canRequestAdStatus = " + iA);
        if (iA == 2) {
            return true;
        }
        if (iA == 4) {
            a(str, R2.drawable.ic_combine_question_loading2);
            return false;
        }
        if (iA == 3) {
            a(str, R2.drawable.ic_cut_question_success_anim_day_2);
            return false;
        }
        if (iA == -1) {
            a(str, R2.drawable.homepage_shangcheng_press);
            return false;
        }
        if (iA != 5) {
            return false;
        }
        a(true, this.f4935j, -991);
        return false;
    }

    public void a(int i2, int i3, String str) {
        if (i2 != i3 || com.beizi.fusion.g.w.c(this.f4939n) == null) {
            return;
        }
        com.beizi.fusion.g.w.d(str + com.beizi.fusion.g.w.c(this.f4939n).toString());
    }

    @NonNull
    public String a(String str, List<AdSpacesBean.BuyerBean> list) {
        StringBuilder sb;
        if (list != null) {
            sb = new StringBuilder();
            sb.append(StrPool.BRACKET_START);
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (i2 != list.size() - 1) {
                    sb.append(list.get(i2).getId());
                    sb.append(",");
                } else {
                    sb.append(list.get(i2).getId());
                }
            }
            sb.append(StrPool.BRACKET_END);
        } else {
            sb = null;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("buyer = ");
        sb2.append(str);
        sb2.append(",buyerBeans = ");
        sb2.append(sb == null ? "null" : sb.toString());
        String string = sb2.toString();
        Log.d("BeiZis", "AdDispense no buyerId and error = " + string);
        return string;
    }

    public void a(String str, int i2, String str2) {
        com.beizi.fusion.b.b bVar = this.f4928c;
        if (bVar != null) {
            bVar.i(str2);
            if (str != null) {
                this.f4928c.j(str);
            }
            this.aa.a(i2, this.f4928c);
            this.L.f4807e.a(i2, 8);
        }
    }

    private int a(AdSpacesBean.ForwardBean forwardBean, AdSpacesBean.BuyerBean buyerBean) {
        int iA = com.beizi.fusion.f.b.a(forwardBean.getBuyerId());
        if (this.L.f4807e.b(iA) == 3) {
            this.L.f4808f.a(iA, 1);
            if (this.L.f4808f.b(iA) == 1) {
                if (a(forwardBean.getBuyerId(), this.N, forwardBean.getBuyerSpaceUuId()) == null) {
                    this.L.f4808f.a(iA, 3);
                    return 3;
                }
                if (this.f4931f <= (forwardBean.getSleepTime() + System.currentTimeMillis()) - this.M) {
                    this.L.f4808f.a(iA, 4);
                    return 4;
                }
                if (f(buyerBean)) {
                    this.L.f4808f.a(iA, 5);
                    return 5;
                }
                if (this.L.f4812j.a() == 2) {
                    this.L.f4808f.a(iA, 6);
                    return 6;
                }
                ac.a("BeiZis", "mUsableTime = " + this.f4931f + " forwardBean.getSleepTime() + System.currentTimeMillis() - mEnterRequestMethodTime = " + ((forwardBean.getSleepTime() + System.currentTimeMillis()) - this.M) + ",forwardBean.getSleepTime() = " + forwardBean.getSleepTime());
                if (this.L.c()) {
                    this.L.f4808f.a(iA, -1);
                    return -1;
                }
                this.L.f4808f.a(iA, 2);
                return 2;
            }
        }
        this.L.f4808f.a(iA, -2);
        return -2;
    }

    public void a(AdSpacesBean.ComponentBean componentBean, String str, boolean z2, int i2, int i3) {
        boolean zA;
        Map<String, com.beizi.fusion.work.a> mapP = p();
        l(str);
        this.f4936k.remove(str);
        StringBuilder sb = new StringBuilder();
        sb.append("enter fail and componentBean == null ? ");
        sb.append(componentBean == null);
        ac.c("BeiZis", sb.toString());
        if (componentBean == null) {
            if (a(i3)) {
                a(z2, mapP, i2);
            }
            if (p() == null || p().size() != 1) {
                return;
            }
            L();
            return;
        }
        List<AdSpacesBean.ForwardBean> forward = componentBean.getForward();
        ac.c("BeiZis", "fail with candidate workerTag = " + str + ",fail after remove mAdWorkers = " + mapP + ",isReq = " + z2);
        if (forward != null && forward.size() != 0) {
            ac.c("BeiZis", str + " fail list:" + forward.toString());
            if (i3 == 1) {
                ac.c("BeiZis", str + " cache fail , try catch candidate ");
                zA = a(componentBean, this.N);
            } else {
                zA = false;
            }
            if (i3 == 0 || !zA) {
                this.K = false;
                a(componentBean);
                ac.c("BeiZis", str + " fail after auction mAdWorkers.size() = " + mapP.size());
                return;
            }
            return;
        }
        ac.c("BeiZis", str + " fail forwardBeans == null");
        if (a(i3)) {
            a(z2, mapP, i2);
        }
    }

    public boolean a(int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("canCallBackFail isDeepestLevelWorker = ");
        sb.append(this.f4941p);
        sb.append(",!isBiddingAlive = ");
        sb.append(!this.f4943r);
        sb.append(",cacheStatus != 1 ");
        sb.append(i2 != 1);
        ac.a("BeiZis", sb.toString());
        return (this.f4941p && !this.f4943r) || i2 != 1;
    }

    public void a(boolean z2, Map<String, com.beizi.fusion.work.a> map, int i2) {
        ac.c("BeiZis", "checkIfNoCandidate isReq = " + z2);
        if (M()) {
            StringBuilder sb = new StringBuilder();
            sb.append("mAdWorkers.size() == 0 ? ");
            sb.append(map.size() == 0);
            ac.c("BeiZis", sb.toString());
            if (z2 && map.size() == 0) {
                if (i2 == -991) {
                    f();
                } else {
                    b(i2);
                }
            }
        }
    }

    public void a(String str, int i2) {
        String str2;
        ac.a("BeiZis", "mWinBuyer = " + this.ad + ",buyerId = " + str);
        if (str == null || (str2 = this.ad) == null || str.equals(str2)) {
            Map<String, com.beizi.fusion.work.a> mapP = p();
            l(str);
            ac.c("BeiZis", "failWithChannelError workerTag = " + str + ",fail after remove mAdWorkers = " + mapP + ",errorCode = " + i2);
            if (mapP.size() == 0) {
                b(i2);
            }
        }
    }

    public synchronized void a(c cVar) {
        if (cVar != null) {
            this.f4937l.add(cVar);
        }
    }

    public void a(String str, View view) {
        Log.d("BeiZis", "AdLoaded:" + str);
        if (this.f4938m) {
            return;
        }
        i(this.f4934i);
        this.f4938m = true;
        this.B = 1;
        B();
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar != null) {
            if (aVar instanceof AdListener) {
                ((AdListener) aVar).onAdLoaded();
            } else if (aVar instanceof RewardedVideoAdListener) {
                ((RewardedVideoAdListener) aVar).onRewardedVideoAdLoaded();
            } else if (aVar instanceof FullScreenVideoAdListener) {
                ((FullScreenVideoAdListener) aVar).onAdLoaded();
            } else if (aVar instanceof BannerAdListener) {
                ((BannerAdListener) aVar).onAdLoaded();
            } else if (aVar instanceof InterstitialAdListener) {
                ((InterstitialAdListener) aVar).onAdLoaded();
            } else if (aVar instanceof NativeAdListener) {
                if (view != null) {
                    com.beizi.fusion.work.a aVar2 = this.f4934i;
                    if (aVar2 != null && aVar2.aH() == 1) {
                        ((NativeAdListener) this.f4933h).onAdLoaded(null);
                    } else {
                        com.beizi.fusion.work.a aVar3 = this.f4934i;
                        if (aVar3 != null && aVar3.s() != null) {
                            ((NativeAdListener) this.f4933h).onAdLoaded(this.f4934i.s());
                        } else {
                            f();
                        }
                    }
                } else {
                    f();
                }
            } else if (aVar instanceof DrawAdListener) {
                if (view != null) {
                    ((DrawAdListener) aVar).onAdLoaded(view);
                } else {
                    f();
                }
            } else if (aVar instanceof NativeUnifiedAdListener) {
                ((NativeUnifiedAdListener) aVar).onAdLoaded(this.f4934i.aK());
            }
            this.ad = str;
        }
        O();
        f4926v = true;
    }

    public void a(long j2) {
        com.beizi.fusion.a aVar = this.f4933h;
        if (aVar == null || !(aVar instanceof AdListener)) {
            return;
        }
        ((AdListener) aVar).onAdTick(j2);
    }

    private void a(ArrayList<String> arrayList) {
        if (arrayList.size() > 0) {
            Iterator<String> it = arrayList.iterator();
            while (it.hasNext()) {
                String next = it.next();
                com.beizi.fusion.work.a aVarG = g(next);
                if (aVarG != null && aVarG.n() != null) {
                    int iA = com.beizi.fusion.f.b.a(aVarG.n().getId());
                    l(next);
                    c(iA);
                }
            }
        }
    }

    @NonNull
    private h a(int i2, com.beizi.fusion.work.a aVar) {
        com.beizi.fusion.work.a aVarG;
        ac.b("BeiZis", "enter comparePrices CompeteStatus TO_DETERMINE before set currentHighestWorker");
        com.beizi.fusion.work.a aVar2 = this.f4934i;
        String strG = aVar2 != null ? aVar2.g() : null;
        if (aVar != null && aVar.k() == com.beizi.fusion.f.a.ADLOAD) {
            this.f4934i = a(aVar, this.f4934i);
        }
        if (strG != null && aVar != null) {
            if (aVar == this.f4934i) {
                if (e(strG) && (aVarG = g(strG)) != null) {
                    c(com.beizi.fusion.f.b.a(aVarG.g()));
                }
                if (!d(aVar) || aVar.k() != com.beizi.fusion.f.a.ADDEFAULT) {
                    l(strG);
                }
            } else if (!d(aVar) || aVar.k() != com.beizi.fusion.f.a.ADDEFAULT) {
                l(aVar.g());
                c(i2);
            }
        }
        n(this.f4934i);
        B();
        return h.TO_DETERMINE;
    }

    @NonNull
    private h a(int i2, com.beizi.fusion.work.a aVar, String str) {
        if (!this.I) {
            a(aVar, str);
            b();
            B();
            return e(i2);
        }
        a(str, R2.drawable.ic_combineq_question_bg_empty_day);
        return f(i2);
    }

    private void a(com.beizi.fusion.work.a aVar, String str) {
        this.I = true;
        ac.a("BeiZis", "handleCompeteSuccess isCompeteSuccess true");
        ac.a("BeiZis", "channel == ---handleCompeteSuccess---" + str + "---" + m(aVar));
        b(str, 1);
        this.f4934i = aVar;
    }

    private com.beizi.fusion.work.a a(com.beizi.fusion.work.a aVar, com.beizi.fusion.work.a aVar2) {
        return (aVar == null || aVar.n() == null) ? aVar2 : (aVar2 == null || aVar2.n() == null || aVar.n().getAvgPrice() > aVar2.n().getAvgPrice()) ? aVar : aVar2;
    }

    public void a(com.beizi.fusion.a aVar) {
        this.f4933h = aVar;
    }

    public boolean a(AdSpacesBean.BuyerBean buyerBean) {
        if (buyerBean == null) {
            return false;
        }
        return "C2S".equalsIgnoreCase(buyerBean.getBidType()) || "S2S".equalsIgnoreCase(buyerBean.getBidType());
    }

    public void a(com.beizi.fusion.d.a.a aVar) {
        StringBuilder sb = new StringBuilder();
        sb.append("callBackBidResult mBidManager != null ? ");
        sb.append(this.al != null);
        sb.append(",bidListener != null ? ");
        sb.append(aVar != null);
        ac.a("BeiZisBid", sb.toString());
        com.beizi.fusion.d.a.b bVar = this.al;
        if (bVar == null || aVar == null) {
            return;
        }
        bVar.a(aVar);
    }

    public void a(boolean z2) {
        this.f4947w = z2;
    }
}
