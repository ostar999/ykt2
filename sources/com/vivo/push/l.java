package com.vivo.push;

import android.content.Context;
import cn.hutool.core.text.StrPool;

/* loaded from: classes6.dex */
public abstract class l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    protected Context f24388a;

    /* renamed from: b, reason: collision with root package name */
    private int f24389b;

    /* renamed from: c, reason: collision with root package name */
    private o f24390c;

    public l(o oVar) {
        this.f24389b = -1;
        this.f24390c = oVar;
        int iB = oVar.b();
        this.f24389b = iB;
        if (iB < 0) {
            throw new IllegalArgumentException("PushTask need a > 0 task id.");
        }
        this.f24388a = e.a().h();
    }

    public final int a() {
        return this.f24389b;
    }

    public abstract void a(o oVar);

    @Override // java.lang.Runnable
    public final void run() {
        Context context = this.f24388a;
        if (context != null && !(this.f24390c instanceof com.vivo.push.b.n)) {
            com.vivo.push.util.p.a(context, "[执行指令]" + this.f24390c);
        }
        a(this.f24390c);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(StrPool.DELIM_START);
        o oVar = this.f24390c;
        sb.append(oVar == null ? "[null]" : oVar.toString());
        sb.append("}");
        return sb.toString();
    }
}
