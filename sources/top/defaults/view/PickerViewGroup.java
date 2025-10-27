package top.defaults.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/* loaded from: classes9.dex */
public class PickerViewGroup extends LinearLayout {
    protected boolean autoFitSize;
    protected boolean curved;
    protected int itemHeight;
    protected int preferredMaxOffsetItemCount;
    protected int textColor;
    protected int textSize;

    public PickerViewGroup(Context context) {
        this(context, null);
    }

    public void addPickerView(PickerView pickerView, boolean z2) {
        addView(pickerView, new LinearLayout.LayoutParams(0, -2, z2 ? 1.0f : 2.0f));
    }

    public void bindParams(PickerView pickerView) {
        pickerView.setPreferredMaxOffsetItemCount(this.preferredMaxOffsetItemCount);
        pickerView.setItemHeight(this.itemHeight);
        pickerView.setTextSize(this.textSize);
        pickerView.setAutoFitSize(this.autoFitSize);
        pickerView.setCurved(this.curved);
    }

    public void setCurved(boolean z2) {
        this.curved = z2;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ((PickerView) getChildAt(i2)).setCurved(z2);
        }
    }

    @Override // android.widget.LinearLayout
    public final void setOrientation(int i2) {
        if (i2 != 0) {
            throw new RuntimeException("DatePickerView's orientation must be HORIZONTAL");
        }
        super.setOrientation(i2);
    }

    public void settlePickerView(PickerView pickerView) {
        settlePickerView(pickerView, false);
    }

    public PickerViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void settlePickerView(PickerView pickerView, boolean z2) {
        if (pickerView == null) {
            return;
        }
        bindParams(pickerView);
        addPickerView(pickerView, z2);
    }

    public PickerViewGroup(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.preferredMaxOffsetItemCount = 3;
        setOrientation(0);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PickerViewGroup);
        this.preferredMaxOffsetItemCount = typedArrayObtainStyledAttributes.getInt(R.styleable.PickerViewGroup_preferredMaxOffsetItemCount, 3);
        this.itemHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PickerViewGroup_itemHeight, Utils.pixelOfDp(getContext(), 56));
        this.textSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PickerViewGroup_textSize, Utils.pixelOfScaled(getContext(), 16));
        this.textColor = typedArrayObtainStyledAttributes.getColor(R.styleable.PickerViewGroup_textColor, -16777216);
        this.autoFitSize = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PickerViewGroup_autoFitSize, true);
        this.curved = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PickerViewGroup_curved, false);
        typedArrayObtainStyledAttributes.recycle();
    }
}
