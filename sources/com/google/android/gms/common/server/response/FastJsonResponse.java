package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ShowFirstParty
@KeepForSdk
/* loaded from: classes3.dex */
public abstract class FastJsonResponse {

    @VisibleForTesting
    @SafeParcelable.Class(creator = "FieldCreator")
    @ShowFirstParty
    @KeepForSdk
    public static class Field<I, O> extends AbstractSafeParcelable {
        public static final zai CREATOR = new zai();

        @SafeParcelable.VersionField(getter = "getVersionCode", id = 1)
        private final int zali;

        @SafeParcelable.Field(getter = "getTypeIn", id = 2)
        protected final int zaqf;

        @SafeParcelable.Field(getter = "isTypeInArray", id = 3)
        protected final boolean zaqg;

        @SafeParcelable.Field(getter = "getTypeOut", id = 4)
        protected final int zaqh;

        @SafeParcelable.Field(getter = "isTypeOutArray", id = 5)
        protected final boolean zaqi;

        @SafeParcelable.Field(getter = "getOutputFieldName", id = 6)
        protected final String zaqj;

        @SafeParcelable.Field(getter = "getSafeParcelableFieldId", id = 7)
        protected final int zaqk;
        protected final Class<? extends FastJsonResponse> zaql;

        @SafeParcelable.Field(getter = "getConcreteTypeName", id = 8)
        private final String zaqm;
        private zaj zaqn;

        @SafeParcelable.Field(getter = "getWrappedConverter", id = 9, type = "com.google.android.gms.common.server.converter.ConverterWrapper")
        private FieldConverter<I, O> zaqo;

        @SafeParcelable.Constructor
        public Field(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) int i3, @SafeParcelable.Param(id = 3) boolean z2, @SafeParcelable.Param(id = 4) int i4, @SafeParcelable.Param(id = 5) boolean z3, @SafeParcelable.Param(id = 6) String str, @SafeParcelable.Param(id = 7) int i5, @SafeParcelable.Param(id = 8) String str2, @SafeParcelable.Param(id = 9) com.google.android.gms.common.server.converter.zab zabVar) {
            this.zali = i2;
            this.zaqf = i3;
            this.zaqg = z2;
            this.zaqh = i4;
            this.zaqi = z3;
            this.zaqj = str;
            this.zaqk = i5;
            if (str2 == null) {
                this.zaql = null;
                this.zaqm = null;
            } else {
                this.zaql = SafeParcelResponse.class;
                this.zaqm = str2;
            }
            if (zabVar == null) {
                this.zaqo = null;
            } else {
                this.zaqo = (FieldConverter<I, O>) zabVar.zacg();
            }
        }

        @VisibleForTesting
        @KeepForSdk
        public static Field<byte[], byte[]> forBase64(String str, int i2) {
            return new Field<>(8, false, 8, false, str, i2, null, null);
        }

        @KeepForSdk
        public static Field<Boolean, Boolean> forBoolean(String str, int i2) {
            return new Field<>(6, false, 6, false, str, i2, null, null);
        }

        @KeepForSdk
        public static <T extends FastJsonResponse> Field<T, T> forConcreteType(String str, int i2, Class<T> cls) {
            return new Field<>(11, false, 11, false, str, i2, cls, null);
        }

        @KeepForSdk
        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> forConcreteTypeArray(String str, int i2, Class<T> cls) {
            return new Field<>(11, true, 11, true, str, i2, cls, null);
        }

        @KeepForSdk
        public static Field<Double, Double> forDouble(String str, int i2) {
            return new Field<>(4, false, 4, false, str, i2, null, null);
        }

        @KeepForSdk
        public static Field<Float, Float> forFloat(String str, int i2) {
            return new Field<>(3, false, 3, false, str, i2, null, null);
        }

        @VisibleForTesting
        @KeepForSdk
        public static Field<Integer, Integer> forInteger(String str, int i2) {
            return new Field<>(0, false, 0, false, str, i2, null, null);
        }

        @KeepForSdk
        public static Field<Long, Long> forLong(String str, int i2) {
            return new Field<>(2, false, 2, false, str, i2, null, null);
        }

