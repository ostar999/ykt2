package com.lxj.xpopup.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.lxj.xpopup.R;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class VerticalRecyclerView extends RecyclerView {
    public VerticalRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public void setupDivider(Boolean bool) {
        SmartDivider smartDivider = new SmartDivider(getContext(), 1);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(getResources().getColor(bool.booleanValue() ? R.color._xpopup_list_dark_divider : R.color._xpopup_list_divider));
        gradientDrawable.setSize(10, XPopupUtils.dp2px(getContext(), 0.5f));
        smartDivider.setDrawable(gradientDrawable);
        addItemDecoration(smartDivider);
    }

    public VerticalRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public VerticalRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
