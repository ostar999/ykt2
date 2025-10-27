package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.google.android.exoplayer2.extractor.ogg.StreamReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.Arrays;
import okio.Utf8;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* loaded from: classes3.dex */
final class OpusReader extends StreamReader {
    private static final int OPUS_CODE = 1332770163;
    private static final byte[] OPUS_SIGNATURE = {79, 112, 117, 115, 72, 101, 97, 100};
    private boolean headerRead;

    private long getPacketDurationUs(byte[] bArr) {
        int i2;
        int i3 = bArr[0] & 255;
        int i4 = i3 & 3;
        if (i4 != 0) {
            i2 = 2;
            if (i4 != 1 && i4 != 2) {
                i2 = bArr[1] & Utf8.REPLACEMENT_BYTE;
            }
        } else {
            i2 = 1;
        }
        int i5 = i3 >> 3;
        return i2 * (i5 >= 16 ? 2500 << r1 : i5 >= 12 ? 10000 << (r1 & 1) : (i5 & 3) == 3 ? 60000 : 10000 << r1);
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        int iBytesLeft = parsableByteArray.bytesLeft();
        byte[] bArr = OPUS_SIGNATURE;
        if (iBytesLeft < bArr.length) {
            return false;
        }
        byte[] bArr2 = new byte[bArr.length];
        parsableByteArray.readBytes(bArr2, 0, bArr.length);
        return Arrays.equals(bArr2, bArr);
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public long preparePayload(ParsableByteArray parsableByteArray) {
        return convertTimeToGranule(getPacketDurationUs(parsableByteArray.getData()));
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    public boolean readHeaders(ParsableByteArray parsableByteArray, long j2, StreamReader.SetupData setupData) {
        if (this.headerRead) {
            Assertions.checkNotNull(setupData.format);
            boolean z2 = parsableByteArray.readInt() == 1332770163;
            parsableByteArray.setPosition(0);
            return z2;
        }
        byte[] bArrCopyOf = Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit());
        setupData.format = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_OPUS).setChannelCount(OpusUtil.getChannelCount(bArrCopyOf)).setSampleRate(48000).setInitializationData(OpusUtil.buildInitializationData(bArrCopyOf)).build();
        this.headerRead = true;
        return true;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public void reset(boolean z2) {
        super.reset(z2);
        if (z2) {
            this.headerRead = false;
        }
    }
}
