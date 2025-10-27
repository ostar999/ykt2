package androidx.datastore.preferences;

import android.content.Context;
import androidx.datastore.core.DataMigration;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.core.PreferenceDataStoreFactory;
import androidx.datastore.preferences.core.Preferences;
import com.tencent.connect.common.Constants;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aJ\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00072\u0014\b\u0002\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"createDataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "Landroid/content/Context;", "name", "", "corruptionHandler", "Landroidx/datastore/core/handlers/ReplaceFileCorruptionHandler;", "migrations", "", "Landroidx/datastore/core/DataMigration;", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "datastore-preferences_release"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class PreferenceDataStoreFactoryKt {
    @NotNull
    public static final DataStore<Preferences> createDataStore(@NotNull final Context createDataStore, @NotNull final String name, @Nullable ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, @NotNull List<? extends DataMigration<Preferences>> migrations, @NotNull CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(createDataStore, "$this$createDataStore");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return PreferenceDataStoreFactory.INSTANCE.create(replaceFileCorruptionHandler, migrations, scope, new Function0<File>() { // from class: androidx.datastore.preferences.PreferenceDataStoreFactoryKt.createDataStore.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final File invoke() {
                return new File(createDataStore.getFilesDir(), "datastore/" + name + ".preferences_pb");
            }
        });
    }

    public static /* synthetic */ DataStore createDataStore$default(Context context, String str, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            replaceFileCorruptionHandler = null;
        }
        if ((i2 & 4) != 0) {
            list = CollectionsKt__CollectionsKt.emptyList();
        }
        if ((i2 & 8) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return createDataStore(context, str, replaceFileCorruptionHandler, list, coroutineScope);
    }
}
