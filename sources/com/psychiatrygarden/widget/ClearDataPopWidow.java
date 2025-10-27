package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ClearDataPopWidow extends CenterPopupView {
    public TextView close;
    public TextView ok;
    public ClickIml sClickIml;

    public interface ClickIml {
        void mClickIml();
    }

    public ClearDataPopWidow(@NonNull Context context, ClickIml sClickIml) {
        super(context);
        this.sClickIml = sClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
        this.sClickIml.mClickIml();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_clear_data_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.close = (TextView) findViewById(R.id.close);
        this.ok = (TextView) findViewById(R.id.ok);
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.e3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16425c.lambda$onCreate$0(view);
            }
        });
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.f3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16473c.lambda$onCreate$1(view);
            }
        });
    }
}
