package me.dkzwm.widget.srl.utils;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes9.dex */
public class BoundaryUtil {
    private static final int[] sPoint = new int[2];

    private static boolean isHorizontalView(View view) {
        RecyclerView.LayoutManager layoutManager;
        if ((view instanceof ViewPager) || (view instanceof HorizontalScrollView) || (view instanceof WebView)) {
            return true;
        }
        if (ScrollCompat.isRecyclerView(view) && (layoutManager = ((RecyclerView) view).getLayoutManager()) != null) {
            return layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).getOrientation() == 0 : (layoutManager instanceof StaggeredGridLayoutManager) && ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 0;
        }
        return false;
    }

    public static boolean isInsideHorizontalView(float f2, float f3, @NonNull View view) {
        if (isHorizontalView(view)) {
            return isInsideView(f2, f3, view);
        }
        if (view instanceof ViewGroup) {
            return isInsideViewGroup(f2, f3, (ViewGroup) view);
        }
        return false;
    }

    public static boolean isInsideView(float f2, float f3, @NonNull View view) {
        int[] iArr = sPoint;
        view.getLocationOnScreen(iArr);
        if (f2 <= iArr[0] || f2 >= r2 + view.getWidth()) {
            return false;
        }
        int i2 = iArr[1];
        return f3 > ((float) i2) && f3 < ((float) (i2 + view.getHeight()));
    }

    private static boolean isInsideViewGroup(float f2, float f3, @NonNull ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt.getVisibility() == 0) {
                if (isHorizontalView(childAt)) {
                    if (isInsideView(f2, f3, childAt)) {
                        return true;
                    }
                } else if (childAt instanceof ViewGroup) {
                    return isInsideViewGroup(f2, f3, (ViewGroup) childAt);
                }
            }
        }
        return false;
    }
}
