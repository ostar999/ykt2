package com.google.android.exoplayer2.extractor.ogg;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.VorbisUtil;
import com.google.android.exoplayer2.extractor.ogg.StreamReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;

/* loaded from: classes3.dex */
final class VorbisReader extends StreamReader {

    @Nullable
    private VorbisUtil.CommentHeader commentHeader;
    private int previousPacketBlockSize;
    private boolean seenFirstAudioPacket;

    @Nullable
    private VorbisUtil.VorbisIdHeader vorbisIdHeader;

    @Nullable
    private VorbisSetup vorbisSetup;

    public static final class VorbisSetup {
        public final VorbisUtil.CommentHeader commentHeader;
        public final int iLogModes;
        public final VorbisUtil.VorbisIdHeader idHeader;
        public final VorbisUtil.Mode[] modes;
        public final byte[] setupHeaderData;

        public VorbisSetup(VorbisUtil.VorbisIdHeader vorbisIdHeader, VorbisUtil.CommentHeader commentHeader, byte[] bArr, VorbisUtil.Mode[] modeArr, int i2) {
            this.idHeader = vorbisIdHeader;
            this.commentHeader = commentHeader;
            this.setupHeaderData = bArr;
            this.modes = modeArr;
            this.iLogModes = i2;
        }
    }

    @VisibleForTesting
    public static void appendNumberOfSamples(ParsableByteArray parsableByteArray, long j2) {
        if (parsableByteArray.capacity() < parsableByteArray.limit() + 4) {
            parsableByteArray.reset(Arrays.copyOf(parsableByteArray.getData(), parsableByteArray.limit() + 4));
        } else {
            parsableByteArray.setLimit(parsableByteArray.limit() + 4);
        }
        byte[] data = parsableByteArray.getData();
        data[parsableByteArray.limit() - 4] = (byte) (j2 & 255);
        data[parsableByteArray.limit() - 3] = (byte) ((j2 >>> 8) & 255);
        data[parsableByteArray.limit() - 2] = (byte) ((j2 >>> 16) & 255);
        data[parsableByteArray.limit() - 1] = (byte) ((j2 >>> 24) & 255);
    }

    private static int decodeBlockSize(byte b3, VorbisSetup vorbisSetup) {
        return !vorbisSetup.modes[readBits(b3, vorbisSetup.iLogModes, 1)].blockFlag ? vorbisSetup.idHeader.blockSize0 : vorbisSetup.idHeader.blockSize1;
    }

    @VisibleForTesting
    public static int readBits(byte b3, int i2, int i3) {
        return (b3 >> i3) & (255 >>> (8 - i2));
    }

    public static boolean verifyBitstreamType(ParsableByteArray parsableByteArray) {
        try {
            return VorbisUtil.verifyVorbisHeaderCapturePattern(1, parsableByteArray, true);
        } catch (ParserException unused) {
            return false;
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public void onSeekEnd(long j2) {
        super.onSeekEnd(j2);
        this.seenFirstAudioPacket = j2 != 0;
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.vorbisIdHeader;
        this.previousPacketBlockSize = vorbisIdHeader != null ? vorbisIdHeader.blockSize0 : 0;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public long preparePayload(ParsableByteArray parsableByteArray) {
        if ((parsableByteArray.getData()[0] & 1) == 1) {
            return -1L;
        }
        int iDecodeBlockSize = decodeBlockSize(parsableByteArray.getData()[0], (VorbisSetup) Assertions.checkStateNotNull(this.vorbisSetup));
        long j2 = this.seenFirstAudioPacket ? (this.previousPacketBlockSize + iDecodeBlockSize) / 4 : 0;
        appendNumberOfSamples(parsableByteArray, j2);
        this.seenFirstAudioPacket = true;
        this.previousPacketBlockSize = iDecodeBlockSize;
        return j2;
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    @EnsuresNonNullIf(expression = {"#3.format"}, result = false)
    public boolean readHeaders(ParsableByteArray parsableByteArray, long j2, StreamReader.SetupData setupData) throws IOException {
        if (this.vorbisSetup != null) {
            Assertions.checkNotNull(setupData.format);
            return false;
        }
        VorbisSetup setupHeaders = readSetupHeaders(parsableByteArray);
        this.vorbisSetup = setupHeaders;
        if (setupHeaders == null) {
            return true;
        }
        VorbisUtil.VorbisIdHeader vorbisIdHeader = setupHeaders.idHeader;
        ArrayList arrayList = new ArrayList();
        arrayList.add(vorbisIdHeader.data);
        arrayList.add(setupHeaders.setupHeaderData);
        setupData.format = new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_VORBIS).setAverageBitrate(vorbisIdHeader.bitrateNominal).setPeakBitrate(vorbisIdHeader.bitrateMaximum).setChannelCount(vorbisIdHeader.channels).setSampleRate(vorbisIdHeader.sampleRate).setInitializationData(arrayList).build();
        return true;
    }

    @Nullable
    @VisibleForTesting
    public VorbisSetup readSetupHeaders(ParsableByteArray parsableByteArray) throws IOException {
        VorbisUtil.VorbisIdHeader vorbisIdHeader = this.vorbisIdHeader;
        if (vorbisIdHeader == null) {
            this.vorbisIdHeader = VorbisUtil.readVorbisIdentificationHeader(parsableByteArray);
            return null;
        }
        VorbisUtil.CommentHeader commentHeader = this.commentHeader;
        if (commentHeader == null) {
            this.commentHeader = VorbisUtil.readVorbisCommentHeader(parsableByteArray);
            return null;
        }
        byte[] bArr = new byte[parsableByteArray.limit()];
        System.arraycopy(parsableByteArray.getData(), 0, bArr, 0, parsableByteArray.limit());
        return new VorbisSetup(vorbisIdHeader, commentHeader, bArr, VorbisUtil.readVorbisModes(parsableByteArray, vorbisIdHeader.channels), VorbisUtil.iLog(r4.length - 1));
    }

    @Override // com.google.android.exoplayer2.extractor.ogg.StreamReader
    public void reset(boolean z2) {
        super.reset(z2);
        if (z2) {
            this.vorbisSetup = null;
            this.vorbisIdHeader = null;
            this.commentHeader = null;
        }
        this.previousPacketBlockSize = 0;
        this.seenFirstAudioPacket = false;
    }
}
