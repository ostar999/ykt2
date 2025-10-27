package com.android.volley.toolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class ByteArrayPool {
    protected static final Comparator<byte[]> BUF_COMPARATOR = new Comparator<byte[]>() { // from class: com.android.volley.toolbox.ByteArrayPool.1
        @Override // java.util.Comparator
        public int compare(byte[] bArr, byte[] bArr2) {
            return bArr.length - bArr2.length;
        }
    };
    private List<byte[]> mBuffersByLastUse = new LinkedList();
    private List<byte[]> mBuffersBySize = new ArrayList(64);
    private int mCurrentSize = 0;
    private final int mSizeLimit;

    public ByteArrayPool(int i2) {
        this.mSizeLimit = i2;
    }

    private synchronized void trim() {
        while (this.mCurrentSize > this.mSizeLimit) {
            byte[] bArrRemove = this.mBuffersByLastUse.remove(0);
            this.mBuffersBySize.remove(bArrRemove);
            this.mCurrentSize -= bArrRemove.length;
        }
    }

    public synchronized byte[] getBuf(int i2) {
        for (int i3 = 0; i3 < this.mBuffersBySize.size(); i3++) {
            byte[] bArr = this.mBuffersBySize.get(i3);
            if (bArr.length >= i2) {
                this.mCurrentSize -= bArr.length;
                this.mBuffersBySize.remove(i3);
                this.mBuffersByLastUse.remove(bArr);
                return bArr;
            }
        }
        return new byte[i2];
    }

    public synchronized void returnBuf(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length <= this.mSizeLimit) {
                this.mBuffersByLastUse.add(bArr);
                int iBinarySearch = Collections.binarySearch(this.mBuffersBySize, bArr, BUF_COMPARATOR);
                if (iBinarySearch < 0) {
                    iBinarySearch = (-iBinarySearch) - 1;
                }
                this.mBuffersBySize.add(iBinarySearch, bArr);
                this.mCurrentSize += bArr.length;
                trim();
            }
        }
    }
}
