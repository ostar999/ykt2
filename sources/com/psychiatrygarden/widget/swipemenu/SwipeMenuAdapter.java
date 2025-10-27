package com.psychiatrygarden.widget.swipemenu;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import androidx.core.internal.view.SupportMenu;
import com.psychiatrygarden.widget.swipemenu.SwipeMenuListView;
import com.psychiatrygarden.widget.swipemenu.SwipeMenuView;

/* loaded from: classes6.dex */
public class SwipeMenuAdapter implements WrapperListAdapter, SwipeMenuView.OnSwipeItemClickListener {
    private ListAdapter mAdapter;
    private Context mContext;
    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener;

    public SwipeMenuAdapter(Context context, ListAdapter adapter) {
        this.mAdapter = adapter;
        this.mContext = context;
    }

    @Override // android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return this.mAdapter.areAllItemsEnabled();
    }

    public void createMenu(SwipeMenu menu, int pos) {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(this.mContext);
        swipeMenuItem.setTitle("Item 1");
        swipeMenuItem.setBackground(new ColorDrawable(-7829368));
        swipeMenuItem.setWidth(300);
        menu.addMenuItem(swipeMenuItem);
        SwipeMenuItem swipeMenuItem2 = new SwipeMenuItem(this.mContext);
        swipeMenuItem2.setTitle("Item 2");
        swipeMenuItem2.setBackground(new ColorDrawable(SupportMenu.CATEGORY_MASK));
        swipeMenuItem2.setWidth(300);
        menu.addMenuItem(swipeMenuItem2);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mAdapter.getCount();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.mAdapter.getItem(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return this.mAdapter.getItemId(position);
    }

    @Override // android.widget.Adapter
    public int getItemViewType(int position) {
        return this.mAdapter.getItemViewType(position);
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        SwipeMenuLayout swipeMenuLayout;
        if (convertView == null) {
            View view = this.mAdapter.getView(position, convertView, parent);
            SwipeMenu swipeMenu = new SwipeMenu(this.mContext);
            swipeMenu.setViewType(getItemViewType(position));
            createMenu(swipeMenu, position);
            SwipeMenuListView swipeMenuListView = (SwipeMenuListView) parent;
            SwipeMenuView swipeMenuView = new SwipeMenuView(swipeMenu, swipeMenuListView);
            swipeMenuView.setOnSwipeItemClickListener(this);
            swipeMenuLayout = new SwipeMenuLayout(view, swipeMenuView, swipeMenuListView.getCloseInterpolator(), swipeMenuListView.getOpenInterpolator());
            swipeMenuLayout.setPosition(position);
        } else {
            swipeMenuLayout = (SwipeMenuLayout) convertView;
            swipeMenuLayout.closeMenu();
            swipeMenuLayout.setPosition(position);
            this.mAdapter.getView(position, swipeMenuLayout.getContentView(), parent);
        }
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter instanceof BaseSwipListAdapter) {
            swipeMenuLayout.setSwipEnable(((BaseSwipListAdapter) listAdapter).getSwipEnableByPosition(position));
        }
        return swipeMenuLayout;
    }

    @Override // android.widget.Adapter
    public int getViewTypeCount() {
        return this.mAdapter.getViewTypeCount();
    }

    @Override // android.widget.WrapperListAdapter
    public ListAdapter getWrappedAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.Adapter
    public boolean hasStableIds() {
        return this.mAdapter.hasStableIds();
    }

    @Override // android.widget.Adapter
    public boolean isEmpty() {
        return this.mAdapter.isEmpty();
    }

    @Override // android.widget.ListAdapter
    public boolean isEnabled(int position) {
        return this.mAdapter.isEnabled(position);
    }

    @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuView.OnSwipeItemClickListener
    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener = this.onMenuItemClickListener;
        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index);
        }
    }

    @Override // android.widget.Adapter
    public void registerDataSetObserver(DataSetObserver observer) {
        this.mAdapter.registerDataSetObserver(observer);
    }

    public void setOnSwipeItemClickListener(SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @Override // android.widget.Adapter
    public void unregisterDataSetObserver(DataSetObserver observer) {
        this.mAdapter.unregisterDataSetObserver(observer);
    }
}
