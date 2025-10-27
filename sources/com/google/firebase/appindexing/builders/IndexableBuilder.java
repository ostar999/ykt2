package com.google.firebase.appindexing.builders;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.IndexableBuilder;
import com.google.firebase.appindexing.internal.Thing;
import com.google.firebase.appindexing.internal.zzt;
import com.yikaobang.yixue.R2;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class IndexableBuilder<T extends IndexableBuilder<?>> {
    private final String type;
    private String url;
    private final Bundle zzay;
    private Thing.zza zzer;

    public IndexableBuilder(@NonNull String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotEmpty(str);
        this.zzay = new Bundle();
        this.type = str;
    }

    private static void zza(@NonNull Bundle bundle, @NonNull String str, @NonNull Thing... thingArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(thingArr);
        if (thingArr.length <= 0) {
            zzt.zzn("Thing array is empty and is ignored by put method.");
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < thingArr.length; i3++) {
            thingArr[i2] = thingArr[i3];
            if (thingArr[i3] == null) {
                StringBuilder sb = new StringBuilder(58);
                sb.append("Thing at ");
                sb.append(i3);
                sb.append(" is null and is ignored by put method.");
                zzt.zzn(sb.toString());
            } else {
                i2++;
            }
        }
        if (i2 > 0) {
            bundle.putParcelableArray(str, (Parcelable[]) zza((Thing[]) Arrays.copyOfRange(thingArr, 0, i2)));
        }
    }

    public final Indexable build() {
        Bundle bundle = new Bundle(this.zzay);
        Thing.zza zzaVarZzaa = this.zzer;
        if (zzaVarZzaa == null) {
            zzaVarZzaa = new Indexable.Metadata.Builder().zzaa();
        }
        return new Thing(bundle, zzaVarZzaa, this.url, this.type);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(@NonNull String str, @NonNull String... strArr) {
        zza(this.zzay, str, strArr);
        return this;
    }

    public final T setDescription(@NonNull String str) {
        Preconditions.checkNotNull(str);
        return (T) put("description", str);
    }

    public final T setImage(@NonNull String str) {
        Preconditions.checkNotNull(str);
        return (T) put("image", str);
    }

    public final T setKeywords(@NonNull String... strArr) {
        return (T) put("keywords", strArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T setMetadata(@NonNull Indexable.Metadata.Builder builder) {
        Preconditions.checkState(this.zzer == null, "setMetadata may only be called once");
        Preconditions.checkNotNull(builder);
        this.zzer = builder.zzaa();
        return this;
    }

    public final T setName(@NonNull String str) {
        Preconditions.checkNotNull(str);
        return (T) put("name", str);
    }

    public final T setSameAs(@NonNull String str) {
        Preconditions.checkNotNull(str);
        return (T) put("sameAs", str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final T setUrl(@NonNull String str) {
        Preconditions.checkNotNull(str);
        this.url = str;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(@NonNull String str, @NonNull Indexable... indexableArr) throws FirebaseAppIndexingInvalidArgumentException {
        zza(this.zzay, str, indexableArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(@NonNull String str, @NonNull boolean... zArr) {
        zza(this.zzay, str, zArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public T put(@NonNull String str, @NonNull long... jArr) {
        zza(this.zzay, str, jArr);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <S extends IndexableBuilder> T put(@NonNull String str, @NonNull S... sArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(sArr);
        if (sArr.length > 0) {
            int length = sArr.length;
            Thing[] thingArr = new Thing[length];
            for (int i2 = 0; i2 < sArr.length; i2++) {
                S s2 = sArr[i2];
                if (s2 == null) {
                    StringBuilder sb = new StringBuilder(60);
                    sb.append("Builder at ");
                    sb.append(i2);
                    sb.append(" is null and is ignored by put method.");
                    zzt.zzn(sb.toString());
                } else {
                    thingArr[i2] = (Thing) s2.build();
                }
            }
            if (length > 0) {
                zza(this.zzay, str, thingArr);
            }
        } else {
            zzt.zzn("Builder array is empty and is ignored by put method.");
        }
        return this;
    }

    public static void zza(@NonNull Bundle bundle, @NonNull String str, @NonNull String... strArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(strArr);
        String[] strArr2 = (String[]) Arrays.copyOf(strArr, strArr.length);
        if (strArr2.length > 0) {
            int i2 = 0;
            for (int i3 = 0; i3 < Math.min(strArr2.length, 100); i3++) {
                String str2 = strArr2[i3];
                strArr2[i2] = str2;
                if (strArr2[i3] == null) {
                    StringBuilder sb = new StringBuilder(59);
                    sb.append("String at ");
                    sb.append(i3);
                    sb.append(" is null and is ignored by put method.");
                    zzt.zzn(sb.toString());
                } else {
                    int i4 = 20000;
                    if (str2.length() > 20000) {
                        StringBuilder sb2 = new StringBuilder(53);
                        sb2.append("String at ");
                        sb2.append(i3);
                        sb2.append(" is too long, truncating string.");
                        zzt.zzn(sb2.toString());
                        String strSubstring = strArr2[i2];
                        if (strSubstring.length() > 20000) {
                            if (Character.isHighSurrogate(strSubstring.charAt(R2.id.tv_region)) && Character.isLowSurrogate(strSubstring.charAt(20000))) {
                                i4 = 19999;
                            }
                            strSubstring = strSubstring.substring(0, i4);
                        }
                        strArr2[i2] = strSubstring;
                    }
                    i2++;
                }
            }
            if (i2 > 0) {
                bundle.putStringArray(str, (String[]) zza((String[]) Arrays.copyOfRange(strArr2, 0, i2)));
                return;
            }
            return;
        }
        zzt.zzn("String array is empty and is ignored by put method.");
    }

    public static void zza(@NonNull Bundle bundle, @NonNull String str, @NonNull Indexable... indexableArr) throws FirebaseAppIndexingInvalidArgumentException {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(indexableArr);
        Thing[] thingArr = new Thing[indexableArr.length];
        for (int i2 = 0; i2 < indexableArr.length; i2++) {
            Indexable indexable = indexableArr[i2];
            if (indexable != null && !(indexable instanceof Thing)) {
                throw new FirebaseAppIndexingInvalidArgumentException("Invalid Indexable encountered. Use Indexable.Builder or convenience methods under Indexables to create the Indexable.");
            }
            thingArr[i2] = (Thing) indexable;
        }
        zza(bundle, str, thingArr);
    }

    public static void zza(@NonNull Bundle bundle, @NonNull String str, @NonNull boolean... zArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(zArr);
        if (zArr.length > 0) {
            if (zArr.length >= 100) {
                zzt.zzn("Input Array of elements is too big, cutting off.");
                zArr = Arrays.copyOf(zArr, 100);
            }
            bundle.putBooleanArray(str, zArr);
            return;
        }
        zzt.zzn("Boolean array is empty and is ignored by put method.");
    }

    public static void zza(@NonNull Bundle bundle, @NonNull String str, @NonNull long... jArr) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(jArr);
        if (jArr.length > 0) {
            if (jArr.length >= 100) {
                zzt.zzn("Input Array of elements is too big, cutting off.");
                jArr = Arrays.copyOf(jArr, 100);
            }
            bundle.putLongArray(str, jArr);
            return;
        }
        zzt.zzn("Long array is empty and is ignored by put method.");
    }

    private static <S> S[] zza(S[] sArr) {
        if (sArr.length < 100) {
            return sArr;
        }
        zzt.zzn("Input Array of elements is too big, cutting off.");
        return (S[]) Arrays.copyOf(sArr, 100);
    }
}
