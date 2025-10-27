package com.tencent.smtt.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.widget.TextView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* loaded from: classes6.dex */
public class TbsLogClient {

    /* renamed from: a, reason: collision with root package name */
    static TbsLogClient f21433a = null;

    /* renamed from: c, reason: collision with root package name */
    static File f21434c = null;

    /* renamed from: d, reason: collision with root package name */
    static String f21435d = null;

    /* renamed from: e, reason: collision with root package name */
    static byte[] f21436e = null;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f21437i = true;

    /* renamed from: b, reason: collision with root package name */
    TextView f21438b;

    /* renamed from: f, reason: collision with root package name */
    private SimpleDateFormat f21439f;

    /* renamed from: g, reason: collision with root package name */
    private Context f21440g;

    /* renamed from: h, reason: collision with root package name */
    private StringBuffer f21441h = new StringBuffer();

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        String f21442a;

        public a(String str) {
            this.f21442a = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            TextView textView = TbsLogClient.this.f21438b;
            if (textView != null) {
                textView.append(this.f21442a + "\n");
            }
        }
    }

    public TbsLogClient(Context context) {
        this.f21439f = null;
        this.f21440g = null;
        try {
            this.f21440g = context.getApplicationContext();
            this.f21439f = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS", Locale.US);
        } catch (Exception unused) {
            this.f21439f = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        }
    }

    private void a() {
        String strA;
        try {
            if (f21434c == null) {
                if (!Environment.getExternalStorageState().equals("mounted") || (strA = FileUtil.a(this.f21440g, 6)) == null) {
                    f21434c = null;
                } else {
                    f21434c = new File(strA, "tbslog.txt");
                    f21435d = LogFileUtils.createKey();
                    f21436e = LogFileUtils.createHeaderText(f21434c.getName(), f21435d);
                }
            }
        } catch (NullPointerException | SecurityException e2) {
            e2.printStackTrace();
        }
    }

    public static void setWriteLogJIT(boolean z2) {
        f21437i = z2;
    }

    public void d(String str, String str2) {
    }

    public void e(String str, String str2) {
    }

    public void i(String str, String str2) {
    }

    public void setLogView(TextView textView) {
        this.f21438b = textView;
    }

    public void showLog(String str) {
        TextView textView = this.f21438b;
        if (textView != null) {
            textView.post(new a(str));
        }
    }

    public void v(String str, String str2) {
    }

    public void w(String str, String str2) {
    }

    public void writeLog(String str) {
        try {
            StringBuffer stringBuffer = this.f21441h;
            stringBuffer.append(System.currentTimeMillis());
            stringBuffer.append(" pid=");
            stringBuffer.append(Process.myPid());
            stringBuffer.append(" tid=");
            stringBuffer.append(Process.myTid());
            stringBuffer.append(str);
            stringBuffer.append("\n");
            if (Thread.currentThread() != Looper.getMainLooper().getThread() || f21437i) {
                writeLogToDisk();
            }
            if (this.f21441h.length() > 524288) {
                StringBuffer stringBuffer2 = this.f21441h;
                stringBuffer2.delete(0, stringBuffer2.length());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void writeLogToDisk() {
        try {
            a();
            File file = f21434c;
            if (file != null) {
                LogFileUtils.writeDataToStorage(file, f21435d, f21436e, this.f21441h.toString(), true);
                StringBuffer stringBuffer = this.f21441h;
                stringBuffer.delete(0, stringBuffer.length());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
