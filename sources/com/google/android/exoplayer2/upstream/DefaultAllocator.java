package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class DefaultAllocator implements Allocator {
    private static final int AVAILABLE_EXTRA_CAPACITY = 100;
    private int allocatedCount;
    private Allocation[] availableAllocations;
    private int availableCount;
    private final int individualAllocationSize;

    @Nullable
    private final byte[] initialAllocationBlock;
    private final Allocation[] singleAllocationReleaseHolder;
    private int targetBufferSize;
    private final boolean trimOnReset;

    public DefaultAllocator(boolean z2, int i2) {
        this(z2, i2, 0);
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized Allocation allocate() {
        Allocation allocation;
        this.allocatedCount++;
        int i2 = this.availableCount;
        if (i2 > 0) {
            Allocation[] allocationArr = this.availableAllocations;
            int i3 = i2 - 1;
            this.availableCount = i3;
            allocation = (Allocation) Assertions.checkNotNull(allocationArr[i3]);
            this.availableAllocations[this.availableCount] = null;
        } else {
            allocation = new Allocation(new byte[this.individualAllocationSize], 0);
        }
        return allocation;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public int getIndividualAllocationLength() {
        return this.individualAllocationSize;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized int getTotalBytesAllocated() {
        return this.allocatedCount * this.individualAllocationSize;
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void release(Allocation allocation) {
        Allocation[] allocationArr = this.singleAllocationReleaseHolder;
        allocationArr[0] = allocation;
        release(allocationArr);
    }

    public synchronized void reset() {
        if (this.trimOnReset) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int i2) {
        boolean z2 = i2 < this.targetBufferSize;
        this.targetBufferSize = i2;
        if (z2) {
            trim();
        }
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void trim() {
        int i2 = 0;
        int iMax = Math.max(0, Util.ceilDivide(this.targetBufferSize, this.individualAllocationSize) - this.allocatedCount);
        int i3 = this.availableCount;
        if (iMax >= i3) {
            return;
        }
        if (this.initialAllocationBlock != null) {
            int i4 = i3 - 1;
            while (i2 <= i4) {
                Allocation allocation = (Allocation) Assertions.checkNotNull(this.availableAllocations[i2]);
                if (allocation.data == this.initialAllocationBlock) {
                    i2++;
                } else {
                    Allocation allocation2 = (Allocation) Assertions.checkNotNull(this.availableAllocations[i4]);
                    if (allocation2.data != this.initialAllocationBlock) {
                        i4--;
                    } else {
                        Allocation[] allocationArr = this.availableAllocations;
                        allocationArr[i2] = allocation2;
                        allocationArr[i4] = allocation;
                        i4--;
                        i2++;
                    }
                }
            }
            iMax = Math.max(iMax, i2);
            if (iMax >= this.availableCount) {
                return;
            }
        }
        Arrays.fill(this.availableAllocations, iMax, this.availableCount, (Object) null);
        this.availableCount = iMax;
    }

    public DefaultAllocator(boolean z2, int i2, int i3) {
        Assertions.checkArgument(i2 > 0);
        Assertions.checkArgument(i3 >= 0);
        this.trimOnReset = z2;
        this.individualAllocationSize = i2;
        this.availableCount = i3;
        this.availableAllocations = new Allocation[i3 + 100];
        if (i3 > 0) {
            this.initialAllocationBlock = new byte[i3 * i2];
            for (int i4 = 0; i4 < i3; i4++) {
                this.availableAllocations[i4] = new Allocation(this.initialAllocationBlock, i4 * i2);
            }
        } else {
            this.initialAllocationBlock = null;
        }
        this.singleAllocationReleaseHolder = new Allocation[1];
    }

    @Override // com.google.android.exoplayer2.upstream.Allocator
    public synchronized void release(Allocation[] allocationArr) {
        int i2 = this.availableCount;
        int length = allocationArr.length + i2;
        Allocation[] allocationArr2 = this.availableAllocations;
        if (length >= allocationArr2.length) {
            this.availableAllocations = (Allocation[]) Arrays.copyOf(allocationArr2, Math.max(allocationArr2.length * 2, i2 + allocationArr.length));
        }
        for (Allocation allocation : allocationArr) {
            Allocation[] allocationArr3 = this.availableAllocations;
            int i3 = this.availableCount;
            this.availableCount = i3 + 1;
            allocationArr3[i3] = allocation;
        }
        this.allocatedCount -= allocationArr.length;
        notifyAll();
    }
}
