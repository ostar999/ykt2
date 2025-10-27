package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import androidx.collection.ArraySet;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@VisibleForTesting
@KeepForSdk
/* loaded from: classes3.dex */
public final class ClientSettings {
    public static final String KEY_CLIENT_SESSION_ID = "com.google.android.gms.common.internal.ClientSettings.sessionId";
    private final Account account;
    private final Set<Scope> zaof;
    private final Set<Scope> zaog;
    private final Map<Api<?>, OptionalApiSettings> zaoh;
    private final int zaoi;
    private final View zaoj;
    private final String zaok;
    private final String zaol;
    private final SignInOptions zaom;
    private final boolean zaon;
    private Integer zaoo;

    @KeepForSdk
    public static final class Builder {
        private Account account;
        private Map<Api<?>, OptionalApiSettings> zaoh;
        private View zaoj;
        private String zaok;
        private String zaol;
        private ArraySet<Scope> zaop;
        private boolean zaoq;
        private int zaoi = 0;
        private SignInOptions zaom = SignInOptions.DEFAULT;

        public final Builder addAllRequiredScopes(Collection<Scope> collection) {
            if (this.zaop == null) {
                this.zaop = new ArraySet<>();
            }
            this.zaop.addAll(collection);
            return this;
        }

        public final Builder addRequiredScope(Scope scope) {
            if (this.zaop == null) {
                this.zaop = new ArraySet<>();
            }
            this.zaop.add(scope);
            return this;
        }

        @KeepForSdk
        public final ClientSettings build() {
            return new ClientSettings(this.account, this.zaop, this.zaoh, this.zaoi, this.zaoj, this.zaok, this.zaol, this.zaom, this.zaoq);
        }

        public final Builder enableSignInClientDisconnectFix() {
            this.zaoq = true;
            return this;
        }

        public final Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public final Builder setGravityForPopups(int i2) {
            this.zaoi = i2;
            return this;
        }

        public final Builder setOptionalApiSettingsMap(Map<Api<?>, OptionalApiSettings> map) {
            this.zaoh = map;
            return this;
        }

        public final Builder setRealClientClassName(String str) {
            this.zaol = str;
            return this;
        }

        @KeepForSdk
        public final Builder setRealClientPackageName(String str) {
            this.zaok = str;
            return this;
        }

        public final Builder setSignInOptions(SignInOptions signInOptions) {
            this.zaom = signInOptions;
            return this;
        }

        public final Builder setViewForPopups(View view) {
            this.zaoj = view;
            return this;
        }
    }

    public static final class OptionalApiSettings {
        public final Set<Scope> mScopes;

        public OptionalApiSettings(Set<Scope> set) {
            Preconditions.checkNotNull(set);
            this.mScopes = Collections.unmodifiableSet(set);
        }
    }

    @KeepForSdk
    public ClientSettings(Account account, Set<Scope> set, Map<Api<?>, OptionalApiSettings> map, int i2, View view, String str, String str2, SignInOptions signInOptions) {
        this(account, set, map, i2, view, str, str2, signInOptions, false);
    }

    @KeepForSdk
    public static ClientSettings createDefault(Context context) {
        return new GoogleApiClient.Builder(context).buildClientSettings();
    }

    @KeepForSdk
    @Nullable
    public final Account getAccount() {
        return this.account;
    }

    @KeepForSdk
    @Nullable
    @Deprecated
    public final String getAccountName() {
        Account account = this.account;
        if (account != null) {
            return account.name;
        }
        return null;
    }

    @KeepForSdk
    public final Account getAccountOrDefault() {
        Account account = this.account;
        return account != null ? account : new Account("<<default account>>", AccountType.GOOGLE);
    }

    @KeepForSdk
    public final Set<Scope> getAllRequestedScopes() {
        return this.zaog;
    }

    @KeepForSdk
    public final Set<Scope> getApplicableScopes(Api<?> api) {
        OptionalApiSettings optionalApiSettings = this.zaoh.get(api);
        if (optionalApiSettings == null || optionalApiSettings.mScopes.isEmpty()) {
            return this.zaof;
        }
        HashSet hashSet = new HashSet(this.zaof);
        hashSet.addAll(optionalApiSettings.mScopes);
        return hashSet;
    }

    @Nullable
    public final Integer getClientSessionId() {
        return this.zaoo;
    }

    @KeepForSdk
    public final int getGravityForPopups() {
        return this.zaoi;
    }

    public final Map<Api<?>, OptionalApiSettings> getOptionalApiSettings() {
        return this.zaoh;
    }

    @Nullable
    public final String getRealClientClassName() {
        return this.zaol;
    }

    @KeepForSdk
    @Nullable
    public final String getRealClientPackageName() {
        return this.zaok;
    }

    @KeepForSdk
    public final Set<Scope> getRequiredScopes() {
        return this.zaof;
    }

    @Nullable
    public final SignInOptions getSignInOptions() {
        return this.zaom;
    }

    @KeepForSdk
    @Nullable
    public final View getViewForPopups() {
        return this.zaoj;
    }

    public final boolean isSignInClientDisconnectFixEnabled() {
        return this.zaon;
    }

    public final void setClientSessionId(Integer num) {
        this.zaoo = num;
    }

    public ClientSettings(Account account, Set<Scope> set, Map<Api<?>, OptionalApiSettings> map, int i2, View view, String str, String str2, SignInOptions signInOptions, boolean z2) {
        this.account = account;
        Set<Scope> setEmptySet = set == null ? Collections.emptySet() : Collections.unmodifiableSet(set);
        this.zaof = setEmptySet;
        map = map == null ? Collections.emptyMap() : map;
        this.zaoh = map;
        this.zaoj = view;
        this.zaoi = i2;
        this.zaok = str;
        this.zaol = str2;
        this.zaom = signInOptions;
        this.zaon = z2;
        HashSet hashSet = new HashSet(setEmptySet);
        Iterator<OptionalApiSettings> it = map.values().iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().mScopes);
        }
        this.zaog = Collections.unmodifiableSet(hashSet);
    }
}
