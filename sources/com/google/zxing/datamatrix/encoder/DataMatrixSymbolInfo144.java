package com.google.zxing.datamatrix.encoder;

import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
final class DataMatrixSymbolInfo144 extends SymbolInfo {
    public DataMatrixSymbolInfo144() {
        super(false, R2.attr.flexDirection, R2.attr.bl_gradient_centerColor, 22, 22, 36, -1, 62);
    }

    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public int getDataLengthForInterleavedBlock(int i2) {
        return i2 <= 8 ? 156 : 155;
    }

    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public int getInterleavedBlockCount() {
        return 10;
    }
}
