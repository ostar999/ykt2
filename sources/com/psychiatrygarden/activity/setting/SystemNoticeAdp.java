package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.knowledge.DailyTasksActivity;
import com.psychiatrygarden.activity.mine.coupons.RedEnvelopeCouponsAct;
import com.psychiatrygarden.bean.MyMessageBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class SystemNoticeAdp extends BaseQuickAdapter<MyMessageBean.DataBean, BaseViewHolder> {
    public SystemNoticeAdp() {
        super(R.layout.item_system_notice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(View view) {
        Context context = this.mContext;
        context.startActivity(RedEnvelopeCouponsAct.newIntent(context, false));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$1(View view) {
        DailyTasksActivity.INSTANCE.navigationToDailyTasksActivity(this.mContext);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(BaseViewHolder holder, MyMessageBean.DataBean item) {
        TextView textView = (TextView) holder.getView(R.id.tv_time);
        TextView textView2 = (TextView) holder.getView(R.id.tv_title);
        TextView textView3 = (TextView) holder.getView(R.id.tv_coupon_title);
        TextView textView4 = (TextView) holder.getView(R.id.tv_content);
        TextView textView5 = (TextView) holder.getView(R.id.tv_coupons_content);
        TextView textView6 = (TextView) holder.getView(R.id.tv_to_details);
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.img_icon);
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.ly_bottom_coupons);
        ImageView imageView = (ImageView) holder.getView(R.id.img_coupons);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.ly_normal_view);
        RelativeLayout relativeLayout2 = (RelativeLayout) holder.getView(R.id.ly_coupons_view);
        TextView textView7 = (TextView) holder.getView(R.id.btn_coupon_txt);
        LinearLayout linearLayout2 = (LinearLayout) holder.getView(R.id.ly_tail);
        TextView textView8 = (TextView) holder.getView(R.id.tv_tail);
        ImageView imageView2 = (ImageView) holder.getView(R.id.img_tail);
        LinearLayout linearLayout3 = (LinearLayout) holder.getView(R.id.ly_validity);
        TextView textView9 = (TextView) holder.getView(R.id.tv_validity);
        ImageView imageView3 = (ImageView) holder.getView(R.id.img_validity);
        LinearLayout linearLayout4 = (LinearLayout) holder.getView(R.id.ly_use_rang);
        TextView textView10 = (TextView) holder.getView(R.id.tv_rang);
        ImageView imageView4 = (ImageView) holder.getView(R.id.img_rang);
        textView.setText(item.getTimestr());
        if (TextUtils.isEmpty(item.getJpush_type()) || !item.getJpush_type().equals("37")) {
            if (!TextUtils.isEmpty(item.getJpush_type()) && item.getJpush_type().equals("46")) {
                relativeLayout.setVisibility(8);
                relativeLayout2.setVisibility(0);
                textView5.setText(item.getContent());
                if (item.getTarget_params() != null) {
                    imageView.setVisibility(0);
                    GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getTarget_params().getCoupon_title().getIcon())).into(imageView);
                    textView3.setText(item.getTarget_params().getCoupon_title().getContent());
                    linearLayout4.setVisibility(8);
                    linearLayout3.setVisibility(8);
                    linearLayout2.setVisibility(8);
                    textView7.setText(item.getTarget_params().getClick().getContent());
                }
                linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.a1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13833c.lambda$convert$1(view);
                    }
                });
                return;
            }
            relativeLayout.setVisibility(0);
            relativeLayout2.setVisibility(8);
            textView2.setText(item.getTitle());
            imageView.setVisibility(8);
            textView4.setText(item.getContent());
            if (TextUtils.isEmpty(item.getImg())) {
                roundedImageView.setVisibility(8);
                GlideApp.with(this.mContext).load(Integer.valueOf(R.drawable.app_icon)).into(roundedImageView);
            } else {
                roundedImageView.setVisibility(0);
                GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getImg())).into(roundedImageView);
            }
            if (item.getTarget_params() == null || item.getTarget_params().getPush_type().equals("0")) {
                textView6.setVisibility(8);
                return;
            } else {
                textView6.setVisibility(0);
                return;
            }
        }
        relativeLayout.setVisibility(8);
        relativeLayout2.setVisibility(0);
        if (item.getTarget_params() != null) {
            imageView.setVisibility(0);
            textView5.setText(item.getTarget_params().getDesc());
            GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getTarget_params().getCoupon_title().getIcon())).into(imageView);
            textView3.setText(item.getTarget_params().getCoupon_title().getContent());
            if (item.getTarget_params().getCoupon_notice_type().equals("1")) {
                if (item.getTarget_params().getUse_range() != null) {
                    linearLayout4.setVisibility(0);
                    GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getTarget_params().getUse_range().getIcon())).into(imageView4);
                    textView10.setText(item.getTarget_params().getUse_range().getContent());
                } else {
                    linearLayout4.setVisibility(8);
                }
                if (item.getTarget_params().getValidity() != null) {
                    linearLayout3.setVisibility(0);
                    GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getTarget_params().getValidity().getIcon())).into(imageView3);
                    textView9.setText(item.getTarget_params().getValidity().getContent());
                } else {
                    linearLayout3.setVisibility(8);
                }
            } else if (item.getTarget_params().getCoupon_notice_type().equals("2")) {
                linearLayout4.setVisibility(8);
                if (item.getTarget_params().getDeductible_amount() != null) {
                    linearLayout3.setVisibility(0);
                    GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getTarget_params().getDeductible_amount().getIcon())).into(imageView3);
                    textView9.setText(item.getTarget_params().getDeductible_amount().getContent());
                } else {
                    linearLayout3.setVisibility(8);
                }
            } else {
                linearLayout4.setVisibility(8);
                linearLayout3.setVisibility(8);
            }
            if (item.getTarget_params().getTail() != null) {
                linearLayout2.setVisibility(0);
                GlideApp.with(this.mContext).load((Object) GlideUtils.generateUrl(item.getTarget_params().getTail().getIcon())).into(imageView2);
                textView8.setText(item.getTarget_params().getTail().getContent());
            } else {
                linearLayout2.setVisibility(8);
            }
            textView7.setText(item.getTarget_params().getClick().getContent());
        }
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.z0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13918c.lambda$convert$0(view);
            }
        });
    }
}
