package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class HlsMediaPeriod implements MediaPeriod, HlsSampleStreamWrapper.Callback, HlsPlaylistTracker.PlaylistEventListener {
    private final Allocator allocator;
    private final boolean allowChunklessPreparation;
    private int audioVideoSampleStreamWrapperCount;

    @Nullable
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final DrmSessionManager drmSessionManager;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;

    @Nullable
    private final TransferListener mediaTransferListener;
    private final int metadataType;
    private int pendingPrepareCount;
    private final HlsPlaylistTracker playlistTracker;
    private TrackGroupArray trackGroups;
    private final boolean useSessionKeys;
    private final IdentityHashMap<SampleStream, Integer> streamWrapperIndices = new IdentityHashMap<>();
    private final TimestampAdjusterProvider timestampAdjusterProvider = new TimestampAdjusterProvider();
    private HlsSampleStreamWrapper[] sampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private HlsSampleStreamWrapper[] enabledSampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private int[][] manifestUrlIndicesPerWrapper = new int[0][];

    public HlsMediaPeriod(HlsExtractorFactory hlsExtractorFactory, HlsPlaylistTracker hlsPlaylistTracker, HlsDataSourceFactory hlsDataSourceFactory, @Nullable TransferListener transferListener, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, boolean z2, int i2, boolean z3) {
        this.extractorFactory = hlsExtractorFactory;
        this.playlistTracker = hlsPlaylistTracker;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.mediaTransferListener = transferListener;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = eventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher2;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.allowChunklessPreparation = z2;
        this.metadataType = i2;
        this.useSessionKeys = z3;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[0]);
    }

    private void buildAndPrepareAudioSampleStreamWrappers(long j2, List<HlsMasterPlaylist.Rendition> list, List<HlsSampleStreamWrapper> list2, List<int[]> list3, Map<String, DrmInitData> map) {
        ArrayList arrayList = new ArrayList(list.size());
        ArrayList arrayList2 = new ArrayList(list.size());
        ArrayList arrayList3 = new ArrayList(list.size());
        HashSet hashSet = new HashSet();
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2).name;
            if (hashSet.add(str)) {
                arrayList.clear();
                arrayList2.clear();
                arrayList3.clear();
                boolean z2 = true;
                for (int i3 = 0; i3 < list.size(); i3++) {
                    if (Util.areEqual(str, list.get(i3).name)) {
                        HlsMasterPlaylist.Rendition rendition = list.get(i3);
                        arrayList3.add(Integer.valueOf(i3));
                        arrayList.add(rendition.url);
                        arrayList2.add(rendition.format);
                        z2 &= Util.getCodecCountOfType(rendition.format.codecs, 1) == 1;
                    }
                }
                HlsSampleStreamWrapper hlsSampleStreamWrapperBuildSampleStreamWrapper = buildSampleStreamWrapper(1, (Uri[]) arrayList.toArray((Uri[]) Util.castNonNullTypeArray(new Uri[0])), (Format[]) arrayList2.toArray(new Format[0]), null, Collections.emptyList(), map, j2);
                list3.add(Ints.toArray(arrayList3));
                list2.add(hlsSampleStreamWrapperBuildSampleStreamWrapper);
                if (this.allowChunklessPreparation && z2) {
                    hlsSampleStreamWrapperBuildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroup[]{new TrackGroup((Format[]) arrayList2.toArray(new Format[0]))}, 0, new int[0]);
                }
            }
        }
    }

    private void buildAndPrepareMainSampleStreamWrapper(HlsMasterPlaylist hlsMasterPlaylist, long j2, List<HlsSampleStreamWrapper> list, List<int[]> list2, Map<String, DrmInitData> map) {
        boolean z2;
        boolean z3;
        int size = hlsMasterPlaylist.variants.size();
        int[] iArr = new int[size];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < hlsMasterPlaylist.variants.size(); i4++) {
            Format format = hlsMasterPlaylist.variants.get(i4).format;
            if (format.height > 0 || Util.getCodecsOfType(format.codecs, 2) != null) {
                iArr[i4] = 2;
                i2++;
            } else if (Util.getCodecsOfType(format.codecs, 1) != null) {
                iArr[i4] = 1;
                i3++;
            } else {
                iArr[i4] = -1;
            }
        }
        if (i2 > 0) {
            size = i2;
            z2 = true;
            z3 = false;
        } else if (i3 < size) {
            size -= i3;
            z2 = false;
            z3 = true;
        } else {
            z2 = false;
            z3 = false;
        }
        Uri[] uriArr = new Uri[size];
        Format[] formatArr = new Format[size];
        int[] iArr2 = new int[size];
        int i5 = 0;
        for (int i6 = 0; i6 < hlsMasterPlaylist.variants.size(); i6++) {
            if ((!z2 || iArr[i6] == 2) && (!z3 || iArr[i6] != 1)) {
                HlsMasterPlaylist.Variant variant = hlsMasterPlaylist.variants.get(i6);
                uriArr[i5] = variant.url;
                formatArr[i5] = variant.format;
                iArr2[i5] = i6;
                i5++;
            }
        }
        String str = formatArr[0].codecs;
        int codecCountOfType = Util.getCodecCountOfType(str, 2);
        int codecCountOfType2 = Util.getCodecCountOfType(str, 1);
        boolean z4 = codecCountOfType2 <= 1 && codecCountOfType <= 1 && codecCountOfType2 + codecCountOfType > 0;
        HlsSampleStreamWrapper hlsSampleStreamWrapperBuildSampleStreamWrapper = buildSampleStreamWrapper((z2 || codecCountOfType2 <= 0) ? 0 : 1, uriArr, formatArr, hlsMasterPlaylist.muxedAudioFormat, hlsMasterPlaylist.muxedCaptionFormats, map, j2);
        list.add(hlsSampleStreamWrapperBuildSampleStreamWrapper);
        list2.add(iArr2);
        if (this.allowChunklessPreparation && z4) {
            ArrayList arrayList = new ArrayList();
            if (codecCountOfType > 0) {
                Format[] formatArr2 = new Format[size];
                for (int i7 = 0; i7 < size; i7++) {
                    formatArr2[i7] = deriveVideoFormat(formatArr[i7]);
                }
                arrayList.add(new TrackGroup(formatArr2));
                if (codecCountOfType2 > 0 && (hlsMasterPlaylist.muxedAudioFormat != null || hlsMasterPlaylist.audios.isEmpty())) {
                    arrayList.add(new TrackGroup(deriveAudioFormat(formatArr[0], hlsMasterPlaylist.muxedAudioFormat, false)));
                }
                List<Format> list3 = hlsMasterPlaylist.muxedCaptionFormats;
                if (list3 != null) {
                    for (int i8 = 0; i8 < list3.size(); i8++) {
                        arrayList.add(new TrackGroup(list3.get(i8)));
                    }
                }
            } else {
                Format[] formatArr3 = new Format[size];
                for (int i9 = 0; i9 < size; i9++) {
                    formatArr3[i9] = deriveAudioFormat(formatArr[i9], hlsMasterPlaylist.muxedAudioFormat, true);
                }
                arrayList.add(new TrackGroup(formatArr3));
            }
            TrackGroup trackGroup = new TrackGroup(new Format.Builder().setId("ID3").setSampleMimeType(MimeTypes.APPLICATION_ID3).build());
            arrayList.add(trackGroup);
            hlsSampleStreamWrapperBuildSampleStreamWrapper.prepareWithMasterPlaylistInfo((TrackGroup[]) arrayList.toArray(new TrackGroup[0]), 0, arrayList.indexOf(trackGroup));
        }
    }

    private void buildAndPrepareSampleStreamWrappers(long j2) {
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(this.playlistTracker.getMasterPlaylist());
        Map<String, DrmInitData> mapDeriveOverridingDrmInitData = this.useSessionKeys ? deriveOverridingDrmInitData(hlsMasterPlaylist.sessionKeyDrmInitData) : Collections.emptyMap();
        boolean z2 = !hlsMasterPlaylist.variants.isEmpty();
        List<HlsMasterPlaylist.Rendition> list = hlsMasterPlaylist.audios;
        List<HlsMasterPlaylist.Rendition> list2 = hlsMasterPlaylist.subtitles;
        this.pendingPrepareCount = 0;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (z2) {
            buildAndPrepareMainSampleStreamWrapper(hlsMasterPlaylist, j2, arrayList, arrayList2, mapDeriveOverridingDrmInitData);
        }
        buildAndPrepareAudioSampleStreamWrappers(j2, list, arrayList, arrayList2, mapDeriveOverridingDrmInitData);
        this.audioVideoSampleStreamWrapperCount = arrayList.size();
        int i2 = 0;
        while (i2 < list2.size()) {
            HlsMasterPlaylist.Rendition rendition = list2.get(i2);
            int i3 = i2;
            HlsSampleStreamWrapper hlsSampleStreamWrapperBuildSampleStreamWrapper = buildSampleStreamWrapper(3, new Uri[]{rendition.url}, new Format[]{rendition.format}, null, Collections.emptyList(), mapDeriveOverridingDrmInitData, j2);
            arrayList2.add(new int[]{i3});
            arrayList.add(hlsSampleStreamWrapperBuildSampleStreamWrapper);
            hlsSampleStreamWrapperBuildSampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroup[]{new TrackGroup(rendition.format)}, 0, new int[0]);
            i2 = i3 + 1;
        }
        this.sampleStreamWrappers = (HlsSampleStreamWrapper[]) arrayList.toArray(new HlsSampleStreamWrapper[0]);
        this.manifestUrlIndicesPerWrapper = (int[][]) arrayList2.toArray(new int[0][]);
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
        this.pendingPrepareCount = hlsSampleStreamWrapperArr.length;
        hlsSampleStreamWrapperArr[0].setIsTimestampMaster(true);
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.continuePreparing();
        }
        this.enabledSampleStreamWrappers = this.sampleStreamWrappers;
    }

    private HlsSampleStreamWrapper buildSampleStreamWrapper(int i2, Uri[] uriArr, Format[] formatArr, @Nullable Format format, @Nullable List<Format> list, Map<String, DrmInitData> map, long j2) {
        return new HlsSampleStreamWrapper(i2, this, new HlsChunkSource(this.extractorFactory, this.playlistTracker, uriArr, formatArr, this.dataSourceFactory, this.mediaTransferListener, this.timestampAdjusterProvider, list), map, this.allocator, j2, format, this.drmSessionManager, this.drmEventDispatcher, this.loadErrorHandlingPolicy, this.eventDispatcher, this.metadataType);
    }

    private static Format deriveAudioFormat(Format format, @Nullable Format format2, boolean z2) {
        String codecsOfType;
        Metadata metadata;
        int i2;
        String str;
        int i3;
        int i4;
        String str2;
        if (format2 != null) {
            codecsOfType = format2.codecs;
            metadata = format2.metadata;
            i3 = format2.channelCount;
            i2 = format2.selectionFlags;
            i4 = format2.roleFlags;
            str = format2.language;
            str2 = format2.label;
        } else {
            codecsOfType = Util.getCodecsOfType(format.codecs, 1);
            metadata = format.metadata;
            if (z2) {
                i3 = format.channelCount;
                i2 = format.selectionFlags;
                i4 = format.roleFlags;
                str = format.language;
                str2 = format.label;
            } else {
                i2 = 0;
                str = null;
                i3 = -1;
                i4 = 0;
                str2 = null;
            }
        }
        return new Format.Builder().setId(format.id).setLabel(str2).setContainerMimeType(format.containerMimeType).setSampleMimeType(MimeTypes.getMediaMimeType(codecsOfType)).setCodecs(codecsOfType).setMetadata(metadata).setAverageBitrate(z2 ? format.averageBitrate : -1).setPeakBitrate(z2 ? format.peakBitrate : -1).setChannelCount(i3).setSelectionFlags(i2).setRoleFlags(i4).setLanguage(str).build();
    }

    private static Map<String, DrmInitData> deriveOverridingDrmInitData(List<DrmInitData> list) {
        ArrayList arrayList = new ArrayList(list);
        HashMap map = new HashMap();
        int i2 = 0;
        while (i2 < arrayList.size()) {
            DrmInitData drmInitDataMerge = list.get(i2);
            String str = drmInitDataMerge.schemeType;
            i2++;
            int i3 = i2;
            while (i3 < arrayList.size()) {
                DrmInitData drmInitData = (DrmInitData) arrayList.get(i3);
                if (TextUtils.equals(drmInitData.schemeType, str)) {
                    drmInitDataMerge = drmInitDataMerge.merge(drmInitData);
                    arrayList.remove(i3);
                } else {
                    i3++;
                }
            }
            map.put(str, drmInitDataMerge);
        }
        return map;
    }

    private static Format deriveVideoFormat(Format format) {
        String codecsOfType = Util.getCodecsOfType(format.codecs, 2);
        return new Format.Builder().setId(format.id).setLabel(format.label).setContainerMimeType(format.containerMimeType).setSampleMimeType(MimeTypes.getMediaMimeType(codecsOfType)).setCodecs(codecsOfType).setMetadata(format.metadata).setAverageBitrate(format.averageBitrate).setPeakBitrate(format.peakBitrate).setWidth(format.width).setHeight(format.height).setFrameRate(format.frameRate).setSelectionFlags(format.selectionFlags).setRoleFlags(format.roleFlags).build();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j2) {
        if (this.trackGroups != null) {
            return this.compositeSequenceableLoader.continueLoading(j2);
        }
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.continuePreparing();
        }
        return false;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.enabledSampleStreamWrappers) {
            hlsSampleStreamWrapper.discardBuffer(j2, z2);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        return j2;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [int] */
    /* JADX WARN: Type inference failed for: r15v7 */
    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        int[] iArr;
        TrackGroupArray trackGroups;
        int primaryTrackGroupIndex;
        HlsMediaPeriod hlsMediaPeriod = this;
        HlsMasterPlaylist hlsMasterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(hlsMediaPeriod.playlistTracker.getMasterPlaylist());
        boolean z2 = !hlsMasterPlaylist.variants.isEmpty();
        int length = hlsMediaPeriod.sampleStreamWrappers.length - hlsMasterPlaylist.subtitles.size();
        int i2 = 0;
        if (z2) {
            HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsMediaPeriod.sampleStreamWrappers[0];
            iArr = hlsMediaPeriod.manifestUrlIndicesPerWrapper[0];
            trackGroups = hlsSampleStreamWrapper.getTrackGroups();
            primaryTrackGroupIndex = hlsSampleStreamWrapper.getPrimaryTrackGroupIndex();
        } else {
            iArr = new int[0];
            trackGroups = TrackGroupArray.EMPTY;
            primaryTrackGroupIndex = 0;
        }
        ArrayList arrayList = new ArrayList();
        boolean z3 = false;
        boolean z4 = false;
        for (ExoTrackSelection exoTrackSelection : list) {
            TrackGroup trackGroup = exoTrackSelection.getTrackGroup();
            int iIndexOf = trackGroups.indexOf(trackGroup);
            if (iIndexOf == -1) {
                ?? r15 = z2;
                while (true) {
                    HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = hlsMediaPeriod.sampleStreamWrappers;
                    if (r15 >= hlsSampleStreamWrapperArr.length) {
                        break;
                    }
                    if (hlsSampleStreamWrapperArr[r15].getTrackGroups().indexOf(trackGroup) != -1) {
                        int i3 = r15 < length ? 1 : 2;
                        int[] iArr2 = hlsMediaPeriod.manifestUrlIndicesPerWrapper[r15];
                        for (int i4 = 0; i4 < exoTrackSelection.length(); i4++) {
                            arrayList.add(new StreamKey(i3, iArr2[exoTrackSelection.getIndexInTrackGroup(i4)]));
                        }
                    } else {
                        hlsMediaPeriod = this;
                        r15++;
                    }
                }
            } else if (iIndexOf == primaryTrackGroupIndex) {
                for (int i5 = i2; i5 < exoTrackSelection.length(); i5++) {
                    arrayList.add(new StreamKey(i2, iArr[exoTrackSelection.getIndexInTrackGroup(i5)]));
                }
                z4 = true;
            } else {
                z3 = true;
            }
            hlsMediaPeriod = this;
            i2 = 0;
        }
        if (z3 && !z4) {
            int i6 = iArr[0];
            int i7 = hlsMasterPlaylist.variants.get(i6).format.bitrate;
            for (int i8 = 1; i8 < iArr.length; i8++) {
                int i9 = hlsMasterPlaylist.variants.get(iArr[i8]).format.bitrate;
                if (i9 < i7) {
                    i6 = iArr[i8];
                    i7 = i9;
                }
            }
            arrayList.add(new StreamKey(0, i6));
        }
        return arrayList;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.trackGroups);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.compositeSequenceableLoader.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.maybeThrowPrepareError();
        }
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public void onPlaylistChanged() {
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.onPlaylistUpdated();
        }
        this.callback.onContinueLoadingRequested(this);
    }

    @Override // com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener
    public boolean onPlaylistError(Uri uri, LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo, boolean z2) {
        boolean zOnPlaylistError = true;
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            zOnPlaylistError &= hlsSampleStreamWrapper.onPlaylistError(uri, loadErrorInfo, z2);
        }
        this.callback.onContinueLoadingRequested(this);
        return zOnPlaylistError;
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPlaylistRefreshRequired(Uri uri) {
        this.playlistTracker.refreshPlaylist(uri);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback
    public void onPrepared() {
        int i2 = this.pendingPrepareCount - 1;
        this.pendingPrepareCount = i2;
        if (i2 > 0) {
            return;
        }
        int i3 = 0;
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            i3 += hlsSampleStreamWrapper.getTrackGroups().length;
        }
        TrackGroup[] trackGroupArr = new TrackGroup[i3];
        int i4 = 0;
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper2 : this.sampleStreamWrappers) {
            int i5 = hlsSampleStreamWrapper2.getTrackGroups().length;
            int i6 = 0;
            while (i6 < i5) {
                trackGroupArr[i4] = hlsSampleStreamWrapper2.getTrackGroups().get(i6);
                i6++;
                i4++;
            }
        }
        this.trackGroups = new TrackGroupArray(trackGroupArr);
        this.callback.onPrepared(this);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.callback = callback;
        this.playlistTracker.addListener(this);
        buildAndPrepareSampleStreamWrappers(j2);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long readDiscontinuity() {
        return C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public void reevaluateBuffer(long j2) {
        this.compositeSequenceableLoader.reevaluateBuffer(j2);
    }

    public void release() {
        this.playlistTracker.removeListener(this);
        for (HlsSampleStreamWrapper hlsSampleStreamWrapper : this.sampleStreamWrappers) {
            hlsSampleStreamWrapper.release();
        }
        this.callback = null;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j2) {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.enabledSampleStreamWrappers;
        if (hlsSampleStreamWrapperArr.length > 0) {
            boolean zSeekToUs = hlsSampleStreamWrapperArr[0].seekToUs(j2, false);
            int i2 = 1;
            while (true) {
                HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr2 = this.enabledSampleStreamWrappers;
                if (i2 >= hlsSampleStreamWrapperArr2.length) {
                    break;
                }
                hlsSampleStreamWrapperArr2[i2].seekToUs(j2, zSeekToUs);
                i2++;
            }
            if (zSeekToUs) {
                this.timestampAdjusterProvider.reset();
            }
        }
        return j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00df  */
    @Override // com.google.android.exoplayer2.source.MediaPeriod
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public long selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[] r22, boolean[] r23, com.google.android.exoplayer2.source.SampleStream[] r24, boolean[] r25, long r26) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.ExoTrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(HlsSampleStreamWrapper hlsSampleStreamWrapper) {
        this.callback.onContinueLoadingRequested(this);
    }
}
