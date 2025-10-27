package org.apache.commons.compress.changes;

import java.io.InputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* loaded from: classes9.dex */
class Change {
    static final int TYPE_ADD = 2;
    static final int TYPE_DELETE = 1;
    static final int TYPE_DELETE_DIR = 4;
    static final int TYPE_MOVE = 3;
    private final ArchiveEntry entry;
    private final InputStream input;
    private final boolean replaceMode;
    private final String targetFile;
    private final int type;

    public Change(String str, int i2) {
        str.getClass();
        this.targetFile = str;
        this.type = i2;
        this.input = null;
        this.entry = null;
        this.replaceMode = true;
    }

    public ArchiveEntry getEntry() {
        return this.entry;
    }

    public InputStream getInput() {
        return this.input;
    }

    public boolean isReplaceMode() {
        return this.replaceMode;
    }

    public String targetFile() {
        return this.targetFile;
    }

    public int type() {
        return this.type;
    }

    public Change(ArchiveEntry archiveEntry, InputStream inputStream, boolean z2) {
        if (archiveEntry != null && inputStream != null) {
            this.entry = archiveEntry;
            this.input = inputStream;
            this.type = 2;
            this.targetFile = null;
            this.replaceMode = z2;
            return;
        }
        throw null;
    }
}
