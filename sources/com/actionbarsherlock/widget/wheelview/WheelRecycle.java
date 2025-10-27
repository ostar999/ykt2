package com.actionbarsherlock.widget.wheelview;

import android.view.View;
import android.widget.LinearLayout;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class WheelRecycle {
    private List<View> emptyItems;
    private List<View> items;
    private WheelView wheel;

    public WheelRecycle(WheelView wheelView) {
        this.wheel = wheelView;
    }

    private List<View> addView(View view, List<View> list) {
        if (list == null) {
            list = new LinkedList<>();
        }
        list.add(view);
        return list;
    }

    private View getCachedView(List<View> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        View view = list.get(0);
        list.remove(0);
        return view;
    }

    private void recycleView(View view, int i2) {
        int itemsCount = this.wheel.getViewAdapter().getItemsCount();
        if ((i2 < 0 || i2 >= itemsCount) && !this.wheel.isCyclic()) {
            this.emptyItems = addView(view, this.emptyItems);
            return;
        }
        while (i2 < 0) {
            i2 += itemsCount;
        }
        int i3 = i2 % itemsCount;
        this.items = addView(view, this.items);
    }

    public void clearAll() {
        List<View> list = this.items;
        if (list != null) {
            list.clear();
        }
        List<View> list2 = this.emptyItems;
        if (list2 != null) {
            list2.clear();
        }
    }

    public View getEmptyItem() {
        return getCachedView(this.emptyItems);
    }

    public View getItem() {
        return getCachedView(this.items);
    }

    public int recycleItems(LinearLayout linearLayout, int i2, ItemsRange itemsRange) {
        int i3 = 0;
        int i4 = i2;
        while (i3 < linearLayout.getChildCount()) {
            if (itemsRange.contains(i4)) {
                i3++;
            } else {
                recycleView(linearLayout.getChildAt(i3), i4);
                linearLayout.removeViewAt(i3);
                if (i3 == 0) {
                    i2++;
                }
            }
            i4++;
        }
        return i2;
    }
}
