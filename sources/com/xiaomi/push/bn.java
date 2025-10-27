package com.xiaomi.push;

import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes6.dex */
public class bn {
    public static String a(String str, Map map) throws Throwable {
        HttpURLConnection httpURLConnection;
        String string = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            URL url = new URL(str);
            BufferedReader bufferedReader = null;
            try {
                httpURLConnection = url.getProtocol().toLowerCase().equals("https") ? (HttpsURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection();
                try {
                    httpURLConnection.setConnectTimeout(30000);
                    httpURLConnection.setReadTimeout(30000);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(false);
                    if (map != null && map.size() > 0) {
                        for (String str2 : map.keySet()) {
                            httpURLConnection.addRequestProperty(str2, (String) map.get(str2));
                        }
                    }
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {
                        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                        try {
                            StringBuffer stringBuffer = new StringBuffer();
                            while (true) {
                                String line = bufferedReader2.readLine();
                                if (line == null) {
                                    break;
                                }
                                stringBuffer.append(line);
                            }
                            string = stringBuffer.toString();
                            bufferedReader2.close();
                            bufferedReader = bufferedReader2;
                        } catch (Exception unused) {
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            return string;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused2) {
                                    throw th;
                                }
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            throw th;
                        }
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Exception unused3) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused4) {
                httpURLConnection = null;
            } catch (Throwable th3) {
                th = th3;
                httpURLConnection = null;
            }
            httpURLConnection.disconnect();
        } catch (MalformedURLException | Exception unused5) {
        }
        return string;
    }
}
