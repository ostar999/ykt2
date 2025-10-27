package com.tencent.liteav.videoengine.decoder;

import android.os.SystemClock;
import androidx.annotation.NonNull;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoengine.decoder.n;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    @NonNull
    private final com.tencent.liteav.videobase.f.a f20359a;

    /* renamed from: b, reason: collision with root package name */
    @NonNull
    private final a f20360b;

    /* renamed from: c, reason: collision with root package name */
    @NonNull
    private final b f20361c;

    /* renamed from: d, reason: collision with root package name */
    private n.a f20362d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f20363e;

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        private long f20371a;

        /* renamed from: b, reason: collision with root package name */
        private long f20372b;

        private b() {
            this.f20371a = 0L;
            this.f20372b = 0L;
        }

        public void a() {
            this.f20372b = 0L;
            this.f20371a = 0L;
        }

        public void b() {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (this.f20372b == 0) {
                this.f20372b = jElapsedRealtime;
            }
            if (this.f20371a == 0) {
                this.f20371a = jElapsedRealtime;
            }
            long j2 = this.f20371a;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            if (jElapsedRealtime > j2 + timeUnit.toMillis(1L) && jElapsedRealtime > this.f20372b + timeUnit.toMillis(2L)) {
                TXCLog.e("VideoDecodeControllerStatistics", "frame interval [%d] > %d", Long.valueOf(jElapsedRealtime - this.f20371a), Long.valueOf(timeUnit.toMillis(1L)));
                this.f20372b = jElapsedRealtime;
            }
            this.f20371a = jElapsedRealtime;
        }
    }

    public m(@NonNull com.tencent.liteav.videobase.f.a aVar) {
        this.f20359a = aVar;
        this.f20360b = new a();
        this.f20361c = new b();
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c() {
        return this.f20362d == n.a.HARDWARE;
    }

    public void a() {
        this.f20360b.a();
        this.f20361c.a();
        this.f20363e = false;
        this.f20362d = null;
    }

    public void b() {
        this.f20360b.b();
        this.f20361c.b();
        if (this.f20363e) {
            return;
        }
        this.f20363e = true;
        this.f20359a.a(com.tencent.liteav.videobase.f.b.EVT_VIDEO_DECODE_FIRST_FRAME_DECODED, "first frame decoded", "", new Object[0]);
    }

    public class a {

        /* renamed from: b, reason: collision with root package name */
        private long f20365b;

        /* renamed from: c, reason: collision with root package name */
        private long f20366c;

        /* renamed from: d, reason: collision with root package name */
        private long f20367d;

        /* renamed from: e, reason: collision with root package name */
        private long f20368e;

        /* renamed from: f, reason: collision with root package name */
        private final Deque<Long> f20369f;

        /* renamed from: g, reason: collision with root package name */
        private final List<Long> f20370g;

        private a() {
            this.f20365b = 0L;
            this.f20366c = 0L;
            this.f20367d = 0L;
            this.f20368e = 0L;
            this.f20369f = new LinkedList();
            this.f20370g = new ArrayList();
        }

        private void c() {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            this.f20370g.add(Long.valueOf(jElapsedRealtime - this.f20368e));
            this.f20368e = jElapsedRealtime;
            this.f20369f.removeFirst();
            if (jElapsedRealtime - this.f20366c >= TimeUnit.SECONDS.toMillis(1L)) {
                this.f20366c = jElapsedRealtime;
                Iterator<Long> it = this.f20370g.iterator();
                long jLongValue = 0;
                while (it.hasNext()) {
                    jLongValue += it.next().longValue();
                }
                this.f20367d = jLongValue / Math.max(this.f20370g.size(), 1);
                this.f20370g.clear();
            }
        }

        private void d() {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (this.f20365b == 0) {
                this.f20365b = jElapsedRealtime;
            }
            if (jElapsedRealtime < this.f20365b + TimeUnit.SECONDS.toMillis(1L)) {
                return;
            }
            this.f20365b = jElapsedRealtime;
            long j2 = this.f20367d;
            if (m.this.c()) {
                m.this.f20359a.a(com.tencent.liteav.videobase.f.c.STATUS_VIDEO_HW_DECODE_TASK_COST, Long.valueOf(j2));
            } else {
                m.this.f20359a.a(com.tencent.liteav.videobase.f.c.STATUS_VIDEO_SW_DECODE_TASK_COST, Long.valueOf(j2));
            }
        }

        public void a() {
            this.f20365b = 0L;
            this.f20366c = 0L;
            this.f20367d = 0L;
            this.f20368e = 0L;
            this.f20369f.clear();
            this.f20370g.clear();
        }

        public void b() {
            c();
            d();
        }

        public void a(long j2) {
            if (this.f20369f.isEmpty()) {
                this.f20368e = SystemClock.elapsedRealtime();
            }
            this.f20369f.addLast(Long.valueOf(j2));
        }
    }

    public void a(long j2) {
        this.f20360b.a(j2);
    }

    public void a(n.a aVar, boolean z2) {
        this.f20362d = aVar;
        this.f20359a.a(com.tencent.liteav.videobase.f.c.DECODER_IS_HARDWARE, Boolean.valueOf(aVar == n.a.HARDWARE));
        this.f20359a.a(com.tencent.liteav.videobase.f.c.DECODER_STREAM_CODEC_TYPE, z2 ? com.tencent.liteav.videobase.e.a.H265 : com.tencent.liteav.videobase.e.a.H264);
    }
}
