package com.ykb.ebook.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.widget.NestedScrollView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0015\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001b\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007B%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u0017\u001a\u00020\u0011H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0002J2\u0010\u001a\u001a\u00020\u00192\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010 \u001a\u00020\tH\u0014J\b\u0010!\u001a\u00020\u0019H\u0002J\b\u0010\"\u001a\u00020\u0019H\u0014J\u0018\u0010#\u001a\u00020\u00192\u0006\u0010$\u001a\u00020\t2\u0006\u0010%\u001a\u00020\tH\u0014J@\u0010&\u001a\u00020\u00192\u0006\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\t2\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\t2\u0006\u0010+\u001a\u00020\t2\u0006\u0010,\u001a\u00020\t2\u0006\u0010-\u001a\u00020.H\u0016J(\u0010/\u001a\u00020\u00192\u0006\u00100\u001a\u00020\t2\u0006\u00101\u001a\u00020\t2\u0006\u00102\u001a\u00020\u00112\u0006\u00103\u001a\u00020\u0011H\u0014J(\u00104\u001a\u00020\u00192\u0006\u00105\u001a\u00020\t2\u0006\u00106\u001a\u00020\t2\u0006\u00107\u001a\u00020\t2\u0006\u00108\u001a\u00020\tH\u0014J\u0018\u00109\u001a\u00020\u00192\u0006\u0010:\u001a\u00020\t2\u0006\u0010;\u001a\u00020\tH\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006<"}, d2 = {"Lcom/ykb/ebook/weight/StickyScrollView;", "Landroidx/core/widget/NestedScrollView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "headView", "Landroid/view/ViewGroup;", "headViewHeight", "getHeadViewHeight", "()I", "isStickyLayout", "", "needInvalidate", "startX", "startY", "tag", "", "awakenScrollBars", "init", "", "measureChildWithMargins", "child", "Landroid/view/View;", "parentWidthMeasureSpec", "widthUsed", "parentHeightMeasureSpec", "heightUsed", "measureChildren", "onFinishInflate", "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onNestedScroll", TypedValues.AttributesType.S_TARGET, "dxConsumed", "dyConsumed", "dxUnconsumed", "dyUnconsumed", "type", "consumed", "", "onOverScrolled", "scrollX", "scrollY", "clampedX", "clampedY", "onScrollChanged", NotifyType.LIGHTS, "t", "oldl", "oldt", "scrollTo", "x", "y", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nStickyScrollView.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StickyScrollView.kt\ncom/ykb/ebook/weight/StickyScrollView\n+ 2 View.kt\nandroidx/core/view/ViewKt\n*L\n1#1,261:1\n252#2:262\n*S KotlinDebug\n*F\n+ 1 StickyScrollView.kt\ncom/ykb/ebook/weight/StickyScrollView\n*L\n61#1:262\n*E\n"})
/* loaded from: classes8.dex */
public final class StickyScrollView extends NestedScrollView {
    private ViewGroup headView;
    private boolean isStickyLayout;
    private boolean needInvalidate;
    private int startX;
    private int startY;

    @NotNull
    private final String tag;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyScrollView(@NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        String simpleName = StickyScrollView.class.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "this::class.java.simpleName");
        this.tag = simpleName;
        init();
    }

    private final int getHeadViewHeight() {
        ViewGroup viewGroup = this.headView;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        if (!(viewGroup.getVisibility() == 0)) {
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

    private final void init() {
        setOverScrollMode(2);
        setMotionEventSplittingEnabled(false);
    }

    private final void measureChildren() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).measure(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getHeadViewHeight(), 1073741824));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFinishInflate$lambda$0(StickyScrollView this$0, View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
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

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public void measureChildWithMargins(@Nullable View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        if (child != null) {
            child.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        View childAt = getChildAt(0);
        Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) childAt;
        if (getChildCount() != 1) {
            throw new IllegalStateException(this.tag + " is designed for nested scrolling and can only have one direct child");
        }
        this.headView = viewGroup;
        ViewGroup viewGroup2 = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        boolean z2 = viewGroup instanceof View.OnScrollChangeListener;
        this.isStickyLayout = z2;
        if (z2) {
            ViewGroup viewGroup3 = this.headView;
            if (viewGroup3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("headView");
            } else {
                viewGroup2 = viewGroup3;
            }
            viewGroup2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.ykb.ebook.weight.y0
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    StickyScrollView.onFinishInflate$lambda$0(this.f26548c, view, i2, i3, i4, i5, i6, i7, i8, i9);
                }
            });
        }
    }

    @Override // androidx.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.MeasureSpec.getSize(heightMeasureSpec));
        ViewGroup viewGroup = this.headView;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("headView");
            viewGroup = null;
        }
        viewGroup.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, 0));
        measureChildren();
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

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyScrollView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        String simpleName = StickyScrollView.class.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "this::class.java.simpleName");
        this.tag = simpleName;
        init();
    }

    public /* synthetic */ StickyScrollView(Context context, AttributeSet attributeSet, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyScrollView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        String simpleName = StickyScrollView.class.getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "this::class.java.simpleName");
        this.tag = simpleName;
        init();
    }

    public /* synthetic */ StickyScrollView(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
