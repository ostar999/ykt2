package g0;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.lang.tree.Node;

/* loaded from: classes.dex */
public final /* synthetic */ class a<T> {
    public static int a(Node node, Node node2) {
        if (node2 == null) {
            return 1;
        }
        return CompareUtil.compare(node.getWeight(), node2.getWeight());
    }
}
