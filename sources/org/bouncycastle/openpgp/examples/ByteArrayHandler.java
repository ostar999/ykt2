package org.bouncycastle.openpgp.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPBEEncryptedData;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes9.dex */
public class ByteArrayHandler {
    private static byte[] compress(byte[] bArr, String str, int i2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PGPCompressedDataGenerator pGPCompressedDataGenerator = new PGPCompressedDataGenerator(i2);
        OutputStream outputStreamOpen = new PGPLiteralDataGenerator().open(pGPCompressedDataGenerator.open(byteArrayOutputStream), 'b', str, bArr.length, new Date());
        outputStreamOpen.write(bArr);
        outputStreamOpen.close();
        pGPCompressedDataGenerator.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decrypt(byte[] bArr, char[] cArr) throws IOException, PGPException, NoSuchProviderException {
        PGPObjectFactory pGPObjectFactory = new PGPObjectFactory(PGPUtil.getDecoderStream(new ByteArrayInputStream(bArr)));
        Object objNextObject = pGPObjectFactory.nextObject();
        if (!(objNextObject instanceof PGPEncryptedDataList)) {
            objNextObject = pGPObjectFactory.nextObject();
        }
        return Streams.readAll(((PGPLiteralData) new PGPObjectFactory(((PGPCompressedData) new PGPObjectFactory(((PGPPBEEncryptedData) ((PGPEncryptedDataList) objNextObject).get(0)).getDataStream(cArr, "BC")).nextObject()).getDataStream()).nextObject()).getInputStream());
    }

    public static byte[] encrypt(byte[] bArr, char[] cArr, String str, int i2, boolean z2) throws IOException, NoSuchProviderException, PGPException {
        if (str == null) {
            str = "_CONSOLE";
        }
        byte[] bArrCompress = compress(bArr, str, 1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStream armoredOutputStream = z2 ? new ArmoredOutputStream(byteArrayOutputStream) : byteArrayOutputStream;
        PGPEncryptedDataGenerator pGPEncryptedDataGenerator = new PGPEncryptedDataGenerator(i2, new SecureRandom(), "BC");
        pGPEncryptedDataGenerator.addMethod(cArr);
        OutputStream outputStreamOpen = pGPEncryptedDataGenerator.open(armoredOutputStream, bArrCompress.length);
        outputStreamOpen.write(bArrCompress);
        outputStreamOpen.close();
        if (z2) {
            armoredOutputStream.close();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] strArr) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        char[] charArray = "Dick Beck".toCharArray();
        byte[] bytes = "Hello world".getBytes();
        System.out.println("Starting PGP test");
        byte[] bArrEncrypt = encrypt(bytes, charArray, "iway", 3, true);
        System.out.println("\nencrypted data = '" + new String(bArrEncrypt) + "'");
        byte[] bArrDecrypt = decrypt(bArrEncrypt, charArray);
        System.out.println("\ndecrypted data = '" + new String(bArrDecrypt) + "'");
        byte[] bArrEncrypt2 = encrypt(bytes, charArray, "iway", 9, false);
        System.out.println("\nencrypted data = '" + new String(Hex.encode(bArrEncrypt2)) + "'");
        byte[] bArrDecrypt2 = decrypt(bArrEncrypt2, charArray);
        System.out.println("\ndecrypted data = '" + new String(bArrDecrypt2) + "'");
    }
}
