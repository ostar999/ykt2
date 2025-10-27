package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.ArrayList;
import java.util.Map;

@ShowFirstParty
@SafeParcelable.Class(creator = "FieldMappingDictionaryEntryCreator")
/* loaded from: classes3.dex */
public final class zam extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zam> CREATOR = new zan();

    @SafeParcelable.Field(id = 2)
    final String className;

    @SafeParcelable.VersionField(id = 1)
    private final int versionCode;

    @SafeParcelable.Field(id = 3)
    final ArrayList<zal> zaro;

    @SafeParcelable.Constructor
    public zam(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) ArrayList<zal> arrayList) {
        this.versionCode = i2;
        this.className = str;
        this.zaro = arrayList;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeString(parcel, 2, this.className, false);
        SafeParcelWriter.writeTypedList(parcel, 3, this.zaro, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zam(String str, Map<String, FastJsonResponse.Field<?, ?>> map) {
        ArrayList<zal> arrayList;
        this.versionCode = 1;
        this.className = str;
        if (map == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList<>();
            for (String str2 : map.keySet()) {
                arrayList.add(new zal(str2, map.get(str2)));
            }
        }
        this.zaro = arrayList;
    }
}
