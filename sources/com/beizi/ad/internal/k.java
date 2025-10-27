package com.beizi.ad.internal;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.beizi.ad.internal.r;
import com.beizi.ad.internal.utilities.HTTPGet;
import com.beizi.ad.internal.utilities.HTTPResponse;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.StringUtil;
import com.beizi.ad.internal.utilities.UrlUtil;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class k extends HTTPGet {

    /* renamed from: a, reason: collision with root package name */
    private String f4213a;

    /* renamed from: b, reason: collision with root package name */
    private r f4214b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f4215c;

    /* renamed from: d, reason: collision with root package name */
    private Context f4216d;

    /* renamed from: e, reason: collision with root package name */
    private a f4217e;

    /* renamed from: f, reason: collision with root package name */
    private ArrayList<String> f4218f;

    /* renamed from: g, reason: collision with root package name */
    private String f4219g;

    public class a implements r.a {

        /* renamed from: a, reason: collision with root package name */
        long f4220a;

        private a() {
            this.f4220a = 0L;
        }

        @Override // com.beizi.ad.internal.r.a
        public void a(boolean z2) {
            if (z2) {
                this.f4220a += 250;
            } else {
                this.f4220a = 0L;
            }
            if (this.f4220a >= 500) {
                k.this.a();
            }
        }
    }

    private k(String str, String str2, r rVar, Context context, ArrayList<String> arrayList) {
        super(false);
        this.f4215c = false;
        this.f4219g = "";
        this.f4213a = str2;
        this.f4214b = rVar;
        this.f4217e = new a();
        this.f4216d = context;
        this.f4218f = arrayList;
        this.f4219g = str;
    }

    @Override // com.beizi.ad.internal.utilities.HTTPGet
    public String getUrl() {
        View viewA = this.f4214b.a();
        if (viewA == null) {
            return UrlUtil.replaceToTouchEventUrl(this.f4213a, "", "", "", "", "", "", "");
        }
        if (!TextUtils.isEmpty(this.f4219g)) {
            this.f4213a = this.f4213a.replace("__REQUESTUUID__", this.f4219g);
        }
        return StringUtil.replaceView(0, viewA, this.f4213a);
    }

    public static k a(String str, String str2, r rVar, Context context, ArrayList<String> arrayList) {
        if (rVar == null) {
            return null;
        }
        k kVar = new k(str, str2, rVar, context, arrayList);
        rVar.a(kVar.f4217e);
        return kVar;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.beizi.ad.internal.utilities.HTTPGet, android.os.AsyncTask
    public void onPostExecute(HTTPResponse hTTPResponse) {
        HaoboLog.d(HaoboLog.nativeLogTag, "Impression tracked.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a() {
        Context context;
        if (!this.f4215c && (context = this.f4216d) != null) {
            com.beizi.ad.internal.network.c cVarA = com.beizi.ad.internal.network.c.a(context.getApplicationContext());
            if (cVarA.b(this.f4216d)) {
                execute(new Void[0]);
                this.f4214b.b(this.f4217e);
                this.f4217e = null;
            } else {
                cVarA.a(this.f4213a, this.f4216d);
            }
            this.f4215c = true;
            this.f4218f.remove(this.f4213a);
        }
    }
}
