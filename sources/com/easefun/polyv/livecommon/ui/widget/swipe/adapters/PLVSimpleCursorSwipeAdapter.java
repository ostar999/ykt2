package com.easefun.polyv.livecommon.ui.widget.swipe.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import com.easefun.polyv.livecommon.ui.widget.swipe.PLVSwipeLayout;
import com.easefun.polyv.livecommon.ui.widget.swipe.implments.PLVSwipeItemMangerImpl;
import com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeAdapterInterface;
import com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface;
import com.easefun.polyv.livecommon.ui.widget.swipe.util.PLVAttributes;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class PLVSimpleCursorSwipeAdapter extends SimpleCursorAdapter implements PLVSwipeItemMangerInterface, PLVSwipeAdapterInterface {
    private PLVSwipeItemMangerImpl mItemManger;

    public PLVSimpleCursorSwipeAdapter(Context context, int layout, Cursor c3, String[] from, int[] to, int flags) {
        super(context, layout, c3, from, to, flags);
        this.mItemManger = new PLVSwipeItemMangerImpl(this);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeAllExcept(PLVSwipeLayout layout) {
        this.mItemManger.closeAllExcept(layout);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public void closeItem(int position) {
        this.mItemManger.closeItem(position);
    }

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

    @Override // androidx.cursoradapter.widget.CursorAdapter, android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        this.mItemManger.bind(view, position);
        return view;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.swipe.interfaces.PLVSwipeItemMangerInterface
    public boolean isOpen(int position) {
        return this.mItemManger.isOpen(position);
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

    public PLVSimpleCursorSwipeAdapter(Context context, int layout, Cursor c3, String[] from, int[] to) {
        super(context, layout, c3, from, to);
        this.mItemManger = new PLVSwipeItemMangerImpl(this);
    }
}
