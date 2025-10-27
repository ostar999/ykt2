package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class NormalConfirmTextPopWidow extends CenterPopupView {
    public TextView ok;
    public ClickIml sClickIml;

    public interface ClickIml {
        void mClickIml();
    }

    public NormalConfirmTextPopWidow(@NonNull Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_normal_confirm_text_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.ok);
        this.ok = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.mb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16707c.lambda$onCreate$0(view);
            }
        });
    }
}
