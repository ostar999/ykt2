package cn.hutool.core.net;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class LocalPortGenerater implements Serializable {
    private static final long serialVersionUID = 1;
    private final AtomicInteger alternativePort;

    public LocalPortGenerater(int i2) {
        this.alternativePort = new AtomicInteger(i2);
    }

    public int generate() {
        int iIncrementAndGet = this.alternativePort.get();
        while (!NetUtil.isUsableLocalPort(iIncrementAndGet)) {
            iIncrementAndGet = this.alternativePort.incrementAndGet();
        }
        return iIncrementAndGet;
    }
}
