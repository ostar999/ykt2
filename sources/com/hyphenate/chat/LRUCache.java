package com.hyphenate.chat;

import java.util.HashMap;

@Deprecated
/* loaded from: classes4.dex */
class LRUCache<K, V> {
    private HashMap<K, LRUCache<K, V>.Node<K, V>> cache;
    private int currentSize;
    private LRUCache<K, V>.Node<K, V> leastRecentlyUsed;
    private int maxSize;
    private LRUCache<K, V>.Node<K, V> mostRecentlyUsed;

    public class Node<T, U> {
        T key;
        LRUCache<K, V>.Node<T, U> next;
        LRUCache<K, V>.Node<T, U> previous;
        U value;

        public Node(LRUCache<K, V>.Node<T, U> node, LRUCache<K, V>.Node<T, U> node2, T t2, U u2) {
            this.previous = node;
            this.next = node2;
            this.key = t2;
            this.value = u2;
        }
    }

    public LRUCache(int i2) {
        this.maxSize = i2 <= 1 ? 2 : i2;
        this.currentSize = 0;
        LRUCache<K, V>.Node<K, V> node = new Node<>(null, null, null, null);
        this.leastRecentlyUsed = node;
        this.mostRecentlyUsed = node;
        this.cache = new HashMap<>();
    }

    public void clear() {
        this.currentSize = 0;
        LRUCache<K, V>.Node<K, V> node = new Node<>(null, null, null, null);
        this.leastRecentlyUsed = node;
        this.mostRecentlyUsed = node;
        this.cache.clear();
    }

    public boolean contains(K k2) {
        return this.cache.containsKey(k2);
    }

    public V get(K k2) {
        Node node = (LRUCache<K, V>.Node<K, V>) ((LRUCache<K, V>.Node<K, V>) this.cache.get(k2));
        if (node == null) {
            return null;
        }
        T t2 = node.key;
        LRUCache<K, V>.Node<K, V> node2 = this.mostRecentlyUsed;
        T t3 = node2.key;
        if (t2 == t3) {
            return (V) node2.value;
        }
        Node node3 = (LRUCache<K, V>.Node<K, V>) node.next;
        LRUCache<K, V>.Node<T, U> node4 = node.previous;
        if (t2 == this.leastRecentlyUsed.key) {
            node3.previous = null;
            this.leastRecentlyUsed = node3;
        } else if (t2 != t3) {
            node4.next = node3;
            node3.previous = node4;
        }
        node.previous = node2;
        node2.next = node;
        this.mostRecentlyUsed = node;
        node.next = null;
        return (V) node.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void put(K k2, V v2) {
        if (this.cache.containsKey(k2)) {
            return;
        }
        Node node = (LRUCache<K, V>.Node<K, V>) new Node(this.mostRecentlyUsed, null, k2, v2);
        this.mostRecentlyUsed.next = node;
        this.cache.put(k2, node);
        this.mostRecentlyUsed = node;
        int i2 = this.currentSize;
        if (i2 != this.maxSize) {
            this.currentSize = i2 + 1;
            return;
        }
        this.cache.remove(this.leastRecentlyUsed.key);
        LRUCache<K, V>.Node<K, V> node2 = this.leastRecentlyUsed.next;
        this.leastRecentlyUsed = node2;
        node2.previous = null;
    }

    public void remove(K k2) {
        LRUCache<K, V>.Node<K, V> node = this.cache.get(k2);
        if (node == null) {
            return;
        }
        this.cache.remove(k2);
        this.currentSize--;
        K k3 = node.key;
        LRUCache<K, V>.Node<K, V> node2 = this.mostRecentlyUsed;
        if (k3 == node2.key) {
            LRUCache<K, V>.Node<K, V> node3 = node2.previous;
            this.mostRecentlyUsed = node3;
            if (node3 != null) {
                node3.next = null;
                return;
            } else {
                this.leastRecentlyUsed = null;
                return;
            }
        }
        LRUCache<K, V>.Node<K, V> node4 = this.leastRecentlyUsed;
        if (k3 != node4.key) {
            LRUCache<K, V>.Node<K, V> node5 = node.next;
            node5.previous = node.previous;
            node.previous.next = node5;
        } else {
            LRUCache<K, V>.Node<K, V> node6 = node4.next;
            this.leastRecentlyUsed = node6;
            if (node6 != null) {
                node6.previous = null;
            } else {
                this.mostRecentlyUsed = null;
            }
        }
    }
}
