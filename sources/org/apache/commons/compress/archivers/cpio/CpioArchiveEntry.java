package org.apache.commons.compress.archivers.cpio;

import java.io.File;
import java.util.Date;
import net.lingala.zip4j.util.InternalZipConstants;
import org.apache.commons.compress.archivers.ArchiveEntry;

/* loaded from: classes9.dex */
public class CpioArchiveEntry implements CpioConstants, ArchiveEntry {
    private final int alignmentBoundary;
    private long chksum;
    private final short fileFormat;
    private long filesize;
    private long gid;
    private final int headerSize;
    private long inode;
    private long maj;
    private long min;
    private long mode;
    private long mtime;
    private String name;
    private long nlink;
    private long rmaj;
    private long rmin;
    private long uid;

    public CpioArchiveEntry(short s2) {
        this.chksum = 0L;
        this.filesize = 0L;
        this.gid = 0L;
        this.inode = 0L;
        this.maj = 0L;
        this.min = 0L;
        this.mode = 0L;
        this.mtime = 0L;
        this.nlink = 0L;
        this.rmaj = 0L;
        this.rmin = 0L;
        this.uid = 0L;
        if (s2 == 1 || s2 == 2) {
            this.headerSize = 110;
            this.alignmentBoundary = 4;
        } else if (s2 == 4) {
            this.headerSize = 76;
            this.alignmentBoundary = 0;
        } else {
            if (s2 != 8) {
                throw new IllegalArgumentException("Unknown header type");
            }
            this.headerSize = 26;
            this.alignmentBoundary = 2;
        }
        this.fileFormat = s2;
    }

    private void checkNewFormat() {
        if ((this.fileFormat & 3) == 0) {
            throw new UnsupportedOperationException();
        }
    }

