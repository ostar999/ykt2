package com.lxj.xpopup.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public final class XPermission {
    private static List<String> PERMISSIONS;
    private static XPermission sInstance;
    private static SimpleCallback sSimpleCallback4DrawOverlays;
    private static SimpleCallback sSimpleCallback4WriteSettings;
    private Context context;
    private FullCallback mFullCallback;
    private OnRationaleListener mOnRationaleListener;
    private Set<String> mPermissions;
    private List<String> mPermissionsDenied;
    private List<String> mPermissionsDeniedForever;
    private List<String> mPermissionsGranted;
    private List<String> mPermissionsRequest;
    private SimpleCallback mSimpleCallback;
    private ThemeCallback mThemeCallback;

    public interface FullCallback {
        void onDenied(List<String> list, List<String> list2);

        void onGranted(List<String> list);
    }

    public interface OnRationaleListener {

        public interface ShouldRequest {
            void again(boolean z2);
        }

        void rationale(ShouldRequest shouldRequest);
    }

    @RequiresApi(api = 23)
    public static class PermissionActivity extends Activity {
        private static final String TYPE = "TYPE";
        public static final int TYPE_DRAW_OVERLAYS = 3;
        public static final int TYPE_RUNTIME = 1;
        public static final int TYPE_WRITE_SETTINGS = 2;

        public static void start(Context context, int i2) {
            Intent intent = new Intent(context, (Class<?>) PermissionActivity.class);
            intent.addFlags(268435456);
            intent.putExtra("TYPE", i2);
            context.startActivity(intent);
        }

        @Override // android.app.Activity, android.view.Window.Callback
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            finish();
            return true;
        }

        @Override // android.app.Activity
        public void onActivityResult(int i2, int i3, Intent intent) {
            if (i2 == 2) {
                if (XPermission.sSimpleCallback4WriteSettings == null) {
                    return;
                }
                if (XPermission.sInstance.isGrantedWriteSettings()) {
                    XPermission.sSimpleCallback4WriteSettings.onGranted();
                } else {
                    XPermission.sSimpleCallback4WriteSettings.onDenied();
                }
                SimpleCallback unused = XPermission.sSimpleCallback4WriteSettings = null;
            } else if (i2 == 3) {
                if (XPermission.sSimpleCallback4DrawOverlays == null) {
                    return;
                }
                if (XPermission.sInstance.isGrantedDrawOverlays()) {
                    XPermission.sSimpleCallback4DrawOverlays.onGranted();
                } else {
                    XPermission.sSimpleCallback4DrawOverlays.onDenied();
                }
                SimpleCallback unused2 = XPermission.sSimpleCallback4DrawOverlays = null;
            }
            finish();
        }

        @Override // android.app.Activity
        public void onCreate(@Nullable Bundle bundle) {
            getWindow().addFlags(262672);
            getWindow().getAttributes().alpha = 0.0f;
            int intExtra = getIntent().getIntExtra("TYPE", 1);
            if (intExtra != 1) {
                if (intExtra == 2) {
                    super.onCreate(bundle);
                    XPermission.sInstance.startWriteSettingsActivity(this, 2);
                    return;
                } else {
                    if (intExtra == 3) {
                        super.onCreate(bundle);
                        XPermission.sInstance.startOverlayPermissionActivity(this, 3);
                        return;
                    }
                    return;
                }
            }
            if (XPermission.sInstance == null) {
                super.onCreate(bundle);
                Log.e("XPermission", "request permissions failed");
                finish();
                return;
            }
            if (XPermission.sInstance.mThemeCallback != null) {
                XPermission.sInstance.mThemeCallback.onActivityCreate(this);
            }
            super.onCreate(bundle);
            if (XPermission.sInstance.rationale(this)) {
                finish();
                return;
            }
            if (XPermission.sInstance.mPermissionsRequest != null) {
                int size = XPermission.sInstance.mPermissionsRequest.size();
                if (size <= 0) {
                    finish();
                } else {
                    requestPermissions((String[]) XPermission.sInstance.mPermissionsRequest.toArray(new String[size]), 1);
                }
            }
        }

        @Override // android.app.Activity
        public void onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
            XPermission.sInstance.onRequestPermissionsResult(this);
            finish();
        }
    }

    public interface SimpleCallback {
        void onDenied();

        void onGranted();
    }

    public interface ThemeCallback {
        void onActivityCreate(Activity activity);
    }

    private XPermission(Context context, String... strArr) {
        sInstance = this;
        this.context = context;
        prepare(strArr);
    }

    public static XPermission create(Context context, String... strArr) {
        XPermission xPermission = sInstance;
        if (xPermission == null) {
            return new XPermission(context, strArr);
        }
        xPermission.context = context;
        xPermission.prepare(strArr);
        return sInstance;
    }

    public static XPermission getInstance() {
        return sInstance;
    }

    private void getPermissionsStatus(Activity activity) {
        for (String str : this.mPermissionsRequest) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsDenied.add(str);
                if (!activity.shouldShowRequestPermissionRationale(str)) {
                    this.mPermissionsDeniedForever.add(str);
                }
            }
        }
    }

    private boolean isIntentAvailable(Intent intent) {
        return this.context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestPermissionsResult(Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }

    private void prepare(String... strArr) {
        this.mPermissions = new LinkedHashSet();
        PERMISSIONS = getPermissions();
        if (strArr == null) {
            return;
        }
        for (String str : strArr) {
            for (String str2 : PermissionConstants.getPermissions(str)) {
                if (PERMISSIONS.contains(str2)) {
                    this.mPermissions.add(str2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCallback() {
        if (this.mSimpleCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissions.size() == this.mPermissionsGranted.size()) {
                this.mSimpleCallback.onGranted();
            } else if (!this.mPermissionsDenied.isEmpty()) {
                this.mSimpleCallback.onDenied();
            }
            this.mSimpleCallback = null;
        }
        if (this.mFullCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissions.size() == this.mPermissionsGranted.size()) {
                this.mFullCallback.onGranted(this.mPermissionsGranted);
            } else if (!this.mPermissionsDenied.isEmpty()) {
                this.mFullCallback.onDenied(this.mPermissionsDeniedForever, this.mPermissionsDenied);
            }
            this.mFullCallback = null;
        }
        this.mOnRationaleListener = null;
        this.mThemeCallback = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public void startOverlayPermissionActivity(Activity activity, int i2) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(Uri.parse("package:" + this.context.getPackageName()));
        if (isIntentAvailable(intent)) {
            activity.startActivityForResult(intent, i2);
        } else {
            launchAppDetailsSettings();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 23)
    public void startPermissionActivity() {
        this.mPermissionsDenied = new ArrayList();
        this.mPermissionsDeniedForever = new ArrayList();
        PermissionActivity.start(this.context, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public void startWriteSettingsActivity(Activity activity, int i2) {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + this.context.getPackageName()));
        if (isIntentAvailable(intent)) {
            activity.startActivityForResult(intent, i2);
        } else {
            launchAppDetailsSettings();
        }
    }

    public XPermission callback(SimpleCallback simpleCallback) {
        this.mSimpleCallback = simpleCallback;
        return this;
    }

    public List<String> getPermissions() {
        return getPermissions(this.context.getPackageName());
    }

    public boolean isGranted(String... strArr) {
        for (String str : strArr) {
            if (!isGranted(str)) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = 23)
    public boolean isGrantedDrawOverlays() {
        return Settings.canDrawOverlays(this.context);
    }

    @RequiresApi(api = 23)
    public boolean isGrantedWriteSettings() {
        return Settings.System.canWrite(this.context);
    }

    public void launchAppDetailsSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + this.context.getPackageName()));
        if (isIntentAvailable(intent)) {
            this.context.startActivity(intent.addFlags(268435456));
        }
    }

    public XPermission rationale(OnRationaleListener onRationaleListener) {
        this.mOnRationaleListener = onRationaleListener;
        return this;
    }

    public void releaseContext() {
        this.context = null;
    }

    public void request() {
        this.mPermissionsGranted = new ArrayList();
        this.mPermissionsRequest = new ArrayList();
        for (String str : this.mPermissions) {
            if (isGranted(str)) {
                this.mPermissionsGranted.add(str);
            } else {
                this.mPermissionsRequest.add(str);
            }
        }
        if (this.mPermissionsRequest.isEmpty()) {
            requestCallback();
        } else {
            startPermissionActivity();
        }
    }

    @RequiresApi(api = 23)
    public void requestDrawOverlays(SimpleCallback simpleCallback) {
        if (!isGrantedDrawOverlays()) {
            sSimpleCallback4DrawOverlays = simpleCallback;
            PermissionActivity.start(this.context, 3);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    @RequiresApi(api = 23)
    public void requestWriteSettings(SimpleCallback simpleCallback) {
        if (!isGrantedWriteSettings()) {
            sSimpleCallback4WriteSettings = simpleCallback;
            PermissionActivity.start(this.context, 2);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 23)
    public boolean rationale(Activity activity) {
        boolean z2 = false;
        if (this.mOnRationaleListener != null) {
            Iterator<String> it = this.mPermissionsRequest.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (activity.shouldShowRequestPermissionRationale(it.next())) {
                    getPermissionsStatus(activity);
                    this.mOnRationaleListener.rationale(new OnRationaleListener.ShouldRequest() { // from class: com.lxj.xpopup.util.XPermission.1
                        @Override // com.lxj.xpopup.util.XPermission.OnRationaleListener.ShouldRequest
                        public void again(boolean z3) {
                            if (z3) {
                                XPermission.this.startPermissionActivity();
                            } else {
                                XPermission.this.requestCallback();
                            }
                        }
                    });
                    z2 = true;
                    break;
                }
            }
            this.mOnRationaleListener = null;
        }
        return z2;
    }

    public XPermission callback(FullCallback fullCallback) {
        this.mFullCallback = fullCallback;
        return this;
    }

    public List<String> getPermissions(String str) {
        try {
            String[] strArr = this.context.getPackageManager().getPackageInfo(str, 4096).requestedPermissions;
            return strArr == null ? Collections.emptyList() : Arrays.asList(strArr);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return Collections.emptyList();
        }
    }

    private boolean isGranted(String str) {
        return ContextCompat.checkSelfPermission(this.context, str) == 0;
    }
}
