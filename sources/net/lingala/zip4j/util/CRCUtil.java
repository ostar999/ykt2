package net.lingala.zip4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

/* loaded from: classes9.dex */
public class CRCUtil {
    private static final int BUF_SIZE = 16384;

    public static long computeFileCRC(String str) throws ZipException {
        return computeFileCRC(str, null);
    }

    public static long computeFileCRC(String str, ProgressMonitor progressMonitor) throws Throwable {
        if (!Zip4jUtil.isStringNotNullAndNotEmpty(str)) {
            throw new ZipException("input file is null or empty, cannot calculate CRC for the file");
        }
        FileInputStream fileInputStream = null;
        try {
            try {
                Zip4jUtil.checkFileReadAccess(str);
                FileInputStream fileInputStream2 = new FileInputStream(new File(str));
                try {
                    byte[] bArr = new byte[16384];
                    CRC32 crc32 = new CRC32();
                    while (true) {
                        int i2 = fileInputStream2.read(bArr);
                        if (i2 == -1) {
                            long value = crc32.getValue();
                            try {
                                fileInputStream2.close();
                                return value;
                            } catch (IOException unused) {
                                throw new ZipException("error while closing the file after calculating crc");
                            }
                        }
                        crc32.update(bArr, 0, i2);
                        if (progressMonitor != null) {
                            progressMonitor.updateWorkCompleted(i2);
                            if (progressMonitor.isCancelAllTasks()) {
                                progressMonitor.setResult(3);
                                progressMonitor.setState(0);
                                try {
                                    fileInputStream2.close();
                                    return 0L;
                                } catch (IOException unused2) {
                                    throw new ZipException("error while closing the file after calculating crc");
                                }
                            }
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    throw new ZipException(e);
                } catch (Exception e3) {
                    e = e3;
                    throw new ZipException(e);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException unused3) {
                            throw new ZipException("error while closing the file after calculating crc");
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
            } catch (Exception e5) {
                e = e5;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
