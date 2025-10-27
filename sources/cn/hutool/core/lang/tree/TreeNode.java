package cn.hutool.core.lang.tree;

import g0.a;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes.dex */
public class TreeNode<T> implements Node<T> {
    private static final long serialVersionUID = 1;
    private Map<String, Object> extra;
    private T id;
    private CharSequence name;
    private T parentId;
    private Comparable<?> weight;

    public TreeNode() {
        this.weight = 0;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public /* synthetic */ int compareTo(Node node) {
        return a.a(this, node);
    }

    @Override // cn.hutool.core.lang.tree.Node, java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return compareTo((Node) obj);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.id, ((TreeNode) obj).id);
    }

    public Map<String, Object> getExtra() {
        return this.extra;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public T getId() {
        return this.id;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public CharSequence getName() {
        return this.name;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public T getParentId() {
        return this.parentId;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public Comparable<?> getWeight() {
        return this.weight;
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }

    public TreeNode<T> setExtra(Map<String, Object> map) {
        this.extra = map;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.lang.tree.Node
    public /* bridge */ /* synthetic */ Node setId(Object obj) {
        return setId((TreeNode<T>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.lang.tree.Node
    public /* bridge */ /* synthetic */ Node setParentId(Object obj) {
        return setParentId((TreeNode<T>) obj);
    }

    @Override // cn.hutool.core.lang.tree.Node
    public /* bridge */ /* synthetic */ Node setWeight(Comparable comparable) {
        return setWeight((Comparable<?>) comparable);
    }

    @Override // cn.hutool.core.lang.tree.Node
    public TreeNode<T> setId(T t2) {
        this.id = t2;
        return this;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public TreeNode<T> setName(CharSequence charSequence) {
        this.name = charSequence;
        return this;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public TreeNode<T> setParentId(T t2) {
        this.parentId = t2;
        return this;
    }

    @Override // cn.hutool.core.lang.tree.Node
    public TreeNode<T> setWeight(Comparable<?> comparable) {
        this.weight = comparable;
        return this;
    }

    public TreeNode(T t2, T t3, String str, Comparable<?> comparable) {
        this.weight = 0;
        this.id = t2;
        this.parentId = t3;
        this.name = str;
        if (comparable != null) {
            this.weight = comparable;
        }
    }
}
