package com.psychiatrygarden.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.plv.socket.user.PLVAuthorizationBean;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.FollowBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class FollowAdapter extends CommAdapter<FollowBean.DataBean> {
    private List<FollowBean.DataBean> dataList;
    private Context mContext;
    private FollowClickListener mListener;

    public interface FollowClickListener {
        void cancelFollow(String userId);

        void followFriend(String userId);
    }

    public FollowAdapter(Context mContext, List<FollowBean.DataBean> dataList) {
        super(dataList, mContext, R.layout.layout_follow_item);
        this.mContext = mContext;
        this.dataList = dataList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(FollowBean.DataBean dataBean, View view) {
        FollowClickListener followClickListener = this.mListener;
        if (followClickListener != null) {
            followClickListener.cancelFollow(dataBean.getUser_id());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(FollowBean.DataBean dataBean, View view) {
        FollowClickListener followClickListener = this.mListener;
        if (followClickListener != null) {
            followClickListener.followFriend(dataBean.getUser_id());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setDataList(List<FollowBean.DataBean> dataList) {
        this.mData = dataList;
    }

    public void setFollowClickListener(FollowClickListener listener) {
        this.mListener = listener;
    }

    @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
    public void convert(ViewHolder vHolder, final FollowBean.DataBean dataBean, int position) {
        CircleImageView circleImageView = (CircleImageView) vHolder.getView(R.id.iv_header_img);
        TextView textView = (TextView) vHolder.getView(R.id.tv_ranking_name);
        TextView textView2 = (TextView) vHolder.getView(R.id.tv_ranking_grade);
        TextView textView3 = (TextView) vHolder.getView(R.id.tv_status);
        ImageView imageView = (ImageView) vHolder.getView(R.id.vipimg);
        GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(dataBean.getAvatar())).into(circleImageView);
        textView.setText(dataBean.getNickname());
        textView2.setText(String.format("%s关注，%s被关注", dataBean.getFollow_user(), dataBean.getTo_follow_user()));
        if (dataBean.getIs_follow().equals("1") || dataBean.getIs_follow().equals("2")) {
            if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
                textView3.setTextColor(Color.parseColor("#606A8A"));
            } else {
                textView3.setTextColor(Color.parseColor("#606060"));
            }
            if (dataBean.getIs_follow().equals("1")) {
                textView3.setText("已关注");
                textView3.setBackgroundResource(R.drawable.shape_round_a4a1a0);
            } else {
                textView3.setText("互关");
                textView3.setBackgroundResource(R.drawable.fff5f5f5_14);
            }
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.o6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14833c.lambda$convert$0(dataBean, view);
                }
            });
        } else {
            textView3.setTextColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT));
            textView3.setText("关注");
            textView3.setBackgroundResource(R.drawable.shape_full_red);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.p6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14873c.lambda$convert$1(dataBean, view);
                }
            });
        }
        if (dataBean.getIs_vip().equals("1")) {
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
    }
}
