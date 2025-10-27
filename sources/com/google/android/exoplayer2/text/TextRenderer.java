package com.google.android.exoplayer2.text;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.BaseRenderer;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.b2;
import com.google.android.exoplayer2.decoder.DecoderException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class TextRenderer extends BaseRenderer implements Handler.Callback {
    private static final int MSG_UPDATE_OUTPUT = 0;
    private static final int REPLACEMENT_STATE_NONE = 0;
    private static final int REPLACEMENT_STATE_SIGNAL_END_OF_STREAM = 1;
    private static final int REPLACEMENT_STATE_WAIT_END_OF_STREAM = 2;
    private static final String TAG = "TextRenderer";

    @Nullable
    private SubtitleDecoder decoder;
    private final SubtitleDecoderFactory decoderFactory;
    private int decoderReplacementState;
    private long finalStreamEndPositionUs;
    private final FormatHolder formatHolder;
    private boolean inputStreamEnded;

    @Nullable
    private SubtitleInputBuffer nextInputBuffer;

    @Nullable
    private SubtitleOutputBuffer nextSubtitle;
    private int nextSubtitleEventIndex;
    private final TextOutput output;

    @Nullable
    private final Handler outputHandler;
    private boolean outputStreamEnded;

    @Nullable
    private Format streamFormat;

    @Nullable
    private SubtitleOutputBuffer subtitle;
    private boolean waitingForKeyFrame;

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper) {
        this(textOutput, looper, SubtitleDecoderFactory.DEFAULT);
    }

    private void clearOutput() {
        updateOutput(Collections.emptyList());
    }

    private long getNextEventTime() {
        if (this.nextSubtitleEventIndex == -1) {
            return Long.MAX_VALUE;
        }
        Assertions.checkNotNull(this.subtitle);
        if (this.nextSubtitleEventIndex >= this.subtitle.getEventTimeCount()) {
            return Long.MAX_VALUE;
        }
        return this.subtitle.getEventTime(this.nextSubtitleEventIndex);
    }

    private void handleDecoderError(SubtitleDecoderException subtitleDecoderException) {
        String strValueOf = String.valueOf(this.streamFormat);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 39);
        sb.append("Subtitle decoding failed. streamFormat=");
        sb.append(strValueOf);
        Log.e(TAG, sb.toString(), subtitleDecoderException);
        clearOutput();
        replaceDecoder();
    }

    private void initDecoder() {
        this.waitingForKeyFrame = true;
        this.decoder = this.decoderFactory.createDecoder((Format) Assertions.checkNotNull(this.streamFormat));
    }

    private void invokeUpdateOutputInternal(List<Cue> list) {
        this.output.onCues(list);
    }

    private void releaseBuffers() {
        this.nextInputBuffer = null;
        this.nextSubtitleEventIndex = -1;
        SubtitleOutputBuffer subtitleOutputBuffer = this.subtitle;
        if (subtitleOutputBuffer != null) {
            subtitleOutputBuffer.release();
            this.subtitle = null;
        }
        SubtitleOutputBuffer subtitleOutputBuffer2 = this.nextSubtitle;
        if (subtitleOutputBuffer2 != null) {
            subtitleOutputBuffer2.release();
            this.nextSubtitle = null;
        }
    }

    private void releaseDecoder() {
        releaseBuffers();
        ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).release();
        this.decoder = null;
        this.decoderReplacementState = 0;
    }

    private void replaceDecoder() {
        releaseDecoder();
        initDecoder();
    }

    private void updateOutput(List<Cue> list) {
        Handler handler = this.outputHandler;
        if (handler != null) {
            handler.obtainMessage(0, list).sendToTarget();
        } else {
            invokeUpdateOutputInternal(list);
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
        invokeUpdateOutputInternal((List) message.obj);
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
        this.streamFormat = null;
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        clearOutput();
        releaseDecoder();
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onPositionReset(long j2, boolean z2) {
        clearOutput();
        this.inputStreamEnded = false;
        this.outputStreamEnded = false;
        this.finalStreamEndPositionUs = C.TIME_UNSET;
        if (this.decoderReplacementState != 0) {
            replaceDecoder();
        } else {
            releaseBuffers();
            ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).flush();
        }
    }

    @Override // com.google.android.exoplayer2.BaseRenderer
    public void onStreamChanged(Format[] formatArr, long j2, long j3) {
        this.streamFormat = formatArr[0];
        if (this.decoder != null) {
            this.decoderReplacementState = 1;
        } else {
            initDecoder();
        }
    }

    @Override // com.google.android.exoplayer2.Renderer
    public void render(long j2, long j3) throws DecoderException {
        boolean z2;
        if (isCurrentStreamFinal()) {
            long j4 = this.finalStreamEndPositionUs;
            if (j4 != C.TIME_UNSET && j2 >= j4) {
                releaseBuffers();
                this.outputStreamEnded = true;
            }
        }
        if (this.outputStreamEnded) {
            return;
        }
        if (this.nextSubtitle == null) {
            ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).setPositionUs(j2);
            try {
                this.nextSubtitle = ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).dequeueOutputBuffer();
            } catch (SubtitleDecoderException e2) {
                handleDecoderError(e2);
                return;
            }
        }
        if (getState() != 2) {
            return;
        }
        if (this.subtitle != null) {
            long nextEventTime = getNextEventTime();
            z2 = false;
            while (nextEventTime <= j2) {
                this.nextSubtitleEventIndex++;
                nextEventTime = getNextEventTime();
                z2 = true;
            }
        } else {
            z2 = false;
        }
        SubtitleOutputBuffer subtitleOutputBuffer = this.nextSubtitle;
        if (subtitleOutputBuffer != null) {
            if (subtitleOutputBuffer.isEndOfStream()) {
                if (!z2 && getNextEventTime() == Long.MAX_VALUE) {
                    if (this.decoderReplacementState == 2) {
                        replaceDecoder();
                    } else {
                        releaseBuffers();
                        this.outputStreamEnded = true;
                    }
                }
            } else if (subtitleOutputBuffer.timeUs <= j2) {
                SubtitleOutputBuffer subtitleOutputBuffer2 = this.subtitle;
                if (subtitleOutputBuffer2 != null) {
                    subtitleOutputBuffer2.release();
                }
                this.nextSubtitleEventIndex = subtitleOutputBuffer.getNextEventTimeIndex(j2);
                this.subtitle = subtitleOutputBuffer;
                this.nextSubtitle = null;
                z2 = true;
            }
        }
        if (z2) {
            Assertions.checkNotNull(this.subtitle);
            updateOutput(this.subtitle.getCues(j2));
        }
        if (this.decoderReplacementState == 2) {
            return;
        }
        while (!this.inputStreamEnded) {
            try {
                SubtitleInputBuffer subtitleInputBufferDequeueInputBuffer = this.nextInputBuffer;
                if (subtitleInputBufferDequeueInputBuffer == null) {
                    subtitleInputBufferDequeueInputBuffer = ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).dequeueInputBuffer();
                    if (subtitleInputBufferDequeueInputBuffer == null) {
                        return;
                    } else {
                        this.nextInputBuffer = subtitleInputBufferDequeueInputBuffer;
                    }
                }
                if (this.decoderReplacementState == 1) {
                    subtitleInputBufferDequeueInputBuffer.setFlags(4);
                    ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).queueInputBuffer(subtitleInputBufferDequeueInputBuffer);
                    this.nextInputBuffer = null;
                    this.decoderReplacementState = 2;
                    return;
                }
                int source = readSource(this.formatHolder, subtitleInputBufferDequeueInputBuffer, 0);
                if (source == -4) {
                    if (subtitleInputBufferDequeueInputBuffer.isEndOfStream()) {
                        this.inputStreamEnded = true;
                        this.waitingForKeyFrame = false;
                    } else {
                        Format format = this.formatHolder.format;
                        if (format == null) {
                            return;
                        }
                        subtitleInputBufferDequeueInputBuffer.subsampleOffsetUs = format.subsampleOffsetUs;
                        subtitleInputBufferDequeueInputBuffer.flip();
                        this.waitingForKeyFrame &= !subtitleInputBufferDequeueInputBuffer.isKeyFrame();
                    }
                    if (!this.waitingForKeyFrame) {
                        ((SubtitleDecoder) Assertions.checkNotNull(this.decoder)).queueInputBuffer(subtitleInputBufferDequeueInputBuffer);
                        this.nextInputBuffer = null;
                    }
                } else if (source == -3) {
                    return;
                }
            } catch (SubtitleDecoderException e3) {
                handleDecoderError(e3);
                return;
            }
        }
    }

    public void setFinalStreamEndPositionUs(long j2) {
        Assertions.checkState(isCurrentStreamFinal());
        this.finalStreamEndPositionUs = j2;
    }

    @Override // com.google.android.exoplayer2.RendererCapabilities
    public int supportsFormat(Format format) {
        if (this.decoderFactory.supportsFormat(format)) {
            return b2.a(format.cryptoType == 0 ? 4 : 2);
        }
        return MimeTypes.isText(format.sampleMimeType) ? b2.a(1) : b2.a(0);
    }

    public TextRenderer(TextOutput textOutput, @Nullable Looper looper, SubtitleDecoderFactory subtitleDecoderFactory) {
        super(3);
        this.output = (TextOutput) Assertions.checkNotNull(textOutput);
        this.outputHandler = looper == null ? null : Util.createHandler(looper, this);
        this.decoderFactory = subtitleDecoderFactory;
        this.formatHolder = new FormatHolder();
        this.finalStreamEndPositionUs = C.TIME_UNSET;
    }
}
