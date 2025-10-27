package com.psychiatrygarden.forum;

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
public class ForumRankingAdp extends BaseQuickAdapter<ForumFilterBean.FilterDataBean, BaseViewHolder> {
    private int mChoosedPos;
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void setItemClickAction(int pos, ForumFilterBean.FilterDataBean item, ImageView imgView);
    }

    public ForumRankingAdp() {
        super(R.layout.item_forum_ranking);
        this.mChoosedPos = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(int i2, ForumFilterBean.FilterDataBean filterDataBean, ImageView imageView, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setItemClickAction(i2, filterDataBean, imageView);
        }
    }

    public int getmChoosedPos() {
        return this.mChoosedPos;
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    public void setmChoosedPos(int mChoosedPos) {
        this.mChoosedPos = mChoosedPos;
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
        if (this.mChoosedPos == helper.getLayoutPosition()) {
            textView.setSelected(true);
        } else {
            textView.setSelected(false);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15332c.lambda$convert$0(layoutPosition, item, imageView, view);
            }
        });
    }
}
