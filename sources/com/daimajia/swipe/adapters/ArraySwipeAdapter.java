package com.daimajia.swipe.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemAdapterMangerImpl;
import com.daimajia.swipe.interfaces.SwipeAdapterInterface;
import com.daimajia.swipe.interfaces.SwipeItemMangerInterface;
import com.daimajia.swipe.util.Attributes;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class ArraySwipeAdapter<T> extends ArrayAdapter implements SwipeItemMangerInterface, SwipeAdapterInterface {
    private SwipeItemAdapterMangerImpl mItemManger;

    public ArraySwipeAdapter(Context context, int i2) {
        super(context, i2);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

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

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        boolean z2 = view == null;
        View view2 = super.getView(i2, view, viewGroup);
        if (z2) {
            this.mItemManger.initialize(view2, i2);
        } else {
            this.mItemManger.updateConvertView(view2, i2);
        }
        return view2;
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

    public ArraySwipeAdapter(Context context, int i2, int i3) {
        super(context, i2, i3);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i2, T[] tArr) {
        super(context, i2, tArr);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i2, int i3, T[] tArr) {
        super(context, i2, i3, tArr);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i2, List<T> list) {
        super(context, i2, list);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }

    public ArraySwipeAdapter(Context context, int i2, int i3, List<T> list) {
        super(context, i2, i3, list);
        this.mItemManger = new SwipeItemAdapterMangerImpl(this);
    }
}
