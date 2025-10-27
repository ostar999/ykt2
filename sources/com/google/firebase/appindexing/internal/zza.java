package com.google.firebase.appindexing.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.appindexing.Action;

@SafeParcelable.Class(creator = "ActionImplCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes4.dex */
public final class zza extends AbstractSafeParcelable implements Action {
    public static final Parcelable.Creator<zza> CREATOR = new zzb();

    @SafeParcelable.Field(getter = "getActionType", id = 1)
    private final String zzar;

    @SafeParcelable.Field(getter = "getPropertyBundle", id = 7)
    private final Bundle zzay;

    @SafeParcelable.Field(getter = "getObjectName", id = 2)
    private final String zzeg;

    @SafeParcelable.Field(getter = "getObjectUrl", id = 3)
    private final String zzeh;

    @SafeParcelable.Field(getter = "getObjectSameAs", id = 4)
    private final String zzei;

    @SafeParcelable.Field(getter = "getMetadata", id = 5)
    private final zzc zzej;

    @SafeParcelable.Field(getter = "getActionStatus", id = 6)
    private final String zzek;

    @SafeParcelable.Constructor
    public zza(@SafeParcelable.Param(id = 1) String str, @SafeParcelable.Param(id = 2) String str2, @SafeParcelable.Param(id = 3) String str3, @SafeParcelable.Param(id = 4) String str4, @SafeParcelable.Param(id = 5) zzc zzcVar, @SafeParcelable.Param(id = 6) String str5, @SafeParcelable.Param(id = 7) Bundle bundle) {
        this.zzar = str;
        this.zzeg = str2;
        this.zzeh = str3;
        this.zzei = str4;
        this.zzej = zzcVar;
        this.zzek = str5;
        if (bundle != null) {
            this.zzay = bundle;
        } else {
            this.zzay = Bundle.EMPTY;
        }
        this.zzay.setClassLoader(zza.class.getClassLoader());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ActionImpl { ");
        sb.append("{ actionType: '");
        sb.append(this.zzar);
        sb.append("' } ");
        sb.append("{ objectName: '");
        sb.append(this.zzeg);
        sb.append("' } ");
        sb.append("{ objectUrl: '");
        sb.append(this.zzeh);
        sb.append("' } ");
        if (this.zzei != null) {
            sb.append("{ objectSameAs: '");
            sb.append(this.zzei);
            sb.append("' } ");
        }
        if (this.zzej != null) {
            sb.append("{ metadata: '");
            sb.append(this.zzej.toString());
            sb.append("' } ");
        }
        if (this.zzek != null) {
            sb.append("{ actionStatus: '");
            sb.append(this.zzek);
            sb.append("' } ");
        }
        if (!this.zzay.isEmpty()) {
            sb.append("{ ");
            sb.append(this.zzay);
            sb.append(" } ");
        }
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzar, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzeg, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzeh, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzei, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzej, i2, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzek, false);
        SafeParcelWriter.writeBundle(parcel, 7, this.zzay, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final zzc zzab() {
        return this.zzej;
    }
}
