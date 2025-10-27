package com.google.android.exoplayer2.extractor.ts;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public interface TsPayloadReader {
    public static final int FLAG_DATA_ALIGNMENT_INDICATOR = 4;
    public static final int FLAG_PAYLOAD_UNIT_START_INDICATOR = 1;
    public static final int FLAG_RANDOM_ACCESS_INDICATOR = 2;

    public static final class DvbSubtitleInfo {
        public final byte[] initializationData;
        public final String language;
        public final int type;

        public DvbSubtitleInfo(String str, int i2, byte[] bArr) {
            this.language = str;
            this.type = i2;
            this.initializationData = bArr;
        }
    }

    public static final class EsInfo {
        public final byte[] descriptorBytes;
        public final List<DvbSubtitleInfo> dvbSubtitleInfos;

        @Nullable
        public final String language;
        public final int streamType;

        public EsInfo(int i2, @Nullable String str, @Nullable List<DvbSubtitleInfo> list, byte[] bArr) {
            this.streamType = i2;
            this.language = str;
            this.dvbSubtitleInfos = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
            this.descriptorBytes = bArr;
        }
    }

    public interface Factory {
        SparseArray<TsPayloadReader> createInitialPayloadReaders();

        @Nullable
        TsPayloadReader createPayloadReader(int i2, EsInfo esInfo);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static final class TrackIdGenerator {
        private static final int ID_UNSET = Integer.MIN_VALUE;
        private final int firstTrackId;
        private String formatId;
        private final String formatIdPrefix;
        private int trackId;
        private final int trackIdIncrement;

        public TrackIdGenerator(int i2, int i3) {
            this(Integer.MIN_VALUE, i2, i3);
        }

        private void maybeThrowUninitializedError() {
            if (this.trackId == Integer.MIN_VALUE) {
                throw new IllegalStateException("generateNewId() must be called before retrieving ids.");
            }
        }

        public void generateNewId() {
            int i2 = this.trackId;
            int i3 = i2 == Integer.MIN_VALUE ? this.firstTrackId : i2 + this.trackIdIncrement;
            this.trackId = i3;
            String str = this.formatIdPrefix;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 11);
            sb.append(str);
            sb.append(i3);
            this.formatId = sb.toString();
        }

        public String getFormatId() {
            maybeThrowUninitializedError();
            return this.formatId;
        }

        public int getTrackId() {
            maybeThrowUninitializedError();
            return this.trackId;
        }

        public TrackIdGenerator(int i2, int i3, int i4) {
            String string;
            if (i2 != Integer.MIN_VALUE) {
                StringBuilder sb = new StringBuilder(12);
                sb.append(i2);
                sb.append("/");
                string = sb.toString();
            } else {
                string = "";
            }
            this.formatIdPrefix = string;
            this.firstTrackId = i3;
            this.trackIdIncrement = i4;
            this.trackId = Integer.MIN_VALUE;
            this.formatId = "";
        }
    }

    void consume(ParsableByteArray parsableByteArray, int i2) throws ParserException;

    void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator);

    void seek();
}
