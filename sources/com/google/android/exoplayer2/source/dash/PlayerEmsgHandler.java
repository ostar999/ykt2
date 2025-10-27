package com.google.android.exoplayer2.source.dash;

import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.emsg.EventMessageDecoder;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes3.dex */
public final class PlayerEmsgHandler implements Handler.Callback {
    private static final int EMSG_MANIFEST_EXPIRED = 1;
    private final Allocator allocator;
    private boolean chunkLoadedCompletedSinceLastManifestRefreshRequest;
    private long expiredManifestPublishTimeUs;
    private boolean isWaitingForManifestRefresh;
    private DashManifest manifest;
    private final PlayerEmsgCallback playerEmsgCallback;
    private boolean released;
    private final TreeMap<Long, Long> manifestPublishTimeToExpiryTimeUs = new TreeMap<>();
    private final Handler handler = Util.createHandlerForCurrentLooper(this);
    private final EventMessageDecoder decoder = new EventMessageDecoder();

    public static final class ManifestExpiryEventInfo {
        public final long eventTimeUs;
        public final long manifestPublishTimeMsInEmsg;

        public ManifestExpiryEventInfo(long j2, long j3) {
            this.eventTimeUs = j2;
            this.manifestPublishTimeMsInEmsg = j3;
        }
    }

    public interface PlayerEmsgCallback {
        void onDashManifestPublishTimeExpired(long j2);

        void onDashManifestRefreshRequested();
    }

    public final class PlayerTrackEmsgHandler implements TrackOutput {
        private final SampleQueue sampleQueue;
        private final FormatHolder formatHolder = new FormatHolder();
        private final MetadataInputBuffer buffer = new MetadataInputBuffer();
        private long maxLoadedChunkEndTimeUs = C.TIME_UNSET;

        public PlayerTrackEmsgHandler(Allocator allocator) {
            this.sampleQueue = SampleQueue.createWithoutDrm(allocator);
        }

        @Nullable
        private MetadataInputBuffer dequeueSample() {
            this.buffer.clear();
            if (this.sampleQueue.read(this.formatHolder, this.buffer, 0, false) != -4) {
                return null;
            }
            this.buffer.flip();
            return this.buffer;
        }

        private void onManifestExpiredMessageEncountered(long j2, long j3) {
            PlayerEmsgHandler.this.handler.sendMessage(PlayerEmsgHandler.this.handler.obtainMessage(1, new ManifestExpiryEventInfo(j2, j3)));
        }

        private void parseAndDiscardSamples() {
            while (this.sampleQueue.isReady(false)) {
                MetadataInputBuffer metadataInputBufferDequeueSample = dequeueSample();
                if (metadataInputBufferDequeueSample != null) {
                    long j2 = metadataInputBufferDequeueSample.timeUs;
                    Metadata metadataDecode = PlayerEmsgHandler.this.decoder.decode(metadataInputBufferDequeueSample);
                    if (metadataDecode != null) {
                        EventMessage eventMessage = (EventMessage) metadataDecode.get(0);
                        if (PlayerEmsgHandler.isPlayerEmsgEvent(eventMessage.schemeIdUri, eventMessage.value)) {
                            parsePlayerEmsgEvent(j2, eventMessage);
                        }
                    }
                }
            }
            this.sampleQueue.discardToRead();
        }

        private void parsePlayerEmsgEvent(long j2, EventMessage eventMessage) {
            long manifestPublishTimeMsInEmsg = PlayerEmsgHandler.getManifestPublishTimeMsInEmsg(eventMessage);
            if (manifestPublishTimeMsInEmsg == C.TIME_UNSET) {
                return;
            }
            onManifestExpiredMessageEncountered(j2, manifestPublishTimeMsInEmsg);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void format(Format format) {
            this.sampleQueue.format(format);
        }

        public boolean maybeRefreshManifestBeforeLoadingNextChunk(long j2) {
            return PlayerEmsgHandler.this.maybeRefreshManifestBeforeLoadingNextChunk(j2);
        }

        public void onChunkLoadCompleted(Chunk chunk) {
            long j2 = this.maxLoadedChunkEndTimeUs;
            if (j2 == C.TIME_UNSET || chunk.endTimeUs > j2) {
                this.maxLoadedChunkEndTimeUs = chunk.endTimeUs;
            }
            PlayerEmsgHandler.this.onChunkLoadCompleted(chunk);
        }

        public boolean onChunkLoadError(Chunk chunk) {
            long j2 = this.maxLoadedChunkEndTimeUs;
            return PlayerEmsgHandler.this.onChunkLoadError(j2 != C.TIME_UNSET && j2 < chunk.startTimeUs);
        }

        public void release() {
            this.sampleQueue.release();
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) {
            return com.google.android.exoplayer2.extractor.d.a(this, dataReader, i2, z2);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
            return this.sampleQueue.sampleData(dataReader, i2, z2);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
            com.google.android.exoplayer2.extractor.d.b(this, parsableByteArray, i2);
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
            this.sampleQueue.sampleMetadata(j2, i2, i3, i4, cryptoData);
            parseAndDiscardSamples();
        }

        @Override // com.google.android.exoplayer2.extractor.TrackOutput
        public void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
            this.sampleQueue.sampleData(parsableByteArray, i2);
        }
    }

