package com.easefun.polyv.livecommon.module.utils;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.easefun.polyv.livecommon.ui.widget.itemview.adapter.PLVViewPagerAdapter;
import com.plv.thirdpart.blankj.utilcode.util.BarUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class PLVViewInitUtils {
    public static <T extends View> T get(View view, int i2) {
        SparseArray sparseArray = (SparseArray) view.getTag();
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            view.setTag(sparseArray);
        }
        T t2 = (T) sparseArray.get(i2);
        if (t2 != null) {
            return t2;
        }
        T t3 = (T) view.findViewById(i2);
        sparseArray.put(i2, t3);
        return t3;
    }

    public static void initHeightWithStatusBar(View view, int defaultViewHeightDp, int defaultStatusBarHeightDp) {
        int statusBarHeight = BarUtils.getStatusBarHeight();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ConvertUtils.dp2px(defaultViewHeightDp) + Math.max(statusBarHeight, ConvertUtils.dp2px(defaultStatusBarHeightDp));
        view.setLayoutParams(layoutParams);
    }

    public static void initMarginTopWithStatusBar(View view, int defaultStatusBarHeightDp) {
        int statusBarHeight = BarUtils.getStatusBarHeight();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = Math.max(statusBarHeight, ConvertUtils.dp2px(defaultStatusBarHeightDp));
        view.setLayoutParams(marginLayoutParams);
    }

    public static View initPopupWindow(View v2, @LayoutRes int resource, final PopupWindow popupWindow, View.OnClickListener listener) {
        View viewInflate = LayoutInflater.from(v2.getContext()).inflate(resource, (ViewGroup) null, false);
        popupWindow.setContentView(viewInflate);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setWidth(-1);
        popupWindow.setHeight(-1);
        viewInflate.setOnClickListener(listener);
        return viewInflate;
    }

    public static void initViewPager(FragmentManager fragmentManager, ViewPager viewPager, int selItem, Fragment... fragments) throws Resources.NotFoundException {
        viewPager.setAdapter(new PLVViewPagerAdapter(fragmentManager, new ArrayList(Arrays.asList(fragments))));
        viewPager.setOffscreenPageLimit(r0.size() - 1);
        viewPager.setCurrentItem(selItem);
    }
}
