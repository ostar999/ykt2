package pl.droidsonroids.gif;

/* loaded from: classes9.dex */
class ConditionVariable {
    private volatile boolean mCondition;

    public synchronized void block() throws InterruptedException {
        while (!this.mCondition) {
            wait();
        }
    }

    public synchronized void close() {
        this.mCondition = false;
    }

    public synchronized void open() {
        boolean z2 = this.mCondition;
        this.mCondition = true;
        if (!z2) {
            notify();
        }
    }

    public synchronized void set(boolean z2) {
        if (z2) {
            open();
        } else {
            close();
        }
    }
}
