package com.sina.weibo.sdk.a;

import com.sina.weibo.sdk.a.c;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public final class a {
    private static final int F;
    private static final int G;
    private static final int H;
    private static final Comparator<Runnable> J;
    private ThreadPoolExecutor I;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        F = iAvailableProcessors;
        G = iAvailableProcessors + 1;
        H = (iAvailableProcessors * 2) + 1;
        J = new Comparator<Runnable>() { // from class: com.sina.weibo.sdk.a.a.1
            @Override // java.util.Comparator
            public final /* bridge */ /* synthetic */ int compare(Runnable runnable, Runnable runnable2) {
                return 0;
            }
        };
    }

    public a() {
        if (this.I == null) {
            this.I = new ThreadPoolExecutor(G, H, 1L, TimeUnit.SECONDS, new PriorityBlockingQueue(5, J));
        }
    }

    public final void a(c cVar) {
        ThreadPoolExecutor threadPoolExecutor = this.I;
        if (cVar.M != c.b.V) {
            int i2 = c.AnonymousClass4.S[cVar.M - 1];
            if (i2 == 1) {
                throw new IllegalStateException("Cannot execute task: the task is already running.");
            }
            if (i2 == 2) {
                throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        cVar.M = c.b.W;
        c.d<Params, Result> dVar = cVar.N;
        dVar.Z = cVar.Q;
        dVar.priority = cVar.P;
        threadPoolExecutor.execute(cVar.O);
    }
}
