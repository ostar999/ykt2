package cn.hutool.core.lang.tree;

import java.io.Serializable;

/* loaded from: classes.dex */
public interface Node<T> extends Comparable<Node<T>>, Serializable {
    int compareTo(Node node);

    @Override // java.lang.Comparable
    /* bridge */ /* synthetic */ int compareTo(Object obj);

    T getId();

    CharSequence getName();

    T getParentId();

    Comparable<?> getWeight();

    Node<T> setId(T t2);

    Node<T> setName(CharSequence charSequence);

    Node<T> setParentId(T t2);

    Node<T> setWeight(Comparable<?> comparable);
}
