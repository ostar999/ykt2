package com.google.android.exoplayer2.source;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.util.BundleableUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class TrackGroupArray implements Bundleable {
    private static final int FIELD_TRACK_GROUPS = 0;
    private int hashCode;
    public final int length;
    private final TrackGroup[] trackGroups;
    public static final TrackGroupArray EMPTY = new TrackGroupArray(new TrackGroup[0]);
    public static final Bundleable.Creator<TrackGroupArray> CREATOR = new Bundleable.Creator() { // from class: com.google.android.exoplayer2.source.j0
        @Override // com.google.android.exoplayer2.Bundleable.Creator
        public final Bundleable fromBundle(Bundle bundle) {
            return TrackGroupArray.lambda$static$0(bundle);
        }
    };

    public TrackGroupArray(TrackGroup... trackGroupArr) {
        this.trackGroups = trackGroupArr;
        this.length = trackGroupArr.length;
    }

    private static String keyForField(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TrackGroupArray lambda$static$0(Bundle bundle) {
        return new TrackGroupArray((TrackGroup[]) BundleableUtil.fromBundleNullableList(TrackGroup.CREATOR, bundle.getParcelableArrayList(keyForField(0)), ImmutableList.of()).toArray(new TrackGroup[0]));
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TrackGroupArray.class != obj.getClass()) {
            return false;
        }
        TrackGroupArray trackGroupArray = (TrackGroupArray) obj;
        return this.length == trackGroupArray.length && Arrays.equals(this.trackGroups, trackGroupArray.trackGroups);
    }

    public TrackGroup get(int i2) {
        return this.trackGroups[i2];
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = Arrays.hashCode(this.trackGroups);
        }
        return this.hashCode;
    }

    public int indexOf(TrackGroup trackGroup) {
        for (int i2 = 0; i2 < this.length; i2++) {
            if (this.trackGroups[i2] == trackGroup) {
                return i2;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override // com.google.android.exoplayer2.Bundleable
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(keyForField(0), BundleableUtil.toBundleArrayList(Lists.newArrayList(this.trackGroups)));
        return bundle;
    }
}
