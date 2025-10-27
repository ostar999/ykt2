package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.forum.ProjectFilterChildAdp;
import com.psychiatrygarden.forum.ProjectFilterParentAdp;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes6.dex */
public class ChooseProjectPopuWindow extends PopupWindow {
    private int mChoosedParentPos = 0;
    private int mTmpChoosedParentPos = 0;

    public interface ProjectChoosedInterface {
        void mItemDissmissLinsenter();

        void mItemLinsenter(int parentPos, int choosePos, ForumFilterBean.FilterDataBean type);
    }

    public ChooseProjectPopuWindow(final Activity activity, View anchorView, View dropView, int allViewHeight, List<ForumFilterBean.FilterDataBean> datas, int chooseParentPos, final ProjectChoosedInterface itemChooseLisenter) {
        View viewInflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.forum_project_rank_pop_window, (ViewGroup) null);
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.parent_recycler);
        RecyclerView recyclerView2 = (RecyclerView) viewInflate.findViewById(R.id.child_recycler);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ly_content);
        AnimUtil.fromTopToBottomAnim(linearLayout);
        anchorView.getLocationOnScreen(new int[2]);
        activity.getWindow().setNavigationBarColor(Color.parseColor("#50000000"));
        final PopupWindow popupWindow = new PopupWindow(viewInflate);
        final ProjectFilterParentAdp projectFilterParentAdp = new ProjectFilterParentAdp();
        recyclerView.setAdapter(projectFilterParentAdp);
        for (int i2 = 0; i2 < datas.size(); i2++) {
            if (chooseParentPos == i2) {
                datas.get(i2).setSelected(true);
            } else {
                datas.get(i2).setSelected(false);
                for (int i3 = 0; i3 < datas.get(i2).getChildren().size(); i3++) {
                    datas.get(i2).getChildren().get(i3).setSelected(false);
                }
            }
        }
        projectFilterParentAdp.setNewData(datas);
        final ProjectFilterChildAdp projectFilterChildAdp = new ProjectFilterChildAdp();
        recyclerView2.setAdapter(projectFilterChildAdp);
        projectFilterParentAdp.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.widget.d2
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i4) {
                this.f16390a.lambda$new$0(projectFilterParentAdp, projectFilterChildAdp, baseQuickAdapter, view, i4);
            }
        });
        projectFilterChildAdp.setNewData(datas.get(chooseParentPos).getChildren());
        projectFilterChildAdp.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.widget.e2
            @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i4) {
                this.f16421a.lambda$new$1(projectFilterChildAdp, itemChooseLisenter, popupWindow, baseQuickAdapter, view, i4);
            }
        });
        int pxByDp = ScreenUtil.getPxByDp((Context) activity, 320);
        ScreenUtil.getPxByDp((Context) activity, 160);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linearLayout.getLayoutParams();
        if (datas.size() > 5) {
            layoutParams.height = pxByDp;
        } else {
            layoutParams.height = -2;
        }
        linearLayout.setLayoutParams(layoutParams);
        popupWindow.setWidth(-1);
        popupWindow.setHeight(allViewHeight);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50000000")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(viewInflate);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.f2
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ChooseProjectPopuWindow.lambda$new$2(activity, itemChooseLisenter);
            }
        });
        popupWindow.setAnimationStyle(0);
        popupWindow.showAsDropDown(dropView);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.g2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ProjectFilterParentAdp projectFilterParentAdp, ProjectFilterChildAdp projectFilterChildAdp, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ForumFilterBean.FilterDataBean item = projectFilterParentAdp.getItem(i2);
        for (int i3 = 0; i3 < projectFilterParentAdp.getData().size(); i3++) {
            if (projectFilterParentAdp.getData().get(i3).getTitle().equals(item.getTitle())) {
                projectFilterParentAdp.getData().get(i3).setSelected(true);
            } else {
                projectFilterParentAdp.getData().get(i3).setSelected(false);
            }
        }
        this.mChoosedParentPos = i2;
        projectFilterParentAdp.notifyDataSetChanged();
        projectFilterChildAdp.setNewData(item.getChildren());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ProjectFilterChildAdp projectFilterChildAdp, ProjectChoosedInterface projectChoosedInterface, PopupWindow popupWindow, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        ForumFilterBean.FilterDataBean item = projectFilterChildAdp.getItem(i2);
        for (int i3 = 0; i3 < projectFilterChildAdp.getData().size(); i3++) {
            if (projectFilterChildAdp.getData().get(i3).getApp_id().equals(item.getApp_id())) {
                projectFilterChildAdp.getData().get(i3).setSelected(true);
            } else {
                projectFilterChildAdp.getData().get(i3).setSelected(false);
            }
        }
        this.mTmpChoosedParentPos = this.mChoosedParentPos;
        projectFilterChildAdp.notifyDataSetChanged();
        projectChoosedInterface.mItemLinsenter(this.mTmpChoosedParentPos, i2, item);
        popupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$2(Activity activity, ProjectChoosedInterface projectChoosedInterface) {
        if (SkinManager.getCurrentSkinType(activity) == 0) {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        projectChoosedInterface.mItemDissmissLinsenter();
    }
}
