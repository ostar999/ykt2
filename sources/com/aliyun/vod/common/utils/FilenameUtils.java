package com.aliyun.vod.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class FilenameUtils {
    public static final char EXTENSION_SEPARATOR = '.';
    private static final char OTHER_SEPARATOR;
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
    private static final char SYSTEM_SEPARATOR = File.separatorChar;

    static {
        if (isSystemWindows()) {
            OTHER_SEPARATOR = '/';
        } else {
            OTHER_SEPARATOR = '\\';
        }
    }

    public static String concat(String str, String str2) {
        int prefixLength = getPrefixLength(str2);
        if (prefixLength < 0) {
            return null;
        }
        if (prefixLength > 0) {
            return normalize(str2);
        }
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return normalize(str2);
        }
        if (isSeparator(str.charAt(length - 1))) {
            return normalize(str + str2);
        }
        return normalize(str + '/' + str2);
    }

    private static String doGetFullPath(String str, boolean z2) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        if (prefixLength >= str.length()) {
            return z2 ? getPrefix(str) : str;
        }
        int iIndexOfLastSeparator = indexOfLastSeparator(str);
        if (iIndexOfLastSeparator < 0) {
            return str.substring(0, prefixLength);
        }
        int i2 = iIndexOfLastSeparator + (z2 ? 1 : 0);
        if (i2 == 0) {
            i2++;
        }
        return str.substring(0, i2);
    }

    private static String doGetPath(String str, int i2) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        int iIndexOfLastSeparator = indexOfLastSeparator(str);
        int i3 = i2 + iIndexOfLastSeparator;
        return (prefixLength >= str.length() || iIndexOfLastSeparator < 0 || prefixLength >= i3) ? "" : str.substring(prefixLength, i3);
    }

    private static String doNormalize(String str, char c3, boolean z2) {
        boolean z3;
        if (str == null) {
            return null;
        }
        int length = str.length();
        if (length == 0) {
            return str;
        }
        int prefixLength = getPrefixLength(str);
        if (prefixLength < 0) {
            return null;
        }
        int i2 = length + 2;
        char[] cArr = new char[i2];
        str.getChars(0, str.length(), cArr, 0);
        char c4 = SYSTEM_SEPARATOR;
        if (c3 == c4) {
            c4 = OTHER_SEPARATOR;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (cArr[i3] == c4) {
                cArr[i3] = c3;
            }
        }
        if (cArr[length - 1] != c3) {
            cArr[length] = c3;
            length++;
            z3 = false;
        } else {
            z3 = true;
        }
        int i4 = prefixLength + 1;
        int i5 = i4;
        while (i5 < length) {
            if (cArr[i5] == c3) {
                int i6 = i5 - 1;
                if (cArr[i6] == c3) {
                    System.arraycopy(cArr, i5, cArr, i6, length - i5);
                    length--;
                    i5--;
                }
            }
            i5++;
        }
        int i7 = i4;
        while (i7 < length) {
            if (cArr[i7] == c3) {
                int i8 = i7 - 1;
                if (cArr[i8] == '.' && (i7 == i4 || cArr[i7 - 2] == c3)) {
                    if (i7 == length - 1) {
                        z3 = true;
                    }
                    System.arraycopy(cArr, i7 + 1, cArr, i8, length - i7);
                    length -= 2;
                    i7--;
                }
            }
            i7++;
        }
        int i9 = prefixLength + 2;
        int i10 = i9;
        while (i10 < length) {
            if (cArr[i10] == c3 && cArr[i10 - 1] == '.' && cArr[i10 - 2] == '.' && (i10 == i9 || cArr[i10 - 3] == c3)) {
                if (i10 == i9) {
                    return null;
                }
                if (i10 == length - 1) {
                    z3 = true;
                }
                int i11 = i10 - 4;
                while (true) {
                    if (i11 < prefixLength) {
                        int i12 = i10 + 1;
                        System.arraycopy(cArr, i12, cArr, prefixLength, length - i10);
                        length -= i12 - prefixLength;
                        i10 = i4;
                        break;
                    }
                    if (cArr[i11] == c3) {
                        int i13 = i11 + 1;
                        System.arraycopy(cArr, i10 + 1, cArr, i13, length - i10);
                        length -= i10 - i11;
                        i10 = i13;
                        break;
                    }
                    i11--;
                }
            }
            i10++;
        }
        return length <= 0 ? "" : length <= prefixLength ? new String(cArr, 0, length) : (z3 && z2) ? new String(cArr, 0, length) : new String(cArr, 0, length - 1);
    }

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        }
        int iIndexOfExtension = indexOfExtension(str);
        return iIndexOfExtension == -1 ? "" : str.substring(iIndexOfExtension + 1);
    }

    public static String getFullPath(String str) {
        return doGetFullPath(str, true);
    }

    public static String getFullPathNoEndSeparator(String str) {
        return doGetFullPath(str, false);
    }

    public static String getName(String str) {
        if (str == null) {
            return null;
        }
        return str.substring(indexOfLastSeparator(str) + 1);
    }

    public static String getPath(String str) {
        return doGetPath(str, 1);
    }

    public static String getPathNoEndSeparator(String str) {
        return doGetPath(str, 0);
    }

    public static String getPrefix(String str) {
        int prefixLength;
        if (str == null || (prefixLength = getPrefixLength(str)) < 0) {
            return null;
        }
        if (prefixLength <= str.length()) {
            return str.substring(0, prefixLength);
        }
        return str + '/';
    }

    public static int getPrefixLength(String str) {
        int iMin;
        if (str == null) {
            return -1;
        }
        int length = str.length();
        if (length == 0) {
            return 0;
        }
        char cCharAt = str.charAt(0);
        if (cCharAt == ':') {
            return -1;
        }
        if (length == 1) {
            if (cCharAt == '~') {
                return 2;
            }
            return isSeparator(cCharAt) ? 1 : 0;
        }
        if (cCharAt == '~') {
            int iIndexOf = str.indexOf(47, 1);
            int iIndexOf2 = str.indexOf(92, 1);
            if (iIndexOf == -1 && iIndexOf2 == -1) {
                return length + 1;
            }
            if (iIndexOf == -1) {
                iIndexOf = iIndexOf2;
            }
            if (iIndexOf2 == -1) {
                iIndexOf2 = iIndexOf;
            }
            iMin = Math.min(iIndexOf, iIndexOf2);
        } else {
            char cCharAt2 = str.charAt(1);
            if (cCharAt2 == ':') {
                char upperCase = Character.toUpperCase(cCharAt);
                if (upperCase < 'A' || upperCase > 'Z') {
                    return -1;
                }
                return (length == 2 || !isSeparator(str.charAt(2))) ? 2 : 3;
            }
            if (!isSeparator(cCharAt) || !isSeparator(cCharAt2)) {
                return isSeparator(cCharAt) ? 1 : 0;
            }
            int iIndexOf3 = str.indexOf(47, 2);
            int iIndexOf4 = str.indexOf(92, 2);
            if ((iIndexOf3 == -1 && iIndexOf4 == -1) || iIndexOf3 == 2 || iIndexOf4 == 2) {
                return -1;
            }
            if (iIndexOf3 == -1) {
                iIndexOf3 = iIndexOf4;
            }
            if (iIndexOf4 == -1) {
                iIndexOf4 = iIndexOf3;
            }
            iMin = Math.min(iIndexOf3, iIndexOf4);
        }
        return iMin + 1;
    }

    public static int indexOfExtension(String str) {
        int iLastIndexOf;
        if (str != null && indexOfLastSeparator(str) <= (iLastIndexOf = str.lastIndexOf(46))) {
            return iLastIndexOf;
        }
        return -1;
    }

    public static int indexOfLastSeparator(String str) {
        if (str == null) {
            return -1;
        }
        return Math.max(str.lastIndexOf(47), str.lastIndexOf(92));
    }

    public static boolean isExtension(String str, String str2) {
        if (str == null) {
            return false;
        }
        return (str2 == null || str2.length() == 0) ? indexOfExtension(str) == -1 : getExtension(str).equals(str2);
    }

    private static boolean isSeparator(char c3) {
        return c3 == '/' || c3 == '\\';
    }

    public static boolean isSystemWindows() {
        return SYSTEM_SEPARATOR == '\\';
    }

    public static String normalize(String str) {
        return doNormalize(str, SYSTEM_SEPARATOR, true);
    }

    public static String normalizeNoEndSeparator(String str) {
        return doNormalize(str, SYSTEM_SEPARATOR, false);
    }

    public static String separatorsToSystem(String str) {
        if (str == null) {
            return null;
        }
        return isSystemWindows() ? separatorsToWindows(str) : separatorsToUnix(str);
    }

    public static String separatorsToUnix(String str) {
        return (str == null || str.indexOf(92) == -1) ? str : str.replace('\\', '/');
    }

    public static String separatorsToWindows(String str) {
        return (str == null || str.indexOf(47) == -1) ? str : str.replace('/', '\\');
    }

    public static String[] splitOnTokens(String str) {
        if (str.indexOf(63) == -1 && str.indexOf(42) == -1) {
            return new String[]{str};
        }
        char[] charArray = str.toCharArray();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            char c3 = charArray[i2];
            if (c3 == '?' || c3 == '*') {
                if (sb.length() != 0) {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                }
                if (charArray[i2] == '?') {
                    arrayList.add("?");
                } else if (arrayList.isEmpty() || (i2 > 0 && !((String) arrayList.get(arrayList.size() - 1)).equals("*"))) {
                    arrayList.add("*");
                }
            } else {
                sb.append(c3);
            }
        }
        if (sb.length() != 0) {
            arrayList.add(sb.toString());
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static String normalize(String str, boolean z2) {
        return doNormalize(str, z2 ? '/' : '\\', true);
    }

    public static String normalizeNoEndSeparator(String str, boolean z2) {
        return doNormalize(str, z2 ? '/' : '\\', false);
    }

    public static boolean isExtension(String str, String[] strArr) {
        if (str == null) {
            return false;
        }
        if (strArr == null || strArr.length == 0) {
            return indexOfExtension(str) == -1;
        }
        String extension = getExtension(str);
        for (String str2 : strArr) {
            if (extension.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isExtension(String str, Collection<String> collection) {
        if (str == null) {
            return false;
        }
        if (collection == null || collection.isEmpty()) {
            return indexOfExtension(str) == -1;
        }
        String extension = getExtension(str);
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            if (extension.equals(it.next())) {
                return true;
            }
        }
        return false;
    }
}
