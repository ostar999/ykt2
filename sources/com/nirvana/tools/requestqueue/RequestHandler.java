package com.nirvana.tools.requestqueue;

import com.nirvana.tools.core.ExecutorManager;
import com.nirvana.tools.requestqueue.Response;
import com.nirvana.tools.requestqueue.strategy.CallbackStrategy;
import com.nirvana.tools.requestqueue.strategy.ThreadStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RunnableScheduledFuture;

/* loaded from: classes4.dex */
final class RequestHandler<T extends Response> {

    /* renamed from: a, reason: collision with root package name */
    List<Callback<T>> f10668a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    RequestHandler<T>.a f10669b;

    /* renamed from: c, reason: collision with root package name */
    Request<T> f10670c;

    /* renamed from: d, reason: collision with root package name */
    private DoneAction f10671d;

    /* renamed from: com.nirvana.tools.requestqueue.RequestHandler$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f10679a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f10680b;

        static {
            int[] iArr = new int[CallbackStrategy.values().length];
            f10680b = iArr;
            try {
                iArr[CallbackStrategy.LIST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f10680b[CallbackStrategy.COVER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[ThreadStrategy.values().length];
            f10679a = iArr2;
            try {
                iArr2[ThreadStrategy.THREAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f10679a[ThreadStrategy.THREAD_MAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f10679a[ThreadStrategy.SAME_WITH_CALLABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public interface DoneAction {
        void run(RequestHandler requestHandler);
    }

    public class a implements Runnable {

        /* renamed from: d, reason: collision with root package name */
        private Runnable f10684d;

        /* renamed from: c, reason: collision with root package name */
        private volatile boolean f10683c = false;

        /* renamed from: a, reason: collision with root package name */
        RunnableScheduledFuture<?> f10681a = null;

        public a(Runnable runnable) {
            this.f10684d = runnable;
        }

        public final synchronized void a() {
            if (this.f10684d != null) {
                ExecutorManager.getInstance().removeFromMain(this.f10684d);
            }
            if (this.f10681a != null) {
                ExecutorManager.getInstance().removeFromThread(this.f10681a);
            }
            this.f10683c = true;
        }

