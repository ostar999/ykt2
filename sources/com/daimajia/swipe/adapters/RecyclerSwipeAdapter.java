package com.daimajia.swipe.adapters;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class RecyclerSwipeAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements SwipeItemMangerInterface, SwipeAdapterInterface {
    public SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllExcept(SwipeLayout swipeLayout) {
        this.mItemManger.closeAllExcept(swipeLayout);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeAllItems() {
        this.mItemManger.closeAllItems();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void closeItem(int i2) {
        this.mItemManger.closeItem(i2);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public Attributes.Mode getMode() {
        return this.mItemManger.getMode();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<Integer> getOpenItems() {
        return this.mItemManger.getOpenItems();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public List<SwipeLayout> getOpenLayouts() {
        return this.mItemManger.getOpenLayouts();
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public boolean isOpen(int i2) {
        return this.mItemManger.isOpen(i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public abstract void onBindViewHolder(VH vh, int i2);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int i2);

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void openItem(int i2) {
        this.mItemManger.openItem(i2);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void removeShownLayouts(SwipeLayout swipeLayout) {
        this.mItemManger.removeShownLayouts(swipeLayout);
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public void setMode(Attributes.Mode mode) {
        this.mItemManger.setMode(mode);
    }
}
