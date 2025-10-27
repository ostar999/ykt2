package com.alibaba.sdk.android.emas;

import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class a implements Cache<e> {

    /* renamed from: a, reason: collision with root package name */
    private int f2680a;

    /* renamed from: a, reason: collision with other field name */
    private List<e> f5a;

    /* renamed from: b, reason: collision with root package name */
    private int f2681b;

    /* renamed from: c, reason: collision with root package name */
    private int f2682c = 0;
    private h mSendManager;

    public a(h hVar, int i2, int i3) {
        this.f2680a = i2;
        this.f2681b = i3;
        this.mSendManager = hVar;
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    /* renamed from: a, reason: collision with other method in class and merged with bridge method [inline-methods] */
    public e get() {
        return null;
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public synchronized void add(e eVar) {
        if (this.f5a == null) {
            this.f5a = new ArrayList();
        }
        this.f5a.add(eVar);
        this.f2682c += eVar.length();
        if (this.f5a.size() >= this.f2680a || this.f2682c >= this.f2681b) {
            LogUtil.d("CacheManager satisfy limit. immediately send. size: " + this.f5a.size() + ", current capacity: " + this.f2682c);
            a();
        }
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    /* renamed from: a, reason: collision with other method in class and merged with bridge method [inline-methods] */
    public boolean remove(e eVar) {
        return false;
    }

    @Override // com.alibaba.sdk.android.emas.Cache
    public void clear() {
    }

    public synchronized void flush() {
        List<e> list = this.f5a;
        if (list != null && !list.isEmpty()) {
            LogUtil.d("CacheManager flush. immediately send.");
            a();
        }
    }

    private void a() {
        this.mSendManager.a(this.f5a);
        this.f5a = null;
        this.f2682c = 0;
    }
}
