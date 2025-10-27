package com.alipay.sdk.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f3413a = true;

    private static AlertDialog.Builder a(Context context, String str, DialogInterface.OnClickListener onClickListener, String str2, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (f3413a) {
            if (!TextUtils.isEmpty(str2) && onClickListener2 != null) {
                builder.setPositiveButton(str2, onClickListener2);
            }
            if (!TextUtils.isEmpty(str) && onClickListener != null) {
                builder.setNegativeButton(str, onClickListener);
            }
        } else {
            if (!TextUtils.isEmpty(str) && onClickListener != null) {
                builder.setPositiveButton(str, onClickListener);
            }
            if (!TextUtils.isEmpty(str2) && onClickListener2 != null) {
                builder.setNegativeButton(str2, onClickListener2);
            }
        }
        return builder;
    }

    public static Dialog a(Context context, String str, String str2, String str3, DialogInterface.OnClickListener onClickListener, String str4, DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (f3413a) {
            if (!TextUtils.isEmpty(str4)) {
                builder.setPositiveButton(str4, onClickListener2);
            }
            if (!TextUtils.isEmpty(str3)) {
                builder.setNegativeButton(str3, onClickListener);
            }
        } else {
            if (!TextUtils.isEmpty(str3)) {
                builder.setPositiveButton(str3, onClickListener);
            }
            if (!TextUtils.isEmpty(str4)) {
                builder.setNegativeButton(str4, onClickListener2);
            }
        }
        builder.setTitle(str);
        builder.setMessage(str2);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setCanceledOnTouchOutside(false);
        alertDialogCreate.setOnKeyListener(new f());
        try {
            alertDialogCreate.show();
        } catch (Throwable unused) {
        }
        return alertDialogCreate;
    }
}
