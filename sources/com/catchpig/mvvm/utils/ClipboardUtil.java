package com.catchpig.mvvm.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.huawei.hms.support.api.entity.core.CommonCode;

/* loaded from: classes2.dex */
public class ClipboardUtil {
    private ClipboardUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void clearFirstClipboard(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            return;
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""));
        if (clipboardManager.hasPrimaryClip()) {
            clipboardManager.getPrimaryClip().getItemAt(0).getText();
        }
    }

    public static void copyIntent(Context context, Intent intent) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newIntent(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, intent));
    }

    public static void copyText2Clipboard(Context context, CharSequence charSequence) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("text", charSequence));
    }

    public static void copyUri(Context context, Uri uri) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newUri(context.getContentResolver(), "uri", uri));
    }

    public static Intent getIntent(Context context) {
        ClipData primaryClip = ((ClipboardManager) context.getSystemService("clipboard")).getPrimaryClip();
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        return primaryClip.getItemAt(0).getIntent();
    }

    public static String getTextFromClipboard(Context context) {
        ClipData primaryClip = ((ClipboardManager) context.getSystemService("clipboard")).getPrimaryClip();
        return (primaryClip == null || primaryClip.getItemCount() <= 0) ? "" : primaryClip.getItemAt(0).coerceToText(context).toString();
    }

    public static Uri getUri(Context context) {
        ClipData primaryClip = ((ClipboardManager) context.getSystemService("clipboard")).getPrimaryClip();
        if (primaryClip == null || primaryClip.getItemCount() <= 0) {
            return null;
        }
        return primaryClip.getItemAt(0).getUri();
    }
}
