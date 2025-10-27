package com.psychiatrygarden.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ExperienceFilterView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ChooseExperienceFilterPopuWindow extends PopupWindow {

    public interface ProjectChoosedInterface {
        void mItemDissmissLinsenter();

        void mItemLinsenter(List<ForumFilterBean.FilterDataBean> list);
    }

    public ChooseExperienceFilterPopuWindow(final Activity activity, View anchorView, View dropView, int allViewHeight, List<ForumFilterBean.FilterDataBean> datas, List<String> choodedPos, final ProjectChoosedInterface itemChooseLisenter) {
        View viewInflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.dialog_experience_filter_window, (ViewGroup) null);
        final LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ly_view);
        TextView textView = (TextView) viewInflate.findViewById(R.id.btn_reset);
        TextView textView2 = (TextView) viewInflate.findViewById(R.id.btn_sure);
        final LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.ly_add_view);
        AnimUtil.fromTopToBottomAnim(linearLayout);
        anchorView.getLocationOnScreen(new int[2]);
        activity.getWindow().setNavigationBarColor(Color.parseColor("#50000000"));
        final PopupWindow popupWindow = new PopupWindow(viewInflate);
        linearLayout2.removeAllViews();
        for (int i2 = 0; i2 < datas.size(); i2++) {
            ExperienceFilterView experienceFilterView = new ExperienceFilterView(activity);
            experienceFilterView.setData(datas.get(i2), choodedPos, new ExperienceFilterView.FilterItemChoosed() { // from class: com.psychiatrygarden.widget.m1
                @Override // com.psychiatrygarden.widget.ExperienceFilterView.FilterItemChoosed
                public final void mItemChoosedLinsenter(int i3, ForumFilterBean.FilterDataBean filterDataBean, String str) {
                    ChooseExperienceFilterPopuWindow.lambda$new$0(i3, filterDataBean, str);
                }
            });
            linearLayout2.addView(experienceFilterView);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.n1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.o1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseExperienceFilterPopuWindow.lambda$new$2(linearLayout2, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.p1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ChooseExperienceFilterPopuWindow.lambda$new$3(linearLayout2, itemChooseLisenter, popupWindow, view);
            }
        });
        final int iDip2px = allViewHeight - UIUtil.dip2px(activity, 96.0d);
        Log.e("height", "resHeight====>" + allViewHeight + "allHeight===>" + allViewHeight + "maxH===>" + iDip2px);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.ChooseExperienceFilterPopuWindow.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = linearLayout.getHeight();
                Log.e("height", "height====>" + height);
                if (height > iDip2px) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                    layoutParams.height = iDip2px;
                    linearLayout.setLayoutParams(layoutParams);
                }
            }
        });
        popupWindow.setWidth(-1);
        popupWindow.setHeight(allViewHeight);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50000000")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(viewInflate);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.q1
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ChooseExperienceFilterPopuWindow.lambda$new$4(activity, itemChooseLisenter);
            }
        });
        popupWindow.setAnimationStyle(0);
        popupWindow.showAsDropDown(dropView);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.r1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(int i2, ForumFilterBean.FilterDataBean filterDataBean, String str) {
        filterDataBean.setKey(str);
        filterDataBean.setmTempKey(filterDataBean.getType() + StrPool.UNDERLINE + str);
        filterDataBean.setmTmpPos(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$2(LinearLayout linearLayout, View view) {
        for (int i2 = 0; i2 < linearLayout.getChildCount(); i2++) {
            ((ExperienceFilterView) linearLayout.getChildAt(i2)).resetChoose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$3(LinearLayout linearLayout, ProjectChoosedInterface projectChoosedInterface, PopupWindow popupWindow, View view) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < linearLayout.getChildCount(); i2++) {
            arrayList.addAll(((ExperienceFilterView) linearLayout.getChildAt(i2)).getSelectedItem());
        }
        projectChoosedInterface.mItemLinsenter(arrayList);
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$4(Activity activity, ProjectChoosedInterface projectChoosedInterface) {
        if (SkinManager.getCurrentSkinType(activity) == 0) {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        projectChoosedInterface.mItemDissmissLinsenter();
    }
}
