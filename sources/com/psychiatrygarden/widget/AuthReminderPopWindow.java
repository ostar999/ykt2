package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.activity.circleactivity.CircleSchoolVerifyActivity;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes6.dex */
public class AuthReminderPopWindow extends CenterPopupView {
    public TextView auth;
    public TextView cancel;

    public AuthReminderPopWindow(@NonNull @NotNull Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        getContext().startActivity(new Intent(getContext(), (Class<?>) CircleSchoolVerifyActivity.class));
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_auth_reminder_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.cancel = (TextView) findViewById(R.id.cancel);
        this.auth = (TextView) findViewById(R.id.auth);
        this.cancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16630c.lambda$onCreate$0(view);
            }
        });
        this.auth.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.l
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16659c.lambda$onCreate$1(view);
            }
        });
    }
}
