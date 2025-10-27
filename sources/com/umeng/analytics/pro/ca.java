package com.umeng.analytics.pro;

import java.util.BitSet;

/* loaded from: classes6.dex */
public final class ca extends bo {

    public static class a implements bw {
        @Override // com.umeng.analytics.pro.bw
        public bu a(ci ciVar) {
            return new ca(ciVar);
        }
    }

    public ca(ci ciVar) {
        super(ciVar);
    }

    @Override // com.umeng.analytics.pro.bu
    public Class<? extends cc> D() {
        return cf.class;
    }

    public void a(BitSet bitSet, int i2) throws bb {
        for (byte b3 : b(bitSet, i2)) {
            a(b3);
        }
    }

    public BitSet b(int i2) throws bb {
        int iCeil = (int) Math.ceil(i2 / 8.0d);
        byte[] bArr = new byte[iCeil];
        for (int i3 = 0; i3 < iCeil; i3++) {
            bArr[i3] = u();
        }
        return a(bArr);
    }

    public static BitSet a(byte[] bArr) {
        BitSet bitSet = new BitSet();
        for (int i2 = 0; i2 < bArr.length * 8; i2++) {
            if ((bArr[(bArr.length - (i2 / 8)) - 1] & (1 << (i2 % 8))) > 0) {
                bitSet.set(i2);
            }
        }
        return bitSet;
    }

    public static byte[] b(BitSet bitSet, int i2) {
        int iCeil = (int) Math.ceil(i2 / 8.0d);
        byte[] bArr = new byte[iCeil];
        for (int i3 = 0; i3 < bitSet.length(); i3++) {
            if (bitSet.get(i3)) {
                int i4 = (iCeil - (i3 / 8)) - 1;
                bArr[i4] = (byte) ((1 << (i3 % 8)) | bArr[i4]);
            }
        }
        return bArr;
    }
}
