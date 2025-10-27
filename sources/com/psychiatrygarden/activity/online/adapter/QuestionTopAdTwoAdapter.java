package com.psychiatrygarden.activity.online.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.viewfilter.XMarqueeView;
import com.psychiatrygarden.utils.viewfilter.XMarqueeViewAdapter;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionTopAdTwoAdapter extends BaseQuickAdapter<HomepageSmallAdBean.DataDTO.BlackAds.BlackAd, BaseViewHolder> {
    private OnItemJumpActionLisenter onItemActionLisenter;

    /* renamed from: com.psychiatrygarden.activity.online.adapter.QuestionTopAdTwoAdapter$1, reason: invalid class name */
    public class AnonymousClass1 extends XMarqueeViewAdapter<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> {
        final /* synthetic */ HomepageSmallAdBean.DataDTO.BlackAds.BlackAd val$item;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(List datas, final HomepageSmallAdBean.DataDTO.BlackAds.BlackAd val$item) {
            super(datas);
            this.val$item = val$item;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onBindView$0(HomepageSmallAdBean.DataDTO.BlackAds.BlackAd blackAd, int i2, View view) {
            if (QuestionTopAdTwoAdapter.this.onItemActionLisenter != null) {
                QuestionTopAdTwoAdapter.this.onItemActionLisenter.setItemLisenter(blackAd.getList().get(i2).getExtra());
            }
        }

        @Override // com.psychiatrygarden.utils.viewfilter.XMarqueeViewAdapter
        public void onBindView(View parent, View view, final int position) {
            TextView textView = (TextView) view.findViewById(R.id.tv_ad_title);
            TextView textView2 = (TextView) view.findViewById(R.id.tv_ad_des);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_ad_icon);
            ImageView imageView2 = (ImageView) view.findViewById(R.id.iv_xm_ad);
            Drawable drawable = ContextCompat.getDrawable(QuestionTopAdTwoAdapter.this.getContext(), R.drawable.ffeeeeee_8_stroke_1);
            if (drawable instanceof GradientDrawable) {
                if (SkinManager.getCurrentSkinType(QuestionTopAdTwoAdapter.this.getContext()) == 1) {
                    GradientDrawable gradientDrawable = (GradientDrawable) drawable;
                    gradientDrawable.setColor(Color.parseColor("#121622"));
                    gradientDrawable.setStroke(CommonUtil.dip2px(QuestionTopAdTwoAdapter.this.getContext(), 0.5f), Color.parseColor("#2e3241"));
                } else {
                    GradientDrawable gradientDrawable2 = (GradientDrawable) drawable;
                    gradientDrawable2.setStroke(CommonUtil.dip2px(QuestionTopAdTwoAdapter.this.getContext(), 0.5f), Color.parseColor("#eeeeee"));
                    gradientDrawable2.setColor(Color.parseColor("#FFFFFF"));
                }
                view.setBackground(drawable);
            }
            boolean zIsEmpty = TextUtils.isEmpty(this.val$item.getList().get(position).getSmallLabel());
            int i2 = R.color.fourth_line_backgroup_color;
            if (zIsEmpty) {
                imageView2.setVisibility(8);
            } else {
                imageView2.setVisibility(0);
                Glide.with(QuestionTopAdTwoAdapter.this.getContext()).load((Object) GlideUtils.generateUrl(this.val$item.getList().get(position).getSmallLabel())).placeholder(new ColorDrawable(ContextCompat.getColor(imageView2.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(imageView2);
            }
            textView.setText(this.val$item.getList().get(position).getTitle());
            textView2.setText(this.val$item.getList().get(position).getSubhead());
            RequestBuilder<Drawable> requestBuilderLoad = Glide.with(QuestionTopAdTwoAdapter.this.getContext()).load((Object) GlideUtils.generateUrl(this.val$item.getList().get(position).getIcon()));
            Context context = imageView.getContext();
            if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                i2 = R.color.bg_backgroud_night;
            }
            requestBuilderLoad.placeholder(new ColorDrawable(ContextCompat.getColor(context, i2))).into(imageView);
            final HomepageSmallAdBean.DataDTO.BlackAds.BlackAd blackAd = this.val$item;
            parent.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.online.adapter.v
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f13141c.lambda$onBindView$0(blackAd, position, view2);
                }
            });
        }

        @Override // com.psychiatrygarden.utils.viewfilter.XMarqueeViewAdapter
        public View onCreateView(XMarqueeView parent) {
            return LayoutInflater.from(QuestionTopAdTwoAdapter.this.getContext()).inflate(R.layout.head_question_top_ad_two_child_item, (ViewGroup) null);
        }
    }

    public static abstract class OnItemJumpActionLisenter {
        public abstract void setItemLisenter(HomepageSmallAdBean.DataDTO.DataAd.AdsDTO.ExtraDTO data);
    }

    public QuestionTopAdTwoAdapter(@Nullable List<HomepageSmallAdBean.DataDTO.BlackAds.BlackAd> data) {
        super(R.layout.head_question_top_ad_two_child, data);
    }

    public void setOnItemActionLisenter(OnItemJumpActionLisenter lisenter) {
        this.onItemActionLisenter = lisenter;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, HomepageSmallAdBean.DataDTO.BlackAds.BlackAd item) {
        XMarqueeView xMarqueeView = (XMarqueeView) helper.getView(R.id.xMarqueeView);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ffeeeeee_8_stroke_1);
        int currentSkinType = SkinManager.getCurrentSkinType(getContext());
        if (drawable instanceof GradientDrawable) {
            if (currentSkinType == 1) {
                GradientDrawable gradientDrawable = (GradientDrawable) drawable;
                gradientDrawable.setColor(Color.parseColor("#121622"));
                gradientDrawable.setStroke(CommonUtil.dip2px(getContext(), 0.5f), Color.parseColor("#2e3241"));
            } else {
                GradientDrawable gradientDrawable2 = (GradientDrawable) drawable;
                gradientDrawable2.setStroke(CommonUtil.dip2px(getContext(), 0.5f), Color.parseColor("#eeeeee"));
                gradientDrawable2.setColor(Color.parseColor("#FFFFFF"));
            }
            xMarqueeView.setBackground(drawable);
        } else {
            xMarqueeView.setBackgroundResource(R.drawable.ffeeeeee_8_stroke_1);
        }
        if (item.getList().size() > 1) {
            xMarqueeView.setFlipInterval(item.getIntervalOfRotation() * 1000);
            xMarqueeView.setFlippingLessCount(true);
        } else {
            xMarqueeView.setFlipInterval(86400000);
            xMarqueeView.setFlippingLessCount(false);
        }
        xMarqueeView.setAdapter(new AnonymousClass1(item.getList(), item));
    }
}
