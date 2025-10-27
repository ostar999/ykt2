package com.alipay.sdk.widget;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* loaded from: classes2.dex */
final class f implements DialogInterface.OnKeyListener {
    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i2, KeyEvent keyEvent) {
        return i2 == 4;
    }
}
