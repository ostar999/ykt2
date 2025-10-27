package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
final class ResamplingAudioProcessor extends BaseAudioProcessor {
    @Override // com.google.android.exoplayer2.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        int i2 = audioFormat.encoding;
        if (i2 == 3 || i2 == 2 || i2 == 268435456 || i2 == 536870912 || i2 == 805306368 || i2 == 4) {
            return i2 != 2 ? new AudioProcessor.AudioFormat(audioFormat.sampleRate, audioFormat.channelCount, 2) : AudioProcessor.AudioFormat.NOT_SET;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    @Override // com.google.android.exoplayer2.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        int iLimit = byteBuffer.limit();
        int i2 = iLimit - iPosition;
        int i3 = this.inputAudioFormat.encoding;
        if (i3 == 3) {
            i2 *= 2;
        } else if (i3 == 4) {
            i2 /= 2;
        } else if (i3 != 268435456) {
            if (i3 != 536870912) {
                if (i3 != 805306368) {
                    throw new IllegalStateException();
                }
                i2 /= 2;
            } else {
                i2 /= 3;
                i2 *= 2;
            }
        }
        ByteBuffer byteBufferReplaceOutputBuffer = replaceOutputBuffer(i2);
        int i4 = this.inputAudioFormat.encoding;
        if (i4 == 3) {
            while (iPosition < iLimit) {
                byteBufferReplaceOutputBuffer.put((byte) 0);
                byteBufferReplaceOutputBuffer.put((byte) ((byteBuffer.get(iPosition) & 255) - 128));
                iPosition++;
            }
        } else if (i4 == 4) {
            while (iPosition < iLimit) {
                short sConstrainValue = (short) (Util.constrainValue(byteBuffer.getFloat(iPosition), -1.0f, 1.0f) * 32767.0f);
                byteBufferReplaceOutputBuffer.put((byte) (sConstrainValue & 255));
                byteBufferReplaceOutputBuffer.put((byte) ((sConstrainValue >> 8) & 255));
                iPosition += 4;
            }
        } else if (i4 == 268435456) {
            while (iPosition < iLimit) {
                byteBufferReplaceOutputBuffer.put(byteBuffer.get(iPosition + 1));
                byteBufferReplaceOutputBuffer.put(byteBuffer.get(iPosition));
                iPosition += 2;
            }
        } else if (i4 == 536870912) {
            while (iPosition < iLimit) {
                byteBufferReplaceOutputBuffer.put(byteBuffer.get(iPosition + 1));
                byteBufferReplaceOutputBuffer.put(byteBuffer.get(iPosition + 2));
                iPosition += 3;
            }
        } else {
            if (i4 != 805306368) {
                throw new IllegalStateException();
            }
            while (iPosition < iLimit) {
                byteBufferReplaceOutputBuffer.put(byteBuffer.get(iPosition + 2));
                byteBufferReplaceOutputBuffer.put(byteBuffer.get(iPosition + 3));
                iPosition += 4;
            }
        }
        byteBuffer.position(byteBuffer.limit());
        byteBufferReplaceOutputBuffer.flip();
    }
}
