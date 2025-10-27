package net.sourceforge.pinyin4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/* loaded from: classes9.dex */
class ChineseToPinyinResource {
    private Properties unicodeToHanyuPinyinTable;

    public static class ChineseToPinyinResourceHolder {
        static final ChineseToPinyinResource theInstance = new ChineseToPinyinResource();

        private ChineseToPinyinResourceHolder() {
        }
    }

    public class Field {
        static final String COMMA = ",";
        static final String LEFT_BRACKET = "(";
        static final String RIGHT_BRACKET = ")";

        public Field() {
        }
    }

    private ChineseToPinyinResource() throws IOException {
        this.unicodeToHanyuPinyinTable = null;
        initializeResource();
    }

    private String getHanyuPinyinRecordFromChar(char c3) {
        String property = getUnicodeToHanyuPinyinTable().getProperty(Integer.toHexString(c3).toUpperCase());
        if (isValidRecord(property)) {
            return property;
        }
        return null;
    }

    public static ChineseToPinyinResource getInstance() {
        return ChineseToPinyinResourceHolder.theInstance;
    }

    private Properties getUnicodeToHanyuPinyinTable() {
        return this.unicodeToHanyuPinyinTable;
    }

    private void initializeResource() throws IOException {
        try {
            setUnicodeToHanyuPinyinTable(new Properties());
            getUnicodeToHanyuPinyinTable().load(ResourceHelper.getResourceInputStream("/pinyindb/unicode_to_hanyu_pinyin.txt"));
        } catch (FileNotFoundException | IOException e2) {
            e2.printStackTrace();
        }
    }

    private boolean isValidRecord(String str) {
        return str != null && !str.equals("(none0)") && str.startsWith("(") && str.endsWith(")");
    }

    private void setUnicodeToHanyuPinyinTable(Properties properties) {
        this.unicodeToHanyuPinyinTable = properties;
    }

    public String[] getHanyuPinyinStringArray(char c3) {
        String hanyuPinyinRecordFromChar = getHanyuPinyinRecordFromChar(c3);
        if (hanyuPinyinRecordFromChar == null) {
            return null;
        }
        return hanyuPinyinRecordFromChar.substring(hanyuPinyinRecordFromChar.indexOf("(") + 1, hanyuPinyinRecordFromChar.lastIndexOf(")")).split(",");
    }
}
