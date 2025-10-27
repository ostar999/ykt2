package org.apache.commons.compress.archivers.zip;

import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.yikaobang.yixue.R2;
import java.io.Serializable;
import java.util.Date;
import java.util.zip.ZipException;

/* loaded from: classes9.dex */
public class X5455_ExtendedTimestamp implements ZipExtraField, Cloneable, Serializable {
    public static final byte ACCESS_TIME_BIT = 2;
    public static final byte CREATE_TIME_BIT = 4;
    private static final ZipShort HEADER_ID = new ZipShort(R2.layout.exo_track_selection_dialog);
    public static final byte MODIFY_TIME_BIT = 1;
    private static final long serialVersionUID = 1;
    private ZipLong accessTime;
    private boolean bit0_modifyTimePresent;
    private boolean bit1_accessTimePresent;
    private boolean bit2_createTimePresent;
    private ZipLong createTime;
    private byte flags;
    private ZipLong modifyTime;

    private static ZipLong dateToZipLong(Date date) {
        if (date == null) {
            return null;
        }
        long time = date.getTime() / 1000;
        if (time < IjkMediaMeta.AV_CH_WIDE_RIGHT) {
            return new ZipLong(time);
        }
        throw new IllegalArgumentException("Cannot set an X5455 timestamp larger than 2^32: " + time);
    }

