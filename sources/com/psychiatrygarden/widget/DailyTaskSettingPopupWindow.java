package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DailyTaskSettingPopupWindow extends BottomPopupView {
    public ClickIml sClickIml;

    public interface ClickIml {
        void mClickIml(int index);
    }

    public DailyTaskSettingPopupWindow(@NonNull Context context, ClickIml sClickIml) {
        super(context);
        this.sClickIml = sClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        ClickIml clickIml = this.sClickIml;
        if (clickIml != null) {
            clickIml.mClickIml(0);
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        ClickIml clickIml = this.sClickIml;
        if (clickIml != null) {
            clickIml.mClickIml(1);
        }
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_daily_task_popup_window;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tvOperaSystemHint);
        TextView textView2 = (TextView) findViewById(R.id.tvResetTask);
        ((TextView) findViewById(R.id.tvCancel)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.p5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16782c.lambda$onCreate$0(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.q5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16815c.lambda$onCreate$1(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.r5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16852c.lambda$onCreate$2(view);
            }
        });
    }

    public void setsClickIml(ClickIml sClickIml) {
        this.sClickIml = sClickIml;
    }
}
