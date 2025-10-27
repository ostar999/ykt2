package com.alibaba.sdk.android.tbrest.rest;

import android.content.Context;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: h, reason: collision with root package name */
    private String f2916h;
    private Context mContext;

    /* renamed from: a, reason: collision with other field name */
    private Object f67a = null;

    /* renamed from: b, reason: collision with root package name */
    private Object f2913b = null;

    /* renamed from: a, reason: collision with root package name */
    private Class f2912a = null;

    /* renamed from: a, reason: collision with other field name */
    private Field f68a = null;

    /* renamed from: b, reason: collision with other field name */
    private Field f69b = null;

    /* renamed from: c, reason: collision with root package name */
    private Field f2914c = null;

    /* renamed from: b, reason: collision with other field name */
    private Method f70b = null;

    /* renamed from: f, reason: collision with root package name */
    private int f2915f = 1;

    /* renamed from: b, reason: collision with other field name */
    private boolean f71b = false;

    public g(Context context, String str) {
        this.mContext = context;
        this.f2916h = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ba A[Catch: all -> 0x00be, TRY_LEAVE, TryCatch #5 {, blocks: (B:3:0x0001, B:33:0x00ba, B:32:0x00b5, B:12:0x003c, B:14:0x0043, B:19:0x0078, B:30:0x009e, B:23:0x008e, B:17:0x0070, B:15:0x0067, B:21:0x0087), top: B:50:0x0001, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0043 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void f() {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r7.f71b     // Catch: java.lang.Throwable -> Lbe
            if (r0 == 0) goto L7
            monitor-exit(r7)
            return
        L7:
            r0 = 0
            r1 = 1
            r2 = 0
            java.lang.String r3 = "com.taobao.wireless.security.sdk.SecurityGuardManager"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch: java.lang.Throwable -> L3b
            java.lang.String r4 = "getInstance"
            java.lang.Class[] r5 = new java.lang.Class[r1]     // Catch: java.lang.Throwable -> L3c
            java.lang.Class<android.content.Context> r6 = android.content.Context.class
            r5[r2] = r6     // Catch: java.lang.Throwable -> L3c
            java.lang.reflect.Method r4 = r3.getMethod(r4, r5)     // Catch: java.lang.Throwable -> L3c
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L3c
            android.content.Context r6 = r7.mContext     // Catch: java.lang.Throwable -> L3c
            r5[r2] = r6     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r4 = r4.invoke(r0, r5)     // Catch: java.lang.Throwable -> L3c
            r7.f67a = r4     // Catch: java.lang.Throwable -> L3c
            java.lang.String r4 = "getSecureSignatureComp"
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch: java.lang.Throwable -> L3c
            java.lang.reflect.Method r4 = r3.getMethod(r4, r5)     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r5 = r7.f67a     // Catch: java.lang.Throwable -> L3c
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r4 = r4.invoke(r5, r6)     // Catch: java.lang.Throwable -> L3c
            r7.f2913b = r4     // Catch: java.lang.Throwable -> L3c
            goto L41
        L3b:
            r3 = r0
        L3c:
            java.lang.String r4 = "initSecurityCheck failure, It's ok "
            com.alibaba.sdk.android.tbrest.utils.LogUtil.i(r4)     // Catch: java.lang.Throwable -> Lbe
        L41:
            if (r3 == 0) goto Lba
            java.lang.String r4 = "com.taobao.wireless.security.sdk.SecurityGuardParamContext"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch: java.lang.Throwable -> Lb5
            r7.f2912a = r4     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r5 = "appKey"
            java.lang.reflect.Field r4 = r4.getDeclaredField(r5)     // Catch: java.lang.Throwable -> Lb5
            r7.f68a = r4     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class r4 = r7.f2912a     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r5 = "paramMap"
            java.lang.reflect.Field r4 = r4.getDeclaredField(r5)     // Catch: java.lang.Throwable -> Lb5
            r7.f69b = r4     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class r4 = r7.f2912a     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r5 = "requestType"
            java.lang.reflect.Field r4 = r4.getDeclaredField(r5)     // Catch: java.lang.Throwable -> Lb5
            r7.f2914c = r4     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r4 = "isOpen"
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch: java.lang.Throwable -> L70
            java.lang.reflect.Method r3 = r3.getMethod(r4, r5)     // Catch: java.lang.Throwable -> L70
            goto L76
        L70:
            java.lang.String r3 = "initSecurityCheck failure, It's ok"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.i(r3)     // Catch: java.lang.Throwable -> Lb5
            r3 = r0
        L76:
            if (r3 == 0) goto L87
            java.lang.Object r0 = r7.f67a     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object r0 = r3.invoke(r0, r4)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch: java.lang.Throwable -> Lb5
            boolean r0 = r0.booleanValue()     // Catch: java.lang.Throwable -> Lb5
            goto L98
        L87:
            java.lang.String r3 = "com.taobao.wireless.security.sdk.securitybody.ISecurityBodyComponent"
            java.lang.Class r0 = java.lang.Class.forName(r3)     // Catch: java.lang.Throwable -> L8e
            goto L93
        L8e:
            java.lang.String r3 = "initSecurityCheck failure, It's ok"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.i(r3)     // Catch: java.lang.Throwable -> Lb5
        L93:
            if (r0 != 0) goto L97
            r0 = r1
            goto L98
        L97:
            r0 = r2
        L98:
            if (r0 == 0) goto L9c
            r0 = r1
            goto L9e
        L9c:
            r0 = 12
        L9e:
            r7.f2915f = r0     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r0 = "com.taobao.wireless.security.sdk.securesignature.ISecureSignatureComponent"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r3 = "signRequest"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class r5 = r7.f2912a     // Catch: java.lang.Throwable -> Lb5
            r4[r2] = r5     // Catch: java.lang.Throwable -> Lb5
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch: java.lang.Throwable -> Lb5
            r7.f70b = r0     // Catch: java.lang.Throwable -> Lb5
            goto Lba
        Lb5:
            java.lang.String r0 = "initSecurityCheck failure, It's ok"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.i(r0)     // Catch: java.lang.Throwable -> Lbe
        Lba:
            r7.f71b = r1     // Catch: java.lang.Throwable -> Lbe
            monitor-exit(r7)
            return
        Lbe:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.rest.g.f():void");
    }

    public String b(String str) throws IllegalAccessException, InstantiationException, IllegalArgumentException {
        Class cls;
        if (!this.f71b) {
            f();
        }
        if (this.f2916h == null) {
            LogUtil.e("RestSecuritySDKRequestAuthentication:getSign There is no appkey,please check it!");
            return null;
        }
        if (str == null || this.f67a == null || (cls = this.f2912a) == null || this.f68a == null || this.f69b == null || this.f2914c == null || this.f70b == null || this.f2913b == null) {
            return null;
        }
        try {
            Object objNewInstance = cls.newInstance();
            this.f68a.set(objNewInstance, this.f2916h);
            ((Map) this.f69b.get(objNewInstance)).put("INPUT", str);
            this.f2914c.set(objNewInstance, Integer.valueOf(this.f2915f));
            return (String) this.f70b.invoke(this.f2913b, objNewInstance);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return null;
        } catch (InstantiationException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return null;
        }
    }
}
