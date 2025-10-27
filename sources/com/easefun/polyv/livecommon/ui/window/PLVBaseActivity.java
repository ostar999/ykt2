package com.easefun.polyv.livecommon.ui.window;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationManager;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVRotationObserver;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVBaseActivity extends AppCompatActivity {
    private static int APP_STATUS = 0;
    private static final int APP_STATUS_KILLED = 0;
    private static final int APP_STATUS_RUNNING = 1;
    private static final String TAG = "PLVBaseActivity";
    private PLVOrientationManager orientationManager;
    private PLVRotationObserver rotationObserver;

    private String getLaunchActivityName() {
        Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(getPackageName());
        List<ResolveInfo> listQueryIntentActivities = getPackageManager().queryIntentActivities(intent, 0);
        if (listQueryIntentActivities == null || listQueryIntentActivities.isEmpty()) {
            return null;
        }
        return listQueryIntentActivities.get(0).activityInfo.name;
    }

    private void initOrientationManager() {
        this.rotationObserver = new PLVRotationObserver(this);
        PLVOrientationManager pLVOrientationManager = PLVOrientationManager.getInstance();
        this.orientationManager = pLVOrientationManager;
        pLVOrientationManager.addRotationObserver(this.rotationObserver, enableRotationObserver());
    }

    private boolean isLaunchActivityInheritFromBase() {
        try {
            if (getLaunchActivityName() != null) {
                return PLVBaseActivity.class.isAssignableFrom(Class.forName(getLaunchActivityName()));
            }
            return false;
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return false;
        }
    }

    private boolean restartApp() {
        try {
            Intent intent = new Intent(this, Class.forName(getLaunchActivityName()));
            intent.setFlags(268468224);
            startActivity(intent);
            stopApp();
            return true;
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, "restartApp:" + e2.getMessage());
            return false;
        }
    }

    private static void stopApp() {
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    private void stopOnAbnormalLaunch() {
        PLVCommonLog.e(TAG, "App stop on abnormal launch");
        if (isLaunchActivityInheritFromBase() && restartApp()) {
            return;
        }
        stopApp();
    }

    public boolean enableRotationObserver() {
        return false;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public <T extends View> T findViewById(@IdRes int i2) {
        return (T) super.findViewById(i2);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        PLVOrientationManager pLVOrientationManager = this.orientationManager;
        if (pLVOrientationManager != null) {
            pLVOrientationManager.notifyConfigurationChanged(this, newConfig);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && APP_STATUS == 0) {
            stopOnAbnormalLaunch();
            return;
        }
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
            savedInstanceState.putParcelable("android:fragments", null);
        }
        super.onCreate(savedInstanceState);
        APP_STATUS = 1;
        initOrientationManager();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        PLVOrientationManager pLVOrientationManager = this.orientationManager;
        if (pLVOrientationManager != null) {
            pLVOrientationManager.removeRotationObserver(this.rotationObserver);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        PLVOrientationManager pLVOrientationManager = this.orientationManager;
        if (pLVOrientationManager != null) {
            pLVOrientationManager.stop();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (this.orientationManager == null || !enableRotationObserver()) {
            return;
        }
        this.orientationManager.start();
    }
}
