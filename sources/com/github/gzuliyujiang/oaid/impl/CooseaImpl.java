package com.github.gzuliyujiang.oaid.impl;

import android.app.KeyguardManager;
import android.content.Context;
import com.github.gzuliyujiang.oaid.IGetter;
import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDException;
import com.github.gzuliyujiang.oaid.OAIDLog;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/* loaded from: classes3.dex */
public class CooseaImpl implements IOAID {
    private final Context context;
    private final KeyguardManager keyguardManager;

    public CooseaImpl(Context context) {
        this.context = context;
        this.keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public void doGet(IGetter iGetter) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.context == null || iGetter == null) {
            return;
        }
        KeyguardManager keyguardManager = this.keyguardManager;
        if (keyguardManager == null) {
            iGetter.onOAIDGetError(new OAIDException("KeyguardManager not found"));
            return;
        }
        try {
            Object objInvoke = keyguardManager.getClass().getDeclaredMethod("obtainOaid", new Class[0]).invoke(this.keyguardManager, new Object[0]);
            if (objInvoke == null) {
                throw new OAIDException("OAID obtain failed");
            }
            String string = objInvoke.toString();
            OAIDLog.print("OAID obtain success: " + string);
            iGetter.onOAIDGetComplete(string);
        } catch (Exception e2) {
            OAIDLog.print(e2);
        }
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public boolean supported() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        KeyguardManager keyguardManager;
        if (this.context == null || (keyguardManager = this.keyguardManager) == null) {
            return false;
        }
        try {
            Object objInvoke = keyguardManager.getClass().getDeclaredMethod("isSupported", new Class[0]).invoke(this.keyguardManager, new Object[0]);
            Objects.requireNonNull(objInvoke);
            return ((Boolean) objInvoke).booleanValue();
        } catch (Exception e2) {
            OAIDLog.print(e2);
            return false;
        }
    }
}
