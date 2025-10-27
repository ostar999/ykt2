package com.beizi.ad.internal;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import com.beizi.ad.R;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.view.BannerAdViewImpl;
import com.beizi.ad.internal.view.InterstitialAdViewImpl;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private ScheduledExecutorService f4041a;

    /* renamed from: f, reason: collision with root package name */
    private final com.beizi.ad.internal.a f4046f;

    /* renamed from: g, reason: collision with root package name */
    private n f4047g;

    /* renamed from: b, reason: collision with root package name */
    private int f4042b = -1;

    /* renamed from: d, reason: collision with root package name */
    private long f4044d = -1;

    /* renamed from: e, reason: collision with root package name */
    private long f4045e = -1;

    /* renamed from: h, reason: collision with root package name */
    private EnumC0051c f4048h = EnumC0051c.STOPPED;

    /* renamed from: c, reason: collision with root package name */
    private final b f4043c = new b(this);

    /* renamed from: com.beizi.ad.internal.c$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4049a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f4050b;

        static {
            int[] iArr = new int[l.values().length];
            f4050b = iArr;
            try {
                iArr[l.BANNER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4050b[l.INTERSTITIAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4050b[l.SPLASH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4050b[l.NATIVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4050b[l.PREFETCH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[EnumC0051c.values().length];
            f4049a = iArr2;
            try {
                iArr2[EnumC0051c.STOPPED.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4049a[EnumC0051c.SINGLE_REQUEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f4049a[EnumC0051c.AUTO_REFRESH.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public class a implements Runnable {
        private a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.handler_message_pass));
            c.this.f4043c.sendEmptyMessage(0);
        }

        public /* synthetic */ a(c cVar, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public static class b extends Handler {

        /* renamed from: a, reason: collision with root package name */
        private final c f4059a;

        public b(c cVar) {
            this.f4059a = cVar;
        }

        @Override // android.os.Handler
        @SuppressLint({"NewApi"})
        public synchronized void handleMessage(Message message) {
            c cVar = this.f4059a;
            if (cVar != null && cVar.f4046f.isReadyToStart()) {
                if (cVar.f4044d != -1) {
                    HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.new_ad_since, Math.max(0, (int) (System.currentTimeMillis() - cVar.f4044d))));
                }
                cVar.f4044d = System.currentTimeMillis();
                int i2 = AnonymousClass1.f4050b[cVar.f4046f.getMediaType().ordinal()];
                if (i2 == 1) {
                    cVar.f4047g = new f((BannerAdViewImpl) cVar.f4046f);
                } else if (i2 == 2) {
                    cVar.f4047g = new f((InterstitialAdViewImpl) cVar.f4046f);
                } else if (i2 == 3) {
                    cVar.f4047g = new f((BannerAdViewImpl) cVar.f4046f);
                } else if (i2 == 4) {
                    cVar.f4047g = new com.beizi.ad.internal.nativead.c((com.beizi.ad.internal.nativead.b) cVar.f4046f);
                } else if (i2 == 5) {
                    cVar.f4047g = new m();
                }
                cVar.f4047g.a();
            }
        }
    }

    /* renamed from: com.beizi.ad.internal.c$c, reason: collision with other inner class name */
    public enum EnumC0051c {
        STOPPED,
        SINGLE_REQUEST,
        AUTO_REFRESH
    }

    public c(com.beizi.ad.internal.a aVar) {
        this.f4046f = aVar;
    }

    private void e() {
        ScheduledExecutorService scheduledExecutorService = this.f4041a;
        if (scheduledExecutorService == null) {
            return;
        }
        scheduledExecutorService.shutdownNow();
        try {
            this.f4041a.awaitTermination(this.f4042b, TimeUnit.MILLISECONDS);
        } catch (InterruptedException unused) {
        } catch (Throwable th) {
            this.f4041a = null;
            throw th;
        }
        this.f4041a = null;
    }

    private void d() {
        if (this.f4041a == null) {
            this.f4041a = Executors.newScheduledThreadPool(4);
        }
    }

    public void b() {
        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.start));
        d();
        int i2 = AnonymousClass1.f4049a[this.f4048h.ordinal()];
        AnonymousClass1 anonymousClass1 = null;
        long jMin = 0;
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.fetcher_start_single));
            this.f4041a.schedule(new a(this, anonymousClass1), 0L, TimeUnit.SECONDS);
            return;
        }
        if (this.f4042b <= 0) {
            HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.fetcher_start_single));
            this.f4041a.schedule(new a(this, anonymousClass1), 0L, TimeUnit.SECONDS);
            this.f4048h = EnumC0051c.SINGLE_REQUEST;
            return;
        }
        HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.fetcher_start_auto));
        int i3 = this.f4042b;
        long j2 = this.f4045e;
        if (j2 != -1) {
            long j3 = this.f4044d;
            if (j3 != -1) {
                long j4 = i3;
                jMin = Math.min(j4, Math.max(0L, j4 - (j2 - j3)));
            }
        }
        long j5 = jMin;
        HaoboLog.v(HaoboLog.baseLogTag, HaoboLog.getString(R.string.request_delayed_by_x_ms, j5));
        this.f4041a.scheduleAtFixedRate(new a(this, anonymousClass1), j5, i3, TimeUnit.MILLISECONDS);
        this.f4048h = EnumC0051c.AUTO_REFRESH;
    }

    public void c() {
        this.f4044d = -1L;
        this.f4045e = -1L;
    }

    public void a(int i2) {
        boolean z2 = this.f4042b != i2;
        this.f4042b = i2;
        if (!z2 || this.f4048h.equals(EnumC0051c.STOPPED)) {
            return;
        }
        HaoboLog.d(HaoboLog.baseLogTag, "AdFetcher refresh mPeriod changed to " + this.f4042b);
        HaoboLog.d(HaoboLog.baseLogTag, "Resetting AdFetcher");
        a();
        b();
    }

    public void a() {
        n nVar = this.f4047g;
        if (nVar != null) {
            nVar.e();
            this.f4047g = null;
        }
        e();
        HaoboLog.d(HaoboLog.baseLogTag, HaoboLog.getString(R.string.stop));
        this.f4045e = System.currentTimeMillis();
        this.f4048h = EnumC0051c.STOPPED;
    }
}
