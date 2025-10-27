package com.aliyun.player.alivcplayerexpand.view.sectionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.view.sectionlist.Section;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes2.dex */
public class SectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 5;
    public static final int VIEW_TYPE_FAILED = 4;
    public static final int VIEW_TYPE_FOOTER = 1;
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM_LOADED = 2;
    public static final int VIEW_TYPE_LOADING = 3;
    private static final int VIEW_TYPE_QTY = 6;
    private int viewTypeCount = 0;
    private final Map<String, Section> sections = new LinkedHashMap();
    private final Map<String, Integer> sectionViewTypeNumbers = new LinkedHashMap();

    /* renamed from: com.aliyun.player.alivcplayerexpand.view.sectionlist.SectionedRecyclerViewAdapter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State;

        static {
            int[] iArr = new int[Section.State.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State = iArr;
            try {
                iArr[Section.State.LOADED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[Section.State.LOADING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[Section.State.FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[Section.State.EMPTY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {
        public EmptyViewHolder(View view) {
            super(view);
        }
    }

    private RecyclerView.ViewHolder getEmptyViewHolder(ViewGroup viewGroup, Section section) {
        View viewInflate;
        if (section.isEmptyViewWillBeProvided()) {
            viewInflate = section.getEmptyView(viewGroup);
            if (viewInflate == null) {
                throw new NullPointerException("Section.getEmptyView() returned null");
            }
        } else {
            Integer emptyResourceId = section.getEmptyResourceId();
            if (emptyResourceId == null) {
                throw new NullPointerException("Missing 'empty' resource id");
            }
            viewInflate = inflate(emptyResourceId.intValue(), viewGroup);
        }
        return section.getEmptyViewHolder(viewInflate);
    }

    private RecyclerView.ViewHolder getFailedViewHolder(ViewGroup viewGroup, Section section) {
        View viewInflate;
        if (section.isFailedViewWillBeProvided()) {
            viewInflate = section.getFailedView(viewGroup);
            if (viewInflate == null) {
                throw new NullPointerException("Section.getFailedView() returned null");
            }
        } else {
            Integer failedResourceId = section.getFailedResourceId();
            if (failedResourceId == null) {
                throw new NullPointerException("Missing 'failed' resource id");
            }
            viewInflate = inflate(failedResourceId.intValue(), viewGroup);
        }
        return section.getFailedViewHolder(viewInflate);
    }

    private RecyclerView.ViewHolder getFooterViewHolder(ViewGroup viewGroup, Section section) {
        View viewInflate;
        if (section.isFooterViewWillBeProvided()) {
            viewInflate = section.getFooterView(viewGroup);
            if (viewInflate == null) {
                throw new NullPointerException("Section.getFooterView() returned null");
            }
        } else {
            Integer footerResourceId = section.getFooterResourceId();
            if (footerResourceId == null) {
                throw new NullPointerException("Missing 'footer' resource id");
            }
            viewInflate = inflate(footerResourceId.intValue(), viewGroup);
        }
        return section.getFooterViewHolder(viewInflate);
    }

    private RecyclerView.ViewHolder getHeaderViewHolder(ViewGroup viewGroup, Section section) {
        View viewInflate;
        if (section.isHeaderViewWillBeProvided()) {
            viewInflate = section.getHeaderView(viewGroup);
            if (viewInflate == null) {
                throw new NullPointerException("Section.getHeaderView() returned null");
            }
        } else {
            Integer headerResourceId = section.getHeaderResourceId();
            if (headerResourceId == null) {
                throw new NullPointerException("Missing 'header' resource id");
            }
            viewInflate = inflate(headerResourceId.intValue(), viewGroup);
        }
        return section.getHeaderViewHolder(viewInflate);
    }

    private RecyclerView.ViewHolder getItemViewHolder(ViewGroup viewGroup, Section section) {
        View viewInflate;
        if (section.isItemViewWillBeProvided()) {
            viewInflate = section.getItemView(viewGroup);
            if (viewInflate == null) {
                throw new NullPointerException("Section.getItemView() returned null");
            }
        } else {
            Integer itemResourceId = section.getItemResourceId();
            if (itemResourceId == null) {
                throw new NullPointerException("Missing 'item' resource id");
            }
            viewInflate = inflate(itemResourceId.intValue(), viewGroup);
        }
        return section.getItemViewHolder(viewInflate);
    }

    private RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup viewGroup, Section section) {
        View viewInflate;
        if (section.isLoadingViewWillBeProvided()) {
            viewInflate = section.getLoadingView(viewGroup);
            if (viewInflate == null) {
                throw new NullPointerException("Section.getLoadingView() returned null");
            }
        } else {
            Integer loadingResourceId = section.getLoadingResourceId();
            if (loadingResourceId == null) {
                throw new NullPointerException("Missing 'loading' resource id");
            }
            viewInflate = inflate(loadingResourceId.intValue(), viewGroup);
        }
        return section.getLoadingViewHolder(viewInflate);
    }

    @NonNull
    private Section getValidSectionOrThrowException(String str) {
        Section section = getSection(str);
        if (section != null) {
            return section;
        }
        throw new IllegalArgumentException("Invalid tag: " + str);
    }

    public void addSection(String str, Section section) {
        this.sections.put(str, section);
        this.sectionViewTypeNumbers.put(str, Integer.valueOf(this.viewTypeCount));
        this.viewTypeCount += 6;
    }

    @VisibleForTesting
    public void callSuperNotifyItemChanged(int i2) {
        super.notifyItemChanged(i2);
    }

    @VisibleForTesting
    public void callSuperNotifyItemInserted(int i2) {
        super.notifyItemInserted(i2);
    }

    @VisibleForTesting
    public void callSuperNotifyItemMoved(int i2, int i3) {
        super.notifyItemMoved(i2, i3);
    }

    @VisibleForTesting
    public void callSuperNotifyItemRangeChanged(int i2, int i3) {
        super.notifyItemRangeChanged(i2, i3);
    }

    @VisibleForTesting
    public void callSuperNotifyItemRangeInserted(int i2, int i3) {
        super.notifyItemRangeInserted(i2, i3);
    }

    @VisibleForTesting
    public void callSuperNotifyItemRangeRemoved(int i2, int i3) {
        super.notifyItemRangeRemoved(i2, i3);
    }

    @VisibleForTesting
    public void callSuperNotifyItemRemoved(int i2) {
        super.notifyItemRemoved(i2);
    }

    @NonNull
    public Map<String, Section> getCopyOfSectionsMap() {
        LinkedHashMap linkedHashMap;
        synchronized (this.sections) {
            linkedHashMap = new LinkedHashMap(this.sections);
        }
        return linkedHashMap;
    }

    public int getFooterPositionInAdapter(String str) {
        return getFooterPositionInAdapter(getValidSectionOrThrowException(str));
    }

    public int getHeaderPositionInAdapter(String str) {
        return getHeaderPositionInAdapter(getValidSectionOrThrowException(str));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        Iterator<Map.Entry<String, Section>> it = this.sections.entrySet().iterator();
        int sectionItemsTotal = 0;
        while (it.hasNext()) {
            Section value = it.next().getValue();
            if (value.isVisible()) {
                sectionItemsTotal += value.getSectionItemsTotal();
            }
        }
        return sectionItemsTotal;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        int i3;
        int i4 = 0;
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            Section value = entry.getValue();
            if (value.isVisible()) {
                int sectionItemsTotal = value.getSectionItemsTotal();
                if (i2 >= i4 && i2 <= (i3 = (i4 + sectionItemsTotal) - 1)) {
                    int iIntValue = this.sectionViewTypeNumbers.get(entry.getKey()).intValue();
                    if (value.hasHeader() && i2 == i4) {
                        return iIntValue;
                    }
                    if (value.hasFooter() && i2 == i3) {
                        return iIntValue + 1;
                    }
                    int i5 = AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$view$sectionlist$Section$State[value.getState().ordinal()];
                    if (i5 == 1) {
                        return iIntValue + 2;
                    }
                    if (i5 == 2) {
                        return iIntValue + 3;
                    }
                    if (i5 == 3) {
                        return iIntValue + 4;
                    }
                    if (i5 == 4) {
                        return iIntValue + 5;
                    }
                    throw new IllegalStateException("Invalid state");
                }
                i4 += sectionItemsTotal;
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    public int getPositionInAdapter(String str, int i2) {
        return getPositionInAdapter(getValidSectionOrThrowException(str), i2);
    }

    public int getPositionInSection(int i2) {
        Iterator<Map.Entry<String, Section>> it = this.sections.entrySet().iterator();
        int i3 = 0;
        while (it.hasNext()) {
            Section value = it.next().getValue();
            if (value.isVisible()) {
                int sectionItemsTotal = value.getSectionItemsTotal();
                if (i2 >= i3 && i2 <= (i3 + sectionItemsTotal) - 1) {
                    return (i2 - i3) - (value.hasHeader() ? 1 : 0);
                }
                i3 += sectionItemsTotal;
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    public Section getSection(String str) {
        return this.sections.get(str);
    }

    public Section getSectionForPosition(int i2) {
        Iterator<Map.Entry<String, Section>> it = this.sections.entrySet().iterator();
        int i3 = 0;
        while (it.hasNext()) {
            Section value = it.next().getValue();
            if (value.isVisible()) {
                int sectionItemsTotal = value.getSectionItemsTotal();
                if (i2 >= i3 && i2 <= (i3 + sectionItemsTotal) - 1) {
                    return value;
                }
                i3 += sectionItemsTotal;
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    public int getSectionItemViewType(int i2) {
        return getItemViewType(i2) % 6;
    }

    @Deprecated
    public int getSectionPosition(int i2) {
        return getPositionInSection(i2);
    }

    @VisibleForTesting
    public View inflate(@LayoutRes int i2, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(i2, viewGroup, false);
    }

    public void notifyFooterChangedInSection(String str) {
        notifyFooterChangedInSection(getValidSectionOrThrowException(str));
    }

    public void notifyFooterInsertedInSection(String str) {
        notifyFooterInsertedInSection(getValidSectionOrThrowException(str));
    }

    public void notifyFooterRemovedFromSection(String str) {
        notifyFooterRemovedFromSection(getValidSectionOrThrowException(str));
    }

    public void notifyHeaderChangedInSection(String str) {
        notifyHeaderChangedInSection(getValidSectionOrThrowException(str));
    }

    public void notifyHeaderInsertedInSection(String str) {
        notifyHeaderInsertedInSection(getValidSectionOrThrowException(str));
    }

    public void notifyHeaderRemovedFromSection(String str) {
        notifyHeaderRemovedFromSection(getValidSectionOrThrowException(str));
    }

    public void notifyItemChangedInSection(String str, int i2) {
        callSuperNotifyItemChanged(getPositionInAdapter(str, i2));
    }

    public void notifyItemInsertedInSection(String str, int i2) {
        callSuperNotifyItemInserted(getPositionInAdapter(str, i2));
    }

    public void notifyItemMovedInSection(String str, int i2, int i3) {
        callSuperNotifyItemMoved(getPositionInAdapter(str, i2), getPositionInAdapter(str, i3));
    }

    public void notifyItemRangeChangedInSection(String str, int i2, int i3) {
        callSuperNotifyItemRangeChanged(getPositionInAdapter(str, i2), i3);
    }

    public void notifyItemRangeInsertedInSection(String str, int i2, int i3) {
        callSuperNotifyItemRangeInserted(getPositionInAdapter(str, i2), i3);
    }

    public void notifyItemRangeRemovedFromSection(String str, int i2, int i3) {
        callSuperNotifyItemRangeRemoved(getPositionInAdapter(str, i2), i3);
    }

    public void notifyItemRemovedFromSection(String str, int i2) {
        callSuperNotifyItemRemoved(getPositionInAdapter(str, i2));
    }

    public void notifyNotLoadedStateChanged(String str, Section.State state) {
        notifyNotLoadedStateChanged(getValidSectionOrThrowException(str), state);
    }

    public void notifySectionChangedToInvisible(String str, int i2) {
        notifySectionChangedToInvisible(getValidSectionOrThrowException(str), i2);
    }

    public void notifySectionChangedToVisible(String str) {
        notifySectionChangedToVisible(getValidSectionOrThrowException(str));
    }

    public void notifyStateChangedFromLoaded(String str, int i2) {
        notifyStateChangedFromLoaded(getValidSectionOrThrowException(str), i2);
    }

    public void notifyStateChangedToLoaded(String str, Section.State state) {
        notifyStateChangedToLoaded(getValidSectionOrThrowException(str), state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
        int i3;
        Iterator<Map.Entry<String, Section>> it = this.sections.entrySet().iterator();
        int i4 = 0;
        while (it.hasNext()) {
            Section value = it.next().getValue();
            if (value.isVisible()) {
                int sectionItemsTotal = value.getSectionItemsTotal();
                if (i2 >= i4 && i2 <= (i4 + sectionItemsTotal) - 1) {
                    if (value.hasHeader() && i2 == i4) {
                        getSectionForPosition(i2).onBindHeaderViewHolder(viewHolder);
                        return;
                    } else if (value.hasFooter() && i2 == i3) {
                        getSectionForPosition(i2).onBindFooterViewHolder(viewHolder);
                        return;
                    } else {
                        getSectionForPosition(i2).onBindContentViewHolder(viewHolder, getPositionInSection(i2));
                        return;
                    }
                }
                i4 += sectionItemsTotal;
            }
        }
        throw new IndexOutOfBoundsException("Invalid position");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        RecyclerView.ViewHolder emptyViewHolder = null;
        for (Map.Entry<String, Integer> entry : this.sectionViewTypeNumbers.entrySet()) {
            if (i2 >= entry.getValue().intValue() && i2 < entry.getValue().intValue() + 6) {
                Section section = this.sections.get(entry.getKey());
                int iIntValue = i2 - entry.getValue().intValue();
                if (iIntValue == 0) {
                    emptyViewHolder = getHeaderViewHolder(viewGroup, section);
                } else if (iIntValue == 1) {
                    emptyViewHolder = getFooterViewHolder(viewGroup, section);
                } else if (iIntValue == 2) {
                    emptyViewHolder = getItemViewHolder(viewGroup, section);
                } else if (iIntValue == 3) {
                    emptyViewHolder = getLoadingViewHolder(viewGroup, section);
                } else if (iIntValue == 4) {
                    emptyViewHolder = getFailedViewHolder(viewGroup, section);
                } else {
                    if (iIntValue != 5) {
                        throw new IllegalArgumentException("Invalid viewType");
                    }
                    emptyViewHolder = getEmptyViewHolder(viewGroup, section);
                }
            }
        }
        return emptyViewHolder;
    }

    public void removeAllSections() {
        this.sections.clear();
    }

    public void removeSection(Section section) {
        String key = null;
        for (Map.Entry<String, Section> entry : this.sections.entrySet()) {
            if (entry.getValue() == section) {
                key = entry.getKey();
            }
        }
        if (key != null) {
            removeSection(key);
        }
    }

    @VisibleForTesting
    public void callSuperNotifyItemRangeChanged(int i2, int i3, Object obj) {
        super.notifyItemRangeChanged(i2, i3, obj);
    }

    public int getSectionPosition(String str) {
        return getSectionPosition(getValidSectionOrThrowException(str));
    }

    public void notifyFooterChangedInSection(Section section) {
        callSuperNotifyItemChanged(getFooterPositionInAdapter(section));
    }

    public void notifyHeaderChangedInSection(Section section) {
        callSuperNotifyItemChanged(getHeaderPositionInAdapter(section));
    }

    public void notifyItemChangedInSection(Section section, int i2) {
        callSuperNotifyItemChanged(getPositionInAdapter(section, i2));
    }

    public void notifyItemInsertedInSection(Section section, int i2) {
        callSuperNotifyItemInserted(getPositionInAdapter(section, i2));
    }

    public void notifyItemMovedInSection(Section section, int i2, int i3) {
        callSuperNotifyItemMoved(getPositionInAdapter(section, i2), getPositionInAdapter(section, i3));
    }

    public void notifyItemRangeChangedInSection(Section section, int i2, int i3) {
        callSuperNotifyItemRangeChanged(getPositionInAdapter(section, i2), i3);
    }

    public void notifyItemRangeInsertedInSection(Section section, int i2, int i3) {
        callSuperNotifyItemRangeInserted(getPositionInAdapter(section, i2), i3);
    }

    public void notifyItemRangeRemovedFromSection(Section section, int i2, int i3) {
        callSuperNotifyItemRangeRemoved(getPositionInAdapter(section, i2), i3);
    }

    public void notifyItemRemovedFromSection(Section section, int i2) {
        callSuperNotifyItemRemoved(getPositionInAdapter(section, i2));
    }

    public int getFooterPositionInAdapter(Section section) {
        if (section.hasFooter()) {
            return (getSectionPosition(section) + section.getSectionItemsTotal()) - 1;
        }
        throw new IllegalStateException("Section doesn't have a footer");
    }

    public int getHeaderPositionInAdapter(Section section) {
        if (section.hasHeader()) {
            return getSectionPosition(section);
        }
        throw new IllegalStateException("Section doesn't have a header");
    }

    public int getPositionInAdapter(Section section, int i2) {
        return getSectionPosition(section) + (section.hasHeader() ? 1 : 0) + i2;
    }

    public void notifyFooterInsertedInSection(Section section) {
        callSuperNotifyItemInserted(getFooterPositionInAdapter(section));
    }

    public void notifyFooterRemovedFromSection(Section section) {
        callSuperNotifyItemRemoved(getSectionPosition(section) + section.getSectionItemsTotal());
    }

    public void notifyHeaderInsertedInSection(Section section) {
        callSuperNotifyItemInserted(getHeaderPositionInAdapter(section));
    }

    public void notifyHeaderRemovedFromSection(Section section) {
        callSuperNotifyItemRemoved(getSectionPosition(section));
    }

    public void notifyItemRangeChangedInSection(String str, int i2, int i3, Object obj) {
        callSuperNotifyItemRangeChanged(getPositionInAdapter(str, i2), i3, obj);
    }

    public void notifyNotLoadedStateChanged(Section section, Section.State state) {
        Section.State state2 = section.getState();
        if (state2 != state) {
            Section.State state3 = Section.State.LOADED;
            if (state == state3) {
                throw new IllegalStateException("Use notifyStateChangedFromLoaded");
            }
            if (state2 != state3) {
                notifyItemChangedInSection(section, 0);
                return;
            }
            throw new IllegalStateException("Use notifyStateChangedToLoaded");
        }
        throw new IllegalStateException("No state changed");
    }

    public void notifySectionChangedToInvisible(Section section, int i2) {
        if (!section.isVisible()) {
            callSuperNotifyItemRangeRemoved(i2, section.getSectionItemsTotal());
            return;
        }
        throw new IllegalStateException("This section is not visible.");
    }

    public void notifySectionChangedToVisible(Section section) {
        if (section.isVisible()) {
            callSuperNotifyItemRangeInserted(getSectionPosition(section), section.getSectionItemsTotal());
            return;
        }
        throw new IllegalStateException("This section is not visible.");
    }

    public void notifyStateChangedFromLoaded(Section section, int i2) {
        if (section.getState() == Section.State.LOADED) {
            throw new IllegalStateException("Use notifyStateChangedToLoaded");
        }
        if (i2 == 0) {
            notifyItemInsertedInSection(section, 0);
            return;
        }
        if (i2 > 1) {
            notifyItemRangeRemovedFromSection(section, 1, i2 - 1);
        }
        notifyItemChangedInSection(section, 0);
    }

    public void notifyStateChangedToLoaded(Section section, Section.State state) {
        Section.State state2 = section.getState();
        if (state2 != state) {
            Section.State state3 = Section.State.LOADED;
            if (state2 != state3) {
                if (state == state3) {
                    throw new IllegalStateException("Use notifyStateChangedFromLoaded");
                }
                throw new IllegalStateException("Use notifyNotLoadedStateChanged");
            }
            int contentItemsTotal = section.getContentItemsTotal();
            if (contentItemsTotal == 0) {
                notifyItemRemovedFromSection(section, 0);
                return;
            }
            notifyItemChangedInSection(section, 0);
            if (contentItemsTotal > 1) {
                notifyItemRangeInsertedInSection(section, 1, contentItemsTotal - 1);
                return;
            }
            return;
        }
        throw new IllegalStateException("No state changed");
    }

    public String addSection(Section section) {
        String string = UUID.randomUUID().toString();
        addSection(string, section);
        return string;
    }

    public int getSectionPosition(Section section) {
        Iterator<Map.Entry<String, Section>> it = this.sections.entrySet().iterator();
        int sectionItemsTotal = 0;
        while (it.hasNext()) {
            Section value = it.next().getValue();
            if (value.isVisible()) {
                if (value == section) {
                    return sectionItemsTotal;
                }
                sectionItemsTotal += value.getSectionItemsTotal();
            }
        }
        throw new IllegalArgumentException("Invalid section");
    }

    public void notifyItemRangeChangedInSection(Section section, int i2, int i3, Object obj) {
        callSuperNotifyItemRangeChanged(getPositionInAdapter(section, i2), i3, obj);
    }

    public void removeSection(String str) {
        this.sections.remove(str);
        this.sectionViewTypeNumbers.remove(str);
    }
}
