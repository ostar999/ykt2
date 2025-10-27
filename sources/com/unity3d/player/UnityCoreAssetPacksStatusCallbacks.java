package com.unity3d.player;

/* loaded from: classes6.dex */
class UnityCoreAssetPacksStatusCallbacks implements IAssetPackManagerDownloadStatusCallback, IAssetPackManagerStatusQueryCallback {
    private final native void nativeStatusQueryResult(String str, int i2, int i3);

    @Override // com.unity3d.player.IAssetPackManagerStatusQueryCallback
    public void onStatusResult(long j2, String[] strArr, int[] iArr, int[] iArr2) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            nativeStatusQueryResult(strArr[i2], iArr[i2], iArr2[i2]);
        }
    }

    @Override // com.unity3d.player.IAssetPackManagerDownloadStatusCallback
    public void onStatusUpdate(String str, int i2, long j2, long j3, int i3, int i4) {
        nativeStatusQueryResult(str, i2, i4);
    }
}
