package com.google.android.exoplayer2.metadata;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.b2;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class MetadataRenderer extends BaseRenderer implements Handler.Callback {
    private static final int MSG_INVOKE_RENDERER = 0;
    private static final String TAG = "MetadataRenderer";
    private final MetadataInputBuffer buffer;

    @Nullable
    private MetadataDecoder decoder;
    private final MetadataDecoderFactory decoderFactory;
    private boolean inputStreamEnded;
    private final MetadataOutput output;

    @Nullable
    private final Handler outputHandler;
    private boolean outputStreamEnded;

    @Nullable
    private Metadata pendingMetadata;
    private long pendingMetadataTimestampUs;
    private long subsampleOffsetUs;

    public MetadataRenderer(MetadataOutput metadataOutput, @Nullable Looper looper) {
        this(metadataOutput, looper, MetadataDecoderFactory.DEFAULT);
    }

    private void decodeWrappedMetadata(Metadata metadata, List<Metadata.Entry> list) {
        for (int i2 = 0; i2 < metadata.length(); i2++) {
            Format wrappedMetadataFormat = metadata.get(i2).getWrappedMetadataFormat();
            if (wrappedMetadataFormat == null || !this.decoderFactory.supportsFormat(wrappedMetadataFormat)) {
                list.add(metadata.get(i2));
            } else {
                MetadataDecoder metadataDecoderCreateDecoder = this.decoderFactory.createDecoder(wrappedMetadataFormat);
                byte[] bArr = (byte[]) Assertions.checkNotNull(metadata.get(i2).getWrappedMetadataBytes());
                this.buffer.clear();
                this.buffer.ensureSpaceForWrite(bArr.length);
                ((ByteBuffer) Util.castNonNull(this.buffer.data)).put(bArr);
                this.buffer.flip();
                Metadata metadataDecode = metadataDecoderCreateDecoder.decode(this.buffer);
                if (metadataDecode != null) {
                    decodeWrappedMetadata(metadataDecode, list);
                }
            }
        }
    }

    private void invokeRenderer(Metadata metadata) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(0, metadata).sendToTarget();
        } else {
            invokeRendererInternal(metadata);
        }
    }

    private void invokeRendererInternal(Metadata metadata) {
        this.output.onMetadata(metadata);
    }

    private boolean outputMetadata(long j2) {
        boolean z2;
        Metadata metadata = this.pendingMetadata;
        if (metadata == null || this.pendingMetadataTimestampUs > j2) {
            z2 = false;
        } else {
            invokeRenderer(metadata);
            this.pendingMetadata = null;
            this.pendingMetadataTimestampUs = C.TIME_UNSET;
            z2 = true;
        }
        if (this.inputStreamEnded && this.pendingMetadata == null) {
            this.outputStreamEnded = true;
        }
        return z2;
    }

    private void readMetadata() {
        if (this.inputStreamEnded || this.pendingMetadata != null) {
            return;
        }
        this.buffer.clear();
        FormatHolder formatHolder = getFormatHolder();
        int source = readSource(formatHolder, this.buffer, 0);
        if (source != -4) {
            if (source == -5) {
                this.subsampleOffsetUs = ((Format) Assertions.checkNotNull(formatHolder.format)).subsampleOffsetUs;
                return;
            }
            return;
        }
        if (this.buffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            return;
        }
        MetadataInputBuffer metadataInputBuffer = this.buffer;
        metadataInputBuffer.subsampleOffsetUs = this.subsampleOffsetUs;
        metadataInputBuffer.flip();
        Metadata metadataDecode = ((MetadataDecoder) Util.castNonNull(this.decoder)).decode(this.buffer);
        if (metadataDecode != null) {
            ArrayList arrayList = new ArrayList(metadataDecode.length());
            decodeWrappedMetadata(metadataDecode, arrayList);
            if (arrayList.isEmpty()) {
                return;
            }
            this.pendingMetadata = new Metadata(arrayList);
            this.pendingMetadataTimestampUs = this.buffer.timeUs;
        }
    }

    @Override // com.google.android.exoplayer2.Renderer, com.google.android.exoplayer2.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 0) {
            throw new IllegalStateException();
        }
        invokeRendererInternal((Metadata) message.obj);
        return true;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isEnded() {
        return this.outputStreamEnded;
    }

    @Override // com.google.android.exoplayer2.Renderer
    public boolean isReady() {
        return true;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onDisabled() {
        this.pendingMetadata = null;
        this.pendingMetadataTimestampUs = C.TIME_UNSET;
        this.decoder = null;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onPositionReset(long j2, boolean z2) {
        this.pendingMetadata = null;
        this.pendingMetadataTimestampUs = C.TIME_UNSET;
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j2, long j3) {
        this.decoder = this.decoderFactory.createDecoder(formatArr[0]);
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j2, long j3) {
        boolean zOutputMetadata = true;
        while (zOutputMetadata) {
            readMetadata();
            zOutputMetadata = outputMetadata(j2);
        }
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (this.decoderFactory.supportsFormat(format)) {
            return b2.a(format.cryptoType == 0 ? 4 : 2);
        }
        return b2.a(0);
    }

    public MetadataRenderer(MetadataOutput metadataOutput, @Nullable Looper looper, MetadataDecoderFactory metadataDecoderFactory) {
        super(5);
        this.output = (MetadataOutput) Assertions.checkNotNull(metadataOutput);
        this.outputHandler = looper == null ? null : Util.createHandler(looper, this);
        this.decoderFactory = (MetadataDecoderFactory) Assertions.checkNotNull(metadataDecoderFactory);
        this.buffer = new MetadataInputBuffer();
        this.pendingMetadataTimestampUs = C.TIME_UNSET;
    }
}
