package com.hyphenate.easeui.manager;

import com.hyphenate.easeui.adapter.EaseAdapterDelegate;
import com.hyphenate.easeui.adapter.EaseBaseDelegateAdapter;
import com.hyphenate.easeui.delegate.EaseCustomAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseExpressionAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseFileAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseImageAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseLocationAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseTextAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseVideoAdapterDelegate;
import com.hyphenate.easeui.delegate.EaseVoiceAdapterDelegate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class EaseMessageTypeSetManager {
    private static EaseMessageTypeSetManager mInstance;
    private Class<? extends EaseAdapterDelegate<?, ?>> defaultDelegateCls;
    private boolean hasConsistItemType;
    private EaseAdapterDelegate<?, ?> defaultDelegate = new EaseTextAdapterDelegate();
    private Set<Class<? extends EaseAdapterDelegate<?, ?>>> delegates = new HashSet();
    private List<Class<? extends EaseAdapterDelegate<?, ?>>> delegateList = new ArrayList();

    private EaseMessageTypeSetManager() {
    }

    public static EaseMessageTypeSetManager getInstance() {
        if (mInstance == null) {
            synchronized (EaseMessageTypeSetManager.class) {
                if (mInstance == null) {
                    mInstance = new EaseMessageTypeSetManager();
                }
            }
        }
        return mInstance;
    }

    public EaseMessageTypeSetManager addMessageType(Class<? extends EaseAdapterDelegate<?, ?>> cls) {
        int size = this.delegates.size();
        this.delegates.add(cls);
        if (this.delegates.size() > size) {
            this.delegateList.add(cls);
        }
        return this;
    }

    public boolean hasConsistItemType() {
        return this.hasConsistItemType;
    }

    public void registerMessageType(EaseBaseDelegateAdapter easeBaseDelegateAdapter) throws IllegalAccessException, InstantiationException {
        if (easeBaseDelegateAdapter == null) {
            return;
        }
        if (this.delegateList.size() <= 0) {
            addMessageType(EaseExpressionAdapterDelegate.class).addMessageType(EaseFileAdapterDelegate.class).addMessageType(EaseImageAdapterDelegate.class).addMessageType(EaseLocationAdapterDelegate.class).addMessageType(EaseVideoAdapterDelegate.class).addMessageType(EaseVoiceAdapterDelegate.class).addMessageType(EaseCustomAdapterDelegate.class).setDefaultMessageType(EaseTextAdapterDelegate.class);
        }
        Iterator<Class<? extends EaseAdapterDelegate<?, ?>>> it = this.delegateList.iterator();
        while (it.hasNext()) {
            easeBaseDelegateAdapter.addDelegate(it.next().newInstance());
        }
        Class<? extends EaseAdapterDelegate<?, ?>> cls = this.defaultDelegateCls;
        if (cls == null) {
            this.defaultDelegate = new EaseTextAdapterDelegate();
        } else {
            this.defaultDelegate = cls.newInstance();
        }
        easeBaseDelegateAdapter.setFallbackDelegate(this.defaultDelegate);
    }

    public void release() {
        this.defaultDelegate = null;
    }

    public EaseMessageTypeSetManager setConsistItemType(boolean z2) {
        this.hasConsistItemType = z2;
        return this;
    }

    public EaseMessageTypeSetManager setDefaultMessageType(Class<? extends EaseAdapterDelegate<?, ?>> cls) {
        this.defaultDelegateCls = cls;
        return this;
    }
}
