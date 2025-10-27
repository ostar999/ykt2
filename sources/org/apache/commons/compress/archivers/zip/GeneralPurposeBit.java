package org.apache.commons.compress.archivers.zip;

/* loaded from: classes9.dex */
public final class GeneralPurposeBit implements Cloneable {
    private static final int DATA_DESCRIPTOR_FLAG = 8;
    private static final int ENCRYPTION_FLAG = 1;
    private static final int NUMBER_OF_SHANNON_FANO_TREES_FLAG = 4;
    private static final int SLIDING_DICTIONARY_SIZE_FLAG = 2;
    private static final int STRONG_ENCRYPTION_FLAG = 64;
    public static final int UFT8_NAMES_FLAG = 2048;
    private int numberOfShannonFanoTrees;
    private int slidingDictionarySize;
    private boolean languageEncodingFlag = false;
    private boolean dataDescriptorFlag = false;
    private boolean encryptionFlag = false;
    private boolean strongEncryptionFlag = false;

    public static GeneralPurposeBit parse(byte[] bArr, int i2) {
        int value = ZipShort.getValue(bArr, i2);
        GeneralPurposeBit generalPurposeBit = new GeneralPurposeBit();
        generalPurposeBit.useDataDescriptor((value & 8) != 0);
        generalPurposeBit.useUTF8ForNames((value & 2048) != 0);
        generalPurposeBit.useStrongEncryption((value & 64) != 0);
        generalPurposeBit.useEncryption((value & 1) != 0);
        generalPurposeBit.slidingDictionarySize = (value & 2) != 0 ? 8192 : 4096;
        generalPurposeBit.numberOfShannonFanoTrees = (value & 4) != 0 ? 3 : 2;
        return generalPurposeBit;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException("GeneralPurposeBit is not Cloneable?", e2);
        }
    }

    public byte[] encode() {
        byte[] bArr = new byte[2];
        encode(bArr, 0);
        return bArr;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GeneralPurposeBit)) {
            return false;
        }
        GeneralPurposeBit generalPurposeBit = (GeneralPurposeBit) obj;
        return generalPurposeBit.encryptionFlag == this.encryptionFlag && generalPurposeBit.strongEncryptionFlag == this.strongEncryptionFlag && generalPurposeBit.languageEncodingFlag == this.languageEncodingFlag && generalPurposeBit.dataDescriptorFlag == this.dataDescriptorFlag;
    }

    public int getNumberOfShannonFanoTrees() {
        return this.numberOfShannonFanoTrees;
    }

    public int getSlidingDictionarySize() {
        return this.slidingDictionarySize;
    }

    public int hashCode() {
        return (((((((this.encryptionFlag ? 1 : 0) * 17) + (this.strongEncryptionFlag ? 1 : 0)) * 13) + (this.languageEncodingFlag ? 1 : 0)) * 7) + (this.dataDescriptorFlag ? 1 : 0)) * 3;
    }

    public void useDataDescriptor(boolean z2) {
        this.dataDescriptorFlag = z2;
    }

    public void useEncryption(boolean z2) {
        this.encryptionFlag = z2;
    }

    public void useStrongEncryption(boolean z2) {
        this.strongEncryptionFlag = z2;
        if (z2) {
            useEncryption(true);
        }
    }

    public void useUTF8ForNames(boolean z2) {
        this.languageEncodingFlag = z2;
    }

    public boolean usesDataDescriptor() {
        return this.dataDescriptorFlag;
    }

    public boolean usesEncryption() {
        return this.encryptionFlag;
    }

    public boolean usesStrongEncryption() {
        return this.encryptionFlag && this.strongEncryptionFlag;
    }

    public boolean usesUTF8ForNames() {
        return this.languageEncodingFlag;
    }

    public void encode(byte[] bArr, int i2) {
        ZipShort.putShort((this.dataDescriptorFlag ? 8 : 0) | (this.languageEncodingFlag ? 2048 : 0) | (this.encryptionFlag ? 1 : 0) | (this.strongEncryptionFlag ? 64 : 0), bArr, i2);
    }
}
