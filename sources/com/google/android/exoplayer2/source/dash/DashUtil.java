package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
public final class DashUtil {
    private DashUtil() {
    }

    public static DataSpec buildDataSpec(Representation representation, String str, RangedUri rangedUri, int i2) {
        return new DataSpec.Builder().setUri(rangedUri.resolveUri(str)).setPosition(rangedUri.start).setLength(rangedUri.length).setKey(resolveCacheKey(representation, rangedUri)).setFlags(i2).build();
    }

    @Nullable
    private static Representation getFirstRepresentation(Period period, int i2) {
        int adaptationSetIndex = period.getAdaptationSetIndex(i2);
        if (adaptationSetIndex == -1) {
            return null;
        }
        List<Representation> list = period.adaptationSets.get(adaptationSetIndex).representations;
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Nullable
    public static ChunkIndex loadChunkIndex(DataSource dataSource, int i2, Representation representation, int i3) throws IOException {
        if (representation.getInitializationUri() == null) {
            return null;
        }
        ChunkExtractor chunkExtractorNewChunkExtractor = newChunkExtractor(i2, representation.format);
        try {
            loadInitializationData(chunkExtractorNewChunkExtractor, dataSource, representation, i3, true);
            chunkExtractorNewChunkExtractor.release();
            return chunkExtractorNewChunkExtractor.getChunkIndex();
        } catch (Throwable th) {
            chunkExtractorNewChunkExtractor.release();
            throw th;
        }
    }

    @Nullable
    public static Format loadFormatWithDrmInitData(DataSource dataSource, Period period) throws IOException {
        int i2 = 2;
        Representation firstRepresentation = getFirstRepresentation(period, 2);
        if (firstRepresentation == null) {
            i2 = 1;
            firstRepresentation = getFirstRepresentation(period, 1);
            if (firstRepresentation == null) {
                return null;
            }
        }
        Format format = firstRepresentation.format;
        Format formatLoadSampleFormat = loadSampleFormat(dataSource, i2, firstRepresentation);
        return formatLoadSampleFormat == null ? format : formatLoadSampleFormat.withManifestFormatInfo(format);
    }

    private static void loadInitializationData(ChunkExtractor chunkExtractor, DataSource dataSource, Representation representation, int i2, boolean z2) throws IOException {
        RangedUri rangedUri = (RangedUri) Assertions.checkNotNull(representation.getInitializationUri());
        if (z2) {
            RangedUri indexUri = representation.getIndexUri();
            if (indexUri == null) {
                return;
            }
            RangedUri rangedUriAttemptMerge = rangedUri.attemptMerge(indexUri, representation.baseUrls.get(i2).url);
            if (rangedUriAttemptMerge == null) {
                loadInitializationData(dataSource, representation, i2, chunkExtractor, rangedUri);
                rangedUri = indexUri;
            } else {
                rangedUri = rangedUriAttemptMerge;
            }
        }
        loadInitializationData(dataSource, representation, i2, chunkExtractor, rangedUri);
    }

    public static DashManifest loadManifest(DataSource dataSource, Uri uri) throws IOException {
        return (DashManifest) ParsingLoadable.load(dataSource, new DashManifestParser(), uri, 4);
    }

    @Nullable
    public static Format loadSampleFormat(DataSource dataSource, int i2, Representation representation, int i3) throws IOException {
        if (representation.getInitializationUri() == null) {
            return null;
        }
        ChunkExtractor chunkExtractorNewChunkExtractor = newChunkExtractor(i2, representation.format);
        try {
            loadInitializationData(chunkExtractorNewChunkExtractor, dataSource, representation, i3, false);
            chunkExtractorNewChunkExtractor.release();
            return ((Format[]) Assertions.checkStateNotNull(chunkExtractorNewChunkExtractor.getSampleFormats()))[0];
        } catch (Throwable th) {
            chunkExtractorNewChunkExtractor.release();
            throw th;
        }
    }

    private static ChunkExtractor newChunkExtractor(int i2, Format format) {
        String str = format.containerMimeType;
        return new BundledChunkExtractor(str != null && (str.startsWith("video/webm") || str.startsWith(MimeTypes.AUDIO_WEBM)) ? new MatroskaExtractor() : new FragmentedMp4Extractor(), i2, format);
    }

    public static String resolveCacheKey(Representation representation, RangedUri rangedUri) {
        String cacheKey = representation.getCacheKey();
        return cacheKey != null ? cacheKey : rangedUri.resolveUri(representation.baseUrls.get(0).url).toString();
    }

    public static void loadInitializationData(ChunkExtractor chunkExtractor, DataSource dataSource, Representation representation, boolean z2) throws IOException {
        loadInitializationData(chunkExtractor, dataSource, representation, 0, z2);
    }

    public static DataSpec buildDataSpec(Representation representation, RangedUri rangedUri, int i2) {
        return buildDataSpec(representation, representation.baseUrls.get(0).url, rangedUri, i2);
    }

    @Nullable
    public static ChunkIndex loadChunkIndex(DataSource dataSource, int i2, Representation representation) throws IOException {
        return loadChunkIndex(dataSource, i2, representation, 0);
    }

    private static void loadInitializationData(DataSource dataSource, Representation representation, int i2, ChunkExtractor chunkExtractor, RangedUri rangedUri) throws IOException {
        new InitializationChunk(dataSource, buildDataSpec(representation, representation.baseUrls.get(i2).url, rangedUri, 0), representation.format, 0, null, chunkExtractor).load();
    }

    @Nullable
    public static Format loadSampleFormat(DataSource dataSource, int i2, Representation representation) throws IOException {
        return loadSampleFormat(dataSource, i2, representation, 0);
    }
}
