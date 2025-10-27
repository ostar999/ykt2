package com.ykb.ebook.page.delegate;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import androidx.annotation.CallSuper;
import androidx.core.app.NotificationCompat;
import com.google.android.material.snackbar.Snackbar;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.umeng.analytics.pro.d;
import com.ykb.ebook.page.PageDirection;
import com.ykb.ebook.weight.PageView;
import com.ykb.ebook.weight.ReadView;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010J\u001a\u00020KH&J\b\u0010L\u001a\u00020KH\u0016J\u0006\u0010M\u001a\u00020KJH\u0010N\u001a\u00020K2\u0006\u00109\u001a\u00020B2\u0006\u0010;\u001a\u00020B2\u0006\u0010O\u001a\u00020B2\u0006\u0010P\u001a\u00020B2\u0006\u0010Q\u001a\u00020B2\u0006\u0010R\u001a\u00020B2\u0006\u0010S\u001a\u00020B2\u0006\u0010T\u001a\u00020BH\u0016J\u0006\u0010U\u001a\u00020\u000eJ\u0006\u0010V\u001a\u00020\u000eJ\u0010\u0010W\u001a\u00020K2\u0006\u0010X\u001a\u00020\u001fH\u0016J\u0010\u0010Y\u001a\u00020K2\u0006\u0010Z\u001a\u00020BH&J\u0010\u0010[\u001a\u00020K2\u0006\u0010Z\u001a\u00020BH&J\b\u0010\\\u001a\u00020KH&J\b\u0010]\u001a\u00020KH\u0016J\u0006\u0010^\u001a\u00020KJ\u0010\u0010_\u001a\u00020K2\u0006\u0010`\u001a\u00020aH&J\b\u0010b\u001a\u00020KH\u0016J\u0010\u0010c\u001a\u00020K2\u0006\u0010d\u001a\u00020eH&J\u0006\u0010f\u001a\u00020KJ\u0010\u0010g\u001a\u00020K2\u0006\u0010Z\u001a\u00020BH&J\u0010\u0010h\u001a\u00020K2\u0006\u0010X\u001a\u00020\u001fH\u0017J\u0018\u0010i\u001a\u00020K2\u0006\u0010j\u001a\u00020B2\u0006\u0010k\u001a\u00020BH\u0017J0\u0010l\u001a\u00020K2\u0006\u00109\u001a\u00020B2\u0006\u0010;\u001a\u00020B2\u0006\u0010m\u001a\u00020B2\u0006\u0010n\u001a\u00020B2\u0006\u0010Z\u001a\u00020BH\u0004J\b\u0010o\u001a\u00020KH\u0004R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u001a\u0010\u0014\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R\u001a\u0010\u0016\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000f\"\u0004\b\u0017\u0010\u0011R\u0014\u0010\u0018\u001a\u00020\u00198DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00198DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001bR\u001a\u0010\u001e\u001a\u00020\u001fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b%\u0010\fR\u001a\u0010&\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u000f\"\u0004\b(\u0010\u0011R\u0014\u0010)\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u001b\u0010-\u001a\u00020.8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b1\u00102\u001a\u0004\b/\u00100R\u000e\u00103\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u00104\u001a\u0002058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b8\u00102\u001a\u0004\b6\u00107R\u0014\u00109\u001a\u00020\u00198DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b:\u0010\u001bR\u0014\u0010;\u001a\u00020\u00198DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b<\u0010\u001bR\u0014\u0010=\u001a\u00020\u00198DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b>\u0010\u001bR\u0014\u0010?\u001a\u00020\u00198DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b@\u0010\u001bR\u001a\u0010A\u001a\u00020BX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020BX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010D\"\u0004\bI\u0010F¨\u0006p"}, d2 = {"Lcom/ykb/ebook/page/delegate/PageDelegate;", "", "readView", "Lcom/ykb/ebook/weight/ReadView;", "(Lcom/ykb/ebook/weight/ReadView;)V", d.R, "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "curPage", "Lcom/ykb/ebook/weight/PageView;", "getCurPage", "()Lcom/ykb/ebook/weight/PageView;", "isCancel", "", "()Z", "setCancel", "(Z)V", "isMoved", "setMoved", "isRunning", "setRunning", "isStarted", "setStarted", "lastX", "", "getLastX", "()F", "lastY", "getLastY", "mDirection", "Lcom/ykb/ebook/page/PageDirection;", "getMDirection", "()Lcom/ykb/ebook/page/PageDirection;", "setMDirection", "(Lcom/ykb/ebook/page/PageDirection;)V", "nextPage", "getNextPage", "noNext", "getNoNext", "setNoNext", "prevPage", "getPrevPage", "getReadView", "()Lcom/ykb/ebook/weight/ReadView;", "scroller", "Landroid/widget/Scroller;", "getScroller", "()Landroid/widget/Scroller;", "scroller$delegate", "Lkotlin/Lazy;", "selectedOnDown", "snackBar", "Lcom/google/android/material/snackbar/Snackbar;", "getSnackBar", "()Lcom/google/android/material/snackbar/Snackbar;", "snackBar$delegate", "startX", "getStartX", "startY", "getStartY", "touchX", "getTouchX", "touchY", "getTouchY", "viewHeight", "", "getViewHeight", "()I", "setViewHeight", "(I)V", "viewWidth", "getViewWidth", "setViewWidth", "abortAnim", "", "computeScroll", "dismissSnackBar", "fling", "velocityX", "velocityY", "minX", "maxX", "minY", "maxY", "hasNext", "hasPrev", "keyTurnPage", HiAnalyticsConstant.HaKey.BI_KEY_DIRECTION, "nextPageByAnim", "animationSpeed", "onAnimStart", "onAnimStop", "onDestroy", "onDown", "onDraw", "canvas", "Landroid/graphics/Canvas;", "onScroll", "onTouch", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "postInvalidate", "prevPageByAnim", "setDirection", "setViewSize", "width", "height", "startScroll", "dx", "dy", "stopScroll", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public abstract class PageDelegate {

    @NotNull
    private final Context context;
    private boolean isCancel;
    private boolean isMoved;
    private boolean isRunning;
    private boolean isStarted;

    @NotNull
    private PageDirection mDirection;
    private boolean noNext;

    @NotNull
    private final ReadView readView;

    /* renamed from: scroller$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy scroller;
    private boolean selectedOnDown;

    /* renamed from: snackBar$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy snackBar;
    private int viewHeight;
    private int viewWidth;

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PageDirection.values().length];
            try {
                iArr[PageDirection.NEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PageDirection.PREV.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public PageDelegate(@NotNull ReadView readView) {
        Intrinsics.checkNotNullParameter(readView, "readView");
        this.readView = readView;
        Context context = readView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "readView.context");
        this.context = context;
        this.viewWidth = readView.getWidth();
        this.viewHeight = readView.getHeight();
        this.scroller = LazyKt__LazyJVMKt.lazy(new Function0<Scroller>() { // from class: com.ykb.ebook.page.delegate.PageDelegate$scroller$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Scroller invoke() {
                return new Scroller(this.this$0.getReadView().getContext(), new LinearInterpolator());
            }
        });
        this.snackBar = LazyKt__LazyJVMKt.lazy(new Function0<Snackbar>() { // from class: com.ykb.ebook.page.delegate.PageDelegate$snackBar$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Snackbar invoke() {
                Snackbar snackbarMake = Snackbar.make(this.this$0.getReadView(), "", -1);
                Intrinsics.checkNotNullExpressionValue(snackbarMake, "make(readView, \"\", Snackbar.LENGTH_SHORT)");
                return snackbarMake;
            }
        });
        this.noNext = true;
        this.mDirection = PageDirection.NONE;
        getCurPage().resetPageOffset();
    }

    private final Snackbar getSnackBar() {
        return (Snackbar) this.snackBar.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void postInvalidate$lambda$0(PageDelegate this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isRunning) {
            ((HorizontalPageDelegate) this$0).setBitmap();
            this$0.readView.invalidate();
        }
    }

    public abstract void abortAnim();

    public void computeScroll() {
        if (getScroller().computeScrollOffset()) {
            ReadView.setTouchPoint$default(this.readView, getScroller().getCurrX(), getScroller().getCurrY(), false, 4, null);
        } else if (this.isStarted) {
            onAnimStop();
            stopScroll();
        }
    }

    public final void dismissSnackBar() {
        if (getSnackBar().isShown()) {
            getSnackBar().dismiss();
        }
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        getScroller().fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
        this.isRunning = true;
        this.isStarted = true;
        this.readView.invalidate();
    }

    @NotNull
    public final Context getContext() {
        return this.context;
    }

    @NotNull
    public final PageView getCurPage() {
        return this.readView.getCurPage();
    }

    public final float getLastX() {
        return this.readView.getLastX();
    }

    public final float getLastY() {
        return this.readView.getLastY();
    }

    @NotNull
    public final PageDirection getMDirection() {
        return this.mDirection;
    }

    @NotNull
    public final PageView getNextPage() {
        return this.readView.getNextPage();
    }

    public final boolean getNoNext() {
        return this.noNext;
    }

    @NotNull
    public final PageView getPrevPage() {
        return this.readView.getPrevPage();
    }

    @NotNull
    public final ReadView getReadView() {
        return this.readView;
    }

    @NotNull
    public final Scroller getScroller() {
        return (Scroller) this.scroller.getValue();
    }

    public final float getStartX() {
        return this.readView.getStartX();
    }

    public final float getStartY() {
        return this.readView.getStartY();
    }

    public final float getTouchX() {
        return this.readView.getTouchX();
    }

    public final float getTouchY() {
        return this.readView.getTouchY();
    }

    public final int getViewHeight() {
        return this.viewHeight;
    }

    public final int getViewWidth() {
        return this.viewWidth;
    }

    public final boolean hasNext() {
        return this.readView.getPageFactory().hasNext();
    }

    public final boolean hasPrev() {
        return this.readView.getPageFactory().hasPrev();
    }

    /* renamed from: isCancel, reason: from getter */
    public final boolean getIsCancel() {
        return this.isCancel;
    }

    /* renamed from: isMoved, reason: from getter */
    public final boolean getIsMoved() {
        return this.isMoved;
    }

    /* renamed from: isRunning, reason: from getter */
    public final boolean getIsRunning() {
        return this.isRunning;
    }

    /* renamed from: isStarted, reason: from getter */
    public final boolean getIsStarted() {
        return this.isStarted;
    }

    public void keyTurnPage(@NotNull PageDirection direction) {
        Intrinsics.checkNotNullParameter(direction, "direction");
        if (this.isRunning) {
            return;
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[direction.ordinal()];
        if (i2 == 1) {
            nextPageByAnim(100);
        } else {
            if (i2 != 2) {
                return;
            }
            prevPageByAnim(100);
        }
    }

    public abstract void nextPageByAnim(int animationSpeed);

    public abstract void onAnimStart(int animationSpeed);

    public abstract void onAnimStop();

    public void onDestroy() {
    }

    public final void onDown() {
        this.isMoved = false;
        this.noNext = false;
        this.isRunning = false;
        this.isCancel = false;
        setDirection(PageDirection.NONE);
    }

    public abstract void onDraw(@NotNull Canvas canvas);

    public void onScroll() {
    }

    public abstract void onTouch(@NotNull MotionEvent event);

    public final void postInvalidate() {
        if (this.isRunning && (this instanceof HorizontalPageDelegate)) {
            this.readView.post(new Runnable() { // from class: com.ykb.ebook.page.delegate.a
                @Override // java.lang.Runnable
                public final void run() {
                    PageDelegate.postInvalidate$lambda$0(this.f26446c);
                }
            });
        }
    }

    public abstract void prevPageByAnim(int animationSpeed);

    public final void setCancel(boolean z2) {
        this.isCancel = z2;
    }

    @CallSuper
    public void setDirection(@NotNull PageDirection direction) {
        Intrinsics.checkNotNullParameter(direction, "direction");
        this.mDirection = direction;
    }

    public final void setMDirection(@NotNull PageDirection pageDirection) {
        Intrinsics.checkNotNullParameter(pageDirection, "<set-?>");
        this.mDirection = pageDirection;
    }

    public final void setMoved(boolean z2) {
        this.isMoved = z2;
    }

    public final void setNoNext(boolean z2) {
        this.noNext = z2;
    }

    public final void setRunning(boolean z2) {
        this.isRunning = z2;
    }

    public final void setStarted(boolean z2) {
        this.isStarted = z2;
    }

    public final void setViewHeight(int i2) {
        this.viewHeight = i2;
    }

    @CallSuper
    public void setViewSize(int width, int height) {
        this.viewWidth = width;
        this.viewHeight = height;
    }

    public final void setViewWidth(int i2) {
        this.viewWidth = i2;
    }

    public final void startScroll(int startX, int startY, int dx, int dy, int animationSpeed) {
        getScroller().startScroll(startX, startY, dx, dy, dx != 0 ? (animationSpeed * Math.abs(dx)) / this.viewWidth : (animationSpeed * Math.abs(dy)) / this.viewHeight);
        this.isRunning = true;
        this.isStarted = true;
        this.readView.invalidate();
    }

    public final void stopScroll() {
        this.isStarted = false;
        this.isMoved = false;
        this.isRunning = false;
        this.readView.postInvalidate();
    }
}
