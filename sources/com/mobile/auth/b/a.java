package com.mobile.auth.b;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes4.dex */
public class a {
    public static String a(Context context, String str, String str2) {
        InputStream inputStream;
        BufferedReader bufferedReader;
        InputStream inputStream2;
        String string = "";
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setRequestProperty("accept", MimeTypes.ANY_TYPE);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.addRequestProperty("Accept-Charset", "UTF-8");
                if (TextUtils.isEmpty(str2)) {
                    httpURLConnection.connect();
                } else {
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(httpURLConnection.getOutputStream()));
                    dataOutputStream.write(str2.getBytes("UTF-8"));
                    dataOutputStream.flush();
                    dataOutputStream.close();
                }
                if (httpURLConnection.getResponseCode() == 200) {
                    inputStream2 = httpURLConnection.getInputStream();
                    try {
                        StringBuilder sb = new StringBuilder();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream2));
                        while (true) {
                            try {
                                String line = bufferedReader.readLine();
                                if (line == null) {
                                    break;
                                }
                                sb.append(line);
                                sb.append("\n");
                            } catch (Throwable th) {
                                th = th;
                                Throwable th2 = th;
                                inputStream = inputStream2;
                                th = th2;
                                try {
                                    th.printStackTrace();
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Exception e2) {
                                            e = e2;
                                            e.printStackTrace();
                                            return string;
                                        }
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                    return string;
                                } finally {
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                }
                            }
                        }
                        string = sb.toString();
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedReader = null;
                    }
                } else {
                    inputStream2 = null;
                    bufferedReader = null;
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e4) {
                        e = e4;
                        e.printStackTrace();
                        return string;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                inputStream = null;
                bufferedReader = null;
            }
            return string;
        } catch (Throwable th5) {
            try {
                ExceptionProcessor.processException(th5);
                return null;
            } catch (Throwable th6) {
                ExceptionProcessor.processException(th6);
                return null;
            }
        }
    }
}
