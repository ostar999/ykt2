package com.beizi.fusion;

import android.content.Context;
import android.text.TextUtils;
import com.mbridge.msdk.MBridgeConstans;
import com.mbridge.msdk.MBridgeSDK;
import com.mbridge.msdk.out.MBridgeSDKFactory;
import com.mbridge.msdk.out.SDKInitStatusListener;
import java.util.Map;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: e, reason: collision with root package name */
    private static d f4732e;

    /* renamed from: a, reason: collision with root package name */
    private Context f4733a;

    /* renamed from: b, reason: collision with root package name */
    private volatile String f4734b;

    /* renamed from: c, reason: collision with root package name */
    private volatile String f4735c;

    /* renamed from: d, reason: collision with root package name */
    private volatile c f4736d;

    /* renamed from: f, reason: collision with root package name */
    private MBridgeSDK f4737f;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f4752a = new b();
    }

    /* renamed from: com.beizi.fusion.b$b, reason: collision with other inner class name */
    public static class C0061b implements SDKInitStatusListener {

        /* renamed from: a, reason: collision with root package name */
        private String f4793a;

        /* renamed from: b, reason: collision with root package name */
        private String f4794b;

        /* renamed from: c, reason: collision with root package name */
        private c f4795c;

        public C0061b(String str, String str2, c cVar) {
            this.f4793a = str;
            this.f4794b = str2;
            this.f4795c = cVar;
        }

        public void onInitFail(String str) {
            d unused = b.f4732e = d.SDK_STATE_INITIALIZE_FAILURE;
            c cVar = this.f4795c;
            if (cVar != null) {
                cVar.a("sdk initialize failed： an exception occurs");
            }
        }

        public void onInitSuccess() {
            d unused = b.f4732e = d.SDK_STATE_INITIALIZE_SUCCESS;
            c cVar = this.f4795c;
            if (cVar != null) {
                cVar.a(this.f4793a, this.f4794b);
            }
        }
    }

    public interface c {
        void a(String str);

        void a(String str, String str2);
    }

    public enum d {
        SDK_STATE_UN_INITIALIZE,
        SDK_STATE_INITIALIZING,
        SDK_STATE_INITIALIZE_SUCCESS,
        SDK_STATE_INITIALIZE_FAILURE
    }

    private b() {
        f4732e = d.SDK_STATE_UN_INITIALIZE;
    }

    public static b a() {
        return a.f4752a;
    }

    public synchronized void a(Context context, String str, String str2, boolean z2, Map<String, String> map, c cVar) {
        d dVar = f4732e;
        d dVar2 = d.SDK_STATE_INITIALIZING;
        if (dVar == dVar2) {
            if (cVar != null) {
                cVar.a("sdk is initializing");
            }
            return;
        }
        this.f4736d = cVar;
        if (a(context, str, str2)) {
            if (f4732e == d.SDK_STATE_INITIALIZE_SUCCESS && TextUtils.equals(this.f4735c, str2) && TextUtils.equals(this.f4734b, str)) {
                if (this.f4736d != null) {
                    this.f4736d.a(this.f4734b, this.f4735c);
                }
            } else {
                f4732e = dVar2;
                this.f4733a = context;
                this.f4734b = str;
                this.f4735c = str2;
                a(z2, map, this.f4736d);
            }
        }
    }

    private void a(boolean z2, Map<String, String> map, c cVar) {
        try {
            MBridgeConstans.DEBUG = z2;
            com.mbridge.msdk.system.a mBridgeSDK = MBridgeSDKFactory.getMBridgeSDK();
            this.f4737f = mBridgeSDK;
            Map mBConfigurationMap = mBridgeSDK.getMBConfigurationMap(this.f4735c, this.f4734b);
            if (map != null && !map.isEmpty()) {
                mBConfigurationMap.putAll(map);
            }
            this.f4737f.init(mBConfigurationMap, this.f4733a, new C0061b(this.f4734b, this.f4735c, this.f4736d));
        } catch (Exception e2) {
            f4732e = d.SDK_STATE_INITIALIZE_FAILURE;
            if (this.f4736d != null) {
                cVar.a(e2.getMessage());
            }
        }
    }

    private boolean a(Context context, String str, String str2) {
        boolean z2;
        String str3;
        boolean z3 = false;
        if (context == null) {
            str3 = "context must not null";
            z2 = false;
        } else {
            z2 = true;
            str3 = "";
        }
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            z3 = z2;
        } else if (TextUtils.isEmpty(str3)) {
            str3 = "appKey or appID must not null";
        } else {
            str3 = str3 + " & appKey or appID must not null";
        }
        if (!z3 && !TextUtils.isEmpty(str3) && this.f4736d != null) {
            f4732e = d.SDK_STATE_INITIALIZE_FAILURE;
            this.f4736d.a(str3);
        }
        return z3;
    }
}
