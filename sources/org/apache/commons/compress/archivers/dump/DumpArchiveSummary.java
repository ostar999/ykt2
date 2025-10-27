package org.apache.commons.compress.archivers.dump;

import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.compress.archivers.zip.ZipEncoding;

/* loaded from: classes9.dex */
public class DumpArchiveSummary {
    private String devname;
    private long dumpDate;
    private String filesys;
    private int firstrec;
    private int flags;
    private String hostname;
    private String label;
    private int level;
    private int ntrec;
    private long previousDumpDate;
    private int volume;

    public DumpArchiveSummary(byte[] bArr, ZipEncoding zipEncoding) throws IOException {
        this.dumpDate = DumpArchiveUtil.convert32(bArr, 4) * 1000;
        this.previousDumpDate = DumpArchiveUtil.convert32(bArr, 8) * 1000;
        this.volume = DumpArchiveUtil.convert32(bArr, 12);
        this.label = DumpArchiveUtil.decode(zipEncoding, bArr, R2.attr.bl_shape_alpha, 16).trim();
        this.level = DumpArchiveUtil.convert32(bArr, R2.attr.bl_unCheckable_gradient_centerColor);
        this.filesys = DumpArchiveUtil.decode(zipEncoding, bArr, 696, 64).trim();
        this.devname = DumpArchiveUtil.decode(zipEncoding, bArr, R2.attr.bl_unSelected_gradient_angle, 64).trim();
        this.hostname = DumpArchiveUtil.decode(zipEncoding, bArr, R2.attr.boxCornerRadiusBottomEnd, 64).trim();
        this.flags = DumpArchiveUtil.convert32(bArr, R2.attr.cardUseCompatPadding);
        this.firstrec = DumpArchiveUtil.convert32(bArr, R2.attr.carousel_firstView);
        this.ntrec = DumpArchiveUtil.convert32(bArr, R2.attr.carousel_previousState);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass().equals(getClass())) {
            DumpArchiveSummary dumpArchiveSummary = (DumpArchiveSummary) obj;
            if (this.dumpDate == dumpArchiveSummary.dumpDate && getHostname() != null && getHostname().equals(dumpArchiveSummary.getHostname()) && getDevname() != null && getDevname().equals(dumpArchiveSummary.getDevname())) {
                return true;
            }
        }
        return false;
    }

    public String getDevname() {
        return this.devname;
    }

    public Date getDumpDate() {
        return new Date(this.dumpDate);
    }

    public String getFilesystem() {
        return this.filesys;
    }

    public int getFirstRecord() {
        return this.firstrec;
    }

    public int getFlags() {
        return this.flags;
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getLabel() {
        return this.label;
    }

    public int getLevel() {
        return this.level;
    }

    public int getNTRec() {
        return this.ntrec;
    }

    public Date getPreviousDumpDate() {
        return new Date(this.previousDumpDate);
    }

    public int getVolume() {
        return this.volume;
    }

    public int hashCode() {
        int iHashCode = (int) ((this.label != null ? r0.hashCode() : 17) + (this.dumpDate * 31));
        String str = this.hostname;
        if (str != null) {
            iHashCode = (str.hashCode() * 31) + 17;
        }
        String str2 = this.devname;
        return str2 != null ? (str2.hashCode() * 31) + 17 : iHashCode;
    }

    public boolean isCompressed() {
        return (this.flags & 128) == 128;
    }

    public boolean isExtendedAttributes() {
        return (this.flags & 32768) == 32768;
    }

    public boolean isMetaDataOnly() {
        return (this.flags & 256) == 256;
    }

    public boolean isNewHeader() {
        return (this.flags & 1) == 1;
    }

    public boolean isNewInode() {
        return (this.flags & 2) == 2;
    }

    public void setDevname(String str) {
        this.devname = str;
    }

    public void setDumpDate(Date date) {
        this.dumpDate = date.getTime();
    }

    public void setFilesystem(String str) {
        this.filesys = str;
    }

    public void setFirstRecord(int i2) {
        this.firstrec = i2;
    }

    public void setFlags(int i2) {
        this.flags = i2;
    }

    public void setHostname(String str) {
        this.hostname = str;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void setLevel(int i2) {
        this.level = i2;
    }

    public void setNTRec(int i2) {
        this.ntrec = i2;
    }

    public void setPreviousDumpDate(Date date) {
        this.previousDumpDate = date.getTime();
    }

    public void setVolume(int i2) {
        this.volume = i2;
    }
}
