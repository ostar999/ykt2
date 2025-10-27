package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002:\u0001*B%\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0014J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0018\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\bH\u0014J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u0012\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0019\u001a\u00020\bH\u0002J0\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020\bH\u0014J\u0018\u0010!\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\b2\u0006\u0010#\u001a\u00020\bH\u0014J0\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\b2\u0006\u0010'\u001a\u00020\b2\u0006\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020\bH\u0016¨\u0006+"}, d2 = {"Lcom/psychiatrygarden/widget/StickyLinearLayout;", "Landroid/widget/LinearLayout;", "Landroid/view/View$OnScrollChangeListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "checkLayoutParams", "", "p", "Landroid/view/ViewGroup$LayoutParams;", "generateDefaultLayoutParams", "Lcom/psychiatrygarden/widget/StickyLinearLayout$LayoutParams;", "generateLayoutParams", "getChildDrawingOrder", "childCount", am.aC, "getViewOffsetHelper", "Lcom/psychiatrygarden/widget/ViewOffsetHelper;", "child", "Landroid/view/View;", "nextStickyView", "index", "onLayout", "", "changed", "left", PLVDanmakuInfo.FONTMODE_TOP, "right", PLVDanmakuInfo.FONTMODE_BOTTOM, "onMeasure", "widthMeasureSpec", "heightMeasureSpec", "onScrollChange", "v", "scrollX", "scrollY", "oldScrollX", "oldScrollY", "LayoutParams", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class StickyLinearLayout extends LinearLayout implements View.OnScrollChangeListener {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public StickyLinearLayout(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public StickyLinearLayout(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ StickyLinearLayout(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }

    private final ViewOffsetHelper getViewOffsetHelper(View child) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type com.psychiatrygarden.widget.StickyLinearLayout.LayoutParams");
        if (!((LayoutParams) layoutParams).getIsSticky()) {
            return null;
        }
        ViewOffsetHelper viewOffsetHelper = (ViewOffsetHelper) child.getTag(R.id.view_offset_helper);
        if (viewOffsetHelper != null) {
            return viewOffsetHelper;
        }
        ViewOffsetHelper viewOffsetHelper2 = new ViewOffsetHelper(child);
        child.setTag(R.id.view_offset_helper, viewOffsetHelper2);
        return viewOffsetHelper2;
    }

    private final View nextStickyView(int index) {
        View child;
        do {
            index--;
            if (-1 >= index) {
                return null;
            }
            child = getChildAt(index);
            Intrinsics.checkNotNullExpressionValue(child, "child");
        } while (getViewOffsetHelper(child) == null);
        return child;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public boolean checkLayoutParams(@NotNull ViewGroup.LayoutParams p2) {
        Intrinsics.checkNotNullParameter(p2, "p");
        return p2 instanceof LayoutParams;
    }

    @Override // android.view.ViewGroup
    public int getChildDrawingOrder(int childCount, int i2) {
        return (childCount - i2) - 1;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(i)");
            ViewOffsetHelper viewOffsetHelper = getViewOffsetHelper(childAt);
            if (viewOffsetHelper != null) {
                viewOffsetHelper.onViewLayout();
                viewOffsetHelper.applyOffsets();
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        while (true) {
            childCount--;
            if (-1 >= childCount) {
                measuredHeight = 0;
                break;
            }
            View childAt = getChildAt(childCount);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type com.psychiatrygarden.widget.StickyLinearLayout.LayoutParams");
            if (((LayoutParams) layoutParams).getIsSticky()) {
                measuredHeight = childAt.getMeasuredHeight();
                break;
            }
        }
        setMinimumHeight(measuredHeight);
    }

    @Override // android.view.View.OnScrollChangeListener
    public void onScrollChange(@NotNull View v2, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Intrinsics.checkNotNullParameter(v2, "v");
        boolean z2 = true;
        boolean z3 = false;
        int i2 = 0;
        for (int childCount = getChildCount() - 1; -1 < childCount; childCount--) {
            View child = getChildAt(childCount);
            Intrinsics.checkNotNullExpressionValue(child, "child");
            ViewOffsetHelper viewOffsetHelper = getViewOffsetHelper(child);
            if (viewOffsetHelper != null) {
                int layoutTop = scrollY - viewOffsetHelper.getLayoutTop();
                if (!z3) {
                    viewOffsetHelper.setTopAndBottomOffset(Math.max(layoutTop, 0));
                    View viewNextStickyView = nextStickyView(childCount);
                    if (viewNextStickyView != null) {
                        int iMax = Math.max(layoutTop + viewNextStickyView.getMeasuredHeight(), 0);
                        i2 = iMax;
                        z3 = iMax > 0;
                    }
                } else if (z2) {
                    viewOffsetHelper.setTopAndBottomOffset(layoutTop - i2);
                    z2 = false;
                }
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public StickyLinearLayout(@NotNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        setChildrenDrawingOrderEnabled(true);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    @NotNull
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nB\u001f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rB\u0011\b\u0016\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010B\u0011\b\u0016\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012¢\u0006\u0002\u0010\u0013B\u0011\b\u0016\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0014R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lcom/psychiatrygarden/widget/StickyLinearLayout$LayoutParams;", "Landroid/widget/LinearLayout$LayoutParams;", am.aF, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "width", "", "height", "(II)V", "weight", "", "(IIF)V", "p", "Landroid/view/ViewGroup$LayoutParams;", "(Landroid/view/ViewGroup$LayoutParams;)V", SocialConstants.PARAM_SOURCE, "Landroid/view/ViewGroup$MarginLayoutParams;", "(Landroid/view/ViewGroup$MarginLayoutParams;)V", "(Landroid/widget/LinearLayout$LayoutParams;)V", "isSticky", "", "()Z", "setSticky", "(Z)V", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class LayoutParams extends LinearLayout.LayoutParams {
        private boolean isSticky;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LayoutParams(@NotNull Context c3, @Nullable AttributeSet attributeSet) {
            super(c3, attributeSet);
            Intrinsics.checkNotNullParameter(c3, "c");
            TypedArray typedArrayObtainStyledAttributes = c3.obtainStyledAttributes(attributeSet, R.styleable.StickyLinearLayout_Layout);
            Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "c.obtainStyledAttributes…tickyLinearLayout_Layout)");
            this.isSticky = typedArrayObtainStyledAttributes.getBoolean(0, false);
            typedArrayObtainStyledAttributes.recycle();
        }

        /* renamed from: isSticky, reason: from getter */
        public final boolean getIsSticky() {
            return this.isSticky;
        }

        public final void setSticky(boolean z2) {
            this.isSticky = z2;
        }

        public LayoutParams(int i2, int i3) {
            super(i2, i3);
        }

        public LayoutParams(int i2, int i3, float f2) {
            super(i2, i3, f2);
        }

        public LayoutParams(@Nullable ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(@Nullable ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(@Nullable LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    @NotNull
    public LayoutParams generateLayoutParams(@NotNull AttributeSet attrs) {
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        return new LayoutParams(context, attrs);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    @NotNull
    public LayoutParams generateLayoutParams(@NotNull ViewGroup.LayoutParams p2) {
        Intrinsics.checkNotNullParameter(p2, "p");
        return new LayoutParams(p2);
    }
}