        @KeepForSdk
        public static Field<String, String> forString(String str, int i2) {
            return new Field<>(7, false, 7, false, str, i2, null, null);
        }

        @KeepForSdk
        public static Field<HashMap<String, String>, HashMap<String, String>> forStringMap(String str, int i2) {
            return new Field<>(10, false, 10, false, str, i2, null, null);
        }

        @KeepForSdk
        public static Field<ArrayList<String>, ArrayList<String>> forStrings(String str, int i2) {
            return new Field<>(7, true, 7, true, str, i2, null, null);
        }

        @KeepForSdk
        public static Field withConverter(String str, int i2, FieldConverter<?, ?> fieldConverter, boolean z2) {
            return new Field(fieldConverter.zach(), z2, fieldConverter.zaci(), false, str, i2, null, fieldConverter);
        }

        private final String zack() {
            String str = this.zaqm;
            if (str == null) {
                return null;
            }
            return str;
        }

        private final com.google.android.gms.common.server.converter.zab zacm() {
            FieldConverter<I, O> fieldConverter = this.zaqo;
            if (fieldConverter == null) {
                return null;
            }
            return com.google.android.gms.common.server.converter.zab.zaa(fieldConverter);
        }

        public final O convert(I i2) {
            return this.zaqo.convert(i2);
        }

        public final I convertBack(O o2) {
            return this.zaqo.convertBack(o2);
        }

        @KeepForSdk
        public int getSafeParcelableFieldId() {
            return this.zaqk;
        }

        public String toString() {
            Objects.ToStringHelper toStringHelperAdd = Objects.toStringHelper(this).add("versionCode", Integer.valueOf(this.zali)).add("typeIn", Integer.valueOf(this.zaqf)).add("typeInArray", Boolean.valueOf(this.zaqg)).add("typeOut", Integer.valueOf(this.zaqh)).add("typeOutArray", Boolean.valueOf(this.zaqi)).add("outputFieldName", this.zaqj).add("safeParcelFieldId", Integer.valueOf(this.zaqk)).add("concreteTypeName", zack());
            Class<? extends FastJsonResponse> cls = this.zaql;
            if (cls != null) {
                toStringHelperAdd.add("concreteType.class", cls.getCanonicalName());
            }
            FieldConverter<I, O> fieldConverter = this.zaqo;
            if (fieldConverter != null) {
                toStringHelperAdd.add("converterName", fieldConverter.getClass().getCanonicalName());
            }
            return toStringHelperAdd.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i2) {
            int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
            SafeParcelWriter.writeInt(parcel, 1, this.zali);
            SafeParcelWriter.writeInt(parcel, 2, this.zaqf);
            SafeParcelWriter.writeBoolean(parcel, 3, this.zaqg);
            SafeParcelWriter.writeInt(parcel, 4, this.zaqh);
            SafeParcelWriter.writeBoolean(parcel, 5, this.zaqi);
            SafeParcelWriter.writeString(parcel, 6, this.zaqj, false);
            SafeParcelWriter.writeInt(parcel, 7, getSafeParcelableFieldId());
            SafeParcelWriter.writeString(parcel, 8, zack(), false);
            SafeParcelWriter.writeParcelable(parcel, 9, zacm(), i2, false);
            SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
        }

        public final void zaa(zaj zajVar) {
            this.zaqn = zajVar;
        }

        public final Field<I, O> zacj() {
            return new Field<>(this.zali, this.zaqf, this.zaqg, this.zaqh, this.zaqi, this.zaqj, this.zaqk, this.zaqm, zacm());
        }

        public final boolean zacl() {
            return this.zaqo != null;
        }

        public final FastJsonResponse zacn() throws IllegalAccessException, InstantiationException {
            Class<? extends FastJsonResponse> cls = this.zaql;
            if (cls != SafeParcelResponse.class) {
                return cls.newInstance();
            }
            Preconditions.checkNotNull(this.zaqn, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.zaqn, this.zaqm);
        }

        public final Map<String, Field<?, ?>> zaco() {
            Preconditions.checkNotNull(this.zaqm);
            Preconditions.checkNotNull(this.zaqn);
            return this.zaqn.zai(this.zaqm);
        }

