package com.psychiatrygarden.activity.mine.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.mine.setting.DownloadSetting;
import com.psychiatrygarden.activity.setting.VideoDefinitionSelectActivity;
import com.psychiatrygarden.aliPlayer.utils.AliPlayUtils;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SdkConstant;
import com.yikaobang.yixue.R;
import java.io.File;

/* loaded from: classes5.dex */
public class DownloadSetting extends BaseActivity {
    private RelativeLayout mLyBookLocal;
    private RelativeLayout mLyInfoLocal;
    private TextView mTvBookLocalAddress;
    private TextView mTvInfoLocalAddress;
    private TextView mTvPlayValue;

    private String getDownloadPath() {
        StringBuilder sb = new StringBuilder();
        String str = File.separator;
        sb.append(str);
        sb.append(SdkConstant.UMENG_ALIS);
        sb.append(str);
        sb.append("ResourceDownload/");
        String string = sb.toString();
        if (Build.VERSION.SDK_INT >= 29) {
            return ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + string;
        }
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        startActivity(new Intent(view.getContext(), (Class<?>) VideoDefinitionSelectActivity.class).putExtra(VideoDefinitionSelectActivity.CONFIG_TYPE, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$init$1(CompoundButton compoundButton, boolean z2) {
        SharePreferencesUtils.writeBooleanConfig(CommonParameter.ALLOW_OPERATOR_DOWNLOAD_CONFIG, z2, compoundButton.getContext());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        finish();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, (Class<?>) DownloadSetting.class));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.mActionBar.hide();
        TextView textView = (TextView) findViewById(R.id.txt_actionbar_title);
        ImageView imageView = (ImageView) findViewById(R.id.iv_actionbar_back);
        textView.setText("下载设置");
        this.mLyInfoLocal = (RelativeLayout) findViewById(R.id.ly_info_local);
        this.mLyBookLocal = (RelativeLayout) findViewById(R.id.ly_book_local);
        this.mTvInfoLocalAddress = (TextView) findViewById(R.id.tv_info_local_address);
        this.mTvBookLocalAddress = (TextView) findViewById(R.id.tv_book_local_address);
        this.mTvPlayValue = (TextView) findViewById(R.id.tv_play_str);
        Switch r02 = (Switch) findViewById(R.id.sw_allow_operator_download);
        findViewById(R.id.tv_video_definition).setOnClickListener(new View.OnClickListener() { // from class: m1.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27733c.lambda$init$0(view);
            }
        });
        r02.setChecked(SharePreferencesUtils.readBooleanConfig(CommonParameter.ALLOW_OPERATOR_DOWNLOAD_CONFIG, false, this));
        r02.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: m1.w
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                DownloadSetting.lambda$init$1(compoundButton, z2);
            }
        });
        this.mTvInfoLocalAddress.setText(getDownloadPath());
        this.mTvBookLocalAddress.setText(getDownloadPath());
        imageView.setOnClickListener(new View.OnClickListener() { // from class: m1.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f27734c.lambda$init$2(view);
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.mTvPlayValue != null) {
            this.mTvPlayValue.setText(AliPlayUtils.getDownloadVideoDefinitionLabel(this));
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_download_setting);
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
