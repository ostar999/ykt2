package cn.hutool.core.lang.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import g0.a;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class Tree<T> extends LinkedHashMap<String, Object> implements Node<T> {
    private static final long serialVersionUID = 1;
    private Tree<T> parent;
    private final TreeNodeConfig treeNodeConfig;

    public Tree() {
        this(null);
    }

    private List<Tree<T>> cloneChildren() {
        List<Tree<T>> children = getChildren();
        if (children == null) {
            return null;
        }
        final ArrayList arrayList = new ArrayList(children.size());
        children.forEach(new Consumer() { // from class: g0.c
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Tree.lambda$cloneChildren$1(arrayList, (Tree) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$cloneChildren$1(List list, Tree tree) {
        list.add(tree.cloneTree());
    }

    private static void printTree(Tree<?> tree, PrintWriter printWriter, int i2) {
        printWriter.println(CharSequenceUtil.format("{}{}[{}]", CharSequenceUtil.repeat(' ', i2), tree.getName(), tree.getId()));
        printWriter.flush();
        List<Tree<?>> children = tree.getChildren();
        if (CollUtil.isNotEmpty((Collection<?>) children)) {
            Iterator<Tree<?>> it = children.iterator();
            while (it.hasNext()) {
                printTree(it.next(), printWriter, i2 + 2);
            }
        }
    }

    @SafeVarargs
    public final Tree<T> addChildren(Tree<T>... treeArr) {
        if (ArrayUtil.isNotEmpty((Object[]) treeArr)) {
            List<Tree<T>> children = getChildren();
            if (children == null) {
                children = new ArrayList<>();
                setChildren(children);
            }
            for (Tree<T> tree : treeArr) {
                tree.setParent(this);
                children.add(tree);
            }
        }
        return this;
    }

    public Tree<T> cloneTree() {
        Tree<T> tree = (Tree) ObjectUtil.clone(this);
        tree.setChildren(cloneChildren());
        return tree;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public /* synthetic */ int compareTo(Node node) {
        return a.a(this, node);
    }

    @Override // cn.hutool.core.lang.tree.Node, java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((Node) obj);
    }

    public Tree<T> filter(Filter<Tree<T>> filter) {
        if (filter.accept(this)) {
            return this;
        }
        List<Tree<T>> children = getChildren();
        if (CollUtil.isNotEmpty((Collection<?>) children)) {
            ArrayList arrayList = new ArrayList(children.size());
            Iterator<Tree<T>> it = children.iterator();
            while (it.hasNext()) {
                Tree<T> treeFilter = it.next().filter(filter);
                if (treeFilter != null) {
                    arrayList.add(treeFilter);
                }
            }
            if (CollUtil.isNotEmpty((Collection<?>) arrayList)) {
                return setChildren(arrayList);
            }
            setChildren(null);
        }
        return null;
    }

    public Tree<T> filterNew(Filter<Tree<T>> filter) {
        return cloneTree().filter(filter);
    }

    public List<Tree<T>> getChildren() {
        return (List) get(this.treeNodeConfig.getChildrenKey());
    }

    public TreeNodeConfig getConfig() {
        return this.treeNodeConfig;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public T getId() {
        return (T) get(this.treeNodeConfig.getIdKey());
    }

    @Override // cn.hutool.core.lang.tree.Node
    public CharSequence getName() {
        return (CharSequence) get(this.treeNodeConfig.getNameKey());
    }

    public Tree<T> getNode(T t2) {
        return TreeUtil.getNode(this, t2);
    }

    public Tree<T> getParent() {
        return this.parent;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public T getParentId() {
        return (T) get(this.treeNodeConfig.getParentIdKey());
    }

    public List<CharSequence> getParentsName(T t2, boolean z2) {
        return TreeUtil.getParentsName(getNode(t2), z2);
    }

    @Override // cn.hutool.core.lang.tree.Node
    public Comparable<?> getWeight() {
        return (Comparable) get(this.treeNodeConfig.getWeightKey());
    }

    public boolean hasChild() {
        return CollUtil.isNotEmpty((Collection<?>) getChildren());
    }

    public void putExtra(String str, Object obj) throws IllegalArgumentException {
        Assert.notEmpty(str, "Key must be not empty !", new Object[0]);
        put(str, obj);
    }

    public Tree<T> setChildren(List<Tree<T>> list) {
        if (list == null) {
            remove(this.treeNodeConfig.getChildrenKey());
        }
        put(this.treeNodeConfig.getChildrenKey(), list);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.lang.tree.Node
    public /* bridge */ /* synthetic */ Node setId(Object obj) {
        return setId((Tree<T>) obj);
    }

    public Tree<T> setParent(Tree<T> tree) {
        this.parent = tree;
        if (tree != null) {
            setParentId((Tree<T>) tree.getId());
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.lang.tree.Node
    public /* bridge */ /* synthetic */ Node setParentId(Object obj) {
        return setParentId((Tree<T>) obj);
    }

    @Override // cn.hutool.core.lang.tree.Node
    public /* bridge */ /* synthetic */ Node setWeight(Comparable comparable) {
        return setWeight((Comparable<?>) comparable);
    }

    @Override // java.util.AbstractMap
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        printTree(this, new PrintWriter(stringWriter), 0);
        return stringWriter.toString();
    }

    public void walk(final Consumer<Tree<T>> consumer) {
        consumer.accept(this);
        List<Tree<T>> children = getChildren();
        if (CollUtil.isNotEmpty((Collection<?>) children)) {
            children.forEach(new Consumer() { // from class: g0.b
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Tree) obj).walk(consumer);
                }
            });
        }
    }

    public Tree(TreeNodeConfig treeNodeConfig) {
        this.treeNodeConfig = (TreeNodeConfig) ObjectUtil.defaultIfNull(treeNodeConfig, TreeNodeConfig.DEFAULT_CONFIG);
    }

    public List<CharSequence> getParentsName(boolean z2) {
        return TreeUtil.getParentsName(this, z2);
    }

    @Override // cn.hutool.core.lang.tree.Node
    public Tree<T> setId(T t2) {
        put(this.treeNodeConfig.getIdKey(), t2);
        return this;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public Tree<T> setName(CharSequence charSequence) {
        put(this.treeNodeConfig.getNameKey(), charSequence);
        return this;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public Tree<T> setParentId(T t2) {
        put(this.treeNodeConfig.getParentIdKey(), t2);
        return this;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public Tree<T> setWeight(Comparable<?> comparable) {
        put(this.treeNodeConfig.getWeightKey(), comparable);
        return this;
    }
}
