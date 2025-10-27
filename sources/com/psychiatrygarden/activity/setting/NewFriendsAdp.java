package com.psychiatrygarden.activity.setting;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.bean.FollowBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class NewFriendsAdp extends BaseQuickAdapter<FollowBean.DataBean, BaseViewHolder> {
    private boolean isNewFriend;
    private OnItemActionLisenter onItemActionLisenter;

    public static abstract class OnItemActionLisenter {
        public abstract void setUserHeadLisenter(int pos, FollowBean.DataBean item);

        public abstract void setUserStatusLisenter(int pos, FollowBean.DataBean item);
    }

    public NewFriendsAdp(boolean isNewFriend) {
        super(R.layout.item_new_friends);
        this.isNewFriend = isNewFriend;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(BaseViewHolder baseViewHolder, FollowBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setUserHeadLisenter(baseViewHolder.getLayoutPosition(), dataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(BaseViewHolder baseViewHolder, FollowBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setUserHeadLisenter(baseViewHolder.getLayoutPosition(), dataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$2(BaseViewHolder baseViewHolder, FollowBean.DataBean dataBean, View view) {
        OnItemActionLisenter onItemActionLisenter = this.onItemActionLisenter;
        if (onItemActionLisenter != null) {
            onItemActionLisenter.setUserStatusLisenter(baseViewHolder.getLayoutPosition(), dataBean);
        }
    }

    public void setOnItemActionLisenter(OnItemActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(final BaseViewHolder holder, final FollowBean.DataBean item) {
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.img_head);
        TextView textView = (TextView) holder.getView(R.id.tv_nickname);
        textView.setText(item.getNickname());
        TextView textView2 = (TextView) holder.getView(R.id.tv_status);
        GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getAvatar())).into(circleImageView);
        if (this.isNewFriend) {
            textView2.setVisibility(0);
            if (item.getIs_follow().equals("1")) {
                if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                    textView2.setTextColor(Color.parseColor("#606A8A"));
                } else {
                    textView2.setTextColor(Color.parseColor("#606060"));
                }
                textView2.setBackgroundResource(R.drawable.shape_round_a4a1a0);
                textView2.setText("已关注");
            } else {
                textView2.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
                textView2.setText("关注");
                textView2.setBackgroundResource(R.drawable.shape_full_red);
            }
        } else {
            textView2.setVisibility(8);
        }
        circleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13830c.lambda$convert$0(holder, item, view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13835c.lambda$convert$1(holder, item, view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13840c.lambda$convert$2(holder, item, view);
            }
        });
    }
}
