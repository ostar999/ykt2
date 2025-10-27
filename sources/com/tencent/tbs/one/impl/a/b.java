package com.tencent.tbs.one.impl.a;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public abstract class b<T> {

    /* renamed from: a, reason: collision with root package name */
    public boolean f21723a;

    /* renamed from: b, reason: collision with root package name */
    public final Object f21724b = new Object();

    /* renamed from: c, reason: collision with root package name */
    public ArrayList<m<T>> f21725c = new ArrayList<>();

    /* renamed from: d, reason: collision with root package name */
    public boolean f21726d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f21727e;

    /* renamed from: f, reason: collision with root package name */
    public int f21728f;

    /* renamed from: g, reason: collision with root package name */
    public T f21729g;

    /* renamed from: h, reason: collision with root package name */
    public int f21730h;

    /* renamed from: i, reason: collision with root package name */
    public String f21731i;

    /* renamed from: j, reason: collision with root package name */
    public Throwable f21732j;

    public abstract void a();

    public final void a(int i2) {
        synchronized (this.f21724b) {
            int i3 = this.f21728f;
            if (i2 - i3 > 2) {
                this.f21728f = i2;
                for (m mVar : (m[]) this.f21725c.toArray(new m[0])) {
                    mVar.a(i3, i2);
                }
            }
        }
    }

    public void a(int i2, String str, Throwable th) {
        synchronized (this.f21724b) {
            this.f21726d = false;
            this.f21728f = 0;
            this.f21730h = i2;
            this.f21731i = str;
            this.f21732j = th;
            m[] mVarArr = (m[]) this.f21725c.toArray(new m[0]);
            this.f21725c.clear();
            for (m mVar : mVarArr) {
                mVar.a(i2, str, th);
            }
        }
    }

    public final void a(m<T> mVar) {
        synchronized (this.f21724b) {
            if (this.f21727e) {
                if (mVar != null) {
                    mVar.a(0, 100);
                    mVar.a(this.f21729g);
                }
                return;
            }
            int i2 = this.f21730h;
            if (i2 != 0) {
                if (mVar != null) {
                    mVar.a(i2, this.f21731i, this.f21732j);
                }
                return;
            }
            if (mVar != null) {
                mVar.a(0, this.f21728f);
                this.f21725c.add(mVar);
            }
            if (this.f21726d) {
                return;
            }
            this.f21726d = true;
            a();
        }
    }

    public void a(T t2) {
        synchronized (this.f21724b) {
            this.f21726d = false;
            this.f21727e = true;
            int i2 = this.f21728f;
            this.f21728f = 100;
            this.f21729g = t2;
            m[] mVarArr = (m[]) this.f21725c.toArray(new m[0]);
            this.f21725c.clear();
            for (m mVar : mVarArr) {
                mVar.a(i2, 100);
                mVar.a(t2);
            }
        }
    }

    public void b() {
        this.f21723a = true;
    }
}
