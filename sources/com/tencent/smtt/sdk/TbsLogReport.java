package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import cn.hutool.core.text.CharPool;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.utils.Base64;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.g;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import net.lingala.zip4j.util.InternalZipConstants;
import org.json.JSONArray;

/* loaded from: classes6.dex */
public class TbsLogReport {

    /* renamed from: a, reason: collision with root package name */
    private static TbsLogReport f20953a;

    /* renamed from: b, reason: collision with root package name */
    private Handler f20954b;

    /* renamed from: c, reason: collision with root package name */
    private final Map<EventType, Boolean> f20955c;

    /* renamed from: d, reason: collision with root package name */
    private final Context f20956d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f20957e = false;

    public enum EventType {
        TYPE_DOWNLOAD(0),
        TYPE_INSTALL(1),
        TYPE_LOAD(2),
        TYPE_CDN_DOWNLOAD_STAT(3),
        TYPE_COOKIE_DB_SWITCH(4),
        TYPE_PV_UPLOAD_STAT(5),
        TYPE_CORE_LOAD_PERFORMANCE(6),
        TYPE_CORE_PROTECT_RESET(7);


        /* renamed from: a, reason: collision with root package name */
        int f20962a;

        EventType(int i2) {
            this.f20962a = i2;
        }
    }

    public static class TbsLogInfo implements Cloneable {

        /* renamed from: a, reason: collision with root package name */
        int f20963a;

        /* renamed from: b, reason: collision with root package name */
        private long f20964b;

        /* renamed from: c, reason: collision with root package name */
        private String f20965c;

        /* renamed from: d, reason: collision with root package name */
        private String f20966d;

        /* renamed from: e, reason: collision with root package name */
        private int f20967e;

        /* renamed from: f, reason: collision with root package name */
        private int f20968f;

        /* renamed from: g, reason: collision with root package name */
        private int f20969g;

        /* renamed from: h, reason: collision with root package name */
        private int f20970h;

        /* renamed from: i, reason: collision with root package name */
        private String f20971i;

        /* renamed from: j, reason: collision with root package name */
        private int f20972j;

        /* renamed from: k, reason: collision with root package name */
        private int f20973k;

        /* renamed from: l, reason: collision with root package name */
        private long f20974l;

        /* renamed from: m, reason: collision with root package name */
        private long f20975m;

        /* renamed from: n, reason: collision with root package name */
        private int f20976n;

        /* renamed from: o, reason: collision with root package name */
        private String f20977o;

        /* renamed from: p, reason: collision with root package name */
        private String f20978p;

        /* renamed from: q, reason: collision with root package name */
        private long f20979q;

