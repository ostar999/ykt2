package com.mobile.auth.gatewayauth.detectnet;

import android.content.Context;
import android.net.Network;
import android.text.TextUtils;
import com.mobile.auth.c.j;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.utils.i;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static volatile e f10053a;

    /* renamed from: g, reason: collision with root package name */
    private String f10059g;

    /* renamed from: h, reason: collision with root package name */
    private String f10060h;

    /* renamed from: c, reason: collision with root package name */
    private AtomicBoolean f10055c = new AtomicBoolean(false);

    /* renamed from: e, reason: collision with root package name */
    private int f10057e = 3;

    /* renamed from: f, reason: collision with root package name */
    private int f10058f = 2;

    /* renamed from: b, reason: collision with root package name */
    private ExecutorService f10054b = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue(3), new ThreadPoolExecutor.CallerRunsPolicy());

    /* renamed from: d, reason: collision with root package name */
    private ConcurrentHashMap<String, String> f10056d = new ConcurrentHashMap<>();

    public e() {
        this.f10060h = "eco.taobao.com";
        try {
            this.f10060h = new URL("https://dypnsapi.aliyuncs.com/?").getHost();
        } catch (MalformedURLException unused) {
        }
    }

    public static /* synthetic */ DetectResult a(e eVar, String str, DetectResult detectResult) {
        try {
            return eVar.b(str, detectResult);
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

    private DetectResult a(String str, DetectResult detectResult) {
        try {
            i.a("pingNet： ping检测top默认");
            c cVarA = a(str, this.f10060h);
            detectResult.setTopConnected(cVarA.a());
            if (cVarA.a()) {
                detectResult.setTopWholeMS(cVarA.b());
            } else {
                detectResult.setTopWholeMS(0L);
            }
            return detectResult;
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

    public static e a() {
        try {
            if (f10053a == null) {
                synchronized (e.class) {
                    if (f10053a == null) {
                        f10053a = new e();
                    }
                }
            }
            return f10053a;
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

    public static /* synthetic */ AtomicBoolean a(e eVar) {
        try {
            return eVar.f10055c;
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

    private void a(Context context, final String str, final a aVar) {
        try {
            i.a("pingNet：检测 默认网络和蜂窝网络");
            final DetectResult detectResult = new DetectResult();
            detectResult.setRequestIds(this.f10056d.keySet()).setSessionIds(this.f10056d.values());
            a(str, detectResult);
            new j().a(context, new j.a() { // from class: com.mobile.auth.gatewayauth.detectnet.e.2
                @Override // com.mobile.auth.c.j.a
                public void a() {
                    try {
                        i.a("pingNet：切换蜂窝网络超时！");
                        detectResult.setTopCellularConnected(false).setTopCellularWholeMS(0L).setCuCellularConnected(false).setCuCellularWholeMS(0L);
                        e.a(e.this).set(false);
                        a aVar2 = aVar;
                        if (aVar2 != null) {
                            aVar2.a(detectResult);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.c.j.a
                public void a(int i2, String str2, long j2) {
                    try {
                        i.a("pingNet：切换蜂窝网络失败！");
                        detectResult.setTopCellularConnected(false).setTopCellularWholeMS(0L).setCuCellularConnected(false).setCuCellularWholeMS(0L);
                        e.a(e.this).set(false);
                        a aVar2 = aVar;
                        if (aVar2 != null) {
                            aVar2.a(detectResult);
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }

                @Override // com.mobile.auth.c.j.a
                public void a(Network network, long j2) {
                    try {
                        i.a("pingNet：切换蜂窝网络成功！");
                        e.a(e.this, str, detectResult);
                        e.b(e.this, str, detectResult);
                        e.a(e.this).set(false);
                        a aVar2 = aVar;
                        if (aVar2 != null) {
                            aVar2.a(detectResult);
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

    public static /* synthetic */ void a(e eVar, Context context, String str, a aVar) {
        try {
            eVar.a(context, str, aVar);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ DetectResult b(e eVar, String str, DetectResult detectResult) {
        try {
            return eVar.c(str, detectResult);
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

    private DetectResult b(String str, DetectResult detectResult) {
        try {
            i.a("pingNet： ping检测top蜂窝");
            c cVarA = a(str, this.f10060h);
            detectResult.setTopCellularConnected(cVarA.a());
            if (cVarA.a()) {
                detectResult.setTopCellularWholeMS(cVarA.b());
            } else {
                detectResult.setTopCellularWholeMS(0L);
            }
            return detectResult;
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

    public static /* synthetic */ ConcurrentHashMap b(e eVar) {
        try {
            return eVar.f10056d;
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

    public static /* synthetic */ DetectResult c(e eVar, String str, DetectResult detectResult) {
        try {
            return eVar.a(str, detectResult);
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

    private DetectResult c(String str, DetectResult detectResult) {
        try {
            i.a("pingNet： ping检测蜂窝");
            if (TextUtils.isEmpty(this.f10059g)) {
                detectResult.setCuCellularConnected(false).setCuCellularWholeMS(0L);
            } else {
                c cVarA = a(str, this.f10059g);
                detectResult.setCuCellularConnected(cVarA.a());
                if (cVarA.a()) {
                    detectResult.setCuCellularWholeMS(cVarA.b());
                } else {
                    detectResult.setCuCellularWholeMS(0L);
                }
            }
            return detectResult;
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

    public c a(String str, String str2) {
        try {
            i.a("pingNet：" + str + " ping start");
            c cVar = new c();
            int i2 = 0;
            while (i2 < this.f10058f) {
                cVar = d.a(str2, this.f10057e);
                i.a("pingNet：第" + i2 + "次 result：" + cVar.toString());
                i2++;
                if (cVar.a()) {
                    break;
                }
            }
            i.a("pingNet：" + str + " ping stop");
            i.a("pingNet：" + str + " 总共 ping了:" + i2 + "次");
            return cVar;
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

    public e a(String str) {
        try {
            this.f10059g = str;
            return this;
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

    public void a(final Context context, final String str, final String str2, final a aVar) {
        try {
            i.a("pingNet：" + str + " startDetect");
            this.f10054b.execute(new Runnable() { // from class: com.mobile.auth.gatewayauth.detectnet.e.1
                @Override // java.lang.Runnable
                public void run() {
                    DetectResult detectResult;
                    a aVar2;
                    try {
                        if (e.a(e.this).compareAndSet(false, true)) {
                            e.b(e.this).clear();
                            e.b(e.this).put(str, str2);
                            if (com.mobile.auth.gatewayauth.utils.c.i(context)) {
                                i.a("pingNet：纯蜂窝网络连接");
                                DetectResult detectResult2 = new DetectResult();
                                detectResult2.setRequestIds(e.b(e.this).keySet()).setSessionIds(e.b(e.this).values());
                                detectResult = e.b(e.this, str, e.a(e.this, str, detectResult2));
                                detectResult.setTopConnected(false).setTopWholeMS(0L);
                                e.a(e.this).set(false);
                                aVar2 = aVar;
                                if (aVar2 != null) {
                                    aVar2.a(detectResult);
                                }
                            } else if (com.mobile.auth.gatewayauth.utils.c.e(context)) {
                                i.a("pingNet：wifi+蜂窝网络");
                                e.a(e.this, context, str, aVar);
                            } else {
                                i.a("pingNet：纯wifi");
                                detectResult = new DetectResult();
                                detectResult.setRequestIds(e.b(e.this).keySet()).setSessionIds(e.b(e.this).values());
                                e.c(e.this, str, detectResult);
                                detectResult.setTopCellularConnected(false).setTopCellularWholeMS(0L).setCuCellularConnected(false).setCuCellularWholeMS(0L);
                                e.a(e.this).set(false);
                                aVar2 = aVar;
                                if (aVar2 != null) {
                                    aVar2.a(detectResult);
                                }
                            }
                        } else {
                            i.a("pingNet：" + str + " 已经有检测在实施：" + e.b(e.this).toString());
                            e.b(e.this).put(str, str2);
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
