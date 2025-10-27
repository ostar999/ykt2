package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(creator = "FeatureCreator")
/* loaded from: classes3.dex */
public class Feature extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new zzb();

    @SafeParcelable.Field(getter = "getName", id = 1)
    private final String name;

    @SafeParcelable.Field(getter = "getOldVersion", id = 2)
    @Deprecated
    private final int zzk;

    @SafeParcelable.Field(defaultValue = "-1", getter = "getVersion", id = 3)
    private final long zzl;

    @KeepForSdk
    public Feature(String str, long j2) {
        this.name = str;
        this.zzl = j2;
        this.zzk = -1;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Feature) {
            Feature feature = (Feature) obj;
            if (((getName() != null && getName().equals(feature.getName())) || (getName() == null && feature.getName() == null)) && getVersion() == feature.getVersion()) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public String getName() {
        return this.name;
    }

    @KeepForSdk
    public long getVersion() {
        long j2 = this.zzl;
        return j2 == -1 ? this.zzk : j2;
    }

    public int hashCode() {
        return Objects.hashCode(getName(), Long.valueOf(getVersion()));
    }

    public String toString() {
        return Objects.toStringHelper(this).add("name", getName()).add("version", Long.valueOf(getVersion())).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getName(), false);
        SafeParcelWriter.writeInt(parcel, 2, this.zzk);
        SafeParcelWriter.writeLong(parcel, 3, getVersion());
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @SafeParcelable.Constructor
    public Feature(@SafeParcelable.Param(id = 1) String str, @SafeParcelable.Param(id = 2) int i2, @SafeParcelable.Param(id = 3) long j2) {
        this.name = str;
        this.zzk = i2;
        this.zzl = j2;
    }
}
