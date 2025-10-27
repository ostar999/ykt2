package org.apache.commons.compress.archivers.zip;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipException;

/* loaded from: classes9.dex */
public class ExtraFieldUtils {
    private static final int WORD = 4;
    private static final Map<ZipShort, Class<?>> implementations = new ConcurrentHashMap();

    public static final class UnparseableExtraField {
        public static final int READ_KEY = 2;
        public static final int SKIP_KEY = 1;
        public static final int THROW_KEY = 0;
        private final int key;
        public static final UnparseableExtraField THROW = new UnparseableExtraField(0);
        public static final UnparseableExtraField SKIP = new UnparseableExtraField(1);
        public static final UnparseableExtraField READ = new UnparseableExtraField(2);

        private UnparseableExtraField(int i2) {
            this.key = i2;
        }

        public int getKey() {
            return this.key;
        }
    }

    static {
        register(AsiExtraField.class);
        register(X5455_ExtendedTimestamp.class);
        register(X7875_NewUnix.class);
        register(JarMarker.class);
        register(UnicodePathExtraField.class);
        register(UnicodeCommentExtraField.class);
        register(Zip64ExtendedInformationExtraField.class);
        register(X000A_NTFS.class);
        register(X0014_X509Certificates.class);
        register(X0015_CertificateIdForFile.class);
        register(X0016_CertificateIdForCentralDirectory.class);
        register(X0017_StrongEncryptionHeader.class);
        register(X0019_EncryptionRecipientCertificateList.class);
    }

    public static ZipExtraField createExtraField(ZipShort zipShort) throws IllegalAccessException, InstantiationException {
        Class<?> cls = implementations.get(zipShort);
        if (cls != null) {
            return (ZipExtraField) cls.newInstance();
        }
        UnrecognizedExtraField unrecognizedExtraField = new UnrecognizedExtraField();
        unrecognizedExtraField.setHeaderId(zipShort);
        return unrecognizedExtraField;
    }

    public static byte[] mergeCentralDirectoryData(ZipExtraField[] zipExtraFieldArr) {
        byte[] centralDirectoryData;
        boolean z2 = zipExtraFieldArr.length > 0 && (zipExtraFieldArr[zipExtraFieldArr.length - 1] instanceof UnparseableExtraFieldData);
        int length = zipExtraFieldArr.length;
        if (z2) {
            length--;
        }
        int value = length * 4;
        for (ZipExtraField zipExtraField : zipExtraFieldArr) {
            value += zipExtraField.getCentralDirectoryLength().getValue();
        }
        byte[] bArr = new byte[value];
        int length2 = 0;
        for (int i2 = 0; i2 < length; i2++) {
            System.arraycopy(zipExtraFieldArr[i2].getHeaderId().getBytes(), 0, bArr, length2, 2);
            System.arraycopy(zipExtraFieldArr[i2].getCentralDirectoryLength().getBytes(), 0, bArr, length2 + 2, 2);
            length2 += 4;
            byte[] centralDirectoryData2 = zipExtraFieldArr[i2].getCentralDirectoryData();
            if (centralDirectoryData2 != null) {
                System.arraycopy(centralDirectoryData2, 0, bArr, length2, centralDirectoryData2.length);
                length2 += centralDirectoryData2.length;
            }
        }
        if (z2 && (centralDirectoryData = zipExtraFieldArr[zipExtraFieldArr.length - 1].getCentralDirectoryData()) != null) {
            System.arraycopy(centralDirectoryData, 0, bArr, length2, centralDirectoryData.length);
        }
        return bArr;
    }

