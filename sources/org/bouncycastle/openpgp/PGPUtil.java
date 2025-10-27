package org.bouncycastle.openpgp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.bcpg.MPInteger;
import org.bouncycastle.bcpg.S2K;
import org.bouncycastle.util.encoders.Base64;

/* loaded from: classes9.dex */
public class PGPUtil implements HashAlgorithmTags {
    private static final int READ_AHEAD = 60;
    private static String defProvider = "BC";

    public static class BufferedInputStreamExt extends BufferedInputStream {
        public BufferedInputStreamExt(InputStream inputStream) {
            super(inputStream);
        }

        @Override // java.io.BufferedInputStream, java.io.FilterInputStream, java.io.InputStream
        public synchronized int available() throws IOException {
            int iAvailable;
            iAvailable = super.available();
            if (iAvailable < 0) {
                iAvailable = Integer.MAX_VALUE;
            }
            return iAvailable;
        }
    }

    public static MPInteger[] dsaSigToMpi(byte[] bArr) throws PGPException {
        try {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) new ASN1InputStream(bArr).readObject();
            return new MPInteger[]{new MPInteger(((DERInteger) aSN1Sequence.getObjectAt(0)).getValue()), new MPInteger(((DERInteger) aSN1Sequence.getObjectAt(1)).getValue())};
        } catch (IOException e2) {
            throw new PGPException("exception encoding signature", e2);
        }
    }

    public static InputStream getDecoderStream(InputStream inputStream) throws IOException {
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStreamExt(inputStream);
        }
        inputStream.mark(60);
        int i2 = inputStream.read();
        if ((i2 & 128) != 0) {
            inputStream.reset();
            return inputStream;
        }
        if (!isPossiblyBase64(i2)) {
            inputStream.reset();
            return new ArmoredInputStream(inputStream);
        }
        byte[] bArr = new byte[60];
        bArr[0] = (byte) i2;
        int i3 = 1;
        int i4 = 1;
        while (i3 != 60) {
            int i5 = inputStream.read();
            if (i5 < 0) {
                break;
            }
            if (!isPossiblyBase64(i5)) {
                inputStream.reset();
                return new ArmoredInputStream(inputStream);
            }
            if (i5 != 10 && i5 != 13) {
                bArr[i4] = (byte) i5;
                i4++;
            }
            i3++;
        }
        inputStream.reset();
        if (i3 < 4) {
            return new ArmoredInputStream(inputStream);
        }
        byte[] bArr2 = new byte[8];
        System.arraycopy(bArr, 0, bArr2, 0, 8);
        return (Base64.decode(bArr2)[0] & 128) != 0 ? new ArmoredInputStream(inputStream, false) : new ArmoredInputStream(inputStream);
    }

    public static String getDefaultProvider() {
        return defProvider;
    }

    public static MessageDigest getDigestInstance(String str, Provider provider) throws NoSuchAlgorithmException {
        try {
            return MessageDigest.getInstance(str, provider);
        } catch (NoSuchAlgorithmException unused) {
            return MessageDigest.getInstance(str);
        }
    }

    public static String getDigestName(int i2) throws PGPException {
        if (i2 == 1) {
            return "MD5";
        }
        if (i2 == 2) {
            return "SHA1";
        }
        if (i2 == 3) {
            return "RIPEMD160";
        }
        if (i2 == 5) {
            return "MD2";
        }
        switch (i2) {
            case 8:
                return "SHA256";
            case 9:
                return "SHA384";
            case 10:
                return "SHA512";
            case 11:
                return "SHA224";
            default:
                throw new PGPException("unknown hash algorithm tag in getDigestName: " + i2);
        }
    }

    public static Provider getProvider(String str) throws NoSuchProviderException {
        Provider provider = Security.getProvider(str);
        if (provider != null) {
            return provider;
        }
        throw new NoSuchProviderException("provider " + str + " not found.");
    }

    public static String getSignatureName(int i2, int i3) throws PGPException {
        String str;
        if (i2 == 1 || i2 == 3) {
            str = "RSA";
        } else if (i2 == 20 || i2 == 16) {
            str = "ElGamal";
        } else {
            if (i2 != 17) {
                throw new PGPException("unknown algorithm tag in signature:" + i2);
            }
            str = "DSA";
        }
        return getDigestName(i3) + "with" + str;
    }

    public static String getSymmetricCipherName(int i2) throws PGPException {
        switch (i2) {
            case 0:
                return null;
            case 1:
                return "IDEA";
            case 2:
                return "DESEDE";
            case 3:
                return "CAST5";
            case 4:
                return "Blowfish";
            case 5:
                return "SAFER";
            case 6:
                return "DES";
            case 7:
            case 8:
            case 9:
                return "AES";
            case 10:
                return "Twofish";
            default:
                throw new PGPException("unknown symmetric algorithm: " + i2);
        }
    }

    private static boolean isPossiblyBase64(int i2) {
        return (i2 >= 65 && i2 <= 90) || (i2 >= 97 && i2 <= 122) || ((i2 >= 48 && i2 <= 57) || i2 == 43 || i2 == 47 || i2 == 13 || i2 == 10);
    }

    public static SecretKey makeKeyFromPassPhrase(int i2, S2K s2k, char[] cArr, String str) throws PGPException, NoSuchProviderException {
        return makeKeyFromPassPhrase(i2, s2k, cArr, getProvider(str));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0048 A[LOOP:0: B:16:0x0045->B:18:0x0048, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0114 A[LOOP:6: B:64:0x0112->B:65:0x0114, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static javax.crypto.SecretKey makeKeyFromPassPhrase(int r16, org.bouncycastle.bcpg.S2K r17, char[] r18, java.security.Provider r19) throws org.bouncycastle.openpgp.PGPException, java.security.NoSuchProviderException {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.openpgp.PGPUtil.makeKeyFromPassPhrase(int, org.bouncycastle.bcpg.S2K, char[], java.security.Provider):javax.crypto.SecretKey");
    }

    public static SecretKey makeKeyFromPassPhrase(int i2, char[] cArr, String str) throws NoSuchProviderException, PGPException {
        return makeKeyFromPassPhrase(i2, (S2K) null, cArr, str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static SecretKey makeRandomKey(int i2, SecureRandom secureRandom) throws PGPException {
        int i3 = 256;
        String str = "AES";
        switch (i2) {
            case 1:
                str = "IDEA";
                i3 = 128;
                byte[] bArr = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr);
                return new SecretKeySpec(bArr, str);
            case 2:
                str = "DES_EDE";
                i3 = 192;
                byte[] bArr2 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr2);
                return new SecretKeySpec(bArr2, str);
            case 3:
                str = "CAST5";
                i3 = 128;
                byte[] bArr22 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr22);
                return new SecretKeySpec(bArr22, str);
            case 4:
                str = "Blowfish";
                i3 = 128;
                byte[] bArr222 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr222);
                return new SecretKeySpec(bArr222, str);
            case 5:
                str = "SAFER";
                i3 = 128;
                byte[] bArr2222 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr2222);
                return new SecretKeySpec(bArr2222, str);
            case 6:
                i3 = 64;
                str = "DES";
                byte[] bArr22222 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr22222);
                return new SecretKeySpec(bArr22222, str);
            case 7:
                i3 = 128;
                byte[] bArr222222 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr222222);
                return new SecretKeySpec(bArr222222, str);
            case 8:
                i3 = 192;
                byte[] bArr2222222 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr2222222);
                return new SecretKeySpec(bArr2222222, str);
            case 9:
                byte[] bArr22222222 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr22222222);
                return new SecretKeySpec(bArr22222222, str);
            case 10:
                str = "Twofish";
                byte[] bArr222222222 = new byte[(i3 + 7) / 8];
                secureRandom.nextBytes(bArr222222222);
                return new SecretKeySpec(bArr222222222, str);
            default:
                throw new PGPException("unknown symmetric algorithm: " + i2);
        }
    }

    private static void pipeFileContents(File file, OutputStream outputStream, int i2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[i2];
        while (true) {
            int i3 = fileInputStream.read(bArr);
            if (i3 <= 0) {
                outputStream.close();
                fileInputStream.close();
                return;
            }
            outputStream.write(bArr, 0, i3);
        }
    }

    public static void setDefaultProvider(String str) {
        defProvider = str;
    }

    public static void writeFileToLiteralData(OutputStream outputStream, char c3, File file) throws IOException {
        pipeFileContents(file, new PGPLiteralDataGenerator().open(outputStream, c3, file.getName(), file.length(), new Date(file.lastModified())), 4096);
    }

    public static void writeFileToLiteralData(OutputStream outputStream, char c3, File file, byte[] bArr) throws IOException {
        pipeFileContents(file, new PGPLiteralDataGenerator().open(outputStream, c3, file.getName(), new Date(file.lastModified()), bArr), bArr.length);
    }
}
