package pl.droidsonroids.gif;

import java.lang.Thread;

/* loaded from: classes9.dex */
abstract class SafeRunnable implements Runnable {
    final GifDrawable mGifDrawable;

    public SafeRunnable(GifDrawable gifDrawable) {
        this.mGifDrawable = gifDrawable;
    }

    public abstract void doWork();

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.mGifDrawable.isRecycled()) {
                return;
            }
            doWork();
        } catch (Throwable th) {
            Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler != null) {
                defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
            }
            throw th;
        }
    }
}
