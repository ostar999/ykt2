package com.psychiatrygarden.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.ScreenUtil;
import com.youth.banner.adapter.BannerAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class BannerHomeAdsAdapter extends BannerAdapter<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO, BannerViewHolder> {

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }

    public BannerHomeAdsAdapter(List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> mDatas) {
        super(mDatas);
    }

    @Override // com.youth.banner.holder.IViewHolder
    public void onBindView(BannerViewHolder holder, HomepageSmallAdBean.DataDTO.DataAd.AdsDTO data, int position, int size) {
        LogUtils.e("img_width_height", "width:" + ScreenUtil.getPxByDp(holder.itemView.getContext(), 325) + ";;;height=" + ScreenUtil.getPxByDp(holder.itemView.getContext(), 403));
        try {
            Glide.with(holder.itemView).load(data.getImg()).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.2f).apply((BaseRequestOptions<?>) new RequestOptions().override(500, 700)).into(holder.imageView);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.youth.banner.holder.IViewHolder
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return new BannerViewHolder(imageView);
    }
}
