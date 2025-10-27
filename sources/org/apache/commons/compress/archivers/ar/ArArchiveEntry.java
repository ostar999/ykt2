package org.apache.commons.compress.archivers.ar;

import java.io.File;
import java.util.Date;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* loaded from: classes9.dex */
public class ArArchiveEntry implements ArchiveEntry {
    private static final int DEFAULT_MODE = 33188;
    public static final String HEADER = "!<arch>\n";
    public static final String TRAILER = "`\n";
    private final int groupId;
    private final long lastModified;
    private final long length;
    private final int mode;
    private final String name;
    private final int userId;

    public ArArchiveEntry(String str, long j2) {
        this(str, j2, 0, 0, 33188, System.currentTimeMillis() / 1000);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArArchiveEntry arArchiveEntry = (ArArchiveEntry) obj;
        String str = this.name;
        if (str == null) {
            if (arArchiveEntry.name != null) {
                return false;
            }
        } else if (!str.equals(arArchiveEntry.name)) {
            return false;
        }
        return true;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public long getLastModified() {
        return this.lastModified;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public Date getLastModifiedDate() {
        return new Date(getLastModified() * 1000);
    }

    public long getLength() {
        return this.length;
    }

    public int getMode() {
        return this.mode;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public String getName() {
        return this.name;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public long getSize() {
        return getLength();
    }

    public int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        String str = this.name;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public boolean isDirectory() {
        return false;
    }

    public ArArchiveEntry(String str, long j2, int i2, int i3, int i4, long j3) {
        this.name = str;
        this.length = j2;
        this.userId = i2;
        this.groupId = i3;
        this.mode = i4;
        this.lastModified = j3;
    }

    public ArArchiveEntry(File file, String str) {
        this(str, file.isFile() ? file.length() : 0L, 0, 0, 33188, file.lastModified() / 1000);
    }
}
