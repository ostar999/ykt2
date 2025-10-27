package com.google.android.gms.common.internal;

import android.content.Intent;
import androidx.fragment.app.Fragment;

/* loaded from: classes3.dex */
final class zac extends DialogRedirect {
    private final /* synthetic */ Fragment val$fragment;
    private final /* synthetic */ int val$requestCode;
    private final /* synthetic */ Intent zaos;

    public zac(Intent intent, Fragment fragment, int i2) {
        this.zaos = intent;
        this.val$fragment = fragment;
        this.val$requestCode = i2;
    }

    @Override // com.google.android.gms.common.internal.DialogRedirect
    public final void redirect() {
        Intent intent = this.zaos;
        if (intent != null) {
            this.val$fragment.startActivityForResult(intent, this.val$requestCode);
        }
    }
}
