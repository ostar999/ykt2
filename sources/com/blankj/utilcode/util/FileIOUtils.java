package com.blankj.utilcode.util;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.List;

/* loaded from: classes2.dex */
public final class FileIOUtils {
    private static int sBufferSize = 524288;

    public interface OnProgressUpdateListener {
        void onProgressUpdate(double d3);
    }

    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static byte[] readFile2BytesByChannel(String str) {
        return readFile2BytesByChannel(UtilsBridge.getFileByPath(str));
    }

    public static byte[] readFile2BytesByMap(String str) {
        return readFile2BytesByMap(UtilsBridge.getFileByPath(str));
    }

    public static byte[] readFile2BytesByStream(String str) {
        return readFile2BytesByStream(UtilsBridge.getFileByPath(str), (OnProgressUpdateListener) null);
    }

    public static List<String> readFile2List(String str) {
        return readFile2List(UtilsBridge.getFileByPath(str), (String) null);
    }

    public static String readFile2String(String str) {
        return readFile2String(UtilsBridge.getFileByPath(str), (String) null);
    }

    public static void setBufferSize(int i2) {
        sBufferSize = i2;
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z2) {
        return writeFileFromBytesByChannel(UtilsBridge.getFileByPath(str), bArr, false, z2);
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z2) {
        return writeFileFromBytesByMap(str, bArr, false, z2);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr) {
        return writeFileFromBytesByStream(UtilsBridge.getFileByPath(str), bArr, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream) {
        return writeFileFromIS(UtilsBridge.getFileByPath(str), inputStream, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromString(String str, String str2) {
        return writeFileFromString(UtilsBridge.getFileByPath(str), str2, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.nio.channels.spi.AbstractInterruptibleChannel] */
    public static byte[] readFile2BytesByChannel(File file) throws Throwable {
        FileChannel channel;
        ?? r12 = 0;
        try {
            if (!UtilsBridge.isFileExists(file)) {
                return null;
            }
            try {
                channel = new RandomAccessFile(file, "r").getChannel();
            } catch (IOException e2) {
                e = e2;
                channel = null;
            } catch (Throwable th) {
                th = th;
                if (r12 != 0) {
                    try {
                        r12.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                if (channel == null) {
                    Log.e("FileIOUtils", "fc is null.");
                    byte[] bArr = new byte[0];
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return bArr;
                }
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) channel.size());
                while (channel.read(byteBufferAllocate) > 0) {
                }
                byte[] bArrArray = byteBufferAllocate.array();
                try {
                    channel.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                return bArrArray;
            } catch (IOException e6) {
                e = e6;
                e.printStackTrace();
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            r12 = file;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.nio.channels.spi.AbstractInterruptibleChannel] */
    public static byte[] readFile2BytesByMap(File file) throws Throwable {
        FileChannel channel;
        ?? r12 = 0;
        try {
            if (!UtilsBridge.isFileExists(file)) {
                return null;
            }
            try {
                channel = new RandomAccessFile(file, "r").getChannel();
            } catch (IOException e2) {
                e = e2;
                channel = null;
            } catch (Throwable th) {
                th = th;
                if (r12 != 0) {
                    try {
                        r12.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
                throw th;
            }
            try {
                if (channel == null) {
                    Log.e("FileIOUtils", "fc is null.");
                    byte[] bArr = new byte[0];
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return bArr;
                }
                int size = (int) channel.size();
                byte[] bArr2 = new byte[size];
                channel.map(FileChannel.MapMode.READ_ONLY, 0L, size).load().get(bArr2, 0, size);
                try {
                    channel.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                return bArr2;
            } catch (IOException e6) {
                e = e6;
                e.printStackTrace();
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            r12 = file;
        }
    }

    public static byte[] readFile2BytesByStream(File file) {
        return readFile2BytesByStream(file, (OnProgressUpdateListener) null);
    }

    public static List<String> readFile2List(String str, String str2) {
        return readFile2List(UtilsBridge.getFileByPath(str), str2);
    }

    public static String readFile2String(String str, String str2) {
        return readFile2String(UtilsBridge.getFileByPath(str), str2);
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z2, boolean z3) {
        return writeFileFromBytesByChannel(UtilsBridge.getFileByPath(str), bArr, z2, z3);
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z2, boolean z3) {
        return writeFileFromBytesByMap(UtilsBridge.getFileByPath(str), bArr, z2, z3);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, boolean z2) {
        return writeFileFromBytesByStream(UtilsBridge.getFileByPath(str), bArr, z2, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, boolean z2) {
        return writeFileFromIS(UtilsBridge.getFileByPath(str), inputStream, z2, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromString(String str, String str2, boolean z2) {
        return writeFileFromString(UtilsBridge.getFileByPath(str), str2, z2);
    }

    public static byte[] readFile2BytesByStream(String str, OnProgressUpdateListener onProgressUpdateListener) {
        return readFile2BytesByStream(UtilsBridge.getFileByPath(str), onProgressUpdateListener);
    }

    public static List<String> readFile2List(File file) {
        return readFile2List(file, 0, Integer.MAX_VALUE, (String) null);
    }

    public static String readFile2String(File file) {
        return readFile2String(file, (String) null);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bArr, boolean z2) {
        return writeFileFromBytesByChannel(file, bArr, false, z2);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bArr, boolean z2) {
        return writeFileFromBytesByMap(file, bArr, false, z2);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr) {
        return writeFileFromBytesByStream(file, bArr, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream) {
        return writeFileFromIS(file, inputStream, false, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromString(File file, String str) {
        return writeFileFromString(file, str, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:71:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:? A[Catch: FileNotFoundException -> 0x0090, SYNTHETIC, TRY_LEAVE, TryCatch #0 {FileNotFoundException -> 0x0090, blocks: (B:5:0x0008, B:24:0x005b, B:20:0x0053, B:41:0x0078, B:36:0x006e, B:53:0x008f, B:52:0x008c, B:47:0x0082, B:21:0x0056, B:38:0x0073, B:44:0x007d, B:17:0x004e, B:33:0x0069, B:49:0x0087), top: B:57:0x0008, inners: #1, #3, #5, #6, #8, #10 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] readFile2BytesByStream(java.io.File r10, com.blankj.utilcode.util.FileIOUtils.OnProgressUpdateListener r11) throws java.lang.Throwable {
        /*
            boolean r0 = com.blankj.utilcode.util.UtilsBridge.isFileExists(r10)
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream     // Catch: java.io.FileNotFoundException -> L90
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.io.FileNotFoundException -> L90
            r2.<init>(r10)     // Catch: java.io.FileNotFoundException -> L90
            int r10 = com.blankj.utilcode.util.FileIOUtils.sBufferSize     // Catch: java.io.FileNotFoundException -> L90
            r0.<init>(r2, r10)     // Catch: java.io.FileNotFoundException -> L90
            java.io.ByteArrayOutputStream r10 = new java.io.ByteArrayOutputStream     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L64
            r10.<init>()     // Catch: java.lang.Throwable -> L61 java.io.IOException -> L64
            int r2 = com.blankj.utilcode.util.FileIOUtils.sBufferSize     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            byte[] r2 = new byte[r2]     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            r3 = -1
            r4 = 0
            if (r11 != 0) goto L2d
        L21:
            int r11 = com.blankj.utilcode.util.FileIOUtils.sBufferSize     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            int r11 = r0.read(r2, r4, r11)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            if (r11 == r3) goto L4a
            r10.write(r2, r4, r11)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            goto L21
        L2d:
            int r5 = r0.available()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            double r5 = (double) r5     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            r7 = 0
            r11.onProgressUpdate(r7)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            r7 = r4
        L38:
            int r8 = com.blankj.utilcode.util.FileIOUtils.sBufferSize     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            int r8 = r0.read(r2, r4, r8)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            if (r8 == r3) goto L4a
            r10.write(r2, r4, r8)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            int r7 = r7 + r8
            double r8 = (double) r7     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            double r8 = r8 / r5
            r11.onProgressUpdate(r8)     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            goto L38
        L4a:
            byte[] r11 = r10.toByteArray()     // Catch: java.io.IOException -> L5f java.lang.Throwable -> L7c
            r0.close()     // Catch: java.io.IOException -> L52
            goto L56
        L52:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.io.FileNotFoundException -> L90
        L56:
            r10.close()     // Catch: java.io.IOException -> L5a
            goto L5e
        L5a:
            r10 = move-exception
            r10.printStackTrace()     // Catch: java.io.FileNotFoundException -> L90
        L5e:
            return r11
        L5f:
            r11 = move-exception
            goto L66
        L61:
            r11 = move-exception
            r10 = r1
            goto L7d
        L64:
            r11 = move-exception
            r10 = r1
        L66:
            r11.printStackTrace()     // Catch: java.lang.Throwable -> L7c
            r0.close()     // Catch: java.io.IOException -> L6d
            goto L71
        L6d:
            r11 = move-exception
            r11.printStackTrace()     // Catch: java.io.FileNotFoundException -> L90
        L71:
            if (r10 == 0) goto L7b
            r10.close()     // Catch: java.io.IOException -> L77
            goto L7b
        L77:
            r10 = move-exception
            r10.printStackTrace()     // Catch: java.io.FileNotFoundException -> L90
        L7b:
            return r1
        L7c:
            r11 = move-exception
        L7d:
            r0.close()     // Catch: java.io.IOException -> L81
            goto L85
        L81:
            r0 = move-exception
            r0.printStackTrace()     // Catch: java.io.FileNotFoundException -> L90
        L85:
            if (r10 == 0) goto L8f
            r10.close()     // Catch: java.io.IOException -> L8b
            goto L8f
        L8b:
            r10 = move-exception
            r10.printStackTrace()     // Catch: java.io.FileNotFoundException -> L90
        L8f:
            throw r11     // Catch: java.io.FileNotFoundException -> L90
        L90:
            r10 = move-exception
            r10.printStackTrace()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileIOUtils.readFile2BytesByStream(java.io.File, com.blankj.utilcode.util.FileIOUtils$OnProgressUpdateListener):byte[]");
    }

    public static List<String> readFile2List(File file, String str) {
        return readFile2List(file, 0, Integer.MAX_VALUE, str);
    }

    public static String readFile2String(File file, String str) {
        byte[] file2BytesByStream = readFile2BytesByStream(file);
        if (file2BytesByStream == null) {
            return null;
        }
        if (UtilsBridge.isSpace(str)) {
            return new String(file2BytesByStream);
        }
        try {
            return new String(file2BytesByStream, str);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bArr, boolean z2, boolean z3) throws IOException {
        if (bArr == null) {
            Log.e("FileIOUtils", "bytes is null.");
            return false;
        }
        if (!UtilsBridge.createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        AbstractInterruptibleChannel abstractInterruptibleChannel = null;
        try {
            try {
                FileChannel channel = new FileOutputStream(file, z2).getChannel();
                if (channel == null) {
                    Log.e("FileIOUtils", "fc is null.");
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    return false;
                }
                channel.position(channel.size());
                channel.write(ByteBuffer.wrap(bArr));
                if (z3) {
                    channel.force(true);
                }
                try {
                    channel.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return true;
            } catch (IOException e4) {
                e4.printStackTrace();
                if (0 != 0) {
                    try {
                        abstractInterruptibleChannel.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
                return false;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    abstractInterruptibleChannel.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bArr, boolean z2, boolean z3) throws IOException {
        if (bArr != null && UtilsBridge.createOrExistsFile(file)) {
            AbstractInterruptibleChannel abstractInterruptibleChannel = null;
            try {
                try {
                    FileChannel channel = new FileOutputStream(file, z2).getChannel();
                    if (channel == null) {
                        Log.e("FileIOUtils", "fc is null.");
                        if (channel != null) {
                            try {
                                channel.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        return false;
                    }
                    MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, channel.size(), bArr.length);
                    map.put(bArr);
                    if (z3) {
                        map.force();
                    }
                    try {
                        channel.close();
                        return true;
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        return true;
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                    if (0 != 0) {
                        try {
                            abstractInterruptibleChannel.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    return false;
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    try {
                        abstractInterruptibleChannel.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                throw th;
            }
        }
        Log.e("FileIOUtils", "create file <" + file + "> failed.");
        return false;
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, boolean z2) {
        return writeFileFromBytesByStream(file, bArr, z2, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream, boolean z2) {
        return writeFileFromIS(file, inputStream, z2, (OnProgressUpdateListener) null);
    }

    public static boolean writeFileFromString(File file, String str, boolean z2) throws Throwable {
        if (file == null || str == null) {
            return false;
        }
        if (!UtilsBridge.createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file, z2));
                try {
                    bufferedWriter2.write(str);
                    try {
                        bufferedWriter2.close();
                        return true;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        return true;
                    }
                } catch (IOException e3) {
                    e = e3;
                    bufferedWriter = bufferedWriter2;
                    e.printStackTrace();
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e6) {
            e = e6;
        }
    }

    public static List<String> readFile2List(String str, int i2, int i3) {
        return readFile2List(UtilsBridge.getFileByPath(str), i2, i3, (String) null);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromBytesByStream(UtilsBridge.getFileByPath(str), bArr, false, onProgressUpdateListener);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromIS(UtilsBridge.getFileByPath(str), inputStream, false, onProgressUpdateListener);
    }

    public static List<String> readFile2List(String str, int i2, int i3, String str2) {
        return readFile2List(UtilsBridge.getFileByPath(str), i2, i3, str2);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, boolean z2, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromBytesByStream(UtilsBridge.getFileByPath(str), bArr, z2, onProgressUpdateListener);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, boolean z2, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromIS(UtilsBridge.getFileByPath(str), inputStream, z2, onProgressUpdateListener);
    }

    public static List<String> readFile2List(File file, int i2, int i3) {
        return readFile2List(file, i2, i3, (String) null);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromBytesByStream(file, bArr, false, onProgressUpdateListener);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream, OnProgressUpdateListener onProgressUpdateListener) {
        return writeFileFromIS(file, inputStream, false, onProgressUpdateListener);
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> readFile2List(java.io.File r6, int r7, int r8, java.lang.String r9) throws java.lang.Throwable {
        /*
            boolean r0 = com.blankj.utilcode.util.UtilsBridge.isFileExists(r6)
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            if (r7 <= r8) goto Lb
            return r1
        Lb:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r0.<init>()     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            boolean r2 = com.blankj.utilcode.util.UtilsBridge.isSpace(r9)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r3 = 1
            if (r2 == 0) goto L27
            java.io.BufferedReader r9 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r4.<init>(r6)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r9.<init>(r2)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            goto L37
        L27:
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r4.<init>(r5, r9)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r2.<init>(r4)     // Catch: java.lang.Throwable -> L55 java.io.IOException -> L57
            r9 = r2
        L37:
            java.lang.String r6 = r9.readLine()     // Catch: java.io.IOException -> L53 java.lang.Throwable -> L67
            if (r6 == 0) goto L4a
            if (r3 <= r8) goto L40
            goto L4a
        L40:
            if (r7 > r3) goto L47
            if (r3 > r8) goto L47
            r0.add(r6)     // Catch: java.io.IOException -> L53 java.lang.Throwable -> L67
        L47:
            int r3 = r3 + 1
            goto L37
        L4a:
            r9.close()     // Catch: java.io.IOException -> L4e
            goto L52
        L4e:
            r6 = move-exception
            r6.printStackTrace()
        L52:
            return r0
        L53:
            r6 = move-exception
            goto L59
        L55:
            r6 = move-exception
            goto L69
        L57:
            r6 = move-exception
            r9 = r1
        L59:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L67
            if (r9 == 0) goto L66
            r9.close()     // Catch: java.io.IOException -> L62
            goto L66
        L62:
            r6 = move-exception
            r6.printStackTrace()
        L66:
            return r1
        L67:
            r6 = move-exception
            r1 = r9
        L69:
            if (r1 == 0) goto L73
            r1.close()     // Catch: java.io.IOException -> L6f
            goto L73
        L6f:
            r7 = move-exception
            r7.printStackTrace()
        L73:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileIOUtils.readFile2List(java.io.File, int, int, java.lang.String):java.util.List");
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, boolean z2, OnProgressUpdateListener onProgressUpdateListener) {
        if (bArr == null) {
            return false;
        }
        return writeFileFromIS(file, new ByteArrayInputStream(bArr), z2, onProgressUpdateListener);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:61|8|9|76|(4:(6:11|(2:12|(1:14)(0))|20|69|24|79)(6:15|(2:16|(1:18)(0))|20|69|24|79)|69|24|79)|74|20) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004c, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004d, code lost:
    
        r7.printStackTrace();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean writeFileFromIS(java.io.File r7, java.io.InputStream r8, boolean r9, com.blankj.utilcode.util.FileIOUtils.OnProgressUpdateListener r10) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r8 == 0) goto L8c
            boolean r1 = com.blankj.utilcode.util.UtilsBridge.createOrExistsFile(r7)
            if (r1 != 0) goto Lb
            goto L8c
        Lb:
            r1 = 0
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            r3.<init>(r7, r9)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            int r7 = com.blankj.utilcode.util.FileIOUtils.sBufferSize     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            r2.<init>(r3, r7)     // Catch: java.lang.Throwable -> L60 java.io.IOException -> L62
            r7 = -1
            if (r10 != 0) goto L29
            int r9 = com.blankj.utilcode.util.FileIOUtils.sBufferSize     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            byte[] r9 = new byte[r9]     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
        L1f:
            int r10 = r8.read(r9)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            if (r10 == r7) goto L48
            r2.write(r9, r0, r10)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            goto L1f
        L29:
            int r9 = r8.available()     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            double r3 = (double) r9     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            r5 = 0
            r10.onProgressUpdate(r5)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            int r9 = com.blankj.utilcode.util.FileIOUtils.sBufferSize     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            byte[] r9 = new byte[r9]     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            r1 = r0
        L38:
            int r5 = r8.read(r9)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            if (r5 == r7) goto L48
            r2.write(r9, r0, r5)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            int r1 = r1 + r5
            double r5 = (double) r1     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            double r5 = r5 / r3
            r10.onProgressUpdate(r5)     // Catch: java.lang.Throwable -> L5a java.io.IOException -> L5d
            goto L38
        L48:
            r8.close()     // Catch: java.io.IOException -> L4c
            goto L50
        L4c:
            r7 = move-exception
            r7.printStackTrace()
        L50:
            r2.close()     // Catch: java.io.IOException -> L54
            goto L58
        L54:
            r7 = move-exception
            r7.printStackTrace()
        L58:
            r7 = 1
            return r7
        L5a:
            r7 = move-exception
            r1 = r2
            goto L79
        L5d:
            r7 = move-exception
            r1 = r2
            goto L63
        L60:
            r7 = move-exception
            goto L79
        L62:
            r7 = move-exception
        L63:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L60
            r8.close()     // Catch: java.io.IOException -> L6a
            goto L6e
        L6a:
            r7 = move-exception
            r7.printStackTrace()
        L6e:
            if (r1 == 0) goto L78
            r1.close()     // Catch: java.io.IOException -> L74
            goto L78
        L74:
            r7 = move-exception
            r7.printStackTrace()
        L78:
            return r0
        L79:
            r8.close()     // Catch: java.io.IOException -> L7d
            goto L81
        L7d:
            r8 = move-exception
            r8.printStackTrace()
        L81:
            if (r1 == 0) goto L8b
            r1.close()     // Catch: java.io.IOException -> L87
            goto L8b
        L87:
            r8 = move-exception
            r8.printStackTrace()
        L8b:
            throw r7
        L8c:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "create file <"
            r8.append(r9)
            r8.append(r7)
            java.lang.String r7 = "> failed."
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            java.lang.String r8 = "FileIOUtils"
            android.util.Log.e(r8, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.FileIOUtils.writeFileFromIS(java.io.File, java.io.InputStream, boolean, com.blankj.utilcode.util.FileIOUtils$OnProgressUpdateListener):boolean");
    }
}
