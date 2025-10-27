package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.tencent.smtt.sdk.TbsReaderView;
import java.util.LinkedList;

/* loaded from: classes6.dex */
public class TbsReaderPredownload {
    public static final int READER_SO_SUCCESS = 2;
    public static final int READER_WAIT_IN_QUEUE = 3;

    /* renamed from: b, reason: collision with root package name */
    static final String[] f20995b = {"docx", "pptx", "xlsx", "pdf", "epub", "txt"};

    /* renamed from: i, reason: collision with root package name */
    ReaderPreDownloadCallback f21003i;

    /* renamed from: a, reason: collision with root package name */
    Handler f20996a = null;

    /* renamed from: c, reason: collision with root package name */
    LinkedList<String> f20997c = new LinkedList<>();

    /* renamed from: d, reason: collision with root package name */
    boolean f20998d = false;

    /* renamed from: e, reason: collision with root package name */
    ReaderWizard f20999e = null;

    /* renamed from: f, reason: collision with root package name */
    TbsReaderView.ReaderCallback f21000f = null;

    /* renamed from: g, reason: collision with root package name */
    Object f21001g = null;

    /* renamed from: h, reason: collision with root package name */
    Context f21002h = null;

    /* renamed from: j, reason: collision with root package name */
    String f21004j = "";

    public interface ReaderPreDownloadCallback {
        public static final int NOTIFY_PLUGIN_FAILED = -1;
        public static final int NOTIFY_PLUGIN_SUCCESS = 0;

        void onEvent(String str, int i2, boolean z2);
    }

    public TbsReaderPredownload(ReaderPreDownloadCallback readerPreDownloadCallback) {
        this.f21003i = readerPreDownloadCallback;
        for (String str : f20995b) {
            this.f20997c.add(str);
        }
        a();
    }

    private void b() {
        b(3);
    }

    public void a() {
        this.f20996a = new Handler(Looper.getMainLooper()) { // from class: com.tencent.smtt.sdk.TbsReaderPredownload.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 3 && !TbsReaderPredownload.this.f20997c.isEmpty()) {
                    TbsReaderPredownload tbsReaderPredownload = TbsReaderPredownload.this;
                    if (tbsReaderPredownload.f20998d) {
                        return;
                    }
                    String strRemoveFirst = tbsReaderPredownload.f20997c.removeFirst();
                    TbsReaderPredownload tbsReaderPredownload2 = TbsReaderPredownload.this;
                    tbsReaderPredownload2.f21004j = strRemoveFirst;
                    if (tbsReaderPredownload2.a(strRemoveFirst)) {
                        return;
                    }
                    TbsReaderPredownload.this.a(-1);
                }
            }
        };
    }

    public void a(int i2) {
        if (this.f21003i != null) {
            this.f21003i.onEvent(this.f21004j, i2, this.f20997c.isEmpty());
        }
    }

    public void a(int i2, int i3) {
        this.f20996a.sendMessageDelayed(this.f20996a.obtainMessage(i2), i3);
    }

    public boolean a(String str) {
        if (this.f21001g == null || this.f20999e == null || !ReaderWizard.isSupportExt(str)) {
            return false;
        }
        return this.f20999e.checkPlugin(this.f21001g, this.f21002h, str, true);
    }

    public void b(int i2) {
        this.f20996a.removeMessages(i2);
    }

    public boolean c(int i2) {
        return this.f20996a.hasMessages(i2);
    }

    public boolean init(Context context) {
        if (context == null) {
            return false;
        }
        this.f21002h = context.getApplicationContext();
        boolean zA = TbsReaderView.a(context.getApplicationContext());
        TbsReaderView.ReaderCallback readerCallback = new TbsReaderView.ReaderCallback() { // from class: com.tencent.smtt.sdk.TbsReaderPredownload.1
            @Override // com.tencent.smtt.sdk.TbsReaderView.ReaderCallback
            public void onCallBackAction(Integer num, Object obj, Object obj2) {
                int iIntValue;
                if (num.intValue() == 5012 && 5014 != (iIntValue = ((Integer) obj).intValue())) {
                    if (5013 == iIntValue || iIntValue == 0) {
                        TbsReaderPredownload.this.a(0);
                    } else {
                        TbsReaderPredownload.this.a(-1);
                    }
                    TbsReaderPredownload tbsReaderPredownload = TbsReaderPredownload.this;
                    tbsReaderPredownload.f21004j = "";
                    tbsReaderPredownload.a(3, 100);
                }
            }
        };
        this.f21000f = readerCallback;
        try {
            if (this.f20999e == null) {
                this.f20999e = new ReaderWizard(readerCallback);
            }
            if (this.f21001g == null) {
                this.f21001g = this.f20999e.getTbsReader();
            }
            Object obj = this.f21001g;
            return obj != null ? this.f20999e.initTbsReader(obj, context.getApplicationContext()) : zA;
        } catch (NullPointerException unused) {
            Log.e("TbsReaderPredownload", "Unexpect null object!");
            return false;
        }
    }

    public void pause() {
        this.f20998d = true;
    }

    public void shutdown() {
        this.f21003i = null;
        this.f20998d = false;
        this.f20997c.clear();
        b();
        ReaderWizard readerWizard = this.f20999e;
        if (readerWizard != null) {
            readerWizard.destroy(this.f21001g);
            this.f21001g = null;
        }
        this.f21002h = null;
    }

    public void start(String str) {
        this.f20998d = false;
        b(3);
        this.f20997c.add(str);
        a(3, 100);
    }

    public void startAll() {
        this.f20998d = false;
        if (!false && !c(3)) {
            a(3, 100);
        }
    }
}
