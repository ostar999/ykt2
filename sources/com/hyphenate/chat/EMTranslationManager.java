package com.hyphenate.chat;

import android.os.AsyncTask;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMTranslator;
import com.hyphenate.chat.adapter.EMATranslateManager;
import com.hyphenate.chat.adapter.EMATranslateResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Deprecated
/* loaded from: classes4.dex */
public class EMTranslationManager {
    static final int MaxCacheSize = 10000;
    public static final int MaxTranslationTextSize = 5000;
    private static final String TAG = "EMTranslationManager";
    private static EMTranslationManager instance;
    EMATranslateManager emaObject;
    private EMTranslator mTranslator;
    private AtomicBoolean mInitializing = new AtomicBoolean(false);
    private AtomicBoolean mInitialized = new AtomicBoolean(false);
    private List<EMLanguage> mLanguageList = new ArrayList();
    private CacheManager mCacheManager = new CacheManager(10000);

    public EMTranslationManager(EMATranslateManager eMATranslateManager) {
        this.emaObject = eMATranslateManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanCache() {
        this.mCacheManager.clear();
    }

    private void deleteAll() {
        removeAllTranslations();
        this.mCacheManager.clear();
    }

    private void deleteTranslationResults(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (this.mCacheManager.check(str)) {
                arrayList.add(str);
            }
        }
        removeTranslationsByMsgId(arrayList);
        this.mCacheManager.removeByMsgIds(arrayList);
    }

    private EMTranslationResult getTranslationResultByMsgId(String str) {
        EMATranslateResult translationResultByMsgId = this.emaObject.getTranslationResultByMsgId(str);
        return translationResultByMsgId == null ? new EMTranslationResult(str) : new EMTranslationResult(translationResultByMsgId);
    }

    private boolean isMessageResult(String str) {
        return this.mCacheManager.check(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$translate$0(EMTranslationResult eMTranslationResult, EMValueCallBack eMValueCallBack, String str, String str2) {
        if (str.isEmpty()) {
            eMValueCallBack.onError(904, str2);
            return;
        }
        eMTranslationResult.setShowTranslation(true);
        eMTranslationResult.setTranslatedText(str);
        eMTranslationResult.setTranslateCount(eMTranslationResult.translateCount() + 1);
        updateTranslationResult(eMTranslationResult);
        eMValueCallBack.onSuccess(eMTranslationResult);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadIds(int i2) {
        List<EMTranslationResult> listLoadTranslateResults = loadTranslateResults(i2);
        if (listLoadTranslateResults.size() > 0) {
            Iterator<EMTranslationResult> it = listLoadTranslateResults.iterator();
            while (it.hasNext()) {
                this.mCacheManager.add(it.next());
            }
        }
    }

    private List<EMTranslationResult> loadTranslateResults(int i2) {
        List<EMATranslateResult> listLoadTranslateResults = this.emaObject.loadTranslateResults(i2);
        ArrayList arrayList = new ArrayList();
        if (listLoadTranslateResults.size() > 0) {
            for (int i3 = 0; i3 < listLoadTranslateResults.size(); i3++) {
                arrayList.add(new EMTranslationResult(listLoadTranslateResults.get(i3)));
            }
        }
        return arrayList;
    }

    private boolean removeAllTranslations() {
        return this.emaObject.removeAllTranslations();
    }

    private boolean removeTranslationsByConversationId(String str) {
        return this.emaObject.removeTranslationsByConversationId(str);
    }

    private boolean removeTranslationsByMsgId(List<String> list) {
        return this.emaObject.removeTranslationsByMsgId(list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean updateTranslate(EMTranslationResult eMTranslationResult) {
        return this.emaObject.updateTranslation((EMATranslateResult) eMTranslationResult.emaObject);
    }

    public void clearTranslations() {
        if (this.mInitializing.get()) {
            deleteAll();
        }
    }

    public List<EMLanguage> getSupportedLanguages() {
        return !this.mInitialized.get() ? new ArrayList() : this.mLanguageList;
    }

    public EMTranslationResult getTranslationResult(String str) {
        if (this.mInitializing.get() && this.mCacheManager.check(str)) {
            return this.mCacheManager.get(str) != null ? this.mCacheManager.get(str) : getTranslationResultByMsgId(str);
        }
        return null;
    }

    public void init(final EMTranslateParams eMTranslateParams) {
        if (EMClient.getInstance().isLoggedInBefore() && this.mInitializing.compareAndSet(false, true)) {
            AsyncTask.execute(new Runnable() { // from class: com.hyphenate.chat.EMTranslationManager.1
                @Override // java.lang.Runnable
                public void run() {
                    EMTranslationManager.this.cleanCache();
                    EMTranslationManager.this.loadIds(eMTranslateParams.LoadCount);
                    EMTranslationManager.this.mInitialized.set(true);
                    EMTranslationManager.this.mTranslator = new EMTranslator(eMTranslateParams);
                    EMTranslationManager eMTranslationManager = EMTranslationManager.this;
                    eMTranslationManager.mLanguageList = eMTranslationManager.mTranslator.getSupportedLanguages();
                }
            });
        }
    }

    public boolean isInitialized() {
        return this.mInitialized.get();
    }

    public boolean isTranslationResultForMessage(String str) {
        if (this.mInitialized.get()) {
            return isMessageResult(str);
        }
        return false;
    }

    public void logout() {
        if (this.mInitialized.get()) {
            this.mInitializing.set(false);
            this.mInitialized.set(false);
            cleanCache();
            this.mLanguageList.clear();
        }
    }

    public void removeResultsByConversationId(String str) {
        if (this.mInitializing.get()) {
            removeTranslationsByConversationId(str);
            this.mCacheManager.removeByConversationId(str);
        }
    }

    public void removeTranslationResult(String str) {
        removeTranslationResults(Arrays.asList(str));
    }

    public void removeTranslationResults(List<String> list) {
        if (this.mInitializing.get()) {
            deleteTranslationResults(list);
        }
    }

    public void translate(String str, String str2, String str3, String str4, final EMValueCallBack<EMTranslationResult> eMValueCallBack) {
        final EMTranslationResult translationResult;
        if (!this.mInitializing.get()) {
            eMValueCallBack.onError(905, "EMTranslationManager is not initialized");
            return;
        }
        if (str3.length() > 5000) {
            eMValueCallBack.onError(903, "Text exceeds limit");
            return;
        }
        if (isTranslationResultForMessage(str)) {
            translationResult = getTranslationResult(str);
        } else {
            EMTranslationResult eMTranslationResult = new EMTranslationResult(str);
            eMTranslationResult.setConversationId(str2);
            translationResult = eMTranslationResult;
        }
        this.mTranslator.translate(str3, str4, new EMTranslator.TranslationCallback() { // from class: com.hyphenate.chat.i
            @Override // com.hyphenate.chat.EMTranslator.TranslationCallback
            public final void onResult(String str5, String str6) {
                this.f8594a.lambda$translate$0(translationResult, eMValueCallBack, str5, str6);
            }
        });
    }

    public void updateTranslationResult(EMTranslationResult eMTranslationResult) {
        String strMsgId = eMTranslationResult.msgId();
        if (this.mCacheManager.check(strMsgId)) {
            removeTranslationResult(strMsgId);
        }
        this.mCacheManager.add(eMTranslationResult);
        updateTranslate(eMTranslationResult);
    }
}
