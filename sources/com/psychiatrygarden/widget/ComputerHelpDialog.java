package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ComputerHelpDialog extends CenterPopupView {
    private SpannableStringBuilder content;
    public TextView ok;
    public ClickIml sClickIml;
    private String sureBtn;
    private String title;

    public interface ClickIml {
        void mClickIml();
    }

    public ComputerHelpDialog(@NonNull Context context, ClickIml sClickIml, String sureBtn) {
        super(context);
        this.sClickIml = sClickIml;
        this.sureBtn = sureBtn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
        this.sClickIml.mClickIml();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_computer_help;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(this.content)) {
            ((TextView) findViewById(R.id.tv_content)).setText(this.content);
        }
        TextView textView = (TextView) findViewById(R.id.tv_top_hint);
        this.ok = (TextView) findViewById(R.id.ok);
        if (!TextUtils.isEmpty(this.title)) {
            textView.setText(this.title);
        }
        if (TextUtils.isEmpty(this.sureBtn)) {
            this.ok.setVisibility(8);
        } else {
            this.ok.setText(this.sureBtn);
            this.ok.setVisibility(0);
        }
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.s3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16888c.lambda$onCreate$0(view);
            }
        });
    }
}
