package com.tencent.liteav.basic.util;

import android.os.Process;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final long f18696a = TimeUnit.SECONDS.toMillis(2);

    /* renamed from: d, reason: collision with root package name */
    private RandomAccessFile f18699d;

    /* renamed from: e, reason: collision with root package name */
    private RandomAccessFile f18700e;

    /* renamed from: f, reason: collision with root package name */
    private long f18701f = 0;

    /* renamed from: g, reason: collision with root package name */
    private float f18702g = 0.0f;

    /* renamed from: h, reason: collision with root package name */
    private float f18703h = 0.0f;

    /* renamed from: i, reason: collision with root package name */
    private long f18704i = 0;

    /* renamed from: j, reason: collision with root package name */
    private long f18705j = 0;

    /* renamed from: k, reason: collision with root package name */
    private float f18706k = 0.0f;

    /* renamed from: b, reason: collision with root package name */
    private final long f18697b = TXCTimeUtil.getClockTickInHz();

    /* renamed from: c, reason: collision with root package name */
    private final int f18698c = Runtime.getRuntime().availableProcessors();

    public b() {
        try {
            this.f18699d = new RandomAccessFile(String.format(Locale.ENGLISH, "/proc/%d/stat", Integer.valueOf(Process.myPid())), "r");
        } catch (IOException e2) {
            TXCLog.e("CpuUsageMeasurer", "open /proc/[PID]/stat failed. " + e2.getMessage());
        }
        try {
            this.f18700e = new RandomAccessFile("/proc/stat", "r");
        } catch (IOException unused) {
        }
    }

    private void b() throws IOException {
        long timeTick;
        long j2;
        String[] strArrA = a(this.f18699d);
        if (strArrA == null || strArrA.length < 52) {
            return;
        }
        long j3 = (long) (((((Long.parseLong(strArrA[13]) + Long.parseLong(strArrA[14])) + Long.parseLong(strArrA[15])) + Long.parseLong(strArrA[16])) * 1000.0f) / this.f18697b);
        String[] strArrA2 = a(this.f18700e);
        if (strArrA2 == null || strArrA2.length < 8) {
            timeTick = TXCTimeUtil.getTimeTick() * this.f18698c;
            j2 = timeTick;
        } else {
            long j4 = Long.parseLong(strArrA2[1]) + Long.parseLong(strArrA2[2]) + Long.parseLong(strArrA2[3]) + Long.parseLong(strArrA2[4]) + Long.parseLong(strArrA2[5]) + Long.parseLong(strArrA2[6]) + Long.parseLong(strArrA2[7]);
            long j5 = Long.parseLong(strArrA2[4]) + Long.parseLong(strArrA2[5]);
            float f2 = j4 * 1000.0f;
            long j6 = this.f18697b;
            timeTick = (long) (f2 / j6);
            j2 = (long) ((j5 * 1000.0f) / j6);
        }
        float f3 = j3;
        float f4 = timeTick - this.f18704i;
        this.f18703h = ((f3 - this.f18702g) * 100.0f) / f4;
        this.f18706k = ((r4 - (j2 - this.f18705j)) * 100.0f) / f4;
        this.f18702g = f3;
        this.f18705j = j2;
        this.f18704i = timeTick;
        this.f18701f = TXCTimeUtil.getTimeTick();
    }

    public int[] a() {
        int[] iArr;
        synchronized (this) {
            if (TXCTimeUtil.getTimeTick() - this.f18701f >= f18696a) {
                b();
            }
            iArr = new int[]{(int) (this.f18703h * 10.0f), (int) (this.f18706k * 10.0f)};
        }
        return iArr;
    }

    public void finalize() throws Throwable {
        super.finalize();
        d.a(this.f18699d);
        d.a(this.f18700e);
        TXCLog.i("CpuUsageMeasurer", "measurer is released");
    }

    private static String[] a(RandomAccessFile randomAccessFile) throws IOException {
        String line;
        if (randomAccessFile == null) {
            return null;
        }
        try {
            randomAccessFile.seek(0L);
            line = randomAccessFile.readLine();
        } catch (IOException e2) {
            TXCLog.e("CpuUsageMeasurer", "read line failed. " + e2.getMessage());
            line = null;
        }
        if (TextUtils.isEmpty(line)) {
            return null;
        }
        return line.split("\\s+");
    }
}
