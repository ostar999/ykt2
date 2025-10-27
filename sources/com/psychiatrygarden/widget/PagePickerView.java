package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.psychiatrygarden.widget.LoopView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class PagePickerView extends LinearLayout implements LoopView.LoopListener {
    private int currentIndex;
    private int currentIndex2;
    private boolean isTwo;
    public LoopView labelView;
    public LoopView labelView2;

    public PagePickerView(Context context) {
        super(context);
        this.isTwo = false;
        initUi();
    }

    private void initUi() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.view_label_picker, this);
        setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
        this.labelView = (LoopView) findViewById(R.id.view_label_picker_labels);
        LoopView loopView = (LoopView) findViewById(R.id.view_label_picker_labels2);
        this.labelView2 = loopView;
        if (this.isTwo) {
            loopView.setVisibility(0);
        }
        this.labelView2.setListener(this);
        this.labelView.setListener(this);
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public int getCurrentIndex2() {
        return this.currentIndex2;
    }

    @Override // com.psychiatrygarden.widget.LoopView.LoopListener
    public void onItemSelect(LoopView loopView, int item) {
        switch (loopView.getId()) {
            case R.id.view_label_picker_labels /* 2131369068 */:
                this.currentIndex = item;
                break;
            case R.id.view_label_picker_labels2 /* 2131369069 */:
                this.currentIndex2 = item;
                break;
        }
    }

    public void setData(ArrayList<String> pages) {
        this.labelView.setArrayList(pages);
    }

    public void setData2(ArrayList<String> pages) {
        this.labelView2.setArrayList(pages);
    }

    public PagePickerView(Context context, boolean isTwo) {
        super(context);
        this.isTwo = isTwo;
        initUi();
    }

    public PagePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isTwo = false;
        initUi();
    }
}
