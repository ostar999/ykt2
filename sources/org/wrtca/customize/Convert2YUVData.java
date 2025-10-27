package org.wrtca.customize;

import core.interfaces.DataProvider;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* loaded from: classes9.dex */
public class Convert2YUVData implements RtcDataSource {
    public ByteBuffer mDstData;
    public int mDstHeight;
    public int mDstWidth;
    public int mRotation;
    public ByteBuffer mSrcData;
    public int mSrcHeight;
    public int mSrcWidth;
    public int mType;

    @Override // org.wrtca.customize.RtcDataSource
    public ByteBuffer pullData() {
        return this.mDstData;
    }

    @Override // org.wrtca.customize.RtcDataSource
    public RtcDataSource pushData(DataProvider dataProvider) {
        if (dataProvider == null) {
            return this;
        }
        ArrayList arrayList = new ArrayList();
        ByteBuffer byteBufferProvideRGBData = dataProvider.provideRGBData(arrayList);
        this.mSrcData = byteBufferProvideRGBData;
        if (byteBufferProvideRGBData == null) {
            return this;
        }
        this.mType = arrayList.get(0).intValue();
        this.mSrcWidth = arrayList.get(1).intValue();
        this.mSrcHeight = arrayList.get(2).intValue();
        return this;
    }
}
