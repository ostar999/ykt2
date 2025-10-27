package com.psychiatrygarden.activity.purchase.zoom;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;
import android.widget.Scroller;

/* loaded from: classes5.dex */
public abstract class ScrollerProxy {

    @TargetApi(9)
    public static class GingerScroller extends ScrollerProxy {
        private OverScroller mScroller;

        public GingerScroller(Context context) {
            this.mScroller = new OverScroller(context);
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public boolean computeScrollOffset() {
            return this.mScroller.computeScrollOffset();
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
            this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public void forceFinished(boolean finished) {
            this.mScroller.forceFinished(finished);
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public int getCurrX() {
            return this.mScroller.getCurrX();
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public int getCurrY() {
            return this.mScroller.getCurrY();
        }
    }

    public static class PreGingerScroller extends ScrollerProxy {
        private Scroller mScroller;

        public PreGingerScroller(Context context) {
            this.mScroller = new Scroller(context);
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public boolean computeScrollOffset() {
            return this.mScroller.computeScrollOffset();
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
            this.mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public void forceFinished(boolean finished) {
            this.mScroller.forceFinished(finished);
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public int getCurrX() {
            return this.mScroller.getCurrX();
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.ScrollerProxy
        public int getCurrY() {
            return this.mScroller.getCurrY();
        }
    }

    public static ScrollerProxy getScroller(Context context) {
        return new GingerScroller(context);
    }

    public abstract boolean computeScrollOffset();

    public abstract void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY);

    public abstract void forceFinished(boolean finished);

    public abstract int getCurrX();

    public abstract int getCurrY();
}
