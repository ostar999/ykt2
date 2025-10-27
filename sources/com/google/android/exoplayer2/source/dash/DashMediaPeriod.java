package com.google.android.exoplayer2.source.dash;

import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.Nullable;
import com.alipay.sdk.util.h;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.Descriptor;
import com.google.android.exoplayer2.source.dash.manifest.EventStream;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
final class DashMediaPeriod implements MediaPeriod, SequenceableLoader.Callback<ChunkSampleStream<DashChunkSource>>, ChunkSampleStream.ReleaseCallback<DashChunkSource> {
    private static final Pattern CEA608_SERVICE_DESCRIPTOR_REGEX = Pattern.compile("CC([1-4])=(.+)");
    private static final Pattern CEA708_SERVICE_DESCRIPTOR_REGEX = Pattern.compile("([1-4])=lang:(\\w+)(,.+)?");
    private final Allocator allocator;
    private final BaseUrlExclusionList baseUrlExclusionList;

    @Nullable
    private MediaPeriod.Callback callback;
    private final DashChunkSource.Factory chunkSourceFactory;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final DrmSessionEventListener.EventDispatcher drmEventDispatcher;
    private final DrmSessionManager drmSessionManager;
    private final long elapsedRealtimeOffsetMs;
    private List<EventStream> eventStreams;
    final int id;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final MediaSourceEventListener.EventDispatcher mediaSourceEventDispatcher;
    private int periodIndex;
    private final PlayerEmsgHandler playerEmsgHandler;
    private final TrackGroupInfo[] trackGroupInfos;
    private final TrackGroupArray trackGroups;

    @Nullable
    private final TransferListener transferListener;
    private ChunkSampleStream<DashChunkSource>[] sampleStreams = newSampleStreamArray(0);
    private EventSampleStream[] eventSampleStreams = new EventSampleStream[0];
    private final IdentityHashMap<ChunkSampleStream<DashChunkSource>, PlayerEmsgHandler.PlayerTrackEmsgHandler> trackEmsgHandlerBySampleStream = new IdentityHashMap<>();

    public static final class TrackGroupInfo {
        private static final int CATEGORY_EMBEDDED = 1;
        private static final int CATEGORY_MANIFEST_EVENTS = 2;
        private static final int CATEGORY_PRIMARY = 0;
        public final int[] adaptationSetIndices;
        public final int embeddedClosedCaptionTrackGroupIndex;
        public final int embeddedEventMessageTrackGroupIndex;
        public final int eventStreamGroupIndex;
        public final int primaryTrackGroupIndex;
        public final int trackGroupCategory;
        public final int trackType;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface TrackGroupCategory {
        }

        private TrackGroupInfo(int i2, int i3, int[] iArr, int i4, int i5, int i6, int i7) {
            this.trackType = i2;
            this.adaptationSetIndices = iArr;
            this.trackGroupCategory = i3;
            this.primaryTrackGroupIndex = i4;
            this.embeddedEventMessageTrackGroupIndex = i5;
            this.embeddedClosedCaptionTrackGroupIndex = i6;
            this.eventStreamGroupIndex = i7;
        }

        public static TrackGroupInfo embeddedClosedCaptionTrack(int[] iArr, int i2) {
            return new TrackGroupInfo(3, 1, iArr, i2, -1, -1, -1);
        }

        public static TrackGroupInfo embeddedEmsgTrack(int[] iArr, int i2) {
            return new TrackGroupInfo(5, 1, iArr, i2, -1, -1, -1);
        }

        public static TrackGroupInfo mpdEventTrack(int i2) {
            return new TrackGroupInfo(5, 2, new int[0], -1, -1, -1, i2);
        }

        public static TrackGroupInfo primaryTrack(int i2, int[] iArr, int i3, int i4, int i5) {
            return new TrackGroupInfo(i2, 0, iArr, i3, i4, i5, -1);
        }
    }

