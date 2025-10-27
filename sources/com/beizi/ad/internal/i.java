package com.beizi.ad.internal;

import android.text.TextUtils;
import com.beizi.ad.internal.utilities.HTTPGet;
import com.beizi.ad.internal.utilities.HTTPResponse;

/* loaded from: classes2.dex */
public class i extends HTTPGet {

    /* renamed from: a, reason: collision with root package name */
    private String f4209a;

    public i(String str) {
        super(false);
        this.f4209a = str;
    }

    @Override // com.beizi.ad.internal.utilities.HTTPGet
    public String getUrl() {
        return this.f4209a;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.beizi.ad.internal.utilities.HTTPGet, android.os.AsyncTask
    public void onPostExecute(HTTPResponse hTTPResponse) {
        if (hTTPResponse == null || !hTTPResponse.getSucceeded()) {
            return;
        }
        int code = hTTPResponse.getCode();
        String locationUrl = hTTPResponse.getLocationUrl();
        if (code != 302 || TextUtils.isEmpty(locationUrl)) {
            return;
        }
        new i(locationUrl).execute(new Void[0]);
    }
}
