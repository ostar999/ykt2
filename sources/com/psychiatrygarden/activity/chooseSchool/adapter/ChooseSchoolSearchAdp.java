package com.psychiatrygarden.activity.chooseSchool.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.ForumFilterBean;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ChooseSchoolSearchAdp extends BaseQuickAdapter<ForumFilterBean.FilterDataBean, BaseViewHolder> {
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void setItemClickAction(int pos, ForumFilterBean.FilterDataBean item, ImageView imgView);
    }

    public ChooseSchoolSearchAdp() {
        super(R.layout.item_choose_school_search_view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(int i2, ForumFilterBean.FilterDataBean filterDataBean, ImageView imageView, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setItemClickAction(i2, filterDataBean, imageView);
        }
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, final ForumFilterBean.FilterDataBean item) {
        LinearLayout linearLayout = (LinearLayout) helper.getView(R.id.ly_all_rank);
        ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) linearLayout.getLayoutParams())).width = (UIUtil.getScreenWidth(getContext()) - (UIUtil.dip2px(getContext(), 16.0d) * 2)) / getData().size();
        TextView textView = (TextView) helper.getView(R.id.tv_all_rank);
        final ImageView imageView = (ImageView) helper.getView(R.id.img_all_rank);
        final int layoutPosition = helper.getLayoutPosition();
        linearLayout.setGravity(17);
        textView.setText(item.getTitle());
        textView.setSelected(item.isSelected());
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.chooseSchool.adapter.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11202c.lambda$convert$0(layoutPosition, item, imageView, view);
            }
        });
    }
}
