package com.tencent.liteav.basic.module;

import com.tencent.connect.common.Constants;
import com.tencent.liteav.basic.log.TXCLog;

/* loaded from: classes6.dex */
public class StatusBucket {
    private static final String TAG = "com.tencent.liteav.basic.module.StatusBucket";
    private long mBucketObj;

    public StatusBucket() {
        this.mBucketObj = 0L;
        this.mBucketObj = nativeCreateStatusBucket();
    }

    private static native long nativeCreateStatusBucket();

    private static native void nativeDestroyStatusBucket(long j2);

    private static native boolean nativeGetBooleanStatus(long j2, String str, int i2);

    private static native int nativeGetIntStatus(long j2, String str, int i2);

    private static native long nativeGetLongStatus(long j2, String str, int i2);

    private static native void nativeMerge(long j2, long j3);

    private static native void nativeSetBooleanStatus(long j2, String str, int i2, boolean z2);

    private static native void nativeSetIntStatus(long j2, String str, int i2, int i3);

    private static native void nativeSetLongStatus(long j2, String str, int i2, long j3);

    public static void testStatusBucket() {
        StatusBucket statusBucket = new StatusBucket();
        StatusBucket statusBucket2 = new StatusBucket();
        statusBucket.setBooleanStatus("1", 1, true);
        statusBucket.setIntStatus("2", 2, 2);
        statusBucket.setLongStatus("3", 3, 3L);
        statusBucket2.setIntStatus("1", 4, 4);
        statusBucket2.setBooleanStatus("5", 5, true);
        statusBucket2.setLongStatus(Constants.VIA_SHARE_TYPE_INFO, 6, 6L);
        statusBucket.merge(statusBucket2);
        String str = TAG;
        TXCLog.e(str, "test_status_bucket: id:1, key:1, value:" + statusBucket.getBooleanStatus("1", 1));
        TXCLog.e(str, "test_status_bucket: id:1, key:4, value:" + statusBucket.getIntStatus("1", 4));
        TXCLog.e(str, "test_status_bucket: id:2, key:2, value:" + statusBucket.getIntStatus("2", 2));
        TXCLog.e(str, "test_status_bucket: id:3, key:3, value:" + statusBucket.getLongStatus("3", 3));
        TXCLog.e(str, "test_status_bucket: id:5, key:5, value:" + statusBucket.getBooleanStatus("5", 5));
        TXCLog.e(str, "test_status_bucket: id:6, key:6, value:" + statusBucket.getLongStatus(Constants.VIA_SHARE_TYPE_INFO, 6));
    }

    public void finalize() throws Throwable {
        super.finalize();
        long j2 = this.mBucketObj;
        this.mBucketObj = 0L;
        nativeDestroyStatusBucket(j2);
    }

    public boolean getBooleanStatus(String str, int i2) {
        return nativeGetBooleanStatus(this.mBucketObj, str, i2);
    }

    public int getIntStatus(String str, int i2) {
        return nativeGetIntStatus(this.mBucketObj, str, i2);
    }

    public long getLongStatus(String str, int i2) {
        return nativeGetLongStatus(this.mBucketObj, str, i2);
    }

    public void merge(StatusBucket statusBucket) {
        nativeMerge(this.mBucketObj, statusBucket.mBucketObj);
    }

    public void setBooleanStatus(String str, int i2, boolean z2) {
        nativeSetBooleanStatus(this.mBucketObj, str, i2, z2);
    }

    public void setIntStatus(String str, int i2, int i3) {
        nativeSetIntStatus(this.mBucketObj, str, i2, i3);
    }

    public void setLongStatus(String str, int i2, long j2) {
        nativeSetLongStatus(this.mBucketObj, str, i2, j2);
    }

    public StatusBucket(long j2) {
        this.mBucketObj = j2;
    }
}
