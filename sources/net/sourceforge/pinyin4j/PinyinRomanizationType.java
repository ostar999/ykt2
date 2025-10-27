package net.sourceforge.pinyin4j;

/* loaded from: classes9.dex */
class PinyinRomanizationType {
    protected String tagName;
    static final PinyinRomanizationType HANYU_PINYIN = new PinyinRomanizationType("Hanyu");
    static final PinyinRomanizationType WADEGILES_PINYIN = new PinyinRomanizationType("Wade");
    static final PinyinRomanizationType MPS2_PINYIN = new PinyinRomanizationType("MPSII");
    static final PinyinRomanizationType YALE_PINYIN = new PinyinRomanizationType("Yale");
    static final PinyinRomanizationType TONGYONG_PINYIN = new PinyinRomanizationType("Tongyong");
    static final PinyinRomanizationType GWOYEU_ROMATZYH = new PinyinRomanizationType("Gwoyeu");

    public PinyinRomanizationType(String str) {
        setTagName(str);
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String str) {
        this.tagName = str;
    }
}
