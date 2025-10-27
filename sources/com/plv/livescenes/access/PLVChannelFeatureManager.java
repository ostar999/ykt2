package com.plv.livescenes.access;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class PLVChannelFeatureManager {

    @Nullable
    private final String channelId;
    private final Map<PLVChannelFeature, Object> features = new HashMap();
    private final List<WeakReference<OnChannelFeatureChangeListener>> onChannelFeatureChangeListeners = new ArrayList();
    private static final Map<String, PLVChannelFeatureManager> INSTANCES = new ConcurrentHashMap();
    private static final PLVChannelFeatureManager DEFAULT_INSTANCE = new PLVChannelFeatureManager(null);

    public interface OnChannelFeatureChangeListener {
        void onChannelFeatureChange(@Nullable String str, @NonNull PLVChannelFeature pLVChannelFeature);
    }

    private PLVChannelFeatureManager(@Nullable String str) {
        this.channelId = str;
    }

    private static <T> void foreachNotNullWeakRef(@NonNull Iterable<WeakReference<T>> iterable, @NonNull final PLVSugarUtil.Consumer<T> consumer) {
        PLVSugarUtil.foreach(iterable, new PLVSugarUtil.Consumer<WeakReference<T>>() { // from class: com.plv.livescenes.access.PLVChannelFeatureManager.2
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(WeakReference<T> weakReference) {
                T t2;
                if (weakReference == null || (t2 = weakReference.get()) == null) {
                    return;
                }
                consumer.accept(t2);
            }
        });
    }

    public static synchronized PLVChannelFeatureManager onChannel(@NonNull String str) {
        if (str == null) {
            return DEFAULT_INSTANCE;
        }
        Map<String, PLVChannelFeatureManager> map = INSTANCES;
        PLVChannelFeatureManager pLVChannelFeatureManager = map.get(str);
        if (pLVChannelFeatureManager == null) {
            pLVChannelFeatureManager = new PLVChannelFeatureManager(str);
            map.put(str, pLVChannelFeatureManager);
        }
        return pLVChannelFeatureManager;
    }

    public void addOnChannelFeatureChangeListener(OnChannelFeatureChangeListener onChannelFeatureChangeListener) {
        this.onChannelFeatureChangeListeners.add(new WeakReference<>(onChannelFeatureChangeListener));
    }

    public void destroy() {
        this.features.clear();
        this.onChannelFeatureChangeListeners.clear();
        if (this.channelId != null) {
            synchronized (PLVChannelFeatureManager.class) {
                INSTANCES.remove(this.channelId);
            }
        }
    }

    @Nullable
    public <T> T get(@NonNull PLVChannelFeature<T> pLVChannelFeature) {
        return this.features.containsKey(pLVChannelFeature) ? (T) this.features.get(pLVChannelFeature) : pLVChannelFeature.getDefaultValue();
    }

    public boolean isFeatureSupport(@NonNull PLVChannelFeature<Boolean> pLVChannelFeature) {
        Boolean bool = (Boolean) get(pLVChannelFeature);
        return bool != null && bool.booleanValue();
    }

    public <T> PLVChannelFeatureManager set(@NonNull final PLVChannelFeature<T> pLVChannelFeature, @Nullable T t2) {
        if (this.features.get(pLVChannelFeature) == null || !this.features.get(pLVChannelFeature).equals(t2)) {
            this.features.put(pLVChannelFeature, t2);
            foreachNotNullWeakRef(this.onChannelFeatureChangeListeners, new PLVSugarUtil.Consumer<OnChannelFeatureChangeListener>() { // from class: com.plv.livescenes.access.PLVChannelFeatureManager.1
                @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
                public void accept(OnChannelFeatureChangeListener onChannelFeatureChangeListener) {
                    onChannelFeatureChangeListener.onChannelFeatureChange(PLVChannelFeatureManager.this.channelId, pLVChannelFeature);
                }
            });
        }
        return this;
    }
}
