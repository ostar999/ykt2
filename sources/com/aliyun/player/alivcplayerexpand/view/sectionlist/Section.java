package com.aliyun.player.alivcplayerexpand.view.sectionlist;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.view.sectionlist.SectionedRecyclerViewAdapter;

/* loaded from: classes2.dex */
public abstract class Section {

    @LayoutRes
    private final Integer emptyResourceId;
    private final boolean emptyViewWillBeProvided;

    @LayoutRes
    private final Integer failedResourceId;
    private final boolean failedViewWillBeProvided;

    @LayoutRes
    private final Integer footerResourceId;
    private final boolean footerViewWillBeProvided;
    private boolean hasFooter;
    private boolean hasHeader;

    @LayoutRes
    private final Integer headerResourceId;
    private final boolean headerViewWillBeProvided;

    @LayoutRes
    private final Integer itemResourceId;
    private final boolean itemViewWillBeProvided;

    @LayoutRes
    private final Integer loadingResourceId;
    private final boolean loadingViewWillBeProvided;
    private State state = State.LOADED;
    private boolean visible = true;

    /* renamed from: com.aliyun.player.alivcplayerexpand.view.sectionlist.Section$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State = iArr;
            try {
                iArr[State.LOADING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[State.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[State.EMPTY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[State.LOADED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum State {
        LOADING,
        LOADED,
        FAILED,
        EMPTY
    }

    public Section(SectionParameters sectionParameters) {
        boolean z2 = true;
        this.hasHeader = false;
        this.hasFooter = false;
        this.itemResourceId = sectionParameters.itemResourceId;
        Integer num = sectionParameters.headerResourceId;
        this.headerResourceId = num;
        Integer num2 = sectionParameters.footerResourceId;
        this.footerResourceId = num2;
        this.loadingResourceId = sectionParameters.loadingResourceId;
        this.failedResourceId = sectionParameters.failedResourceId;
        this.emptyResourceId = sectionParameters.emptyResourceId;
        this.itemViewWillBeProvided = sectionParameters.itemViewWillBeProvided;
        boolean z3 = sectionParameters.headerViewWillBeProvided;
        this.headerViewWillBeProvided = z3;
        boolean z4 = sectionParameters.footerViewWillBeProvided;
        this.footerViewWillBeProvided = z4;
        this.loadingViewWillBeProvided = sectionParameters.loadingViewWillBeProvided;
        this.failedViewWillBeProvided = sectionParameters.failedViewWillBeProvided;
        this.emptyViewWillBeProvided = sectionParameters.emptyViewWillBeProvided;
        this.hasHeader = num != null || z3;
        if (num2 == null && !z4) {
            z2 = false;
        }
        this.hasFooter = z2;
    }

    public abstract int getContentItemsTotal();

    public final Integer getEmptyResourceId() {
        return this.emptyResourceId;
    }

    public View getEmptyView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("You need to implement getEmptyView() if you set emptyViewWillBeProvided");
    }

    public RecyclerView.ViewHolder getEmptyViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    public final Integer getFailedResourceId() {
        return this.failedResourceId;
    }

    public View getFailedView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("You need to implement getFailedView() if you set failedViewWillBeProvided");
    }

    public RecyclerView.ViewHolder getFailedViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    public final Integer getFooterResourceId() {
        return this.footerResourceId;
    }

    public View getFooterView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("You need to implement getFooterView() if you set footerViewWillBeProvided");
    }

    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    public final Integer getHeaderResourceId() {
        return this.headerResourceId;
    }

    public View getHeaderView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("You need to implement getHeaderView() if you set headerViewWillBeProvided");
    }

    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    public final Integer getItemResourceId() {
        return this.itemResourceId;
    }

    public View getItemView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("You need to implement getItemView() if you set itemViewWillBeProvided");
    }

    public abstract RecyclerView.ViewHolder getItemViewHolder(View view);

    public final Integer getLoadingResourceId() {
        return this.loadingResourceId;
    }

    public View getLoadingView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("You need to implement getLoadingView() if you set loadingViewWillBeProvided");
    }

    public RecyclerView.ViewHolder getLoadingViewHolder(View view) {
        return new SectionedRecyclerViewAdapter.EmptyViewHolder(view);
    }

    public final int getSectionItemsTotal() {
        int i2 = AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[this.state.ordinal()];
        int contentItemsTotal = 1;
        if (i2 != 1 && i2 != 2 && i2 != 3) {
            if (i2 != 4) {
                throw new IllegalStateException("Invalid state");
            }
            contentItemsTotal = getContentItemsTotal();
        }
        return contentItemsTotal + (this.hasHeader ? 1 : 0) + (this.hasFooter ? 1 : 0);
    }

    public final State getState() {
        return this.state;
    }

    public final boolean hasFooter() {
        return this.hasFooter;
    }

    public final boolean hasHeader() {
        return this.hasHeader;
    }

    public final boolean isEmptyViewWillBeProvided() {
        return this.emptyViewWillBeProvided;
    }

    public final boolean isFailedViewWillBeProvided() {
        return this.failedViewWillBeProvided;
    }

    public final boolean isFooterViewWillBeProvided() {
        return this.footerViewWillBeProvided;
    }

    public final boolean isHeaderViewWillBeProvided() {
        return this.headerViewWillBeProvided;
    }

    public final boolean isItemViewWillBeProvided() {
        return this.itemViewWillBeProvided;
    }

    public final boolean isLoadingViewWillBeProvided() {
        return this.loadingViewWillBeProvided;
    }

    public final boolean isVisible() {
        return this.visible;
    }

    public final void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
        int i3 = AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[this.state.ordinal()];
        if (i3 == 1) {
            onBindLoadingViewHolder(viewHolder);
            return;
        }
        if (i3 == 2) {
            onBindFailedViewHolder(viewHolder);
        } else if (i3 == 3) {
            onBindEmptyViewHolder(viewHolder);
        } else {
            if (i3 != 4) {
                throw new IllegalStateException("Invalid state");
            }
            onBindItemViewHolder(viewHolder, i2);
        }
    }

    public void onBindEmptyViewHolder(RecyclerView.ViewHolder viewHolder) {
    }

    public void onBindFailedViewHolder(RecyclerView.ViewHolder viewHolder) {
    }

    public void onBindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
    }

    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {
    }

    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int i2);

    public void onBindLoadingViewHolder(RecyclerView.ViewHolder viewHolder) {
    }

    public final void setHasFooter(boolean z2) {
        this.hasFooter = z2;
    }

    public final void setHasHeader(boolean z2) {
        this.hasHeader = z2;
    }

    public final void setState(State state) {
        int i2 = AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[state.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 == 3 && this.emptyResourceId == null && !this.emptyViewWillBeProvided) {
                    throw new IllegalStateException("Resource id for 'empty state' should be provided or 'emptyViewWillBeProvided' should be set");
                }
            } else if (this.failedResourceId == null && !this.failedViewWillBeProvided) {
                throw new IllegalStateException("Resource id for 'failed state' should be provided or 'failedViewWillBeProvided' should be set");
            }
        } else if (this.loadingResourceId == null && !this.loadingViewWillBeProvided) {
            throw new IllegalStateException("Resource id for 'loading state' should be provided or 'loadingViewWillBeProvided' should be set");
        }
        this.state = state;
    }

    public final void setVisible(boolean z2) {
        this.visible = z2;
    }
}
