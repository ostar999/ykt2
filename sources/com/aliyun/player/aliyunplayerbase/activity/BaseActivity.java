package com.aliyun.player.aliyunplayerbase.activity;

import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes2.dex */
public class BaseActivity extends AppCompatActivity {
    public boolean isStrangePhone() {
        String str = Build.DEVICE;
        return "mx5".equalsIgnoreCase(str) || "Redmi Note2".equalsIgnoreCase(str) || "Z00A_1".equalsIgnoreCase(str) || "hwH60-L02".equalsIgnoreCase(str) || "hermes".equalsIgnoreCase(str) || ("V4".equalsIgnoreCase(str) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER)) || ("m1metal".equalsIgnoreCase(str) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));
    }
}
