package com.daimajia.swipe.implments;

import android.view.View;
import android.widget.BaseAdapter;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.implments.SwipeItemMangerImpl;

/* loaded from: classes3.dex */
public class SwipeItemAdapterMangerImpl extends SwipeItemMangerImpl {
    protected BaseAdapter mAdapter;

    public SwipeItemAdapterMangerImpl(BaseAdapter baseAdapter) {
        super(baseAdapter);
        this.mAdapter = baseAdapter;
    }

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void bindView(View view, int i2) {
    }

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void initialize(View view, int i2) {
        int swipeLayoutId = getSwipeLayoutId(i2);
        SwipeItemMangerImpl.OnLayoutListener onLayoutListener = new SwipeItemMangerImpl.OnLayoutListener(i2);
        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(swipeLayoutId);
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        SwipeItemMangerImpl.SwipeMemory swipeMemory = new SwipeItemMangerImpl.SwipeMemory(i2);
        swipeLayout.addSwipeListener(swipeMemory);
        swipeLayout.addOnLayoutListener(onLayoutListener);
        swipeLayout.setTag(swipeLayoutId, new SwipeItemMangerImpl.ValueBox(i2, swipeMemory, onLayoutListener));
        this.mShownLayouts.add(swipeLayout);
    }

    @Override // com.daimajia.swipe.implments.SwipeItemMangerImpl
    public void updateConvertView(View view, int i2) {
        int swipeLayoutId = getSwipeLayoutId(i2);
        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(swipeLayoutId);
        if (swipeLayout == null) {
            throw new IllegalStateException("can not find SwipeLayout in target view");
        }
        SwipeItemMangerImpl.ValueBox valueBox = (SwipeItemMangerImpl.ValueBox) swipeLayout.getTag(swipeLayoutId);
        valueBox.swipeMemory.setPosition(i2);
        valueBox.onLayoutListener.setPosition(i2);
        valueBox.position = i2;
    }
}
