package com.tencent.liteav.a;

import android.content.Context;
import android.media.MediaFormat;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.audio.g;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.util.h;
import com.tencent.liteav.muxer.c;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.liteav.videoencoder.f;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes6.dex */
public class a implements g, f {

    /* renamed from: c, reason: collision with root package name */
    private c f18134c;

    /* renamed from: d, reason: collision with root package name */
    private C0322a f18135d;

    /* renamed from: e, reason: collision with root package name */
    private b f18136e;

    /* renamed from: f, reason: collision with root package name */
    private long f18137f = 0;

    /* renamed from: g, reason: collision with root package name */
    private long f18138g = -1;

    /* renamed from: h, reason: collision with root package name */
    private boolean f18139h = false;

    /* renamed from: i, reason: collision with root package name */
    private Handler f18140i = new Handler(Looper.getMainLooper()) { // from class: com.tencent.liteav.a.a.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (a.this.f18136e != null) {
                int i2 = message.what;
                if (i2 == 1) {
                    a.this.f18136e.a(((Long) message.obj).longValue());
                    return;
                }
                if (i2 != 2) {
                    return;
                }
                TXCLog.d("TXCStreamRecord", "record complete. errcode = " + message.arg1 + ", errmsg = " + ((String) message.obj) + ", outputPath = " + a.this.f18135d.f18147f + ", coverImage = " + a.this.f18135d.f18148g);
                if (message.arg1 == 0 && a.this.f18135d.f18148g != null && !a.this.f18135d.f18148g.isEmpty() && !h.a(a.this.f18135d.f18147f, a.this.f18135d.f18148g)) {
                    TXCLog.e("TXCStreamRecord", "saveVideoThumb error. sourcePath = " + a.this.f18135d.f18147f + ", coverImagePath = " + a.this.f18135d.f18148g);
                }
                if (message.arg1 != 0) {
                    try {
                        File file = new File(a.this.f18135d.f18147f);
                        if (file.exists()) {
                            file.delete();
                        }
                    } catch (Exception e2) {
                        TXCLog.e("TXCStreamRecord", "delete file failed.", e2);
                    }
                }
                a.this.f18136e.a(message.arg1, (String) message.obj, a.this.f18135d.f18147f, a.this.f18135d.f18148g);
            }
        }
    };

    /* renamed from: a, reason: collision with root package name */
    private com.tencent.liteav.audio.impl.Record.b f18132a = new com.tencent.liteav.audio.impl.Record.b();

    /* renamed from: b, reason: collision with root package name */
    private com.tencent.liteav.videoencoder.b f18133b = new com.tencent.liteav.videoencoder.b();

    /* renamed from: com.tencent.liteav.a.a$a, reason: collision with other inner class name */
    public static class C0322a {

        /* renamed from: e, reason: collision with root package name */
        public Object f18146e;

        /* renamed from: f, reason: collision with root package name */
        public String f18147f;

        /* renamed from: g, reason: collision with root package name */
        public String f18148g;

        /* renamed from: a, reason: collision with root package name */
        public int f18142a = R2.attr.bl_checked_gradient_type;

        /* renamed from: b, reason: collision with root package name */
        public int f18143b = 960;

        /* renamed from: c, reason: collision with root package name */
        public int f18144c = 20;

        /* renamed from: d, reason: collision with root package name */
        public int f18145d = 1000;

        /* renamed from: h, reason: collision with root package name */
        public int f18149h = 0;

        /* renamed from: i, reason: collision with root package name */
        public int f18150i = 0;

        /* renamed from: j, reason: collision with root package name */
        public int f18151j = 16;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("TXCStreamRecordParams: [width=" + this.f18142a);
            sb.append("; height=" + this.f18143b);
            sb.append("; fps=" + this.f18144c);
            sb.append("; bitrate=" + this.f18145d);
            sb.append("; channels=" + this.f18149h);
            sb.append("; samplerate=" + this.f18150i);
            sb.append("; bits=" + this.f18151j);
            sb.append("; EGLContext=" + this.f18146e);
            sb.append("; coveriamge=" + this.f18148g);
            sb.append("; outputpath=" + this.f18147f + StrPool.BRACKET_END);
            return sb.toString();
        }
    }

    public interface b {
        void a(int i2, String str, String str2, String str3);

        void a(long j2);
    }

    public a(Context context) {
        this.f18134c = new c(context, 2);
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void a(int i2, long j2, long j3) {
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void l(int i2) {
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void m(int i2) {
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordEncData(byte[] bArr, long j2, int i2, int i3, int i4) {
        this.f18134c.a(bArr, 0, bArr.length, j2 * 1000, 1);
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordError(int i2, String str) {
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordPcmData(byte[] bArr, long j2, int i2, int i3, int i4) {
    }

    @Override // com.tencent.liteav.audio.g
    public void onRecordRawPcmData(byte[] bArr, long j2, int i2, int i3, int i4, boolean z2) {
    }

    public void a(b bVar) {
        this.f18136e = bVar;
    }

    public void a(C0322a c0322a) {
        int i2;
        int i3;
        this.f18135d = c0322a;
        this.f18137f = 0L;
        this.f18138g = -1L;
        this.f18134c.a(c0322a.f18147f);
        int i4 = c0322a.f18149h;
        if (i4 > 0 && (i2 = c0322a.f18150i) > 0 && (i3 = c0322a.f18151j) > 0) {
            this.f18132a.a(10, i2, i4, i3, new WeakReference<>(this));
            C0322a c0322a2 = this.f18135d;
            this.f18134c.b(h.a(c0322a2.f18150i, c0322a2.f18149h, 2));
            this.f18139h = true;
        }
        this.f18133b.setListener(this);
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        C0322a c0322a3 = this.f18135d;
        tXSVideoEncoderParam.width = c0322a3.f18142a;
        tXSVideoEncoderParam.height = c0322a3.f18143b;
        tXSVideoEncoderParam.fps = c0322a3.f18144c;
        tXSVideoEncoderParam.glContext = c0322a3.f18146e;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.appendSpsPps = false;
        this.f18133b.setBitrate(c0322a3.f18145d);
        this.f18133b.start(tXSVideoEncoderParam);
    }

    public void a() {
        this.f18139h = false;
        this.f18132a.a();
        this.f18133b.stop();
        if (this.f18134c.b() < 0) {
            Handler handler = this.f18140i;
            handler.sendMessage(Message.obtain(handler, 2, 1, 0, "mp4合成失败"));
        } else {
            Handler handler2 = this.f18140i;
            handler2.sendMessage(Message.obtain(handler2, 2, 0, 0, ""));
        }
    }

    public void a(int i2, long j2) {
        com.tencent.liteav.videoencoder.b bVar = this.f18133b;
        C0322a c0322a = this.f18135d;
        bVar.pushVideoFrame(i2, c0322a.f18142a, c0322a.f18143b, j2);
    }

    public void a(byte[] bArr, long j2) {
        if (this.f18139h) {
            this.f18132a.a(bArr, j2);
        } else {
            TXCLog.e("TXCStreamRecord", "drainAudio fail because of not init yet!");
        }
    }

    public static String a(Context context, String str) {
        if (context == null) {
            return null;
        }
        try {
            String strValueOf = String.valueOf(System.currentTimeMillis() / 1000);
            String str2 = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Long.valueOf(strValueOf + "000").longValue()));
            String strA = a(context);
            if (TextUtils.isEmpty(strA)) {
                return null;
            }
            return new File(strA, String.format("TXUGC_%s" + str, str2)).getAbsolutePath();
        } catch (Exception e2) {
            TXCLog.e("TXCStreamRecord", "create file path failed.", e2);
            return null;
        }
    }

    private static String a(Context context) {
        if (context == null) {
            return null;
        }
        if (!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
            return context.getFilesDir().getPath();
        }
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        return externalFilesDir != null ? externalFilesDir.getPath() : null;
    }

    private String a(int i2) {
        String str;
        switch (i2) {
            case 10000002:
                str = "Video encoder is not activated";
                break;
            case 10000003:
                str = "Illegal video input parameters";
                break;
            case 10000004:
                str = "Video encoding failed to initialize";
                break;
            case 10000005:
                str = "Video encoding failed";
                break;
            default:
                str = "";
                break;
        }
        Handler handler = this.f18140i;
        handler.sendMessage(Message.obtain(handler, 2, 1, 0, str));
        return str;
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void a(TXSNALPacket tXSNALPacket, int i2) {
        if (i2 == 0) {
            c cVar = this.f18134c;
            byte[] bArr = tXSNALPacket.nalData;
            cVar.b(bArr, 0, bArr.length, tXSNALPacket.pts * 1000, tXSNALPacket.info.flags);
            if (this.f18138g < 0) {
                this.f18138g = tXSNALPacket.pts;
            }
            long j2 = tXSNALPacket.pts;
            if (j2 > this.f18137f + 500) {
                Handler handler = this.f18140i;
                handler.sendMessage(Message.obtain(handler, 1, new Long(j2 - this.f18138g)));
                this.f18137f = tXSNALPacket.pts;
                return;
            }
            return;
        }
        TXCLog.e("TXCStreamRecord", "video encode error! errmsg: " + a(i2));
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void a(MediaFormat mediaFormat) {
        this.f18134c.a(mediaFormat);
        if (!this.f18134c.c() || this.f18134c.a() >= 0) {
            return;
        }
        Handler handler = this.f18140i;
        handler.sendMessage(Message.obtain(handler, 2, 1, 0, "mp4 wrapper failed to start"));
    }
}
