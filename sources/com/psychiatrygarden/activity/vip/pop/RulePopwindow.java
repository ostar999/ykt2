package com.psychiatrygarden.activity.vip.pop;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class RulePopwindow extends CenterPopupView {
    public TextView okBt;
    public TextView txttv;
    public String txtx;

    public RulePopwindow(@NonNull Context context, String txtx) {
        super(context);
        this.txtx = txtx;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_rule_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.txttv = (TextView) findViewById(R.id.txttv);
        this.okBt = (TextView) findViewById(R.id.okBt);
        this.txttv.setText(this.txtx + "");
        this.txttv.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.okBt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.RulePopwindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RulePopwindow.this.dismiss();
            }
        });
    }
}