    public static byte[] mergeLocalFileDataData(ZipExtraField[] zipExtraFieldArr) {
        byte[] localFileDataData;
        boolean z2 = zipExtraFieldArr.length > 0 && (zipExtraFieldArr[zipExtraFieldArr.length - 1] instanceof UnparseableExtraFieldData);
        int length = zipExtraFieldArr.length;
        if (z2) {
            length--;
        }
        int value = length * 4;
        for (ZipExtraField zipExtraField : zipExtraFieldArr) {
            value += zipExtraField.getLocalFileDataLength().getValue();
        }
        byte[] bArr = new byte[value];
        int length2 = 0;
        for (int i2 = 0; i2 < length; i2++) {
            System.arraycopy(zipExtraFieldArr[i2].getHeaderId().getBytes(), 0, bArr, length2, 2);
            System.arraycopy(zipExtraFieldArr[i2].getLocalFileDataLength().getBytes(), 0, bArr, length2 + 2, 2);
            length2 += 4;
            byte[] localFileDataData2 = zipExtraFieldArr[i2].getLocalFileDataData();
            if (localFileDataData2 != null) {
                System.arraycopy(localFileDataData2, 0, bArr, length2, localFileDataData2.length);
                length2 += localFileDataData2.length;
            }
        }
        if (z2 && (localFileDataData = zipExtraFieldArr[zipExtraFieldArr.length - 1].getLocalFileDataData()) != null) {
            System.arraycopy(localFileDataData, 0, bArr, length2, localFileDataData.length);
        }
        return bArr;
    }

    public static ZipExtraField[] parse(byte[] bArr) throws ZipException {
        return parse(bArr, true, UnparseableExtraField.THROW);
    }

    public static void register(Class<?> cls) {
        try {
            implementations.put(((ZipExtraField) cls.newInstance()).getHeaderId(), cls);
        } catch (ClassCastException unused) {
            throw new RuntimeException(cls + " doesn't implement ZipExtraField");
        } catch (IllegalAccessException unused2) {
            throw new RuntimeException(cls + "'s no-arg constructor is not public");
        } catch (InstantiationException unused3) {
            throw new RuntimeException(cls + " is not a concrete class");
        }
    }

    public static ZipExtraField[] parse(byte[] bArr, boolean z2) throws ZipException {
        return parse(bArr, z2, UnparseableExtraField.THROW);
    }

    public static ZipExtraField[] parse(byte[] bArr, boolean z2, UnparseableExtraField unparseableExtraField) throws ZipException {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (true) {
            if (i2 > bArr.length - 4) {
                break;
            }
            ZipShort zipShort = new ZipShort(bArr, i2);
            int value = new ZipShort(bArr, i2 + 2).getValue();
            int i3 = i2 + 4;
            if (i3 + value > bArr.length) {
                int key = unparseableExtraField.getKey();
                if (key == 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("bad extra field starting at ");
                    sb.append(i2);
                    sb.append(".  Block length of ");
                    sb.append(value);
                    sb.append(" bytes exceeds remaining");
                    sb.append(" data of ");
                    sb.append((bArr.length - i2) - 4);
                    sb.append(" bytes.");
                    throw new ZipException(sb.toString());
                }
                if (key != 1) {
                    if (key == 2) {
                        UnparseableExtraFieldData unparseableExtraFieldData = new UnparseableExtraFieldData();
                        if (z2) {
                            unparseableExtraFieldData.parseFromLocalFileData(bArr, i2, bArr.length - i2);
                        } else {
                            unparseableExtraFieldData.parseFromCentralDirectoryData(bArr, i2, bArr.length - i2);
                        }
                        arrayList.add(unparseableExtraFieldData);
                    } else {
                        throw new ZipException("unknown UnparseableExtraField key: " + unparseableExtraField.getKey());
                    }
                }
            } else {
                try {
                    ZipExtraField zipExtraFieldCreateExtraField = createExtraField(zipShort);
                    if (z2) {
                        zipExtraFieldCreateExtraField.parseFromLocalFileData(bArr, i3, value);
                    } else {
                        zipExtraFieldCreateExtraField.parseFromCentralDirectoryData(bArr, i3, value);
                    }
                    arrayList.add(zipExtraFieldCreateExtraField);
                    i2 += value + 4;
                } catch (IllegalAccessException e2) {
                    throw ((ZipException) new ZipException(e2.getMessage()).initCause(e2));
                } catch (InstantiationException e3) {
                    throw ((ZipException) new ZipException(e3.getMessage()).initCause(e3));
                }
            }
        }
        return (ZipExtraField[]) arrayList.toArray(new ZipExtraField[arrayList.size()]);
    }
}