    public PlayerEmsgHandler(DashManifest dashManifest, PlayerEmsgCallback playerEmsgCallback, Allocator allocator) {
        this.manifest = dashManifest;
        this.playerEmsgCallback = playerEmsgCallback;
        this.allocator = allocator;
    }

    @Nullable
    private Map.Entry<Long, Long> ceilingExpiryEntryForPublishTime(long j2) {
        return this.manifestPublishTimeToExpiryTimeUs.ceilingEntry(Long.valueOf(j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getManifestPublishTimeMsInEmsg(EventMessage eventMessage) {
        try {
            return Util.parseXsDateTime(Util.fromUtf8Bytes(eventMessage.messageData));
        } catch (ParserException unused) {
            return C.TIME_UNSET;
        }
    }

    private void handleManifestExpiredMessage(long j2, long j3) {
        Long l2 = this.manifestPublishTimeToExpiryTimeUs.get(Long.valueOf(j3));
        if (l2 == null) {
            this.manifestPublishTimeToExpiryTimeUs.put(Long.valueOf(j3), Long.valueOf(j2));
        } else if (l2.longValue() > j2) {
            this.manifestPublishTimeToExpiryTimeUs.put(Long.valueOf(j3), Long.valueOf(j2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPlayerEmsgEvent(String str, String str2) {
        return "urn:mpeg:dash:event:2012".equals(str) && ("1".equals(str2) || "2".equals(str2) || "3".equals(str2));
    }

    private void maybeNotifyDashManifestRefreshNeeded() {
        if (this.chunkLoadedCompletedSinceLastManifestRefreshRequest) {
            this.isWaitingForManifestRefresh = true;
            this.chunkLoadedCompletedSinceLastManifestRefreshRequest = false;
            this.playerEmsgCallback.onDashManifestRefreshRequested();
        }
    }

    private void notifyManifestPublishTimeExpired() {
        this.playerEmsgCallback.onDashManifestPublishTimeExpired(this.expiredManifestPublishTimeUs);
    }

    private void removePreviouslyExpiredManifestPublishTimeValues() {
        Iterator<Map.Entry<Long, Long>> it = this.manifestPublishTimeToExpiryTimeUs.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey().longValue() < this.manifest.publishTimeMs) {
                it.remove();
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (this.released) {
            return true;
        }
        if (message.what != 1) {
            return false;
        }
        ManifestExpiryEventInfo manifestExpiryEventInfo = (ManifestExpiryEventInfo) message.obj;
        handleManifestExpiredMessage(manifestExpiryEventInfo.eventTimeUs, manifestExpiryEventInfo.manifestPublishTimeMsInEmsg);
        return true;
    }

    public boolean maybeRefreshManifestBeforeLoadingNextChunk(long j2) {
        DashManifest dashManifest = this.manifest;
        boolean z2 = false;
        if (!dashManifest.dynamic) {
            return false;
        }
        if (this.isWaitingForManifestRefresh) {
            return true;
        }
        Map.Entry<Long, Long> entryCeilingExpiryEntryForPublishTime = ceilingExpiryEntryForPublishTime(dashManifest.publishTimeMs);
        if (entryCeilingExpiryEntryForPublishTime != null && entryCeilingExpiryEntryForPublishTime.getValue().longValue() < j2) {
            this.expiredManifestPublishTimeUs = entryCeilingExpiryEntryForPublishTime.getKey().longValue();
            notifyManifestPublishTimeExpired();
            z2 = true;
        }
        if (z2) {
            maybeNotifyDashManifestRefreshNeeded();
        }
        return z2;
    }

    public PlayerTrackEmsgHandler newPlayerTrackEmsgHandler() {
        return new PlayerTrackEmsgHandler(this.allocator);
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        this.chunkLoadedCompletedSinceLastManifestRefreshRequest = true;
    }

    public boolean onChunkLoadError(boolean z2) {
        if (!this.manifest.dynamic) {
            return false;
        }
        if (this.isWaitingForManifestRefresh) {
            return true;
        }
        if (!z2) {
            return false;
        }
        maybeNotifyDashManifestRefreshNeeded();
        return true;
    }

    public void release() {
        this.released = true;
        this.handler.removeCallbacksAndMessages(null);
    }

    public void updateManifest(DashManifest dashManifest) {
        this.isWaitingForManifestRefresh = false;
        this.expiredManifestPublishTimeUs = C.TIME_UNSET;
        this.manifest = dashManifest;
        removePreviouslyExpiredManifestPublishTimeValues();
    }
}
