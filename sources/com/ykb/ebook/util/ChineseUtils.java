package com.ykb.ebook.util;

import com.github.liuyueyi.quick.transfer.Trie;
import com.github.liuyueyi.quick.transfer.constants.TransType;
import com.github.liuyueyi.quick.transfer.dictionary.BasicDictionary;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryContainer;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.ykb.ebook.extensions.TrieExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bJ%\u0010\u000b\u001a\u00020\u0006*\u00020\f2\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000e\"\u00020\bH\u0002¢\u0006\u0002\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/ykb/ebook/util/ChineseUtils;", "", "()V", "fixed", "", "fixT2sDict", "", "s2t", "", "content", "t2s", PLVRemoveMicSiteEvent.EVENT_NAME, "Lcom/github/liuyueyi/quick/transfer/dictionary/BasicDictionary;", "keys", "", "(Lcom/github/liuyueyi/quick/transfer/dictionary/BasicDictionary;[Ljava/lang/String;)V", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ChineseUtils {

    @NotNull
    public static final ChineseUtils INSTANCE = new ChineseUtils();
    private static boolean fixed;

    private ChineseUtils() {
    }

    private final void fixT2sDict() {
        BasicDictionary fixT2sDict$lambda$0 = DictionaryContainer.getInstance().getDictionary(TransType.TRADITIONAL_TO_SIMPLE);
        ChineseUtils chineseUtils = INSTANCE;
        Intrinsics.checkNotNullExpressionValue(fixT2sDict$lambda$0, "fixT2sDict$lambda$0");
        chineseUtils.remove(fixT2sDict$lambda$0, "劈", "脊", "槃");
        chineseUtils.remove(fixT2sDict$lambda$0, "支援", "沈默", "類比", "模擬", "划槳", "列根", "先進");
        chineseUtils.remove(fixT2sDict$lambda$0, "路易斯", "非同步", "出租车", "周杰倫");
    }

    private final void remove(BasicDictionary basicDictionary, String... strArr) {
        for (String str : strArr) {
            if (str.length() == 1) {
                basicDictionary.getChars().remove(Character.valueOf(str.charAt(0)));
            } else {
                Trie<String> dict = basicDictionary.getDict();
                Intrinsics.checkNotNullExpressionValue(dict, "dict");
                TrieExtensionsKt.remove(dict, str);
            }
        }
    }

    @NotNull
    public final String s2t(@NotNull String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        String strS2t = com.github.liuyueyi.quick.transfer.ChineseUtils.s2t(content);
        Intrinsics.checkNotNullExpressionValue(strS2t, "s2t(content)");
        return strS2t;
    }

    @NotNull
    public final String t2s(@NotNull String content) {
        Intrinsics.checkNotNullParameter(content, "content");
        if (!fixed) {
            fixed = true;
            fixT2sDict();
        }
        String strT2s = com.github.liuyueyi.quick.transfer.ChineseUtils.t2s(content);
        Intrinsics.checkNotNullExpressionValue(strT2s, "t2s(content)");
        return strT2s;
    }
}
