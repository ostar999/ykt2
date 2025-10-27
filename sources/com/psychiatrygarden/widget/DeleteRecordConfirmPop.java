package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DeleteRecordConfirmPop extends CenterPopupView {
    private String cancelBtn;
    public TextView close;
    private String content;
    public TextView ok;
    public ClickIml sClickIml;
    private String sureBtn;
    private String title;

    public interface ClickIml {
        void mClickIml();
    }

    public DeleteRecordConfirmPop(@NonNull Context context, ClickIml sClickIml) {
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
        return R.layout.layout_delete_record_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(this.title)) {
            ((TextView) findViewById(R.id.tv_top_hint)).setText(this.title);
        }
        if (!TextUtils.isEmpty(this.content)) {
            ((TextView) findViewById(R.id.tv_content)).setText(this.content);
        }
        this.close = (TextView) findViewById(R.id.cancel);
        this.ok = (TextView) findViewById(R.id.ok);
        if (!TextUtils.isEmpty(this.cancelBtn)) {
            this.close.setText(this.cancelBtn);
        }
        if (!TextUtils.isEmpty(this.sureBtn)) {
            this.ok.setText(this.sureBtn);
        }
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.x5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17077c.lambda$onCreate$0(view);
            }
        });
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.y5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17114c.lambda$onCreate$1(view);
            }
        });
    }

    public DeleteRecordConfirmPop(@NonNull Context context, ClickIml sClickIml, String content, String title, String cancelBtn, String sureBtn) {
        super(context);
        this.sClickIml = sClickIml;
        this.content = content;
        this.title = title;
        this.cancelBtn = cancelBtn;
        this.sureBtn = sureBtn;
    }
}
