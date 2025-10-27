package com.psychiatrygarden.activity.setting;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class VideoDefinitionConfigActivity extends BaseActivity {
    private RelativeLayout mLyDownConfig;
    private RelativeLayout mLyPlayConfig;
    private TextView mTvDownConfig;
    private TextView mTvPlayConfig;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) VideoDefinitionSelectActivity.class).putExtra(VideoDefinitionSelectActivity.CONFIG_TYPE, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) VideoDefinitionSelectActivity.class).putExtra(VideoDefinitionSelectActivity.CONFIG_TYPE, 1));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("视频清晰度");
        this.mLyPlayConfig = (RelativeLayout) findViewById(R.id.ly_play_config);
        this.mTvPlayConfig = (TextView) findViewById(R.id.tv_play_config);
        this.mLyDownConfig = (RelativeLayout) findViewById(R.id.ly_down_config);
        this.mTvDownConfig = (TextView) findViewById(R.id.tv_down_config);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.mTvPlayConfig != null) {
            this.mTvPlayConfig.setText(AliPlayUtils.getPlayVideoDefinitionLabel(this));
        }
        if (this.mTvDownConfig != null) {
            this.mTvDownConfig.setText(AliPlayUtils.getDownloadVideoDefinitionLabel(this));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_video_definition_config);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mLyPlayConfig.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.b1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13838c.lambda$setListenerForWidget$0(view);
            }
        });
        this.mLyDownConfig.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.setting.c1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13843c.lambda$setListenerForWidget$1(view);
            }
        });
    }
}
