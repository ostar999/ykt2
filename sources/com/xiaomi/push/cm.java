package com.xiaomi.push;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.hutool.core.text.StrPool;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class cm extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private final int f24687a;

    /* renamed from: a, reason: collision with other field name */
    private WeakReference f266a;

    /* renamed from: b, reason: collision with root package name */
    private WeakReference f24688b;

    public cm(Handler handler, Context context, int i2) {
        this.f266a = new WeakReference(handler);
        this.f24688b = new WeakReference(context);
        this.f24687a = i2;
        start();
    }

    private Context a() {
        if (this.f266a == null) {
            return null;
        }
        return (Context) this.f24688b.get();
    }

    /* renamed from: a, reason: collision with other method in class */
    private Handler m290a() {
        WeakReference weakReference = this.f266a;
        if (weakReference == null) {
            return null;
        }
        return (Handler) weakReference.get();
    }

    /* renamed from: a, reason: collision with other method in class */
    private HashMap m291a() throws Throwable {
        HashMap map = new HashMap();
        String strM287a = cj.m287a(a());
        if (strM287a != null) {
            String strSubstring = strM287a.substring(0, strM287a.lastIndexOf(StrPool.DOT));
            ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(40);
            try {
                Runnable[] runnableArr = new Runnable[255];
                for (int i2 = 1; i2 < 255; i2++) {
                    runnableArr[i2] = new ce(this, strSubstring + StrPool.DOT + i2);
                }
                for (int i3 = 1; i3 < 255; i3++) {
                    executorServiceNewFixedThreadPool.execute(runnableArr[i3]);
                }
            } catch (Exception unused) {
            } finally {
                executorServiceNewFixedThreadPool.shutdown();
                try {
                    executorServiceNewFixedThreadPool.awaitTermination(com.heytap.mcssdk.constant.a.f7153q, TimeUnit.MILLISECONDS);
                } catch (Exception unused2) {
                }
            }
            try {
                executorServiceNewFixedThreadPool.awaitTermination(com.heytap.mcssdk.constant.a.f7153q, TimeUnit.MILLISECONDS);
            } catch (Exception unused3) {
            }
            a(strSubstring, 1, 255, map);
        }
        return map;
    }

    public static void a(Context context, Handler handler, int i2) {
        new cm(handler, context, i2);
    }

    private void a(String str, int i2, int i3, HashMap map) throws Throwable {
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));
            } catch (FileNotFoundException unused) {
            } catch (IOException unused2) {
            } catch (Throwable th) {
                th = th;
            }
            try {
                bufferedReader.readLine();
                int i4 = (i3 - i2) + 1;
                String[] strArr = new String[i4];
                for (int i5 = 0; i5 < i4; i5++) {
                    strArr[i5] = str + StrPool.DOT + i5;
                }
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] strArrSplit = line.split("[ ]+");
                    if (strArrSplit.length >= 6) {
                        String str2 = strArrSplit[0];
                        String str3 = strArrSplit[3];
                        for (int i6 = 0; i6 < i4; i6++) {
                            if (strArr[i6].equals(str2) && str3.matches("..:..:..:..:..:..") && !"00:00:00:00:00:00".equals(str3)) {
                                map.put(str2, str3);
                            }
                        }
                    }
                }
                bufferedReader.close();
            } catch (FileNotFoundException unused3) {
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 == null) {
                    return;
                }
                bufferedReader2.close();
            } catch (IOException unused4) {
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 == null) {
                    return;
                }
                bufferedReader2.close();
            } catch (Throwable th2) {
                th = th2;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException unused5) {
                    }
                }
                throw th;
            }
        } catch (IOException unused6) {
        }
    }

    private void a(HashMap map) {
        Handler handlerM290a = m290a();
        Message messageObtainMessage = handlerM290a.obtainMessage(this.f24687a);
        messageObtainMessage.obj = map;
        handlerM290a.sendMessage(messageObtainMessage);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        a(m291a());
    }
}
