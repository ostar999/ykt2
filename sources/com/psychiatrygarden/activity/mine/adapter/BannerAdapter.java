package com.psychiatrygarden.activity.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.psychiatrygarden.bean.HomepageSmallAdBean;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class BannerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> mImages;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.iv_banner);
        }
    }

    public BannerAdapter(Context context, List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> images) {
        this.mContext = context;
        this.mImages = images;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(int i2, View view) {
        OnItemClickListener onItemClickListener = this.mListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(i2);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<HomepageSmallAdBean.DataDTO.DataAd.AdsDTO> list = this.mImages;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (!TextUtils.isEmpty(this.mImages.get(position).getImg())) {
            Glide.with(this.mContext).load(this.mImages.get(position).getImg()).into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.mine.adapter.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f12770c.lambda$onBindViewHolder$0(position, view);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_banner, parent, false));
    }
}
