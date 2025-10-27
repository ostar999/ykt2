package androidx.datastore.migrations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import androidx.datastore.core.DataMigration;
import androidx.exifinterface.media.ExifInterface;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.umeng.analytics.pro.d;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0086\u0001\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012$\b\u0002\u0010\u000b\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\f\u0012(\u0010\u000f\u001a$\b\u0001\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0010ø\u0001\u0000¢\u0006\u0002\u0010\u0012J\u0011\u0010 \u001a\u00020!H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\"J\u0018\u0010#\u001a\u00020!2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0006H\u0003J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&H\u0002J\u0018\u0010(\u001a\u00020&2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0006H\u0002J\u0019\u0010\u000f\u001a\u00028\u00002\u0006\u0010)\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010*J\u0019\u0010+\u001a\u00020\n2\u0006\u0010)\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010*R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R5\u0010\u000f\u001a$\b\u0001\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0010X\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0019R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001a\u001a\u00020\u001b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0018\u001a\u0004\b\u001c\u0010\u001dR/\u0010\u000b\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\fX\u0082\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u001f\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006,"}, d2 = {"Landroidx/datastore/migrations/SharedPreferencesMigration;", ExifInterface.GPS_DIRECTION_TRUE, "Landroidx/datastore/core/DataMigration;", d.R, "Landroid/content/Context;", "sharedPreferencesName", "", "keysToMigrate", "", "deleteEmptyPreferences", "", "shouldRunMigration", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "migrate", "Lkotlin/Function3;", "Landroidx/datastore/migrations/SharedPreferencesView;", "(Landroid/content/Context;Ljava/lang/String;Ljava/util/Set;ZLkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)V", "keySet", "", "getKeySet", "()Ljava/util/Set;", "keySet$delegate", "Lkotlin/Lazy;", "Lkotlin/jvm/functions/Function3;", "sharedPrefs", "Landroid/content/SharedPreferences;", "getSharedPrefs", "()Landroid/content/SharedPreferences;", "sharedPrefs$delegate", "Lkotlin/jvm/functions/Function2;", "cleanUp", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSharedPreferences", "name", "getSharedPrefsBackup", "Ljava/io/File;", "prefsFile", "getSharedPrefsFile", "currentData", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shouldMigrate", "datastore_release"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class SharedPreferencesMigration<T> implements DataMigration<T> {
    private final Context context;
    private final boolean deleteEmptyPreferences;

    /* renamed from: keySet$delegate, reason: from kotlin metadata */
    private final Lazy keySet;
    private final Function3<SharedPreferencesView, T, Continuation<? super T>, Object> migrate;
    private final String sharedPreferencesName;

    /* renamed from: sharedPrefs$delegate, reason: from kotlin metadata */
    private final Lazy sharedPrefs;
    private final Function2<T, Continuation<? super Boolean>, Object> shouldRunMigration;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, AdvanceSetting.NETWORK_TYPE, "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.migrations.SharedPreferencesMigration$1", f = "SharedPreferencesMigration.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.datastore.migrations.SharedPreferencesMigration$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<T, Continuation<? super Boolean>, Object> {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new AnonymousClass1(completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Continuation<? super Boolean> continuation) {
            return ((AnonymousClass1) create(obj, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Boxing.boxBoolean(true);
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0006\u001a\u0004\u0018\u00010\u0005\"\u0004\b\u0000\u0010\u00002\u0006\u0010\u0001\u001a\u00028\u00002\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0096@"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "currentData", "Lkotlin/coroutines/Continuation;", "", "continuation", "", "shouldMigrate"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.migrations.SharedPreferencesMigration", f = "SharedPreferencesMigration.kt", i = {0}, l = {78}, m = "shouldMigrate", n = {"this"}, s = {"L$0"})
    /* renamed from: androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1, reason: invalid class name and case insensitive filesystem */
    public static final class C04931 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C04931(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SharedPreferencesMigration.this.shouldMigrate(null, this);
        }
    }

    @JvmOverloads
    public SharedPreferencesMigration(@NotNull Context context, @NotNull String str, @Nullable Set<String> set, @NotNull Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        this(context, str, set, false, null, function3, 24, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public SharedPreferencesMigration(@NotNull Context context, @NotNull String sharedPreferencesName, @Nullable final Set<String> set, boolean z2, @NotNull Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> shouldRunMigration, @NotNull Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> migrate) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(sharedPreferencesName, "sharedPreferencesName");
        Intrinsics.checkNotNullParameter(shouldRunMigration, "shouldRunMigration");
        Intrinsics.checkNotNullParameter(migrate, "migrate");
        this.context = context;
        this.sharedPreferencesName = sharedPreferencesName;
        this.deleteEmptyPreferences = z2;
        this.shouldRunMigration = shouldRunMigration;
        this.migrate = migrate;
        this.sharedPrefs = LazyKt__LazyJVMKt.lazy(new Function0<SharedPreferences>() { // from class: androidx.datastore.migrations.SharedPreferencesMigration$sharedPrefs$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final SharedPreferences invoke() {
                return this.this$0.context.getSharedPreferences(this.this$0.sharedPreferencesName, 0);
            }
        });
        this.keySet = LazyKt__LazyJVMKt.lazy(new Function0<Set<String>>() { // from class: androidx.datastore.migrations.SharedPreferencesMigration$keySet$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Set<String> invoke() {
                Set<String> setKeySet = set;
                if (setKeySet == null) {
                    setKeySet = this.this$0.getSharedPrefs().getAll().keySet();
                }
                return CollectionsKt___CollectionsKt.toMutableSet(setKeySet);
            }
        });
    }

    @JvmOverloads
    public SharedPreferencesMigration(@NotNull Context context, @NotNull String str, @Nullable Set<String> set, boolean z2, @NotNull Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        this(context, str, set, z2, null, function3, 16, null);
    }

    @JvmOverloads
    public SharedPreferencesMigration(@NotNull Context context, @NotNull String str, @NotNull Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        this(context, str, null, false, null, function3, 28, null);
    }

    @SuppressLint({"UnsafeNewApiCall"})
    private final void deleteSharedPreferences(Context context, String name) throws IOException {
        if (Build.VERSION.SDK_INT < 24) {
            File sharedPrefsFile = getSharedPrefsFile(context, name);
            File sharedPrefsBackup = getSharedPrefsBackup(sharedPrefsFile);
            sharedPrefsFile.delete();
            sharedPrefsBackup.delete();
            return;
        }
        if (context.deleteSharedPreferences(name)) {
            return;
        }
        throw new IOException("Unable to delete SharedPreferences: " + name);
    }

    private final Set<String> getKeySet() {
        return (Set) this.keySet.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SharedPreferences getSharedPrefs() {
        return (SharedPreferences) this.sharedPrefs.getValue();
    }

    private final File getSharedPrefsBackup(File prefsFile) {
        return new File(prefsFile.getPath() + ".bak");
    }

    private final File getSharedPrefsFile(Context context, String name) {
        return new File(new File(context.getApplicationInfo().dataDir, "shared_prefs"), name + ".xml");
    }

    @Override // androidx.datastore.core.DataMigration
    @Nullable
    public Object cleanUp(@NotNull Continuation<? super Unit> continuation) throws IOException {
        SharedPreferences.Editor editorEdit = getSharedPrefs().edit();
        Iterator<String> it = getKeySet().iterator();
        while (it.hasNext()) {
            editorEdit.remove(it.next());
        }
        if (!editorEdit.commit()) {
            throw new IOException("Unable to delete migrated keys from SharedPreferences: " + this.sharedPreferencesName);
        }
        if (this.deleteEmptyPreferences && getSharedPrefs().getAll().isEmpty()) {
            deleteSharedPreferences(this.context, this.sharedPreferencesName);
        }
        getKeySet().clear();
        return Unit.INSTANCE;
    }

    @Override // androidx.datastore.core.DataMigration
    @Nullable
    public Object migrate(T t2, @NotNull Continuation<? super T> continuation) {
        return this.migrate.invoke(new SharedPreferencesView(getSharedPrefs(), getKeySet()), t2, continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // androidx.datastore.core.DataMigration
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object shouldMigrate(T r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super java.lang.Boolean> r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof androidx.datastore.migrations.SharedPreferencesMigration.C04931
            if (r0 == 0) goto L13
            r0 = r6
            androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1 r0 = (androidx.datastore.migrations.SharedPreferencesMigration.C04931) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1 r0 = new androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            java.lang.Object r5 = r0.L$0
            androidx.datastore.migrations.SharedPreferencesMigration r5 = (androidx.datastore.migrations.SharedPreferencesMigration) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L46
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlin.jvm.functions.Function2<T, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r6 = r4.shouldRunMigration
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r6 = r6.invoke(r5, r0)
            if (r6 != r1) goto L45
            return r1
        L45:
            r5 = r4
        L46:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            r0 = 0
            if (r6 != 0) goto L54
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r0)
            return r5
        L54:
            java.util.Set r6 = r5.getKeySet()
            android.content.SharedPreferences r5 = r5.getSharedPrefs()
            boolean r1 = r6 instanceof java.util.Collection
            if (r1 == 0) goto L68
            boolean r1 = r6.isEmpty()
            if (r1 == 0) goto L68
        L66:
            r3 = r0
            goto L86
        L68:
            java.util.Iterator r6 = r6.iterator()
        L6c:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L66
            java.lang.Object r1 = r6.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r1 = r5.contains(r1)
            java.lang.Boolean r1 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r1)
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L6c
        L86:
            java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.migrations.SharedPreferencesMigration.shouldMigrate(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public /* synthetic */ SharedPreferencesMigration(Context context, String str, Set set, boolean z2, Function2 function2, Function3 function3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, str, (i2 & 4) != 0 ? (Set) SharedPreferencesMigrationKt.getMIGRATE_ALL_KEYS() : set, (i2 & 8) != 0 ? true : z2, (i2 & 16) != 0 ? new AnonymousClass1(null) : function2, function3);
    }
}
