package com.lxj.easyadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.easyadapter.ViewHolder;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b\u0016\u0018\u0000 D*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002:\u0003DEFB\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0012J\u000e\u0010%\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0012J\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000(J\"\u0010&\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010)\u001a\u00020\u000b2\f\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000(J\u001b\u0010*\u001a\u00020#2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010,\u001a\u00028\u0000¢\u0006\u0002\u0010-J\b\u0010.\u001a\u00020\u000bH\u0016J\u0010\u0010/\u001a\u00020\u000b2\u0006\u00100\u001a\u00020\u000bH\u0016J\u0010\u00101\u001a\u0002022\u0006\u0010)\u001a\u00020\u000bH\u0004J\u0010\u00103\u001a\u0002022\u0006\u00100\u001a\u00020\u000bH\u0002J\u0010\u00104\u001a\u0002022\u0006\u00100\u001a\u00020\u000bH\u0002J\u0010\u00105\u001a\u00020#2\u0006\u00106\u001a\u000207H\u0016J\u0018\u00108\u001a\u00020#2\u0006\u0010+\u001a\u00020\u00032\u0006\u00100\u001a\u00020\u000bH\u0016J\u0018\u00109\u001a\u00020\u00032\u0006\u0010:\u001a\u00020;2\u0006\u0010)\u001a\u00020\u000bH\u0016J\u0010\u0010<\u001a\u00020#2\u0006\u0010+\u001a\u00020\u0003H\u0016J\u0016\u0010=\u001a\u00020#2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010>\u001a\u00020\u0012J \u0010?\u001a\u00020#2\u0006\u0010:\u001a\u00020;2\u0006\u0010@\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u000bH\u0004J\u000e\u0010A\u001a\u00020#2\u0006\u0010B\u001a\u00020\u001bJ\b\u0010C\u001a\u000202H\u0004R \u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0006R\u0011\u0010\n\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\r¨\u0006G"}, d2 = {"Lcom/lxj/easyadapter/MultiItemTypeAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/lxj/easyadapter/ViewHolder;", "data", "", "(Ljava/util/List;)V", "getData", "()Ljava/util/List;", PLVRxEncryptDataFunction.SET_DATA_METHOD, "footersCount", "", "getFootersCount", "()I", "headersCount", "getHeadersCount", "mFootViews", "Landroid/util/SparseArray;", "Landroid/view/View;", "mHeaderViews", "mItemDelegateManager", "Lcom/lxj/easyadapter/ItemDelegateManager;", "getMItemDelegateManager", "()Lcom/lxj/easyadapter/ItemDelegateManager;", "setMItemDelegateManager", "(Lcom/lxj/easyadapter/ItemDelegateManager;)V", "mOnItemClickListener", "Lcom/lxj/easyadapter/MultiItemTypeAdapter$OnItemClickListener;", "getMOnItemClickListener", "()Lcom/lxj/easyadapter/MultiItemTypeAdapter$OnItemClickListener;", "setMOnItemClickListener", "(Lcom/lxj/easyadapter/MultiItemTypeAdapter$OnItemClickListener;)V", "realItemCount", "getRealItemCount", "addFootView", "", "view", "addHeaderView", "addItemDelegate", "itemViewDelegate", "Lcom/lxj/easyadapter/ItemDelegate;", "viewType", "convert", "holder", "t", "(Lcom/lxj/easyadapter/ViewHolder;Ljava/lang/Object;)V", "getItemCount", "getItemViewType", "position", "isEnabled", "", "isFooterViewPos", "isHeaderViewPos", "onAttachedToRecyclerView", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "onBindViewHolder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "onViewAttachedToWindow", "onViewHolderCreated", "itemView", "setListener", "viewHolder", "setOnItemClickListener", "onItemClickListener", "useItemDelegateManager", "Companion", "OnItemClickListener", "SimpleOnItemClickListener", "easy-adapter_release"}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private static final int BASE_ITEM_TYPE_HEADER = 100000;

    @NotNull
    private List<? extends T> data;
    private final SparseArray<View> mFootViews;
    private final SparseArray<View> mHeaderViews;

    @NotNull
    private ItemDelegateManager<T> mItemDelegateManager;

    @Nullable
    private OnItemClickListener mOnItemClickListener;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J \u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\f"}, d2 = {"Lcom/lxj/easyadapter/MultiItemTypeAdapter$OnItemClickListener;", "", "onItemClick", "", "view", "Landroid/view/View;", "holder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "position", "", "onItemLongClick", "", "easy-adapter_release"}, k = 1, mv = {1, 1, 15})
    public interface OnItemClickListener {
        void onItemClick(@NotNull View view, @NotNull RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(@NotNull View view, @NotNull RecyclerView.ViewHolder holder, int position);
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\r"}, d2 = {"Lcom/lxj/easyadapter/MultiItemTypeAdapter$SimpleOnItemClickListener;", "Lcom/lxj/easyadapter/MultiItemTypeAdapter$OnItemClickListener;", "()V", "onItemClick", "", "view", "Landroid/view/View;", "holder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "position", "", "onItemLongClick", "", "easy-adapter_release"}, k = 1, mv = {1, 1, 15})
    public static class SimpleOnItemClickListener implements OnItemClickListener {
        @Override // com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
        public void onItemClick(@NotNull View view, @NotNull RecyclerView.ViewHolder holder, int position) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(holder, "holder");
        }

        @Override // com.lxj.easyadapter.MultiItemTypeAdapter.OnItemClickListener
        public boolean onItemLongClick(@NotNull View view, @NotNull RecyclerView.ViewHolder holder, int position) {
            Intrinsics.checkParameterIsNotNull(view, "view");
            Intrinsics.checkParameterIsNotNull(holder, "holder");
            return false;
        }
    }

    public MultiItemTypeAdapter(@NotNull List<? extends T> data) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        this.data = data;
        this.mHeaderViews = new SparseArray<>();
        this.mFootViews = new SparseArray<>();
        this.mItemDelegateManager = new ItemDelegateManager<>();
    }

    private final int getRealItemCount() {
        return (getItemCount() - getHeadersCount()) - getFootersCount();
    }

    private final boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    private final boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    public final void addFootView(@NotNull View view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        SparseArray<View> sparseArray = this.mFootViews;
        sparseArray.put(sparseArray.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public final void addHeaderView(@NotNull View view) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        SparseArray<View> sparseArray = this.mHeaderViews;
        sparseArray.put(sparseArray.size() + 100000, view);
    }

    @NotNull
    public final MultiItemTypeAdapter<T> addItemDelegate(@NotNull ItemDelegate<T> itemViewDelegate) {
        Intrinsics.checkParameterIsNotNull(itemViewDelegate, "itemViewDelegate");
        this.mItemDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public final void convert(@NotNull ViewHolder holder, T t2) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        this.mItemDelegateManager.convert(holder, t2, holder.getAdapterPosition() - getHeadersCount());
    }

    @NotNull
    public final List<T> getData() {
        return this.data;
    }

    public final int getFootersCount() {
        return this.mFootViews.size();
    }

    public final int getHeadersCount() {
        return this.mHeaderViews.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + this.data.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return isHeaderViewPos(position) ? this.mHeaderViews.keyAt(position) : isFooterViewPos(position) ? this.mFootViews.keyAt((position - getHeadersCount()) - getRealItemCount()) : !useItemDelegateManager() ? super.getItemViewType(position) : this.mItemDelegateManager.getItemViewType(this.data.get(position - getHeadersCount()), position - getHeadersCount());
    }

    @NotNull
    public final ItemDelegateManager<T> getMItemDelegateManager() {
        return this.mItemDelegateManager;
    }

    @Nullable
    public final OnItemClickListener getMOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public final boolean isEnabled(int viewType) {
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkParameterIsNotNull(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        WrapperUtils.INSTANCE.onAttachedToRecyclerView(recyclerView, new Function3<GridLayoutManager, GridLayoutManager.SpanSizeLookup, Integer, Integer>() { // from class: com.lxj.easyadapter.MultiItemTypeAdapter.onAttachedToRecyclerView.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Integer invoke(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup spanSizeLookup, Integer num) {
                return Integer.valueOf(invoke(gridLayoutManager, spanSizeLookup, num.intValue()));
            }

            public final int invoke(@NotNull GridLayoutManager layoutManager, @NotNull GridLayoutManager.SpanSizeLookup oldLookup, int i2) {
                Intrinsics.checkParameterIsNotNull(layoutManager, "layoutManager");
                Intrinsics.checkParameterIsNotNull(oldLookup, "oldLookup");
                int itemViewType = MultiItemTypeAdapter.this.getItemViewType(i2);
                if (MultiItemTypeAdapter.this.mHeaderViews.get(itemViewType) == null && MultiItemTypeAdapter.this.mFootViews.get(itemViewType) == null) {
                    return oldLookup.getSpanSize(i2);
                }
                return layoutManager.getSpanCount();
            }
        });
    }

    public final void onViewHolderCreated(@NotNull ViewHolder holder, @NotNull View itemView) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        Intrinsics.checkParameterIsNotNull(itemView, "itemView");
    }

    public final void setData(@NotNull List<? extends T> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        this.data = list;
    }

    public final void setListener(@NotNull ViewGroup parent, @NotNull final ViewHolder viewHolder, int viewType) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        Intrinsics.checkParameterIsNotNull(viewHolder, "viewHolder");
        if (isEnabled(viewType)) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() { // from class: com.lxj.easyadapter.MultiItemTypeAdapter.setListener.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View v2) {
                    if (MultiItemTypeAdapter.this.getMOnItemClickListener() != null) {
                        int adapterPosition = viewHolder.getAdapterPosition() - MultiItemTypeAdapter.this.getHeadersCount();
                        OnItemClickListener mOnItemClickListener = MultiItemTypeAdapter.this.getMOnItemClickListener();
                        if (mOnItemClickListener == null) {
                            Intrinsics.throwNpe();
                        }
                        Intrinsics.checkExpressionValueIsNotNull(v2, "v");
                        mOnItemClickListener.onItemClick(v2, viewHolder, adapterPosition);
                    }
                }
            });
            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.lxj.easyadapter.MultiItemTypeAdapter.setListener.2
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View v2) {
                    if (MultiItemTypeAdapter.this.getMOnItemClickListener() == null) {
                        return false;
                    }
                    int adapterPosition = viewHolder.getAdapterPosition() - MultiItemTypeAdapter.this.getHeadersCount();
                    OnItemClickListener mOnItemClickListener = MultiItemTypeAdapter.this.getMOnItemClickListener();
                    if (mOnItemClickListener == null) {
                        Intrinsics.throwNpe();
                    }
                    Intrinsics.checkExpressionValueIsNotNull(v2, "v");
                    return mOnItemClickListener.onItemLongClick(v2, viewHolder, adapterPosition);
                }
            });
        }
    }

    public final void setMItemDelegateManager(@NotNull ItemDelegateManager<T> itemDelegateManager) {
        Intrinsics.checkParameterIsNotNull(itemDelegateManager, "<set-?>");
        this.mItemDelegateManager = itemDelegateManager;
    }

    public final void setMOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public final void setOnItemClickListener(@NotNull OnItemClickListener onItemClickListener) {
        Intrinsics.checkParameterIsNotNull(onItemClickListener, "onItemClickListener");
        this.mOnItemClickListener = onItemClickListener;
    }

    public final boolean useItemDelegateManager() {
        return this.mItemDelegateManager.getItemViewDelegateCount() > 0;
    }

    @NotNull
    public final MultiItemTypeAdapter<T> addItemDelegate(int viewType, @NotNull ItemDelegate<T> itemViewDelegate) {
        Intrinsics.checkParameterIsNotNull(itemViewDelegate, "itemViewDelegate");
        this.mItemDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            return;
        }
        convert(holder, this.data.get(position - getHeadersCount()));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        if (this.mHeaderViews.get(viewType) != null) {
            ViewHolder.Companion companion = ViewHolder.INSTANCE;
            View view = this.mHeaderViews.get(viewType);
            if (view == null) {
                Intrinsics.throwNpe();
            }
            return companion.createViewHolder(view);
        }
        if (this.mFootViews.get(viewType) != null) {
            ViewHolder.Companion companion2 = ViewHolder.INSTANCE;
            View view2 = this.mFootViews.get(viewType);
            if (view2 == null) {
                Intrinsics.throwNpe();
            }
            return companion2.createViewHolder(view2);
        }
        int layoutId = this.mItemDelegateManager.getItemViewDelegate(viewType).getLayoutId();
        ViewHolder.Companion companion3 = ViewHolder.INSTANCE;
        Context context = parent.getContext();
        Intrinsics.checkExpressionValueIsNotNull(context, "parent.context");
        ViewHolder viewHolderCreateViewHolder = companion3.createViewHolder(context, parent, layoutId);
        onViewHolderCreated(viewHolderCreateViewHolder, viewHolderCreateViewHolder.getConvertView());
        setListener(parent, viewHolderCreateViewHolder, viewType);
        return viewHolderCreateViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(@NotNull ViewHolder holder) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.onViewAttachedToWindow((MultiItemTypeAdapter<T>) holder);
        int layoutPosition = holder.getLayoutPosition();
        if (isHeaderViewPos(layoutPosition) || isFooterViewPos(layoutPosition)) {
            WrapperUtils.INSTANCE.setFullSpan(holder);
        }
    }
}
