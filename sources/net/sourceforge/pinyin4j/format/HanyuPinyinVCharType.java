package net.sourceforge.pinyin4j.format;

/* loaded from: classes9.dex */
public class HanyuPinyinVCharType {
    protected String name;
    public static final HanyuPinyinVCharType WITH_U_AND_COLON = new HanyuPinyinVCharType("WITH_U_AND_COLON");
    public static final HanyuPinyinVCharType WITH_V = new HanyuPinyinVCharType("WITH_V");
    public static final HanyuPinyinVCharType WITH_U_UNICODE = new HanyuPinyinVCharType("WITH_U_UNICODE");

    public HanyuPinyinVCharType(String str) {
        setName(str);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }
}
