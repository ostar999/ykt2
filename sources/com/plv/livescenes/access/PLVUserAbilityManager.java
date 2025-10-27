package com.plv.livescenes.access;

import androidx.annotation.NonNull;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public class PLVUserAbilityManager {
    private static volatile PLVUserAbilityManager myInstance;
    private final Set<PLVUserRole> roles = new HashSet();
    private final Set<PLVUserAbility> abilities = new HashSet();
    private final List<WeakReference<OnUserRoleChangedListener>> onUserRoleChangeListeners = new ArrayList();
    private final List<WeakReference<OnUserAbilityChangedListener>> onUserAbilityChangeListeners = new ArrayList();

    public interface OnUserAbilityChangedListener {
        void onUserAbilitiesChanged(@NonNull List<PLVUserAbility> list, @NonNull List<PLVUserAbility> list2);
    }

    public interface OnUserRoleChangedListener {
        void onUserRoleAdded(PLVUserRole pLVUserRole);

        void onUserRoleRemoved(PLVUserRole pLVUserRole);
    }

    private PLVUserAbilityManager() {
    }

    private void changeAbilityToSet() {
        HashSet hashSet = new HashSet(this.abilities);
        this.abilities.clear();
        PLVSugarUtil.foreach(this.roles, new PLVSugarUtil.Consumer<PLVUserRole>() { // from class: com.plv.livescenes.access.PLVUserAbilityManager.1
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(PLVUserRole pLVUserRole) {
                PLVUserAbilityManager.this.abilities.addAll(pLVUserRole.getAbilities());
            }
        });
        notifyUserAbilitiesChanged(PLVSugarUtil.collectionMinus(this.abilities, hashSet), PLVSugarUtil.collectionMinus(hashSet, this.abilities));
    }

    private static <T> void foreachNotNullWeakRef(@NonNull Iterable<WeakReference<T>> iterable, @NonNull final PLVSugarUtil.Consumer<T> consumer) {
        PLVSugarUtil.foreach(iterable, new PLVSugarUtil.Consumer<WeakReference<T>>() { // from class: com.plv.livescenes.access.PLVUserAbilityManager.5
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

    public static PLVUserAbilityManager myAbility() {
        if (myInstance == null) {
            synchronized (PLVUserAbilityManager.class) {
                if (myInstance == null) {
                    myInstance = new PLVUserAbilityManager();
                }
            }
        }
        return myInstance;
    }

    private void notifyUserAbilitiesChanged(@NonNull final List<PLVUserAbility> list, @NonNull final List<PLVUserAbility> list2) {
        foreachNotNullWeakRef(this.onUserAbilityChangeListeners, new PLVSugarUtil.Consumer<OnUserAbilityChangedListener>() { // from class: com.plv.livescenes.access.PLVUserAbilityManager.4
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(OnUserAbilityChangedListener onUserAbilityChangedListener) {
                onUserAbilityChangedListener.onUserAbilitiesChanged(list, list2);
            }
        });
    }

    private void notifyUserRoleAdd(final PLVUserRole pLVUserRole) {
        foreachNotNullWeakRef(this.onUserRoleChangeListeners, new PLVSugarUtil.Consumer<OnUserRoleChangedListener>() { // from class: com.plv.livescenes.access.PLVUserAbilityManager.2
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(OnUserRoleChangedListener onUserRoleChangedListener) {
                onUserRoleChangedListener.onUserRoleAdded(pLVUserRole);
            }
        });
    }

    private void notifyUserRoleRemoved(final PLVUserRole pLVUserRole) {
        foreachNotNullWeakRef(this.onUserRoleChangeListeners, new PLVSugarUtil.Consumer<OnUserRoleChangedListener>() { // from class: com.plv.livescenes.access.PLVUserAbilityManager.3
            @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Consumer
            public void accept(OnUserRoleChangedListener onUserRoleChangedListener) {
                onUserRoleChangedListener.onUserRoleRemoved(pLVUserRole);
            }
        });
    }

    public void addRole(PLVUserRole pLVUserRole) {
        if (this.roles.add(pLVUserRole)) {
            notifyUserRoleAdd(pLVUserRole);
        }
        changeAbilityToSet();
    }

    public void addUserAbilityChangeListener(WeakReference<OnUserAbilityChangedListener> weakReference) {
        this.onUserAbilityChangeListeners.add(weakReference);
    }

    public void addUserRoleChangeListener(WeakReference<OnUserRoleChangedListener> weakReference) {
        this.onUserRoleChangeListeners.add(weakReference);
    }

    public void clearRole() {
        Iterator<PLVUserRole> it = this.roles.iterator();
        while (it.hasNext()) {
            PLVUserRole next = it.next();
            it.remove();
            notifyUserRoleRemoved(next);
        }
        changeAbilityToSet();
    }

    public boolean hasAbility(PLVUserAbility pLVUserAbility) {
        return this.abilities.contains(pLVUserAbility);
    }

    public boolean hasRole(PLVUserRole pLVUserRole) {
        return this.roles.contains(pLVUserRole);
    }

    public boolean notHasAbility(PLVUserAbility pLVUserAbility) {
        return !hasAbility(pLVUserAbility);
    }

    public void removeRole(PLVUserRole pLVUserRole) {
        if (this.roles.remove(pLVUserRole)) {
            notifyUserRoleRemoved(pLVUserRole);
        }
        changeAbilityToSet();
    }
}
