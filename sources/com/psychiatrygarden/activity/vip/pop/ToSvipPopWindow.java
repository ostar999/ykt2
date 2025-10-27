package com.psychiatrygarden.activity.vip.pop;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ToSvipPopWindow extends CenterPopupView {
    public TextView cancletv;
    public TextView okBt;
    public ToSvipClickIml toSvipClickIml;
    public TextView txttv;
    public String txtx;

    public interface ToSvipClickIml {
        void mToSvipClick();
    }

    public ToSvipPopWindow(@NonNull Context context, String txtx, ToSvipClickIml toSvipClickIml) {
        super(context);
        this.txtx = txtx;
        this.toSvipClickIml = toSvipClickIml;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_to_svip_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.cancletv = (TextView) findViewById(R.id.cancletv);
        this.txttv = (TextView) findViewById(R.id.txttv);
        this.okBt = (TextView) findViewById(R.id.okBt);
        this.txttv.setText(this.txtx + "");
        this.txttv.setMovementMethod(ScrollingMovementMethod.getInstance());
        this.okBt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.ToSvipPopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ToSvipPopWindow.this.toSvipClickIml.mToSvipClick();
                ToSvipPopWindow.this.dismiss();
            }
        });
        this.cancletv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.vip.pop.ToSvipPopWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ToSvipPopWindow.this.dismiss();
            }
        });
    }
}
