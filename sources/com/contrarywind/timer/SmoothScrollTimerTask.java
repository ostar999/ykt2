package com.contrarywind.timer;

import com.contrarywind.view.WheelView;
import java.util.TimerTask;

/* loaded from: classes3.dex */
public final class SmoothScrollTimerTask extends TimerTask {
    private int offset;
    private final WheelView wheelView;
    private int realTotalOffset = Integer.MAX_VALUE;
    private int realOffset = 0;

    public SmoothScrollTimerTask(WheelView wheelView, int i2) {
        this.wheelView = wheelView;
        this.offset = i2;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        if (this.realTotalOffset == Integer.MAX_VALUE) {
            this.realTotalOffset = this.offset;
        }
        int i2 = this.realTotalOffset;
        int i3 = (int) (i2 * 0.1f);
        this.realOffset = i3;
        if (i3 == 0) {
            if (i2 < 0) {
                this.realOffset = -1;
            } else {
                this.realOffset = 1;
            }
        }
        if (Math.abs(i2) <= 1) {
            this.wheelView.cancelFuture();
            this.wheelView.getHandler().sendEmptyMessage(3000);
            return;
        }
        WheelView wheelView = this.wheelView;
        wheelView.setTotalScrollY(wheelView.getTotalScrollY() + this.realOffset);
        if (!this.wheelView.isLoop()) {
            float itemHeight = this.wheelView.getItemHeight();
            float itemsCount = ((this.wheelView.getItemsCount() - 1) - this.wheelView.getInitPosition()) * itemHeight;
            if (this.wheelView.getTotalScrollY() <= (-this.wheelView.getInitPosition()) * itemHeight || this.wheelView.getTotalScrollY() >= itemsCount) {
                WheelView wheelView2 = this.wheelView;
                wheelView2.setTotalScrollY(wheelView2.getTotalScrollY() - this.realOffset);
                this.wheelView.cancelFuture();
                this.wheelView.getHandler().sendEmptyMessage(3000);
                return;
            }
        }
        this.wheelView.getHandler().sendEmptyMessage(1000);
        this.realTotalOffset -= this.realOffset;
    }
}
