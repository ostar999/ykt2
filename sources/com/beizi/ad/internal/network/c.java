package com.beizi.ad.internal.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.beizi.ad.internal.utilities.HTTPGet;
import com.beizi.ad.internal.utilities.HTTPResponse;
import com.beizi.ad.internal.utilities.HttpErrorCode;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f4385a;

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<a> f4386b = new ArrayList<>();

    /* renamed from: c, reason: collision with root package name */
    private Timer f4387c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f4388d;

    public class a {

        /* renamed from: a, reason: collision with root package name */
        String f4393a;

        /* renamed from: b, reason: collision with root package name */
        int f4394b = 0;

        public a(String str) {
            this.f4393a = str;
        }
    }

    private c(Context context) {
        if (context != null) {
            this.f4388d = context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == 0;
        }
    }

    private void c(Context context) {
        if (this.f4387c == null) {
            final WeakReference weakReference = new WeakReference(context);
            Timer timer = new Timer();
            this.f4387c = timer;
            timer.scheduleAtFixedRate(new TimerTask() { // from class: com.beizi.ad.internal.network.c.1
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    Context context2 = (Context) weakReference.get();
                    if (context2 == null) {
                        c.this.a();
                        return;
                    }
                    while (!c.this.f4386b.isEmpty() && c.this.b(context2)) {
                        boolean z2 = false;
                        final a aVar = (a) c.this.f4386b.remove(0);
                        if (aVar.f4394b < 3) {
                            new HTTPGet(z2) { // from class: com.beizi.ad.internal.network.c.1.1
                                @Override // com.beizi.ad.internal.utilities.HTTPGet
                                public String getUrl() {
                                    return aVar.f4393a;
                                }

                                /* JADX WARN: Can't rename method to resolve collision */
                                @Override // com.beizi.ad.internal.utilities.HTTPGet, android.os.AsyncTask
                                public void onPostExecute(HTTPResponse hTTPResponse) {
                                    if (hTTPResponse == null || (!hTTPResponse.getSucceeded() && hTTPResponse.getErrorCode() == HttpErrorCode.CONNECTION_FAILURE)) {
                                        aVar.f4394b++;
                                        c.this.f4386b.add(aVar);
                                    }
                                }
                            }.execute(new Void[0]);
                        }
                    }
                    if (c.this.f4386b.isEmpty()) {
                        c.this.a();
                    }
                }
            }, com.heytap.mcssdk.constant.a.f7153q, com.heytap.mcssdk.constant.a.f7153q);
        }
    }

    public static c a(Context context) {
        if (f4385a == null) {
            f4385a = new c(context);
        }
        return f4385a;
    }

    public boolean b(Context context) {
        if (!this.f4388d) {
            return true;
        }
        if (context == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public synchronized void a(String str, Context context) {
        this.f4386b.add(new a(str));
        c(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        Timer timer = this.f4387c;
        if (timer != null) {
            timer.cancel();
            this.f4387c = null;
        }
    }
}
