package com.tencent.tbs.one.impl.d;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.tbs.one.impl.a.b;
import com.tencent.tbs.one.impl.a.d;
import com.tencent.tbs.one.impl.a.o;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import org.eclipse.jetty.http.HttpHeaderValues;

/* loaded from: classes6.dex */
public final class a extends b<Integer> {

    /* renamed from: b, reason: collision with root package name */
    public String f22041b;

    /* renamed from: c, reason: collision with root package name */
    public String f22042c;

    /* renamed from: d, reason: collision with root package name */
    public Map<String, String> f22043d;

    /* renamed from: e, reason: collision with root package name */
    public byte[] f22044e;

    /* renamed from: f, reason: collision with root package name */
    public InterfaceC0368a f22045f;

    /* renamed from: g, reason: collision with root package name */
    public boolean f22046g;

    /* renamed from: h, reason: collision with root package name */
    public Context f22047h;

    /* renamed from: i, reason: collision with root package name */
    public int f22048i;

    /* renamed from: j, reason: collision with root package name */
    public int f22049j;

    /* renamed from: com.tencent.tbs.one.impl.d.a$a, reason: collision with other inner class name */
    public interface InterfaceC0368a {
        void a(int i2, Map<String, List<String>> map, InputStream inputStream);
    }

    public a(Context context, String str) {
        this(context, str, "GET");
    }

    public a(Context context, String str, String str2) {
        this(context, str, str2, (byte) 0);
    }

    public a(Context context, String str, String str2, byte b3) {
        this(context, str, str2, null, null);
    }

    public a(Context context, String str, String str2, Map<String, String> map, byte[] bArr) {
        this.f22047h = context;
        this.f22041b = str;
        this.f22042c = str2;
        this.f22043d = map;
        this.f22044e = bArr;
    }

    private HttpURLConnection a(String str) {
        String str2;
        int i2;
        a(5);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(20000);
            httpURLConnection.setRequestProperty("Connection", "close");
            return httpURLConnection;
        } catch (MalformedURLException e2) {
            e = e2;
            str2 = "Failed to parse url " + str;
            i2 = 202;
            a(i2, str2, e);
            return null;
        } catch (IOException e3) {
            e = e3;
            str2 = "Failed to open connection, url: " + str;
            i2 = 203;
            a(i2, str2, e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void a(com.tencent.tbs.one.impl.d.a r8, java.lang.String r9, java.lang.String r10, java.util.Map r11, byte[] r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.d.a.a(com.tencent.tbs.one.impl.d.a, java.lang.String, java.lang.String, java.util.Map, byte[]):void");
    }

    private boolean a(HttpURLConnection httpURLConnection, String str, String str2, Map<String, String> map, byte[] bArr) throws IOException {
        a(10);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        OutputStream outputStream = null;
        try {
            try {
                try {
                    httpURLConnection.setRequestMethod(str2);
                    if (str2.equals("POST") && bArr != null) {
                        outputStream = httpURLConnection.getOutputStream();
                        if (map != null) {
                            String str3 = map.get("Content-Encoding");
                            if (!TextUtils.isEmpty(str3) && str3.equals(HttpHeaderValues.GZIP)) {
                                outputStream = new GZIPOutputStream(new BufferedOutputStream(outputStream, 204800));
                            }
                        }
                        outputStream.write(bArr);
                        outputStream.flush();
                    }
                    d.a(outputStream);
                    return true;
                } catch (Exception e2) {
                    if (!b(str)) {
                        a(205, "Failed to send request, url: " + str, e2);
                    }
                    d.a(outputStream);
                    return false;
                }
            } catch (ProtocolException e3) {
                a(204, "Failed to parse http method " + str2 + ", url: " + str, e3);
                d.a(outputStream);
                return false;
            }
        } catch (Throwable th) {
            d.a(outputStream);
            throw th;
        }
    }

    private boolean b(final String str) {
        int i2 = this.f22048i + 1;
        this.f22048i = i2;
        if (i2 > 3) {
            return false;
        }
        o.d(new Runnable() { // from class: com.tencent.tbs.one.impl.d.a.3
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                a aVar = a.this;
                a.a(aVar, str, aVar.f22042c, aVar.f22043d, aVar.f22044e);
            }
        });
        return true;
    }

    private boolean c() {
        if (!this.f21723a) {
            return true;
        }
        a(104, "Aborted", null);
        return false;
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a() {
        o.d(new Runnable() { // from class: com.tencent.tbs.one.impl.d.a.1
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                a aVar = a.this;
                a.a(aVar, aVar.f22041b, aVar.f22042c, aVar.f22043d, aVar.f22044e);
            }
        });
    }
}
