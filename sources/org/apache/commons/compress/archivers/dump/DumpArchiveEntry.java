package org.apache.commons.compress.archivers.dump;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.dump.DumpArchiveConstants;

/* loaded from: classes9.dex */
public class DumpArchiveEntry implements ArchiveEntry {
    private long atime;
    private long ctime;
    private int generation;
    private int gid;
    private int ino;
    private boolean isDeleted;
    private int mode;
    private long mtime;
    private String name;
    private int nlink;
    private long offset;
    private String originalName;
    private String simpleName;
    private long size;
    private int uid;
    private int volume;
    private TYPE type = TYPE.UNKNOWN;
    private Set<PERMISSION> permissions = Collections.emptySet();
    private final DumpArchiveSummary summary = null;
    private final TapeSegmentHeader header = new TapeSegmentHeader();

    public enum PERMISSION {
        SETUID(2048),
        SETGUI(1024),
        STICKY(512),
        USER_READ(256),
        USER_WRITE(128),
        USER_EXEC(64),
        GROUP_READ(32),
        GROUP_WRITE(16),
        GROUP_EXEC(8),
        WORLD_READ(4),
        WORLD_WRITE(2),
        WORLD_EXEC(1);

        private int code;

        PERMISSION(int i2) {
            this.code = i2;
        }

        public static Set<PERMISSION> find(int i2) {
            HashSet hashSet = new HashSet();
            for (PERMISSION permission : values()) {
                int i3 = permission.code;
                if ((i2 & i3) == i3) {
                    hashSet.add(permission);
                }
            }
            return hashSet.isEmpty() ? Collections.emptySet() : EnumSet.copyOf((Collection) hashSet);
        }
    }

    public enum TYPE {
        WHITEOUT(14),
        SOCKET(12),
        LINK(10),
        FILE(8),
        BLKDEV(6),
        DIRECTORY(4),
        CHRDEV(2),
        FIFO(1),
        UNKNOWN(15);

        private int code;

        TYPE(int i2) {
            this.code = i2;
        }

        public static TYPE find(int i2) {
            TYPE type = UNKNOWN;
            for (TYPE type2 : values()) {
                if (i2 == type2.code) {
                    type = type2;
                }
            }
            return type;
        }
    }

    public static class TapeSegmentHeader {
        private final byte[] cdata = new byte[512];
        private int count;
        private int holes;
        private int ino;
        private DumpArchiveConstants.SEGMENT_TYPE type;
        private int volume;

        public static /* synthetic */ int access$408(TapeSegmentHeader tapeSegmentHeader) {
            int i2 = tapeSegmentHeader.holes;
            tapeSegmentHeader.holes = i2 + 1;
            return i2;
        }

        public int getCdata(int i2) {
            return this.cdata[i2];
        }

        public int getCount() {
            return this.count;
        }

        public int getHoles() {
            return this.holes;
        }

        public int getIno() {
            return this.ino;
        }

        public DumpArchiveConstants.SEGMENT_TYPE getType() {
            return this.type;
        }

        public int getVolume() {
            return this.volume;
        }

        public void setIno(int i2) {
            this.ino = i2;
        }
    }

    public DumpArchiveEntry() {
    }

