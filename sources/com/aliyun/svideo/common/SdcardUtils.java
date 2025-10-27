package com.aliyun.svideo.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import com.aliyun.svideo.common.utils.FileUtils;
import com.aliyun.svideo.common.utils.PermissionUtils;

/* loaded from: classes2.dex */
public class SdcardUtils {
    private static final String TAG = "SdcardUtils";

    public static void checkAvailableSize(Context context, int i2) {
        if (PermissionUtils.checkPermissionsGroup(context, PermissionUtils.PERMISSION_STORAGE)) {
            long sdcardAvailableSize = (FileUtils.getSdcardAvailableSize() / 1024) / 1024;
            Log.e(TAG, "log_common_SdcardUtils_availableSize : " + sdcardAvailableSize);
            if (sdcardAvailableSize < i2) {
                showAlertDialog(context);
            }
        }
    }

    private static void showAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.alivc_common_note));
        builder.setMessage(context.getResources().getString(R.string.alivc_common_device_memory_not_enough));
        builder.setPositiveButton(context.getResources().getString(R.string.alivc_common_confirm), new DialogInterface.OnClickListener() { // from class: com.aliyun.svideo.common.SdcardUtils.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
