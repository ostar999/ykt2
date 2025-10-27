package com.google.android.material.timepicker;

import android.view.accessibility.AccessibilityManager;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;
import com.google.android.material.timepicker.ClockHandView;
import com.google.android.material.timepicker.TimePickerView;
import com.tencent.connect.common.Constants;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes3.dex */
class TimePickerClockPresenter implements ClockHandView.OnRotateListener, TimePickerView.OnSelectionChange, TimePickerView.OnPeriodChangeListener, ClockHandView.OnActionUpListener, TimePickerPresenter {
    private static final int DEGREES_PER_HOUR = 30;
    private static final int DEGREES_PER_MINUTE = 6;
    private boolean broadcasting = false;
    private float hourRotation;
    private float minuteRotation;
    private TimeModel time;
    private TimePickerView timePickerView;
    private static final String[] HOUR_CLOCK_VALUES = {Constants.VIA_REPORT_TYPE_SET_AVATAR, "1", "2", "3", "4", "5", Constants.VIA_SHARE_TYPE_INFO, "7", Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, Constants.VIA_SHARE_TYPE_MINI_PROGRAM, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE};
    private static final String[] HOUR_CLOCK_24_VALUES = {TarConstants.VERSION_POSIX, "2", "4", Constants.VIA_SHARE_TYPE_INFO, Constants.VIA_SHARE_TYPE_PUBLISHVIDEO, Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, Constants.VIA_REPORT_TYPE_SET_AVATAR, Constants.VIA_REPORT_TYPE_MAKE_FRIEND, "16", "18", "20", Constants.VIA_REPORT_TYPE_DATALINE};
    private static final String[] MINUTE_CLOCK_VALUES = {TarConstants.VERSION_POSIX, "5", Constants.VIA_REPORT_TYPE_SHARE_TO_QQ, Constants.VIA_REPORT_TYPE_WPA_STATE, "20", Constants.VIA_REPORT_TYPE_CHAT_AUDIO, "30", "35", "40", "45", "50", "55"};

    public TimePickerClockPresenter(TimePickerView timePickerView, TimeModel timeModel) {
        this.timePickerView = timePickerView;
        this.time = timeModel;
        initialize();
    }

    private int getDegreesPerHour() {
        return this.time.format == 1 ? 15 : 30;
    }

    private String[] getHourClockValues() {
        return this.time.format == 1 ? HOUR_CLOCK_24_VALUES : HOUR_CLOCK_VALUES;
    }

    private void performHapticFeedback(int i2, int i3) {
        TimeModel timeModel = this.time;
        if (timeModel.minute == i3 && timeModel.hour == i2) {
            return;
        }
        this.timePickerView.performHapticFeedback(4);
    }

    private void updateTime() {
        TimePickerView timePickerView = this.timePickerView;
        TimeModel timeModel = this.time;
        timePickerView.updateTime(timeModel.period, timeModel.getHourForDisplay(), this.time.minute);
    }

    private void updateValues() {
        updateValues(HOUR_CLOCK_VALUES, TimeModel.NUMBER_FORMAT);
        updateValues(HOUR_CLOCK_24_VALUES, TimeModel.NUMBER_FORMAT);
        updateValues(MINUTE_CLOCK_VALUES, TimeModel.ZERO_LEADING_NUMBER_FORMAT);
    }

    @Override // com.google.android.material.timepicker.TimePickerPresenter
    public void hide() {
        this.timePickerView.setVisibility(8);
    }

    @Override // com.google.android.material.timepicker.TimePickerPresenter
    public void initialize() {
        if (this.time.format == 0) {
            this.timePickerView.showToggle();
        }
        this.timePickerView.addOnRotateListener(this);
        this.timePickerView.setOnSelectionChangeListener(this);
        this.timePickerView.setOnPeriodChangeListener(this);
        this.timePickerView.setOnActionUpListener(this);
        updateValues();
        invalidate();
    }

    @Override // com.google.android.material.timepicker.TimePickerPresenter
    public void invalidate() {
        this.hourRotation = this.time.getHourForDisplay() * getDegreesPerHour();
        TimeModel timeModel = this.time;
        this.minuteRotation = timeModel.minute * 6;
        setSelection(timeModel.selection, false);
        updateTime();
    }

    @Override // com.google.android.material.timepicker.ClockHandView.OnActionUpListener
    public void onActionUp(float f2, boolean z2) {
        this.broadcasting = true;
        TimeModel timeModel = this.time;
        int i2 = timeModel.minute;
        int i3 = timeModel.hour;
        if (timeModel.selection == 10) {
            this.timePickerView.setHandRotation(this.hourRotation, false);
            if (!((AccessibilityManager) ContextCompat.getSystemService(this.timePickerView.getContext(), AccessibilityManager.class)).isTouchExplorationEnabled()) {
                setSelection(12, true);
            }
        } else {
            int iRound = Math.round(f2);
            if (!z2) {
                this.time.setMinute(((iRound + 15) / 30) * 5);
                this.minuteRotation = this.time.minute * 6;
            }
            this.timePickerView.setHandRotation(this.minuteRotation, z2);
        }
        this.broadcasting = false;
        updateTime();
        performHapticFeedback(i3, i2);
    }

    @Override // com.google.android.material.timepicker.TimePickerView.OnPeriodChangeListener
    public void onPeriodChange(int i2) {
        this.time.setPeriod(i2);
    }

    @Override // com.google.android.material.timepicker.ClockHandView.OnRotateListener
    public void onRotate(float f2, boolean z2) {
        if (this.broadcasting) {
            return;
        }
        TimeModel timeModel = this.time;
        int i2 = timeModel.hour;
        int i3 = timeModel.minute;
        int iRound = Math.round(f2);
        TimeModel timeModel2 = this.time;
        if (timeModel2.selection == 12) {
            timeModel2.setMinute((iRound + 3) / 6);
            this.minuteRotation = (float) Math.floor(this.time.minute * 6);
        } else {
            this.time.setHour((iRound + (getDegreesPerHour() / 2)) / getDegreesPerHour());
            this.hourRotation = this.time.getHourForDisplay() * getDegreesPerHour();
        }
        if (z2) {
            return;
        }
        updateTime();
        performHapticFeedback(i2, i3);
    }

    @Override // com.google.android.material.timepicker.TimePickerView.OnSelectionChange
    public void onSelectionChanged(int i2) {
        setSelection(i2, true);
    }

    public void setSelection(int i2, boolean z2) {
        boolean z3 = i2 == 12;
        this.timePickerView.setAnimateOnTouchUp(z3);
        this.time.selection = i2;
        this.timePickerView.setValues(z3 ? MINUTE_CLOCK_VALUES : getHourClockValues(), z3 ? R.string.material_minute_suffix : R.string.material_hour_suffix);
        this.timePickerView.setHandRotation(z3 ? this.minuteRotation : this.hourRotation, z2);
        this.timePickerView.setActiveSelection(i2);
        this.timePickerView.setMinuteHourDelegate(new ClickActionDelegate(this.timePickerView.getContext(), R.string.material_hour_selection));
        this.timePickerView.setHourClickDelegate(new ClickActionDelegate(this.timePickerView.getContext(), R.string.material_minute_selection));
    }

    @Override // com.google.android.material.timepicker.TimePickerPresenter
    public void show() {
        this.timePickerView.setVisibility(0);
    }

    private void updateValues(String[] strArr, String str) {
        for (int i2 = 0; i2 < strArr.length; i2++) {
            strArr[i2] = TimeModel.formatText(this.timePickerView.getResources(), strArr[i2], str);
        }
    }
}
