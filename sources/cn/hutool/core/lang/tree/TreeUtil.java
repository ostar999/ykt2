package cn.hutool.core.lang.tree;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.ObjectUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class TreeUtil {
    public static List<Tree<Integer>> build(List<TreeNode<Integer>> list) {
        return build((List<TreeNode<int>>) list, 0);
    }

    public static Tree<Integer> buildSingle(List<TreeNode<Integer>> list) {
        return buildSingle((List<TreeNode<int>>) list, 0);
    }

    public static <E> Tree<E> createEmptyNode(E e2) {
        return new Tree().setId((Tree) e2);
    }

    public static <T> Tree<T> getNode(Tree<T> tree, T t2) {
        if (ObjectUtil.equal(t2, tree.getId())) {
            return tree;
        }
        List<Tree<T>> children = tree.getChildren();
        if (children == null) {
            return null;
        }
        Iterator<Tree<T>> it = children.iterator();
        while (it.hasNext()) {
            Tree<T> node = it.next().getNode(t2);
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    public static <T> List<T> getParentsId(Tree<T> tree, boolean z2) {
        ArrayList arrayList = new ArrayList();
        if (tree == null) {
            return arrayList;
        }
        if (z2) {
            arrayList.add(tree.getId());
        }
        Tree<T> parent = tree.getParent();
        while (parent != null) {
            T id = parent.getId();
            parent = parent.getParent();
            if (id != null || parent != null) {
                arrayList.add(id);
            }
        }
        return arrayList;
    }

    public static <T> List<CharSequence> getParentsName(Tree<T> tree, boolean z2) {
        ArrayList arrayList = new ArrayList();
        if (tree == null) {
            return arrayList;
        }
        if (z2) {
            arrayList.add(tree.getName());
        }
        Tree<T> parent = tree.getParent();
        while (parent != null) {
            CharSequence name = parent.getName();
            parent = parent.getParent();
            if (name != null || parent != null) {
                arrayList.add(name);
            }
        }
        return arrayList;
    }

    public static <E> List<Tree<E>> build(List<TreeNode<E>> list, E e2) {
        return build(list, e2, TreeNodeConfig.DEFAULT_CONFIG, new DefaultNodeParser());
    }

    public static <E> Tree<E> buildSingle(List<TreeNode<E>> list, E e2) {
        return buildSingle(list, e2, TreeNodeConfig.DEFAULT_CONFIG, new DefaultNodeParser());
    }

    public static <T, E> List<Tree<E>> build(List<T> list, E e2, NodeParser<T, E> nodeParser) {
        return build(list, e2, TreeNodeConfig.DEFAULT_CONFIG, nodeParser);
    }

    public static <T, E> Tree<E> buildSingle(List<T> list, E e2, NodeParser<T, E> nodeParser) {
        return buildSingle(list, e2, TreeNodeConfig.DEFAULT_CONFIG, nodeParser);
    }

    public static <T, E> List<Tree<E>> build(List<T> list, E e2, TreeNodeConfig treeNodeConfig, NodeParser<T, E> nodeParser) {
        return buildSingle(list, e2, treeNodeConfig, nodeParser).getChildren();
    }

    public static <T, E> Tree<E> buildSingle(List<T> list, E e2, TreeNodeConfig treeNodeConfig, NodeParser<T, E> nodeParser) {
        return TreeBuilder.of(e2, treeNodeConfig).append(list, e2, nodeParser).build();
    }

    public static <E> List<Tree<E>> build(Map<E, Tree<E>> map, E e2) {
        return buildSingle(map, e2).getChildren();
    }

    public static <E> Tree<E> buildSingle(Map<E, Tree<E>> map, E e2) {
        Tree tree = (Tree) IterUtil.getFirstNoneNull(map.values());
        if (tree != null) {
            return TreeBuilder.of(e2, tree.getConfig()).append(map).build();
        }
        return createEmptyNode(e2);
    }
}
