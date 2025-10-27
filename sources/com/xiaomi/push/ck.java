package com.xiaomi.push;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
class ck extends AsyncTask {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cn f24685a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f265a;

    private ck(cn cnVar) {
        this.f24685a = cnVar;
        this.f265a = true;
    }

    public /* synthetic */ ck(cn cnVar, cb cbVar) {
        this(cnVar);
    }

    private String a(String str) throws Throwable {
        String strA = bn.a(str, null);
        if (strA == null) {
            return null;
        }
        try {
            return new JSONObject(strA).getString("real-ip");
        } catch (JSONException unused) {
            return null;
        }
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public String doInBackground(String... strArr) {
        if (!this.f265a) {
            return null;
        }
        try {
            return a(strArr[0]);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: collision with other method in class and merged with bridge method [inline-methods] */
    public void onPostExecute(String str) {
        if (this.f265a) {
            this.f24685a.f270a.sendMessage(this.f24685a.f270a.obtainMessage(3, str));
        }
    }

    @Override // android.os.AsyncTask
    public void onCancelled() {
        this.f265a = false;
    }
}
