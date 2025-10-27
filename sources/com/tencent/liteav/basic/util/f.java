package com.tencent.liteav.basic.util;

import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import com.tencent.liteav.basic.log.TXCLog;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class f extends Handler {

    /* renamed from: a, reason: collision with root package name */
    private static final long f18714a = TimeUnit.SECONDS.toMillis(30);

    /* renamed from: b, reason: collision with root package name */
    private final Handler f18715b;

    /* renamed from: c, reason: collision with root package name */
    private Runnable f18716c;

    public f(Looper looper) {
        super(looper);
        this.f18715b = new Handler(Looper.getMainLooper());
        this.f18716c = new Runnable() { // from class: com.tencent.liteav.basic.util.f.1
            @Override // java.lang.Runnable
            public void run() {
                TXCLog.e("TXCHandler", "quit looper failed. " + f.this.getLooper());
            }
        };
    }

    public void e() {
        final MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() { // from class: com.tencent.liteav.basic.util.f.3
            @Override // android.os.MessageQueue.IdleHandler
            public boolean queueIdle() {
                TXCLog.i("TXCHandler", "queueIdle on %s", Looper.myLooper());
                if (TXCBuild.VersionInt() >= 18) {
                    f.this.getLooper().quitSafely();
                } else {
                    f.this.getLooper().quit();
                }
                f.this.f18715b.removeCallbacks(f.this.f18716c);
                return false;
            }
        };
        post(new Runnable() { // from class: com.tencent.liteav.basic.util.f.4
            @Override // java.lang.Runnable
            public void run() {
                if (f.this.getLooper() == Looper.getMainLooper()) {
                    TXCLog.e("TXCHandler", "try to quitLooper main looper!");
                } else {
                    TXCLog.i("TXCHandler", "add idle handle for %s", f.this.getLooper());
                    Looper.myQueue().addIdleHandler(idleHandler);
                }
            }
        });
        this.f18715b.postDelayed(this.f18716c, f18714a);
    }

    public boolean a(Runnable runnable) {
        return a(runnable, -1L);
    }

    public boolean a(final Runnable runnable, long j2) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        boolean zPost = post(new Runnable() { // from class: com.tencent.liteav.basic.util.f.2
            @Override // java.lang.Runnable
            public void run() {
                runnable.run();
                countDownLatch.countDown();
            }
        });
        if (zPost) {
            try {
                if (j2 > 0) {
                    countDownLatch.await(j2, TimeUnit.MILLISECONDS);
                } else {
                    countDownLatch.await();
                }
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
        return zPost;
    }
}
