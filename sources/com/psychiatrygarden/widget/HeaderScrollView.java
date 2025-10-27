package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0016\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0018\u001a\u00020\u0016H\u0014J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0007H\u0016J\u0006\u0010\u001e\u001a\u00020\u0007J\b\u0010\u001f\u001a\u00020\u001cH\u0002J\b\u0010 \u001a\u00020\u001cH\u0014J\u0018\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u00072\u0006\u0010#\u001a\u00020\u0007H\u0014J0\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00072\u0006\u0010(\u001a\u00020\u00072\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0007H\u0016J@\u0010,\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&2\u0006\u0010-\u001a\u00020\u00072\u0006\u0010.\u001a\u00020\u00072\u0006\u0010/\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u00072\u0006\u0010)\u001a\u00020*H\u0016J(\u00101\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\u00072\u0006\u00103\u001a\u00020\u00072\u0006\u00104\u001a\u00020\u00162\u0006\u00105\u001a\u00020\u0016H\u0014J(\u00106\u001a\u00020\u001c2\u0006\u00107\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u00072\u0006\u00109\u001a\u00020\u00072\u0006\u0010:\u001a\u00020\u0007H\u0014J\u0018\u0010;\u001a\u00020\u001c2\u0006\u0010<\u001a\u00020\u00072\u0006\u0010=\u001a\u00020\u0007H\u0016J\u0018\u0010>\u001a\u00020\u00162\u0006\u0010?\u001a\u00020\u00072\u0006\u0010+\u001a\u00020\u0007H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000fR\u0014\u0010\u0013\u001a\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000fR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lcom/psychiatrygarden/widget/HeaderScrollView;", "Landroidx/core/widget/NestedScrollView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "TAG", "", "contentView", "Landroid/view/ViewGroup;", "contentViewHeight", "getContentViewHeight", "()I", "headView", "headViewHeight", "getHeadViewHeight", "headViewMinHeight", "getHeadViewMinHeight", "isStickyLayout", "", "needInvalidate", "awakenScrollBars", "findRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "fling", "", "velocityY", "getMaxScrollY", "measureChildren", "onFinishInflate", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onNestedPreScroll", TypedValues.AttributesType.S_TARGET, "Landroid/view/View;", "dx", "dy", "consumed", "", "type", "onNestedScroll", "dxConsumed", "dyConsumed", "dxUnconsumed", "dyUnconsumed", "onOverScrolled", "scrollX", "scrollY", "clampedX", "clampedY", "onScrollChanged", NotifyType.LIGHTS, "t", "oldl", "oldt", "scrollTo", "x", "y", "startNestedScroll", "axes", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class HeaderScrollView extends NestedScrollView {

    @NotNull
    private final String TAG;
    private ViewGroup contentView;
    private ViewGroup headView;
    private boolean isStickyLayout;
    private boolean needInvalidate;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HeaderScrollView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HeaderScrollView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ HeaderScrollView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final RecyclerView findRecyclerView(ViewGroup contentView) {
        RecyclerView recyclerViewFindRecyclerView;
        if ((contentView instanceof RecyclerView) && Intrinsics.areEqual(contentView.getClass(), RecyclerView.class)) {
            return (RecyclerView) contentView;
        }
        int childCount = contentView.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = contentView.getChildAt(i2);
            if ((childAt instanceof ViewGroup) && (recyclerViewFindRecyclerView = findRecyclerView((ViewGroup) childAt)) != null) {
                return recyclerViewFindRecyclerView;
            }
        }
        return null;
    }

    private final int getContentViewHeight() {
        ViewGroup viewGroup = this.contentView;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("contentView");
            viewGroup = null;
        }
        return viewGroup.getVisibility() == 0 ? (getMeasuredHeight() + getHeadViewHeight()) - getHeadViewMinHeight() : getMeasuredHeight();
    }

    private final int getHeadViewHeight() {
        ViewGroup viewGroup = this.headView;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        if (viewGroup.getVisibility() != 0) {
            return 0;
        }
        ViewGroup viewGroup3 = this.headView;
        if (viewGroup3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
        } else {
            viewGroup2 = viewGroup3;
        }
        return viewGroup2.getMeasuredHeight();
    }

    private final int getHeadViewMinHeight() {
        ViewGroup viewGroup = this.headView;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        if (viewGroup.getVisibility() != 0) {
            return 0;
        }
        ViewGroup viewGroup3 = this.headView;
        if (viewGroup3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
        } else {
            viewGroup2 = viewGroup3;
        }
        return viewGroup2.getMinimumHeight();
    }

    private final void measureChildren() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getContentViewHeight(), 1073741824));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFinishInflate$lambda$0(HeaderScrollView this$0, View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewGroup viewGroup = this$0.headView;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        ((View.OnScrollChangeListener) viewGroup).onScrollChange(this$0, this$0.getScrollX(), this$0.getScrollY(), this$0.getScrollX(), this$0.getScrollY());
    }

    @Override // android.view.View
    public boolean awakenScrollBars() {
        if (!this.needInvalidate) {
            return super.awakenScrollBars();
        }
        invalidate();
        return true;
    }

    @Override // androidx.core.widget.NestedScrollView
    public void fling(int velocityY) {
        ViewGroup viewGroup = this.contentView;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("contentView");
            viewGroup = null;
        }
        RecyclerView recyclerViewFindRecyclerView = findRecyclerView(viewGroup);
        if (recyclerViewFindRecyclerView == null) {
            super.fling(velocityY);
        } else if (recyclerViewFindRecyclerView.canScrollVertically(1)) {
            recyclerViewFindRecyclerView.fling(0, velocityY);
        } else {
            super.fling(velocityY);
        }
    }

    public final int getMaxScrollY() {
        return getHeadViewHeight();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        View childAt = getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) childAt;
        if (viewGroup.getChildCount() != 2) {
            throw new IllegalStateException(this.TAG + " is designed for nested scrolling and can only have two direct child");
        }
        View childAt2 = viewGroup.getChildAt(0);
        Intrinsics.checkNotNull(childAt2, "null cannot be cast to non-null type android.view.ViewGroup");
        this.headView = (ViewGroup) childAt2;
        View childAt3 = viewGroup.getChildAt(1);
        Intrinsics.checkNotNull(childAt3, "null cannot be cast to non-null type android.view.ViewGroup");
        this.contentView = (ViewGroup) childAt3;
        ViewGroup viewGroup2 = this.headView;
        ViewGroup viewGroup3 = null;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup2 = null;
        }
        boolean z2 = viewGroup2 instanceof View.OnScrollChangeListener;
        this.isStickyLayout = z2;
        if (z2) {
            ViewGroup viewGroup4 = this.headView;
            if (viewGroup4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("headView");
            } else {
                viewGroup3 = viewGroup4;
            }
            viewGroup3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.psychiatrygarden.widget.r9
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    HeaderScrollView.onFinishInflate$lambda$0(this.f16857c, view, i2, i3, i4, i5, i6, i7, i8, i9);
                }
            });
        }
    }

    @Override // androidx.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int iMakeMeasureSpec;
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
        ViewGroup viewGroup = this.headView;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        if (viewGroup.getLayoutParams().height != -2) {
            ViewGroup viewGroup3 = this.headView;
            if (viewGroup3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("headView");
                viewGroup3 = null;
            }
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(viewGroup3.getLayoutParams().height, 1073741824);
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        ViewGroup viewGroup4 = this.headView;
        if (viewGroup4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
        } else {
            viewGroup2 = viewGroup4;
        }
        viewGroup2.measure(widthMeasureSpec, iMakeMeasureSpec);
        measureChildren();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0036  */
    @Override // androidx.core.widget.NestedScrollView, androidx.core.view.NestedScrollingParent2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onNestedPreScroll(@org.jetbrains.annotations.NotNull android.view.View r7, int r8, int r9, @org.jetbrains.annotations.NotNull int[] r10, int r11) {
        /*
            r6 = this;
            java.lang.String r0 = "target"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.lang.String r7 = "consumed"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r7)
            r4 = 0
            r0 = r6
            r1 = r8
            r2 = r9
            r3 = r10
            r5 = r11
            boolean r7 = r0.dispatchNestedPreScroll(r1, r2, r3, r4, r5)
            if (r7 != 0) goto L40
            r7 = 0
            r8 = 1
            if (r9 <= 0) goto L36
            int r11 = r6.getScrollY()
            int r0 = r6.getHeadViewHeight()
            android.view.ViewGroup r1 = r6.headView
            if (r1 != 0) goto L2d
            java.lang.String r1 = "headView"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r1)
            r1 = 0
        L2d:
            int r1 = r1.getMinimumHeight()
            int r0 = r0 - r1
            if (r11 >= r0) goto L36
            r11 = r8
            goto L37
        L36:
            r11 = r7
        L37:
            if (r11 == 0) goto L40
            r6.needInvalidate = r8
            r6.scrollBy(r7, r9)
            r10[r8] = r9
        L40:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.HeaderScrollView.onNestedPreScroll(android.view.View, int, int, int[], int):void");
    }

    @Override // androidx.core.widget.NestedScrollView, androidx.core.view.NestedScrollingParent3
    public void onNestedScroll(@NotNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NotNull int[] consumed) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(consumed, "consumed");
        this.needInvalidate = true;
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        int headViewHeight = getHeadViewHeight();
        ViewGroup viewGroup = this.headView;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        super.onOverScrolled(scrollX, Math.min(headViewHeight - viewGroup.getMinimumHeight(), scrollY), clampedX, clampedY);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    public void onScrollChanged(int l2, int t2, int oldl, int oldt) {
        super.onScrollChanged(l2, t2, oldl, oldt);
        if (this.isStickyLayout) {
            ViewGroup viewGroup = this.headView;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("headView");
                viewGroup = null;
            }
            ((View.OnScrollChangeListener) viewGroup).onScrollChange(this, l2, t2, oldl, oldt);
        }
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    public void scrollTo(int x2, int y2) {
        int headViewHeight = getHeadViewHeight();
        ViewGroup viewGroup = this.headView;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        super.scrollTo(x2, Math.min(headViewHeight - viewGroup.getMinimumHeight(), y2));
    }

    @Override // androidx.core.widget.NestedScrollView, androidx.core.view.NestedScrollingChild2
    public boolean startNestedScroll(int axes, int type) {
        if (type == 0) {
            ViewGroup viewGroup = this.contentView;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("contentView");
                viewGroup = null;
            }
            RecyclerView recyclerViewFindRecyclerView = findRecyclerView(viewGroup);
            if (recyclerViewFindRecyclerView != null) {
                if (recyclerViewFindRecyclerView.getScrollState() == 2) {
                    recyclerViewFindRecyclerView.stopScroll();
                }
                onStopNestedScroll(recyclerViewFindRecyclerView, 1);
            }
        }
        return super.startNestedScroll(axes, type);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HeaderScrollView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        String simpleName = HeaderScrollView.class.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "HeaderScrollView::class.java.simpleName");
        this.TAG = simpleName;
        setOverScrollMode(2);
        setMotionEventSplittingEnabled(false);
    }
}
