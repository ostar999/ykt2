package com.psychiatrygarden.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import com.youth.banner.adapter.BannerAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class BannerQuestionAdsAdapter extends BannerAdapter<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO, BannerViewHolder> {
    private boolean margin;

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;

        public BannerViewHolder(@NonNull RoundedImageView view) {
            super(view);
            this.imageView = view;
        }
    }

    public BannerQuestionAdsAdapter(List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> mDatas) {
        super(mDatas);
        this.margin = true;
    }

    @Override // com.youth.banner.holder.IViewHolder
    public void onBindView(BannerViewHolder holder, HomepageSmallAdBean.DataDTO.DataAd.AdsDTO data, int position, int size) {
        int i2 = R.color.fourth_line_backgroup_color;
        try {
            if (AndroidBaseUtils.isPad(holder.imageView.getContext()) && AndroidBaseUtils.isCurOriLand(holder.imageView.getContext())) {
                Glide.with(holder.itemView).load((Object) GlideUtils.generateUrl(data.getPad_img())).placeholder(new ColorDrawable(ContextCompat.getColor(holder.imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(holder.imageView);
            } else {
                Glide.with(holder.itemView).load((Object) GlideUtils.generateUrl(data.getImg())).placeholder(new ColorDrawable(ContextCompat.getColor(holder.imageView.getContext(), SkinManager.getCurrentSkinType(ProjectApp.instance()) == 0 ? R.color.fourth_line_backgroup_color : R.color.bg_backgroud_night))).into(holder.imageView);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            RequestBuilder<Drawable> requestBuilderLoad = Glide.with(holder.itemView).load((Object) GlideUtils.generateUrl(data.getImg()));
            Context context = holder.imageView.getContext();
            if (SkinManager.getCurrentSkinType(ProjectApp.instance()) != 0) {
                i2 = R.color.bg_backgroud_night;
            }
            requestBuilderLoad.placeholder(new ColorDrawable(ContextCompat.getColor(context, i2))).into(holder.imageView);
        }
    }

    @Override // com.youth.banner.holder.IViewHolder
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        RoundedImageView roundedImageView = new RoundedImageView(parent.getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        if (this.margin) {
            layoutParams.setMargins(ScreenUtil.getPxByDp(parent.getContext(), 16), 0, ScreenUtil.getPxByDp(parent.getContext(), 16), 0);
        }
        roundedImageView.setLayoutParams(layoutParams);
        roundedImageView.setCornerRadius(ScreenUtil.getPxByDp(parent.getContext(), 8));
        roundedImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(roundedImageView);
    }

    public BannerQuestionAdsAdapter(List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> mDatas, boolean margin) {
        super(mDatas);
        this.margin = margin;
    }
}
