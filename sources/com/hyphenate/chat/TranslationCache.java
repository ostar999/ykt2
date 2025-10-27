package com.hyphenate.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Deprecated
/* loaded from: classes4.dex */
class TranslationCache extends LRUCache<String, EMTranslationResult> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private HashMap<String, List<String>> mConversationMap;

    public TranslationCache(int i2) {
        super(i2);
        this.mConversationMap = new HashMap<>();
    }

    @Override // com.hyphenate.chat.LRUCache
    public void clear() {
        super.clear();
        this.mConversationMap.clear();
    }

    @Override // com.hyphenate.chat.LRUCache
    public void put(String str, EMTranslationResult eMTranslationResult) {
        super.put((TranslationCache) str, (String) eMTranslationResult);
        if (this.mConversationMap.get(eMTranslationResult.conversationId()) == null) {
            this.mConversationMap.put(eMTranslationResult.conversationId(), new ArrayList());
        }
        this.mConversationMap.get(eMTranslationResult.conversationId()).add(str);
    }

    @Override // com.hyphenate.chat.LRUCache
    public void remove(String str) {
        List<String> list = this.mConversationMap.get(get(str).conversationId());
        if (list != null) {
            list.remove(str);
        }
        super.remove((TranslationCache) str);
    }

    public void removeByConversationId(String str) {
        List<String> list = this.mConversationMap.get(str);
        if (list != null) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                super.remove((TranslationCache) it.next());
            }
        }
    }
}
