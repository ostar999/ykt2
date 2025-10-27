package net.polyv.danmaku.ui.widget;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import net.polyv.danmaku.controller.IDanmakuView;
import net.polyv.danmaku.danmaku.model.BaseDanmaku;
import net.polyv.danmaku.danmaku.model.IDanmakus;
import net.polyv.danmaku.danmaku.model.android.Danmakus;

/* loaded from: classes9.dex */
public class DanmakuTouchHelper {
    private IDanmakuView danmakuView;
    private RectF mDanmakuBounds;
    private final GestureDetector.OnGestureListener mOnGestureListener;
    private final GestureDetector mTouchDelegate;
    private float mXOff;
    private float mYOff;

    /* JADX WARN: Multi-variable type inference failed */
    private DanmakuTouchHelper(IDanmakuView iDanmakuView) {
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: net.polyv.danmaku.ui.widget.DanmakuTouchHelper.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent motionEvent) {
                if (DanmakuTouchHelper.this.danmakuView == null || DanmakuTouchHelper.this.danmakuView.getOnDanmakuClickListener() == null) {
                    return false;
                }
                DanmakuTouchHelper danmakuTouchHelper = DanmakuTouchHelper.this;
                danmakuTouchHelper.mXOff = danmakuTouchHelper.danmakuView.getXOff();
                DanmakuTouchHelper danmakuTouchHelper2 = DanmakuTouchHelper.this;
                danmakuTouchHelper2.mYOff = danmakuTouchHelper2.danmakuView.getYOff();
                return true;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent motionEvent) {
                if (DanmakuTouchHelper.this.danmakuView.getOnDanmakuClickListener() == null) {
                    return;
                }
                DanmakuTouchHelper danmakuTouchHelper = DanmakuTouchHelper.this;
                danmakuTouchHelper.mXOff = danmakuTouchHelper.danmakuView.getXOff();
                DanmakuTouchHelper danmakuTouchHelper2 = DanmakuTouchHelper.this;
                danmakuTouchHelper2.mYOff = danmakuTouchHelper2.danmakuView.getYOff();
                IDanmakus iDanmakus = DanmakuTouchHelper.this.touchHitDanmaku(motionEvent.getX(), motionEvent.getY());
                if (iDanmakus == null || iDanmakus.isEmpty()) {
                    return;
                }
                DanmakuTouchHelper.this.performDanmakuClick(iDanmakus, true);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                IDanmakus iDanmakus = DanmakuTouchHelper.this.touchHitDanmaku(motionEvent.getX(), motionEvent.getY());
                boolean zPerformDanmakuClick = false;
                if (iDanmakus != null && !iDanmakus.isEmpty()) {
                    zPerformDanmakuClick = DanmakuTouchHelper.this.performDanmakuClick(iDanmakus, false);
                }
                return !zPerformDanmakuClick ? DanmakuTouchHelper.this.performViewClick() : zPerformDanmakuClick;
            }
        };
        this.mOnGestureListener = simpleOnGestureListener;
        this.danmakuView = iDanmakuView;
        this.mDanmakuBounds = new RectF();
        this.mTouchDelegate = new GestureDetector(((View) iDanmakuView).getContext(), simpleOnGestureListener);
    }

    public static synchronized DanmakuTouchHelper instance(IDanmakuView iDanmakuView) {
        return new DanmakuTouchHelper(iDanmakuView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performDanmakuClick(IDanmakus iDanmakus, boolean z2) {
        IDanmakuView.OnDanmakuClickListener onDanmakuClickListener = this.danmakuView.getOnDanmakuClickListener();
        if (onDanmakuClickListener != null) {
            return z2 ? onDanmakuClickListener.onDanmakuLongClick(iDanmakus) : onDanmakuClickListener.onDanmakuClick(iDanmakus);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performViewClick() {
        IDanmakuView.OnDanmakuClickListener onDanmakuClickListener = this.danmakuView.getOnDanmakuClickListener();
        if (onDanmakuClickListener != null) {
            return onDanmakuClickListener.onViewClick(this.danmakuView);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IDanmakus touchHitDanmaku(final float f2, final float f3) {
        final Danmakus danmakus = new Danmakus();
        this.mDanmakuBounds.setEmpty();
        IDanmakus currentVisibleDanmakus = this.danmakuView.getCurrentVisibleDanmakus();
        if (currentVisibleDanmakus != null && !currentVisibleDanmakus.isEmpty()) {
            currentVisibleDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: net.polyv.danmaku.ui.widget.DanmakuTouchHelper.2
                @Override // net.polyv.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (baseDanmaku == null) {
                        return 0;
                    }
                    DanmakuTouchHelper.this.mDanmakuBounds.set(baseDanmaku.getLeft(), baseDanmaku.getTop(), baseDanmaku.getRight(), baseDanmaku.getBottom());
                    if (!DanmakuTouchHelper.this.mDanmakuBounds.intersect(f2 - DanmakuTouchHelper.this.mXOff, f3 - DanmakuTouchHelper.this.mYOff, f2 + DanmakuTouchHelper.this.mXOff, f3 + DanmakuTouchHelper.this.mYOff)) {
                        return 0;
                    }
                    danmakus.addItem(baseDanmaku);
                    return 0;
                }
            });
        }
        return danmakus;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mTouchDelegate.onTouchEvent(motionEvent);
    }
}
