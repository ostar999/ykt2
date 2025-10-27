package org.apache.commons.compress.compressors.pack200;

import java.io.IOException;

/* loaded from: classes9.dex */
public enum Pack200Strategy {
    IN_MEMORY { // from class: org.apache.commons.compress.compressors.pack200.Pack200Strategy.1
        @Override // org.apache.commons.compress.compressors.pack200.Pack200Strategy
        public StreamBridge newStreamBridge() {
            return new InMemoryCachingStreamBridge();
        }
    },
    TEMP_FILE { // from class: org.apache.commons.compress.compressors.pack200.Pack200Strategy.2
        @Override // org.apache.commons.compress.compressors.pack200.Pack200Strategy
        public StreamBridge newStreamBridge() throws IOException {
            return new TempFileCachingStreamBridge();
        }
    };

    public abstract StreamBridge newStreamBridge() throws IOException;
}
