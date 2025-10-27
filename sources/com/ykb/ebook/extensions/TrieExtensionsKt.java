package com.ykb.ebook.extensions;

import androidx.exifinterface.media.ExifInterface;
import com.github.liuyueyi.quick.transfer.Trie;
import com.github.liuyueyi.quick.transfer.TrieNode;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a(\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00030\u0001\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0003\u001a\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0003\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0006\u001a\u001e\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00062\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b"}, d2 = {"getChildren", "Ljava/util/HashMap;", "", "Lcom/github/liuyueyi/quick/transfer/TrieNode;", ExifInterface.GPS_DIRECTION_TRUE, "getRoot", "Lcom/github/liuyueyi/quick/transfer/Trie;", PLVRemoveMicSiteEvent.EVENT_NAME, "", "value", "", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTrieExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TrieExtensions.kt\ncom/ykb/ebook/extensions/TrieExtensionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,47:1\n1#2:48\n*E\n"})
/* loaded from: classes7.dex */
public final class TrieExtensionsKt {
    @NotNull
    public static final <T> HashMap<Character, TrieNode<T>> getChildren(@NotNull TrieNode<T> trieNode) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(trieNode, "<this>");
        Field declaredField = trieNode.getClass().getDeclaredField("children");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(trieNode);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.HashMap<kotlin.Char, com.github.liuyueyi.quick.transfer.TrieNode<T of com.ykb.ebook.extensions.TrieExtensionsKt.getChildren>>");
        return (HashMap) obj;
    }

    @NotNull
    public static final <T> TrieNode<T> getRoot(@NotNull Trie<T> trie) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(trie, "<this>");
        Field declaredField = trie.getClass().getDeclaredField("root");
        declaredField.setAccessible(true);
        Object obj = declaredField.get(trie);
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.github.liuyueyi.quick.transfer.TrieNode<T of com.ykb.ebook.extensions.TrieExtensionsKt.getRoot>");
        return (TrieNode) obj;
    }

    public static final <T> void remove(@NotNull Trie<T> trie, @NotNull String value) {
        Intrinsics.checkNotNullParameter(trie, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        try {
            Result.Companion companion = Result.INSTANCE;
            TrieNode root = getRoot(trie);
            ArrayList arrayList = new ArrayList();
            char[] charArray = value.toCharArray();
            Intrinsics.checkNotNullExpressionValue(charArray, "this as java.lang.String).toCharArray()");
            for (char c3 : charArray) {
                arrayList.add(root);
                root = (TrieNode) getChildren(root).get(Character.valueOf(c3));
                if (root == null) {
                    break;
                }
                Intrinsics.checkNotNullExpressionValue(root, "node.getChildren()[c] ?: break");
                if (root.isLeaf()) {
                    for (Pair pair : CollectionsKt___CollectionsKt.zip(ArraysKt___ArraysKt.reversed(charArray), CollectionsKt___CollectionsKt.reversed(arrayList))) {
                        getChildren((TrieNode) pair.component2()).remove(Character.valueOf(((Character) pair.component1()).charValue()));
                        if (!r4.isEmpty()) {
                            break;
                        }
                    }
                }
            }
            Result.m783constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m783constructorimpl(ResultKt.createFailure(th));
        }
    }
}
