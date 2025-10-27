package com.easefun.polyv.livecommon.module.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVDialogFactory {
    public static Dialog createConfirmDialog(Context context, String message, String positiveMsg, DialogInterface.OnClickListener onClickListener) {
        return new AlertDialog.Builder(context).setMessage(message).setPositiveButton(positiveMsg, onClickListener).setNegativeButton(R.string.plv_common_dialog_click_wrong, new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.utils.PLVDialogFactory.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
    }
}
