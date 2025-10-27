package com.psychiatrygarden.activity.mine.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.setting.PlayerSetting;
import com.psychiatrygarden.activity.setting.VideoDefinitionSelectActivity;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class PlayerSetting extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) VideoDefinitionSelectActivity.class).putExtra(VideoDefinitionSelectActivity.CONFIG_TYPE, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$1(CompoundButton compoundButton, boolean z2) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.ALLOW_OPERATOR_PLAY_CONFIG, z2, compoundButton.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        finish();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, (Class<?>) PlayerSetting.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_back);
        textView.setText("播放设置");
        Switch r02 = (Switch) findViewById(R.id.sw_allow_operator_play);
        findViewById(R.id.tv_video_definition).setOnClickListener(new View.OnClickListener() { // from class: m1.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27735c.lambda$init$0(view);
            }
        });
        r02.setChecked(SharePreferencesUtils.readBooleanConfig(CommonParameter.ALLOW_OPERATOR_PLAY_CONFIG, false, this));
        r02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: m1.z
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                PlayerSetting.lambda$init$1(compoundButton, z2);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: m1.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27675c.lambda$init$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_player_setting);
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
