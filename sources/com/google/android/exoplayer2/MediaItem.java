package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes3.dex */
public final class MediaItem implements Bundleable {
    public static final String DEFAULT_MEDIA_ID = "";
    private static final int FIELD_CLIPPING_PROPERTIES = 3;
    private static final int FIELD_LIVE_CONFIGURATION = 1;
    private static final int FIELD_MEDIA_ID = 0;
    private static final int FIELD_MEDIA_METADATA = 2;
    public final ClippingConfiguration clippingConfiguration;

    @Deprecated
    public final ClippingProperties clippingProperties;
    public final LiveConfiguration liveConfiguration;

    @Nullable
    public final LocalConfiguration localConfiguration;
    public final String mediaId;
    public final MediaMetadata mediaMetadata;

    @Nullable
    @Deprecated
    public final PlaybackProperties playbackProperties;
    public static final MediaItem EMPTY = new Builder().build();
    public static final Bundleable.Creator<MediaItem> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.m1
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return MediaItem.fromBundle(bundle);
        }
    };

    public static final class AdsConfiguration {
        public final Uri adTagUri;

        @Nullable
        public final Object adsId;

        public static final class Builder {
            private Uri adTagUri;

            @Nullable
            private Object adsId;

            public Builder(Uri uri) {
                this.adTagUri = uri;
            }

            public AdsConfiguration build() {
                return new AdsConfiguration(this);
            }

            public Builder setAdTagUri(Uri uri) {
                this.adTagUri = uri;
                return this;
            }

            public Builder setAdsId(@Nullable Object obj) {
                this.adsId = obj;
                return this;
            }
        }

        public Builder buildUpon() {
            return new Builder(this.adTagUri).setAdsId(this.adsId);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdsConfiguration)) {
                return false;
            }
            AdsConfiguration adsConfiguration = (AdsConfiguration) obj;
            return this.adTagUri.equals(adsConfiguration.adTagUri) && Util.areEqual(this.adsId, adsConfiguration.adsId);
        }

        public int hashCode() {
            int iHashCode = this.adTagUri.hashCode() * 31;
            Object obj = this.adsId;
            return iHashCode + (obj != null ? obj.hashCode() : 0);
        }

        private AdsConfiguration(Builder builder) {
            this.adTagUri = builder.adTagUri;
            this.adsId = builder.adsId;
        }
    }

    public static final class Builder {

        @Nullable
        private AdsConfiguration adsConfiguration;
        private ClippingConfiguration.Builder clippingConfiguration;

        @Nullable
        private String customCacheKey;
        private DrmConfiguration.Builder drmConfiguration;
        private LiveConfiguration.Builder liveConfiguration;

        @Nullable
        private String mediaId;

        @Nullable
        private MediaMetadata mediaMetadata;

        @Nullable
        private String mimeType;
        private List<StreamKey> streamKeys;
        private ImmutableList<SubtitleConfiguration> subtitleConfigurations;

        @Nullable
        private Object tag;

        @Nullable
        private Uri uri;

        public MediaItem build() {
            PlaybackProperties playbackProperties;
            Assertions.checkState(this.drmConfiguration.licenseUri == null || this.drmConfiguration.scheme != null);
            Uri uri = this.uri;
            if (uri != null) {
                playbackProperties = new PlaybackProperties(uri, this.mimeType, this.drmConfiguration.scheme != null ? this.drmConfiguration.build() : null, this.adsConfiguration, this.streamKeys, this.customCacheKey, this.subtitleConfigurations, this.tag);
            } else {
                playbackProperties = null;
            }
            String str = this.mediaId;
            if (str == null) {
                str = "";
            }
            String str2 = str;
            ClippingProperties clippingPropertiesBuildClippingProperties = this.clippingConfiguration.buildClippingProperties();
            LiveConfiguration liveConfigurationBuild = this.liveConfiguration.build();
            MediaMetadata mediaMetadata = this.mediaMetadata;
            if (mediaMetadata == null) {
                mediaMetadata = MediaMetadata.EMPTY;
            }
            return new MediaItem(str2, clippingPropertiesBuildClippingProperties, playbackProperties, liveConfigurationBuild, mediaMetadata);
        }

        @Deprecated
        public Builder setAdTagUri(@Nullable String str) {
            return setAdTagUri(str != null ? Uri.parse(str) : null);
        }

        public Builder setAdsConfiguration(@Nullable AdsConfiguration adsConfiguration) {
            this.adsConfiguration = adsConfiguration;
            return this;
        }

        @Deprecated
        public Builder setClipEndPositionMs(long j2) {
            this.clippingConfiguration.setEndPositionMs(j2);
            return this;
        }

        @Deprecated
        public Builder setClipRelativeToDefaultPosition(boolean z2) {
            this.clippingConfiguration.setRelativeToDefaultPosition(z2);
            return this;
        }

        @Deprecated
        public Builder setClipRelativeToLiveWindow(boolean z2) {
            this.clippingConfiguration.setRelativeToLiveWindow(z2);
            return this;
        }

        @Deprecated
        public Builder setClipStartPositionMs(@IntRange(from = 0) long j2) {
            this.clippingConfiguration.setStartPositionMs(j2);
            return this;
        }

        @Deprecated
        public Builder setClipStartsAtKeyFrame(boolean z2) {
            this.clippingConfiguration.setStartsAtKeyFrame(z2);
            return this;
        }

        public Builder setClippingConfiguration(ClippingConfiguration clippingConfiguration) {
            this.clippingConfiguration = clippingConfiguration.buildUpon();
            return this;
        }

        public Builder setCustomCacheKey(@Nullable String str) {
            this.customCacheKey = str;
            return this;
        }

        public Builder setDrmConfiguration(@Nullable DrmConfiguration drmConfiguration) {
            this.drmConfiguration = drmConfiguration != null ? drmConfiguration.buildUpon() : new DrmConfiguration.Builder();
            return this;
        }

        @Deprecated
        public Builder setDrmForceDefaultLicenseUri(boolean z2) {
            this.drmConfiguration.setForceDefaultLicenseUri(z2);
            return this;
        }

        @Deprecated
        public Builder setDrmKeySetId(@Nullable byte[] bArr) {
            this.drmConfiguration.setKeySetId(bArr);
            return this;
        }

        @Deprecated
        public Builder setDrmLicenseRequestHeaders(@Nullable Map<String, String> map) {
            DrmConfiguration.Builder builder = this.drmConfiguration;
            if (map == null) {
                map = ImmutableMap.of();
            }
            builder.setLicenseRequestHeaders(map);
            return this;
        }

        @Deprecated
        public Builder setDrmLicenseUri(@Nullable Uri uri) {
            this.drmConfiguration.setLicenseUri(uri);
            return this;
        }

        @Deprecated
        public Builder setDrmMultiSession(boolean z2) {
            this.drmConfiguration.setMultiSession(z2);
            return this;
        }

        @Deprecated
        public Builder setDrmPlayClearContentWithoutKey(boolean z2) {
            this.drmConfiguration.setPlayClearContentWithoutKey(z2);
            return this;
        }

        @Deprecated
        public Builder setDrmSessionForClearPeriods(boolean z2) {
            this.drmConfiguration.forceSessionsForAudioAndVideoTracks(z2);
            return this;
        }

        @Deprecated
        public Builder setDrmSessionForClearTypes(@Nullable List<Integer> list) {
            DrmConfiguration.Builder builder = this.drmConfiguration;
            if (list == null) {
                list = ImmutableList.of();
            }
            builder.setForcedSessionTrackTypes(list);
            return this;
        }

        @Deprecated
        public Builder setDrmUuid(@Nullable UUID uuid) {
            this.drmConfiguration.setNullableScheme(uuid);
            return this;
        }

        public Builder setLiveConfiguration(LiveConfiguration liveConfiguration) {
            this.liveConfiguration = liveConfiguration.buildUpon();
            return this;
        }

        @Deprecated
        public Builder setLiveMaxOffsetMs(long j2) {
            this.liveConfiguration.setMaxOffsetMs(j2);
            return this;
        }

        @Deprecated
        public Builder setLiveMaxPlaybackSpeed(float f2) {
            this.liveConfiguration.setMaxPlaybackSpeed(f2);
            return this;
        }

        @Deprecated
        public Builder setLiveMinOffsetMs(long j2) {
            this.liveConfiguration.setMinOffsetMs(j2);
            return this;
        }

        @Deprecated
        public Builder setLiveMinPlaybackSpeed(float f2) {
            this.liveConfiguration.setMinPlaybackSpeed(f2);
            return this;
        }

        @Deprecated
        public Builder setLiveTargetOffsetMs(long j2) {
            this.liveConfiguration.setTargetOffsetMs(j2);
            return this;
        }

        public Builder setMediaId(String str) {
            this.mediaId = (String) Assertions.checkNotNull(str);
            return this;
        }

        public Builder setMediaMetadata(MediaMetadata mediaMetadata) {
            this.mediaMetadata = mediaMetadata;
            return this;
        }

        public Builder setMimeType(@Nullable String str) {
            this.mimeType = str;
            return this;
        }

        public Builder setStreamKeys(@Nullable List<StreamKey> list) {
            this.streamKeys = (list == null || list.isEmpty()) ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(list));
            return this;
        }

        public Builder setSubtitleConfigurations(List<SubtitleConfiguration> list) {
            this.subtitleConfigurations = ImmutableList.copyOf((Collection) list);
            return this;
        }

        @Deprecated
        public Builder setSubtitles(@Nullable List<Subtitle> list) {
            this.subtitleConfigurations = list != null ? ImmutableList.copyOf((Collection) list) : ImmutableList.of();
            return this;
        }

        public Builder setTag(@Nullable Object obj) {
            this.tag = obj;
            return this;
        }

        public Builder setUri(@Nullable String str) {
            return setUri(str == null ? null : Uri.parse(str));
        }

        public Builder() {
            this.clippingConfiguration = new ClippingConfiguration.Builder();
            this.drmConfiguration = new DrmConfiguration.Builder();
            this.streamKeys = Collections.emptyList();
            this.subtitleConfigurations = ImmutableList.of();
            this.liveConfiguration = new LiveConfiguration.Builder();
        }

        @Deprecated
        public Builder setAdTagUri(@Nullable Uri uri) {
            return setAdTagUri(uri, null);
        }

        @Deprecated
        public Builder setDrmLicenseUri(@Nullable String str) {
            this.drmConfiguration.setLicenseUri(str);
            return this;
        }

        public Builder setUri(@Nullable Uri uri) {
            this.uri = uri;
            return this;
        }

        @Deprecated
        public Builder setAdTagUri(@Nullable Uri uri, @Nullable Object obj) {
            this.adsConfiguration = uri != null ? new AdsConfiguration.Builder(uri).setAdsId(obj).build() : null;
            return this;
        }

        private Builder(MediaItem mediaItem) {
            DrmConfiguration.Builder builder;
            this();
            this.clippingConfiguration = mediaItem.clippingConfiguration.buildUpon();
            this.mediaId = mediaItem.mediaId;
            this.mediaMetadata = mediaItem.mediaMetadata;
            this.liveConfiguration = mediaItem.liveConfiguration.buildUpon();
            LocalConfiguration localConfiguration = mediaItem.localConfiguration;
            if (localConfiguration != null) {
                this.customCacheKey = localConfiguration.customCacheKey;
                this.mimeType = localConfiguration.mimeType;
                this.uri = localConfiguration.uri;
                this.streamKeys = localConfiguration.streamKeys;
                this.subtitleConfigurations = localConfiguration.subtitleConfigurations;
                this.tag = localConfiguration.tag;
                DrmConfiguration drmConfiguration = localConfiguration.drmConfiguration;
                if (drmConfiguration != null) {
                    builder = drmConfiguration.buildUpon();
                } else {
                    builder = new DrmConfiguration.Builder();
                }
                this.drmConfiguration = builder;
                this.adsConfiguration = localConfiguration.adsConfiguration;
            }
        }
    }

    public static class ClippingConfiguration implements Bundleable {
        private static final int FIELD_END_POSITION_MS = 1;
        private static final int FIELD_RELATIVE_TO_DEFAULT_POSITION = 3;
        private static final int FIELD_RELATIVE_TO_LIVE_WINDOW = 2;
        private static final int FIELD_STARTS_AT_KEY_FRAME = 4;
        private static final int FIELD_START_POSITION_MS = 0;
        public final long endPositionMs;
        public final boolean relativeToDefaultPosition;
        public final boolean relativeToLiveWindow;

        @IntRange(from = 0)
        public final long startPositionMs;
        public final boolean startsAtKeyFrame;
        public static final ClippingConfiguration UNSET = new Builder().build();
        public static final Bundleable.Creator<ClippingProperties> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.n1
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return MediaItem.ClippingConfiguration.lambda$static$0(bundle);
            }
        };

        public static final class Builder {
            private long endPositionMs;
            private boolean relativeToDefaultPosition;
            private boolean relativeToLiveWindow;
            private long startPositionMs;
            private boolean startsAtKeyFrame;

            public ClippingConfiguration build() {
                return buildClippingProperties();
            }

            @Deprecated
            public ClippingProperties buildClippingProperties() {
                return new ClippingProperties(this);
            }

            public Builder setEndPositionMs(long j2) {
                Assertions.checkArgument(j2 == Long.MIN_VALUE || j2 >= 0);
                this.endPositionMs = j2;
                return this;
            }

            public Builder setRelativeToDefaultPosition(boolean z2) {
                this.relativeToDefaultPosition = z2;
                return this;
            }

            public Builder setRelativeToLiveWindow(boolean z2) {
                this.relativeToLiveWindow = z2;
                return this;
            }

            public Builder setStartPositionMs(@IntRange(from = 0) long j2) {
                Assertions.checkArgument(j2 >= 0);
                this.startPositionMs = j2;
                return this;
            }

            public Builder setStartsAtKeyFrame(boolean z2) {
                this.startsAtKeyFrame = z2;
                return this;
            }

            public Builder() {
                this.endPositionMs = Long.MIN_VALUE;
            }

            private Builder(ClippingConfiguration clippingConfiguration) {
                this.startPositionMs = clippingConfiguration.startPositionMs;
                this.endPositionMs = clippingConfiguration.endPositionMs;
                this.relativeToLiveWindow = clippingConfiguration.relativeToLiveWindow;
                this.relativeToDefaultPosition = clippingConfiguration.relativeToDefaultPosition;
                this.startsAtKeyFrame = clippingConfiguration.startsAtKeyFrame;
            }
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ ClippingProperties lambda$static$0(Bundle bundle) {
            return new Builder().setStartPositionMs(bundle.getLong(keyForField(0), 0L)).setEndPositionMs(bundle.getLong(keyForField(1), Long.MIN_VALUE)).setRelativeToLiveWindow(bundle.getBoolean(keyForField(2), false)).setRelativeToDefaultPosition(bundle.getBoolean(keyForField(3), false)).setStartsAtKeyFrame(bundle.getBoolean(keyForField(4), false)).buildClippingProperties();
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClippingConfiguration)) {
                return false;
            }
            ClippingConfiguration clippingConfiguration = (ClippingConfiguration) obj;
            return this.startPositionMs == clippingConfiguration.startPositionMs && this.endPositionMs == clippingConfiguration.endPositionMs && this.relativeToLiveWindow == clippingConfiguration.relativeToLiveWindow && this.relativeToDefaultPosition == clippingConfiguration.relativeToDefaultPosition && this.startsAtKeyFrame == clippingConfiguration.startsAtKeyFrame;
        }

        public int hashCode() {
            long j2 = this.startPositionMs;
            int i2 = ((int) (j2 ^ (j2 >>> 32))) * 31;
            long j3 = this.endPositionMs;
            return ((((((i2 + ((int) ((j3 >>> 32) ^ j3))) * 31) + (this.relativeToLiveWindow ? 1 : 0)) * 31) + (this.relativeToDefaultPosition ? 1 : 0)) * 31) + (this.startsAtKeyFrame ? 1 : 0);
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(keyForField(0), this.startPositionMs);
            bundle.putLong(keyForField(1), this.endPositionMs);
            bundle.putBoolean(keyForField(2), this.relativeToLiveWindow);
            bundle.putBoolean(keyForField(3), this.relativeToDefaultPosition);
            bundle.putBoolean(keyForField(4), this.startsAtKeyFrame);
            return bundle;
        }

        private ClippingConfiguration(Builder builder) {
            this.startPositionMs = builder.startPositionMs;
            this.endPositionMs = builder.endPositionMs;
            this.relativeToLiveWindow = builder.relativeToLiveWindow;
            this.relativeToDefaultPosition = builder.relativeToDefaultPosition;
            this.startsAtKeyFrame = builder.startsAtKeyFrame;
        }
    }

    @Deprecated
    public static final class ClippingProperties extends ClippingConfiguration {
        public static final ClippingProperties UNSET = new ClippingConfiguration.Builder().buildClippingProperties();

        private ClippingProperties(ClippingConfiguration.Builder builder) {
            super(builder);
        }
    }

    public static final class DrmConfiguration {
        public final boolean forceDefaultLicenseUri;
        public final ImmutableList<Integer> forcedSessionTrackTypes;

        @Nullable
        private final byte[] keySetId;
        public final ImmutableMap<String, String> licenseRequestHeaders;

        @Nullable
        public final Uri licenseUri;
        public final boolean multiSession;
        public final boolean playClearContentWithoutKey;

        @Deprecated
        public final ImmutableMap<String, String> requestHeaders;
        public final UUID scheme;

        @Deprecated
        public final ImmutableList<Integer> sessionForClearTypes;

        @Deprecated
        public final UUID uuid;

        public static final class Builder {
            private boolean forceDefaultLicenseUri;
            private ImmutableList<Integer> forcedSessionTrackTypes;

            @Nullable
            private byte[] keySetId;
            private ImmutableMap<String, String> licenseRequestHeaders;

            @Nullable
            private Uri licenseUri;
            private boolean multiSession;
            private boolean playClearContentWithoutKey;

            @Nullable
            private UUID scheme;

            /* JADX INFO: Access modifiers changed from: private */
            @Deprecated
            public Builder setNullableScheme(@Nullable UUID uuid) {
                this.scheme = uuid;
                return this;
            }

            public DrmConfiguration build() {
                return new DrmConfiguration(this);
            }

            public Builder forceSessionsForAudioAndVideoTracks(boolean z2) {
                setForcedSessionTrackTypes(z2 ? ImmutableList.of(2, 1) : ImmutableList.of());
                return this;
            }

            public Builder setForceDefaultLicenseUri(boolean z2) {
                this.forceDefaultLicenseUri = z2;
                return this;
            }

            public Builder setForcedSessionTrackTypes(List<Integer> list) {
                this.forcedSessionTrackTypes = ImmutableList.copyOf((Collection) list);
                return this;
            }

            public Builder setKeySetId(@Nullable byte[] bArr) {
                this.keySetId = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
                return this;
            }

            public Builder setLicenseRequestHeaders(Map<String, String> map) {
                this.licenseRequestHeaders = ImmutableMap.copyOf((Map) map);
                return this;
            }

            public Builder setLicenseUri(@Nullable Uri uri) {
                this.licenseUri = uri;
                return this;
            }

            public Builder setMultiSession(boolean z2) {
                this.multiSession = z2;
                return this;
            }

            public Builder setPlayClearContentWithoutKey(boolean z2) {
                this.playClearContentWithoutKey = z2;
                return this;
            }

            public Builder setScheme(UUID uuid) {
                this.scheme = uuid;
                return this;
            }

            public Builder setLicenseUri(@Nullable String str) {
                this.licenseUri = str == null ? null : Uri.parse(str);
                return this;
            }

            public Builder(UUID uuid) {
                this.scheme = uuid;
                this.licenseRequestHeaders = ImmutableMap.of();
                this.forcedSessionTrackTypes = ImmutableList.of();
            }

            @Deprecated
            private Builder() {
                this.licenseRequestHeaders = ImmutableMap.of();
                this.forcedSessionTrackTypes = ImmutableList.of();
            }

            private Builder(DrmConfiguration drmConfiguration) {
                this.scheme = drmConfiguration.scheme;
                this.licenseUri = drmConfiguration.licenseUri;
                this.licenseRequestHeaders = drmConfiguration.licenseRequestHeaders;
                this.multiSession = drmConfiguration.multiSession;
                this.playClearContentWithoutKey = drmConfiguration.playClearContentWithoutKey;
                this.forceDefaultLicenseUri = drmConfiguration.forceDefaultLicenseUri;
                this.forcedSessionTrackTypes = drmConfiguration.forcedSessionTrackTypes;
                this.keySetId = drmConfiguration.keySetId;
            }
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DrmConfiguration)) {
                return false;
            }
            DrmConfiguration drmConfiguration = (DrmConfiguration) obj;
            return this.scheme.equals(drmConfiguration.scheme) && Util.areEqual(this.licenseUri, drmConfiguration.licenseUri) && Util.areEqual(this.licenseRequestHeaders, drmConfiguration.licenseRequestHeaders) && this.multiSession == drmConfiguration.multiSession && this.forceDefaultLicenseUri == drmConfiguration.forceDefaultLicenseUri && this.playClearContentWithoutKey == drmConfiguration.playClearContentWithoutKey && this.forcedSessionTrackTypes.equals(drmConfiguration.forcedSessionTrackTypes) && Arrays.equals(this.keySetId, drmConfiguration.keySetId);
        }

        @Nullable
        public byte[] getKeySetId() {
            byte[] bArr = this.keySetId;
            if (bArr != null) {
                return Arrays.copyOf(bArr, bArr.length);
            }
            return null;
        }

        public int hashCode() {
            int iHashCode = this.scheme.hashCode() * 31;
            Uri uri = this.licenseUri;
            return ((((((((((((iHashCode + (uri != null ? uri.hashCode() : 0)) * 31) + this.licenseRequestHeaders.hashCode()) * 31) + (this.multiSession ? 1 : 0)) * 31) + (this.forceDefaultLicenseUri ? 1 : 0)) * 31) + (this.playClearContentWithoutKey ? 1 : 0)) * 31) + this.forcedSessionTrackTypes.hashCode()) * 31) + Arrays.hashCode(this.keySetId);
        }

        private DrmConfiguration(Builder builder) {
            Assertions.checkState((builder.forceDefaultLicenseUri && builder.licenseUri == null) ? false : true);
            UUID uuid = (UUID) Assertions.checkNotNull(builder.scheme);
            this.scheme = uuid;
            this.uuid = uuid;
            this.licenseUri = builder.licenseUri;
            this.requestHeaders = builder.licenseRequestHeaders;
            this.licenseRequestHeaders = builder.licenseRequestHeaders;
            this.multiSession = builder.multiSession;
            this.forceDefaultLicenseUri = builder.forceDefaultLicenseUri;
            this.playClearContentWithoutKey = builder.playClearContentWithoutKey;
            this.sessionForClearTypes = builder.forcedSessionTrackTypes;
            this.forcedSessionTrackTypes = builder.forcedSessionTrackTypes;
            this.keySetId = builder.keySetId != null ? Arrays.copyOf(builder.keySetId, builder.keySetId.length) : null;
        }
    }

    public static final class LiveConfiguration implements Bundleable {
        private static final int FIELD_MAX_OFFSET_MS = 2;
        private static final int FIELD_MAX_PLAYBACK_SPEED = 4;
        private static final int FIELD_MIN_OFFSET_MS = 1;
        private static final int FIELD_MIN_PLAYBACK_SPEED = 3;
        private static final int FIELD_TARGET_OFFSET_MS = 0;
        public final long maxOffsetMs;
        public final float maxPlaybackSpeed;
        public final long minOffsetMs;
        public final float minPlaybackSpeed;
        public final long targetOffsetMs;
        public static final LiveConfiguration UNSET = new Builder().build();
        public static final Bundleable.Creator<LiveConfiguration> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.o1
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return MediaItem.LiveConfiguration.lambda$static$0(bundle);
            }
        };

        public static final class Builder {
            private long maxOffsetMs;
            private float maxPlaybackSpeed;
            private long minOffsetMs;
            private float minPlaybackSpeed;
            private long targetOffsetMs;

            public LiveConfiguration build() {
                return new LiveConfiguration(this);
            }

            public Builder setMaxOffsetMs(long j2) {
                this.maxOffsetMs = j2;
                return this;
            }

            public Builder setMaxPlaybackSpeed(float f2) {
                this.maxPlaybackSpeed = f2;
                return this;
            }

            public Builder setMinOffsetMs(long j2) {
                this.minOffsetMs = j2;
                return this;
            }

            public Builder setMinPlaybackSpeed(float f2) {
                this.minPlaybackSpeed = f2;
                return this;
            }

            public Builder setTargetOffsetMs(long j2) {
                this.targetOffsetMs = j2;
                return this;
            }

            public Builder() {
                this.targetOffsetMs = C.TIME_UNSET;
                this.minOffsetMs = C.TIME_UNSET;
                this.maxOffsetMs = C.TIME_UNSET;
                this.minPlaybackSpeed = -3.4028235E38f;
                this.maxPlaybackSpeed = -3.4028235E38f;
            }

            private Builder(LiveConfiguration liveConfiguration) {
                this.targetOffsetMs = liveConfiguration.targetOffsetMs;
                this.minOffsetMs = liveConfiguration.minOffsetMs;
                this.maxOffsetMs = liveConfiguration.maxOffsetMs;
                this.minPlaybackSpeed = liveConfiguration.minPlaybackSpeed;
                this.maxPlaybackSpeed = liveConfiguration.maxPlaybackSpeed;
            }
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ LiveConfiguration lambda$static$0(Bundle bundle) {
            return new LiveConfiguration(bundle.getLong(keyForField(0), C.TIME_UNSET), bundle.getLong(keyForField(1), C.TIME_UNSET), bundle.getLong(keyForField(2), C.TIME_UNSET), bundle.getFloat(keyForField(3), -3.4028235E38f), bundle.getFloat(keyForField(4), -3.4028235E38f));
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LiveConfiguration)) {
                return false;
            }
            LiveConfiguration liveConfiguration = (LiveConfiguration) obj;
            return this.targetOffsetMs == liveConfiguration.targetOffsetMs && this.minOffsetMs == liveConfiguration.minOffsetMs && this.maxOffsetMs == liveConfiguration.maxOffsetMs && this.minPlaybackSpeed == liveConfiguration.minPlaybackSpeed && this.maxPlaybackSpeed == liveConfiguration.maxPlaybackSpeed;
        }

        public int hashCode() {
            long j2 = this.targetOffsetMs;
            long j3 = this.minOffsetMs;
            int i2 = ((((int) (j2 ^ (j2 >>> 32))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31;
            long j4 = this.maxOffsetMs;
            int i3 = (i2 + ((int) ((j4 >>> 32) ^ j4))) * 31;
            float f2 = this.minPlaybackSpeed;
            int iFloatToIntBits = (i3 + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
            float f3 = this.maxPlaybackSpeed;
            return iFloatToIntBits + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0);
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(keyForField(0), this.targetOffsetMs);
            bundle.putLong(keyForField(1), this.minOffsetMs);
            bundle.putLong(keyForField(2), this.maxOffsetMs);
            bundle.putFloat(keyForField(3), this.minPlaybackSpeed);
            bundle.putFloat(keyForField(4), this.maxPlaybackSpeed);
            return bundle;
        }

        private LiveConfiguration(Builder builder) {
            this(builder.targetOffsetMs, builder.minOffsetMs, builder.maxOffsetMs, builder.minPlaybackSpeed, builder.maxPlaybackSpeed);
        }

        @Deprecated
        public LiveConfiguration(long j2, long j3, long j4, float f2, float f3) {
            this.targetOffsetMs = j2;
            this.minOffsetMs = j3;
            this.maxOffsetMs = j4;
            this.minPlaybackSpeed = f2;
            this.maxPlaybackSpeed = f3;
        }
    }

    public static class LocalConfiguration {

        @Nullable
        public final AdsConfiguration adsConfiguration;

        @Nullable
        public final String customCacheKey;

        @Nullable
        public final DrmConfiguration drmConfiguration;

        @Nullable
        public final String mimeType;
        public final List<StreamKey> streamKeys;
        public final ImmutableList<SubtitleConfiguration> subtitleConfigurations;

        @Deprecated
        public final List<Subtitle> subtitles;

        @Nullable
        public final Object tag;
        public final Uri uri;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LocalConfiguration)) {
                return false;
            }
            LocalConfiguration localConfiguration = (LocalConfiguration) obj;
            return this.uri.equals(localConfiguration.uri) && Util.areEqual(this.mimeType, localConfiguration.mimeType) && Util.areEqual(this.drmConfiguration, localConfiguration.drmConfiguration) && Util.areEqual(this.adsConfiguration, localConfiguration.adsConfiguration) && this.streamKeys.equals(localConfiguration.streamKeys) && Util.areEqual(this.customCacheKey, localConfiguration.customCacheKey) && this.subtitleConfigurations.equals(localConfiguration.subtitleConfigurations) && Util.areEqual(this.tag, localConfiguration.tag);
        }

        public int hashCode() {
            int iHashCode = this.uri.hashCode() * 31;
            String str = this.mimeType;
            int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
            DrmConfiguration drmConfiguration = this.drmConfiguration;
            int iHashCode3 = (iHashCode2 + (drmConfiguration == null ? 0 : drmConfiguration.hashCode())) * 31;
            AdsConfiguration adsConfiguration = this.adsConfiguration;
            int iHashCode4 = (((iHashCode3 + (adsConfiguration == null ? 0 : adsConfiguration.hashCode())) * 31) + this.streamKeys.hashCode()) * 31;
            String str2 = this.customCacheKey;
            int iHashCode5 = (((iHashCode4 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.subtitleConfigurations.hashCode()) * 31;
            Object obj = this.tag;
            return iHashCode5 + (obj != null ? obj.hashCode() : 0);
        }

        private LocalConfiguration(Uri uri, @Nullable String str, @Nullable DrmConfiguration drmConfiguration, @Nullable AdsConfiguration adsConfiguration, List<StreamKey> list, @Nullable String str2, ImmutableList<SubtitleConfiguration> immutableList, @Nullable Object obj) {
            this.uri = uri;
            this.mimeType = str;
            this.drmConfiguration = drmConfiguration;
            this.adsConfiguration = adsConfiguration;
            this.streamKeys = list;
            this.customCacheKey = str2;
            this.subtitleConfigurations = immutableList;
            ImmutableList.Builder builder = ImmutableList.builder();
            for (int i2 = 0; i2 < immutableList.size(); i2++) {
                builder.add((ImmutableList.Builder) immutableList.get(i2).buildUpon().buildSubtitle());
            }
            this.subtitles = builder.build();
            this.tag = obj;
        }
    }

    @Deprecated
    public static final class PlaybackProperties extends LocalConfiguration {
        private PlaybackProperties(Uri uri, @Nullable String str, @Nullable DrmConfiguration drmConfiguration, @Nullable AdsConfiguration adsConfiguration, List<StreamKey> list, @Nullable String str2, ImmutableList<SubtitleConfiguration> immutableList, @Nullable Object obj) {
            super(uri, str, drmConfiguration, adsConfiguration, list, str2, immutableList, obj);
        }
    }

    @Deprecated
    public static final class Subtitle extends SubtitleConfiguration {
        @Deprecated
        public Subtitle(Uri uri, String str, @Nullable String str2) {
            this(uri, str, str2, 0);
        }

        @Deprecated
        public Subtitle(Uri uri, String str, @Nullable String str2, int i2) {
            this(uri, str, str2, i2, 0, null);
        }

        @Deprecated
        public Subtitle(Uri uri, String str, @Nullable String str2, int i2, int i3, @Nullable String str3) {
            super(uri, str, str2, i2, i3, str3);
        }

        private Subtitle(SubtitleConfiguration.Builder builder) {
            super(builder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static MediaItem fromBundle(Bundle bundle) {
        String str = (String) Assertions.checkNotNull(bundle.getString(keyForField(0), ""));
        Bundle bundle2 = bundle.getBundle(keyForField(1));
        LiveConfiguration liveConfiguration = bundle2 == null ? LiveConfiguration.UNSET : (LiveConfiguration) LiveConfiguration.CREATOR.fromBundle(bundle2);
        Bundle bundle3 = bundle.getBundle(keyForField(2));
        MediaMetadata mediaMetadata = bundle3 == null ? MediaMetadata.EMPTY : (MediaMetadata) MediaMetadata.CREATOR.fromBundle(bundle3);
        Bundle bundle4 = bundle.getBundle(keyForField(3));
        return new MediaItem(str, bundle4 == null ? ClippingProperties.UNSET : (ClippingProperties) ClippingConfiguration.CREATOR.fromBundle(bundle4), null, liveConfiguration, mediaMetadata);
    }

    public static MediaItem fromUri(String str) {
        return new Builder().setUri(str).build();
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    public Builder buildUpon() {
        return new Builder();
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) obj;
        return Util.areEqual(this.mediaId, mediaItem.mediaId) && this.clippingConfiguration.equals(mediaItem.clippingConfiguration) && Util.areEqual(this.localConfiguration, mediaItem.localConfiguration) && Util.areEqual(this.liveConfiguration, mediaItem.liveConfiguration) && Util.areEqual(this.mediaMetadata, mediaItem.mediaMetadata);
    }

    public int hashCode() {
        int iHashCode = this.mediaId.hashCode() * 31;
        LocalConfiguration localConfiguration = this.localConfiguration;
        return ((((((iHashCode + (localConfiguration != null ? localConfiguration.hashCode() : 0)) * 31) + this.liveConfiguration.hashCode()) * 31) + this.clippingConfiguration.hashCode()) * 31) + this.mediaMetadata.hashCode();
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(keyForField(0), this.mediaId);
        bundle.putBundle(keyForField(1), this.liveConfiguration.toBundle());
        bundle.putBundle(keyForField(2), this.mediaMetadata.toBundle());
        bundle.putBundle(keyForField(3), this.clippingConfiguration.toBundle());
        return bundle;
    }

    public static class SubtitleConfiguration {

        @Nullable
        public final String label;

        @Nullable
        public final String language;

        @Nullable
        public final String mimeType;
        public final int roleFlags;
        public final int selectionFlags;
        public final Uri uri;

        public static final class Builder {

            @Nullable
            private String label;

            @Nullable
            private String language;

            @Nullable
            private String mimeType;
            private int roleFlags;
            private int selectionFlags;
            private Uri uri;

            /* JADX INFO: Access modifiers changed from: private */
            public Subtitle buildSubtitle() {
                return new Subtitle(this);
            }

            public SubtitleConfiguration build() {
                return new SubtitleConfiguration(this);
            }

            public Builder setLabel(@Nullable String str) {
                this.label = str;
                return this;
            }

            public Builder setLanguage(@Nullable String str) {
                this.language = str;
                return this;
            }

            public Builder setMimeType(String str) {
                this.mimeType = str;
                return this;
            }

            public Builder setRoleFlags(int i2) {
                this.roleFlags = i2;
                return this;
            }

            public Builder setSelectionFlags(int i2) {
                this.selectionFlags = i2;
                return this;
            }

            public Builder setUri(Uri uri) {
                this.uri = uri;
                return this;
            }

            public Builder(Uri uri) {
                this.uri = uri;
            }

            private Builder(SubtitleConfiguration subtitleConfiguration) {
                this.uri = subtitleConfiguration.uri;
                this.mimeType = subtitleConfiguration.mimeType;
                this.language = subtitleConfiguration.language;
                this.selectionFlags = subtitleConfiguration.selectionFlags;
                this.roleFlags = subtitleConfiguration.roleFlags;
                this.label = subtitleConfiguration.label;
            }
        }

        public Builder buildUpon() {
            return new Builder();
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SubtitleConfiguration)) {
                return false;
            }
            SubtitleConfiguration subtitleConfiguration = (SubtitleConfiguration) obj;
            return this.uri.equals(subtitleConfiguration.uri) && Util.areEqual(this.mimeType, subtitleConfiguration.mimeType) && Util.areEqual(this.language, subtitleConfiguration.language) && this.selectionFlags == subtitleConfiguration.selectionFlags && this.roleFlags == subtitleConfiguration.roleFlags && Util.areEqual(this.label, subtitleConfiguration.label);
        }

        public int hashCode() {
            int iHashCode = this.uri.hashCode() * 31;
            String str = this.mimeType;
            int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.language;
            int iHashCode3 = (((((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.selectionFlags) * 31) + this.roleFlags) * 31;
            String str3 = this.label;
            return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
        }

        private SubtitleConfiguration(Uri uri, String str, @Nullable String str2, int i2, int i3, @Nullable String str3) {
            this.uri = uri;
            this.mimeType = str;
            this.language = str2;
            this.selectionFlags = i2;
            this.roleFlags = i3;
            this.label = str3;
        }

        private SubtitleConfiguration(Builder builder) {
            this.uri = builder.uri;
            this.mimeType = builder.mimeType;
            this.language = builder.language;
            this.selectionFlags = builder.selectionFlags;
            this.roleFlags = builder.roleFlags;
            this.label = builder.label;
        }
    }

    private MediaItem(String str, ClippingProperties clippingProperties, @Nullable PlaybackProperties playbackProperties, LiveConfiguration liveConfiguration, MediaMetadata mediaMetadata) {
        this.mediaId = str;
        this.localConfiguration = playbackProperties;
        this.playbackProperties = playbackProperties;
        this.liveConfiguration = liveConfiguration;
        this.mediaMetadata = mediaMetadata;
        this.clippingConfiguration = clippingProperties;
        this.clippingProperties = clippingProperties;
    }

    public static MediaItem fromUri(Uri uri) {
        return new Builder().setUri(uri).build();
    }
}
