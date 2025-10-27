package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class ChooseFilterPopuWindow extends PopupWindow {
    boolean mOneList;
    String mTypeDescribe;

    public interface ProjectChoosedInterface {
        void mItemDissmissLinsenter();

        void mItemLinsenter(int choosePos, ForumFilterBean.FilterDataBean type);
    }

    public ChooseFilterPopuWindow(Activity activity, View dropView, int allViewHeight, List<ForumFilterBean.FilterDataBean> mList, ProjectChoosedInterface itemChooseLisenter, boolean isChooseSchool, boolean oneList, String type_describe) {
        this.mOneList = oneList;
        this.mTypeDescribe = type_describe;
        initPopupWindow(activity, dropView, allViewHeight, mList, itemChooseLisenter, isChooseSchool);
    }

    private void initPopupWindow(final Activity activity, View dropView, int allViewHeight, List<ForumFilterBean.FilterDataBean> mList, final ProjectChoosedInterface itemChooseLisenter, final boolean isChooseSchool) {
        Resources resources;
        int i2;
        View viewInflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.forum_type_rank_top_pop_window, (ViewGroup) null);
        GridView gridView = (GridView) viewInflate.findViewById(R.id.gridview);
        RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.typeDescribeRl);
        TextView textView = (TextView) viewInflate.findViewById(R.id.type_describe_tv);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ly_content);
        if (this.mOneList) {
            gridView.setNumColumns(1);
        } else {
            gridView.setNumColumns(isChooseSchool ? 4 : 3);
        }
        AnimUtil.fromTopToBottomAnim(linearLayout);
        setGridViewMaxHeight(linearLayout, allViewHeight - CommonUtil.dip2px(activity, 96.0f));
        activity.getWindow().setNavigationBarColor(Color.parseColor("#50000000"));
        final PopupWindow popupWindow = new PopupWindow(viewInflate);
        final CommAdapter<ForumFilterBean.FilterDataBean> commAdapter = new CommAdapter<ForumFilterBean.FilterDataBean>(mList, activity, R.layout.item_rank_value) { // from class: com.psychiatrygarden.widget.ChooseFilterPopuWindow.1
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, ForumFilterBean.FilterDataBean dataBean, int position) {
                Resources resources2;
                int i3;
                Resources resources3;
                int i4;
                TextView textView2 = (TextView) vHolder.getView(R.id.tv_title);
                if (ChooseFilterPopuWindow.this.mOneList) {
                    ViewGroup.LayoutParams layoutParams = textView2.getLayoutParams();
                    layoutParams.height = CommonUtil.dip2px(activity, 40.0f);
                    textView2.setLayoutParams(layoutParams);
                }
                textView2.setText(dataBean.getTitle());
                TextPaint paint = textView2.getPaint();
                if (dataBean.isSelected()) {
                    if (SkinManager.getCurrentSkinType(activity) == 0) {
                        resources3 = activity.getResources();
                        i4 = R.color.main_theme_color;
                    } else {
                        resources3 = activity.getResources();
                        i4 = R.color.main_theme_color_night;
                    }
                    textView2.setTextColor(resources3.getColor(i4));
                    textView2.setBackgroundResource(R.drawable.shape_main_color_round8);
                    paint.setFakeBoldText(true);
                } else {
                    paint.setFakeBoldText(false);
                    if (SkinManager.getCurrentSkinType(activity) == 0) {
                        resources2 = activity.getResources();
                        i3 = R.color.first_txt_color;
                    } else {
                        resources2 = activity.getResources();
                        i3 = R.color.first_txt_color_night;
                    }
                    textView2.setTextColor(resources2.getColor(i3));
                    textView2.setBackgroundResource(R.drawable.shape_bg_one_round8);
                }
                textView2.invalidate();
                notifyDataSetChanged();
            }
        };
        gridView.setAdapter((ListAdapter) commAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.widget.s1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i3, long j2) {
                ChooseFilterPopuWindow.lambda$initPopupWindow$0(commAdapter, isChooseSchool, itemChooseLisenter, popupWindow, adapterView, view, i3, j2);
            }
        });
        if (!TextUtils.isEmpty(this.mTypeDescribe)) {
            relativeLayout.setVisibility(0);
            if (SkinManager.getCurrentSkinType(activity) == 1) {
                resources = activity.getResources();
                i2 = R.color.tertiary_text_color_night;
            } else {
                resources = activity.getResources();
                i2 = R.color.tertiary_text_color;
            }
            textView.setTextColor(resources.getColor(i2));
            textView.setText("      " + this.mTypeDescribe);
        }
        popupWindow.setWidth(-1);
        popupWindow.setHeight(allViewHeight);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50000000")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(viewInflate);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.t1
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ChooseFilterPopuWindow.lambda$initPopupWindow$1(activity, itemChooseLisenter);
            }
        });
        popupWindow.setAnimationStyle(0);
        popupWindow.showAsDropDown(dropView);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.u1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initPopupWindow$0(CommAdapter commAdapter, boolean z2, ProjectChoosedInterface projectChoosedInterface, PopupWindow popupWindow, AdapterView adapterView, View view, int i2, long j2) {
        ForumFilterBean.FilterDataBean filterDataBean = (ForumFilterBean.FilterDataBean) commAdapter.getItem(i2);
        if (z2) {
            filterDataBean.setSelected(!filterDataBean.isSelected());
        } else {
            filterDataBean.setSelected(true);
        }
        projectChoosedInterface.mItemLinsenter(i2, filterDataBean);
        commAdapter.notifyDataSetChanged();
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initPopupWindow$1(Activity activity, ProjectChoosedInterface projectChoosedInterface) {
        if (SkinManager.getCurrentSkinType(activity) == 0) {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        projectChoosedInterface.mItemDissmissLinsenter();
    }

    private void setGridViewMaxHeight(final LinearLayout gridView, final int maxHeight) {
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.ChooseFilterPopuWindow.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (gridView.getHeight() > maxHeight) {
                    ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                    layoutParams.height = maxHeight;
                    gridView.setLayoutParams(layoutParams);
                }
            }
        });
    }

    public ChooseFilterPopuWindow(Activity activity, View dropView, int allViewHeight, List<ForumFilterBean.FilterDataBean> mList, ProjectChoosedInterface itemChooseLisenter, boolean isChooseSchool, boolean oneList) {
        this.mTypeDescribe = "";
        this.mOneList = oneList;
        initPopupWindow(activity, dropView, allViewHeight, mList, itemChooseLisenter, isChooseSchool);
    }

    public ChooseFilterPopuWindow(Activity activity, View dropView, int allViewHeight, List<ForumFilterBean.FilterDataBean> mList, ProjectChoosedInterface itemChooseLisenter, boolean isChooseSchool) {
        this.mOneList = false;
        this.mTypeDescribe = "";
        initPopupWindow(activity, dropView, allViewHeight, mList, itemChooseLisenter, isChooseSchool);
    }
}