    public static DumpArchiveEntry parse(byte[] bArr) {
        DumpArchiveEntry dumpArchiveEntry = new DumpArchiveEntry();
        TapeSegmentHeader tapeSegmentHeader = dumpArchiveEntry.header;
        tapeSegmentHeader.type = DumpArchiveConstants.SEGMENT_TYPE.find(DumpArchiveUtil.convert32(bArr, 0));
        tapeSegmentHeader.volume = DumpArchiveUtil.convert32(bArr, 12);
        dumpArchiveEntry.ino = tapeSegmentHeader.ino = DumpArchiveUtil.convert32(bArr, 20);
        int iConvert16 = DumpArchiveUtil.convert16(bArr, 32);
        dumpArchiveEntry.setType(TYPE.find((iConvert16 >> 12) & 15));
        dumpArchiveEntry.setMode(iConvert16);
        dumpArchiveEntry.nlink = DumpArchiveUtil.convert16(bArr, 34);
        dumpArchiveEntry.setSize(DumpArchiveUtil.convert64(bArr, 40));
        dumpArchiveEntry.setAccessTime(new Date((DumpArchiveUtil.convert32(bArr, 48) * 1000) + (DumpArchiveUtil.convert32(bArr, 52) / 1000)));
        dumpArchiveEntry.setLastModifiedDate(new Date((DumpArchiveUtil.convert32(bArr, 56) * 1000) + (DumpArchiveUtil.convert32(bArr, 60) / 1000)));
        dumpArchiveEntry.ctime = (DumpArchiveUtil.convert32(bArr, 64) * 1000) + (DumpArchiveUtil.convert32(bArr, 68) / 1000);
        dumpArchiveEntry.generation = DumpArchiveUtil.convert32(bArr, 140);
        dumpArchiveEntry.setUserId(DumpArchiveUtil.convert32(bArr, 144));
        dumpArchiveEntry.setGroupId(DumpArchiveUtil.convert32(bArr, 148));
        tapeSegmentHeader.count = DumpArchiveUtil.convert32(bArr, 160);
        tapeSegmentHeader.holes = 0;
        for (int i2 = 0; i2 < 512 && i2 < tapeSegmentHeader.count; i2++) {
            if (bArr[i2 + 164] == 0) {
                TapeSegmentHeader.access$408(tapeSegmentHeader);
            }
        }
        System.arraycopy(bArr, 164, tapeSegmentHeader.cdata, 0, 512);
        dumpArchiveEntry.volume = tapeSegmentHeader.getVolume();
        return dumpArchiveEntry;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            DumpArchiveEntry dumpArchiveEntry = (DumpArchiveEntry) obj;
            if (this.header == null || dumpArchiveEntry.header == null || this.ino != dumpArchiveEntry.ino) {
                return false;
            }
            DumpArchiveSummary dumpArchiveSummary = this.summary;
            return (dumpArchiveSummary != null || dumpArchiveEntry.summary == null) && (dumpArchiveSummary == null || dumpArchiveSummary.equals(dumpArchiveEntry.summary));
        }
        return false;
    }

    public Date getAccessTime() {
        return new Date(this.atime);
    }

    public Date getCreationTime() {
        return new Date(this.ctime);
    }

    public long getEntrySize() {
        return this.size;
    }

    public int getGeneration() {
        return this.generation;
    }

    public int getGroupId() {
        return this.gid;
    }

    public int getHeaderCount() {
        return this.header.getCount();
    }

    public int getHeaderHoles() {
        return this.header.getHoles();
    }

    public DumpArchiveConstants.SEGMENT_TYPE getHeaderType() {
        return this.header.getType();
    }

    public int getIno() {
        return this.header.getIno();
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public Date getLastModifiedDate() {
        return new Date(this.mtime);
    }

    public int getMode() {
        return this.mode;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public String getName() {
        return this.name;
    }

    public int getNlink() {
        return this.nlink;
    }

    public long getOffset() {
        return this.offset;
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public Set<PERMISSION> getPermissions() {
        return this.permissions;
    }

    public String getSimpleName() {
        return this.simpleName;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public long getSize() {
        if (isDirectory()) {
            return -1L;
        }
        return this.size;
    }

    public TYPE getType() {
        return this.type;
    }

    public int getUserId() {
        return this.uid;
    }

    public int getVolume() {
        return this.volume;
    }

    public int hashCode() {
        return this.ino;
    }

    public boolean isBlkDev() {
        return this.type == TYPE.BLKDEV;
    }

    public boolean isChrDev() {
        return this.type == TYPE.CHRDEV;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public boolean isDirectory() {
        return this.type == TYPE.DIRECTORY;
    }

    public boolean isFifo() {
        return this.type == TYPE.FIFO;
    }

    public boolean isFile() {
        return this.type == TYPE.FILE;
    }

    public boolean isSocket() {
        return this.type == TYPE.SOCKET;
    }

    public boolean isSparseRecord(int i2) {
        return (this.header.getCdata(i2) & 1) == 0;
    }

    public void setAccessTime(Date date) {
        this.atime = date.getTime();
    }

    public void setCreationTime(Date date) {
        this.ctime = date.getTime();
    }

    public void setDeleted(boolean z2) {
        this.isDeleted = z2;
    }

    public void setGeneration(int i2) {
        this.generation = i2;
    }

    public void setGroupId(int i2) {
        this.gid = i2;
    }

    public void setLastModifiedDate(Date date) {
        this.mtime = date.getTime();
    }

    public void setMode(int i2) {
        this.mode = i2 & 4095;
        this.permissions = PERMISSION.find(i2);
    }

    public final void setName(String str) {
        this.originalName = str;
        if (str != null) {
            if (isDirectory() && !str.endsWith("/")) {
                str = str + "/";
            }
            if (str.startsWith("./")) {
                str = str.substring(2);
            }
        }
        this.name = str;
    }

    public void setNlink(int i2) {
        this.nlink = i2;
    }

    public void setOffset(long j2) {
        this.offset = j2;
    }

    public void setSimpleName(String str) {
        this.simpleName = str;
    }

    public void setSize(long j2) {
        this.size = j2;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public void setUserId(int i2) {
        this.uid = i2;
    }

    public void setVolume(int i2) {
        this.volume = i2;
    }

    public String toString() {
        return getName();
    }

    public void update(byte[] bArr) {
        this.header.volume = DumpArchiveUtil.convert32(bArr, 16);
        this.header.count = DumpArchiveUtil.convert32(bArr, 160);
        this.header.holes = 0;
        for (int i2 = 0; i2 < 512 && i2 < this.header.count; i2++) {
            if (bArr[i2 + 164] == 0) {
                TapeSegmentHeader.access$408(this.header);
            }
        }
        System.arraycopy(bArr, 164, this.header.cdata, 0, 512);
    }

    public DumpArchiveEntry(String str, String str2) {
        setName(str);
        this.simpleName = str2;
    }

    public DumpArchiveEntry(String str, String str2, int i2, TYPE type) {
        setType(type);
        setName(str);
        this.simpleName = str2;
        this.ino = i2;
        this.offset = 0L;
    }
}
