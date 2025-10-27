package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.bean.ReportParams;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SignRulePop extends BottomPopupView {
    private String content;
    private ReportParams mReportParams;

    public SignRulePop(Context context, ReportParams params) {
        super(context);
        this.mReportParams = params;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.sign_bottom_layout;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        getHostWindow().setLayout(-1, -1);
        TextView textView = (TextView) findViewById(R.id.sign_rule_tv);
        ImageView imageView = (ImageView) findViewById(R.id.sign_rule_close_iv);
        CommonUtil.setParagraphSpacing(textView, this.content, 25);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.qh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16836c.lambda$onCreate$0(view);
            }
        });
    }

    public SignRulePop(@NonNull Context context, String content) {
        super(context);
        this.content = content;
    }
}
