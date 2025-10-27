package com.aliyun.svideo.common.baseAdapter;

import android.view.ViewGroup;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.aliyun.svideo.common.baseAdapter.entity.SectionEntity;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BaseSectionQuickAdapter<T extends SectionEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    protected static final int SECTION_HEADER_VIEW = 1092;
    protected int mSectionHeadResId;

    public BaseSectionQuickAdapter(int i2, int i3, List<T> list) {
        super(i2, list);
        this.mSectionHeadResId = i3;
    }

    public abstract void convertHead(K k2, T t2);

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public int getDefItemViewType(int i2) {
        return this.mData.get(i2).isHeader ? 1092 : 0;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public boolean isFixedViewType(int i2) {
        return super.isFixedViewType(i2) || i2 == 1092;
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i2) {
        return i2 == 1092 ? createBaseViewHolder(getItemView(this.mSectionHeadResId, viewGroup)) : (K) super.onCreateDefViewHolder(viewGroup, i2);
    }

    @Override // com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(K k2, int i2) {
        if (k2.getItemViewType() != 1092) {
            super.onBindViewHolder((BaseSectionQuickAdapter<T, K>) k2, i2);
        } else {
            setFullSpan(k2);
            convertHead(k2, getItem(i2 - getHeaderLayoutCount()));
        }
    }
}
