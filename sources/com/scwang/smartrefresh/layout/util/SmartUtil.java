package com.scwang.smartrefresh.layout.util;

import android.content.res.Resources;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ScrollingView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class SmartUtil implements Interpolator {
    public static int INTERPOLATOR_DECELERATE = 1;
    public static int INTERPOLATOR_VISCOUS_FLUID = 0;
    private static final float VISCOUS_FLUID_NORMALIZE;
    private static final float VISCOUS_FLUID_OFFSET;
    private static final float VISCOUS_FLUID_SCALE = 8.0f;
    private static float density = Resources.getSystem().getDisplayMetrics().density;
    private int type;

    static {
        float fViscousFluid = 1.0f / viscousFluid(1.0f);
        VISCOUS_FLUID_NORMALIZE = fViscousFluid;
        VISCOUS_FLUID_OFFSET = 1.0f - (fViscousFluid * viscousFluid(1.0f));
    }

    public SmartUtil(int i2) {
        this.type = i2;
    }

    public static boolean canLoadMore(@NonNull View view, PointF pointF, boolean z2) {
        if (canScrollVertically(view, 1) && view.getVisibility() == 0) {
            return false;
        }
        if ((view instanceof ViewGroup) && pointF != null && !isScrollableView(view)) {
            ViewGroup viewGroup = (ViewGroup) view;
            PointF pointF2 = new PointF();
            for (int childCount = viewGroup.getChildCount(); childCount > 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount - 1);
                if (isTransformedTouchPointInView(viewGroup, childAt, pointF.x, pointF.y, pointF2)) {
                    if ("fixed".equals(childAt.getTag()) || "fixed-top".equals(childAt.getTag())) {
                        return false;
                    }
                    pointF.offset(pointF2.x, pointF2.y);
                    boolean zCanLoadMore = canLoadMore(childAt, pointF, z2);
                    pointF.offset(-pointF2.x, -pointF2.y);
                    return zCanLoadMore;
                }
            }
        }
        return z2 || canScrollVertically(view, -1);
    }

    public static boolean canRefresh(@NonNull View view, PointF pointF) {
        if (canScrollVertically(view, -1) && view.getVisibility() == 0) {
            return false;
        }
        if (!(view instanceof ViewGroup) || pointF == null) {
            return true;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        PointF pointF2 = new PointF();
        for (int childCount = viewGroup.getChildCount(); childCount > 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount - 1);
            if (isTransformedTouchPointInView(viewGroup, childAt, pointF.x, pointF.y, pointF2)) {
                if ("fixed".equals(childAt.getTag()) || "fixed-bottom".equals(childAt.getTag())) {
                    return false;
                }
                pointF.offset(pointF2.x, pointF2.y);
                boolean zCanRefresh = canRefresh(childAt, pointF);
                pointF.offset(-pointF2.x, -pointF2.y);
                return zCanRefresh;
            }
        }
        return true;
    }

    public static boolean canScrollVertically(@NonNull View view, int i2) {
        return view.canScrollVertically(i2);
    }

    public static int dp2px(float f2) {
        return (int) ((f2 * density) + 0.5f);
    }

    public static void fling(View view, int i2) {
        if (view instanceof ScrollView) {
            ((ScrollView) view).fling(i2);
            return;
        }
        if (view instanceof AbsListView) {
            ((AbsListView) view).fling(i2);
            return;
        }
        if (view instanceof WebView) {
            ((WebView) view).flingScroll(0, i2);
        } else if (view instanceof NestedScrollView) {
            ((NestedScrollView) view).fling(i2);
        } else if (view instanceof RecyclerView) {
            ((RecyclerView) view).fling(0, i2);
        }
    }

    public static boolean isContentView(View view) {
        return isScrollableView(view) || (view instanceof ViewPager) || (view instanceof NestedScrollingParent);
    }

    public static boolean isScrollableView(View view) {
        return (view instanceof AbsListView) || (view instanceof ScrollView) || (view instanceof ScrollingView) || (view instanceof WebView) || (view instanceof NestedScrollingChild);
    }

    public static boolean isTransformedTouchPointInView(@NonNull View view, @NonNull View view2, float f2, float f3, PointF pointF) {
        if (view2.getVisibility() != 0) {
            return false;
        }
        float[] fArr = {f2, f3};
        fArr[0] = (view.getScrollX() - view2.getLeft()) + f2;
        float scrollY = fArr[1] + (view.getScrollY() - view2.getTop());
        fArr[1] = scrollY;
        float f4 = fArr[0];
        boolean z2 = f4 >= 0.0f && scrollY >= 0.0f && f4 < ((float) view2.getWidth()) && fArr[1] < ((float) view2.getHeight());
        if (z2 && pointF != null) {
            pointF.set(fArr[0] - f2, fArr[1] - f3);
        }
        return z2;
    }

    public static int measureViewHeight(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
        int i2 = layoutParams.height;
        view.measure(childMeasureSpec, i2 > 0 ? View.MeasureSpec.makeMeasureSpec(i2, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
        return view.getMeasuredHeight();
    }

    public static float px2dp(int i2) {
        return i2 / density;
    }

    public static void scrollListBy(@NonNull AbsListView absListView, int i2) {
        absListView.scrollListBy(i2);
    }

    private static float viscousFluid(float f2) {
        float f3 = f2 * 8.0f;
        return f3 < 1.0f ? f3 - (1.0f - ((float) Math.exp(-f3))) : 0.36787945f + ((1.0f - ((float) Math.exp(1.0f - f3))) * 0.63212055f);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f2) {
        if (this.type == INTERPOLATOR_DECELERATE) {
            float f3 = 1.0f - f2;
            return 1.0f - (f3 * f3);
        }
        float fViscousFluid = VISCOUS_FLUID_NORMALIZE * viscousFluid(f2);
        return fViscousFluid > 0.0f ? fViscousFluid + VISCOUS_FLUID_OFFSET : fViscousFluid;
    }
}
