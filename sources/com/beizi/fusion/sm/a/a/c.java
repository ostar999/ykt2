package com.beizi.fusion.sm.a.a;

import android.app.KeyguardManager;
import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/* loaded from: classes2.dex */
public class c implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5286a;

    /* renamed from: b, reason: collision with root package name */
    private final KeyguardManager f5287b;

    public c(Context context) {
        this.f5286a = context;
        this.f5287b = (KeyguardManager) context.getSystemService("keyguard");
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        KeyguardManager keyguardManager;
        if (this.f5286a == null || (keyguardManager = this.f5287b) == null) {
            return false;
        }
        try {
            Object objInvoke = keyguardManager.getClass().getDeclaredMethod("isSupported", new Class[0]).invoke(this.f5287b, new Object[0]);
            Objects.requireNonNull(objInvoke);
            return ((Boolean) objInvoke).booleanValue();
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f5286a == null || cVar == null) {
            return;
        }
        KeyguardManager keyguardManager = this.f5287b;
        if (keyguardManager == null) {
            cVar.a(new com.beizi.fusion.sm.a.e("KeyguardManager not found"));
            return;
        }
        try {
            Object objInvoke = keyguardManager.getClass().getDeclaredMethod("obtainOaid", new Class[0]).invoke(this.f5287b, new Object[0]);
            if (objInvoke != null) {
                String string = objInvoke.toString();
                com.beizi.fusion.sm.a.f.a("OAID obtain success: " + string);
                cVar.a(string);
                return;
            }
            throw new com.beizi.fusion.sm.a.e("OAID obtain failed");
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
        }
    }
}
