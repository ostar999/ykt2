package com.psychiatrygarden.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class FriendPermissionAct extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        startActivity(BlackListAct.newIntent(this));
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, (Class<?>) FriendPermissionAct.class);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        ((TextView) findViewById(R.id.tv_black_form)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.p
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13896c.lambda$init$0(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_friend_permission);
        setTitle("朋友权限");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
