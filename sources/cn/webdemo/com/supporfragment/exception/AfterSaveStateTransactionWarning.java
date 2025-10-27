package cn.webdemo.com.supporfragment.exception;

import android.util.Log;

/* loaded from: classes.dex */
public class AfterSaveStateTransactionWarning extends RuntimeException {
    public AfterSaveStateTransactionWarning(String str) {
        super("Warning: Perform this " + str + " action after onSaveInstanceState!");
        Log.w("Fragmentation", getMessage());
    }
}