        @Override // java.lang.Runnable
        public final void run() throws Exception {
            if (this.f10683c) {
                return;
            }
            try {
                T tCall = RequestHandler.this.f10670c.getAction().call();
                if (this.f10683c) {
                    return;
                }
                RequestHandler.this.a((RequestHandler) tCall);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public RequestHandler(Request<T> request, DoneAction doneAction) {
        this.f10670c = request;
        this.f10671d = doneAction;
    }

    public final void a() {
        if (this.f10669b == null) {
            Runnable runnable = new Runnable() { // from class: com.nirvana.tools.requestqueue.RequestHandler.3
                @Override // java.lang.Runnable
                public final void run() {
                    RequestHandler.this.a((RequestHandler) RequestHandler.this.f10670c.getAction().onTimeout());
                }
            };
            this.f10669b = new a(runnable);
            int i2 = AnonymousClass4.f10679a[this.f10670c.getThreadStrategy().ordinal()];
            if (i2 == 1) {
                ExecutorManager.getInstance().scheduleFuture(this.f10669b);
                this.f10669b.f10681a = ExecutorManager.getInstance().scheduleFutureDelay(runnable, this.f10670c.getTimeout());
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException("Request Callable ThreadStrategy Illegal");
                }
                ExecutorManager.getInstance().postMain(this.f10669b);
                ExecutorManager.getInstance().postMain(runnable, this.f10670c.getTimeout());
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0042 A[Catch: all -> 0x004d, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:11:0x003a, B:13:0x0042, B:8:0x0024, B:9:0x002f, B:10:0x0033), top: B:19:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void a(com.nirvana.tools.requestqueue.Request<T> r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            com.nirvana.tools.requestqueue.Callback r0 = r6.getCallback()     // Catch: java.lang.Throwable -> L4d
            long r1 = r6.getTimeout()     // Catch: java.lang.Throwable -> L4d
            long r3 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L4d
            long r1 = r1 + r3
            r0.setExpiredTime(r1)     // Catch: java.lang.Throwable -> L4d
            int[] r0 = com.nirvana.tools.requestqueue.RequestHandler.AnonymousClass4.f10680b     // Catch: java.lang.Throwable -> L4d
            com.nirvana.tools.requestqueue.strategy.CallbackStrategy r1 = r6.getCallbackStrategy()     // Catch: java.lang.Throwable -> L4d
            int r1 = r1.ordinal()     // Catch: java.lang.Throwable -> L4d
            r0 = r0[r1]     // Catch: java.lang.Throwable -> L4d
            r1 = 1
            if (r0 == r1) goto L33
            r1 = 2
            if (r0 == r1) goto L24
            goto L3a
        L24:
            java.util.List<com.nirvana.tools.requestqueue.Callback<T extends com.nirvana.tools.requestqueue.Response>> r0 = r5.f10668a     // Catch: java.lang.Throwable -> L4d
            r0.clear()     // Catch: java.lang.Throwable -> L4d
            java.util.List<com.nirvana.tools.requestqueue.Callback<T extends com.nirvana.tools.requestqueue.Response>> r0 = r5.f10668a     // Catch: java.lang.Throwable -> L4d
            com.nirvana.tools.requestqueue.Callback r1 = r6.getCallback()     // Catch: java.lang.Throwable -> L4d
        L2f:
            r0.add(r1)     // Catch: java.lang.Throwable -> L4d
            goto L3a
        L33:
            java.util.List<com.nirvana.tools.requestqueue.Callback<T extends com.nirvana.tools.requestqueue.Response>> r0 = r5.f10668a     // Catch: java.lang.Throwable -> L4d
            com.nirvana.tools.requestqueue.Callback r1 = r6.getCallback()     // Catch: java.lang.Throwable -> L4d
            goto L2f
        L3a:
            java.util.List<com.nirvana.tools.requestqueue.Callback<T extends com.nirvana.tools.requestqueue.Response>> r0 = r5.f10668a     // Catch: java.lang.Throwable -> L4d
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> L4d
            if (r0 == 0) goto L4b
            java.util.List<com.nirvana.tools.requestqueue.Callback<T extends com.nirvana.tools.requestqueue.Response>> r0 = r5.f10668a     // Catch: java.lang.Throwable -> L4d
            com.nirvana.tools.requestqueue.Callback r6 = r6.getCallback()     // Catch: java.lang.Throwable -> L4d
            r0.add(r6)     // Catch: java.lang.Throwable -> L4d
        L4b:
            monitor-exit(r5)
            return
        L4d:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nirvana.tools.requestqueue.RequestHandler.a(com.nirvana.tools.requestqueue.Request):void");
    }

    public final synchronized void a(final T t2) {
        if (this.f10668a.size() > 0) {
            ArrayList arrayList = new ArrayList(this.f10668a.size());
            Iterator<Callback<T>> it = this.f10668a.iterator();
            long j2 = 0;
            while (it.hasNext()) {
                final Callback<T> next = it.next();
                if (t2.isTimeout()) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - next.getExpiredTime();
                    if (jCurrentTimeMillis > next.getThreshold()) {
                        if (j2 > jCurrentTimeMillis) {
                            j2 = jCurrentTimeMillis;
                        }
                    }
                }
                int i2 = AnonymousClass4.f10679a[next.getThreadStrategy().ordinal()];
                if (i2 == 1) {
                    ExecutorManager.getInstance().scheduleFuture(new Runnable() { // from class: com.nirvana.tools.requestqueue.RequestHandler.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public final void run() {
                            next.onResult(t2);
                        }
                    });
                } else if (i2 == 2) {
                    ExecutorManager.getInstance().postMain(new Runnable() { // from class: com.nirvana.tools.requestqueue.RequestHandler.2
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // java.lang.Runnable
                        public final void run() {
                            next.onResult(t2);
                        }
                    });
                } else if (i2 == 3) {
                    arrayList.add(next);
                }
                it.remove();
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                ((Callback) it2.next()).onResult(t2);
            }
            arrayList.clear();
            if (this.f10668a.isEmpty()) {
                DoneAction doneAction = this.f10671d;
                if (doneAction != null) {
                    doneAction.run(this);
                }
            } else {
                Request<T> request = this.f10670c;
                if (request != null) {
                    request.setTimeout(j2);
                }
                a();
            }
        }
    }
}
