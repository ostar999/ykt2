package com.aliyun.svideo.common.baseAdapter;

import android.util.SparseArray;
import android.view.View;
import androidx.annotation.Nullable;
import com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.aliyun.svideo.common.baseAdapter.delegate.MultiTypeDelegate;
import com.aliyun.svideo.common.baseAdapter.provider.BaseItemProvider;
import com.aliyun.svideo.common.baseAdapter.utils.ProviderDelegate;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {
    private SparseArray<BaseItemProvider> mItemProviders;
    protected ProviderDelegate mProviderDelegate;

    public MultipleItemRvAdapter(@Nullable List<T> list) {
        super(list);
    }

    private void bindClick(final V v2, final T t2, final int i2, final BaseItemProvider baseItemProvider) {
        BaseQuickAdapter.OnItemClickListener onItemClickListener = getOnItemClickListener();
        BaseQuickAdapter.OnItemLongClickListener onItemLongClickListener = getOnItemLongClickListener();
        if (onItemClickListener == null || onItemLongClickListener == null) {
            View view = v2.itemView;
            if (onItemClickListener == null) {
                view.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.baseAdapter.MultipleItemRvAdapter.2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        baseItemProvider.onClick(v2, t2, i2);
                    }
                });
            }
            if (onItemLongClickListener == null) {
                view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.aliyun.svideo.common.baseAdapter.MultipleItemRvAdapter.3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view2) {
                        return baseItemProvider.onLongClick(v2, t2, i2);
                    }
                });
            }
        }
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public void convert(V v2, T t2) {
        BaseItemProvider baseItemProvider = this.mItemProviders.get(v2.getItemViewType());
        baseItemProvider.mContext = v2.itemView.getContext();
        int layoutPosition = v2.getLayoutPosition() - getHeaderLayoutCount();
        baseItemProvider.convert(v2, t2, layoutPosition);
        bindClick(v2, t2, layoutPosition, baseItemProvider);
    }

    public void finishInitialize() {
        this.mProviderDelegate = new ProviderDelegate();
        setMultiTypeDelegate(new MultiTypeDelegate<T>() { // from class: com.aliyun.svideo.common.baseAdapter.MultipleItemRvAdapter.1
            @Override // com.aliyun.svideo.common.baseAdapter.delegate.MultiTypeDelegate
            public int getItemType(T t2) {
                return MultipleItemRvAdapter.this.getViewType(t2);
            }
        });
        registerItemProvider();
        this.mItemProviders = this.mProviderDelegate.getItemProviders();
        for (int i2 = 0; i2 < this.mItemProviders.size(); i2++) {
            int iKeyAt = this.mItemProviders.keyAt(i2);
            BaseItemProvider baseItemProvider = this.mItemProviders.get(iKeyAt);
            baseItemProvider.mData = this.mData;
            getMultiTypeDelegate().registerItemType(iKeyAt, baseItemProvider.layout());
        }
    }

    public abstract int getViewType(T t2);

    public abstract void registerItemProvider();
}
