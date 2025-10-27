package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

/* loaded from: classes3.dex */
final class zad extends DialogRedirect {
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ Intent zaos;

    public zad(Intent intent, Activity activity, int i2) {
        this.zaos = intent;
        this.val$activity = activity;
        this.val$requestCode = i2;
    }

    @Override // com.google.android.gms.common.internal.DialogRedirect
    public final void redirect() {
        Intent intent = this.zaos;
        if (intent != null) {
            this.val$activity.startActivityForResult(intent, this.val$requestCode);
        }
    }
}
