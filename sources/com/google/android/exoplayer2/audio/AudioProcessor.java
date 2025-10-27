package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes3.dex */
public interface AudioProcessor {
    public static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder());

    public static final class AudioFormat {
        public static final AudioFormat NOT_SET = new AudioFormat(-1, -1, -1);
        public final int bytesPerFrame;
        public final int channelCount;
        public final int encoding;
        public final int sampleRate;

        public AudioFormat(int i2, int i3, int i4) {
            this.sampleRate = i2;
            this.channelCount = i3;
            this.encoding = i4;
            this.bytesPerFrame = Util.isEncodingLinearPcm(i4) ? Util.getPcmFrameSize(i4, i3) : -1;
        }

        public String toString() {
            int i2 = this.sampleRate;
            int i3 = this.channelCount;
            int i4 = this.encoding;
            StringBuilder sb = new StringBuilder(83);
            sb.append("AudioFormat[sampleRate=");
            sb.append(i2);
            sb.append(", channelCount=");
            sb.append(i3);
            sb.append(", encoding=");
            sb.append(i4);
            sb.append(']');
            return sb.toString();
        }
    }

    public static final class UnhandledAudioFormatException extends Exception {
        public UnhandledAudioFormatException(AudioFormat audioFormat) {
            String strValueOf = String.valueOf(audioFormat);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 18);
            sb.append("Unhandled format: ");
            sb.append(strValueOf);
            super(sb.toString());
        }
    }

    AudioFormat configure(AudioFormat audioFormat) throws UnhandledAudioFormatException;

    void flush();

    ByteBuffer getOutput();

    boolean isActive();

    boolean isEnded();

    void queueEndOfStream();

    void queueInput(ByteBuffer byteBuffer);

    void reset();
}
