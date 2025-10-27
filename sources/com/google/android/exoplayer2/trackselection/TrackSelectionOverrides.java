package com.google.android.exoplayer2.trackselection;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class TrackSelectionOverrides implements Bundleable {
    private static final int FIELD_OVERRIDES = 0;
    private final ImmutableMap<TrackGroup, TrackSelectionOverride> overrides;
    public static final TrackSelectionOverrides EMPTY = new TrackSelectionOverrides(ImmutableMap.of());
    public static final Bundleable.Creator<TrackSelectionOverrides> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.trackselection.h
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return TrackSelectionOverrides.lambda$static$0(bundle);
        }
    };

    public static final class Builder {
        private final HashMap<TrackGroup, TrackSelectionOverride> overrides;

        public Builder addOverride(TrackSelectionOverride trackSelectionOverride) {
            this.overrides.put(trackSelectionOverride.trackGroup, trackSelectionOverride);
            return this;
        }

        public TrackSelectionOverrides build() {
            return new TrackSelectionOverrides(this.overrides);
        }

        public Builder clearOverride(TrackGroup trackGroup) {
            this.overrides.remove(trackGroup);
            return this;
        }

        public Builder clearOverridesOfType(int i2) {
            Iterator<TrackSelectionOverride> it = this.overrides.values().iterator();
            while (it.hasNext()) {
                if (it.next().getTrackType() == i2) {
                    it.remove();
                }
            }
            return this;
        }

        public Builder setOverrideForType(TrackSelectionOverride trackSelectionOverride) {
            clearOverridesOfType(trackSelectionOverride.getTrackType());
            this.overrides.put(trackSelectionOverride.trackGroup, trackSelectionOverride);
            return this;
        }

        public Builder() {
            this.overrides = new HashMap<>();
        }

        private Builder(Map<TrackGroup, TrackSelectionOverride> map) {
            this.overrides = new HashMap<>(map);
        }
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TrackSelectionOverrides lambda$static$0(Bundle bundle) {
        List listFromBundleNullableList = BundleableUtil.fromBundleNullableList(TrackSelectionOverride.CREATOR, bundle.getParcelableArrayList(keyForField(0)), ImmutableList.of());
        ImmutableMap.Builder builder = new ImmutableMap.Builder();
        for (int i2 = 0; i2 < listFromBundleNullableList.size(); i2++) {
            TrackSelectionOverride trackSelectionOverride = (TrackSelectionOverride) listFromBundleNullableList.get(i2);
            builder.put(trackSelectionOverride.trackGroup, trackSelectionOverride);
        }
        return new TrackSelectionOverrides(builder.build());
    }

    public ImmutableList<TrackSelectionOverride> asList() {
        return ImmutableList.copyOf((Collection) this.overrides.values());
    }

    public Builder buildUpon() {
        return new Builder(this.overrides);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TrackSelectionOverrides.class != obj.getClass()) {
            return false;
        }
        return this.overrides.equals(((TrackSelectionOverrides) obj).overrides);
    }

    @Nullable
    public TrackSelectionOverride getOverride(TrackGroup trackGroup) {
        return this.overrides.get(trackGroup);
    }

    public int hashCode() {
        return this.overrides.hashCode();
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(keyForField(0), BundleableUtil.toBundleArrayList(this.overrides.values()));
        return bundle;
    }

    private TrackSelectionOverrides(Map<TrackGroup, TrackSelectionOverride> map) {
        this.overrides = ImmutableMap.copyOf((Map) map);
    }

    public static final class TrackSelectionOverride implements Bundleable {
        public static final Bundleable.Creator<TrackSelectionOverride> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.trackselection.i
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return TrackSelectionOverrides.TrackSelectionOverride.lambda$static$0(bundle);
            }
        };
        private static final int FIELD_TRACKS = 1;
        private static final int FIELD_TRACK_GROUP = 0;
        public final TrackGroup trackGroup;
        public final ImmutableList<Integer> trackIndices;

        public TrackSelectionOverride(TrackGroup trackGroup) {
            this.trackGroup = trackGroup;
            ImmutableList.Builder builder = new ImmutableList.Builder();
            for (int i2 = 0; i2 < trackGroup.length; i2++) {
                builder.add((ImmutableList.Builder) Integer.valueOf(i2));
            }
            this.trackIndices = builder.build();
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackSelectionOverride lambda$static$0(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle(keyForField(0));
            Assertions.checkNotNull(bundle2);
            TrackGroup trackGroup = (TrackGroup) TrackGroup.CREATOR.fromBundle(bundle2);
            int[] intArray = bundle.getIntArray(keyForField(1));
            return intArray == null ? new TrackSelectionOverride(trackGroup) : new TrackSelectionOverride(trackGroup, Ints.asList(intArray));
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || TrackSelectionOverride.class != obj.getClass()) {
                return false;
            }
            TrackSelectionOverride trackSelectionOverride = (TrackSelectionOverride) obj;
            return this.trackGroup.equals(trackSelectionOverride.trackGroup) && this.trackIndices.equals(trackSelectionOverride.trackIndices);
        }

        public int getTrackType() {
            return MimeTypes.getTrackType(this.trackGroup.getFormat(0).sampleMimeType);
        }

        public int hashCode() {
            return this.trackGroup.hashCode() + (this.trackIndices.hashCode() * 31);
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBundle(keyForField(0), this.trackGroup.toBundle());
            bundle.putIntArray(keyForField(1), Ints.toArray(this.trackIndices));
            return bundle;
        }

        public TrackSelectionOverride(TrackGroup trackGroup, List<Integer> list) {
            if (!list.isEmpty() && (((Integer) Collections.min(list)).intValue() < 0 || ((Integer) Collections.max(list)).intValue() >= trackGroup.length)) {
                throw new IndexOutOfBoundsException();
            }
            this.trackGroup = trackGroup;
            this.trackIndices = ImmutableList.copyOf((Collection) list);
        }
    }
}
