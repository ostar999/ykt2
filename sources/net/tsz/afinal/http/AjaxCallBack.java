package net.tsz.afinal.http;

/* loaded from: classes9.dex */
public abstract class AjaxCallBack<T> {
    private boolean progress = true;
    private int rate = 1000;

    public int getRate() {
        return this.rate;
    }

    public boolean isProgress() {
        return this.progress;
    }

    public void onFailure(Throwable th, int i2, String str) {
    }

    public void onLoading(long j2, long j3) {
    }

    public void onStart() {
    }

    public void onSuccess(T t2) {
    }

    public AjaxCallBack<T> progress(boolean z2, int i2) {
        this.progress = z2;
        this.rate = i2;
        return this;
    }
}
