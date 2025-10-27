package com.mobile.auth.b;

import android.os.Build;
import android.text.TextUtils;
import cn.hutool.core.date.DatePattern;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private String f9568b;

    /* renamed from: d, reason: collision with root package name */
    private String f9570d;

    /* renamed from: f, reason: collision with root package name */
    private String f9572f;

    /* renamed from: g, reason: collision with root package name */
    private String f9573g;

    /* renamed from: h, reason: collision with root package name */
    private String f9574h;

    /* renamed from: i, reason: collision with root package name */
    private String f9575i;

    /* renamed from: j, reason: collision with root package name */
    private String f9576j;

    /* renamed from: k, reason: collision with root package name */
    private String f9577k;

    /* renamed from: l, reason: collision with root package name */
    private String f9578l;

    /* renamed from: o, reason: collision with root package name */
    private int f9581o;

    /* renamed from: q, reason: collision with root package name */
    private long f9583q;

    /* renamed from: r, reason: collision with root package name */
    private long f9584r;

    /* renamed from: s, reason: collision with root package name */
    private String f9585s;

    /* renamed from: u, reason: collision with root package name */
    private long f9587u;

    /* renamed from: v, reason: collision with root package name */
    private long f9588v;

    /* renamed from: w, reason: collision with root package name */
    private String f9589w;

    /* renamed from: t, reason: collision with root package name */
    private StringBuffer f9586t = new StringBuffer();

    /* renamed from: c, reason: collision with root package name */
    private String f9569c = "";

    /* renamed from: e, reason: collision with root package name */
    private String f9571e = "";

    /* renamed from: n, reason: collision with root package name */
    private String f9580n = "";

    /* renamed from: m, reason: collision with root package name */
    private String f9579m = "";

    /* renamed from: p, reason: collision with root package name */
    private String f9582p = "";

    /* renamed from: a, reason: collision with root package name */
    private String f9567a = "1.1";

    public b(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.f9587u = jCurrentTimeMillis;
        this.f9568b = a(jCurrentTimeMillis);
        this.f9570d = "";
        this.f9572f = "";
        this.f9573g = Build.BRAND;
        this.f9574h = Build.MODEL;
        this.f9575i = "Android";
        this.f9576j = Build.VERSION.RELEASE;
        this.f9577k = "SDK-JJ-v4.5.5";
        this.f9578l = str;
        this.f9585s = "0";
        this.f9589w = "";
    }

    public static String a(long j2) {
        try {
            return new SimpleDateFormat(DatePattern.NORM_DATETIME_MS_PATTERN, Locale.CHINA).format(new Date(j2));
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }

    public b a(int i2) {
        try {
            this.f9581o = i2;
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

    public b a(String str) {
        try {
            this.f9570d = str;
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

    public b b(long j2) {
        try {
            this.f9583q = j2;
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

    public b b(String str) {
        try {
            this.f9571e = str;
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

    public b c(String str) {
        try {
            this.f9572f = str;
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

    public b d(String str) {
        try {
            this.f9579m = str;
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

    public b e(String str) {
        try {
            this.f9580n = str;
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

    public b f(String str) {
        try {
            this.f9582p = str;
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

    public b g(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                this.f9585s = str;
            }
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

    public b h(String str) {
        try {
            StringBuffer stringBuffer = this.f9586t;
            stringBuffer.append(str);
            stringBuffer.append("\n");
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

    public b i(String str) {
        try {
            this.f9589w = str;
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

    public String toString() {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.f9588v = jCurrentTimeMillis;
            this.f9584r = jCurrentTimeMillis - this.f9587u;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("v", this.f9567a);
            jSONObject.put("t", this.f9568b);
            jSONObject.put("tag", this.f9569c);
            jSONObject.put("ai", this.f9570d);
            jSONObject.put(AppIconSetting.DEFAULT_LARGE_ICON, this.f9571e);
            jSONObject.put(NotificationStyle.NOTIFICATION_STYLE, this.f9572f);
            jSONObject.put("br", this.f9573g);
            jSONObject.put("ml", this.f9574h);
            jSONObject.put("os", this.f9575i);
            jSONObject.put(AliyunLogKey.KEY_OSVERSION, this.f9576j);
            jSONObject.put(com.alipay.sdk.sys.a.f3323h, this.f9577k);
            jSONObject.put("ri", this.f9578l);
            jSONObject.put("api", this.f9579m);
            jSONObject.put("p", this.f9580n);
            jSONObject.put("rt", this.f9581o);
            jSONObject.put("msg", this.f9582p);
            jSONObject.put("st", this.f9583q);
            jSONObject.put("tt", this.f9584r);
            jSONObject.put("ot", this.f9585s);
            jSONObject.put("ep", this.f9586t.toString());
            jSONObject.put("aip", this.f9589w);
            return jSONObject.toString();
        } catch (Throwable th) {
            try {
                th.printStackTrace();
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }
}
