package com.mobile.auth.s;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.ResultCode;
import com.mobile.auth.gatewayauth.manager.RequestCallback;
import com.mobile.auth.gatewayauth.manager.a;
import com.mobile.auth.gatewayauth.manager.base.b;
import com.mobile.auth.gatewayauth.manager.d;
import com.mobile.auth.gatewayauth.model.MonitorStruct;
import com.mobile.auth.gatewayauth.utils.c;
import com.mobile.auth.gatewayauth.utils.i;
import com.unicom.online.account.shield.ResultListener;
import com.unicom.online.account.shield.UniAccountHelper;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a extends com.mobile.auth.gatewayauth.manager.a {

    /* renamed from: i, reason: collision with root package name */
    private volatile String f10545i;

    public a(Context context, d dVar) {
        super(context, dVar, Constant.VENDOR_CUCC, null);
    }

    public static /* synthetic */ String a(a aVar) {
        try {
            return aVar.f10545i;
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
            UniAccountHelper.getInstance().cuGetToken((int) this.f10155c, new ResultListener() { // from class: com.mobile.auth.s.a.1
                @Override // com.unicom.online.account.shield.ResultListener
                public void onResult(String str2) {
                    try {
                        try {
                            if (!TextUtils.isEmpty("")) {
                                Log.i("cuzx login result:", str2);
                            }
                            JSONObject jSONObject = new JSONObject(str2);
                            int iOptInt = jSONObject.optInt("resultCode");
                            String strOptString = jSONObject.optString("resultMsg");
                            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("resultData");
                            if (iOptInt != 100) {
                                if ("1202".equals(Integer.valueOf(iOptInt))) {
                                    a.c(a.this, requestCallback, String.valueOf(iOptInt), ResultCode.MSG_ERROR_ANALYZE_SDK_BLACKLIST_INFO, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                    return;
                                } else {
                                    a.d(a.this, requestCallback, String.valueOf(iOptInt), strOptString, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                    return;
                                }
                            }
                            if (jSONObjectOptJSONObject != null) {
                                String strOptString2 = jSONObjectOptJSONObject.optString("fakeMobile");
                                String strOptString3 = jSONObjectOptJSONObject.optString("accessCode");
                                long jOptLong = jSONObjectOptJSONObject.optLong("exp");
                                if (TextUtils.isEmpty(strOptString2) || TextUtils.isEmpty(strOptString3)) {
                                    if ("1202".equals(Integer.valueOf(iOptInt))) {
                                        a.a(a.this, requestCallback, String.valueOf(iOptInt), ResultCode.MSG_ERROR_ANALYZE_SDK_BLACKLIST_INFO, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                        return;
                                    } else {
                                        a.b(a.this, requestCallback, String.valueOf(iOptInt), strOptString, str2, Constant.VENDOR_CUCC, monitorStruct, str);
                                        return;
                                    }
                                }
                                requestCallback.onSuccess(a.C0199a.a().a(strOptString2).c(Constant.CUCC_WOPROTOCOL).d(Constant.CUCC_WOPROTOCOL_URL).b(strOptString3).a(jOptLong).a(!TextUtils.isEmpty(a.a(a.this))).e(a.a(a.this)).a());
                                monitorStruct.setAccessCode(strOptString3);
                                monitorStruct.setPhoneNumber(strOptString2);
                                a.a(a.this, String.valueOf(iOptInt), "", "", true, Constant.VENDOR_CUCC, monitorStruct);
                            }
                        } catch (Exception e2) {
                            a.e(a.this, requestCallback, Constant.CODE_ERROR_UNKNOWN_FAIL, "JSON转换失败", e2.toString(), Constant.VENDOR_CUCC, monitorStruct, str);
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

    public void a(String str) {
        try {
            this.f10545i = str;
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
            UniAccountHelper.getInstance().setLogEnable(i.b());
            UniAccountHelper.getInstance().init(this.f10156d, str);
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

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005a  */
    @Override // com.mobile.auth.gatewayauth.manager.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String b(java.lang.String r3, java.lang.String r4) {
        /*
            r2 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Throwable -> L6c
            if (r0 == 0) goto L7
            return r3
        L7:
            int r0 = r3.hashCode()     // Catch: java.lang.Throwable -> L6c
            r1 = 1508389(0x170425, float:2.113703E-39)
            if (r0 == r1) goto L50
            switch(r0) {
                case 1535446013: goto L46;
                case 1535446014: goto L3c;
                case 1535446015: goto L32;
                case 1535446016: goto L28;
                case 1535446017: goto L1e;
                case 1535446018: goto L14;
                default: goto L13;
            }     // Catch: java.lang.Throwable -> L6c
        L13:
            goto L5a
        L14:
            java.lang.String r0 = "410005"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Throwable -> L6c
            if (r3 == 0) goto L5a
            r3 = 5
            goto L5b
        L1e:
            java.lang.String r0 = "410004"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Throwable -> L6c
            if (r3 == 0) goto L5a
            r3 = 4
            goto L5b
        L28:
            java.lang.String r0 = "410003"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Throwable -> L6c
            if (r3 == 0) goto L5a
            r3 = 3
            goto L5b
        L32:
            java.lang.String r0 = "410002"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Throwable -> L6c
            if (r3 == 0) goto L5a
            r3 = 2
            goto L5b
        L3c:
            java.lang.String r0 = "410001"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Throwable -> L6c
            if (r3 == 0) goto L5a
            r3 = 1
            goto L5b
        L46:
            java.lang.String r0 = "410000"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Throwable -> L6c
            if (r3 == 0) goto L5a
            r3 = 0
            goto L5b
        L50:
            java.lang.String r0 = "1105"
            boolean r3 = r3.equals(r0)     // Catch: java.lang.Throwable -> L6c
            if (r3 == 0) goto L5a
            r3 = 6
            goto L5b
        L5a:
            r3 = -1
        L5b:
            switch(r3) {
                case 0: goto L68;
                case 1: goto L65;
                case 2: goto L65;
                case 3: goto L62;
                case 4: goto L62;
                case 5: goto L62;
                case 6: goto L5f;
                default: goto L5e;
            }     // Catch: java.lang.Throwable -> L6c
        L5e:
            goto L6b
        L5f:
            java.lang.String r3 = "600027"
            return r3
        L62:
            java.lang.String r3 = "-10006"
            return r3
        L65:
            java.lang.String r3 = "600025"
            return r3
        L68:
            java.lang.String r3 = "600015"
            return r3
        L6b:
            return r4
        L6c:
            r3 = move-exception
            r4 = 0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r3)     // Catch: java.lang.Throwable -> L72
            return r4
        L72:
            r3 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r3)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.s.a.b(java.lang.String, java.lang.String):java.lang.String");
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
            monitorStruct.setAction(Constant.ACTION_CUCC_LOGIN_CODE);
            monitorStruct.setUrgency(1);
            monitorStruct.setNetworkType(c.a(this.f10156d, true));
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
    public synchronized void e(final RequestCallback<a.C0199a, b> requestCallback, a.b bVar) {
        try {
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CUCC_LOGIN_TOKEN);
            monitorStruct.setNetworkType(c.a(this.f10156d, true));
            a(new RequestCallback<a.C0199a, b>() { // from class: com.mobile.auth.s.a.2
                public void a(a.C0199a c0199a) {
                    try {
                        requestCallback.onSuccess(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(a.C0199a c0199a) {
                    try {
                        a(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
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
            MonitorStruct monitorStruct = new MonitorStruct();
            monitorStruct.putApiParam("timeout", String.valueOf(this.f10155c));
            monitorStruct.setSessionId(bVar.c());
            monitorStruct.setRequestId(bVar.b());
            monitorStruct.setStartTime(System.currentTimeMillis());
            monitorStruct.setAction(Constant.ACTION_CUCC_AUTH_TOKEN);
            monitorStruct.setNetworkType(c.a(this.f10156d, true));
            a(new RequestCallback<a.C0199a, b>() { // from class: com.mobile.auth.s.a.3
                public void a(a.C0199a c0199a) {
                    try {
                        requestCallback.onSuccess(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                public void a(b bVar2) {
                    try {
                        requestCallback.onError(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onError(b bVar2) {
                    try {
                        a(bVar2);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.gatewayauth.manager.RequestCallback
                public /* synthetic */ void onSuccess(a.C0199a c0199a) {
                    try {
                        a(c0199a);
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            }, monitorStruct, ResultCode.CODE_GET_TOKEN_FAIL);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
