package com.catchpig.mvvm.utils;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.PreferenceDataStoreFactoryKt;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesFactory;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.core.PreferencesKt;
import androidx.exifinterface.media.ExifInterface;
import com.catchpig.mvvm.config.Config;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.umeng.analytics.pro.d;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.eclipse.jetty.servlet.ServletHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0013\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0006\u001a\u00020\u0007H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\bJ\u0006\u0010\t\u001a\u00020\u0007J'\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\f0\u000b\"\u0004\b\u0000\u0010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u0002H\f¢\u0006\u0002\u0010\u0010J!\u0010\u0011\u001a\u0002H\f\"\u0004\b\u0000\u0010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u0002H\f¢\u0006\u0002\u0010\u0012J\u000e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015J'\u0010\u0016\u001a\u00020\u0007\"\u0004\b\u0000\u0010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u0002H\fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0018J!\u0010\u0019\u001a\u00020\u0007\"\u0004\b\u0000\u0010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u0002H\f¢\u0006\u0002\u0010\u001aJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u001cJ\u001e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u001cJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u001fJ\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u001fJ\u0018\u0010!\u001a\u00020\"2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\"J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020\"0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\"J\u0018\u0010$\u001a\u00020%2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020%J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020%0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020%J\u0018\u0010'\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eJ\u001e\u0010(\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eJ!\u0010)\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u001cH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010*J!\u0010+\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u001fH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010,J!\u0010-\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\"H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010.J!\u0010/\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020%H\u0086@ø\u0001\u0000¢\u0006\u0002\u00100J!\u00101\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000eH\u0086@ø\u0001\u0000¢\u0006\u0002\u00102J\u0016\u00103\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u001cJ\u0016\u00104\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u001fJ\u0016\u00105\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\"J\u0016\u00106\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020%J\u0016\u00107\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u000eR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082.¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u00068"}, d2 = {"Lcom/catchpig/mvvm/utils/DataStoreUtils;", "", "()V", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", PLVDocumentMarkToolType.CLEAR, "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearSync", "getData", "Lkotlinx/coroutines/flow/Flow;", "U", "key", "", ServletHandler.__DEFAULT_SERVLET, "(Ljava/lang/String;Ljava/lang/Object;)Lkotlinx/coroutines/flow/Flow;", "getSyncData", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "init", d.R, "Landroid/content/Context;", "putData", "value", "(Ljava/lang/String;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "putSyncData", "(Ljava/lang/String;Ljava/lang/Object;)V", "readBooleanData", "", "readBooleanFlow", "readFloatData", "", "readFloatFlow", "readIntData", "", "readIntFlow", "readLongData", "", "readLongFlow", "readStringData", "readStringFlow", "saveBooleanData", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveFloatData", "(Ljava/lang/String;FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveIntData", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveLongData", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveStringData", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveSyncBooleanData", "saveSyncFloatData", "saveSyncIntData", "saveSyncLongData", "saveSyncStringData", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDataStoreUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataStoreUtils.kt\ncom/catchpig/mvvm/utils/DataStoreUtils\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt\n+ 4 SafeCollector.common.kt\nkotlinx/coroutines/flow/internal/SafeCollector_commonKt\n*L\n1#1,298:1\n47#2:299\n49#2:303\n47#2:304\n49#2:308\n47#2:309\n49#2:313\n47#2:314\n49#2:318\n47#2:319\n49#2:323\n50#3:300\n55#3:302\n50#3:305\n55#3:307\n50#3:310\n55#3:312\n50#3:315\n55#3:317\n50#3:320\n55#3:322\n106#4:301\n106#4:306\n106#4:311\n106#4:316\n106#4:321\n*S KotlinDebug\n*F\n+ 1 DataStoreUtils.kt\ncom/catchpig/mvvm/utils/DataStoreUtils\n*L\n132#1:299\n132#1:303\n156#1:304\n156#1:308\n180#1:309\n180#1:313\n204#1:314\n204#1:318\n228#1:319\n228#1:323\n132#1:300\n132#1:302\n156#1:305\n156#1:307\n180#1:310\n180#1:312\n204#1:315\n204#1:317\n228#1:320\n228#1:322\n132#1:301\n156#1:306\n180#1:311\n204#1:316\n228#1:321\n*E\n"})
/* loaded from: classes2.dex */
public final class DataStoreUtils {

    @NotNull
    public static final DataStoreUtils INSTANCE = new DataStoreUtils();
    private static DataStore<Preferences> dataStore;

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/MutablePreferences;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$clear$2", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$clear$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).clear();
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Landroidx/datastore/preferences/core/Preferences;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$clearSync$1", f = "DataStoreUtils.kt", i = {}, l = {R2.attr.all_comment_text_color}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$clearSync$1, reason: invalid class name */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
        int label;

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/MutablePreferences;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$clearSync$1$1", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$clearSync$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00971 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
            /* synthetic */ Object L$0;
            int label;

            public C00971(Continuation<? super C00971> continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C00971 c00971 = new C00971(continuation);
                c00971.L$0 = obj;
                return c00971;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
                return ((C00971) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                ((MutablePreferences) this.L$0).clear();
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Preferences> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = DataStoreUtils.dataStore;
                if (dataStore == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dataStore");
                    dataStore = null;
                }
                C00971 c00971 = new C00971(null);
                this.label = 1;
                obj = PreferencesKt.edit(dataStore, c00971, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Landroidx/datastore/preferences/core/Preferences;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readBooleanData$1", f = "DataStoreUtils.kt", i = {}, l = {139}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readBooleanData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05271 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
        final /* synthetic */ boolean $default;
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.BooleanRef $value;
        int label;

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/Preferences;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readBooleanData$1$1", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readBooleanData$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00981 extends SuspendLambda implements Function2<Preferences, Continuation<? super Boolean>, Object> {
            final /* synthetic */ boolean $default;
            final /* synthetic */ String $key;
            final /* synthetic */ Ref.BooleanRef $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00981(Ref.BooleanRef booleanRef, String str, boolean z2, Continuation<? super C00981> continuation) {
                super(2, continuation);
                this.$value = booleanRef;
                this.$key = str;
                this.$default = z2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C00981 c00981 = new C00981(this.$value, this.$key, this.$default, continuation);
                c00981.L$0 = obj;
                return c00981;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull Preferences preferences, @Nullable Continuation<? super Boolean> continuation) {
                return ((C00981) create(preferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Preferences preferences = (Preferences) this.L$0;
                Ref.BooleanRef booleanRef = this.$value;
                Boolean bool = (Boolean) preferences.get(PreferencesKeys.booleanKey(this.$key));
                booleanRef.element = bool != null ? bool.booleanValue() : this.$default;
                return Boxing.boxBoolean(true);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05271(Ref.BooleanRef booleanRef, String str, boolean z2, Continuation<? super C05271> continuation) {
            super(2, continuation);
            this.$value = booleanRef;
            this.$key = str;
            this.$default = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05271(this.$value, this.$key, this.$default, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Preferences> continuation) {
            return ((C05271) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = DataStoreUtils.dataStore;
                if (dataStore == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dataStore");
                    dataStore = null;
                }
                Flow data = dataStore.getData();
                C00981 c00981 = new C00981(this.$value, this.$key, this.$default, null);
                this.label = 1;
                obj = FlowKt.first(data, c00981, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Landroidx/datastore/preferences/core/Preferences;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$1", f = "DataStoreUtils.kt", i = {}, l = {128}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05281 extends SuspendLambda implements Function3<FlowCollector<? super Preferences>, Throwable, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C05281(Continuation<? super C05281> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Preferences> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            C05281 c05281 = new C05281(continuation);
            c05281.L$0 = flowCollector;
            c05281.L$1 = th;
            return c05281.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Throwable th = (Throwable) this.L$1;
                if (!(th instanceof IOException)) {
                    throw th;
                }
                th.printStackTrace();
                Preferences preferencesCreateEmpty = PreferencesFactory.createEmpty();
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(preferencesCreateEmpty, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Landroidx/datastore/preferences/core/Preferences;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readFloatData$1", f = "DataStoreUtils.kt", i = {}, l = {211}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readFloatData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05291 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
        final /* synthetic */ float $default;
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.FloatRef $value;
        int label;

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/Preferences;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readFloatData$1$1", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readFloatData$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C00991 extends SuspendLambda implements Function2<Preferences, Continuation<? super Boolean>, Object> {
            final /* synthetic */ float $default;
            final /* synthetic */ String $key;
            final /* synthetic */ Ref.FloatRef $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00991(Ref.FloatRef floatRef, String str, float f2, Continuation<? super C00991> continuation) {
                super(2, continuation);
                this.$value = floatRef;
                this.$key = str;
                this.$default = f2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C00991 c00991 = new C00991(this.$value, this.$key, this.$default, continuation);
                c00991.L$0 = obj;
                return c00991;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull Preferences preferences, @Nullable Continuation<? super Boolean> continuation) {
                return ((C00991) create(preferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Preferences preferences = (Preferences) this.L$0;
                Ref.FloatRef floatRef = this.$value;
                Float f2 = (Float) preferences.get(PreferencesKeys.floatKey(this.$key));
                floatRef.element = f2 != null ? f2.floatValue() : this.$default;
                return Boxing.boxBoolean(true);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05291(Ref.FloatRef floatRef, String str, float f2, Continuation<? super C05291> continuation) {
            super(2, continuation);
            this.$value = floatRef;
            this.$key = str;
            this.$default = f2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05291(this.$value, this.$key, this.$default, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Preferences> continuation) {
            return ((C05291) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = DataStoreUtils.dataStore;
                if (dataStore == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dataStore");
                    dataStore = null;
                }
                Flow data = dataStore.getData();
                C00991 c00991 = new C00991(this.$value, this.$key, this.$default, null);
                this.label = 1;
                obj = FlowKt.first(data, c00991, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Landroidx/datastore/preferences/core/Preferences;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$1", f = "DataStoreUtils.kt", i = {}, l = {200}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05301 extends SuspendLambda implements Function3<FlowCollector<? super Preferences>, Throwable, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C05301(Continuation<? super C05301> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Preferences> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            C05301 c05301 = new C05301(continuation);
            c05301.L$0 = flowCollector;
            c05301.L$1 = th;
            return c05301.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Throwable th = (Throwable) this.L$1;
                if (!(th instanceof IOException)) {
                    throw th;
                }
                th.printStackTrace();
                Preferences preferencesCreateEmpty = PreferencesFactory.createEmpty();
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(preferencesCreateEmpty, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Landroidx/datastore/preferences/core/Preferences;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readIntData$1", f = "DataStoreUtils.kt", i = {}, l = {163}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readIntData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05311 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
        final /* synthetic */ int $default;
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.IntRef $value;
        int label;

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/Preferences;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readIntData$1$1", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readIntData$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C01001 extends SuspendLambda implements Function2<Preferences, Continuation<? super Boolean>, Object> {
            final /* synthetic */ int $default;
            final /* synthetic */ String $key;
            final /* synthetic */ Ref.IntRef $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01001(Ref.IntRef intRef, String str, int i2, Continuation<? super C01001> continuation) {
                super(2, continuation);
                this.$value = intRef;
                this.$key = str;
                this.$default = i2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C01001 c01001 = new C01001(this.$value, this.$key, this.$default, continuation);
                c01001.L$0 = obj;
                return c01001;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull Preferences preferences, @Nullable Continuation<? super Boolean> continuation) {
                return ((C01001) create(preferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Preferences preferences = (Preferences) this.L$0;
                Ref.IntRef intRef = this.$value;
                Integer num = (Integer) preferences.get(PreferencesKeys.intKey(this.$key));
                intRef.element = num != null ? num.intValue() : this.$default;
                return Boxing.boxBoolean(true);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05311(Ref.IntRef intRef, String str, int i2, Continuation<? super C05311> continuation) {
            super(2, continuation);
            this.$value = intRef;
            this.$key = str;
            this.$default = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05311(this.$value, this.$key, this.$default, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Preferences> continuation) {
            return ((C05311) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = DataStoreUtils.dataStore;
                if (dataStore == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dataStore");
                    dataStore = null;
                }
                Flow data = dataStore.getData();
                C01001 c01001 = new C01001(this.$value, this.$key, this.$default, null);
                this.label = 1;
                obj = FlowKt.first(data, c01001, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Landroidx/datastore/preferences/core/Preferences;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$1", f = "DataStoreUtils.kt", i = {}, l = {152}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05321 extends SuspendLambda implements Function3<FlowCollector<? super Preferences>, Throwable, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C05321(Continuation<? super C05321> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Preferences> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            C05321 c05321 = new C05321(continuation);
            c05321.L$0 = flowCollector;
            c05321.L$1 = th;
            return c05321.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Throwable th = (Throwable) this.L$1;
                if (!(th instanceof IOException)) {
                    throw th;
                }
                th.printStackTrace();
                Preferences preferencesCreateEmpty = PreferencesFactory.createEmpty();
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(preferencesCreateEmpty, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Landroidx/datastore/preferences/core/Preferences;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readLongData$1", f = "DataStoreUtils.kt", i = {}, l = {235}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readLongData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05331 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
        final /* synthetic */ long $default;
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.LongRef $value;
        int label;

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/Preferences;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readLongData$1$1", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readLongData$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C01011 extends SuspendLambda implements Function2<Preferences, Continuation<? super Boolean>, Object> {
            final /* synthetic */ long $default;
            final /* synthetic */ String $key;
            final /* synthetic */ Ref.LongRef $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01011(Ref.LongRef longRef, String str, long j2, Continuation<? super C01011> continuation) {
                super(2, continuation);
                this.$value = longRef;
                this.$key = str;
                this.$default = j2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C01011 c01011 = new C01011(this.$value, this.$key, this.$default, continuation);
                c01011.L$0 = obj;
                return c01011;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull Preferences preferences, @Nullable Continuation<? super Boolean> continuation) {
                return ((C01011) create(preferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Preferences preferences = (Preferences) this.L$0;
                Ref.LongRef longRef = this.$value;
                Long l2 = (Long) preferences.get(PreferencesKeys.longKey(this.$key));
                longRef.element = l2 != null ? l2.longValue() : this.$default;
                return Boxing.boxBoolean(true);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05331(Ref.LongRef longRef, String str, long j2, Continuation<? super C05331> continuation) {
            super(2, continuation);
            this.$value = longRef;
            this.$key = str;
            this.$default = j2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05331(this.$value, this.$key, this.$default, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Preferences> continuation) {
            return ((C05331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = DataStoreUtils.dataStore;
                if (dataStore == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dataStore");
                    dataStore = null;
                }
                Flow data = dataStore.getData();
                C01011 c01011 = new C01011(this.$value, this.$key, this.$default, null);
                this.label = 1;
                obj = FlowKt.first(data, c01011, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Landroidx/datastore/preferences/core/Preferences;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$1", f = "DataStoreUtils.kt", i = {}, l = {224}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05341 extends SuspendLambda implements Function3<FlowCollector<? super Preferences>, Throwable, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C05341(Continuation<? super C05341> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Preferences> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            C05341 c05341 = new C05341(continuation);
            c05341.L$0 = flowCollector;
            c05341.L$1 = th;
            return c05341.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Throwable th = (Throwable) this.L$1;
                if (!(th instanceof IOException)) {
                    throw th;
                }
                th.printStackTrace();
                Preferences preferencesCreateEmpty = PreferencesFactory.createEmpty();
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(preferencesCreateEmpty, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "Landroidx/datastore/preferences/core/Preferences;", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readStringData$1", f = "DataStoreUtils.kt", i = {}, l = {187}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readStringData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05351 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
        final /* synthetic */ String $default;
        final /* synthetic */ String $key;
        final /* synthetic */ Ref.ObjectRef<String> $value;
        int label;

        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/Preferences;", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
        @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readStringData$1$1", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readStringData$1$1, reason: invalid class name and collision with other inner class name */
        public static final class C01021 extends SuspendLambda implements Function2<Preferences, Continuation<? super Boolean>, Object> {
            final /* synthetic */ String $default;
            final /* synthetic */ String $key;
            final /* synthetic */ Ref.ObjectRef<String> $value;
            /* synthetic */ Object L$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C01021(Ref.ObjectRef<String> objectRef, String str, String str2, Continuation<? super C01021> continuation) {
                super(2, continuation);
                this.$value = objectRef;
                this.$key = str;
                this.$default = str2;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @NotNull
            public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
                C01021 c01021 = new C01021(this.$value, this.$key, this.$default, continuation);
                c01021.L$0 = obj;
                return c01021;
            }

            @Override // kotlin.jvm.functions.Function2
            @Nullable
            public final Object invoke(@NotNull Preferences preferences, @Nullable Continuation<? super Boolean> continuation) {
                return ((C01021) create(preferences, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                Preferences preferences = (Preferences) this.L$0;
                Ref.ObjectRef<String> objectRef = this.$value;
                String str = (String) preferences.get(PreferencesKeys.stringKey(this.$key));
                T t2 = str;
                if (str == null) {
                    t2 = this.$default;
                }
                objectRef.element = t2;
                return Boxing.boxBoolean(true);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05351(Ref.ObjectRef<String> objectRef, String str, String str2, Continuation<? super C05351> continuation) {
            super(2, continuation);
            this.$value = objectRef;
            this.$key = str;
            this.$default = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05351(this.$value, this.$key, this.$default, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Preferences> continuation) {
            return ((C05351) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStore dataStore = DataStoreUtils.dataStore;
                if (dataStore == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dataStore");
                    dataStore = null;
                }
                Flow data = dataStore.getData();
                C01021 c01021 = new C01021(this.$value, this.$key, this.$default, null);
                this.label = 1;
                obj = FlowKt.first(data, c01021, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0005\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00010\u00002\u0006\u0010\u0003\u001a\u00020\u0002H\u008a@"}, d2 = {"Lkotlinx/coroutines/flow/FlowCollector;", "Landroidx/datastore/preferences/core/Preferences;", "", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$1", f = "DataStoreUtils.kt", i = {}, l = {176}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05361 extends SuspendLambda implements Function3<FlowCollector<? super Preferences>, Throwable, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C05361(Continuation<? super C05361> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        @Nullable
        public final Object invoke(@NotNull FlowCollector<? super Preferences> flowCollector, @NotNull Throwable th, @Nullable Continuation<? super Unit> continuation) {
            C05361 c05361 = new C05361(continuation);
            c05361.L$0 = flowCollector;
            c05361.L$1 = th;
            return c05361.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Throwable th = (Throwable) this.L$1;
                if (!(th instanceof IOException)) {
                    throw th;
                }
                th.printStackTrace();
                Preferences preferencesCreateEmpty = PreferencesFactory.createEmpty();
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(preferencesCreateEmpty, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/MutablePreferences;", "mutablePreferences", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveBooleanData$2", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveBooleanData$2, reason: invalid class name and case insensitive filesystem */
    public static final class C05372 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ boolean $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05372(String str, boolean z2, Continuation<? super C05372> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C05372 c05372 = new C05372(this.$key, this.$value, continuation);
            c05372.L$0 = obj;
            return c05372;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
            return ((C05372) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).set(PreferencesKeys.booleanKey(this.$key), Boxing.boxBoolean(this.$value));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/MutablePreferences;", "mutablePreferences", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveFloatData$2", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveFloatData$2, reason: invalid class name and case insensitive filesystem */
    public static final class C05382 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ float $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05382(String str, float f2, Continuation<? super C05382> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = f2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C05382 c05382 = new C05382(this.$key, this.$value, continuation);
            c05382.L$0 = obj;
            return c05382;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
            return ((C05382) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).set(PreferencesKeys.floatKey(this.$key), Boxing.boxFloat(this.$value));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/MutablePreferences;", "mutablePreferences", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveIntData$2", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveIntData$2, reason: invalid class name and case insensitive filesystem */
    public static final class C05392 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ int $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05392(String str, int i2, Continuation<? super C05392> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C05392 c05392 = new C05392(this.$key, this.$value, continuation);
            c05392.L$0 = obj;
            return c05392;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
            return ((C05392) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).set(PreferencesKeys.intKey(this.$key), Boxing.boxInt(this.$value));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/MutablePreferences;", "mutablePreferences", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveLongData$2", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveLongData$2, reason: invalid class name and case insensitive filesystem */
    public static final class C05402 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ long $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05402(String str, long j2, Continuation<? super C05402> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = j2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C05402 c05402 = new C05402(this.$key, this.$value, continuation);
            c05402.L$0 = obj;
            return c05402;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
            return ((C05402) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).set(PreferencesKeys.longKey(this.$key), Boxing.boxLong(this.$value));
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H\u008a@"}, d2 = {"Landroidx/datastore/preferences/core/MutablePreferences;", "mutablePreferences", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveStringData$2", f = "DataStoreUtils.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveStringData$2, reason: invalid class name and case insensitive filesystem */
    public static final class C05412 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ String $value;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05412(String str, String str2, Continuation<? super C05412> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C05412 c05412 = new C05412(this.$key, this.$value, continuation);
            c05412.L$0 = obj;
            return c05412;
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull MutablePreferences mutablePreferences, @Nullable Continuation<? super Unit> continuation) {
            return ((C05412) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((MutablePreferences) this.L$0).set(PreferencesKeys.stringKey(this.$key), this.$value);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveSyncBooleanData$1", f = "DataStoreUtils.kt", i = {}, l = {250}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveSyncBooleanData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ boolean $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05421(String str, boolean z2, Continuation<? super C05421> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = z2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05421(this.$key, this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
                String str = this.$key;
                boolean z2 = this.$value;
                this.label = 1;
                if (dataStoreUtils.saveBooleanData(str, z2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveSyncFloatData$1", f = "DataStoreUtils.kt", i = {}, l = {R2.attr.adSizes}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveSyncFloatData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05431 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ float $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05431(String str, float f2, Continuation<? super C05431> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = f2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05431(this.$key, this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05431) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
                String str = this.$key;
                float f2 = this.$value;
                this.label = 1;
                if (dataStoreUtils.saveFloatData(str, f2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveSyncIntData$1", f = "DataStoreUtils.kt", i = {}, l = {258}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveSyncIntData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05441 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ int $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05441(String str, int i2, Continuation<? super C05441> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = i2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05441(this.$key, this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05441) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
                String str = this.$key;
                int i3 = this.$value;
                this.label = 1;
                if (dataStoreUtils.saveIntData(str, i3, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveSyncLongData$1", f = "DataStoreUtils.kt", i = {}, l = {R2.attr.adjustable}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveSyncLongData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05451 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ long $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05451(String str, long j2, Continuation<? super C05451> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = j2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05451(this.$key, this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05451) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
                String str = this.$key;
                long j2 = this.$value;
                this.label = 1;
                if (dataStoreUtils.saveLongData(str, j2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0010\u0002\u001a\u00020\u0001*\u00020\u0000H\u008a@"}, d2 = {"Lkotlinx/coroutines/CoroutineScope;", "", "<anonymous>"}, k = 3, mv = {1, 8, 0})
    @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$saveSyncStringData$1", f = "DataStoreUtils.kt", i = {}, l = {R2.attr.activityChooserViewStyle}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$saveSyncStringData$1, reason: invalid class name and case insensitive filesystem */
    public static final class C05461 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $key;
        final /* synthetic */ String $value;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C05461(String str, String str2, Continuation<? super C05461> continuation) {
            super(2, continuation);
            this.$key = str;
            this.$value = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            return new C05461(this.$key, this.$value, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        @Nullable
        public final Object invoke(@NotNull CoroutineScope coroutineScope, @Nullable Continuation<? super Unit> continuation) {
            return ((C05461) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreUtils dataStoreUtils = DataStoreUtils.INSTANCE;
                String str = this.$key;
                String str2 = this.$value;
                this.label = 1;
                if (dataStoreUtils.saveStringData(str, str2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    private DataStoreUtils() {
    }

    public static /* synthetic */ boolean readBooleanData$default(DataStoreUtils dataStoreUtils, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return dataStoreUtils.readBooleanData(str, z2);
    }

    public static /* synthetic */ Flow readBooleanFlow$default(DataStoreUtils dataStoreUtils, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        return dataStoreUtils.readBooleanFlow(str, z2);
    }

    public static /* synthetic */ float readFloatData$default(DataStoreUtils dataStoreUtils, String str, float f2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            f2 = 0.0f;
        }
        return dataStoreUtils.readFloatData(str, f2);
    }

    public static /* synthetic */ Flow readFloatFlow$default(DataStoreUtils dataStoreUtils, String str, float f2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            f2 = 0.0f;
        }
        return dataStoreUtils.readFloatFlow(str, f2);
    }

    public static /* synthetic */ int readIntData$default(DataStoreUtils dataStoreUtils, String str, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return dataStoreUtils.readIntData(str, i2);
    }

    public static /* synthetic */ Flow readIntFlow$default(DataStoreUtils dataStoreUtils, String str, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        return dataStoreUtils.readIntFlow(str, i2);
    }

    public static /* synthetic */ long readLongData$default(DataStoreUtils dataStoreUtils, String str, long j2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        return dataStoreUtils.readLongData(str, j2);
    }

    public static /* synthetic */ Flow readLongFlow$default(DataStoreUtils dataStoreUtils, String str, long j2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            j2 = 0;
        }
        return dataStoreUtils.readLongFlow(str, j2);
    }

    public static /* synthetic */ String readStringData$default(DataStoreUtils dataStoreUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = "";
        }
        return dataStoreUtils.readStringData(str, str2);
    }

    public static /* synthetic */ Flow readStringFlow$default(DataStoreUtils dataStoreUtils, String str, String str2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str2 = "";
        }
        return dataStoreUtils.readStringFlow(str, str2);
    }

    @Nullable
    public final Object clear(@NotNull Continuation<? super Unit> continuation) {
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        Object objEdit = PreferencesKt.edit(dataStore2, new AnonymousClass2(null), continuation);
        return objEdit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEdit : Unit.INSTANCE;
    }

    public final void clearSync() {
        BuildersKt__BuildersKt.runBlocking$default(null, new AnonymousClass1(null), 1, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final <U> Flow<U> getData(@NotNull String key, U u2) {
        Flow<U> flow;
        Intrinsics.checkNotNullParameter(key, "key");
        if (u2 instanceof Long) {
            flow = (Flow<U>) readLongFlow(key, ((Number) u2).longValue());
        } else if (u2 instanceof String) {
            flow = (Flow<U>) readStringFlow(key, (String) u2);
        } else if (u2 instanceof Integer) {
            flow = (Flow<U>) readIntFlow(key, ((Number) u2).intValue());
        } else if (u2 instanceof Boolean) {
            flow = (Flow<U>) readBooleanFlow(key, ((Boolean) u2).booleanValue());
        } else {
            if (!(u2 instanceof Float)) {
                throw new IllegalArgumentException("This type can be saved into DataStore");
            }
            flow = (Flow<U>) readFloatFlow(key, ((Number) u2).floatValue());
        }
        Intrinsics.checkNotNull(flow, "null cannot be cast to non-null type kotlinx.coroutines.flow.Flow<U of com.catchpig.mvvm.utils.DataStoreUtils.getData>");
        return flow;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <U> U getSyncData(@NotNull String key, U u2) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (u2 instanceof Long) {
            return (U) Long.valueOf(readLongData(key, ((Number) u2).longValue()));
        }
        if (u2 instanceof String) {
            return (U) readStringData(key, (String) u2);
        }
        if (u2 instanceof Integer) {
            return (U) Integer.valueOf(readIntData(key, ((Number) u2).intValue()));
        }
        if (u2 instanceof Boolean) {
            return (U) Boolean.valueOf(readBooleanData(key, ((Boolean) u2).booleanValue()));
        }
        if (u2 instanceof Float) {
            return (U) Float.valueOf(readFloatData(key, ((Number) u2).floatValue()));
        }
        throw new IllegalArgumentException("This type can be saved into DataStore");
    }

    public final void init(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        dataStore = PreferenceDataStoreFactoryKt.createDataStore$default(context, Config.DATA_STORE_NAME, null, null, null, 14, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public final <U> Object putData(@NotNull String str, U u2, @NotNull Continuation<? super Unit> continuation) {
        if (u2 instanceof Long) {
            Object objSaveLongData = saveLongData(str, ((Number) u2).longValue(), continuation);
            return objSaveLongData == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSaveLongData : Unit.INSTANCE;
        }
        if (u2 instanceof String) {
            Object objSaveStringData = saveStringData(str, (String) u2, continuation);
            return objSaveStringData == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSaveStringData : Unit.INSTANCE;
        }
        if (u2 instanceof Integer) {
            Object objSaveIntData = saveIntData(str, ((Number) u2).intValue(), continuation);
            return objSaveIntData == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSaveIntData : Unit.INSTANCE;
        }
        if (u2 instanceof Boolean) {
            Object objSaveBooleanData = saveBooleanData(str, ((Boolean) u2).booleanValue(), continuation);
            return objSaveBooleanData == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSaveBooleanData : Unit.INSTANCE;
        }
        if (!(u2 instanceof Float)) {
            throw new IllegalArgumentException("This type can be saved into DataStore");
        }
        Object objSaveFloatData = saveFloatData(str, ((Number) u2).floatValue(), continuation);
        return objSaveFloatData == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objSaveFloatData : Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <U> void putSyncData(@NotNull String key, U value) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (value instanceof Long) {
            saveSyncLongData(key, ((Number) value).longValue());
            return;
        }
        if (value instanceof String) {
            saveSyncStringData(key, (String) value);
            return;
        }
        if (value instanceof Integer) {
            saveSyncIntData(key, ((Number) value).intValue());
        } else if (value instanceof Boolean) {
            saveSyncBooleanData(key, ((Boolean) value).booleanValue());
        } else {
            if (!(value instanceof Float)) {
                throw new IllegalArgumentException("This type can be saved into DataStore");
            }
            saveSyncFloatData(key, ((Number) value).floatValue());
        }
    }

    public final boolean readBooleanData(@NotNull String key, boolean z2) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C05271(booleanRef, key, z2, null), 1, null);
        return booleanRef.element;
    }

    @NotNull
    public final Flow<Boolean> readBooleanFlow(@NotNull final String key, final boolean z2) {
        Intrinsics.checkNotNullParameter(key, "key");
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        final Flow flowM2338catch = FlowKt.m2338catch(dataStore2.getData(), new C05281(null));
        return new Flow<Boolean>() { // from class: com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1

            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 DataStoreUtils.kt\ncom/catchpig/mvvm/utils/DataStoreUtils\n*L\n1#1,222:1\n48#2:223\n133#3:224\n*E\n"})
            /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ boolean $default$inlined;
                final /* synthetic */ String $key$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1$2", f = "DataStoreUtils.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1$emit$1\n*L\n1#1,222:1\n*E\n"})
                /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, String str, boolean z2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$key$inlined = str;
                    this.$default$inlined = z2;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1$2$1 r0 = (com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1$2$1 r0 = new com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        java.lang.String r2 = r4.$key$inlined
                        androidx.datastore.preferences.core.Preferences$Key r2 = androidx.datastore.preferences.core.PreferencesKeys.booleanKey(r2)
                        java.lang.Object r5 = r5.get(r2)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        if (r5 == 0) goto L4b
                        boolean r5 = r5.booleanValue()
                        goto L4d
                    L4b:
                        boolean r5 = r4.$default$inlined
                    L4d:
                        java.lang.Boolean r5 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r5)
                        r0.label = r3
                        java.lang.Object r5 = r6.emit(r5, r0)
                        if (r5 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.utils.DataStoreUtils$readBooleanFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super Boolean> flowCollector, @NotNull Continuation continuation) {
                Object objCollect = flowM2338catch.collect(new AnonymousClass2(flowCollector, key, z2), continuation);
                return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public final float readFloatData(@NotNull String key, float f2) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Ref.FloatRef floatRef = new Ref.FloatRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C05291(floatRef, key, f2, null), 1, null);
        return floatRef.element;
    }

    @NotNull
    public final Flow<Float> readFloatFlow(@NotNull final String key, final float f2) {
        Intrinsics.checkNotNullParameter(key, "key");
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        final Flow flowM2338catch = FlowKt.m2338catch(dataStore2.getData(), new C05301(null));
        return new Flow<Float>() { // from class: com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1

            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 DataStoreUtils.kt\ncom/catchpig/mvvm/utils/DataStoreUtils\n*L\n1#1,222:1\n48#2:223\n205#3:224\n*E\n"})
            /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ float $default$inlined;
                final /* synthetic */ String $key$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1$2", f = "DataStoreUtils.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1$emit$1\n*L\n1#1,222:1\n*E\n"})
                /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, String str, float f2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$key$inlined = str;
                    this.$default$inlined = f2;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1$2$1 r0 = (com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1$2$1 r0 = new com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        java.lang.String r2 = r4.$key$inlined
                        androidx.datastore.preferences.core.Preferences$Key r2 = androidx.datastore.preferences.core.PreferencesKeys.floatKey(r2)
                        java.lang.Object r5 = r5.get(r2)
                        java.lang.Float r5 = (java.lang.Float) r5
                        if (r5 == 0) goto L4b
                        float r5 = r5.floatValue()
                        goto L4d
                    L4b:
                        float r5 = r4.$default$inlined
                    L4d:
                        java.lang.Float r5 = kotlin.coroutines.jvm.internal.Boxing.boxFloat(r5)
                        r0.label = r3
                        java.lang.Object r5 = r6.emit(r5, r0)
                        if (r5 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.utils.DataStoreUtils$readFloatFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super Float> flowCollector, @NotNull Continuation continuation) {
                Object objCollect = flowM2338catch.collect(new AnonymousClass2(flowCollector, key, f2), continuation);
                return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public final int readIntData(@NotNull String key, int i2) {
        Intrinsics.checkNotNullParameter(key, "key");
        Ref.IntRef intRef = new Ref.IntRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C05311(intRef, key, i2, null), 1, null);
        return intRef.element;
    }

    @NotNull
    public final Flow<Integer> readIntFlow(@NotNull final String key, final int i2) {
        Intrinsics.checkNotNullParameter(key, "key");
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        final Flow flowM2338catch = FlowKt.m2338catch(dataStore2.getData(), new C05321(null));
        return new Flow<Integer>() { // from class: com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1

            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 DataStoreUtils.kt\ncom/catchpig/mvvm/utils/DataStoreUtils\n*L\n1#1,222:1\n48#2:223\n157#3:224\n*E\n"})
            /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ int $default$inlined;
                final /* synthetic */ String $key$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1$2", f = "DataStoreUtils.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1$emit$1\n*L\n1#1,222:1\n*E\n"})
                /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, String str, int i2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$key$inlined = str;
                    this.$default$inlined = i2;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1$2$1 r0 = (com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1$2$1 r0 = new com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5a
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        java.lang.String r2 = r4.$key$inlined
                        androidx.datastore.preferences.core.Preferences$Key r2 = androidx.datastore.preferences.core.PreferencesKeys.intKey(r2)
                        java.lang.Object r5 = r5.get(r2)
                        java.lang.Integer r5 = (java.lang.Integer) r5
                        if (r5 == 0) goto L4b
                        int r5 = r5.intValue()
                        goto L4d
                    L4b:
                        int r5 = r4.$default$inlined
                    L4d:
                        java.lang.Integer r5 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r5)
                        r0.label = r3
                        java.lang.Object r5 = r6.emit(r5, r0)
                        if (r5 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.utils.DataStoreUtils$readIntFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super Integer> flowCollector, @NotNull Continuation continuation) {
                Object objCollect = flowM2338catch.collect(new AnonymousClass2(flowCollector, key, i2), continuation);
                return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public final long readLongData(@NotNull String key, long j2) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Ref.LongRef longRef = new Ref.LongRef();
        BuildersKt__BuildersKt.runBlocking$default(null, new C05331(longRef, key, j2, null), 1, null);
        return longRef.element;
    }

    @NotNull
    public final Flow<Long> readLongFlow(@NotNull final String key, final long j2) {
        Intrinsics.checkNotNullParameter(key, "key");
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        final Flow flowM2338catch = FlowKt.m2338catch(dataStore2.getData(), new C05341(null));
        return new Flow<Long>() { // from class: com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1

            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 DataStoreUtils.kt\ncom/catchpig/mvvm/utils/DataStoreUtils\n*L\n1#1,222:1\n48#2:223\n229#3:224\n*E\n"})
            /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ long $default$inlined;
                final /* synthetic */ String $key$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1$2", f = "DataStoreUtils.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1$emit$1\n*L\n1#1,222:1\n*E\n"})
                /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, String str, long j2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$key$inlined = str;
                    this.$default$inlined = j2;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1$2$1 r0 = (com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1$2$1 r0 = new com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5a
                    L29:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L31:
                        kotlin.ResultKt.throwOnFailure(r8)
                        kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                        androidx.datastore.preferences.core.Preferences r7 = (androidx.datastore.preferences.core.Preferences) r7
                        java.lang.String r2 = r6.$key$inlined
                        androidx.datastore.preferences.core.Preferences$Key r2 = androidx.datastore.preferences.core.PreferencesKeys.longKey(r2)
                        java.lang.Object r7 = r7.get(r2)
                        java.lang.Long r7 = (java.lang.Long) r7
                        if (r7 == 0) goto L4b
                        long r4 = r7.longValue()
                        goto L4d
                    L4b:
                        long r4 = r6.$default$inlined
                    L4d:
                        java.lang.Long r7 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r4)
                        r0.label = r3
                        java.lang.Object r7 = r8.emit(r7, r0)
                        if (r7 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.utils.DataStoreUtils$readLongFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super Long> flowCollector, @NotNull Continuation continuation) {
                Object objCollect = flowM2338catch.collect(new AnonymousClass2(flowCollector, key, j2), continuation);
                return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public final String readStringData(@NotNull String key, @NotNull String str) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(str, "default");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = "";
        BuildersKt__BuildersKt.runBlocking$default(null, new C05351(objectRef, key, str, null), 1, null);
        return (String) objectRef.element;
    }

    @NotNull
    public final Flow<String> readStringFlow(@NotNull final String key, @NotNull final String str) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(str, "default");
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        final Flow flowM2338catch = FlowKt.m2338catch(dataStore2.getData(), new C05361(null));
        return new Flow<String>() { // from class: com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1

            @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u008a@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"<anonymous>", "", ExifInterface.GPS_DIRECTION_TRUE, "R", "value", "emit", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1$2"}, k = 3, mv = {1, 8, 0}, xi = 48)
            @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1\n+ 2 Transform.kt\nkotlinx/coroutines/flow/FlowKt__TransformKt\n+ 3 DataStoreUtils.kt\ncom/catchpig/mvvm/utils/DataStoreUtils\n*L\n1#1,222:1\n48#2:223\n181#3:224\n*E\n"})
            /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1$2, reason: invalid class name */
            public static final class AnonymousClass2<T> implements FlowCollector {
                final /* synthetic */ String $default$inlined;
                final /* synthetic */ String $key$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;

                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                @DebugMetadata(c = "com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1$2", f = "DataStoreUtils.kt", i = {}, l = {223}, m = "emit", n = {}, s = {})
                @SourceDebugExtension({"SMAP\nEmitters.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Emitters.kt\nkotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$1$1$emit$1\n*L\n1#1,222:1\n*E\n"})
                /* renamed from: com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1$2$1, reason: invalid class name */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, String str, String str2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$key$inlined = str;
                    this.$default$inlined = str2;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                @org.jetbrains.annotations.Nullable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1$2$1 r0 = (com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1$2$1 r0 = new com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L51
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlinx.coroutines.flow.FlowCollector r6 = r4.$this_unsafeFlow
                        androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
                        java.lang.String r2 = r4.$key$inlined
                        androidx.datastore.preferences.core.Preferences$Key r2 = androidx.datastore.preferences.core.PreferencesKeys.stringKey(r2)
                        java.lang.Object r5 = r5.get(r2)
                        java.lang.String r5 = (java.lang.String) r5
                        if (r5 != 0) goto L48
                        java.lang.String r5 = r4.$default$inlined
                    L48:
                        r0.label = r3
                        java.lang.Object r5 = r6.emit(r5, r0)
                        if (r5 != r1) goto L51
                        return r1
                    L51:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.catchpig.mvvm.utils.DataStoreUtils$readStringFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            @Nullable
            public Object collect(@NotNull FlowCollector<? super String> flowCollector, @NotNull Continuation continuation) {
                Object objCollect = flowM2338catch.collect(new AnonymousClass2(flowCollector, key, str), continuation);
                return objCollect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    @Nullable
    public final Object saveBooleanData(@NotNull String str, boolean z2, @NotNull Continuation<? super Unit> continuation) {
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        Object objEdit = PreferencesKt.edit(dataStore2, new C05372(str, z2, null), continuation);
        return objEdit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEdit : Unit.INSTANCE;
    }

    @Nullable
    public final Object saveFloatData(@NotNull String str, float f2, @NotNull Continuation<? super Unit> continuation) {
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        Object objEdit = PreferencesKt.edit(dataStore2, new C05382(str, f2, null), continuation);
        return objEdit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEdit : Unit.INSTANCE;
    }

    @Nullable
    public final Object saveIntData(@NotNull String str, int i2, @NotNull Continuation<? super Unit> continuation) {
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        Object objEdit = PreferencesKt.edit(dataStore2, new C05392(str, i2, null), continuation);
        return objEdit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEdit : Unit.INSTANCE;
    }

    @Nullable
    public final Object saveLongData(@NotNull String str, long j2, @NotNull Continuation<? super Unit> continuation) {
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        Object objEdit = PreferencesKt.edit(dataStore2, new C05402(str, j2, null), continuation);
        return objEdit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEdit : Unit.INSTANCE;
    }

    @Nullable
    public final Object saveStringData(@NotNull String str, @NotNull String str2, @NotNull Continuation<? super Unit> continuation) {
        DataStore<Preferences> dataStore2 = dataStore;
        if (dataStore2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dataStore");
            dataStore2 = null;
        }
        Object objEdit = PreferencesKt.edit(dataStore2, new C05412(str, str2, null), continuation);
        return objEdit == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objEdit : Unit.INSTANCE;
    }

    public final void saveSyncBooleanData(@NotNull String key, boolean value) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        BuildersKt__BuildersKt.runBlocking$default(null, new C05421(key, value, null), 1, null);
    }

    public final void saveSyncFloatData(@NotNull String key, float value) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        BuildersKt__BuildersKt.runBlocking$default(null, new C05431(key, value, null), 1, null);
    }

    public final void saveSyncIntData(@NotNull String key, int value) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        BuildersKt__BuildersKt.runBlocking$default(null, new C05441(key, value, null), 1, null);
    }

    public final void saveSyncLongData(@NotNull String key, long value) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        BuildersKt__BuildersKt.runBlocking$default(null, new C05451(key, value, null), 1, null);
    }

    public final void saveSyncStringData(@NotNull String key, @NotNull String value) throws InterruptedException {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        BuildersKt__BuildersKt.runBlocking$default(null, new C05461(key, value, null), 1, null);
    }
}
