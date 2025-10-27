package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.aliyun.vod.log.struct.AliyunLogKey;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\rH\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u00142\u0006\u0010 \u001a\u00020\u0014H\u0014R\u0016\u0010\b\u001a\u0004\u0018\u00010\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/psychiatrygarden/widget/NestedScrollableHost;", "Landroid/widget/FrameLayout;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "child", "Landroid/view/View;", "getChild", "()Landroid/view/View;", "initialX", "", "initialY", "parentViewPager", "Landroidx/viewpager2/widget/ViewPager2;", "getParentViewPager", "()Landroidx/viewpager2/widget/ViewPager2;", "touchSlop", "", "canChildScroll", "", "orientation", "delta", "handleInterceptTouchEvent", "", AliyunLogKey.KEY_EVENT, "Landroid/view/MotionEvent;", "onInterceptTouchEvent", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class NestedScrollableHost extends FrameLayout {
    private float initialX;
    private float initialY;
    private int touchSlop;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedScrollableHost(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private final boolean canChildScroll(int orientation, float delta) {
        View child;
        int i2 = -((int) Math.signum(delta));
        if (orientation != 0) {
            if (orientation == 1 && (child = getChild()) != null) {
                return child.canScrollVertically(i2);
            }
            return false;
        }
        View child2 = getChild();
        if (child2 != null) {
            return child2.canScrollHorizontally(i2);
        }
        return false;
    }

    private final View getChild() {
        if (getChildCount() > 0) {
            return getChildAt(0);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x000d, code lost:
    
        r0 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final androidx.viewpager2.widget.ViewPager2 getParentViewPager() {
        /*
            r3 = this;
            android.view.ViewParent r0 = r3.getParent()
            boolean r1 = r0 instanceof android.view.View
            r2 = 0
            if (r1 == 0) goto Lc
            android.view.View r0 = (android.view.View) r0
            goto Ld
        Lc:
            r0 = r2
        Ld:
            if (r0 == 0) goto L1e
            boolean r1 = r0 instanceof androidx.viewpager2.widget.ViewPager2
            if (r1 != 0) goto L1e
            android.view.ViewParent r0 = r0.getParent()
            boolean r1 = r0 instanceof android.view.View
            if (r1 == 0) goto Lc
            android.view.View r0 = (android.view.View) r0
            goto Ld
        L1e:
            boolean r1 = r0 instanceof androidx.viewpager2.widget.ViewPager2
            if (r1 == 0) goto L25
            r2 = r0
            androidx.viewpager2.widget.ViewPager2 r2 = (androidx.viewpager2.widget.ViewPager2) r2
        L25:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.NestedScrollableHost.getParentViewPager():androidx.viewpager2.widget.ViewPager2");
    }

    private final void handleInterceptTouchEvent(MotionEvent e2) {
        ViewPager2 parentViewPager = getParentViewPager();
        if (parentViewPager != null) {
            int orientation = parentViewPager.getOrientation();
            if (canChildScroll(orientation, -1.0f) || canChildScroll(orientation, 1.0f)) {
                if (e2.getAction() == 0) {
                    this.initialX = e2.getX();
                    this.initialY = e2.getY();
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return;
                }
                if (e2.getAction() == 2) {
                    float x2 = e2.getX() - this.initialX;
                    float y2 = e2.getY() - this.initialY;
                    boolean z2 = orientation == 0;
                    float fAbs = Math.abs(x2) * (z2 ? 0.5f : 1.0f);
                    float fAbs2 = Math.abs(y2) * (z2 ? 1.0f : 0.5f);
                    int i2 = this.touchSlop;
                    if (fAbs > i2 || fAbs2 > i2) {
                        if (z2 == (fAbs2 > fAbs)) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                            return;
                        }
                        if (!z2) {
                            x2 = y2;
                        }
                        if (canChildScroll(orientation, x2)) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(@NotNull MotionEvent e2) {
        Intrinsics.checkNotNullParameter(e2, "e");
        handleInterceptTouchEvent(e2);
        return super.onInterceptTouchEvent(e2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int i2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            childAt.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, 0));
            int measuredHeight = childAt.getMeasuredHeight();
            if (measuredHeight > i2) {
                i2 = measuredHeight;
            }
        }
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(i2, 1073741824));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedScrollableHost(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }
}
