package com.psychiatrygarden.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ChooseExpDefaultFilterPopuWindow extends PopupWindow {

    public class DefaultChildFilterAdp extends BaseQuickAdapter<ForumFilterBean.FilterDataBean, BaseViewHolder> {
        private String mType;

        public DefaultChildFilterAdp(String type) {
            super(R.layout.item_exp_default_filter);
            this.mType = type;
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, ForumFilterBean.FilterDataBean item) {
            item.setType(this.mType);
            TextView textView = (TextView) holder.getView(R.id.tv_title);
            TextView textView2 = (TextView) holder.getView(R.id.tv_label);
            ((RecyclerView) holder.getView(R.id.child_recycler)).setVisibility(8);
            textView.setVisibility(8);
            textView2.setVisibility(0);
            textView2.setSelected(item.isSelected());
            TextPaint paint = textView2.getPaint();
            if (item.isSelected()) {
                textView2.setSelected(true);
                paint.setFakeBoldText(true);
            } else {
                textView2.setSelected(false);
                paint.setFakeBoldText(false);
            }
            textView2.invalidate();
            textView2.setText(item.getTitle());
        }
    }

    public class DefaultFilterAdp extends BaseQuickAdapter<ForumFilterBean.FilterDataBean, BaseViewHolder> {
        private ProjectChoosedInterface itemChooseLisenter;
        private PopupWindow popupWindow;

        public DefaultFilterAdp(PopupWindow popupWindow, ProjectChoosedInterface itemChooseLisenter) {
            super(R.layout.item_exp_default_filter);
            this.itemChooseLisenter = itemChooseLisenter;
            this.popupWindow = popupWindow;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(int i2, DefaultChildFilterAdp defaultChildFilterAdp, BaseQuickAdapter baseQuickAdapter, View view, int i3) {
            for (int i4 = 0; i4 < getData().size(); i4++) {
                if (i4 == i2) {
                    for (int i5 = 0; i5 < getData().get(i4).getList().size(); i5++) {
                        if (i5 == i3) {
                            getData().get(i4).getList().get(i5).setSelected(true);
                        } else {
                            getData().get(i4).getList().get(i5).setSelected(false);
                        }
                    }
                } else {
                    for (int i6 = 0; i6 < getData().get(i4).getList().size(); i6++) {
                        getData().get(i4).getList().get(i6).setSelected(false);
                    }
                }
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(defaultChildFilterAdp.getItem(i3));
            this.itemChooseLisenter.mItemLinsenter(arrayList);
            this.popupWindow.dismiss();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder holder, ForumFilterBean.FilterDataBean item) {
            TextView textView = (TextView) holder.getView(R.id.tv_title);
            RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.child_recycler);
            final int layoutPosition = holder.getLayoutPosition();
            final DefaultChildFilterAdp defaultChildFilterAdp = ChooseExpDefaultFilterPopuWindow.this.new DefaultChildFilterAdp(item.getType());
            defaultChildFilterAdp.setList(item.getList());
            recyclerView.setAdapter(defaultChildFilterAdp);
            textView.setText(item.getTitle());
            defaultChildFilterAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.l1
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    this.f16663c.lambda$convert$0(layoutPosition, defaultChildFilterAdp, baseQuickAdapter, view, i2);
                }
            });
        }
    }

    public interface ProjectChoosedInterface {
        void mItemDissmissLinsenter();

        void mItemLinsenter(List<ForumFilterBean.FilterDataBean> list);
    }

    public ChooseExpDefaultFilterPopuWindow(final Activity activity, View anchorView, View dropView, int allViewHeight, List<ForumFilterBean.FilterDataBean> datas, final ProjectChoosedInterface itemChooseLisenter) {
        View viewInflate = ((LayoutInflater) activity.getSystemService("layout_inflater")).inflate(R.layout.dialog_exp_default_filter_window, (ViewGroup) null);
        final LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.ly_view);
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recyclerview);
        AnimUtil.fromTopToBottomAnim(linearLayout);
        anchorView.getLocationOnScreen(new int[2]);
        activity.getWindow().setNavigationBarColor(Color.parseColor("#50000000"));
        final PopupWindow popupWindow = new PopupWindow(viewInflate);
        popupWindow.setWidth(-1);
        popupWindow.setHeight(allViewHeight);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#50000000")));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(viewInflate);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.h1
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                ChooseExpDefaultFilterPopuWindow.lambda$new$0(activity, itemChooseLisenter);
            }
        });
        popupWindow.setAnimationStyle(0);
        popupWindow.showAsDropDown(dropView);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.i1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        final DefaultFilterAdp defaultFilterAdp = new DefaultFilterAdp(popupWindow, itemChooseLisenter);
        recyclerView.setAdapter(defaultFilterAdp);
        defaultFilterAdp.setNewInstance(datas);
        defaultFilterAdp.setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.j1
            @Override // com.chad.library.adapter.base.listener.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                ChooseExpDefaultFilterPopuWindow.lambda$new$2(defaultFilterAdp, baseQuickAdapter, view, i2);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.k1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        final int iDip2px = allViewHeight - UIUtil.dip2px(activity, 96.0d);
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.psychiatrygarden.widget.ChooseExpDefaultFilterPopuWindow.1
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(Activity activity, ProjectChoosedInterface projectChoosedInterface) {
        if (SkinManager.getCurrentSkinType(activity) == 0) {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#FBFBFB"));
        } else {
            activity.getWindow().setNavigationBarColor(Color.parseColor("#121622"));
        }
        projectChoosedInterface.mItemDissmissLinsenter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$2(DefaultFilterAdp defaultFilterAdp, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
        defaultFilterAdp.getItem(i2).setSelected(!r0.isSelected());
    }
}
