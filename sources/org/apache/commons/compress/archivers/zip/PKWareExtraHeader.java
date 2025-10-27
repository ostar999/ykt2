package org.apache.commons.compress.archivers.zip;

import com.tencent.rtmp.sharp.jni.TraeAudioManager;
import com.umeng.commonsdk.internal.a;
import com.yikaobang.yixue.R2;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes9.dex */
public abstract class PKWareExtraHeader implements ZipExtraField {
    private byte[] centralData;
    private final ZipShort headerId;
    private byte[] localData;

    public enum EncryptionAlgorithm {
        DES(R2.style.Widget_Material3_MaterialCalendar_DayOfWeekLabel),
        RC2pre52(R2.style.Widget_Material3_MaterialCalendar_DayTextView),
        TripleDES168(R2.style.Widget_Material3_MaterialCalendar_Fullscreen),
        TripleDES192(R2.style.Widget_Material3_MaterialCalendar_HeaderTitle),
        AES128(R2.style.Widget_Material3_MaterialCalendar_Year),
        AES192(R2.style.Widget_Material3_MaterialCalendar_Year_Selected),
        AES256(R2.style.Widget_Material3_MaterialCalendar_Year_Today),
        RC2(R2.style.person_info_text_style),
        RC4(R2.styleable.AppCompatTheme_buttonBarStyle),
        UNKNOWN(65535);

        private static final Map<Integer, EncryptionAlgorithm> codeToEnum;
        private final int code;

        static {
            HashMap map = new HashMap();
            for (EncryptionAlgorithm encryptionAlgorithm : values()) {
                map.put(Integer.valueOf(encryptionAlgorithm.getCode()), encryptionAlgorithm);
            }
            codeToEnum = Collections.unmodifiableMap(map);
        }

        EncryptionAlgorithm(int i2) {
            this.code = i2;
        }

        public static EncryptionAlgorithm getAlgorithmByCode(int i2) {
            return codeToEnum.get(Integer.valueOf(i2));
        }

        public int getCode() {
            return this.code;
        }
    }

    public enum HashAlgorithm {
        NONE(0),
        CRC32(1),
        MD5(a.f23174h),
        SHA1(32772),
        RIPEND160(32775),
        SHA256(TraeAudioManager.TraeAudioManagerLooper.MESSAGE_VOICECALLPREPROCESS),
        SHA384(32781),
        SHA512(32782);

        private static final Map<Integer, HashAlgorithm> codeToEnum;
        private final int code;

        static {
            HashMap map = new HashMap();
            for (HashAlgorithm hashAlgorithm : values()) {
                map.put(Integer.valueOf(hashAlgorithm.getCode()), hashAlgorithm);
            }
            codeToEnum = Collections.unmodifiableMap(map);
        }

        HashAlgorithm(int i2) {
            this.code = i2;
        }

        public static HashAlgorithm getAlgorithmByCode(int i2) {
            return codeToEnum.get(Integer.valueOf(i2));
        }

        public int getCode() {
            return this.code;
        }
    }

    public PKWareExtraHeader(ZipShort zipShort) {
        this.headerId = zipShort;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getCentralDirectoryData() {
        byte[] bArr = this.centralData;
        return bArr != null ? ZipUtil.copy(bArr) : getLocalFileDataData();
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getCentralDirectoryLength() {
        byte[] bArr = this.centralData;
        return bArr != null ? new ZipShort(bArr.length) : getLocalFileDataLength();
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return this.headerId;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getLocalFileDataData() {
        return ZipUtil.copy(this.localData);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getLocalFileDataLength() {
        byte[] bArr = this.localData;
        return new ZipShort(bArr != null ? bArr.length : 0);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        setCentralDirectoryData(bArr2);
        if (this.localData == null) {
            setLocalFileDataData(bArr2);
        }
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        setLocalFileDataData(bArr2);
    }

    public void setCentralDirectoryData(byte[] bArr) {
        this.centralData = ZipUtil.copy(bArr);
    }

    public void setLocalFileDataData(byte[] bArr) {
        this.localData = ZipUtil.copy(bArr);
    }
}
