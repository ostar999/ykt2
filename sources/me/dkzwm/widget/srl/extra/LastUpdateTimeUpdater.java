package me.dkzwm.widget.srl.extra;

import androidx.annotation.NonNull;

/* loaded from: classes9.dex */
public class LastUpdateTimeUpdater implements Runnable {
    private AbsClassicRefreshView mRefreshView;
    private boolean mRunning = false;
    private ITimeUpdater mUpdater;

    public interface ITimeUpdater {
        boolean canUpdate();

        void updateTime(AbsClassicRefreshView absClassicRefreshView);
    }

    public LastUpdateTimeUpdater(AbsClassicRefreshView absClassicRefreshView) {
        this.mRefreshView = absClassicRefreshView;
        this.mUpdater = absClassicRefreshView;
    }

    @Override // java.lang.Runnable
    public void run() {
        ITimeUpdater iTimeUpdater = this.mUpdater;
        if (iTimeUpdater == null || this.mRefreshView == null) {
            return;
        }
        if (iTimeUpdater.canUpdate()) {
            this.mUpdater.updateTime(this.mRefreshView);
        }
        this.mRefreshView.removeCallbacks(this);
        if (this.mRunning) {
            this.mRefreshView.postDelayed(this, 1000L);
        }
    }

    public void setTimeUpdater(@NonNull ITimeUpdater iTimeUpdater) {
        this.mUpdater = iTimeUpdater;
    }

    public void start() {
        this.mRunning = true;
        AbsClassicRefreshView absClassicRefreshView = this.mRefreshView;
        if (absClassicRefreshView != null) {
            absClassicRefreshView.post(this);
        }
    }

    public void stop() {
        this.mRunning = false;
        AbsClassicRefreshView absClassicRefreshView = this.mRefreshView;
        if (absClassicRefreshView != null) {
            absClassicRefreshView.removeCallbacks(this);
        }
    }
}
