package androidx.datastore.preferences;

import android.content.Context;
import androidx.datastore.migrations.SharedPreferencesMigration;
import androidx.datastore.migrations.SharedPreferencesView;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import com.umeng.analytics.pro.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a:\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0010\b\u0002\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\"\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\u000f"}, d2 = {"MIGRATE_ALL_KEYS", "", "getMIGRATE_ALL_KEYS", "()Ljava/lang/Void;", "SharedPreferencesMigration", "Landroidx/datastore/migrations/SharedPreferencesMigration;", "Landroidx/datastore/preferences/core/Preferences;", d.R, "Landroid/content/Context;", "sharedPreferencesName", "", "keysToMigrate", "", "deleteEmptyPreferences", "", "datastore-preferences_release"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class SharedPreferencesMigrationKt {

    @Nullable
    private static final Void MIGRATE_ALL_KEYS = null;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "prefs", "Landroidx/datastore/preferences/core/Preferences;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.preferences.SharedPreferencesMigrationKt$SharedPreferencesMigration$1", f = "SharedPreferencesMigration.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.datastore.preferences.SharedPreferencesMigrationKt$SharedPreferencesMigration$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<Preferences, Continuation<? super Boolean>, Object> {
        final /* synthetic */ Set $keysToMigrate;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Set set, Continuation continuation) {
            super(2, continuation);
            this.$keysToMigrate = set;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$keysToMigrate, completion);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Preferences preferences, Continuation<? super Boolean> continuation) {
            return ((AnonymousClass1) create(preferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Set<Preferences.Key<?>> setKeySet = ((Preferences) this.L$0).asMap().keySet();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setKeySet, 10));
            Iterator<T> it = setKeySet.iterator();
            while (it.hasNext()) {
                arrayList.add(((Preferences.Key) it.next()).getName());
            }
            Set set = this.$keysToMigrate;
            boolean zBooleanValue = true;
            if (set != null) {
                boolean z2 = false;
                if (!set.isEmpty()) {
                    Iterator it2 = set.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        if (Boxing.boxBoolean(!arrayList.contains((String) it2.next())).booleanValue()) {
                            z2 = true;
                            break;
                        }
                    }
                }
                Boolean boolBoxBoolean = Boxing.boxBoolean(z2);
                if (boolBoxBoolean != null) {
                    zBooleanValue = boolBoxBoolean.booleanValue();
                }
            }
            return Boxing.boxBoolean(zBooleanValue);
        }
    }

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0001H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {"<anonymous>", "Landroidx/datastore/preferences/core/Preferences;", "sharedPrefs", "Landroidx/datastore/migrations/SharedPreferencesView;", "currentData", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.preferences.SharedPreferencesMigrationKt$SharedPreferencesMigration$2", f = "SharedPreferencesMigration.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.datastore.preferences.SharedPreferencesMigrationKt$SharedPreferencesMigration$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function3<SharedPreferencesView, Preferences, Continuation<? super Preferences>, Object> {
        private /* synthetic */ Object L$0;
        private /* synthetic */ Object L$1;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(3, continuation);
        }

        @NotNull
        public final Continuation<Unit> create(@NotNull SharedPreferencesView sharedPrefs, @NotNull Preferences currentData, @NotNull Continuation<? super Preferences> continuation) {
            Intrinsics.checkNotNullParameter(sharedPrefs, "sharedPrefs");
            Intrinsics.checkNotNullParameter(currentData, "currentData");
            Intrinsics.checkNotNullParameter(continuation, "continuation");
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.L$0 = sharedPrefs;
            anonymousClass2.L$1 = currentData;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(SharedPreferencesView sharedPreferencesView, Preferences preferences, Continuation<? super Preferences> continuation) {
            return ((AnonymousClass2) create(sharedPreferencesView, preferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SharedPreferencesView sharedPreferencesView = (SharedPreferencesView) this.L$0;
            Preferences preferences = (Preferences) this.L$1;
            Set<Preferences.Key<?>> setKeySet = preferences.asMap().keySet();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(setKeySet, 10));
            Iterator<T> it = setKeySet.iterator();
            while (it.hasNext()) {
                arrayList.add(((Preferences.Key) it.next()).getName());
            }
            Map<String, Object> all = sharedPreferencesView.getAll();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<String, Object> entry : all.entrySet()) {
                if (Boxing.boxBoolean(!arrayList.contains(entry.getKey())).booleanValue()) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                String str = (String) entry2.getKey();
                Object value = entry2.getValue();
                if (value instanceof Boolean) {
                    mutablePreferences.set(PreferencesKeys.booleanKey(str), value);
                } else if (value instanceof Float) {
                    mutablePreferences.set(PreferencesKeys.floatKey(str), value);
                } else if (value instanceof Integer) {
                    mutablePreferences.set(PreferencesKeys.intKey(str), value);
                } else if (value instanceof Long) {
                    mutablePreferences.set(PreferencesKeys.longKey(str), value);
                } else if (value instanceof String) {
                    mutablePreferences.set(PreferencesKeys.stringKey(str), value);
                } else if (value instanceof Set) {
                    Preferences.Key<Set<String>> keyStringSetKey = PreferencesKeys.stringSetKey(str);
                    if (value == null) {
                        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Set<kotlin.String>");
                    }
                    mutablePreferences.set(keyStringSetKey, (Set) value);
                } else {
                    continue;
                }
            }
            return mutablePreferences.toPreferences();
        }
    }

    @JvmOverloads
    @NotNull
    public static final SharedPreferencesMigration<Preferences> SharedPreferencesMigration(@NotNull Context context, @NotNull String str) {
        return SharedPreferencesMigration$default(context, str, null, false, 12, null);
    }

    @JvmOverloads
    @NotNull
    public static final SharedPreferencesMigration<Preferences> SharedPreferencesMigration(@NotNull Context context, @NotNull String str, @Nullable Set<String> set) {
        return SharedPreferencesMigration$default(context, str, set, false, 8, null);
    }

    @JvmOverloads
    @NotNull
    public static final SharedPreferencesMigration<Preferences> SharedPreferencesMigration(@NotNull Context context, @NotNull String sharedPreferencesName, @Nullable Set<String> set, boolean z2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sharedPreferencesName, "sharedPreferencesName");
        return new SharedPreferencesMigration<>(context, sharedPreferencesName, set, z2, new AnonymousClass1(set, null), new AnonymousClass2(null));
    }

    public static /* synthetic */ SharedPreferencesMigration SharedPreferencesMigration$default(Context context, String str, Set set, boolean z2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            set = (Set) MIGRATE_ALL_KEYS;
        }
        if ((i2 & 8) != 0) {
            z2 = true;
        }
        return SharedPreferencesMigration(context, str, set, z2);
    }

    @Nullable
    public static final Void getMIGRATE_ALL_KEYS() {
        return MIGRATE_ALL_KEYS;
    }
}
