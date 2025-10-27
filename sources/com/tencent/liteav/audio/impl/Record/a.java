package com.tencent.liteav.audio.impl.Record;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference<c> f18174a;

    /* renamed from: b, reason: collision with root package name */
    private int f18175b;

    /* renamed from: c, reason: collision with root package name */
    private int f18176c;

    /* renamed from: d, reason: collision with root package name */
    private int f18177d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f18178e;

    /* renamed from: f, reason: collision with root package name */
    private Thread f18179f;

    /* renamed from: g, reason: collision with root package name */
    private byte[] f18180g;

    private void b() {
        c cVar;
        synchronized (this) {
            WeakReference<c> weakReference = this.f18174a;
            cVar = weakReference != null ? weakReference.get() : null;
        }
        if (cVar != null) {
            cVar.onAudioRecordStart();
        } else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordStart:no callback");
        }
    }

    private void c() {
        c cVar;
        synchronized (this) {
            WeakReference<c> weakReference = this.f18174a;
            cVar = weakReference != null ? weakReference.get() : null;
        }
        if (cVar != null) {
            cVar.onAudioRecordStop();
        } else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordStop:no callback");
        }
    }

    public void a() throws InterruptedException {
        this.f18178e = false;
        long jCurrentTimeMillis = System.currentTimeMillis();
        Thread thread = this.f18179f;
        if (thread != null && thread.isAlive() && Thread.currentThread().getId() != this.f18179f.getId()) {
            try {
                this.f18179f.join();
            } catch (Exception e2) {
                TXCLog.e("AudioCenter:TXCAudioBGMRecord", "record stop Exception: " + e2.getMessage());
            }
        }
        TXCLog.i("AudioCenter:TXCAudioBGMRecord", "stop record cost time(MS): " + (System.currentTimeMillis() - jCurrentTimeMillis));
        this.f18179f = null;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        if (!this.f18178e) {
            TXCLog.w("AudioCenter:TXCAudioBGMRecord", "audio record: abandom start audio sys record thread!");
            return;
        }
        b();
        int i2 = this.f18175b;
        int i3 = this.f18176c;
        int i4 = this.f18177d;
        int i5 = ((i3 * 1024) * i4) / 8;
        byte[] bArr = new byte[i5];
        this.f18180g = bArr;
        Arrays.fill(bArr, (byte) 0);
        long jCurrentTimeMillis = System.currentTimeMillis();
        long length = 0;
        while (this.f18178e && !Thread.interrupted()) {
            if (((((((System.currentTimeMillis() - jCurrentTimeMillis) * i2) * i3) * i4) / 8) / 1000) - length < i5) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                }
            } else {
                byte[] bArr2 = this.f18180g;
                length += bArr2.length;
                a(bArr2, bArr2.length, TXCTimeUtil.getTimeTick());
            }
        }
        c();
    }

    private void a(byte[] bArr, int i2, long j2) {
        c cVar;
        synchronized (this) {
            WeakReference<c> weakReference = this.f18174a;
            cVar = weakReference != null ? weakReference.get() : null;
        }
        if (cVar != null) {
            cVar.onAudioRecordPCM(bArr, i2, j2);
        } else {
            TXCLog.e("AudioCenter:TXCAudioBGMRecord", "onRecordPcmData:no callback");
        }
    }
}
