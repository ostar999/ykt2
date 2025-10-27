package org.wrtca.customize;

import core.interfaces.DataProvider;
import java.nio.ByteBuffer;

/* loaded from: classes9.dex */
public interface RtcDataSource {
    ByteBuffer pullData();

    RtcDataSource pushData(DataProvider dataProvider);
}
