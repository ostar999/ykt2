package com.aliyun.player.aliyunplayerbase.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.aliyunplayerbase.R;

/* loaded from: classes2.dex */
public class SdkVersionActivity extends AppCompatActivity {
    @SuppressLint({"SetTextI18n"})
    private void showVersionInfo() {
        ((TextView) findViewById(R.id.tv_version)).setText("VERSION :" + AliPlayerFactory.getSdkVersion());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sdk_version);
        showVersionInfo();
    }
}
