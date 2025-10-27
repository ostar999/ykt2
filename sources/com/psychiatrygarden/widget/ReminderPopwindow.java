package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ReminderPopwindow extends CenterPopupView {
    TextView agree;
    TextView content;
    private Context context;
    private onclickImL monclickImL;
    TextView refuse;

    public interface onclickImL {
        void agree();
    }

    public ReminderPopwindow(@NonNull Context context, onclickImL monclickImL) {
        super(context);
        this.context = context;
        this.monclickImL = monclickImL;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        getContext().startActivity(new Intent(this.context, (Class<?>) WebLongSaveActivity.class).putExtra("web_url", NetworkRequestsURL.getPrivacyApi).putExtra("title", "隐私政策").addFlags(268435456));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1() {
        this.context.startActivity(new Intent(this.context, (Class<?>) WebLongSaveActivity.class).putExtra("web_url", NetworkRequestsURL.userAgreementApi).putExtra("title", "用户协议").addFlags(268435456));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        dismiss();
        System.exit(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(View view) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.agree, true, this.context);
        dismiss();
        this.monclickImL.agree();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_reminder_pop_law;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.refuse = (TextView) findViewById(R.id.refuse);
        this.agree = (TextView) findViewById(R.id.agree);
        this.content = (TextView) findViewById(R.id.content);
        TextView textView = (TextView) findViewById(R.id.tv_tips);
        SpannableString spannableString = new SpannableString(textView.getText().toString());
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.app_theme_red));
        spannableString.setSpan(foregroundColorSpan, 18, 24, 34);
        spannableString.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.widget.zf
            @Override // com.psychiatrygarden.interfaceclass.DomoIml
            public final void clickToast() {
                this.f17155a.lambda$onCreate$0();
            }
        }, false), 18, 24, 34);
        spannableString.setSpan(foregroundColorSpan, 25, 31, 34);
        spannableString.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.widget.ag
            @Override // com.psychiatrygarden.interfaceclass.DomoIml
            public final void clickToast() {
                this.f16320a.lambda$onCreate$1();
            }
        }, false), 25, 31, 34);
        textView.setHighlightColor(0);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString);
        this.refuse.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.bg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16350c.lambda$onCreate$2(view);
            }
        });
        this.agree.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.cg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16380c.lambda$onCreate$3(view);
            }
        });
    }
}
