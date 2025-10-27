package com.tencent.open.utils;

import cn.hutool.core.text.StrPool;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ProtocolException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Properties;
import java.util.zip.ZipException;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes6.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static final l f20653a = new l(InternalZipConstants.ENDSIG);

    /* renamed from: b, reason: collision with root package name */
    private static final m f20654b = new m(38651);

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        Properties f20655a;

        /* renamed from: b, reason: collision with root package name */
        byte[] f20656b;

        private a() {
            this.f20655a = new Properties();
        }

        public void a(byte[] bArr) throws IOException {
            if (bArr == null) {
                return;
            }
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
            int length = b.f20654b.a().length;
            byte[] bArr2 = new byte[length];
            byteBufferWrap.get(bArr2);
            if (!b.f20654b.equals(new m(bArr2))) {
                throw new ProtocolException("unknow protocl [" + Arrays.toString(bArr) + StrPool.BRACKET_END);
            }
            if (bArr.length - length <= 2) {
                return;
            }
            byte[] bArr3 = new byte[2];
            byteBufferWrap.get(bArr3);
            int iB = new m(bArr3).b();
            if ((bArr.length - length) - 2 < iB) {
                return;
            }
            byte[] bArr4 = new byte[iB];
            byteBufferWrap.get(bArr4);
            this.f20655a.load(new ByteArrayInputStream(bArr4));
            int length2 = ((bArr.length - length) - iB) - 2;
            if (length2 > 0) {
                byte[] bArr5 = new byte[length2];
                this.f20656b = bArr5;
                byteBufferWrap.get(bArr5);
            }
        }

        public String toString() {
            return "ApkExternalInfo [p=" + this.f20655a + ", otherData=" + Arrays.toString(this.f20656b) + StrPool.BRACKET_END;
        }
    }

    public static String a(File file, String str) throws Throwable {
        RandomAccessFile randomAccessFile;
        RandomAccessFile randomAccessFile2 = null;
        byte b3 = 0;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArrA = a(randomAccessFile);
            if (bArrA == null) {
                randomAccessFile.close();
                return null;
            }
            a aVar = new a();
            aVar.a(bArrA);
            String property = aVar.f20655a.getProperty(str);
            randomAccessFile.close();
            return property;
        } catch (Throwable th2) {
            th = th2;
            randomAccessFile2 = randomAccessFile;
            if (randomAccessFile2 != null) {
                randomAccessFile2.close();
            }
            throw th;
        }
    }

    public static String a(File file) throws IOException {
        return a(file, "channelNo");
    }

    private static byte[] a(RandomAccessFile randomAccessFile) throws IOException {
        boolean z2;
        long length = randomAccessFile.length() - 22;
        randomAccessFile.seek(length);
        byte[] bArrA = f20653a.a();
        int i2 = randomAccessFile.read();
        while (true) {
            z2 = false;
            if (i2 == -1) {
                break;
            }
            if (i2 == bArrA[0]) {
                z2 = true;
                if (randomAccessFile.read() == bArrA[1] && randomAccessFile.read() == bArrA[2] && randomAccessFile.read() == bArrA[3]) {
                    break;
                }
            }
            length--;
            randomAccessFile.seek(length);
            i2 = randomAccessFile.read();
        }
        if (z2) {
            randomAccessFile.seek(length + 16 + 4);
            byte[] bArr = new byte[2];
            randomAccessFile.readFully(bArr);
            int iB = new m(bArr).b();
            if (iB == 0) {
                return null;
            }
            byte[] bArr2 = new byte[iB];
            randomAccessFile.read(bArr2);
            return bArr2;
        }
        throw new ZipException("archive is not a ZIP archive");
    }
}
