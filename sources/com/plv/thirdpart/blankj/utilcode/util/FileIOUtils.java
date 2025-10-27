package com.plv.thirdpart.blankj.utilcode.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public final class FileIOUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static int sBufferSize = 8192;

    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    private static boolean createOrExistsFile(String str) {
        return createOrExistsFile(getFileByPath(str));
    }

    private static File getFileByPath(String str) {
        if (isSpace(str)) {
            return null;
        }
        return new File(str);
    }

    private static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static byte[] readFile2BytesByChannel(String str) {
        return readFile2BytesByChannel(getFileByPath(str));
    }

    public static byte[] readFile2BytesByMap(String str) {
        return readFile2BytesByMap(getFileByPath(str));
    }

    public static byte[] readFile2BytesByStream(String str) {
        return readFile2BytesByStream(getFileByPath(str));
    }

    public static List<String> readFile2List(String str) {
        return readFile2List(getFileByPath(str), (String) null);
    }

    public static String readFile2String(String str) {
        return readFile2String(getFileByPath(str), (String) null);
    }

    public static void setBufferSize(int i2) {
        sBufferSize = i2;
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z2) {
        return writeFileFromBytesByChannel(getFileByPath(str), bArr, false, z2);
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z2) {
        return writeFileFromBytesByMap(str, bArr, false, z2);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr) {
        return writeFileFromBytesByStream(getFileByPath(str), bArr, false);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream) {
        return writeFileFromIS(getFileByPath(str), inputStream, false);
    }

    public static boolean writeFileFromString(String str, String str2) {
        return writeFileFromString(getFileByPath(str), str2, false);
    }

    private static boolean createOrExistsFile(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static byte[] readFile2BytesByChannel(File file) throws Throwable {
        Throwable th;
        FileChannel channel;
        if (!isFileExists(file)) {
            return null;
        }
        try {
            try {
                channel = new RandomAccessFile(file, "r").getChannel();
            } catch (IOException e2) {
                e = e2;
                channel = null;
            } catch (Throwable th2) {
                th = th2;
                file = null;
                CloseUtils.closeIO(file);
                throw th;
            }
            try {
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) channel.size());
                while (channel.read(byteBufferAllocate) > 0) {
                }
                byte[] bArrArray = byteBufferAllocate.array();
                CloseUtils.closeIO(channel);
                return bArrArray;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                CloseUtils.closeIO(channel);
                return null;
            }
        } catch (Throwable th3) {
            th = th3;
            CloseUtils.closeIO(file);
            throw th;
        }
    }

    public static byte[] readFile2BytesByMap(File file) throws Throwable {
        Throwable th;
        FileChannel channel;
        if (!isFileExists(file)) {
            return null;
        }
        try {
            channel = new RandomAccessFile(file, "r").getChannel();
        } catch (IOException e2) {
            e = e2;
            channel = null;
        } catch (Throwable th2) {
            th = th2;
            channel = null;
            CloseUtils.closeIO(channel);
            throw th;
        }
        try {
            try {
                int size = (int) channel.size();
                byte[] bArr = new byte[size];
                channel.map(FileChannel.MapMode.READ_ONLY, 0L, size).load().get(bArr, 0, size);
                CloseUtils.closeIO(channel);
                return bArr;
            } catch (Throwable th3) {
                th = th3;
                CloseUtils.closeIO(channel);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            CloseUtils.closeIO(channel);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static byte[] readFile2BytesByStream(File file) throws Throwable {
        FileInputStream fileInputStream;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        if (!isFileExists(file)) {
            return null;
        }
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (IOException e2) {
                e = e2;
                byteArrayOutputStream = null;
                fileInputStream = null;
            } catch (Throwable th2) {
                fileInputStream = null;
                th = th2;
                file = null;
            }
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (IOException e3) {
                e = e3;
                byteArrayOutputStream = null;
            } catch (Throwable th3) {
                th = th3;
                file = null;
                CloseUtils.closeIO(fileInputStream, file);
                throw th;
            }
            try {
                byte[] bArr = new byte[sBufferSize];
                while (true) {
                    int i2 = fileInputStream.read(bArr, 0, sBufferSize);
                    if (i2 == -1) {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        CloseUtils.closeIO(fileInputStream, byteArrayOutputStream);
                        return byteArray;
                    }
                    byteArrayOutputStream.write(bArr, 0, i2);
                }
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                CloseUtils.closeIO(fileInputStream, byteArrayOutputStream);
                return null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public static List<String> readFile2List(String str, String str2) {
        return readFile2List(getFileByPath(str), str2);
    }

    public static String readFile2String(String str, String str2) {
        return readFile2String(getFileByPath(str), str2);
    }

    public static boolean writeFileFromBytesByChannel(String str, byte[] bArr, boolean z2, boolean z3) {
        return writeFileFromBytesByChannel(getFileByPath(str), bArr, z2, z3);
    }

    public static boolean writeFileFromBytesByMap(String str, byte[] bArr, boolean z2, boolean z3) {
        return writeFileFromBytesByMap(getFileByPath(str), bArr, z2, z3);
    }

    public static boolean writeFileFromBytesByStream(String str, byte[] bArr, boolean z2) {
        return writeFileFromBytesByStream(getFileByPath(str), bArr, z2);
    }

    public static boolean writeFileFromIS(String str, InputStream inputStream, boolean z2) {
        return writeFileFromIS(getFileByPath(str), inputStream, z2);
    }

    public static boolean writeFileFromString(String str, String str2, boolean z2) {
        return writeFileFromString(getFileByPath(str), str2, z2);
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
        return writeFileFromBytesByStream(file, bArr, false);
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream) {
        return writeFileFromIS(file, inputStream, false);
    }

    public static boolean writeFileFromString(File file, String str) {
        return writeFileFromString(file, str, false);
    }

    public static List<String> readFile2List(File file, String str) {
        return readFile2List(file, 0, Integer.MAX_VALUE, str);
    }

    public static String readFile2String(File file, String str) throws Throwable {
        BufferedReader bufferedReader;
        StringBuilder sb;
        BufferedReader bufferedReader2 = null;
        if (!isFileExists(file)) {
            return null;
        }
        try {
            sb = new StringBuilder();
            if (isSpace(str)) {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), str));
            }
        } catch (IOException e2) {
            e = e2;
            bufferedReader = null;
        } catch (Throwable th) {
            th = th;
            CloseUtils.closeIO(bufferedReader2);
            throw th;
        }
        try {
            try {
                String line = bufferedReader.readLine();
                if (line != null) {
                    sb.append(line);
                    while (true) {
                        String line2 = bufferedReader.readLine();
                        if (line2 == null) {
                            break;
                        }
                        sb.append(LINE_SEP);
                        sb.append(line2);
                    }
                }
                String string = sb.toString();
                CloseUtils.closeIO(bufferedReader);
                return string;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader2 = bufferedReader;
                CloseUtils.closeIO(bufferedReader2);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            CloseUtils.closeIO(bufferedReader);
            return null;
        }
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bArr, boolean z2, boolean z3) throws IOException {
        if (bArr == null) {
            return false;
        }
        FileChannel channel = null;
        try {
            try {
                channel = new FileOutputStream(file, z2).getChannel();
                channel.position(channel.size());
                channel.write(ByteBuffer.wrap(bArr));
                if (z3) {
                    channel.force(true);
                }
                CloseUtils.closeIO(channel);
                return true;
            } catch (IOException e2) {
                e2.printStackTrace();
                CloseUtils.closeIO(channel);
                return false;
            }
        } catch (Throwable th) {
            CloseUtils.closeIO(channel);
            throw th;
        }
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bArr, boolean z2, boolean z3) throws IOException {
        if (bArr == null || !createOrExistsFile(file)) {
            return false;
        }
        FileChannel channel = null;
        try {
            try {
                channel = new FileOutputStream(file, z2).getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, channel.size(), bArr.length);
                map.put(bArr);
                if (z3) {
                    map.force();
                }
                CloseUtils.closeIO(channel);
                return true;
            } catch (IOException e2) {
                e2.printStackTrace();
                CloseUtils.closeIO(channel);
                return false;
            }
        } catch (Throwable th) {
            CloseUtils.closeIO(channel);
            throw th;
        }
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bArr, boolean z2) throws Throwable {
        if (bArr == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z2));
                try {
                    bufferedOutputStream2.write(bArr);
                    CloseUtils.closeIO(bufferedOutputStream2);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    CloseUtils.closeIO(bufferedOutputStream);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    CloseUtils.closeIO(bufferedOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static boolean writeFileFromIS(File file, InputStream inputStream, boolean z2) throws Throwable {
        if (!createOrExistsFile(file) || inputStream == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file, z2));
                try {
                    byte[] bArr = new byte[sBufferSize];
                    while (true) {
                        int i2 = inputStream.read(bArr, 0, sBufferSize);
                        if (i2 != -1) {
                            bufferedOutputStream2.write(bArr, 0, i2);
                        } else {
                            CloseUtils.closeIO(inputStream, bufferedOutputStream2);
                            return true;
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    bufferedOutputStream = bufferedOutputStream2;
                    e.printStackTrace();
                    CloseUtils.closeIO(inputStream, bufferedOutputStream);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedOutputStream = bufferedOutputStream2;
                    CloseUtils.closeIO(inputStream, bufferedOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e3) {
            e = e3;
        }
    }

    public static boolean writeFileFromString(File file, String str, boolean z2) throws Throwable {
        if (file == null || str == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bufferedWriter = null;
        try {
            try {
                BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file, z2));
                try {
                    bufferedWriter2.write(str);
                    CloseUtils.closeIO(bufferedWriter2);
                    return true;
                } catch (IOException e2) {
                    e = e2;
                    bufferedWriter = bufferedWriter2;
                    e.printStackTrace();
                    CloseUtils.closeIO(bufferedWriter);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    bufferedWriter = bufferedWriter2;
                    CloseUtils.closeIO(bufferedWriter);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static List<String> readFile2List(String str, int i2, int i3) {
        return readFile2List(getFileByPath(str), i2, i3, (String) null);
    }

    public static List<String> readFile2List(String str, int i2, int i3, String str2) {
        return readFile2List(getFileByPath(str), i2, i3, str2);
    }

    public static List<String> readFile2List(File file, int i2, int i3) {
        return readFile2List(file, i2, i3, (String) null);
    }

    public static List<String> readFile2List(File file, int i2, int i3, String str) throws Throwable {
        BufferedReader bufferedReader;
        int i4;
        BufferedReader bufferedReader2 = null;
        if (!isFileExists(file) || i2 > i3) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            if (isSpace(str)) {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                i4 = 1;
            } else {
                BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(new FileInputStream(file), str));
                i4 = 1;
                bufferedReader = bufferedReader3;
            }
            while (true) {
                try {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null || i4 > i3) {
                            break;
                        }
                        if (i2 <= i4 && i4 <= i3) {
                            arrayList.add(line);
                        }
                        i4++;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader2 = bufferedReader;
                        CloseUtils.closeIO(bufferedReader2);
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    CloseUtils.closeIO(bufferedReader);
                    return null;
                }
            }
            CloseUtils.closeIO(bufferedReader);
            return arrayList;
        } catch (IOException e3) {
            e = e3;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            CloseUtils.closeIO(bufferedReader2);
            throw th;
        }
    }
}
