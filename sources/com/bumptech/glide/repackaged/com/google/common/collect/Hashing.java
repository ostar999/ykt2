package com.bumptech.glide.repackaged.com.google.common.collect;

/* loaded from: classes2.dex */
final class Hashing {
    private static int MAX_TABLE_SIZE = 1073741824;

    public static int smear(int i2) {
        return Integer.rotateLeft(i2 * (-862048943), 15) * 461845907;
    }

    public static int smearedHash(Object obj) {
        return smear(obj == null ? 0 : obj.hashCode());
    }
}
