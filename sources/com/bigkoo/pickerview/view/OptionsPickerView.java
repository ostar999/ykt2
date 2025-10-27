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
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import java.util.List;

/* loaded from: classes2.dex */
public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener {
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_SUBMIT = "submit";
    private WheelOptions<T> wheelOptions;

    public OptionsPickerView(PickerOptions pickerOptions) {
        super(pickerOptions.context);
        this.mPickerOptions = pickerOptions;
        initView(pickerOptions.context);
    }

    private void initView(Context context) {
        setDialogOutSideCancelable();
        initViews();
        initAnim();
        initEvents();
        CustomListener customListener = this.mPickerOptions.customListener;
        if (customListener == null) {
            LayoutInflater.from(context).inflate(this.mPickerOptions.layoutRes, this.contentContainer);
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
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.optionspicker);
        linearLayout.setBackgroundColor(this.mPickerOptions.bgColorWheel);
        WheelOptions<T> wheelOptions = new WheelOptions<>(linearLayout, this.mPickerOptions.isRestoreItem);
        this.wheelOptions = wheelOptions;
        OnOptionsSelectChangeListener onOptionsSelectChangeListener = this.mPickerOptions.optionsSelectChangeListener;
        if (onOptionsSelectChangeListener != null) {
            wheelOptions.setOptionsSelectChangeListener(onOptionsSelectChangeListener);
        }
        this.wheelOptions.setTextContentSize(this.mPickerOptions.textSizeContent);
        this.wheelOptions.setItemsVisible(this.mPickerOptions.itemsVisibleCount);
        this.wheelOptions.setAlphaGradient(this.mPickerOptions.isAlphaGradient);
        WheelOptions<T> wheelOptions2 = this.wheelOptions;
        PickerOptions pickerOptions = this.mPickerOptions;
        wheelOptions2.setLabels(pickerOptions.label1, pickerOptions.label2, pickerOptions.label3);
        WheelOptions<T> wheelOptions3 = this.wheelOptions;
        PickerOptions pickerOptions2 = this.mPickerOptions;
        wheelOptions3.setTextXOffset(pickerOptions2.x_offset_one, pickerOptions2.x_offset_two, pickerOptions2.x_offset_three);
        WheelOptions<T> wheelOptions4 = this.wheelOptions;
        PickerOptions pickerOptions3 = this.mPickerOptions;
        wheelOptions4.setCyclic(pickerOptions3.cyclic1, pickerOptions3.cyclic2, pickerOptions3.cyclic3);
        this.wheelOptions.setTypeface(this.mPickerOptions.font);
        setOutSideCancelable(this.mPickerOptions.cancelable);
        this.wheelOptions.setDividerColor(this.mPickerOptions.dividerColor);
        this.wheelOptions.setDividerType(this.mPickerOptions.dividerType);
        this.wheelOptions.setLineSpacingMultiplier(this.mPickerOptions.lineSpacingMultiplier);
        this.wheelOptions.setTextColorOut(this.mPickerOptions.textColorOut);
        this.wheelOptions.setTextColorCenter(this.mPickerOptions.textColorCenter);
        this.wheelOptions.isCenterLabel(this.mPickerOptions.isCenterLabel);
    }

    private void reSetCurrentItems() {
        WheelOptions<T> wheelOptions = this.wheelOptions;
        if (wheelOptions != null) {
            PickerOptions pickerOptions = this.mPickerOptions;
            wheelOptions.setCurrentItems(pickerOptions.option1, pickerOptions.option2, pickerOptions.option3);
        }
    }

    @Override // com.bigkoo.pickerview.view.BasePickerView
    public boolean isDialog() {
        return this.mPickerOptions.isDialog;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        View.OnClickListener onClickListener;
        String str = (String) view.getTag();
        if (str.equals(TAG_SUBMIT)) {
            returnData();
        } else if (str.equals("cancel") && (onClickListener = this.mPickerOptions.cancelListener) != null) {
            onClickListener.onClick(view);
        }
        dismiss();
    }

    public void returnData() {
        if (this.mPickerOptions.optionsSelectListener != null) {
            int[] currentItems = this.wheelOptions.getCurrentItems();
            this.mPickerOptions.optionsSelectListener.onOptionsSelect(currentItems[0], currentItems[1], currentItems[2], this.clickView);
        }
    }

    public void setNPicker(List<T> list, List<T> list2, List<T> list3) {
        this.wheelOptions.setLinkage(false);
        this.wheelOptions.setNPicker(list, list2, list3);
        reSetCurrentItems();
    }

    public void setPicker(List<T> list) {
        setPicker(list, null, null);
    }

    public void setSelectOptions(int i2) {
        this.mPickerOptions.option1 = i2;
        reSetCurrentItems();
    }

    public void setTitleText(String str) {
        TextView textView = (TextView) findViewById(R.id.tvTitle);
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setPicker(List<T> list, List<List<T>> list2) {
        setPicker(list, list2, null);
    }

    public void setPicker(List<T> list, List<List<T>> list2, List<List<List<T>>> list3) {
        this.wheelOptions.setPicker(list, list2, list3);
        reSetCurrentItems();
    }

    public void setSelectOptions(int i2, int i3) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.option1 = i2;
        pickerOptions.option2 = i3;
        reSetCurrentItems();
    }

    public void setSelectOptions(int i2, int i3, int i4) {
        PickerOptions pickerOptions = this.mPickerOptions;
        pickerOptions.option1 = i2;
        pickerOptions.option2 = i3;
        pickerOptions.option3 = i4;
        reSetCurrentItems();
    }
}
