package com.google.android.exoplayer2.util;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/* loaded from: classes3.dex */
public final class SntpClient {
    public static final String DEFAULT_NTP_HOST = "time.android.com";
    private static final int NTP_LEAP_NOSYNC = 3;
    private static final int NTP_MODE_BROADCAST = 5;
    private static final int NTP_MODE_CLIENT = 3;
    private static final int NTP_MODE_SERVER = 4;
    private static final int NTP_PACKET_SIZE = 48;
    private static final int NTP_PORT = 123;
    private static final int NTP_STRATUM_DEATH = 0;
    private static final int NTP_STRATUM_MAX = 15;
    private static final int NTP_VERSION = 3;
    private static final long OFFSET_1900_TO_1970 = 2208988800L;
    private static final int ORIGINATE_TIME_OFFSET = 24;
    private static final int RECEIVE_TIME_OFFSET = 32;
    private static final int TIMEOUT_MS = 10000;
    private static final int TRANSMIT_TIME_OFFSET = 40;

    @GuardedBy("valueLock")
    private static long elapsedRealtimeOffsetMs = 0;

    @GuardedBy("valueLock")
    private static boolean isInitialized = false;

    @GuardedBy("valueLock")
    private static String ntpHost = "time.android.com";
    private static final Object loaderLock = new Object();
    private static final Object valueLock = new Object();

    public interface InitializationCallback {
        void onInitializationFailed(IOException iOException);

        void onInitialized();
    }

    public static final class NtpTimeCallback implements Loader.Callback<Loader.Loadable> {

        @Nullable
        private final InitializationCallback callback;

