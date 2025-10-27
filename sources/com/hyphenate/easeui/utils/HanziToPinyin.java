package com.hyphenate.easeui.utils;

import android.icu.text.Transliterator;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import net.sourceforge.pinyin4j.PinyinHelper;

/* loaded from: classes4.dex */
public class HanziToPinyin {
    private static final String TAG = "HanziToPinyin";
    private static HanziToPinyin sInstance;
    private Transliterator mAsciiTransliterator;
    private Transliterator mPinyinTransliterator;

    public static class Token {
        public static final int LATIN = 1;
        public static final int PINYIN = 2;
        public static final String SEPARATOR = " ";
        public static final int UNKNOWN = 3;
        public String source;
        public String target;
        public int type;

        public Token() {
        }

        public Token(int i2, String str, String str2) {
            this.type = i2;
            this.source = str;
            this.target = str2;
        }
    }

    private HanziToPinyin() {
        try {
            this.mPinyinTransliterator = Transliterator.getInstance("Han-Latin/Names; Latin-Ascii; Any-Upper");
            this.mAsciiTransliterator = Transliterator.getInstance("Latin-Ascii");
        } catch (IllegalArgumentException unused) {
            Log.w(TAG, "Han-Latin/Names transliterator data is missing, HanziToPinyin is disabled");
        }
    }

    private void addToken(StringBuilder sb, ArrayList<Token> arrayList, int i2) {
        String string = sb.toString();
        arrayList.add(new Token(i2, string, string));
        sb.setLength(0);
    }

    public static HanziToPinyin getInstance() {
        HanziToPinyin hanziToPinyin;
        synchronized (HanziToPinyin.class) {
            if (sInstance == null) {
                sInstance = new HanziToPinyin();
            }
            hanziToPinyin = sInstance;
        }
        return hanziToPinyin;
    }

    public static String getPinyin(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] hanyuPinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(str.trim().charAt(0));
        return hanyuPinyinStringArray != null ? hanyuPinyinStringArray[0].substring(0, 1).toUpperCase() : str;
    }

    private void tokenize(char c3, Token token) {
        String string = Character.toString(c3);
        token.source = string;
        if (c3 < 128) {
            token.type = 1;
            token.target = string;
            return;
        }
        if (c3 < 592 || (7680 <= c3 && c3 < 7935)) {
            token.type = 1;
            Transliterator transliterator = this.mAsciiTransliterator;
            if (transliterator != null) {
                string = transliterator.transliterate(string);
            }
            token.target = string;
            return;
        }
        token.type = 2;
        String strTransliterate = this.mPinyinTransliterator.transliterate(string);
        token.target = strTransliterate;
        if (TextUtils.isEmpty(strTransliterate) || TextUtils.equals(token.source, token.target)) {
            token.type = 3;
            token.target = token.source;
        }
    }

    public ArrayList<Token> get(String str) {
        ArrayList<Token> arrayList = new ArrayList<>();
        if (hasChineseTransliterator() && !TextUtils.isEmpty(str)) {
            int length = str.length();
            StringBuilder sb = new StringBuilder();
            Token token = new Token();
            int i2 = 1;
            for (int i3 = 0; i3 < length; i3++) {
                char cCharAt = str.charAt(i3);
                if (!Character.isSpaceChar(cCharAt)) {
                    tokenize(cCharAt, token);
                    int i4 = token.type;
                    if (i4 == 2) {
                        if (sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                        arrayList.add(token);
                        token = new Token();
                    } else {
                        if (i2 != i4 && sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                        sb.append(token.target);
                    }
                    i2 = token.type;
                } else if (sb.length() > 0) {
                    addToken(sb, arrayList, i2);
                }
            }
            if (sb.length() > 0) {
                addToken(sb, arrayList, i2);
            }
        }
        return arrayList;
    }

    public boolean hasChineseTransliterator() {
        return this.mPinyinTransliterator != null;
    }

    public String transliterate(String str) {
        if (!hasChineseTransliterator() || TextUtils.isEmpty(str)) {
            return null;
        }
        return this.mPinyinTransliterator.transliterate(str);
    }
}
