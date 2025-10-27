package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.util.ArrayList;
import java.util.HashMap;

@KeepForSdk
@SafeParcelable.Class(creator = "StringToIntConverterCreator")
/* loaded from: classes3.dex */
public final class StringToIntConverter extends AbstractSafeParcelable implements FastJsonResponse.FieldConverter<String, Integer> {
    public static final Parcelable.Creator<StringToIntConverter> CREATOR = new zad();

    @SafeParcelable.VersionField(id = 1)
    private final int zali;
    private final HashMap<String, Integer> zaqc;
    private final SparseArray<String> zaqd;

    @SafeParcelable.Field(getter = "getSerializedMap", id = 2)
    private final ArrayList<zaa> zaqe;

    @SafeParcelable.Constructor
    public StringToIntConverter(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) ArrayList<zaa> arrayList) {
        this.zali = i2;
        this.zaqc = new HashMap<>();
        this.zaqd = new SparseArray<>();
        this.zaqe = null;
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            zaa zaaVar = arrayList.get(i3);
            i3++;
            zaa zaaVar2 = zaaVar;
            add(zaaVar2.zaqa, zaaVar2.zaqb);
        }
    }

    @KeepForSdk
    public final StringToIntConverter add(String str, int i2) {
        this.zaqc.put(str, Integer.valueOf(i2));
        this.zaqd.put(i2, str);
        return this;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter
    public final /* synthetic */ Integer convert(String str) {
        Integer num = this.zaqc.get(str);
        return num == null ? this.zaqc.get("gms_unknown") : num;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter
    public final /* synthetic */ String convertBack(Integer num) {
        String str = this.zaqd.get(num.intValue());
        return (str == null && this.zaqc.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        ArrayList arrayList = new ArrayList();
        for (String str : this.zaqc.keySet()) {
            arrayList.add(new zaa(str, this.zaqc.get(str).intValue()));
        }
        SafeParcelWriter.writeTypedList(parcel, 2, arrayList, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter
    public final int zach() {
        return 7;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse.FieldConverter
    public final int zaci() {
        return 0;
    }

    @SafeParcelable.Class(creator = "StringToIntConverterEntryCreator")
    public static final class zaa extends AbstractSafeParcelable {
        public static final Parcelable.Creator<zaa> CREATOR = new zac();

        @SafeParcelable.VersionField(id = 1)
        private final int versionCode;

        @SafeParcelable.Field(id = 2)
        final String zaqa;

        @SafeParcelable.Field(id = 3)
        final int zaqb;

        @SafeParcelable.Constructor
        public zaa(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) int i3) {
            this.versionCode = i2;
            this.zaqa = str;
            this.zaqb = i3;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
            SafeParcelWriter.writeString(parcel, 2, this.zaqa, false);
            SafeParcelWriter.writeInt(parcel, 3, this.zaqb);
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }

        public zaa(String str, int i2) {
            this.versionCode = 1;
            this.zaqa = str;
            this.zaqb = i2;
        }
    }

    @KeepForSdk
    public StringToIntConverter() {
        this.zali = 1;
        this.zaqc = new HashMap<>();
        this.zaqd = new SparseArray<>();
        this.zaqe = null;
    }
}
