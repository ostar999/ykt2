package com.unity3d.player;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/* loaded from: classes6.dex */
public class UnityPermissions {
    private static final String SKIP_DIALOG_METADATA_NAME = "unityplayer.SkipPermissionsDialog";

    public static class ModalWaitForPermissionResponse implements IPermissionRequestCallbacks {
        private boolean haveResponse = false;

        @Override // com.unity3d.player.IPermissionRequestCallbacks
        public synchronized void onPermissionDenied(String str) {
            this.haveResponse = true;
            notify();
        }

        @Override // com.unity3d.player.IPermissionRequestCallbacks
        public synchronized void onPermissionDeniedAndDontAskAgain(String str) {
            this.haveResponse = true;
            notify();
        }

        @Override // com.unity3d.player.IPermissionRequestCallbacks
        public synchronized void onPermissionGranted(String str) {
            this.haveResponse = true;
            notify();
        }

        public synchronized void waitForResponse() {
            try {
                if (this.haveResponse) {
                    return;
                }
                wait();
            } catch (InterruptedException unused) {
            }
        }
    }

    private static boolean checkInfoForMetadata(PackageItemInfo packageItemInfo) {
        try {
            return packageItemInfo.metaData.getBoolean(SKIP_DIALOG_METADATA_NAME);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean hasUserAuthorizedPermission(Activity activity, String str) {
        return activity.checkCallingOrSelfPermission(str) == 0;
    }

    public static void requestUserPermissions(Activity activity, String[] strArr, IPermissionRequestCallbacks iPermissionRequestCallbacks) {
        if (activity == null || strArr == null) {
            return;
        }
        if (!PlatformSupport.MARSHMALLOW_SUPPORT) {
            if (iPermissionRequestCallbacks != null) {
                for (String str : strArr) {
                    iPermissionRequestCallbacks.onPermissionDeniedAndDontAskAgain(str);
                }
                return;
            }
            return;
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag("96489") == null) {
            g gVar = new g(activity, iPermissionRequestCallbacks);
            Bundle bundle = new Bundle();
            bundle.putStringArray("PermissionNames", strArr);
            gVar.setArguments(bundle);
            FragmentTransaction fragmentTransactionBeginTransaction = fragmentManager.beginTransaction();
            fragmentTransactionBeginTransaction.add(0, gVar, "96489");
            fragmentTransactionBeginTransaction.commit();
        }
    }

    public static boolean skipPermissionsDialog(Activity activity) throws PackageManager.NameNotFoundException {
        ActivityInfo activityInfo;
        ApplicationInfo applicationInfo;
        if (!PlatformSupport.MARSHMALLOW_SUPPORT) {
            return false;
        }
        try {
            PackageManager packageManager = activity.getPackageManager();
            activityInfo = packageManager.getActivityInfo(activity.getComponentName(), 128);
            applicationInfo = packageManager.getApplicationInfo(activity.getPackageName(), 128);
        } catch (Exception unused) {
        }
        if (checkInfoForMetadata(activityInfo)) {
            return true;
        }
        return checkInfoForMetadata(applicationInfo);
    }
}
