package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable;
import com.google.android.gms.auth.api.signin.internal.HashAccumulator;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(creator = "GoogleSignInOptionsCreator")
/* loaded from: classes3.dex */
public class GoogleSignInOptions extends AbstractSafeParcelable implements Api.ApiOptions.Optional, ReflectedParcelable {
    public static final Parcelable.Creator<GoogleSignInOptions> CREATOR;
    public static final GoogleSignInOptions DEFAULT_GAMES_SIGN_IN;
    public static final GoogleSignInOptions DEFAULT_SIGN_IN;
    private static Comparator<Scope> zaag;

    @VisibleForTesting
    public static final Scope zar = new Scope(Scopes.PROFILE);

    @VisibleForTesting
    public static final Scope zas = new Scope("email");

    @VisibleForTesting
    public static final Scope zat = new Scope("openid");

    @VisibleForTesting
    public static final Scope zau;

    @VisibleForTesting
    public static final Scope zav;

    @SafeParcelable.VersionField(id = 1)
    private final int versionCode;

    @SafeParcelable.Field(getter = "isForceCodeForRefreshToken", id = 6)
    private final boolean zaaa;

    @SafeParcelable.Field(getter = "getServerClientId", id = 7)
    private String zaab;

    @SafeParcelable.Field(getter = "getHostedDomain", id = 8)
    private String zaac;

    @SafeParcelable.Field(getter = "getExtensions", id = 9)
    private ArrayList<GoogleSignInOptionsExtensionParcelable> zaad;

    @SafeParcelable.Field(getter = "getLogSessionId", id = 10)
    private String zaae;
    private Map<Integer, GoogleSignInOptionsExtensionParcelable> zaaf;

    @SafeParcelable.Field(getter = "getScopes", id = 2)
    private final ArrayList<Scope> zaw;

    @SafeParcelable.Field(getter = "getAccount", id = 3)
    private Account zax;

    @SafeParcelable.Field(getter = "isIdTokenRequested", id = 4)
    private boolean zay;

    @SafeParcelable.Field(getter = "isServerAuthCodeRequested", id = 5)
    private final boolean zaz;

    public static final class Builder {
        private Set<Scope> mScopes;
        private boolean zaaa;
        private String zaab;
        private String zaac;
        private String zaae;
        private Map<Integer, GoogleSignInOptionsExtensionParcelable> zaah;
        private Account zax;
        private boolean zay;
        private boolean zaz;

        public Builder() {
            this.mScopes = new HashSet();
            this.zaah = new HashMap();
        }

        private final String zac(String str) {
            Preconditions.checkNotEmpty(str);
            String str2 = this.zaab;
            Preconditions.checkArgument(str2 == null || str2.equals(str), "two different server client ids provided");
            return str;
        }

        public final Builder addExtension(GoogleSignInOptionsExtension googleSignInOptionsExtension) {
            if (this.zaah.containsKey(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()))) {
                throw new IllegalStateException("Only one extension per type may be added");
            }
            if (googleSignInOptionsExtension.getImpliedScopes() != null) {
                this.mScopes.addAll(googleSignInOptionsExtension.getImpliedScopes());
            }
            this.zaah.put(Integer.valueOf(googleSignInOptionsExtension.getExtensionType()), new GoogleSignInOptionsExtensionParcelable(googleSignInOptionsExtension));
            return this;
        }

        public final GoogleSignInOptions build() {
            if (this.mScopes.contains(GoogleSignInOptions.zav)) {
                Set<Scope> set = this.mScopes;
                Scope scope = GoogleSignInOptions.zau;
                if (set.contains(scope)) {
                    this.mScopes.remove(scope);
                }
            }
            if (this.zay && (this.zax == null || !this.mScopes.isEmpty())) {
                requestId();
            }
            return new GoogleSignInOptions(3, new ArrayList(this.mScopes), this.zax, this.zay, this.zaz, this.zaaa, this.zaab, this.zaac, this.zaah, this.zaae, null);
        }

        public final Builder requestEmail() {
            this.mScopes.add(GoogleSignInOptions.zas);
            return this;
        }

        public final Builder requestId() {
            this.mScopes.add(GoogleSignInOptions.zat);
            return this;
        }

        public final Builder requestIdToken(String str) {
            this.zay = true;
            this.zaab = zac(str);
            return this;
        }

        public final Builder requestProfile() {
            this.mScopes.add(GoogleSignInOptions.zar);
            return this;
        }

