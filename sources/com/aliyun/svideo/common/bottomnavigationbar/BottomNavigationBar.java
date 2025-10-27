package com.aliyun.svideo.common.bottomnavigationbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.aliyun.svideo.common.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class BottomNavigationBar extends LinearLayout implements View.OnClickListener {
    private static final String DEFAULT_SELECTED_COLOR = "#000000";
    private static final String DEFAULT_UNSELECTED_COLOR = "#999999";
    private IBnbItemDoubleClickListener bnbItemDoubleClickListener;
    private IBnbItemSelectListener bnbItemSelectListener;
    private List<BottomNavigationEntity> entities;
    private boolean isAnim;
    private int mCurrentPosition;
    private int mItemLayout;
    private int mTextSelectedColor;
    private int mTextUnSelectedColor;
    private float scaleRatio;

    public interface IBnbItemDoubleClickListener {
        void onBnbItemDoubleClick(int i2);
    }

    public interface IBnbItemSelectListener {
        void onBnbItemSelect(int i2);
    }

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    private void addItems() {
        if (this.entities.isEmpty()) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = 1.0f;
        for (int i2 = 0; i2 < this.entities.size(); i2++) {
            BottomNavigationEntity bottomNavigationEntity = this.entities.get(i2);
            BottomNavigationItemView bottomNavigationItemView = new BottomNavigationItemView(getContext());
            bottomNavigationItemView.setLayoutId(this.mItemLayout);
            bottomNavigationItemView.setAnim(this.isAnim);
            bottomNavigationItemView.setScaleRatio(this.scaleRatio);
            bottomNavigationItemView.setBottomNavigationEntity(bottomNavigationEntity);
            bottomNavigationItemView.setTextSelectedColor(this.mTextSelectedColor);
            bottomNavigationItemView.setTextUnSelectedColor(this.mTextUnSelectedColor);
            bottomNavigationItemView.setTag(Integer.valueOf(i2));
            addView(bottomNavigationItemView, layoutParams);
            bottomNavigationItemView.setOnClickListener(this);
            bottomNavigationItemView.setDefaultState();
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomNavigationBar);
        this.mTextSelectedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.BottomNavigationBar_bnb_selectedColor, Color.parseColor(DEFAULT_SELECTED_COLOR));
        this.mTextUnSelectedColor = typedArrayObtainStyledAttributes.getColor(R.styleable.BottomNavigationBar_bnb_unSelectedColor, Color.parseColor(DEFAULT_UNSELECTED_COLOR));
        this.isAnim = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomNavigationBar_bnb_anim, false);
        this.scaleRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.BottomNavigationBar_bnb_scale_ratio, 1.1f);
        this.mItemLayout = typedArrayObtainStyledAttributes.getResourceId(R.styleable.BottomNavigationBar_bnb_layoutId, -1);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        IBnbItemDoubleClickListener iBnbItemDoubleClickListener;
        int iIntValue = ((Integer) view.getTag()).intValue();
        int i2 = this.mCurrentPosition;
        if (iIntValue == i2 && (iBnbItemDoubleClickListener = this.bnbItemDoubleClickListener) != null) {
            iBnbItemDoubleClickListener.onBnbItemDoubleClick(iIntValue);
        } else if (iIntValue != i2) {
            setCurrentPosition(iIntValue);
        }
    }

    public void refreshItem(int i2) {
        if (i2 >= 0 && i2 < getChildCount()) {
            ((BottomNavigationItemView) getChildAt(i2)).refresh();
        }
    }

    public void setAnim(boolean z2) {
        this.isAnim = z2;
    }

    public void setBnbItemDoubleClickListener(IBnbItemDoubleClickListener iBnbItemDoubleClickListener) {
        this.bnbItemDoubleClickListener = iBnbItemDoubleClickListener;
    }

    public void setBnbItemSelectListener(IBnbItemSelectListener iBnbItemSelectListener) {
        this.bnbItemSelectListener = iBnbItemSelectListener;
    }

    public void setCurrentPosition(int i2) {
        int i3;
        int childCount = getChildCount();
        if (childCount == 0 || i2 > childCount || i2 == (i3 = this.mCurrentPosition)) {
            return;
        }
        BottomNavigationItemView bottomNavigationItemView = (BottomNavigationItemView) getChildAt(i3);
        BottomNavigationItemView bottomNavigationItemView2 = (BottomNavigationItemView) getChildAt(i2);
        if (bottomNavigationItemView != null) {
            bottomNavigationItemView.setSelected(false);
        }
        if (bottomNavigationItemView2 != null) {
            bottomNavigationItemView2.setSelected(true);
        }
        this.mCurrentPosition = i2;
        IBnbItemSelectListener iBnbItemSelectListener = this.bnbItemSelectListener;
        if (iBnbItemSelectListener != null) {
            iBnbItemSelectListener.onBnbItemSelect(i2);
        }
    }

    public void setEntities(List<BottomNavigationEntity> list) {
        this.entities.clear();
        this.entities.addAll(list);
        addItems();
    }

    public BottomNavigationBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomNavigationBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, 0);
        this.entities = new ArrayList();
        this.mCurrentPosition = -1;
        init(context, attributeSet);
    }
}
