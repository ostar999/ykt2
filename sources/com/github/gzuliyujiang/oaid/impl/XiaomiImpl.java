package com.github.gzuliyujiang.oaid.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import com.github.gzuliyujiang.oaid.IGetter;
import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDException;
import com.github.gzuliyujiang.oaid.OAIDLog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
class XiaomiImpl implements IOAID {
    private final Context context;
    private Class<?> idProviderClass;
    private Object idProviderImpl;

    @SuppressLint({"PrivateApi"})
    public XiaomiImpl(Context context) throws ClassNotFoundException {
        this.context = context;
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            this.idProviderClass = cls;
            this.idProviderImpl = cls.newInstance();
        } catch (Exception e2) {
            OAIDLog.print(e2);
        }
    }

    private String getOAID() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return (String) this.idProviderClass.getMethod("getOAID", Context.class).invoke(this.idProviderImpl, this.context);
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public void doGet(IGetter iGetter) {
        if (this.context == null || iGetter == null) {
            return;
        }
        if (this.idProviderClass == null || this.idProviderImpl == null) {
            iGetter.onOAIDGetError(new OAIDException("Xiaomi IdProvider not exists"));
            return;
        }
        try {
            String oaid = getOAID();
            if (oaid == null || oaid.length() == 0) {
                throw new OAIDException("OAID query failed");
            }
            OAIDLog.print("OAID query success: " + oaid);
            iGetter.onOAIDGetComplete(oaid);
        } catch (Exception e2) {
            OAIDLog.print(e2);
            iGetter.onOAIDGetError(e2);
        }
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public boolean supported() {
        return this.idProviderImpl != null;
    }
}
