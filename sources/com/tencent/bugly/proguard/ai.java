package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes6.dex */
public final class ai {

    /* renamed from: b, reason: collision with root package name */
    private static ai f17472b;

    /* renamed from: a, reason: collision with root package name */
    public ah f17473a;

    /* renamed from: d, reason: collision with root package name */
    private final Context f17475d;

    /* renamed from: f, reason: collision with root package name */
    private long f17477f;

    /* renamed from: g, reason: collision with root package name */
    private long f17478g;

    /* renamed from: e, reason: collision with root package name */
    private Map<Integer, Long> f17476e = new HashMap();

    /* renamed from: h, reason: collision with root package name */
    private LinkedBlockingQueue<Runnable> f17479h = new LinkedBlockingQueue<>();

    /* renamed from: i, reason: collision with root package name */
    private LinkedBlockingQueue<Runnable> f17480i = new LinkedBlockingQueue<>();

    /* renamed from: j, reason: collision with root package name */
    private final Object f17481j = new Object();

    /* renamed from: k, reason: collision with root package name */
    private long f17482k = 0;

    /* renamed from: l, reason: collision with root package name */
    private int f17483l = 0;

    /* renamed from: c, reason: collision with root package name */
    private final w f17474c = w.a();

    private ai(Context context) {
        this.f17475d = context;
    }

    public static /* synthetic */ int b(ai aiVar) {
        int i2 = aiVar.f17483l - 1;
        aiVar.f17483l = i2;
        return i2;
    }

    public static synchronized ai a(Context context) {
        if (f17472b == null) {
            f17472b = new ai(context);
        }
        return f17472b;
    }

