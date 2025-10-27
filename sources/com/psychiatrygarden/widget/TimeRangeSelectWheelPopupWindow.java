package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import top.defaults.view.PickerView;

/* loaded from: classes6.dex */
public class TimeRangeSelectWheelPopupWindow extends BottomPopupView {
    private RelativeLayout layoutSwitch;
    public ClickIml sClickIml;
    private boolean showSwitch;
    private Switch switchTime;
    private String timeRange;
    private String title;

    public interface ClickIml {
        void mClickIml(String timeRange);

        void switchOpera(boolean open);
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

    public TimeRangeSelectWheelPopupWindow(@NonNull Context context, ClickIml sClickIml, String title, boolean showSwitch) {
        super(context);
        this.sClickIml = sClickIml;
        this.title = title;
        this.showSwitch = showSwitch;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(CompoundButton compoundButton, boolean z2) {
        ClickIml clickIml = this.sClickIml;
        if (clickIml != null) {
            clickIml.switchOpera(z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        ClickIml clickIml = this.sClickIml;
        if (clickIml != null) {
            clickIml.mClickIml(this.timeRange);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(List list, PickerView pickerView, int i2, int i3) {
        this.timeRange = ((CustomerPickerItem) list.get(i3)).name;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_time_range_wheel_popup_window;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutSwitch);
        this.layoutSwitch = relativeLayout;
        relativeLayout.setVisibility(this.showSwitch ? 0 : 8);
        ((TextView) findViewById(R.id.tvTitle)).setText(this.title);
        TextView textView = (TextView) findViewById(R.id.tvCancel);
        TextView textView2 = (TextView) findViewById(R.id.tvSubmit);
        Switch r3 = (Switch) findViewById(R.id.switchTime);
        this.switchTime = r3;
        r3.setChecked(false);
        this.switchTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.psychiatrygarden.widget.ki
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                this.f16656c.lambda$onCreate$0(compoundButton, z2);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.li
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16690c.lambda$onCreate$1(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.mi
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16719c.lambda$onCreate$2(view);
            }
        });
        PickerView pickerView = (PickerView) findViewById(R.id.wheel1);
        pickerView.setChangeItemColor(true);
        PickerStringAdapter pickerStringAdapter = new PickerStringAdapter();
        final ArrayList arrayList = new ArrayList();
        arrayList.add(new CustomerPickerItem("8:00 - 10:00"));
        arrayList.add(new CustomerPickerItem("14:00 - 18:00"));
        arrayList.add(new CustomerPickerItem("20:00 - 22:00"));
        pickerStringAdapter.setDivisions(arrayList);
        pickerView.setAdapter(pickerStringAdapter);
        pickerView.setOnSelectedItemChangedListener(new PickerView.OnSelectedItemChangedListener() { // from class: com.psychiatrygarden.widget.ni
            @Override // top.defaults.view.PickerView.OnSelectedItemChangedListener
            public final void onSelectedItemChanged(PickerView pickerView2, int i2, int i3) {
                this.f16743a.lambda$onCreate$3(arrayList, pickerView2, i2, i3);
            }
        });
    }

    public void setsClickIml(ClickIml sClickIml) {
        this.sClickIml = sClickIml;
    }
}
