package com.xiaomi.push;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.aliyun.vod.log.core.AliyunLogCommon;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class as {

    /* renamed from: a, reason: collision with root package name */
    public static final Pattern f24611a = Pattern.compile("([^\\s;]+)(.*)");

    /* renamed from: b, reason: collision with root package name */
    public static final Pattern f24612b = Pattern.compile("(.*?charset\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);

    /* renamed from: c, reason: collision with root package name */
    public static final Pattern f24613c = Pattern.compile("(\\<\\?xml\\s+.*?encoding\\s*=[^a-zA-Z0-9]*)([-a-zA-Z0-9]+)(.*)", 2);

    public static final class a extends FilterInputStream {

        /* renamed from: a, reason: collision with root package name */
        private boolean f24614a;

        public a(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i2, int i3) {
            int i4;
            if (!this.f24614a && (i4 = super.read(bArr, i2, i3)) != -1) {
                return i4;
            }
            this.f24614a = true;
            return -1;
        }
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public int f24615a;

        /* renamed from: a, reason: collision with other field name */
        public Map<String, String> f194a;

        public String toString() {
            return String.format("resCode = %1$d, headers = %2$s", Integer.valueOf(this.f24615a), this.f194a.toString());
        }
    }

    public static int a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return -1;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return -1;
            }
            return activeNetworkInfo.getType();
        } catch (Exception unused) {
            return -1;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static NetworkInfo m200a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return null;
            }
            return connectivityManager.getActiveNetworkInfo();
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static aq a(Context context, String str, String str2, Map<String, String> map, String str3) {
        BufferedReader bufferedReader;
        aq aqVar = new aq();
        try {
            try {
                try {
                    HttpURLConnection httpURLConnectionM202a = m202a(context, m203a(str));
                    httpURLConnectionM202a.setConnectTimeout(10000);
                    httpURLConnectionM202a.setReadTimeout(15000);
                    String str4 = str2;
                    if (str2 == 0) {
                        str4 = "GET";
                    }
                    httpURLConnectionM202a.setRequestMethod(str4);
                    if (map != null) {
                        for (String str5 : map.keySet()) {
                            httpURLConnectionM202a.setRequestProperty(str5, map.get(str5));
                        }
                    }
                    int i2 = 0;
                    if (!TextUtils.isEmpty(str3)) {
                        httpURLConnectionM202a.setDoOutput(true);
                        byte[] bytes = str3.getBytes();
                        OutputStream outputStream = httpURLConnectionM202a.getOutputStream();
                        try {
                            outputStream.write(bytes, 0, bytes.length);
                            outputStream.flush();
                            outputStream.close();
                        } catch (IOException e2) {
                            e = e2;
                            throw new IOException("err while request " + str + ":" + e.getClass().getSimpleName());
                        } catch (Throwable th) {
                            th = th;
                            throw new IOException(th.getMessage());
                        }
                    }
                    aqVar.f24610a = httpURLConnectionM202a.getResponseCode();
                    Log.d("com.xiaomi.common.Network", "Http POST Response Code: " + aqVar.f24610a);
                    while (true) {
                        String headerFieldKey = httpURLConnectionM202a.getHeaderFieldKey(i2);
                        String headerField = httpURLConnectionM202a.getHeaderField(i2);
                        if (headerFieldKey == null && headerField == null) {
                            try {
                                break;
                            } catch (IOException unused) {
                                bufferedReader = new BufferedReader(new InputStreamReader(new a(httpURLConnectionM202a.getErrorStream())));
                            }
                        } else {
                            aqVar.f193a.put(headerFieldKey, headerField);
                            i2 = i2 + 1 + 1;
                        }
                    }
                    bufferedReader = new BufferedReader(new InputStreamReader(new a(httpURLConnectionM202a.getInputStream())));
                    try {
                        StringBuffer stringBuffer = new StringBuffer();
                        String property = System.getProperty("line.separator");
                        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                            stringBuffer.append(line);
                            stringBuffer.append(property);
                        }
                        aqVar.f192a = stringBuffer.toString();
                        bufferedReader.close();
                        y.a((Closeable) null);
                        y.a((Closeable) null);
                        return aqVar;
                    } catch (IOException e3) {
                        e = e3;
                        throw new IOException("err while request " + str + ":" + e.getClass().getSimpleName());
                    } catch (Throwable th2) {
                        th = th2;
                        throw new IOException(th.getMessage());
                    }
                } catch (IOException e4) {
                    e = e4;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            y.a((Closeable) null);
            y.a((Closeable) str2);
            throw th4;
        }
    }

    public static aq a(Context context, String str, Map<String, String> map) {
        return a(context, str, "POST", (Map<String, String>) null, a(map));
    }

    public static InputStream a(Context context, URL url, boolean z2, String str, String str2) {
        return a(context, url, z2, str, str2, null, null);
    }

    public static InputStream a(Context context, URL url, boolean z2, String str, String str2, Map<String, String> map, b bVar) throws IOException {
        if (context == null) {
            throw new IllegalArgumentException(com.umeng.analytics.pro.d.R);
        }
        if (url == null) {
            throw new IllegalArgumentException("url");
        }
        URL url2 = !z2 ? new URL(a(url.toString())) : url;
        try {
            HttpURLConnection.setFollowRedirects(true);
            HttpURLConnection httpURLConnectionM202a = m202a(context, url2);
            httpURLConnectionM202a.setConnectTimeout(10000);
            httpURLConnectionM202a.setReadTimeout(15000);
            if (!TextUtils.isEmpty(str)) {
                httpURLConnectionM202a.setRequestProperty("User-Agent", str);
            }
            if (str2 != null) {
                httpURLConnectionM202a.setRequestProperty("Cookie", str2);
            }
            if (map != null) {
                for (String str3 : map.keySet()) {
                    httpURLConnectionM202a.setRequestProperty(str3, map.get(str3));
                }
            }
            if (bVar != null && (url.getProtocol().equals("http") || url.getProtocol().equals("https"))) {
                bVar.f24615a = httpURLConnectionM202a.getResponseCode();
                if (bVar.f194a == null) {
                    bVar.f194a = new HashMap();
                }
                int i2 = 0;
                while (true) {
                    String headerFieldKey = httpURLConnectionM202a.getHeaderFieldKey(i2);
                    String headerField = httpURLConnectionM202a.getHeaderField(i2);
                    if (headerFieldKey == null && headerField == null) {
                        break;
                    }
                    if (!TextUtils.isEmpty(headerFieldKey) && !TextUtils.isEmpty(headerField)) {
                        bVar.f194a.put(headerFieldKey, headerField);
                    }
                    i2++;
                }
            }
            return new a(httpURLConnectionM202a.getInputStream());
        } catch (IOException e2) {
            throw new IOException("IOException:" + e2.getClass().getSimpleName());
        } catch (Throwable th) {
            throw new IOException(th.getMessage());
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m201a(Context context) {
        if (d(context)) {
            return "wifi";
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return "";
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return "";
            }
            return (activeNetworkInfo.getTypeName() + "-" + activeNetworkInfo.getSubtypeName() + "-" + activeNetworkInfo.getExtraInfo()).toLowerCase();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String a(Context context, URL url) {
        return a(context, url, false, null, "UTF-8", null);
    }

    public static String a(Context context, URL url, boolean z2, String str, String str2, String str3) throws Throwable {
        InputStream inputStreamA;
        try {
            inputStreamA = a(context, url, z2, str, str3);
        } catch (Throwable th) {
            th = th;
            inputStreamA = null;
        }
        try {
            StringBuilder sb = new StringBuilder(1024);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamA, str2));
            char[] cArr = new char[4096];
            while (true) {
                int i2 = bufferedReader.read(cArr);
                if (-1 == i2) {
                    y.a(inputStreamA);
                    return sb.toString();
                }
                sb.append(cArr, 0, i2);
            }
        } catch (Throwable th2) {
            th = th2;
            y.a(inputStreamA);
            throw th;
        }
    }

    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        new String();
        return String.format("%s&key=%s", str, ax.a(String.format("%sbe988a6134bc8254465424e5a70ef037", str)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String a(String str, Map<String, String> map, File file, String str2) {
        if (!file.exists()) {
            return null;
        }
        String name = file.getName();
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*****");
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                    }
                }
                httpURLConnection.setFixedLengthStreamingMode(name.length() + 77 + ((int) file.length()) + str2.length());
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes("--*****\r\n");
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + str2 + "\";filename=\"" + file.getName() + "\"\r\n");
                dataOutputStream.writeBytes("\r\n");
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i2 = fileInputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        dataOutputStream.write(bArr, 0, i2);
                        dataOutputStream.flush();
                    }
                    dataOutputStream.writeBytes("\r\n");
                    dataOutputStream.writeBytes("--");
                    dataOutputStream.writeBytes("*****");
                    dataOutputStream.writeBytes("--");
                    dataOutputStream.writeBytes("\r\n");
                    dataOutputStream.flush();
                    StringBuffer stringBuffer = new StringBuffer();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new a(httpURLConnection.getInputStream())));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                String string = stringBuffer.toString();
                                y.a(fileInputStream);
                                y.a(bufferedReader);
                                return string;
                            }
                            stringBuffer.append(line);
                        } catch (IOException e2) {
                            e = e2;
                            throw new IOException("IOException:" + e.getClass().getSimpleName());
                        } catch (Throwable th) {
                            th = th;
                            throw new IOException(th.getMessage());
                        }
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e4) {
                e = e4;
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            y.a((Closeable) null);
            y.a((Closeable) file);
            throw th4;
        }
    }

    public static String a(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                try {
                    stringBuffer.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    stringBuffer.append("=");
                    stringBuffer.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    stringBuffer.append("&");
                } catch (UnsupportedEncodingException e2) {
                    Log.d("com.xiaomi.common.Network", "Failed to convert from params map to string: " + e2.toString());
                    Log.d("com.xiaomi.common.Network", "map: " + map.toString());
                    return null;
                }
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static HttpURLConnection m202a(Context context, URL url) {
        return (HttpURLConnection) (("http".equals(url.getProtocol()) && m204a(context)) ? url.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80))) : url.openConnection());
    }

    /* renamed from: a, reason: collision with other method in class */
    private static URL m203a(String str) {
        return new URL(str);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m204a(Context context) {
        ConnectivityManager connectivityManager;
        if (!"CN".equalsIgnoreCase(((TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)).getSimCountryIso())) {
            return false;
        }
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception unused) {
        }
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        String extraInfo = activeNetworkInfo.getExtraInfo();
        return !TextUtils.isEmpty(extraInfo) && extraInfo.length() >= 3 && extraInfo.contains("ctwap");
    }

    public static boolean b(Context context) {
        return a(context) >= 0;
    }

    public static boolean c(Context context) {
        NetworkInfo activeNetworkInfo;
        try {
            activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception unused) {
            activeNetworkInfo = null;
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean d(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && 1 == activeNetworkInfo.getType();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean e(Context context) {
        return f(context) || g(context) || h(context);
    }

    public static boolean f(Context context) {
        NetworkInfo networkInfoM200a = m200a(context);
        return networkInfoM200a != null && networkInfoM200a.getType() == 0 && 13 == networkInfoM200a.getSubtype();
    }

    public static boolean g(Context context) {
        NetworkInfo networkInfoM200a = m200a(context);
        if (networkInfoM200a == null || networkInfoM200a.getType() != 0) {
            return false;
        }
        String subtypeName = networkInfoM200a.getSubtypeName();
        if (!"TD-SCDMA".equalsIgnoreCase(subtypeName) && !"CDMA2000".equalsIgnoreCase(subtypeName) && !"WCDMA".equalsIgnoreCase(subtypeName)) {
            switch (networkInfoM200a.getSubtype()) {
            }
            return false;
        }
        return true;
    }

    public static boolean h(Context context) {
        NetworkInfo networkInfoM200a = m200a(context);
        if (networkInfoM200a == null || networkInfoM200a.getType() != 0) {
            return false;
        }
        int subtype = networkInfoM200a.getSubtype();
        return subtype == 1 || subtype == 2 || subtype == 4 || subtype == 7 || subtype == 11;
    }
}
