package com.github.liuyueyi.quick.transfer;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class TrieNode<T> {
    private char key;
    private boolean leaf;
    private T value;
    private int level = 0;
    private Map<Character, TrieNode<T>> children = new HashMap();

    public TrieNode(char c3) {
        this.key = c3;
    }

    public TrieNode<T> addChild(char c3) {
        TrieNode<T> trieNode = new TrieNode<>(c3);
        trieNode.level = this.level + 1;
        this.children.put(Character.valueOf(c3), trieNode);
        return trieNode;
    }

    public TrieNode<T> child(char c3) {
        return this.children.get(Character.valueOf(c3));
    }

    public char getKey() {
        return this.key;
    }

    public int getLevel() {
        return this.level;
    }

    public T getValue() {
        return this.value;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setKey(char c3) {
        this.key = c3;
    }

    public void setLeaf(boolean z2) {
        this.leaf = z2;
    }

    public void setLevel(int i2) {
        this.level = i2;
    }

    public void setValue(T t2) {
        this.value = t2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.key);
        if (this.value != null) {
            sb.append(":");
            sb.append(this.value);
        }
        return sb.toString();
    }
}