        public final Builder requestScopes(Scope scope, Scope... scopeArr) {
            this.mScopes.add(scope);
            this.mScopes.addAll(Arrays.asList(scopeArr));
            return this;
        }

        public final Builder requestServerAuthCode(String str) {
            return requestServerAuthCode(str, false);
        }

        public final Builder setAccountName(String str) {
            this.zax = new Account(Preconditions.checkNotEmpty(str), AccountType.GOOGLE);
            return this;
        }

        public final Builder setHostedDomain(String str) {
            this.zaac = Preconditions.checkNotEmpty(str);
            return this;
        }

        @KeepForSdk
        public final Builder setLogSessionId(String str) {
            this.zaae = str;
            return this;
        }

        public final Builder requestServerAuthCode(String str, boolean z2) {
            this.zaz = true;
            this.zaab = zac(str);
            this.zaaa = z2;
            return this;
        }

        public Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
            this.mScopes = new HashSet();
            this.zaah = new HashMap();
            Preconditions.checkNotNull(googleSignInOptions);
            this.mScopes = new HashSet(googleSignInOptions.zaw);
            this.zaz = googleSignInOptions.zaz;
            this.zaaa = googleSignInOptions.zaaa;
            this.zay = googleSignInOptions.zay;
            this.zaab = googleSignInOptions.zaab;
            this.zax = googleSignInOptions.zax;
            this.zaac = googleSignInOptions.zaac;
            this.zaah = GoogleSignInOptions.zaa(googleSignInOptions.zaad);
            this.zaae = googleSignInOptions.zaae;
        }
    }

    static {
        Scope scope = new Scope(Scopes.GAMES_LITE);
        zau = scope;
        zav = new Scope(Scopes.GAMES);
        DEFAULT_SIGN_IN = new Builder().requestId().requestProfile().build();
        DEFAULT_GAMES_SIGN_IN = new Builder().requestScopes(scope, new Scope[0]).build();
        CREATOR = new zad();
        zaag = new zac();
    }

    @SafeParcelable.Constructor
    public GoogleSignInOptions(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) ArrayList<Scope> arrayList, @SafeParcelable.Param(id = 3) Account account, @SafeParcelable.Param(id = 4) boolean z2, @SafeParcelable.Param(id = 5) boolean z3, @SafeParcelable.Param(id = 6) boolean z4, @SafeParcelable.Param(id = 7) String str, @SafeParcelable.Param(id = 8) String str2, @SafeParcelable.Param(id = 9) ArrayList<GoogleSignInOptionsExtensionParcelable> arrayList2, @SafeParcelable.Param(id = 10) String str3) {
        this(i2, arrayList, account, z2, z3, z4, str, str2, zaa(arrayList2), str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map<Integer, GoogleSignInOptionsExtensionParcelable> zaa(@Nullable List<GoogleSignInOptionsExtensionParcelable> list) {
        HashMap map = new HashMap();
        if (list == null) {
            return map;
        }
        for (GoogleSignInOptionsExtensionParcelable googleSignInOptionsExtensionParcelable : list) {
            map.put(Integer.valueOf(googleSignInOptionsExtensionParcelable.getType()), googleSignInOptionsExtensionParcelable);
        }
        return map;
    }

    @Nullable
    public static GoogleSignInOptions zab(@Nullable String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str);
        HashSet hashSet = new HashSet();
        JSONArray jSONArray = jSONObject.getJSONArray("scopes");
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            hashSet.add(new Scope(jSONArray.getString(i2)));
        }
        String strOptString = jSONObject.optString("accountName", null);
        return new GoogleSignInOptions(3, (ArrayList<Scope>) new ArrayList(hashSet), !TextUtils.isEmpty(strOptString) ? new Account(strOptString, AccountType.GOOGLE) : null, jSONObject.getBoolean("idTokenRequested"), jSONObject.getBoolean("serverAuthRequested"), jSONObject.getBoolean("forceCodeForRefreshToken"), jSONObject.optString("serverClientId", null), jSONObject.optString("hostedDomain", null), new HashMap(), (String) null);
    }

    private final JSONObject zad() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            Collections.sort(this.zaw, zaag);
            ArrayList<Scope> arrayList = this.zaw;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Scope scope = arrayList.get(i2);
                i2++;
                jSONArray.put(scope.getScopeUri());
            }
            jSONObject.put("scopes", jSONArray);
            Account account = this.zax;
            if (account != null) {
                jSONObject.put("accountName", account.name);
            }
            jSONObject.put("idTokenRequested", this.zay);
            jSONObject.put("forceCodeForRefreshToken", this.zaaa);
            jSONObject.put("serverAuthRequested", this.zaz);
            if (!TextUtils.isEmpty(this.zaab)) {
                jSONObject.put("serverClientId", this.zaab);
            }
            if (!TextUtils.isEmpty(this.zaac)) {
                jSONObject.put("hostedDomain", this.zaac);
            }
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0052 A[Catch: ClassCastException -> 0x008f, TryCatch #0 {ClassCastException -> 0x008f, blocks: (B:5:0x0004, B:7:0x000e, B:10:0x0018, B:12:0x0028, B:15:0x0035, B:17:0x0039, B:22:0x004a, B:24:0x0052, B:29:0x0069, B:31:0x0071, B:33:0x0079, B:35:0x0081, B:27:0x005d, B:20:0x0040), top: B:41:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005d A[Catch: ClassCastException -> 0x008f, TryCatch #0 {ClassCastException -> 0x008f, blocks: (B:5:0x0004, B:7:0x000e, B:10:0x0018, B:12:0x0028, B:15:0x0035, B:17:0x0039, B:22:0x004a, B:24:0x0052, B:29:0x0069, B:31:0x0071, B:33:0x0079, B:35:0x0081, B:27:0x005d, B:20:0x0040), top: B:41:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008d A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L4
            return r0
        L4:
            com.google.android.gms.auth.api.signin.GoogleSignInOptions r4 = (com.google.android.gms.auth.api.signin.GoogleSignInOptions) r4     // Catch: java.lang.ClassCastException -> L8f
            java.util.ArrayList<com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable> r1 = r3.zaad     // Catch: java.lang.ClassCastException -> L8f
            int r1 = r1.size()     // Catch: java.lang.ClassCastException -> L8f
            if (r1 > 0) goto L8f
            java.util.ArrayList<com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable> r1 = r4.zaad     // Catch: java.lang.ClassCastException -> L8f
            int r1 = r1.size()     // Catch: java.lang.ClassCastException -> L8f
            if (r1 <= 0) goto L18
            goto L8f
        L18:
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.zaw     // Catch: java.lang.ClassCastException -> L8f
            int r1 = r1.size()     // Catch: java.lang.ClassCastException -> L8f
            java.util.ArrayList r2 = r4.getScopes()     // Catch: java.lang.ClassCastException -> L8f
            int r2 = r2.size()     // Catch: java.lang.ClassCastException -> L8f
            if (r1 != r2) goto L8f
            java.util.ArrayList<com.google.android.gms.common.api.Scope> r1 = r3.zaw     // Catch: java.lang.ClassCastException -> L8f
            java.util.ArrayList r2 = r4.getScopes()     // Catch: java.lang.ClassCastException -> L8f
            boolean r1 = r1.containsAll(r2)     // Catch: java.lang.ClassCastException -> L8f
            if (r1 != 0) goto L35
            goto L8f
        L35:
            android.accounts.Account r1 = r3.zax     // Catch: java.lang.ClassCastException -> L8f
            if (r1 != 0) goto L40
            android.accounts.Account r1 = r4.getAccount()     // Catch: java.lang.ClassCastException -> L8f
            if (r1 != 0) goto L8f
            goto L4a
        L40:
            android.accounts.Account r2 = r4.getAccount()     // Catch: java.lang.ClassCastException -> L8f
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L8f
            if (r1 == 0) goto L8f
        L4a:
            java.lang.String r1 = r3.zaab     // Catch: java.lang.ClassCastException -> L8f
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.ClassCastException -> L8f
            if (r1 == 0) goto L5d
            java.lang.String r1 = r4.getServerClientId()     // Catch: java.lang.ClassCastException -> L8f
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.ClassCastException -> L8f
            if (r1 == 0) goto L8f
            goto L69
        L5d:
            java.lang.String r1 = r3.zaab     // Catch: java.lang.ClassCastException -> L8f
            java.lang.String r2 = r4.getServerClientId()     // Catch: java.lang.ClassCastException -> L8f
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L8f
            if (r1 == 0) goto L8f
        L69:
            boolean r1 = r3.zaaa     // Catch: java.lang.ClassCastException -> L8f
            boolean r2 = r4.isForceCodeForRefreshToken()     // Catch: java.lang.ClassCastException -> L8f
            if (r1 != r2) goto L8f
            boolean r1 = r3.zay     // Catch: java.lang.ClassCastException -> L8f
            boolean r2 = r4.isIdTokenRequested()     // Catch: java.lang.ClassCastException -> L8f
            if (r1 != r2) goto L8f
            boolean r1 = r3.zaz     // Catch: java.lang.ClassCastException -> L8f
            boolean r2 = r4.isServerAuthCodeRequested()     // Catch: java.lang.ClassCastException -> L8f
            if (r1 != r2) goto L8f
            java.lang.String r1 = r3.zaae     // Catch: java.lang.ClassCastException -> L8f
            java.lang.String r4 = r4.getLogSessionId()     // Catch: java.lang.ClassCastException -> L8f
            boolean r4 = android.text.TextUtils.equals(r1, r4)     // Catch: java.lang.ClassCastException -> L8f
            if (r4 == 0) goto L8f
            r4 = 1
            return r4
        L8f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.GoogleSignInOptions.equals(java.lang.Object):boolean");
    }

    @KeepForSdk
    public Account getAccount() {
        return this.zax;
    }

    @KeepForSdk
    public ArrayList<GoogleSignInOptionsExtensionParcelable> getExtensions() {
        return this.zaad;
    }

    @Nullable
    @KeepForSdk
    public String getLogSessionId() {
        return this.zaae;
    }

    public Scope[] getScopeArray() {
        ArrayList<Scope> arrayList = this.zaw;
        return (Scope[]) arrayList.toArray(new Scope[arrayList.size()]);
    }

    @KeepForSdk
    public ArrayList<Scope> getScopes() {
        return new ArrayList<>(this.zaw);
    }

    @KeepForSdk
    public String getServerClientId() {
        return this.zaab;
    }

    public int hashCode() {
        ArrayList arrayList = new ArrayList();
        ArrayList<Scope> arrayList2 = this.zaw;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Scope scope = arrayList2.get(i2);
            i2++;
            arrayList.add(scope.getScopeUri());
        }
        Collections.sort(arrayList);
        return new HashAccumulator().addObject(arrayList).addObject(this.zax).addObject(this.zaab).zaa(this.zaaa).zaa(this.zay).zaa(this.zaz).addObject(this.zaae).hash();
    }

    @KeepForSdk
    public boolean isForceCodeForRefreshToken() {
        return this.zaaa;
    }

    @KeepForSdk
    public boolean isIdTokenRequested() {
        return this.zay;
    }

    @KeepForSdk
    public boolean isServerAuthCodeRequested() {
        return this.zaz;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeTypedList(parcel, 2, getScopes(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, getAccount(), i2, false);
        SafeParcelWriter.writeBoolean(parcel, 4, isIdTokenRequested());
        SafeParcelWriter.writeBoolean(parcel, 5, isServerAuthCodeRequested());
        SafeParcelWriter.writeBoolean(parcel, 6, isForceCodeForRefreshToken());
        SafeParcelWriter.writeString(parcel, 7, getServerClientId(), false);
        SafeParcelWriter.writeString(parcel, 8, this.zaac, false);
        SafeParcelWriter.writeTypedList(parcel, 9, getExtensions(), false);
        SafeParcelWriter.writeString(parcel, 10, getLogSessionId(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zae() {
        return zad().toString();
    }

    private GoogleSignInOptions(int i2, ArrayList<Scope> arrayList, Account account, boolean z2, boolean z3, boolean z4, String str, String str2, Map<Integer, GoogleSignInOptionsExtensionParcelable> map, String str3) {
        this.versionCode = i2;
        this.zaw = arrayList;
        this.zax = account;
        this.zay = z2;
        this.zaz = z3;
        this.zaaa = z4;
        this.zaab = str;
        this.zaac = str2;
        this.zaad = new ArrayList<>(map.values());
        this.zaaf = map;
        this.zaae = str3;
    }

    public /* synthetic */ GoogleSignInOptions(int i2, ArrayList arrayList, Account account, boolean z2, boolean z3, boolean z4, String str, String str2, Map map, String str3, zac zacVar) {
        this(3, (ArrayList<Scope>) arrayList, account, z2, z3, z4, str, str2, (Map<Integer, GoogleSignInOptionsExtensionParcelable>) map, str3);
    }
}
