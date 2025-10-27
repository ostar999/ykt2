package com.opensource.svgaplayer.utils;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.opensource.svgaplayer.BuildConfig;
import kotlin.Metadata;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lcom/opensource/svgaplayer/utils/SVGARange;", "", "location", "", SessionDescription.ATTR_LENGTH, "(II)V", "getLength", "()I", "getLocation", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGARange {
    private final int length;
    private final int location;

    public SVGARange(int i2, int i3) {
        this.location = i2;
        this.length = i3;
    }

    public final int getLength() {
        return this.length;
    }

    public final int getLocation() {
        return this.location;
    }
}
