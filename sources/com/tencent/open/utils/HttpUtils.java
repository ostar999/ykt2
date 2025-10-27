package com.tencent.open.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.log.SLog;
import com.tencent.tauth.IRequestListener;
import java.io.CharConversionException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.lang.reflect.InvocationTargetException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileLockInterruptionException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.zip.ZipException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLKeyException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class HttpUtils {

    public static class NetworkUnavailableException extends Exception {
        public static final String ERROR_INFO = "network unavailable";

        public NetworkUnavailableException(String str) {
            super(str);
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public final String f20649a;

        /* renamed from: b, reason: collision with root package name */
        public final int f20650b;

        private a(String str, int i2) {
            this.f20649a = str;
            this.f20650b = i2;
        }
    }

    private HttpUtils() {
    }

    private static void a(Context context, QQToken qQToken, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (str.indexOf("add_share") > -1 || str.indexOf("upload_pic") > -1 || str.indexOf("add_topic") > -1 || str.indexOf("set_user_face") > -1 || str.indexOf("add_t") > -1 || str.indexOf("add_pic_t") > -1 || str.indexOf("add_pic_url") > -1 || str.indexOf("add_video") > -1) {
            com.tencent.connect.a.a.a(context, qQToken, "requireApi", str);
        }
    }

    private static Map<String, byte[]> b(Bundle bundle) {
        HashMap map = new HashMap(0);
        if (bundle != null && bundle.size() != 0) {
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (obj instanceof byte[]) {
                    map.put(str, (byte[]) obj);
                }
            }
        }
        return map;
    }

    public static String encodeUrl(Bundle bundle) {
        return encodeUrl(a(bundle));
    }

    public static int getErrorCodeFromException(IOException iOException) {
        if (iOException instanceof CharConversionException) {
            return -20;
        }
        if (iOException instanceof MalformedInputException) {
            return -21;
        }
        if (iOException instanceof UnmappableCharacterException) {
            return -22;
        }
        if (iOException instanceof ClosedChannelException) {
            return -24;
        }
        if (iOException instanceof EOFException) {
            return -26;
        }
        if (iOException instanceof FileLockInterruptionException) {
            return -27;
        }
        if (iOException instanceof FileNotFoundException) {
            return -28;
        }
        if (iOException instanceof HttpRetryException) {
            return -29;
        }
        if (iOException instanceof SocketTimeoutException) {
            return -8;
        }
        if (iOException instanceof InvalidPropertiesFormatException) {
            return -30;
        }
        if (iOException instanceof MalformedURLException) {
            return -3;
        }
        if (iOException instanceof InvalidClassException) {
            return -33;
        }
        if (iOException instanceof InvalidObjectException) {
            return -34;
        }
        if (iOException instanceof NotActiveException) {
            return -35;
        }
        if (iOException instanceof NotSerializableException) {
            return -36;
        }
        if (iOException instanceof OptionalDataException) {
            return -37;
        }
        if (iOException instanceof StreamCorruptedException) {
            return -38;
        }
        if (iOException instanceof WriteAbortedException) {
            return -39;
        }
        if (iOException instanceof ProtocolException) {
            return -40;
        }
        if (iOException instanceof SSLHandshakeException) {
            return -41;
        }
        if (iOException instanceof SSLKeyException) {
            return -42;
        }
        if (iOException instanceof SSLPeerUnverifiedException) {
            return -43;
        }
        if (iOException instanceof SSLProtocolException) {
            return -44;
        }
        if (iOException instanceof BindException) {
            return -45;
        }
        if (iOException instanceof ConnectException) {
            return -46;
        }
        if (iOException instanceof NoRouteToHostException) {
            return -47;
        }
        if (iOException instanceof PortUnreachableException) {
            return -48;
        }
        if (iOException instanceof SyncFailedException) {
            return -49;
        }
        if (iOException instanceof UTFDataFormatException) {
            return -50;
        }
        if (iOException instanceof UnknownHostException) {
            return -51;
        }
        if (iOException instanceof UnknownServiceException) {
            return -52;
        }
        if (iOException instanceof UnsupportedEncodingException) {
            return -53;
        }
        return iOException instanceof ZipException ? -54 : -2;
    }

    public static a getProxy(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        if (context != null && (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.getType() == 0) {
            String strB = b(context);
            int iA = a(context);
            if (!TextUtils.isEmpty(strB) && iA >= 0) {
                return new a(strB, iA);
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x018b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static org.json.JSONObject request(com.tencent.connect.auth.QQToken r21, android.content.Context r22, java.lang.String r23, android.os.Bundle r24, java.lang.String r25) throws java.lang.IllegalAccessException, org.json.JSONException, java.io.IOException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException, com.tencent.open.utils.HttpUtils.HttpStatusException, com.tencent.open.utils.HttpUtils.NetworkUnavailableException {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.utils.HttpUtils.request(com.tencent.connect.auth.QQToken, android.content.Context, java.lang.String, android.os.Bundle, java.lang.String):org.json.JSONObject");
    }

    public static void requestAsync(final QQToken qQToken, final Context context, final String str, final Bundle bundle, final String str2, final IRequestListener iRequestListener) {
        SLog.i("openSDK_LOG.HttpUtils", "OpenApi requestAsync");
        j.a(new Runnable() { // from class: com.tencent.open.utils.HttpUtils.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    JSONObject jSONObjectRequest = HttpUtils.request(qQToken, context, str, bundle, str2);
                    IRequestListener iRequestListener2 = iRequestListener;
                    if (iRequestListener2 != null) {
                        iRequestListener2.onComplete(jSONObjectRequest);
                        SLog.i("openSDK_LOG.HttpUtils", "OpenApi onComplete");
                    }
                } catch (HttpStatusException e2) {
                    IRequestListener iRequestListener3 = iRequestListener;
                    if (iRequestListener3 != null) {
                        iRequestListener3.onHttpStatusException(e2);
                        SLog.e("openSDK_LOG.HttpUtils", "OpenApi requestAsync onHttpStatusException" + e2.toString());
                    }
                } catch (NetworkUnavailableException e3) {
                    IRequestListener iRequestListener4 = iRequestListener;
                    if (iRequestListener4 != null) {
                        iRequestListener4.onNetworkUnavailableException(e3);
                        SLog.e("openSDK_LOG.HttpUtils", "OpenApi requestAsync onNetworkUnavailableException" + e3.toString());
                    }
                } catch (MalformedURLException e4) {
                    IRequestListener iRequestListener5 = iRequestListener;
                    if (iRequestListener5 != null) {
                        iRequestListener5.onMalformedURLException(e4);
                        SLog.e("openSDK_LOG.HttpUtils", "OpenApi requestAsync MalformedURLException" + e4.toString());
                    }
                } catch (SocketTimeoutException e5) {
                    IRequestListener iRequestListener6 = iRequestListener;
                    if (iRequestListener6 != null) {
                        iRequestListener6.onSocketTimeoutException(e5);
                        SLog.e("openSDK_LOG.HttpUtils", "OpenApi requestAsync onSocketTimeoutException" + e5.toString());
                    }
                } catch (IOException e6) {
                    IRequestListener iRequestListener7 = iRequestListener;
                    if (iRequestListener7 != null) {
                        iRequestListener7.onIOException(e6);
                        SLog.e("openSDK_LOG.HttpUtils", "OpenApi requestAsync IOException" + e6.toString());
                    }
                } catch (JSONException e7) {
                    IRequestListener iRequestListener8 = iRequestListener;
                    if (iRequestListener8 != null) {
                        iRequestListener8.onJSONException(e7);
                        SLog.e("openSDK_LOG.HttpUtils", "OpenApi requestAsync JSONException" + e7.toString());
                    }
                } catch (Exception e8) {
                    IRequestListener iRequestListener9 = iRequestListener;
                    if (iRequestListener9 != null) {
                        iRequestListener9.onUnknowException(e8);
                        SLog.e("openSDK_LOG.HttpUtils", "OpenApi requestAsync onUnknowException" + e8.toString());
                    }
                }
            }
        });
    }

    public static class HttpStatusException extends Exception {
        public static final String ERROR_INFO = "http status code error:";
        public final int statusCode;

        public HttpStatusException(String str) {
            super(str);
            this.statusCode = -1;
        }

        public HttpStatusException(int i2) {
            super(ERROR_INFO + i2);
            this.statusCode = i2;
        }
    }

    public static String encodeUrl(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (String str : map.keySet()) {
            if (z2) {
                z2 = false;
            } else {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(str));
            sb.append("=");
            sb.append(URLEncoder.encode(map.get(str)));
        }
        return sb.toString();
    }

    private static String b(Context context) {
        return System.getProperty("http.proxyHost");
    }

    private static com.tencent.open.a.b a(String str, String str2, Bundle bundle) throws IOException {
        Bundle bundle2;
        if (bundle != null) {
            bundle2 = new Bundle(bundle);
        } else {
            bundle2 = new Bundle();
        }
        if (str2.equalsIgnoreCase("GET")) {
            Map<String, String> mapA = a(bundle2);
            Bundle bundleB = com.tencent.open.log.d.b(bundle2);
            if (bundleB != bundle2) {
                SLog.i("openSDK_LOG.HttpUtils", "-->openUrl encodedParam =" + bundleB.toString() + " -- url = " + str);
            } else {
                SLog.i("openSDK_LOG.HttpUtils", "-->openUrl encodedParam =" + mapA.toString() + " -- url = " + str);
            }
            return com.tencent.open.a.a.a().a(str, mapA);
        }
        if (str2.equalsIgnoreCase("POST")) {
            Map<String, String> mapA2 = a(bundle2);
            Map<String, byte[]> mapB = b(bundle2);
            if (mapB != null && mapB.size() != 0) {
                SLog.w("openSDK_LOG.HttpUtils", "openUrl: has binary " + mapB.size());
                return com.tencent.open.a.a.a().a(str, mapA2, mapB);
            }
            return com.tencent.open.a.a.a().b(str, mapA2);
        }
        SLog.e("openSDK_LOG.HttpUtils", "openUrl: http method " + str2 + " is not supported.");
        throw new IOException("http method is not supported.");
    }

    private static Map<String, String> a(Bundle bundle) {
        HashMap map = new HashMap();
        if (bundle != null && bundle.size() != 0) {
            for (String str : bundle.keySet()) {
                Object obj = bundle.get(str);
                if (!(obj instanceof String) && !(obj instanceof String[])) {
                    SLog.w("openSDK_LOG.HttpUtils", "parseBundleToMap: the type " + obj.getClass() + " is unsupported");
                } else if (obj instanceof String[]) {
                    String[] strArr = (String[]) obj;
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < strArr.length; i2++) {
                        if (i2 != 0) {
                            sb.append(",");
                        }
                        sb.append(strArr[i2]);
                    }
                    map.put(str, sb.toString());
                } else {
                    map.put(str, (String) obj);
                }
            }
        }
        return map;
    }

    private static int a(Context context) {
        String property = System.getProperty("http.proxyPort");
        if (!TextUtils.isEmpty(property)) {
            try {
                return Integer.parseInt(property);
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }
}
