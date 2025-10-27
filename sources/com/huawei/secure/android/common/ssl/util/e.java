package com.huawei.secure.android.common.ssl.util;

import android.content.Context;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class e extends AsyncTask<Context, Integer, Boolean> {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8413a = "e";

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Boolean doInBackground(Context... contextArr) throws IOException {
        InputStream bksFromTss;
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            bksFromTss = BksUtil.getBksFromTss(contextArr[0]);
        } catch (Exception e2) {
            g.b(f8413a, "doInBackground: exception : " + e2.getMessage());
            bksFromTss = null;
        }
        g.a(f8413a, "doInBackground: get bks from hms tss cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        if (bksFromTss == null) {
            return Boolean.FALSE;
        }
        f.a(bksFromTss);
        return Boolean.TRUE;
    }

    @Override // android.os.AsyncTask
    public void onPreExecute() {
        g.a(f8413a, "onPreExecute");
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(Boolean bool) {
        if (bool.booleanValue()) {
            g.c(f8413a, "onPostExecute: upate done");
        } else {
            g.b(f8413a, "onPostExecute: upate failed");
        }
    }

    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onProgressUpdate(Integer... numArr) {
        g.c(f8413a, "onProgressUpdate");
    }
}
