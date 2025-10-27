package com.youth.banner.adapter;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.youth.banner.R;
import com.youth.banner.holder.IViewHolder;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes8.dex */
public abstract class BannerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IViewHolder<T, VH> {
    protected List<T> mDatas = new ArrayList();
    private int mIncreaseCount = 2;
    private OnBannerListener<T> mOnBannerListener;
    private VH mViewHolder;

    public BannerAdapter(List<T> list) {
        setDatas(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBindViewHolder$0(Object obj, int i2, View view) {
        this.mOnBannerListener.OnBannerClick(obj, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreateViewHolder$1(RecyclerView.ViewHolder viewHolder, View view) {
        if (this.mOnBannerListener != null) {
            this.mOnBannerListener.OnBannerClick(viewHolder.itemView.getTag(R.id.banner_data_key), ((Integer) viewHolder.itemView.getTag(R.id.banner_pos_key)).intValue());
        }
    }

    public T getData(int i2) {
        return this.mDatas.get(i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return getRealCount() > 1 ? getRealCount() + this.mIncreaseCount : getRealCount();
    }

    public int getRealCount() {
        List<T> list = this.mDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public T getRealData(int i2) {
        return this.mDatas.get(getRealPosition(i2));
    }

    public int getRealPosition(int i2) {
        return BannerUtils.getRealPosition(this.mIncreaseCount == 2, i2, getRealCount());
    }

    public VH getViewHolder() {
        return this.mViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(@NonNull VH vh, int i2) {
        this.mViewHolder = vh;
        final int realPosition = getRealPosition(i2);
        final T t2 = this.mDatas.get(realPosition);
        vh.itemView.setTag(R.id.banner_data_key, t2);
        vh.itemView.setTag(R.id.banner_pos_key, Integer.valueOf(realPosition));
        onBindView(vh, this.mDatas.get(realPosition), realPosition, getRealCount());
        if (this.mOnBannerListener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.youth.banner.adapter.b
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f26553c.lambda$onBindViewHolder$0(t2, realPosition, view);
                }
            });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        final VH vhOnCreateHolder = onCreateHolder(viewGroup, i2);
        vhOnCreateHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.youth.banner.adapter.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26551c.lambda$onCreateViewHolder$1(vhOnCreateHolder, view);
            }
        });
        return vhOnCreateHolder;
    }

    public void setDatas(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.mDatas = list;
        notifyDataSetChanged();
    }

    public void setIncreaseCount(int i2) {
        this.mIncreaseCount = i2;
    }

    public void setOnBannerListener(OnBannerListener<T> onBannerListener) {
        this.mOnBannerListener = onBannerListener;
    }
}
