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
public class DeleteDownloadDialog extends CenterPopupView {
    private String cancelBtn;
    public TextView close;
    private SpannableStringBuilder content;
    public TextView ok;
    public ClickIml sClickIml;
    public ClickIml2 sClickIml2;
    private String sureBtn;
    private String title;

    public interface ClickIml {
        void mClickIml();
    }

    public interface ClickIml2 {
        void mClickCancel();

        void mClickIml();
    }

    public DeleteDownloadDialog(@NonNull Context context, ClickIml sClickIml, SpannableStringBuilder content, String cancelBtn, String sureBtn) {
        super(context);
        this.sClickIml = sClickIml;
        this.content = content;
        this.cancelBtn = cancelBtn;
        this.sureBtn = sureBtn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
        ClickIml2 clickIml2 = this.sClickIml2;
        if (clickIml2 != null) {
            clickIml2.mClickCancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
        ClickIml clickIml = this.sClickIml;
        if (clickIml != null) {
            clickIml.mClickIml();
        }
        ClickIml2 clickIml2 = this.sClickIml2;
        if (clickIml2 != null) {
            clickIml2.mClickIml();
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_delete_download;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        if (!TextUtils.isEmpty(this.content)) {
            ((TextView) findViewById(R.id.tv_content)).setText(this.content);
        }
        TextView textView = (TextView) findViewById(R.id.tv_top_hint);
        this.close = (TextView) findViewById(R.id.cancel);
        this.ok = (TextView) findViewById(R.id.ok);
        View viewFindViewById = findViewById(R.id.line_view);
        if (!TextUtils.isEmpty(this.title)) {
            textView.setText(this.title);
        }
        if (TextUtils.isEmpty(this.cancelBtn)) {
            this.close.setVisibility(8);
        } else {
            this.close.setText(this.cancelBtn);
        }
        if (TextUtils.isEmpty(this.sureBtn)) {
            viewFindViewById.setVisibility(8);
            this.ok.setVisibility(8);
        } else {
            this.ok.setText(this.sureBtn);
            viewFindViewById.setVisibility(0);
            this.ok.setVisibility(0);
        }
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.t5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16924c.lambda$onCreate$0(view);
            }
        });
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.u5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16947c.lambda$onCreate$1(view);
            }
        });
    }

    public DeleteDownloadDialog(@NonNull Context context, ClickIml sClickIml, SpannableStringBuilder content, String title, String cancelBtn, String sureBtn) {
        super(context);
        this.sClickIml = sClickIml;
        this.content = content;
        this.cancelBtn = cancelBtn;
        this.sureBtn = sureBtn;
        this.title = title;
    }

    public DeleteDownloadDialog(@NonNull Context context, ClickIml2 sClickIml2, SpannableStringBuilder content, String title, String cancelBtn, String sureBtn) {
        super(context);
        this.sClickIml2 = sClickIml2;
        this.content = content;
        this.cancelBtn = cancelBtn;
        this.sureBtn = sureBtn;
        this.title = title;
    }
}
