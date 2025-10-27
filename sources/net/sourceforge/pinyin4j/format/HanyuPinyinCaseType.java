package net.sourceforge.pinyin4j.format;

/* loaded from: classes9.dex */
public class HanyuPinyinCaseType {
    protected String name;
    public static final HanyuPinyinCaseType UPPERCASE = new HanyuPinyinCaseType("UPPERCASE");
    public static final HanyuPinyinCaseType LOWERCASE = new HanyuPinyinCaseType("LOWERCASE");

    public HanyuPinyinCaseType(String str) {
        setName(str);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }
}
