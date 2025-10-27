package com.bigkoo.pickerview.builder;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.view.WheelView;

/* loaded from: classes2.dex */
public class OptionsPickerBuilder {
    private PickerOptions mPickerOptions;

    public OptionsPickerBuilder(Context context, OnOptionsSelectListener onOptionsSelectListener) {
        PickerOptions pickerOptions = new PickerOptions(1);
        this.mPickerOptions = pickerOptions;
        pickerOptions.context = context;
        pickerOptions.optionsSelectListener = onOptionsSelectListener;
    }

    public OptionsPickerBuilder addOnCancelClickListener(View.OnClickListener onClickListener) {
        this.mPickerOptions.cancelListener = onClickListener;
        return this;
    }

    public <T> OptionsPickerView<T> build() {
        return new OptionsPickerView<>(this.mPickerOptions);
    }

    public OptionsPickerBuilder isAlphaGradient(boolean z2) {
        this.mPickerOptions.isAlphaGradient = z2;
        return this;
    }

    public OptionsPickerBuilder isCenterLabel(boolean z2) {
        this.mPickerOptions.isCenterLabel = z2;
        return this;
    }

    public OptionsPickerBuilder isDialog(boolean z2) {
        this.mPickerOptions.isDialog = z2;
        return this;
    }

    public OptionsPickerBuilder isRestoreItem(boolean z2) {
        this.mPickerOptions.isRestoreItem = z2;
        return this;
    }

    @Deprecated
    public OptionsPickerBuilder setBackgroundId(int i2) {
        this.mPickerOptions.outSideColor = i2;
        return this;
    }

    public OptionsPickerBuilder setBgColor(int i2) {
        this.mPickerOptions.bgColorWheel = i2;
        return this;
    }

    public OptionsPickerBuilder setCancelColor(int i2) {
        this.mPickerOptions.textColorCancel = i2;
        return this;
    }

    public OptionsPickerBuilder setCancelText(String str) {
        this.mPickerOptions.textContentCancel = str;
        return this;
    }

    public OptionsPickerBuilder setContentTextSize(int i2) {
        this.mPickerOptions.textSizeContent = i2;
        return this;
    }

    public OptionsPickerBuilder setCyclic(boolean z2, boolean z3, boolean z4) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.cyclic1 = z2;
        pickerOptions.cyclic2 = z3;
        pickerOptions.cyclic3 = z4;
        return this;
    }

    public OptionsPickerBuilder setDecorView(ViewGroup viewGroup) {
        this.mPickerOptions.decorView = viewGroup;
        return this;
    }

    public OptionsPickerBuilder setDividerColor(@ColorInt int i2) {
        this.mPickerOptions.dividerColor = i2;
        return this;
    }

    public OptionsPickerBuilder setDividerType(WheelView.DividerType dividerType) {
        this.mPickerOptions.dividerType = dividerType;
        return this;
    }

    public OptionsPickerBuilder setItemVisibleCount(int i2) {
        this.mPickerOptions.itemsVisibleCount = i2;
        return this;
    }

    public OptionsPickerBuilder setLabels(String str, String str2, String str3) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.label1 = str;
        pickerOptions.label2 = str2;
        pickerOptions.label3 = str3;
        return this;
    }

    public OptionsPickerBuilder setLayoutRes(int i2, CustomListener customListener) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.layoutRes = i2;
        pickerOptions.customListener = customListener;
        return this;
    }

    public OptionsPickerBuilder setLineSpacingMultiplier(float f2) {
        this.mPickerOptions.lineSpacingMultiplier = f2;
        return this;
    }

    public OptionsPickerBuilder setOptionsSelectChangeListener(OnOptionsSelectChangeListener onOptionsSelectChangeListener) {
        this.mPickerOptions.optionsSelectChangeListener = onOptionsSelectChangeListener;
        return this;
    }

    public OptionsPickerBuilder setOutSideCancelable(boolean z2) {
        this.mPickerOptions.cancelable = z2;
        return this;
    }

    public OptionsPickerBuilder setOutSideColor(int i2) {
        this.mPickerOptions.outSideColor = i2;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int i2) {
        this.mPickerOptions.option1 = i2;
        return this;
    }

    public OptionsPickerBuilder setSubCalSize(int i2) {
        this.mPickerOptions.textSizeSubmitCancel = i2;
        return this;
    }

    public OptionsPickerBuilder setSubmitColor(int i2) {
        this.mPickerOptions.textColorConfirm = i2;
        return this;
    }

    public OptionsPickerBuilder setSubmitText(String str) {
        this.mPickerOptions.textContentConfirm = str;
        return this;
    }

    public OptionsPickerBuilder setTextColorCenter(int i2) {
        this.mPickerOptions.textColorCenter = i2;
        return this;
    }

    public OptionsPickerBuilder setTextColorOut(@ColorInt int i2) {
        this.mPickerOptions.textColorOut = i2;
        return this;
    }

    public OptionsPickerBuilder setTextXOffset(int i2, int i3, int i4) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.x_offset_one = i2;
        pickerOptions.x_offset_two = i3;
        pickerOptions.x_offset_three = i4;
        return this;
    }

    public OptionsPickerBuilder setTitleBgColor(int i2) {
        this.mPickerOptions.bgColorTitle = i2;
        return this;
    }

    public OptionsPickerBuilder setTitleColor(int i2) {
        this.mPickerOptions.textColorTitle = i2;
        return this;
    }

    public OptionsPickerBuilder setTitleSize(int i2) {
        this.mPickerOptions.textSizeTitle = i2;
        return this;
    }

    public OptionsPickerBuilder setTitleText(String str) {
        this.mPickerOptions.textContentTitle = str;
        return this;
    }

    public OptionsPickerBuilder setTypeface(Typeface typeface) {
        this.mPickerOptions.font = typeface;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int i2, int i3) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.option1 = i2;
        pickerOptions.option2 = i3;
        return this;
    }

    public OptionsPickerBuilder setSelectOptions(int i2, int i3, int i4) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.option1 = i2;
        pickerOptions.option2 = i3;
        pickerOptions.option3 = i4;
        return this;
    }
}
