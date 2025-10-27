package com.beizi.ad.internal.c;

import android.text.TextUtils;
import com.beizi.ad.internal.utilities.HaoboLog;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes2.dex */
class j {

    /* renamed from: a, reason: collision with root package name */
    private final ExecutorService f4105a = Executors.newSingleThreadExecutor();

    /* renamed from: b, reason: collision with root package name */
    private final String f4106b;

    /* renamed from: c, reason: collision with root package name */
    private final int f4107c;

    public class a implements Callable<Boolean> {
        private a() {
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Boolean call() throws Exception {
            return Boolean.valueOf(j.this.a());
        }
    }

    public j(String str, int i2) {
        this.f4106b = (String) k.a(str);
        this.f4107c = i2;
    }

    private String b() {
        String strA = com.beizi.ad.a.a.b.a("aHR0cDovLyVzOiVkLyVz");
        if (TextUtils.isEmpty(strA)) {
            return null;
        }
        return String.format(Locale.US, strA, this.f4106b, Integer.valueOf(this.f4107c), "ping");
    }

    public boolean a(int i2, int i3) {
        k.a(i2 >= 1);
        k.a(i3 > 0);
        int i4 = 0;
        while (i4 < i2) {
            try {
            } catch (InterruptedException e2) {
                e = e2;
                HaoboLog.e(HaoboLog.pingerLogTag, "Error pinging server due to unexpected error", e);
            } catch (ExecutionException e3) {
                e = e3;
                HaoboLog.e(HaoboLog.pingerLogTag, "Error pinging server due to unexpected error", e);
            } catch (TimeoutException unused) {
                HaoboLog.w(HaoboLog.pingerLogTag, "Error pinging server (attempt: " + i4 + ", timeout: " + i3 + "). ");
            }
            if (((Boolean) this.f4105a.submit(new a()).get(i3, TimeUnit.MILLISECONDS)).booleanValue()) {
                return true;
            }
            i4++;
            i3 *= 2;
        }
        return false;
    }

    public boolean a(String str) {
        return "ping".equals(str);
    }

    public void a(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("HTTP/1.1 200 OK\n\n".getBytes());
        outputStream.write("ping ok".getBytes());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() throws m {
        h hVar = new h(b());
        try {
            byte[] bytes = "ping ok".getBytes();
            hVar.a(0);
            byte[] bArr = new byte[bytes.length];
            hVar.a(bArr);
            boolean zEquals = Arrays.equals(bytes, bArr);
            HaoboLog.i(HaoboLog.pingerLogTag, "Ping response: `" + new String(bArr) + "`, pinged? " + zEquals);
            return zEquals;
        } catch (m e2) {
            HaoboLog.e(HaoboLog.pingerLogTag, "Error reading ping response", e2);
            return false;
        } finally {
            hVar.b();
        }
    }
}
