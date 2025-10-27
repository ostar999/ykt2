package com.hyphenate.easeui.widget.photoview;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;
import android.widget.Scroller;

/* loaded from: classes4.dex */
abstract class ScrollerProxy {

    @TargetApi(9)
    public static class GingerScroller extends ScrollerProxy {
        private OverScroller mScroller;

        public GingerScroller(Context context) {
            this.mScroller = new OverScroller(context);
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public boolean computeScrollOffset() {
            return this.mScroller.computeScrollOffset();
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public void fling(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
            this.mScroller.fling(i2, i3, i4, i5, i6, i7, i8, i9, i10, i11);
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public void forceFinished(boolean z2) {
            this.mScroller.forceFinished(z2);
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public int getCurrX() {
            return this.mScroller.getCurrX();
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public int getCurrY() {
            return this.mScroller.getCurrY();
        }
    }

    public static class PreGingerScroller extends ScrollerProxy {
        private Scroller mScroller;

        public PreGingerScroller(Context context) {
            this.mScroller = new Scroller(context);
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public boolean computeScrollOffset() {
            return this.mScroller.computeScrollOffset();
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public void fling(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
            this.mScroller.fling(i2, i3, i4, i5, i6, i7, i8, i9);
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public void forceFinished(boolean z2) {
            this.mScroller.forceFinished(z2);
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public int getCurrX() {
            return this.mScroller.getCurrX();
        }

        @Override // com.hyphenate.easeui.widget.photoview.ScrollerProxy
        public int getCurrY() {
            return this.mScroller.getCurrY();
        }
    }

    public static ScrollerProxy getScroller(Context context) {
        return new GingerScroller(context);
    }

    public abstract boolean computeScrollOffset();

    public abstract void fling(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    public abstract void forceFinished(boolean z2);

    public abstract int getCurrX();

    public abstract int getCurrY();
}
