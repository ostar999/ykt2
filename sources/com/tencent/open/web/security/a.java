package com.tencent.open.web.security;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import com.tencent.open.log.SLog;

/* loaded from: classes6.dex */
public class a extends InputConnectionWrapper {

    /* renamed from: a, reason: collision with root package name */
    public static String f20697a = null;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f20698b = false;

    /* renamed from: c, reason: collision with root package name */
    public static boolean f20699c = false;

    public a(InputConnection inputConnection, boolean z2) {
        super(inputConnection, z2);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i2) {
        f20699c = true;
        f20697a = charSequence.toString();
        SLog.v("openSDK_LOG.CaptureInputConnection", "-->commitText: " + charSequence.toString());
        return super.commitText(charSequence, i2);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            SLog.i("openSDK_LOG.CaptureInputConnection", "sendKeyEvent");
            f20697a = String.valueOf((char) keyEvent.getUnicodeChar());
            f20699c = true;
            SLog.d("openSDK_LOG.CaptureInputConnection", "s: " + f20697a);
        }
        SLog.d("openSDK_LOG.CaptureInputConnection", "-->sendKeyEvent: " + f20697a);
        return super.sendKeyEvent(keyEvent);
    }

    @Override // android.view.inputmethod.InputConnectionWrapper, android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i2) {
        f20699c = true;
        f20697a = charSequence.toString();
        SLog.v("openSDK_LOG.CaptureInputConnection", "-->setComposingText: " + charSequence.toString());
        return super.setComposingText(charSequence, i2);
    }
}
