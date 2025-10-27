package com.alibaba.sdk.android.oss.model;

/* loaded from: classes2.dex */
public enum StorageClass {
    Standard("Standard"),
    IA("IA"),
    Archive("Archive"),
    Unknown("Unknown");

    private String storageClassString;

    StorageClass(String str) {
        this.storageClassString = str;
    }

    public static StorageClass parse(String str) {
        for (StorageClass storageClass : values()) {
            if (storageClass.toString().equals(str)) {
                return storageClass;
            }
        }
        throw new IllegalArgumentException("Unable to parse " + str);
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.storageClassString;
    }
}
