package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.widget.OverScroller;

/* loaded from: classes6.dex */
public class GingerScroller extends ScrollerProxy {
    private boolean mFirstScroll = false;
    protected final OverScroller mScroller;

    public GingerScroller(Context context) {
        this.mScroller = new OverScroller(context);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.ScrollerProxy
    public boolean computeScrollOffset() {
        if (this.mFirstScroll) {
            this.mScroller.computeScrollOffset();
            this.mFirstScroll = false;
        }
        return this.mScroller.computeScrollOffset();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.ScrollerProxy
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.ScrollerProxy
    public void forceFinished(boolean finished) {
        this.mScroller.forceFinished(finished);
    }

    @Override // com.psychiatrygarden.widget.imagezoom.ScrollerProxy
    public int getCurrX() {
        return this.mScroller.getCurrX();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.ScrollerProxy
    public int getCurrY() {
        return this.mScroller.getCurrY();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.ScrollerProxy
    public boolean isFinished() {
        return this.mScroller.isFinished();
    }
}
