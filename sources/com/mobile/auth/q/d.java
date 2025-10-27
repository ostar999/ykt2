package com.mobile.auth.q;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.model.MonitorStruct;

/* loaded from: classes4.dex */
public class d extends com.mobile.auth.gatewayauth.manager.a {

    /* renamed from: i, reason: collision with root package name */
    private a f10524i;

    /* renamed from: j, reason: collision with root package name */
    private volatile String f10525j;

    public d(Context context, com.mobile.auth.gatewayauth.manager.d dVar) {
        super(context, dVar, Constant.VENDOR_CMCC, null);
        this.f10524i = a.a(this.f10156d);
    }

    public static /* synthetic */ com.mobile.auth.o.a a(d dVar) {
        try {
            return dVar.f10160h;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ void a(d dVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            dVar.a((RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(d dVar, String str, String str2, String str3, boolean z2, String str4, MonitorStruct monitorStruct) {
        try {
            dVar.a(str, str2, str3, z2, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ String b(d dVar) {
        try {
            return dVar.f10525j;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ void b(d dVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            dVar.a((RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void b(d dVar, String str, String str2, String str3, boolean z2, String str4, MonitorStruct monitorStruct) {
        try {
            dVar.a(str, str2, str3, z2, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a c(d dVar) {
        try {
            return dVar.f10160h;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ void c(d dVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            dVar.a((RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void c(d dVar, String str, String str2, String str3, boolean z2, String str4, MonitorStruct monitorStruct) {
        try {
            dVar.a(str, str2, str3, z2, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ a d(d dVar) {
        try {
            return dVar.f10524i;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a e(d dVar) {
        try {
            return dVar.f10160h;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void a(long j2) {
        try {
            super.a(j2);
            this.f10524i.a(this.f10155c);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(String str) {
        try {
            this.f10525j = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void a(String str, String str2) {
        try {
            super.a(str, str2);
            this.f10524i.a(str);
            this.f10524i.b(str2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void a(boolean z2) {
        try {
            com.mobile.auth.e.e.a(z2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0084  */
    @Override // com.mobile.auth.gatewayauth.manager.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String b(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Throwable -> L96
            if (r0 == 0) goto L7
            return r4
        L7:
            int r0 = java.lang.Integer.parseInt(r4)     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L96
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L96
            int r1 = r0.intValue()     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L96
            r2 = 10000(0x2710, float:1.4013E-41)
            if (r1 < r2) goto L21
            int r0 = r0.intValue()     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L96
            r1 = 40000(0x9c40, float:5.6052E-41)
            if (r0 > r1) goto L21
            return r5
        L21:
            int r0 = r4.hashCode()     // Catch: java.lang.Throwable -> L96
            switch(r0) {
                case 1448695583: goto L7a;
                case 1448695585: goto L70;
                case 1448696546: goto L66;
                case 1448699433: goto L5c;
                case 1448725413: goto L51;
                case 1477264254: goto L47;
                case 1477264255: goto L3d;
                case 1477264256: goto L33;
                case 1477264259: goto L29;
                default: goto L28;
            }     // Catch: java.lang.Throwable -> L96
        L28:
            goto L84
        L29:
            java.lang.String r0 = "200027"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 3
            goto L85
        L33:
            java.lang.String r0 = "200024"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 7
            goto L85
        L3d:
            java.lang.String r0 = "200023"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 6
            goto L85
        L47:
            java.lang.String r0 = "200022"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 4
            goto L85
        L51:
            java.lang.String r0 = "103119"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 8
            goto L85
        L5c:
            java.lang.String r0 = "102507"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 5
            goto L85
        L66:
            java.lang.String r0 = "102203"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 0
            goto L85
        L70:
            java.lang.String r0 = "102103"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 2
            goto L85
        L7a:
            java.lang.String r0 = "102101"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> L96
            if (r4 == 0) goto L84
            r4 = 1
            goto L85
        L84:
            r4 = -1
        L85:
            switch(r4) {
                case 0: goto L92;
                case 1: goto L8f;
                case 2: goto L8f;
                case 3: goto L8f;
                case 4: goto L8f;
                case 5: goto L8c;
                case 6: goto L8c;
                case 7: goto L8c;
                case 8: goto L89;
                default: goto L88;
            }     // Catch: java.lang.Throwable -> L96
        L88:
            goto L95
        L89:
            java.lang.String r4 = "600027"
            return r4
        L8c:
            java.lang.String r4 = "600015"
            return r4
        L8f:
            java.lang.String r4 = "-10006"
            return r4
        L92:
            java.lang.String r4 = "600025"
            return r4
        L95:
            return r5
        L96:
            r4 = move-exception
            r5 = 0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r4)     // Catch: java.lang.Throwable -> L9c
            return r5
        L9c:
            r4 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.q.d.b(java.lang.String, java.lang.String):java.lang.String");
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void c() {
        try {
            this.f10524i.c();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public String d() {
        try {
            return this.f10524i.d();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void d(final RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, a.b bVar) {
        final MonitorStruct monitorStruct;
        try {
            monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CMCC_LOGIN_CODE);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f10156d, true));
        } finally {
            return;
        }
        if (!TextUtils.isEmpty(this.f10153a) && !TextUtils.isEmpty(this.f10154b)) {
            this.f10524i.a(new com.mobile.auth.e.b() { // from class: com.mobile.auth.q.d.1
                /* JADX WARN: Removed duplicated region for block: B:20:0x0055 A[Catch: all -> 0x00ec, TryCatch #1 {all -> 0x00ec, blocks: (B:3:0x0006, B:5:0x0011, B:6:0x0016, B:9:0x0024, B:18:0x0041, B:20:0x0055, B:21:0x0080, B:23:0x008b, B:27:0x00a3, B:28:0x00da, B:11:0x002a, B:13:0x0030, B:14:0x0035, B:16:0x003b), top: B:38:0x0006 }] */
                /* JADX WARN: Removed duplicated region for block: B:23:0x008b A[Catch: all -> 0x00ec, TryCatch #1 {all -> 0x00ec, blocks: (B:3:0x0006, B:5:0x0011, B:6:0x0016, B:9:0x0024, B:18:0x0041, B:20:0x0055, B:21:0x0080, B:23:0x008b, B:27:0x00a3, B:28:0x00da, B:11:0x002a, B:13:0x0030, B:14:0x0035, B:16:0x003b), top: B:38:0x0006 }] */
                /* JADX WARN: Removed duplicated region for block: B:28:0x00da A[Catch: all -> 0x00ec, TRY_LEAVE, TryCatch #1 {all -> 0x00ec, blocks: (B:3:0x0006, B:5:0x0011, B:6:0x0016, B:9:0x0024, B:18:0x0041, B:20:0x0055, B:21:0x0080, B:23:0x008b, B:27:0x00a3, B:28:0x00da, B:11:0x002a, B:13:0x0030, B:14:0x0035, B:16:0x003b), top: B:38:0x0006 }] */
                @Override // com.mobile.auth.e.b
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void a(int r12, org.json.JSONObject r13) {
                    /*
                        Method dump skipped, instructions count: 246
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.q.d.AnonymousClass1.a(int, org.json.JSONObject):void");
                }
            });
            return;
        }
        a(requestCallback, ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, "", Constant.VENDOR_CMCC, monitorStruct, ResultCode.CODE_GET_MASK_FAIL);
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void e(final RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, a.b bVar) {
        try {
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CMCC_LOGIN_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f10156d, true));
            this.f10524i.c(new com.mobile.auth.e.b() { // from class: com.mobile.auth.q.d.2
                /* JADX WARN: Removed duplicated region for block: B:20:0x005a A[Catch: all -> 0x00fd, TryCatch #1 {all -> 0x00fd, blocks: (B:3:0x0008, B:5:0x0013, B:6:0x0018, B:9:0x0026, B:18:0x0043, B:20:0x005a, B:21:0x0085, B:23:0x008b, B:27:0x00c9, B:28:0x00eb, B:11:0x002c, B:13:0x0032, B:14:0x0037, B:16:0x003d), top: B:38:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:23:0x008b A[Catch: all -> 0x00fd, TryCatch #1 {all -> 0x00fd, blocks: (B:3:0x0008, B:5:0x0013, B:6:0x0018, B:9:0x0026, B:18:0x0043, B:20:0x005a, B:21:0x0085, B:23:0x008b, B:27:0x00c9, B:28:0x00eb, B:11:0x002c, B:13:0x0032, B:14:0x0037, B:16:0x003d), top: B:38:0x0008 }] */
                /* JADX WARN: Removed duplicated region for block: B:28:0x00eb A[Catch: all -> 0x00fd, TRY_LEAVE, TryCatch #1 {all -> 0x00fd, blocks: (B:3:0x0008, B:5:0x0013, B:6:0x0018, B:9:0x0026, B:18:0x0043, B:20:0x005a, B:21:0x0085, B:23:0x008b, B:27:0x00c9, B:28:0x00eb, B:11:0x002c, B:13:0x0032, B:14:0x0037, B:16:0x003d), top: B:38:0x0008 }] */
                @Override // com.mobile.auth.e.b
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void a(int r13, org.json.JSONObject r14) {
                    /*
                        Method dump skipped, instructions count: 263
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.q.d.AnonymousClass2.a(int, org.json.JSONObject):void");
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void f(final RequestCallback<a.C0199a, com.mobile.auth.gatewayauth.manager.base.b> requestCallback, a.b bVar) {
        final MonitorStruct monitorStruct;
        try {
            monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CMCC_AUTH_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f10156d, true));
        } finally {
            return;
        }
        if (!TextUtils.isEmpty(this.f10153a) && !TextUtils.isEmpty(this.f10154b)) {
            this.f10524i.b(new com.mobile.auth.e.b() { // from class: com.mobile.auth.q.d.3
                /* JADX WARN: Removed duplicated region for block: B:20:0x0053 A[Catch: all -> 0x00dc, TryCatch #1 {all -> 0x00dc, blocks: (B:3:0x0006, B:5:0x0011, B:6:0x0016, B:9:0x0024, B:18:0x0041, B:20:0x0053, B:22:0x005b, B:23:0x0076, B:27:0x00a8, B:28:0x00ca, B:11:0x002a, B:13:0x0030, B:14:0x0035, B:16:0x003b), top: B:38:0x0006 }] */
                /* JADX WARN: Removed duplicated region for block: B:28:0x00ca A[Catch: all -> 0x00dc, TRY_LEAVE, TryCatch #1 {all -> 0x00dc, blocks: (B:3:0x0006, B:5:0x0011, B:6:0x0016, B:9:0x0024, B:18:0x0041, B:20:0x0053, B:22:0x005b, B:23:0x0076, B:27:0x00a8, B:28:0x00ca, B:11:0x002a, B:13:0x0030, B:14:0x0035, B:16:0x003b), top: B:38:0x0006 }] */
                @Override // com.mobile.auth.e.b
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public void a(int r12, org.json.JSONObject r13) {
                    /*
                        r11 = this;
                        java.lang.String r12 = "resultString"
                        java.lang.String r0 = "desc"
                        java.lang.String r1 = "resultDesc"
                        com.mobile.auth.gatewayauth.model.MonitorStruct r2 = r2     // Catch: java.lang.Throwable -> Ldc
                        long r3 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ldc
                        r2.setCarrierReturnTime(r3)     // Catch: java.lang.Throwable -> Ldc
                        if (r13 != 0) goto L16
                        org.json.JSONObject r13 = new org.json.JSONObject     // Catch: java.lang.Throwable -> Ldc
                        r13.<init>()     // Catch: java.lang.Throwable -> Ldc
                    L16:
                        java.lang.String r2 = "resultCode"
                        java.lang.String r5 = r13.optString(r2)     // Catch: java.lang.Throwable -> Ldc
                        boolean r2 = r13.has(r1)     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r3 = ""
                        if (r2 == 0) goto L2a
                        java.lang.String r12 = r13.optString(r1)     // Catch: java.lang.Throwable -> Ldc
                    L28:
                        r6 = r12
                        goto L41
                    L2a:
                        boolean r1 = r13.has(r0)     // Catch: java.lang.Throwable -> Ldc
                        if (r1 == 0) goto L35
                        java.lang.String r12 = r13.optString(r0)     // Catch: java.lang.Throwable -> Ldc
                        goto L28
                    L35:
                        boolean r0 = r13.has(r12)     // Catch: java.lang.Throwable -> Ldc
                        if (r0 == 0) goto L40
                        java.lang.String r12 = r13.optString(r12)     // Catch: java.lang.Throwable -> Ldc
                        goto L28
                    L40:
                        r6 = r3
                    L41:
                        java.lang.String r12 = "traceId"
                        java.lang.String r12 = r13.optString(r12)     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r0 = "token"
                        java.lang.String r0 = r13.optString(r0)     // Catch: java.lang.Throwable -> Ldc
                        boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> Ldc
                        if (r1 != 0) goto Lca
                        boolean r1 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Throwable -> Ldc
                        r2 = 1
                        r10 = 0
                        if (r1 != 0) goto L76
                        com.mobile.auth.q.d r1 = com.mobile.auth.q.d.this     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.o.a r1 = com.mobile.auth.q.d.e(r1)     // Catch: java.lang.Throwable -> Ldc
                        r3 = 3
                        java.lang.String[] r3 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r4 = "cmcc："
                        r3[r10] = r4     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r4 = "getAccessCode:"
                        r3[r2] = r4     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r13 = r13.toString()     // Catch: java.lang.Throwable -> Ldc
                        r4 = 2
                        r3[r4] = r13     // Catch: java.lang.Throwable -> Ldc
                        r1.a(r3)     // Catch: java.lang.Throwable -> Ldc
                    L76:
                        com.mobile.auth.gatewayauth.model.MonitorStruct r13 = r2     // Catch: java.lang.Throwable -> Ldc
                        r13.setCarrierTraceId(r12)     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.gatewayauth.model.MonitorStruct r12 = r2     // Catch: java.lang.Throwable -> Ldc
                        r12.setAccessCode(r0)     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.q.d r3 = com.mobile.auth.q.d.this     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r12 = ""
                        java.lang.String r6 = ""
                        r7 = 1
                        java.lang.String r8 = "cm_zyhl"
                        com.mobile.auth.gatewayauth.model.MonitorStruct r9 = r2     // Catch: java.lang.Throwable -> Ldc
                        r4 = r5
                        r5 = r12
                        com.mobile.auth.q.d.c(r3, r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.gatewayauth.manager.RequestCallback r12 = r3     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.gatewayauth.manager.a$a$a r13 = com.mobile.auth.gatewayauth.manager.a.C0199a.a()     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.gatewayauth.manager.a$a$a r13 = r13.b(r0)     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.q.d r0 = com.mobile.auth.q.d.this     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r0 = com.mobile.auth.q.d.b(r0)     // Catch: java.lang.Throwable -> Ldc
                        boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> Ldc
                        if (r0 != 0) goto La7
                        goto La8
                    La7:
                        r2 = r10
                    La8:
                        com.mobile.auth.gatewayauth.manager.a$a$a r13 = r13.a(r2)     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.q.d r0 = com.mobile.auth.q.d.this     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r0 = com.mobile.auth.q.d.b(r0)     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.gatewayauth.manager.a$a$a r13 = r13.e(r0)     // Catch: java.lang.Throwable -> Ldc
                        long r0 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Ldc
                        r2 = 120000(0x1d4c0, double:5.9288E-319)
                        long r0 = r0 + r2
                        com.mobile.auth.gatewayauth.manager.a$a$a r13 = r13.a(r0)     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.gatewayauth.manager.a$a r13 = r13.a()     // Catch: java.lang.Throwable -> Ldc
                        r12.onSuccess(r13)     // Catch: java.lang.Throwable -> Ldc
                        goto Le5
                    Lca:
                        com.mobile.auth.q.d r3 = com.mobile.auth.q.d.this     // Catch: java.lang.Throwable -> Ldc
                        com.mobile.auth.gatewayauth.manager.RequestCallback r4 = r3     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r7 = r13.toString()     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r8 = "cm_zyhl"
                        com.mobile.auth.gatewayauth.model.MonitorStruct r9 = r2     // Catch: java.lang.Throwable -> Ldc
                        java.lang.String r10 = "600011"
                        com.mobile.auth.q.d.c(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> Ldc
                        goto Le5
                    Ldc:
                        r12 = move-exception
                        com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r12)     // Catch: java.lang.Throwable -> Le1
                        goto Le5
                    Le1:
                        r12 = move-exception
                        com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r12)
                    Le5:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.q.d.AnonymousClass3.a(int, org.json.JSONObject):void");
                }
            });
            return;
        }
        a(requestCallback, ResultCode.CODE_ERROR_ANALYZE_SDK_INFO, ResultCode.MSG_ERROR_ANALYZE_SDK_INFO, "", Constant.VENDOR_CMCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
    }
}
