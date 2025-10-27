package com.google.android.exoplayer2.transformer;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.easefun.polyv.mediasdk.player.misc.IMediaFormat;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecInfo;
import com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MediaFormatUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

@RequiresApi(18)
/* loaded from: classes3.dex */
final class MediaCodecAdapterWrapper {
    private static final int MEDIA_CODEC_PCM_ENCODING = 2;
    private final MediaCodecAdapter codec;
    private boolean inputStreamEnded;

    @Nullable
    private ByteBuffer outputBuffer;
    private Format outputFormat;
    private boolean outputStreamEnded;
    private final MediaCodec.BufferInfo outputBufferInfo = new MediaCodec.BufferInfo();
    private int inputBufferIndex = -1;
    private int outputBufferIndex = -1;

    public static class Factory extends SynchronousMediaCodecAdapter.Factory {
        private Factory() {
        }

        @Override // com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter.Factory
        public MediaCodec createCodec(MediaCodecAdapter.Configuration configuration) throws IOException {
            String str = (String) Assertions.checkNotNull(configuration.mediaFormat.getString(IMediaFormat.KEY_MIME));
            return (configuration.flags & 1) == 0 ? MediaCodec.createDecoderByType((String) Assertions.checkNotNull(str)) : MediaCodec.createEncoderByType((String) Assertions.checkNotNull(str));
        }
    }

    private MediaCodecAdapterWrapper(MediaCodecAdapter mediaCodecAdapter) {
        this.codec = mediaCodecAdapter;
    }

    public static MediaCodecAdapterWrapper createForAudioDecoding(Format format) throws IOException {
        MediaCodecAdapter mediaCodecAdapterCreateAdapter = null;
        byte b3 = 0;
        try {
            MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat((String) Assertions.checkNotNull(format.sampleMimeType), format.sampleRate, format.channelCount);
            MediaFormatUtil.maybeSetInteger(mediaFormatCreateAudioFormat, "max-input-size", format.maxInputSize);
            MediaFormatUtil.setCsdBuffers(mediaFormatCreateAudioFormat, format.initializationData);
            mediaCodecAdapterCreateAdapter = new Factory().createAdapter(MediaCodecAdapter.Configuration.createForAudioDecoding(createPlaceholderMediaCodecInfo(), mediaFormatCreateAudioFormat, format, null));
            return new MediaCodecAdapterWrapper(mediaCodecAdapterCreateAdapter);
        } catch (Exception e2) {
            if (mediaCodecAdapterCreateAdapter != null) {
                mediaCodecAdapterCreateAdapter.release();
            }
            throw e2;
        }
    }