    public DashMediaPeriod(int i2, DashManifest dashManifest, BaseUrlExclusionList baseUrlExclusionList, int i3, DashChunkSource.Factory factory, @Nullable TransferListener transferListener, DrmSessionManager drmSessionManager, DrmSessionEventListener.EventDispatcher eventDispatcher, LoadErrorHandlingPolicy loadErrorHandlingPolicy, MediaSourceEventListener.EventDispatcher eventDispatcher2, long j2, LoaderErrorThrower loaderErrorThrower, Allocator allocator, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, PlayerEmsgHandler.PlayerEmsgCallback playerEmsgCallback) {
        this.id = i2;
        this.manifest = dashManifest;
        this.baseUrlExclusionList = baseUrlExclusionList;
        this.periodIndex = i3;
        this.chunkSourceFactory = factory;
        this.transferListener = transferListener;
        this.drmSessionManager = drmSessionManager;
        this.drmEventDispatcher = eventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.mediaSourceEventDispatcher = eventDispatcher2;
        this.elapsedRealtimeOffsetMs = j2;
        this.manifestLoaderErrorThrower = loaderErrorThrower;
        this.allocator = allocator;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.playerEmsgHandler = new PlayerEmsgHandler(dashManifest, playerEmsgCallback, allocator);
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        Period period = dashManifest.getPeriod(i3);
        List<EventStream> list = period.eventStreams;
        this.eventStreams = list;
        Pair<TrackGroupArray, TrackGroupInfo[]> pairBuildTrackGroups = buildTrackGroups(drmSessionManager, period.adaptationSets, list);
        this.trackGroups = (TrackGroupArray) pairBuildTrackGroups.first;
        this.trackGroupInfos = (TrackGroupInfo[]) pairBuildTrackGroups.second;
    }

    private static void buildManifestEventTrackGroupInfos(List<EventStream> list, TrackGroup[] trackGroupArr, TrackGroupInfo[] trackGroupInfoArr, int i2) {
        int i3 = 0;
        while (i3 < list.size()) {
            trackGroupArr[i2] = new TrackGroup(new Format.Builder().setId(list.get(i3).id()).setSampleMimeType(MimeTypes.APPLICATION_EMSG).build());
            trackGroupInfoArr[i2] = TrackGroupInfo.mpdEventTrack(i3);
            i3++;
            i2++;
        }
    }

    private static int buildPrimaryAndEmbeddedTrackGroupInfos(DrmSessionManager drmSessionManager, List<AdaptationSet> list, int[][] iArr, int i2, boolean[] zArr, Format[][] formatArr, TrackGroup[] trackGroupArr, TrackGroupInfo[] trackGroupInfoArr) {
        int i3;
        int i4;
        int i5 = 0;
        int i6 = 0;
        while (i5 < i2) {
            int[] iArr2 = iArr[i5];
            ArrayList arrayList = new ArrayList();
            for (int i7 : iArr2) {
                arrayList.addAll(list.get(i7).representations);
            }
            int size = arrayList.size();
            Format[] formatArr2 = new Format[size];
            for (int i8 = 0; i8 < size; i8++) {
                Format format = ((Representation) arrayList.get(i8)).format;
                formatArr2[i8] = format.copyWithCryptoType(drmSessionManager.getCryptoType(format));
            }
            AdaptationSet adaptationSet = list.get(iArr2[0]);
            int i9 = i6 + 1;
            if (zArr[i5]) {
                i3 = i9 + 1;
            } else {
                i3 = i9;
                i9 = -1;
            }
            if (formatArr[i5].length != 0) {
                i4 = i3 + 1;
            } else {
                i4 = i3;
                i3 = -1;
            }
            trackGroupArr[i6] = new TrackGroup(formatArr2);
            trackGroupInfoArr[i6] = TrackGroupInfo.primaryTrack(adaptationSet.type, iArr2, i6, i9, i3);
            if (i9 != -1) {
                Format.Builder builder = new Format.Builder();
                int i10 = adaptationSet.id;
                StringBuilder sb = new StringBuilder(16);
                sb.append(i10);
                sb.append(":emsg");
                trackGroupArr[i9] = new TrackGroup(builder.setId(sb.toString()).setSampleMimeType(MimeTypes.APPLICATION_EMSG).build());
                trackGroupInfoArr[i9] = TrackGroupInfo.embeddedEmsgTrack(iArr2, i6);
            }
            if (i3 != -1) {
                trackGroupArr[i3] = new TrackGroup(formatArr[i5]);
                trackGroupInfoArr[i3] = TrackGroupInfo.embeddedClosedCaptionTrack(iArr2, i6);
            }
            i5++;
            i6 = i4;
        }
        return i6;
    }

