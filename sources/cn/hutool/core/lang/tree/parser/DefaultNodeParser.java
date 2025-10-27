package cn.hutool.core.lang.tree.parser;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.map.MapUtil;
import java.util.Map;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public class DefaultNodeParser<T> implements NodeParser<TreeNode<T>, T> {
    @Override // cn.hutool.core.lang.tree.parser.NodeParser
    public void parse(TreeNode<T> treeNode, final Tree<T> tree) {
        tree.setId((Tree<T>) treeNode.getId());
        tree.setParentId((Tree<T>) treeNode.getParentId());
        tree.setWeight(treeNode.getWeight());
        tree.setName(treeNode.getName());
        Map<String, Object> extra = treeNode.getExtra();
        if (MapUtil.isNotEmpty(extra)) {
            extra.forEach(new BiConsumer() { // from class: h0.a
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) throws IllegalArgumentException {
                    tree.putExtra((String) obj, obj2);
                }
            });
        }
    }
}
