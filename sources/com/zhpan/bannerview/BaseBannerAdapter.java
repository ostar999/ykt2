package com.zhpan.bannerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.zhpan.bannerview.utils.BannerUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes8.dex */
public abstract class BaseBannerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    public static final int MAX_VALUE = 1000;
    private boolean isCanLoop;
    protected List<T> mList = new ArrayList();
    private PageClickListener mPageClickListener;

    public interface PageClickListener {
        void onPageClick(View view, int i2, int i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateViewHolder$0(BaseViewHolder baseViewHolder, View view) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (this.mPageClickListener == null || adapterPosition == -1) {
            return;
        }
        this.mPageClickListener.onPageClick(view, BannerUtils.getRealPosition(adapterPosition, getListSize()), adapterPosition);
    }

    public abstract void bindData(BaseViewHolder<T> baseViewHolder, T t2, int i2, int i3);

    public BaseViewHolder<T> createViewHolder(@NonNull ViewGroup viewGroup, View view, int i2) {
        return new BaseViewHolder<>(view);
    }

    public List<T> getData() {
        return this.mList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        if (!this.isCanLoop || getListSize() <= 1) {
            return getListSize();
        }
        return 1000;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i2) {
        return getViewType(BannerUtils.getRealPosition(i2, getListSize()));
    }

    @LayoutRes
    public abstract int getLayoutId(int i2);

    public int getListSize() {
        return this.mList.size();
    }

    public int getViewType(int i2) {
        return 0;
    }

    public boolean isCanLoop() {
        return this.isCanLoop;
    }

    public void setCanLoop(boolean z2) {
        this.isCanLoop = z2;
    }

    public void setData(List<? extends T> list) {
        if (list != null) {
            this.mList.clear();
            this.mList.addAll(list);
        }
    }

    public void setPageClickListener(PageClickListener pageClickListener) {
        this.mPageClickListener = pageClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(@NonNull BaseViewHolder<T> baseViewHolder, int i2) {
        int realPosition = BannerUtils.getRealPosition(i2, getListSize());
        bindData(baseViewHolder, this.mList.get(realPosition), realPosition, getListSize());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public final BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i2) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutId(i2), viewGroup, false);
        final BaseViewHolder<T> baseViewHolderCreateViewHolder = createViewHolder(viewGroup, viewInflate, i2);
        viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.zhpan.bannerview.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26562c.lambda$onCreateViewHolder$0(baseViewHolderCreateViewHolder, view);
            }
        });
        return baseViewHolderCreateViewHolder;
    }
}
