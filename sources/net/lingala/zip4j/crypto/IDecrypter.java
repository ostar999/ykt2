package net.lingala.zip4j.crypto;

import net.lingala.zip4j.exception.ZipException;

/* loaded from: classes9.dex */
public interface IDecrypter {
    int decryptData(byte[] bArr) throws ZipException;

    int decryptData(byte[] bArr, int i2, int i3) throws ZipException;
}
