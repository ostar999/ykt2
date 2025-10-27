package com.tencent.liteav.videobase.frame;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videobase.frame.a.c;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public abstract class a<T extends c> {

    /* renamed from: a, reason: collision with root package name */
    private static final long f20070a = TimeUnit.SECONDS.toMillis(3);

    /* renamed from: c, reason: collision with root package name */
    private final Map<b, Deque<T>> f20072c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private volatile boolean f20073d = false;

    /* renamed from: e, reason: collision with root package name */
    private final InterfaceC0341a<T> f20074e = com.tencent.liteav.videobase.frame.b.a(this);

    /* renamed from: b, reason: collision with root package name */
    private final String f20071b = null;

    /* renamed from: com.tencent.liteav.videobase.frame.a$a, reason: collision with other inner class name */
    public interface InterfaceC0341a<T extends c> {
        void a(T t2);
    }

    public interface b {
    }

    public static abstract class c {
        private final InterfaceC0341a mRecycler;
        private final AtomicInteger mRefCnt = new AtomicInteger(0);
        private long mLastUsedTimestamp = -1;
        private final String mStackTrace = null;

        public c(InterfaceC0341a interfaceC0341a) {
            this.mRecycler = interfaceC0341a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getLastUsedTimestamp() {
            return this.mLastUsedTimestamp;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateLastUsedTimestamp(long j2) {
            this.mLastUsedTimestamp = j2;
        }

        public void finalize() throws Throwable {
            super.finalize();
            if (this.mRecycler == null || this.mRefCnt.get() == 0) {
                return;
            }
            TXCLog.e("FramePool", "Object's reference count(%d) isn't zero when finalize.\n %s", Integer.valueOf(this.mRefCnt.get()), this.mStackTrace);
        }

        public void release() {
            InterfaceC0341a interfaceC0341a;
            if (this.mRefCnt.decrementAndGet() != 0 || (interfaceC0341a = this.mRecycler) == null) {
                return;
            }
            interfaceC0341a.a(this);
        }

        public int retain() {
            return this.mRefCnt.addAndGet(1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void a(a aVar, c cVar) {
        if (cVar == 0) {
            return;
        }
        synchronized (aVar.f20072c) {
            if (aVar.f20073d) {
                aVar.a((a) cVar);
                return;
            }
            Deque<T> dequeB = aVar.b(aVar.b((a) cVar));
            cVar.updateLastUsedTimestamp(SystemClock.elapsedRealtime());
            dequeB.addFirst(cVar);
            aVar.c();
        }
    }

    private void c() {
        T tPeekLast;
        ArrayList arrayList = new ArrayList();
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.f20072c) {
            for (Deque<T> deque : this.f20072c.values()) {
                while (!deque.isEmpty() && ((tPeekLast = deque.peekLast()) == null || jElapsedRealtime - tPeekLast.getLastUsedTimestamp() >= f20070a)) {
                    deque.pollLast();
                    arrayList.add(tPeekLast);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            a((a<T>) it.next());
        }
    }

    public abstract T a(InterfaceC0341a<T> interfaceC0341a, b bVar);

    public abstract void a(T t2);

    public abstract b b(T t2);

    public void b() {
        this.f20073d = true;
        a();
    }

    public void finalize() throws Throwable {
        super.finalize();
        if (this.f20073d) {
            return;
        }
        TXCLog.e("FramePool", "%s must call destroy() before finalize()!\n%s", getClass().getName(), this.f20071b);
    }

    private Deque<T> b(b bVar) {
        Deque<T> deque = this.f20072c.get(bVar);
        if (deque != null) {
            return deque;
        }
        LinkedList linkedList = new LinkedList();
        this.f20072c.put(bVar, linkedList);
        return linkedList;
    }

    @NonNull
    public T a(b bVar) {
        T tRemoveFirst;
        synchronized (this.f20072c) {
            Deque<T> dequeB = b(bVar);
            tRemoveFirst = !dequeB.isEmpty() ? dequeB.removeFirst() : null;
        }
        c();
        if (tRemoveFirst == null) {
            tRemoveFirst = (T) a(this.f20074e, bVar);
        }
        if (tRemoveFirst.retain() != 1) {
            TXCLog.e("FramePool", "invalid reference count for %s", tRemoveFirst);
        }
        return tRemoveFirst;
    }

    public void a() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.f20072c) {
            Iterator<Deque<T>> it = this.f20072c.values().iterator();
            while (it.hasNext()) {
                arrayList.addAll(it.next());
            }
            this.f20072c.clear();
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            a((a<T>) it2.next());
        }
    }
}
