package cn.hutool.core.io.file;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.ReUtil;
import java.io.File;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class FileNameUtil {
    public static final String EXT_CLASS = ".class";
    public static final String EXT_JAR = ".jar";
    public static final String EXT_JAVA = ".java";
    private static final Pattern FILE_NAME_INVALID_PATTERN_WIN = Pattern.compile("[\\\\/:*?\"<>|\r\n]");
    private static final CharSequence[] SPECIAL_SUFFIX = {"tar.bz2", "tar.Z", "tar.gz", "tar.xz"};
    public static final char UNIX_SEPARATOR = '/';
    public static final char WINDOWS_SEPARATOR = '\\';

    public static String cleanInvalid(String str) {
        return CharSequenceUtil.isBlank(str) ? str : ReUtil.delAll(FILE_NAME_INVALID_PATTERN_WIN, str);
    }

    public static boolean containsInvalid(String str) {
        return !CharSequenceUtil.isBlank(str) && ReUtil.contains(FILE_NAME_INVALID_PATTERN_WIN, str);
    }

    public static String extName(File file) {
        if (file == null || file.isDirectory()) {
            return null;
        }
        return extName(file.getName());
    }

    public static String getName(File file) {
        if (file != null) {
            return file.getName();
        }
        return null;
    }

    public static String getPrefix(File file) {
        return mainName(file);
    }

    public static String getSuffix(File file) {
        return extName(file);
    }

    public static boolean isType(String str, String... strArr) {
        return CharSequenceUtil.equalsAnyIgnoreCase(extName(str), strArr);
    }

    public static String mainName(File file) {
        return file.isDirectory() ? file.getName() : mainName(file.getName());
    }

    public static String getName(String str) {
        int i2;
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return str;
        }
        if (CharUtil.isFileSeparator(str.charAt(length - 1))) {
            length--;
        }
        int i3 = length - 1;
        while (true) {
            if (i3 <= -1) {
                i2 = 0;
                break;
            }
            if (CharUtil.isFileSeparator(str.charAt(i3))) {
                i2 = i3 + 1;
                break;
            }
            i3--;
        }
        return str.substring(i2, length);
    }

    public static String getPrefix(String str) {
        return mainName(str);
    }

    public static String getSuffix(String str) {
        return extName(str);
    }

    public static String extName(String str) {
        if (str == null) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(StrPool.DOT);
        if (iLastIndexOf == -1) {
            return "";
        }
        int iLastIndexOf2 = str.substring(0, iLastIndexOf).lastIndexOf(StrPool.DOT);
        String strSubstring = str.substring(iLastIndexOf2 == -1 ? iLastIndexOf : iLastIndexOf2 + 1);
        if (CharSequenceUtil.containsAny(strSubstring, SPECIAL_SUFFIX)) {
            return strSubstring;
        }
        String strSubstring2 = str.substring(iLastIndexOf + 1);
        return CharSequenceUtil.containsAny(strSubstring2, '/', '\\') ? "" : strSubstring2;
    }

    public static String mainName(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return str;
        }
        int i2 = 0;
        for (CharSequence charSequence : SPECIAL_SUFFIX) {
            if (CharSequenceUtil.endWith(str, StrPool.DOT + ((Object) charSequence))) {
                return CharSequenceUtil.subPre(str, (length - r5.length()) - 1);
            }
        }
        if (CharUtil.isFileSeparator(str.charAt(length - 1))) {
            length--;
        }
        int i3 = length - 1;
        int i4 = length;
        while (true) {
            if (i3 < 0) {
                break;
            }
            char cCharAt = str.charAt(i3);
            if (length == i4 && '.' == cCharAt) {
                i4 = i3;
            }
            if (CharUtil.isFileSeparator(cCharAt)) {
                i2 = i3 + 1;
                break;
            }
            i3--;
        }
        return str.substring(i2, i4);
    }
}
