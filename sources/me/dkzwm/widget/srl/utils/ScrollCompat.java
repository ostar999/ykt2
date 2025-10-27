package me.dkzwm.widget.srl.utils;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.ScrollView;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import me.dkzwm.widget.srl.SmoothRefreshLayout;

/* loaded from: classes9.dex */
public class ScrollCompat {
    public static boolean canAutoLoadMore(View view) {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        int iFindLastVisibleItemPosition;
        if (view instanceof AbsListView) {
            AbsListView absListView = (AbsListView) view;
            int lastVisiblePosition = absListView.getLastVisiblePosition();
            Adapter adapter = absListView.getAdapter();
            return adapter != null && adapter.getCount() > 0 && lastVisiblePosition >= 0 && lastVisiblePosition >= adapter.getCount() - 1;
        }
        if (!isRecyclerView(view) || (layoutManager = (recyclerView = (RecyclerView) view).getLayoutManager()) == null) {
            return false;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.getOrientation() == 0) {
                return false;
            }
            iFindLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (staggeredGridLayoutManager.getOrientation() == 0) {
                return false;
            }
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int[] iArr = new int[spanCount];
            staggeredGridLayoutManager.findLastVisibleItemPositions(iArr);
            iFindLastVisibleItemPosition = iArr[0];
            for (int i2 = 0; i2 < spanCount; i2++) {
                int i3 = iArr[i2];
                if (i3 > iFindLastVisibleItemPosition) {
                    iFindLastVisibleItemPosition = i3;
                }
            }
        } else {
            iFindLastVisibleItemPosition = 0;
        }
        RecyclerView.Adapter adapter2 = recyclerView.getAdapter();
        return adapter2 != null && adapter2.getItemCount() > 0 && iFindLastVisibleItemPosition >= 0 && iFindLastVisibleItemPosition >= adapter2.getItemCount() - 1;
    }

    public static boolean canAutoRefresh(View view) {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        int iFindFirstVisibleItemPosition;
        if (view instanceof AbsListView) {
            AbsListView absListView = (AbsListView) view;
            return absListView.getAdapter() != null && absListView.getLastVisiblePosition() == 0;
        }
        if (!isRecyclerView(view) || (layoutManager = (recyclerView = (RecyclerView) view).getLayoutManager()) == null) {
            return false;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.getOrientation() == 0) {
                return false;
            }
            iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            if (staggeredGridLayoutManager.getOrientation() == 0) {
                return false;
            }
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            int[] iArr = new int[spanCount];
            staggeredGridLayoutManager.findFirstVisibleItemPositions(iArr);
            iFindFirstVisibleItemPosition = iArr[0];
            int i2 = 0;
            while (true) {
                if (i2 >= spanCount) {
                    break;
                }
                if (iArr[i2] == 0) {
                    iFindFirstVisibleItemPosition = 0;
                    break;
                }
                i2++;
            }
        } else {
            iFindFirstVisibleItemPosition = -1;
        }
        return recyclerView.getAdapter() != null && iFindFirstVisibleItemPosition == 0;
    }

    public static boolean canChildScrollDown(View view) {
        return Build.VERSION.SDK_INT < 26 ? ViewCompat.canScrollVertically(view, 1) : view.canScrollVertically(1);
    }

    public static boolean canChildScrollUp(View view) {
        return view.canScrollVertically(-1);
    }

    public static boolean canScaleInternal(View view) {
        return ((view instanceof ScrollView) && ((ScrollView) view).getChildCount() > 0) || ((view instanceof NestedScrollView) && ((NestedScrollView) view).getChildCount() > 0);
    }

    public static void flingCompat(View view, int i2) {
        try {
            if (view instanceof ScrollView) {
                ((ScrollView) view).fling(i2);
            } else if (view instanceof WebView) {
                ((WebView) view).flingScroll(0, i2);
            } else if (isRecyclerView(view)) {
                ((RecyclerView) view).fling(0, i2);
            } else if (view instanceof NestedScrollView) {
                ((NestedScrollView) view).fling(i2);
            } else if (view instanceof AbsListView) {
                ((AbsListView) view).fling(i2);
            }
        } catch (Exception unused) {
        }
    }

    public static boolean isRecyclerView(View view) {
        try {
            return view instanceof RecyclerView;
        } catch (NoClassDefFoundError unused) {
            return false;
        }
    }

    public static boolean isScrollingView(View view) {
        return (view instanceof AbsListView) || (view instanceof ScrollView) || (view instanceof ScrollingView) || (view instanceof WebView);
    }

    public static boolean isViewPager(ViewParent viewParent) {
        return viewParent instanceof ViewPager;
    }

    public static boolean scrollCompat(SmoothRefreshLayout smoothRefreshLayout, View view, float f2) {
        if (view != null) {
            try {
                if (view instanceof AbsListView) {
                    ((AbsListView) view).scrollListBy((int) f2);
                    return true;
                }
                if (!(view instanceof WebView) && !(view instanceof ScrollView) && !(view instanceof NestedScrollView)) {
                    if (isRecyclerView(view)) {
                        RecyclerView recyclerView = (RecyclerView) view;
                        if (recyclerView.getScrollState() == 2) {
                            recyclerView.stopScroll();
                            smoothRefreshLayout.stopNestedScroll(1);
                        }
                        view.scrollBy(0, (int) f2);
                        return true;
                    }
                }
                view.scrollBy(0, (int) f2);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static void stopFling(View view) {
        try {
            if (view instanceof ScrollView) {
                ((ScrollView) view).smoothScrollBy(0, 0);
            } else if (view instanceof WebView) {
                ((WebView) view).flingScroll(0, 0);
            } else if (isRecyclerView(view)) {
                ((RecyclerView) view).stopScroll();
            } else if (view instanceof NestedScrollView) {
                ((NestedScrollView) view).smoothScrollBy(0, 0);
            } else if (view instanceof AbsListView) {
                ((AbsListView) view).smoothScrollBy(0, 0);
            }
        } catch (Exception unused) {
        }
    }
}
