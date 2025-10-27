package org.jsoup.nodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/* loaded from: classes9.dex */
public class Entities {
    private static final Map<String, Character> base;
    private static final Map<Character, String> baseByVal;
    private static final Map<String, Character> full;
    private static final Map<Character, String> fullByVal;
    private static final Object[][] xhtmlArray;
    private static final Map<Character, String> xhtmlByVal = new HashMap();

    public enum EscapeMode {
        xhtml(Entities.xhtmlByVal),
        base(Entities.baseByVal),
        extended(Entities.fullByVal);

        private Map<Character, String> map;

        EscapeMode(Map map) {
            this.map = map;
        }

        public Map<Character, String> getMap() {
            return this.map;
        }
    }

    static {
        Object[][] objArr = {new Object[]{"quot", 34}, new Object[]{"amp", 38}, new Object[]{"lt", 60}, new Object[]{"gt", 62}};
        xhtmlArray = objArr;
        Map<String, Character> mapLoadEntities = loadEntities("entities-base.properties");
        base = mapLoadEntities;
        baseByVal = toCharacterKey(mapLoadEntities);
        Map<String, Character> mapLoadEntities2 = loadEntities("entities-full.properties");
        full = mapLoadEntities2;
        fullByVal = toCharacterKey(mapLoadEntities2);
        for (Object[] objArr2 : objArr) {
            xhtmlByVal.put(Character.valueOf((char) ((Integer) objArr2[1]).intValue()), (String) objArr2[0]);
        }
    }

    private Entities() {
    }

    public static String escape(String str, Document.OutputSettings outputSettings) {
        StringBuilder sb = new StringBuilder(str.length() * 2);
        escape(sb, str, outputSettings, false, false, false);
        return sb.toString();
    }

    public static Character getCharacterByName(String str) {
        return full.get(str);
    }

    public static boolean isBaseNamedEntity(String str) {
        return base.containsKey(str);
    }

    public static boolean isNamedEntity(String str) {
        return full.containsKey(str);
    }

    private static Map<String, Character> loadEntities(String str) throws IOException {
        Properties properties = new Properties();
        HashMap map = new HashMap();
        try {
            InputStream resourceAsStream = Entities.class.getResourceAsStream(str);
            properties.load(resourceAsStream);
            resourceAsStream.close();
            for (Map.Entry entry : properties.entrySet()) {
                map.put((String) entry.getKey(), Character.valueOf((char) Integer.parseInt((String) entry.getValue(), 16)));
            }
            return map;
        } catch (IOException e2) {
            throw new MissingResourceException("Error loading entities resource: " + e2.getMessage(), "Entities", str);
        }
    }

    private static Map<Character, String> toCharacterKey(Map<String, Character> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, Character> entry : map.entrySet()) {
            Character value = entry.getValue();
            String key = entry.getKey();
            if (!map2.containsKey(value)) {
                map2.put(value, key);
            } else if (key.toLowerCase().equals(key)) {
                map2.put(value, key);
            }
        }
        return map2;
    }

    public static String unescape(String str) {
        return unescape(str, false);
    }

    public static String unescape(String str, boolean z2) {
        return Parser.unescapeEntities(str, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void escape(java.lang.StringBuilder r16, java.lang.String r17, org.jsoup.nodes.Document.OutputSettings r18, boolean r19, boolean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.nodes.Entities.escape(java.lang.StringBuilder, java.lang.String, org.jsoup.nodes.Document$OutputSettings, boolean, boolean, boolean):void");
    }
}
