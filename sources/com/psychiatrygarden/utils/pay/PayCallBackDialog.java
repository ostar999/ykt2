package com.psychiatrygarden.utils.pay;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import com.psychiatrygarden.utils.pay.PayCallBackDialog;
import com.psychiatrygarden.widget.CustomDialog;

/* loaded from: classes6.dex */
public class PayCallBackDialog {
    private static CustomDialog payCallBackDialog;

    public static void dismiss() {
        CustomDialog customDialog = payCallBackDialog;
        if (customDialog != null) {
            customDialog.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showPayCallBackDialog$0(View view) {
        payCallBackDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showPayCallBackDialog$2(View view) {
        payCallBackDialog.dismissNoAnimaltion();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showPayCallBackDialog$3(View view) {
        payCallBackDialog.dismissNoAnimaltion();
    }

    public static void showPayCallBackDialog(Context context, String message, View.OnClickListener clickListener) {
        if (payCallBackDialog == null) {
            CustomDialog customDialog = new CustomDialog(context, 1);
            payCallBackDialog = customDialog;
            customDialog.setCancelable(false);
            payCallBackDialog.isOutTouchDismiss(false);
            payCallBackDialog.setMessage(message);
            if (clickListener != null) {
                payCallBackDialog.setPositiveBtn("确定", clickListener);
            }
            payCallBackDialog.setNegativeBtn("取消", new View.OnClickListener() { // from class: n1.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PayCallBackDialog.lambda$showPayCallBackDialog$0(view);
                }
            });
        }
        if (!payCallBackDialog.isShowing()) {
            payCallBackDialog.show();
        }
        payCallBackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: n1.b
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PayCallBackDialog.payCallBackDialog = null;
            }
        });
    }

    public static void showPayCallBackDialog(Context context, String message) {
        if (payCallBackDialog == null) {
            CustomDialog customDialog = new CustomDialog(context, 1);
            payCallBackDialog = customDialog;
            customDialog.setCancelable(false);
            payCallBackDialog.isOutTouchDismiss(false);
            payCallBackDialog.setMessage(message);
            payCallBackDialog.setPositiveBtn("确定", new View.OnClickListener() { // from class: n1.c
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PayCallBackDialog.lambda$showPayCallBackDialog$2(view);
                }
            });
            payCallBackDialog.setNegativeBtn("取消", new View.OnClickListener() { // from class: n1.d
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PayCallBackDialog.lambda$showPayCallBackDialog$3(view);
                }
            });
        }
        if (!payCallBackDialog.isShowing()) {
            payCallBackDialog.show();
        }
        payCallBackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: n1.e
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PayCallBackDialog.payCallBackDialog = null;
            }
        });
    }
}
