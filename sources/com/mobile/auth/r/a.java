package com.mobile.auth.r;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.a.c;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.base.b;
import com.mobile.auth.gatewayauth.manager.d;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.model.ctcctoken.CTCCTokenRet;
import com.mobile.auth.gatewayauth.model.ctcctoken.Data;
import com.nirvana.tools.core.ExecutorManager;

/* loaded from: classes4.dex */
public class a extends com.mobile.auth.gatewayauth.manager.a {

    /* renamed from: i, reason: collision with root package name */
    private c f10535i;

    /* renamed from: j, reason: collision with root package name */
    private volatile String f10536j;

    public a(Context context, d dVar) {
        super(context, dVar, Constant.VENDOR_CTCC, null);
        this.f10535i = new c() { // from class: com.mobile.auth.r.a.1
            @Override // com.mobile.auth.a.c
            public void a(String str, String str2) {
                try {
                    a.a(a.this).a(str, str2);
                } catch (Throwable th) {
                    try {
                        ExceptionProcessor.processException(th);
                    } catch (Throwable th2) {
                        ExceptionProcessor.processException(th2);
                    }
                }
            }

            @Override // com.mobile.auth.a.c
            public void a(String str, String str2, Throwable th) {
                try {
                    if (th != null) {
                        a.b(a.this).c(str, str2, ExecutorManager.getErrorInfoFromException(th));
                    } else {
                        a.c(a.this).c(str, str2);
                    }
                } catch (Throwable th2) {
                    try {
                        ExceptionProcessor.processException(th2);
                    } catch (Throwable th3) {
                        ExceptionProcessor.processException(th3);
                    }
                }
            }
        };
    }

