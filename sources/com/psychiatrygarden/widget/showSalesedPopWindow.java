package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class showSalesedPopWindow extends CenterPopupView {
    public TextView okTxt;

    public showSalesedPopWindow(@NonNull @NotNull Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.show_salesed_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.okTxt);
        this.okTxt = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.showSalesedPopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                showSalesedPopWindow.this.dismiss();
            }
        });
    }
}
