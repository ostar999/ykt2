package com.hyphenate.chat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated
/* loaded from: classes4.dex */
class CacheManager {
    private Set<Integer> mIdCache = new HashSet();
    private TranslationCache mTranslationCache;

    public CacheManager(int i2) {
        this.mTranslationCache = new TranslationCache(i2);
    }

    private int id2Value(String str) {
        return (int) ((Long.parseLong(str) >> 22) & (-1));
    }

    public void add(EMTranslationResult eMTranslationResult) {
        synchronized (CacheManager.class) {
            String strMsgId = eMTranslationResult.msgId();
            this.mTranslationCache.put(strMsgId, eMTranslationResult);
            this.mIdCache.add(Integer.valueOf(id2Value(strMsgId)));
        }
    }

    public boolean check(String str) {
        boolean zContains;
        synchronized (CacheManager.class) {
            zContains = this.mIdCache.contains(Integer.valueOf(id2Value(str)));
        }
        return zContains;
    }

    public void clear() {
        synchronized (CacheManager.class) {
            this.mTranslationCache.clear();
            this.mIdCache.clear();
        }
    }

    public EMTranslationResult get(String str) {
        EMTranslationResult eMTranslationResult;
        synchronized (CacheManager.class) {
            eMTranslationResult = this.mTranslationCache.get(str);
        }
        return eMTranslationResult;
    }

    public void removeByConversationId(String str) {
        this.mTranslationCache.removeByConversationId(str);
    }

    public void removeByMsgIds(List<String> list) {
        synchronized (CacheManager.class) {
            for (String str : list) {
                this.mTranslationCache.remove(str);
                this.mIdCache.remove(Integer.valueOf(id2Value(str)));
            }
        }
    }
}
