package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ClearUnRedNumberPopuWindow extends CenterPopupView {
    public ClickIml sClickIml;

    public interface ClickIml {
        void mClickIml();
    }

    public ClearUnRedNumberPopuWindow(@NonNull Context context, ClickIml sClickIml) {
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
        return R.layout.layout_clean_unred_number_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.cancel);
        TextView textView2 = (TextView) findViewById(R.id.ok);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.g3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16502c.lambda$onCreate$0(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.h3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16539c.lambda$onCreate$1(view);
            }
        });
    }
}
