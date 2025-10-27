package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Assertions;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
final class ChannelMappingAudioProcessor extends BaseAudioProcessor {

    @Nullable
    private int[] outputChannels;

    @Nullable
    private int[] pendingOutputChannels;

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        int[] iArr = this.pendingOutputChannels;
        if (iArr == null) {
            return AudioProcessor.AudioFormat.NOT_SET;
        }
        if (audioFormat.encoding != 2) {
            throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
        }
        boolean z2 = audioFormat.channelCount != iArr.length;
        int i2 = 0;
        while (i2 < iArr.length) {
            int i3 = iArr[i2];
            if (i3 >= audioFormat.channelCount) {
                throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
            }
            z2 |= i3 != i2;
            i2++;
        }
        return z2 ? new AudioProcessor.AudioFormat(audioFormat.sampleRate, iArr.length, 2) : AudioProcessor.AudioFormat.NOT_SET;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public void onFlush() {
        this.outputChannels = this.pendingOutputChannels;
    }

    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public void onReset() {
        this.outputChannels = null;
        this.pendingOutputChannels = null;
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        int[] iArr = (int[]) Assertions.checkNotNull(this.outputChannels);
        int iPosition = byteBuffer.position();
        int iLimit = byteBuffer.limit();
        ByteBuffer byteBufferReplaceOutputBuffer = replaceOutputBuffer(((iLimit - iPosition) / this.inputAudioFormat.bytesPerFrame) * this.outputAudioFormat.bytesPerFrame);
        while (iPosition < iLimit) {
            for (int i2 : iArr) {
                byteBufferReplaceOutputBuffer.putShort(byteBuffer.getShort((i2 * 2) + iPosition));
            }
            iPosition += this.inputAudioFormat.bytesPerFrame;
        }
        byteBuffer.position(iLimit);
        byteBufferReplaceOutputBuffer.flip();
    }

    public void setChannelMap(@Nullable int[] iArr) {
        this.pendingOutputChannels = iArr;
    }
}
