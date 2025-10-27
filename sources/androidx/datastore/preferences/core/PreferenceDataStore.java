package androidx.datastore.preferences.core;

import androidx.datastore.core.DataStore;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\u0002\u0010\u0004JD\u0010\t\u001a\u00020\u000221\u0010\n\u001a-\b\u0001\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000bH\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0011R\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"Landroidx/datastore/preferences/core/PreferenceDataStore;", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "delegate", "(Landroidx/datastore/core/DataStore;)V", "data", "Lkotlinx/coroutines/flow/Flow;", "getData", "()Lkotlinx/coroutines/flow/Flow;", "updateData", "transform", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "t", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-preferences-core"}, k = 1, mv = {1, 4, 1})
/* loaded from: classes.dex */
public final class PreferenceDataStore implements DataStore<Preferences> {
    private final DataStore<Preferences> delegate;

    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "Landroidx/datastore/preferences/core/Preferences;", AdvanceSetting.NETWORK_TYPE, "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 1})
    @DebugMetadata(c = "androidx.datastore.preferences.core.PreferenceDataStore$updateData$2", f = "PreferenceDataStoreFactory.kt", i = {}, l = {85}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.datastore.preferences.core.PreferenceDataStore$updateData$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<Preferences, Continuation<? super Preferences>, Object> {
        final /* synthetic */ Function2 $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$transform = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$transform, completion);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Preferences preferences, Continuation<? super Preferences> continuation) {
            return ((AnonymousClass2) create(preferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                Preferences preferences = (Preferences) this.L$0;
                Function2 function2 = this.$transform;
                this.label = 1;
                obj = function2.invoke(preferences, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Preferences preferences2 = (Preferences) obj;
            if (preferences2 == null) {
                throw new NullPointerException("null cannot be cast to non-null type androidx.datastore.preferences.core.MutablePreferences");
            }
            ((MutablePreferences) preferences2).freeze$datastore_preferences_core();
            return preferences2;
        }
    }

    public PreferenceDataStore(@NotNull DataStore<Preferences> delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // androidx.datastore.core.DataStore
    @NotNull
    public Flow<Preferences> getData() {
        return this.delegate.getData();
    }

    @Override // androidx.datastore.core.DataStore
    @Nullable
    public Object updateData(@NotNull Function2<? super Preferences, ? super Continuation<? super Preferences>, ? extends Object> function2, @NotNull Continuation<? super Preferences> continuation) {
        return this.delegate.updateData(new AnonymousClass2(function2, null), continuation);
    }
}
