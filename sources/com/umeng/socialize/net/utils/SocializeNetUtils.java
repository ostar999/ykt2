package com.umeng.socialize.net.utils;

import android.os.Bundle;
import com.just.agentweb.DefaultWebClient;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

/* loaded from: classes6.dex */
public class SocializeNetUtils {
    private static final String TAG = "SocializeNetUtils";

    public static String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line + "/n");
                    } catch (IOException e2) {
                        SLog.error(UmengText.NET.TOOL, e2);
                    }
                } catch (IOException e3) {
                    SLog.error(UmengText.NET.TOOL, e3);
                    inputStream.close();
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    SLog.error(UmengText.NET.TOOL, e4);
                }
                throw th;
            }
        }
        inputStream.close();
        return sb.toString();
    }

    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String str2 : str.split("&")) {
                String[] strArrSplit = str2.split("=");
                try {
                    bundle.putString(URLDecoder.decode(strArrSplit[0], "UTF-8"), URLDecoder.decode(strArrSplit[1], "UTF-8"));
                } catch (UnsupportedEncodingException e2) {
                    SLog.error(UmengText.NET.TOOL, e2);
                }
            }
        }
        return bundle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x00da A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v12, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.IOException, java.lang.Throwable] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x00b4 -> B:94:0x00d5). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:76:0x00e4 -> B:97:0x0105). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getNetData(java.lang.String r5) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.utils.SocializeNetUtils.getNetData(java.lang.String):byte[]");
    }

    public static boolean isConSpeCharacters(String str) {
        return str.replaceAll("[一-龥]*[a-z]*[A-Z]*\\d*-*_*\\s*", "").length() != 0;
    }

    public static boolean isSelfAppkey(String str) {
        return str.equals("5126ff896c738f2bfa000438") && !ContextUtil.getPackageName().equals("com.umeng.soexample");
    }

    public static Bundle parseUri(String str) {
        try {
            return decodeUrl(new URI(str).getQuery());
        } catch (Exception e2) {
            SLog.error(UmengText.NET.TOOL, e2);
            return new Bundle();
        }
    }

    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str);
            Bundle bundleDecodeUrl = decodeUrl(url.getQuery());
            bundleDecodeUrl.putAll(decodeUrl(url.getRef()));
            return bundleDecodeUrl;
        } catch (MalformedURLException e2) {
            SLog.error(UmengText.NET.TOOL, e2);
            return new Bundle();
        }
    }

    public static String request(String str) throws IOException {
        try {
            URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
            if (uRLConnectionOpenConnection == null) {
                return "";
            }
            uRLConnectionOpenConnection.connect();
            InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
            return inputStream == null ? "" : convertStreamToString(inputStream);
        } catch (Exception e2) {
            SLog.error(UmengText.NET.TOOL, e2);
            return "";
        }
    }

    public static boolean startWithHttp(String str) {
        return str.startsWith(DefaultWebClient.HTTP_SCHEME) || str.startsWith(DefaultWebClient.HTTPS_SCHEME);
    }
}
