package com.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.Utils;
import com.blankj.utilcode.util.UtilsTransActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public final class PermissionUtils {
    private static PermissionUtils sInstance;
    private static SimpleCallback sSimpleCallback4DrawOverlays;
    private static SimpleCallback sSimpleCallback4WriteSettings;
    private FullCallback mFullCallback;
    private OnExplainListener mOnExplainListener;
    private OnRationaleListener mOnRationaleListener;
    private Set<String> mPermissions;
    private List<String> mPermissionsDenied;
    private List<String> mPermissionsDeniedForever;
    private List<String> mPermissionsGranted;
    private String[] mPermissionsParam;
    private List<String> mPermissionsRequest;
    private SimpleCallback mSimpleCallback;
    private SingleCallback mSingleCallback;
    private ThemeCallback mThemeCallback;

    public interface FullCallback {
        void onDenied(@NonNull List<String> list, @NonNull List<String> list2);

        void onGranted(@NonNull List<String> list);
    }

    public interface OnExplainListener {

        public interface ShouldRequest {
            void start(boolean z2);
        }

        void explain(@NonNull UtilsTransActivity utilsTransActivity, @NonNull List<String> list, @NonNull ShouldRequest shouldRequest);
    }

    public interface OnRationaleListener {

        public interface ShouldRequest {
            void again(boolean z2);
        }

        void rationale(@NonNull UtilsTransActivity utilsTransActivity, @NonNull ShouldRequest shouldRequest);
    }

    @RequiresApi(api = 23)
    public static final class PermissionActivityImpl extends UtilsTransActivity.TransActivityDelegate {
        private static PermissionActivityImpl INSTANCE = new PermissionActivityImpl();
        private static final String TYPE = "TYPE";
        private static final int TYPE_DRAW_OVERLAYS = 3;
        private static final int TYPE_RUNTIME = 1;
        private static final int TYPE_WRITE_SETTINGS = 2;
        private static int currentRequestCode = -1;

        private void checkRequestCallback(int i2) {
            if (i2 == 2) {
                if (PermissionUtils.sSimpleCallback4WriteSettings == null) {
                    return;
                }
                if (PermissionUtils.isGrantedWriteSettings()) {
                    PermissionUtils.sSimpleCallback4WriteSettings.onGranted();
                } else {
                    PermissionUtils.sSimpleCallback4WriteSettings.onDenied();
                }
                SimpleCallback unused = PermissionUtils.sSimpleCallback4WriteSettings = null;
                return;
            }
            if (i2 != 3 || PermissionUtils.sSimpleCallback4DrawOverlays == null) {
                return;
            }
            if (PermissionUtils.isGrantedDrawOverlays()) {
                PermissionUtils.sSimpleCallback4DrawOverlays.onGranted();
            } else {
                PermissionUtils.sSimpleCallback4DrawOverlays.onDenied();
            }
            SimpleCallback unused2 = PermissionUtils.sSimpleCallback4DrawOverlays = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void requestPermissions(final UtilsTransActivity utilsTransActivity) {
            if (PermissionUtils.sInstance.shouldRationale(utilsTransActivity, new Runnable() { // from class: com.blankj.utilcode.util.PermissionUtils.PermissionActivityImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    utilsTransActivity.requestPermissions((String[]) PermissionUtils.sInstance.mPermissionsRequest.toArray(new String[0]), 1);
                }
            })) {
                return;
            }
            utilsTransActivity.requestPermissions((String[]) PermissionUtils.sInstance.mPermissionsRequest.toArray(new String[0]), 1);
        }

        public static void start(final int i2) {
            UtilsTransActivity.start(new Utils.Consumer<Intent>() { // from class: com.blankj.utilcode.util.PermissionUtils.PermissionActivityImpl.1
                @Override // com.blankj.utilcode.util.Utils.Consumer
                public void accept(Intent intent) {
                    intent.putExtra("TYPE", i2);
                }
            }, INSTANCE);
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public boolean dispatchTouchEvent(@NonNull UtilsTransActivity utilsTransActivity, MotionEvent motionEvent) {
            utilsTransActivity.finish();
            return true;
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onActivityResult(@NonNull UtilsTransActivity utilsTransActivity, int i2, int i3, Intent intent) {
            utilsTransActivity.finish();
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onCreated(@NonNull final UtilsTransActivity utilsTransActivity, @Nullable Bundle bundle) {
            utilsTransActivity.getWindow().addFlags(262160);
            int intExtra = utilsTransActivity.getIntent().getIntExtra("TYPE", -1);
            if (intExtra != 1) {
                if (intExtra == 2) {
                    currentRequestCode = 2;
                    PermissionUtils.startWriteSettingsActivity(utilsTransActivity, 2);
                    return;
                } else if (intExtra == 3) {
                    currentRequestCode = 3;
                    PermissionUtils.startOverlayPermissionActivity(utilsTransActivity, 3);
                    return;
                } else {
                    utilsTransActivity.finish();
                    Log.e("PermissionUtils", "type is wrong.");
                    return;
                }
            }
            if (PermissionUtils.sInstance == null) {
                Log.e("PermissionUtils", "sInstance is null.");
                utilsTransActivity.finish();
                return;
            }
            if (PermissionUtils.sInstance.mPermissionsRequest == null) {
                Log.e("PermissionUtils", "mPermissionsRequest is null.");
                utilsTransActivity.finish();
                return;
            }
            if (PermissionUtils.sInstance.mPermissionsRequest.size() <= 0) {
                Log.e("PermissionUtils", "mPermissionsRequest's size is no more than 0.");
                utilsTransActivity.finish();
                return;
            }
            if (PermissionUtils.sInstance.mThemeCallback != null) {
                PermissionUtils.sInstance.mThemeCallback.onActivityCreate(utilsTransActivity);
            }
            if (PermissionUtils.sInstance.mOnExplainListener == null) {
                requestPermissions(utilsTransActivity);
            } else {
                PermissionUtils.sInstance.mOnExplainListener.explain(utilsTransActivity, PermissionUtils.sInstance.mPermissionsRequest, new OnExplainListener.ShouldRequest() { // from class: com.blankj.utilcode.util.PermissionUtils.PermissionActivityImpl.2
                    @Override // com.blankj.utilcode.util.PermissionUtils.OnExplainListener.ShouldRequest
                    public void start(boolean z2) {
                        if (z2) {
                            PermissionActivityImpl.this.requestPermissions(utilsTransActivity);
                        } else {
                            utilsTransActivity.finish();
                        }
                    }
                });
                PermissionUtils.sInstance.mOnExplainListener = null;
            }
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onDestroy(@NonNull UtilsTransActivity utilsTransActivity) {
            int i2 = currentRequestCode;
            if (i2 != -1) {
                checkRequestCallback(i2);
                currentRequestCode = -1;
            }
            super.onDestroy(utilsTransActivity);
        }

        @Override // com.blankj.utilcode.util.UtilsTransActivity.TransActivityDelegate
        public void onRequestPermissionsResult(@NonNull UtilsTransActivity utilsTransActivity, int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
            utilsTransActivity.finish();
            if (PermissionUtils.sInstance == null || PermissionUtils.sInstance.mPermissionsRequest == null) {
                return;
            }
            PermissionUtils.sInstance.onRequestPermissionsResult(utilsTransActivity);
        }
    }

    public interface SimpleCallback {
        void onDenied();

        void onGranted();
    }

    public interface SingleCallback {
        void callback(boolean z2, @NonNull List<String> list, @NonNull List<String> list2, @NonNull List<String> list3);
    }

    public interface ThemeCallback {
        void onActivityCreate(@NonNull Activity activity);
    }

    private PermissionUtils(String... strArr) {
        this.mPermissionsParam = strArr;
        sInstance = this;
    }

    public static List<String> getPermissions() {
        return getPermissions(Utils.getApp().getPackageName());
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

    private static Pair<List<String>, List<String>> getRequestAndDeniedPermissions(String... strArr) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<String> permissions = getPermissions();
        for (String str : strArr) {
            boolean z2 = false;
            for (String str2 : PermissionConstants.getPermissions(str)) {
                if (permissions.contains(str2)) {
                    arrayList.add(str2);
                    z2 = true;
                }
            }
            if (!z2) {
                arrayList2.add(str);
                Log.e("PermissionUtils", "U should add the permission of " + str + " in manifest.");
            }
        }
        return Pair.create(arrayList, arrayList2);
    }

    public static boolean isGranted(String... strArr) {
        Pair<List<String>, List<String>> requestAndDeniedPermissions = getRequestAndDeniedPermissions(strArr);
        if (!((List) requestAndDeniedPermissions.second).isEmpty()) {
            return false;
        }
        Iterator it = ((List) requestAndDeniedPermissions.first).iterator();
        while (it.hasNext()) {
            if (!isGranted((String) it.next())) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = 23)
    public static boolean isGrantedDrawOverlays() {
        return Settings.canDrawOverlays(Utils.getApp());
    }

    @RequiresApi(api = 23)
    public static boolean isGrantedWriteSettings() {
        return Settings.System.canWrite(Utils.getApp());
    }

    public static void launchAppDetailsSettings() {
        Intent launchAppDetailsSettingsIntent = UtilsBridge.getLaunchAppDetailsSettingsIntent(Utils.getApp().getPackageName(), true);
        if (UtilsBridge.isIntentAvailable(launchAppDetailsSettingsIntent)) {
            Utils.getApp().startActivity(launchAppDetailsSettingsIntent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestPermissionsResult(Activity activity) {
        getPermissionsStatus(activity);
        requestCallback();
    }

    public static PermissionUtils permission(String... strArr) {
        return new PermissionUtils(strArr);
    }

    public static PermissionUtils permissionGroup(String... strArr) {
        return permission(strArr);
    }

    private void rationalInner(final UtilsTransActivity utilsTransActivity, final Runnable runnable) {
        getPermissionsStatus(utilsTransActivity);
        this.mOnRationaleListener.rationale(utilsTransActivity, new OnRationaleListener.ShouldRequest() { // from class: com.blankj.utilcode.util.PermissionUtils.1
            @Override // com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest
            public void again(boolean z2) {
                if (!z2) {
                    utilsTransActivity.finish();
                    PermissionUtils.this.requestCallback();
                    return;
                }
                PermissionUtils.this.mPermissionsDenied = new ArrayList();
                PermissionUtils.this.mPermissionsDeniedForever = new ArrayList();
                runnable.run();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestCallback() {
        SingleCallback singleCallback = this.mSingleCallback;
        if (singleCallback != null) {
            singleCallback.callback(this.mPermissionsDenied.isEmpty(), this.mPermissionsGranted, this.mPermissionsDeniedForever, this.mPermissionsDenied);
            this.mSingleCallback = null;
        }
        if (this.mSimpleCallback != null) {
            if (this.mPermissionsDenied.isEmpty()) {
                this.mSimpleCallback.onGranted();
            } else {
                this.mSimpleCallback.onDenied();
            }
            this.mSimpleCallback = null;
        }
        if (this.mFullCallback != null) {
            if (this.mPermissionsRequest.size() == 0 || this.mPermissionsGranted.size() > 0) {
                this.mFullCallback.onGranted(this.mPermissionsGranted);
            }
            if (!this.mPermissionsDenied.isEmpty()) {
                this.mFullCallback.onDenied(this.mPermissionsDeniedForever, this.mPermissionsDenied);
            }
            this.mFullCallback = null;
        }
        this.mOnRationaleListener = null;
        this.mThemeCallback = null;
    }

    @RequiresApi(api = 23)
    public static void requestDrawOverlays(SimpleCallback simpleCallback) {
        if (!isGrantedDrawOverlays()) {
            sSimpleCallback4DrawOverlays = simpleCallback;
            PermissionActivityImpl.start(3);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    @RequiresApi(api = 23)
    public static void requestWriteSettings(SimpleCallback simpleCallback) {
        if (!isGrantedWriteSettings()) {
            sSimpleCallback4WriteSettings = simpleCallback;
            PermissionActivityImpl.start(2);
        } else if (simpleCallback != null) {
            simpleCallback.onGranted();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(api = 23)
    public boolean shouldRationale(UtilsTransActivity utilsTransActivity, Runnable runnable) {
        boolean z2 = false;
        if (this.mOnRationaleListener != null) {
            Iterator<String> it = this.mPermissionsRequest.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (utilsTransActivity.shouldShowRequestPermissionRationale(it.next())) {
                    rationalInner(utilsTransActivity, runnable);
                    z2 = true;
                    break;
                }
            }
            this.mOnRationaleListener = null;
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public static void startOverlayPermissionActivity(Activity activity, int i2) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (UtilsBridge.isIntentAvailable(intent)) {
            activity.startActivityForResult(intent, i2);
        } else {
            launchAppDetailsSettings();
        }
    }

    @RequiresApi(api = 23)
    private void startPermissionActivity() {
        PermissionActivityImpl.start(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @TargetApi(23)
    public static void startWriteSettingsActivity(Activity activity, int i2) {
        Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
        intent.setData(Uri.parse("package:" + Utils.getApp().getPackageName()));
        if (UtilsBridge.isIntentAvailable(intent)) {
            activity.startActivityForResult(intent, i2);
        } else {
            launchAppDetailsSettings();
        }
    }

    public PermissionUtils callback(SingleCallback singleCallback) {
        this.mSingleCallback = singleCallback;
        return this;
    }

    public PermissionUtils explain(OnExplainListener onExplainListener) {
        this.mOnExplainListener = onExplainListener;
        return this;
    }

    public PermissionUtils rationale(OnRationaleListener onRationaleListener) {
        this.mOnRationaleListener = onRationaleListener;
        return this;
    }

    public void request() {
        String[] strArr = this.mPermissionsParam;
        if (strArr == null || strArr.length <= 0) {
            Log.w("PermissionUtils", "No permissions to request.");
            return;
        }
        this.mPermissions = new LinkedHashSet();
        this.mPermissionsRequest = new ArrayList();
        this.mPermissionsGranted = new ArrayList();
        this.mPermissionsDenied = new ArrayList();
        this.mPermissionsDeniedForever = new ArrayList();
        Pair<List<String>, List<String>> requestAndDeniedPermissions = getRequestAndDeniedPermissions(this.mPermissionsParam);
        this.mPermissions.addAll((Collection) requestAndDeniedPermissions.first);
        this.mPermissionsDenied.addAll((Collection) requestAndDeniedPermissions.second);
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

    public PermissionUtils theme(ThemeCallback themeCallback) {
        this.mThemeCallback = themeCallback;
        return this;
    }

    public static List<String> getPermissions(String str) {
        try {
            String[] strArr = Utils.getApp().getPackageManager().getPackageInfo(str, 4096).requestedPermissions;
            return strArr == null ? Collections.emptyList() : Arrays.asList(strArr);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return Collections.emptyList();
        }
    }

    public PermissionUtils callback(SimpleCallback simpleCallback) {
        this.mSimpleCallback = simpleCallback;
        return this;
    }

    public PermissionUtils callback(FullCallback fullCallback) {
        this.mFullCallback = fullCallback;
        return this;
    }

    private static boolean isGranted(String str) {
        return ContextCompat.checkSelfPermission(Utils.getApp(), str) == 0;
    }
}
