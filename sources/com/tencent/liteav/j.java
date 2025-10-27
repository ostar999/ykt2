package com.tencent.liteav;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.plv.socket.event.sclass.PLVInLiveAckResult;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes6.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private String f19394a = "";

    /* renamed from: b, reason: collision with root package name */
    private String f19395b = "";

    /* renamed from: c, reason: collision with root package name */
    private int f19396c = 0;

    /* renamed from: d, reason: collision with root package name */
    private String f19397d = "";

    /* renamed from: e, reason: collision with root package name */
    private String f19398e = "";

    /* renamed from: f, reason: collision with root package name */
    private long f19399f = 0;

    public interface a {
        void a(long j2);
    }

    public long a() {
        return System.currentTimeMillis() - this.f19399f;
    }

    public String a(long j2) {
        String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(this.f19399f + (j2 * 1000)));
        int i2 = this.f19396c;
        return i2 < 0 ? String.format("http://%s/timeshift/%s/%s/timeshift.m3u8?delay=%d", this.f19394a, this.f19398e, this.f19395b, Long.valueOf(((System.currentTimeMillis() - this.f19399f) - j2) / 1000)) : String.format("http://%s/%s/%s/timeshift.m3u8?starttime=%s&appid=%s&txKbps=0", this.f19394a, Integer.valueOf(i2), this.f19395b, str, this.f19397d);
    }

    public int a(final String str, final String str2, final int i2, final a aVar) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        String appID = TXCCommonUtil.getAppID();
        this.f19397d = appID;
        if (TextUtils.isEmpty(appID)) {
            return -2;
        }
        AsyncTask.execute(new Runnable() { // from class: com.tencent.liteav.j.1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                j.this.f19399f = System.currentTimeMillis();
                String str3 = "";
                j.this.f19395b = "";
                j.this.f19396c = i2;
                j.this.f19394a = str2;
                j.this.f19395b = TXCCommonUtil.getStreamIDByStreamUrl(str);
                j.this.f19398e = TXCCommonUtil.getAppNameByStreamUrl(str);
                if (j.this.f19398e == null) {
                    j.this.f19398e = PLVInLiveAckResult.STATUS_LIVE;
                }
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(j.this.f19396c < 0 ? String.format("http://%s/timeshift/%s/%s/timeshift.m3u8?delay=0", j.this.f19394a, j.this.f19398e, j.this.f19395b) : String.format("http://%s/%s/%s/timeshift.m3u8?delay=0&appid=%s&txKbps=0", j.this.f19394a, Integer.valueOf(j.this.f19396c), j.this.f19395b, j.this.f19397d)).openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Charsert", "UTF-8");
                    httpURLConnection.setRequestProperty("Content-Type", "text/plain;");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while (true) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        str3 = str3 + line;
                    }
                    TXCLog.i("TXCTimeShiftUtil", "prepareSeekTime: receive response, strResponse = " + str3);
                    String strA = j.this.a(str3);
                    if (strA != null) {
                        j.this.f19399f = Long.parseLong(strA) * 1000;
                    }
                } catch (Exception e2) {
                    j.this.f19399f = System.currentTimeMillis();
                    TXCLog.e("TXCTimeShiftUtil", "prepareSeekTime error " + e2.toString());
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                TXCLog.i("TXCTimeShiftUtil", "live start time:" + j.this.f19399f + ",currentTime:" + jCurrentTimeMillis + ",diff:" + (jCurrentTimeMillis - j.this.f19399f));
                final long j2 = jCurrentTimeMillis - j.this.f19399f;
                if (aVar != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.j.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            aVar.a(j2);
                        }
                    });
                }
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String a(String str) {
        int iIndexOf;
        String strSubstring;
        int iIndexOf2;
        if (!str.contains("#EXT-TX-TS-START-TIME") || (iIndexOf = str.indexOf("#EXT-TX-TS-START-TIME:") + 22) <= 0 || (iIndexOf2 = (strSubstring = str.substring(iIndexOf)).indexOf(DictionaryFactory.SHARP)) <= 0) {
            return null;
        }
        return strSubstring.substring(0, iIndexOf2).replaceAll("\r\n", "");
    }
}
