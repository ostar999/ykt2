package com.beizi.fusion.b;

import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.model.AdSpacesBean;
import java.util.HashMap;
import java.util.Observable;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static SparseArray<String> f4738a = new SparseArray<>();

    /* renamed from: b, reason: collision with root package name */
    public static SparseArray<String> f4739b = new SparseArray<>();

    /* renamed from: c, reason: collision with root package name */
    public static SparseArray<String> f4740c = new SparseArray<>();

    /* renamed from: d, reason: collision with root package name */
    public static SparseArray<String> f4741d = new SparseArray<>();

    /* renamed from: e, reason: collision with root package name */
    public static SparseArray<String> f4742e = new SparseArray<>();

    /* renamed from: f, reason: collision with root package name */
    public static SparseArray<String> f4743f = new SparseArray<>();

    /* renamed from: g, reason: collision with root package name */
    public static SparseArray<String> f4744g = new SparseArray<>();

    /* renamed from: h, reason: collision with root package name */
    public static SparseArray<String> f4745h = new SparseArray<>();

    /* renamed from: i, reason: collision with root package name */
    public static SparseArray<String> f4746i = new SparseArray<>();

    /* renamed from: j, reason: collision with root package name */
    public static SparseArray<String> f4747j = new SparseArray<>();

    /* renamed from: k, reason: collision with root package name */
    public static SparseArray<String> f4748k = new SparseArray<>();

    /* renamed from: l, reason: collision with root package name */
    private HashMap<Integer, Pair<AdSpacesBean.BuyerBean, AdSpacesBean.ForwardBean>> f4749l = new HashMap<>();

    /* renamed from: m, reason: collision with root package name */
    private HashMap<Integer, com.beizi.fusion.b.b> f4750m = new HashMap<>();

    /* renamed from: n, reason: collision with root package name */
    private com.beizi.fusion.b.b f4751n;

    /* renamed from: com.beizi.fusion.b.a$a, reason: collision with other inner class name */
    public class C0060a extends l {
        public C0060a() {
            super();
        }
    }

    public class b extends l {
        public b() {
            super();
        }
    }

    public class c extends l {
        public c() {
            super();
        }
    }

    public class d extends l {
        public d() {
            super();
        }
    }

    public class e extends l {
        public e() {
            super();
        }
    }

    public class f extends l {
        public f() {
            super();
        }
    }

    public class g extends l {
        public g() {
            super();
        }
    }

    public class h extends l {
        public h() {
            super();
        }
    }

    public class i extends l {
        public i() {
            super();
        }
    }

    public class j extends l {
        public j() {
            super();
        }
    }

    public class k extends l {
        public k() {
            super();
        }
    }

    static {
        f4738a.put(1, "100.000");
        f4738a.put(2, "100.200");
        f4739b.put(-1, "210.400");
        f4739b.put(-2, "210.999");
        f4739b.put(1, "200.001");
        f4739b.put(2, "210.100");
        f4739b.put(3, "210.200");
        f4739b.put(4, "210.401");
        f4739b.put(5, "210.402");
        f4739b.put(6, "210.403");
        f4739b.put(7, "210.404");
        f4740c.put(-1, "220.400");
        f4740c.put(-2, "220.999");
        f4740c.put(1, "220.000");
        f4740c.put(2, "200.000");
        f4740c.put(3, "220.401");
        f4740c.put(4, "220.402");
        f4740c.put(5, "220.403");
        f4740c.put(6, "220.404");
        f4741d.put(-1, "230.400");
        f4741d.put(-2, "230.999");
        f4741d.put(1, "230.000");
        f4741d.put(2, "230.200");
        f4741d.put(3, "230.401");
        f4741d.put(4, "230.402");
        f4742e.put(-1, "245.400");
        f4742e.put(-2, "245.999");
        f4742e.put(1, "240.000");
        f4742e.put(2, "245.000");
        f4742e.put(3, "245.200");
        f4742e.put(4, "245.401");
        f4742e.put(5, "245.402");
        f4742e.put(6, "245.403");
        f4742e.put(7, "245.404");
        f4742e.put(8, "245.300");
        f4742e.put(9, "240.100");
        f4743f.put(-1, "249.400");
        f4743f.put(-2, "249.999");
        f4743f.put(1, "248.000");
        f4743f.put(2, "249.000");
        f4743f.put(3, "248.401");
        f4743f.put(4, "248.402");
        f4743f.put(5, "249.401");
        f4743f.put(6, "249.402");
        f4744g.put(-1, "280.600");
        f4744g.put(1, "250.100");
        f4744g.put(2, "250.200");
        f4744g.put(16, "250.401");
        f4744g.put(3, "255.200");
        f4744g.put(4, "280.200");
        f4744g.put(5, "280.280");
        f4744g.put(6, "280.300");
        f4744g.put(12, "280.350");
        f4744g.put(7, "290.300");
        f4744g.put(17, "290.301");
        f4744g.put(8, "280.400");
        f4744g.put(9, "280.450");
        f4744g.put(11, "280.500");
        f4744g.put(13, "280.301");
        f4744g.put(14, "280.302");
        f4744g.put(15, "280.303");
        f4744g.put(18, "250.000");
        f4744g.put(19, "250.400");
        f4744g.put(20, "280.000");
        f4744g.put(21, "250.500");
        f4745h.put(1, "280.210");
        f4745h.put(-2, "280.249");
        f4745h.put(2, "280.220");
        f4745h.put(3, "280.250");
        f4745h.put(4, "280.241");
        f4745h.put(5, "280.240");
        f4745h.put(6, "280.242");
        f4745h.put(7, "280.243");
        f4746i.put(1, "280.261");
        f4746i.put(2, "280.262");
        f4746i.put(3, "280.263");
        f4746i.put(4, "280.264");
        f4748k.put(1, "212.000");
        f4748k.put(2, "212.400");
        f4748k.put(3, "212.401");
        f4748k.put(4, "212.999");
        f4748k.put(5, "214.000");
        f4748k.put(6, "214.200");
        f4748k.put(7, "216.200");
        f4748k.put(8, "216.401");
    }

    private Pair<AdSpacesBean.BuyerBean, AdSpacesBean.ForwardBean> b(int i2) {
        if (this.f4749l.containsKey(Integer.valueOf(i2))) {
            return this.f4749l.get(Integer.valueOf(i2));
        }
        return null;
    }

    public static String a(l lVar) {
        if (lVar instanceof i) {
            return f4738a.get(lVar.a());
        }
        if (lVar instanceof h) {
            return f4739b.get(lVar.a());
        }
        if (lVar instanceof k) {
            return f4740c.get(lVar.a());
        }
        if (lVar instanceof g) {
            return f4741d.get(lVar.a());
        }
        return lVar instanceof j ? f4747j.get(lVar.a()) : "0";
    }

    public class l extends Observable {

        /* renamed from: a, reason: collision with root package name */
        private int f4764a = 0;

        /* renamed from: c, reason: collision with root package name */
        private SparseIntArray f4766c = new SparseIntArray();

        public l() {
        }

        public void a(int i2) {
            if (this.f4764a != i2) {
                this.f4764a = i2;
                com.beizi.fusion.b.b bVarA = a.this.a(a.a(this));
                setChanged();
                notifyObservers(bVarA);
            }
        }

        public int b(int i2) {
            return this.f4766c.get(i2);
        }

        public int a() {
            return this.f4764a;
        }

        public void a(int i2, int i3) {
            if (this.f4766c.get(i2) != i3 || i3 == 17) {
                this.f4766c.put(i2, i3);
                String strA = a.a(i2, this);
                ac.b("BeiZis", "changeStatus channel = " + i2 + ",eventCode = " + strA);
                com.beizi.fusion.b.b bVarA = a.this.a(i2, strA);
                if ("290.300".equalsIgnoreCase(strA)) {
                    ac.b("BeiZis", "eventBean = " + bVarA);
                }
                setChanged();
                notifyObservers(bVarA);
            }
        }
    }

    public static String a(int i2, l lVar) {
        if (lVar instanceof d) {
            return f4742e.get(lVar.b(i2));
        }
        if (lVar instanceof e) {
            return f4743f.get(lVar.b(i2));
        }
        if (lVar instanceof f) {
            return f4744g.get(lVar.b(i2));
        }
        if (lVar instanceof c) {
            return f4745h.get(lVar.b(i2));
        }
        if (lVar instanceof b) {
            return f4746i.get(lVar.b(i2));
        }
        return lVar instanceof C0060a ? f4748k.get(lVar.b(i2)) : "0";
    }

    public void a(com.beizi.fusion.b.b bVar) {
        this.f4751n = bVar;
    }

    public com.beizi.fusion.b.b a(int i2) {
        if (this.f4750m.containsKey(Integer.valueOf(i2))) {
            try {
                com.beizi.fusion.b.b bVar = this.f4750m.get(Integer.valueOf(i2));
                if (bVar != null) {
                    com.beizi.fusion.b.b bVarClone = bVar.clone();
                    this.f4750m.put(Integer.valueOf(i2), bVarClone);
                    return bVarClone;
                }
            } catch (CloneNotSupportedException e2) {
                e2.printStackTrace();
            }
        } else {
            com.beizi.fusion.b.b bVar2 = this.f4751n;
            if (bVar2 != null) {
                try {
                    com.beizi.fusion.b.b bVarClone2 = bVar2.clone();
                    this.f4750m.put(Integer.valueOf(i2), bVarClone2);
                    return bVarClone2;
                } catch (CloneNotSupportedException e3) {
                    e3.printStackTrace();
                }
            }
        }
        com.beizi.fusion.b.b bVar3 = new com.beizi.fusion.b.b(com.beizi.fusion.d.b.f4908b, "", "", "", com.beizi.fusion.d.b.a().b(), "", "", String.valueOf(System.currentTimeMillis()), "");
        this.f4750m.put(Integer.valueOf(i2), bVar3);
        return bVar3;
    }

    public void a(int i2, com.beizi.fusion.b.b bVar) {
        if (i2 == -1 || bVar == null) {
            return;
        }
        this.f4750m.put(Integer.valueOf(i2), bVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.beizi.fusion.b.b a(String str) {
        com.beizi.fusion.b.b bVar = this.f4751n;
        if (bVar == null || str == null) {
            return null;
        }
        try {
            com.beizi.fusion.b.b bVarClone = bVar.clone();
            bVarClone.c(str);
            bVarClone.h(String.valueOf(System.currentTimeMillis()));
            return bVarClone;
        } catch (CloneNotSupportedException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void a(int i2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean) {
        this.f4749l.put(Integer.valueOf(i2), new Pair<>(buyerBean, forwardBean));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.beizi.fusion.b.b a(int i2, String str) {
        Pair<AdSpacesBean.BuyerBean, AdSpacesBean.ForwardBean> pairB = b(i2);
        StringBuilder sb = new StringBuilder();
        sb.append("getChannelEventBean eventCode = ");
        sb.append(str);
        sb.append(",channelData == null ");
        sb.append(pairB == null);
        ac.b("BeiZis", sb.toString());
        if (pairB != null) {
            AdSpacesBean.BuyerBean buyerBean = (AdSpacesBean.BuyerBean) pairB.first;
            AdSpacesBean.ForwardBean forwardBean = (AdSpacesBean.ForwardBean) pairB.second;
            com.beizi.fusion.b.b bVarA = a(i2);
            if (bVarA == null || buyerBean == null || forwardBean == null) {
                return bVarA;
            }
            bVarA.c(str);
            bVarA.h(String.valueOf(System.currentTimeMillis()));
            a(bVarA, i2, buyerBean, forwardBean);
            this.f4750m.put(Integer.valueOf(i2), bVarA);
            return bVarA;
        }
        ac.c("BeiZis", "getChannelEventBean eventCode = " + str + ",but channelData is null !!!");
        return null;
    }

    private static void a(com.beizi.fusion.b.b bVar, int i2, AdSpacesBean.BuyerBean buyerBean, AdSpacesBean.ForwardBean forwardBean) {
        if (bVar == null) {
            return;
        }
        if (i2 == 1013) {
            ac.b("BeiZis", "1013 buyerBean = " + buyerBean);
        }
        bVar.j(String.valueOf(i2));
        if (buyerBean != null) {
            bVar.k(buyerBean.getAppId());
            bVar.l(buyerBean.getSpaceId());
            bVar.D(buyerBean.getFilterSsid());
            bVar.E(buyerBean.getRenderViewSsid());
            bVar.M(String.valueOf(buyerBean.getAvgPrice()));
            bVar.L(com.beizi.fusion.f.b.b(forwardBean.getBaseId()));
            if (!"BPDI".equalsIgnoreCase(buyerBean.getBidType()) && !"C2S".equalsIgnoreCase(buyerBean.getBidType()) && !"S2S".equalsIgnoreCase(buyerBean.getBidType())) {
                if (buyerBean.getCache() == 1) {
                    bVar.a(2);
                } else if (buyerBean.getCache() == 0) {
                    bVar.a(0);
                }
            } else {
                bVar.a(1);
                if (buyerBean.getBidPrice() > 0.0d) {
                    bVar.N(String.valueOf(buyerBean.getBidPrice()));
                } else {
                    bVar.N("0");
                }
            }
            if (i2 == 1012) {
                ac.b("BeiZis", "1012 SrcType = " + bVar.O() + ",price = " + bVar.P());
            }
        }
        if (forwardBean != null) {
            bVar.n(forwardBean.getForwardId());
            bVar.o(forwardBean.getParentForwardId());
            bVar.p(forwardBean.getLevel());
            bVar.q(forwardBean.getBuyerSpaceUuId());
        }
    }
}
