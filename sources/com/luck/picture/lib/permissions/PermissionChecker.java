package com.luck.picture.lib.permissions;

import android.content.Context;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.hjq.permissions.Permission;
import com.luck.picture.lib.basic.PictureCommonFragment;
import com.luck.picture.lib.utils.ActivityCompatHelper;
import com.luck.picture.lib.utils.SdkVersionUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PermissionChecker {
    private static final int REQUEST_CODE = 10086;
    private static PermissionChecker mInstance;

    private PermissionChecker() {
    }

    public static boolean checkSelfPermission(Context context, String[] strArr) {
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(context.getApplicationContext(), str) != 0) {
                return false;
            }
        }
        return true;
    }

    public static PermissionChecker getInstance() {
        if (mInstance == null) {
            synchronized (PermissionChecker.class) {
                if (mInstance == null) {
                    mInstance = new PermissionChecker();
                }
            }
        }
        return mInstance;
    }

    public static boolean isCheckCamera(Context context) {
        return checkSelfPermission(context, new String[]{Permission.CAMERA});
    }

    public static boolean isCheckReadStorage(Context context) {
        return SdkVersionUtils.isR() ? Environment.isExternalStorageManager() : checkSelfPermission(context, new String[]{Permission.READ_EXTERNAL_STORAGE});
    }

    public static boolean isCheckWriteStorage(Context context) {
        return SdkVersionUtils.isR() ? Environment.isExternalStorageManager() : checkSelfPermission(context, new String[]{Permission.WRITE_EXTERNAL_STORAGE});
    }

    public void onRequestPermissionsResult(int[] iArr, PermissionResultCallback permissionResultCallback) {
        if (PermissionUtil.isAllGranted(iArr)) {
            permissionResultCallback.onGranted();
        } else {
            permissionResultCallback.onDenied();
        }
    }

    public void requestPermissions(Fragment fragment, String[] strArr, PermissionResultCallback permissionResultCallback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(strArr);
        requestPermissions(fragment, arrayList, 10086, permissionResultCallback);
    }

    public void requestPermissions(Fragment fragment, List<String[]> list, PermissionResultCallback permissionResultCallback) {
        requestPermissions(fragment, list, 10086, permissionResultCallback);
    }

    private void requestPermissions(Fragment fragment, List<String[]> list, int i2, PermissionResultCallback permissionResultCallback) {
        if (!ActivityCompatHelper.isDestroy(fragment.getActivity()) && (fragment instanceof PictureCommonFragment)) {
            FragmentActivity activity = fragment.getActivity();
            ArrayList arrayList = new ArrayList();
            for (String[] strArr : list) {
                for (String str : strArr) {
                    if (ContextCompat.checkSelfPermission(activity, str) != 0) {
                        arrayList.add(str);
                    }
                }
            }
            if (arrayList.size() <= 0) {
                if (permissionResultCallback != null) {
                    permissionResultCallback.onGranted();
                }
            } else {
                ((PictureCommonFragment) fragment).setPermissionsResultAction(permissionResultCallback);
                String[] strArr2 = new String[arrayList.size()];
                arrayList.toArray(strArr2);
                fragment.requestPermissions(strArr2, i2);
                ActivityCompat.requestPermissions(activity, strArr2, i2);
            }
        }
    }
}
