package com.aliyun.svideo.common.utils.image;

/* loaded from: classes2.dex */
public interface ImageLoaderRequestListener<R> {
    boolean onLoadFailed(String str, boolean z2);

    boolean onResourceReady(R r2, boolean z2);
}
