package com.hyphenate.easeui.manager;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseSidebar;
import java.util.List;

/* loaded from: classes4.dex */
public class SidebarPresenter implements EaseSidebar.OnTouchEventListener {
    private RecyclerView.Adapter mAdapter;
    private TextView mFloatingHeader;
    private RecyclerView mRecyclerView;

    private void hideFloatingHeader() {
        this.mFloatingHeader.setVisibility(8);
    }

    private void moveToRecyclerItem(String str) {
        List data;
        LinearLayoutManager linearLayoutManager;
        RecyclerView.Adapter adapter = this.mAdapter;
        if (adapter == null || !(adapter instanceof EaseBaseRecyclerViewAdapter) || (data = ((EaseBaseRecyclerViewAdapter) adapter).getData()) == null || data.isEmpty() || !(data.get(0) instanceof EaseUser)) {
            return;
        }
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (TextUtils.equals(((EaseUser) data.get(i2)).getInitialLetter(), str) && (linearLayoutManager = (LinearLayoutManager) this.mRecyclerView.getLayoutManager()) != null) {
                linearLayoutManager.scrollToPositionWithOffset(i2, 0);
            }
        }
    }

    private void showFloatingHeader(String str) {
        if (TextUtils.isEmpty(str)) {
            hideFloatingHeader();
        } else {
            this.mFloatingHeader.setText(str);
            this.mFloatingHeader.setVisibility(0);
        }
    }

    @Override // com.hyphenate.easeui.widget.EaseSidebar.OnTouchEventListener
    public void onActionDown(MotionEvent motionEvent, String str) {
        showFloatingHeader(str);
        moveToRecyclerItem(str);
    }

    @Override // com.hyphenate.easeui.widget.EaseSidebar.OnTouchEventListener
    public void onActionMove(MotionEvent motionEvent, String str) {
        showFloatingHeader(str);
        moveToRecyclerItem(str);
    }

    @Override // com.hyphenate.easeui.widget.EaseSidebar.OnTouchEventListener
    public void onActionUp(MotionEvent motionEvent) {
        hideFloatingHeader();
    }

    public void setupWithRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, TextView textView) {
        this.mRecyclerView = recyclerView;
        this.mAdapter = adapter;
        this.mFloatingHeader = textView;
    }
}
