package net.sourceforge.pinyin4j.format;

/* loaded from: classes9.dex */
public class HanyuPinyinToneType {
    protected String name;
    public static final HanyuPinyinToneType WITH_TONE_NUMBER = new HanyuPinyinToneType("WITH_TONE_NUMBER");
    public static final HanyuPinyinToneType WITHOUT_TONE = new HanyuPinyinToneType("WITHOUT_TONE");
    public static final HanyuPinyinToneType WITH_TONE_MARK = new HanyuPinyinToneType("WITH_TONE_MARK");

    public HanyuPinyinToneType(String str) {
        setName(str);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }
}
