package cn.hutool.core.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public final /* synthetic */ class q<K, V> {
    public static boolean a(ForestMap forestMap, Object obj, final Object obj2) {
        return ((Boolean) Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.i
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return Boolean.valueOf(((TreeEntry) obj3).containsChild(obj2));
            }
        }).orElse(Boolean.FALSE)).booleanValue();
    }

    public static boolean b(ForestMap forestMap, Object obj, final Object obj2) {
        return ((Boolean) Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.j
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return Boolean.valueOf(((TreeEntry) obj3).containsParent(obj2));
            }
        }).orElse(Boolean.FALSE)).booleanValue();
    }

    public static Collection c(ForestMap forestMap, Object obj) {
        return (Collection) Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.k
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return ((TreeEntry) obj2).getChildren();
            }
        }).map(new l()).orElseGet(new m());
    }

    public static Collection d(ForestMap forestMap, Object obj) {
        return (Collection) Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.p
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return ((TreeEntry) obj2).getDeclaredChildren();
            }
        }).map(new l()).orElseGet(new m());
    }

    public static TreeEntry e(ForestMap forestMap, Object obj) {
        return (TreeEntry) Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.o
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return ((TreeEntry) obj2).getDeclaredParent();
            }
        }).orElse(null);
    }

    public static Object f(ForestMap forestMap, Object obj) {
        return Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.n
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return ((TreeEntry) obj2).getValue();
            }
        }).get();
    }

    public static TreeEntry g(ForestMap forestMap, Object obj, final Object obj2) {
        return (TreeEntry) Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.h
            @Override // java.util.function.Function
            public final Object apply(Object obj3) {
                return ((TreeEntry) obj3).getParent(obj2);
            }
        }).orElse(null);
    }

    public static TreeEntry h(ForestMap forestMap, Object obj) {
        return (TreeEntry) Opt.ofNullable(forestMap.get(obj)).map(new Function() { // from class: cn.hutool.core.map.e
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return ((TreeEntry) obj2).getRoot();
            }
        }).orElse(null);
    }

    public static Set i(ForestMap forestMap, Object obj) {
        TreeEntry<K, V> treeEntry = forestMap.get(obj);
        if (ObjectUtil.isNull(treeEntry)) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSetNewLinkedHashSet = CollUtil.newLinkedHashSet(treeEntry.getRoot());
        CollUtil.addAll((Collection) linkedHashSetNewLinkedHashSet, (Iterable) treeEntry.getRoot().getChildren().values());
        return linkedHashSetNewLinkedHashSet;
    }

    public static void j(ForestMap forestMap, Object obj, Object obj2) {
        forestMap.linkNodes(obj, obj2, null);
    }

    public static TreeEntry k(ForestMap forestMap, Object obj, TreeEntry treeEntry) {
        return forestMap.putNode(obj, treeEntry.getValue());
    }

    public static void m(final ForestMap forestMap, Map map) {
        if (CollUtil.isEmpty((Map<?, ?>) map)) {
            return;
        }
        map.forEach(new BiConsumer() { // from class: cn.hutool.core.map.g
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                q.q(forestMap, obj, (TreeEntry) obj2);
            }
        });
    }

    public static void n(final ForestMap forestMap, Collection collection, final Function function, final Function function2, final boolean z2) {
        if (CollUtil.isEmpty((Collection<?>) collection)) {
            return;
        }
        collection.forEach(new Consumer() { // from class: cn.hutool.core.map.f
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                q.r(forestMap, function, function2, z2, obj);
            }
        });
    }

    public static void o(ForestMap forestMap, Object obj, Object obj2, Object obj3, Object obj4) {
        forestMap.putNode(obj, obj2);
        forestMap.putNode(obj3, obj4);
        forestMap.linkNodes(obj, obj3);
    }

    public static /* synthetic */ void q(ForestMap forestMap, Object obj, TreeEntry treeEntry) {
        if (!treeEntry.hasParent()) {
            forestMap.putNode(treeEntry.getKey(), treeEntry.getValue());
        } else {
            TreeEntry<K, V> declaredParent = treeEntry.getDeclaredParent();
            forestMap.putLinkedNodes(declaredParent.getKey(), declaredParent.getValue(), treeEntry.getKey(), treeEntry.getValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void r(ForestMap forestMap, Function function, Function function2, boolean z2, Object obj) {
        Object objApply = function.apply(obj);
        Object objApply2 = function2.apply(obj);
        boolean zIsNotNull = ObjectUtil.isNotNull(objApply);
        boolean zIsNotNull2 = ObjectUtil.isNotNull(objApply2);
        if (!z2 || (zIsNotNull && zIsNotNull2)) {
            forestMap.linkNodes(objApply2, objApply);
            ((TreeEntry) forestMap.get(objApply)).setValue(obj);
        } else if (zIsNotNull || zIsNotNull2) {
            if (zIsNotNull) {
                forestMap.putNode(objApply, obj);
            } else {
                forestMap.putNode(objApply2, null);
            }
        }
    }
}
