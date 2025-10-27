package com.beizi.fusion.d.a;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.beizi.fusion.d.a.d;
import com.beizi.fusion.d.a.e;
import com.beizi.fusion.g.aa;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.l;
import com.beizi.fusion.g.z;
import com.beizi.fusion.model.AdSpacesBean;
import com.mobile.auth.gatewayauth.ResultCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class b extends com.beizi.fusion.d.e {
    private boolean A;
    private Timer B;
    private TimerTask C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
    private String I;

    /* renamed from: v, reason: collision with root package name */
    Timer f4823v;

    /* renamed from: w, reason: collision with root package name */
    TimerTask f4824w;

    /* renamed from: x, reason: collision with root package name */
    private List<com.beizi.fusion.work.a> f4825x;

    /* renamed from: y, reason: collision with root package name */
    private boolean f4826y;

    /* renamed from: z, reason: collision with root package name */
    private com.beizi.fusion.work.a f4827z;

    public b(Context context, String str, ViewGroup viewGroup, View view, com.beizi.fusion.a aVar, long j2) {
        super(context, str, aVar, j2);
        this.f4826y = false;
        this.A = false;
        this.D = "1002";
        this.E = "不出价";
        this.F = "1003";
        this.G = ResultCode.MSG_ERROR_NETWORK;
        this.H = "1004";
        this.I = "未找到渠道buyer";
        this.f4929d = viewGroup;
        this.f4932g = view;
    }

    private void B() {
        TimerTask timerTask = this.f4824w;
        if (timerTask != null) {
            timerTask.cancel();
            this.f4824w = null;
        }
        Timer timer = this.f4823v;
        if (timer != null) {
            timer.cancel();
            this.f4823v = null;
        }
    }

    private void C() {
        TimerTask timerTask = this.C;
        if (timerTask != null) {
            timerTask.cancel();
            this.C = null;
        }
        Timer timer = this.B;
        if (timer != null) {
            timer.cancel();
            this.B = null;
        }
    }

    @Override // com.beizi.fusion.d.e
    public com.beizi.fusion.work.a a(AdSpacesBean.ForwardBean forwardBean, String str, AdSpacesBean.BuyerBean buyerBean, List<AdSpacesBean.RenderViewBean> list, com.beizi.fusion.work.a aVar) {
        return null;
    }

    @Override // com.beizi.fusion.d.e
    public void a() {
    }

    @Override // com.beizi.fusion.d.e
    public boolean w() {
        List<com.beizi.fusion.work.a> list = this.f4825x;
        if (list == null) {
            return true;
        }
        boolean z2 = true;
        for (com.beizi.fusion.work.a aVar : list) {
            if (!"BPDI".equalsIgnoreCase(aVar.h())) {
                ac.b("BeiZis", "worker.getBidType() = " + aVar.h() + ",worker.getWorkerAdStatus() = " + aVar.v());
                z2 &= (aVar.v() == 1 || aVar.v() == 0) ? false : true;
            }
        }
        ac.a("BeiZisBid", "isAllNotDefaultOrReq = " + z2);
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(com.beizi.fusion.work.a aVar) {
        ac.c("BeiZisBid", "mAdWorker = " + aVar + " start bid request");
        aVar.d();
        aVar.d(1);
    }

    public List<com.beizi.fusion.work.a> b() {
        return this.f4825x;
    }

    public List<com.beizi.fusion.work.a> c(List<com.beizi.fusion.work.a> list) {
        if (list.size() < 2) {
            return list;
        }
        Collections.sort(list, new Comparator<com.beizi.fusion.work.a>() { // from class: com.beizi.fusion.d.a.b.3
            @Override // java.util.Comparator
            /* renamed from: a, reason: merged with bridge method [inline-methods] */
            public int compare(com.beizi.fusion.work.a aVar, com.beizi.fusion.work.a aVar2) {
                if (aVar != null && aVar2 != null) {
                    AdSpacesBean.BuyerBean buyerBeanN = aVar.n();
                    AdSpacesBean.BuyerBean buyerBeanN2 = aVar2.n();
                    if (buyerBeanN == null || buyerBeanN2 == null || buyerBeanN.getAvgPrice() == buyerBeanN2.getAvgPrice()) {
                        return 0;
                    }
                    return buyerBeanN.getAvgPrice() < buyerBeanN2.getAvgPrice() ? 1 : -1;
                }
                return 0;
            }
        });
        ac.c("BeiZisBid", "after sort list = " + list.toString());
        return list;
    }

    public void b(int i2, com.beizi.fusion.d.e eVar) {
        if (eVar.c() != null) {
            ac.c("BeiZis", "channel " + i2 + " reportS2SRequestTimeOut mManagerObserver.mChannelResultStatus.getStatus(channel) = " + eVar.c().f4809g.b(i2));
            eVar.c().f4809g.a(i2, 19);
        }
    }

    public List<com.beizi.fusion.work.a> a(List<AdSpacesBean.ForwardBean> list, List<AdSpacesBean.BuyerBean> list2, int i2, com.beizi.fusion.d.e eVar, final a aVar) {
        if (i2 > 0 && eVar != null) {
            b(aVar);
            eVar.b(aVar);
            this.f4825x = new ArrayList();
            this.f4824w = new TimerTask() { // from class: com.beizi.fusion.d.a.b.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    for (int i3 = 0; i3 < b.this.f4825x.size(); i3++) {
                        com.beizi.fusion.work.a aVar2 = (com.beizi.fusion.work.a) b.this.f4825x.get(i3);
                        if (aVar2.v() == 1) {
                            aVar2.d(3);
                            aVar2.c(3);
                            aVar2.f(2);
                            aVar2.af();
                        }
                    }
                    b.this.a(aVar);
                }
            };
            Timer timer = new Timer();
            this.f4823v = timer;
            timer.schedule(this.f4824w, i2);
            for (AdSpacesBean.ForwardBean forwardBean : list) {
                String buyerId = forwardBean.getBuyerId();
                AdSpacesBean.BuyerBean buyerBeanA = a(buyerId, list2, forwardBean.getBuyerSpaceUuId());
                if (buyerBeanA != null && "C2S".equalsIgnoreCase(buyerBeanA.getBidType())) {
                    eVar.a(forwardBean, false, true);
                    com.beizi.fusion.work.a aVarA = eVar.a(forwardBean, buyerId, buyerBeanA, buyerBeanA.getRenderView(), (com.beizi.fusion.work.a) null);
                    aVarA.a(buyerBeanA.getBidType());
                    this.f4825x.add(aVarA);
                    f(aVarA);
                }
            }
            return this.f4825x;
        }
        a(aVar);
        return null;
    }

    @Override // com.beizi.fusion.d.e
    public void a(final a aVar) {
        ac.a("BeiZisBid", "enter callBackBidResult isEnterCallBack = " + this.f4826y);
        B();
        this.f4946u.post(new Runnable() { // from class: com.beizi.fusion.d.a.b.2
            @Override // java.lang.Runnable
            public void run() {
                if (b.this.f4825x == null || b.this.f4826y) {
                    return;
                }
                b bVar = b.this;
                bVar.f4825x = bVar.c(bVar.f4825x);
                a aVar2 = aVar;
                if (aVar2 != null) {
                    aVar2.b(b.this.f4825x);
                    b.this.f4826y = true;
                }
            }
        });
    }

    public void a(final List<AdSpacesBean.ForwardBean> list, final List<AdSpacesBean.BuyerBean> list2, final int i2, final com.beizi.fusion.d.e eVar, final c cVar) {
        if (i2 <= 0) {
            ac.c("BeiZis", "S2S竞价失败--超时时间小于0");
            a((String) null, (String) null, (List<String>) null, cVar, eVar);
            return;
        }
        if (!list.isEmpty() && !list2.isEmpty()) {
            for (AdSpacesBean.ForwardBean forwardBean : list) {
                AdSpacesBean.BuyerBean buyerBeanA = com.beizi.fusion.f.b.a(forwardBean.getBuyerId(), list2, forwardBean.getBuyerSpaceUuId());
                if (buyerBeanA != null && "S2S".equals(buyerBeanA.getBidType())) {
                    eVar.a(forwardBean, false, true);
                    a(com.beizi.fusion.f.b.a(buyerBeanA.getId()), eVar);
                }
            }
            l.a().execute(new Runnable() { // from class: com.beizi.fusion.d.a.b.4
                /* JADX WARN: Removed duplicated region for block: B:15:0x008e  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void run() {
                    /*
                        Method dump skipped, instructions count: 282
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.beizi.fusion.d.a.b.AnonymousClass4.run():void");
                }
            });
            return;
        }
        ac.c("BeiZis", "S2S竞价失败-- forwardBean is null or buyer is null");
        a((String) null, (String) null, (List<String>) null, cVar, eVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, final List<String> list, final com.beizi.fusion.d.e eVar, final c cVar) {
        this.C = new TimerTask() { // from class: com.beizi.fusion.d.a.b.5
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    b.this.b(com.beizi.fusion.f.b.a((String) it.next()), eVar);
                }
                b.this.a((String) null, (String) null, (List<String>) list, cVar, eVar);
            }
        };
        Timer timer = new Timer();
        this.B = timer;
        timer.schedule(this.C, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, String str2, List<String> list, final c cVar, com.beizi.fusion.d.e eVar) {
        Log.e("BeiZis", "S2S竞价失败，errorCode：" + str);
        if (!this.A) {
            this.f4946u.post(new Runnable() { // from class: com.beizi.fusion.d.a.b.6
                @Override // java.lang.Runnable
                public void run() {
                    cVar.a(null);
                }
            });
            if (!TextUtils.isEmpty(str) && list != null && list.size() > 0) {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    a(str, str2, com.beizi.fusion.f.b.a(it.next()), eVar);
                }
            }
        }
        C();
        this.A = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List<AdSpacesBean.ForwardBean> list, List<AdSpacesBean.BuyerBean> list2, List<String> list3, List<d.k> list4, int i2, com.beizi.fusion.d.e eVar, c cVar) {
        try {
            boolean zEquals = "1".equals(e());
            String strA = ar.a();
            String strA2 = z.a(this.f4927b, z.a(this.f4927b, strA, z(), zEquals, list3, list4, i2), strA);
            if (TextUtils.isEmpty(strA2)) {
                ac.c("BeiZis", "S2S竞价失败-- response is null");
                a(this.F, this.G, list3, cVar, eVar);
                return;
            }
            JSONObject jSONObject = new JSONObject(com.beizi.fusion.g.d.b(aa.a(), strA2));
            ac.c("BeiZis", "S2S竞价结果--response is " + jSONObject.toString());
            int iOptInt = jSONObject.optInt("code");
            if (iOptInt != 200) {
                ac.c("BeiZis", "S2S竞价失败--response code is " + iOptInt);
                a(this.F, this.G, list3, cVar, eVar);
                return;
            }
            String strOptString = jSONObject.optString("data");
            if (!TextUtils.isEmpty(strOptString) && !strOptString.equals("null")) {
                a(list, list2, strOptString, list3, eVar, cVar);
                return;
            }
            ac.c("BeiZis", "S2S竞价失败--data is null");
            a(this.F, this.G, list3, cVar, eVar);
        } catch (Exception e2) {
            e2.printStackTrace();
            ac.c("BeiZis", "S2S竞价失败--数据异常");
            a((String) null, (String) null, list3, cVar, eVar);
        }
    }

    private void a(List<AdSpacesBean.ForwardBean> list, List<AdSpacesBean.BuyerBean> list2, String str, List<String> list3, com.beizi.fusion.d.e eVar, final c cVar) {
        e.b bVarC;
        if (!this.A && !TextUtils.isEmpty(str)) {
            e eVarA = e.a(str);
            if (eVarA != null && eVarA.a() != null && eVarA.a().size() != 0) {
                AdSpacesBean.BuyerBean buyerBean = null;
                AdSpacesBean.ForwardBean forwardBean = null;
                for (AdSpacesBean.ForwardBean forwardBean2 : list) {
                    Iterator<e.c> it = eVarA.a().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (forwardBean2.getBuyerId().equals(it.next().a())) {
                                forwardBean = forwardBean2;
                                break;
                            }
                        }
                    }
                }
                for (AdSpacesBean.BuyerBean buyerBean2 : list2) {
                    Iterator<e.c> it2 = eVarA.a().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            if (buyerBean2.getId().equals(it2.next().a())) {
                                buyerBean = buyerBean2;
                                break;
                            }
                        }
                    }
                }
                if (forwardBean != null && buyerBean != null) {
                    com.beizi.fusion.work.a aVarA = eVar.a(forwardBean, forwardBean.getBuyerId(), buyerBean, buyerBean.getRenderView(), (com.beizi.fusion.work.a) null);
                    this.f4827z = aVarA;
                    aVarA.a(buyerBean.getBidType());
                    try {
                        List<e.a> listB = eVarA.a().get(0).b();
                        if (listB != null && listB.size() > 0) {
                            this.f4827z.n().setBidPrice(Double.parseDouble(listB.get(0).a()));
                            if ("GDT".equalsIgnoreCase(buyerBean.getId())) {
                                e.d dVarB = listB.get(0).b();
                                if (dVarB != null && !TextUtils.isEmpty(dVarB.a())) {
                                    this.f4827z.b(dVarB.a());
                                }
                            } else if ("KUAISHOU".equalsIgnoreCase(buyerBean.getId()) && (bVarC = eVarA.a().get(0).c()) != null && !TextUtils.isEmpty(bVarC.a())) {
                                this.f4827z.b(bVarC.a());
                            }
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.beizi.fusion.d.a.b.7
                        @Override // java.lang.Runnable
                        public void run() {
                            cVar.a(b.this.f4827z);
                            b bVar = b.this;
                            bVar.f(bVar.f4827z);
                        }
                    });
                } else {
                    ac.c("BeiZis", "S2S竞价失败--tempBuyerBean is null");
                    a(this.H, this.I, list3, cVar, eVar);
                    return;
                }
            } else {
                ac.c("BeiZis", "S2S竞价失败--不出价");
                a(this.D, this.E, list3, cVar, eVar);
                return;
            }
        }
        C();
    }

    public void a(int i2, com.beizi.fusion.d.e eVar) {
        if (eVar.c() != null) {
            ac.c("BeiZis", "channel " + i2 + " reportS2SRequest mManagerObserver.mChannelResultStatus.getStatus(channel) = " + eVar.c().f4809g.b(i2));
            eVar.c().f4809g.a(i2, 18);
        }
    }

    public void a(String str, String str2, int i2, com.beizi.fusion.d.e eVar) {
        if (eVar.c() != null) {
            if (eVar.A() != null) {
                eVar.A().i(str2);
                eVar.A().m(str);
                eVar.c().a().a(i2, eVar.A());
            }
            ac.c("BeiZis", "channel " + i2 + " reportS2SRequestError mManagerObserver.mChannelResultStatus.getStatus(channel) = " + eVar.c().f4809g.b(i2));
            eVar.c().f4809g.a(i2, 21);
            if (eVar.A() != null) {
                eVar.A().i(null);
                eVar.A().m(null);
                eVar.c().a().a(i2, eVar.A());
            }
        }
    }
}
