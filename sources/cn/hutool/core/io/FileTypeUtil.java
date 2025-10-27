package cn.hutool.core.io;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.HexUtil;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/* loaded from: classes.dex */
public class FileTypeUtil {
    private static final Map<String, String> FILE_TYPE_MAP = new ConcurrentSkipListMap();

    public static String getType(String str) {
        Map<String, String> map = FILE_TYPE_MAP;
        if (MapUtil.isNotEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (CharSequenceUtil.startWithIgnoreCase(str, entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return FileMagicNumber.getMagicNumber(HexUtil.decodeHex(str)).getExtension();
    }

    public static String getTypeByPath(String str, boolean z2) throws IORuntimeException {
        return getType(FileUtil.file(str), z2);
    }

    public static String putFileType(String str, String str2) {
        return FILE_TYPE_MAP.put(str, str2);
    }

    public static String removeFileType(String str) {
        return FILE_TYPE_MAP.remove(str);
    }

    public static String getTypeByPath(String str) throws IORuntimeException {
        return getTypeByPath(str, false);
    }

    public static String getType(InputStream inputStream, int i2) throws IORuntimeException {
        return getType(IoUtil.readHex(inputStream, i2, false));
    }

    public static String getType(InputStream inputStream, boolean z2) throws IORuntimeException {
        if (z2) {
            return getType(IoUtil.readHex8192Upper(inputStream));
        }
        return getType(IoUtil.readHex64Upper(inputStream));
    }

    public static String getType(InputStream inputStream) throws IORuntimeException {
        return getType(inputStream, false);
    }

    public static String getType(InputStream inputStream, String str) throws IORuntimeException {
        return getType(inputStream, str, false);
    }

    public static String getType(InputStream inputStream, String str, boolean z2) throws IORuntimeException {
        String type = getType(inputStream, z2);
        if (type == null) {
            return FileUtil.extName(str);
        }
        String str2 = "zip";
        if ("zip".equals(type)) {
            String strExtName = FileUtil.extName(str);
            if (!"docx".equalsIgnoreCase(strExtName)) {
                if (!"xlsx".equalsIgnoreCase(strExtName)) {
                    if (!"pptx".equalsIgnoreCase(strExtName)) {
                        if ("jar".equalsIgnoreCase(strExtName)) {
                            return "jar";
                        }
                        str2 = "war";
                        if (!"war".equalsIgnoreCase(strExtName)) {
                            str2 = "ofd";
                            if (!"ofd".equalsIgnoreCase(strExtName)) {
                                if (!"apk".equalsIgnoreCase(strExtName)) {
                                    return type;
                                }
                                return "apk";
                            }
                        }
                        return str2;
                    }
                    return "pptx";
                }
                return "xlsx";
            }
            return "docx";
        }
        if (!"jar".equals(type)) {
            return type;
        }
        String strExtName2 = FileUtil.extName(str);
        if (!"xlsx".equalsIgnoreCase(strExtName2)) {
            if (!"docx".equalsIgnoreCase(strExtName2)) {
                if (!"pptx".equalsIgnoreCase(strExtName2)) {
                    if (!"zip".equalsIgnoreCase(strExtName2)) {
                        if (!"apk".equalsIgnoreCase(strExtName2)) {
                            return type;
                        }
                        return "apk";
                    }
                    return str2;
                }
                return "pptx";
            }
            return "docx";
        }
        return "xlsx";
    }

    public static String getType(File file, boolean z2) throws Throwable {
        FileInputStream stream;
        if (FileUtil.isFile(file)) {
            try {
                stream = IoUtil.toStream(file);
            } catch (Throwable th) {
                th = th;
                stream = null;
            }
            try {
                String type = getType(stream, file.getName(), z2);
                IoUtil.close((Closeable) stream);
                return type;
            } catch (Throwable th2) {
                th = th2;
                IoUtil.close((Closeable) stream);
                throw th;
            }
        }
        throw new IllegalArgumentException("Not a regular file!");
    }

    public static String getType(File file) throws IORuntimeException {
        return getType(file, false);
    }
}
