package com.ykb.ebook.page.delegate;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import androidx.core.app.NotificationCompat;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.weight.ReadView;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000eH\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u000eH\u0016J\b\u0010\u001a\u001a\u00020\u0013H\u0016J\b\u0010\u001b\u001a\u00020\u0013H\u0016J\u0010\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!H\u0002J\u0010\u0010\"\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!H\u0016J\u0010\u0010#\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u000eX\u0082D¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/ykb/ebook/page/delegate/ScrollPageDelegate;", "Lcom/ykb/ebook/page/delegate/PageDelegate;", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", "mVelocity", "Landroid/view/VelocityTracker;", "noAnim", "", "getNoAnim", "()Z", "setNoAnim", "(Z)V", "slopSquare", "", "getSlopSquare", "()I", "velocityDuration", "abortAnim", "", "calcNextPageOffset", "calcPrevPageOffset", "computeScroll", "nextPageByAnim", "animationSpeed", "onAnimStart", "onAnimStop", "onDestroy", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onScroll", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "onTouch", "prevPageByAnim", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ScrollPageDelegate extends PageDelegate {

    @NotNull
    private final VelocityTracker mVelocity;
    private boolean noAnim;
    private final int velocityDuration;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrollPageDelegate(@NotNull ReadView readView) {
        super(readView);
        Intrinsics.checkNotNullParameter(readView, "readView");
        this.velocityDuration = 1000;
        VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
        Intrinsics.checkNotNullExpressionValue(velocityTrackerObtain, "obtain()");
        this.mVelocity = velocityTrackerObtain;
    }

    private final int calcNextPageOffset() {
        return -(((int) ((TextLine) CollectionsKt___CollectionsKt.last((List) getReadView().getCurVisiblePage().getLines())).getLineTop()) - ChapterProvider.getPaddingTop());
    }

    private final int calcPrevPageOffset() {
        return ChapterProvider.getVisibleHeight() - (((int) ((TextLine) CollectionsKt___CollectionsKt.first((List) getReadView().getCurVisiblePage().getLines())).getLineBottom()) - ChapterProvider.getPaddingTop());
    }

    private final int getSlopSquare() {
        return getReadView().getPageSlopSquare2();
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void abortAnim() {
        getReadView().onScrollAnimStop();
        setStarted(false);
        setMoved(false);
        setRunning(false);
        if (getScroller().isFinished()) {
            getReadView().setAbortAnim(false);
        } else {
            getReadView().setAbortAnim(true);
            getScroller().abortAnimation();
        }
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void computeScroll() {
        if (getScroller().computeScrollOffset()) {
            getReadView().setTouchPoint(getScroller().getCurrX(), getScroller().getCurrY(), false);
        } else if (getIsStarted()) {
            onAnimStop();
            stopScroll();
        }
    }

    public final boolean getNoAnim() {
        return this.noAnim;
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void nextPageByAnim(int animationSpeed) {
        if (getReadView().getIsAbortAnim()) {
            getReadView().setAbortAnim(false);
        } else if (this.noAnim) {
            getCurPage().scroll(calcNextPageOffset());
        } else {
            getReadView().setStartPoint(0.0f, 0.0f, false);
            startScroll(0, 0, 0, calcNextPageOffset(), animationSpeed);
        }
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStart(int animationSpeed) {
        getReadView().onScrollAnimStart();
        fling(0, (int) getTouchY(), 0, (int) this.mVelocity.getYVelocity(), 0, 0, getViewHeight() * (-10), getViewHeight() * 10);
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onAnimStop() {
        getReadView().onScrollAnimStop();
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onDestroy() {
        super.onDestroy();
        this.mVelocity.recycle();
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onScroll() {
        getCurPage().scroll((int) (getTouchY() - getLastY()));
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void onTouch(@NotNull MotionEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getActionMasked() == 5) {
            getReadView().setStartPoint(event.getX(event.getPointerCount() - 1), event.getY(event.getPointerCount() - 1), false);
        } else if (event.getActionMasked() == 6) {
            getReadView().setStartPoint(event.getX(), event.getY(), false);
            return;
        }
        int action = event.getAction();
        if (action == 0) {
            abortAnim();
            this.mVelocity.clear();
            return;
        }
        if (action != 1) {
            if (action == 2) {
                onScroll(event);
                return;
            } else if (action != 3) {
                return;
            }
        }
        onAnimStart(getReadView().getDefaultAnimationSpeed());
    }

    @Override // com.ykb.ebook.page.delegate.PageDelegate
    public void prevPageByAnim(int animationSpeed) {
        if (getReadView().getIsAbortAnim()) {
            getReadView().setAbortAnim(false);
        } else if (this.noAnim) {
            getCurPage().scroll(calcPrevPageOffset());
        } else {
            getReadView().setStartPoint(0.0f, 0.0f, false);
            startScroll(0, 0, 0, calcPrevPageOffset(), animationSpeed);
        }
    }

    public final void setNoAnim(boolean z2) {
        this.noAnim = z2;
    }

    private final void onScroll(MotionEvent event) {
        this.mVelocity.addMovement(event);
        this.mVelocity.computeCurrentVelocity(this.velocityDuration);
        float x2 = event.getX(event.getPointerCount() - 1);
        float y2 = event.getY(event.getPointerCount() - 1);
        if (getIsMoved() || getReadView().isLongScreenShot()) {
            getReadView().setTouchPoint(x2, y2, false);
        }
        if (!getIsMoved()) {
            int startX = (int) (x2 - getStartX());
            int startY = (int) (y2 - getStartY());
            setMoved((startX * startX) + (startY * startY) > getSlopSquare());
            if (getIsMoved()) {
                getReadView().setStartPoint(event.getX(), event.getY(), false);
            }
        }
        if (getIsMoved()) {
            setRunning(true);
        }
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getFirstPage()) {
            readConfig.setFirstPage(false);
            getCurPage().hideFirstPage();
        }
    }
}