        private TbsLogInfo() {
            resetArgs();
        }

        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException unused) {
                return this;
            }
        }

        public int getDownFinalFlag() {
            return this.f20973k;
        }

        public void resetArgs() {
            this.f20964b = 0L;
            this.f20965c = null;
            this.f20966d = null;
            this.f20967e = 0;
            this.f20968f = 0;
            this.f20969g = 0;
            this.f20970h = 2;
            this.f20971i = "unknown";
            this.f20972j = 0;
            this.f20973k = 2;
            this.f20974l = 0L;
            this.f20975m = 0L;
            this.f20976n = 1;
            this.f20963a = 0;
            this.f20977o = null;
            this.f20978p = null;
            this.f20979q = 0L;
        }

        public void setApn(String str) {
            this.f20971i = str;
        }

        public void setCheckErrorDetail(String str) {
            setErrorCode(108);
            this.f20977o = str;
        }

        public void setDownConsumeTime(long j2) {
            this.f20975m += j2;
        }

        public void setDownFinalFlag(int i2) {
            this.f20973k = i2;
        }

        public void setDownloadCancel(int i2) {
            this.f20969g = i2;
        }

        public void setDownloadSize(long j2) {
            this.f20979q += j2;
        }

        public void setDownloadUrl(String str) {
            if (this.f20965c != null) {
                str = this.f20965c + com.alipay.sdk.util.h.f3376b + str;
            }
            this.f20965c = str;
        }

        public void setErrorCode(int i2) {
            if (i2 != 100 && i2 != 110 && i2 != 120 && i2 != 111 && i2 < 400) {
                TbsLog.i(TbsDownloader.LOGTAG, "error occured, errorCode:" + i2, true);
            }
            if (i2 == 111) {
                TbsLog.i(TbsDownloader.LOGTAG, "you are not in wifi, downloading stoped", true);
            }
            this.f20963a = i2;
        }

        public void setEventTime(long j2) {
            this.f20964b = j2;
        }

        public void setFailDetail(String str) {
            if (str == null) {
                return;
            }
            if (str.length() > 1024) {
                str = str.substring(0, 1024);
            }
            this.f20978p = str;
        }

        public void setFailDetail(Throwable th) {
            if (th == null) {
                this.f20978p = "";
                return;
            }
            String stackTraceString = Log.getStackTraceString(th);
            if (stackTraceString.length() > 1024) {
                stackTraceString = stackTraceString.substring(0, 1024);
            }
            this.f20978p = stackTraceString;
        }

        public void setHttpCode(int i2) {
            this.f20967e = i2;
        }

        public void setNetworkChange(int i2) {
            this.f20976n = i2;
        }

        public void setNetworkType(int i2) {
            this.f20972j = i2;
        }

        public void setPatchUpdateFlag(int i2) {
            this.f20968f = i2;
        }

        public void setPkgSize(long j2) {
            this.f20974l = j2;
        }

        public void setResolveIp(String str) {
            this.f20966d = str;
        }

        public void setUnpkgFlag(int i2) {
            this.f20970h = i2;
        }

        public String toString() {
            return "TbsLogInfo{mEventTime=" + this.f20964b + ", mResolveIp='" + this.f20966d + CharPool.SINGLE_QUOTE + ", mHttpCode=" + this.f20967e + ", mDownloadCancel=" + this.f20969g + ", mNetworkType=" + this.f20972j + ", mDownConsumeTime=" + this.f20975m + ", mErrorCode=" + this.f20963a + ", mCheckErrorDetail='" + this.f20977o + CharPool.SINGLE_QUOTE + ", mFailDetail='" + this.f20978p + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private final String f20980a;

        /* renamed from: b, reason: collision with root package name */
        private final String f20981b;

        public a(String str, String str2) {
            this.f20980a = str;
            this.f20981b = str2;
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x003e -> B:29:0x0041). Please report as a decompilation issue!!! */
        private static void a(File file) throws Throwable {
            RandomAccessFile randomAccessFile = null;
            RandomAccessFile randomAccessFile2 = null;
            randomAccessFile = null;
            try {
            } catch (IOException e2) {
                e2.printStackTrace();
                randomAccessFile = randomAccessFile;
            }
            try {
                try {
                    RandomAccessFile randomAccessFile3 = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE);
                    try {
                        int i2 = Integer.parseInt("00001000", 2);
                        randomAccessFile3.seek(7L);
                        int i3 = randomAccessFile3.read();
                        if ((i3 & i2) > 0) {
                            randomAccessFile3.seek(7L);
                            randomAccessFile3.write((~i2) & 255 & i3);
                        }
                        randomAccessFile3.close();
                        randomAccessFile = i3;
                    } catch (Exception e3) {
                        e = e3;
                        randomAccessFile2 = randomAccessFile3;
                        e.printStackTrace();
                        randomAccessFile = randomAccessFile2;
                        if (randomAccessFile2 != null) {
                            randomAccessFile2.close();
                            randomAccessFile = randomAccessFile2;
                        }
                    } catch (Throwable th) {
                        th = th;
                        randomAccessFile = randomAccessFile3;
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Exception e5) {
                    e = e5;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        /* JADX WARN: Can't wrap try/catch for region: R(17:99|6|126|7|130|8|117|9|(2:10|(1:12)(1:133))|13|109|14|(2:105|18)|46|107|47|51) */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x008d, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x008e, code lost:
        
            r0.printStackTrace();
         */
        /* JADX WARN: Removed duplicated region for block: B:100:0x006f A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:122:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:85:0x00d4 -> B:102:0x00d7). Please report as a decompilation issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void a() throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 238
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsLogReport.a.a():void");
        }
    }

    private TbsLogReport(Context context) {
        this.f20954b = null;
        this.f20956d = context.getApplicationContext();
        this.f20955c = TbsPVConfig.getInstance(context).getLogReportSwitchMap();
        HandlerThread handlerThread = new HandlerThread("TbsLogReportThread");
        handlerThread.start();
        this.f20954b = new Handler(handlerThread.getLooper()) { // from class: com.tencent.smtt.sdk.TbsLogReport.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i2 = message.what;
                if (i2 != 600) {
                    if (i2 == 601) {
                        TbsLogReport.this.b();
                        return;
                    }
                    return;
                }
                Object obj = message.obj;
                if (obj instanceof TbsLogInfo) {
                    try {
                        int i3 = message.arg1;
                        TbsLogReport.this.a(i3, (TbsLogInfo) obj);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        };
    }

    private String a(int i2) {
        return i2 + HiAnalyticsConstant.REPORT_VAL_SEPARATOR;
    }

    private String a(long j2) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(j2));
        } catch (Exception unused) {
            return null;
        }
    }

    private String a(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(HiAnalyticsConstant.REPORT_VAL_SEPARATOR);
        return sb.toString();
    }

    private JSONArray a() {
        String string = d().getString("tbs_tbslogreport_upload", null);
        if (string != null) {
            try {
                string = new String(Base64.a(string, 2));
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        if (string == null) {
            return new JSONArray();
        }
        try {
            JSONArray jSONArray = new JSONArray(string);
            if (jSONArray.length() <= 5) {
                return jSONArray;
            }
            JSONArray jSONArray2 = new JSONArray();
            int length = jSONArray.length();
            while (true) {
                length--;
                if (length < jSONArray.length() - 5) {
                    return jSONArray2;
                }
                jSONArray2.put(jSONArray.get(length));
            }
        } catch (Exception unused) {
            return new JSONArray();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, TbsLogInfo tbsLogInfo) {
        Map<String, Object> map = QbSdk.f20831o;
        if (map != null && map.containsKey(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD) && QbSdk.f20831o.get(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD).equals(k.a.f27524v)) {
            TbsLog.i("TbsLogReport", "[TbsLogReport.sendLogReportRequest] -- SET_SENDREQUEST_AND_UPLOAD is false");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a(i2));
        sb.append(a(""));
        sb.append(a(com.tencent.smtt.utils.l.a(this.f20956d)));
        sb.append(a(o.a().h(this.f20956d)));
        sb.append(a(""));
        String packageName = this.f20956d.getPackageName();
        sb.append(a(packageName));
        sb.append("com.tencent.mm".equals(packageName) ? a(com.tencent.smtt.utils.b.a(this.f20956d, TbsDownloader.TBS_METADATA)) : a(com.tencent.smtt.utils.b.e(this.f20956d)));
        sb.append(a(a(tbsLogInfo.f20964b)));
        sb.append(a(tbsLogInfo.f20965c));
        sb.append(a(tbsLogInfo.f20966d));
        sb.append(a(tbsLogInfo.f20967e));
        sb.append(a(tbsLogInfo.f20968f));
        sb.append(a(tbsLogInfo.f20969g));
        sb.append(a(tbsLogInfo.f20970h));
        sb.append(a(tbsLogInfo.f20971i));
        sb.append(a(tbsLogInfo.f20972j));
        sb.append(a(tbsLogInfo.f20973k));
        sb.append(b(tbsLogInfo.f20979q));
        sb.append(b(tbsLogInfo.f20974l));
        sb.append(b(tbsLogInfo.f20975m));
        sb.append(a(tbsLogInfo.f20976n));
        sb.append(a(tbsLogInfo.f20963a));
        sb.append(a(tbsLogInfo.f20977o));
        sb.append(a(tbsLogInfo.f20978p));
        sb.append(a(TbsDownloadConfig.getInstance(this.f20956d).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0)));
        sb.append(a(com.tencent.smtt.utils.b.k(this.f20956d)));
        sb.append(a("44226"));
        sb.append(false);
        SharedPreferences sharedPreferencesD = d();
        JSONArray jSONArrayA = a();
        jSONArrayA.put(sb.toString());
        SharedPreferences.Editor editorEdit = sharedPreferencesD.edit();
        String string = jSONArrayA.toString();
        try {
            string = Base64.encodeToString(string.getBytes(), 2);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        editorEdit.putString("tbs_tbslogreport_upload", string);
        editorEdit.commit();
        if (this.f20957e) {
            b();
        }
    }

    private void a(int i2, TbsLogInfo tbsLogInfo, EventType eventType) {
        tbsLogInfo.setErrorCode(i2);
        tbsLogInfo.setEventTime(System.currentTimeMillis());
        QbSdk.f20830n.onInstallFinish(i2);
        eventReport(eventType, tbsLogInfo);
    }

    private void a(int i2, String str) {
        TbsLogInfo tbsLogInfo = tbsLogInfo();
        tbsLogInfo.setErrorCode(i2);
        tbsLogInfo.setEventTime(System.currentTimeMillis());
        tbsLogInfo.setFailDetail(str);
        eventReport(EventType.TYPE_LOAD, tbsLogInfo);
    }

    private String b(long j2) {
        return j2 + HiAnalyticsConstant.REPORT_VAL_SEPARATOR;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        Map<String, Object> map = QbSdk.f20831o;
        if (map != null && map.containsKey(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD) && QbSdk.f20831o.get(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD).equals(k.a.f27524v)) {
            TbsLog.i("TbsLogReport", "[TbsLogReport.sendLogReportRequest] -- SET_SENDREQUEST_AND_UPLOAD is false");
            return;
        }
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat]");
        JSONArray jSONArrayA = a();
        if (jSONArrayA == null || jSONArrayA.length() == 0) {
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat] no data");
            return;
        }
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat] jsonArray:" + jSONArrayA);
        try {
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat] response:" + com.tencent.smtt.utils.g.a(com.tencent.smtt.utils.o.a(this.f20956d).c(), jSONArrayA.toString().getBytes("utf-8"), new g.a() { // from class: com.tencent.smtt.sdk.TbsLogReport.3
                @Override // com.tencent.smtt.utils.g.a
                public void a(int i2) {
                    TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat] onHttpResponseCode:" + i2);
                    if (i2 < 300) {
                        TbsLogReport.this.c();
                    }
                }
            }, true) + " testcase: -1");
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        SharedPreferences.Editor editorEdit = d().edit();
        editorEdit.remove("tbs_tbslogreport_upload");
        editorEdit.commit();
    }

    private SharedPreferences d() {
        return this.f20956d.getSharedPreferences("tbs_event_stat", 4);
    }

    public static TbsLogReport getInstance(Context context) {
        if (f20953a == null) {
            synchronized (TbsLogReport.class) {
                if (f20953a == null) {
                    f20953a = new TbsLogReport(context);
                }
            }
        }
        return f20953a;
    }

    public void clear() {
        try {
            SharedPreferences.Editor editorEdit = d().edit();
            editorEdit.clear();
            editorEdit.commit();
        } catch (Exception unused) {
        }
    }

    public void dailyReport() {
        this.f20954b.sendEmptyMessage(601);
    }

    public void eventReport(EventType eventType, TbsLogInfo tbsLogInfo) {
        TbsLog.i("TbsLogReport", "[TbsLogReport.eventRepost] " + eventType + ": " + tbsLogInfo);
        Boolean bool = this.f20955c.get(eventType);
        if (bool == null || !bool.booleanValue()) {
            return;
        }
        TbsLog.i("TbsLogReport", "[TbsLogReport.eventRepost] needReport!");
        try {
            TbsLogInfo tbsLogInfo2 = (TbsLogInfo) tbsLogInfo.clone();
            Message messageObtainMessage = this.f20954b.obtainMessage();
            messageObtainMessage.what = 600;
            messageObtainMessage.arg1 = eventType.f20962a;
            messageObtainMessage.obj = tbsLogInfo2;
            this.f20954b.sendMessage(messageObtainMessage);
        } catch (Throwable th) {
            TbsLog.w("TbsLogReport", "[TbsLogReport.eventReport] error, message=" + th.getMessage());
        }
    }

    public boolean getShouldUploadEventReport() {
        return this.f20957e;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x010d A[PHI: r0 r5
      0x010d: PHI (r0v13 java.io.File) = (r0v10 java.io.File), (r0v14 java.io.File), (r0v14 java.io.File) binds: [B:54:0x0134, B:69:0x010d, B:32:0x010a] A[DONT_GENERATE, DONT_INLINE]
      0x010d: PHI (r5v11 byte[]) = (r5v16 byte[]), (r5v13 byte[]), (r5v13 byte[]) binds: [B:54:0x0134, B:69:0x010d, B:32:0x010a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x012c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0131 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x015d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0158 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /* JADX WARN: Type inference failed for: r8v11, types: [java.io.ByteArrayOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5, types: [java.io.ByteArrayOutputStream] */
    /* JADX WARN: Type inference failed for: r8v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void reportTbsLog() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 358
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsLogReport.reportTbsLog():void");
    }

    public void setInstallErrorCode(int i2, String str) {
        setInstallErrorCode(i2, str, EventType.TYPE_INSTALL);
    }

    public void setInstallErrorCode(int i2, String str, EventType eventType) {
        if (i2 != 200 && i2 != 220 && i2 != 221) {
            TbsLog.i(TbsDownloader.LOGTAG, "error occured in installation, errorCode:" + i2, true);
        }
        TbsLogInfo tbsLogInfo = tbsLogInfo();
        tbsLogInfo.setFailDetail(str);
        a(i2, tbsLogInfo, eventType);
    }

    public void setInstallErrorCode(int i2, Throwable th) {
        TbsLogInfo tbsLogInfo = tbsLogInfo();
        tbsLogInfo.setFailDetail(th);
        a(i2, tbsLogInfo, EventType.TYPE_INSTALL);
    }

    public void setLoadErrorCode(int i2, Throwable th) {
        String strSubstring;
        if (th != null) {
            strSubstring = "msg: " + th.getMessage() + "; err: " + th + "; cause: " + Log.getStackTraceString(th.getCause());
            if (strSubstring.length() > 1024) {
                strSubstring = strSubstring.substring(0, 1024);
            }
        } else {
            strSubstring = PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL;
        }
        a(i2, strSubstring);
    }

    public void setShouldUploadEventReport(boolean z2) {
        this.f20957e = z2;
    }

    public TbsLogInfo tbsLogInfo() {
        return new TbsLogInfo();
    }
}
