package com.google.android.exoplayer2.source.hls;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.FileTypes;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.common.primitives.Ints;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class DefaultHlsExtractorFactory implements HlsExtractorFactory {
    private static final int[] DEFAULT_EXTRACTOR_ORDER = {8, 13, 11, 2, 0, 1, 7};
    private final boolean exposeCea608WhenMissingDeclarations;
    private final int payloadReaderFactoryFlags;

    public DefaultHlsExtractorFactory() {
        this(0, true);
    }

    private static void addFileTypeIfValidAndNotPresent(int i2, List<Integer> list) {
        if (Ints.indexOf(DEFAULT_EXTRACTOR_ORDER, i2) == -1 || list.contains(Integer.valueOf(i2))) {
            return;
        }
        list.add(Integer.valueOf(i2));
    }

    @Nullable
    @SuppressLint({"SwitchIntDef"})
    private Extractor createExtractorByFileType(int i2, Format format, @Nullable List<Format> list, TimestampAdjuster timestampAdjuster) {
        if (i2 == 0) {
            return new Ac3Extractor();
        }
        if (i2 == 1) {
            return new Ac4Extractor();
        }
        if (i2 == 2) {
            return new AdtsExtractor();
        }
        if (i2 == 7) {
            return new Mp3Extractor(0, 0L);
        }
        if (i2 == 8) {
            return createFragmentedMp4Extractor(timestampAdjuster, format, list);
        }
        if (i2 == 11) {
            return createTsExtractor(this.payloadReaderFactoryFlags, this.exposeCea608WhenMissingDeclarations, format, list, timestampAdjuster);
        }
        if (i2 != 13) {
            return null;
        }
        return new WebvttExtractor(format.language, timestampAdjuster);
    }

    private static FragmentedMp4Extractor createFragmentedMp4Extractor(TimestampAdjuster timestampAdjuster, Format format, @Nullable List<Format> list) {
        int i2 = isFmp4Variant(format) ? 4 : 0;
        if (list == null) {
            list = Collections.emptyList();
        }
        return new FragmentedMp4Extractor(i2, timestampAdjuster, null, list);
    }

    private static TsExtractor createTsExtractor(int i2, boolean z2, Format format, @Nullable List<Format> list, TimestampAdjuster timestampAdjuster) {
        int i3 = i2 | 16;
        if (list != null) {
            i3 |= 32;
        } else {
            list = z2 ? Collections.singletonList(new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA608).build()) : Collections.emptyList();
        }
        String str = format.codecs;
        if (!TextUtils.isEmpty(str)) {
            if (!MimeTypes.containsCodecsCorrespondingToMimeType(str, MimeTypes.AUDIO_AAC)) {
                i3 |= 2;
            }
            if (!MimeTypes.containsCodecsCorrespondingToMimeType(str, MimeTypes.VIDEO_H264)) {
                i3 |= 4;
            }
        }
        return new TsExtractor(2, timestampAdjuster, new DefaultTsPayloadReaderFactory(i3, list));
    }

    private static boolean isFmp4Variant(Format format) {
        Metadata metadata = format.metadata;
        if (metadata == null) {
            return false;
        }
        for (int i2 = 0; i2 < metadata.length(); i2++) {
            if (metadata.get(i2) instanceof HlsTrackMetadataEntry) {
                return !((HlsTrackMetadataEntry) r2).variantInfos.isEmpty();
            }
        }
        return false;
    }

    private static boolean sniffQuietly(Extractor extractor, ExtractorInput extractorInput) throws IOException {
        try {
            boolean zSniff = extractor.sniff(extractorInput);
            extractorInput.resetPeekPosition();
            return zSniff;
        } catch (EOFException unused) {
            extractorInput.resetPeekPosition();
            return false;
        } catch (Throwable th) {
            extractorInput.resetPeekPosition();
            throw th;
        }
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public /* bridge */ /* synthetic */ HlsMediaChunkExtractor createExtractor(Uri uri, Format format, @Nullable List list, TimestampAdjuster timestampAdjuster, Map map, ExtractorInput extractorInput) throws IOException {
        return createExtractor(uri, format, (List<Format>) list, timestampAdjuster, (Map<String, List<String>>) map, extractorInput);
    }

    public DefaultHlsExtractorFactory(int i2, boolean z2) {
        this.payloadReaderFactoryFlags = i2;
        this.exposeCea608WhenMissingDeclarations = z2;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public BundledHlsMediaChunkExtractor createExtractor(Uri uri, Format format, @Nullable List<Format> list, TimestampAdjuster timestampAdjuster, Map<String, List<String>> map, ExtractorInput extractorInput) throws IOException {
        int iInferFileTypeFromMimeType = FileTypes.inferFileTypeFromMimeType(format.sampleMimeType);
        int iInferFileTypeFromResponseHeaders = FileTypes.inferFileTypeFromResponseHeaders(map);
        int iInferFileTypeFromUri = FileTypes.inferFileTypeFromUri(uri);
        int[] iArr = DEFAULT_EXTRACTOR_ORDER;
        ArrayList arrayList = new ArrayList(iArr.length);
        addFileTypeIfValidAndNotPresent(iInferFileTypeFromMimeType, arrayList);
        addFileTypeIfValidAndNotPresent(iInferFileTypeFromResponseHeaders, arrayList);
        addFileTypeIfValidAndNotPresent(iInferFileTypeFromUri, arrayList);
        for (int i2 : iArr) {
            addFileTypeIfValidAndNotPresent(i2, arrayList);
        }
        extractorInput.resetPeekPosition();
        Extractor extractor = null;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            int iIntValue = ((Integer) arrayList.get(i3)).intValue();
            Extractor extractor2 = (Extractor) Assertions.checkNotNull(createExtractorByFileType(iIntValue, format, list, timestampAdjuster));
            if (sniffQuietly(extractor2, extractorInput)) {
                return new BundledHlsMediaChunkExtractor(extractor2, format, timestampAdjuster);
            }
            if (extractor == null && (iIntValue == iInferFileTypeFromMimeType || iIntValue == iInferFileTypeFromResponseHeaders || iIntValue == iInferFileTypeFromUri || iIntValue == 11)) {
                extractor = extractor2;
            }
        }
        return new BundledHlsMediaChunkExtractor((Extractor) Assertions.checkNotNull(extractor), format, timestampAdjuster);
    }
}
