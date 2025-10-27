package com.google.android.exoplayer2.source.rtsp;

import android.os.Handler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.source.rtsp.RtpDataChannel;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.DataSourceUtil;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* loaded from: classes3.dex */
final class RtpDataLoadable implements Loader.Loadable {
    private final EventListener eventListener;
    private RtpExtractor extractor;
    private volatile boolean loadCancelled;
    private volatile long nextRtpTimestamp;
    private final ExtractorOutput output;
    private final RtpDataChannel.Factory rtpDataChannelFactory;
    public final RtspMediaTrack rtspMediaTrack;
    public final int trackId;
    private final Handler playbackThreadHandler = Util.createHandlerForCurrentLooper();
    private volatile long pendingSeekPositionUs = C.TIME_UNSET;

    public interface EventListener {
        void onTransportReady(String str, RtpDataChannel rtpDataChannel);
    }

    public RtpDataLoadable(int i2, RtspMediaTrack rtspMediaTrack, EventListener eventListener, ExtractorOutput extractorOutput, RtpDataChannel.Factory factory) {
        this.trackId = i2;
        this.rtspMediaTrack = rtspMediaTrack;
        this.eventListener = eventListener;
        this.output = extractorOutput;
        this.rtpDataChannelFactory = factory;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$load$0(String str, RtpDataChannel rtpDataChannel) {
        this.eventListener.onTransportReady(str, rtpDataChannel);
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void cancelLoad() {
        this.loadCancelled = true;
    }

    @Override // com.google.android.exoplayer2.upstream.Loader.Loadable
    public void load() throws IOException {
        final RtpDataChannel rtpDataChannelCreateAndOpenDataChannel = null;
        try {
            rtpDataChannelCreateAndOpenDataChannel = this.rtpDataChannelFactory.createAndOpenDataChannel(this.trackId);
            final String transport = rtpDataChannelCreateAndOpenDataChannel.getTransport();
            this.playbackThreadHandler.post(new Runnable() { // from class: com.google.android.exoplayer2.source.rtsp.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f6883c.lambda$load$0(transport, rtpDataChannelCreateAndOpenDataChannel);
                }
            });
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput((DataReader) Assertions.checkNotNull(rtpDataChannelCreateAndOpenDataChannel), 0L, -1L);
            RtpExtractor rtpExtractor = new RtpExtractor(this.rtspMediaTrack.payloadFormat, this.trackId);
            this.extractor = rtpExtractor;
            rtpExtractor.init(this.output);
            while (!this.loadCancelled) {
                if (this.pendingSeekPositionUs != C.TIME_UNSET) {
                    this.extractor.seek(this.nextRtpTimestamp, this.pendingSeekPositionUs);
                    this.pendingSeekPositionUs = C.TIME_UNSET;
                }
                if (this.extractor.read(defaultExtractorInput, new PositionHolder()) == -1) {
                    break;
                }
            }
        } finally {
            DataSourceUtil.closeQuietly(rtpDataChannelCreateAndOpenDataChannel);
        }
    }

    public void resetForSeek() {
        ((RtpExtractor) Assertions.checkNotNull(this.extractor)).preSeek();
    }

    public void seekToUs(long j2, long j3) {
        this.pendingSeekPositionUs = j2;
        this.nextRtpTimestamp = j3;
    }

    public void setSequenceNumber(int i2) {
        if (((RtpExtractor) Assertions.checkNotNull(this.extractor)).hasReadFirstRtpPacket()) {
            return;
        }
        this.extractor.setFirstSequenceNumber(i2);
    }

    public void setTimestamp(long j2) {
        if (j2 == C.TIME_UNSET || ((RtpExtractor) Assertions.checkNotNull(this.extractor)).hasReadFirstRtpPacket()) {
            return;
        }
        this.extractor.setFirstTimestamp(j2);
    }
}
