package com.beizi.ad;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.beizi.ad.internal.network.ServerResponse;
import com.beizi.ad.internal.utilities.DeviceInfo;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.WebviewUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

/* loaded from: classes2.dex */
public class AdActivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f3606a = false;

    /* renamed from: b, reason: collision with root package name */
    static Class f3607b = AdActivity.class;

    /* renamed from: c, reason: collision with root package name */
    private a f3608c;

    /* renamed from: com.beizi.ad.AdActivity$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3610a;

        static {
            int[] iArr = new int[b.values().length];
            f3610a = iArr;
            try {
                iArr[b.none.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3610a[b.landscape.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3610a[b.portrait.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface a {
        void a();

        void b();

        void c();

        void d();

        void e();

        WebView f();
    }

    public enum b {
        portrait,
        landscape,
        none
    }

    public static Class a() {
        return f3607b;
    }

    public static void b(Activity activity) {
        activity.setRequestedOrientation(-1);
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        a aVar = this.f3608c;
        if (aVar != null) {
            aVar.b();
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String stringExtra = getIntent().getStringExtra("ACTIVITY_TYPE");
        if (TextUtils.isEmpty(stringExtra)) {
            HaoboLog.e(HaoboLog.baseLogTag, HaoboLog.getString(R.string.adactivity_no_type));
            finish();
        } else if ("INTERSTITIAL".equals(stringExtra)) {
            com.beizi.ad.internal.a.b bVar = new com.beizi.ad.internal.a.b(this);
            this.f3608c = bVar;
            bVar.a();
        } else if ("BROWSER".equals(stringExtra)) {
            com.beizi.ad.internal.a.a aVar = new com.beizi.ad.internal.a.a(this);
            this.f3608c = aVar;
            aVar.a();
        } else if (ServerResponse.EXTRAS_KEY_MRAID.equals(stringExtra)) {
            com.beizi.ad.internal.a.c cVar = new com.beizi.ad.internal.a.c(this);
            this.f3608c = cVar;
            cVar.a();
        } else if ("DOWNLOADBROWSER".equals(stringExtra)) {
            com.beizi.ad.internal.a.a aVar2 = new com.beizi.ad.internal.a.a(this);
            this.f3608c = aVar2;
            aVar2.a();
            new Thread(new Runnable() { // from class: com.beizi.ad.AdActivity.1
                @Override // java.lang.Runnable
                public void run() {
                    int i2 = 0;
                    while (i2 != 3) {
                        i2++;
                        if (AdActivity.f3606a) {
                            AdActivity.f3606a = false;
                            AdActivity.this.finish();
                            i2 = 3;
                        }
                        SystemClock.sleep(500L);
                    }
                }
            }).start();
        }
        CookieSyncManager.createInstance(this);
        CookieSyncManager cookieSyncManager = CookieSyncManager.getInstance();
        if (cookieSyncManager != null) {
            cookieSyncManager.startSync();
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override // android.app.Activity
    public void onDestroy() {
        a aVar = this.f3608c;
        if (aVar != null) {
            aVar.c();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public void onPause() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a aVar = this.f3608c;
        if (aVar != null) {
            WebviewUtil.onPause(aVar.f());
        }
        CookieSyncManager cookieSyncManager = CookieSyncManager.getInstance();
        if (cookieSyncManager != null) {
            cookieSyncManager.stopSync();
        }
        super.onPause();
    }

    @Override // android.app.Activity
    public void onResume() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a aVar = this.f3608c;
        if (aVar != null) {
            WebviewUtil.onResume(aVar.f());
        }
        CookieSyncManager cookieSyncManager = CookieSyncManager.getInstance();
        if (cookieSyncManager != null) {
            cookieSyncManager.startSync();
        }
        super.onResume();
    }

    public static void a(Activity activity) {
        b(activity, activity.getResources().getConfiguration().orientation);
    }

    @TargetApi(9)
    private static void b(Activity activity, int i2) {
        String str = DeviceInfo.getInstance().model;
        Locale locale = Locale.US;
        String upperCase = str.toUpperCase(locale);
        boolean z2 = DeviceInfo.getInstance().brand.toUpperCase(locale).equals("AMAZON") && (upperCase.equals("KFTT") || upperCase.equals("KFJWI") || upperCase.equals("KFJWA"));
        Display defaultDisplay = ((WindowManager) activity.getSystemService("window")).getDefaultDisplay();
        if (i2 == 1) {
            int rotation = defaultDisplay.getRotation();
            if (rotation == 1 || rotation == 2) {
                activity.setRequestedOrientation(9);
                return;
            } else {
                activity.setRequestedOrientation(1);
                return;
            }
        }
        if (i2 == 2) {
            int rotation2 = defaultDisplay.getRotation();
            if (z2) {
                if (rotation2 == 0 || rotation2 == 1) {
                    activity.setRequestedOrientation(8);
                    return;
                } else {
                    activity.setRequestedOrientation(0);
                    return;
                }
            }
            if (rotation2 == 0 || rotation2 == 1) {
                activity.setRequestedOrientation(0);
            } else {
                activity.setRequestedOrientation(8);
            }
        }
    }

    public static void a(Activity activity, int i2) {
        b(activity, i2);
    }

    public static void a(Activity activity, b bVar) {
        int i2 = activity.getResources().getConfiguration().orientation;
        int i3 = AnonymousClass2.f3610a[bVar.ordinal()];
        if (i3 != 1) {
            if (i3 == 2) {
                i2 = 2;
            } else if (i3 == 3) {
                i2 = 1;
            }
            b(activity, i2);
            return;
        }
        activity.setRequestedOrientation(-1);
    }
}
