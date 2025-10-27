package com.daimajia.swipe.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemAdapterMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseSwipeAdapter extends BaseAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {
    protected SwipeItemAdapterMangerImpl mItemManger = new SwipeItemAdapterMangerImpl(this);

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

    public abstract void fillValues(int i2, View view);

    public abstract View generateView(int i2, ViewGroup viewGroup);

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

    @Override // com.daimajia.swipe.interfaces.SwipeAdapterInterface
    public abstract int getSwipeLayoutResourceId(int i2);

    @Override // android.widget.Adapter
    public final View getView(int i2, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = generateView(i2, viewGroup);
            this.mItemManger.initialize(view, i2);
        } else {
            this.mItemManger.updateConvertView(view, i2);
        }
        fillValues(i2, view);
        return view;
    }

    @Override // com.daimajia.swipe.interfaces.SwipeItemMangerInterface
    public boolean isOpen(int i2) {
        return this.mItemManger.isOpen(i2);
    }

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