    public static /* synthetic */ com.mobile.auth.o.a a(a aVar) {
        try {
            return aVar.f10160h;
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

    private synchronized void a(final RequestCallback<a.C0199a, b> requestCallback, final MonitorStruct monitorStruct, final String str) {
        try {
            com.mobile.auth.a.a.a((int) this.f10155c, (int) this.f10155c, (int) this.f10155c, this.f10535i);
            com.mobile.auth.a.a.a(this.f10156d, this.f10153a, this.f10154b, new com.mobile.auth.a.b() { // from class: com.mobile.auth.r.a.2
                @Override // com.mobile.auth.a.b
                public void a(String str2) {
                    try {
                        monitorStruct.setCarrierReturnTime(System.currentTimeMillis());
                        boolean z2 = true;
                        if (!TextUtils.isEmpty("")) {
                            a.d(a.this).a("ctcc：", "getLoginInfo:", str2);
                        }
                        if (TextUtils.isEmpty(str2)) {
                            a.a(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "CTCC 获得的手机授权码结果为空", "", Constant.VENDOR_CTCC, monitorStruct, str);
                            return;
                        }
                        try {
                            CTCCTokenRet cTCCTokenRetFromJson = CTCCTokenRet.fromJson(str2);
                            if (cTCCTokenRetFromJson != null) {
                                monitorStruct.setCarrierTraceId(cTCCTokenRetFromJson.getReqId());
                                if (cTCCTokenRetFromJson.getResult() != 0 || cTCCTokenRetFromJson.getData() == null) {
                                    a.d(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str2, Constant.VENDOR_CTCC, monitorStruct, str);
                                    return;
                                }
                                Data data = cTCCTokenRetFromJson.getData();
                                String number = data.getNumber();
                                String accessCode = data.getAccessCode();
                                if (TextUtils.isEmpty(number)) {
                                    a.c(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str2, Constant.VENDOR_CTCC, monitorStruct, str);
                                    return;
                                }
                                RequestCallback requestCallback2 = requestCallback;
                                a.C0199a.C0200a c0200aE = a.C0199a.a().a(number).c(Constant.CTCC_PROTOCOL).d(Constant.CTCC_PROTOCOL_URL).b(accessCode).e(a.f(a.this));
                                if (TextUtils.isEmpty(a.f(a.this))) {
                                    z2 = false;
                                }
                                requestCallback2.onSuccess(c0200aE.a(z2).a(System.currentTimeMillis() + (data.getExpiredTime() * 1000)).a());
                                monitorStruct.setPhoneNumber(number);
                                monitorStruct.setAccessCode(accessCode);
                                a.a(a.this, String.valueOf(cTCCTokenRetFromJson.getResult()), "", "", true, Constant.VENDOR_CTCC, monitorStruct);
                            }
                        } catch (Exception e2) {
                            a.e(a.this).e("CTCCValidManager init exception:", ExecutorManager.getErrorInfoFromException(e2));
                            a.b(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "JSON转换失败", str2, Constant.VENDOR_CTCC, monitorStruct, str);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
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

    public static /* synthetic */ void a(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void a(a aVar, String str, String str2, String str3, boolean z2, String str4, MonitorStruct monitorStruct) {
        try {
            aVar.a(str, str2, str3, z2, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a b(a aVar) {
        try {
            return aVar.f10160h;
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

    public static /* synthetic */ void b(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ void b(a aVar, String str, String str2, String str3, boolean z2, String str4, MonitorStruct monitorStruct) {
        try {
            aVar.a(str, str2, str3, z2, str4, monitorStruct);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a c(a aVar) {
        try {
            return aVar.f10160h;
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

    public static /* synthetic */ void c(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a d(a aVar) {
        try {
            return aVar.f10160h;
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

    public static /* synthetic */ void d(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a e(a aVar) {
        try {
            return aVar.f10160h;
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

    public static /* synthetic */ void e(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ String f(a aVar) {
        try {
            return aVar.f10536j;
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

    public static /* synthetic */ void f(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a g(a aVar) {
        try {
            return aVar.f10160h;
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

    public static /* synthetic */ void g(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ com.mobile.auth.o.a h(a aVar) {
        try {
            return aVar.f10160h;
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

    public static /* synthetic */ void h(a aVar, RequestCallback requestCallback, String str, String str2, String str3, String str4, MonitorStruct monitorStruct, String str5) {
        try {
            aVar.a((RequestCallback<a.C0199a, b>) requestCallback, str, str2, str3, str4, monitorStruct, str5);
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
            this.f10536j = str;
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
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00a8  */
    @Override // com.mobile.auth.gatewayauth.manager.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String b(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Throwable -> Lba
            if (r0 == 0) goto L7
            return r4
        L7:
            int r0 = java.lang.Integer.parseInt(r4)     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> Lba
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> Lba
            int r1 = r0.intValue()     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> Lba
            r2 = 10000(0x2710, float:1.4013E-41)
            if (r1 < r2) goto L21
            int r0 = r0.intValue()     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> Lba
            r1 = 40000(0x9c40, float:5.6052E-41)
            if (r0 > r1) goto L21
            return r5
        L21:
            int r0 = r4.hashCode()     // Catch: java.lang.Throwable -> Lba
            switch(r0) {
                case -1389750326: goto L9e;
                case 1449: goto L93;
                case 43274408: goto L89;
                case 43274409: goto L7e;
                case 43275366: goto L74;
                case 53194808: goto L6a;
                case 53194811: goto L60;
                case 53194812: goto L55;
                case 53194813: goto L4b;
                case 53202496: goto L41;
                case 1335041958: goto L36;
                case 1335965482: goto L2a;
                default: goto L28;
            }     // Catch: java.lang.Throwable -> Lba
        L28:
            goto La8
        L2a:
            java.lang.String r0 = "-20005"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 11
            goto La9
        L36:
            java.lang.String r0 = "-10002"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 0
            goto La9
        L41:
            java.lang.String r0 = "80800"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 3
            goto La9
        L4b:
            java.lang.String r0 = "80005"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 2
            goto La9
        L55:
            java.lang.String r0 = "80004"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 8
            goto La9
        L60:
            java.lang.String r0 = "80003"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 7
            goto La9
        L6a:
            java.lang.String r0 = "80000"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 1
            goto La9
        L74:
            java.lang.String r0 = "-8100"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 6
            goto La9
        L7e:
            java.lang.String r0 = "-8004"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 9
            goto La9
        L89:
            java.lang.String r0 = "-8003"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 4
            goto La9
        L93:
            java.lang.String r0 = "-6"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 10
            goto La9
        L9e:
            java.lang.String r0 = "-720002"
            boolean r4 = r4.equals(r0)     // Catch: java.lang.Throwable -> Lba
            if (r4 == 0) goto La8
            r4 = 5
            goto La9
        La8:
            r4 = -1
        La9:
            switch(r4) {
                case 0: goto Lb6;
                case 1: goto Lb3;
                case 2: goto Lb3;
                case 3: goto Lb3;
                case 4: goto Lb3;
                case 5: goto Lb3;
                case 6: goto Lb0;
                case 7: goto Lb0;
                case 8: goto Lb0;
                case 9: goto Lb0;
                case 10: goto Lad;
                case 11: goto Lad;
                default: goto Lac;
            }     // Catch: java.lang.Throwable -> Lba
        Lac:
            goto Lb9
        Lad:
            java.lang.String r4 = "600027"
            return r4
        Lb0:
            java.lang.String r4 = "-10006"
            return r4
        Lb3:
            java.lang.String r4 = "600015"
            return r4
        Lb6:
            java.lang.String r4 = "600025"
            return r4
        Lb9:
            return r5
        Lba:
            r4 = move-exception
            r5 = 0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r4)     // Catch: java.lang.Throwable -> Lc0
            return r5
        Lc0:
            r4 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.r.a.b(java.lang.String, java.lang.String):java.lang.String");
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public void c() {
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void d(RequestCallback<a.C0199a, b> requestCallback, a.b bVar) {
        try {
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CTCC_LOGIN_CODE);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f10156d, true));
            a(requestCallback, monitorStruct, ResultCode.CODE_GET_MASK_FAIL);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void e(RequestCallback<a.C0199a, b> requestCallback, a.b bVar) {
        try {
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CTCC_LOGIN_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f10156d, true));
            a(requestCallback, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @Override // com.mobile.auth.gatewayauth.manager.a
    public synchronized void f(final RequestCallback<a.C0199a, b> requestCallback, a.b bVar) {
        try {
            final MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CTCC_AUTH_TOKEN);
            monitorStruct.setNetworkType(com.mobile.auth.gatewayauth.utils.c.a(this.f10156d, true));
            com.mobile.auth.a.a.a((int) this.f10155c, (int) this.f10155c, (int) this.f10155c, this.f10535i);
            com.mobile.auth.a.a.a(this.f10156d, this.f10153a, this.f10154b, new com.mobile.auth.a.b() { // from class: com.mobile.auth.r.a.3
                @Override // com.mobile.auth.a.b
                public void a(String str) {
                    try {
                        monitorStruct.setCarrierReturnTime(System.currentTimeMillis());
                        boolean z2 = true;
                        if (!TextUtils.isEmpty("")) {
                            a.g(a.this).a("ctcc：", "getVerifyInfo:", str);
                        }
                        if (TextUtils.isEmpty(str)) {
                            a.e(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "CTCC 获得认证Token结果为空", "", Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                            return;
                        }
                        try {
                            CTCCTokenRet cTCCTokenRetFromJson = CTCCTokenRet.fromJson(str);
                            if (cTCCTokenRetFromJson != null) {
                                monitorStruct.setCarrierTraceId(cTCCTokenRetFromJson.getReqId());
                                if (cTCCTokenRetFromJson.getResult() != 0 || cTCCTokenRetFromJson.getData() == null) {
                                    a.h(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str, Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                                    return;
                                }
                                String accessCode = cTCCTokenRetFromJson.getData().getAccessCode();
                                if (TextUtils.isEmpty(accessCode)) {
                                    a.g(a.this, requestCallback, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), str, Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                                    return;
                                }
                                monitorStruct.setAccessCode(accessCode);
                                a.b(a.this, String.valueOf(cTCCTokenRetFromJson.getResult()), cTCCTokenRetFromJson.getMsg(), "", true, Constant.VENDOR_CTCC, monitorStruct);
                                RequestCallback requestCallback2 = requestCallback;
                                a.C0199a.C0200a c0200aB = a.C0199a.a().b(accessCode);
                                if (TextUtils.isEmpty(a.f(a.this))) {
                                    z2 = false;
                                }
                                requestCallback2.onSuccess(c0200aB.a(z2).e(a.f(a.this)).a(System.currentTimeMillis() + (r1.getExpiredTime() * 1000)).a());
                            }
                        } catch (Exception e2) {
                            a.h(a.this).e("CTCCValidManager init exception:", ExecutorManager.getErrorInfoFromException(e2));
                            a.f(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "JSON转换失败", str, Constant.VENDOR_CTCC, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
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
}
