package com.google.android.exoplayer2.source;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManagerProvider;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.text.SubtitleDecoderFactory;
import com.google.android.exoplayer2.text.SubtitleExtractor;
import com.google.android.exoplayer2.ui.AdViewProvider;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public final class DefaultMediaSourceFactory implements MediaSourceFactory {
    private static final String TAG = "DefaultMediaSourceFactory";

    @Nullable
    private AdViewProvider adViewProvider;

    @Nullable
    private AdsLoaderProvider adsLoaderProvider;
    private final DataSource.Factory dataSourceFactory;
    private final DelegateFactoryLoader delegateFactoryLoader;
    private long liveMaxOffsetMs;
    private float liveMaxSpeed;
    private long liveMinOffsetMs;
    private float liveMinSpeed;
    private long liveTargetOffsetMs;

    @Nullable
    private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private boolean useProgressiveMediaSourceForSubtitles;

    public interface AdsLoaderProvider {
        @Nullable
        AdsLoader getAdsLoader(MediaItem.AdsConfiguration adsConfiguration);
    }

    public static final class DelegateFactoryLoader {
        private final DataSource.Factory dataSourceFactory;

        @Nullable
        private HttpDataSource.Factory drmHttpDataSourceFactory;

        @Nullable
        private DrmSessionManager drmSessionManager;

        @Nullable
        private DrmSessionManagerProvider drmSessionManagerProvider;
        private final ExtractorsFactory extractorsFactory;

        @Nullable
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;

        @Nullable
        private List<StreamKey> streamKeys;

        @Nullable
        private String userAgent;
        private final Map<Integer, Supplier<MediaSourceFactory>> mediaSourceFactorySuppliers = new HashMap();
        private final Set<Integer> supportedTypes = new HashSet();
        private final Map<Integer, MediaSourceFactory> mediaSourceFactories = new HashMap();

        public DelegateFactoryLoader(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
            this.dataSourceFactory = factory;
            this.extractorsFactory = extractorsFactory;
        }

        private void ensureAllSuppliersAreLoaded() {
            maybeLoadSupplier(0);
            maybeLoadSupplier(1);
            maybeLoadSupplier(2);
            maybeLoadSupplier(3);
            maybeLoadSupplier(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ MediaSourceFactory lambda$maybeLoadSupplier$0(Class cls) {
            return DefaultMediaSourceFactory.newInstance(cls, this.dataSourceFactory);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ MediaSourceFactory lambda$maybeLoadSupplier$1(Class cls) {
            return DefaultMediaSourceFactory.newInstance(cls, this.dataSourceFactory);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ MediaSourceFactory lambda$maybeLoadSupplier$2(Class cls) {
            return DefaultMediaSourceFactory.newInstance(cls, this.dataSourceFactory);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ MediaSourceFactory lambda$maybeLoadSupplier$4() {
            return new ProgressiveMediaSource.Factory(this.dataSourceFactory, this.extractorsFactory);
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x006d  */
        @androidx.annotation.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private com.google.common.base.Supplier<com.google.android.exoplayer2.source.MediaSourceFactory> maybeLoadSupplier(int r4) {
            /*
                r3 = this;
                java.util.Map<java.lang.Integer, com.google.common.base.Supplier<com.google.android.exoplayer2.source.MediaSourceFactory>> r0 = r3.mediaSourceFactorySuppliers
                java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
                boolean r0 = r0.containsKey(r1)
                if (r0 == 0) goto L19
                java.util.Map<java.lang.Integer, com.google.common.base.Supplier<com.google.android.exoplayer2.source.MediaSourceFactory>> r0 = r3.mediaSourceFactorySuppliers
                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                java.lang.Object r4 = r0.get(r4)
                com.google.common.base.Supplier r4 = (com.google.common.base.Supplier) r4
                return r4
            L19:
                java.lang.Class<com.google.android.exoplayer2.source.MediaSourceFactory> r0 = com.google.android.exoplayer2.source.MediaSourceFactory.class
                r1 = 0
                if (r4 == 0) goto L56
                r2 = 1
                if (r4 == r2) goto L4a
                r2 = 2
                if (r4 == r2) goto L3e
                r2 = 3
                if (r4 == r2) goto L32
                r0 = 4
                if (r4 == r0) goto L2b
                goto L62
            L2b:
                com.google.android.exoplayer2.source.h r0 = new com.google.android.exoplayer2.source.h     // Catch: java.lang.ClassNotFoundException -> L62
                r0.<init>()     // Catch: java.lang.ClassNotFoundException -> L62
                r1 = r0
                goto L62
            L32:
                java.lang.Class<com.google.android.exoplayer2.source.rtsp.RtspMediaSource$Factory> r2 = com.google.android.exoplayer2.source.rtsp.RtspMediaSource.Factory.class
                java.lang.Class r0 = r2.asSubclass(r0)     // Catch: java.lang.ClassNotFoundException -> L62
                com.google.android.exoplayer2.source.g r2 = new com.google.android.exoplayer2.source.g     // Catch: java.lang.ClassNotFoundException -> L62
                r2.<init>()     // Catch: java.lang.ClassNotFoundException -> L62
                goto L61
            L3e:
                java.lang.Class<com.google.android.exoplayer2.source.hls.HlsMediaSource$Factory> r2 = com.google.android.exoplayer2.source.hls.HlsMediaSource.Factory.class
                java.lang.Class r0 = r2.asSubclass(r0)     // Catch: java.lang.ClassNotFoundException -> L62
                com.google.android.exoplayer2.source.f r2 = new com.google.android.exoplayer2.source.f     // Catch: java.lang.ClassNotFoundException -> L62
                r2.<init>()     // Catch: java.lang.ClassNotFoundException -> L62
                goto L61
            L4a:
                java.lang.Class<com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource$Factory> r2 = com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource.Factory.class
                java.lang.Class r0 = r2.asSubclass(r0)     // Catch: java.lang.ClassNotFoundException -> L62
                com.google.android.exoplayer2.source.e r2 = new com.google.android.exoplayer2.source.e     // Catch: java.lang.ClassNotFoundException -> L62
                r2.<init>()     // Catch: java.lang.ClassNotFoundException -> L62
                goto L61
            L56:
                java.lang.Class<com.google.android.exoplayer2.source.dash.DashMediaSource$Factory> r2 = com.google.android.exoplayer2.source.dash.DashMediaSource.Factory.class
                java.lang.Class r0 = r2.asSubclass(r0)     // Catch: java.lang.ClassNotFoundException -> L62
                com.google.android.exoplayer2.source.d r2 = new com.google.android.exoplayer2.source.d     // Catch: java.lang.ClassNotFoundException -> L62
                r2.<init>()     // Catch: java.lang.ClassNotFoundException -> L62
            L61:
                r1 = r2
            L62:
                java.util.Map<java.lang.Integer, com.google.common.base.Supplier<com.google.android.exoplayer2.source.MediaSourceFactory>> r0 = r3.mediaSourceFactorySuppliers
                java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
                r0.put(r2, r1)
                if (r1 == 0) goto L76
                java.util.Set<java.lang.Integer> r0 = r3.supportedTypes
                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                r0.add(r4)
            L76:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.DefaultMediaSourceFactory.DelegateFactoryLoader.maybeLoadSupplier(int):com.google.common.base.Supplier");
        }

        @Nullable
        public MediaSourceFactory getMediaSourceFactory(int i2) {
            MediaSourceFactory mediaSourceFactory = this.mediaSourceFactories.get(Integer.valueOf(i2));
            if (mediaSourceFactory != null) {
                return mediaSourceFactory;
            }
            Supplier<MediaSourceFactory> supplierMaybeLoadSupplier = maybeLoadSupplier(i2);
            if (supplierMaybeLoadSupplier == null) {
                return null;
            }
            MediaSourceFactory mediaSourceFactory2 = supplierMaybeLoadSupplier.get();
            HttpDataSource.Factory factory = this.drmHttpDataSourceFactory;
            if (factory != null) {
                mediaSourceFactory2.setDrmHttpDataSourceFactory(factory);
            }
            String str = this.userAgent;
            if (str != null) {
                mediaSourceFactory2.setDrmUserAgent(str);
            }
            DrmSessionManager drmSessionManager = this.drmSessionManager;
            if (drmSessionManager != null) {
                mediaSourceFactory2.setDrmSessionManager(drmSessionManager);
            }
            DrmSessionManagerProvider drmSessionManagerProvider = this.drmSessionManagerProvider;
            if (drmSessionManagerProvider != null) {
                mediaSourceFactory2.setDrmSessionManagerProvider(drmSessionManagerProvider);
            }
            LoadErrorHandlingPolicy loadErrorHandlingPolicy = this.loadErrorHandlingPolicy;
            if (loadErrorHandlingPolicy != null) {
                mediaSourceFactory2.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
            }
            List<StreamKey> list = this.streamKeys;
            if (list != null) {
                mediaSourceFactory2.setStreamKeys(list);
            }
            this.mediaSourceFactories.put(Integer.valueOf(i2), mediaSourceFactory2);
            return mediaSourceFactory2;
        }

        public int[] getSupportedTypes() {
            ensureAllSuppliersAreLoaded();
            return Ints.toArray(this.supportedTypes);
        }

        public void setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
            this.drmHttpDataSourceFactory = factory;
            Iterator<MediaSourceFactory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setDrmHttpDataSourceFactory(factory);
            }
        }

        public void setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
            this.drmSessionManager = drmSessionManager;
            Iterator<MediaSourceFactory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setDrmSessionManager(drmSessionManager);
            }
        }

        public void setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
            this.drmSessionManagerProvider = drmSessionManagerProvider;
            Iterator<MediaSourceFactory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setDrmSessionManagerProvider(drmSessionManagerProvider);
            }
        }

        public void setDrmUserAgent(@Nullable String str) {
            this.userAgent = str;
            Iterator<MediaSourceFactory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setDrmUserAgent(str);
            }
        }

        public void setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            Iterator<MediaSourceFactory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
            }
        }

        public void setStreamKeys(@Nullable List<StreamKey> list) {
            this.streamKeys = list;
            Iterator<MediaSourceFactory> it = this.mediaSourceFactories.values().iterator();
            while (it.hasNext()) {
                it.next().setStreamKeys(list);
            }
        }
    }

    public static final class UnknownSubtitlesExtractor implements Extractor {
        private final Format format;

        public UnknownSubtitlesExtractor(Format format) {
            this.format = format;
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public void init(ExtractorOutput extractorOutput) {
            TrackOutput trackOutputTrack = extractorOutput.track(0, 3);
            extractorOutput.seekMap(new SeekMap.Unseekable(C.TIME_UNSET));
            extractorOutput.endTracks();
            trackOutputTrack.format(this.format.buildUpon().setSampleMimeType(MimeTypes.TEXT_UNKNOWN).setCodecs(this.format.sampleMimeType).build());
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
            return extractorInput.skip(Integer.MAX_VALUE) == -1 ? -1 : 0;
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public void release() {
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public void seek(long j2, long j3) {
        }

        @Override // com.google.android.exoplayer2.extractor.Extractor
        public boolean sniff(ExtractorInput extractorInput) {
            return true;
        }
    }

    public DefaultMediaSourceFactory(Context context) {
        this(new DefaultDataSource.Factory(context));
    }

    public static /* synthetic */ MediaSourceFactory access$000(Class cls) {
        return newInstance(cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Extractor[] lambda$createMediaSource$0(Format format) {
        Extractor[] extractorArr = new Extractor[1];
        SubtitleDecoderFactory subtitleDecoderFactory = SubtitleDecoderFactory.DEFAULT;
        extractorArr[0] = subtitleDecoderFactory.supportsFormat(format) ? new SubtitleExtractor(subtitleDecoderFactory.createDecoder(format), format) : new UnknownSubtitlesExtractor(format);
        return extractorArr;
    }

    private static MediaSource maybeClipMediaSource(MediaItem mediaItem, MediaSource mediaSource) {
        MediaItem.ClippingConfiguration clippingConfiguration = mediaItem.clippingConfiguration;
        long j2 = clippingConfiguration.startPositionMs;
        if (j2 == 0 && clippingConfiguration.endPositionMs == Long.MIN_VALUE && !clippingConfiguration.relativeToDefaultPosition) {
            return mediaSource;
        }
        long jMsToUs = Util.msToUs(j2);
        long jMsToUs2 = Util.msToUs(mediaItem.clippingConfiguration.endPositionMs);
        MediaItem.ClippingConfiguration clippingConfiguration2 = mediaItem.clippingConfiguration;
        return new ClippingMediaSource(mediaSource, jMsToUs, jMsToUs2, !clippingConfiguration2.startsAtKeyFrame, clippingConfiguration2.relativeToLiveWindow, clippingConfiguration2.relativeToDefaultPosition);
    }

    private MediaSource maybeWrapWithAdsMediaSource(MediaItem mediaItem, MediaSource mediaSource) {
        Assertions.checkNotNull(mediaItem.localConfiguration);
        MediaItem.AdsConfiguration adsConfiguration = mediaItem.localConfiguration.adsConfiguration;
        if (adsConfiguration == null) {
            return mediaSource;
        }
        AdsLoaderProvider adsLoaderProvider = this.adsLoaderProvider;
        AdViewProvider adViewProvider = this.adViewProvider;
        if (adsLoaderProvider == null || adViewProvider == null) {
            Log.w(TAG, "Playing media without ads. Configure ad support by calling setAdsLoaderProvider and setAdViewProvider.");
            return mediaSource;
        }
        AdsLoader adsLoader = adsLoaderProvider.getAdsLoader(adsConfiguration);
        if (adsLoader == null) {
            Log.w(TAG, "Playing media without ads, as no AdsLoader was provided.");
            return mediaSource;
        }
        DataSpec dataSpec = new DataSpec(adsConfiguration.adTagUri);
        Object obj = adsConfiguration.adsId;
        return new AdsMediaSource(mediaSource, dataSpec, obj != null ? obj : ImmutableList.of((Uri) mediaItem.mediaId, mediaItem.localConfiguration.uri, adsConfiguration.adTagUri), this, adsLoader, adViewProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaSourceFactory newInstance(Class<? extends MediaSourceFactory> cls, DataSource.Factory factory) {
        try {
            return cls.getConstructor(DataSource.Factory.class).newInstance(factory);
        } catch (Exception e2) {
            throw new IllegalStateException(e2);
        }
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public /* synthetic */ MediaSource createMediaSource(Uri uri) {
        return z.a(this, uri);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public MediaSource createMediaSource(MediaItem mediaItem) {
        Assertions.checkNotNull(mediaItem.localConfiguration);
        MediaItem.LocalConfiguration localConfiguration = mediaItem.localConfiguration;
        int iInferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(localConfiguration.uri, localConfiguration.mimeType);
        MediaSourceFactory mediaSourceFactory = this.delegateFactoryLoader.getMediaSourceFactory(iInferContentTypeForUriAndMimeType);
        StringBuilder sb = new StringBuilder(68);
        sb.append("No suitable media source factory found for content type: ");
        sb.append(iInferContentTypeForUriAndMimeType);
        Assertions.checkStateNotNull(mediaSourceFactory, sb.toString());
        MediaItem.LiveConfiguration.Builder builderBuildUpon = mediaItem.liveConfiguration.buildUpon();
        if (mediaItem.liveConfiguration.targetOffsetMs == C.TIME_UNSET) {
            builderBuildUpon.setTargetOffsetMs(this.liveTargetOffsetMs);
        }
        if (mediaItem.liveConfiguration.minPlaybackSpeed == -3.4028235E38f) {
            builderBuildUpon.setMinPlaybackSpeed(this.liveMinSpeed);
        }
        if (mediaItem.liveConfiguration.maxPlaybackSpeed == -3.4028235E38f) {
            builderBuildUpon.setMaxPlaybackSpeed(this.liveMaxSpeed);
        }
        if (mediaItem.liveConfiguration.minOffsetMs == C.TIME_UNSET) {
            builderBuildUpon.setMinOffsetMs(this.liveMinOffsetMs);
        }
        if (mediaItem.liveConfiguration.maxOffsetMs == C.TIME_UNSET) {
            builderBuildUpon.setMaxOffsetMs(this.liveMaxOffsetMs);
        }
        MediaItem.LiveConfiguration liveConfigurationBuild = builderBuildUpon.build();
        if (!liveConfigurationBuild.equals(mediaItem.liveConfiguration)) {
            mediaItem = mediaItem.buildUpon().setLiveConfiguration(liveConfigurationBuild).build();
        }
        MediaSource mediaSourceCreateMediaSource = mediaSourceFactory.createMediaSource(mediaItem);
        ImmutableList<MediaItem.SubtitleConfiguration> immutableList = ((MediaItem.LocalConfiguration) Util.castNonNull(mediaItem.localConfiguration)).subtitleConfigurations;
        if (!immutableList.isEmpty()) {
            MediaSource[] mediaSourceArr = new MediaSource[immutableList.size() + 1];
            mediaSourceArr[0] = mediaSourceCreateMediaSource;
            for (int i2 = 0; i2 < immutableList.size(); i2++) {
                if (this.useProgressiveMediaSourceForSubtitles) {
                    final Format formatBuild = new Format.Builder().setSampleMimeType(immutableList.get(i2).mimeType).setLanguage(immutableList.get(i2).language).setSelectionFlags(immutableList.get(i2).selectionFlags).setRoleFlags(immutableList.get(i2).roleFlags).setLabel(immutableList.get(i2).label).build();
                    mediaSourceArr[i2 + 1] = new ProgressiveMediaSource.Factory(this.dataSourceFactory, new ExtractorsFactory() { // from class: com.google.android.exoplayer2.source.c
                        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
                        public final Extractor[] createExtractors() {
                            return DefaultMediaSourceFactory.lambda$createMediaSource$0(formatBuild);
                        }

                        @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
                        public /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
                            return com.google.android.exoplayer2.extractor.c.a(this, uri, map);
                        }
                    }).createMediaSource(MediaItem.fromUri(immutableList.get(i2).uri.toString()));
                } else {
                    mediaSourceArr[i2 + 1] = new SingleSampleMediaSource.Factory(this.dataSourceFactory).setLoadErrorHandlingPolicy(this.loadErrorHandlingPolicy).createMediaSource(immutableList.get(i2), C.TIME_UNSET);
                }
            }
            mediaSourceCreateMediaSource = new MergingMediaSource(mediaSourceArr);
        }
        return maybeWrapWithAdsMediaSource(mediaItem, maybeClipMediaSource(mediaItem, mediaSourceCreateMediaSource));
    }

    public DefaultMediaSourceFactory experimentalUseProgressiveMediaSourceForSubtitles(boolean z2) {
        this.useProgressiveMediaSourceForSubtitles = z2;
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public int[] getSupportedTypes() {
        return this.delegateFactoryLoader.getSupportedTypes();
    }

    public DefaultMediaSourceFactory setAdViewProvider(@Nullable AdViewProvider adViewProvider) {
        this.adViewProvider = adViewProvider;
        return this;
    }

    public DefaultMediaSourceFactory setAdsLoaderProvider(@Nullable AdsLoaderProvider adsLoaderProvider) {
        this.adsLoaderProvider = adsLoaderProvider;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxOffsetMs(long j2) {
        this.liveMaxOffsetMs = j2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMaxSpeed(float f2) {
        this.liveMaxSpeed = f2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinOffsetMs(long j2) {
        this.liveMinOffsetMs = j2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveMinSpeed(float f2) {
        this.liveMinSpeed = f2;
        return this;
    }

    public DefaultMediaSourceFactory setLiveTargetOffsetMs(long j2) {
        this.liveTargetOffsetMs = j2;
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public /* bridge */ /* synthetic */ MediaSourceFactory setStreamKeys(@Nullable List list) {
        return setStreamKeys((List<StreamKey>) list);
    }

    public DefaultMediaSourceFactory(Context context, ExtractorsFactory extractorsFactory) {
        this(new DefaultDataSource.Factory(context), extractorsFactory);
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public DefaultMediaSourceFactory setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
        this.delegateFactoryLoader.setDrmHttpDataSourceFactory(factory);
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public DefaultMediaSourceFactory setDrmSessionManager(@Nullable DrmSessionManager drmSessionManager) {
        this.delegateFactoryLoader.setDrmSessionManager(drmSessionManager);
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setDrmSessionManagerProvider(@Nullable DrmSessionManagerProvider drmSessionManagerProvider) {
        this.delegateFactoryLoader.setDrmSessionManagerProvider(drmSessionManagerProvider);
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public DefaultMediaSourceFactory setDrmUserAgent(@Nullable String str) {
        this.delegateFactoryLoader.setDrmUserAgent(str);
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    public DefaultMediaSourceFactory setLoadErrorHandlingPolicy(@Nullable LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.delegateFactoryLoader.setLoadErrorHandlingPolicy(loadErrorHandlingPolicy);
        return this;
    }

    @Override // com.google.android.exoplayer2.source.MediaSourceFactory
    @Deprecated
    public DefaultMediaSourceFactory setStreamKeys(@Nullable List<StreamKey> list) {
        this.delegateFactoryLoader.setStreamKeys(list);
        return this;
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory) {
        this(factory, new DefaultExtractorsFactory());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaSourceFactory newInstance(Class<? extends MediaSourceFactory> cls) {
        try {
            return cls.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e2) {
            throw new IllegalStateException(e2);
        }
    }

    public DefaultMediaSourceFactory(DataSource.Factory factory, ExtractorsFactory extractorsFactory) {
        this.dataSourceFactory = factory;
        this.delegateFactoryLoader = new DelegateFactoryLoader(factory, extractorsFactory);
        this.liveTargetOffsetMs = C.TIME_UNSET;
        this.liveMinOffsetMs = C.TIME_UNSET;
        this.liveMaxOffsetMs = C.TIME_UNSET;
        this.liveMinSpeed = -3.4028235E38f;
        this.liveMaxSpeed = -3.4028235E38f;
    }
}
