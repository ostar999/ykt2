package net.sourceforge.pinyin4j;

/* loaded from: classes9.dex */
class TextHelper {
    public static String extractPinyinString(String str) {
        return str.substring(0, str.length() - 1);
    }

    public static String extractToneNumber(String str) {
        return str.substring(str.length() - 1);
    }
}
