package cn.hutool.core.lang.tree.parser;

import cn.hutool.core.lang.tree.Tree;

@FunctionalInterface
/* loaded from: classes.dex */
public interface NodeParser<T, E> {
    void parse(T t2, Tree<E> tree);
}
