package com.umeng.commonsdk.utils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLock;

/* loaded from: classes6.dex */
public class FileLockUtil {
    private final Object lockObject = new Object();

    /* JADX WARN: Removed duplicated region for block: B:22:0x0023 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.nio.channels.FileLock getFileLock(java.lang.String r3) throws java.io.IOException {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch: java.io.IOException -> L16 java.io.FileNotFoundException -> L1c
            java.lang.String r2 = "rw"
            r1.<init>(r3, r2)     // Catch: java.io.IOException -> L16 java.io.FileNotFoundException -> L1c
            java.nio.channels.FileChannel r3 = r1.getChannel()     // Catch: java.io.IOException -> L16 java.io.FileNotFoundException -> L1c
            java.nio.channels.FileLock r3 = r3.lock()     // Catch: java.io.IOException -> L12 java.io.FileNotFoundException -> L14
            return r3
        L12:
            r1 = move-exception
            goto L18
        L14:
            r1 = move-exception
            goto L1e
        L16:
            r1 = move-exception
            r3 = r0
        L18:
            r1.printStackTrace()
            goto L21
        L1c:
            r1 = move-exception
            r3 = r0
        L1e:
            r1.printStackTrace()
        L21:
            if (r3 == 0) goto L2b
            r3.close()     // Catch: java.io.IOException -> L27
            goto L2b
        L27:
            r3 = move-exception
            r3.printStackTrace()
        L2b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.FileLockUtil.getFileLock(java.lang.String):java.nio.channels.FileLock");
    }

    public void doFileOperateion(File file, FileLockCallback fileLockCallback, Object obj) {
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(file.getAbsolutePath());
                if (fileLock != null) {
                    try {
                        try {
                            fileLockCallback.onFileLock(file.getName(), obj);
                            try {
                                fileLock.release();
                                fileLock.channel().close();
                            } catch (IOException e2) {
                                e = e2;
                                e.printStackTrace();
                            }
                        } finally {
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (IOException e4) {
                            e = e4;
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void doFileOperateion(File file, FileLockCallback fileLockCallback, int i2) {
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(file.getAbsolutePath());
                if (fileLock != null) {
                    try {
                        try {
                            fileLockCallback.onFileLock(file, i2);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            try {
                                fileLock.release();
                                fileLock.channel().close();
                            } catch (Throwable th) {
                                th = th;
                                th.printStackTrace();
                            }
                        }
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (Throwable th2) {
                            th = th2;
                            th.printStackTrace();
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public void doFileOperateion(File file, FileLockCallback fileLockCallback) {
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(file.getAbsolutePath());
                if (fileLock != null) {
                    try {
                        try {
                            fileLockCallback.onFileLock(file.getName());
                            try {
                                fileLock.release();
                                fileLock.channel().close();
                            } catch (IOException e2) {
                                e = e2;
                                e.printStackTrace();
                            }
                        } finally {
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (IOException e4) {
                            e = e4;
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public void doFileOperateion(String str, FileLockCallback fileLockCallback) {
        File file = new File(str);
        if (file.exists()) {
            synchronized (this.lockObject) {
                FileLock fileLock = getFileLock(str);
                if (fileLock != null) {
                    try {
                        try {
                            fileLockCallback.onFileLock(file.getName());
                        } finally {
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        try {
                            fileLock.release();
                            fileLock.channel().close();
                        } catch (IOException e3) {
                            e = e3;
                            e.printStackTrace();
                        }
                    }
                    try {
                        fileLock.release();
                        fileLock.channel().close();
                    } catch (IOException e4) {
                        e = e4;
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