        public NtpTimeCallback(@Nullable InitializationCallback initializationCallback) {
            this.callback = initializationCallback;
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCanceled(Loader.Loadable loadable, long j2, long j3, boolean z2) {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public void onLoadCompleted(Loader.Loadable loadable, long j2, long j3) {
            if (this.callback != null) {
                if (SntpClient.isInitialized()) {
                    this.callback.onInitialized();
                } else {
                    this.callback.onInitializationFailed(new IOException(new ConcurrentModificationException()));
                }
            }
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Callback
        public Loader.LoadErrorAction onLoadError(Loader.Loadable loadable, long j2, long j3, IOException iOException, int i2) {
            InitializationCallback initializationCallback = this.callback;
            if (initializationCallback != null) {
                initializationCallback.onInitializationFailed(iOException);
            }
            return Loader.DONT_RETRY;
        }
    }

    public static final class NtpTimeLoadable implements Loader.Loadable {
        private NtpTimeLoadable() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void cancelLoad() {
        }

        @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
        public void load() throws IOException {
            synchronized (SntpClient.loaderLock) {
                synchronized (SntpClient.valueLock) {
                    if (SntpClient.isInitialized) {
                        return;
                    }
                    long jLoadNtpTimeOffsetMs = SntpClient.loadNtpTimeOffsetMs();
                    synchronized (SntpClient.valueLock) {
                        long unused = SntpClient.elapsedRealtimeOffsetMs = jLoadNtpTimeOffsetMs;
                        boolean unused2 = SntpClient.isInitialized = true;
                    }
                }
            }
        }
    }

    private SntpClient() {
    }

    private static void checkValidServerReply(byte b3, byte b4, int i2, long j2) throws IOException {
        if (b3 == 3) {
            throw new IOException("SNTP: Unsynchronized server");
        }
        if (b4 != 4 && b4 != 5) {
            StringBuilder sb = new StringBuilder(26);
            sb.append("SNTP: Untrusted mode: ");
            sb.append((int) b4);
            throw new IOException(sb.toString());
        }
        if (i2 != 0 && i2 <= 15) {
            if (j2 == 0) {
                throw new IOException("SNTP: Zero transmitTime");
            }
        } else {
            StringBuilder sb2 = new StringBuilder(36);
            sb2.append("SNTP: Untrusted stratum: ");
            sb2.append(i2);
            throw new IOException(sb2.toString());
        }
    }

    public static long getElapsedRealtimeOffsetMs() {
        long j2;
        synchronized (valueLock) {
            j2 = isInitialized ? elapsedRealtimeOffsetMs : C.TIME_UNSET;
        }
        return j2;
    }

    public static String getNtpHost() {
        String str;
        synchronized (valueLock) {
            str = ntpHost;
        }
        return str;
    }

    public static void initialize(@Nullable Loader loader, @Nullable InitializationCallback initializationCallback) {
        if (isInitialized()) {
            if (initializationCallback != null) {
                initializationCallback.onInitialized();
            }
        } else {
            if (loader == null) {
                loader = new Loader("SntpClient");
            }
            loader.startLoading(new NtpTimeLoadable(), new NtpTimeCallback(initializationCallback), 1);
        }
    }

    public static boolean isInitialized() {
        boolean z2;
        synchronized (valueLock) {
            z2 = isInitialized;
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long loadNtpTimeOffsetMs() throws IOException {
        InetAddress byName = InetAddress.getByName(getNtpHost());
        DatagramSocket datagramSocket = new DatagramSocket();
        try {
            datagramSocket.setSoTimeout(10000);
            byte[] bArr = new byte[48];
            DatagramPacket datagramPacket = new DatagramPacket(bArr, 48, byName, 123);
            bArr[0] = Ascii.ESC;
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jElapsedRealtime = android.os.SystemClock.elapsedRealtime();
            writeTimestamp(bArr, 40, jCurrentTimeMillis);
            datagramSocket.send(datagramPacket);
            datagramSocket.receive(new DatagramPacket(bArr, 48));
            long jElapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
            long j2 = jCurrentTimeMillis + (jElapsedRealtime2 - jElapsedRealtime);
            byte b3 = bArr[0];
            int i2 = bArr[1] & 255;
            long timestamp = readTimestamp(bArr, 24);
            long timestamp2 = readTimestamp(bArr, 32);
            long timestamp3 = readTimestamp(bArr, 40);
            checkValidServerReply((byte) ((b3 >> 6) & 3), (byte) (b3 & 7), i2, timestamp3);
            long j3 = (j2 + (((timestamp2 - timestamp) + (timestamp3 - j2)) / 2)) - jElapsedRealtime2;
            datagramSocket.close();
            return j3;
        } catch (Throwable th) {
            try {
                datagramSocket.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static long read32(byte[] bArr, int i2) {
        int i3 = bArr[i2];
        int i4 = bArr[i2 + 1];
        int i5 = bArr[i2 + 2];
        int i6 = bArr[i2 + 3];
        if ((i3 & 128) == 128) {
            i3 = (i3 & 127) + 128;
        }
        if ((i4 & 128) == 128) {
            i4 = (i4 & 127) + 128;
        }
        if ((i5 & 128) == 128) {
            i5 = (i5 & 127) + 128;
        }
        if ((i6 & 128) == 128) {
            i6 = (i6 & 127) + 128;
        }
        return (i3 << 24) + (i4 << 16) + (i5 << 8) + i6;
    }

    private static long readTimestamp(byte[] bArr, int i2) {
        long j2 = read32(bArr, i2);
        long j3 = read32(bArr, i2 + 4);
        if (j2 == 0 && j3 == 0) {
            return 0L;
        }
        return ((j2 - OFFSET_1900_TO_1970) * 1000) + ((j3 * 1000) / IjkMediaMeta.AV_CH_WIDE_RIGHT);
    }

    public static void setNtpHost(String str) {
        synchronized (valueLock) {
            if (!ntpHost.equals(str)) {
                ntpHost = str;
                isInitialized = false;
            }
        }
    }

    private static void writeTimestamp(byte[] bArr, int i2, long j2) {
        if (j2 == 0) {
            Arrays.fill(bArr, i2, i2 + 8, (byte) 0);
            return;
        }
        long j3 = j2 / 1000;
        long j4 = j2 - (j3 * 1000);
        long j5 = j3 + OFFSET_1900_TO_1970;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (j5 >> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (j5 >> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (j5 >> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (j5 >> 0);
        long j6 = (j4 * IjkMediaMeta.AV_CH_WIDE_RIGHT) / 1000;
        int i7 = i6 + 1;
        bArr[i6] = (byte) (j6 >> 24);
        int i8 = i7 + 1;
        bArr[i7] = (byte) (j6 >> 16);
        bArr[i8] = (byte) (j6 >> 8);
        bArr[i8 + 1] = (byte) (Math.random() * 255.0d);
    }
}
