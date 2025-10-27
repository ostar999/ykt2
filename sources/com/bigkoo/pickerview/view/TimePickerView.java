package com.bigkoo.pickerview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.R;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.ISelectTimeCallback;
import java.text.ParseException;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class TimePickerView extends BasePickerView implements View.OnClickListener {
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_SUBMIT = "submit";
    private WheelTime wheelTime;

    public TimePickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        this.mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initDefaultSelectedDate() {
        PickerOptions pickerOptions = this.mPickerOptions;
        Calendar calendar = pickerOptions.startDate;
        if (calendar == null || pickerOptions.endDate == null) {
            if (calendar != null) {
                pickerOptions.date = calendar;
                return;
            }
            Calendar calendar2 = pickerOptions.endDate;
            if (calendar2 != null) {
                pickerOptions.date = calendar2;
                return;
            }
            return;
        }
        Calendar calendar3 = pickerOptions.date;
        if (calendar3 == null || calendar3.getTimeInMillis() < this.mPickerOptions.startDate.getTimeInMillis() || this.mPickerOptions.date.getTimeInMillis() > this.mPickerOptions.endDate.getTimeInMillis()) {
            PickerOptions pickerOptions2 = this.mPickerOptions;
            pickerOptions2.date = pickerOptions2.startDate;
        }
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        CustomListener customListener = this.mPickerOptions.customListener;
        if (customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_time, this.contentContainer);
            TextView textView = (TextView) findViewById(R.id.tvTitle);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rv_topbar);
            Button button = (Button) findViewById(R.id.btnSubmit);
            Button button2 = (Button) findViewById(R.id.btnCancel);
            button.setTag(TAG_SUBMIT);
            button2.setTag("cancel");
            button.setOnClickListener(this);
            button2.setOnClickListener(this);
            button.setText(TextUtils.isEmpty(this.mPickerOptions.textContentConfirm) ? context.getResources().getString(R.string.pickerview_submit) : this.mPickerOptions.textContentConfirm);
            button2.setText(TextUtils.isEmpty(this.mPickerOptions.textContentCancel) ? context.getResources().getString(R.string.pickerview_cancel) : this.mPickerOptions.textContentCancel);
            textView.setText(TextUtils.isEmpty(this.mPickerOptions.textContentTitle) ? "" : this.mPickerOptions.textContentTitle);
            button.setTextColor(this.mPickerOptions.textColorConfirm);
            button2.setTextColor(this.mPickerOptions.textColorCancel);
            textView.setTextColor(this.mPickerOptions.textColorTitle);
            relativeLayout.setBackgroundColor(this.mPickerOptions.bgColorTitle);
            button.setTextSize(this.mPickerOptions.textSizeSubmitCancel);
            button2.setTextSize(this.mPickerOptions.textSizeSubmitCancel);
            textView.setTextSize(this.mPickerOptions.textSizeTitle);
        } else {
            customListener.customLayout(LayoutInflater.from(context).inflate(this.mPickerOptions.layoutRes, this.contentContainer));
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.timepicker);
        linearLayout.setBackgroundColor(this.mPickerOptions.bgColorWheel);
        initWheelTime(linearLayout);
    }

    private void initWheelTime(LinearLayout linearLayout) {
        int i2;
        PickerOptions pickerOptions = this.mPickerOptions;
        WheelTime wheelTime = new WheelTime(linearLayout, pickerOptions.type, pickerOptions.textGravity, pickerOptions.textSizeContent);
        this.wheelTime = wheelTime;
        if (this.mPickerOptions.timeSelectChangeListener != null) {
            wheelTime.setSelectChangeCallback(new ISelectTimeCallback() { // from class: com.bigkoo.pickerview.view.TimePickerView.1
                @Override // com.bigkoo.pickerview.listener.ISelectTimeCallback
                public void onTimeSelectChanged() throws ParseException {
                    try {
                        TimePickerView.this.mPickerOptions.timeSelectChangeListener.onTimeSelectChanged(WheelTime.dateFormat.parse(TimePickerView.this.wheelTime.getTime()));
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
        this.wheelTime.setLunarMode(this.mPickerOptions.isLunarCalendar);
        PickerOptions pickerOptions2 = this.mPickerOptions;
        int i3 = pickerOptions2.startYear;
        if (i3 != 0 && (i2 = pickerOptions2.endYear) != 0 && i3 <= i2) {
            setRange();
        }
        PickerOptions pickerOptions3 = this.mPickerOptions;
        Calendar calendar = pickerOptions3.startDate;
        if (calendar == null || pickerOptions3.endDate == null) {
            if (calendar == null) {
                Calendar calendar2 = pickerOptions3.endDate;
                if (calendar2 != null && calendar2.get(1) > 2100) {
                    throw new IllegalArgumentException("The endDate should not be later than 2100");
                }
                setRangDate();
            } else {
                if (calendar.get(1) < 1900) {
                    throw new IllegalArgumentException("The startDate can not as early as 1900");
                }
                setRangDate();
            }
        } else {
            if (calendar.getTimeInMillis() > this.mPickerOptions.endDate.getTimeInMillis()) {
                throw new IllegalArgumentException("startDate can't be later than endDate");
            }
            setRangDate();
        }
        setTime();
        WheelTime wheelTime2 = this.wheelTime;
        PickerOptions pickerOptions4 = this.mPickerOptions;
        wheelTime2.setLabels(pickerOptions4.label_year, pickerOptions4.label_month, pickerOptions4.label_day, pickerOptions4.label_hours, pickerOptions4.label_minutes, pickerOptions4.label_seconds);
        WheelTime wheelTime3 = this.wheelTime;
        PickerOptions pickerOptions5 = this.mPickerOptions;
        wheelTime3.setTextXOffset(pickerOptions5.x_offset_year, pickerOptions5.x_offset_month, pickerOptions5.x_offset_day, pickerOptions5.x_offset_hours, pickerOptions5.x_offset_minutes, pickerOptions5.x_offset_seconds);
        this.wheelTime.setItemsVisible(this.mPickerOptions.itemsVisibleCount);
        this.wheelTime.setAlphaGradient(this.mPickerOptions.isAlphaGradient);
        setOutSideCancelable(this.mPickerOptions.cancelable);
        this.wheelTime.setCyclic(this.mPickerOptions.cyclic);
        this.wheelTime.setDividerColor(this.mPickerOptions.dividerColor);
        this.wheelTime.setDividerType(this.mPickerOptions.dividerType);
        this.wheelTime.setLineSpacingMultiplier(this.mPickerOptions.lineSpacingMultiplier);
        this.wheelTime.setTextColorOut(this.mPickerOptions.textColorOut);
        this.wheelTime.setTextColorCenter(this.mPickerOptions.textColorCenter);
        this.wheelTime.isCenterLabel(this.mPickerOptions.isCenterLabel);
    }

    private void setRangDate() {
        WheelTime wheelTime = this.wheelTime;
        PickerOptions pickerOptions = this.mPickerOptions;
        wheelTime.setRangDate(pickerOptions.startDate, pickerOptions.endDate);
        initDefaultSelectedDate();
    }

    private void setRange() {
        this.wheelTime.setStartYear(this.mPickerOptions.startYear);
        this.wheelTime.setEndYear(this.mPickerOptions.endYear);
    }

    private void setTime() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = this.mPickerOptions.date;
        if (calendar2 == null) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            i2 = calendar.get(1);
            i3 = calendar.get(2);
            i4 = calendar.get(5);
            i5 = calendar.get(11);
            i6 = calendar.get(12);
            i7 = calendar.get(13);
        } else {
            i2 = calendar2.get(1);
            i3 = this.mPickerOptions.date.get(2);
            i4 = this.mPickerOptions.date.get(5);
            i5 = this.mPickerOptions.date.get(11);
            i6 = this.mPickerOptions.date.get(12);
            i7 = this.mPickerOptions.date.get(13);
        }
        int i8 = i5;
        int i9 = i4;
        int i10 = i3;
        WheelTime wheelTime = this.wheelTime;
        wheelTime.setPicker(i2, i10, i9, i8, i6, i7);
    }

    @Override // com.bigkoo.pickerview.view.BasePickerView
    public boolean isDialog() {
        return this.mPickerOptions.isDialog;
    }

    public boolean isLunarCalendar() {
        return this.wheelTime.isLunarMode();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) throws ParseException {
        View.OnClickListener onClickListener;
        String str = (String) view.getTag();
        if (str.equals(TAG_SUBMIT)) {
            returnData();
        } else if (str.equals("cancel") && (onClickListener = this.mPickerOptions.cancelListener) != null) {
            onClickListener.onClick(view);
        }
        dismiss();
    }

    public void returnData() throws ParseException {
        if (this.mPickerOptions.timeSelectListener != null) {
            try {
                this.mPickerOptions.timeSelectListener.onTimeSelect(WheelTime.dateFormat.parse(this.wheelTime.getTime()), this.clickView);
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setDate(Calendar calendar) {
        this.mPickerOptions.date = calendar;
        setTime();
    }

    public void setLunarCalendar(boolean z2) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(WheelTime.dateFormat.parse(this.wheelTime.getTime()));
            int i2 = calendar.get(1);
            int i3 = calendar.get(2);
            int i4 = calendar.get(5);
            int i5 = calendar.get(11);
            int i6 = calendar.get(12);
            int i7 = calendar.get(13);
            this.wheelTime.setLunarMode(z2);
            WheelTime wheelTime = this.wheelTime;
            PickerOptions pickerOptions = this.mPickerOptions;
            wheelTime.setLabels(pickerOptions.label_year, pickerOptions.label_month, pickerOptions.label_day, pickerOptions.label_hours, pickerOptions.label_minutes, pickerOptions.label_seconds);
            this.wheelTime.setPicker(i2, i3, i4, i5, i6, i7);
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    public void setTitleText(String str) {
        TextView textView = (TextView) findViewById(R.id.tvTitle);
        if (textView != null) {
            textView.setText(str);
        }
    }
}
