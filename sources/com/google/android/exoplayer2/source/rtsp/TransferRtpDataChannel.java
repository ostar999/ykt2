package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.upstream.BaseDataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
final class TransferRtpDataChannel extends BaseDataSource implements RtpDataChannel, RtspMessageChannel.InterleavedBinaryDataListener {
    private static final String DEFAULT_TCP_TRANSPORT_FORMAT = "RTP/AVP/TCP;unicast;interleaved=%d-%d";
    private int channelNumber;
    private final LinkedBlockingQueue<byte[]> packetQueue;
    private final long pollTimeoutMs;
    private byte[] unreadData;

    public TransferRtpDataChannel(long j2) {
        super(true);
        this.pollTimeoutMs = j2;
        this.packetQueue = new LinkedBlockingQueue<>();
        this.unreadData = new byte[0];
        this.channelNumber = -1;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public RtspMessageChannel.InterleavedBinaryDataListener getInterleavedBinaryDataListener() {
        return this;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public int getLocalPort() {
        return this.channelNumber;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public String getTransport() {
        Assertions.checkState(this.channelNumber != -1);
        return Util.formatInvariant(DEFAULT_TCP_TRANSPORT_FORMAT, Integer.valueOf(this.channelNumber), Integer.valueOf(this.channelNumber + 1));
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return null;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtspMessageChannel.InterleavedBinaryDataListener
    public void onInterleavedBinaryDataReceived(byte[] bArr) {
        this.packetQueue.add(bArr);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) {
        this.channelNumber = dataSpec.uri.getPort();
        return -1L;
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i2, int i3) throws InterruptedException {
        if (i3 == 0) {
            return 0;
        }
        int iMin = Math.min(i3, this.unreadData.length);
        System.arraycopy(this.unreadData, 0, bArr, i2, iMin);
        int i4 = iMin + 0;
        byte[] bArr2 = this.unreadData;
        this.unreadData = Arrays.copyOfRange(bArr2, iMin, bArr2.length);
        if (i4 == i3) {
            return i4;
        }
        try {
            byte[] bArrPoll = this.packetQueue.poll(this.pollTimeoutMs, TimeUnit.MILLISECONDS);
            if (bArrPoll == null) {
                return -1;
            }
            int iMin2 = Math.min(i3 - i4, bArrPoll.length);
            System.arraycopy(bArrPoll, 0, bArr, i2 + i4, iMin2);
            if (iMin2 < bArrPoll.length) {
                this.unreadData = Arrays.copyOfRange(bArrPoll, iMin2, bArrPoll.length);
            }
            return i4 + iMin2;
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }
}
