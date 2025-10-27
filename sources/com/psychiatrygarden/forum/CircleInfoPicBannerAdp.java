package com.psychiatrygarden.forum;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.psychiatrygarden.bean.CircleInfoBean;
import com.youth.banner.adapter.BannerAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class CircleInfoPicBannerAdp extends BannerAdapter<CircleInfoBean.DataBean.ImgBean, BannerViewHolder> {

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }

    public CircleInfoPicBannerAdp(List<CircleInfoBean.DataBean.ImgBean> mDatas) {
        super(mDatas);
    }

    @Override // com.youth.banner.holder.IViewHolder
    public void onBindView(BannerViewHolder holder, CircleInfoBean.DataBean.ImgBean data, int position, int size) {
        try {
            Glide.with(holder.itemView).load(data.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.2f).apply((BaseRequestOptions<?>) new RequestOptions().override(500, 700)).into(holder.imageView);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.youth.banner.holder.IViewHolder
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        return new BannerViewHolder(imageView);
    }
}
