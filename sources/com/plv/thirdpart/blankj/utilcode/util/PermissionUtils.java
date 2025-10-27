package com.plv.thirdpart.blankj.utilcode.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public final class PermissionUtils {
    private static OnPermissionListener mOnPermissionListener = null;
    private static int mRequestCode = -1;

    public interface OnPermissionListener {
        void onPermissionDenied(String[] strArr);

        void onPermissionGranted();
    }

    public static abstract class RationaleHandler {
        private Context context;
        private String[] permissions;
        private int requestCode;

        @TargetApi(23)
        public void requestPermissionsAgain() {
            ((Activity) this.context).requestPermissions(this.permissions, this.requestCode);
        }

        public abstract void showRationale();

        public void showRationale(Context context, int i2, String[] strArr) {
            this.context = context;
            this.requestCode = i2;
            this.permissions = strArr;
            showRationale();
        }
    }

    private static String[] getDeniedPermissions(Context context, String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context, str) == -1) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static boolean hasAlwaysDeniedPermission(Context context, String... strArr) {
        for (String str : strArr) {
            if (!shouldShowRequestPermissionRationale(context, str)) {
                return true;
            }
        }
        return false;
    }

    public static void onRequestPermissionsResult(Activity activity, int i2, String[] strArr, int[] iArr) {
        int i3 = mRequestCode;
        if (i3 == -1 || i2 != i3 || mOnPermissionListener == null) {
            return;
        }
        String[] deniedPermissions = getDeniedPermissions(activity, strArr);
        if (deniedPermissions.length > 0) {
            mOnPermissionListener.onPermissionDenied(deniedPermissions);
        } else {
            mOnPermissionListener.onPermissionGranted();
        }
    }

    @TargetApi(23)
    public static void requestPermissions(Context context, int i2, String[] strArr, OnPermissionListener onPermissionListener) {
        requestPermissions(context, i2, strArr, onPermissionListener, null);
    }

    private static boolean shouldShowRequestPermissionRationale(Context context, String... strArr) {
        for (String str : strArr) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, str)) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(23)
    public static void requestPermissions(Context context, int i2, String[] strArr, OnPermissionListener onPermissionListener, RationaleHandler rationaleHandler) {
        if (!(context instanceof Activity)) {
            PLVCommonLog.exception(new RuntimeException("Context must be an Activity"));
            return;
        }
        mRequestCode = i2;
        mOnPermissionListener = onPermissionListener;
        String[] deniedPermissions = getDeniedPermissions(context, strArr);
        if (deniedPermissions.length <= 0) {
            OnPermissionListener onPermissionListener2 = mOnPermissionListener;
            if (onPermissionListener2 != null) {
                onPermissionListener2.onPermissionGranted();
                return;
            }
            return;
        }
        if (!shouldShowRequestPermissionRationale(context, deniedPermissions) || rationaleHandler == null) {
            ((Activity) context).requestPermissions(deniedPermissions, i2);
        } else {
            rationaleHandler.showRationale(context, i2, deniedPermissions);
        }
    }
}
