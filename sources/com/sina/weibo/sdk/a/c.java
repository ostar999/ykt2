package com.sina.weibo.sdk.a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* loaded from: classes6.dex */
public abstract class c<Params, Progress, Result> {
    final d<Params, Result> N;
    final FutureTask<Result> O;
    Params[] Q;
    volatile int M = b.V;
    int P = 5;

    /* renamed from: y, reason: collision with root package name */
    Handler f17171y = new Handler(Looper.getMainLooper()) { // from class: com.sina.weibo.sdk.a.c.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            a aVar = (a) message.obj;
            if (message.what != 1) {
                return;
            }
            c.a(aVar.T, aVar.U[0]);
            message.obj = null;
        }
    };

    /* renamed from: com.sina.weibo.sdk.a.c$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] S;

        static {
            int[] iArr = new int[b.o().length];
            S = iArr;
            try {
                iArr[b.W - 1] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                S[b.X - 1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class a<Data> {
        final c T;
        final Data[] U;

        public a(c cVar, Data... dataArr) {
            this.T = cVar;
            this.U = dataArr;
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class b {
        public static final int V = 1;
        public static final int W = 2;
        public static final int X = 3;
        private static final /* synthetic */ int[] Y = {1, 2, 3};

        public static int[] o() {
            return (int[]) Y.clone();
        }
    }

    /* renamed from: com.sina.weibo.sdk.a.c$c, reason: collision with other inner class name */
    public abstract class AbstractC0312c extends FutureTask<Result> implements Comparable<Object> {
        int priority;

        public AbstractC0312c(d dVar) {
            super(dVar);
            this.priority = dVar.priority;
        }
    }

    public c() {
        d<Params, Result> dVar = new d<Params, Result>() { // from class: com.sina.weibo.sdk.a.c.2
            @Override // java.util.concurrent.Callable
            public final Result call() throws SecurityException, IllegalArgumentException {
                Process.setThreadPriority(c.this.P);
                return (Result) c.this.n();
            }
        };
        this.N = dVar;
        this.O = new c<Params, Progress, Result>.AbstractC0312c(dVar) { // from class: com.sina.weibo.sdk.a.c.3
            @Override // java.lang.Comparable
            public final int compareTo(Object obj) {
                return 0;
            }

            @Override // java.util.concurrent.FutureTask
            public final void done() {
                try {
                    Result result = get();
                    c cVar = c.this;
                    cVar.f17171y.obtainMessage(1, new a(cVar, result)).sendToTarget();
                } catch (InterruptedException unused) {
                    throw new RuntimeException("An error occur while execute doInBackground().");
                } catch (CancellationException unused2) {
                    c.this.f17171y.obtainMessage(3, new a(c.this, null)).sendToTarget();
                } catch (ExecutionException unused3) {
                    throw new RuntimeException("An error occur while execute doInBackground().");
                }
            }
        };
    }

    public static /* synthetic */ void a(c cVar, Object obj) {
        cVar.onPostExecute(obj);
        cVar.M = b.X;
    }

    public abstract Result n();

    public void onPostExecute(Result result) {
    }

    public static abstract class d<Params, Result> implements Callable<Result> {
        Params[] Z;
        int priority;

        private d() {
            this.priority = 10;
        }

        public /* synthetic */ d(byte b3) {
            this();
        }
    }
}
