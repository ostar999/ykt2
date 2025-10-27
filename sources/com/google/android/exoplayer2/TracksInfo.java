package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Booleans;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/* loaded from: classes3.dex */
public final class TracksInfo implements Bundleable {
    private static final int FIELD_TRACK_GROUP_INFOS = 0;
    private final ImmutableList<TrackGroupInfo> trackGroupInfos;
    public static final TracksInfo EMPTY = new TracksInfo(ImmutableList.of());
    public static final Bundleable.Creator<TracksInfo> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.i2
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return TracksInfo.lambda$static$0(bundle);
        }
    };

    public static final class TrackGroupInfo implements Bundleable {
        public static final Bundleable.Creator<TrackGroupInfo> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.j2
            @Override // com.google.android.exoplayer2.Bundleable.Creator
            public final Bundleable fromBundle(Bundle bundle) {
                return TracksInfo.TrackGroupInfo.lambda$static$0(bundle);
            }
        };
        private static final int FIELD_TRACK_GROUP = 0;
        private static final int FIELD_TRACK_SELECTED = 3;
        private static final int FIELD_TRACK_SUPPORT = 1;
        private static final int FIELD_TRACK_TYPE = 2;
        private final TrackGroup trackGroup;
        private final boolean[] trackSelected;
        private final int[] trackSupport;
        private final int trackType;

        public TrackGroupInfo(TrackGroup trackGroup, int[] iArr, int i2, boolean[] zArr) {
            int i3 = trackGroup.length;
            Assertions.checkArgument(i3 == iArr.length && i3 == zArr.length);
            this.trackGroup = trackGroup;
            this.trackSupport = (int[]) iArr.clone();
            this.trackType = i2;
            this.trackSelected = (boolean[]) zArr.clone();
        }

        private static String keyForField(int i2) {
            return Integer.toString(i2, 36);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ TrackGroupInfo lambda$static$0(Bundle bundle) {
            TrackGroup trackGroup = (TrackGroup) BundleableUtil.fromNullableBundle(TrackGroup.CREATOR, bundle.getBundle(keyForField(0)));
            Assertions.checkNotNull(trackGroup);
            return new TrackGroupInfo(trackGroup, (int[]) MoreObjects.firstNonNull(bundle.getIntArray(keyForField(1)), new int[trackGroup.length]), bundle.getInt(keyForField(2), -1), (boolean[]) MoreObjects.firstNonNull(bundle.getBooleanArray(keyForField(3)), new boolean[trackGroup.length]));
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || TrackGroupInfo.class != obj.getClass()) {
                return false;
            }
            TrackGroupInfo trackGroupInfo = (TrackGroupInfo) obj;
            return this.trackType == trackGroupInfo.trackType && this.trackGroup.equals(trackGroupInfo.trackGroup) && Arrays.equals(this.trackSupport, trackGroupInfo.trackSupport) && Arrays.equals(this.trackSelected, trackGroupInfo.trackSelected);
        }

        public TrackGroup getTrackGroup() {
            return this.trackGroup;
        }

        public int getTrackSupport(int i2) {
            return this.trackSupport[i2];
        }

        public int getTrackType() {
            return this.trackType;
        }

        public int hashCode() {
            return (((((this.trackGroup.hashCode() * 31) + Arrays.hashCode(this.trackSupport)) * 31) + this.trackType) * 31) + Arrays.hashCode(this.trackSelected);
        }

        public boolean isSelected() {
            return Booleans.contains(this.trackSelected, true);
        }

        public boolean isSupported() {
            for (int i2 = 0; i2 < this.trackSupport.length; i2++) {
                if (isTrackSupported(i2)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isTrackSelected(int i2) {
            return this.trackSelected[i2];
        }

        public boolean isTrackSupported(int i2) {
            return this.trackSupport[i2] == 4;
        }

        @Override // com.google.android.exoplayer2.Bundleable
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putBundle(keyForField(0), this.trackGroup.toBundle());
            bundle.putIntArray(keyForField(1), this.trackSupport);
            bundle.putInt(keyForField(2), this.trackType);
            bundle.putBooleanArray(keyForField(3), this.trackSelected);
            return bundle;
        }
    }

    public TracksInfo(List<TrackGroupInfo> list) {
        this.trackGroupInfos = ImmutableList.copyOf((Collection) list);
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TracksInfo lambda$static$0(Bundle bundle) {
        return new TracksInfo(BundleableUtil.fromBundleNullableList(TrackGroupInfo.CREATOR, bundle.getParcelableArrayList(keyForField(0)), ImmutableList.of()));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TracksInfo.class != obj.getClass()) {
            return false;
        }
        return this.trackGroupInfos.equals(((TracksInfo) obj).trackGroupInfos);
    }

    public ImmutableList<TrackGroupInfo> getTrackGroupInfos() {
        return this.trackGroupInfos;
    }

    public int hashCode() {
        return this.trackGroupInfos.hashCode();
    }

    public boolean isTypeSelected(int i2) {
        for (int i3 = 0; i3 < this.trackGroupInfos.size(); i3++) {
            TrackGroupInfo trackGroupInfo = this.trackGroupInfos.get(i3);
            if (trackGroupInfo.isSelected() && trackGroupInfo.getTrackType() == i2) {
                return true;
            }
        }
        return false;
    }

    public boolean isTypeSupportedOrEmpty(int i2) {
        boolean z2 = true;
        for (int i3 = 0; i3 < this.trackGroupInfos.size(); i3++) {
            if (this.trackGroupInfos.get(i3).trackType == i2) {
                if (this.trackGroupInfos.get(i3).isSupported()) {
                    return true;
                }
                z2 = false;
            }
        }
        return z2;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(keyForField(0), BundleableUtil.toBundleArrayList(this.trackGroupInfos));
        return bundle;
    }
}
