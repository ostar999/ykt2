package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import top.defaults.view.PickerView;

/* loaded from: classes6.dex */
public class TimeSelectWheelPopupWindow extends BottomPopupView {
    private String hourNumber;
    private String minuteNumber;
    public ClickIml sClickIml;
    private Switch switchTime;

    public interface ClickIml {
        void mClickIml(String h2, String m2);
    }

    public static class CustomerPickerItem implements PickerView.PickerItem {
        String name;

        public CustomerPickerItem(String name) {
            this.name = name;
        }

        @Override // top.defaults.view.PickerView.PickerItem
        public String getText() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PickerStringAdapter extends PickerView.Adapter<CustomerPickerItem> {
        private List<? extends CustomerPickerItem> divisions;

        @Override // top.defaults.view.PickerView.Adapter
        public int getItemCount() {
            List<? extends CustomerPickerItem> list = this.divisions;
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        public void setDivisions(List<? extends CustomerPickerItem> divisions) {
            this.divisions = divisions;
            notifyDataSetChanged();
        }

        @Override // top.defaults.view.PickerView.Adapter
        public CustomerPickerItem getItem(int index) {
            return this.divisions.get(index);
        }
    }

    public TimeSelectWheelPopupWindow(@NonNull Context context, ClickIml sClickIml) {
        super(context);
        this.sClickIml = sClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onCreate$0(CompoundButton compoundButton, boolean z2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        ClickIml clickIml = this.sClickIml;
        if (clickIml != null) {
            clickIml.mClickIml(this.hourNumber, this.minuteNumber);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(List list, PickerView pickerView, int i2, int i3) {
        this.hourNumber = ((CustomerPickerItem) list.get(i3)).name;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(List list, PickerView pickerView, int i2, int i3) {
        this.minuteNumber = ((CustomerPickerItem) list.get(i3)).name;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_time_wheel_popup_window;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        StringBuilder sb;
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tvCancel);
        TextView textView2 = (TextView) findViewById(R.id.tvSubmit);
        Switch r2 = (Switch) findViewById(R.id.switchTime);
        this.switchTime = r2;
        r2.setChecked(false);
        this.switchTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.widget.oi
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                TimeSelectWheelPopupWindow.lambda$onCreate$0(compoundButton, z2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.pi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16803c.lambda$onCreate$1(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.qi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16837c.lambda$onCreate$2(view);
            }
        });
        PickerView pickerView = (PickerView) findViewById(R.id.wheel1);
        PickerView pickerView2 = (PickerView) findViewById(R.id.wheel2);
        pickerView2.setChangeItemColor(true);
        pickerView.setChangeItemColor(true);
        PickerStringAdapter pickerStringAdapter = new PickerStringAdapter();
        final ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 24; i2++) {
            arrayList.add(new CustomerPickerItem(i2 < 10 ? "0" + i2 : "" + i2));
        }
        pickerStringAdapter.setDivisions(arrayList);
        pickerView.setAdapter(pickerStringAdapter);
        pickerView.setOnSelectedItemChangedListener(new PickerView.OnSelectedItemChangedListener() { // from class: com.psychiatrygarden.widget.ri
            @Override // top.defaults.view.PickerView.OnSelectedItemChangedListener
            public final void onSelectedItemChanged(PickerView pickerView3, int i3, int i4) {
                this.f16873a.lambda$onCreate$3(arrayList, pickerView3, i3, i4);
            }
        });
        PickerStringAdapter pickerStringAdapter2 = new PickerStringAdapter();
        final ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < 59; i3++) {
            if (i3 < 10) {
                sb = new StringBuilder();
                sb.append("0");
            } else {
                sb = new StringBuilder();
                sb.append("");
            }
            sb.append(i3);
            arrayList2.add(new CustomerPickerItem(sb.toString()));
        }
        pickerStringAdapter2.setDivisions(arrayList2);
        pickerView2.setAdapter(pickerStringAdapter2);
        pickerView2.setOnSelectedItemChangedListener(new PickerView.OnSelectedItemChangedListener() { // from class: com.psychiatrygarden.widget.si
            @Override // top.defaults.view.PickerView.OnSelectedItemChangedListener
            public final void onSelectedItemChanged(PickerView pickerView3, int i4, int i5) {
                this.f16911a.lambda$onCreate$4(arrayList2, pickerView3, i4, i5);
            }
        });
    }

    public void setsClickIml(ClickIml sClickIml) {
        this.sClickIml = sClickIml;
    }
}