        private Field(int i2, boolean z2, int i3, boolean z3, String str, int i4, Class<? extends FastJsonResponse> cls, FieldConverter<I, O> fieldConverter) {
            this.zali = 1;
            this.zaqf = i2;
            this.zaqg = z2;
            this.zaqh = i3;
            this.zaqi = z3;
            this.zaqj = str;
            this.zaqk = i4;
            this.zaql = cls;
            if (cls == null) {
                this.zaqm = null;
            } else {
                this.zaqm = cls.getCanonicalName();
            }
            this.zaqo = fieldConverter;
        }
    }

    @ShowFirstParty
    public interface FieldConverter<I, O> {
        O convert(I i2);

        I convertBack(O o2);

        int zach();

        int zaci();
    }

    private final <I, O> void zaa(Field<I, O> field, I i2) {
        String str = field.zaqj;
        O oConvert = field.convert(i2);
        switch (field.zaqh) {
            case 0:
                if (zaa(str, oConvert)) {
                    setIntegerInternal(field, str, ((Integer) oConvert).intValue());
                    return;
                }
                return;
            case 1:
                zaa((Field<?, ?>) field, str, (BigInteger) oConvert);
                return;
            case 2:
                if (zaa(str, oConvert)) {
                    setLongInternal(field, str, ((Long) oConvert).longValue());
                    return;
                }
                return;
            case 3:
            default:
                int i3 = field.zaqh;
                StringBuilder sb = new StringBuilder(44);
                sb.append("Unsupported type for conversion: ");
                sb.append(i3);
                throw new IllegalStateException(sb.toString());
            case 4:
                if (zaa(str, oConvert)) {
                    zaa((Field<?, ?>) field, str, ((Double) oConvert).doubleValue());
                    return;
                }
                return;
            case 5:
                zaa((Field<?, ?>) field, str, (BigDecimal) oConvert);
                return;
            case 6:
                if (zaa(str, oConvert)) {
                    setBooleanInternal(field, str, ((Boolean) oConvert).booleanValue());
                    return;
                }
                return;
            case 7:
                setStringInternal(field, str, (String) oConvert);
                return;
            case 8:
            case 9:
                if (zaa(str, oConvert)) {
                    setDecodedBytesInternal(field, str, (byte[]) oConvert);
                    return;
                }
                return;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <O, I> I zab(Field<I, O> field, Object obj) {
        return ((Field) field).zaqo != null ? field.convertBack(obj) : obj;
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(Field<?, ?> field, String str, ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    @KeepForSdk
    public <T extends FastJsonResponse> void addConcreteTypeInternal(Field<?, ?> field, String str, T t2) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    @KeepForSdk
    public abstract Map<String, Field<?, ?>> getFieldMappings();

    @KeepForSdk
    public Object getFieldValue(Field field) {
        String str = field.zaqj;
        if (field.zaql == null) {
            return getValueObject(str);
        }
        Preconditions.checkState(getValueObject(str) == null, "Concrete field shouldn't be value object: %s", field.zaqj);
        try {
            char upperCase = Character.toUpperCase(str.charAt(0));
            String strSubstring = str.substring(1);
            StringBuilder sb = new StringBuilder(String.valueOf(strSubstring).length() + 4);
            sb.append("get");
            sb.append(upperCase);
            sb.append(strSubstring);
            return getClass().getMethod(sb.toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    @KeepForSdk
    public abstract Object getValueObject(String str);

    @KeepForSdk
    public boolean isFieldSet(Field field) {
        if (field.zaqh != 11) {
            return isPrimitiveFieldSet(field.zaqj);
        }
        if (field.zaqi) {
            throw new UnsupportedOperationException("Concrete type arrays not supported");
        }
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    @KeepForSdk
    public abstract boolean isPrimitiveFieldSet(String str);

    @KeepForSdk
    public void setBooleanInternal(Field<?, ?> field, String str, boolean z2) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    @KeepForSdk
    public void setDecodedBytesInternal(Field<?, ?> field, String str, byte[] bArr) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    @KeepForSdk
    public void setIntegerInternal(Field<?, ?> field, String str, int i2) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    @KeepForSdk
    public void setLongInternal(Field<?, ?> field, String str, long j2) {
        throw new UnsupportedOperationException("Long not supported");
    }

    @KeepForSdk
    public void setStringInternal(Field<?, ?> field, String str, String str2) {
        throw new UnsupportedOperationException("String not supported");
    }

    @KeepForSdk
    public void setStringMapInternal(Field<?, ?> field, String str, Map<String, String> map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    @KeepForSdk
    public void setStringsInternal(Field<?, ?> field, String str, ArrayList<String> arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    @KeepForSdk
    public String toString() {
        Map<String, Field<?, ?>> fieldMappings = getFieldMappings();
        StringBuilder sb = new StringBuilder(100);
        for (String str : fieldMappings.keySet()) {
            Field<?, ?> field = fieldMappings.get(str);
            if (isFieldSet(field)) {
                Object objZab = zab(field, getFieldValue(field));
                if (sb.length() == 0) {
                    sb.append(StrPool.DELIM_START);
                } else {
                    sb.append(",");
                }
                sb.append("\"");
                sb.append(str);
                sb.append("\":");
                if (objZab != null) {
                    switch (field.zaqh) {
                        case 8:
                            sb.append("\"");
                            sb.append(Base64Utils.encode((byte[]) objZab));
                            sb.append("\"");
                            break;
                        case 9:
                            sb.append("\"");
                            sb.append(Base64Utils.encodeUrlSafe((byte[]) objZab));
                            sb.append("\"");
                            break;
                        case 10:
                            MapUtils.writeStringMapToJson(sb, (HashMap) objZab);
                            break;
                        default:
                            if (field.zaqg) {
                                ArrayList arrayList = (ArrayList) objZab;
                                sb.append(StrPool.BRACKET_START);
                                int size = arrayList.size();
                                for (int i2 = 0; i2 < size; i2++) {
                                    if (i2 > 0) {
                                        sb.append(",");
                                    }
                                    Object obj = arrayList.get(i2);
                                    if (obj != null) {
                                        zaa(sb, field, obj);
                                    }
                                }
                                sb.append(StrPool.BRACKET_END);
                                break;
                            } else {
                                zaa(sb, field, objZab);
                                break;
                            }
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append(StrPool.EMPTY_JSON);
        }
        return sb.toString();
    }

    public final <O> void zac(Field<ArrayList<Long>, O> field, ArrayList<Long> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<Long>, O>, O>) field, (Field<ArrayList<Long>, O>) arrayList);
        } else {
            zac(field, field.zaqj, arrayList);
        }
    }

    public final <O> void zad(Field<ArrayList<Float>, O> field, ArrayList<Float> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<Float>, O>, O>) field, (Field<ArrayList<Float>, O>) arrayList);
        } else {
            zad(field, field.zaqj, arrayList);
        }
    }

    public final <O> void zae(Field<ArrayList<Double>, O> field, ArrayList<Double> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<Double>, O>, O>) field, (Field<ArrayList<Double>, O>) arrayList);
        } else {
            zae(field, field.zaqj, arrayList);
        }
    }

    public final <O> void zaf(Field<ArrayList<BigDecimal>, O> field, ArrayList<BigDecimal> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<BigDecimal>, O>, O>) field, (Field<ArrayList<BigDecimal>, O>) arrayList);
        } else {
            zaf(field, field.zaqj, arrayList);
        }
    }

    public final <O> void zag(Field<ArrayList<Boolean>, O> field, ArrayList<Boolean> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<Boolean>, O>, O>) field, (Field<ArrayList<Boolean>, O>) arrayList);
        } else {
            zag(field, field.zaqj, arrayList);
        }
    }

    public final <O> void zah(Field<ArrayList<String>, O> field, ArrayList<String> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<String>, O>, O>) field, (Field<ArrayList<String>, O>) arrayList);
        } else {
            setStringsInternal(field, field.zaqj, arrayList);
        }
    }

    public final <O> void zab(Field<ArrayList<BigInteger>, O> field, ArrayList<BigInteger> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<BigInteger>, O>, O>) field, (Field<ArrayList<BigInteger>, O>) arrayList);
        } else {
            zab(field, field.zaqj, arrayList);
        }
    }

    public void zac(Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    public void zad(Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    public void zae(Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    public void zaf(Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    public void zag(Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    public void zab(Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    public final <O> void zaa(Field<Integer, O> field, int i2) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<Integer, O>, O>) field, (Field<Integer, O>) Integer.valueOf(i2));
        } else {
            setIntegerInternal(field, field.zaqj, i2);
        }
    }

    public final <O> void zaa(Field<ArrayList<Integer>, O> field, ArrayList<Integer> arrayList) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<ArrayList<Integer>, O>, O>) field, (Field<ArrayList<Integer>, O>) arrayList);
        } else {
            zaa(field, field.zaqj, arrayList);
        }
    }

    public final <O> void zaa(Field<BigInteger, O> field, BigInteger bigInteger) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<BigInteger, O>, O>) field, (Field<BigInteger, O>) bigInteger);
        } else {
            zaa(field, field.zaqj, bigInteger);
        }
    }

    public final <O> void zaa(Field<Long, O> field, long j2) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<Long, O>, O>) field, (Field<Long, O>) Long.valueOf(j2));
        } else {
            setLongInternal(field, field.zaqj, j2);
        }
    }

    public final <O> void zaa(Field<Float, O> field, float f2) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<Float, O>, O>) field, (Field<Float, O>) Float.valueOf(f2));
        } else {
            zaa((Field<?, ?>) field, field.zaqj, f2);
        }
    }

    public final <O> void zaa(Field<Double, O> field, double d3) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<Double, O>, O>) field, (Field<Double, O>) Double.valueOf(d3));
        } else {
            zaa(field, field.zaqj, d3);
        }
    }

    public final <O> void zaa(Field<BigDecimal, O> field, BigDecimal bigDecimal) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<BigDecimal, O>, O>) field, (Field<BigDecimal, O>) bigDecimal);
        } else {
            zaa(field, field.zaqj, bigDecimal);
        }
    }

    public final <O> void zaa(Field<Boolean, O> field, boolean z2) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<Boolean, O>, O>) field, (Field<Boolean, O>) Boolean.valueOf(z2));
        } else {
            setBooleanInternal(field, field.zaqj, z2);
        }
    }

    public final <O> void zaa(Field<String, O> field, String str) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<String, O>, O>) field, (Field<String, O>) str);
        } else {
            setStringInternal(field, field.zaqj, str);
        }
    }

    public final <O> void zaa(Field<byte[], O> field, byte[] bArr) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<byte[], O>, O>) field, (Field<byte[], O>) bArr);
        } else {
            setDecodedBytesInternal(field, field.zaqj, bArr);
        }
    }

    public final <O> void zaa(Field<Map<String, String>, O> field, Map<String, String> map) {
        if (((Field) field).zaqo != null) {
            zaa((Field<Field<Map<String, String>, O>, O>) field, (Field<Map<String, String>, O>) map);
        } else {
            setStringMapInternal(field, field.zaqj, map);
        }
    }

    public void zaa(Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    public void zaa(Field<?, ?> field, String str, BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    public void zaa(Field<?, ?> field, String str, float f2) {
        throw new UnsupportedOperationException("Float not supported");
    }

    public void zaa(Field<?, ?> field, String str, double d3) {
        throw new UnsupportedOperationException("Double not supported");
    }

    public void zaa(Field<?, ?> field, String str, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    private static <O> boolean zaa(String str, O o2) {
        if (o2 != null) {
            return true;
        }
        if (!Log.isLoggable("FastJsonResponse", 6)) {
            return false;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 58);
        sb.append("Output field (");
        sb.append(str);
        sb.append(") has a null value, but expected a primitive");
        Log.e("FastJsonResponse", sb.toString());
        return false;
    }

    private static void zaa(StringBuilder sb, Field field, Object obj) {
        int i2 = field.zaqf;
        if (i2 == 11) {
            sb.append(field.zaql.cast(obj).toString());
        } else {
            if (i2 == 7) {
                sb.append("\"");
                sb.append(JsonUtils.escapeString((String) obj));
                sb.append("\"");
                return;
            }
            sb.append(obj);
        }
    }
}
