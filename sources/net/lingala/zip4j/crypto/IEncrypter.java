package net.lingala.zip4j.crypto;

import net.lingala.zip4j.exception.ZipException;

/* loaded from: classes9.dex */
public interface IEncrypter {
    int encryptData(byte[] bArr) throws ZipException;

    int encryptData(byte[] bArr, int i2, int i3) throws ZipException;
}