    public static MediaCodecAdapterWrapper createForAudioEncoding(Format format) throws Exception {
        MediaCodecAdapter mediaCodecAdapterCreateAdapter = null;
        byte b3 = 0;
        try {
            MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat((String) Assertions.checkNotNull(format.sampleMimeType), format.sampleRate, format.channelCount);
            mediaFormatCreateAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, format.bitrate);
            mediaCodecAdapterCreateAdapter = new Factory().createAdapter(MediaCodecAdapter.Configuration.createForAudioEncoding(createPlaceholderMediaCodecInfo(), mediaFormatCreateAudioFormat, format));
            return new MediaCodecAdapterWrapper(mediaCodecAdapterCreateAdapter);
        } catch (Exception e2) {
            if (mediaCodecAdapterCreateAdapter != null) {
                mediaCodecAdapterCreateAdapter.release();
            }
            throw e2;
        }
    }

    public static MediaCodecAdapterWrapper createForVideoDecoding(Format format, Surface surface) throws Exception {
        MediaCodecAdapter mediaCodecAdapterCreateAdapter = null;
        byte b3 = 0;
        try {
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat((String) Assertions.checkNotNull(format.sampleMimeType), format.width, format.height);
            MediaFormatUtil.maybeSetInteger(mediaFormatCreateVideoFormat, "max-input-size", format.maxInputSize);
            MediaFormatUtil.setCsdBuffers(mediaFormatCreateVideoFormat, format.initializationData);
            mediaCodecAdapterCreateAdapter = new Factory().createAdapter(MediaCodecAdapter.Configuration.createForVideoDecoding(createPlaceholderMediaCodecInfo(), mediaFormatCreateVideoFormat, format, surface, null));
            return new MediaCodecAdapterWrapper(mediaCodecAdapterCreateAdapter);
        } catch (Exception e2) {
            if (mediaCodecAdapterCreateAdapter != null) {
                mediaCodecAdapterCreateAdapter.release();
            }
            throw e2;
        }
    }

    public static MediaCodecAdapterWrapper createForVideoEncoding(Format format, Map<String, Integer> map) throws Exception {
        Assertions.checkArgument(format.width != -1);
        Assertions.checkArgument(format.height != -1);
        MediaCodecAdapter mediaCodecAdapterCreateAdapter = null;
        byte b3 = 0;
        try {
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat((String) Assertions.checkNotNull(format.sampleMimeType), format.width, format.height);
            mediaFormatCreateVideoFormat.setInteger("color-format", 2130708361);
            mediaFormatCreateVideoFormat.setInteger("frame-rate", 30);
            mediaFormatCreateVideoFormat.setInteger("i-frame-interval", 1);
            mediaFormatCreateVideoFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, 413000);
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                mediaFormatCreateVideoFormat.setInteger(entry.getKey(), entry.getValue().intValue());
            }
            mediaCodecAdapterCreateAdapter = new Factory().createAdapter(MediaCodecAdapter.Configuration.createForVideoEncoding(createPlaceholderMediaCodecInfo(), mediaFormatCreateVideoFormat, format));
            return new MediaCodecAdapterWrapper(mediaCodecAdapterCreateAdapter);
        } catch (Exception e2) {
            if (mediaCodecAdapterCreateAdapter != null) {
                mediaCodecAdapterCreateAdapter.release();
            }
            throw e2;
        }
    }

    private static MediaCodecInfo createPlaceholderMediaCodecInfo() {
        return MediaCodecInfo.newInstance("name-placeholder", "mime-type-placeholder", "mime-type-placeholder", null, false, false, false, false, false);
    }

    private static Format getFormat(MediaFormat mediaFormat) {
        ImmutableList.Builder builder = new ImmutableList.Builder();
        int i2 = 0;
        while (true) {
            StringBuilder sb = new StringBuilder(15);
            sb.append("csd-");
            sb.append(i2);
            ByteBuffer byteBuffer = mediaFormat.getByteBuffer(sb.toString());
            if (byteBuffer == null) {
                break;
            }
            byte[] bArr = new byte[byteBuffer.remaining()];
            byteBuffer.get(bArr);
            builder.add((ImmutableList.Builder) bArr);
            i2++;
        }
        String string = mediaFormat.getString(IMediaFormat.KEY_MIME);
        Format.Builder initializationData = new Format.Builder().setSampleMimeType(mediaFormat.getString(IMediaFormat.KEY_MIME)).setInitializationData(builder.build());
        if (MimeTypes.isVideo(string)) {
            initializationData.setWidth(mediaFormat.getInteger("width")).setHeight(mediaFormat.getInteger("height"));
        } else if (MimeTypes.isAudio(string)) {
            initializationData.setChannelCount(mediaFormat.getInteger("channel-count")).setSampleRate(mediaFormat.getInteger("sample-rate")).setPcmEncoding(2);
        }
        return initializationData.build();
    }

    private boolean maybeDequeueAndSetOutputBuffer() {
        if (!maybeDequeueOutputBuffer()) {
            return false;
        }
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(this.codec.getOutputBuffer(this.outputBufferIndex));
        this.outputBuffer = byteBuffer;
        byteBuffer.position(this.outputBufferInfo.offset);
        ByteBuffer byteBuffer2 = this.outputBuffer;
        MediaCodec.BufferInfo bufferInfo = this.outputBufferInfo;
        byteBuffer2.limit(bufferInfo.offset + bufferInfo.size);
        return true;
    }

    private boolean maybeDequeueOutputBuffer() {
        if (this.outputBufferIndex >= 0) {
            return true;
        }
        if (this.outputStreamEnded) {
            return false;
        }
        int iDequeueOutputBufferIndex = this.codec.dequeueOutputBufferIndex(this.outputBufferInfo);
        this.outputBufferIndex = iDequeueOutputBufferIndex;
        if (iDequeueOutputBufferIndex < 0) {
            if (iDequeueOutputBufferIndex == -2) {
                this.outputFormat = getFormat(this.codec.getOutputFormat());
            }
            return false;
        }
        MediaCodec.BufferInfo bufferInfo = this.outputBufferInfo;
        int i2 = bufferInfo.flags;
        if ((i2 & 4) != 0) {
            this.outputStreamEnded = true;
            if (bufferInfo.size == 0) {
                releaseOutputBuffer();
                return false;
            }
        }
        if ((i2 & 2) == 0) {
            return true;
        }
        releaseOutputBuffer();
        return false;
    }

    @Nullable
    public Surface getInputSurface() {
        return this.codec.getInputSurface();
    }

    @Nullable
    public ByteBuffer getOutputBuffer() {
        if (maybeDequeueAndSetOutputBuffer()) {
            return this.outputBuffer;
        }
        return null;
    }

    @Nullable
    public MediaCodec.BufferInfo getOutputBufferInfo() {
        if (maybeDequeueOutputBuffer()) {
            return this.outputBufferInfo;
        }
        return null;
    }

    @Nullable
    public Format getOutputFormat() {
        maybeDequeueOutputBuffer();
        return this.outputFormat;
    }

    public boolean isEnded() {
        return this.outputStreamEnded && this.outputBufferIndex == -1;
    }

    @EnsuresNonNullIf(expression = {"#1.data"}, result = true)
    public boolean maybeDequeueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        if (this.inputStreamEnded) {
            return false;
        }
        if (this.inputBufferIndex < 0) {
            int iDequeueInputBufferIndex = this.codec.dequeueInputBufferIndex();
            this.inputBufferIndex = iDequeueInputBufferIndex;
            if (iDequeueInputBufferIndex < 0) {
                return false;
            }
            decoderInputBuffer.data = this.codec.getInputBuffer(iDequeueInputBufferIndex);
            decoderInputBuffer.clear();
        }
        Assertions.checkNotNull(decoderInputBuffer.data);
        return true;
    }

    public void queueInputBuffer(DecoderInputBuffer decoderInputBuffer) {
        int iPosition;
        int iRemaining;
        Assertions.checkState(!this.inputStreamEnded, "Input buffer can not be queued after the input stream has ended.");
        ByteBuffer byteBuffer = decoderInputBuffer.data;
        int i2 = 0;
        if (byteBuffer == null || !byteBuffer.hasRemaining()) {
            iPosition = 0;
            iRemaining = 0;
        } else {
            iPosition = decoderInputBuffer.data.position();
            iRemaining = decoderInputBuffer.data.remaining();
        }
        if (decoderInputBuffer.isEndOfStream()) {
            this.inputStreamEnded = true;
            i2 = 4;
        }
        this.codec.queueInputBuffer(this.inputBufferIndex, iPosition, iRemaining, decoderInputBuffer.timeUs, i2);
        this.inputBufferIndex = -1;
        decoderInputBuffer.data = null;
    }

    public void release() {
        this.outputBuffer = null;
        this.codec.release();
    }

    public void releaseOutputBuffer() {
        releaseOutputBuffer(false);
    }

    @RequiresApi(18)
    public void signalEndOfInputStream() {
        this.codec.signalEndOfInputStream();
    }

    public void releaseOutputBuffer(boolean z2) {
        this.outputBuffer = null;
        this.codec.releaseOutputBuffer(this.outputBufferIndex, z2);
        this.outputBufferIndex = -1;
    }
}
