package com.meizu.cloud.pushsdk.b.c;

import com.meizu.cloud.pushsdk.b.c.k;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes4.dex */
public class e implements a {

    /* renamed from: a, reason: collision with root package name */
    com.meizu.cloud.pushsdk.b.h.a f9078a = new com.meizu.cloud.pushsdk.b.h.a(null);

    private static l a(final HttpURLConnection httpURLConnection) throws IOException {
        if (!httpURLConnection.getDoInput()) {
            return null;
        }
        final com.meizu.cloud.pushsdk.b.g.c cVarA = com.meizu.cloud.pushsdk.b.g.f.a(com.meizu.cloud.pushsdk.b.g.f.a(a(httpURLConnection.getResponseCode()) ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream()));
        return new l() { // from class: com.meizu.cloud.pushsdk.b.c.e.1
            @Override // com.meizu.cloud.pushsdk.b.c.l
            public com.meizu.cloud.pushsdk.b.g.c a() {
                return cVarA;
            }
        };
    }

    public static void a(HttpURLConnection httpURLConnection, i iVar) throws IOException {
        String str;
        String str2;
        int iC = iVar.c();
        if (iC != 0) {
            if (iC == 1) {
                str2 = "POST";
            } else if (iC == 2) {
                str2 = "PUT";
            } else if (iC == 3) {
                str = "DELETE";
            } else if (iC == 4) {
                str = "HEAD";
            } else {
                if (iC != 5) {
                    throw new IllegalStateException("Unknown method type.");
                }
                str2 = "PATCH";
            }
            httpURLConnection.setRequestMethod(str2);
            b(httpURLConnection, iVar);
            return;
        }
        str = "GET";
        httpURLConnection.setRequestMethod(str);
    }

    public static boolean a(int i2) {
        return i2 >= 200 && i2 < 300;
    }

    private HttpURLConnection b(i iVar) throws IOException {
        String string = iVar.a().toString();
        HttpURLConnection httpURLConnectionA = a(new URL(string));
        httpURLConnectionA.setConnectTimeout(60000);
        httpURLConnectionA.setReadTimeout(60000);
        httpURLConnectionA.setUseCaches(false);
        httpURLConnectionA.setDoInput(true);
        if (iVar.f() && string.startsWith("https://api-push.meizu.com")) {
            ((HttpsURLConnection) httpURLConnectionA).setSSLSocketFactory(this.f9078a);
        }
        return httpURLConnectionA;
    }

    private static void b(HttpURLConnection httpURLConnection, i iVar) throws IOException {
        j jVarE = iVar.e();
        if (jVarE != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", jVarE.a().toString());
            com.meizu.cloud.pushsdk.b.g.b bVarA = com.meizu.cloud.pushsdk.b.g.f.a(com.meizu.cloud.pushsdk.b.g.f.a(httpURLConnection.getOutputStream()));
            jVarE.a(bVarA);
            bVarA.close();
        }
    }

    @Override // com.meizu.cloud.pushsdk.b.c.a
    public k a(i iVar) throws IOException {
        HttpURLConnection httpURLConnectionB = b(iVar);
        for (String str : iVar.d().b()) {
            String strA = iVar.a(str);
            com.meizu.cloud.pushsdk.b.a.a.b("current header name " + str + " value " + strA);
            httpURLConnectionB.addRequestProperty(str, strA);
        }
        a(httpURLConnectionB, iVar);
        return new k.a().a(httpURLConnectionB.getResponseCode()).a(iVar.d()).a(httpURLConnectionB.getResponseMessage()).a(iVar).a(a(httpURLConnectionB)).a();
    }

    public HttpURLConnection a(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        return httpURLConnection;
    }
}
