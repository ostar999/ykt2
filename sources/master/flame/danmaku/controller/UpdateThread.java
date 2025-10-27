package master.flame.danmaku.controller;

/* loaded from: classes6.dex */
public class UpdateThread extends Thread {
    volatile boolean mIsQuited;

    public UpdateThread(String str) {
        super(str);
    }

    public boolean isQuited() {
        return this.mIsQuited;
    }

    public void quit() {
        this.mIsQuited = true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
    }
}
