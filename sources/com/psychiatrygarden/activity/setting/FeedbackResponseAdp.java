package com.psychiatrygarden.activity.setting;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.makeramen.roundedimageview.RoundedImageView;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.psychiatrygarden.bean.FeedbackResponseBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.uuzuche.lib_zxing.DisplayUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class FeedbackResponseAdp extends BaseQuickAdapter<FeedbackResponseBean.DataBean, BaseViewHolder> {
    public FeedbackResponseAdp() {
        super(R.layout.item_feedback_response);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(FeedbackResponseBean.DataBean dataBean, View view) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(dataBean.getImage());
        CommonUtil.doPicture(this.mContext, arrayList, 0, null, R.drawable.app_icon);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder holder, final FeedbackResponseBean.DataBean item) {
        TextView textView = (TextView) holder.getView(R.id.tv_replay_time);
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.img_user_head);
        TextView textView2 = (TextView) holder.getView(R.id.tv_user_content);
        final RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.img_pic);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.ly_user_ask);
        RelativeLayout relativeLayout2 = (RelativeLayout) holder.getView(R.id.ly_offical_replay);
        CircleImageView circleImageView2 = (CircleImageView) holder.getView(R.id.img_offical_head);
        TextView textView3 = (TextView) holder.getView(R.id.tv_offical_content);
        View view = holder.getView(R.id.empty_view);
        textView.setText(item.getDate());
        if (item.getType().equals(PLVLinkMicManager.USER)) {
            relativeLayout2.setVisibility(8);
            relativeLayout.setVisibility(0);
            GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getAvatar())).into(circleImageView);
            if (TextUtils.isEmpty(item.getImage())) {
                textView.setVisibility(0);
                roundedImageView.setVisibility(8);
                textView2.setVisibility(0);
                textView2.setText(item.getContent());
            } else {
                textView.setVisibility(8);
                roundedImageView.setVisibility(0);
                textView2.setVisibility(8);
                final int iDip2px = DisplayUtil.dip2px(this.mContext, 120.0f);
                Glide.with(this.mContext).load(item.getImage()).into((RequestBuilder<Drawable>) new SimpleTarget<Drawable>() { // from class: com.psychiatrygarden.activity.setting.FeedbackResponseAdp.1
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                        onResourceReady((Drawable) resource, (Transition<? super Drawable>) transition);
                    }

                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        float intrinsicHeight = resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                        int intrinsicWidth = resource.getIntrinsicWidth();
                        int i2 = iDip2px;
                        if (intrinsicWidth > i2) {
                            intrinsicWidth = i2;
                        }
                        roundedImageView.getLayoutParams().height = (int) (intrinsicWidth * intrinsicHeight);
                        roundedImageView.getLayoutParams().width = intrinsicWidth;
                        roundedImageView.setImageDrawable(resource);
                    }
                });
            }
        } else {
            relativeLayout2.setVisibility(0);
            relativeLayout.setVisibility(8);
            GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getAvatar())).into(circleImageView2);
            textView3.setText(item.getContent());
        }
        if (holder.getLayoutPosition() == this.mData.size() - 1) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
        roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f13891c.lambda$convert$0(item, view2);
            }
        });
    }
}