    public final boolean b(int i2) {
        if (p.f17851c) {
            al.c("Uploading frequency will not be checked if SDK is in debug mode.", new Object[0]);
            return true;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - a(i2);
        al.c("[UploadManager] Time interval is %d seconds since last uploading(ID: %d).", Long.valueOf(jCurrentTimeMillis / 1000), Integer.valueOf(i2));
        if (jCurrentTimeMillis >= 30000) {
            return true;
        }
        al.a("[UploadManager] Data only be uploaded once in %d seconds.", 30L);
        return false;
    }

    public static synchronized ai a() {
        return f17472b;
    }

    public final void a(int i2, bq bqVar, String str, String str2, ah ahVar, long j2, boolean z2) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            a(new aj(this.f17475d, i2, bqVar.f17751g, ae.a((Object) bqVar), str, str2, ahVar, z2), true, true, j2);
        } catch (Throwable th2) {
            th = th2;
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    private void b() {
        ak akVarA = ak.a();
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
        final LinkedBlockingQueue linkedBlockingQueue2 = new LinkedBlockingQueue();
        synchronized (this.f17481j) {
            al.c("[UploadManager] Try to poll all upload task need and put them into temp queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            int size = this.f17479h.size();
            final int size2 = this.f17480i.size();
            if (size == 0 && size2 == 0) {
                al.c("[UploadManager] There is no upload task in queue.", new Object[0]);
                return;
            }
            if (akVarA == null || !akVarA.c()) {
                size2 = 0;
            }
            a(this.f17479h, linkedBlockingQueue, size);
            a(this.f17480i, linkedBlockingQueue2, size2);
            a(size, linkedBlockingQueue);
            if (size2 > 0) {
                al.c("[UploadManager] Execute upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(size2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            }
            ak akVarA2 = ak.a();
            if (akVarA2 != null) {
                akVarA2.a(new Runnable() { // from class: com.tencent.bugly.proguard.ai.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        Runnable runnable;
                        for (int i2 = 0; i2 < size2 && (runnable = (Runnable) linkedBlockingQueue2.poll()) != null; i2++) {
                            runnable.run();
                        }
                    }
                });
            }
        }
    }

    private void a(int i2, int i3, byte[] bArr, String str, String str2, ah ahVar, boolean z2) {
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            a(new aj(this.f17475d, i2, i3, bArr, str, str2, ahVar, 0, 0, false), z2, false, 0L);
        } catch (Throwable th2) {
            th = th2;
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public final void a(int i2, bq bqVar, String str, String str2, ah ahVar, boolean z2) {
        a(i2, bqVar.f17751g, ae.a((Object) bqVar), str, str2, ahVar, z2);
    }

    public final long a(boolean z2) {
        long jD;
        long jB = ap.b();
        int i2 = z2 ? 5 : 3;
        List<y> listA = this.f17474c.a(i2);
        if (listA != null && listA.size() > 0) {
            jD = 0;
            try {
                y yVar = listA.get(0);
                if (yVar.f17931e >= jB) {
                    jD = ap.d(yVar.f17933g);
                    if (i2 == 3) {
                        this.f17477f = jD;
                    } else {
                        this.f17478g = jD;
                    }
                    listA.remove(yVar);
                }
            } catch (Throwable th) {
                al.a(th);
            }
            if (listA.size() > 0) {
                this.f17474c.a(listA);
            }
        } else {
            jD = z2 ? this.f17478g : this.f17477f;
        }
        al.c("[UploadManager] Local network consume: %d KB", Long.valueOf(jD / 1024));
        return jD;
    }

    public final synchronized void a(long j2, boolean z2) {
        int i2 = z2 ? 5 : 3;
        y yVar = new y();
        yVar.f17928b = i2;
        yVar.f17931e = ap.b();
        yVar.f17929c = "";
        yVar.f17930d = "";
        yVar.f17933g = ap.c(j2);
        this.f17474c.b(i2);
        this.f17474c.a(yVar);
        if (z2) {
            this.f17478g = j2;
        } else {
            this.f17477f = j2;
        }
        al.c("[UploadManager] Network total consume: %d KB", Long.valueOf(j2 / 1024));
    }

    public final synchronized void a(int i2, long j2) {
        if (i2 < 0) {
            al.e("[UploadManager] Unknown uploading ID: %d", Integer.valueOf(i2));
            return;
        }
        this.f17476e.put(Integer.valueOf(i2), Long.valueOf(j2));
        y yVar = new y();
        yVar.f17928b = i2;
        yVar.f17931e = j2;
        yVar.f17929c = "";
        yVar.f17930d = "";
        yVar.f17933g = new byte[0];
        this.f17474c.b(i2);
        this.f17474c.a(yVar);
        al.c("[UploadManager] Uploading(ID:%d) time: %s", Integer.valueOf(i2), ap.a(j2));
    }

    public final synchronized long a(int i2) {
        if (i2 >= 0) {
            Long l2 = this.f17476e.get(Integer.valueOf(i2));
            if (l2 != null) {
                return l2.longValue();
            }
        } else {
            al.e("[UploadManager] Unknown upload ID: %d", Integer.valueOf(i2));
        }
        return 0L;
    }

    private static void a(LinkedBlockingQueue<Runnable> linkedBlockingQueue, LinkedBlockingQueue<Runnable> linkedBlockingQueue2, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            Runnable runnablePeek = linkedBlockingQueue.peek();
            if (runnablePeek == null) {
                return;
            }
            try {
                linkedBlockingQueue2.put(runnablePeek);
                linkedBlockingQueue.poll();
            } catch (Throwable th) {
                al.e("[UploadManager] Failed to add upload task to temp urgent queue: %s", th.getMessage());
            }
        }
    }

    private void a(int i2, LinkedBlockingQueue<Runnable> linkedBlockingQueue) {
        ak akVarA = ak.a();
        if (i2 > 0) {
            al.c("[UploadManager] Execute urgent upload tasks of queue which has %d tasks (pid=%d | tid=%d)", Integer.valueOf(i2), Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        }
        for (int i3 = 0; i3 < i2; i3++) {
            final Runnable runnablePoll = linkedBlockingQueue.poll();
            if (runnablePoll == null) {
                return;
            }
            synchronized (this.f17481j) {
                if (this.f17483l >= 2 && akVarA != null) {
                    akVarA.a(runnablePoll);
                } else {
                    al.a("[UploadManager] Create and start a new thread to execute a upload task: %s", "BUGLY_ASYNC_UPLOAD");
                    if (ap.a(new Runnable() { // from class: com.tencent.bugly.proguard.ai.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            runnablePoll.run();
                            synchronized (ai.this.f17481j) {
                                ai.b(ai.this);
                            }
                        }
                    }, "BUGLY_ASYNC_UPLOAD") != null) {
                        synchronized (this.f17481j) {
                            this.f17483l++;
                        }
                    } else {
                        al.d("[UploadManager] Failed to start a thread to execute asynchronous upload task,will try again next time.", new Object[0]);
                        a(runnablePoll, true);
                    }
                }
            }
        }
    }

    private boolean a(Runnable runnable, boolean z2) {
        if (runnable == null) {
            al.a("[UploadManager] Upload task should not be null", new Object[0]);
            return false;
        }
        try {
            al.c("[UploadManager] Add upload task to queue (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
            synchronized (this.f17481j) {
                if (z2) {
                    this.f17479h.put(runnable);
                } else {
                    this.f17480i.put(runnable);
                }
            }
            return true;
        } catch (Throwable th) {
            al.e("[UploadManager] Failed to add upload task to queue: %s", th.getMessage());
            return false;
        }
    }

    private void a(Runnable runnable, long j2) {
        if (runnable == null) {
            al.d("[UploadManager] Upload task should not be null", new Object[0]);
            return;
        }
        al.c("[UploadManager] Execute synchronized upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        Thread threadA = ap.a(runnable, "BUGLY_SYNC_UPLOAD");
        if (threadA == null) {
            al.e("[UploadManager] Failed to start a thread to execute synchronized upload task, add it to queue.", new Object[0]);
            a(runnable, true);
            return;
        }
        try {
            threadA.join(j2);
        } catch (Throwable th) {
            al.e("[UploadManager] Failed to join upload synchronized task with message: %s. Add it to queue.", th.getMessage());
            a(runnable, true);
            b();
        }
    }

    private void a(Runnable runnable, boolean z2, boolean z3, long j2) {
        al.c("[UploadManager] Add upload task (pid=%d | tid=%d)", Integer.valueOf(Process.myPid()), Integer.valueOf(Process.myTid()));
        if (z3) {
            a(runnable, j2);
        } else {
            a(runnable, z2);
            b();
        }
    }
}
