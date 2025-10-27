package com.mobile.auth.q;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.l.h;
import com.mobile.auth.l.n;
import com.mobile.auth.l.q;
import com.umeng.analytics.pro.am;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: b, reason: collision with root package name */
    private static volatile a f10501b;

    /* renamed from: a, reason: collision with root package name */
    private com.mobile.auth.e.a f10502a;

    /* renamed from: c, reason: collision with root package name */
    private Context f10503c;

    /* renamed from: d, reason: collision with root package name */
    private com.mobile.auth.e.c f10504d;

    /* renamed from: e, reason: collision with root package name */
    private com.cmic.sso.sdk.a f10505e;

    /* renamed from: f, reason: collision with root package name */
    private String f10506f = "";

    /* renamed from: g, reason: collision with root package name */
    private Handler f10507g;

    /* renamed from: h, reason: collision with root package name */
    private String f10508h;

    /* renamed from: i, reason: collision with root package name */
    private String f10509i;

    /* renamed from: com.mobile.auth.q.a$a, reason: collision with other inner class name */
    public class RunnableC0203a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private com.cmic.sso.sdk.a f10518b;

        /* renamed from: c, reason: collision with root package name */
        private volatile boolean f10519c = false;

        public RunnableC0203a(com.cmic.sso.sdk.a aVar) {
            this.f10518b = aVar;
        }

        private synchronized boolean a() {
            boolean z2;
            try {
                z2 = this.f10519c;
                this.f10519c = true;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return false;
                }
            }
            return !z2;
        }

        public static /* synthetic */ boolean a(RunnableC0203a runnableC0203a) {
            try {
                return runnableC0203a.a();
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return false;
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                if (a()) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("resultCode", "200023");
                        jSONObject.put("resultString", "登录超时");
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    a.a(a.this).a("200023", "登录超时", this.f10518b, jSONObject);
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    public a(Context context) {
        this.f10502a = com.mobile.auth.e.a.a(context);
        this.f10503c = context.getApplicationContext();
        this.f10507g = new Handler(this.f10503c.getMainLooper());
    }

    public static /* synthetic */ com.mobile.auth.e.a a(a aVar) {
        try {
            return aVar.f10502a;
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

    public static a a(Context context) {
        try {
            if (f10501b == null) {
                synchronized (a.class) {
                    if (f10501b == null) {
                        f10501b = new a(context);
                    }
                }
            }
            return f10501b;
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

    public static /* synthetic */ String a(a aVar, String str) {
        try {
            aVar.f10506f = str;
            return str;
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

    private void a(com.mobile.auth.e.c cVar, com.cmic.sso.sdk.a aVar, com.mobile.auth.e.d dVar) {
        Method declaredMethod = null;
        try {
            try {
                try {
                    try {
                        declaredMethod = com.mobile.auth.e.c.class.getDeclaredMethod(am.av, com.cmic.sso.sdk.a.class, com.mobile.auth.e.d.class);
                        declaredMethod.setAccessible(true);
                        declaredMethod.invoke(cVar, aVar, dVar);
                    } catch (NoSuchMethodException e2) {
                        e2.printStackTrace();
                    } catch (InvocationTargetException e3) {
                        e3.printStackTrace();
                    }
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                }
                declaredMethod.setAccessible(false);
            } catch (Throwable th) {
                declaredMethod.setAccessible(false);
                throw th;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    public static /* synthetic */ void a(a aVar, String str, com.cmic.sso.sdk.a aVar2, c cVar) {
        try {
            aVar.a(str, aVar2, cVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String str, com.cmic.sso.sdk.a aVar, final c cVar) {
        try {
            final RunnableC0203a runnableC0203a = new RunnableC0203a(aVar);
            this.f10507g.postDelayed(runnableC0203a, b());
            aVar.b("authTypeInput", str);
            a(e(), aVar, new com.mobile.auth.e.d() { // from class: com.mobile.auth.q.a.3
                @Override // com.mobile.auth.e.d
                public void a(String str2, String str3, com.cmic.sso.sdk.a aVar2, JSONObject jSONObject) {
                    try {
                        if (RunnableC0203a.a(runnableC0203a)) {
                            a.f(a.this).removeCallbacks(runnableC0203a);
                            cVar.a().put("securityphone", aVar2.b("securityphone", ""));
                            if (1 != aVar2.c("logintype") || !"显示登录取号成功".equals(str3) || com.mobile.auth.l.e.a(aVar2.b("traceId"))) {
                                a.a(a.this).a(str2, str3, aVar2, jSONObject);
                                return;
                            }
                            String strB = aVar2.b("traceId");
                            a.a(a.this, strB);
                            com.mobile.auth.l.e.a(strB, aVar2);
                            JSONObject jSONObject2 = new JSONObject();
                            try {
                                jSONObject2.put("resultCode", str2);
                                jSONObject2.put("authType", aVar2.b("authType", ""));
                                jSONObject2.put("authTypeDes", aVar2.b("authTypeDes", ""));
                                jSONObject2.put("openId", aVar2.b("openId", ""));
                                jSONObject2.put("token", aVar2.b("token", ""));
                                jSONObject2.put("traceId", aVar2.b("traceId", ""));
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                            com.mobile.auth.e.b bVarC = com.mobile.auth.l.e.c(strB);
                            if (bVarC != null) {
                                bVarC.a(aVar2.c("SDKRequestCode"), jSONObject2);
                            }
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0059 A[Catch: all -> 0x0064, PHI: r2
      0x0059: PHI (r2v5 java.lang.reflect.Method) = (r2v3 java.lang.reflect.Method), (r2v4 java.lang.reflect.Method), (r2v6 java.lang.reflect.Method) binds: [B:11:0x0052, B:19:0x0061, B:14:0x0057] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x0064, blocks: (B:4:0x0046, B:25:0x0069, B:26:0x006c, B:15:0x0059, B:3:0x0004, B:9:0x004d, B:13:0x0054, B:18:0x005e), top: B:34:0x0004, inners: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(com.mobile.auth.e.a r13, com.cmic.sso.sdk.a r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, int r18, com.mobile.auth.e.b r19) {
        /*
            r12 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 0
            r2 = 0
            java.lang.Class<com.mobile.auth.e.e> r3 = com.mobile.auth.e.e.class
            java.lang.String r4 = "a"
            r5 = 6
            java.lang.Class[] r6 = new java.lang.Class[r5]     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            java.lang.Class<com.cmic.sso.sdk.a> r7 = com.cmic.sso.sdk.a.class
            r6[r1] = r7     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r7 = 1
            r6[r7] = r0     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r8 = 2
            r6[r8] = r0     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r9 = 3
            r6[r9] = r0     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            java.lang.Class r0 = java.lang.Integer.TYPE     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r10 = 4
            r6[r10] = r0     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            java.lang.Class<com.mobile.auth.e.b> r0 = com.mobile.auth.e.b.class
            r11 = 5
            r6[r11] = r0     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            java.lang.reflect.Method r2 = r3.getDeclaredMethod(r4, r6)     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r2.setAccessible(r7)     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            java.lang.Object[] r0 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r0[r1] = r14     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r0[r7] = r15     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r0[r8] = r16     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r0[r9] = r17     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            java.lang.Integer r3 = java.lang.Integer.valueOf(r18)     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r0[r10] = r3     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r0[r11] = r19     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r3 = r13
            java.lang.Object r0 = r2.invoke(r13, r0)     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            boolean r0 = r0.booleanValue()     // Catch: java.lang.Throwable -> L4a java.lang.reflect.InvocationTargetException -> L4c java.lang.IllegalAccessException -> L53 java.lang.NoSuchMethodException -> L5d
            r2.setAccessible(r1)     // Catch: java.lang.Throwable -> L64
            return r0
        L4a:
            r0 = move-exception
            goto L67
        L4c:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L4a
            if (r2 == 0) goto L66
            goto L63
        L53:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L4a
            if (r2 == 0) goto L66
        L59:
            r2.setAccessible(r1)     // Catch: java.lang.Throwable -> L64
            goto L66
        L5d:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L4a
            if (r2 == 0) goto L66
        L63:
            goto L59
        L64:
            r0 = move-exception
            goto L6d
        L66:
            return r1
        L67:
            if (r2 == 0) goto L6c
            r2.setAccessible(r1)     // Catch: java.lang.Throwable -> L64
        L6c:
            throw r0     // Catch: java.lang.Throwable -> L64
        L6d:
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r0)     // Catch: java.lang.Throwable -> L71
            return r1
        L71:
            r0 = move-exception
            r2 = r0
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.q.a.a(com.mobile.auth.e.a, com.cmic.sso.sdk.a, java.lang.String, java.lang.String, java.lang.String, int, com.mobile.auth.e.b):boolean");
    }

    public static /* synthetic */ boolean a(a aVar, com.mobile.auth.e.a aVar2, com.cmic.sso.sdk.a aVar3, String str, String str2, String str3, int i2, com.mobile.auth.e.b bVar) {
        try {
            return aVar.a(aVar2, aVar3, str, str2, str3, i2, bVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static /* synthetic */ com.cmic.sso.sdk.a b(a aVar) {
        try {
            return aVar.f10505e;
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

    public static /* synthetic */ String c(a aVar) {
        try {
            return aVar.f10508h;
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

    public static /* synthetic */ String d(a aVar) {
        try {
            return aVar.f10509i;
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

    public static /* synthetic */ Context e(a aVar) {
        try {
            return aVar.f10503c;
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

    /* JADX WARN: Removed duplicated region for block: B:29:0x003a A[Catch: all -> 0x0041, TRY_ENTER, TryCatch #0 {all -> 0x0041, blocks: (B:3:0x0001, B:9:0x001c, B:29:0x003a, B:30:0x003d, B:31:0x003e), top: B:39:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.mobile.auth.e.c e() {
        /*
            r4 = this;
            r0 = 0
            com.mobile.auth.e.c r1 = r4.f10504d     // Catch: java.lang.Throwable -> L41
            if (r1 != 0) goto L3e
            r1 = 0
            java.lang.Class<com.mobile.auth.e.e> r2 = com.mobile.auth.e.e.class
            java.lang.String r3 = "a"
            java.lang.reflect.Field r2 = r2.getDeclaredField(r3)     // Catch: java.lang.Throwable -> L24 java.lang.IllegalAccessException -> L27 java.lang.NoSuchFieldException -> L2f
            r3 = 1
            r2.setAccessible(r3)     // Catch: java.lang.IllegalAccessException -> L20 java.lang.NoSuchFieldException -> L22 java.lang.Throwable -> L37
            com.mobile.auth.e.a r3 = r4.f10502a     // Catch: java.lang.IllegalAccessException -> L20 java.lang.NoSuchFieldException -> L22 java.lang.Throwable -> L37
            java.lang.Object r3 = r2.get(r3)     // Catch: java.lang.IllegalAccessException -> L20 java.lang.NoSuchFieldException -> L22 java.lang.Throwable -> L37
            com.mobile.auth.e.c r3 = (com.mobile.auth.e.c) r3     // Catch: java.lang.IllegalAccessException -> L20 java.lang.NoSuchFieldException -> L22 java.lang.Throwable -> L37
            r4.f10504d = r3     // Catch: java.lang.IllegalAccessException -> L20 java.lang.NoSuchFieldException -> L22 java.lang.Throwable -> L37
        L1c:
            r2.setAccessible(r1)     // Catch: java.lang.Throwable -> L41
            goto L3e
        L20:
            r3 = move-exception
            goto L29
        L22:
            r3 = move-exception
            goto L31
        L24:
            r3 = move-exception
            r2 = r0
            goto L38
        L27:
            r3 = move-exception
            r2 = r0
        L29:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L37
            if (r2 == 0) goto L3e
            goto L1c
        L2f:
            r3 = move-exception
            r2 = r0
        L31:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L37
            if (r2 == 0) goto L3e
            goto L1c
        L37:
            r3 = move-exception
        L38:
            if (r2 == 0) goto L3d
            r2.setAccessible(r1)     // Catch: java.lang.Throwable -> L41
        L3d:
            throw r3     // Catch: java.lang.Throwable -> L41
        L3e:
            com.mobile.auth.e.c r0 = r4.f10504d     // Catch: java.lang.Throwable -> L41
            return r0
        L41:
            r1 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r1)     // Catch: java.lang.Throwable -> L46
            return r0
        L46:
            r1 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.q.a.e():com.mobile.auth.e.c");
    }

    public static /* synthetic */ Handler f(a aVar) {
        try {
            return aVar.f10507g;
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

    public void a() {
        try {
            this.f10502a.b();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(long j2) {
        try {
            this.f10502a.a(j2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(com.mobile.auth.e.b bVar) {
        try {
            final c cVar = new c(bVar);
            this.f10505e = new com.cmic.sso.sdk.a(64);
            String strC = q.c();
            this.f10505e.a(new com.cmic.sso.sdk.d.b());
            this.f10505e.a("traceId", strC);
            com.mobile.auth.l.c.a("traceId", strC);
            com.mobile.auth.l.e.a(strC, cVar);
            this.f10505e.a("SDKRequestCode", -1);
            n.a(new n.a(this.f10503c, this.f10505e) { // from class: com.mobile.auth.q.a.1
                @Override // com.mobile.auth.l.n.a
                public void a() {
                    try {
                        a aVar = a.this;
                        if (a.a(aVar, a.a(aVar), a.b(a.this), a.c(a.this), a.d(a.this), "preGetMobile", 3, cVar)) {
                            a.a(a.this, String.valueOf(3), a.b(a.this), cVar);
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

    public void a(String str) {
        try {
            this.f10508h = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public long b() {
        try {
            return this.f10502a.c();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public void b(com.mobile.auth.e.b bVar) {
        try {
            this.f10502a.a(this.f10508h, this.f10509i, new c(bVar));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void b(String str) {
        try {
            this.f10509i = str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void c() {
        try {
            this.f10502a.d();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void c(com.mobile.auth.e.b bVar) {
        try {
            final c cVar = new c(bVar);
            this.f10505e = new com.cmic.sso.sdk.a(64);
            String strC = q.c();
            this.f10505e.a(new com.cmic.sso.sdk.d.b());
            this.f10505e.a("traceId", strC);
            com.mobile.auth.l.c.a("traceId", strC);
            com.mobile.auth.l.e.a(strC, cVar);
            this.f10505e.a("SDKRequestCode", -1);
            n.a(new n.a(this.f10503c, this.f10505e) { // from class: com.mobile.auth.q.a.2
                @Override // com.mobile.auth.l.n.a
                public void a() {
                    try {
                        a aVar = a.this;
                        if (a.a(aVar, a.a(aVar), a.b(a.this), a.c(a.this), a.d(a.this), "loginAuth", 1, cVar)) {
                            String strA = h.a(a.e(a.this));
                            if (!TextUtils.isEmpty(strA)) {
                                a.b(a.this).a("phonescrip", strA);
                            }
                            h.a(true, false);
                            a.a(a.this, String.valueOf(3), a.b(a.this), cVar);
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

    public String d() {
        try {
            return this.f10508h;
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
}
