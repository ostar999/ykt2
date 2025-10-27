package com.google.android.gms.common.server.response;

import com.google.android.gms.common.server.response.FastParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

/* loaded from: classes3.dex */
final class zag implements FastParser.zaa<BigDecimal> {
    @Override // com.google.android.gms.common.server.response.FastParser.zaa
    public final /* synthetic */ BigDecimal zah(FastParser fastParser, BufferedReader bufferedReader) throws FastParser.ParseException, IOException {
        return fastParser.zai(bufferedReader);
    }
}
