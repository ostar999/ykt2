package cn.hutool.core.lang.tree;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class TreeBuilder<E> implements Builder<Tree<E>> {
    private static final long serialVersionUID = 1;
    private final Map<E, Tree<E>> idTreeMap;
    private boolean isBuild;
    private final Tree<E> root;

    public TreeBuilder(E e2, TreeNodeConfig treeNodeConfig) {
        Tree<E> tree = new Tree<>(treeNodeConfig);
        this.root = tree;
        tree.setId((Tree<E>) e2);
        this.idTreeMap = new LinkedHashMap();
    }

    private void buildFromMap() {
        if (MapUtil.isEmpty(this.idTreeMap)) {
            return;
        }
        Map mapSortByValue = MapUtil.sortByValue(this.idTreeMap, false);
        for (Tree<E> tree : mapSortByValue.values()) {
            if (tree != null) {
                E parentId = tree.getParentId();
                if (ObjectUtil.equals(this.root.getId(), parentId)) {
                    this.root.addChildren(tree);
                } else {
                    Tree tree2 = (Tree) mapSortByValue.get(parentId);
                    if (tree2 != null) {
                        tree2.addChildren(tree);
                    }
                }
            }
        }
    }

    private void checkBuilt() throws Throwable {
        Assert.isFalse(this.isBuild, "Current tree has been built.", new Object[0]);
    }

    private void cutTree() {
        Integer deep = this.root.getConfig().getDeep();
        if (deep == null || deep.intValue() < 0) {
            return;
        }
        cutTree(this.root, 0, deep.intValue());
    }

    public static <T> TreeBuilder<T> of(T t2) {
        return of(t2, null);
    }

    public TreeBuilder<E> append(Map<E, Tree<E>> map) throws Throwable {
        checkBuilt();
        this.idTreeMap.putAll(map);
        return this;
    }

    public List<Tree<E>> buildList() {
        return this.isBuild ? this.root.getChildren() : build().getChildren();
    }

    public TreeBuilder<E> putExtra(String str, Object obj) throws IllegalArgumentException {
        Assert.notEmpty(str, "Key must be not empty !", new Object[0]);
        this.root.put(str, obj);
        return this;
    }

    public TreeBuilder<E> reset() {
        this.idTreeMap.clear();
        this.root.setChildren(null);
        this.isBuild = false;
        return this;
    }

    public TreeBuilder<E> setId(E e2) {
        this.root.setId((Tree<E>) e2);
        return this;
    }

    public TreeBuilder<E> setName(CharSequence charSequence) {
        this.root.setName(charSequence);
        return this;
    }

    public TreeBuilder<E> setParentId(E e2) {
        this.root.setParentId((Tree<E>) e2);
        return this;
    }

    public TreeBuilder<E> setWeight(Comparable<?> comparable) {
        this.root.setWeight(comparable);
        return this;
    }

    public static <T> TreeBuilder<T> of(T t2, TreeNodeConfig treeNodeConfig) {
        return new TreeBuilder<>(t2, treeNodeConfig);
    }

    @Override // cn.hutool.core.builder.Builder
    public Tree<E> build() throws Throwable {
        checkBuilt();
        buildFromMap();
        cutTree();
        this.isBuild = true;
        this.idTreeMap.clear();
        return this.root;
    }

    public TreeBuilder<E> append(Iterable<Tree<E>> iterable) throws Throwable {
        checkBuilt();
        for (Tree<E> tree : iterable) {
            this.idTreeMap.put(tree.getId(), tree);
        }
        return this;
    }

    private void cutTree(Tree<E> tree, int i2, int i3) {
        if (tree == null) {
            return;
        }
        if (i2 == i3) {
            tree.setChildren(null);
            return;
        }
        List<Tree<E>> children = tree.getChildren();
        if (CollUtil.isNotEmpty((Collection<?>) children)) {
            Iterator<Tree<E>> it = children.iterator();
            while (it.hasNext()) {
                cutTree(it.next(), i2 + 1, i3);
            }
        }
    }

    public <T> TreeBuilder<E> append(List<T> list, NodeParser<T, E> nodeParser) {
        return append(list, null, nodeParser);
    }

    public <T> TreeBuilder<E> append(List<T> list, E e2, NodeParser<T, E> nodeParser) throws Throwable {
        checkBuilt();
        TreeNodeConfig config = this.root.getConfig();
        LinkedHashMap linkedHashMap = new LinkedHashMap(list.size(), 1.0f);
        for (T t2 : list) {
            Tree<E> tree = new Tree<>(config);
            nodeParser.parse(t2, tree);
            if (e2 != null && !e2.getClass().equals(tree.getId().getClass())) {
                throw new IllegalArgumentException("rootId type is node.getId().getClass()!");
            }
            linkedHashMap.put(tree.getId(), tree);
        }
        return append(linkedHashMap);
    }
}
