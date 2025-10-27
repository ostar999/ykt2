package com.github.liuyueyi.quick.transfer;

/* loaded from: classes3.dex */
public class Trie<T> {
    private TrieNode<T> root = new TrieNode<>(' ');

    public void add(char[] cArr, T t2) {
        if (cArr.length < 1) {
            return;
        }
        TrieNode<T> trieNodeAddChild = this.root;
        for (int i2 = 0; i2 < cArr.length; i2++) {
            TrieNode<T> trieNodeChild = trieNodeAddChild.child(cArr[i2]);
            trieNodeAddChild = trieNodeChild == null ? trieNodeAddChild.addChild(cArr[i2]) : trieNodeChild;
        }
        trieNodeAddChild.setLeaf(true);
        trieNodeAddChild.setValue(t2);
    }

    public TrieNode<T> bestMatch(char[] cArr, int i2, int i3) {
        TrieNode<T> trieNodeChild = this.root;
        TrieNode<T> trieNode = null;
        while (i2 < i3) {
            trieNodeChild = trieNodeChild.child(cArr[i2]);
            if (trieNodeChild == null) {
                break;
            }
            if (trieNodeChild.isLeaf()) {
                trieNode = trieNodeChild;
            }
            i2++;
        }
        return trieNode;
    }

    public TrieNode<T> match(char[] cArr, int i2, int i3) {
        TrieNode<T> trieNodeChild = this.root;
        for (int i4 = 0; i4 < i3; i4++) {
            trieNodeChild = trieNodeChild.child(cArr[i2 + i4]);
            if (trieNodeChild == null) {
                return null;
            }
        }
        return trieNodeChild;
    }

    public TrieNode<T> bestMatch(char[] cArr, int i2) {
        return bestMatch(cArr, i2, cArr.length);
    }

    public void add(String str, T t2) {
        if (str == null) {
            return;
        }
        add(str.toCharArray(), (char[]) t2);
    }
}
