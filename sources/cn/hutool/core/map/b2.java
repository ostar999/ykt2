package cn.hutool.core.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import java.util.Map;

/* loaded from: classes.dex */
public final /* synthetic */ class b2<K, V> {
    public static boolean a(TreeEntry treeEntry, Object obj) {
        return ObjectUtil.isNotNull(treeEntry.getChild(obj));
    }

    public static boolean b(TreeEntry treeEntry, Object obj) {
        return ObjectUtil.isNotNull(treeEntry.getParent(obj));
    }

    public static boolean c(TreeEntry treeEntry) {
        return CollUtil.isNotEmpty((Map<?, ?>) treeEntry.getDeclaredChildren());
    }

    public static boolean d(TreeEntry treeEntry) {
        return ObjectUtil.isNotNull(treeEntry.getDeclaredParent());
    }
}