    private void checkOldFormat() {
        if ((this.fileFormat & 12) == 0) {
            throw new UnsupportedOperationException();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CpioArchiveEntry cpioArchiveEntry = (CpioArchiveEntry) obj;
        String str = this.name;
        if (str == null) {
            if (cpioArchiveEntry.name != null) {
                return false;
            }
        } else if (!str.equals(cpioArchiveEntry.name)) {
            return false;
        }
        return true;
    }

    public int getAlignmentBoundary() {
        return this.alignmentBoundary;
    }

    public long getChksum() {
        checkNewFormat();
        return this.chksum;
    }

    public int getDataPadCount() {
        int i2;
        int i3 = this.alignmentBoundary;
        if (i3 != 0 && (i2 = (int) (this.filesize % i3)) > 0) {
            return i3 - i2;
        }
        return 0;
    }

    public long getDevice() {
        checkOldFormat();
        return this.min;
    }

    public long getDeviceMaj() {
        checkNewFormat();
        return this.maj;
    }

    public long getDeviceMin() {
        checkNewFormat();
        return this.min;
    }

    public short getFormat() {
        return this.fileFormat;
    }

    public long getGID() {
        return this.gid;
    }

    public int getHeaderPadCount() {
        if (this.alignmentBoundary == 0) {
            return 0;
        }
        int length = this.headerSize + 1;
        String str = this.name;
        if (str != null) {
            length += str.length();
        }
        int i2 = this.alignmentBoundary;
        int i3 = length % i2;
        if (i3 > 0) {
            return i2 - i3;
        }
        return 0;
    }

    public int getHeaderSize() {
        return this.headerSize;
    }

    public long getInode() {
        return this.inode;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public Date getLastModifiedDate() {
        return new Date(getTime() * 1000);
    }

    public long getMode() {
        if (this.mode != 0 || CpioConstants.CPIO_TRAILER.equals(this.name)) {
            return this.mode;
        }
        return 32768L;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public String getName() {
        return this.name;
    }

    public long getNumberOfLinks() {
        long j2 = this.nlink;
        return j2 == 0 ? isDirectory() ? 2L : 1L : j2;
    }

    public long getRemoteDevice() {
        checkOldFormat();
        return this.rmin;
    }

    public long getRemoteDeviceMaj() {
        checkNewFormat();
        return this.rmaj;
    }

    public long getRemoteDeviceMin() {
        checkNewFormat();
        return this.rmin;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public long getSize() {
        return this.filesize;
    }

    public long getTime() {
        return this.mtime;
    }

    public long getUID() {
        return this.uid;
    }

    public int hashCode() {
        String str = this.name;
        return 31 + (str == null ? 0 : str.hashCode());
    }

    public boolean isBlockDevice() {
        return CpioUtil.fileType(this.mode) == 24576;
    }

    public boolean isCharacterDevice() {
        return CpioUtil.fileType(this.mode) == 8192;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveEntry
    public boolean isDirectory() {
        return CpioUtil.fileType(this.mode) == 16384;
    }

    public boolean isNetwork() {
        return CpioUtil.fileType(this.mode) == 36864;
    }

    public boolean isPipe() {
        return CpioUtil.fileType(this.mode) == 4096;
    }

    public boolean isRegularFile() {
        return CpioUtil.fileType(this.mode) == 32768;
    }

    public boolean isSocket() {
        return CpioUtil.fileType(this.mode) == 49152;
    }

    public boolean isSymbolicLink() {
        return CpioUtil.fileType(this.mode) == 40960;
    }

    public void setChksum(long j2) {
        checkNewFormat();
        this.chksum = j2;
    }

    public void setDevice(long j2) {
        checkOldFormat();
        this.min = j2;
    }

    public void setDeviceMaj(long j2) {
        checkNewFormat();
        this.maj = j2;
    }

    public void setDeviceMin(long j2) {
        checkNewFormat();
        this.min = j2;
    }

    public void setGID(long j2) {
        this.gid = j2;
    }

    public void setInode(long j2) {
        this.inode = j2;
    }

    public void setMode(long j2) {
        long j3 = 61440 & j2;
        switch ((int) j3) {
            case 4096:
            case 8192:
            case 16384:
            case 24576:
            case 32768:
            case CpioConstants.C_ISNWK /* 36864 */:
            case 40960:
            case CpioConstants.C_ISSOCK /* 49152 */:
                this.mode = j2;
                return;
            default:
                throw new IllegalArgumentException("Unknown mode. Full: " + Long.toHexString(j2) + " Masked: " + Long.toHexString(j3));
        }
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setNumberOfLinks(long j2) {
        this.nlink = j2;
    }

    public void setRemoteDevice(long j2) {
        checkOldFormat();
        this.rmin = j2;
    }

    public void setRemoteDeviceMaj(long j2) {
        checkNewFormat();
        this.rmaj = j2;
    }

    public void setRemoteDeviceMin(long j2) {
        checkNewFormat();
        this.rmin = j2;
    }

    public void setSize(long j2) {
        if (j2 >= 0 && j2 <= InternalZipConstants.ZIP_64_LIMIT) {
            this.filesize = j2;
            return;
        }
        throw new IllegalArgumentException("invalid entry size <" + j2 + ">");
    }

    public void setTime(long j2) {
        this.mtime = j2;
    }

    public void setUID(long j2) {
        this.uid = j2;
    }

    public CpioArchiveEntry(String str) {
        this((short) 1, str);
    }

    public CpioArchiveEntry(short s2, String str) {
        this(s2);
        this.name = str;
    }

    public CpioArchiveEntry(String str, long j2) {
        this(str);
        setSize(j2);
    }

    public CpioArchiveEntry(short s2, String str, long j2) {
        this(s2, str);
        setSize(j2);
    }

    public CpioArchiveEntry(File file, String str) {
        this((short) 1, file, str);
    }

    public CpioArchiveEntry(short s2, File file, String str) {
        this(s2, str, file.isFile() ? file.length() : 0L);
        if (file.isDirectory()) {
            setMode(16384L);
        } else if (file.isFile()) {
            setMode(32768L);
        } else {
            throw new IllegalArgumentException("Cannot determine type of file " + file.getName());
        }
        setTime(file.lastModified() / 1000);
    }
}
