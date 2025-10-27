package com.tencent.liteav.basic.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    protected static volatile g f18724a;

    /* renamed from: c, reason: collision with root package name */
    private SharedPreferences f18726c;

    /* renamed from: e, reason: collision with root package name */
    private Context f18728e;

    /* renamed from: b, reason: collision with root package name */
    private final String f18725b = "TXCSpUtil";

    /* renamed from: d, reason: collision with root package name */
    private final Object f18727d = new Object();

    public enum a {
        CODEC_TYPE_H264(0),
        CODEC_TYPE_H265(1);

        private final int value;

        a(int i2) {
            this.value = i2;
        }

        public int a() {
            return this.value;
        }

        public static a a(int i2) {
            for (a aVar : values()) {
                if (aVar.a() == i2) {
                    return aVar;
                }
            }
            return CODEC_TYPE_H264;
        }
    }

    private g() {
    }

    public static g a() {
        if (f18724a == null) {
            synchronized (g.class) {
                if (f18724a == null) {
                    f18724a = new g();
                }
            }
        }
        return f18724a;
    }

    public boolean b(String str, boolean z2) {
        Context context = this.f18728e;
        try {
        } catch (Exception e2) {
            TXCLog.e("TXCSpUtil", "saveConfigInfo: error: " + str + ", " + z2 + " ; " + e2);
        }
        synchronized (this.f18727d) {
            if (this.f18726c == null && context != null) {
                this.f18726c = context.getSharedPreferences("liteav_hw_encoder_config", 0);
            }
            SharedPreferences sharedPreferences = this.f18726c;
            if (sharedPreferences == null) {
                return z2;
            }
            return sharedPreferences.getBoolean(str, z2);
        }
    }

    public void a(Context context) {
        this.f18728e = context.getApplicationContext();
    }

    public void a(String str, boolean z2) {
        Context context = this.f18728e;
        try {
            synchronized (this.f18727d) {
                if (this.f18726c == null && context != null) {
                    this.f18726c = context.getSharedPreferences("liteav_hw_encoder_config", 0);
                }
                SharedPreferences sharedPreferences = this.f18726c;
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putBoolean(str, z2).commit();
                }
            }
        } catch (Exception e2) {
            TXCLog.e("TXCSpUtil", "saveConfigInfo: error: " + str + ", " + z2 + " ; " + e2);
        }
    }

    public int b(String str, int i2) {
        Context context = this.f18728e;
        try {
        } catch (Exception e2) {
            TXCLog.e("TXCSpUtil", "getLastEncodeType: error.", e2);
        }
        synchronized (this.f18727d) {
            if (this.f18726c == null && context != null) {
                this.f18726c = context.getSharedPreferences("liteav_hw_encoder_config", 0);
            }
            SharedPreferences sharedPreferences = this.f18726c;
            if (sharedPreferences == null) {
                return i2;
            }
            return sharedPreferences.getInt(str, i2);
        }
    }

    public void a(String str, int i2) {
        Context context = this.f18728e;
        try {
            synchronized (this.f18727d) {
                if (this.f18726c == null && context != null) {
                    this.f18726c = context.getSharedPreferences("liteav_hw_encoder_config", 0);
                }
                SharedPreferences sharedPreferences = this.f18726c;
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putInt(str, i2).commit();
                }
            }
        } catch (Exception e2) {
            TXCLog.e("TXCSpUtil", "setLastEncodeType: error: " + str + " , " + i2 + " ; " + e2);
        }
    }
}
