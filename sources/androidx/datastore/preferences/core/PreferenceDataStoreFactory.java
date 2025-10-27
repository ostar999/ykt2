package androidx.datastore.preferences.core;

import androidx.datastore.core.DataMigration;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import com.tencent.connect.common.Constants;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.io.FilesKt__UtilsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JN\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00072\u0014\b\u0002\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\n0\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0007¨\u0006\u0010"}, d2 = {"Landroidx/datastore/preferences/core/PreferenceDataStoreFactory;", "", "()V", "create", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "corruptionHandler", "Landroidx/datastore/core/handlers/ReplaceFileCorruptionHandler;", "migrations", "", "Landroidx/datastore/core/DataMigration;", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "produceFile", "Lkotlin/Function0;", "Ljava/io/File;", "datastore-preferences-core"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class PreferenceDataStoreFactory {

    @NotNull
    public static final PreferenceDataStoreFactory INSTANCE = new PreferenceDataStoreFactory();

    private PreferenceDataStoreFactory() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DataStore create$default(PreferenceDataStoreFactory preferenceDataStoreFactory, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, Function0 function0, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            replaceFileCorruptionHandler = null;
        }
        if ((i2 & 2) != 0) {
            list = CollectionsKt__CollectionsKt.emptyList();
        }
        if ((i2 & 4) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return preferenceDataStoreFactory.create(replaceFileCorruptionHandler, list, coroutineScope, function0);
    }

    @JvmOverloads
    @NotNull
    public final DataStore<Preferences> create(@Nullable ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, @NotNull List<? extends DataMigration<Preferences>> list, @NotNull Function0<? extends File> function0) {
        return create$default(this, replaceFileCorruptionHandler, list, null, function0, 4, null);
    }

    @JvmOverloads
    @NotNull
    public final DataStore<Preferences> create(@Nullable ReplaceFileCorruptionHandler<Preferences> corruptionHandler, @NotNull List<? extends DataMigration<Preferences>> migrations, @NotNull CoroutineScope scope, @NotNull final Function0<? extends File> produceFile) {
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(produceFile, "produceFile");
        return new PreferenceDataStore(DataStoreFactory.INSTANCE.create(PreferencesSerializer.INSTANCE, corruptionHandler, migrations, scope, new Function0<File>() { // from class: androidx.datastore.preferences.core.PreferenceDataStoreFactory$create$delegate$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final File invoke() {
                File file = (File) produceFile.invoke();
                String extension = FilesKt__UtilsKt.getExtension(file);
                PreferencesSerializer preferencesSerializer = PreferencesSerializer.INSTANCE;
                if (Intrinsics.areEqual(extension, preferencesSerializer.getFileExtension())) {
                    return file;
                }
                throw new IllegalStateException(("File extension for file: " + file + " does not match required extension for Preferences file: " + preferencesSerializer.getFileExtension()).toString());
            }
        }));
    }

    @JvmOverloads
    @NotNull
    public final DataStore<Preferences> create(@Nullable ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, @NotNull Function0<? extends File> function0) {
        return create$default(this, replaceFileCorruptionHandler, null, null, function0, 6, null);
    }

    @JvmOverloads
    @NotNull
    public final DataStore<Preferences> create(@NotNull Function0<? extends File> function0) {
        return create$default(this, null, null, null, function0, 7, null);
    }
}
