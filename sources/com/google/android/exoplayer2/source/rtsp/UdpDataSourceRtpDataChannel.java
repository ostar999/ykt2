package com.google.android.exoplayer2.source.rtsp;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.rtsp.RtspMessageChannel;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.UdpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes3.dex */
final class UdpDataSourceRtpDataChannel implements RtpDataChannel {
    private static final String DEFAULT_UDP_TRANSPORT_FORMAT = "RTP/AVP;unicast;client_port=%d-%d";
    private final UdpDataSource dataSource;

    @Nullable
    private UdpDataSourceRtpDataChannel rtcpChannel;

    public UdpDataSourceRtpDataChannel(long j2) {
        this.dataSource = new UdpDataSource(2000, Ints.checkedCast(j2));
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
        this.dataSource.addTransferListener(transferListener);
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() throws IOException {
        this.dataSource.close();
        UdpDataSourceRtpDataChannel udpDataSourceRtpDataChannel = this.rtcpChannel;
        if (udpDataSourceRtpDataChannel != null) {
            udpDataSourceRtpDataChannel.close();
        }
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    @Nullable
    public RtspMessageChannel.InterleavedBinaryDataListener getInterleavedBinaryDataListener() {
        return null;
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public int getLocalPort() {
        int localPort = this.dataSource.getLocalPort();
        if (localPort == -1) {
            return -1;
        }
        return localPort;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public /* synthetic */ Map getResponseHeaders() {
        return com.google.android.exoplayer2.upstream.f.a(this);
    }

    @Override // com.google.android.exoplayer2.source.rtsp.RtpDataChannel
    public String getTransport() {
        int localPort = getLocalPort();
        Assertions.checkState(localPort != -1);
        return Util.formatInvariant(DEFAULT_UDP_TRANSPORT_FORMAT, Integer.valueOf(localPort), Integer.valueOf(localPort + 1));
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    @Nullable
    public Uri getUri() {
        return this.dataSource.getUri();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        return this.dataSource.open(dataSpec);
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        try {
            return this.dataSource.read(bArr, i2, i3);
        } catch (UdpDataSource.UdpDataSourceException e2) {
            if (e2.reason == 2002) {
                return -1;
            }
            throw e2;
        }
    }

    public void setRtcpChannel(UdpDataSourceRtpDataChannel udpDataSourceRtpDataChannel) {
        Assertions.checkArgument(this != udpDataSourceRtpDataChannel);
        this.rtcpChannel = udpDataSourceRtpDataChannel;
    }
}