    private void reset() {
        setFlags((byte) 0);
        this.modifyTime = null;
        this.accessTime = null;
        this.createTime = null;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof X5455_ExtendedTimestamp)) {
            return false;
        }
        X5455_ExtendedTimestamp x5455_ExtendedTimestamp = (X5455_ExtendedTimestamp) obj;
        if ((this.flags & 7) != (x5455_ExtendedTimestamp.flags & 7)) {
            return false;
        }
        ZipLong zipLong = this.modifyTime;
        ZipLong zipLong2 = x5455_ExtendedTimestamp.modifyTime;
        if (zipLong != zipLong2 && (zipLong == null || !zipLong.equals(zipLong2))) {
            return false;
        }
        ZipLong zipLong3 = this.accessTime;
        ZipLong zipLong4 = x5455_ExtendedTimestamp.accessTime;
        if (zipLong3 != zipLong4 && (zipLong3 == null || !zipLong3.equals(zipLong4))) {
            return false;
        }
        ZipLong zipLong5 = this.createTime;
        ZipLong zipLong6 = x5455_ExtendedTimestamp.createTime;
        return zipLong5 == zipLong6 || (zipLong5 != null && zipLong5.equals(zipLong6));
    }

    public Date getAccessJavaTime() {
        if (this.accessTime != null) {
            return new Date(this.accessTime.getValue() * 1000);
        }
        return null;
    }

    public ZipLong getAccessTime() {
        return this.accessTime;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getCentralDirectoryData() {
        int value = getCentralDirectoryLength().getValue();
        byte[] bArr = new byte[value];
        System.arraycopy(getLocalFileDataData(), 0, bArr, 0, value);
        return bArr;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getCentralDirectoryLength() {
        return new ZipShort((this.bit0_modifyTimePresent ? 4 : 0) + 1);
    }

    public Date getCreateJavaTime() {
        if (this.createTime != null) {
            return new Date(this.createTime.getValue() * 1000);
        }
        return null;
    }

    public ZipLong getCreateTime() {
        return this.createTime;
    }

    public byte getFlags() {
        return this.flags;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getLocalFileDataData() {
        ZipLong zipLong;
        ZipLong zipLong2;
        byte[] bArr = new byte[getLocalFileDataLength().getValue()];
        bArr[0] = 0;
        int i2 = 1;
        if (this.bit0_modifyTimePresent) {
            bArr[0] = (byte) (0 | 1);
            System.arraycopy(this.modifyTime.getBytes(), 0, bArr, 1, 4);
            i2 = 5;
        }
        if (this.bit1_accessTimePresent && (zipLong2 = this.accessTime) != null) {
            bArr[0] = (byte) (bArr[0] | 2);
            System.arraycopy(zipLong2.getBytes(), 0, bArr, i2, 4);
            i2 += 4;
        }
        if (this.bit2_createTimePresent && (zipLong = this.createTime) != null) {
            bArr[0] = (byte) (bArr[0] | 4);
            System.arraycopy(zipLong.getBytes(), 0, bArr, i2, 4);
        }
        return bArr;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getLocalFileDataLength() {
        return new ZipShort((this.bit0_modifyTimePresent ? 4 : 0) + 1 + ((!this.bit1_accessTimePresent || this.accessTime == null) ? 0 : 4) + ((!this.bit2_createTimePresent || this.createTime == null) ? 0 : 4));
    }

    public Date getModifyJavaTime() {
        if (this.modifyTime != null) {
            return new Date(this.modifyTime.getValue() * 1000);
        }
        return null;
    }

    public ZipLong getModifyTime() {
        return this.modifyTime;
    }

    public int hashCode() {
        int iRotateLeft = (this.flags & 7) * (-123);
        ZipLong zipLong = this.modifyTime;
        if (zipLong != null) {
            iRotateLeft ^= zipLong.hashCode();
        }
        ZipLong zipLong2 = this.accessTime;
        if (zipLong2 != null) {
            iRotateLeft ^= Integer.rotateLeft(zipLong2.hashCode(), 11);
        }
        ZipLong zipLong3 = this.createTime;
        return zipLong3 != null ? iRotateLeft ^ Integer.rotateLeft(zipLong3.hashCode(), 22) : iRotateLeft;
    }

    public boolean isBit0_modifyTimePresent() {
        return this.bit0_modifyTimePresent;
    }

    public boolean isBit1_accessTimePresent() {
        return this.bit1_accessTimePresent;
    }

    public boolean isBit2_createTimePresent() {
        return this.bit2_createTimePresent;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) throws ZipException {
        reset();
        parseFromLocalFileData(bArr, i2, i3);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i2, int i3) throws ZipException {
        int i4;
        reset();
        int i5 = i3 + i2;
        int i6 = i2 + 1;
        setFlags(bArr[i2]);
        if (this.bit0_modifyTimePresent) {
            this.modifyTime = new ZipLong(bArr, i6);
            i6 += 4;
        }
        if (this.bit1_accessTimePresent && (i4 = i6 + 4) <= i5) {
            this.accessTime = new ZipLong(bArr, i6);
            i6 = i4;
        }
        if (!this.bit2_createTimePresent || i6 + 4 > i5) {
            return;
        }
        this.createTime = new ZipLong(bArr, i6);
    }

    public void setAccessJavaTime(Date date) {
        setAccessTime(dateToZipLong(date));
    }

    public void setAccessTime(ZipLong zipLong) {
        this.bit1_accessTimePresent = zipLong != null;
        byte b3 = this.flags;
        this.flags = (byte) (zipLong != null ? b3 | 2 : b3 & (-3));
        this.accessTime = zipLong;
    }

    public void setCreateJavaTime(Date date) {
        setCreateTime(dateToZipLong(date));
    }

    public void setCreateTime(ZipLong zipLong) {
        this.bit2_createTimePresent = zipLong != null;
        byte b3 = this.flags;
        this.flags = (byte) (zipLong != null ? b3 | 4 : b3 & (-5));
        this.createTime = zipLong;
    }

    public void setFlags(byte b3) {
        this.flags = b3;
        this.bit0_modifyTimePresent = (b3 & 1) == 1;
        this.bit1_accessTimePresent = (b3 & 2) == 2;
        this.bit2_createTimePresent = (b3 & 4) == 4;
    }

    public void setModifyJavaTime(Date date) {
        setModifyTime(dateToZipLong(date));
    }

    public void setModifyTime(ZipLong zipLong) {
        this.bit0_modifyTimePresent = zipLong != null;
        this.flags = (byte) (zipLong != null ? 1 | this.flags : this.flags & (-2));
        this.modifyTime = zipLong;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0x5455 Zip Extra Field: Flags=");
        sb.append(Integer.toBinaryString(ZipUtil.unsignedIntToSignedByte(this.flags)));
        sb.append(" ");
        if (this.bit0_modifyTimePresent && this.modifyTime != null) {
            Date modifyJavaTime = getModifyJavaTime();
            sb.append(" Modify:[");
            sb.append(modifyJavaTime);
            sb.append("] ");
        }
        if (this.bit1_accessTimePresent && this.accessTime != null) {
            Date accessJavaTime = getAccessJavaTime();
            sb.append(" Access:[");
            sb.append(accessJavaTime);
            sb.append("] ");
        }
        if (this.bit2_createTimePresent && this.createTime != null) {
            Date createJavaTime = getCreateJavaTime();
            sb.append(" Create:[");
            sb.append(createJavaTime);
            sb.append("] ");
        }
        return sb.toString();
    }
}
