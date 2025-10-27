package androidx.datastore;

import android.content.Context;
import androidx.datastore.core.DataMigration;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.core.Serializer;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.exifinterface.media.ExifInterface;
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

@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a^\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00072\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"createDataStore", "Landroidx/datastore/core/DataStore;", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/content/Context;", "fileName", "", "serializer", "Landroidx/datastore/core/Serializer;", "corruptionHandler", "Landroidx/datastore/core/handlers/ReplaceFileCorruptionHandler;", "migrations", "", "Landroidx/datastore/core/DataMigration;", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "datastore_release"}, k = 2, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class DataStoreFactoryKt {
    @NotNull
    public static final <T> DataStore<T> createDataStore(@NotNull final Context createDataStore, @NotNull final String fileName, @NotNull Serializer<T> serializer, @Nullable ReplaceFileCorruptionHandler<T> replaceFileCorruptionHandler, @NotNull List<? extends DataMigration<T>> migrations, @NotNull CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(createDataStore, "$this$createDataStore");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return DataStoreFactory.INSTANCE.create(serializer, replaceFileCorruptionHandler, migrations, scope, new Function0<File>() { // from class: androidx.datastore.DataStoreFactoryKt.createDataStore.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final File invoke() {
                return new File(createDataStore.getFilesDir(), "datastore/" + fileName);
            }
        });
    }

    public static /* synthetic */ DataStore createDataStore$default(Context context, String str, Serializer serializer, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, int i2, Object obj) {
        ReplaceFileCorruptionHandler replaceFileCorruptionHandler2 = (i2 & 4) != 0 ? null : replaceFileCorruptionHandler;
        if ((i2 & 8) != 0) {
            list = CollectionsKt__CollectionsKt.emptyList();
        }
        List list2 = list;
        if ((i2 & 16) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return createDataStore(context, str, serializer, replaceFileCorruptionHandler2, list2, coroutineScope);
    }
}
