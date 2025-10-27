package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.server.response.FastJsonResponse;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@VisibleForTesting
@KeepForSdk
@SafeParcelable.Class(creator = "SafeParcelResponseCreator")
/* loaded from: classes3.dex */
public class SafeParcelResponse extends FastSafeParcelableJsonResponse {

    @KeepForSdk
    public static final Parcelable.Creator<SafeParcelResponse> CREATOR = new zap();
    private final String mClassName;

    @SafeParcelable.VersionField(getter = "getVersionCode", id = 1)
    private final int zali;

    @SafeParcelable.Field(getter = "getFieldMappingDictionary", id = 3)
    private final zaj zaqn;

    @SafeParcelable.Field(getter = "getParcel", id = 2)
    private final Parcel zarp;
    private final int zarq;
    private int zarr;
    private int zars;

    public SafeParcelResponse(zaj zajVar, String str) {
        this.zali = 1;
        this.zarp = Parcel.obtain();
        this.zarq = 0;
        this.zaqn = (zaj) Preconditions.checkNotNull(zajVar);
        this.mClassName = (String) Preconditions.checkNotNull(str);
        this.zarr = 0;
    }

    @KeepForSdk
    public static <T extends FastJsonResponse & SafeParcelable> SafeParcelResponse from(T t2) {
        String canonicalName = t2.getClass().getCanonicalName();
        zaj zajVar = new zaj(t2.getClass());
        zaa(zajVar, t2);
        zajVar.zacq();
        zajVar.zacp();
        return new SafeParcelResponse(t2, zajVar, canonicalName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void zaa(zaj zajVar, FastJsonResponse fastJsonResponse) {
        Class<?> cls = fastJsonResponse.getClass();
        if (zajVar.zaa(cls)) {
            return;
        }
        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = fastJsonResponse.getFieldMappings();
        zajVar.zaa(cls, fieldMappings);
        Iterator<String> it = fieldMappings.keySet().iterator();
        while (it.hasNext()) {
            FastJsonResponse.Field<?, ?> field = fieldMappings.get(it.next());
            Class<? extends FastJsonResponse> cls2 = field.zaql;
            if (cls2 != null) {
                try {
                    zaa(zajVar, cls2.newInstance());
                } catch (IllegalAccessException e2) {
                    String strValueOf = String.valueOf(field.zaql.getCanonicalName());
                    throw new IllegalStateException(strValueOf.length() != 0 ? "Could not access object of type ".concat(strValueOf) : new String("Could not access object of type "), e2);
                } catch (InstantiationException e3) {
                    String strValueOf2 = String.valueOf(field.zaql.getCanonicalName());
                    throw new IllegalStateException(strValueOf2.length() != 0 ? "Could not instantiate an object of type ".concat(strValueOf2) : new String("Could not instantiate an object of type "), e3);
                }
            }
        }
    }

    private final void zab(FastJsonResponse.Field<?, ?> field) {
        if (!(field.zaqk != -1)) {
            throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
        }
        Parcel parcel = this.zarp;
        if (parcel == null) {
            throw new IllegalStateException("Internal Parcel object is null.");
        }
        int i2 = this.zarr;
        if (i2 == 0) {
            this.zars = SafeParcelWriter.beginObjectHeader(parcel);
            this.zarr = 1;
        } else if (i2 != 1) {
            if (i2 == 2) {
                throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
            }
            throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
        }
    }

    private final Parcel zacs() {
        int i2 = this.zarr;
        if (i2 != 0) {
            if (i2 == 1) {
            }
            return this.zarp;
        }
        this.zars = SafeParcelWriter.beginObjectHeader(this.zarp);
        SafeParcelWriter.finishObjectHeader(this.zarp, this.zars);
        this.zarr = 2;
        return this.zarp;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<T> arrayList) {
        zab(field);
        ArrayList arrayList2 = new ArrayList();
        arrayList.size();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            T t2 = arrayList.get(i2);
            i2++;
            arrayList2.add(((SafeParcelResponse) t2).zacs());
        }
        SafeParcelWriter.writeParcelList(this.zarp, field.getSafeParcelableFieldId(), arrayList2, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t2) {
        zab(field);
        SafeParcelWriter.writeParcel(this.zarp, field.getSafeParcelableFieldId(), ((SafeParcelResponse) t2).zacs(), true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
        zaj zajVar = this.zaqn;
        if (zajVar == null) {
            return null;
        }
        return zajVar.zai(this.mClassName);
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse, com.google.android.gms.common.server.response.FastJsonResponse
    public Object getValueObject(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse, com.google.android.gms.common.server.response.FastJsonResponse
    public boolean isPrimitiveFieldSet(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String str, boolean z2) {
        zab(field);
        SafeParcelWriter.writeBoolean(this.zarp, field.getSafeParcelableFieldId(), z2);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> field, String str, byte[] bArr) {
        zab(field);
        SafeParcelWriter.writeByteArray(this.zarp, field.getSafeParcelableFieldId(), bArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i2) {
        zab(field);
        SafeParcelWriter.writeInt(this.zarp, field.getSafeParcelableFieldId(), i2);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public void setLongInternal(FastJsonResponse.Field<?, ?> field, String str, long j2) {
        zab(field);
        SafeParcelWriter.writeLong(this.zarp, field.getSafeParcelableFieldId(), j2);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
        zab(field);
        SafeParcelWriter.writeString(this.zarp, field.getSafeParcelableFieldId(), str2, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public void setStringMapInternal(FastJsonResponse.Field<?, ?> field, String str, Map<String, String> map) {
        zab(field);
        Bundle bundle = new Bundle();
        for (String str2 : map.keySet()) {
            bundle.putString(str2, map.get(str2));
        }
        SafeParcelWriter.writeBundle(this.zarp, field.getSafeParcelableFieldId(), bundle, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public void setStringsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<String> arrayList) {
        zab(field);
        int size = arrayList.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            strArr[i2] = arrayList.get(i2);
        }
        SafeParcelWriter.writeStringArray(this.zarp, field.getSafeParcelableFieldId(), strArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public String toString() {
        Preconditions.checkNotNull(this.zaqn, "Cannot convert to JSON on client side.");
        Parcel parcelZacs = zacs();
        parcelZacs.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        zaa(sb, this.zaqn.zai(this.mClassName), parcelZacs);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        zaj zajVar;
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        SafeParcelWriter.writeParcel(parcel, 2, zacs(), false);
        int i3 = this.zarq;
        if (i3 == 0) {
            zajVar = null;
        } else {
            if (i3 != 1 && i3 != 2) {
                int i4 = this.zarq;
                StringBuilder sb = new StringBuilder(34);
                sb.append("Invalid creation type: ");
                sb.append(i4);
                throw new IllegalStateException(sb.toString());
            }
            zajVar = this.zaqn;
        }
        SafeParcelWriter.writeParcelable(parcel, 3, zajVar, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zac(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        zab(field);
        int size = arrayList.size();
        long[] jArr = new long[size];
        for (int i2 = 0; i2 < size; i2++) {
            jArr[i2] = arrayList.get(i2).longValue();
        }
        SafeParcelWriter.writeLongArray(this.zarp, field.getSafeParcelableFieldId(), jArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zad(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        zab(field);
        int size = arrayList.size();
        float[] fArr = new float[size];
        for (int i2 = 0; i2 < size; i2++) {
            fArr[i2] = arrayList.get(i2).floatValue();
        }
        SafeParcelWriter.writeFloatArray(this.zarp, field.getSafeParcelableFieldId(), fArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zae(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        zab(field);
        int size = arrayList.size();
        double[] dArr = new double[size];
        for (int i2 = 0; i2 < size; i2++) {
            dArr[i2] = arrayList.get(i2).doubleValue();
        }
        SafeParcelWriter.writeDoubleArray(this.zarp, field.getSafeParcelableFieldId(), dArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zaf(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        zab(field);
        int size = arrayList.size();
        BigDecimal[] bigDecimalArr = new BigDecimal[size];
        for (int i2 = 0; i2 < size; i2++) {
            bigDecimalArr[i2] = arrayList.get(i2);
        }
        SafeParcelWriter.writeBigDecimalArray(this.zarp, field.getSafeParcelableFieldId(), bigDecimalArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zag(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        zab(field);
        int size = arrayList.size();
        boolean[] zArr = new boolean[size];
        for (int i2 = 0; i2 < size; i2++) {
            zArr[i2] = arrayList.get(i2).booleanValue();
        }
        SafeParcelWriter.writeBooleanArray(this.zarp, field.getSafeParcelableFieldId(), zArr, true);
    }

    private SafeParcelResponse(SafeParcelable safeParcelable, zaj zajVar, String str) {
        this.zali = 1;
        Parcel parcelObtain = Parcel.obtain();
        this.zarp = parcelObtain;
        safeParcelable.writeToParcel(parcelObtain, 0);
        this.zarq = 1;
        this.zaqn = (zaj) Preconditions.checkNotNull(zajVar);
        this.mClassName = (String) Preconditions.checkNotNull(str);
        this.zarr = 2;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zab(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        zab(field);
        int size = arrayList.size();
        BigInteger[] bigIntegerArr = new BigInteger[size];
        for (int i2 = 0; i2 < size; i2++) {
            bigIntegerArr[i2] = arrayList.get(i2);
        }
        SafeParcelWriter.writeBigIntegerArray(this.zarp, field.getSafeParcelableFieldId(), bigIntegerArr, true);
    }

    @SafeParcelable.Constructor
    public SafeParcelResponse(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) Parcel parcel, @SafeParcelable.Param(id = 3) zaj zajVar) {
        this.zali = i2;
        this.zarp = (Parcel) Preconditions.checkNotNull(parcel);
        this.zarq = 2;
        this.zaqn = zajVar;
        if (zajVar == null) {
            this.mClassName = null;
        } else {
            this.mClassName = zajVar.zacr();
        }
        this.zarr = 2;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zaa(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        zab(field);
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = arrayList.get(i2).intValue();
        }
        SafeParcelWriter.writeIntArray(this.zarp, field.getSafeParcelableFieldId(), iArr, true);
    }

    private final void zab(StringBuilder sb, FastJsonResponse.Field<?, ?> field, Object obj) {
        if (field.zaqg) {
            ArrayList arrayList = (ArrayList) obj;
            sb.append(StrPool.BRACKET_START);
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (i2 != 0) {
                    sb.append(",");
                }
                zaa(sb, field.zaqf, arrayList.get(i2));
            }
            sb.append(StrPool.BRACKET_END);
            return;
        }
        zaa(sb, field.zaqf, obj);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zaa(FastJsonResponse.Field<?, ?> field, String str, BigInteger bigInteger) {
        zab(field);
        SafeParcelWriter.writeBigInteger(this.zarp, field.getSafeParcelableFieldId(), bigInteger, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zaa(FastJsonResponse.Field<?, ?> field, String str, float f2) {
        zab(field);
        SafeParcelWriter.writeFloat(this.zarp, field.getSafeParcelableFieldId(), f2);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zaa(FastJsonResponse.Field<?, ?> field, String str, double d3) {
        zab(field);
        SafeParcelWriter.writeDouble(this.zarp, field.getSafeParcelableFieldId(), d3);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public final void zaa(FastJsonResponse.Field<?, ?> field, String str, BigDecimal bigDecimal) {
        zab(field);
        SafeParcelWriter.writeBigDecimal(this.zarp, field.getSafeParcelableFieldId(), bigDecimal, true);
    }

    private final void zaa(StringBuilder sb, Map<String, FastJsonResponse.Field<?, ?>> map, Parcel parcel) {
        SparseArray sparseArray = new SparseArray();
        for (Map.Entry<String, FastJsonResponse.Field<?, ?>> entry : map.entrySet()) {
            sparseArray.put(entry.getValue().getSafeParcelableFieldId(), entry);
        }
        sb.append('{');
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z2 = false;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            Map.Entry entry2 = (Map.Entry) sparseArray.get(SafeParcelReader.getFieldId(header));
            if (entry2 != null) {
                if (z2) {
                    sb.append(",");
                }
                String str = (String) entry2.getKey();
                FastJsonResponse.Field<?, ?> field = (FastJsonResponse.Field) entry2.getValue();
                sb.append("\"");
                sb.append(str);
                sb.append("\":");
                if (field.zacl()) {
                    switch (field.zaqh) {
                        case 0:
                            zab(sb, field, FastJsonResponse.zab(field, Integer.valueOf(SafeParcelReader.readInt(parcel, header))));
                            break;
                        case 1:
                            zab(sb, field, FastJsonResponse.zab(field, SafeParcelReader.createBigInteger(parcel, header)));
                            break;
                        case 2:
                            zab(sb, field, FastJsonResponse.zab(field, Long.valueOf(SafeParcelReader.readLong(parcel, header))));
                            break;
                        case 3:
                            zab(sb, field, FastJsonResponse.zab(field, Float.valueOf(SafeParcelReader.readFloat(parcel, header))));
                            break;
                        case 4:
                            zab(sb, field, FastJsonResponse.zab(field, Double.valueOf(SafeParcelReader.readDouble(parcel, header))));
                            break;
                        case 5:
                            zab(sb, field, FastJsonResponse.zab(field, SafeParcelReader.createBigDecimal(parcel, header)));
                            break;
                        case 6:
                            zab(sb, field, FastJsonResponse.zab(field, Boolean.valueOf(SafeParcelReader.readBoolean(parcel, header))));
                            break;
                        case 7:
                            zab(sb, field, FastJsonResponse.zab(field, SafeParcelReader.createString(parcel, header)));
                            break;
                        case 8:
                        case 9:
                            zab(sb, field, FastJsonResponse.zab(field, SafeParcelReader.createByteArray(parcel, header)));
                            break;
                        case 10:
                            Bundle bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
                            HashMap map2 = new HashMap();
                            for (String str2 : bundleCreateBundle.keySet()) {
                                map2.put(str2, bundleCreateBundle.getString(str2));
                            }
                            zab(sb, field, FastJsonResponse.zab(field, map2));
                            break;
                        case 11:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            int i2 = field.zaqh;
                            StringBuilder sb2 = new StringBuilder(36);
                            sb2.append("Unknown field out type = ");
                            sb2.append(i2);
                            throw new IllegalArgumentException(sb2.toString());
                    }
                } else if (field.zaqi) {
                    sb.append(StrPool.BRACKET_START);
                    switch (field.zaqh) {
                        case 0:
                            ArrayUtils.writeArray(sb, SafeParcelReader.createIntArray(parcel, header));
                            break;
                        case 1:
                            ArrayUtils.writeArray(sb, SafeParcelReader.createBigIntegerArray(parcel, header));
                            break;
                        case 2:
                            ArrayUtils.writeArray(sb, SafeParcelReader.createLongArray(parcel, header));
                            break;
                        case 3:
                            ArrayUtils.writeArray(sb, SafeParcelReader.createFloatArray(parcel, header));
                            break;
                        case 4:
                            ArrayUtils.writeArray(sb, SafeParcelReader.createDoubleArray(parcel, header));
                            break;
                        case 5:
                            ArrayUtils.writeArray(sb, SafeParcelReader.createBigDecimalArray(parcel, header));
                            break;
                        case 6:
                            ArrayUtils.writeArray(sb, SafeParcelReader.createBooleanArray(parcel, header));
                            break;
                        case 7:
                            ArrayUtils.writeStringArray(sb, SafeParcelReader.createStringArray(parcel, header));
                            break;
                        case 8:
                        case 9:
                        case 10:
                            throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                        case 11:
                            Parcel[] parcelArrCreateParcelArray = SafeParcelReader.createParcelArray(parcel, header);
                            int length = parcelArrCreateParcelArray.length;
                            for (int i3 = 0; i3 < length; i3++) {
                                if (i3 > 0) {
                                    sb.append(",");
                                }
                                parcelArrCreateParcelArray[i3].setDataPosition(0);
                                zaa(sb, field.zaco(), parcelArrCreateParcelArray[i3]);
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out.");
                    }
                    sb.append(StrPool.BRACKET_END);
                } else {
                    switch (field.zaqh) {
                        case 0:
                            sb.append(SafeParcelReader.readInt(parcel, header));
                            break;
                        case 1:
                            sb.append(SafeParcelReader.createBigInteger(parcel, header));
                            break;
                        case 2:
                            sb.append(SafeParcelReader.readLong(parcel, header));
                            break;
                        case 3:
                            sb.append(SafeParcelReader.readFloat(parcel, header));
                            break;
                        case 4:
                            sb.append(SafeParcelReader.readDouble(parcel, header));
                            break;
                        case 5:
                            sb.append(SafeParcelReader.createBigDecimal(parcel, header));
                            break;
                        case 6:
                            sb.append(SafeParcelReader.readBoolean(parcel, header));
                            break;
                        case 7:
                            String strCreateString = SafeParcelReader.createString(parcel, header);
                            sb.append("\"");
                            sb.append(JsonUtils.escapeString(strCreateString));
                            sb.append("\"");
                            break;
                        case 8:
                            byte[] bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
                            sb.append("\"");
                            sb.append(Base64Utils.encode(bArrCreateByteArray));
                            sb.append("\"");
                            break;
                        case 9:
                            byte[] bArrCreateByteArray2 = SafeParcelReader.createByteArray(parcel, header);
                            sb.append("\"");
                            sb.append(Base64Utils.encodeUrlSafe(bArrCreateByteArray2));
                            sb.append("\"");
                            break;
                        case 10:
                            Bundle bundleCreateBundle2 = SafeParcelReader.createBundle(parcel, header);
                            Set<String> setKeySet = bundleCreateBundle2.keySet();
                            setKeySet.size();
                            sb.append(StrPool.DELIM_START);
                            boolean z3 = true;
                            for (String str3 : setKeySet) {
                                if (!z3) {
                                    sb.append(",");
                                }
                                sb.append("\"");
                                sb.append(str3);
                                sb.append("\"");
                                sb.append(":");
                                sb.append("\"");
                                sb.append(JsonUtils.escapeString(bundleCreateBundle2.getString(str3)));
                                sb.append("\"");
                                z3 = false;
                            }
                            sb.append("}");
                            break;
                        case 11:
                            Parcel parcelCreateParcel = SafeParcelReader.createParcel(parcel, header);
                            parcelCreateParcel.setDataPosition(0);
                            zaa(sb, field.zaco(), parcelCreateParcel);
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out");
                    }
                }
                z2 = true;
            }
        }
        if (parcel.dataPosition() == iValidateObjectHeader) {
            sb.append('}');
            return;
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("Overread allowed size end=");
        sb3.append(iValidateObjectHeader);
        throw new SafeParcelReader.ParseException(sb3.toString(), parcel);
    }

    private static void zaa(StringBuilder sb, int i2, Object obj) {
        switch (i2) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"");
                sb.append(JsonUtils.escapeString(obj.toString()));
                sb.append("\"");
                return;
            case 8:
                sb.append("\"");
                sb.append(Base64Utils.encode((byte[]) obj));
                sb.append("\"");
                return;
            case 9:
                sb.append("\"");
                sb.append(Base64Utils.encodeUrlSafe((byte[]) obj));
                sb.append("\"");
                return;
            case 10:
                MapUtils.writeStringMapToJson(sb, (HashMap) obj);
                return;
            case 11:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                StringBuilder sb2 = new StringBuilder(26);
                sb2.append("Unknown type = ");
                sb2.append(i2);
                throw new IllegalArgumentException(sb2.toString());
        }
    }
}
