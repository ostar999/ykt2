package org.apache.commons.compress.archivers.dump;

import cn.hutool.core.text.StrPool;

/* loaded from: classes9.dex */
public class InvalidFormatException extends DumpArchiveException {
    private static final long serialVersionUID = 1;
    protected long offset;

    public InvalidFormatException() {
        super("there was an error decoding a tape segment");
    }

    public long getOffset() {
        return this.offset;
    }

    public InvalidFormatException(long j2) {
        super("there was an error decoding a tape segment header at offset " + j2 + StrPool.DOT);
        this.offset = j2;
    }
}
