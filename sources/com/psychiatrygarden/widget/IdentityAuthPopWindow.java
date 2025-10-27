package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class IdentityAuthPopWindow extends CenterPopupView {
    public TextView cancelTxt;
    public IdentityAuthClickIml mIdentityAuthClickIml;
    public TextView okTxt;

    public interface IdentityAuthClickIml {
        void mIdentityClick();
    }

    public IdentityAuthPopWindow(@NonNull @NotNull Context context, IdentityAuthClickIml mIdentityAuthClickIml) {
        super(context);
        this.mIdentityAuthClickIml = mIdentityAuthClickIml;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_identity_auth_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.okTxt = (TextView) findViewById(R.id.okTxt);
        TextView textView = (TextView) findViewById(R.id.cancelTxt);
        this.cancelTxt = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.IdentityAuthPopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                IdentityAuthPopWindow.this.dismiss();
            }
        });
        this.okTxt.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.IdentityAuthPopWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                IdentityAuthPopWindow.this.dismiss();
                IdentityAuthPopWindow.this.mIdentityAuthClickIml.mIdentityClick();
            }
        });
    }
}
