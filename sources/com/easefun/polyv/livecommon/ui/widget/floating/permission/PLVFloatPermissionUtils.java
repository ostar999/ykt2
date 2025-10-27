package com.easefun.polyv.livecommon.ui.widget.floating.permission;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.HuaweiUtils;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.MeizuUtils;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.MiuiUtils;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.OppoUtils;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.QikuUtils;
import com.easefun.polyv.livecommon.ui.widget.floating.permission.rom.RomUtils;

/* loaded from: classes3.dex */
public class PLVFloatPermissionUtils {
    public static final int REQUEST_CODE_MANAGE_OVERLAY_PERMISSION = 1010;
    private static String TAG = "FloatPermissionUtils";

    public interface IPLVOverlayPermissionListener {
        void onResult(boolean isGrant);
    }

    public static class PLVPermissionFragment extends Fragment {
        private static IPLVOverlayPermissionListener permissionListener;

        public static void requestPermission(Activity activity, IPLVOverlayPermissionListener listener) {
            permissionListener = listener;
            FragmentManager fragmentManager = activity.getFragmentManager();
            if (fragmentManager.findFragmentByTag(activity.getLocalClassName()) == null) {
                fragmentManager.beginTransaction().add(new PLVPermissionFragment(), activity.getLocalClassName()).commitAllowingStateLoss();
            }
        }

        @Override // android.app.Fragment
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            PLVFloatPermissionUtils.requestPermission(this);
        }

        @Override // android.app.Fragment
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == 1010) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.floating.permission.PLVFloatPermissionUtils.PLVPermissionFragment.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PLVPermissionFragment.permissionListener != null) {
                            PLVPermissionFragment.permissionListener.onResult(PLVFloatPermissionUtils.checkPermission(PLVPermissionFragment.this.getActivity()));
                            IPLVOverlayPermissionListener unused = PLVPermissionFragment.permissionListener = null;
                        }
                        PLVPermissionFragment.this.getFragmentManager().beginTransaction().remove(PLVPermissionFragment.this).commitAllowingStateLoss();
                    }
                }, 500L);
            }
        }
    }

    public static boolean checkPermission(Activity activity) {
        return commonROMPermissionCheck(activity);
    }

    private static void commonROMPermissionApply(Fragment fragment) {
        if (RomUtils.checkIsMeizuRom()) {
            MeizuUtils.applyPermission(fragment);
        } else {
            commonROMPermissionApplyInternal(fragment);
        }
    }

    public static void commonROMPermissionApplyInternal(Fragment fragment) {
        fragment.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + fragment.getActivity().getPackageName())), 1010);
    }

    private static boolean commonROMPermissionCheck(Context context) {
        return RomUtils.checkIsMeizuRom() ? meizuPermissionCheck(context) : Settings.canDrawOverlays(context);
    }

    private static boolean huaweiPermissionCheck(Context context) {
        return HuaweiUtils.checkFloatWindowPermission(context);
    }

    private static boolean meizuPermissionCheck(Context context) {
        return MeizuUtils.checkFloatWindowPermission(context);
    }

    private static boolean miuiPermissionCheck(Context context) {
        return MiuiUtils.checkFloatWindowPermission(context);
    }

    private static boolean oppoROMPermissionCheck(Context context) {
        return OppoUtils.checkFloatWindowPermission(context);
    }

    private static boolean qikuPermissionCheck(Context context) {
        return QikuUtils.checkFloatWindowPermission(context);
    }

    public static void requestPermission(Activity activity, IPLVOverlayPermissionListener listener) {
        PLVPermissionFragment.requestPermission(activity, listener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void requestPermission(Fragment fragment) {
        commonROMPermissionApply(fragment);
    }
}
