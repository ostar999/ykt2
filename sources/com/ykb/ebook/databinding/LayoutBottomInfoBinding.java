package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.BatteryView;
import com.ykb.ebook.weight.BatteryWidget;

/* loaded from: classes6.dex */
public final class LayoutBottomInfoBinding implements ViewBinding {

    @NonNull
    public final BatteryWidget batteryInfo;

    @NonNull
    public final LinearLayout info;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final BatteryView tvProgress;

    @NonNull
    public final BatteryView tvTime;

    private LayoutBottomInfoBinding(@NonNull LinearLayout linearLayout, @NonNull BatteryWidget batteryWidget, @NonNull LinearLayout linearLayout2, @NonNull BatteryView batteryView, @NonNull BatteryView batteryView2) {
        this.rootView = linearLayout;
        this.batteryInfo = batteryWidget;
        this.info = linearLayout2;
        this.tvProgress = batteryView;
        this.tvTime = batteryView2;
    }

    @NonNull
    public static LayoutBottomInfoBinding bind(@NonNull View view) {
        int i2 = R.id.battery_info;
        BatteryWidget batteryWidget = (BatteryWidget) ViewBindings.findChildViewById(view, i2);
        if (batteryWidget != null) {
            LinearLayout linearLayout = (LinearLayout) view;
            i2 = R.id.tv_progress;
            BatteryView batteryView = (BatteryView) ViewBindings.findChildViewById(view, i2);
            if (batteryView != null) {
                i2 = R.id.tv_time;
                BatteryView batteryView2 = (BatteryView) ViewBindings.findChildViewById(view, i2);
                if (batteryView2 != null) {
                    return new LayoutBottomInfoBinding(linearLayout, batteryWidget, linearLayout, batteryView, batteryView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static LayoutBottomInfoBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutBottomInfoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_bottom_info, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public LinearLayout getRoot() {
        return this.rootView;
    }
}