    private ChunkSampleStream<DashChunkSource> buildSampleStream(TrackGroupInfo trackGroupInfo, ExoTrackSelection exoTrackSelection, long j2) {
        int i2;
        TrackGroup trackGroup;
        TrackGroup trackGroup2;
        int i3;
        int i4 = trackGroupInfo.embeddedEventMessageTrackGroupIndex;
        boolean z2 = i4 != -1;
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandlerNewPlayerTrackEmsgHandler = null;
        if (z2) {
            trackGroup = this.trackGroups.get(i4);
            i2 = 1;
        } else {
            i2 = 0;
            trackGroup = null;
        }
        int i5 = trackGroupInfo.embeddedClosedCaptionTrackGroupIndex;
        boolean z3 = i5 != -1;
        if (z3) {
            trackGroup2 = this.trackGroups.get(i5);
            i2 += trackGroup2.length;
        } else {
            trackGroup2 = null;
        }
        Format[] formatArr = new Format[i2];
        int[] iArr = new int[i2];
        if (z2) {
            formatArr[0] = trackGroup.getFormat(0);
            iArr[0] = 5;
            i3 = 1;
        } else {
            i3 = 0;
        }
        ArrayList arrayList = new ArrayList();
        if (z3) {
            for (int i6 = 0; i6 < trackGroup2.length; i6++) {
                Format format = trackGroup2.getFormat(i6);
                formatArr[i3] = format;
                iArr[i3] = 3;
                arrayList.add(format);
                i3++;
            }
        }
        if (this.manifest.dynamic && z2) {
            playerTrackEmsgHandlerNewPlayerTrackEmsgHandler = this.playerEmsgHandler.newPlayerTrackEmsgHandler();
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler = playerTrackEmsgHandlerNewPlayerTrackEmsgHandler;
        ChunkSampleStream<DashChunkSource> chunkSampleStream = new ChunkSampleStream<>(trackGroupInfo.trackType, iArr, formatArr, this.chunkSourceFactory.createDashChunkSource(this.manifestLoaderErrorThrower, this.manifest, this.baseUrlExclusionList, this.periodIndex, trackGroupInfo.adaptationSetIndices, exoTrackSelection, trackGroupInfo.trackType, this.elapsedRealtimeOffsetMs, z2, arrayList, playerTrackEmsgHandler, this.transferListener), this, this.allocator, j2, this.drmSessionManager, this.drmEventDispatcher, this.loadErrorHandlingPolicy, this.mediaSourceEventDispatcher);
        synchronized (this) {
            this.trackEmsgHandlerBySampleStream.put(chunkSampleStream, playerTrackEmsgHandler);
        }
        return chunkSampleStream;
    }

    private static Pair<TrackGroupArray, TrackGroupInfo[]> buildTrackGroups(DrmSessionManager drmSessionManager, List<AdaptationSet> list, List<EventStream> list2) {
        int[][] groupedAdaptationSetIndices = getGroupedAdaptationSetIndices(list);
        int length = groupedAdaptationSetIndices.length;
        boolean[] zArr = new boolean[length];
        Format[][] formatArr = new Format[length][];
        int iIdentifyEmbeddedTracks = identifyEmbeddedTracks(length, list, groupedAdaptationSetIndices, zArr, formatArr) + length + list2.size();
        TrackGroup[] trackGroupArr = new TrackGroup[iIdentifyEmbeddedTracks];
        TrackGroupInfo[] trackGroupInfoArr = new TrackGroupInfo[iIdentifyEmbeddedTracks];
        buildManifestEventTrackGroupInfos(list2, trackGroupArr, trackGroupInfoArr, buildPrimaryAndEmbeddedTrackGroupInfos(drmSessionManager, list, groupedAdaptationSetIndices, length, zArr, formatArr, trackGroupArr, trackGroupInfoArr));
        return Pair.create(new TrackGroupArray(trackGroupArr), trackGroupInfoArr);
    }

    @Nullable
    private static Descriptor findAdaptationSetSwitchingProperty(List<Descriptor> list) {
        return findDescriptor(list, "urn:mpeg:dash:adaptation-set-switching:2016");
    }

    @Nullable
    private static Descriptor findDescriptor(List<Descriptor> list, String str) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = list.get(i2);
            if (str.equals(descriptor.schemeIdUri)) {
                return descriptor;
            }
        }
        return null;
    }

    @Nullable
    private static Descriptor findTrickPlayProperty(List<Descriptor> list) {
        return findDescriptor(list, "http://dashif.org/guidelines/trickmode");
    }

    private static Format[] getClosedCaptionTrackFormats(List<AdaptationSet> list, int[] iArr) {
        for (int i2 : iArr) {
            AdaptationSet adaptationSet = list.get(i2);
            List<Descriptor> list2 = list.get(i2).accessibilityDescriptors;
            for (int i3 = 0; i3 < list2.size(); i3++) {
                Descriptor descriptor = list2.get(i3);
                if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri)) {
                    Format.Builder sampleMimeType = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA608);
                    int i4 = adaptationSet.id;
                    StringBuilder sb = new StringBuilder(18);
                    sb.append(i4);
                    sb.append(":cea608");
                    return parseClosedCaptionDescriptor(descriptor, CEA608_SERVICE_DESCRIPTOR_REGEX, sampleMimeType.setId(sb.toString()).build());
                }
                if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri)) {
                    Format.Builder sampleMimeType2 = new Format.Builder().setSampleMimeType(MimeTypes.APPLICATION_CEA708);
                    int i5 = adaptationSet.id;
                    StringBuilder sb2 = new StringBuilder(18);
                    sb2.append(i5);
                    sb2.append(":cea708");
                    return parseClosedCaptionDescriptor(descriptor, CEA708_SERVICE_DESCRIPTOR_REGEX, sampleMimeType2.setId(sb2.toString()).build());
                }
            }
        }
        return new Format[0];
    }

    private static int[][] getGroupedAdaptationSetIndices(List<AdaptationSet> list) {
        int iMin;
        Descriptor descriptorFindAdaptationSetSwitchingProperty;
        int size = list.size();
        SparseIntArray sparseIntArray = new SparseIntArray(size);
        ArrayList arrayList = new ArrayList(size);
        SparseArray sparseArray = new SparseArray(size);
        for (int i2 = 0; i2 < size; i2++) {
            sparseIntArray.put(list.get(i2).id, i2);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(Integer.valueOf(i2));
            arrayList.add(arrayList2);
            sparseArray.put(i2, arrayList2);
        }
        for (int i3 = 0; i3 < size; i3++) {
            AdaptationSet adaptationSet = list.get(i3);
            Descriptor descriptorFindTrickPlayProperty = findTrickPlayProperty(adaptationSet.essentialProperties);
            if (descriptorFindTrickPlayProperty == null) {
                descriptorFindTrickPlayProperty = findTrickPlayProperty(adaptationSet.supplementalProperties);
            }
            if (descriptorFindTrickPlayProperty == null || (iMin = sparseIntArray.get(Integer.parseInt(descriptorFindTrickPlayProperty.value), -1)) == -1) {
                iMin = i3;
            }
            if (iMin == i3 && (descriptorFindAdaptationSetSwitchingProperty = findAdaptationSetSwitchingProperty(adaptationSet.supplementalProperties)) != null) {
                for (String str : Util.split(descriptorFindAdaptationSetSwitchingProperty.value, ",")) {
                    int i4 = sparseIntArray.get(Integer.parseInt(str), -1);
                    if (i4 != -1) {
                        iMin = Math.min(iMin, i4);
                    }
                }
            }
            if (iMin != i3) {
                List list2 = (List) sparseArray.get(i3);
                List list3 = (List) sparseArray.get(iMin);
                list3.addAll(list2);
                sparseArray.put(i3, list3);
                arrayList.remove(list2);
            }
        }
        int size2 = arrayList.size();
        int[][] iArr = new int[size2][];
        for (int i5 = 0; i5 < size2; i5++) {
            int[] array = Ints.toArray((Collection) arrayList.get(i5));
            iArr[i5] = array;
            Arrays.sort(array);
        }
        return iArr;
    }

    private int getPrimaryStreamIndex(int i2, int[] iArr) {
        int i3 = iArr[i2];
        if (i3 == -1) {
            return -1;
        }
        int i4 = this.trackGroupInfos[i3].primaryTrackGroupIndex;
        for (int i5 = 0; i5 < iArr.length; i5++) {
            int i6 = iArr[i5];
            if (i6 == i4 && this.trackGroupInfos[i6].trackGroupCategory == 0) {
                return i5;
            }
        }
        return -1;
    }

    private int[] getStreamIndexToTrackGroupIndex(ExoTrackSelection[] exoTrackSelectionArr) {
        int[] iArr = new int[exoTrackSelectionArr.length];
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
            if (exoTrackSelection != null) {
                iArr[i2] = this.trackGroups.indexOf(exoTrackSelection.getTrackGroup());
            } else {
                iArr[i2] = -1;
            }
        }
        return iArr;
    }

    private static boolean hasEventMessageTrack(List<AdaptationSet> list, int[] iArr) {
        for (int i2 : iArr) {
            List<Representation> list2 = list.get(i2).representations;
            for (int i3 = 0; i3 < list2.size(); i3++) {
                if (!list2.get(i3).inbandEventStreams.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int identifyEmbeddedTracks(int i2, List<AdaptationSet> list, int[][] iArr, boolean[] zArr, Format[][] formatArr) {
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (hasEventMessageTrack(list, iArr[i4])) {
                zArr[i4] = true;
                i3++;
            }
            Format[] closedCaptionTrackFormats = getClosedCaptionTrackFormats(list, iArr[i4]);
            formatArr[i4] = closedCaptionTrackFormats;
            if (closedCaptionTrackFormats.length != 0) {
                i3++;
            }
        }
        return i3;
    }

    private static ChunkSampleStream<DashChunkSource>[] newSampleStreamArray(int i2) {
        return new ChunkSampleStream[i2];
    }

    private static Format[] parseClosedCaptionDescriptor(Descriptor descriptor, Pattern pattern, Format format) throws NumberFormatException {
        String str = descriptor.value;
        if (str == null) {
            return new Format[]{format};
        }
        String[] strArrSplit = Util.split(str, h.f3376b);
        Format[] formatArr = new Format[strArrSplit.length];
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            Matcher matcher = pattern.matcher(strArrSplit[i2]);
            if (!matcher.matches()) {
                return new Format[]{format};
            }
            int i3 = Integer.parseInt(matcher.group(1));
            Format.Builder builderBuildUpon = format.buildUpon();
            String str2 = format.id;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 12);
            sb.append(str2);
            sb.append(":");
            sb.append(i3);
            formatArr[i2] = builderBuildUpon.setId(sb.toString()).setAccessibilityChannel(i3).setLanguage(matcher.group(2)).build();
        }
        return formatArr;
    }

    private void releaseDisabledStreams(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr) {
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            if (exoTrackSelectionArr[i2] == null || !zArr[i2]) {
                SampleStream sampleStream = sampleStreamArr[i2];
                if (sampleStream instanceof ChunkSampleStream) {
                    ((ChunkSampleStream) sampleStream).release(this);
                } else if (sampleStream instanceof ChunkSampleStream.EmbeddedSampleStream) {
                    ((ChunkSampleStream.EmbeddedSampleStream) sampleStream).release();
                }
                sampleStreamArr[i2] = null;
            }
        }
    }

    private void releaseOrphanEmbeddedStreams(ExoTrackSelection[] exoTrackSelectionArr, SampleStream[] sampleStreamArr, int[] iArr) {
        boolean z2;
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            SampleStream sampleStream = sampleStreamArr[i2];
            if ((sampleStream instanceof EmptySampleStream) || (sampleStream instanceof ChunkSampleStream.EmbeddedSampleStream)) {
                int primaryStreamIndex = getPrimaryStreamIndex(i2, iArr);
                if (primaryStreamIndex == -1) {
                    z2 = sampleStreamArr[i2] instanceof EmptySampleStream;
                } else {
                    SampleStream sampleStream2 = sampleStreamArr[i2];
                    z2 = (sampleStream2 instanceof ChunkSampleStream.EmbeddedSampleStream) && ((ChunkSampleStream.EmbeddedSampleStream) sampleStream2).parent == sampleStreamArr[primaryStreamIndex];
                }
                if (!z2) {
                    SampleStream sampleStream3 = sampleStreamArr[i2];
                    if (sampleStream3 instanceof ChunkSampleStream.EmbeddedSampleStream) {
                        ((ChunkSampleStream.EmbeddedSampleStream) sampleStream3).release();
                    }
                    sampleStreamArr[i2] = null;
                }
            }
        }
    }

    private void selectNewStreams(ExoTrackSelection[] exoTrackSelectionArr, SampleStream[] sampleStreamArr, boolean[] zArr, long j2, int[] iArr) {
        for (int i2 = 0; i2 < exoTrackSelectionArr.length; i2++) {
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i2];
            if (exoTrackSelection != null) {
                SampleStream sampleStream = sampleStreamArr[i2];
                if (sampleStream == null) {
                    zArr[i2] = true;
                    TrackGroupInfo trackGroupInfo = this.trackGroupInfos[iArr[i2]];
                    int i3 = trackGroupInfo.trackGroupCategory;
                    if (i3 == 0) {
                        sampleStreamArr[i2] = buildSampleStream(trackGroupInfo, exoTrackSelection, j2);
                    } else if (i3 == 2) {
                        sampleStreamArr[i2] = new EventSampleStream(this.eventStreams.get(trackGroupInfo.eventStreamGroupIndex), exoTrackSelection.getTrackGroup().getFormat(0), this.manifest.dynamic);
                    }
                } else if (sampleStream instanceof ChunkSampleStream) {
                    ((DashChunkSource) ((ChunkSampleStream) sampleStream).getChunkSource()).updateTrackSelection(exoTrackSelection);
                }
            }
        }
        for (int i4 = 0; i4 < exoTrackSelectionArr.length; i4++) {
            if (sampleStreamArr[i4] == null && exoTrackSelectionArr[i4] != null) {
                TrackGroupInfo trackGroupInfo2 = this.trackGroupInfos[iArr[i4]];
                if (trackGroupInfo2.trackGroupCategory == 1) {
                    int primaryStreamIndex = getPrimaryStreamIndex(i4, iArr);
                    if (primaryStreamIndex == -1) {
                        sampleStreamArr[i4] = new EmptySampleStream();
                    } else {
                        sampleStreamArr[i4] = ((ChunkSampleStream) sampleStreamArr[primaryStreamIndex]).selectEmbeddedTrack(j2, trackGroupInfo2.trackType);
                    }
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean continueLoading(long j2) {
        return this.compositeSequenceableLoader.continueLoading(j2);
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void discardBuffer(long j2, boolean z2) {
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.discardBuffer(j2, z2);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long getAdjustedSeekPositionUs(long j2, SeekParameters seekParameters) {
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
            if (chunkSampleStream.primaryTrackType == 2) {
                return chunkSampleStream.getAdjustedSeekPositionUs(j2, seekParameters);
            }
        }
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

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public List<StreamKey> getStreamKeys(List<ExoTrackSelection> list) {
        List<AdaptationSet> list2 = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList arrayList = new ArrayList();
        for (ExoTrackSelection exoTrackSelection : list) {
            TrackGroupInfo trackGroupInfo = this.trackGroupInfos[this.trackGroups.indexOf(exoTrackSelection.getTrackGroup())];
            if (trackGroupInfo.trackGroupCategory == 0) {
                int[] iArr = trackGroupInfo.adaptationSetIndices;
                int length = exoTrackSelection.length();
                int[] iArr2 = new int[length];
                for (int i2 = 0; i2 < exoTrackSelection.length(); i2++) {
                    iArr2[i2] = exoTrackSelection.getIndexInTrackGroup(i2);
                }
                Arrays.sort(iArr2);
                int size = list2.get(iArr[0]).representations.size();
                int i3 = 0;
                int i4 = 0;
                for (int i5 = 0; i5 < length; i5++) {
                    int i6 = iArr2[i5];
                    while (true) {
                        int i7 = i4 + size;
                        if (i6 >= i7) {
                            i3++;
                            size = list2.get(iArr[i3]).representations.size();
                            i4 = i7;
                        }
                    }
                    arrayList.add(new StreamKey(this.periodIndex, iArr[i3], i6 - i4));
                }
            }
        }
        return arrayList;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod, com.google.android.exoplayer2.source.SequenceableLoader
    public boolean isLoading() {
        return this.compositeSequenceableLoader.isLoading();
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void maybeThrowPrepareError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkSampleStream.ReleaseCallback
    public synchronized void onSampleStreamReleased(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandlerRemove = this.trackEmsgHandlerBySampleStream.remove(chunkSampleStream);
        if (playerTrackEmsgHandlerRemove != null) {
            playerTrackEmsgHandlerRemove.release();
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public void prepare(MediaPeriod.Callback callback, long j2) {
        this.callback = callback;
        callback.onPrepared(this);
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
        this.playerEmsgHandler.release();
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.release(this);
        }
        this.callback = null;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long seekToUs(long j2) {
        for (ChunkSampleStream<DashChunkSource> chunkSampleStream : this.sampleStreams) {
            chunkSampleStream.seekToUs(j2);
        }
        for (EventSampleStream eventSampleStream : this.eventSampleStreams) {
            eventSampleStream.seekToUs(j2);
        }
        return j2;
    }

    @Override // com.google.android.exoplayer2.source.MediaPeriod
    public long selectTracks(ExoTrackSelection[] exoTrackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j2) {
        int[] streamIndexToTrackGroupIndex = getStreamIndexToTrackGroupIndex(exoTrackSelectionArr);
        releaseDisabledStreams(exoTrackSelectionArr, zArr, sampleStreamArr);
        releaseOrphanEmbeddedStreams(exoTrackSelectionArr, sampleStreamArr, streamIndexToTrackGroupIndex);
        selectNewStreams(exoTrackSelectionArr, sampleStreamArr, zArr2, j2, streamIndexToTrackGroupIndex);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (SampleStream sampleStream : sampleStreamArr) {
            if (sampleStream instanceof ChunkSampleStream) {
                arrayList.add((ChunkSampleStream) sampleStream);
            } else if (sampleStream instanceof EventSampleStream) {
                arrayList2.add((EventSampleStream) sampleStream);
            }
        }
        ChunkSampleStream<DashChunkSource>[] chunkSampleStreamArrNewSampleStreamArray = newSampleStreamArray(arrayList.size());
        this.sampleStreams = chunkSampleStreamArrNewSampleStreamArray;
        arrayList.toArray(chunkSampleStreamArrNewSampleStreamArray);
        EventSampleStream[] eventSampleStreamArr = new EventSampleStream[arrayList2.size()];
        this.eventSampleStreams = eventSampleStreamArr;
        arrayList2.toArray(eventSampleStreamArr);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        return j2;
    }

    public void updateManifest(DashManifest dashManifest, int i2) {
        this.manifest = dashManifest;
        this.periodIndex = i2;
        this.playerEmsgHandler.updateManifest(dashManifest);
        ChunkSampleStream<DashChunkSource>[] chunkSampleStreamArr = this.sampleStreams;
        if (chunkSampleStreamArr != null) {
            for (ChunkSampleStream<DashChunkSource> chunkSampleStream : chunkSampleStreamArr) {
                ((DashChunkSource) chunkSampleStream.getChunkSource()).updateManifest(dashManifest, i2);
            }
            this.callback.onContinueLoadingRequested(this);
        }
        this.eventStreams = dashManifest.getPeriod(i2).eventStreams;
        for (EventSampleStream eventSampleStream : this.eventSampleStreams) {
            Iterator<EventStream> it = this.eventStreams.iterator();
            while (true) {
                if (it.hasNext()) {
                    EventStream next = it.next();
                    if (next.id().equals(eventSampleStream.eventStreamId())) {
                        eventSampleStream.updateEventStream(next, dashManifest.dynamic && i2 == dashManifest.getPeriodCount() - 1);
                    }
                }
            }
        }
    }

    @Override // com.google.android.exoplayer2.source.SequenceableLoader.Callback
    public void onContinueLoadingRequested(ChunkSampleStream<DashChunkSource> chunkSampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }
}
