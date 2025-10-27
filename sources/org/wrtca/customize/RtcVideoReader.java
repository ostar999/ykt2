package org.wrtca.customize;

import org.wrtca.api.VideoFrame;
import org.wrtca.customize.RtcDataSource;

/* loaded from: classes9.dex */
public interface RtcVideoReader<T extends RtcDataSource> {
    void close();

    VideoFrame getNextFrame(T t2, int i2);
}
