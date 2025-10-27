package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.compress.PasswordRequiredException;

/* loaded from: classes9.dex */
class AES256SHA256Decoder extends CoderBase {
    public AES256SHA256Decoder() {
        super(new Class[0]);
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public InputStream decode(final String str, final InputStream inputStream, long j2, final Coder coder, final byte[] bArr) throws IOException {
        return new InputStream() { // from class: org.apache.commons.compress.archivers.sevenz.AES256SHA256Decoder.1
            private boolean isInitialized = false;
            private CipherInputStream cipherInputStream = null;

            private CipherInputStream init() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidAlgorithmParameterException {
                byte[] bArrDigest;
                if (this.isInitialized) {
                    return this.cipherInputStream;
                }
                byte[] bArr2 = coder.properties;
                int i2 = bArr2[0] & 255;
                int i3 = i2 & 63;
                int i4 = bArr2[1] & 255;
                int i5 = ((i2 >> 6) & 1) + (i4 & 15);
                int i6 = ((i2 >> 7) & 1) + (i4 >> 4);
                int i7 = i6 + 2;
                if (i7 + i5 > bArr2.length) {
                    throw new IOException("Salt size + IV size too long in " + str);
                }
                byte[] bArr3 = new byte[i6];
                System.arraycopy(bArr2, 2, bArr3, 0, i6);
                byte[] bArr4 = new byte[16];
                System.arraycopy(coder.properties, i7, bArr4, 0, i5);
                if (bArr == null) {
                    throw new PasswordRequiredException(str);
                }
                if (i3 == 63) {
                    bArrDigest = new byte[32];
                    System.arraycopy(bArr3, 0, bArrDigest, 0, i6);
                    byte[] bArr5 = bArr;
                    System.arraycopy(bArr5, 0, bArrDigest, i6, Math.min(bArr5.length, 32 - i6));
                } else {
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                        byte[] bArr6 = new byte[8];
                        for (long j3 = 0; j3 < (1 << i3); j3++) {
                            messageDigest.update(bArr3);
                            messageDigest.update(bArr);
                            messageDigest.update(bArr6);
                            for (int i8 = 0; i8 < 8; i8++) {
                                byte b3 = (byte) (bArr6[i8] + 1);
                                bArr6[i8] = b3;
                                if (b3 != 0) {
                                    break;
                                }
                            }
                        }
                        bArrDigest = messageDigest.digest();
                    } catch (NoSuchAlgorithmException e2) {
                        throw new IOException("SHA-256 is unsupported by your Java implementation", e2);
                    }
                }
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArrDigest, "AES");
                try {
                    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
                    cipher.init(2, secretKeySpec, new IvParameterSpec(bArr4));
                    CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
                    this.cipherInputStream = cipherInputStream;
                    this.isInitialized = true;
                    return cipherInputStream;
                } catch (GeneralSecurityException e3) {
                    throw new IOException("Decryption error (do you have the JCE Unlimited Strength Jurisdiction Policy Files installed?)", e3);
                }
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                return init().read();
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr2, int i2, int i3) throws IOException {
                return init().read(bArr2, i2, i3);
            }
        };
    }
}
