package top.defaults.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import java.util.List;
import top.defaults.view.PickerView;

/* loaded from: classes9.dex */
public class DivisionPickerView extends PickerViewGroup {
    public static final int TYPE_ALL = 0;
    public static final int TYPE_PROVINCE_AND_CITY = 1;
    private final DivisionAdapter cityAdapter;
    private PickerView cityPicker;
    private final DivisionAdapter divisionAdapter;
    private PickerView divisionPicker;
    private OnSelectedDivisionChangedListener onSelectedDivisionChangedListener;
    private PickerView provincePicker;
    private final DivisionAdapter provisionAdapter;
    private int type;

    public interface OnSelectedDivisionChangedListener {
        void onSelectedDivisionChanged(Division division);
    }

    public DivisionPickerView(Context context) {
        this(context, null);
    }

    private void configure() {
        if (this.type == 1) {
            this.divisionPicker.setVisibility(8);
        } else {
            this.divisionPicker.setVisibility(0);
        }
    }

    public PickerView getCityPicker() {
        return this.cityPicker;
    }

    public PickerView getDivisionPicker() {
        return this.divisionPicker;
    }

    public PickerView getProvincePicker() {
        return this.provincePicker;
    }

    public Division getSelectedDivision() {
        Division division = this.type == 0 ? (Division) this.divisionPicker.getSelectedItem(Division.class) : null;
        if (division == null) {
            division = (Division) this.cityPicker.getSelectedItem(Division.class);
        }
        return division == null ? (Division) this.provincePicker.getSelectedItem(Division.class) : division;
    }

    public void setDivisions(List<? extends Division> list) {
        this.provisionAdapter.setDivisions(list);
        this.provincePicker.setAdapter(this.provisionAdapter);
        this.cityAdapter.setDivisions(this.provisionAdapter.getItem(this.provincePicker.getSelectedItemPosition()).getChildren());
        this.cityPicker.setAdapter(this.cityAdapter);
        this.divisionAdapter.setDivisions(this.cityAdapter.getItem(this.cityPicker.getSelectedItemPosition()).getChildren());
        this.divisionPicker.setAdapter(this.divisionAdapter);
        PickerView.OnSelectedItemChangedListener onSelectedItemChangedListener = new PickerView.OnSelectedItemChangedListener() { // from class: top.defaults.view.DivisionPickerView.1
            @Override // top.defaults.view.PickerView.OnSelectedItemChangedListener
            public void onSelectedItemChanged(PickerView pickerView, int i2, int i3) {
                if (pickerView == DivisionPickerView.this.provincePicker) {
                    DivisionPickerView.this.cityAdapter.setDivisions(DivisionPickerView.this.provisionAdapter.getItem(DivisionPickerView.this.provincePicker.getSelectedItemPosition()).getChildren());
                    DivisionPickerView.this.divisionAdapter.setDivisions(DivisionPickerView.this.cityAdapter.getItem(DivisionPickerView.this.cityPicker.getSelectedItemPosition()).getChildren());
                } else if (pickerView == DivisionPickerView.this.cityPicker) {
                    DivisionPickerView.this.divisionAdapter.setDivisions(DivisionPickerView.this.cityAdapter.getItem(DivisionPickerView.this.cityPicker.getSelectedItemPosition()).getChildren());
                }
                if (DivisionPickerView.this.onSelectedDivisionChangedListener != null) {
                    DivisionPickerView.this.onSelectedDivisionChangedListener.onSelectedDivisionChanged(DivisionPickerView.this.getSelectedDivision());
                }
            }
        };
        this.provincePicker.setOnSelectedItemChangedListener(onSelectedItemChangedListener);
        this.cityPicker.setOnSelectedItemChangedListener(onSelectedItemChangedListener);
        this.divisionPicker.setOnSelectedItemChangedListener(onSelectedItemChangedListener);
    }

    public void setOnSelectedDateChangedListener(OnSelectedDivisionChangedListener onSelectedDivisionChangedListener) {
        this.onSelectedDivisionChangedListener = onSelectedDivisionChangedListener;
    }

    public void setType(int i2) {
        this.type = i2;
        configure();
    }

    public DivisionPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DivisionPickerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.provisionAdapter = new DivisionAdapter();
        this.cityAdapter = new DivisionAdapter();
        this.divisionAdapter = new DivisionAdapter();
        this.type = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DivisionPickerView);
        this.type = typedArrayObtainStyledAttributes.getInt(R.styleable.DivisionPickerView_divisionPickerType, 1);
        typedArrayObtainStyledAttributes.recycle();
        PickerView pickerView = new PickerView(context);
        this.provincePicker = pickerView;
        settlePickerView(pickerView);
        PickerView pickerView2 = new PickerView(context);
        this.cityPicker = pickerView2;
        settlePickerView(pickerView2);
        PickerView pickerView3 = new PickerView(context);
        this.divisionPicker = pickerView3;
        settlePickerView(pickerView3);
        configure();
    }
}
