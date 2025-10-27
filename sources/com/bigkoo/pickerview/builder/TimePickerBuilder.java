package com.bigkoo.pickerview.builder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class TimePickerBuilder {
    private PickerOptions mPickerOptions;

    public TimePickerBuilder(Context context, OnTimeSelectListener onTimeSelectListener) {
        PickerOptions pickerOptions = new PickerOptions(2);
        this.mPickerOptions = pickerOptions;
        pickerOptions.context = context;
        pickerOptions.timeSelectListener = onTimeSelectListener;
    }

    public TimePickerBuilder addOnCancelClickListener(View.OnClickListener onClickListener) {
        this.mPickerOptions.cancelListener = onClickListener;
        return this;
    }

    public TimePickerView build() {
        return new TimePickerView(this.mPickerOptions);
    }

    public TimePickerBuilder isAlphaGradient(boolean z2) {
        this.mPickerOptions.isAlphaGradient = z2;
        return this;
    }

    public TimePickerBuilder isCenterLabel(boolean z2) {
        this.mPickerOptions.isCenterLabel = z2;
        return this;
    }

    public TimePickerBuilder isCyclic(boolean z2) {
        this.mPickerOptions.cyclic = z2;
        return this;
    }

    public TimePickerBuilder isDialog(boolean z2) {
        this.mPickerOptions.isDialog = z2;
        return this;
    }

    @Deprecated
    public TimePickerBuilder setBackgroundId(int i2) {
        this.mPickerOptions.outSideColor = i2;
        return this;
    }

    public TimePickerBuilder setBgColor(int i2) {
        this.mPickerOptions.bgColorWheel = i2;
        return this;
    }

    public TimePickerBuilder setCancelColor(int i2) {
        this.mPickerOptions.textColorCancel = i2;
        return this;
    }

    public TimePickerBuilder setCancelText(String str) {
        this.mPickerOptions.textContentCancel = str;
        return this;
    }

    public TimePickerBuilder setContentTextSize(int i2) {
        this.mPickerOptions.textSizeContent = i2;
        return this;
    }

    public TimePickerBuilder setDate(Calendar calendar) {
        this.mPickerOptions.date = calendar;
        return this;
    }

    public TimePickerBuilder setDecorView(ViewGroup viewGroup) {
        this.mPickerOptions.decorView = viewGroup;
        return this;
    }

    public TimePickerBuilder setDividerColor(@ColorInt int i2) {
        this.mPickerOptions.dividerColor = i2;
        return this;
    }

    public TimePickerBuilder setDividerType(WheelView.DividerType dividerType) {
        this.mPickerOptions.dividerType = dividerType;
        return this;
    }

    public TimePickerBuilder setGravity(int i2) {
        this.mPickerOptions.textGravity = i2;
        return this;
    }

    public TimePickerBuilder setItemVisibleCount(int i2) {
        this.mPickerOptions.itemsVisibleCount = i2;
        return this;
    }

    public TimePickerBuilder setLabel(String str, String str2, String str3, String str4, String str5, String str6) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.label_year = str;
        pickerOptions.label_month = str2;
        pickerOptions.label_day = str3;
        pickerOptions.label_hours = str4;
        pickerOptions.label_minutes = str5;
        pickerOptions.label_seconds = str6;
        return this;
    }

    public TimePickerBuilder setLayoutRes(int i2, CustomListener customListener) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.layoutRes = i2;
        pickerOptions.customListener = customListener;
        return this;
    }

    public TimePickerBuilder setLineSpacingMultiplier(float f2) {
        this.mPickerOptions.lineSpacingMultiplier = f2;
        return this;
    }

    public TimePickerBuilder setLunarCalendar(boolean z2) {
        this.mPickerOptions.isLunarCalendar = z2;
        return this;
    }

    public TimePickerBuilder setOutSideCancelable(boolean z2) {
        this.mPickerOptions.cancelable = z2;
        return this;
    }

    public TimePickerBuilder setOutSideColor(@ColorInt int i2) {
        this.mPickerOptions.outSideColor = i2;
        return this;
    }

    public TimePickerBuilder setRangDate(Calendar calendar, Calendar calendar2) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.startDate = calendar;
        pickerOptions.endDate = calendar2;
        return this;
    }

    public TimePickerBuilder setSubCalSize(int i2) {
        this.mPickerOptions.textSizeSubmitCancel = i2;
        return this;
    }

    public TimePickerBuilder setSubmitColor(int i2) {
        this.mPickerOptions.textColorConfirm = i2;
        return this;
    }

    public TimePickerBuilder setSubmitText(String str) {
        this.mPickerOptions.textContentConfirm = str;
        return this;
    }

    public TimePickerBuilder setTextColorCenter(@ColorInt int i2) {
        this.mPickerOptions.textColorCenter = i2;
        return this;
    }

    public TimePickerBuilder setTextColorOut(@ColorInt int i2) {
        this.mPickerOptions.textColorOut = i2;
        return this;
    }

    public TimePickerBuilder setTextXOffset(int i2, int i3, int i4, int i5, int i6, int i7) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.x_offset_year = i2;
        pickerOptions.x_offset_month = i3;
        pickerOptions.x_offset_day = i4;
        pickerOptions.x_offset_hours = i5;
        pickerOptions.x_offset_minutes = i6;
        pickerOptions.x_offset_seconds = i7;
        return this;
    }

    public TimePickerBuilder setTimeSelectChangeListener(OnTimeSelectChangeListener onTimeSelectChangeListener) {
        this.mPickerOptions.timeSelectChangeListener = onTimeSelectChangeListener;
        return this;
    }

    public TimePickerBuilder setTitleBgColor(int i2) {
        this.mPickerOptions.bgColorTitle = i2;
        return this;
    }

    public TimePickerBuilder setTitleColor(int i2) {
        this.mPickerOptions.textColorTitle = i2;
        return this;
    }

    public TimePickerBuilder setTitleSize(int i2) {
        this.mPickerOptions.textSizeTitle = i2;
        return this;
    }

    public TimePickerBuilder setTitleText(String str) {
        this.mPickerOptions.textContentTitle = str;
        return this;
    }

    public TimePickerBuilder setType(boolean[] zArr) {
        this.mPickerOptions.type = zArr;
        return this;
    }
}
