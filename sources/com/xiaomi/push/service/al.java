package com.xiaomi.push.service;

import android.os.Process;
import android.text.TextUtils;
import com.xiaomi.push.da;
import com.xiaomi.push.es;
import com.xiaomi.push.ho;
import com.yikaobang.yixue.R2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class al {

    /* renamed from: a, reason: collision with other field name */
    private static final Pattern f996a = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");

    /* renamed from: a, reason: collision with root package name */
    private static long f25580a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static ThreadPoolExecutor f995a = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, new LinkedBlockingQueue());

    private static String a(String str) throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(str)));
        } catch (Exception unused) {
            bufferedReader = null;
        } catch (Throwable th2) {
            bufferedReader = null;
            th = th2;
        }
        try {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    String string = sb.toString();
                    com.xiaomi.push.y.a(bufferedReader);
                    return string;
                }
                sb.append("\n");
                sb.append(line);
            }
        } catch (Exception unused2) {
            com.xiaomi.push.y.a(bufferedReader);
            return null;
        } catch (Throwable th3) {
            th = th3;
            com.xiaomi.push.y.a(bufferedReader);
            throw th;
        }
    }

    public static void a() {
        es.a aVarM735a;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if ((f995a.getActiveCount() <= 0 || jCurrentTimeMillis - f25580a >= 1800000) && ho.m488a().m493a() && (aVarM735a = bi.a().m735a()) != null && aVarM735a.e() > 0) {
            f25580a = jCurrentTimeMillis;
            a(aVarM735a.m339a(), true);
        }
    }

    public static void a(List<String> list, boolean z2) {
        f995a.execute(new am(list, z2));
    }

    public static void b() throws Throwable {
        String strA = a("/proc/self/net/tcp");
        if (!TextUtils.isEmpty(strA)) {
            com.xiaomi.channel.commonutils.logger.b.m117a("dump tcp for uid = " + Process.myUid());
            com.xiaomi.channel.commonutils.logger.b.m117a(strA);
        }
        String strA2 = a("/proc/self/net/tcp6");
        if (TextUtils.isEmpty(strA2)) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m117a("dump tcp6 for uid = " + Process.myUid());
        com.xiaomi.channel.commonutils.logger.b.m117a(strA2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            com.xiaomi.channel.commonutils.logger.b.m117a("ConnectivityTest: begin to connect to " + str);
            Socket socket = new Socket();
            socket.connect(da.m311a(str, R2.color.m3_sys_color_dark_inverse_primary), 5000);
            socket.setTcpNoDelay(true);
            com.xiaomi.channel.commonutils.logger.b.m117a("ConnectivityTest: connect to " + str + " in " + (System.currentTimeMillis() - jCurrentTimeMillis));
            socket.close();
            return true;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.d("ConnectivityTest: could not connect to:" + str + " exception: " + th.getClass().getSimpleName() + " description: " + th.getMessage());
            return false;
        }
    }
}
