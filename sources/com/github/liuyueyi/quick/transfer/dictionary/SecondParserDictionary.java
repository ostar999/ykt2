package com.github.liuyueyi.quick.transfer.dictionary;

import com.github.liuyueyi.quick.transfer.Trie;
import java.util.Map;

/* loaded from: classes3.dex */
public class SecondParserDictionary extends BasicDictionary {
    private BasicDictionary parentDictionary;
    private boolean reverse;

    public SecondParserDictionary(String str, BasicDictionary basicDictionary, Map<Character, Character> map, Trie<String> trie, int i2, boolean z2) {
        super(str, map, trie, i2);
        this.parentDictionary = basicDictionary;
        this.reverse = z2;
    }

    @Override // com.github.liuyueyi.quick.transfer.dictionary.BasicDictionary
    public String convert(String str) {
        if (!this.reverse) {
            return super.convert(this.parentDictionary.convert(str));
        }
        return this.parentDictionary.convert(super.convert(str));
    }
}
