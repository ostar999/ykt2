package com.beizi.fusion.b;

import android.text.TextUtils;
import com.beizi.fusion.b.a;
import com.beizi.fusion.b.a.C0060a;
import com.beizi.fusion.b.a.b;
import com.beizi.fusion.b.a.c;
import com.beizi.fusion.b.a.d;
import com.beizi.fusion.b.a.e;
import com.beizi.fusion.b.a.f;
import com.beizi.fusion.b.a.g;
import com.beizi.fusion.b.a.h;
import com.beizi.fusion.b.a.i;
import com.beizi.fusion.b.a.j;
import com.beizi.fusion.b.a.k;
import com.beizi.fusion.g.ac;
import java.util.Observable;
import java.util.Observer;

/* loaded from: classes2.dex */
public class d implements Observer {

    /* renamed from: a, reason: collision with root package name */
    public a.i f4803a;

    /* renamed from: b, reason: collision with root package name */
    public a.h f4804b;

    /* renamed from: c, reason: collision with root package name */
    public a.k f4805c;

    /* renamed from: d, reason: collision with root package name */
    public a.g f4806d;

    /* renamed from: e, reason: collision with root package name */
    public a.d f4807e;

    /* renamed from: f, reason: collision with root package name */
    public a.e f4808f;

    /* renamed from: g, reason: collision with root package name */
    public a.f f4809g;

    /* renamed from: h, reason: collision with root package name */
    public a.c f4810h;

    /* renamed from: i, reason: collision with root package name */
    public a.b f4811i;

    /* renamed from: j, reason: collision with root package name */
    public a.j f4812j;

    /* renamed from: k, reason: collision with root package name */
    public a.C0060a f4813k;

    /* renamed from: l, reason: collision with root package name */
    private final a f4814l;

    /* renamed from: m, reason: collision with root package name */
    private b f4815m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f4816n = false;

    public d(b bVar) {
        this.f4815m = bVar;
        a aVar = new a();
        this.f4814l = aVar;
        this.f4803a = aVar.new i();
        this.f4804b = aVar.new h();
        this.f4805c = aVar.new k();
        this.f4806d = aVar.new g();
        this.f4807e = aVar.new d();
        this.f4808f = aVar.new e();
        this.f4809g = aVar.new f();
        this.f4810h = aVar.new c();
        this.f4811i = aVar.new b();
        this.f4812j = aVar.new j();
        this.f4813k = aVar.new C0060a();
    }

    public a a() {
        return this.f4814l;
    }

    public b b() {
        return this.f4815m;
    }

    public boolean c() {
        return this.f4816n;
    }

    @Override // java.util.Observer
    public void update(Observable observable, Object obj) {
        if ((observable instanceof a.l) && (obj instanceof b)) {
            b bVar = (b) obj;
            int i2 = !TextUtils.isEmpty(bVar.k()) ? Integer.parseInt(bVar.k()) : -1;
            String strD = bVar.d();
            int iO = bVar.O();
            String strP = bVar.P();
            String strQ = bVar.Q();
            bVar.R();
            ac.a("BeiZis", "channel == " + i2 + ",eventCode = " + strD + ",srcType = " + iO + ",price = " + strP + ",bidPrice = " + strQ + ",eventId = " + bVar.c() + ",adType = " + bVar.e() + ",buyerSpaceId = " + bVar.m());
            c.a(com.beizi.fusion.d.b.a().e()).a(bVar);
        }
    }

    public void a(boolean z2) {
        this.f4816n = z2;
    }
}
