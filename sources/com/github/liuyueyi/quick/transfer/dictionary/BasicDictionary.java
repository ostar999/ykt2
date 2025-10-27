package com.github.liuyueyi.quick.transfer.dictionary;

import com.github.liuyueyi.quick.transfer.Trie;
import com.github.liuyueyi.quick.transfer.TrieNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/* loaded from: classes3.dex */
public class BasicDictionary {
    protected Map<Character, Character> charMap;
    protected Trie<String> dict;
    private int maxLen;
    protected String name;

    public BasicDictionary(String str, Map<Character, Character> map, Trie<String> trie, int i2) {
        this.name = str;
        this.charMap = map;
        this.dict = trie;
        this.maxLen = i2;
    }

    public char convert(char c3) {
        Character ch = this.charMap.get(Character.valueOf(c3));
        return ch == null ? c3 : ch.charValue();
    }

    public Map<Character, Character> getChars() {
        return this.charMap;
    }

    public Trie<String> getDict() {
        return this.dict;
    }

    public int getMaxLen() {
        return this.maxLen;
    }

    public void convert(Reader reader, Writer writer) throws IOException {
        PushbackReader pushbackReader = new PushbackReader(new BufferedReader(reader), this.maxLen);
        char[] cArr = new char[this.maxLen];
        while (true) {
            int i2 = pushbackReader.read(cArr);
            if (i2 == -1) {
                return;
            }
            TrieNode<String> trieNodeBestMatch = this.dict.bestMatch(cArr, 0, i2);
            if (trieNodeBestMatch != null) {
                int level = trieNodeBestMatch.getLevel();
                writer.write(trieNodeBestMatch.getValue());
                pushbackReader.unread(cArr, level, i2 - level);
            } else {
                pushbackReader.unread(cArr, 0, i2);
                writer.write(convert((char) pushbackReader.read()));
            }
        }
    }

    public String convert(String str) {
        StringReader stringReader = new StringReader(str);
        StringWriter stringWriter = new StringWriter();
        try {
            convert(stringReader, stringWriter);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return stringWriter.toString();
    }
}
