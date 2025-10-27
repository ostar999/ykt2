package com.easefun.polyv.livecommon.ui.widget.swipe.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout;
import com.easefun.polyv.livecommon.ui.widget.swipe.implments.PLVSwipeItemMangerImpl;
import com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeAdapterInterface;
import com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface;
import com.easefun.polyv.livecommon.ui.widget.swipe.util.PLVAttributes;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVBaseSwipeAdapter extends BaseAdapter implements PLVSwipeItemMangerInterface, PLVSwipeAdapterInterface {
    protected PLVSwipeItemMangerImpl mItemManger = new PLVSwipeItemMangerImpl(this);

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeAllExcept(PLVSwipeLayout layout) {
        this.mItemManger.closeAllExcept(layout);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeAllItems() {
        this.mItemManger.closeAllItems();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeItem(int position) {
        this.mItemManger.closeItem(position);
    }

    public abstract void fillValues(int position, View convertView);

    public abstract View generateView(int position, ViewGroup parent);

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public PLVAttributes.Mode getMode() {
        return this.mItemManger.getMode();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public List<Integer> getOpenItems() {
        return this.mItemManger.getOpenItems();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public List<PLVSwipeLayout> getOpenLayouts() {
        return this.mItemManger.getOpenLayouts();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeAdapterInterface
    public abstract int getSwipeLayoutResourceId(int position);

    @Override // android.widget.Adapter
    public final View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = generateView(position, parent);
        }
        this.mItemManger.bind(convertView, position);
        fillValues(position, convertView);
        return convertView;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public boolean isOpen(int position) {
        return this.mItemManger.isOpen(position);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeAdapterInterface
    public void notifyDatasetChanged() {
        super.notifyDataSetChanged();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void openItem(int position) {
        this.mItemManger.openItem(position);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void removeShownLayouts(PLVSwipeLayout layout) {
        this.mItemManger.removeShownLayouts(layout);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void setMode(PLVAttributes.Mode mode) {
        this.mItemManger.setMode(mode);
    }
}
