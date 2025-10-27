package androidx.datastore.core;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Add missing generic type declarations: [T] */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001\"\u0004\b\u0001\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", ExifInterface.GPS_DIRECTION_TRUE, "startingData", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 1})
@DebugMetadata(c = "androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2", f = "DataMigrationInitializer.kt", i = {0, 0}, l = {44, 46}, m = "invokeSuspend", n = {"migration", "data"}, s = {"L$1", "L$2"})
/* loaded from: classes.dex */
public final class DataMigrationInitializer$Companion$runMigrations$2<T> extends SuspendLambda implements Function2<T, Continuation<? super T>, Object> {
    final /* synthetic */ List $cleanUps;
    final /* synthetic */ List $migrations;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataMigrationInitializer$Companion$runMigrations$2(List list, List list2, Continuation continuation) {
        super(2, continuation);
        this.$migrations = list;
        this.$cleanUps = list2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @NotNull
    public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
        Intrinsics.checkNotNullParameter(completion, "completion");
        DataMigrationInitializer$Companion$runMigrations$2 dataMigrationInitializer$Companion$runMigrations$2 = new DataMigrationInitializer$Companion$runMigrations$2(this.$migrations, this.$cleanUps, completion);
        dataMigrationInitializer$Companion$runMigrations$2.L$0 = obj;
        return dataMigrationInitializer$Companion$runMigrations$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataMigrationInitializer$Companion$runMigrations$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0082 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L2e
            if (r1 == r3) goto L1e
            if (r1 != r2) goto L16
            java.lang.Object r1 = r10.L$0
            java.util.Iterator r1 = (java.util.Iterator) r1
            kotlin.ResultKt.throwOnFailure(r11)
            goto L3b
        L16:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L1e:
            java.lang.Object r1 = r10.L$2
            java.lang.Object r4 = r10.L$1
            androidx.datastore.core.DataMigration r4 = (androidx.datastore.core.DataMigration) r4
            java.lang.Object r5 = r10.L$0
            java.util.Iterator r5 = (java.util.Iterator) r5
            kotlin.ResultKt.throwOnFailure(r11)
            r6 = r4
            r4 = r10
            goto L5c
        L2e:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            java.util.List r1 = r10.$migrations
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Iterator r1 = r1.iterator()
        L3b:
            r4 = r10
        L3c:
            boolean r5 = r1.hasNext()
            if (r5 == 0) goto L82
            java.lang.Object r5 = r1.next()
            androidx.datastore.core.DataMigration r5 = (androidx.datastore.core.DataMigration) r5
            r4.L$0 = r1
            r4.L$1 = r5
            r4.L$2 = r11
            r4.label = r3
            java.lang.Object r6 = r5.shouldMigrate(r11, r4)
            if (r6 != r0) goto L57
            return r0
        L57:
            r9 = r1
            r1 = r11
            r11 = r6
            r6 = r5
            r5 = r9
        L5c:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L80
            java.util.List r11 = r4.$cleanUps
            androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2$1$1 r7 = new androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2$1$1
            r8 = 0
            r7.<init>(r6, r8)
            r11.add(r7)
            r4.L$0 = r5
            r4.L$1 = r8
            r4.L$2 = r8
            r4.label = r2
            java.lang.Object r11 = r6.migrate(r1, r4)
            if (r11 != r0) goto L7e
            return r0
        L7e:
            r1 = r5
            goto L3c
        L80:
            r11 = r1
            goto L7e
        L82:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataMigrationInitializer$Companion$runMigrations$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
