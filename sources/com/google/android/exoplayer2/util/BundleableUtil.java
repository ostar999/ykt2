package com.google.android.exoplayer2.util;

import android.os.Bundle;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.PLVGradientView;
import com.google.android.exoplayer2.Bundleable;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class BundleableUtil {
    private BundleableUtil() {
    }

    public static void ensureClassLoader(@Nullable Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader((ClassLoader) Util.castNonNull(BundleableUtil.class.getClassLoader()));
        }
    }

    public static <T extends Bundleable> ImmutableList<T> fromBundleList(Bundleable.Creator<T> creator, List<Bundle> list) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i2 = 0; i2 < list.size(); i2++) {
            builder.add((ImmutableList.Builder) creator.fromBundle((Bundle) Assertions.checkNotNull(list.get(i2))));
        }
        return builder.build();
    }

    public static <T extends Bundleable> List<T> fromBundleNullableList(Bundleable.Creator<T> creator, @Nullable List<Bundle> list, List<T> list2) {
        return list == null ? list2 : fromBundleList(creator, list);
    }

    public static <T extends Bundleable> SparseArray<T> fromBundleNullableSparseArray(Bundleable.Creator<T> creator, @Nullable SparseArray<Bundle> sparseArray, SparseArray<T> sparseArray2) {
        if (sparseArray == null) {
            return sparseArray2;
        }
        PLVGradientView.AnonymousClass1 anonymousClass1 = (SparseArray<T>) new SparseArray(sparseArray.size());
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            anonymousClass1.put(sparseArray.keyAt(i2), creator.fromBundle(sparseArray.valueAt(i2)));
        }
        return anonymousClass1;
    }

    @Nullable
    public static <T extends Bundleable> T fromNullableBundle(Bundleable.Creator<T> creator, @Nullable Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return (T) creator.fromBundle(bundle);
    }

    public static <T extends Bundleable> ArrayList<Bundle> toBundleArrayList(Collection<T> collection) {
        ArrayList<Bundle> arrayList = new ArrayList<>(collection.size());
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().toBundle());
        }
        return arrayList;
    }

    public static <T extends Bundleable> ImmutableList<Bundle> toBundleList(List<T> list) {
        ImmutableList.Builder builder = ImmutableList.builder();
        for (int i2 = 0; i2 < list.size(); i2++) {
            builder.add((ImmutableList.Builder) list.get(i2).toBundle());
        }
        return builder.build();
    }

    public static <T extends Bundleable> SparseArray<Bundle> toBundleSparseArray(SparseArray<T> sparseArray) {
        SparseArray<Bundle> sparseArray2 = new SparseArray<>(sparseArray.size());
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            sparseArray2.put(sparseArray.keyAt(i2), sparseArray.valueAt(i2).toBundle());
        }
        return sparseArray2;
    }

    @Nullable
    public static Bundle toNullableBundle(@Nullable Bundleable bundleable) {
        if (bundleable == null) {
            return null;
        }
        return bundleable.toBundle();
    }

    public static <T extends Bundleable> T fromNullableBundle(Bundleable.Creator<T> creator, @Nullable Bundle bundle, T t2) {
        return bundle == null ? t2 : (T) creator.fromBundle(bundle);
    }
}
