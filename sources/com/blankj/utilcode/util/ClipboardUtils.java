package com.blankj.utilcode.util;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;

/* loaded from: classes2.dex */
public final class ClipboardUtils {
    private ClipboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void addChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        ((ClipboardManager) Utils.getApp().getSystemService("clipboard")).addPrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    public static void clear() {
        ((ClipboardManager) Utils.getApp().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, ""));
    }

    public static void copyText(CharSequence charSequence) {
        ((ClipboardManager) Utils.getApp().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(Utils.getApp().getPackageName(), charSequence));
    }

    public static CharSequence getLabel() {
        CharSequence label;
        ClipDescription primaryClipDescription = ((ClipboardManager) Utils.getApp().getSystemService("clipboard")).getPrimaryClipDescription();
        return (primaryClipDescription == null || (label = primaryClipDescription.getLabel()) == null) ? "" : label;
    }

    public static CharSequence getText() {
        CharSequence charSequenceCoerceToText;
        ClipData primaryClip = ((ClipboardManager) Utils.getApp().getSystemService("clipboard")).getPrimaryClip();
        return (primaryClip == null || primaryClip.getItemCount() <= 0 || (charSequenceCoerceToText = primaryClip.getItemAt(0).coerceToText(Utils.getApp())) == null) ? "" : charSequenceCoerceToText;
    }

    public static void removeChangedListener(ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        ((ClipboardManager) Utils.getApp().getSystemService("clipboard")).removePrimaryClipChangedListener(onPrimaryClipChangedListener);
    }

    public static void copyText(CharSequence charSequence, CharSequence charSequence2) {
        ((ClipboardManager) Utils.getApp().getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(charSequence, charSequence2));
    }
}
