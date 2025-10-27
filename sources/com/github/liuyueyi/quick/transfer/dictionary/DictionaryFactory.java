package com.github.liuyueyi.quick.transfer.dictionary;

import com.github.liuyueyi.quick.transfer.Trie;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes3.dex */
public class DictionaryFactory {
    public static final String EMPTY = "";
    public static final String EQUAL = "=";
    public static final String SHARP = "#";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11, types: [int] */
    /* JADX WARN: Type inference failed for: r3v12, types: [int] */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5, types: [int] */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [int] */
    /* JADX WARN: Type inference failed for: r3v8 */
    public static BasicDictionary loadDictionary(String str, boolean z2) throws Throwable {
        HashMap map = new HashMap(8192);
        Trie trie = new Trie();
        char c3 = 2;
        ?? r3 = 0;
        BufferedReader bufferedReader = null;
        try {
            try {
                InputStream resourceAsStream = DictionaryFactory.class.getClassLoader().getResourceAsStream(str);
                Objects.requireNonNull(resourceAsStream);
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new BufferedInputStream(resourceAsStream), StandardCharsets.UTF_8));
                ?? Max = 2;
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line == null) {
                            try {
                                break;
                            } catch (Exception e2) {
                                e2.printStackTrace();
                                r3 = Max;
                            }
                        } else if (line.length() != 0 && !line.startsWith(SHARP)) {
                            String[] strArrSplit = split(line, "=");
                            if (strArrSplit.length >= 2) {
                                if (z2) {
                                    if (strArrSplit[0].length() == 1 && strArrSplit[1].length() == 1) {
                                        map.put(Character.valueOf(strArrSplit[1].charAt(0)), Character.valueOf(strArrSplit[0].charAt(0)));
                                    } else {
                                        Max = Math.max(strArrSplit[0].length(), (int) Max);
                                        trie.add(strArrSplit[1], strArrSplit[0]);
                                    }
                                } else if (strArrSplit[0].length() == 1 && strArrSplit[1].length() == 1) {
                                    map.put(Character.valueOf(strArrSplit[0].charAt(0)), Character.valueOf(strArrSplit[1].charAt(0)));
                                } else {
                                    Max = Math.max(strArrSplit[0].length(), (int) Max);
                                    trie.add(strArrSplit[0], strArrSplit[1]);
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        c3 = Max;
                        bufferedReader = bufferedReader2;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                        r3 = c3;
                        return new BasicDictionary(str, map, trie, r3);
                    } catch (Throwable th) {
                        th = th;
                        r3 = bufferedReader2;
                        if (r3 != 0) {
                            try {
                                r3.close();
                            } catch (Exception e5) {
                                e5.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader2.close();
                r3 = Max;
            } catch (Exception e6) {
                e = e6;
            }
            return new BasicDictionary(str, map, trie, r3);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static SecondParserDictionary loadSecondDictionary(BasicDictionary basicDictionary, String str, boolean z2) throws Throwable {
        BasicDictionary basicDictionaryLoadDictionary = loadDictionary(str, z2);
        return new SecondParserDictionary(str, basicDictionary, basicDictionaryLoadDictionary.getChars(), basicDictionaryLoadDictionary.getDict(), basicDictionaryLoadDictionary.getMaxLen(), z2);
    }

    private static String[] split(String str, String str2) {
        int iIndexOf = str.indexOf(str2);
        return iIndexOf < 0 ? new String[]{str} : new String[]{str.substring(0, iIndexOf), str.substring(iIndexOf + 1)};
    }
}
